package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.Monitor;
import Matheuszin_springboot.commons.MonitorUtils;
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
class MonitorHardCodedRepositoryTest {

    @InjectMocks
    private MonitorHardCodedRepository repository;
    @Mock
    private MonitorData monitorData;
    @InjectMocks
    private MonitorUtils monitorUtils;
    private List<Monitor> monitorList = new ArrayList<>();

    @BeforeEach
    void init() {
        {
            monitorList = monitorUtils.newMonitorList();
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