package FYP;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompositeUserDetailsService implements UserDetailsService {

    private final List<UserDetailsService> userDetailsServiceList;

    public CompositeUserDetailsService(List<UserDetailsService> userDetailsServiceList) {
        this.userDetailsServiceList = userDetailsServiceList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (UserDetailsService userDetailsService : userDetailsServiceList) {
            try {
                return userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                // Continue to the next UserDetailsService
            }
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
