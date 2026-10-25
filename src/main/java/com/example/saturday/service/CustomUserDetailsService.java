package com.example.saturday.service;

import com.example.saturday.entity.CustomUserDetails;
import com.example.saturday.entity.Member;
import com.example.saturday.exception.ResourceNotFoundException;
import com.example.saturday.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    public CustomUserDetailsService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member=memberRepository.findByEmail(username)
                .orElseThrow( ()-> new UsernameNotFoundException(
                        "Member with "+username+ " doesn't exists"
                ));
        return new CustomUserDetails(member);
    }
}
