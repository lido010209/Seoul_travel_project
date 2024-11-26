package com.example.travel.authentication.user.service;

import com.example.travel.authentication.user.entity.Authority;
import com.example.travel.authentication.user.repo.AuthorityRepo;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    private final AuthorityRepo authorityRepo;
    private static final String[] authorities =
            {"ROLE_VIEWER", "ROLE_USER", "ROLE_ADMIN"};

    public AuthorityService(AuthorityRepo authorityRepo) {
        this.authorityRepo = authorityRepo;
        for (String role: authorities){
            if (!authorityRepo.existsByRole(role)){
                authorityRepo.save(
                        Authority.builder().role(role).build()
                );
            }
        }
    }
}
