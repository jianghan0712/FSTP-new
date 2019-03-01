package com.purefun.fstp.core.cache;

import org.apache.ignite.configuration.CacheConfiguration;

/**
 * FSTP的cache模块的接口，目前有两种实现，redis和ignite。推荐使用ignite，为了后续ignite做流计算时候，不需要重新数据导入
 * <p>cache的配置在{@link FstpConfiguration}的cacheType参数，具体的配置分别在redis.properties和ignite.xml
 * <p>如果使用ignite，则需要在对应的config.xml文件中主动的声明cacheConfigurationList，list中存放的每一个{@link CacheConfiguration}
 * 都对应一个ignite实例。cacheConfigurationList表示该服务将对其中bo请求的进行响应
 * 
 * 
 * @author Jianghan
 *
 */
public interface ICommonCache {
	public void put(String cacheName, String key, Object value);
	
	public Object get(String cacheName, String... key);
	
	public void addCache(CacheConfiguration cachecfg);
	
	public void update(String cacheName, String key, Object value);
	
	public int size(String cacheName);
	
	public boolean isEmpte(String cacheName);
	
	public Object getCacheInstance(String cacheName);
}
