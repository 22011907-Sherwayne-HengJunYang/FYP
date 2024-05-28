package FYP;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
public class CartCouponController {

	@Autowired
	private CartCouponRepository cartCouponRepo;

	@Autowired
	private CouponRepository couponRepo;

	@Autowired
	private ClaimantRepository claimantRepo;
	
	@Autowired
	private OrderCouponRepository orderRepo;

	@GetMapping("/cart")
	public String showCart(Model model, Principal principal) {
	    // Get currently logged in user
	    ClaimantDetails loggedInClaimant = (ClaimantDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    int loggedInClaimantId = loggedInClaimant.getClaimant().getId();

	    // Get shopping cart items added by this user
	    List<CartCoupon> cartCouponList = cartCouponRepo.findByClaimant_Id(loggedInClaimantId);

	    // Add the shopping cart items to the model
	    model.addAttribute("cartCouponList", cartCouponList);


	    // Add the shopping cart total to the model
	    model.addAttribute("claimantId", loggedInClaimantId);

	    return "cart";
	}
	
	@PostMapping("/cart/process_order") 
    public String processOrder(Model model, 
     @RequestParam("claimantId") int claimantId, @RequestParam("orderId") String orderId, 
     @RequestParam("transactionId") String transactionId) { 
    
         // 1. Retrieve the member object   
        Claimant claimant = claimantRepo.getReferenceById(claimantId); 
    
         // 2. Retrieve all cart items, paid for by the member 
        List<CartCoupon> cartCouponList = cartCouponRepo.findByClaimant_Id(claimantId); 
    
        // 3. For each cart item paid, 
        for (int i = 0; i < cartCouponList.size(); i++) { 
            // a) Retrieve the item id, item, and quantity of the item purchased 
         CartCoupon currCartCoupon = cartCouponList.get(i); 
         Coupon coupon2Update = currCartCoupon.getCoupon(); 
         int qtyOfCouponCollected = currCartCoupon.getQuantity(); 
         int coupon2UpdateId = coupon2Update.getId() ; 
         System.out.println("Item: " + coupon2Update.getDescription());
    
            // b) Update the item’s quantity in the item repository 
         Coupon inventoryCoupon = couponRepo.getReferenceById(coupon2UpdateId) ; 
         int currentInventoryQuantity = inventoryCoupon.getPublicQuantity(); 
         System.out.println("Current inventory quantity: " + inventoryCoupon.getPublicQuantity()); 
         inventoryCoupon.setPublicQuantity(currentInventoryQuantity - qtyOfCouponCollected); 
         couponRepo.save(inventoryCoupon); 
         
        // c) Add the member, item, quantity of the item purchased, order id, and transaction id into the order repository 
            OrderCoupon orderCoupon = new OrderCoupon(); 
            orderCoupon.setClaimant(claimant); 
            orderCoupon.setCoupon(coupon2Update); 
            orderCoupon.setQuantity(qtyOfCouponCollected); 
            orderCoupon.setOrderId(orderId); 
            orderCoupon.setTransactionId(transactionId); 
            orderRepo.save(orderCoupon); 
         
    
        // d) Clear the items purchased by the member from the cart item repository 
        cartCouponRepo.deleteById(currCartCoupon.getId()); 
        } 
        
        // 4. Add the cart total, cart item list, member object, order id, and transaction id to the model 
        // for display on the acknowledgement page 
      
        model.addAttribute("cartCouponList", cartCouponList); 
        model.addAttribute("claimant", claimant); 
        model.addAttribute("orderId", orderId); 
        model.addAttribute("transactionId", transactionId); 
    
        
        return "redirect:/cart";
    }
	
	@PostMapping("/cart/add/{couponId}")
	public String addToCart(@PathVariable("couponId") int couponId, @RequestParam("quantity") int publicQuantity,
	                        Principal principal, RedirectAttributes redirectAttributes) {

	    // Step 1: Get currently logged in user
	    Object loggedInUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (loggedInUser instanceof ClaimantDetails) {
	        ClaimantDetails loggedInClaimant = (ClaimantDetails) loggedInUser;
	        Claimant claimant = loggedInClaimant.getClaimant();
	        // Proceed with adding to cart logic for claimants
	        Coupon coupon = couponRepo.findById(couponId).orElse(null);
	        if (coupon != null && publicQuantity <= publicQuantity) {
	            // Create a new CartItem object
	            CartCoupon newCartCoupon = new CartCoupon();
	            newCartCoupon.setCoupon(coupon);
	            newCartCoupon.setClaimant(claimant);
	            newCartCoupon.setQuantity(publicQuantity);
	            // Save the new CartItem object to the repository
	            cartCouponRepo.save(newCartCoupon);
	        } else {
	            redirectAttributes.addFlashAttribute("addError", true);
	        }
	    } else {
	        // If the logged-in user is not a claimant, redirect or show an error
	        redirectAttributes.addFlashAttribute("addError", true); // Or any other appropriate message
	    }

	    // Redirect back to the cart page
	    return "redirect:/cart";
	}

    @PostMapping("/cart/update/{id}")
    public String updateCart(@PathVariable("id") int cartCouponId, @RequestParam("qty") int newQty,
                             RedirectAttributes redirectAttributes) {

        // Step 1: Get cartItem object by cartItem's id
        CartCoupon cartCoupon = cartCouponRepo.findById(cartCouponId).orElse(null);

        if (cartCoupon != null) {
            // Step 2: Get the actual quantity of the item
            int actualQty = cartCoupon.getCoupon().getPublicQuantity();

            // Step 3: Check if the new quantity is less than or equal to the actual quantity
            if (newQty <= actualQty) {
                // Step 4: Set the quantity of the cartItem object retrieved
                cartCoupon.setQuantity(newQty);

                // Step 5: Save the cartItem back to the cartItemRepo
                cartCouponRepo.save(cartCoupon);
                redirectAttributes.addFlashAttribute("updateSuccess", true);
            } else {
                // Step 6: If the new quantity is greater than the actual quantity, set an error attribute
                redirectAttributes.addFlashAttribute("updateError", true);
            }
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCoupon(@PathVariable("id") int cartCouponId,RedirectAttributes redirectAttributes) {
        // Step 1: Remove item from cartItemRepo 
        cartCouponRepo.deleteById(cartCouponId);
        
        redirectAttributes.addFlashAttribute("DeleteSuccess", true);

        // Step 2: Redirect to the cart page
        return "redirect:/cart";
    }
    
    @GetMapping("/Inventory")
    public String viewInventory(Model model) {
        // Retrieve all claimants from the database
        List<OrderCoupon> listOrders = orderRepo.findAll();

        // Add the list of claimants to the model
        model.addAttribute("listOrders", listOrders);
        
        // Return the view to display the list of claimants
        return "Inventory";
    }
}
