package rusk.rest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuskConfig extends ResourceConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(RuskConfig.class);
    
    public RuskConfig() {
        packages(this.getClass().getPackage().getName());

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                logger.debug("configure");
                bindAsContract(SampleResource.class);
            }
        });
    }
}
