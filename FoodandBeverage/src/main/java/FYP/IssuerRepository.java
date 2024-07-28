package FYP;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuerRepository extends JpaRepository<Issuer, Integer> {

	Issuer findByUsername(String username);
	Issuer findById(int id);
	Page<Issuer> findAll(Pageable pageable);
}

 

