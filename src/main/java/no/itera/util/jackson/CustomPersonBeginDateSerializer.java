package no.itera.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import no.itera.util.DateConstants;

import java.io.IOException;
import java.time.LocalDate;

public class CustomPersonBeginDateSerializer extends StdSerializer<LocalDate> {

    public CustomPersonBeginDateSerializer() {
        this(null);
    }

    public CustomPersonBeginDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateConstants.dateFormatterSerizalisztion.format(date));
    }
}
