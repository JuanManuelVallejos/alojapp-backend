package com.grupo1.alojapp.Controllers;

import com.grupo1.alojapp.DTOs.LoginDTO;
import com.grupo1.alojapp.DTOs.UserDTO;
import com.grupo1.alojapp.Services.UsuarioService;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT })
public class UsuarioController {
    private static final Logger LOGGER = LogManager.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("usuario/register")
    @ResponseBody
    public ResponseEntity<UserDTO> registrarUsuario(@RequestBody UserDTO userDTO){
        usuarioService.registrarUsuarioFromDTO(userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("usuario/login")
    @ResponseBody
    public ResponseEntity<UserDTO> loginUsuarioCorrecto(@RequestBody LoginDTO loginDTO){
        Pair<UserDTO,Boolean> response = usuarioService.loginUsuarioCorrecto(loginDTO);
        if(response == null){
            return ResponseEntity.notFound().build();
        }
        if(response.getValue()){
            return ResponseEntity.ok(response.getKey());
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

}
