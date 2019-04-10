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
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    public SendSMS sendSMS;

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
            if (u.getId().equals(userDB.getId())){
                return false;
            }
        trip.getUsers().add(userDB);
        userDB.getTrips().add(trip);
        tripRepository.save(trip);
        userRepository.save(userDB);
        return true;
    }

    public void addUser(User user) throws IOException {
        user.setActive(true);
        user.setUsername(user.getUsername());
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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

    public void saveUser(Long userId, String username, Map<String, String> form) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        user.setUsername(username);
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

    public void updateProfile(User user1, String username,String firstLastName, MultipartFile file) throws IOException {
        User user = userRepository.findById(user1.getId()).orElseThrow(()->new RuntimeException("User not found"));
        String userFirstName = user.getFirstLastName();

        boolean isFirstNameChanged = (firstLastName != null && !firstLastName.equals(userFirstName) ||
                (userFirstName != null && !userFirstName.equals(firstLastName)));
        if (isFirstNameChanged){
            user.setFirstLastName(firstLastName);
        }
               boolean isEmailChanged = (username != null && !username.equals(username) ||
                (username != null && !username.equals(username)));
        if (isEmailChanged){
            user.setUsername(username);
            if(!StringUtils.isEmpty(username)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
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

    public void deleteUserFromTrip(Long userId, Long tripId) {
        User user = userRepository.findById(userId).orElse(null);
        List<Trip> trips = user.getTrips();
        trips.removeIf(trip -> trip.getId().equals(tripId));
        Trip trip = tripRepository.findById(tripId).orElse(null);
        List<User> users = trip.getUsers();
        users.removeIf(user1 -> user.getId().equals(userId));
        userRepository.save(user);
        tripRepository.save(trip);
    }

    public boolean sendSMSToUser(String username, String kode) {
        User userFromDb = userRepository.findByUsername(username);
        if (userFromDb != null){
            return false;
        }
        String sms = "Ваш код активації:" + kode;
        sendSMS.sendSMStoUser(username,sms);
        return true;
    }
}
