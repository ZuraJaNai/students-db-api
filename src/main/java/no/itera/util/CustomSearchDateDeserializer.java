package no.itera.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import no.itera.model.SearchDate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

public class CustomSearchDateDeserializer extends JsonDeserializer<SearchDate> {

    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("MM.yyyy")
            .parseDefaulting(DAY_OF_MONTH, 15)
            .toFormatter();

    String singleDateRegex = "[0-9]{2}.[0-9]{4}";
    String doubleDateRegex = "[0-9]{2}[.][0-9]{4}[-][0-9]{2}[.][0-9]{4}";

    @Override
    public SearchDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String date = jp.getValueAsString();
        if(date.matches(singleDateRegex)){
            return new SearchDate(LocalDate.parse(date,formatter));
        }
        else if(date.matches(doubleDateRegex)){
            List<String> dates = Arrays.asList(date.split("-"));
            return new SearchDate(LocalDate.parse(dates.get(0),formatter),LocalDate.parse(dates.get(1),formatter));
        }
        else{
            throw new InvalidFormatException(jp,"Error! Format is invalid",date,String.class);
        }
    }
}
