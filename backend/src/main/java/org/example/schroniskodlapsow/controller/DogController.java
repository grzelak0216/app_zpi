package org.example.schroniskodlapsow.controller;

import lombok.RequiredArgsConstructor;
import org.example.schroniskodlapsow.dto.DogDto;
import org.example.schroniskodlapsow.entity.breed.BreedEntity;
import org.example.schroniskodlapsow.entity.dog.DogEntity;
import org.example.schroniskodlapsow.repository.dog.DogRepository;
import org.example.schroniskodlapsow.service.DogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class DogController {

    private final DogService service;
    private final DogRepository dogRepository;

    @GetMapping("/breeds")
    public ResponseEntity<List<BreedEntity>> getBreeds(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BreedEntity> breedsPage = service.getAllBreeds(pageable);
        return ResponseEntity.ok(breedsPage.getContent());
    }

    @GetMapping("/dogs")
    public ResponseEntity<List<DogDto>> getDogs(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllDogs(pageable));
    }

    @GetMapping("/breeds/{id}")
    public ResponseEntity<BreedEntity> getBreedDetails(@PathVariable("id") int breedId) {
        Optional<BreedEntity> breedEntity = service.getBreedDetails(breedId);
        if (breedEntity.isPresent()) {
            return ResponseEntity.ok(breedEntity.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Breed not found");
        }
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<DogDto> getDogById(@PathVariable int id) {
        Optional<DogEntity> dog = dogRepository.findById(id);
        return dog.map(d -> ResponseEntity.ok(DogDto.from(d)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dog not found"));
    }

    @GetMapping("/breeds/random")
    public ResponseEntity<String> getRandomBreed() {
        List<BreedEntity> breeds = service.getAllBreeds();
        if (breeds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Random random = new Random();
        int randomIndex = random.nextInt(breeds.size());

        Optional<BreedEntity> randomBreed = service.getBreedDetails(breeds.get(randomIndex).getId());
        return randomBreed.map(breed -> ResponseEntity.ok(breed.getName()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/dogs/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getDogImage(@PathVariable int id) {
        try {
            byte[] image = service.getDogImage(id);
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/admin/cache/clear")
    public ResponseEntity<Void> clearCache() {
        service.clearImageCache();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reservation")
    public ResponseEntity<Boolean> reserveVisit(@RequestBody ReservationRequestDto reservationRequestDto) {
        return ResponseEntity.ok(service.reserveIfAvailable(reservationRequestDto.dogId, reservationRequestDto.date));
    }

    public record ReservationRequestDto(int dogId, LocalDateTime date){};
}
