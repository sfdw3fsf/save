package vn.vnpt.common.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
/*import com.google.gson.Gson;
import com.google.gson.GsonBuilder;*/

import java.io.IOException;
import java.util.List;

public class JsonConvert {

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        return objectMapper;
    }

    public static String serialize(Object object) throws IOException {
        return getObjectMapper().writeValueAsString(object);
    }

	/*
	 * public static <T> String convertListToJson(List<T> list){ Gson gson = new
	 * GsonBuilder().setPrettyPrinting().create(); String json = gson.toJson(list);
	 * return json; }
	 */    /**
     * Parse json string to a collection (include: Map, List)
     */
    public static <T> T deserialize(String json) throws IOException {
        return getObjectMapper().readValue(json, new TypeReference<T>(){});
    }

    /**
     * Parse json string to a simple object
     */
    public static <T> T deserialize(String json, Class<T> classz) throws IOException {
        return getObjectMapper().readValue(json, classz);
    }
}
