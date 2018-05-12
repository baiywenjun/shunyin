package com.shunyin.common.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service("uploadService")
public class UploadService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
    /**
	 * 文件上传
	 * @param destDir 上传的目标目录
	 * @param request
	 * @param response
	 * @return 上传成功后返回的上传地址
	 * @throws UnsupportedEncodingException
	 */
	public UpResult doPost(String destDir, HttpServletRequest request, HttpServletResponse response) 
			throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		upload.setFileSizeMax(4 * 1024 * 1024 );
		upload.setSizeMax(128 * 1024 * 1024);
		
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				
				List<FileItem> itemList = upload.parseRequest(request);
				
				for (FileItem fileItem : itemList) {
					
					if (fileItem.isFormField()) {
						// 是普通表单项
						String fieldName = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						log.info("=====>"+ "[input:name]:" + fieldName + " : [input:value]:" + value);
						
					} else {
						
						// 是附件上传项
						String fileName = fileItem.getName();
						if (fileName == null || "".equals(fileName.trim())) {// 如果用户没有上传任何，返回
							
							return null;
						} else {
							// 获取文件信息
							String contentType = fileItem.getContentType();
							long size = fileItem.getSize();
							InputStream in = fileItem.getInputStream();
							log.info("=====>" + "上传文件类型:" + contentType + "  上传文件大小:" + size);
							// 写文件
							String uuidName = UUID.randomUUID().toString().replace("-", "") + "@" + fileName;
							String filePath = destDir + uuidName;
							
							File destDirFile = new File(destDir);
							if(false == destDirFile.exists()){
								destDirFile.mkdirs();
							}
							
							fileItem.write(new File(filePath));
							fileItem.delete();
							
							UpResult result = new UpResult(fileName,uuidName, destDir, filePath );
							
							return result;
						}
					}
					
				}
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}