package integration_test.jersey;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.rules.ExternalResource;

import rusk.rest.RuskConfig;

public class JerseyTestRule extends ExternalResource {
    
    private JerseyTest jerseyTest;
    
    public JerseyTestRule() {
        this.jerseyTest = new JerseyTest() {
            @Override
            protected Application configure() {
                return new RuskConfig(new TestRuskHK2Binder());
            }
        };
    }
    
    @Override
    public void before() throws Throwable {
        jerseyTest.setUp();
        jerseyTest.target("system").request().post(Entity.text(""));
    }
    
    @Override
    public void after() {
        try {
            jerseyTest.tearDown();
        } catch (Exception e) {
            throw new RuntimeException("failed to tear down JerseyTest.", e);
        }
    }
    
    public JerseyTest getTest() {
        return this.jerseyTest;
    }
}
