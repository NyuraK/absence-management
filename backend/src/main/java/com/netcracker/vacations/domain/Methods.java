package com.netcracker.vacations.domain;

import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.repository.*;

import java.util.*;

public class Methods {

    public void SafeDeleteTeam(TeamEntity team, UserRepository usersRepo, TeamRepository teamRepo) {
        List<UserEntity> teamsUsers = usersRepo.findAllByTeamsId(team);
        for (UserEntity user : teamsUsers) {
            user.setTeamsId(null);
        }
        teamRepo.deleteByTeamsId(team.getTeamsId());
    }

    public void SafeDeleteDepartment(DepartmentEntity department, TeamRepository teamRepo, DepartmentRepository depRepo) {
        List<TeamEntity> depTeams = teamRepo.findAllByDepartment(department);
        for (TeamEntity team : depTeams) {
            team.setDepartment(null);
        }
        depRepo.deleteByDepartmentsId(department.getDepartmentsId());
    }

    public void SafeDeleteUser(UserEntity user, TeamRepository teamRepo, DepartmentRepository depRepo, UserRepository userRepo) {
        List<DepartmentEntity> directors = depRepo.findAllByDirector(user);
        List<TeamEntity> managers = teamRepo.findAllByManager(user);
        for (DepartmentEntity director : directors) {
            director.setDirector(null);
        }
        for (TeamEntity manager : managers) {
            manager.setManager(null);
        }
        userRepo.deleteById(user.getUsersId());
    }

    public void SafeDeleteType(RequestTypeEntity type, RequestRepository requestsRepo, RequestTypeRepository typeRepo) {
        List<RequestEntity> requests = requestsRepo.findAllByTypeOfRequest(type);
        for (RequestEntity request : requests) {
            request.setTypeOfRequest(null);
        }
        typeRepo.deleteById(type.getTypeOfRequest());
    }

    public List<RequestEntity> findByDates(Date begin, Date end, RequestRepository requestsRepo) {
        List<RequestEntity> requests = requestsRepo.findAllByStatus(Status.ACCEPTED.name);
        List<RequestEntity> result = new ArrayList<RequestEntity>();
        for (RequestEntity request : requests) {
            if ((((request.getBeginning()).after(begin)) || (request.getBeginning().equals(begin))) && ((((request.getEnding()).before(end)) || (request.getEnding().equals(end))))) {
                result.add(request);
            }
        }
        return result;
    }

    public void /*Map<Date,String>*/ ColorMonth(int year, int month/*, TeamEntity team*/) {
        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(maxDay);
    }
}
