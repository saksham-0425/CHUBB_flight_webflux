package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "airlines")
public class Airline {
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String code; 
    private String logoUrl;
}
