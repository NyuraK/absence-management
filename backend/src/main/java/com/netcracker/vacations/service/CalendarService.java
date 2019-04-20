package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CalendarService {
    private UserRepository userRepo;
    private TeamRepository teamRepo;
    private DepartmentRepository depRepo;
    private RequestRepository reqRepo;
    private RequestTypeRepository typeRepo;

    @Autowired
    public CalendarService(UserRepository userRepo, TeamRepository teamRepo, DepartmentRepository depRepo, RequestRepository reqRepo, RequestTypeRepository typeRepo) {
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
        this.depRepo = depRepo;
        this.reqRepo = reqRepo;
        this.typeRepo = typeRepo;
    }

    public List<String> getVacationsPerDay(String mode, String name) {
        UserEntity user = userRepo.findByLogin(name).get(0);
        TeamEntity team = user.getTeamsId();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

        if (!(team == null)) {
            try {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                Date BeginDate = new SimpleDateFormat("MM/dd/yy").parse("01/01/" + year);
                Date EndDate = new SimpleDateFormat("MM/dd/yy").parse("31/12/" + year);

                List<Date> dates = getDatesBetween(BeginDate, EndDate);
                List<RequestEntity> teamReqs = new ArrayList();
                List<UserEntity> teamUsers = userRepo.findAllByTeamsId(team);
                List<String> occupied = new ArrayList<String>();
                List<String> busy = new ArrayList<String>();

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                int quota = team.getQuota();

                for (UserEntity users : teamUsers) {
                    List<RequestEntity> userReqs = reqRepo.findAllByUsersId(users);
                    for (RequestEntity req : userReqs) {
                        if (req.getStatus().equals("Accepted") && (req.getTypeOfRequest().getInfluenceOnVr())) {
                            teamReqs.add(req);
                        }
                    }
                }
                for (Date date : dates) {
                    int counter = 0;
                    for (RequestEntity req : teamReqs) {

                        cal1.setTime(date);
                        cal2.setTime(req.getBeginning());
                        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                        if ((((req.getBeginning()).before(date)) || sameDay) && ((((req.getEnding()).after(date)) || sameDay))) {
                            counter++;
                        }
                    }
                    if (counter >= quota) {
                        occupied.add(formatter.format(date));
                    } else if (counter >= quota / 2) {
                        busy.add(formatter.format(date));
                    }
                }
                if (mode.equals("Occupied")) {
                    for (String date : occupied) {
                        System.out.println("DATE OCC=>" + date);
                    }
                    return occupied;
                }
                if (mode.equals("Busy")) {
                    for (String date : busy) {
                        System.out.println("DATE BUSY=>" + date);
                    }
                    return busy;
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public List<Date> getDatesBetween(Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        try {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

            while (calendar.before(endCalendar)) {
                String dateString = formatter.format(calendar.getTime());
                Date result = formatter.parse(dateString);
                datesInRange.add(result);
                calendar.add(Calendar.DATE, 1);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return datesInRange;
    }

    public UserDTO toDTO(UserEntity entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(entity.getUsersId());
        userDTO.setDescription(entity.getDescription());
        userDTO.setEmail(entity.getEmail());
        userDTO.setFamilyName(entity.getFamilyName());
        userDTO.setHireDate(entity.getHireDate());
        userDTO.setName(entity.getName());
        userDTO.setSurname(entity.getSurname());
        userDTO.setPassword(entity.getPassword());
        userDTO.setPhoneNumber(entity.getPhoneNumber());
        userDTO.setRole(entity.getRole());
        userDTO.setLogin(entity.getLogin());
        userDTO.setRestDays(entity.getRestDays());
        userDTO.setTeamId(entity.getTeamsId() == null ? -1 : entity.getTeamsId().getTeamsId());
        userDTO.setTeamName(entity.getTeamsId() == null ? "User without team" : entity.getTeamsId().getName());
        return userDTO;
    }

    public List<String> getVacations(String status, String name) {
        UserEntity user = userRepo.findByLogin(name).get(0);
        List<RequestEntity> reqs = reqRepo.findAllByUsersId(user);
        List<RequestEntity> business = new ArrayList<RequestEntity>();
        List<RequestEntity> child = new ArrayList<RequestEntity>();
        List<RequestEntity> vacation = new ArrayList<RequestEntity>();
        List<RequestEntity> sick = new ArrayList<RequestEntity>();
        List<RequestEntity> remote = new ArrayList<RequestEntity>();

        List<String> dates = new ArrayList<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        String begin;
        String end;
        for (RequestEntity req : reqs) {
            if (status.equals("Accepted")) {
                if (req.getTypeOfRequest().getName().equals("Business trip") && req.getStatus().equals("Accepted")) {
                    business.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Child care") && req.getStatus().equals("Accepted")) {
                    child.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Vacation") && req.getStatus().equals("Accepted")) {
                    vacation.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Remote work") && req.getStatus().equals("Accepted")) {
                    remote.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Sick leave") && req.getStatus().equals("Accepted")) {
                    sick.add(req);
                }
            } else if (status.equals("Consider")) {
                if (req.getTypeOfRequest().getName().equals("Business trip") && req.getStatus().equals("Under consideration")) {
                    business.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Child care") && req.getStatus().equals("Under consideration")) {
                    child.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Vacation") && req.getStatus().equals("Under consideration")) {
                    vacation.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Remote work") && req.getStatus().equals("Under consideration")) {
                    remote.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Sick leave") && req.getStatus().equals("Under consideration")) {
                    sick.add(req);
                }
            } else if (status.equals("Declined")) {
                if (req.getTypeOfRequest().getName().equals("Business trip") && req.getStatus().equals("Declined")) {
                    business.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Child care") && req.getStatus().equals("Declined")) {
                    child.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Vacation") && req.getStatus().equals("Declined")) {
                    vacation.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Remote work") && req.getStatus().equals("Declined")) {
                    remote.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Sick leave") && req.getStatus().equals("Declined")) {
                    sick.add(req);
                }
            }
        }
        for (RequestEntity req : business) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("BU//"+begin + "//" + end);
        }
        for (RequestEntity req : child) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("CH//"+begin + "//" + end);
        }
        for (RequestEntity req : vacation) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("VA//"+begin + "//" + end);
        }
        for (RequestEntity req : sick) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("SI//"+begin + "//" + end);
        }
        for (RequestEntity req : remote) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("RE//"+begin + "//" + end);
        }
        for (String date : dates) {
            System.out.println(date);
        }
        return dates;
    }

}

