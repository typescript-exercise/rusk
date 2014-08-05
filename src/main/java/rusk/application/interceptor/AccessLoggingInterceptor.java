package rusk.application.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web RESTful API へのアクセスログを記録するためのインターセプター。
 */
public class AccessLoggingInterceptor implements MethodInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(AccessLoggingInterceptor.class);
    
    private final ServiceLocator locator;
    
    public AccessLoggingInterceptor(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        HttpServletRequest request = this.locator.getService(HttpServletRequest.class);
        
        if (request != null) {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            logger.debug("method = {}, uri = {}", method, uri);
        }
        
        return invocation.proceed();
    }

}
