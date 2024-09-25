package jesus.loginSecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jesus.loginSecurity.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{    

} 