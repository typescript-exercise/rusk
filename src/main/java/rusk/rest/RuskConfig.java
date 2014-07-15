package rusk.rest;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
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
        register(binder);
    }
    
    /**
     * 指定したリソースクラスをベースにした{@link UriBuilder} を取得する。
     * 
     * @param resourceClass リソースクラス
     * @return 指定したリソースクラスをベースにした{@link UriBuilder}
     */
    public static UriBuilder resource(Class<?> resourceClass) {
        return UriBuilder.fromPath("rest").path(resourceClass);
    }
}
