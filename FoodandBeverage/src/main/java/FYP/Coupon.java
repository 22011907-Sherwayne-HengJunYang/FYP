package FYP;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean status;
    private int quantity;
    private boolean publicCoupon;
    private int publicQuantity;
    private String description;
    
    // Date fields
    private Date issueDate;
    private Date expiryDate;

    private String redeemCode;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
    
    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderCoupon> orderCoupons;

    
    // Default constructor
    public Coupon() {
        this.issueDate = new Date(); // Sets issue date to the current date and time
        this.expiryDate = calculateExpiryDate(this.issueDate);
        this.status = true; // Initial status can be set as per your preference (e.g. true or false)
        this.redeemCode = generateRedeemCode(); // Generate random redeem code
    }

    // Method to calculate the expiry date
    public Date calculateExpiryDate(Date issueDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Adds 30 days to the issue date
        return calendar.getTime();
    }

    // Method to generate random redeem code
    private String generateRedeemCode() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24]; // 24 bytes = 192 bits
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    // Getters and setters
    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
        this.expiryDate = calculateExpiryDate(issueDate);
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }
    
    public boolean isPublicCoupon() {
        return publicCoupon;
    }

    public void setPublicCoupon(boolean publicCoupon) {
        this.publicCoupon = publicCoupon;
    }
    
    // Method to get the string representation of the status
    public String getStatusString() {
        return status ? "valid" : "invalid";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public int getPublicQuantity() {
		return publicQuantity;
	}

	public void setPublicQuantity(int publicQuantity) {
		this.publicQuantity = publicQuantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
