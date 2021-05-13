package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.chernov.diplom.component.FormChecker;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.page.error.AuthError;
import ru.chernov.diplom.page.notification.AuthNotification;
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
                            @RequestParam(required = false) String notification,
                            @RequestParam(required = false) String error,
                            Model model) {

        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var frontendData = new HashMap<String, Object>();
        // specific Spring login error
        frontendData.put("error", (error != null && error.isEmpty()) ? AuthError.WRONG_CREDENTIALS.toString() : error);
        frontendData.put("notification", notification);
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@AuthenticationPrincipal User authUser,
                                   @RequestParam(required = false) String notification,
                                   @RequestParam(required = false) String error,
                                   @RequestParam(required = false) String username,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String surname,
                                   @RequestParam(required = false) String middleName,
                                   @RequestParam(required = false) String passportId,
                                   @RequestParam(required = false) String passportSeries,
                                   Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var frontendData = new HashMap<String, Object>();
        var formData = new HashMap<>() {{
            put("username", username);
            put("name", name);
            put("surname", surname);
            put("middleName", middleName);
            put("passportId", passportId);
            put("passportSeries", passportSeries);
        }};
        frontendData.put("formData", formData);
        frontendData.put("authUser", authUser);
        frontendData.put("error", error);
        frontendData.put("notification", notification);
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
                               @RequestParam String passwordConfirm,
                               RedirectAttributes ra) {
        var error = formChecker.checkRegistrationData(username, password, passwordConfirm);

        if (error != null) {
            ra.addAttribute("error", error);
            ra.addAttribute("username", username);
            ra.addAttribute("name", name);
            ra.addAttribute("surname", surname);
            ra.addAttribute("middleName", middleName);
            ra.addAttribute("passportId", passportId);
            ra.addAttribute("passportSeries", passportSeries);
            return "redirect:/registration";
        }

        userService.registration(username, name, surname, middleName, passportId, passportSeries, password);
        ra.addAttribute("notification", AuthNotification.REGISTRATION_SUCCESSFUL.toString());
        return "redirect:/login";
    }
}
