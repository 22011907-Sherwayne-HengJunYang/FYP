package FYP;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Objects;
import java.util.Set;

@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vendorID;

    private String name;
    private String description;
    
    @OneToMany(mappedBy="vendor")
    private Set<Coupon> coupons;

    /// Add more fields as needed

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Add getters and setters for other fields

    // Override equals and hashCode methods for consistency

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;
        Vendor vendor = (Vendor) o;
        return Objects.equals(vendorID, vendor.vendorID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorID);
    }
}
