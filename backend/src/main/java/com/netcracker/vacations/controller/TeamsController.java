package com.netcracker.vacations.controller;


import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.repository.TeamRepository;
import com.netcracker.vacations.service.TeamService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamsController {

    private final TeamRepository teamRepository;
    private TeamService service;

    public TeamsController(TeamRepository teamRepository, TeamService service) {
        this.teamRepository = teamRepository;
        this.service = service;
    }

    //    @Secured("ROLE_ADMIN")
    @GetMapping
    public Iterable<TeamEntity> teams() {
        return teamRepository.findAll();
    }

    //    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public TeamEntity getTeam(@PathVariable("id") TeamEntity team) {
        System.out.println("in the get team");
        return team;
    }

    //    @Secured("ROLE_ADMIN")
    @PostMapping("/addTeam")
    public TeamEntity addUser(@RequestBody TeamEntity team) {
        return teamRepository.save(team);
    }

    //    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public TeamEntity updateTeam(
            @PathVariable("id") TeamEntity teamFromDb,
            @RequestBody TeamEntity team
    ) {
        BeanUtils.copyProperties(team, teamFromDb, "id");

        return teamRepository.save(teamFromDb);
    }

    //    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable("id") TeamEntity team) {
        teamRepository.delete(team);
    }

    @GetMapping("/timeline")
    public List<String> getTeamMembers(){
        return service.getMembers();
    }

}

