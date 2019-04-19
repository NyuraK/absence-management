package com.netcracker.vacations.controller;


import com.netcracker.vacations.dto.TeamDTO;
import com.netcracker.vacations.repository.TeamRepository;
import com.netcracker.vacations.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/teams")
public class TeamsController {

    private TeamService teamService;
    private final TeamRepository teamRepository;

    public TeamsController(TeamRepository teamRepository, TeamService service) {
        this.teamRepository = teamRepository;
        this.teamService = service;
    }

    @GetMapping
    public List<TeamDTO> teams() {
        return teamService.getTeams();
    }

    @GetMapping("/{id}")
    public TeamDTO getTeam(@PathVariable("id") Integer id) {
        return teamService.getTeam(id);
    }

    @PostMapping("/addTeam")
    public TeamDTO addTeam(@RequestBody TeamDTO teamDTO) {
        return teamService.addTeam(teamDTO);
    }

    @PutMapping("/{id}")
    public TeamDTO updateTeam(
            @PathVariable("id") Integer id,
            @RequestBody TeamDTO teamDTO
    ) {
        return teamService.updateTeam(id, teamDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable("id") Integer id) {
        teamService.deleteTeam(id);
    }

}

