package FYP;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuerRepository extends JpaRepository<Issuer, Integer> {

	Issuer findByUsername(String username);
}

 

