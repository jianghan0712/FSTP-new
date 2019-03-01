package com.purefun.fstp.core.cache.ignite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.internal.processors.cache.CacheEntryImpl;
import org.apache.ignite.lang.IgniteBiTuple;

import com.purefun.fstp.core.cache.ICommonCache;

/**
 * 
 * @author Jianghan
 *
 */
public class IgniteCacheImp2 implements ICommonCache{
	public Ignite ignite;
	public HashMap<String, IgniteCache> cacheMap = new HashMap<String, IgniteCache>();
	
	public IgniteCacheImp2(Ignite ignite, List<CacheConfiguration> cacheCfgList) {
		this.ignite = ignite;
		for(CacheConfiguration each:cacheCfgList) {
			cacheMap.put(each.getName(), this.ignite.getOrCreateCache(each));
		}
	}

	/**
	 * 
	 */
	@Override
	public void put(String cacheName,String key, Object value) {
		// TODO Auto-generated method stub
		
		if(!cacheMap.containsKey(cacheName)) {
			//not insert
			ignite.log().info("cache {} is not here", cacheName);
//			CacheConfiguration temp = new CacheConfiguration(cacheName);
//			cacheMap.put(cacheName, ignite.getOrCreateCache(temp));
		}
		IgniteCache cache = cacheMap.get(cacheName);
		cache.put(key, value);
	}

	@Override
	public Object get(String cacheName, String... key) {
		// TODO Auto-generated method stub
		if(cacheName==null || !cacheMap.containsKey(cacheName)) {
			return null;
		}
		
		List all = new ArrayList();
		if (key!=null) {//key不为空，但可以同时取出多条数据，需要处理
			
			for(String each:key) {
				all.add(cacheMap.get(cacheName).get(each));
			}
		}else {//key为null时表示取出所有数据
			IgniteCache cache = cacheMap.get(cacheName);
			for(Object c:cache) {
				all.add(((IgniteBiTuple)c).get2());
			}
		}					
		return all;
	}

	@Override
	public void addCache(CacheConfiguration cachecfg) {
		// TODO Auto-generated method stub
		cacheMap.putIfAbsent(cachecfg.getName(), this.ignite.getOrCreateCache(cachecfg));
	}

	@Deprecated
	public <T> List<T> query(String condition, Class<T> clazz) {
		SqlQuery sql = null;
		if(condition==null) {
			sql = new SqlQuery<AffinityKey<String>, T>(clazz, "1=1");
		}else {
			sql = new SqlQuery<AffinityKey<String>, T>(clazz, condition);
		}
		if(!cacheMap.containsKey(clazz.getName())) {
			return null;
		}
    	QueryCursor<?> res = cacheMap.get(clazz.getName()).query(sql);
    	List<T> ret = new ArrayList<T>();
    	for(Object each:res.getAll()) {
    		T c = (T) ((CacheEntryImpl<String, T>)each).getValue();
    		ret.add(c);
    	}
    	
		return ret;
		
	}

	@Override
	public void update(String cacheName, String key, Object value) {
		// TODO Auto-generated method stub
		IgniteCache cache = cacheMap.get(cacheName);
		if (cache==null) {
			
		}else {
			cache.getAndReplace(key, value);
		}
		
		
	}

	@Override
	public int size(String cacheName) {
		// TODO Auto-generated method stub
		return cacheMap.get(cacheName).size(null);
	}

	@Override
	public boolean isEmpte(String cacheName) {
		// TODO Auto-generated method stub
		return cacheMap.get(cacheName).size(null)==0 ? true:false;
	}

	@Override
	public Object getCacheInstance(String cacheName) {
		// TODO Auto-generated method stub		
		return cacheMap.get(cacheName);
	}
	
}
