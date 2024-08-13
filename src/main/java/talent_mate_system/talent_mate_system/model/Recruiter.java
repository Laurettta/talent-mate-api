package talent_mate_system.talent_mate_system.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recruiter extends User{
    private String companyName;
    private String jobOpenings;
    private String requirements;
}
