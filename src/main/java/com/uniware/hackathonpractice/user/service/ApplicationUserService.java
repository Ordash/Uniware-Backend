package com.uniware.hackathonpractice.user.service;

import com.uniware.hackathonpractice.security.model.UserContext;
import com.uniware.hackathonpractice.user.exceptions.EmailNotValidException;
import com.uniware.hackathonpractice.user.exceptions.UserNameIsTakenException;
import com.uniware.hackathonpractice.user.exceptions.UserRoleNotFoundException;
import com.uniware.hackathonpractice.user.persistence.model.ApplicationUser;
import com.uniware.hackathonpractice.user.persistence.model.ApplicationUserRole;
import com.uniware.hackathonpractice.user.persistence.repository.ApplicationUserRepository;
import com.uniware.hackathonpractice.user.util.ApplicationUserDTO;
import com.uniware.hackathonpractice.user.util.Role;
import com.uniware.hackathonpractice.user.web.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder encoder;
    private RoleService roleService;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  PasswordEncoder encoder, RoleService roleService) {
        this.applicationUserRepository = applicationUserRepository;
        this.encoder = encoder;
        this.roleService = roleService;
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
            throws UserNameIsTakenException, UserRoleNotFoundException, EmailNotValidException {

        if (applicationUserDTO.getEmail().matches("^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            if (!existsByUsername(applicationUserDTO.getUsername())) {

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

                applicationUserRepository.save(applicationUser);

                return new RegisterResponse(applicationUser.getId(),
                        applicationUser.getUsername());
            }
            throw new UserNameIsTakenException();
        }
        throw new EmailNotValidException("Not a valid email");
    }

    private Boolean existsByUsername(String username) {
        return applicationUserRepository.existsByUsername(username);
    }
}
