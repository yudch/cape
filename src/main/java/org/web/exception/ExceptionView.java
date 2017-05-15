package org.web.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.View;
import org.web.core.Platform;
import org.web.log.Logger;

public class ExceptionView implements View {
	private static final String X_ERROR = "X-Error";
	Exception ex;

	public ExceptionView(Exception ex) {
		this.ex = ex;
	}

	public String getContentType() {
		return "application/Json";
	}

	public void render(Map<String, ?> arg0, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Logger.error(this.ex);
		response.setContentType("UTF-8");
		response.addHeader("X-Error", Boolean.TRUE.toString());
		String exMessage = this.ex.getMessage() == null ? "未知异常" : this.ex
				.getMessage();
		exMessage = exMessage.split("\n")[0];

		ExceptionResult er = new ExceptionResult();
		if ((this.ex instanceof WebRuntimeException)) {
			er.setOperate(((WebRuntimeException) this.ex).getOperate());
			String hint = ((WebRuntimeException) this.ex).getHint();
			if (!StringUtils.isEmpty(hint)) {
				exMessage = hint;
			}
		}
		er.setMessage(exMessage);
		if (Platform.isDevelopMode()) {
			StringWriter out = new StringWriter();
			PrintWriter pw = new PrintWriter(out);
			this.ex.printStackTrace(pw);
			String stack = out.toString();
			er.setStack(stack);
		}
//		String str = JsonUtil.toJson(er);
//		response.getWriter().write(str);
	}
}
