package com.grupo1.alojapp.Assemblies;

import com.grupo1.alojapp.DTOs.UserDTO;
import com.grupo1.alojapp.Model.Usuario;
import org.modelmapper.ModelMapper;

public class UsuarioAssembly extends ModelMapper {

    public UserDTO map(Usuario usuario, Class<UserDTO> destinationType){
        UserDTO usuarioDTO = super.map(usuario,destinationType);
        return  usuarioDTO;
    }

    public Usuario map(UserDTO usuarioDTO, Class<Usuario> destinationType){
        Usuario usuario = super.map(usuarioDTO, destinationType);
        return usuario;
    }

}
