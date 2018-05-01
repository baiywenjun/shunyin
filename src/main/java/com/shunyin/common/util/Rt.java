package com.shunyin.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 */
public class Rt extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public Rt() {
		put("code", 0);
		put("count",0);
		put("msg", "success");
	}
	
	public static Rt error() {
		return error(500, "服务器异常，请联系管理员");
	}
	
	public static Rt error(String msg) {
		return error(500, msg);
	}
	
	public static Rt error(int code, String msg) {
		Rt rt = new Rt();
		rt.put("code", code);
		rt.put("count",0);
		rt.put("msg", msg);
		rt.put("data", null);
		return rt;
	}

	public static Rt ok(int count,Object data) {
		Rt rt = new Rt();
		rt.put("count", count);
		rt.put("data",data);
		return rt;
	}
	
	public static Rt ok(Map<String, Object> map) {
		Rt rt = new Rt();
		rt.putAll(map);
		return rt;
	}

	@Override
	public Rt put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
