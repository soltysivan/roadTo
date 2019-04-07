package org.euro.controller;


import org.euro.dao.dto.CaptchaResponseDto;
import org.euro.dao.entity.User;
import org.euro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Controller
public class UserRegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    public UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String getUserRegistrationPage(){
        return "registration";
    }

    @PostMapping("/registration")
    public String saveUser(@RequestParam("password2") String passwordConfirm,
                           @RequestParam String password,
                           @RequestParam("g-recaptcha-response") String captchaResponce,
                           @Valid User user,
                           BindingResult bindingResult,
                           Model model) throws IOException {
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isConfirm =StringUtils.isEmpty(passwordConfirm);
        boolean isConfirm2 =StringUtils.isEmpty(password);
        if (isConfirm || bindingResult.hasErrors() || !response.isSuccess()){
            Map<String, String> errors = UtilsController.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (isConfirm2){
            model.addAttribute("passwordError", "Пароль не може бути пустим");
            return "registration";
        }
        if (isConfirm){
            model.addAttribute("password2Error", "Підтвердження паролю не може бути пустим");
            return "registration";
        }
        if (user.getPassword()!= null && !user.getPassword().equals(passwordConfirm)){
            model.addAttribute("password2Error", "Паролі не рівні");
            return "registration";
        }
        if (!userService.addUser(user)){
            model.addAttribute("usernameError","Користувач з таким іменем існує!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/acti/{code}")
    public String activation (Model model, @PathVariable String code){
        boolean isActive = userService.activateUser(code);

        if(isActive){
            model.addAttribute("ac", "Активація успішна");
        }else {
            model.addAttribute("ac", "Код активаціїї незнайдено");
        }
        return "/login";
    }

}
