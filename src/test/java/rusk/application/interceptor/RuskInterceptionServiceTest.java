package rusk.application.interceptor;

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

import rusk.application.interceptor.AccessLoggingInterceptor;
import rusk.application.interceptor.RuskInterceptionService;
import rusk.application.interceptor.SystemInitializeInterceptor;
import rusk.application.interceptor.TransactionInterceptor;
import rusk.application.interceptor.Transactional;

public class RuskInterceptionServiceTest {
    
    @Mocked
    private ServiceLocator locator;
    
    private RuskInterceptionService service;
    
    @Before
    public void setup() {
        service = new RuskInterceptionService(locator);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void リソースクラスのメソッドを渡すと_アクセスロギングとシステム初期化のインターセプターが取得できること() throws Exception {
        // setup
        Method method = MockResource.class.getMethod("method");
        
        // exercise
        List<MethodInterceptor> interceptors = service.getMethodInterceptors(method);
        
        // verify
        assertThat(interceptors, contains(instanceOf(AccessLoggingInterceptor.class), instanceOf(SystemInitializeInterceptor.class)));
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
