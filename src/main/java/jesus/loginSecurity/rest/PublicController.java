package jesus.loginSecurity.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jesus.loginSecurity.entities.Product;
import jesus.loginSecurity.repositories.ProductRepository;

@RestController
@RequestMapping("/public")
public class PublicController {
    
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/home")
    String publicHome(){
        return "Home publico";
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        
        List<Product> products = productRepository.findAll();

        return products;

    }
}
