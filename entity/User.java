package com.ms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users_table")
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String name;

 @Column(unique = true)
 private String email;

 private String password;

 @Enumerated(EnumType.STRING)
 private Role role;

 public enum Role {
     ADMIN, STAFF, USER
 }
}
