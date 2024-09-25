package jesus.loginSecurity.rest.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse <T>{

    private T data;
    private String message;
    private boolean success;

}
