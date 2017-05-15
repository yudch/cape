package org.web.utils;

import org.owasp.esapi.ESAPI;
import org.web.esapi.EncryptException;
import org.web.esapi.IEOPESAPI;

/**
 * Created by zengxs on 2015/8/25.
 */
public class TokenGenerator {

    /**
     * 生成用户登陆token
     *
     * @param uname
     * @param ts
     * @param seed
     * @return
     * @throws EncryptException
     */
    public static String genToken(String uname, long ts, String seed) throws EncryptException {
        return IEOPESAPI.encryptor().hash(uname + ts, seed);
    }

    /**
     * 生成系统的加密seed
     *
     * @return
     * @throws EncryptException
     */
    public static String genSeed() throws EncryptException {
        return IEOPESAPI.encryptor().hash(new String(ESAPI.securityConfiguration().getMasterKey()), new String(ESAPI.securityConfiguration().getMasterSalt()));
    }
}
