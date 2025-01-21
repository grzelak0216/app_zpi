package org.example.schroniskodlapsow.entity.reservation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schroniskodlapsow.entity.dog.DogEntity;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReservationEntity {

    @GeneratedValue
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    @JsonBackReference
    private DogEntity dog;

    private Instant date;
}
