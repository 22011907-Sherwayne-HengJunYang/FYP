package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminDetailsService implements UserDetailsService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public AdminDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin admin = adminRepository.findByUsername(username);
		
		if (admin == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new AdminDetails(admin);
	}
}
