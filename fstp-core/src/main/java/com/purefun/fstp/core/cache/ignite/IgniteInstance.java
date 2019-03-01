package com.purefun.fstp.core.cache.ignite;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.lang.IgniteBiTuple;

/**
 * 用于管理ignite类型的cache
 * @author Jianghan
 *
 * @param <K>
 * @param <V>
 */
public class IgniteInstance<K, V> {
	IgniteCache<K, V> cache = null;
	
	public IgniteInstance(IgniteCache<K, V> cache) {
		this.cache = cache;
	}	

	/**
	 * 获取所有cache中的数据
	 * @return 所有cache中的数据
	 */
	Map<K, V> getAllEntrys() {		
		Map<K, V> ret = new HashMap<K, V>();
		for(Object c : cache) {
			ret.put((K)((IgniteBiTuple)c).get1(), (V)((IgniteBiTuple)c).get2());
		}
				
		return ret;
	}
	
	/**
	 * 获取指定igniteInstance的所有key
	 * @return key组成的set
	 */
	Set<K> getAllKeys() {
		Set<K> ret = new HashSet<K>();
		for(Object c : cache) {
			ret.add((K)((IgniteBiTuple)c).get1());
		}
		
		return ret;
	}

	int size() {
		// TODO Auto-generated method stub
		return cache.size(CachePeekMode.ALL);
	}
	
	/**
	 * 获取指定igniteInstance的所有value
	 * @param cacheName
	 * @return value组成的set
	 */
	Set<V> getAllValues() {
		Set<V> ret = new HashSet<V>();
		for(Object c : cache) {
			ret.add((V)((IgniteBiTuple)c).get2());
		}
		
		return ret;
	}
	
	
	
	IgniteCache<K,V> getCache() {
		return this.cache;
	}

	void put(K key, V value) {
		// TODO Auto-generated method stub
		cache.put(key, value);
	}

	void update(K key, V value) {
		// TODO Auto-generated method stub
		cache.getAndReplace(key, value);
	}

	public Map<K, V> getEntry(K... key) {
		// TODO Auto-generated method stub
		Map<K, V> ret = new HashMap<K, V>();
		for(K e : key) {
			ret.put(e, cache.get(e));				
		}
		return ret;
	}
}
