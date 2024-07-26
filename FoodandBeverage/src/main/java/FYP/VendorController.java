package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class VendorController {
	@Autowired
    private VendorRepository vendorRepository;

	@Autowired
    private VendorService vendorService;

    @GetMapping("/vendors")
    public String viewVendors(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 10; // Define the size of the page
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Vendor> vendorPage = vendorService.findVendors(pageRequest);

        model.addAttribute("listVendors", vendorPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vendorPage.getTotalPages());

        return "view_vendors";
    }

    // Add new vendor
    @GetMapping("/vendors/add")
    public String showAddVendorForm(Model model) {
        // Create a new Vendor object to bind the form data
        model.addAttribute("vendor", new Vendor());
        return "add_vendor";
    }

    @PostMapping("/vendors/save")
    public String saveVendor(Vendor vendor, RedirectAttributes redirectAttribute) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(vendor.getPassword());
	

		vendor.setPassword(encodedPassword);
		vendor.setRole("ROLE_Vendor");
    	// Save the new vendor to the database
        vendorRepository.save(vendor);
        
        // Redirect to the vendors page to see the updated list
        
        return "redirect:/vendors";
    }

    // Edit existing vendor
    @GetMapping("/vendors/edit/{id}")
    public String editVendor(@PathVariable("id") Integer id, Model model) {
        // Retrieve the vendor by ID
        Vendor vendor = vendorRepository.getReferenceById(id);
        
        // Add the vendor to the model
        model.addAttribute("vendor", vendor);
        
        // Return the view to edit the vendor
        return "edit_vendor";
    }

    @PostMapping("/vendors/edit/{id}")
    public String saveUpdatedVendor(@PathVariable("id") Integer id, Vendor vendor) {
        // Save the updated vendor to the database
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(vendor.getPassword());

		vendor.setPassword(encodedPassword);
		vendor.setRole("Vendor");
		
        vendorRepository.save(vendor);
        
        // Redirect to the vendors page to see the updated list
        return "redirect:/vendors";
    }

    // Delete vendor
    @GetMapping("/vendors/delete/{id}")
    public String deleteVendor(@PathVariable("id") Integer id) {
        // Delete the vendor by ID
        vendorRepository.deleteById(id);
        
        // Redirect to the vendors page to see the updated list
        return "redirect:/vendors";
    }
    
    
}
