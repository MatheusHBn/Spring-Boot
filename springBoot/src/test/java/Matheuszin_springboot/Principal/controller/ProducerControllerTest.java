package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.domain.Producer;
import Matheuszin_springboot.Principal.repository.ProducerData;
import Matheuszin_springboot.Principal.repository.ProducerHardCodedRepository;
import Matheuszin_springboot.commons.FileUtils;
import Matheuszin_springboot.commons.ProducerUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = ProducerController.class)
class ProducerControllerTest {
    private static final String URL = "/v1/producers/list";
    private static final String URL2 = "/v1/producers";

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private ProducerHardCodedRepository repository;
    @Autowired
    private FileUtils fileUtils;
    @MockitoBean
    private ProducerData producerData;
    private List<Producer> producerList = new ArrayList<>();
    @Autowired
    private ProducerUtils producerUtils;

    @BeforeEach
    void init() {
        producerList = producerUtils.newProducerList();
    }

    @Order(1)
    @DisplayName("GET v1/producers should returns a list with all producers, when name is null")
    @Test
    void findAll_ReturnsAllProducers_WhenNameIsNull() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(2)
    @DisplayName("GET v1/producers?name=Nubank returns list with found object when name exists")
    @Test
    void findAll_ReturnsFoundProducerInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var verifyError = fileUtils.readResourceFile("producer/get-producer-by-id-404.json");
        var name = "Nubank";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(3)
    @DisplayName("GET v1/producers?name=x returns empty list when name is not found")
    @Test
    void findAll2_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var verifyError = fileUtils.readResourceFile("producer/get-producer-by-id-404.json");
        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(4)
    @DisplayName("GET v1/producers/1 returns a producer id when name is null")
    @Test
    void findById_ReturnsProducerId_WhenNameIsNull() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var verifyError = fileUtils.readResourceFile("producer/get-producer-by-id-404.json");
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL2+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(5)
    @DisplayName("GET v1/producers/99 throws NotFound when producer is not found")
    @Test
    void findById_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var verifyError = fileUtils.readResourceFile("producer/get-producer-by-id-404.json");
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }
    @Order(6)
    @DisplayName("POST v1/producers creates a producer")
    @Test
    void save_CreatesProducer_WhenSuccessful() throws Exception {
        var verifyError = fileUtils.readResourceFile("producer/get-producer-by-id-404.json");
        var request = fileUtils.readResourceFile("producer/post-request-producer-200.json");
        var producerToSave = producerUtils.newProducerToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/producers")
                        .content(request).header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(verifyError));

    }

    @SneakyThrows
    @Order(7)
    @DisplayName("PUT v1/producer updates a producer")
    @Test
    void update_UpdatesProducer_WhenSuccessful() {
        var request = fileUtils.readResourceFile("producer/put-producer-by-id-200.json");

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/producers")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Order(8)
    @DisplayName("update throws NotFound when producer is not found")
    @Test
    void update_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {

        var request = fileUtils.readResourceFile("producer/put-producer-by-id-404.json");
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/producers")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Order(9)
    @DisplayName("DELETE v1/producers/1 creates a producer")
    @Test
    void delete_RemoveProducer_WhenSuccessful() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var id = producerList.getFirst().getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Order(10)
    @DisplayName("DELETE v11/producers/99 throws NotFound when producer is not found")
    @Test
    void delete_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var request = fileUtils.readResourceFile("producer/put-producer-by-id-404.json");
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason(request));

    }
}