package com.netcracker.vacations;

import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

public class Util {

    private static String secret = "JwtSecretKey";

    public static String extractLoginFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

}
