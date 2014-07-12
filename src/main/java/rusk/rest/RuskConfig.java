package rusk.rest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import rusk.RuskHK2Binder;
import rusk.rest.system.SystemResource;

/**
 * Jersey の設定クラス。
 */
public class RuskConfig extends ResourceConfig {
    
    public RuskConfig() {
        packages(this.getClass().getPackage().getName());
        this.registerResources();
        register(new RuskHK2Binder());
    }
    
    /**
     * Binder を指定してインスタンスを生成する。
     * <p>
     * このコンストラクタは、 HK2 に登録するコンポーネントを外部から指定する場合に使用します。
     * 例えば、テストの時に DB 接続先を切り替えた Binder を設定する場合などに使用します。
     * 
     * @param binder Binder
     */
    public RuskConfig(AbstractBinder binder) {
        packages(this.getClass().getPackage().getName());
        this.registerResources();
        register(binder);
    }
    
    private void registerResources() {
        register(SystemResource.class);
        register(TaskListResource.class);
    }
}
