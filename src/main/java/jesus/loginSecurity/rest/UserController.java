package jesus.loginSecurity.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import jesus.loginSecurity.entities.Purchase;
import jesus.loginSecurity.entities.PurchaseId;
import jesus.loginSecurity.repositories.ProductRepository;
import jesus.loginSecurity.repositories.PurchaseRepository;
import jesus.loginSecurity.rest.reponse.ApiResponse;
import jesus.loginSecurity.rest.requests.PurchaseRequest;
import jesus.loginSecurity.security.TokenUtils;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("purchases-history")
    public List<Purchase> purchaseHistory(HttpServletRequest request) {
        String token = TokenUtils.extractTokenFromRequest(request);
        int id = TokenUtils.getUserIdFromToken(token);

        List<Purchase> purchases = purchaseRepository.findPurchasesByUserId(id);
        return purchases;
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/purchase-product")
    public ResponseEntity<ApiResponse> purchaseProduct(HttpServletRequest request, @RequestBody PurchaseRequest purchaseRequest) {
        String token = TokenUtils.extractTokenFromRequest(request);
        int userId = TokenUtils.getUserIdFromToken(token);

        boolean productExists = productRepository.existsById(purchaseRequest.getProductId());

        if (!productExists) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(null, "Producto no encontrado", false));
        }

        if(purchaseRequest.getQuantity() <= 0){
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(null,"Cantidad invalida", false));
        }

        LocalDateTime now = LocalDateTime.now();

        PurchaseId purchaseId = new PurchaseId(userId, purchaseRequest.getProductId(), now);
        Purchase purchase = new Purchase(purchaseId, purchaseRequest.getQuantity());

        purchaseRepository.save(purchase);
        return ResponseEntity.ok().body(
                new ApiResponse<Purchase>(purchase, "Compra registrada correctamente", true)
        );

    }
}
