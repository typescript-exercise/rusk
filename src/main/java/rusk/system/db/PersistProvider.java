package rusk.system.db;

import javax.inject.Inject;

import net.sf.persist.Persist;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;

/**
 * {@link Persist} のインスタンスを取得するためのプロバイダー。
 * <p>
 * このプロバイダから取得する {@link Persist} には、現在のリクエストスコープに紐付いたコネクションが設定されています。
 */
public class PersistProvider implements Factory<Persist> {
    
    private final ServiceLocator locator;

    /**
     * コンストラクタ。
     * 
     * @param locator {@code ServiceLocator}
     */
    @Inject
    public PersistProvider(ServiceLocator locator) {
        this.locator = locator;
    }

    /**
     * {@link Persist} のインスタンスを取得する。
     */
    @Override
    public Persist provide() {
        RuskConnection connection = this.locator.getService(RuskConnection.class);
        return new Persist(connection.getConnection());
    }

    @Override public void dispose(Persist instance) {/* no use */}
}
