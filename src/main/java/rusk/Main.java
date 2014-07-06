package rusk;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Objects;
import java.util.jar.Manifest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Rusk 起動用のメインクラス。
 */
public class Main {
    /**Rusk 起動用のポート番号*/
    private static final int PORT_NUMBER = 58634;
    /**コンテキストパス*/
    private static final String CONTEXT_PATH = "/rusk";
    
    /**
     * アプリケーションを起動します。
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) throws Exception {
        WebAppContext war = new WebAppContext();
        war.setContextPath(CONTEXT_PATH);
        
        setWar(war);
        
        Server server = new Server(PORT_NUMBER);
        server.setHandler(war);
        server.start();
        
        String url = "http://localhost:" + PORT_NUMBER + CONTEXT_PATH;
        
        startWebBrowser(url);
        
        server.join();
    }
    
    /**
     * Web コンテンツフォルダを設定します。
     * <p>
     * 開発中とリリース時に合わせて Web コンテンツフォルダの場所が切り替わります。<br>
     * 開発中は{@code src/main/webapp} が指定され、リリース時は実行している war ファイル自体が指定されます。
     * 
     * @param war WebAppContext
     */
    private static void setWar(WebAppContext war) throws IOException {
        if (isRelease()) {
            ProtectionDomain domain = Main.class.getProtectionDomain();
            URL warLocation = domain.getCodeSource().getLocation();
            war.setWar(warLocation.toExternalForm());
        } else {
            war.setResourceBase("src/main/webapp");
        }
    }
    
    /**
     * デフォルトの Web ブラウザを使って Rusk のページを表示させます。
     * @param url URL のベース
     */
    private static void startWebBrowser(String url) throws URISyntaxException, IOException {
        System.out.println("url : " + url);
        if (isRelease()) {
            Desktop desktop = Desktop.getDesktop();
            URI uri = new URI(url);
            desktop.browse(uri);
        }
    }
    
    /**
     * 現在の実行環境が開発中かリリース後かを確認します。
     * 
     * @return リリース後の場合は true
     */
    private static boolean isRelease() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {
            Manifest manifest = new Manifest(is);
            String release = manifest.getMainAttributes().getValue("Rusk-Release");
            return Objects.equals(release, "true");
        }
    }
}
