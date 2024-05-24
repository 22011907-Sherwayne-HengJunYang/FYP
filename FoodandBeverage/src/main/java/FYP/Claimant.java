package FYP;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Claimant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer claimantID;
    private String name;
    private String username;
    private String password;
    private String email;
    private String role;


    /// Add more fields as needed

    public Integer getClaimantID() {
        return claimantID;
    }

    public void setClaimantID(Integer claimantID) {
        this.claimantID = claimantID;
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
        if (!(o instanceof Claimant)) return false;
        Claimant claimant = (Claimant) o;
        return Objects.equals(claimantID, claimant.claimantID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(claimantID);
    }
}
