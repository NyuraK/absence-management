package com.netcracker.vacations.domain;

import com.netcracker.vacations.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class DeleteMethods {

    public void SafeDeleteTeam(TeamsEntity team, UsersRepository usersRepo, TeamsRepository teamRepo){
        ArrayList<UsersEntity> teamsUsers=usersRepo.findAllByTeamsId(team);
        for (UsersEntity user:teamsUsers){
            user.setTeamsId(null);
        }
        teamRepo.deleteByTeamsId(team.getTeamsId());
    }
    public void SafeDeleteDepartment(DepartmentsEntity department,TeamsRepository teamRepo, DepartmentsRepository depRepo){
        ArrayList<TeamsEntity> depTeams=teamRepo.findAllByDepartmentsId(department);
        for (TeamsEntity team:depTeams){
            team.setDepartmentsId(null);
        }
        depRepo.deleteByDepartmentsId(department.getDepartmentsId());
    }
    public void SafeDeleteUser(UsersEntity user,TeamsRepository teamRepo, DepartmentsRepository depRepo, UsersRepository userRepo){
        ArrayList<DepartmentsEntity> directors=depRepo.findAllByDirectorsId(user);
        ArrayList<TeamsEntity> managers=teamRepo.findAllByManagersId(user);
        for (DepartmentsEntity director:directors){
            director.setDirectorsId(null);
        }
        for (TeamsEntity manager:managers){
            manager.setManagersId(null);
        }
        userRepo.deleteById(user.getUsersId());
    }
    public void SafeDeleteType(TypeOfRequestsEntity type,RequestsRepository requestsRepo, TypeOfRequestsRepository typeRepo){
        ArrayList<RequestsEntity> requests=requestsRepo.findAllByTypeOfRequest(type);
        for (RequestsEntity request:requests){
            request.setTypeOfRequest(null);}
        typeRepo.deleteById(type.getTypeOfRequest());
    }
}
