package FYP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimantRepository extends JpaRepository<Claimant, Integer> {

	Claimant findByUsername(String username);
	Claimant findById(int id);
	Page<Claimant> findAll(Pageable pageable);
}

 
