package FYP;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class VendorPasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String[] names = {
            "Liceria Restaurant", "Sunset Diner", "Oceanview Cafe", "Mountain Lodge Grill", "City Central Bistro",
            "Riverside Eatery", "Forest Feast", "Urban Delight", "Sunny Side Eatery", "Moonlight Diner",
            "Starry Night Cafe", "Golden Gate Grill", "Seaside Bistro", "Hilltop Cafe", "Garden Grove Eatery",
            "Icecream Haven", "Noodle House", "Fusion Bistro", "Classic Diner", "Modern Eatery"
        };
        String[] descriptions = {
            "Steak House", "Family Diner", "Seafood Specials", "Grill and Bar", "Urban Eatery",
            "Waterfront Dining", "Forest Retreat", "City Center Delight", "Sunny Breakfasts", "Night Owl Specials",
            "Romantic Dinners", "Golden Grills", "Coastal Cuisine", "Hilltop Views", "Garden Fresh",
            "Ice Cream Parlour", "Noodle Specialties", "Fusion Cuisine", "Classic Comforts", "Modern Menu"
        };
        for (int i = 1; i <= 20; i++) {
            String password = "vendor" + i;
            String encodedPassword = encoder.encode(password);
            String name = names[i - 1];
            String description = descriptions[i - 1];
            String email = "vendor" + i + "@example.com";
            String role = "ROLE_Vendor";
            String username = "vendor" + i;
            String sql = String.format(
                "INSERT INTO vendor (vendorid, description, email, name, password, role, username) VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s');",
                i, description, email, name, encodedPassword, role, username
            );
            System.out.println(sql);
        }
    }
}
