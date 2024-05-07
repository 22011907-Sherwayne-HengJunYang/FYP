/**
 * 
 * I declare that this code was written by me, 22011907. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sherwayne Heng Jun Yang
 * Student ID: 22011907
 * Class: E6C
 * Date created: 2024-Apr-24 12:07:49 pm 
 * 
 */

package FYP;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 */
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponID;
	/* private int foodID; */
	/* private int issuerID; */
	/* private int claimantID; */
    private boolean status;
    
    // Date fields
    private Date issueDate;
    private Date expiryDate;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id",nullable=false)
    private Vendor vendor;
    
    // Default constructor
    public Coupon() {
        // Initialize dates
        this.issueDate = new Date(); // Sets issue date to the current date and time
        this.expiryDate = calculateExpiryDate(this.issueDate);
    }

    // Method to calculate the expiry date
    Date calculateExpiryDate(Date issueDate) {
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

	/*
	 * public int getFoodID() { return foodID; }
	 * 
	 * public void setFoodID(int foodID) { this.foodID = foodID; }
	 */

	/*
	 * public int getIssuerID() { return issuerID; }
	 * 
	 * public void setIssuerID(int issuerID) { this.issuerID = issuerID; }
	 */
	/*
	 * public int getClaimantID() { return claimantID; }
	 * 
	 * public void setClaimantID(int claimantID) { this.claimantID = claimantID; }
	 */
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
        this.status = status;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getIssueDate() {
        return issueDate;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
        // Recalculate expiry date whenever issue date is changed
        this.expiryDate = calculateExpiryDate(issueDate);
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getExpiryDate() {
        return expiryDate;
    }
}