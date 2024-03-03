package com.asdc.funderbackend.util;
	
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
	
@Component
public class JwtUtil {
		private static final int ONEHOUR = 3600000;

		@Value("${jwt.secret}")
		private String secret;

		public String generateToken(UserDetails userDetails) {
	        return Jwts.builder()
	                .setSubject(userDetails.getUsername())
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + ONEHOUR))
	                .signWith(SignatureAlgorithm.HS256, secret)
	                .compact();
	    }
		
		public String extractUsername(String token) {
		  return extractClaim(token, Claims::getSubject);
		}
		
		public Date extractExpiration(String token) {
		  return extractClaim(token, Claims::getExpiration);
		}
	
		private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		  final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		  return claimsResolver.apply(claims);
		}
	
		private Boolean isTokenExpired(String token) {
		  final Date expiration = extractExpiration(token);
		  return expiration.before(new Date());
		}
		
		public Boolean validateToken(String token, UserDetails userDetails) {
	      final String username = extractUsername(token);
	      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	  }
	}
