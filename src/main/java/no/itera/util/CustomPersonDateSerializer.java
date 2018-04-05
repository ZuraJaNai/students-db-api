package no.itera.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomPersonDateSerializer extends StdSerializer<Date> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM.yyyy");

    public CustomPersonDateSerializer() {
        this(null);
    }

    public CustomPersonDateSerializer(Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(dateFormat.format(date));
    }
}
