package com.cos.reflect.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetNamingTest {

    @Test
    @DisplayName("키 값을 입력하면 키에 대한 세터명을 출력한다.")
    public void keyMethodTest() {

        // given
        String key = "username";

        // when
        String methodKey = keyToMethodKey(key);

        // then
        Assertions.assertEquals(methodKey, "setUsername");
    }

    public String keyToMethodKey(String key) {
        String firstKey = "set";
        String upperKey = key.substring(0, 1).toUpperCase();
        String remainKey = key.substring(1);

        return firstKey + upperKey + remainKey;
    }


}