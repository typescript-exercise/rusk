package rusk.system.db;

import javax.inject.Inject;

import net.sf.persist.Persist;

import org.glassfish.hk2.api.ServiceLocator;

/**
 * {@link Persist} のインスタンスを取得するプロバイダー。
 * <p>
 * このプロバイダから取得する {@link Persist} には、現在のリクエストスコープに紐付いたコネクションが設定されています。
 */
public class HK2PersistProvider implements PersistProvider {
    
    private final ServiceLocator locator;

    /**
     * コンストラクタ。
     * 
     * @param locator {@code ServiceLocator}
     */
    @Inject
    public HK2PersistProvider(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    public Persist getPersist() {
        RuskConnection connection = this.locator.getService(RuskConnection.class);
        return new Persist(connection.getConnection());
    }
}
