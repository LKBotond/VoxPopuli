package com.VoxPopuli.FilterService.integrationtests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.VoxPopuli.FilterService.dtos.CensorRequest;
import com.VoxPopuli.FilterService.dtos.CensorResponse;
import com.VoxPopuli.FilterService.services.Censor;
import com.VoxPopuli.FilterService.utils.TestDataUtils;

@SpringBootTest
public class CensorTest {

    @Autowired
    private Censor censor;

    @Test
    public void testBasicProfanitiesFlagging() {
        CensorRequest request = TestDataUtils.createBasicCensorRequet();
        CensorResponse response = censor.censorInput(request);
        assertTrue(response.isFlagged());
        System.out.println(response.getCaughtWords());
        assertTrue(!response.getCaughtWords().isEmpty());

    }

    @Test
    public void testSpaceProfanitiesFlagging() {
        CensorRequest request = TestDataUtils.createSpaceSeparatedCensorRequet();
        CensorResponse response = censor.censorInput(request);
        assertTrue(response.isFlagged());
        assertTrue(!response.getCaughtWords().isEmpty());
        System.out.println(response.getCaughtWords());
    }
    
    @Test
    public void testDotProfanitiesFlagging() {
        CensorRequest request = TestDataUtils.createDotSeparatedCensorRequet();
        CensorResponse response = censor.censorInput(request);
        assertTrue(response.isFlagged());
        assertTrue(!response.getCaughtWords().isEmpty());
        System.out.println(response.getCaughtWords());
    }

    @Test
    public void testElaborateProfanitiesFlagging() {
        CensorRequest request = TestDataUtils.createElaborateCensorRequet();
        CensorResponse response = censor.censorInput(request);
        assertTrue(response.isFlagged());
        assertTrue(!response.getCaughtWords().isEmpty());
        System.out.println(response.getCaughtWords());
    }

    @Test
    public void testNonProfanitiesflagging() {
        CensorRequest request = TestDataUtils.createNonOffensivetext();
        CensorResponse response = censor.censorInput(request);
        assertTrue(!response.isFlagged());
        assertTrue(response.getCaughtWords().isEmpty());
        System.out.println(response.getCaughtWords());

    }
}
