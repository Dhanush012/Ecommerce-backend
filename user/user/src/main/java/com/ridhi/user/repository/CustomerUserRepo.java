package com.ridhi.user.repository;


import com.ridhi.user.model.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerUserRepo extends JpaRepository<CustomerUser,Long> {

}
