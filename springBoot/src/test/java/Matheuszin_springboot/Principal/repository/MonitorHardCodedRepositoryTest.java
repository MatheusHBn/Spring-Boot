package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.Monitor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class MonitorHardCodedRepositoryTest {

    @InjectMocks
    private MonitorHardCodedRepository repository;
    @Mock
    private MonitorData monitorData;
    private List<Monitor> monitorList = new ArrayList<>();

    @BeforeEach
    void init() {
        {
            var alienware = new Monitor("Alienware", 75L, LocalDateTime.now());
            var lg = new Monitor("LG", 175L, LocalDateTime.now());
            var pichau = new Monitor("Pichau", 240L, LocalDateTime.now());
            monitorList.addAll(List.of(alienware, lg, pichau));
        }
    }

    @Order(1)
    @DisplayName("findAll should returns a list with all monitors")
    @Test
    void findAll_ReturnsAllMonitors_WhenSuccessful() {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var monitors = repository.findAll();
        org.assertj.core.api.Assertions.assertThat(monitors).hasSameElementsAs(monitors);
    }

    @Order(2)
    @DisplayName("findByHertz should returns a monitor with given Hertz")
    @Test
    void findByHertz_ReturnsAllHertzMonitors_WhenSuccessful() {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var monitorsList = monitorList.getFirst();
        var monitors = repository.findByHertz(monitorsList.getHertz());
        Assertions.assertThat(monitors).isPresent().contains(monitorsList);
    }

    @Order(3)
    @DisplayName("findByName should returns empty list when name is null")
    @Test
    void findByName_ReturnsEmptyListMonitor_WhenIsNull() {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var monitors = repository.findByName(null);
        Assertions.assertThat(monitors).isNotNull().isEmpty();
    }

    @Order(4)
    @DisplayName("findByName should returns list with found object when name exists")
    @Test
    void findByName_ReturnsListMonitor_WhenNameIsFound() {
        BDDMockito.when(monitorData.getMonitorList()).thenReturn(monitorList);
        var expectedMonitors = monitorList.getFirst();
        var monitors = repository.findByName(expectedMonitors.getName());
        Assertions.assertThat(monitors).hasSize(1);

    }
}