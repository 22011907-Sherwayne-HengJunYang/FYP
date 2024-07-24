package FYP;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Entity
public class OrderCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean status = true;

    @Temporal(TemporalType.DATE)
    private Date claimdate;
    
    @Temporal(TemporalType.DATE)
    private Date redeemdate;

    @ManyToOne
    @JoinColumn(name = "claimant_id")
    private Claimant claimant;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private int quantity;

    private String redeemCode; // Redeem code field for OrderCoupon
    
    public Vendor getVendor() {
        return this.coupon.getVendor();
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Claimant getClaimant() {
        return claimant;
    }

    public void setClaimant(Claimant claimant) {
        this.claimant = claimant;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }

    public Date getClaimdate() {
        return claimdate;
    }

    public void setClaimdate(Date claimdate) {
        this.claimdate = claimdate;
    }

    public Date getRedeemdate() {
        return redeemdate;
    }

    public void setRedeemdate(Date redeemdate) {
        this.redeemdate = redeemdate;
    }

    // Method to generate a random redeem code for the OrderCoupon
    public void generateRedeemCode() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[9]; // 9 bytes to ensure the encoded string is approximately 12 characters long
        random.nextBytes(bytes);
        this.redeemCode = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 12);
    }
}
