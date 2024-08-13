package talent_mate_system.talent_mate_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import talent_mate_system.talent_mate_system.dto.SignupRequest;
import talent_mate_system.talent_mate_system.enums.ProfileCategory;
import talent_mate_system.talent_mate_system.exception.EmailAlreadyInUseException;
import talent_mate_system.talent_mate_system.exception.EmailSendingException;
import talent_mate_system.talent_mate_system.exception.InvalidCredentialsException;
import talent_mate_system.talent_mate_system.model.JobSeeker;
import talent_mate_system.talent_mate_system.model.Recruiter;
import talent_mate_system.talent_mate_system.model.User;
import talent_mate_system.talent_mate_system.repository.UserRepository;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    public void registerJobSeeker(SignupRequest signupRequest, MultipartFile resume)
            throws EmailAlreadyInUseException, EmailSendingException {

        // Check if email is already in use
        if (userRepository.findByEmail(signupRequest.getEmail()) != null) {
            logger.warn("Attempt to register with an email already in use: {}", signupRequest.getEmail());
            throw new EmailAlreadyInUseException("Email is already in use!");
        }

        // Parse resume data and set profile details
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setWorkExperience(parseResumeForExperience(resume));
        jobSeeker.setSkills(parseResumeForSkills(resume));
        jobSeeker.setEducation(parseResumeForEducation(resume));

        // Set other profile details
        jobSeeker.setFirstName(signupRequest.getFirstName());
        jobSeeker.setLastName(signupRequest.getLastName());
        jobSeeker.setEmail(signupRequest.getEmail());
        jobSeeker.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        jobSeeker.setProfileCategory(ProfileCategory.JOB_SEEKER);
        jobSeeker.setConfirmed(false); // Email confirmation pending

        // Save job seeker to the repository
        userRepository.save(jobSeeker);

        // Send confirmation email
        boolean emailSent = emailService.sendConfirmationEmail(jobSeeker);
        if (!emailSent) {
            logger.error("Failed to send confirmation email to: {}", jobSeeker.getEmail());
            throw new EmailSendingException("Failed to send confirmation email.");
        }
    }

    public void registerRecruiter(SignupRequest signupRequest)
            throws EmailAlreadyInUseException, EmailSendingException {

        if (userRepository.findByEmail(signupRequest.getEmail()) != null) {
            logger.warn("Attempt to register with an email already in use: {}", signupRequest.getEmail());
            throw new EmailAlreadyInUseException("Email is already in use!");
        }

        Recruiter recruiter = new Recruiter();
        recruiter.setCompanyName(signupRequest.getCompanyName());
        recruiter.setJobOpenings(signupRequest.getJobOpenings());
        recruiter.setRequirements(signupRequest.getRequirements());

        recruiter.setFirstName(signupRequest.getFirstName());
        recruiter.setLastName(signupRequest.getLastName());
        recruiter.setEmail(signupRequest.getEmail());
        recruiter.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        recruiter.setProfileCategory(ProfileCategory.RECRUITER);
        recruiter.setConfirmed(false);

        userRepository.save(recruiter);

        boolean emailSent = emailService.sendConfirmationEmail(recruiter);
        if (!emailSent) {
            logger.error("Failed to send confirmation email to: {}", recruiter.getEmail());
            throw new EmailSendingException("Failed to send confirmation email.");
        }
    }

    public User login(String email, String password) throws InvalidCredentialsException {
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("Invalid login attempt with email: {}", email);
            throw new InvalidCredentialsException("Invalid email or password!");
        }
        return user;
    }

    // Helper methods to parse resume data
    private String parseResumeForExperience(MultipartFile resume) {
        // Logic to parse resume and extract work experience
        return "Parsed Experience";
    }

    private String parseResumeForSkills(MultipartFile resume) {
        // Logic to parse resume and extract skills
        return "Parsed Skills";
    }

    private String parseResumeForEducation(MultipartFile resume) {
        // Logic to parse resume and extract education
        return "Parsed Education";
    }
}
