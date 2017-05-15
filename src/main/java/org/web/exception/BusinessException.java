package org.web.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.web.log.Logger;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	String stack;
	String operate;
	public void render(HttpServletRequest request,
			HttpServletResponse response) {
		Logger.error(this.getMessage());
		response.setContentType("UTF-8");
		response.addHeader("X-Error", Boolean.TRUE.toString());
		String exMessage = this.getMessage() == null ? "未知异常" : this.getMessage();
		exMessage = exMessage.split("\n")[0];

		ExceptionResult er = new ExceptionResult();
	
		er.setMessage(exMessage);
		try {
			
			response.getWriter().write(this.getStack());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStack() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		this.printStackTrace(pw);
		return "\r\n" + sw.toString() + "\r\n";
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getOperate() {
		return this.operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
}
