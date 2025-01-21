package org.example.schroniskodlapsow.entity.breed;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BreedEntity {
    @Id
    @GeneratedValue
    int Id;
    String name;

    @Override
    public String toString() {
        return name;
    }
}
