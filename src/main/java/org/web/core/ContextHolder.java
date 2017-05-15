package org.web.core;

import org.springframework.context.ApplicationContext;

/**
 * Created by Administrator on 2015/6/29.
 */
public class ContextHolder {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        if (context != null) {
            ContextHolder.context = context;
        }
    }
}
