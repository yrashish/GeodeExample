import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.*;

public class Example {
    public static void main(String[] args) throws InterruptedException {
        ClientCacheFactory clientCacheFactory = new ClientCacheFactory();
        // clientCacheFactory.addPoolLocator("localhost",10334);
        //  ReflectionBasedAutoSerializer reflectionBasedAutoSerializer = new ReflectionBasedAutoSerializer("*");
        //clientCacheFactory.setPdxSerializer(reflectionBasedAutoSerializer);
        ClientCache clientCache = clientCacheFactory.create();
        Pool poolPrimary = PoolManager.createFactory().addLocator("localhost", 10334).create("Primary");
        clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY).setPoolName("Primary").create("Test");
        Region<String, String> region = null;
        try {
            region = clientCache.getRegion("Test");
            region.put("1", "test");
        } catch (NoAvailableLocatorsException | NoAvailableServersException e) {
            e.printStackTrace();
            failOver(poolPrimary, clientCache, region);
        }
    }

    static void failOver(Pool primary, ClientCache clientCache, Region<String, String> primaryRegion) throws InterruptedException {

        System.out.println("Starting failover to secondary cluster");
        primaryRegion.close();
        primary.destroy();
        clientCache.close();
        Thread.sleep(10000);
        ClientCacheFactory clientCacheFactory = new ClientCacheFactory();
        clientCache = clientCacheFactory.create();
        Pool poolSecondary = PoolManager.createFactory().addLocator("localhost", 11334).create("Secondary");
        clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY).setPoolName(poolSecondary.getName()).create("Test");
        Region<String, String> region = clientCache.getRegion("Test");
        System.out.println(region.get("1"));

    }
}
