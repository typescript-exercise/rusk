package rusk.system.db;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import mockit.NonStrictExpectations;

import org.junit.Test;

public class DatabaseConfigTest {
    
    @Test
    public void 環境変数_RUSK_HOME_が設定されていない場合_ユーザーのホームディレクトリにデータベースファイルが生成されるようにURLが定義されること() {
        // setup
        new NonStrictExpectations(System.class) {{
            System.getenv("RUSK_HOME"); result = null;
            System.getProperty("user.home"); result = "userhome";
        }};
        
        ProductionDatabaseConfig config = new ProductionDatabaseConfig();
        
        // exercise
        String url = config.getUrl();
        
        // verify
        assertThat(url, is("jdbc:hsqldb:file:userhome/.rusk/db/rusk;shutdown=true"));
    }
    
    @Test
    public void 環境変数_RUSK_HOME_が設定されている場合_RUSK_HOME_の下にデータベースファイルが生成されるようにURLが定義されること() {
        // setup
        new NonStrictExpectations(System.class) {{
            System.getenv("RUSK_HOME"); result = "ruskhome";
            System.getProperty("user.home"); result = "userhome";
        }};
        
        ProductionDatabaseConfig config = new ProductionDatabaseConfig();
        
        // exercise
        String url = config.getUrl();
        
        // verify
        assertThat(url, is("jdbc:hsqldb:file:ruskhome/.rusk/db/rusk;shutdown=true"));
    }
}
