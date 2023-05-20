package cl.api.creausuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.api.creausuarios.entity.Phone;
import cl.api.creausuarios.entity.Usuario;
import cl.api.creausuarios.entity.UsuarioResponse;
import cl.api.creausuarios.repository.PhoneRepository;
import cl.api.creausuarios.repository.UsuarioRepository;
import cl.api.creausuarios.service.UsuarioService;
import cl.api.creausuarios.util.Funciones;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired(required=true)
    private Funciones funciones;

    @GetMapping("algo")
    public String algo() {
    	return "hola";
    }
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario user) {
    	try {
        if (user.getName() == null || user.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"mensaje\": \"Faltan campos obligatorios\"}");
        }
        
        if(usuarioService.validarCorreoExistente(user.getEmail())) {
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body("{\"mensaje\": \"Correo ya registrado\"}");
        }
        
        if(!Funciones.validatePassword(user.getPassword())) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"mensaje\": \"La clave no cumple con las politicas, debe contener una mayuscula, minusculas y 2 números\"}");
        }
        
        String secretKey = funciones.getSecretKey();
        Usuario usuarioAGuardar = new Usuario();
        usuarioAGuardar.setCreated(new Date());
        usuarioAGuardar.setEmail(user.getEmail());
        usuarioAGuardar.setIsactive(true);
        usuarioAGuardar.setName(user.getName());
        usuarioAGuardar.setPassword(user.getPassword());
        usuarioAGuardar.setToken(funciones.token(user.getName(), secretKey));
        
        Phone telefonoAGuardar = new Phone();
        telefonoAGuardar.setNumber(user.getPhones().get(0).getNumber());
        telefonoAGuardar.setCitycode(user.getPhones().get(0).getCitycode());
        telefonoAGuardar.setContrycode(user.getPhones().get(0).getContrycode());
        Phone telefonoCreado = phoneRepository.save(telefonoAGuardar);
        List<Phone> listaTelefono = new ArrayList<Phone>();
        listaTelefono.add(telefonoCreado);
        usuarioAGuardar.setPhones(listaTelefono);
        usuarioAGuardar.setLastLogin(new Date());
        usuarioAGuardar.setModified(new Date());
        usuarioAGuardar.setLastLogin(new Date());
        
        Usuario usuarioCreado = userRepository.save(usuarioAGuardar);
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuarioCreado.getId());
        response.setCreated(usuarioCreado.getCreated());
        response.setModified(usuarioCreado.getModified());
        response.setLastLogin(usuarioCreado.getLastLogin());
        response.setToken(usuarioCreado.getToken());
        response.setIsactive(usuarioCreado.getIsactive());
        // Retornar una respuesta exitosa
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"mensaje\": \""+ e.getMessage()+"\"}");
    	}
    }
    
    /*id: id del usuario (puede ser lo que se genera por el banco de datos, pero sería
más deseable un UUID)
○ created: fecha de creación del usuario
○ modified: fecha de la última actualización de usuario
○ last_login: del último ingreso (en caso de nuevo usuario, va a coincidir con la
fecha de creación)
○ token: token de acceso de la API (puede ser UUID o JWT)
○ isactive: Indica si el usuario sigue habilitado dentro del sistema.
*/
    
}
