package org.web.security.digest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.util.Assert;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * 消息摘要工具类
 * 本类中暂时只提供JDK 已经提供的HmacMD5(摘要长度128),HmacSHA1(摘要长度160),HmacSHA256(摘要长度256),,HmacSHA384(摘要长度384),,HmacSHA512(摘要长度512)
 * <p/>
 * Created by zengxs on 2015/9/2.
 */
public class DigestUtils {

    /**
     * 随机数算法
     */
    private static final String RANDOM_ALG = "SHA1PRNG";

    /**
     * 默认过期时间，后期可配置
     */
    private static final long PERIOD = 900000L;

    private static final String PATTERN_TS = "[1-9]\\d+";

    /**
     * 算法枚举
     */
    public static enum Algorithm_HMAC {
        HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512;
    }

    /**
     * 初始化秘钥
     *
     * @param seed
     * @param hmacAlg
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] initKey(byte[] seed, Algorithm_HMAC hmacAlg) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(hmacAlg.name());
        keyGenerator.init(new SecureRandom(seed));
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }


    /**
     * 计算消息摘要，返回Hex字符串
     *
     * @param param
     * @param seed
     * @param algorithm
     * @return
     */
    public static String hmac(RestHMacParam param, String seed, Algorithm_HMAC algorithm) {
        try {
            Mac mac = getMacAlg(seed, algorithm);
            long ts = -1;
            if (param.getBeginTs()!=null && Pattern.matches(PATTERN_TS, param.getBeginTs())) {
                ts = Long.parseLong(param.getBeginTs()) + PERIOD;
            }
            if (param.getExpireTs()!=null &&Pattern.matches(PATTERN_TS, param.getExpireTs())) {
                ts = Long.parseLong(param.getExpireTs());
            }

            StringBuffer digestData = new StringBuffer(param.getRequestPath());
            if (ts > -1) {
                digestData.append(ts);
            }
            if (Boolean.valueOf(param.getIpAuth())) {
                Assert.notNull(param.getIpAddress());
                digestData.append(param.getIpAddress());
            }

            return Hex.encodeHexString(mac.doFinal(StringUtils.getBytesUtf8(digestData.toString())));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 初始化Mac摘要算法
     *
     * @param seed
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static Mac getMacAlg(String seed, Algorithm_HMAC algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] keyCode = initKey(Base64.decodeBase64(seed), algorithm);
        SecretKey secretKey = new SecretKeySpec(keyCode, algorithm.name());
        Mac mac = Mac.getInstance(algorithm.name());
        mac.init(secretKey);
        return mac;
    }

    /**
     * 初始化seed
     *
     * @return
     */
    public static String initSeed() throws Exception {
        SecureRandom random = SecureRandom.getInstance(RANDOM_ALG);
        byte[] seed = new byte[20];
        random.nextBytes(seed);
        return Base64.encodeBase64String(seed);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(initSeed());
    }

}
