package com.purefun.fstp.core.cache.redis.tool;

import java.util.List;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	public static Jedis jedisEntry = null;
	
	public static void setObject(String key ,Object obj){  
	    try {  
	        obj = obj == null ? new Object():obj;  
	        getJedis().set(key.getBytes(), SerializeUtil.serialize(obj));  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	 /** 
	    * 获取对象 
	    * @param key 
	    * @return Object 
	    */  
	public static Object getObject(String key){  
	    if(getJedis() == null || !getJedis().exists(key)){  
	        return null;  
	    }  
	    byte[] data = getJedis().get(key.getBytes());  
	    return (Object)SerializeUtil.unserialize(data);  
	}  

	/** 
	    * 设置List集合 
	    * @param key 
	    * @param list 
	    */  
	   public static void setList(String key ,List<?> list){  
	    try {  
	          
	        if(!list.isEmpty()){  
	            getJedis().set(key.getBytes(), SerializeUtil.serializeList(list));  
	        }else{//如果list为空,则设置一个空  
	            getJedis().set(key.getBytes(), "".getBytes());  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}  
	  
	   /** 
	    * 获取List集合 
	    * @param key 
	    * @return 
	    */  
	public static List<?> getList(String key){  
	    if(getJedis() == null || !getJedis().exists(key)){  
	        return null;  
	    }  
	    byte[] data = getJedis().get(key.getBytes());  
	    return SerializeUtil.unserializeList(data);  
	}  
	
	private static Jedis getJedis() {
		// TODO Auto-generated method stub
		return jedisEntry;
	}
	
	public static void setJedis(Jedis jedis) {
		// TODO Auto-generated method stub		
		jedisEntry = jedis;		
	}
	
	
}
