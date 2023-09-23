package com.TMSystem.Controller;

import com.TMSystem.DTO.RequestUser;
import com.TMSystem.DTO.ResponseUser;
import com.TMSystem.Enum.UserRole;
import com.TMSystem.Entity.User;
import com.TMSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable String username) {
        ResponseUser user= userService.getUser(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody RequestUser user){

        User newUser = userService.registerUser(user);
        if(newUser==null) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("User has been created successfully.",HttpStatus.OK);
        }
    }
    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody RequestUser user){

        User newUser = userService.registerAdmin(user);
        if(newUser==null) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Admin has been created successfully.",HttpStatus.OK);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody RequestUser user) {
        User updatedUser = userService.updateUser(id,user);
        if (updatedUser != null) {
            return new ResponseEntity<>("User has been updated successfully.",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something went wrong.",HttpStatus.BAD_REQUEST);
        }
    }
}
