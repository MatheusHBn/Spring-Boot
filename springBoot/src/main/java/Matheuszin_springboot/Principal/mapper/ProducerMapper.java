package Matheuszin_springboot.Principal.mapper;

import Matheuszin_springboot.Principal.Response.ProducerGetResponse;
import Matheuszin_springboot.Principal.domain.Producer;
import Matheuszin_springboot.Principal.request.ProducerPostRequest;
import Matheuszin_springboot.Principal.request.ProducerPutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "localDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1000))")
    Producer toProducer(ProducerPutRequest request);
    Producer toProducer(ProducerPostRequest producerPostRequest);

    ProducerGetResponse toProducerGetResponse(Producer producer);

    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producer);

}
