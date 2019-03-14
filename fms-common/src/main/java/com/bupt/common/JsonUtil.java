package com.bupt.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {

    private static Logger log = LoggerFactory.getLogger(JsonUtil.class);

    public static Map<String, Object> getMapByExtra(String extra) {
        if (StringUtils.isEmpty(extra)) {
            return null;
        }
        ObjectMapper om = new ObjectMapper();
        try {
            Map<String, Object> map1 =
                    om.readValue(extra, om.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
            return map1;
        } catch (Exception e) {
            log.warn("parse json extra exception, extra=" + extra, e);
        }
        return null;
    }

    public static String toJSON(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            SimpleModule module = new SimpleModule();
            module.addSerializer(String.class, new StringUnicodeSerializer());
            objectMapper.registerModule(module);
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            log.error("toJSON error:", ex);
        }
        return null;
    }

    public static class StringUnicodeSerializer extends JsonSerializer<String> {

        private final char[] hexChars = "0123456789ABCDEF".toCharArray();
        private final int[] escapeCodes = CharTypes.get7BitOutputEscapes();

        private void writeUnicodeEscape(JsonGenerator gen, char c) throws IOException {
            gen.writeRaw('\\');
            gen.writeRaw('u');
            gen.writeRaw(hexChars[(c >> 12) & 0xF]);
            gen.writeRaw(hexChars[(c >> 8) & 0xF]);
            gen.writeRaw(hexChars[(c >> 4) & 0xF]);
            gen.writeRaw(hexChars[c & 0xF]);
        }

        private void writeShortEscape(JsonGenerator gen, char c) throws IOException {
            gen.writeRaw('\\');
            gen.writeRaw(c);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void serialize(String str, JsonGenerator gen, SerializerProvider provider)
                throws IOException, JsonProcessingException {
            int status = ((JsonWriteContext) gen.getOutputContext()).writeValue();
            switch (status) {
                case JsonWriteContext.STATUS_OK_AFTER_COLON:
                    gen.writeRaw(':');
                    break;
                case JsonWriteContext.STATUS_OK_AFTER_COMMA:
                    gen.writeRaw(',');
                    break;
                case JsonWriteContext.STATUS_EXPECT_NAME:
                    throw new JsonGenerationException("Can not write string value here");
                default: {
                    break;
                }
            }
            gen.writeRaw('"');
            for (char c : str.toCharArray()) {
                if (c >= 0x80) {
                    writeUnicodeEscape(gen, c);
                } else {

                    int code = (c < escapeCodes.length ? escapeCodes[c] : 0);
                    if (code == 0) {
                        gen.writeRaw(c); // 姝ゅ涓嶇敤杞箟
                    } else if (code < 0) {
                        writeUnicodeEscape(gen, (char) (-code - 1));
                    } else {
                        writeShortEscape(gen, (char) code);
                    }
                }
            }
            gen.writeRaw('"');
        }

    }

    /**
     *
     * 
     * @param content
     * @return
     */
    public static boolean isJson(String content) {
        try {
            JSON.parse(content);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
