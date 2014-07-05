package rusk.rest.interceptor;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.ServiceLocator;

import rusk.rest.system.db.RuskConnection;

public class TransactionInterceptor implements MethodInterceptor {
    private final ServiceLocator locator;
    
    @Inject
    public TransactionInterceptor(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        RuskConnection connection = locator.getService(RuskConnection.class);
        
        Object result;
        
        try {
            connection.beginTransaction();
            result = invocation.proceed();
            connection.commit();
            return result;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        }
    }
}
