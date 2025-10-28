package com.springboot.journalApp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private String id;
    private String userName;
    private List<String> roles;
}
