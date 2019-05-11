package com.netcracker.vacations.controller;

import com.netcracker.vacations.Util;
import com.netcracker.vacations.dto.AbsenceDTO;
import com.netcracker.vacations.dto.TeamDTO;
import com.netcracker.vacations.service.TeamService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/teams")
public class TeamsController {

    private TeamService teamService;

    public TeamsController(TeamService service) {
        this.teamService = service;
    }

    @PreAuthorize("hasAnyRole('DIRECTOR', 'ADMIN')")
    @GetMapping
    public List<TeamDTO> teams(@RequestParam(name = "departmentId", required = false) Integer departmentId) {
        if (departmentId != null) {
            return teamService.getTeamsFromDepartment(departmentId);
        }
        return teamService.getTeams();
    }

    @PreAuthorize("hasAnyRole('DIRECTOR', 'ADMIN') or @Security.isTeamManager(#id)")
    @GetMapping("/{id}")
    public TeamDTO getTeam(@PathVariable("id") @P("id") Integer id) {
        return teamService.getTeam(id);
    }

    @PreAuthorize("hasAnyRole('DIRECTOR', 'ADMIN')")
    @PostMapping("/addTeam")
    public TeamDTO addTeam(@RequestBody TeamDTO teamDTO) {
        return teamService.addTeam(teamDTO);
    }

    @PreAuthorize("hasAnyRole('DIRECTOR', 'ADMIN')")
    @PutMapping("/{id}")
    public TeamDTO updateTeam(
            @PathVariable("id") Integer id,
            @RequestBody TeamDTO teamDTO
    ) {
        return teamService.updateTeam(id, teamDTO);
    }

    @PreAuthorize("hasAnyRole('DIRECTOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable("id") Integer id) {
        teamService.deleteTeam(id);
    }

    @GetMapping(value = {"/members", "/members/{id}"})
    public List<AbsenceDTO> getTeamMembers(HttpServletRequest request,
                                           @PathVariable(value = "id") Optional<Integer> teamId) {
        String username = Util.extractLoginFromRequest(request);
        if (teamId.isPresent())
            return teamService.getTeamMembers(username, teamId.get());
        else return teamService.getTeamMembers(username);

    }

    @GetMapping("/absences/{id}")
    public List<AbsenceDTO> getTeamAbsences(HttpServletRequest request,
                                            @PathVariable("id") @P("teamID") Integer teamID) {
        String username = Util.extractLoginFromRequest(request);
        return teamService.getTeamAbsences(username, teamID);
    }

    @GetMapping("/my")
    public List<TeamDTO> getManagerTeams(HttpServletRequest request) {
        String username = Util.extractLoginFromRequest(request);
        return teamService.getManagerTeams(username);
    }

}

