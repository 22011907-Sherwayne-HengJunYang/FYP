package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping("/vendors")
    public String viewVendors(Model model) {
        // Retrieve all vendors from the database
        List<Vendor> listVendors = vendorRepository.findAll();

        // Add the list of vendors to the model
        model.addAttribute("listVendors", listVendors);
        
        // Return the view to display the list of vendors
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
    public String saveVendor(Vendor vendor) {
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
