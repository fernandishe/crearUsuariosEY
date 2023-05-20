package cl.api.creausuarios.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

@Component
public class JwtTokenUtil {

	private static final long EXPIRATION_TIME = 86400000; // Tiempo de expiración del token (en milisegundos)
    @Autowired(required=true)
    private static Funciones funciones;
	
	
	@SuppressWarnings("deprecation")
	public static String generateToken(String username, String secretKey) {
		byte[] secretKey2 = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
		return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, secretKey2).compact();
	}
}
