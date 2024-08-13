package talent_mate_system.talent_mate_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import talent_mate_system.talent_mate_system.dto.SignupRequest;
import talent_mate_system.talent_mate_system.exception.EmailAlreadyInUseException;
import talent_mate_system.talent_mate_system.exception.EmailSendingException;
import talent_mate_system.talent_mate_system.exception.InvalidCredentialsException;
import talent_mate_system.talent_mate_system.model.User;
import talent_mate_system.talent_mate_system.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup/jobseeker")
    public ResponseEntity<String> signupJobSeeker(@RequestPart("signupRequest") SignupRequest signupRequest,
                                                  @RequestPart("resume") MultipartFile resume) {

        try {
            userService.registerJobSeeker(signupRequest, resume);
            return ResponseEntity.ok("Job Seeker registered successfully! Please check your email for confirmation.");
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmailSendingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send confirmation email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

    @PostMapping("/signup/recruiter")
    public ResponseEntity<String> signupRecruiter(@RequestBody SignupRequest signupRequest) {
        try {
            userService.registerRecruiter(signupRequest);
            return ResponseEntity.ok("Recruiter registered successfully! Please check your email for confirmation.");
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmailSendingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send confirmation email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.login(email, password);
            return ResponseEntity.ok(user);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
