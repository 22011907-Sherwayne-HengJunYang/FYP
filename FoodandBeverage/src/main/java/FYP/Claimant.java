package FYP;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
public class Claimant {
  
    private String name;
    private String username;
    private String password;
    private String email;
    private String role;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private int age;
    private String gender;
    
    @OneToMany(mappedBy = "claimant")
    private List<OrderCoupon> orderCoupons;

    @Transient
    private Date claimedDate;

    @Transient
    private Date redeemDate;

    // Getters and Setters

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<OrderCoupon> getOrderCoupons() {
        return orderCoupons;
    }

    public void setOrderCoupons(List<OrderCoupon> orderCoupons) {
        this.orderCoupons = orderCoupons;
    }

    public Date getClaimedDate() {
        return orderCoupons != null && !orderCoupons.isEmpty() ? orderCoupons.get(0).getClaimdate() : null;
    }

    public Date getRedeemDate() {
        return orderCoupons != null && !orderCoupons.isEmpty() ? orderCoupons.get(0).getRedeemdate() : null;
    }
}
