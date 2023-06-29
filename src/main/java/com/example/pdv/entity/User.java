package com.example.pdv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    @NotBlank(message = "Campo nome é obrigatório")
    private String name;
    @NotBlank(message = "O campo username é obrigatório.")
    @Column(length = 30, nullable = false)
    private String userName;
    @NotBlank(message = "O campo senha é obrigatório.")
    @Column(length = 30, nullable = false)
    private String password;
    private boolean isEnable;

    @OneToMany(mappedBy = "user")
    private List<Sale> sales;
}

