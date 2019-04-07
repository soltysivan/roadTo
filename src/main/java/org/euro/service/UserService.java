package org.euro.service;


import org.euro.dao.entity.*;
import org.euro.dao.repository.DialogRepository;
import org.euro.dao.repository.FileDBRepository;
import org.euro.dao.repository.TripRepository;
import org.euro.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    public DialogRepository dialogRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public TripRepository tripRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public FileDBRepository fileDBRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return  user;
    }

    public boolean acceptTrip(User user, Long id){
        Trip trip = tripRepository.findById(id).orElseThrow(()->new RuntimeException("Trip not found"));
        User userDB = userRepository.findById(user.getId()).orElseThrow(()->new RuntimeException("User not found"));
        List<User> users = trip.getUsers();

        for (User u: users)
        if (u.equals(userDB)){
            return false;
        }
        trip.getUsers().add(userDB);
        user.getTrips().add(trip);
        tripRepository.save(trip);
        userRepository.save(user);
        return true;
    }

    public boolean addUser(User user) throws IOException {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String message = String.format("Вітаю, %s! \n" +
                        "Дякуємо Вам за вашу довіру нашому сервісу," +
                        "комфортні перевезення з найдійними перевізниками. \n" +
                        "Будь-ласка відвідайте наступне посилання," +
                        " для повної активаціїї вашого акаунта " +
                        ": http://%s/acti/%s",
                user.getFirstName(), "longwayeuro.herokuapp.com",user.getActivationCode());


        return true;
    }


    public boolean activateUser(String code){
        User user = userRepository.findByActivationCode(code);

        if (user==null){
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }


    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public void saveUser(Long userId, String username, String email, String telephone, Map<String, String> form) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        user.setUsername(username);
        user.setEmail(email);
        user.setTelephone(telephone);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();

        for (String key: form.keySet()){
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }



    public List<User> finAllUserVSTrip() {
        List<User> usersTrip = userRepository.findAll();
        List<User> filter = new ArrayList<>();

        for (User user : usersTrip){
            if (user.getTrips().isEmpty()){
                filter.add(user);
            }
        }
        return filter;
    }

    public void updateProfile(User user1,String firstName, String lastName, String email, String telephone, MultipartFile file) throws IOException {
        User user = userRepository.findById(user1.getId()).orElseThrow(()->new RuntimeException("User not found"));
        String userEmail = user.getEmail();
        String userTelephone = user.getTelephone();
        String userFirstName = user.getFirstName();
        String userLastName = user.getLastName();

        boolean isFirstNameChanged = (firstName != null && !firstName.equals(userFirstName) ||
                (userFirstName != null && !userFirstName.equals(firstName)));
        if (isFirstNameChanged){
            user.setFirstName(firstName);
        }
        boolean isLastNameChanged = (lastName != null && !lastName.equals(userLastName) ||
                (userLastName != null && !userLastName.equals(lastName)));
        if (isLastNameChanged){
            user.setLastName(lastName);
        }
        boolean isEmailChanged = (email != null && !email.equals(userEmail) ||
                (userEmail != null && !userEmail.equals(email)));
        if (isEmailChanged){
            user.setEmail(email);
            if(!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        boolean isTelephoneChanged = (telephone != null && !telephone.equals(userTelephone) ||
                (userTelephone != null && !userTelephone.equals(telephone)));
        if (isTelephoneChanged){
            user.setTelephone(telephone);
        }
        if(!file.isEmpty()){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            FileDB fileDB = new FileDB(filename, file.getContentType(), file.getBytes());
            fileDBRepository.save(fileDB);
            if (user.getAvatar() != null) {
                fileDBRepository.deleteById(user.getAvatar());
            }
            user.setAvatar(fileDB.getId());
        }

        userRepository.save(user);
        if(isEmailChanged){
            String message = String.format("Вітаю, %s! \n" +
                            "Дякуємо Вам за вашу довіру нашому сервісу," +
                            "комфортні перевезення з найдійними перевізниками. \n" +
                            "Будь-ласка відвідайте наступне посилання," +
                            " для повної активаціїї вашого акаунта " +
                            ": http://%s/acti/%s",
                    user.getFirstName(), "longwayeuro.herokuapp.com",user.getActivationCode());
        }
    }


    public List<User> findAllAndSortByNewMessages(Long currentUserId) {
        List<User> users = userRepository.findAll(new Sort(Sort.Direction.DESC, "newMes")).stream()
                .filter(user -> !user.getId().equals(currentUserId))
                .collect(Collectors.toList());
        return users;
    }

    public List<User> findByDialogsAndSortByNewMessages(User user) {
        List<Dialog> dialogs = (List<Dialog>) dialogRepository.findAll();
        Set<User> userSet = new LinkedHashSet<>();
        for (Dialog dialog : dialogs){
            userSet.add(dialog.getAddress());
            userSet.add(dialog.getSender());
        }
        List<User> userList = userSet.stream().distinct().filter(user1 -> !user1.getId().equals(user.getId())).collect(Collectors.toList());
        userList.sort((o1, o2) -> Integer.compare(o2.getNewMes(),o1.getNewMes()));
        return userList;
    }

    public void updateUserPassword(String newPassword, User user) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
