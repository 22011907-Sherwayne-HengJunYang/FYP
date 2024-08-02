	package FYP;
	
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
	    private int quantityClaimed;
	    private int quantityRedeemed;
	    private float itemCost; // cost per product for coupon
	    private String title;
	    private String img;
	    
	    
	    
	    public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		// Date fields
	    private Date issueDate;
	    private Date expiryDate;
	
	    @ManyToOne
	    @JoinColumn(name = "vendor_id", nullable = false)
	    private Vendor vendor;
	    
	    @ManyToOne
	    @JoinColumn(name = "issuer_id")
	    private Issuer issuer;
	    
	    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<OrderCoupon> orderCoupons;
	
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
	    public Issuer getIssuer() {
	    	return issuer;
	    }
	    
	    public void setIssuer(Issuer issuer) {
	    	this.issuer = issuer;
	    }
	    
	    public Vendor getVendor() {
	        return vendor;
	    }
	
	    public void setVendor(Vendor vendor) {
	        this.vendor = vendor;
	    }
	
	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	        updateTotalQuantity();
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
	
	    public boolean isPublicCoupon() {
	        return publicCoupon;
	    }
	
	    public void setPublicCoupon(boolean publicCoupon) {
	        this.publicCoupon = publicCoupon;
	    }
	
	    public String getStatusString() {
	        return status ? "Available" : "Redeemed";
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
	        updateTotalQuantity();
	    }
	
	    public String getDescription() {
	        return description;
	    }
	
	    public void setDescription(String description) {
	        this.description = description;
	    }
	    
	    public String getCouponType() {
	        return couponType;
	    }
	
	    public void setCouponType(String couponType) {
	        this.couponType = couponType;
	    }
	
	    public int getQuantityClaimed() {
	        return quantityClaimed;
	    }
	
	    public void setQuantityClaimed(int quantityClaimed) {
	        this.quantityClaimed = quantityClaimed;
	    }
	    
	    public int getQuantityRedeemed() {
	        return quantityRedeemed;
	    }
	
	    public void setQuantityRedeemed(int quantityRedeemed) {
	        this.quantityRedeemed = quantityRedeemed;
	    }
	
	    public float getItemCost() {
	        return itemCost;
	    }
	
	    public void setItemCost(float itemCost) {
	        this.itemCost = itemCost;
	    }
	
	    private String couponType; // "discounted" or "free"
	    private int totalQuantity;
	    private boolean totalQuantityInitialized = false;
	
	    public int getTotalQuantity() {
	        return totalQuantity;
	    }
	
	    public void setTotalQuantity(int totalQuantity) {
	        if (!totalQuantityInitialized) {
	            this.totalQuantity = totalQuantity;
	            this.totalQuantityInitialized = true;
	        }
	    }
	
	    private void updateTotalQuantity() {
	        if (!totalQuantityInitialized) {
	            this.totalQuantity = this.publicQuantity + this.quantity;
	            this.totalQuantityInitialized = true;
	        }
	        this.quantityClaimed = this.totalQuantity - this.publicQuantity - this.quantity;
	    }
	    
	    
	}
