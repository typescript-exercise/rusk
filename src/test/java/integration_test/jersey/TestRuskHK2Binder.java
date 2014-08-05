package integration_test.jersey;

import integration_test.db.TestDatabaseConfig;
import rusk.RuskHK2Binder;

/**
 * 結合テスト用の HK2 バインダー
 */
public class TestRuskHK2Binder extends RuskHK2Binder {

    @Override
    protected void configure() {
        this.bindComponents(TestDatabaseConfig.class);
    }
}
