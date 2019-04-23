package com.netcracker.vacations.controller;


import com.netcracker.vacations.dto.AbsenceDTO;
import com.netcracker.vacations.dto.TeamDTO;
import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamsController {

    private TeamService teamService;

    public TeamsController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamDTO> teams(@RequestParam Optional<String> username) {
        if (username.isPresent())
            return teamService.getManagerTeams(username.get());
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

    @GetMapping("/{user}/{id}")
    public List<AbsenceDTO> getTeamMembers(@PathVariable("user") String username,
                                           @PathVariable("id") Optional<Integer> teamId) {
        if (teamId.isPresent())
            return teamService.getTeamMembers(teamId.get());
        else return teamService.getTeamMembers(username);
    }

    @GetMapping("/absences/{id}")
    public List<AbsenceDTO> getTeamAbsences(@RequestParam String username, @PathVariable("id") Integer teamID) {
        return teamService.getTeamAbsences(username, teamID);
    }

}
