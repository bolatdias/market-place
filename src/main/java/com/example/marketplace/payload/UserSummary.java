package com.example.marketplace.payload;

import com.example.marketplace.model.RoleName;
import lombok.Data;

import java.util.List;



@Data
public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String email;
    private List<RoleName> roles;

    public UserSummary(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

}
