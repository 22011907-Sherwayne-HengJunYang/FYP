package FYP;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimantRepository extends JpaRepository<Claimant, Integer> {

	Claimant findByUsername(String username);
}

 
