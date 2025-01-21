package org.example.schroniskodlapsow.repository.reservation;

import org.example.schroniskodlapsow.entity.dog.DogEntity;
import org.example.schroniskodlapsow.entity.reservation.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

    public Optional<ReservationEntity> findByDogAndDate(DogEntity dog, Instant date);
}
