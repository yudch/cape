package org.web.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Platform
{
  private static final String LOGGER = "logger";
  private static volatile Platform pl;
  private Properties pi;
  
  public String getLogger()
  {
    return this.pi.getProperty("logger");
  }
  
  public static String getBaseHome()
  {
    return System.getProperty("nc.server.location");
  }
  
  public static final boolean isDevelopMode()
  {
    return "develop".equals(System.getProperty("nc.runMode"));
  }
  
  private Platform()
  {
    InputStream inf = null;
    try
    {
      inf = Thread.currentThread().getContextClassLoader().getResourceAsStream("prop.inf");
      
      this.pi = new Properties();
      this.pi.load(inf); return;
    }
    catch (Exception e)
    {
      throw new RuntimeException("can't find prop.inf in classpath.");
    }
    finally
    {
      if (inf != null) {
        try
        {
          inf.close();
        }
        catch (IOException e) {}
      }
    }
  }
  
  public static Platform get()
  {
    if (pl == null) {
      synchronized (Platform.class)
      {
        if (pl == null) {
          pl = new Platform();
        }
      }
    }
    return pl;
  }
}
