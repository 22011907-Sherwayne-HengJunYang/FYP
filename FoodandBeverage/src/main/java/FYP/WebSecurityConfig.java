package FYP;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public AdminDetailsService adminDetailsService() {
		return new AdminDetailsService();
	}
	
	@Bean
    public VendorDetailsService vendorDetailsService() {
        return new VendorDetailsService();
    }

    @Bean
    public IssuerDetailsService issuerDetailsService() {
        return new IssuerDetailsService();
    }

    @Bean
    public ClaimantDetailsService claimantDetailsService() {
        return new ClaimantDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CompositeUserDetailsService compositeUserDetailsService() {
        return new CompositeUserDetailsService(
                Arrays.asList(vendorDetailsService(), issuerDetailsService(), claimantDetailsService(), adminDetailsService()));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(compositeUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				.requestMatchers("/coupons", "/coupons/add", "/coupons/edit/*", "/coupons/save", "/coupons/delete",
						"/issuer", "/issuer/add", "/issuer/edit/*", "/issuer/save", "/issuer/delete/*", "/vendors",
						"/vendors/add", "/vendors/edit/*", "/vendors/save", "/vendors/delete/*","/claimants","/claimants/add","/claimants/edit/*"
						,"/claimants/save","/claimants/delete/*","/makePublic","/publicCoupons")
				.permitAll().requestMatchers("header.html").permitAll().requestMatchers("/").permitAll() // Home page is
																											// visible
																											// without
																											// logging
																											// in
				.requestMatchers("/aboutus").permitAll() // about page is visible without logging in
				.requestMatchers("/signup").permitAll().requestMatchers("/signup/save").permitAll() // about page is
																									// visible without
																									// logging in
				.requestMatchers("/Contactus").permitAll() // contact page is visible without logging in
				.requestMatchers("/bootstrap/*/*").permitAll() // for static resources, visible to all
				.requestMatchers("login.css").permitAll() // for static resources, visible to all
				.requestMatchers("/login").permitAll() // for static resources, visible to all
				.requestMatchers("index.css").permitAll() // for static resources, visible to all
				.requestMatchers("/uploads/*/*/*").permitAll() // for static resources, visible to all
				.requestMatchers("product.css").permitAll() // for static resources, visible to all
				.requestMatchers("button.css").permitAll() // for static resources, visible to all
				.requestMatchers("banner.css").permitAll() // for static resources, visible to all
				.requestMatchers("/images/*").permitAll() // for static resources, visible to all
				.anyRequest().authenticated())// Any other requests not specified earlier
				.formLogin((login) -> login.loginPage("/login").permitAll().defaultSuccessUrl("/")) // Goes to homepage
																									// upon login
				.logout((logout) -> logout.logoutSuccessUrl("/"))// Goes to homepage upon logout
				.exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/403"));

		return http.build();
	}
}
