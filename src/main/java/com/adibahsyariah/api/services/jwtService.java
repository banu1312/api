package com.adibahsyariah.api.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class jwtService{
    
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String ,Object> extractClaims,UserDetails userDetails) {
        // long expirationMillis = System.currentTimeMillis() + (1000 * 60 * 60 * 24); // One day in milliseconds
        // Date expirationDate = new Date(expirationMillis);
    
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaim(token);
        return claimsResolvers.apply(claims);   
    }

    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode("2SvpZk2aoCSOPW/izky/RTnRwHRIJcrGOP8DVJwi+8GwpRaaQvesus96PQn0834fuLPL9BdEElkeY2cIo1i4ADK8fXjtPD18UT5fJQsn+OnaX0EknD3HBMVTefGl1vyHFQg969buh0pQq2eRLIvd7ffXgdQ3681/XaoS5q1k/oeClAwdD0SYNHJix5kyDeD1fSc0Xq41v0OLXlJztTx6zIRbcTQ2MnW6cU3pAW0JWrbT9E1qwcapbTcuBG8WuA3EpJcrXq70ohi+J4g30QEECIWhrx9TDKgHEm5mxC0ORXvsSsSh0dx2Sp2gXFiPTi+wdD+nzFFJOx4mwroi3p6ZyQsYjLuNvoOwX25XXWGTkwQ=");
        return Keys.hmacShaKeyFor(key);
    }
    private Claims extractAllClaim(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public boolean isTokenExpired(String token){
        return extractExp(token).before(new Date());
    }

    private Date extractExp(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
}
