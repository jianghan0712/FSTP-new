package com.purefun.fstp.core.cache.ignite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.internal.processors.cache.CacheEntryImpl;
import org.apache.ignite.lang.IgniteBiTuple;

import com.purefun.fstp.core.bo.RDSStockBO;
import com.purefun.fstp.core.bo.otw.RDSStockBO_OTW;
import com.purefun.fstp.core.cache.ICommonCache;

/**
 * 
 * @author Jianghan
 *
 */
public class IgniteCacheImp {
	public Ignite ignite;
	public HashMap<String, IgniteInstance> cacheMap = new HashMap<String, IgniteInstance>(); 
//	public HashMap<String, IgniteCache> cacheMap = new HashMap<String, IgniteCache>();
	
	/**
	 * @param ignite
	 * @param cacheCfgList
	 */
	public IgniteCacheImp(Ignite ignite, List<CacheConfiguration> cacheCfgList) {
		this.ignite = ignite;
		for(CacheConfiguration each: cacheCfgList) {
//			cacheMap.put(each.getName(), this.ignite.getOrCreateCache(each));
			Class[] a = each.getIndexedTypes();
			IgniteInstance<?,?> instance = new IgniteInstance<>(this.ignite.getOrCreateCache(each));
			cacheMap.put(each.getName(), instance);
		}
	}

	/**
	 * 获取指定cacheInstance内的数据量
	 * @param cacheName
	 * @return 指定cacheInstance内的数据量
	 */
	public int size(Class<?> cacheName) {
		if (!containCache(cacheName)) {
			return 0;
		}
		return cacheMap.get(cacheName.getName()).size();
	}

	/**
	 * 判断指定cacheInstance内是否为空
	 * @param cacheName
	 * @return true/false
	 */
	public boolean isEmpte(Class<?> cacheName) {
		if (!containCache(cacheName)) {
			return false;
		}
		return cacheMap.get(cacheName.getName()).size()==0 ? true:false;
	}

	/**
	 * 获取指定的igniteInstance
	 * @param cacheName
	 * @return 指定的ignite instance
	 */
	public IgniteCache getCacheInstance(Class<?> cacheName) {
		if (!containCache(cacheName)) {
			return null;
		}
		return cacheMap.get(cacheName.getName()).getCache();
	}
	
	/**
	 * 获取指定igniteInstance的所有entry
	 * @param cacheName
	 * @return HashMap
	 */
	public Map getAllEntrys(Class<?> cacheName) {
		if (!containCache(cacheName)) {
			return null;
		}
		
		return cacheMap.get(cacheName.getName()).getAllEntrys();
	}
	
	/**
	 * 获取指定igniteInstance的所有key
	 * @param cacheName
	 * @return key组成的set
	 */
	public Set getAllKeys(Class<?> cacheName) {
		if (!containCache(cacheName)) {
			return null;
		}
		
		return cacheMap.get(cacheName.getName()).getAllKeys();
	}
	
	/**
	 * 获取指定igniteInstance的所有value
	 * @param cacheName
	 * @return value组成的set
	 */
	public Set getAllValues(Class<?> cacheName) {
		if (!containCache(cacheName)) {
			return null;
		}
		
		return cacheMap.get(cacheName.getName()).getAllValues();
	}
	
	/**
	 * 插入数据
	 * @param cacheName
	 * @param key
	 * @param value
	 * 后续插入失败会抛出错误
	 */
	public void put(Class<?> cacheName, String key, Object value) {		
		if (!containCache(cacheName)) {
			ignite.log().info("cache {} is not here", cacheName.getName());
			return;
		}else {
			cacheMap.get(cacheName.getName()).put(key, value);
		}
	}
	
	/**
	 * 更新数据
	 * @param cacheName
	 * @param key
	 * @param value
	 * 后续更新失败会抛出错误
	 */
	public void update(Class<?> cacheName, String key, Object value) {
		if (!containCache(cacheName)) {
			ignite.log().info("cache {} is not here", cacheName.getName());
			return;
		}else {
			cacheMap.get(cacheName.getName()).update(key, value);
		}	
	}	

	/**
	 * 获取单个或多个数据
	 * @param cacheName
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object get(Class<?> cacheName, Object... key) {
		if (!containCache(cacheName)) {
			return null;
		}
		
		Map ret = null;
		if(key==null) {//全取
			ret = cacheMap.get(cacheName.getName()).getAllEntrys();
		}else  {//取单个或多个
			ret = cacheMap.get(cacheName.getName()).getEntry(key);
		}
		
		return ret;
	}
	
	/**
	 * 获取单个或多个数据
	 * @param cacheName
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object get(String cacheName, Object... key) {
		if (!containCache(cacheName)) {
			return null;
		}
		
		List ret = null;
		if(key==null) {//全取
			ret = Arrays.asList(cacheMap.get(cacheName).getAllEntrys().values().toArray());
			
			
		}else  {//取单个或多个
			ret = Arrays.asList(cacheMap.get(cacheName).getEntry(key).values().toArray());
		}
		
		return ret;
	}

//	public void addCache(CacheConfiguration cachecfg) {
//		// TODO Auto-generated method stub
//		cacheMap.putIfAbsent(cachecfg.getName(), this.ignite.getOrCreateCache(cachecfg));
//	}

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
    	QueryCursor<?> res = cacheMap.get(clazz.getName()).getCache().query(sql);
    	List<T> ret = new ArrayList<T>();
    	for(Object each:res.getAll()) {
    		T c = (T) ((CacheEntryImpl<String, T>)each).getValue();
    		ret.add(c);
    	}
    	
		return ret;
		
	}

	/**
	 * 判断是否存在该class类型的cacheInstance
	 * @param cacheClass
	 * @return
	 */
	boolean containCache(Class<?> cacheClass) {
		boolean ret = false;
		if(cacheClass == null || !cacheMap.containsKey(cacheClass.getName())) {
			ret = false;
		}else {
			ret = true;
		}
		
		return ret;
	}
	
	/**
	 * 判断是否存在该class类型的cacheInstance
	 * @param cacheClass
	 * @return
	 */
	boolean containCache(String cacheClass) {
		boolean ret = false;
		if(cacheClass == null || !cacheMap.containsKey(cacheClass)) {
			ret = false;
		}else {
			ret = true;
		}
		
		return ret;
	}
	
}
