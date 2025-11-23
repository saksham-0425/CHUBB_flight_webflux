package com.flightapp.model;

import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Passenger {

    @NotBlank(message = "passenger name required")
    private String name;

    @NotBlank(message = "gender required")
    private String gender;

    @NotNull(message = "age required")
    @Min(value = 0, message = "age must be non-negative")
    private Integer age;

    private String meal;

    private String seatNumber;
}
