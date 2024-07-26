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
public class IssuerController {
	@Autowired
    private IssuerService issuerService;
 
    @Autowired
    private IssuerRepository issuerRepository;

    @GetMapping("/issuer")
    public String viewVendors(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 10; // Define the size of the page
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Issuer> issuerPage = issuerService.findIssuers(pageRequest);

        model.addAttribute("listIssuer", issuerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", issuerPage.getTotalPages());
        return "view_issuer";
    }

    // Add new issuer
    @GetMapping("/issuer/add")
    public String showAddIssuerForm(Model model) {
        // Create a new Issuer object to bind the form data
        model.addAttribute("issuer", new Issuer());
        return "add_issuer";
    }

    @PostMapping("/issuer/save")
    public String saveIssuer(Issuer issuer, RedirectAttributes redirectAttribute) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(issuer.getPassword());

		issuer.setPassword(encodedPassword);
		issuer.setRole("ROLE_Issuer");
    	// Save the new issuer to the database
        issuerRepository.save(issuer);
        
        // Redirect to the issuer page to see the updated list
        return "redirect:/issuer";
    }

    // Edit existing issuer
    @GetMapping("/issuer/edit/{id}")
    public String editIssuer(@PathVariable("id") Integer id, Model model) {
        // Retrieve the issuer by ID
        Issuer issuer = issuerRepository.getReferenceById(id);
        
        // Add the issuer to the model
        model.addAttribute("issuer", issuer);
        
        // Return the view to edit the issuer
        return "edit_issuer";
    }

    @PostMapping("/issuer/edit/{id}")
    public String saveUpdatedIssuer(@PathVariable("id") Integer id, Issuer issuer) {
        // Save the updated issuer to the database
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(issuer.getPassword());

		issuer.setPassword(encodedPassword);
		issuer.setRole("Issuer");
		
        issuerRepository.save(issuer);
        
        // Redirect to the issuer page to see the updated list
        return "redirect:/issuer";
    }

    // Delete issuer
    @GetMapping("/issuer/delete/{id}")
    public String deleteIssuer(@PathVariable("id") Integer id) {
        // Delete the issuer by ID
        issuerRepository.deleteById(id);
        
        // Redirect to the issuer page to see the updated list
        return "redirect:/issuer";
    }
    
    
}
