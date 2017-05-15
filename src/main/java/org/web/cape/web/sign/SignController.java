package org.web.cape.web.sign;

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
import org.web.utils.PropertyUtil;

@Controller
@RequestMapping(value = "/sign")
public class SignController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "signRequest", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONObject signRequest(@RequestParam(value = "signUrl") String signUrl) {
        JSONObject json = new JSONObject();
        //模拟客户端解析令牌，对请求进行签名；
        String clientType = "UAP_MOBILE";
        try {
            String clientTokenStr = PropertyUtil.getPropertyByKey("clientToken");
            clientTokenStr = "6/L9xLxf7Fg9jxIpWYZqTs+mUWE=";
            RestHMacParam param = new RestHMacParam();
            param.setRequestPath(signUrl);
            final String sign = DigestUtils.hmac(param, clientTokenStr, DigestUtils.Algorithm_HMAC.HmacSHA1);
            System.out.println("签名:" + sign);
            json.put("result", sign);
            return json;
        } catch (Exception e) {
            logger.error("sign error!", e);
            e.printStackTrace();
        }
        json.put("result", "fail");
        return json;
    }
}
