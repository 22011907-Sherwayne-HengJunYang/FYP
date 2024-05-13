package FYP;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ClaimantDetails implements UserDetails {
    
    private Claimant claimant;
    
    public ClaimantDetails(Claimant claimant){
        this.claimant = claimant;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(claimant.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return claimant.getPassword();
    }

    @Override
    public String getUsername() {
        return claimant.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public Claimant getClaimant() {
        return claimant;
    }


    public void setClaimant(Claimant claimant) {
        this.claimant = claimant;
    }
}
