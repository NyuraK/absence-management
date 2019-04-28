package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.DepartmentEntity;
import com.netcracker.vacations.dto.DepartmentDTO;
import com.netcracker.vacations.repository.DepartmentRepository;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DepartmentService {
    private DepartmentRepository departmentRepository;
    private UserRepository userRepository;

    public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> getDepartments() {
        List<DepartmentDTO> response = new ArrayList<>();
        for (DepartmentEntity entity : departmentRepository.findAll()) {
            response.add(toDTO(entity));
        }
        return response;
    }

    public DepartmentDTO getDepartment(Integer id) {
        return toDTO(departmentRepository.findById(id).get());
    }

    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity entity = toEntity(departmentDTO);
        departmentRepository.save(entity);
        return departmentDTO;
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }

    public DepartmentDTO updateDepartment(Integer id, DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = departmentRepository.findByDepartmentsId(id).get(0);
        BeanUtils.copyProperties(toEntity(departmentDTO), departmentEntity, "departmentsId");
        departmentRepository.save(departmentEntity);
        return departmentDTO;
    }

    private DepartmentEntity toEntity(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        if (departmentDTO.getDirectorId() == null) {
            departmentEntity.setDirector(null);
        } else {
            departmentEntity.setDirector(userRepository.findByUsersId(departmentDTO.getDirectorId()).get(0));
        }
        departmentEntity.setDepartmentsId(null);
        departmentEntity.setName(departmentDTO.getName());
        return departmentEntity;
    }

    private DepartmentDTO toDTO(DepartmentEntity entity) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(entity.getDepartmentsId());
        departmentDTO.setName(entity.getName());
        departmentDTO.setDirectorId(entity.getDirector() == null ? null : entity.getDirector().getUsersId());
        departmentDTO.setDirectorName(entity.getDirector() == null ? null : entity.getDirector().getName() + " "
        + entity.getDirector().getSurname());
        return departmentDTO;
    }
}
