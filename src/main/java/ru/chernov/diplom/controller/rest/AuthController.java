package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.diplom.component.FormChecker;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.page.Error;
import ru.chernov.diplom.service.UserService;

import java.util.HashMap;


/**
 * @author Pavel Chernov
 */
@Controller
public class AuthController {

    private final UserService userService;
    private final FormChecker formChecker;

    @Autowired
    public AuthController(UserService userService, FormChecker formChecker) {
        this.userService = userService;
        this.formChecker = formChecker;
    }

    @GetMapping("/login")
    public String loginPage(@AuthenticationPrincipal User authUser,
                            Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var frontendData = new HashMap<String, Object>();
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@AuthenticationPrincipal User authUser,
                                   Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var frontendData = new HashMap<String, Object>();
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String username,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String middleName,
                               @RequestParam int passportId,
                               @RequestParam int passportSeries,
                               @RequestParam String password,
                               @RequestParam String passwordConfirm) {
        Error error = formChecker.checkRegistrationData(username, password, passwordConfirm);
        if (error == null)
            userService.registration(username, name, surname, middleName, passportId, passportSeries, password);
        return "redirect:/";
    }
}
