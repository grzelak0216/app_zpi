package org.example.schroniskodlapsow.service;

import lombok.RequiredArgsConstructor;
import org.example.schroniskodlapsow.dto.DogDto;
import org.example.schroniskodlapsow.entity.breed.BreedEntity;
import org.example.schroniskodlapsow.entity.dog.DogEntity;
import org.example.schroniskodlapsow.entity.reservation.ReservationEntity;
import org.example.schroniskodlapsow.repository.breed.BreedRepository;
import org.example.schroniskodlapsow.repository.dog.DogRepository;
import org.example.schroniskodlapsow.repository.reservation.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DogService {

    private final DogRepository dogRepository;
    private final BreedRepository breedRepository;
    private final ImageCacheService imageCacheService;
    private final ReservationRepository reservationRepository;

    public Optional<BreedEntity> getBreedDetails(int breedId) {
        return breedRepository.findById(breedId);
    }

    public Page<BreedEntity> getAllBreeds(Pageable pageable) {
        return breedRepository.findAll(pageable);
    }

    public List<BreedEntity> getAllBreeds() {
        return breedRepository.findAll();
    }

    public List<DogDto> getAllDogs(Pageable pageable) {
        Page<DogEntity> dogsPage = dogRepository.findAll(pageable);
        return dogsPage.getContent().stream()
                .map(DogDto::from)
                .collect(Collectors.toList());
    }

    public byte[] getDogImage(int dogId) throws IOException {
        Optional<DogEntity> dog = dogRepository.findById(dogId);
        if (dog.isEmpty()) {
            throw new IllegalArgumentException("Dog not found with ID: " + dogId);
        }

        String breedName = dog.get().getBreed().getName().replace(" ", "");
        String dogName = dog.get().getName();
        String imagePath = "src/main/resources/static/" + breedName + "/" + dogName + ".png";

        return imageCacheService.getImage(imagePath);
    }

    public boolean reserveIfAvailable(int dogId, LocalDateTime reservationDate) {
        // Define the timezone
        ZoneId zoneId = ZoneId.systemDefault(); // Or specify a timezone, e.g., ZoneId.of("UTC")

        // Convert LocalDateTime to Instant
        Instant date = reservationDate.atZone(zoneId).toInstant();
        DogEntity dog = dogRepository.findById(dogId).get();

        boolean isReservationAvailable = reservationRepository.findByDogAndDate(dog, date).isEmpty();

        if(isReservationAvailable) {
            ReservationEntity reservation = ReservationEntity.builder().dog(dog).date(date).build();
            reservationRepository.save(reservation);
            return true;
        }

        return false;
    }

    public void clearImageCache() {
        imageCacheService.clearCache();
    }
}
