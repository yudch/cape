package org.web.log;

public abstract interface ILogger
{
  public abstract void info(String paramString);
  
  public abstract void console(String paramString);
  
  public abstract void debug(String paramString);
  
  public abstract void error(String paramString, Throwable paramThrowable);
  
  public abstract void error(String paramString);
  
  public abstract void error(Throwable paramThrowable);
  
  public abstract void warn(String paramString);
  
  public abstract boolean isDebugEnabled();
  
  public abstract boolean isInfoEnabled();
  
  public abstract boolean isWarnEnabled();
  
  public abstract boolean isErrorEnabled();
}
