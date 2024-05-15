package FYP;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class IssuerDetails implements UserDetails {
	
	private Issuer issuer;
	
	public IssuerDetails(Issuer issuer){
		this.issuer = issuer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(issuer.getRole());
        return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		return issuer.getPassword();
	}

	@Override
	public String getUsername() {
		return issuer.getUsername();
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


	public Issuer getIssuer() {
		return issuer;
	}


	public void setIssuer(Issuer issuer) {
		this.issuer = issuer;
	}
}
	

