package rusk.rest.interceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;

import rusk.Transactional;

public class RuskInterceptionService implements InterceptionService {
    
    private final MethodInterceptor accessLogging;
    private final MethodInterceptor transaction;
    
    @Inject
    public RuskInterceptionService(ServiceLocator locator) {
        this.accessLogging = new AccessLoggingInterceptor(locator);
        this.transaction = new TransactionInterceptor(locator);
    }
    
    @Override
    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        List<MethodInterceptor> interceptors = new ArrayList<>();
        
        if (isResourceMethod(method)) {
            interceptors.add(this.accessLogging);
        }
        
        if (isTransactionalMethod(method)) {
            interceptors.add(this.transaction);
        }
        
        return Collections.unmodifiableList(interceptors);
    }

    private static boolean isTransactionalMethod(Method method) {
        return method.getDeclaringClass().isAnnotationPresent(Transactional.class);
    }

    private static boolean isResourceMethod(Method method) {
        return method.getDeclaringClass().isAnnotationPresent(Path.class);
    }

    @Override
    public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> constructor) {
        return null;
    }

    @Override
    public Filter getDescriptorFilter() {
        return BuilderHelper.allFilter();
    }
}
