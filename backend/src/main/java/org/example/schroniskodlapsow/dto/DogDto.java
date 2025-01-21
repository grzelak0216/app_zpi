package org.example.schroniskodlapsow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.schroniskodlapsow.entity.dog.DogEntity;

import java.util.Base64;

@Data
@AllArgsConstructor
public class DogDto {
    private int id;
    private String name;
    private String breedName;
    private String image;

    private int age;
    private String sex;
    private String size;
    private double weight;

    private String description;
    private String color;

    private boolean vaccinated;
    private boolean sterilized;
    private boolean microchipped;

    private boolean friendlyWithKids;
    private boolean friendlyWithAnimals;

    public static DogDto from(DogEntity entity) {
        return new DogDto(
                entity.getId(),
                entity.getName(),
                entity.getBreed().getName(),
                Base64.getEncoder().encodeToString(entity.getImage()),
                entity.getAge(),
                entity.getSex(),
                entity.getSize(),
                entity.getWeight(),
                entity.getDescription(),
                entity.getColor(),
                entity.isVaccinated(),
                entity.isSterilized(),
                entity.isMicrochipped(),
                entity.isFriendlyWithKids(),
                entity.isFriendlyWithAnimals()
        );
    }
}
