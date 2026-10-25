package com.example.saturday.controller;

import com.example.saturday.dto.MemberRequestDto;
import com.example.saturday.dto.MemberResponseDto;
import com.example.saturday.repository.RoleRepository;
import com.example.saturday.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private  final MemberService memberService;



    public MemberController(
            MemberService memberService

    ){
        this.memberService=memberService;

    }
    @PostMapping("/register")
    public ResponseEntity<MemberResponseDto>registerMember(@Valid @RequestBody MemberRequestDto requestDto){
        MemberResponseDto response =
                memberService.registerMember(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMemberById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }
    @GetMapping("/by-role")
    public ResponseEntity<List<MemberResponseDto>> getMembersByRole(
            @RequestParam String roleName
    ) {
        return ResponseEntity.ok(
                memberService.getMembersByRole(roleName)
        );
    }
}
