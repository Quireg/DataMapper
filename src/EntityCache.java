import java.util.HashMap;
import java.util.HashSet;

public class EntityCache {

    static private HashMap entityCache = new HashMap<>();


    public static void saveToCache(long id, Object obj){
         String key = obj.getClass().getSimpleName() + id;

         entityCache.put(key, obj);
    }
    public static Object loadFromCache(Object obj, long id){
        String key = obj.getClass().getSimpleName() + id;

        return entityCache.get(key);
    }

}
