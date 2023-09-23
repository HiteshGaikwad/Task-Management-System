package com.TMSystem.Service;

import com.TMSystem.DTO.Convertor;
import com.TMSystem.DTO.RequestUser;
import com.TMSystem.Entity.User;
import com.TMSystem.Enum.UserRole;
import com.TMSystem.Repository.UserRepository;
import com.TMSystem.DTO.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public ResponseUser getUser(String username){
        User user = userRepository.findByUsername(username);
        ResponseUser responseUser= Convertor.convertToResponseUser(user);
        return responseUser;
    }

    public User registerUser(RequestUser user){
        User existedUser= userRepository.findByUsername(user.getUsername());
        if(existedUser==null) {
            User newUser= Convertor.convertTouser(user);
            newUser.setRole(UserRole.USER);
            userRepository.save(newUser);
            return newUser;
        } else {
            return null;
        }
    }
    public User registerAdmin(RequestUser user){
        User existedUser= userRepository.findByUsername(user.getUsername());
        if(existedUser==null) {
            User newUser= Convertor.convertTouser(user);
            newUser.setRole(UserRole.ADMIN);
            userRepository.save(newUser);
            return newUser;
        } else {
            return null;
        }
    }

    public User updateUser(long id, RequestUser user){
        User existedUser= userRepository.findById(id);
        if(user.getUsername()!=null) {
            existedUser.setUsername(user.getUsername());
        }
        if(user.getPassword()!=null) {
            existedUser.setPassword(user.getPassword());
        }
        userRepository.save(existedUser);
        return existedUser;
    }
}


