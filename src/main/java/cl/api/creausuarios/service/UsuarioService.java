package cl.api.creausuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.api.creausuarios.entity.Usuario;
import cl.api.creausuarios.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository usuarioRepository;
	
    private List<Usuario> users = new ArrayList<>();

    public List<Usuario> getAllUsers() {
        return users;
    }

    public Usuario createUser(Usuario user) {
        users.add(user);
        return user;
    }
    
    public boolean validarCorreoExistente(String correo) {
        return usuarioRepository.existsByEmail(correo);
    }
    
}
