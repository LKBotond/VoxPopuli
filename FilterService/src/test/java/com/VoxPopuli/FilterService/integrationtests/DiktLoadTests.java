package com.VoxPopuli.FilterService.integrationtests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.VoxPopuli.FilterService.dtos.CensorRequest;
import com.VoxPopuli.FilterService.dtos.CensorResponse;
import com.VoxPopuli.FilterService.services.Censor;
import com.VoxPopuli.FilterService.services.ProfanityDataHandler;
import com.VoxPopuli.FilterService.utils.TestDataUtils;

@SpringBootTest
public class DiktLoadTests {

    @Autowired
    Censor censor;

    @Autowired
    ProfanityDataHandler handler;

    @Test
    void testDroppingDictionary() {
        CensorRequest request = TestDataUtils.createBasicCensorRequet();
        CensorResponse response = censor.censorInput(request);
        assertTrue(response.isFlagged());
        assertTrue(!response.getCaughtWords().isEmpty());
        handler.dropDictionary("eng");
        CensorResponse afterDeleted = censor.censorInput(request);
        assertTrue(!afterDeleted.isFlagged());
        assertTrue(afterDeleted.getCaughtWords().isEmpty());
    }

    @Test
    void testHotLoadingDict() {
        CensorRequest request = TestDataUtils.createBasicCensorRequet();
        Set<String> copy = handler.getDictByTag("eng");
        handler.dropDictionary("eng");

        CensorResponse afterDeleted = censor.censorInput(request);
        assertTrue(!afterDeleted.isFlagged());
        assertTrue(afterDeleted.getCaughtWords().isEmpty());

        handler.loadDictionary("eng", copy);
        CensorResponse afterLoaded = censor.censorInput(request);
        assertTrue(afterLoaded.isFlagged());
        assertTrue(!afterLoaded.getCaughtWords().isEmpty());
    }

}
