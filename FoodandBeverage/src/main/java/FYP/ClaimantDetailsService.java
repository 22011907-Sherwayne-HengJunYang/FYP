package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ClaimantDetailsService implements UserDetailsService{

	@Autowired
	private ClaimantRepository claimantRepository;
	
	@Override
	public ClaimantDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Claimant claimant = claimantRepository.findByUsername(username);
		
		if (claimant == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new ClaimantDetails(claimant);
	}
}
