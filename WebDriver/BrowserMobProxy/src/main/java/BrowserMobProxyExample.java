import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class BrowserMobProxyExample {

    public static void main(String[] args) {
        BrowserMobProxyServer server = new BrowserMobProxyServer();

        //server.setTrustAllServers(true);
        server.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        server.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS);
        server.start(0, InetAddress.getLoopbackAddress());
                        
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(server);
        seleniumProxy.setSslProxy("trustAllSSLCertificates");
        seleniumProxy.setHttpProxy("127.0.0.1:" + server.getPort()); 
        seleniumProxy.setSslProxy("127.0.0.1:" + server.getPort());

        //No exe in this repo
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/drivers/linux/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();  
        //chromeOptions.addArguments("--headless");
        
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, seleniumProxy);
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        server.newHar(); //<-- starting to listen to network traffic
        //TODO WebDriver actions here
       
        try {
            Har har = server.getHar();
            
            //Example processing from another project, anyway a har file contains all network traffic since newHar() call.
            mainloop: for(HarEntry entry : har.getLog().getEntries()) {
                for(HarNameValuePair nameValuePair :  entry.getRequest().getHeaders()) {
                    //System.out.println(nameValuePair.getName() + " " + nameValuePair.getValue());
                    if(nameValuePair.getValue().startsWith("Bearer ")) {
                        System.setProperty("WebToken", nameValuePair.getValue().substring(7));
                        break mainloop;
                    }
                }
                
            }
            File harFile = new File("target/network_traffic_output.har");
            har.writeTo(harFile);              
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
            server.stop();
    }

}
