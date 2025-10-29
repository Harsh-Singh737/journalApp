package com.springboot.journalApp.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllUsersDTO {
    private String id;
    private String userName;
    private String email;
    private List<String> roles;
    private String createdAt;
}
