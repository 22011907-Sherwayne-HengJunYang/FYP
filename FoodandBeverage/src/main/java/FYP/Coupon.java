package FYP;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponID;
    private boolean status;
    
    // Date fields
    private Date issueDate;
    private Date expiryDate;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
    
    // Default constructor
    public Coupon() {
        this.issueDate = new Date(); // Sets issue date to the current date and time
        this.expiryDate = calculateExpiryDate(this.issueDate);
        this.status = true; // Initial status can be set as per your preference (e.g. true or false)
    }

    // Method to calculate the expiry date
    public Date calculateExpiryDate(Date issueDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Adds 30 days to the issue date
        return calendar.getTime();
    }

    // Getters and setters
    public int getCouponID() {
        return couponID;
    }

    public void setCouponID(int couponID) {
        this.couponID = couponID;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        // Directly set the status based on user input
        this.status = status;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
        // Recalculate expiry date based on the new issue date
        this.expiryDate = calculateExpiryDate(issueDate);
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    // Method to get the string representation of the status
    public String getStatusString() {
        return status ? "valid" : "invalid";
    }
}
