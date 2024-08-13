package talent_mate_system.talent_mate_system.dto;

import lombok.Data;
import talent_mate_system.talent_mate_system.enums.ProfileCategory;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ProfileCategory profileCategory;
    private String workExperience; // For Job Seekers
    private String skills;          // For Job Seekers
    private String education;       // For Job Seekers
    private String companyName;     // For Recruiters
    private String jobOpenings;     // For Recruiters
    private String requirements;    // For Recruiters
}
