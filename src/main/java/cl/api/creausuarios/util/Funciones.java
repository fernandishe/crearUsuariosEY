package cl.api.creausuarios.util;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Funciones {

	@Autowired
	private static Environment environment;
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d{2}).*$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

	public String token(String parametro, String secretKey) {
		return JwtTokenUtil.generateToken(parametro, secretKey);
	}

	public String obtenerValorPropiedad(String nombrePropiedad) {
		return environment.getProperty(nombrePropiedad);
	}

	@Value("${parametros.token.secretkey}")
	private String secretKey;

	public String getSecretKey() {
		return secretKey;
	}
	
    public static boolean validatePassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
