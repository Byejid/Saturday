package com.example.saturday.controller;

import com.example.saturday.dto.RoleRequestDto;
import com.example.saturday.dto.RoleResponseDto;
import com.example.saturday.entity.Role;
import com.example.saturday.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
   private  final RoleService roleService;
   public RoleController(RoleService roleService){
       this.roleService=roleService;
   }
   @PostMapping
    public ResponseEntity<Role> addRole(@Valid @RequestBody Role  requestDto){
      Role responseDto=roleService.addRole(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<Role>>getAllRoles(){
       List<Role> roleList=   roleService.getAllRoles();
       return ResponseEntity
               .ok(roleList);
    }
}
