package com.netcracker.vacations.controllers;

import com.netcracker.vacations.domain.*;
import com.netcracker.vacations.domain.enums.RequestsTypes;
import com.netcracker.vacations.domain.enums.Role;
import com.netcracker.vacations.domain.enums.Statuses;
import com.netcracker.vacations.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UsersRepository usersRepo;
    @Autowired
    private TypeOfRequestsRepository typeRepo;
    @Autowired
    private RequestsRepository requestRepo;
    @Autowired
    private DepartmentsRepository depRepo;
    @Autowired
    private TeamsRepository teamRepo;
    private boolean infoWasPutInBase=false;
    /*private List<Map<String, String>> users = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("login", "Anna"); put("password", "1234"); }});
        add(new HashMap<String, String>() {{ put("login", "Artem"); put("password", "5678"); }});
        add(new HashMap<String, String>() {{ put("login", "Denis"); put("password", "9012"); }});
    }};*/
    public void putInfoInBase (){
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        try {
            Date date1 = formatter.parse("01/29/02");
            Date date2 = formatter.parse("05/29/02");
            Date date3 = formatter.parse("07/29/02");

            TypeOfRequestsEntity type1=new TypeOfRequestsEntity(false,false, RequestsTypes.BUISNESSTRIP);
            TypeOfRequestsEntity type2=new TypeOfRequestsEntity(true,false, RequestsTypes.SICKNESS);
            TypeOfRequestsEntity type3=new TypeOfRequestsEntity(true,false, RequestsTypes.CHILDCARE);
            TypeOfRequestsEntity type4=new TypeOfRequestsEntity(false,true, RequestsTypes.REMOTE);
            TypeOfRequestsEntity type5=new TypeOfRequestsEntity(true,true, RequestsTypes.VACATION);
            typeRepo.save(type1);
            typeRepo.save(type2);
            typeRepo.save(type3);
            typeRepo.save(type4);
            typeRepo.save(type5);

            UsersEntity user1=new UsersEntity("Denis", "0901", Role.ADMIN, 20, date1);
            UsersEntity user2=new UsersEntity("Anna", "5101", Role.MANAGER, 20, date2);
            UsersEntity user3=new UsersEntity("Artem", "4901", Role.EMPLOYEE, 20, date3);
            usersRepo.save(user1);
            usersRepo.save(user2);
            usersRepo.save(user3);
            DepartmentsEntity dep1=new DepartmentsEntity(user3);
            DepartmentsEntity dep2=new DepartmentsEntity(user2);
            depRepo.save(dep1);
            depRepo.save(dep2);
            RequestsEntity calendar1 = new RequestsEntity(user2, date1, date2, type1, Statuses.ACCEPTED);
            requestRepo.save(calendar1);
            RequestsEntity calendar2 = new RequestsEntity(user2, date2, date1, type2, Statuses.CONSIDER);
            requestRepo.save(calendar2);
            RequestsEntity calendar3 = new RequestsEntity(user3, date1, date2, type3, Statuses.DECLINED);
            requestRepo.save(calendar3);

            TeamsEntity team1=new TeamsEntity(6, 2, user2,dep1,"Netcracker");
            teamRepo.save(team1);
            TeamsEntity team2=new TeamsEntity(6, 2, user2,dep2,"JavaScript");
            teamRepo.save(team2);
            user2.setTeamsId(team1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public boolean check(@RequestBody Map<String, String> credentials) {
        boolean result = false;
        boolean overloaded = false;
        System.out.println("method check");
        if (!infoWasPutInBase){
            putInfoInBase();
            infoWasPutInBase=true;
        }//Помещает данные о пользователях в базу данных

        String login = credentials.get("login");
        ArrayList<UsersEntity> user = usersRepo.findByLogin(login);
        ArrayList<DepartmentsEntity> deps = depRepo.findByDepartmentsId(2);
        ArrayList<UsersEntity> usersCheck = usersRepo.findByUsersId(2);
        ArrayList<TypeOfRequestsEntity> typeCheck = typeRepo.findByTypeOfRequest(2);
        DepartmentsEntity dep=deps.get(0);//Ищет  пользователя в базе данных по логину
        if (user.size() > 1) { //Проверка на наличие такого же логина в базе, потребуется при создании регистрации, пока не используется
            overloaded = true;
            System.out.println("Такой логин уже существует " + overloaded);
        } else if (user.size()!=0){ //Если логин найден в базе данных, то
            UsersEntity entity=user.get(0);
            if (entity.checkPassword(credentials.get("password"))) { //Проверка пароля
                result = true;
            }
        }
        Methods methods=new Methods();
        //delete.SafeDeleteType(typeCheck.get(0),requestRepo,typeRepo);
        try{
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date date1 = formatter.parse("01/29/02");
        Date date2 = formatter.parse("05/29/02");
        ArrayList<RequestsEntity> reqs=methods.findByDates(date1,date2,requestRepo);
        for (RequestsEntity req:reqs){
            System.out.println("ID=> "+req.getRequestsId());
        }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Autentification is succesful => "+result);
        return result;
    }

}
