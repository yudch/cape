package org.web.log;

import org.web.core.Platform;
import org.web.utils.ClassUtil;

public class Logger
{
  private static volatile ILogger log = null;
  
  public static void info(String msg)
  {
    getLogStub().info(msg);
  }
  
  public static void console(String msg)
  {
    debug(msg);
  }
  
  public static void debug(String msg)
  {
    getLogStub().debug(msg);
  }
  
  public static void error(String msg, Throwable t)
  {
    getLogStub().error(msg, t);
  }
  
  public static void error(String msg)
  {
    getLogStub().error(msg);
  }
  
  public static void error(Throwable e)
  {
    getLogStub().error(e.getMessage(), e);
  }
  
  public static void warn(String msg)
  {
    getLogStub().warn(msg);
  }
  
  public static boolean isDebugEnabled()
  {
    return getLogStub().isDebugEnabled();
  }
  
  public static boolean isInfoEnabled()
  {
    return getLogStub().isInfoEnabled();
  }
  
  public static boolean isWarnEnabled()
  {
    return getLogStub().isWarnEnabled();
  }
  
  public static boolean isErrorEnabled()
  {
    return getLogStub().isErrorEnabled();
  }
  
  public static ILogger getLogStub()
  {
    if (log == null) {
      synchronized (Logger.class)
      {
        if (log == null)
        {
          String className = Platform.get().getLogger();
          log = (ILogger)ClassUtil.newInstance(className);
        }
      }
    }
    return log;
  }
}
