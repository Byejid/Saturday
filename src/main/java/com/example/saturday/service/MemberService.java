package com.example.saturday.service;

import com.example.saturday.dto.MemberRequestDto;
import com.example.saturday.dto.MemberResponseDto;
import com.example.saturday.entity.Member;
import com.example.saturday.entity.Role;
import com.example.saturday.enums.MemberStatus;
import com.example.saturday.exception.DuplicateResourceFoundException;
import com.example.saturday.exception.ResourceNotFoundException;
import com.example.saturday.repository.MemberRepository;
import com.example.saturday.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public MemberService(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ){
        this.memberRepository=memberRepository;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
    }
  @Transactional
    public MemberResponseDto registerMember(MemberRequestDto memberRequestDto){
         if(memberRepository.existsByEmail(memberRequestDto.getEmail())){
             throw new DuplicateResourceFoundException(
                     "A member with this email already exists"
             );
         }


        Member member= new Member();
        Role role= roleRepository.findByName("ROLE_STUDENT")
                        .orElseThrow(()-> new ResourceNotFoundException(
                                "ROLE_GUEST was not found. Please create this role first."
                        ));
         member.setName(memberRequestDto.getName());
         member.setEmail(memberRequestDto.getEmail());
         member.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
         member.setRole(role);
         member.setEnabled(true);
         member.setStatus(MemberStatus.ACTIVE);
    Member savedMember= memberRepository.save(member);
     return mapToResponseDto(savedMember);
    }

    public List<MemberResponseDto> getAllMembers() {

        return memberRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    public MemberResponseDto getMemberById(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with ID: " + id
                        )
                );

        return mapToResponseDto(member);
    }
    public List<MemberResponseDto> getMembersByRole(String roleName) {


        Role role = roleRepository.findByName(roleName)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Role not found: " + roleName
                ));


        return memberRepository.findByRole_Name(roleName)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }


    private MemberResponseDto mapToResponseDto(Member member) {

        MemberResponseDto responseDto = new MemberResponseDto();

        responseDto.setId(member.getId());
        responseDto.setName(member.getName());
        responseDto.setEmail(member.getEmail());
        responseDto.setEnabled(member.isEnabled());
        responseDto.setStatus(member.getStatus());
        responseDto.setRoleId(member.getRole().getId());
        responseDto.setRoleName(member.getRole().getName());

        return responseDto;
    }
}
