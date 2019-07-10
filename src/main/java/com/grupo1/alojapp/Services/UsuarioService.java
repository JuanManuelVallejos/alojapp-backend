package com.grupo1.alojapp.Services;

import com.grupo1.alojapp.DTOs.UserDTO;
import com.grupo1.alojapp.Model.Rol;
import com.grupo1.alojapp.Model.Usuario;
import com.grupo1.alojapp.Repositories.UsuarioRepository;
import com.grupo1.alojapp.Specifications.UsuarioSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolService rolService;

    public User GetUser(String username){
        Usuario usuario = getUsuarioByUsername(username);
        return this.userBuilder(usuario.getEmail(), usuario.getPassword(), usuario.getRoles());
    }

    private User userBuilder(String username, String password, Collection<Rol> roles) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        return new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
    }

    public UserDTO registrarUsuarioFromDTO(UserDTO userDTO){
        Collection<Rol> roles = rolService.getListRoleByNames(userDTO.getRoles());

        Usuario usuario =
                new Usuario(
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    roles);

        usuarioRepository.save(usuario);

        userDTO.setId(usuario.getId());
        userDTO.setEnabled(usuario.isEnabled());
        userDTO.setDateCreated(usuario.getDateCreated());

        return userDTO;
    }

    public Usuario getUsuarioByUsername(String username){
        Usuario filter = new Usuario();
        filter.setEmail(username);
        Specification<Usuario> spec = new UsuarioSpecification(filter);
        return usuarioRepository.findOne(spec).get();
    }

}
