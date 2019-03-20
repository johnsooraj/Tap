package com.cyspan.tap.commons.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {

protected final Log logger = LogFactory.getLog(getClass());
	
	public void info(Object message){
		logger.info(message);
	}
	public void debug(Object message){
		logger.debug(message);
	}
	public void warn(Object message){
		logger.warn(message);
	}
	public void error(Object message){
		logger.error(message);
	}
	public void fatal(Object message){
		logger.fatal(message);
	}
	public void trace(Object message){
		logger.trace(message);
	}
    public boolean isInfoEnabled(){
        return logger.isInfoEnabled();
    }
    public boolean isDebugEnabled(){
        return logger.isDebugEnabled();
    }
    public boolean isWarnEnabled(){
        return logger.isWarnEnabled();
    }
    public boolean isErrorEnabled(){
        return logger.isErrorEnabled();
    }
    public boolean isFatalEnabled(){
        return logger.isFatalEnabled();
    }
    public boolean isTraceEnabled(){
        return logger.isTraceEnabled();
    }  
	
	
	
	
	
}
