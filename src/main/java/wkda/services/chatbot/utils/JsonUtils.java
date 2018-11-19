package wkda.services.chatbot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@UtilityClass
@Slf4j
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException{
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw e;
        }
    }
}
