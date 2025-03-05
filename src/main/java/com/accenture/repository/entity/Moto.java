package com.accenture.repository.entity;


import com.accenture.model.Cylindree;
import com.accenture.model.TypeMoto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Moto extends Vehicule {


    @Enumerated(EnumType.STRING)
    private Cylindree cylindree;
    @Enumerated(EnumType.STRING)
    private TypeMoto typeMoto;
    @NotNull
    private Integer poids;
    @NotNull
    private Integer puissanceKw;
    @NotBlank
    private String hauteurSelle;

}
