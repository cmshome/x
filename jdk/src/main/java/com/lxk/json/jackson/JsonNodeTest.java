package com.lxk.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxk.tool.util.FileIOUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiXuekai on 2025/6/6
 */
public class JsonNodeTest {
    private String s;
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() throws IOException {
        String path = "/Users/fang/Downloads/test/OpenTelemetryLog.txt";
        s = FileIOUtil.readFile(path, "utf-8");
    }

    @Test
    public void array() throws JsonProcessingException {
        JsonNode rootNode = mapper.readTree(s);
        JsonNode logs = rootNode.get("resourceLogs");
        System.out.println(logs);

        List<JsonNode> values = new ArrayList<>();
        rootNode.withArray("resourceLogs").forEach(values::add);
        System.out.println(values);


    }
}
