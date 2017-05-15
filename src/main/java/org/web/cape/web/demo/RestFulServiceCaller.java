package org.web.cape.web.demo;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.security.digest.DigestUtils;
import org.web.security.digest.RestHMacParam;
import org.web.utils.HttpTookit;
import org.web.utils.PropertyUtil;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/restful")
public class RestFulServiceCaller {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "call", method = RequestMethod.POST)
    @ResponseBody
    public Object call(
            @RequestParam(value = "callUrl") String callUrl) {

        //模拟客户端解析令牌，对请求进行签名；
        String clientType = "UAP_MOBILE";
        try {
            //获取方式待优化
            String clientTokenStr = PropertyUtil.getPropertyByKey("clientToken");
            clientTokenStr = "6/L9xLxf7Fg9jxIpWYZqTs+mUWE=";
            String beginTs = System.currentTimeMillis() + "";
            RestHMacParam param = new RestHMacParam(callUrl, beginTs, null, "true", "127.0.0.1");
            final String sign = DigestUtils.hmac(param, clientTokenStr, DigestUtils.Algorithm_HMAC.HmacSHA1);


            // 发起http请求
            Map<String, String> params = new HashMap<String, String>();
            params.put("sign", sign);
            params.put("clientType", clientType);
            params.put("beginTs", beginTs);
            params.put("ipAuth", "true");
            String result = HttpTookit.doPost(callUrl, null, params);
            logger.info(result);
            return result;
        } catch (RuntimeException e) {
            logger.error("Error!", e);
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        json.put("result", "fail");
        return json;
    }
}
