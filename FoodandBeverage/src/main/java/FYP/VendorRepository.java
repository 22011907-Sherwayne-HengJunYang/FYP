package FYP;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	Vendor findByUsername(String username);
    /// Add custom query methods if needed
	Page<Vendor> findAll(Pageable pageable);
}
