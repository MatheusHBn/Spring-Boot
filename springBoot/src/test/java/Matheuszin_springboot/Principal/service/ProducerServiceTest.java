package Matheuszin_springboot.Principal.service;

import Matheuszin_springboot.Principal.domain.Producer;
import Matheuszin_springboot.Principal.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodedRepository repository;

    private List<Producer> producerList;

    @BeforeEach
    void init() {
        var nubank = new Producer("Nubank", 1L, LocalDateTime.now());
        var itau = new Producer("Itaú", 2L, LocalDateTime.now());
        var c6Bank = new Producer("C6 Bank", 3L, LocalDateTime.now());
        producerList = new ArrayList<>(List.of(nubank, itau, c6Bank));
    }

    @Order(1)
    @DisplayName("findAll should returns a list with all producers, when name is null")
    @Test
    void findAll_ReturnsAllProducers_WhenNameIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        var producers = service.findAll(null);

        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().hasSize(producers.size());

    }

    @Order(2)
    @DisplayName("findAll should returns a list with all producers, when name is null")
    @Test
    void findByName_ReturnsAllProducers_WhenNameIsNull() {
        var producer = producerList.getFirst();
        var expectedProducersFound = singletonList(producer);
        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducersFound);
        var producersFound = service.findAll(producer.getName());
        Assertions.assertThat(producersFound).containsAll(expectedProducersFound);

    }

    @Order(3)
    @DisplayName("findByName should returns empty list when name is not found")
    @Test
    void findByName_ReturnsEmptyListProducer_WhenNameIsNotFound() {
        var name = "Nubank";
        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());
        var producers = service.findAll(name);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Order(4)
    @DisplayName("findById returns producer id when name is null")
    @Test
    void findById_ReturnsProducerId_WhenNameIsNull() {
        var expectedProducer = producerList.getFirst();
        BDDMockito.when(repository.findByID(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));
        var producers = service.findByIdOrThrowNotFound(expectedProducer.getId());
        Assertions.assertThat(producers).isEqualTo(expectedProducer);
    }

    @Order(5)
    @DisplayName("findById throws ResponseStatusException when producer is not found")
    @Test
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducer = producerList.getFirst();
        BDDMockito.when(repository.findByID(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProducer.getId())).isInstanceOf(ResponseStatusException.class);
    }

    @Order(6)
    @DisplayName("save creates a producer")
    @Test
    void save_CreatesProducer_WhenSuccessful() {
        var producerToSave = Producer.builder().id(99L).name("Nubank").localDateTime(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);
        var savedProducer = service.save(producerToSave);
        Assertions.assertThat(savedProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }

    @Order(7)
    @DisplayName("save creates a producer")
    @Test
    void delete_RemoveProducer_WhenSuccessful() {
        var producerToDelete = producerList.getFirst();
        BDDMockito.when(repository.findByID(producerToDelete.getId())).thenReturn(Optional.of(producerToDelete));
        BDDMockito.doNothing().when(repository).deleteById(producerToDelete);
        Assertions.assertThatNoException().isThrownBy(() -> service.deleteById(producerToDelete.getId()));
    }

    @Order(8)
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    @Test
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var producerToDelete = producerList.getFirst();
        BDDMockito.when(repository.findByID(producerToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.deleteById(producerToDelete.getId())).isInstanceOf(ResponseStatusException.class);

    }

    @Order(9)
    @DisplayName("update a producer")
    @Test
    void update_UpdatesProducer_WhenSuccessful() {
        var producerToUpdate = producerList.getFirst();
        producerToUpdate.setName("Nubank");
        BDDMockito.when(repository.findByID(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));
        BDDMockito.doNothing().when(repository).update(producerToUpdate);
        service.update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));

    }

    @Order(10)
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Test
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var producerToUpdate = producerList.getFirst();

        BDDMockito.when(repository.findByID(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.update(producerToUpdate)).isInstanceOf(ResponseStatusException.class);

    }
}