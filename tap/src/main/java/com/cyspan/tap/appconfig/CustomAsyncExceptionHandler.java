package com.cyspan.tap.appconfig;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	static Logger logger = Logger.getLogger(CustomAsyncExceptionHandler.class.getName());

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		logger.error("ASYNC(message) - " + ex.getMessage());
		logger.error("ASYNC(method) - " + method.getName());
		for (final Object param : params) {
			logger.error("ASYNC(param) - " + param);
		}
	}

}
