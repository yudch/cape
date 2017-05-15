package org.web.utils;

import java.lang.reflect.Constructor;

import org.web.exception.WebRuntimeException;

public class ClassUtil
{
  public static Class<?> forName(String className, ClassLoader loader)
  {
    try
    {
      return Class.forName(className.trim(), true, loader);
    }
    catch (ClassNotFoundException e)
    {
      throw new WebRuntimeException("ClassNotFoundException", e);
    }
  }
  
  public static Class<?> forName(String className)
  {
    return forName(className, Thread.currentThread().getContextClassLoader());
  }
  
  public static Object newInstance(String className)
  {
    if ((className == null) || (className.equals(""))) {
      throw new WebRuntimeException("require className");
    }
    return newInstance(forName(className));
  }
  
  public static Object newInstance(String className, ClassLoader loader)
  {
    return newInstance(forName(className, loader));
  }
  
  public static Object newInstance(String className, Class[] params, Object[] values)
  {
    return newInstance(forName(className), params, values);
  }
  
  public static Object newInstance(String className, Class[] params, Object[] values, ClassLoader loader)
  {
    return newInstance(forName(className, loader), params, values);
  }
  
  public static <T> T newInstance(Class<T> c, Class[] params, Object[] values)
  {
    try
    {
      Constructor<T> cs = c.getConstructor(params);
      return (T)cs.newInstance(values);
    }
    catch (Exception e)
    {
      throw new WebRuntimeException("ClassNotFoundException", e);
    }
  }
  
  public static <T> T newInstance(Class<T> c)
  {
    if (c == null) {
      return null;
    }
    try
    {
      return (T)c.newInstance();
    }
    catch (Exception e)
    {
      throw new WebRuntimeException("create instance error:" + c.getName(), e);
    }
  }
}
