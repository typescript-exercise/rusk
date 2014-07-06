package rusk.rest;

import org.glassfish.jersey.server.ResourceConfig;

import rusk.RuskHK2Binder;

/**
 * Jersey の設定クラス。
 */
public class RuskConfig extends ResourceConfig {
    
    public RuskConfig() {
        packages(this.getClass().getPackage().getName());
        register(new RuskHK2Binder());
    }
}
