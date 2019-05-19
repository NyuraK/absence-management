package com.netcracker.vacations.converter;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.dto.AbsenceDTO;

import java.util.Date;

public class AbsenceConverter {

    public static AbsenceDTO convert(RequestEntity requestEntity) {
        AbsenceDTO absenceDTO = new AbsenceDTO();
        absenceDTO.setName(requestEntity.getUser().getName());
        absenceDTO.setSurname(requestEntity.getUser().getSurname());
        absenceDTO.setType(requestEntity.getTypeOfRequest().getName());
        absenceDTO.setBegin(requestEntity.getBeginning().toString());
        absenceDTO.setEnd(requestEntity.getEnding().toString());
        return absenceDTO;
    }

    public static AbsenceDTO convert(UserEntity user) {
        AbsenceDTO absenceDTO = new AbsenceDTO();
        absenceDTO.setName(user.getName());
        absenceDTO.setSurname(user.getSurname());
        absenceDTO.setType("");
        absenceDTO.setBegin(new Date().toString());
        absenceDTO.setEnd(new Date().toString());
        return absenceDTO;
    }

}
