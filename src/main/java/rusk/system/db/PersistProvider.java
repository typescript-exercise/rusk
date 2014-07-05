package rusk.system.db;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.sf.persist.Persist;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;

@Singleton
public class PersistProvider implements Factory<Persist> {
    
    private final ServiceLocator locator;

    @Inject
    public PersistProvider(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    public Persist provide() {
        RuskConnection connection = this.locator.getService(RuskConnection.class);
        return new Persist(connection.getConnection());
    }

    @Override public void dispose(Persist instance) {/* no use */}
}
