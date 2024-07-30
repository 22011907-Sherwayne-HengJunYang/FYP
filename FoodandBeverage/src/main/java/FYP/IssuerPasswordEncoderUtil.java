package FYP;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class IssuerPasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String[] names = {
            "Issuer One", "Issuer Two", "Issuer Three", "Issuer Four", "Issuer Five",
            "Issuer Six", "Issuer Seven", "Issuer Eight", "Issuer Nine", "Issuer Ten",
            "Issuer Eleven", "Issuer Twelve", "Issuer Thirteen", "Issuer Fourteen", "Issuer Fifteen"
        };
        for (int i = 1; i <= 15; i++) {
            String password = "issuer" + i;
            String encodedPassword = encoder.encode(password);
            String name = names[i - 1];
            String email = "issuer" + i + "@example.com";
            String role = "ROLE_Issuer";
            String username = "issuer" + i;
            String sql = String.format(
                "INSERT INTO issuer (issuerid, email, name, password, role, username) VALUES (%d, '%s', '%s', '%s', '%s', '%s');",
                i, email, name, encodedPassword, role, username
            );
            System.out.println(sql);
        }
    }
}
