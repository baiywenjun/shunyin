package com.shunyin.common.apiUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

public class HttpRequestor {
	private static Logger log = LoggerFactory.getLogger(HttpRequestor.class);
	private static String charset = "utf-8";
	private static Integer connectTimeout = null;
	private static Integer socketTimeout = null;
	private static String proxyHost = null;
	private static Integer proxyPort = null;
	public static HttpRequestor HttpRequestor;

	private HttpRequestor() {

	}

	public synchronized static HttpRequestor getInstance() {
		if (HttpRequestor == null) {
			HttpRequestor = new HttpRequestor();
		}
		return HttpRequestor;
	}

	public String doGet(String url) {
		String result = "";
		HttpClient httpClient = null;
		try {
			// 根据地址获取请求
			HttpGet request = new HttpGet(url);// 这里发送get请求
			// 获取当前客户端对象
			httpClient = new SSLClient();
			// 通过请求对象获取响应对象
			HttpResponse response = httpClient.execute(request);
			// 判断网络连接状态码是否正常(0--200都数正常)
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	public String doGet(String url, Map<String, Object> paramMap) {
		String result = "";
		HttpClient httpClient = null;
		try {
			// 根据地址获取请求
			String apiUrl = url;
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : paramMap.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(paramMap.get(key));
				i++;
			}
			apiUrl += URLEncoder.encode(param.toString());
			HttpGet request = new HttpGet(apiUrl);// 这里发送get请求

			// 获取当前客户端对象
			httpClient = new SSLClient();
			// 通过请求对象获取响应对象
			HttpResponse response = httpClient.execute(request);
			// 判断网络连接状态码是否正常(0--200都数正常)
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	/**
	 * 用于调用ssl接口
	 **/
	public String GetByGbk(String url, Map<String, Object> paramMap) {
		String result = "";
		HttpClient httpClient = null;
		try {
			// 根据地址获取请求
			String apiUrl = url;
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : paramMap.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(paramMap.get(key));
				i++;
			}
			apiUrl +=param.toString();
			HttpGet request = new HttpGet(apiUrl);// 这里发送get请求
			request.addHeader("Content-Type","text/xml;");
			request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;");
			// 获取当前客户端对象
			httpClient = new SSLClient();
			// 通过请求对象获取响应对象
			HttpResponse response = httpClient.execute(request);
			
			// 判断网络连接状态码是否正常(0--200都数正常)
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity responseEntity=response.getEntity();
		result = inputStreamToString(responseEntity.getContent());
		 log.debug("{result=>\r\n"+result+"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	public String PostMethoad(String url, LinkedHashMap<String, String> map) {
		String result = "";
		try {
HttpClient client=new DefaultHttpClient();
			
			HttpPost postMethoad = new HttpPost();
			postMethoad.addHeader("Content-Type", "text/html;charset=gbk");
			postMethoad.addHeader("accept","text/html");
			postMethoad.addHeader("Connection","keep-alive");
			postMethoad.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
            log.debug("{创建模拟请求对象实例成功}");
			URI uri = new URI(url);
			postMethoad.setURI(uri);
			  log.debug("{设置请求目标地址}");
			 String param = foreachMap(map, "mac");
			StringEntity entity = new StringEntity(param, "utf-8");
			postMethoad.setEntity(entity);
			
			
			
			HttpResponse response = client.execute(postMethoad);
			response.setHeader("Content-Type","application/json;charset=UTF-8");
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				log.debug("{请求代付接口数据,状态成功。【" + result + "】}");
				return result;
			}
			log.debug("{请求代付接口数据,请求失败，结果【" + result + "】}");
			return result;
		} catch (Exception e) {
			log.error("{模拟请求执行错误【" + e.getMessage() + "】}");
			return result;
		}
	}

	public String doPost(String url, Map<String, Object> map) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}

			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			httpPost.addHeader("Content-Typ","application/x-www-form-urlencoded; charset=utf-8");
			httpPost.addHeader("Accept","text/plain;charset=utf-8");
			HttpResponse response = httpClient.execute(httpPost);
			
			if (response != null) {
				HttpEntity resEntity = response.getEntity();

				if (resEntity != null) {
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return result;
		}
		return result;
	}

	// 内部类,实现DefaultHttpClient,忽略SSL证书
	class SSLClient extends DefaultHttpClient {
		public SSLClient() throws Exception {
			super();
			SSLContext ctx = SSLContext.getInstance("TLSv1.2");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			
			ctx.init(null, new TrustManager[] { tm }, new java.security.SecureRandom());
			SSLSocketFactory ssf =new SSLSocketFactory(ctx,new X509HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					// TODO Auto-generated method stub
					return true;
				}
				
				@Override
				public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
				}
				
				@Override
				public void verify(String arg0, X509Certificate arg1) throws SSLException {
				}
				@Override
				public void verify(String arg0, SSLSocket arg1) throws IOException {
				}
			});
			ClientConnectionManager ccm = this.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
		}
	}

	private static String foreachMap(LinkedHashMap<String, String> request, String excludeKey) {
		StringBuffer str = new StringBuffer();
		for (String key : request.keySet()) {
			if (!excludeKey.equals(key)) {
				String value = (String) request.get(key);
				if (StringUtils.isNotBlank(value)) {
					str.append("&").append(key).append("=").append((String) request.get(key));
				}
			}
		}
		if (str.length() > 1) {
			return str.toString().substring(1);
		}
		return str.toString();
	}
	private String inputStreamToString(InputStream is) {

        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        try {
        	BufferedReader	rd = new BufferedReader(new InputStreamReader(is,"gbk"));
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        // Return full string
        return total.toString();
    }
}