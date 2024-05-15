package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class IssuerDetailsService implements UserDetailsService{

	@Autowired
	private IssuerRepository issuerRepository;
	
	@Override
	public IssuerDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Issuer issuer = issuerRepository.findByUsername(username);
		
		if (issuer == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new IssuerDetails(issuer);
	}
}

