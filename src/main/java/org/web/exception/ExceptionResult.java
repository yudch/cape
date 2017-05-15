package org.web.exception;

public class ExceptionResult
{
  String message;
  String stack;
  String operate;
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public String getStack()
  {
    return this.stack;
  }
  
  public void setStack(String stack)
  {
    this.stack = stack;
  }
  
  public String getOperate()
  {
    return this.operate;
  }
  
  public void setOperate(String operate)
  {
    this.operate = operate;
  }
}
