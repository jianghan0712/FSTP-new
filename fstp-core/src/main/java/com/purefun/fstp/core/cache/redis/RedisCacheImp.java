package com.purefun.fstp.core.cache.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;

import com.purefun.fstp.core.cache.ICommonCache;
import com.purefun.fstp.core.cache.redis.tool.ObjectTransCoder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCacheImp implements ICommonCache{	
	private List<String> publicCacheList = null;	//主要存该service可以管理的BO类名
	private List<String> privateCacheList = null;	//存该servic需要存入cache中的数据的key
	private Jedis cache = null;
	static Logger log = null;
	String serviceName = null;
	JedisPool jedisPool = null;
	
	public RedisCacheImp(Logger log, String serviceName, BeanFactory beanfactory) {
		publicCacheList = new ArrayList<String>();
		privateCacheList = new ArrayList<String>();
		this.log = log;
		this.serviceName = serviceName;
		
		initCache(beanfactory);
	}
	
	private void initCache(BeanFactory factory) {
		// TODO Auto-generated method stub
		if(factory == null) {
			log.error("{} init Redis Cache fail",serviceName);
			return;
		}
		jedisPool = (JedisPool)factory.getBean("jedisPool");
		if(jedisPool != null) {
			cache = jedisPool.getResource();
			log.info("{} init Redis Cache succesful",serviceName);
		}				
	}
	
	@Override
	public void put(String cacheName,String key, Object value) {
		// TODO Auto-generated method stub
		setMap(cacheName, key, (byte[])value);
	}

	@Override
	public Object get(String cacheName, String... key) {
		// TODO Auto-generated method stub
		return getMap(cacheName, key);
	}

	/*************   用于存储非bo的object    ***************/
	public Object getObject(String key) {
		Object ret = null;		
		if(cache != null) {
			ret = ObjectTransCoder.deserialize(cache.get(key.getBytes()));
			log.debug("get key={} object!",key);
		}else {
			log.debug("The {} not found in cache:{}",key,cache);
		}
		return ret;
	}
	
	public void setObject(String key, Object value) {
		byte[] bytes = null;
		if(cache != null && value != null) {
			bytes = ObjectTransCoder.serialize(value);
			cache.set(key.getBytes(),bytes);
			log.debug("Set key={} successfull!",key);
		}else {
			log.debug("The object is null");
		}
	}
	
	public void delObjct(String key) {
		if(cache != null)
			cache.del(key);
	}	
	
	/*************   用于存储bo的List 废弃   ***************/
	@Deprecated
	public void setList(String key, byte[] value) {
		byte[] bytes = null;
		if(cache != null && value != null) {
			cache.rpush(key.getBytes(),value);
			log.debug("Set List key={} successfull!",key);
		}else {
			log.debug("The object is null");
		}
	}
	
	@Deprecated
	public List<byte[]> getList(String key) {
		List<byte[]> ret = null;
		
		if(cache != null) {
			ret = cache.lrange(key.getBytes(),0,-1);
			log.debug("get key={} object!",key);
		}else {
			log.debug("The {} not found in cache:{}",key,cache);
		}
		return ret;
	}
	
	/*************   用于操作bo的map   ***************/
	public void setMap(String cacheName, String key, byte[] value) {
		if(cache == null) {
			log.error("cache is not init!");
			return;
		}
		if(value==null) {
			log.error("can't insert NULL to cache!");
			return;
		}
		
		if(cache.exists(cacheName) == null) {
			log.info("first create cacheName:{}",cacheName);
			Map<byte[], byte[]> cachemap = new HashMap<byte[], byte[]>();
			cachemap.put(key.getBytes(), value);
			cache.hmset(cacheName.getBytes(), cachemap);
		}else {
			cache.hset(cacheName.getBytes(), key.getBytes(), value);
		}
	}
	
	public Object getMap(String cacheName, String... key) {
		List<byte[]> ret = new ArrayList<byte[]>();
		if(!cache.isConnected()) {
			log.error("cache is not init!");
			return null;
		}
		if(!cache.exists(cacheName.getBytes())) {
			log.error("cache {} is not exists!", cacheName);
			return null;
		}
		
		if(key==null) {//获取该cacheName下所有bo
			ret = cache.hvals(cacheName.getBytes());
		}else {
			byte[][] temp = new byte[key.length][];
			int count = 0;
			for(String each:key) {
				temp[count++] = each.getBytes();
			}
			ret = cache.hmget(cacheName.getBytes(), temp);
		}		
		return ret;
	}
	
	/*************   用于其他管理方法，废弃   ***************/
	@Deprecated
	public void delPublicCache() {
		if(publicCacheList.size()==0)
			return;
		for(String each:publicCacheList) {
			delObjct(each);
		}
	}
	
	@Deprecated
	public void delPrivateCache() {
		if(privateCacheList.size()==0)
			return;
		for(String each:privateCacheList) {
			delObjct(each);
		}
	}
	
	public void delAllObject() {
		delPublicCache();
		delPrivateCache();
	}
	
	public void stop() {
		delAllObject();
		cache.close();
		jedisPool.close();
	}
	
	/*************   cache manager end    ***************/
	/*************   Map manager start    ***************/
	public Jedis getCache() {
		return cache;
	}

	public List<String> getPublicCacheList() {
		return publicCacheList;
	}

	public void setPublicCacheList(List<String> publicCacheList) {
		this.publicCacheList = publicCacheList;
	}

	public List<String> getPrivateCacheList() {
		return privateCacheList;
	}

	public void setPrivateCacheList(List<String> privateCacheList) {
		this.privateCacheList = privateCacheList;
	}

	public void setCache(Jedis cache) {
		this.cache = cache;
	}

	@Override
	public void addCache(CacheConfiguration cachecfg) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(String cacheName, String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size(String cacheName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpte(String cacheName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getCacheInstance(String cacheName) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
