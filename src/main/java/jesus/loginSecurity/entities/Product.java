package jesus.loginSecurity.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;

    @Column(unique = true,nullable = false)
    private String name;
    
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private int ammount;

    public boolean validProduct(){
        if(this.name == null || this.name.isBlank() || this.name.isEmpty()){
            return false;
        }

        if(this.ammount < 0 ){
            return false;
        }

        if(this.price <= 0){
          return false;
        }

        return true;
    }
}
