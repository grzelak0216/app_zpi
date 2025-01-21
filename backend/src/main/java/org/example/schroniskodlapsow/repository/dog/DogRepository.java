package org.example.schroniskodlapsow.repository.dog;

import org.example.schroniskodlapsow.entity.dog.DogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<DogEntity, Integer> {
}
