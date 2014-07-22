package rusk.integration_test.jersey;

import rusk.RuskHK2Binder;
import rusk.integration_test.db.TestDatabaseConfig;

/**
 * 結合テスト用の HK2 バインダー
 */
public class TestRuskHK2Binder extends RuskHK2Binder {

    @Override
    protected void configure() {
        this.bindComponents(TestDatabaseConfig.class);
    }
}
