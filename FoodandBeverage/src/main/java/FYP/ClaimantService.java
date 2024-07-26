package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClaimantService {

    @Autowired
    private ClaimantRepository claimantRepository;

    public Page<Claimant> findClaimants(Pageable pageable) {
        return claimantRepository.findAll(pageable);
    }
}

