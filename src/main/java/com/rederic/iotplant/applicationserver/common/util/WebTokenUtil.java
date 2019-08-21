package com.rederic.iotplant.applicationserver.common.util;

import com.rederic.iotplant.applicationserver.common.beans.WebToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("deprecation")
@Component
public class WebTokenUtil {
	
    private static Logger log = LoggerFactory.getLogger(WebTokenUtil.class);
    private static WebToken webToken = new WebToken();
    static {
        try {
            Resource resource = new ClassPathResource("/webtoken.properties");
            Properties Props = PropertiesLoaderUtils.loadProperties(resource);
            webToken.setExpiresSecond(Props.getProperty("expiresSecond") == null ? 6379
                    : Integer.parseInt(Props.getProperty("expiresSecond")));
            webToken.setName(Props.getProperty("name"));
            webToken.setSalt(Props.getProperty("salt"));
        } catch (IOException e) {
            log.error("获取 WebTokenProps : " + e);
        }
    }
    /**
     * @desc 生成一个token值
     * @param key
     * @param obj
     * @return
     */
    public static String getWebToken(String key, Object obj) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(webToken.getSalt());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").claim(key, obj).setIssuer(webToken.getName())
                .signWith(SignatureAlgorithm.HS256, signingKey);
        // 添加Token过期时间
        long TTLMillis = webToken.getExpiresSecond() * 24 * 60 * 60 * 1000;
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        // 生成JWT
        return builder.compact();
    }
    /**
     * @desc 解析token值
     * @param jsonWebToken
//     * @param base64Security
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseWebToken(String jsonWebToken, String key) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(webToken.getSalt()))
                    .parseClaimsJws(jsonWebToken).getBody();
            Map<String, Object> res = (Map<String, Object>) claims.get(key);
            return res;
        } catch (Exception ex) {
            log.error("解析token值：" + ex);
        }
        return null;
    }
    /**
     * 获取webtoken的失效时间
     * 
     * @param jsonWebToken
     * @param key
     * @return
     * @date 2017年7月10日 上午11:52:52
     */
    public static Long getWebTokenTime(String jsonWebToken, String key) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(webToken.getSalt()))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims.getExpiration().getTime();
        } catch (Exception ex) {
            log.error("解析token值：" + ex);
        }
        return null;
    }
//    /**
//     * 获取当前登录的用户对象
//     * HttpUserInfoRes
//     * @author hufx
//     * @date 2017年8月15日上午11:53:35
//     */
//     public static ModelUser getUserByWebToken(HttpServletRequest request) {
//         try {
//               String jsonWebToken = request.getHeader("token");
//               if (jsonWebToken == null) {
//                   return null;
//               }
//               Map<String, Object> tUserMap = (Map<String, Object>)parseWebToken(jsonWebToken, "tUser");
//               if (tUserMap == null) {
//                   return null;
//               }
//               //获取当前登录用户时可返回此对象
//               HttpUserInfoRes user = new HttpUserInfoRes();
//               user.setId((String) tUserMap.get("userid"));
//               user.setUsername((String) tUserMap.get("username"));
//               return user;
//          } catch (Exception e) {
//        	   return null;
//          }
//     }
}