package geode.example;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

public class ExamplePut {
    public static void main(String[] args) {
        ClientCacheFactory clientCacheFactory = new ClientCacheFactory();
        clientCacheFactory.addPoolLocator("localhost",10334);
        ClientCache clientCache = clientCacheFactory.create();
        clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY).create("Test");
        Region<String, String> region = clientCache.getRegion("Test");
        region.put("one","Hello");
    }
}
