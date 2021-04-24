package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.diplom.component.FormChecker;
import ru.chernov.diplom.page.Error;
import ru.chernov.diplom.service.UserService;


/**
 * @author Pavel Chernov
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final FormChecker formChecker;

    @Autowired
    public AuthController(UserService userService, FormChecker formChecker) {
        this.userService = userService;
        this.formChecker = formChecker;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
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
        return "redirect:/main";
    }
}
