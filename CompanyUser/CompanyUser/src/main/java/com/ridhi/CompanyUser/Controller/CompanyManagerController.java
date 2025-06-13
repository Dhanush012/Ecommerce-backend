package com.ridhi.CompanyUser.Controller;

import com.ridhi.CompanyUser.Model.CompanyManager;
import com.ridhi.CompanyUser.Service.CompanyManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-managers")
public class CompanyManagerController {

    @Autowired
    private CompanyManagerService managerService;


    @GetMapping
    public List<CompanyManager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/company/{companyName}")
    public CompanyManager getManagersByCompany(@PathVariable String companyName) {
        return managerService.getManagersByCompany(companyName);
    }

    @GetMapping("/{id}")
    public CompanyManager getManagerById(@PathVariable Long id) {
        return managerService.getManagerById(id);
    }

    @PostMapping
    public CompanyManager createManager(@Valid @RequestBody CompanyManager manager) {
        return managerService.saveManager(manager);
    }
}
