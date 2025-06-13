package com.ridhi.CompanyUser.Repository;


import com.ridhi.CompanyUser.Model.CompanyManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyManagerRepository extends JpaRepository<CompanyManager,Long> {

    CompanyManager findByCompanyName(String companyName);
}
