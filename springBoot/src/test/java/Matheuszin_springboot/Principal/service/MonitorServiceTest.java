package Matheuszin_springboot.Principal.service;

import Matheuszin_springboot.Principal.domain.Monitor;
import Matheuszin_springboot.Principal.repository.MonitorHardCodedRepository;
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
class MonitorServiceTest {

    @InjectMocks
    private MonitorService service;
    @Mock
    private MonitorHardCodedRepository repository;
    private List<Monitor> monitorList;

    @BeforeEach
    void init() {
        var alienware = new Monitor("Alienware", 75L, LocalDateTime.now());
        var lg = new Monitor("LG", 175L, LocalDateTime.now());
        var pichau = new Monitor("Pichau", 240L, LocalDateTime.now());
        monitorList = new ArrayList<>(List.of(alienware, lg, pichau));
    }


    @Order(1)
    @DisplayName("findAll should returns a list with all monitors, when name is null")
    @Test
    void findAll_ReturnsAllMonitors_WhenNameIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(monitorList);

        var monitors = service.findAll(null);

        org.assertj.core.api.Assertions.assertThat(monitors).isNotNull().hasSameElementsAs(monitorList);
    }

    @Order(2)
    @DisplayName("findAll should returns a list with all monitors, when name is null")
    @Test
    void findByName_ReturnsAllMonitors_WhenNameIsNull() {
        var monitor = monitorList.getFirst();
        var expectedMonitorsFound = singletonList(monitor);
        BDDMockito.when(repository.findByName(monitor.getName())).thenReturn(expectedMonitorsFound);
        var monitorsFound = service.findAll(monitor.getName());
        Assertions.assertThat(monitorsFound).containsAll(expectedMonitorsFound);

    }

    @Order(3)
    @DisplayName("findByName should returns empty list when name is not found")
    @Test
    void findByName_ReturnsEmptyListMonitor_WhenNameIsNotFound() {
        var name = "Nubank";
        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());
        var monitors = service.findAll(name);
        Assertions.assertThat(monitors).isNotNull().isEmpty();
    }

    @Order(4)
    @DisplayName("findByHertz returns monitor Hertz when name is null")
    @Test
    void findByHertz_ReturnsMonitorHertz_WhenNameIsNull() {
        var expectedmonitor = monitorList.getFirst();
        BDDMockito.when(repository.findByHertz(expectedmonitor.getHertz())).thenReturn(Optional.of(expectedmonitor));
        var monitors = service.findByHertzOrThrowNotFound(expectedmonitor.getHertz());
        Assertions.assertThat(monitors).isEqualTo(expectedmonitor);
    }

    @Order(5)
    @DisplayName("findByHertz throws ResponseStatusException when monitor is not found")
    @Test
    void findByHertz_ThrowsResponseStatusException_WhenMonitorIsNotFound() {
        var expectedmonitor = monitorList.getFirst();
        BDDMockito.when(repository.findByHertz(expectedmonitor.getHertz())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.findByHertzOrThrowNotFound(expectedmonitor.getHertz())).isInstanceOf(ResponseStatusException.class);
    }

    @Order(6)
    @DisplayName("save creates a monitor")
    @Test
    void save_CreatesMonitor_WhenSuccessful() {
        var monitorToSave = Monitor.builder().hertz(99L).name("Nubank").localDateTime(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(monitorToSave)).thenReturn(monitorToSave);
        var savedmonitor = service.save(monitorToSave);
        Assertions.assertThat(savedmonitor).isEqualTo(monitorToSave).hasNoNullFieldsOrProperties();
    }

    @Order(7)
    @DisplayName("save creates a monitor")
    @Test
    void delete_RemoveMonitor_WhenSuccessful() {
        var monitorToDelete = monitorList.getFirst();
        BDDMockito.when(repository.findByHertz(monitorToDelete.getHertz())).thenReturn(Optional.of(monitorToDelete));
        BDDMockito.doNothing().when(repository).deleteByHertz(monitorToDelete);
        Assertions.assertThatNoException().isThrownBy(() -> service.deleteByHertz(monitorToDelete.getHertz()));
    }

    @Order(8)
    @DisplayName("delete throws ResponseStatusException when monitor is not found")
    @Test
    void delete_ThrowsResponseStatusException_WhenMonitorIsNotFound() {
        var monitorToDelete = monitorList.getFirst();
        BDDMockito.when(repository.findByHertz(monitorToDelete.getHertz())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.deleteByHertz(monitorToDelete.getHertz())).isInstanceOf(ResponseStatusException.class);

    }

    @Order(9)
    @DisplayName("update a monitor")
    @Test
    void update_UpdatesMonitor_WhenSuccessful() {
        var monitorToUpdate = monitorList.getFirst();
        monitorToUpdate.setName("Nubank");
        BDDMockito.when(repository.findByHertz(monitorToUpdate.getHertz())).thenReturn(Optional.of(monitorToUpdate));
        BDDMockito.doNothing().when(repository).updateByHertz(monitorToUpdate);
        service.update(monitorToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(monitorToUpdate));

    }

    @Order(10)
    @DisplayName("update throws ResponseStatusException when monitor is not found")
    @Test
    void update_ThrowsResponseStatusException_WhenMonitorIsNotFound() {
        var monitorToUpdate = monitorList.getFirst();

        BDDMockito.when(repository.findByHertz(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.update(monitorToUpdate)).isInstanceOf(ResponseStatusException.class);

    }
}