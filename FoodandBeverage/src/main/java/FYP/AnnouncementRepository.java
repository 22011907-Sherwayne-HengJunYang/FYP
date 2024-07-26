package FYP;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Announcement findFirstByOrderByIdDesc(); // Get the latest announcement
}
