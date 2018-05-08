package com.shunyin.exception;

import com.shunyin.common.util.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author HackPeng
 * @email gzpjx@top
 * @date 2017年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		return r;
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error(2,"已存在该记录");
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		logger.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public R handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
		logger.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}
	
	
	/**
	 * 处理参数绑定异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public R handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		logger.error(e.getMessage(), e);
		return R.error(803,"参数类型错误");
	}
}
