package com.example.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;
    private String name;
    private String userName;
    private String password;
    private boolean isEnabled;


}
