package com.ridhi.user.controller;

import com.ridhi.user.model.CustomerUser;
import com.ridhi.user.service.CustomerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerUserController {

    @Autowired
    private CustomerUserService customerUserService;

    @GetMapping("/users")
    public ResponseEntity<List<CustomerUser>> getallusers(){
        return new ResponseEntity<>(customerUserService.getallusers(),HttpStatus.OK);
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<CustomerUser> getuserbyId(@PathVariable Long userid){
        return new ResponseEntity<>(customerUserService.getuserbyId(userid),HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid")
    public void deleteUser(@RequestParam Long id) {
        customerUserService.deleteUserById(id);
    }

}
