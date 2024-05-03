package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;



    // View all coupons
    @GetMapping("/coupons")
    public String viewCoupons(Model model) {
        List<Coupon> listCoupons = couponRepository.findAll();
        model.addAttribute("listCoupons", listCoupons);
        return "view_coupons";
    }

    // Add a new coupon (form page)
    @GetMapping("/coupons/add")
    public String addCoupon(Model model) {
        model.addAttribute("coupon", new Coupon());

        // You can also add other model attributes if needed

        return "add_coupon";
    }

    // Save a new coupon
    @PostMapping("/coupons/save")
    public ResponseEntity<Coupon> saveCoupon(@RequestBody Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        return new ResponseEntity<>(savedCoupon, HttpStatus.CREATED);
    }

    // Edit an existing coupon (form page)
    @GetMapping("/coupons/edit/{id}")
    public String editCoupon(@PathVariable int id, Model model) {
        Coupon coupon = couponRepository.findById(id).orElse(null);
        model.addAttribute("coupon", coupon);
        return "edit_coupon";
    }

    // Save updated coupon
    @PostMapping("/coupons/edit/{id}")
    public ResponseEntity<Coupon> saveUpdatedCoupon(@PathVariable int id, @RequestBody Coupon updatedCoupon) {
        if (couponRepository.existsById(id)) {
            // Update the existing coupon with the new data
            updatedCoupon.setCouponID(id);
            Coupon savedCoupon = couponRepository.save(updatedCoupon);
            return new ResponseEntity<>(savedCoupon, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a coupon
    @GetMapping("/coupons/delete/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable int id) {
        if (couponRepository.existsById(id)) {
            couponRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // View single coupon
    @GetMapping("/coupons/{id}")
    public String viewSingleCoupon(@PathVariable int id, Model model) {
        Coupon coupon = couponRepository.findById(id).orElse(null);
        model.addAttribute("coupon", coupon);
        return "view_single_coupon";
    }

    // Additional controller methods for other operations (if needed)
}
