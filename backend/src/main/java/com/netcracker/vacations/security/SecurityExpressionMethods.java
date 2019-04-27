package com.netcracker.vacations.security;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.repository.TeamRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("Security")
public class SecurityExpressionMethods {
    private static final Logger logger = LogManager.getLogger(SecurityExpressionMethods.class);

    private TeamRepository teamRepository;

    @Autowired
    public SecurityExpressionMethods(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public boolean isManager(Integer teamId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserEntity user = ((MyUserPrincipal) authentication.getPrincipal()).getUser();
        if (teamRepository.findByManager(user).isEmpty()) return false;
        Integer user_team_id = teamRepository.findByManager(user).get(0).getTeamsId();
        return user_team_id.equals(teamId);
    }
}
