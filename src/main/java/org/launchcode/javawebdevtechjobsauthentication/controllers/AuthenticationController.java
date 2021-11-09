package org.launchcode.javawebdevtechjobsauthentication.controllers;

import org.launchcode.javawebdevtechjobsauthentication.models.User;
import org.launchcode.javawebdevtechjobsauthentication.models.data.UserRepository;
import org.launchcode.javawebdevtechjobsauthentication.models.dto.LoginDTO;
import org.launchcode.javawebdevtechjobsauthentication.models.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    private static final String userSessionKey = "user";

    @GetMapping("login")
    public String displayLogin(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute(new LoginDTO());
        return "login";
    }
    @PostMapping("login")
    public String processLogin(@ModelAttribute @Valid LoginDTO loginDTO, Errors errors, HttpServletRequest request, Model model){

        if(errors.hasErrors()){
            model.addAttribute("title", "Login");
            return "login";
        }
        User theUser = userRepository.findByUsername(loginDTO.getUsername());
        if(theUser==null){
            errors.rejectValue("username", "user.invalid", "no such user");
            model.addAttribute("title", "Log In");
            return "login";
        }
        String password = loginDTO.getPassword();

        if(!theUser.checkPassword(password)){
            errors.rejectValue("password", "password.invalid", "Invalid Pasword");
            model.addAttribute("title", "Log In");
            return "login";
        }
        setUserInSession(request.getSession(), theUser);
        return "redirect:";


    }
    @GetMapping("logout")
    public String processLogout(HttpServletRequest request, Model model){
        request.getSession().invalidate();
        return "redirect:/login";
    }
    @GetMapping("register")
    public String displayRegister(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute(new RegisterDTO());
        return "register";
    }
    @PostMapping("register")
    public String processRegister(@ModelAttribute @Valid RegisterDTO registerDTO,
                                  Errors errors, HttpServletRequest request,
                                  Model model){
    if(errors.hasErrors()){
        model.addAttribute("title", "Register");
        return "register";
    }

    User existingUser = userRepository.findByUsername(registerDTO.getUsername());

    if(existingUser!=null){
        errors.rejectValue("username", "username.alreadyexists", "A user with that name already exists");
        model.addAttribute("title", "Register");
        return "register";
    }
    String password = registerDTO.getPassword();
    String verifyPassword = registerDTO.getVerifyPassword();
    if(!password.equals(verifyPassword)){
        errors.rejectValue("password", "passwords.mismatch", "passwords do not match");
        model.addAttribute("title", "Register");
        return "register";
    }
    User newUser = new User(registerDTO.getUsername(),registerDTO.getPassword());
    userRepository.save(newUser);
    setUserInSession(request.getSession(), newUser);

        return "redirect:";
    }
    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }
    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }
}
