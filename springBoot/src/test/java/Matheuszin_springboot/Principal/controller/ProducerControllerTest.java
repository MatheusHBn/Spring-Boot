package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.domain.Producer;
import Matheuszin_springboot.Principal.mapper.ProducerMapperImpl;
import Matheuszin_springboot.Principal.repository.ProducerData;
import Matheuszin_springboot.Principal.repository.ProducerHardCodedRepository;
import Matheuszin_springboot.Principal.service.ProducerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = ProducerControllerTest.class)
//@Import({ProducerMapperImpl.class, ProducerService.class, ProducerHardCodedRepository.class, ProducerData.class})
class ProducerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;
    @MockitoBean
    private ProducerData producerData;
    private List<Producer> producerList = new ArrayList<>();

    @BeforeEach
    void init() {
        String dateTime = "2026-07-24T01:57:42.0274804";
        var localDateTime = LocalDateTime.parse(dateTime);
        var nubank = new Producer("Nubank", 1L, localDateTime);
        var itau = new Producer("Itaú", 2L, localDateTime);
        var c6Bank = new Producer("C6 Bank", 3L, localDateTime);
        producerList = new ArrayList<>(List.of(nubank, itau, c6Bank));
    }

    @Order(1)
    @DisplayName("GET v1/producers should returns a list with all producers, when name is null")
    @Test
    void findAll_ReturnsAllProducers_WhenNameIsNull() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(2)
    @DisplayName("GET v1/producers?name=Nubank returns list with found object when name exists")
    @Test
    void findAll_ReturnsFoundProducerInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-Nubank-name-200.json");
        var name = "Nubank";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/list").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(3)
    @DisplayName("GET v1/producers?name=x returns empty list when name is not found")
    @Test
    void findAll2_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-x-name-200.json");
        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/list").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(4)
    @DisplayName("GET v1/producers/1 returns a producer id when name is null")
    @Test
    void findById_ReturnsProducerId_WhenNameIsNull() throws Exception{
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-by-id-200.json");
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(5)
    @DisplayName("GET v1/producers/99 throws ResponseStatusException when producer is not found")
    @Test
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception{
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Producer not found"));
    }
    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }
}