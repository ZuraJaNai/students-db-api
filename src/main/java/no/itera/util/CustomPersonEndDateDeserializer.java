package no.itera.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;


public class CustomPersonEndDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        LocalDate date = LocalDate.parse(p.getValueAsString(), DateConstants.dateFormatterDeserialization);
        return date.withDayOfMonth(date.lengthOfMonth());
    }
}
