package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Class is temporary, as I'm not sure yet where better get team members

@Service
public class TeamService {

    //temporary field, as we don;t have teams now
    private UserRepository repository;

    @Autowired
    public TeamService(UserRepository repository) {
        this.repository = repository;
    }

    public List<List<String>> getMembers() {
        List<List<String>> res = new ArrayList<>();
        for (UserEntity user: repository.findAll()) {
            List<String> row = new ArrayList<>();
            row.add(user.getName() + " " + user.getFamilyName());
            row.add("");
//            row.add("opacity: 0");
            row.add(new Date().toString());
            row.add(new Date().toString());
            res.add(row);
        }
        return res;
    }
}
