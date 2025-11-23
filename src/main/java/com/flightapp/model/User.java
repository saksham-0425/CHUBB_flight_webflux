package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "hashedPassword")
@EqualsAndHashCode
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank(message = "name required")
    private String name;

    @NotBlank(message = "email required")
    @Email(message = "email must be valid")
    @Indexed(unique = true)
    private String email;
    private String hashedPassword;
    private String[] roles;
}
