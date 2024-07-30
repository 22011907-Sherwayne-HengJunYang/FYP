package FYP;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for (int i = 1; i <= 200; i++) {
            String password = "claimant" + i;
            String encodedPassword = encoder.encode(password);
            String sql = String.format(
                "INSERT INTO claimant (id, age, email, gender, name, password, role, username) VALUES (%d, %d, 'user%d@example.com', '%s', 'Name%d', '%s', 'ROLE_Claimant', 'claimant%d');",
                i, getRandomAge(), i, getRandomGender(), i, encodedPassword, i
            );
            System.out.println(sql);
        }
    }

    private static int getRandomAge() {
        return (int) (Math.random() * (60 - 18 + 1) + 18);
    }

    private static String getRandomGender() {
        return Math.random() > 0.5 ? "M" : "F";
    }
}
