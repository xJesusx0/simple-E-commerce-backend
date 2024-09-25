package jesus.loginSecurity.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PurchaseId implements Serializable {
    private int user_id;
    private int product_id;
    private LocalDateTime purchase_date;

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        PurchaseId that = (PurchaseId) o;

        return user_id == that.user_id && product_id == that.product_id && Objects.equals(purchase_date, that.purchase_date);
    }

    @Override
    public int hashCode(){
        return Objects.hash(user_id,product_id,purchase_date);
    }
}
