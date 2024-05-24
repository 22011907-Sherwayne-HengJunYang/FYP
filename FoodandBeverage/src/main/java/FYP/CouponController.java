package FYP;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class CouponController {

    @Autowired
    private CouponRepository CouponRepository;
    
    @Autowired
    private VendorRepository VendorRepository;


     //View all coupons
    @GetMapping("/coupons")
    public String viewCoupons(Model model) {
        List<Coupon> listCoupons = CouponRepository.findAll();
        model.addAttribute("listCoupons", listCoupons);
        return "view_coupons";
    }

    // Add a new coupon (form page)
    @GetMapping("/coupons/add")
    public String addCoupon(Model model) {
        model.addAttribute("coupon", new Coupon());

        // You can also add other model attributes if needed
        List<Vendor> venList = VendorRepository.findAll();
        model.addAttribute ("venList", venList);
        
        
        return "add_coupon";
    }

    /// Save a new coupon
    @PostMapping("/coupons/save")
    public String saveCoupons(@Valid Coupon coupon, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors
            List<Vendor> venList = VendorRepository.findAll();
            model.addAttribute("venList", venList);
            return "add_coupon";
        }
        // Set the issueDate and expiryDate before saving
        coupon.setIssueDate(new Date()); // Sets the issue date to the current date
        coupon.calculateExpiryDate(calculateExpiryDate(coupon.getIssueDate()));
        
        CouponRepository.save(coupon);
        return "redirect:/coupons";
    }
    
    @GetMapping("/coupons/edit/{id}")
    public String editCoupon(@PathVariable("id") Integer id, Model model) {

        Coupon coupon = CouponRepository.getReferenceById(id);
        

        List<Vendor> venList = VendorRepository.findAll();
        model.addAttribute ("venList", venList);
        model.addAttribute("coupon", coupon);
        
        return "edit_coupon";
    }
    @PostMapping("/coupons/edit/{id}")
    public String saveupdated(@PathVariable("id") Integer id, Coupon coupon) {
   
   
        CouponRepository.save(coupon);
        
        return "redirect:/coupons";
    }
 // Method to calculate the expiry date
    private Date calculateExpiryDate(Date issueDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Adds 30 days to the issue date
        return calendar.getTime();
    }
    
    @GetMapping("/coupons/delete/{id}")
    public String deleteItem(@PathVariable("id") Integer id) {
    	
    	CouponRepository.deleteById(id);
    	
    	return "redirect:/coupons";
    }
    
    @PostMapping("/makePublic")
    public String makeCouponPublic(@RequestParam("couponID") int couponID,
                                   @RequestParam("quantity") int quantity,
                                   RedirectAttributes redirectAttributes) {
        // Retrieve the coupon from the database
        Coupon coupon = CouponRepository.findById(couponID).orElse(null);

        if (coupon != null && quantity > 0) {
            if (coupon.getQuantity() >= quantity) {
                // Update the coupon to make it public
                coupon.setPublicCoupon(true);

                // Decrement the quantity by the specified amount
                coupon.setQuantity(coupon.getQuantity() - quantity);

                // Increment the public quantity by the specified amount
                coupon.setPublicQuantity(coupon.getPublicQuantity() + quantity);

                CouponRepository.save(coupon);
                return "redirect:/publicCoupons";
            } else {
                // Add a flash attribute for insufficient quantity
                redirectAttributes.addFlashAttribute("errorMessage", "Insufficient quantity of coupons.");
            }
        }
        return "redirect:/coupons"; // Redirect to the coupon list page
    }
    // Handler for viewing public coupons
    @GetMapping("/publicCoupons")
    public String viewPublicCoupons(Model model) {
        // Retrieve all public coupons from the database
        List<Coupon> publicCoupons = CouponRepository.findByPublicCoupon(true);

        // Add the list of public coupons to the model
        model.addAttribute("publicCoupons", publicCoupons);

        // Return the view for viewing public coupons
        return "publicCoupons";
    }
}
