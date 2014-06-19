package com.hajix.wcn.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.hajix.wcn.model.Match;

public class MatchSerializer extends JsonSerializer<Match> {

    @Override
    public void serialize(Match match, JsonGenerator jgen, SerializerProvider sp)
            throws IOException, JsonProcessingException {
        
        jgen.writeStartObject();
        
        jgen.writeFieldName("name");
        jgen.writeString(match.name());
        
        jgen.writeFieldName("home");
        jgen.writeString(match.getHome().getPrintableName());
        
        jgen.writeFieldName("homeflag");
        jgen.writeString(match.getHome().getFlagName());
        
        jgen.writeFieldName("away");
        jgen.writeString(match.getAway().getPrintableName());
        
        jgen.writeFieldName("awayflag");
        jgen.writeString(match.getAway().getFlagName());
        
        jgen.writeFieldName("timestamp");
        jgen.writeNumber(match.getTimestamp());
        
        jgen.writeEndObject();
    }

}
