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
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admins")
    public String viewAdmins(Model model) {
        // Retrieve all admins from the database
        List<Admin> listAdmins = adminRepository.findAll();

        // Add the list of admins to the model
        model.addAttribute("listAdmins", listAdmins);
        
        // Return the view to display the list of admins
        return "view_admins";
    }

    // Add new admin
    @GetMapping("/admins/add")
    public String showAddAdminForm(Model model) {
        // Create a new Admin object to bind the form data
        model.addAttribute("admin", new Admin());
        return "add_admin";
    }

    @PostMapping("/admins/save")
    public String saveAdmin(Admin admin, RedirectAttributes redirectAttribute) {
        // Encrypt the password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        
        // Set role for admin
        admin.setRole("ROLE_Admin");

        // Save the new admin to the database
        adminRepository.save(admin);
        
        // Redirect to the admins page to see the updated list
        return "redirect:/admins";
    }

    // Edit existing admin
    @GetMapping("/admins/edit/{id}")
    public String editAdmin(@PathVariable("id") Integer id, Model model) {
        // Retrieve the admin by ID
        Admin admin = adminRepository.getReferenceById(id);
        
        // Add the admin to the model
        model.addAttribute("admin", admin);
        
        // Return the view to edit the admin
        return "edit_admin";
    }

    @PostMapping("/admins/edit/{id}")
    public String saveUpdatedAdmin(@PathVariable("id") Integer id, Admin admin) {
        // Encrypt the password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        
        // Set role for admin
        admin.setRole("Admin");

        // Save the updated admin to the database
        adminRepository.save(admin);
        
        // Redirect to the admins page to see the updated list
        return "redirect:/admins";
    }

    // Delete admin
    @GetMapping("/admins/delete/{id}")
    public String deleteAdmin(@PathVariable("id") Integer id) {
        // Delete the admin by ID
        adminRepository.deleteById(id);
        
        // Redirect to the admins page to see the updated list
        return "redirect:/admins";
    }
}
