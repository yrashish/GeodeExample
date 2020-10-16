import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.*;

public class ExampleGet {
    public static void main(String[] args) {
        ClientCacheFactory clientCacheFactory = new ClientCacheFactory();
        clientCacheFactory.addPoolLocator("localhost",10334);
        ClientCache clientCache = clientCacheFactory.create();
        clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY).create("HelloWorld");
        Region<String, String> region = clientCache.getRegion("HelloWorld");
        System.out.println(region.get("1"));
    }
}
