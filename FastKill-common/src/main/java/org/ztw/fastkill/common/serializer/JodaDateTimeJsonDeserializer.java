package org.ztw.fastkill.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * 日期反序列化
 */
public class JodaDateTimeJsonDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dataString = p.readValueAs(String.class);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return DateTime.parse(dataString,formatter);
    }
}
