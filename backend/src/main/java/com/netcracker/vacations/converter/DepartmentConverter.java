package com.netcracker.vacations.converter;

import com.netcracker.vacations.domain.DepartmentEntity;
import com.netcracker.vacations.dto.DepartmentDTO;

public class DepartmentConverter {


    public static DepartmentDTO toDTO(DepartmentEntity entity) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(entity.getDepartmentsId());
        departmentDTO.setName(entity.getName());
        departmentDTO.setDirectorId(entity.getDirector() == null ? null : entity.getDirector().getUsersId());
        departmentDTO.setDirectorName(entity.getDirector() == null ? null : entity.getDirector().getName() + " "
                + entity.getDirector().getSurname());
        return departmentDTO;
    }
}
