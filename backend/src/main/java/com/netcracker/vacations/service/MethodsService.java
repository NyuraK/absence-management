package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.*;
import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.domain.enums.Role;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.exception.EndingBeforeBeginningException;
import com.netcracker.vacations.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MethodsService {

    private UserRepository userRepo;
    private TeamRepository teamRepo;
    private DepartmentRepository depRepo;
    private RequestRepository reqRepo;
    private RequestTypeRepository typeRepo;

    @Autowired
    public MethodsService(UserRepository userRepo, TeamRepository teamRepo, DepartmentRepository depRepo, RequestRepository reqRepo, RequestTypeRepository typeRepo) {
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
        this.depRepo = depRepo;
        this.reqRepo = reqRepo;
        this.typeRepo = typeRepo;
    }

    public void SafeDeleteTeam(TeamEntity team) {
        List<UserEntity> teamsUsers = userRepo.findAllByTeam(team);
        for (UserEntity user : teamsUsers) {
            user.setTeam(null);
        }
        teamRepo.deleteByTeamsId(team.getTeamsId());
    }

    public void SafeDeleteDepartment(DepartmentEntity department) {
        List<TeamEntity> depTeams = teamRepo.findAllByDepartment(department);
        for (TeamEntity team : depTeams) {
            team.setDepartment(null);
        }
        depRepo.deleteByDepartmentsId(department.getDepartmentsId());
    }

    public void SafeDeleteUser(UserEntity user) {
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

    public void SafeDeleteType(RequestTypeEntity type) {
        List<RequestEntity> requests = reqRepo.findAllByTypeOfRequest(type);
        for (RequestEntity request : requests) {
            request.setTypeOfRequest(null);
        }
        typeRepo.deleteById(type.getTypeOfRequest());
    }

    public List<RequestEntity> findByDates(Date begin, Date end) {
        List<RequestEntity> requests = reqRepo.findAllByStatus(Status.ACCEPTED.name);
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

    public void putInfoInBase() {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        try {
            Date date1 = formatter.parse("04/1/19");
            Date date2 = formatter.parse("04/5/19");
            Date date3 = formatter.parse("04/10/19");
            Date date4 = formatter.parse("04/12/19");
            Date date5 = formatter.parse("04/14/19");
            Date date6 = formatter.parse("04/19/19");
            Date date7 = formatter.parse("04/22/19");
            Date date8 = formatter.parse("04/24/19");
            Date date9 = formatter.parse("04/28/19");

            RequestTypeEntity type1 = new RequestTypeEntity(false, false, RequestType.BUSINESSTRIP);
            RequestTypeEntity type2 = new RequestTypeEntity(true, false, RequestType.SICKNESS);
            RequestTypeEntity type3 = new RequestTypeEntity(true, false, RequestType.CHILDCARE);
            RequestTypeEntity type4 = new RequestTypeEntity(false, true, RequestType.REMOTE);
            RequestTypeEntity type5 = new RequestTypeEntity(true, true, RequestType.VACATION);
            typeRepo.save(type1);
            typeRepo.save(type2);
            typeRepo.save(type3);
            typeRepo.save(type4);
            typeRepo.save(type5);

            UserEntity user1 = new UserEntity("Denis", "0901", Role.ADMIN, 20, date1);
            UserEntity user2 = new UserEntity("Aleksei", "5101", Role.MANAGER, 20, date2);
            UserEntity user3 = new UserEntity("Artem", "4901", Role.EMPLOYEE, 20, date3);
            userRepo.save(user1);
            userRepo.save(user2);
            userRepo.save(user3);
            DepartmentEntity dep1 = new DepartmentEntity(user3);
            DepartmentEntity dep2 = new DepartmentEntity(user2);
            depRepo.save(dep1);
            depRepo.save(dep2);
            try {
                RequestEntity calendar1 = new RequestEntity(user2, date1, date2, type1, Status.ACCEPTED);
                reqRepo.save(calendar1);
                RequestEntity calendar2 = new RequestEntity(user2, date2, date3, type2, Status.ACCEPTED);
                reqRepo.save(calendar2);
                RequestEntity calendar3 = new RequestEntity(user3, date2, date4, type3, Status.ACCEPTED);
                reqRepo.save(calendar3);
                RequestEntity calendar4 = new RequestEntity(user3, date4, date5, type4, Status.ACCEPTED);
                reqRepo.save(calendar4);
                RequestEntity calendar5 = new RequestEntity(user1, date1, date6, type5, Status.ACCEPTED);
                reqRepo.save(calendar5);
                RequestEntity calendar6 = new RequestEntity(user1, date7, date9, type1, Status.ACCEPTED);
                reqRepo.save(calendar6);
                RequestEntity calendar7 = new RequestEntity(user1, date6, date8, type2, Status.CONSIDER);
                reqRepo.save(calendar7);
                RequestEntity calendar8 = new RequestEntity(user1, date8, date9, type3, Status.CONSIDER);
                reqRepo.save(calendar8);
                RequestEntity calendar9 = new RequestEntity(user2, date4, date7, type5, Status.DECLINED);
                reqRepo.save(calendar9);
            } catch (EndingBeforeBeginningException ex) {
                ex.printStackTrace();
                System.out.println(ex.message);
            }


            TeamEntity team1 = new TeamEntity(6, user2, "Netcracker");
            teamRepo.save(team1);
            TeamEntity team2 = new TeamEntity(6, user2, "JavaScript");
            teamRepo.save(team2);
            user2.setTeam(team1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getUsersAcceptedRequests(int id, String criterion, String topic1/*FOR TYPE*/, String topic2/*FOR STATUS*/) {
        List<UserEntity> users = userRepo.findByUsersId(id);
        UserEntity user = users.get(0);
        System.out.println("USED METHOD");
        List<RequestEntity> reqs = reqRepo.findAllByUser(user);
        List<RequestEntity> result = new ArrayList<RequestEntity>();
        if (!criterion.equals("Both")) {
            if (!topic1.equals("All")) {
                switch (criterion) {
                    case "Type": {
                        for (RequestEntity req : reqs) {
                            int typeID = typeRepo.findByName(topic1).get(0).getTypeOfRequest();
                            if ((req.getTypeOfRequest().getTypeOfRequest()) == typeID) {
                                result.add(req);
                            }
                        }
                        break;
                    }
                    case "Status": {
                        for (RequestEntity req : reqs) {
                            if (req.getStatus().equals(topic1)) {
                                result.add(req);
                            }
                        }
                        break;
                    }
                    default: {
                        System.out.println("Criterion went wrong");
                    }
                }
            }
            if (topic1.equals("All")) {
                for (RequestEntity req : reqs) {
                    result.add(req);
                }
            }
        } else {
            int typeID = typeRepo.findByName(topic1).get(0).getTypeOfRequest();
            for (RequestEntity req : reqs) {
                if (((req.getTypeOfRequest().getTypeOfRequest()) == typeID) && (req.getStatus().equals(topic2))) {
                    result.add(req);
                }
            }
        }
        for (RequestEntity req : result) {
            System.out.println("Request's ID is=> " + req.getRequestsId());
        }
    }
}
