package com.Matheuszin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class User {
    @EqualsAndHashCode.Include
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
