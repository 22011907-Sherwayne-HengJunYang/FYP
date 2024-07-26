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
    public String viewVendors(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Vendor> vendorPage;

        if (keyword != null && !keyword.isEmpty()) {
            vendorPage = vendorService.searchVendors(keyword, pageRequest);
            model.addAttribute("keyword", keyword);
        } else {
            vendorPage = vendorService.findVendors(pageRequest);
        }

        model.addAttribute("listVendors", vendorPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vendorPage.getTotalPages());

        return "view_vendors";
    }

    // Add new vendor
    @GetMapping("/vendors/add")
    public String showAddVendorForm(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "add_vendor";
    }

    @PostMapping("/vendors/save")
    public String saveVendor(Vendor vendor, RedirectAttributes redirectAttribute) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(vendor.getPassword());

        vendor.setPassword(encodedPassword);
        vendor.setRole("ROLE_Vendor");
        vendorRepository.save(vendor);
        return "redirect:/vendors";
    }

    // Edit existing vendor
    @GetMapping("/vendors/edit/{id}")
    public String editVendor(@PathVariable("id") Integer id, Model model) {
        Vendor vendor = vendorRepository.getReferenceById(id);
        model.addAttribute("vendor", vendor);
        return "edit_vendor";
    }

    @PostMapping("/vendors/edit/{id}")
    public String saveUpdatedVendor(@PathVariable("id") Integer id, Vendor vendor) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(vendor.getPassword());
        vendor.setPassword(encodedPassword);
        vendor.setRole("Vendor");
        vendorRepository.save(vendor);
        return "redirect:/vendors";
    }

    // Delete vendor
    @GetMapping("/vendors/delete/{id}")
    public String deleteVendor(@PathVariable("id") Integer id) {
        vendorRepository.deleteById(id);
        return "redirect:/vendors";
    }
}
