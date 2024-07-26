package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {
	@Autowired
	private AnnouncementService announcementService;

    @GetMapping("/403")
    public String error403() {
        return "403";
    }
    
    @GetMapping("/graph")
    public String graph() {
        return "graph";
    }
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("announcementText", announcementService.getLatestAnnouncement());
        return "index"; 
    }
}
