package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Class is temporary, as I'm not sure yet where better get team members

@Service
public class TeamService {

    //temporary field, as we don't have teams now
    private UserRepository repository;

    @Autowired
    public TeamService(UserRepository repository) {
        this.repository = repository;
    }

        public List<String> getMembers() {
        List<String> res = new ArrayList<>();
        for (UserEntity user : repository.findAll()) {
            res.add(user.getName() + " " + user.getFamilyName());
        }
        return res;
    }

}
