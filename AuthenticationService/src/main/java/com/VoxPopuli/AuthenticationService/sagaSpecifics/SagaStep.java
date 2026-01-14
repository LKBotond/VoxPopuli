package com.VoxPopuli.AuthenticationService.sagaSpecifics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@AllArgsConstructor
public class SagaStep {
    private final String name;
    private final Runnable rollback;

    public void rollback() {
        try {
            rollback.run();
        } catch (Exception e) {
            log.error("Step " + name + "could not be rolled back", e);
        }
    }
}
