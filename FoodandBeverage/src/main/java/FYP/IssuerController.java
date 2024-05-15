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
public class IssuerController {

    @Autowired
    private IssuerRepository issuerRepository;

    @GetMapping("/issuers")
    public String viewIssuers(Model model) {
        // Retrieve all issuers from the database
        List<Issuer> listIssuers = issuerRepository.findAll();

        /// Add the list of issuers to the model
        model.addAttribute("listIssuers", listIssuers);
        
        // Return the view to display the list of issuers
        return "view_issuers";
    }

    // Add new issuer
    @GetMapping("/issuers/add")
    public String showAddIssuerForm(Model model) {
        // Create a new Issuer object to bind the form data
        model.addAttribute("issuer", new Issuer());
        return "add_issuer";
    }

    @PostMapping("/issuers/save")
    public String saveIssuer(Issuer issuer, RedirectAttributes redirectAttribute) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(issuer.getPassword());

		issuer.setPassword(encodedPassword);
		issuer.setRole("Issuer");
    	// Save the new issuer to the database
        issuerRepository.save(issuer);
        
        // Redirect to the issuers page to see the updated list
        return "redirect:/issuers";
    }

    // Edit existing issuer
    @GetMapping("/issuers/edit/{id}")
    public String editIssuer(@PathVariable("id") Integer id, Model model) {
        // Retrieve the issuer by ID
        Issuer issuer = issuerRepository.getReferenceById(id);
        
        // Add the issuer to the model
        model.addAttribute("issuer", issuer);
        
        // Return the view to edit the issuer
        return "edit_issuer";
    }

    @PostMapping("/issuers/edit/{id}")
    public String saveUpdatedIssuer(@PathVariable("id") Integer id, Issuer issuer) {
        // Save the updated issuer to the database
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(issuer.getPassword());

		issuer.setPassword(encodedPassword);
		issuer.setRole("Issuer");
		
        issuerRepository.save(issuer);
        
        // Redirect to the issuers page to see the updated list
        return "redirect:/issuers";
    }

    // Delete issuer
    @GetMapping("/issuers/delete/{id}")
    public String deleteIssuer(@PathVariable("id") Integer id) {
        // Delete the issuer by ID
        issuerRepository.deleteById(id);
        
        // Redirect to the issuers page to see the updated list
        return "redirect:/issuers";
    }
    
    
}
