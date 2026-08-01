package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.domain.Monitor;
import Matheuszin_springboot.Principal.mapper.MonitorMapperImpl;
import Matheuszin_springboot.Principal.mapper.ProducerMapperImpl;
import Matheuszin_springboot.Principal.repository.MonitorData;
import Matheuszin_springboot.Principal.repository.MonitorHardCodedRepository;
import Matheuszin_springboot.Principal.repository.ProducerData;
import Matheuszin_springboot.Principal.repository.ProducerHardCodedRepository;
import Matheuszin_springboot.Principal.service.MonitorService;
import Matheuszin_springboot.Principal.service.ProducerService;
import Matheuszin_springboot.commons.FileUtils;
import Matheuszin_springboot.commons.MonitorUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
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
@SpringBootTest
@AutoConfigureMockMvc
@Import({ProducerMapperImpl.class, ProducerService.class, ProducerHardCodedRepository.class, ProducerData.class})
class MonitorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static final String URL = "/v1/monitors/list";
    private static final String URL2 = "/v1/monitors";

    @MockitoBean
    private MonitorService service;

    @Autowired
    private FileUtils fileUtils;
    @MockitoBean
    private MonitorHardCodedRepository repository;
    @Autowired
    private ResourceLoader resourceLoader;
    @MockitoBean
    private MonitorData monitorData;
    @Autowired
    private MonitorUtils monitorUtils;
    private List<Monitor> monitorList = new ArrayList<>();

    @BeforeEach
    void init() {
        monitorList = monitorUtils.newMonitorList();
    }

    @Order(1)
    @DisplayName("GET v1/monitors should returns a list with all monitors, when name is null")
    @Test
    void findAll_ReturnsAllMonitors_WhenNameIsNull() throws Exception {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var response = fileUtils.readResourceFile("monitor/get-monitor-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(2)
    @DisplayName("GET v1/monitors?name=Alienware returns list with found object when name exists")
    @Test
    void findByName_ReturnsFoundMonitorInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var verifyError = fileUtils.readResourceFile("monitor/get-monitor-by-id-404.json");
        var name = "Alienware";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(3)
    @DisplayName("GET v1/monitors?name=x returns empty list when name is not found")
    @Test
    void findByName_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var verifyError = fileUtils.readResourceFile("monitor/get-monitor-by-id-404.json");
        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(4)
    @DisplayName("GET v1/monitors/1 returns a producer id when name is null")
    @Test
    void findById_ReturnsMonitorId_WhenNameIsNull() throws Exception {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var response = fileUtils.readResourceFile("monitor/get-monitor-by-id-404.json");
        var hertz = 75L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL2+"/{hertz}", hertz))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(5)
    @DisplayName("GET v1/monitors/99 throws NotFound when monitor is not found")
    @Test
    void findById_ThrowsNotFound_WhenMonitorIsNotFound() throws Exception {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);

        var hertz = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL2+"/{hertz}", hertz))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Monitor not found"));
    }
    @Order(6)
    @DisplayName("POST v1/monitors creates a monitor")
    @Test
    void save_CreatesMonitor_WhenSuccessful() throws Exception {
        var verifyError = fileUtils.readResourceFile("monitor/get-monitor-by-id-404.json");
        var request = fileUtils.readResourceFile("monitor/post-request-monitor-200.json");
        var producerToSave = monitorUtils.newMonitorToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL2)
                        .content(request).header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(verifyError));

    }

    @SneakyThrows
    @Order(7)
    @DisplayName("PUT v1/monitor updates a monitor")
    @Test
    void update_UpdatesMonitor_WhenSuccessful() {
        var request = fileUtils.readResourceFile("monitor/put-monitor-by-id-200.json");

        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL2)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Order(8)
    @DisplayName("update throws NotFound when monitor is not found")
    @Test
    void update_ThrowsNotFound_WhenMonitorIsNotFound() throws Exception {

        var request = fileUtils.readResourceFile("monitor/put-monitor-by-id-404.json");
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL2)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Order(9)
    @DisplayName("DELETE v1/monitors/1 creates a monitors")
    @Test
    void delete_RemoveMonitor_WhenSuccessful() throws Exception {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var hertz = monitorList.getFirst().getHertz();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL2+"/{hertz}", hertz))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Order(10)
    @DisplayName("DELETE v1/monitors/99 throws NotFound when monitors is not found")
    @Test
    void delete_ThrowsNotFound_WhenMonitorIsNotFound() throws Exception {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var verifyError = fileUtils.readResourceFile("monitor/get-monitor-by-id-404.json");
        var hertz = 99L;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL2+"/{hertz}", hertz))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));

    }
    
}