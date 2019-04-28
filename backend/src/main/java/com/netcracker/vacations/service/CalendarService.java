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
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

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
        TeamEntity team = user.getTeam();

        if (!(team == null)) {
            try {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                Date BeginDate = formatter.parse("01 January " + year);
                Date EndDate = formatter.parse("31 December "+year);

                List<Date> dates = getDatesBetween(BeginDate, EndDate);
                List<RequestEntity> teamReqs = new ArrayList();
                List<UserEntity> teamUsers = userRepo.findAllByTeam(team);
                List<String> occupied = new ArrayList<String>();
                List<String> busy = new ArrayList<String>();

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                Calendar cal3 = Calendar.getInstance();

                int quota = team.getQuota();

                for (UserEntity users : teamUsers) {
                    List<RequestEntity> userReqs = reqRepo.findAllByUser(users);
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
                        cal3.setTime(req.getEnding());
                        boolean sameDayBegin = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                        boolean sameDayEnd = cal1.get(Calendar.DAY_OF_YEAR) == cal3.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal3.get(Calendar.YEAR);
                        if ((((req.getBeginning()).before(date)) || sameDayBegin) && ((((req.getEnding()).after(date)) || sameDayEnd))) {
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
        DateFormat format = new SimpleDateFormat("MM/dd/yy");

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        try {
            while (calendar.before(endCalendar)) {
                String dateString = format.format(calendar.getTime());
                Date result = format.parse(dateString);
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
        userDTO.setRole(entity.getRole().getName());
        userDTO.setLogin(entity.getLogin());
        userDTO.setRestDays(entity.getRestDays());
        userDTO.setTeamId(entity.getTeam() == null ? -1 : entity.getTeam().getTeamsId());
        userDTO.setTeamName(entity.getTeam() == null ? "User without team" : entity.getTeam().getName());
        return userDTO;
    }

    public List<String> getVacations(String status, String name) {
        UserEntity user = userRepo.findByLogin(name).get(0);
        List<RequestEntity> reqs = reqRepo.findAllByUser(user);
        List<RequestEntity> business = new ArrayList<RequestEntity>();
        List<RequestEntity> child = new ArrayList<RequestEntity>();
        List<RequestEntity> vacation = new ArrayList<RequestEntity>();
        List<RequestEntity> sick = new ArrayList<RequestEntity>();
        List<RequestEntity> remote = new ArrayList<RequestEntity>();

        List<String> dates = new ArrayList<String>();
        String begin;
        String end;
        for (RequestEntity req : reqs) {
            if (status.equals("Accepted") && req.getStatus().equals("Accepted")) {
                if (req.getTypeOfRequest().getName().equals("Business trip")) {
                    business.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Child care")) {
                    child.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Vacation")) {
                    vacation.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Remote work")) {
                    remote.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Sick leave")) {
                    sick.add(req);
                }
            } else if (status.equals("Consider")&& req.getStatus().equals("Under consideration")) {
                if (req.getTypeOfRequest().getName().equals("Business trip")) {
                    business.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Child care")) {
                    child.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Vacation")) {
                    vacation.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Remote work")) {
                    remote.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Sick leave")) {
                    sick.add(req);
                }
            } else if (status.equals("Declined") && req.getStatus().equals("Declined")) {
                if (req.getTypeOfRequest().getName().equals("Business trip")) {
                    business.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Child care")) {
                    child.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Vacation")) {
                    vacation.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Remote work")) {
                    remote.add(req);
                } else if (req.getTypeOfRequest().getName().equals("Sick leave")) {
                    sick.add(req);
                }
            }
        }
        for (RequestEntity req : business) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("BU//" + begin + "//" + end);
        }
        for (RequestEntity req : child) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("CH//" + begin + "//" + end);
        }
        for (RequestEntity req : vacation) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("VA//" + begin + "//" + end);
        }
        for (RequestEntity req : sick) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("SI//" + begin + "//" + end);
        }
        for (RequestEntity req : remote) {
            begin = formatter.format(req.getBeginning());
            end = formatter.format(req.getEnding());
            dates.add("RE//" + begin + "//" + end);
        }
        for (String date : dates) {
            System.out.println(date);
        }
        return dates;
    }

}

