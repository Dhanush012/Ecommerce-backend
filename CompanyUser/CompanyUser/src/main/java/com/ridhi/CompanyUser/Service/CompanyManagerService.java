package com.ridhi.CompanyUser.Service;

import com.ridhi.CompanyUser.Model.CompanyManager;
import com.ridhi.CompanyUser.Repository.CompanyManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyManagerService {

    @Autowired
    private CompanyManagerRepository companyManagerRepository;

    public List<CompanyManager> getAllManagers(){
        return companyManagerRepository.findAll();
    }

    public CompanyManager getManagerById(Long id){
        Optional<CompanyManager> manager = companyManagerRepository.findById(id);
        if(manager.isPresent()){
            CompanyManager m = manager.get();
            return m;
        }
        throw new RuntimeException("CompanyManager Not Found!!");
    }

    public CompanyManager getManagersByCompany(String companyName){
        return companyManagerRepository.findByCompanyName(companyName);
    }

    public CompanyManager saveManager(CompanyManager companyManager){
        return companyManagerRepository.save(companyManager);
    }
}
