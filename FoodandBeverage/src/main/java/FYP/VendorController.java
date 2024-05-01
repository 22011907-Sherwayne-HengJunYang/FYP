package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    // View all vendors
    @GetMapping("/vendors")
    public String viewVendors(Model model) {
        List<Vendor> listVendors = vendorRepository.findAll();
        model.addAttribute("listVendors", listVendors);
        return "view_vendors";
    }

    // Add a new vendor (form page)
    @GetMapping("/vendors/add")
    public String addVendor(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "add_vendor";
    }

    // Save a new vendor
    @PostMapping("/vendors/save")
    public ResponseEntity<Vendor> saveVendor(@RequestBody Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
    }

    // Edit an existing vendor (form page)
    @GetMapping("/vendors/edit/{id}")
    public String editVendor(@PathVariable int id, Model model) {
        Vendor vendor = vendorRepository.findById(id).orElse(null);
        model.addAttribute("vendor", vendor);
        return "edit_vendor";
    }

    // Save updated vendor
    @PostMapping("/vendors/edit/{id}")
    public ResponseEntity<Vendor> saveUpdatedVendor(@PathVariable int id, @RequestBody Vendor updatedVendor) {
        if (vendorRepository.existsById(id)) {
            // Update the existing vendor with the new data
            updatedVendor.setVendorID(id);
            Vendor savedVendor = vendorRepository.save(updatedVendor);
            return new ResponseEntity<>(savedVendor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a vendor
    @GetMapping("/vendors/delete/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable int id) {
        if (vendorRepository.existsById(id)) {
            vendorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // View single vendor
    @GetMapping("/vendors/{id}")
    public String viewSingleVendor(@PathVariable int id, Model model) {
        Vendor vendor = vendorRepository.findById(id).orElse(null);
        model.addAttribute("vendor", vendor);
        return "view_single_vendor";
    }
}
