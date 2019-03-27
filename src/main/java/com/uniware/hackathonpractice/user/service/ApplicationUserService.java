package com.uniware.hackathonpractice.user.service;

import com.uniware.hackathonpractice.security.model.UserContext;
import com.uniware.hackathonpractice.user.exceptions.EmailNotValidException;
import com.uniware.hackathonpractice.user.exceptions.UserNameOrEmailIsTakenException;
import com.uniware.hackathonpractice.user.exceptions.UserRoleNotFoundException;
import com.uniware.hackathonpractice.user.persistence.model.ApplicationUser;
import com.uniware.hackathonpractice.user.persistence.model.ApplicationUserRole;
import com.uniware.hackathonpractice.user.persistence.model.ConfirmationToken;
import com.uniware.hackathonpractice.user.persistence.repository.ApplicationUserRepository;
import com.uniware.hackathonpractice.user.persistence.repository.ConfirmationTokenRepository;
import com.uniware.hackathonpractice.user.util.ApplicationUserDTO;
import com.uniware.hackathonpractice.user.util.Role;
import com.uniware.hackathonpractice.user.util.UserListDTO;
import com.uniware.hackathonpractice.user.web.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder encoder;
    private RoleService roleService;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  PasswordEncoder encoder, RoleService roleService, ConfirmationTokenRepository confirmationTokenRepository, EmailSenderService emailSenderService) {
        this.applicationUserRepository = applicationUserRepository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
    }

    public UserContext createUserContext(String username) {
        ApplicationUser applicationUser = findByUserName(username);

        if (applicationUser.getRoles() == null)
            throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = applicationUser.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleEnum().authority()))
                .collect(Collectors.toList());

        return UserContext.create(applicationUser.getUsername(), authorities);
    }

    public ApplicationUser findByUserName(String username) throws UsernameNotFoundException {
        return applicationUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public RegisterResponse registerApplicationUser(ApplicationUserDTO applicationUserDTO)
            throws UserNameOrEmailIsTakenException, UserRoleNotFoundException, EmailNotValidException {

        if (applicationUserDTO.getEmail().matches("^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            if (!existsByUsername(applicationUserDTO.getUsername()) && !existsByEmail(applicationUserDTO.getEmail())) {

                final ApplicationUser applicationUser = new ApplicationUser();
                //TODO this is used only for development purpose
                ApplicationUserRole applicationUserRole = new ApplicationUserRole(1L, Role.USER);
                roleService.saveRole(applicationUserRole);
                List<ApplicationUserRole> userRoles = new ArrayList<>();
                userRoles.add(roleService.findById(1L));

                applicationUser.setUsername(applicationUserDTO.getUsername());
                applicationUser.setPassword(encoder.encode(applicationUserDTO.getPassword()));
                applicationUser.setEmail(applicationUserDTO.getEmail());
                applicationUser.setRoles(userRoles);

                ConfirmationToken confirmationToken = new ConfirmationToken(applicationUser);
                applicationUser.setConfirmationToken(confirmationToken);
                applicationUserRepository.save(applicationUser);
                confirmationTokenRepository.save(confirmationToken);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(applicationUserDTO.getEmail());
                mailMessage.setSubject("Complete Registration!");
                mailMessage.setFrom("mailappforhackathon@gmail.com");
                mailMessage.setText("To confirm your account, please click here : "
                        +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

                emailSenderService.sendEmail(mailMessage);
                return new RegisterResponse(applicationUser.getId(),
                        applicationUser.getUsername(), "Please verify your email address");
            }
            throw new UserNameOrEmailIsTakenException();
        }
        throw new EmailNotValidException("Not a valid email");
    }

    public String verifyEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        RegisterResponse registerResponse = new RegisterResponse();
        if (token != null) {
            ApplicationUser user = applicationUserRepository.findByEmailIgnoreCase(token.getApplicationUser().getEmail());
            user.setEnabled(true);
            applicationUserRepository.save(user);
            registerResponse.setVerification("Email verified");
        } else {
            registerResponse.setVerification("Verification failed");
        }
        return registerResponse.getVerification();
    }

    private Boolean existsByUsername(String username) {
        return applicationUserRepository.existsByUsername(username);
    }

    private Boolean existsByEmail(String email) {
        return applicationUserRepository.existsByEmail(email);
    }

    public UserListDTO listUsers() {
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setUsers(applicationUserRepository.findAll().stream().map(ApplicationUser::getUsername).collect(Collectors.toList()));
        return userListDTO;
    }


}
