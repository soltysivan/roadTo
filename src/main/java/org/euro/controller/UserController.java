package org.euro.controller;


import org.apache.tomcat.util.codec.binary.Base64;
import org.euro.dao.entity.FileDB;
import org.euro.dao.entity.Role;
import org.euro.dao.entity.Trip;
import org.euro.dao.entity.User;
import org.euro.service.FileDBService;
import org.euro.service.TripService;
import org.euro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    public FileDBService fileDBService;

    @Autowired
    public TripService tripService;

    @Autowired
    public UserService userService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list/user")
    public String showUserPage(@AuthenticationPrincipal User user,
                               Model model) {
        List<User> users = userService.findAll().stream().filter(u -> u.getId()!= user.getId()).collect(Collectors.toList());
        model.addAttribute("users", users);
        model.addAttribute("size", users.size());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{userId}")
    public String updateUserPage(@PathVariable("userId") Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("id", user.getId());
        model.addAttribute("avatar", user.getAvatar());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("telephone", user.getTelephone());
        model.addAttribute("roles", Role.values());
        return "updateUser";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit/save")
    public String saveUpdateUser(@RequestParam Long id,
                                 @RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam String telephone,
                                 @RequestParam Map<String, String> form) {
        userService.saveUser(id, username, email, telephone, form);
        return "redirect:/list/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/list/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/trip/adm")
    public String showAdminTripPage(Model model) {
        List<User> users = userService.finAllUserVSTrip();
        List<Trip> trips = tripService.findAllTripVSUser();
        model.addAttribute("users", users);
        model.addAttribute("trips", trips);
        return "adminTrip";
    }

    @PostMapping("/trip/accept/{id}")
    public String acceptTrip(@AuthenticationPrincipal User loginUser,
                                  @PathVariable("id") Long id,
                             Model model) {
        if (!userService.acceptTrip(loginUser, id)){
            model.addAttribute("mes", "Ви вже прийняли цю поїздку");
            model.addAttribute("trip", tripService.findById(id));
            return "detailsTrip";
        }else{
            return "redirect:/trip/list";
        }
    }
    @GetMapping("/trip/user/{id}")
    public String showTripUsers (@PathVariable ("id") Long id, Model model){
        Trip tripUser = tripService.findById(id);
        model.addAttribute("users", tripUser.getUsers() );
        return "AdminTripUsersList";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal User user) throws UnsupportedEncodingException {
        setAtributeToProfilePage(model, user);
        return "profile";
    }

    private void setAtributeToProfilePage(Model model, @AuthenticationPrincipal User user) throws UnsupportedEncodingException {
        User user1 = userService.findById(user.getId());
        model.addAttribute("username", user1.getUsername());
        model.addAttribute("firstName", user1.getFirstName());
        model.addAttribute("lastName", user1.getLastName());
        model.addAttribute("email", user1.getEmail());
        model.addAttribute("telephone", user1.getTelephone());
        model.addAttribute("password", user1.getPassword());
        if (user1.getAvatar()!= null) {
            FileDB fileDB = fileDBService.findById(user1.getAvatar());
            byte[] content = fileDB.getData();
            String base64Encoded = new String(Base64.encodeBase64(content), "UTF-8");
            model.addAttribute("avatar", base64Encoded);
        }
    }


    @GetMapping("/profile/update")
    public String showProfileUpdate(Model model, @AuthenticationPrincipal User user) throws UnsupportedEncodingException {
        setAtributeToProfilePage(model,user);
        return "profileUpdate";
    }

    @PostMapping("/profile/update")
    public String editProfile(@AuthenticationPrincipal User user1,
                              @RequestParam ("file")MultipartFile file,
                              @Valid User user,
                              BindingResult bindingResult,
                              Model model) throws IOException {
        if (bindingResult.hasErrors()){
        Map<String, String> errors = UtilsController.getErrors(bindingResult);
        model.mergeAttributes(errors);
        setAtributeToProfilePage(model, user1);
            return "profileUpdate";
        }
        userService.updateProfile(user1, user.getFirstName(), user.getLastName(), user.getEmail(), user.getTelephone(), file);
        return "redirect:/profile";
    }
    @GetMapping("/pass/update")
    public String getPasswordEditPage(@AuthenticationPrincipal User user1,
                                      Model model) throws UnsupportedEncodingException {
        User user = userService.findById(user1.getId());
        setAtributPasswordUpdate(model, user);
        return "passwordUpdate";
    }
    public boolean isConfirm(String value) {
        boolean isConf = StringUtils.isEmpty(value);
        return isConf;
    }
    public boolean checkPassword(String password1, String password2){
        return passwordEncoder.matches(password1, password2);
    }
    @PostMapping("/pass/update")
    public String setNewPassword(@AuthenticationPrincipal User user1,
                                 @RequestParam("password") String password,
                                 @RequestParam("password1") String newPassword,
                                 @RequestParam("password2") String passwordConfirm,
                                 Model model) throws UnsupportedEncodingException {
        User user = userService.findById(user1.getId());
        if (isConfirm(password)){
            model.addAttribute("passwordError", "Пароль не може бути пустим");
            setAtributPasswordUpdate(model, user);
            return "passwordUpdate";
        }
        if (password!=null && !checkPassword(password, user.getPassword())){
            model.addAttribute("passwordError", "Введено невірний пароль");
            setAtributeToProfilePage(model, user);
            return "passwordUpdate";
        }
        if (isConfirm(newPassword)){
            model.addAttribute("passwordError1", "Пароь не може бути пустим");
            setAtributPasswordUpdate(model, user);
            return "passwordUpdate";
        }
        if (isConfirm(passwordConfirm)){
            model.addAttribute("passwordError2", "Підтвердження паролю не може бути пустим");
            setAtributPasswordUpdate(model, user);
            return "passwordUpdate";
        }
        if (newPassword!= null && !newPassword.equals(passwordConfirm)){
            model.addAttribute("passwordError2", "Паролі не рівні");
            return "passwordUpdate";
        }
        userService.updateUserPassword(newPassword, user);
        return "redirect:/profile";
    }

    private void setAtributPasswordUpdate(Model model, User user) throws UnsupportedEncodingException {
        if (user.getAvatar() != null) {
            FileDB fileDB = fileDBService.findById(user.getAvatar());
            byte[] content = fileDB.getData();
            String base64Encoded = new String(Base64.encodeBase64(content), "UTF-8");
            model.addAttribute("avatar", base64Encoded);
        }
        model.addAttribute("user", user);
    }
}
