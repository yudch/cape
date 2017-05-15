package org.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

public class WebExceptionHandler  implements HandlerExceptionResolver
{
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex)
  {
    ModelAndView mv = new ModelAndView();
    View view = new ExceptionView(ex);
    mv.setView(view);
    return mv;
  }
}
