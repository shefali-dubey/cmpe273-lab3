package edu.sjsu.cmpe.cache.client;

import java.util.HashMap;
import java.util.Map;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        CacheServiceInterface cacheA = new DistributedCacheService(
                "http://localhost:3000");
        
        CacheServiceInterface cacheB = new DistributedCacheService(
                "http://localhost:3001");
        
        CacheServiceInterface cacheC = new DistributedCacheService(
                "http://localhost:3002");
        
        
        CacheNodes<CacheServiceInterface> cacheNodes = new CacheNodes<CacheServiceInterface>();
        
        cacheNodes.add(cacheA);
        cacheNodes.add(cacheB);
        cacheNodes.add(cacheC);
        
        // Populate Map with data to be cached
        Map<Integer, String> data = new HashMap<Integer, String>();
        data.put(1, "a");
        data.put(2, "b");
        data.put(3, "c");
        data.put(4, "d");
        data.put(5, "e");
        data.put(6, "f");
        data.put(7, "g");
        data.put(8, "h");
        data.put(9, "i");
        data.put(10, "j");
        
        String value = "";
        
        CacheServiceInterface cache = null;
        
        int bucket = 0;
        
        
        for(int i = 1; i <= 10; i++){
        	value = data.get(i);
        	
        	bucket = Hashing.consistentHash(
        			Hashing.md5().hashString(Integer.toString(i)), 
        			cacheNodes.getSize());
        	
        	cache = cacheNodes.get(bucket);
        	
        	System.out.println("Putting Value: " +value +" in cache: " +cache.toString());
        	
        	cache.put(i, value);
        }
        
        for(int j = 1; j <= 10; j++){
        	value = data.get(j);
        	
        	bucket = Hashing.consistentHash(
        			Hashing.md5().hashString(Integer.toString(j)), 
        			cacheNodes.getSize());
        	
        	cache = cacheNodes.get(bucket);
        	
        	System.out.println("Getting value from cache: " +cache.toString() +", value: " +value);
        	
        }
        
        System.out.println("Existing Cache Client...");
        
     }

}
