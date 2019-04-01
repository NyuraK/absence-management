package com.netcracker.vacations.controllers;

import com.netcracker.vacations.domain.DepartmentsEntity;
import com.netcracker.vacations.domain.RequestsEntity;
import com.netcracker.vacations.domain.TypeOfRequestsEntity;
import com.netcracker.vacations.domain.UsersEntity;
import com.netcracker.vacations.repository.DepartmentsRepository;
import com.netcracker.vacations.repository.RequestsRepository;
import com.netcracker.vacations.repository.TypeOfRequestsRepository;
import com.netcracker.vacations.repository.UsersRepository;
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
    private DepartmentsRepository depEntity;
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
            TypeOfRequestsEntity type=new TypeOfRequestsEntity(true,false);
            typeRepo.save(type);
            DepartmentsEntity dep=new DepartmentsEntity(1);
            depEntity.save(dep);
            UsersEntity user=new UsersEntity("Denis", "0901", "user", 20, date1);
            usersRepo.save(user);
            RequestsEntity calendar = new RequestsEntity(user, date2, date3, type, "В рассмотрении");
            requestRepo.save(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/login")
    public boolean check(@RequestBody Map<String, String> credentials) {
        boolean result = false;
        boolean overloaded = false;
        System.out.println("method check");
        putInfoInBase();//Помещает данные о пользователях в базу данных

        /*String login = credentials.get("login");
        ArrayList<UsersEntity> user = entityRepo.findByLogin(login);//Ищет  пользователя в базе данных по логину
        if (user.size() > 1) { //Проверка на наличие такого же логина в базе, потребуется при создании регистрации, пока не используется
            overloaded = true;
            System.out.println("Такой логин уже существует " + overloaded);
        } else if (user.size()!=0){ //Если логин найден в базе данных, то
            UsersEntity entity=user.get(0);
            if (entity.getPassword().equals(credentials.get("password"))) { //Проверка пароля
                result = true;
            }
        }

        System.out.println("Autentification is succesful => "+result);*/
        return result;
    }

}
