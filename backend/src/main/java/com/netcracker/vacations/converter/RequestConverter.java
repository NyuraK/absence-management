package com.netcracker.vacations.converter;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.dto.RequestDTO;

public class RequestConverter {

    public static RequestDTO convert(RequestEntity entity) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(entity.getRequestsId());
        String name = entity.getUser().getName();
        String familyName = entity.getUser().getFamilyName();
        if ((name == null || name.isEmpty()) && (familyName == null || familyName.isEmpty())) {
            requestDTO.setName("-");
        } else if (familyName == null || familyName.isEmpty()) {
            requestDTO.setName(entity.getUser().getName());
        } else if (name == null || name.isEmpty()) {
            requestDTO.setName(entity.getUser().getFamilyName());
        } else {
            requestDTO.setName(entity.getUser().getName() + " " + entity.getUser().getFamilyName());
        }
        if (entity.getUser().getTeam() != null) {
            requestDTO.setTeamName(entity.getUser().getTeam().getName());
        } else {
            requestDTO.setTeamName("-");
        }
        if (entity.getUser().getTeam() != null) {
            requestDTO.setTeamName(entity.getUser().getTeam().getName());
        } else {
            requestDTO.setTeamName("-");
        }
        requestDTO.setUsername(entity.getUser().getLogin());
        requestDTO.setName(entity.getUser().getName() + " " + entity.getUser().getFamilyName());
        requestDTO.setDescription(entity.getDescription());
        requestDTO.setStart(DateConverter.convertToDateViaSqlDate(entity.getBeginning()));
        requestDTO.setEnd(DateConverter.convertToDateViaSqlDate(entity.getEnding()));
        requestDTO.setType(entity.getTypeOfRequest().getName());
        requestDTO.setStatus(entity.getStatus().getName());
        return requestDTO;
    }
}
