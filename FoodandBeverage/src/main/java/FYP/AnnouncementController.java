package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/announcement/edit")
    public String editAnnouncementForm(Model model) {
        model.addAttribute("announcementText", announcementService.getLatestAnnouncement());
        return "edit_announcement";
    }

    @PostMapping("/announcement/edit")
    public String saveAnnouncement(@RequestParam String announcementText) {
        announcementService.saveAnnouncement(announcementText);
        return("index");
    }
}
