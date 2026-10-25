package com.example.saturday.dto;

import com.example.saturday.enums.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private boolean enabled;
    private MemberStatus status;
    private Long roleId;
    private String roleName;
}
