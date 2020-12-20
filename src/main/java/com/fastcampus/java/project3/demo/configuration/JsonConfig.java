package com.fastcampus.java.project3.demo.configuration;

import com.fastcampus.java.project3.demo.configuration.serializer.BirthdaySerializer;
import com.fastcampus.java.project3.demo.domain.Birthday;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JsonConfig {

    //MAppingJackson2HttpMessageConverter은 Spring의 Controller에서 반환하는 결과를 Json으로 만드는데, 해당 포맷을 커스터마이징할 수 있게 해줌
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    //ObjectMapper를 사용하여 BirthdayModule을 등록
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BirthdayModule());
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }

    //module에 serializer를 등록
    //SimpleModule 커스텀으로 만든 Serializer를 Converter에 등록하기 위해서 모듈화해줌
    static class BirthdayModule extends SimpleModule{
        BirthdayModule() {
            super();
            addSerializer(Birthday.class, new BirthdaySerializer());
        }
    }
}
