package no.itera.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;

public class CustomPersonEndDateSerializer extends StdSerializer<LocalDate> {

    public CustomPersonEndDateSerializer() {
        this(null);
    }

    public CustomPersonEndDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateConstants.dateFormatterSerizalisztion.format(date));
    }
}