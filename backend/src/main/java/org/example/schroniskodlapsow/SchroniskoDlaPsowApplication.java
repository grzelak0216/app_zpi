package org.example.schroniskodlapsow;

import org.example.schroniskodlapsow.entity.breed.BreedEntity;
import org.example.schroniskodlapsow.entity.dog.DogEntity;
import org.example.schroniskodlapsow.entity.user.MyUser;
import org.example.schroniskodlapsow.repository.breed.BreedRepository;
import org.example.schroniskodlapsow.repository.dog.DogRepository;
import org.example.schroniskodlapsow.repository.user.MyUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
public class SchroniskoDlaPsowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchroniskoDlaPsowApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            MyUserRepository myUserRepository,
            BreedRepository breedRepository,
            DogRepository dogRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Dodanie użytkownika
            String password = passwordEncoder.encode("1234");
            MyUser myUser = MyUser.builder()
                    .firstName("Jan")
                    .lastName("Kowalski")
                    .email("test@gmail.com")
                    .password(password)
                    .build();
            myUserRepository.save(myUser);

            // Dodajemy dane o rasach i psach:
            addBreedWithDogs(breedRepository, dogRepository,
                    "Labrador Retriever", List.of("Burek", "Reksio"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "German Shepherd", List.of("Azor", "Max"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Golden Retriever", List.of("Ciapek", "Rocky"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Bulldog", List.of("Bella", "Molly"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Beagle", List.of("Daisy", "Charlie"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Poodle", List.of("Luna", "Cooper"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Boxer", List.of("Buddy", "Bailey"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Dachshund", List.of("Rosie", "Toby"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Siberian Husky", List.of("Roxy", "Sammy"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Great Dane", List.of("Zoe", "Simba"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Shih Tzu", List.of("Duke", "Sadie"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Rottweiler", List.of("Milo", "Jack"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Chihuahua", List.of("Lilly", "Oscar"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Border Collie", List.of("Bruno", "Ruby"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Maltese", List.of("Shadow", "Lucy"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Yorkshire Terrier", List.of("Piper", "Jake"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Pembroke Welsh Corgi", List.of("Ace", "Oliver"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Boston Terrier", List.of("Finn", "Ginger"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Shetland Sheepdog", List.of("Hunter", "Sophie"));
            addBreedWithDogs(breedRepository, dogRepository,
                    "Dalmatian", List.of("Nala", "Benji"));
        };
    }

    private void addBreedWithDogs(
            BreedRepository breedRepository,
            DogRepository dogRepository,
            String breedName,
            List<String> dogNames
    ) throws IOException {
        // Pobierz istniejącą rasę lub utwórz nową
        BreedEntity breed = breedRepository.findByName(breedName);
        if (breed == null) {
            breed = BreedEntity.builder()
                    .name(breedName)
                    .build();
            breed = breedRepository.saveAndFlush(breed);
        }

        for (String dogName : dogNames) {
            // Budujemy ścieżkę do pliku z obrazem
            String breedString = breedName.replace(" ", "") + "/" + dogName + ".png";
            // Path imagePath = Path.of("src/main/resources/static/" + breedString);
            Path imagePath = Path.of("./resources/static/" + breedString);


            byte[] imageData = Files.readAllBytes(imagePath);

            // Tworzymy obiekt DogEntity z danymi właściwymi dla danego psa
            DogEntity dog = createDogEntityWithDetails(breed, dogName, breedName, imageData);

            // Zapis w bazie
            dogRepository.save(dog);
        }
    }

    /**
     * Metoda tworzy DogEntity z przypisaniem konkretnych danych
     * (wiek, płeć, rozmiar, waga) oraz dodatkowych atrybutów,
     * w zależności od rasy i imienia psa.
     */
    private DogEntity createDogEntityWithDetails(
            BreedEntity breed,
            String dogName,
            String breedName,
            byte[] imageData
    ) {
        // Domyślne wartości (nadpisywane w if-else):
        int age = 1;
        String sex = "male";
        String size = "medium";
        double weight = 10.0;

        // Nowe atrybuty
        String description = "Kocha ludzi i długie spacery.";
        String color = "brown";
        boolean vaccinated = true;
        boolean sterilized = false;
        boolean microchipped = false;
        boolean friendlyWithKids = true;
        boolean friendlyWithAnimals = true;

        if (breedName.equals("Labrador Retriever")) {
            if (dogName.equals("Burek")) {
                age = 3;
                sex = "male";
                size = "large";
                weight = 34.0; // Średnia waga dorosłego labradora

                description = "Burek to pełen energii labrador, uwielbia aportować i pływać. Jest niezwykle towarzyski i zawsze chętny do zabawy. " +
                        "Lubi długie spacery w lesie i świetnie dogaduje się z innymi psami. Jego ulubioną zabawą jest łapanie frisbee.";
                color = "chocolate";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            } else if (dogName.equals("Reksio")) {
                age = 4;
                sex = "male";
                size = "large";
                weight = 32.0;

                description = "Reksio jest spokojnym i lojalnym labradorem, zawsze chętnym do zabawy. Z łatwością dogaduje się z dziećmi i innymi zwierzętami. " +
                        "Ceni sobie czas spędzany na świeżym powietrzu, szczególnie w ogrodzie, gdzie uwielbia bawić się piłką.";
                color = "golden";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("German Shepherd")) {
            if (dogName.equals("Azor")) {
                age = 2;
                sex = "male";
                size = "large";
                weight = 38.0; // Typowa waga dla dorosłego owczarka niemieckiego

                description = "Azor to inteligentny owczarek niemiecki, uwielbia pracować i uczyć się sztuczek. Jest czujny i zawsze gotowy do działania. " +
                        "Świetnie sprawdza się jako pies stróżujący, ale także uwielbia spędzać czas ze swoją rodziną. " +
                        "Jego energia wymaga codziennych aktywności i stymulacji umysłowej.";
                color = "black and tan";
                vaccinated = true;
                sterilized = false;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = false; // Może być ostrożny wobec innych psów
            } else if (dogName.equals("Max")) {
                age = 5;
                sex = "male";
                size = "large";
                weight = 36.0;

                description = "Max jest opanowanym i wiernym owczarkiem, doskonale sprawdza się jako pies rodzinny. Lubi aktywność, ale ceni też chwile spokoju. " +
                        "Jego łagodny charakter sprawia, że jest ulubieńcem dzieci. Uwielbia spacery w parku i dobrze radzi sobie w nowych sytuacjach.";
                color = "sable";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Golden Retriever")) {
            if (dogName.equals("Ciapek")) {
                age = 2;
                sex = "male";
                size = "large";
                weight = 31.0; // Zaktualizowana średnia waga

                description = "Ciapek to radosny golden, który kocha towarzystwo ludzi i długie spacery w lesie. Jest bardzo przyjazny i zawsze gotowy do zabawy. " +
                        "Jego błyszcząca sierść wymaga regularnej pielęgnacji, co sprawia, że wygląda imponująco. Uwielbia wodne zabawy i bieganie za piłką.";
                color = "golden";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            } else if (dogName.equals("Rocky")) {
                age = 5;
                sex = "male";
                size = "large";
                weight = 34.0;

                description = "Rocky jest łagodny i mądry, świetnie sprawdza się w towarzystwie dzieci. Lubi spacery i aktywność na świeżym powietrzu. " +
                        "Jest bardzo lojalny wobec swojej rodziny i zawsze stara się być blisko swoich opiekunów. Jego łagodny charakter czyni go idealnym towarzyszem.";
                color = "cream";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Bulldog")) {
            if (dogName.equals("Bella")) {
                age = 3;
                sex = "female";
                size = "medium";
                weight = 22.0;

                description = "Bella to urocza buldożka, lubi leniwe popołudnia i przytulanie na kanapie. Jest bardzo spokojna, ale nie odmówi spaceru, jeśli go zaproponujesz. " +
                        "Ceni sobie wygodę i uwielbia być w centrum uwagi swojej rodziny.";
                color = "white and brown";
                vaccinated = true;
                sterilized = true;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            } else if (dogName.equals("Molly")) {
                age = 4;
                sex = "female";
                size = "medium";
                weight = 20.0;

                description = "Molly jest spokojna, ale nie odmówi spaceru ani zabawy z piłką. Jej wesoły charakter sprawia, że jest doskonałą towarzyszką dla dzieci. " +
                        "Uwielbia ciepłe miejsca i często zasypia w ulubionym kącie domu.";
                color = "brindle";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Beagle")) {
            if (dogName.equals("Daisy")) {
                age = 2;
                sex = "female";
                size = "small";
                weight = 10.0;

                description = "Daisy to wesoła beagle, która nie może się oprzeć tropieniu zapachów. Jest bardzo energiczna i potrzebuje dużo ruchu. " +
                        "Często angażuje się w zabawy w ogrodzie, a jej wrodzona ciekawość sprawia, że zawsze odkrywa coś nowego.";
                color = "tricolor";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            } else if (dogName.equals("Charlie")) {
                age = 3;
                sex = "male";
                size = "small";
                weight = 12.0;

                description = "Charlie jest energiczny i dociekliwy, uwielbia nowe wyzwania i długie spacery. Jego zwinność i inteligencja czynią go idealnym kompanem do aktywnych zabaw. " +
                        "Lubi towarzystwo innych psów i świetnie odnajduje się w grupie.";
                color = "tricolor";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Poodle")) {
            if (dogName.equals("Luna")) {
                age = 3;
                sex = "female";
                size = "medium";
                weight = 14.0;

                description = "Luna to elegancka pudlica, bardzo towarzyska i chętna do nauki nowych trików. Uwielbia być w centrum uwagi i ceni sobie regularne spacery. " +
                        "Jej inteligencja i łagodny charakter czynią ją ulubieńcem całej rodziny.";
                color = "white";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            } else if (dogName.equals("Cooper")) {
                age = 4;
                sex = "male";
                size = "medium";
                weight = 16.0;

                description = "Cooper jest spokojnym psem, uwielbia wspólne zabawy i kontakt z człowiekiem. Często towarzyszy swojej rodzinie podczas codziennych aktywności. " +
                        "Jego miękka sierść wymaga regularnej pielęgnacji, ale dzięki temu zawsze wygląda świetnie.";
                color = "apricot";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Boxer")) {
            if (dogName.equals("Buddy")) {
                age = 5;
                sex = "male";
                size = "large";
                weight = 29.0;

                description = "Buddy to pełen energii bokser, zawsze chętny do zabawy i biegania. Jego wesoły charakter i lojalność sprawiają, że jest niezastąpionym członkiem rodziny. " +
                        "Uwielbia interakcje z ludźmi, ale potrzebuje dużo ruchu, aby rozładować swoją energię.";
                color = "fawn";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = false;
            } else if (dogName.equals("Bailey")) {
                age = 3;
                sex = "female";
                size = "large";
                weight = 27.0;

                description = "Bailey jest czułą bokserką, uwielbia przytulanie i zabawę w domu. Jest łagodna dla dzieci i dobrze dogaduje się z innymi zwierzętami. " +
                        "Chociaż ma dużo energii, potrafi również docenić chwile spokoju z rodziną.";
                color = "brindle";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Dachshund")) {
            if (dogName.equals("Rosie")) {
                age = 2;
                sex = "female";
                size = "small";
                weight = 7.0;

                description = "Rosie to wesoła jamniczka, uwielbia długie drzemki w ciepłym miejscu. Jej niewielki rozmiar sprawia, że jest idealna do mieszkania w bloku. " +
                        "Lubi spokojne spacery i jest bardzo przywiązana do swojej rodziny.";
                color = "black and tan";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            } else if (dogName.equals("Toby")) {
                age = 5;
                sex = "male";
                size = "small";
                weight = 8.0;

                description = "Toby jest inteligentny i uwielbia eksplorować zakamarki domu oraz ogród. Jest energiczny i zawsze gotowy na nowe przygody. " +
                        "Jego wesołe usposobienie czyni go świetnym kompanem dla starszych dzieci.";
                color = "chocolate";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = false;
            }
        } else if (breedName.equals("Siberian Husky")) {
            if (dogName.equals("Roxy")) {
                age = 3;
                sex = "female";
                size = "large";
                weight = 20.0; // Średnia waga dla suki husky

                description = "Roxy to piękna husky z niebieskimi oczami, która uwielbia długie zimowe wyprawy. Jest energiczna, inteligentna i niezwykle lojalna wobec swoich opiekunów. Idealna dla osób prowadzących aktywny tryb życia.";
                color = "gray and white";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;

            } else if (dogName.equals("Sammy")) {
                age = 4;
                sex = "male";
                size = "large";
                weight = 27.0; // Średnia waga dla samca husky

                description = "Sammy potrzebuje aktywnego opiekuna, ponieważ ma niespożytą energię. Jest towarzyski, wesoły i uwielbia eksplorować nowe miejsca. Doskonale odnajdzie się w rodzinie, która zapewni mu odpowiednią dawkę ruchu i uwagi.";
                color = "black and white";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Great Dane")) {
            if (dogName.equals("Zoe")) {
                age = 6;
                sex = "female";
                size = "large";
                weight = 50.0; // Średnia waga dla suki doga niemieckiego

                description = "Zoe to dostojna dog niemiecka, której łagodny charakter podbija serca wszystkich, którzy ją spotykają. Jest niezwykle przyjazna, spokojna i bardzo lojalna wobec swojej rodziny. Idealna dla osób, które szukają dużego psa o wspaniałym temperamencie.";
                color = "blue";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;

            } else if (dogName.equals("Simba")) {
                age = 2;
                sex = "male";
                size = "large";
                weight = 65.0; // Średnia waga dla samca doga niemieckiego

                description = "Simba jest jeszcze młody, ale już imponuje swoim rozmiarem i spokojnym usposobieniem. Uwielbia biegać po ogrodzie i czuwać nad swoją rodziną. Jego ciepły charakter sprawia, że jest wspaniałym towarzyszem dla każdego.";
                color = "fawn";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Shih Tzu")) {
            if (dogName.equals("Duke")) {
                age = 3;
                sex = "male";
                size = "small";
                weight = 6.0; // Typowa waga dla Shih Tzu

                description = "Duke to elegancki i pełen wdzięku Shih Tzu, który uwielbia być w centrum uwagi. Jego ulubionym zajęciem jest spędzanie czasu na kanapie i bycie rozpieszczanym przez opiekunów. To pies idealny dla osób ceniących spokojne chwile w domu.";
                color = "white and gray";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;

            } else if (dogName.equals("Sadie")) {
                age = 5;
                sex = "female";
                size = "small";
                weight = 5.0; // Typowa waga dla Shih Tzu

                description = "Sadie jest łagodna i spokojna, z łatwością nawiązuje więź z opiekunami. Uwielbia spędzać czas na kolanach swojego właściciela i cieszyć się spokojną atmosferą domu. To doskonały wybór dla rodzin z dziećmi.";
                color = "golden and white";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }

        } else if (breedName.equals("Rottweiler")) {
            if (dogName.equals("Milo")) {
                age = 2;
                sex = "male";
                size = "large";
                weight = 42.0; // Typowa waga dla samca rottweilera

                description = "Milo to silny i zwinny Rottweiler, który wyróżnia się swoim oddaniem wobec właściciela. Jest aktywny, energiczny i wymaga regularnej dawki ruchu. Idealny dla osób z doświadczeniem w wychowywaniu dużych psów.";
                color = "black and tan";
                vaccinated = true;
                sterilized = false;
                microchipped = true;
                friendlyWithKids = false; // Niepewny wobec małych dzieci
                friendlyWithAnimals = false;

            } else if (dogName.equals("Jack")) {
                age = 4;
                sex = "male";
                size = "large";
                weight = 50.0; // Typowa waga dla dojrzałego samca rottweilera

                description = "Jack jest zrównoważonym i posłusznym Rottweilerem. Jest bardzo lojalny, ale wymaga konsekwentnego i doświadczonego opiekuna. Doskonale nadaje się do roli psa stróżującego, ale równie dobrze sprawdza się jako towarzysz rodziny.";
                color = "black and tan";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = false;
            }
        } else if (breedName.equals("Chihuahua")) {
            if (dogName.equals("Lilly")) {
                age = 3;
                sex = "female";
                size = "small";
                weight = 2.5; // Typowa waga dla Chihuahua

                description = "Lilly to malutka suczka o wielkim charakterze. Jest ciekawska i pełna energii, uwielbia tulenie i spędzanie czasu w ramionach właściciela. Doskonale odnajduje się w małych przestrzeniach.";
                color = "cream";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = false; // Może być nerwowa przy małych dzieciach
                friendlyWithAnimals = true;

            } else if (dogName.equals("Oscar")) {
                age = 5;
                sex = "male";
                size = "small";
                weight = 3.0; // Typowa waga dla Chihuahua

                description = "Oscar jest pewny siebie i uwielbia być w centrum uwagi. Jego silna osobowość sprawia, że szybko zdobywa sympatię otoczenia. Idealny dla osoby, która szuka oddanego i zabawnego kompana.";
                color = "black";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = false;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Border Collie")) {
            if (dogName.equals("Bruno")) {
                age = 4;
                sex = "male";
                size = "medium";
                weight = 18.0; // Typowa waga dla samca Border Collie

                description = "Bruno jest niezwykle aktywnym i inteligentnym psem, który uwielbia być w ruchu. Jest idealnym towarzyszem dla osób uprawiających sport, takich jak bieganie czy rowerowe przejażdżki. Doskonale radzi sobie w treningach posłuszeństwa i zadaniach wymagających myślenia.";
                color = "black and white";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;

            } else if (dogName.equals("Ruby")) {
                age = 2;
                sex = "female";
                size = "medium";
                weight = 16.0; // Typowa waga dla suki Border Collie

                description = "Ruby to pełna energii Border Collie, która uwielbia bawić się w agility i aportowanie. Jest bardzo czuła wobec swoich opiekunów i wymaga dużo uwagi oraz stymulacji umysłowej. To idealny wybór dla aktywnej rodziny.";
                color = "black and white";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Maltese")) {
            if (dogName.equals("Shadow")) {
                age = 4;
                sex = "male";
                size = "small";
                weight = 4.0; // Typowa waga dla Maltańczyka

                description = "Shadow to delikatny i łagodny maltańczyk, który doskonale odnajduje się w spokojnym otoczeniu. Uwielbia być blisko swoich opiekunów i spędzać czas na kanapie. Jego spokojny charakter czyni go idealnym towarzyszem dla starszych osób.";
                color = "white";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;

            } else if (dogName.equals("Lucy")) {
                age = 1;
                sex = "female";
                size = "small";
                weight = 3.0; // Typowa waga dla młodego Maltańczyka

                description = "Lucy to maleńka, wesoła suczka, która uwielbia głaskanie i zabawy z zabawkami. Jest niezwykle czuła i szybko nawiązuje więź z opiekunami, sprawiając, że każdy dzień z nią to czysta radość.";
                color = "white";
                vaccinated = true;
                sterilized = false;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }
        } else if (breedName.equals("Yorkshire Terrier")) {
            if (dogName.equals("Piper")) {
                age = 2;
                sex = "female";
                size = "small";
                weight = 2.5; // Typowa waga dla suczki rasy Yorkshire Terrier

                description = "Piper to rezolutna i odważna suczka, lubi odkrywać nowe miejsca. Jest pełna energii i zawsze gotowa do zabawy, a jej ciekawość świata sprawia, że każdy spacer to przygoda. Mimo niewielkich rozmiarów, posiada silny charakter i potrafi być stanowcza, co jest typowe dla tej rasy.";
                color = "steel blue and tan";
                vaccinated = true;
                sterilized = true;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;

            } else if (dogName.equals("Jake")) {
                age = 5;
                sex = "male";
                size = "small";
                weight = 3.0; // Typowa waga dla psa rasy Yorkshire Terrier

                description = "Jake jest spokojniejszy, uwielbia towarzyszyć opiekunowi w codziennych czynnościach. Jego lojalność i przywiązanie sprawiają, że jest doskonałym towarzyszem na każdą okazję. Ceni sobie komfort domowego zacisza, ale nie odmówi krótkiej zabawy czy spaceru.";
                color = "gray and tan";
                vaccinated = true;
                sterilized = false;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }

        } else if (breedName.equals("Pembroke Welsh Corgi")) {
            if (dogName.equals("Ace")) {
                age = 4;
                sex = "male";
                size = "medium";
                weight = 12.0; // Typowa waga dla psa rasy Pembroke Welsh Corgi

                description = "Ace to przyjazny corgi, który kocha zabawy i bieganie za piłką. Jego inteligencja i chęć do nauki czynią go idealnym kandydatem do psich sportów i treningów. Uwielbia spędzać czas z rodziną, a jego wesołe usposobienie zaraża wszystkich wokół.";
                color = "red and white";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;

            } else if (dogName.equals("Oliver")) {
                age = 2;
                sex = "male";
                size = "medium";
                weight = 10.0; // Typowa waga dla młodego psa rasy Pembroke Welsh Corgi

                description = "Oliver jest bystrym i towarzyskim psiakiem, szybko nawiązuje relacje. Jego energiczna natura sprawia, że jest zawsze gotowy na nowe wyzwania i przygody. Potrzebuje regularnej aktywności, aby spożytkować swoją energię i utrzymać dobrą kondycję.";
                color = "sable and white";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }

        } else if (breedName.equals("Boston Terrier")) {
            if (dogName.equals("Finn")) {
                age = 3;
                sex = "male";
                size = "small";
                weight = 7.0; // Typowa waga dla psa rasy Boston Terrier

                description = "Finn jest wesoły i ciekawski, uwielbia bawić się w aportowanie. Jego zwinność i szybkie reakcje czynią go mistrzem w łapaniu piłek i frisbee. Jest również czuły i lubi spędzać czas na kolanach opiekuna, ciesząc się wspólną obecnością.";
                color = "black and white";
                vaccinated = true;
                sterilized = false;
                microchipped = false;
                friendlyWithKids = true;
                friendlyWithAnimals = true;


            } else if (dogName.equals("Ginger")) {
                age = 4;
                sex = "female";
                size = "small";
                weight = 6.5; // Typowa waga dla suczki rasy Boston Terrier

                description = "Ginger jest pełna uroku, najbardziej lubi wylegiwanie się na poduszce. Jej spokojna natura sprawia, że jest idealnym psem do towarzystwa w domowym zaciszu. Mimo to, nie odmówi krótkiej zabawy czy spaceru, ciesząc się każdą chwilą spędzoną z rodziną.";
                color = "seal and white";
                vaccinated = true;
                sterilized = true;
                microchipped = true;
                friendlyWithKids = true;
                friendlyWithAnimals = true;
            }


            } else if (breedName.equals("Shetland Sheepdog")) {
                if (dogName.equals("Hunter")) {
                    age = 5;
                    sex = "male";
                    size = "medium";
                    weight = 12.0; // Typowa waga dla psa rasy Shetland Sheepdog

                    description = "Hunter to bystry sheltie, zawsze chętny do nauki nowych komend i sztuczek. Jego pasja do pracy i zadowolenia opiekuna czyni go doskonałym psem do różnorodnych aktywności. Uwielbia długie spacery i potrzebuje regularnej stymulacji umysłowej, aby być szczęśliwym.";
                    color = "sable and white";
                    vaccinated = true;
                    sterilized = false;
                    microchipped = false;
                    friendlyWithKids = true;
                    friendlyWithAnimals = true;

                } else if (dogName.equals("Sophie")) {
                    age = 3;
                    sex = "female";
                    size = "medium";
                    weight = 10.5; // Typowa waga dla suczki rasy Shetland Sheepdog

                    description = "Sophie jest czuła i kochana, lubi spędzać czas z całą rodziną. Jej łagodny temperament sprawia, że doskonale dogaduje się z dziećmi i innymi zwierzętami. Jest również bardzo inteligentna i szybko się uczy, co czyni ją wspaniałym towarzyszem w codziennym życiu.";
                    color = "blue merle and white";
                    vaccinated = true;
                    sterilized = true;
                    microchipped = true;
                    friendlyWithKids = true;
                    friendlyWithAnimals = true;
                }
                } else if (breedName.equals("Dalmatian")) {
                    if (dogName.equals("Nala")) {
                        age = 2;
                        sex = "female";
                        size = "large";
                        weight = 25.0; // Typowa waga dla suczki rasy Dalmatyńczyk

                        description = "Nala to wesoła dalmatynka, pełna energii i chętna do zabawy w gonitwy. Jej niekończąca się energia sprawia, że potrzebuje dużo ruchu i aktywności na świeżym powietrzu. Jest lojalnym towarzyszem, zawsze gotowa bronić swojej rodziny. Idealna dla osób prowadzących aktywny tryb życia.";
                        color = "white with black spots";
                        vaccinated = true;
                        sterilized = false;
                        microchipped = false;
                        friendlyWithKids = true;
                        friendlyWithAnimals = true;

                    } else if (dogName.equals("Benji")) {
                        age = 4;
                        sex = "male";
                        size = "large";
                        weight = 27.0; // Typowa waga dla psa rasy Dalmatyńczyk

                        description = "Benji uwielbia biegać i odkrywać nowe miejsca, jest psem o wielkim sercu. Jego zwinność i siła czynią go świetnym towarzyszem do długich wędrówek i aktywnych zabaw. Benji szybko zżywa się ze swoją rodziną, okazując uczucia w ciepły i oddany sposób.";
                        color = "white with liver spots";
                        vaccinated = true;
                        sterilized = true;
                        microchipped = true;
                        friendlyWithKids = true;
                        friendlyWithAnimals = true;
                    }
                }

                // Tworzymy obiekt z danymi wypełnionymi z powyższych if-else
                return DogEntity.builder()
                        .name(dogName)
                        .breed(breed)
                        .image(imageData)
                        .age(age)
                        .sex(sex)
                        .size(size)
                        .weight(weight)

                        // Nowe pola
                        .description(description)
                        .color(color)
                        .vaccinated(vaccinated)
                        .sterilized(sterilized)
                        .microchipped(microchipped)
                        .friendlyWithKids(friendlyWithKids)
                        .friendlyWithAnimals(friendlyWithAnimals)

                        .build();
            }

        }
