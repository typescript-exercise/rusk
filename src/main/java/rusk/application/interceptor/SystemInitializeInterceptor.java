package rusk.application.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.ServiceLocator;

import rusk.application.facade.system.SystemInitializeFacade;

public class SystemInitializeInterceptor implements MethodInterceptor {
    
    private final ServiceLocator locator;
    
    public SystemInitializeInterceptor(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        SystemInitializeFacade system = this.locator.getService(SystemInitializeFacade.class);
        
        if (!system.isInitialized()) {
            system.initialize();
        }
        
        return invocation.proceed();
    }
}
