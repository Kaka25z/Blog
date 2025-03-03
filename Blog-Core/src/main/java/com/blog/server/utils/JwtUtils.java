package com.blog.server.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.blog.server.constants.Const;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.expire}")
    private int expire;

    @Resource
    StringRedisTemplate template;

    public Date expireTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expire);
        return calendar.getTime();
    }

    public boolean invalidateJwt(String headerToken){

        String token = this.convertToken(headerToken);
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            return deleteToken(verify.getId(), verify.getExpiresAt());
        }catch (JWTVerificationException e){
            return false;
        }
    }

    public String createToken(String username, String role){

        Algorithm algorithm = Algorithm.HMAC256(key);
        Date expire = this.expireTime();

        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("name", username)
                .withClaim("role", role)
                .withExpiresAt(expire)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public String getUsernameFromToken(String headerToken){
        String token = this.convertToken(headerToken);

        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        return verify.getClaim("name").asString();
    }

    public String extractRoleFromToken(String headerToken){
        String token = this.convertToken(headerToken);
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        return verify.getClaim("role").asString();
    }

    public boolean verifyToken(String headerToken){

        String token = this.convertToken(headerToken);
        DecodedJWT decodedJWT = JWT.decode(token);
        if (this.isInvalidToken(decodedJWT.getId())){
            return false;
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;

        } catch (JWTVerificationException e){
            return false;
        }
    }

    private boolean deleteToken(String uuid, Date time){

        if (this.isInvalidToken(uuid))
            return false;

        Date now = new Date();
        long expire = Math.max(time.getTime() - now.getTime(), 0);
        template.opsForValue().set(Const.JWT_BLACK_LIST + uuid, "", expire, TimeUnit.MILLISECONDS);
        return true;
    }

    private String convertToken(String headerToken){
        if(headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }

    private boolean isInvalidToken(String uuid) {
        return Boolean.TRUE.equals(template.hasKey(Const.JWT_BLACK_LIST + uuid));
    }

}
