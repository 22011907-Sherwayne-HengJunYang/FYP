package FYP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignUpController {
  @Autowired
  private ClaimantRepository claimantRepository;
  
  @GetMapping("/signup")
  public String signupPage(Model model) {
    model.addAttribute("claimant", new Claimant());
      return "signup";
  }  

  @PostMapping("/signup/save")
  public String saveMember(Claimant claimant, RedirectAttributes redirectAttribute) {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(claimant.getPassword());

    claimant.setPassword(encodedPassword);
    claimant.setRole("ROLE_Claimant");

    claimantRepository.save(claimant);

    //

    return "redirect:/login";
  }
}