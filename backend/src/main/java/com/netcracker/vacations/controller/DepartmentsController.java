package com.netcracker.vacations.controller;

import com.netcracker.vacations.dto.DepartmentDTO;
import com.netcracker.vacations.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentsController {

    private DepartmentService departmentService;

    public DepartmentsController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<DepartmentDTO> departments() {
        return departmentService.getDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentDTO getDepartment(@PathVariable("id") Integer id) {
        return departmentService.getDepartment(id);
    }

    @PostMapping("/addDepartment")
    public DepartmentDTO addDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.addDepartment(departmentDTO);
    }

    @PutMapping("/{id}")
    public DepartmentDTO updateDepartment(
            @PathVariable("id") Integer id,
            @RequestBody DepartmentDTO departmentDTO
    ) {
        return departmentService.updateDepartment(id, departmentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") Integer id) {
        departmentService.deleteDepartment(id);
    }
}
