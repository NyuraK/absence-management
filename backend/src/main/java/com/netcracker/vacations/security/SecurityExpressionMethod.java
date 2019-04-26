package com.netcracker.vacations.security;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.repository.TeamRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("Security")
public class SecurityExpressionMethod {
    private static final Logger logger = LogManager.getLogger(SecurityExpressionMethod.class);

    public boolean isManager(Integer teamId, TeamRepository teamRepository, Authentication authentication) {
        logger.info("in isManager");
        final UserEntity user = ((MyUserPrincipal) authentication.getPrincipal()).getUser();
        if (teamRepository.findByManager(user).isEmpty()) return false;
        Integer user_team_id = teamRepository.findByManager(user).get(0).getTeamsId();
        return user_team_id.equals(teamId);
    }

}

