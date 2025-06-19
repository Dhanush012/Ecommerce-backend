package com.ridhi.user.service;

import com.ridhi.user.model.CustomerUser;
import com.ridhi.user.repository.CustomerUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerUserService {

    @Autowired
    private CustomerUserRepo customerUserRepo;

    public List<CustomerUser> getallusers(){
        return customerUserRepo.findAll();
    }

    public CustomerUser getuserbyId(Long userid){
        Optional<CustomerUser> optionalUser = customerUserRepo.findById(userid);
        if(optionalUser.isPresent()){
            CustomerUser mainUser = optionalUser.get();
            return mainUser;
        }
        throw new RuntimeException("User Not Found!!");
    }

    public void deleteUserById(Long userId) {
        if (!customerUserRepo.existsById(userId)) {
            throw new RuntimeException("User with ID " + userId + " not found!");
        }
        customerUserRepo.deleteById(userId);
    }

    public CustomerUser adduser(CustomerUser user) {
        return customerUserRepo.save(user);
    }
}
