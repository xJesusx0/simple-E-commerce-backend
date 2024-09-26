package jesus.loginSecurity.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jesus.loginSecurity.entities.User;
import jesus.loginSecurity.repositories.UserRepository;
import jesus.loginSecurity.rest.reponse.ApiResponse;

@RestController
@RequestMapping("/admin/users")
public class AdminUsersController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<User>>> getUsers() {
        List<User> users = userRepository.findAll();

        return ResponseEntity.ok()
            .body(new ApiResponse<List<User>>(users,"Usuarios encontrados", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable int id) {
        boolean userExists = userRepository.existsById(id);

        if(!userExists){
            return ResponseEntity.status(404)
                .body(new ApiResponse<>(null,"Usuario no encontrado", false));
        }

        User user = userRepository.findById(id).orElse(null);

        return ResponseEntity.ok()
            .body(new ApiResponse<User>(user,"Usuario encontrado",true));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<User>> addUser(@RequestBody User user){
        
        if(!user.validRole()){
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(null,"Rol invalido",false));
        }
        
        user.encryptPassword();
        
        try {
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok()
                .body(new ApiResponse<User>(savedUser, "Usuario guardado correctamente", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(new ApiResponse<>(null,"No se pudo registrar al usuario",false)); 
        }
        
    }

}
