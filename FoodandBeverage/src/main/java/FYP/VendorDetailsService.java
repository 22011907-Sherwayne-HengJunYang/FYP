package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class VendorDetailsService implements UserDetailsService{

	@Autowired
	private VendorRepository vendorRepository;
	
	@Override
	public VendorDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Vendor vendor = vendorRepository.findByUsername(username);
		
		if (vendor == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new VendorDetails(vendor);
	}
}
