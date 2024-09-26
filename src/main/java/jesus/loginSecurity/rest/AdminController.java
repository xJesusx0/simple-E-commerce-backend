package jesus.loginSecurity.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('admin')")
public class AdminController {

    @GetMapping("/home")
    public String adminHome() {
        return "admin";
    }
}
