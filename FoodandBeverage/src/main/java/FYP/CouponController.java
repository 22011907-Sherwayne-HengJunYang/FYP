package FYP;

import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class CouponController {
	@Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;
    
    @Autowired
    private VendorRepository vendorRepository;
    

    // View all coupons
    @GetMapping("/coupons")
    public String viewCoupons(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 8; // Define the size of the page
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Coupon> couponPage = couponService.findCoupons(pageRequest);

        model.addAttribute("listCoupons", couponPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", couponPage.getTotalPages());

        return "view_coupons";
    }

    // Add a new coupon (form page)
    @GetMapping("/coupons/add")
    public String addCoupon(Model model) {
        model.addAttribute("coupon", new Coupon());

        // Get the logged-in vendor's details
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof VendorDetails) {
            VendorDetails loggedInVendor = (VendorDetails) principal;
            model.addAttribute("loggedInVendor", loggedInVendor.getVendor());
        }

        return "add_coupon";
    }

    // Save a new coupon
    @PostMapping("/coupons/save")
    public String saveCoupons(@Valid Coupon coupon, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof VendorDetails) {
                VendorDetails vendorDetails = (VendorDetails) principal;
                model.addAttribute("loggedInVendorID", vendorDetails.getVendor().getVendorID());
            }
            return "add_coupon";
        }

        // Set the issueDate and expiryDate before saving
        coupon.setIssueDate(new Date()); // Set the issue date to the current date
        coupon.setExpiryDate(calculateExpiryDate(coupon.getIssueDate()));
        
        // Set the vendor ID to the logged-in vendor
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof VendorDetails) {
            VendorDetails vendorDetails = (VendorDetails) principal;
            coupon.setVendor(vendorDetails.getVendor()); // Set vendor from VendorDetails
        } else {
            // Handle the case where principal is not of the expected type
            throw new IllegalStateException("Expected VendorDetails but got " + principal.getClass().getName());
        }

        couponRepository.save(coupon);
        return "redirect:/coupons";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    // Edit a coupon (form page)
    @GetMapping("/coupons/edit/{id}")
    public String editCoupon(@PathVariable("id") Integer id, Model model) throws AccessDeniedException {
        Coupon coupon = couponRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid coupon Id:" + id));

        // Get the logged-in vendor's ID
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof VendorDetails) {
            Vendor loggedInVendor = ((VendorDetails) principal).getVendor();
            // Check if the logged-in vendor is allowed to edit this coupon
            if (!coupon.getVendor().getVendorID().equals(loggedInVendor.getVendorID())) {
                throw new AccessDeniedException("You are not authorized to edit this coupon.");
            }
        }

        List<Vendor> venList = vendorRepository.findAll();
        model.addAttribute("venList", venList);
        model.addAttribute("coupon", coupon);
        return "edit_coupon";
    }


    // Save the edited coupon
    @PostMapping("/coupons/edit/{id}")
    public String saveUpdatedCoupon(@PathVariable("id") Integer id, @Valid Coupon coupon, BindingResult result, Model model) throws AccessDeniedException {
        if (result.hasErrors()) {
            List<Vendor> venList = vendorRepository.findAll();
            model.addAttribute("venList", venList);
            model.addAttribute("coupon", coupon);
            return "edit_coupon";
        }

        Coupon existingCoupon = couponRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid coupon Id:" + id));

        // Get the logged-in vendor's ID
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof VendorDetails) {
            Vendor loggedInVendor = ((VendorDetails) principal).getVendor();
            // Check if the logged-in vendor is allowed to edit this coupon
            if (!existingCoupon.getVendor().getVendorID().equals(loggedInVendor.getVendorID())) {
                throw new AccessDeniedException("You are not authorized to edit this coupon.");
            }
        }

        existingCoupon.setVendor(coupon.getVendor());
        existingCoupon.setQuantity(coupon.getQuantity());
        existingCoupon.setExpiryDate(coupon.getExpiryDate());
        existingCoupon.setPublicCoupon(coupon.isPublicCoupon());
        existingCoupon.setPublicQuantity(coupon.getPublicQuantity());

        couponRepository.save(existingCoupon);
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
    public String deleteCoupon(@PathVariable("id") Integer id) throws AccessDeniedException {
        Coupon coupon = couponRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid coupon Id:" + id));

        // Get the logged-in vendor's ID
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof VendorDetails) {
            Vendor loggedInVendor = ((VendorDetails) principal).getVendor();
            // Check if the logged-in vendor is allowed to delete this coupon
            if (!coupon.getVendor().getVendorID().equals(loggedInVendor.getVendorID())) {
                throw new AccessDeniedException("You are not authorized to delete this coupon.");
            }
        }

        couponRepository.deleteById(id);
        return "redirect:/coupons";
    }

    @PostMapping("/makePublic")
    public String makeCouponPublic(@RequestParam("id") int id, @RequestParam("quantity") int quantity, RedirectAttributes redirectAttributes) {
        Coupon coupon = couponRepository.findById(id).orElse(null);
        
        Object loggedInUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (loggedInUser instanceof IssuerDetails) {
            IssuerDetails loggedInIssuer = (IssuerDetails) loggedInUser;
            Issuer issuer = loggedInIssuer.getIssuer();
            
            if (coupon != null && quantity > 0) {
                if (coupon.getQuantity() >= quantity) {
                    coupon.setIssuer(issuer); // Corrected this line to set the actual Issuer object
                    coupon.setPublicCoupon(true);
                    coupon.setQuantity(coupon.getQuantity() - quantity);
                    coupon.setPublicQuantity(coupon.getPublicQuantity() + quantity);

                    couponRepository.save(coupon);
                    return "redirect:/publicCoupons";
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Insufficient quantity of coupons.");
                }
            }
        }
        return "redirect:/coupons"; // Redirect to the coupon list page
    }

    // Handler for viewing public coupons

    @GetMapping("/publicCoupons")
    public String viewPublicCoupons(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 10; // Define the size of the page
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Coupon> couponPage = couponService.findPublicCoupons(pageRequest);

        model.addAttribute("publicCoupons", couponPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", couponPage.getTotalPages());

        return "publicCoupons";
    }
}
