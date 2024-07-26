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
public class ClaimantController {

    @Autowired
    private ClaimantRepository claimantRepository;
    
    @Autowired
    private ClaimantService claimantService;
    
    @GetMapping("/claimants")
    public String viewVendors(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 10; // Define the size of the page
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Claimant> claimantPage = claimantService.findClaimants(pageRequest);

        model.addAttribute("listClaimants", claimantPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", claimantPage.getTotalPages());
        return "view_claimants";
    }

    // Add new claimant
    @GetMapping("/claimants/add")
    public String showAddClaimantForm(Model model) {
        // Create a new Claimant object to bind the form data
        model.addAttribute("claimant", new Claimant());
        return "add_claimant";
    }

    @PostMapping("/claimants/save")
    public String saveClaimant(Claimant claimant, RedirectAttributes redirectAttribute) {
        // Encrypt the password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(claimant.getPassword());
        claimant.setPassword(encodedPassword);
        
        // Set role for claimant
        claimant.setRole("ROLE_Claimant");

        // Save the new claimant to the database
        claimantRepository.save(claimant);
        
        // Redirect to the claimants page to see the updated list
        return "redirect:/claimants";
    }

    // Edit existing claimant
    @GetMapping("/claimants/edit/{id}")
    public String editClaimant(@PathVariable("id") Integer id, Model model) {
        // Retrieve the claimant by ID
        Claimant claimant = claimantRepository.getReferenceById(id);
        
        // Add the claimant to the model
        model.addAttribute("claimant", claimant);
        
        // Return the view to edit the claimant
        return "edit_claimant";
    }

    @PostMapping("/claimants/edit/{id}")
    public String saveUpdatedClaimant(@PathVariable("id") Integer id, Claimant claimant) {
        // Encrypt the password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(claimant.getPassword());
        claimant.setPassword(encodedPassword);
        
        // Set role for claimant
        claimant.setRole("Claimant");

        // Save the updated claimant to the database
        claimantRepository.save(claimant);
        
        // Redirect to the claimants page to see the updated list
        return "redirect:/claimants";
    }

    // Delete claimant
    @GetMapping("/claimants/delete/{id}")
    public String deleteClaimant(@PathVariable("id") Integer id) {
        // Delete the claimant by ID
        claimantRepository.deleteById(id);
        
        // Redirect to the claimants page to see the updated list
        return "redirect:/claimants";
    }
}
