package rusk.rest;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Jersey の設定クラス。
 */
public class RuskConfig extends ResourceConfig {
    
    public RuskConfig() {
        packages(this.getClass().getPackage().getName());
        register(new RuskHK2Binder());
    }
}
