package Matheuszin_springboot.Principal.mapper;

import Matheuszin_springboot.Principal.Response.MonitorGetResponse;
import Matheuszin_springboot.Principal.Response.MonitorPostResponse;
import Matheuszin_springboot.Principal.domain.Monitor;
import Matheuszin_springboot.Principal.request.MonitorPostRequest;
import Matheuszin_springboot.Principal.request.MonitorPutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface MonitorMapper {
    MonitorMapper INSTANCE = Mappers.getMapper(MonitorMapper.class);

    @Mapping(target = "hertz", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(540))")
    @Mapping(target = "localDateTime", expression = "java(java.time.LocalDateTime.now())")
    Monitor toMonitor(MonitorPutRequest request);
    Monitor toMonitor(MonitorPostRequest monitorPostRequest);

    MonitorGetResponse toMonitorGetResponse(Monitor monitor);

    List<MonitorGetResponse> toMonitorGetResponseList(List<Monitor> monitor);

    MonitorPostResponse toMonitorPostResponse(Monitor monitor);

}
