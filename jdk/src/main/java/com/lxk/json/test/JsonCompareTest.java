package com.lxk.json.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author LiXuekai on 2022/6/2
 */
public class JsonCompareTest {
    private String json1= "";


    private String json2 = "";


    @Test
    public void compare() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode1 = mapper.readTree(json1);
        JsonNode jsonNode2 = mapper.readTree(json2);


        Assert.assertEquals(jsonNode1, jsonNode2);


    }


}
