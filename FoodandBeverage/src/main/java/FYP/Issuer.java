package FYP;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Issuer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issuerID;

    private String name;
    private String username;
    private String password;
    private String email;
    private String role;


    /// Add more fields as needed

    public Integer getIssuerID() {
        return issuerID;
    }

    public void setIssuerID(Integer issuerID) {
        this.issuerID = issuerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Add getters and setters for other fields

    // Override equals and hashCode methods for consistency

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Issuer)) return false;
        Issuer issuer = (Issuer) o;
        return Objects.equals(issuerID, issuer.issuerID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issuerID);
    }
}
