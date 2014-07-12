package rusk.interceptor;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.Path;

import mockit.Mocked;

import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.junit.Before;
import org.junit.Test;

import rusk.Transactional;
import rusk.interceptor.AccessLoggingInterceptor;
import rusk.interceptor.RuskInterceptionService;
import rusk.interceptor.TransactionInterceptor;

public class RuskInterceptionServiceTest {
    
    @Mocked
    private ServiceLocator locator;
    
    private RuskInterceptionService service;
    
    @Before
    public void setup() {
        service = new RuskInterceptionService(locator);
    }
    
    @Test
    public void リソースクラスのメソッドを渡すと_アクセスロギング用のインターセプターが取得できること() throws Exception {
        // setup
        Method method = MockResource.class.getMethod("method");
        
        // exercise
        List<MethodInterceptor> interceptors = service.getMethodInterceptors(method);
        
        // verify
        assertThat(interceptors, contains(instanceOf(AccessLoggingInterceptor.class)));
    }
    
    @Test
    public void トランザクション制御対象のクラスのメソッドを渡すと_トランザクションインターセプターが取得できること() throws Exception {
        // setup
        Method method = MockeTransactional.class.getMethod("method");
        
        // exercise
        List<MethodInterceptor> interceptors = service.getMethodInterceptors(method);
        
        // verify
        assertThat(interceptors, contains(instanceOf(TransactionInterceptor.class)));
    }
    
    @Path("")
    public static class MockResource {
        public void method() {}
    }
    
    @Transactional
    public static class MockeTransactional {
        public void method() {}
    }
}
