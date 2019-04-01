package com.netcracker.vacations.controllers;

//import com.netcracker.vacations.domain.UserEntity;
//import com.netcracker.vacations.repository.EntityRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class LoginController {
    /*@Autowired
    private EntityRepository entityRepo;*/
    private boolean infoWasPutInBase=false;
    /*private List<Map<String, String>> users = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("login", "Anna"); put("password", "1234"); }});
        add(new HashMap<String, String>() {{ put("login", "Artem"); put("password", "5678"); }});
        add(new HashMap<String, String>() {{ put("login", "Denis"); put("password", "9012"); }});
    }};*/
    /*public void putInfoInBase (){
        UserEntity calendar = new UserEntity("AnnaKapranova","1234","Kapranova", "Anna");
        entityRepo.save(calendar);
        calendar = new UserEntity("ArtemRogovskii","5678", "Rogovskii", "Artem");
        entityRepo.save(calendar);
        calendar = new UserEntity("DenisMitskovskii","9012", "Mitskovskii", "Denis");
        entityRepo.save(calendar);
    }*/
    @PostMapping("/login")
    public boolean check(@RequestBody Map<String, String> credentials) {
        boolean result = false;
        /*boolean overloaded = false;

        if (!infoWasPutInBase){
        putInfoInBase();//Помещает данные о пользователях в базу данных
        infoWasPutInBase=true;// если в базу данных не помещали иформации раньше
        }

        String login = credentials.get("login");
        ArrayList<UserEntity> user = entityRepo.findByLogin(login);//Ищет  пользователя в базе данных по логину
        if (user.size() > 1) { //Проверка на наличие такого же логина в базе, потребуется при создании регистрации, пока не используется
            overloaded = true;
            System.out.println("Такой логин уже существует " + overloaded);
        } else if (user.size()!=0){ //Если логин найден в базе данных, то
            UserEntity entity=user.get(0);
            if (entity.getPassword().equals(credentials.get("password"))) { //Проверка пароля
                result = true;
            }
        }
        */
        System.out.println("Autentification is succesful => "+result);
        return result;
    }

}
