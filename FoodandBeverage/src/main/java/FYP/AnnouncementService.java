package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public String getLatestAnnouncement() {
        Announcement announcement = announcementRepository.findFirstByOrderByIdDesc();
        return (announcement != null) ? announcement.getText() : "";
    }

    public void saveAnnouncement(String text) {
        Announcement announcement = new Announcement();
        announcement.setText(text);
        announcementRepository.save(announcement);
    }
}
