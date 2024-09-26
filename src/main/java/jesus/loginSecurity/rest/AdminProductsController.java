package jesus.loginSecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jesus.loginSecurity.entities.Product;
import jesus.loginSecurity.repositories.ProductRepository;
import jesus.loginSecurity.rest.reponse.ApiResponse;

@RestController
@RequestMapping("/admin/products")
@PreAuthorize("hasAuthority('admin')")
public class AdminProductsController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product product) {

        if (!product.validProduct()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(null, "Producto invalido", false));
        }
        try {
            Product savedProduct = productRepository.save(product);

            return ResponseEntity.status(200)
                    .body(new ApiResponse<Product>(savedProduct, "Producto guardado correctamente", true));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(null, "Ha ocurrido un error al insertar el producto", false));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable int id, @RequestBody Product product) {
        
        boolean productExists = productRepository.existsById(id);

        if(!productExists){
            return ResponseEntity.status(404)
                .body(new ApiResponse<>(null, "Producto no encontrado",false));
        }

        if(!product.validProduct()){
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(null,"Producto invalido", false));
        }

        Product existingProduct = productRepository.findById(id).orElse(null);

        existingProduct.setName(product.getName());
        existingProduct.setAmount(product.getAmount());
        existingProduct.setPrice(product.getPrice());

        try{
            Product savedProduct = productRepository.save(existingProduct);
            return ResponseEntity.ok()
                .body(new ApiResponse<Product>(savedProduct, "Producto actualizado correctamente", true));

        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(new ApiResponse<>(null,"Ha ocurrido un error al actualizar el producto",false));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable int id) {
        
        boolean productExists = productRepository.existsById(id);
        if(!productExists){
            return ResponseEntity.status(404)
                .body(new ApiResponse<>(null, "Producto no encontrado",false));
        }

        try {
            productRepository.deleteById(id);
            return ResponseEntity.ok()
                .body(new ApiResponse<>(null,"Producto eliminado correctamente", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(new ApiResponse<>(null,"Ha ocurrido un error al actualizar el producto",false));
 
        }
    }
}
