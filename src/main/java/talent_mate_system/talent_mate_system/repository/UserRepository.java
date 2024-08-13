package talent_mate_system.talent_mate_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talent_mate_system.talent_mate_system.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
