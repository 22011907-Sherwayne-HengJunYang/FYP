package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Save a new coupon
    @PostMapping("/coupons/save")
    public String saveCoupons(Coupon coupon) {
    	CouponRepository.save(coupon);
    	return "redirect:/coupons";
    }

   
}
