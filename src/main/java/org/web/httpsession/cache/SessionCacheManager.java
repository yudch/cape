package org.web.httpsession.cache;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.nosql.redis.JedisTemplate;
import org.springside.modules.nosql.redis.JedisUtils;
import org.web.auth.Constants;
import org.web.esapi.EncryptException;
import org.web.utils.CookieUtil;
import org.web.utils.TokenGenerator;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Session属性管理类，session中需要存储的属性都放到redis缓存中，session丢失后从redis中复制回去
 * <br>
 * 要求session中不能存储复杂的数据结构，且存放的属性建议基本类型,属性值需要实现序列化接口
 *
 * @author liujmc
 */
public class SessionCacheManager {
	
	public static final String TOKEN_SEED = "token_seed";
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    private int sessionTimeout = 3600;

    private JedisTemplate jedisTemplate;
    
    public JedisTemplate getJedisTemplate() {
        return jedisTemplate;
    }

    public void setJedisTemplate(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }
    
    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    
    //-------------------------------------------session cache部分，只允许session类型的缓存使用---------------------------------------------------//
    public Map<String, Object> getAllSessionAttrCache(String sid) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        Map<byte[], byte[]> redisMap = hgetAll(sid);
        for (Iterator<byte[]> iterator = redisMap.keySet().iterator(); iterator.hasNext(); ) {
            byte[] byteKey = iterator.next();
            String key = new String(byteKey, Charset.forName("UTF-8"));
            Object obj = SerializUtil.byteToObject(redisMap.get(byteKey));
            hashMap.put(key, obj);
        }
        expire(sid, sessionTimeout);
        return hashMap;
    }

    public void removeSessionCache(String sid) {
        jedisTemplate.del(sid);
    }

    /**
     * 设置redis 缓存，过期时间=默认系统配置
     *
     * @param sid
     * @param key
     * @param value
     */
    public <T extends Serializable> void putSessionCacheAttribute(String sid, String key, T value) {
        putSessionCacheAttribute(sid, key, value, sessionTimeout);
    }

    /**
     * 设置redis 缓存
     *
     * @param sid
     * @param key
     * @param value
     */
    public <T extends Serializable> void putSessionCacheAttribute(String sid, String key, T value, int timeout) {
        hset(sid, key, value);
        if (timeout > 0) {
            expire(sid, timeout);
        }
    }


    public <T extends Serializable> void updateSessionCacheAttribute(String sid, String key, T value) {
        if (jedisTemplate.hexists(sid, key)) {
            putSessionCacheAttribute(sid, key, value);
        }
    }

    public <T extends Serializable> T getSessionCacheAttribute(String sid, String key) {
    	T result = null;
        boolean isExist = exists(sid);
        if (isExist) {
            expire(sid, sessionTimeout);
            result = hget(sid, key);
        }
        return result;
    }

    public void removeSessionCacheAttribute(String sid, String key) {
        if (StringUtils.isNotBlank(sid) && StringUtils.isNotBlank(key)) {
            jedisTemplate.hdel(sid, key);
        }
    }
    
  //-------------------------------------------用户 cache部分，只允许用户、登录部分使用---------------------------------------------------//
    public <T extends Serializable> T getUserCache(String key) {
        boolean isExist = exists(key);
        T result = null;
        if (isExist) {
            expire(key, sessionTimeout);
            result = get(key);
        }
        return result;
    }
    
    
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getCurUser(String uname) {
        return StringUtils.isNotBlank(uname) ? (T) getUserCache(createUserCacheKey(uname)) : null;
    }

    public <T extends Serializable> T getCurUser(HttpServletRequest request) {
        String uname = CookieUtil.findCookieValue(request.getCookies(), Constants.PARAM_USERNAME);
        if (StringUtils.isNotBlank(uname)) {
            return getCurUser(uname);
        }
        return null;
    }

    public <T extends Serializable> void cacheUser(String uname, T user) {
    	putUserCache(createUserCacheKey(uname), user);
    }
    
    /**
     * 设置用户缓存，根据系统配置的过期时间
     */
    public <T extends Serializable> void putUserCache(String key, T value) {
    	putTimedCache(key, value, sessionTimeout);
    }

    private String createUserCacheKey(String uname) {
        return new StringBuffer(Constants.USER_INFO_LOGIN).append(":").append(uname).toString();
    }

    
    public <T extends Serializable> void disCacheUser(String uname) {
        removeCache(createUserCacheKey(uname));
    }

    
    //-------------------------------------------utils---------------------------------------------------//
    /**
     * 获得当前系统的 token seed
     */
    public String findSeed() throws EncryptException {
        String seed = getSeedValue(TOKEN_SEED);
        if (org.apache.commons.lang.StringUtils.isBlank(seed)) {
            seed = TokenGenerator.genSeed();
            jedisTemplate.set(TOKEN_SEED, seed);
        }
        return seed;
        
    }
    
    public String getSeedValue(String key) {
    	return jedisTemplate.get(key);
    }
    
  
    //-------------------------------------------commons---------------------------------------------------//
    /**
     * 设置一定期限的redis缓存
     */
    public <T extends Serializable> void putTimedCache(String key, T value, int timeout) {
    	setex(key,value,timeout);
    }
    
    
	public <T extends Serializable> void set(final String key, final T value) {
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
				byte[] valueBytes = SerializUtil.objectToByte(value);
				jedis.set(keyBytes, valueBytes);
			}
		});
	}
    
	public <T extends Serializable> void setex(final String key, final T value, final int timeout) {
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				byte[] valueBytes = SerializUtil.objectToByte(value);
		        byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
		        jedis.setex(keyBytes,timeout, valueBytes);
			}
		});
	}
	
	public void expire(final String key, final int timeout) {
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.expire(key, timeout);
			}
		});
	}
	
	public Boolean exists(final String key) {
		return execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return jedis.exists(key);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T get(final String key) {
		return execute(new JedisAction<T>() {
			@Override
			public T action(Jedis jedis) {
				 byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
				 if(keyBytes==null){
					 return null;
				 }
				 byte[] valueBytes = jedis.get(keyBytes);
				 if(valueBytes == null){
					 return null;
				 } else {
					 return (T)SerializUtil.byteToObject(valueBytes);
				 }
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T hget(final String key, final String fieldName) {
		return execute(new JedisAction<T>() {
			@Override
			public T action(Jedis jedis) {
				byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
				byte[] fieldBytes = fieldName.getBytes(Charset.forName("UTF-8"));
				byte[] attrBytes = jedis.hget(keyBytes, fieldBytes);
				if(attrBytes == null){
					return null;
				} else {
					return (T)SerializUtil.byteToObject(attrBytes);
				}
			}
		});
	}
	
	public Map<byte[], byte[]> hgetAll(final String key) {
		return execute(new JedisAction<Map<byte[], byte[]>>() {
			@Override
			public Map<byte[], byte[]> action(Jedis jedis) {
				return jedis.hgetAll(key.getBytes(Charset.forName("UTF-8")));
			}
		});
	}
	
	public <T extends Serializable> void hset(final String key, final String fieldName, final T value) {
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
				byte[] fieldBytes = fieldName.getBytes(Charset.forName("UTF-8"));
				byte[] valueBytes = SerializUtil.objectToByte(value);
				jedis.hset(keyBytes, fieldBytes, valueBytes);
			}
		});
	}
    
    public void removeCache(String key) {
        if (StringUtils.isNotBlank(key)) {
            jedisTemplate.del(key);
        }
    }
    
    
	public interface JedisAction<T> {
		T action(Jedis jedis);
	}
	
	public interface JedisActionNoResult {
		void action(Jedis jedis);
	}
	
	public <T> T execute(JedisAction<T> jedisAction) throws JedisException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisTemplate.getJedisPool().getResource();
			return jedisAction.action(jedis);
		} catch (JedisException e) {
			broken = handleJedisException(e);
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}
	
	public void execute(JedisActionNoResult jedisAction) throws JedisException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisTemplate.getJedisPool().getResource();
			jedisAction.action(jedis);
		} catch (JedisException e) {
			broken = handleJedisException(e);
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}
	
	protected boolean handleJedisException(JedisException jedisException) {
		if (jedisException instanceof JedisConnectionException) {
			logger.error("Redis connection " + jedisTemplate.getJedisPool().getAddress() + " lost.", jedisException);
		} else if (jedisException instanceof JedisDataException) {
			if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
				logger.error("Redis connection " + jedisTemplate.getJedisPool().getAddress() + " are read-only slave.", jedisException);
			} else {
				return false;
			}
		} else {
			logger.error("Jedis exception happen.", jedisException);
		}
		return true;
	}
	
	protected void closeResource(Jedis jedis, boolean conectionBroken) {
		try {
			if (conectionBroken) {
				jedisTemplate.getJedisPool().returnBrokenResource(jedis);
			} else {
				jedisTemplate.getJedisPool().returnResource(jedis);
			}
		} catch (Exception e) {
			logger.error("return back jedis failed, will fore close the jedis.", e);
			JedisUtils.destroyJedis(jedis);
		}
	}
}
