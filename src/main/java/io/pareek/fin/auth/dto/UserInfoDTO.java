package io.pareek.fin.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDTO {
    private final String id;
    private final String name;
    private final String email;
    private final String picture;
}