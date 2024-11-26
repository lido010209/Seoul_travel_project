package com.example.travel.authentication.user.dto;

import com.example.travel.authentication.user.entity.Authority;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class AuthorityDto {
    private Long id;
    private String role;
    public static AuthorityDto dto(Authority authority){
        AuthorityDto authorityDto = AuthorityDto.builder()
                .id(authority.getId())
                .role(authority.getRole())
                .build();
        return authorityDto;
    }
}
