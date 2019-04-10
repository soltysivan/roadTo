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
import java.util.Random;

@Controller
public class UserRegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    public UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/registration")
    public String saveUser(@RequestParam("password2") String passwordConfirm,
                           @RequestParam String password,
                           @RequestParam String username,
                           @Valid User user,
                           BindingResult bindingResult,
                           Model model) throws IOException {
        boolean isConfirm =StringUtils.isEmpty(passwordConfirm);
        boolean isConfirm2 =StringUtils.isEmpty(password);
        if (isConfirm || bindingResult.hasErrors()){
            Map<String, String> errors = UtilsController.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("username", username);
            return "registration";
        }
        if (isConfirm2){
            model.addAttribute("passwordError", "Пароль не може бути пустим");
            model.addAttribute("username", username);
            return "registration";
        }
        if (isConfirm){
            model.addAttribute("password2Error", "Підтвердження паролю не може бути пустим");
            model.addAttribute("username", username);
            return "registration";
        }
        if (user.getPassword()!= null && !user.getPassword().equals(passwordConfirm)){
            model.addAttribute("password2Error", "Паролі не рівні");
            model.addAttribute("username", username);
            return "registration";
        }
        userService.addUser(user);
        userService.loadUserByUsername(username);
        return "redirect:/";
    }

    @GetMapping("registration/tel")
    public String getRegistrationTelPage (){
        return "registrationTel";
    }

    @PostMapping("registration/tel")
    public String sendSmS(@RequestParam String username,
                          @RequestParam("g-recaptcha-response") String captchaResponce,
                          Model model){
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (!userService.findByUsername(username)){
            model.addAttribute("usernameError","Користувач з таким телефон вже існує!");
            return "registrationTel";
        }
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Підтвердіть що ви не робот");
        }
        model.addAttribute("username", username);
        return "registration";
    }
    @PostMapping("/acti/again")
    public String newCode(@RequestParam String username, Model model){
        Random random = new Random();
        int kode = random.nextInt(9999);
        String kodeString = String.valueOf(kode);
        userService.sendSMSToUser(username, kodeString);
        model.addAttribute("kode", kodeString);
        model.addAttribute("username", username);
        return "registrationTelActivate";
    }
    @PostMapping("/regisrtation/tel/acti")
    public String activationCode(@RequestParam String username,
                                 @RequestParam String kode,
                                 @RequestParam String kodeInput,
                                 Model model){

        if (!kodeInput.equals(kode)){
            model.addAttribute("kode", kode);
            model.addAttribute("username", username);
            model.addAttribute("kodeInputError", "Невірий код");
            return "registrationTelActivate";
        }
        model.addAttribute("username", username);
        return "registration";
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
