package com.grupo1.alojapp.Controllers;

import com.grupo1.alojapp.DTOs.LoginDTO;
import com.grupo1.alojapp.DTOs.ResponseDTO;
import com.grupo1.alojapp.DTOs.ResponseHttp;
import com.grupo1.alojapp.DTOs.UserDTO;
import com.grupo1.alojapp.Exceptions.RoleNotFoundException;
import com.grupo1.alojapp.Services.UsuarioService;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT })
public class UsuarioController {
    private static final Logger LOGGER = LogManager.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("usuario")
    @ResponseBody
    public ResponseEntity<ResponseHttp> registrarUsuario(@RequestBody UserDTO userDTO){
        try{
            usuarioService.registrarUsuarioFromDTO(userDTO);
        }
        catch(RoleNotFoundException roleException){
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(
                            "Checkee que se esten enviando correctamente los roles"));
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(
                    "Ha ocurrido un error inesperado."));
        }

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
