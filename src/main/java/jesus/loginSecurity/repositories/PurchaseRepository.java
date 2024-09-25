package jesus.loginSecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jesus.loginSecurity.entities.Purchase;
import jesus.loginSecurity.entities.PurchaseId;

public interface PurchaseRepository extends JpaRepository<Purchase,PurchaseId>{

    @Query("SELECT purchases FROM Purchase purchases WHERE purchases.id.user_id = :userId")
    List<Purchase> findPurchasesByUserId(@Param("userId") int userId);
} 