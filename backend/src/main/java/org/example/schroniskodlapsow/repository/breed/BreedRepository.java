package org.example.schroniskodlapsow.repository.breed;

import org.example.schroniskodlapsow.entity.breed.BreedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BreedRepository extends JpaRepository<BreedEntity, Integer> {

    Optional<BreedEntity> findDogEntityByName(String name);

    BreedEntity findByName(String breedName);
}
