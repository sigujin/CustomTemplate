package com.vito.base.utils.network.jsonpaser;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;


public class JsonUtils {

    public static boolean isValidJson(String jsonStr, Class clazz) {
        return isValidJson(jsonStr, clazz, true);
    }

    /**
     * 验证json数据的有效性
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidJson(String jsonStr, Class clazz,
                                      boolean ignoreUnkonwnProperties) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (ignoreUnkonwnProperties)
                mapper.getDeserializationConfig().without(
                        Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            Object obj = mapper.readValue(new File(jsonStr), clazz);
            // if (obj instanceof Checkable)
            // return ((Checkable)obj).check();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getDeserializationConfig().without(
                Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static Object convertValue(String jsonStr, Class<?> cls) {
        Object obj = null;
        try {
            obj = createObjectMapper().readValue(jsonStr, cls);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }

    public static Object convertValue(String jsonStr, TypeReference reference) {
        Object obj = null;
        try {
            obj = createObjectMapper().readValue(jsonStr, reference);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }

    public static String writeValueAsString(Object in_obj) {
        String re = null;
        try {
            re = createObjectMapper().writeValueAsString(in_obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }

}
