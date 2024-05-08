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
   
}
