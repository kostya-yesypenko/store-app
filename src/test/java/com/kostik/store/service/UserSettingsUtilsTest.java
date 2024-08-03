package com.kostik.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.kostik.store.service.userSettings.UserDataUtils;

public class UserSettingsUtilsTest {

    @Test
    public void testMapToString() {
        UserDataUtils utils = new UserDataUtils();
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");

        String result = utils.MapToString(data);
        assertTrue(result.contains("key1:value1;"));
        assertTrue(result.contains("key2:value2;"));
    }

    @Test
    public void testStringToMap() {
        UserDataUtils utils = new UserDataUtils();
        String input = "key1:value1;key2:value2";

        Map<String, String> result = utils.StringToMap(input);

        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }

    @Test
    public void testStringToMapEmptyString() {
        UserDataUtils utils = new UserDataUtils();
        String input = "";

        Map<String, String> result = utils.StringToMap(input);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testMapToStringEmptyMap() {
        UserDataUtils utils = new UserDataUtils();
        Map<String, String> data = new HashMap<>();

        String result = utils.MapToString(data);
        assertEquals("", result);
    }

    @Test
    public void testStringToMapInvalidFormat() {
        UserDataUtils utils = new UserDataUtils();
        String input = "key1value1;key2:value2";

        Map<String, String> result = utils.StringToMap(input);

        assertEquals(1, result.size());
        assertNull(result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }

    @Test
    public void testMapToStringSpecialCharacters() {
        UserDataUtils utils = new UserDataUtils();
        Map<String, String> data = new HashMap<>();
        data.put("key:with:colons", "value:with:colons");
        data.put("key;with;semicolons", "value;with;semicolons");

        String result = utils.MapToString(data);
        assertTrue(result.contains("key:with:colons:value:with:colons;"));
        assertTrue(result.contains("key;with;semicolons:value;with;semicolons;"));
    }
}
