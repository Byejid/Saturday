package com.example.saturday.service;

import com.example.saturday.dto.RoleRequestDto;
import com.example.saturday.dto.RoleResponseDto;
import com.example.saturday.entity.Role;
import com.example.saturday.exception.DuplicateResourceFoundException;
import com.example.saturday.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    @Transactional
    public Role  addRole(Role requestDto){
        if(roleRepository.existsByName(requestDto.getName())){
            throw  new DuplicateResourceFoundException(
                    "Already exists "+ requestDto.getName()
            );

        }
        Role role = new Role();
        role.setName(requestDto.getName());
      return    roleRepository.save(role);



    }

    public List<Role> getAllRoles(){

        return roleRepository.findAll()
                .stream()
                .map(role -> new Role(
                        role.getId(),
                        role.getName()
                ) )
                .toList();
    }
}
