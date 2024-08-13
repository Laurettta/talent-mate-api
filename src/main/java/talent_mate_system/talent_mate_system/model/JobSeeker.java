package talent_mate_system.talent_mate_system.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobSeeker extends User{
    private String workExperience;
    private String skills;
    private String education;
}
