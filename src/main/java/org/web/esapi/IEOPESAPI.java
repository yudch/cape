package org.web.esapi;

/**
 * IEOPESAPI工具类,提供编码/解码，加密/解密，签名
 * <p/>
 * Created by zengxs on 2015/8/14.
 */
public class IEOPESAPI {

    private static final IEOPEncryptor encryptUtils = new IEOPEncryptor();

    private static final IEOPEncoder encoderUtils = new IEOPEncoder();

    /**
     * 获得加解密工具类
     *
     * @return
     */
    public static IEOPEncryptor encryptor() {
        return encryptUtils;
    }

    /**
     * 获得编码工具类
     * @return
     */
    public static IEOPEncoder encoder() {
        return encoderUtils;
    }


}
