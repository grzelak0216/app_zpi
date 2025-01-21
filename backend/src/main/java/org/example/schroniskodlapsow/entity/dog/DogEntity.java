package org.example.schroniskodlapsow.entity.dog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schroniskodlapsow.entity.breed.BreedEntity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DogEntity {
    @Id
    @GeneratedValue
    private int Id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    @JsonBackReference
    private BreedEntity breed;

    @Lob
    private byte[] image;

    private int age;
    private String sex;
    private String size;
    private double weight;

    @Column(length = 1000) // np. większa długość kolumny, by pomieścić dłuższą historię
    private String description;      // Krótki opis/historia psa

    private String color;            // Np. "czarny", "brązowy"

    private boolean vaccinated;      // Czy zaszczepiony
    private boolean sterilized;      // Czy wysterylizowany
    private boolean microchipped;    // Czy posiada mikrochip

    private boolean friendlyWithKids;     // Przyjazny dzieciom?
    private boolean friendlyWithAnimals;  // Przyjazny innym psom / kotom?

    @Override
    public String toString() {
        return name + " " + breed.getName();
    }
}
