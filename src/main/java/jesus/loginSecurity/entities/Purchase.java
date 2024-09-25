package jesus.loginSecurity.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "purchases")
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    @EmbeddedId
    private PurchaseId purchase_id;

    @Column(nullable = false)
    private int quantity;
}
