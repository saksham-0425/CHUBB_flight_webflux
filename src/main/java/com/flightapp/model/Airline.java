package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Document(collection = "airlines")
public class Airline {

    @Id
    private String id;

    @NotBlank(message = "Airline name must not be blank")
    private String name;

    @NotBlank(message = "Airline code must not be blank")
    @Indexed(unique = true)
    private String code;
    private String logoUrl;
}
