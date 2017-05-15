package org.web.exception;

public class WebRuntimeException
  extends RuntimeException
{
  String operate;
  String hint;
  private static final long serialVersionUID = -898303141841662298L;
  
  public WebRuntimeException(String message)
  {
    super(message);
  }
  
  public WebRuntimeException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public String getOperate()
  {
    return this.operate;
  }
  
  public void setOperate(String operate)
  {
    this.operate = operate;
  }
  
  public String getHint()
  {
    return this.hint;
  }
  
  public void setHint(String hint)
  {
    this.hint = hint;
  }
}
