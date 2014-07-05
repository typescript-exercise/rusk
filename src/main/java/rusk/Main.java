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

public class Main {
    
    private static final int PORT_NUMBER = 58634;
    private static final String CONTEXT_PATH = "/rusk";

    public static void main(String[] args) throws Exception {
        WebAppContext war = new WebAppContext();
        war.setContextPath(CONTEXT_PATH);
        
        setWar(war);
        
        Server server = new Server(PORT_NUMBER);
        server.setHandler(war);
        server.start();
        
        startWebBrowser();
        
        server.join();
    }
    
    private static void setWar(WebAppContext war) throws IOException {
        if (isRelease()) {
            System.out.println("release");
            ProtectionDomain domain = Main.class.getProtectionDomain();
            URL warLocation = domain.getCodeSource().getLocation();
            war.setWar(warLocation.toExternalForm());
        } else {
            System.out.println("develop");
            war.setResourceBase("src/main/webapp");
        }
    }
    
    private static void startWebBrowser() throws URISyntaxException, IOException {
        String url = "http://localhost:" + PORT_NUMBER + CONTEXT_PATH;
        System.out.println("url : " + url);
        Desktop desktop = Desktop.getDesktop();
        URI uri = new URI(url);
        desktop.browse(uri);
    }
    
    private static boolean isRelease() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {
            Manifest manifest = new Manifest(is);
            String release = manifest.getMainAttributes().getValue("Rusk-Release");
            return Objects.equals(release, "true");
        }
    }
}
