package rusk.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.ServiceLocator;

import rusk.service.system.SystemInitializeService;

public class SystemInitializeInterceptor implements MethodInterceptor {
    
    private final ServiceLocator locator;
    
    public SystemInitializeInterceptor(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        SystemInitializeService system = this.locator.getService(SystemInitializeService.class);
        
        if (!system.isInitialized()) {
            system.initialize();
        }
        
        return invocation.proceed();
    }
}
