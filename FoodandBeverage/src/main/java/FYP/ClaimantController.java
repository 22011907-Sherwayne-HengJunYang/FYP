package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClaimantController {

    @Autowired
    private ClaimantRepository claimantRepository;

    @GetMapping("/claimants")
    public String viewClaimants(Model model) {
        // Retrieve all claimants from the database
        List<Claimant> listClaimants = claimantRepository.findAll();

        // Add the list of claimants to the model
        model.addAttribute("listClaimants", listClaimants);
        
        // Return the view to display the list of claimants
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
        claimant.setRole("Claimant");

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
