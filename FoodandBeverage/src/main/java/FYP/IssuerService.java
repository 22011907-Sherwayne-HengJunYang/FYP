package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IssuerService {

    @Autowired
    private IssuerRepository issuerRepository;

    public Page<Issuer> findIssuers(Pageable pageable) {
        return issuerRepository.findAll(pageable);
    }
}

