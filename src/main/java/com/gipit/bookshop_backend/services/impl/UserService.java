package com.gipit.bookshop_backend.services.impl;

import com.gipit.bookshop_backend.dto.request.CreateUserRequest;
import com.gipit.bookshop_backend.dto.request.UpdateUserRequest;
import com.gipit.bookshop_backend.exception.AppException;
import com.gipit.bookshop_backend.exception.ErrorCode;
import com.gipit.bookshop_backend.models.Role;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.models.UserPrincipal;
import com.gipit.bookshop_backend.repositories.UserRepository;
import com.gipit.bookshop_backend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService,UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("User not found");

        return new UserPrincipal(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        if(userRepository.findByUsername(createUserRequest.getUsername())!=null)
            throw new AppException(ErrorCode.USER_EXISTS);
        boolean test=userRepository.existsByEmail(createUserRequest.getEmail());

        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        }
        Role list= roleService.findRoleById(3);
        String activationCode= UUID.randomUUID().toString();
        User user= new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setEmail(createUserRequest.getEmail());
        user.setListRoles(Arrays.asList(list));
        user.setActive(false);
        user.setProvider("LOCAL");
        user.setActivationCode(activationCode);
        User userCreate= userRepository.save(user);
        sendEmailVerification(user.getEmail(),activationCode);
        return userCreate;

    }

    @Override
    public void sendEmailVerification(String to, String activeCode) {
        String url="http://localhost:8080/api/users/active/"+activeCode;
        String subject="Kích hoạt tài khoản của bạn tại BookStore";
        String text= "Vui lòng sử dụng mã dưới để kích hoạt cho tài khoản <"+to+">: <html><body><h2 style:'display:inline-block;background:#c8bfbf;padding:0 5px'>"+activeCode+"</h2></body></html>";
        text+="<br/>Click vào đường link bên dưới để kích hoạt tài khoản";
        text+="<br/> <a href='"+url+"'>"+url+"</a>";
        mailService.sendMail("thohayeuthick@gmail.com",to,subject,text);
    }

    @Override
    public boolean activeAccount(String activeCode) {
        Optional<User> user=userRepository.findByActivationCode(activeCode);
        if(user.isPresent()){
            User userUpdate=user.get();
            userUpdate.setActive(true);
            userUpdate.setActivationCode(null);
            userRepository.save(userUpdate);
            return true;
        }
        return false;
    }

    @Override
    public User updateUser(int userID,UpdateUserRequest updateUserRequest) {
        User user= userRepository.findById(userID).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setGender(updateUserRequest.getGender());
        user.setPhone(updateUserRequest.getPhone());
        user.setDeliveryAddress(updateUserRequest.getDeliveryAddress());
        user.setPurchaseAddress(updateUserRequest.getPurchaseAddress());

        return userRepository.save(user);
    }


}
