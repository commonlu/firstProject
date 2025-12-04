package com.gdou.hr_system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单的 JWT 工具类：
 * - 生成包含用户名和角色的 token
 * - 后续可以扩展解析和校验方法
 */
public class JwtUtil {

    /**
     * 建议后续改为从配置文件读取，并使用更复杂的随机字符串
     * 这里为了演示先写死一个 base64 编码的 key
     */
    private static final String SECRET_KEY =
            "dGhpc19pc19hX3ZlcnlfbG9uZ19zZWNyZXRfa2V5X2Zvcl9ocnN5c3RlbV9qd3Q=";

    /** token 有效期，这里先设为 2 小时 */
    private static final long EXPIRATION_MILLIS = 2 * 60 * 60 * 1000L;

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成 JWT token，包含用户名和角色
     */
    public static String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_MILLIS);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析 token 获取 Claims（后续做拦截器时会用到）
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}


