package jesus.loginSecurity.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jesus.loginSecurity.entities.Purchase;
import jesus.loginSecurity.repositories.PurchaseRepository;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    PurchaseRepository purchaseRepository;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    // @PostMapping("/buy-product")
    // public ResponseEntity buyProduct() {

    // }


    @GetMapping("purchases-history")
    public List<Purchase> purchaseHistory() {
        List<Purchase> purchases = purchaseRepository.findPurchasesByUserId(1);
        return purchases;
    }
}
