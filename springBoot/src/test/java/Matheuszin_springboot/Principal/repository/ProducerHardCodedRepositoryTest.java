package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.Producer;
import Matheuszin_springboot.commons.ProducerUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private  ProducerHardCodedRepository repository;
    @Mock
    private ProducerData producerData;
    private List<Producer> producerList = new ArrayList<>();
    @InjectMocks
    private ProducerUtils producerUtils;

    @BeforeEach
    void init(){

        producerList = producerUtils.newProducerList();
    }

    @Order(1)
    @DisplayName("findAll should returns a list with all producers")
    @Test
    void findAll_ReturnsAllProducers_WhenSuccessful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers = repository.findAll();
        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().hasSize(producers.size());

    }

    @Order(2)
    @DisplayName("findById should returns a producer with given id")
    @Test
    void findById_ReturnsAllProducersById_WhenSuccessful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer = producerList.getFirst();
        var producers = repository.findByID(expectedProducer.getId());
        org.assertj.core.api.Assertions.assertThat(producers).isPresent().contains(expectedProducer);

    }

    @Order(3)

    @DisplayName("findByName should returns empty list when name is null")
    @Test
    void findByName_ReturnsEmptyList_WhenNameIsNull(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers = repository.findByName(null);
        Assertions.assertThat(producers).isNotNull().isEmpty();

    }

    @Order(4)
    @DisplayName("findByName should returns list with found object when name exists")
    @Test
    void findByName_ReturnsFoundProducerInList_WhenNameIsFound(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer = producerList.getFirst();

        var producers = repository.findByName(expectedProducer.getName());
        Assertions.assertThat(producers).hasSize(1);

    }
}