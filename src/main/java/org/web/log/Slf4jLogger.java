package org.web.log;


public class Slf4jLogger implements  ILogger{

	@Override
	public void info(String msg) {
	}

	@Override
	public void console(String msg) {
	}

	@Override
	public void debug(String msg) {
	}

	@Override
	public void error(String msg, Throwable t) {
		t.printStackTrace();
	}

	@Override
	public void error(String msg) {
	}

	@Override
	public void error(Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void warn(String msg) {
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

}
