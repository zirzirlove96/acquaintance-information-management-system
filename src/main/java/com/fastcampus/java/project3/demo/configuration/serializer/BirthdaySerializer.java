package com.fastcampus.java.project3.demo.configuration.serializer;

import com.fastcampus.java.project3.demo.domain.Birthday;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

//Birthday Serializer
public class BirthdaySerializer extends JsonSerializer<Birthday> {
    @Override
    public void serialize(Birthday value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value != null) {
            //localdate형식으로 리턴을 해준다는 의미이다.
            gen.writeObject(LocalDate.of(value.getYear(), value.getMonth(), value.getDay()));
        }
    }
}
