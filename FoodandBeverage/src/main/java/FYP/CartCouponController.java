package FYP;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

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

        List<CartCoupon> cartCouponList = cartCouponRepo.findByClaimant_Id(loggedInClaimantId);

        model.addAttribute("cartCouponList", cartCouponList);
        model.addAttribute("claimantId", loggedInClaimantId);

        return "cart";
    }

    @PostMapping("/cart/process_order")
    public String processOrder(Model model,
                               @RequestParam("claimantId") int claimantId,
                               @RequestParam("transactionId") String transactionId,
                               RedirectAttributes redirectAttributes) {

        // Generate a random order ID using UUID
        String orderId = UUID.randomUUID().toString();

        Claimant claimant = claimantRepo.getReferenceById(claimantId);

        List<CartCoupon> cartCouponList = cartCouponRepo.findByClaimant_Id(claimantId);
        boolean orderProcessed = false;

        for (CartCoupon currCartCoupon : cartCouponList) {
            Coupon coupon2Update = currCartCoupon.getCoupon();
            int qtyOfCouponCollected = currCartCoupon.getQuantity();
            int coupon2UpdateId = coupon2Update.getId();

            // Check if the user already has this coupon in their inventory
            boolean alreadyInInventory = orderRepo.existsByClaimant_IdAndCoupon_Id(claimantId, coupon2UpdateId);

            if (!alreadyInInventory) {
                Coupon inventoryCoupon = couponRepo.getReferenceById(coupon2UpdateId);
                int currentInventoryQuantity = inventoryCoupon.getPublicQuantity();
                inventoryCoupon.setPublicQuantity(currentInventoryQuantity - qtyOfCouponCollected);
                couponRepo.save(inventoryCoupon);

                OrderCoupon orderCoupon = new OrderCoupon();
                orderCoupon.setClaimant(claimant);
                orderCoupon.setCoupon(coupon2Update);
                orderCoupon.setQuantity(qtyOfCouponCollected);
                orderCoupon.setOrderId(orderId);
                orderCoupon.setTransactionId(transactionId);
                orderRepo.save(orderCoupon);

                orderProcessed = true;
            } else {
                redirectAttributes.addFlashAttribute("inventoryError", "Coupon " + coupon2Update.getDescription() + " already exists in inventory.");
            }

            // Always remove from cart, even if it wasn't added to inventory
            cartCouponRepo.deleteById(currCartCoupon.getId());
        }

        if (orderProcessed) {
            redirectAttributes.addFlashAttribute("orderSuccess", "Order processed successfully.");
        }

        return "redirect:/Inventory";
    }

    @PostMapping("/cart/add/{couponId}")
    public String addToCart(@PathVariable("couponId") int couponId, @RequestParam("quantity") int publicQuantity,
                            Principal principal, RedirectAttributes redirectAttributes) {

        Object loggedInUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser instanceof ClaimantDetails) {
            ClaimantDetails loggedInClaimant = (ClaimantDetails) loggedInUser;
            Claimant claimant = loggedInClaimant.getClaimant();

            Coupon coupon = couponRepo.findById(couponId).orElse(null);
            if (coupon != null) {
                // Try to find this coupon in the user's cart
                CartCoupon existingCartCoupon = cartCouponRepo.findByClaimant_IdAndCoupon_Id(claimant.getId(), couponId);

                if (existingCartCoupon == null) {
                    // User doesn't have this coupon in their cart yet
                    int availableQuantity = coupon.getPublicQuantity();
                    if (publicQuantity <= availableQuantity) {
                        CartCoupon newCartCoupon = new CartCoupon();
                        newCartCoupon.setCoupon(coupon);
                        newCartCoupon.setClaimant(claimant);
                        newCartCoupon.setQuantity(publicQuantity);
                        cartCouponRepo.save(newCartCoupon);
                        redirectAttributes.addFlashAttribute("addSuccess", true);
                    } else {
                        redirectAttributes.addFlashAttribute("addError", "Not enough quantity available.");
                    }
                } else {
                    redirectAttributes.addFlashAttribute("addError", "Coupon already in cart.");
                }
            } else {
                redirectAttributes.addFlashAttribute("addError", "Coupon not found.");
            }
        } else {
            redirectAttributes.addFlashAttribute("addError", "User not logged in or invalid user type.");
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCoupon(@PathVariable("id") int cartCouponId, RedirectAttributes redirectAttributes) {
        cartCouponRepo.deleteById(cartCouponId);
        redirectAttributes.addFlashAttribute("deleteSuccess", true);
        return "redirect:/cart";
    }

    @GetMapping("/Inventory")
    public String viewInventory(Model model) {
        // Get currently logged in user
        ClaimantDetails loggedInClaimant = (ClaimantDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loggedInClaimantId = loggedInClaimant.getClaimant().getId();

        // Fetch only the orders for the logged-in user
        List<OrderCoupon> listOrders = orderRepo.findByClaimant_Id(loggedInClaimantId);
        model.addAttribute("listOrders", listOrders);

        return "Inventory";
    }
}
