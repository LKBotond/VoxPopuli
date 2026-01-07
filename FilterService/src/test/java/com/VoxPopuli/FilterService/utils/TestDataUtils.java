package com.VoxPopuli.FilterService.utils;

import com.VoxPopuli.FilterService.dtos.CensorRequest;

public final class TestDataUtils {

    private TestDataUtils() {
    }

    public static final CensorRequest createBasicCensorRequet() {
        String profanities = "Stupid Asshole";
        return new CensorRequest(profanities);
    }

    public static final CensorRequest createSpaceSeparatedCensorRequet() {
        String profanities = "S t u p i d  a s s h o l e";
        return new CensorRequest(profanities);
    }

    public static final CensorRequest createDotSeparatedCensorRequet() {
        String profanities = "S.t.u.p.i.d  a.s.s.h.o.l.e";
        return new CensorRequest(profanities);
    }

    public static final CensorRequest createElaborateCensorRequet() {
        String profanities = "Stupid @ssh0le";
        return new CensorRequest(profanities);
    }

    public static final CensorRequest createNonOffensivetext() {
        String text = "Greetings, how has thy day been so far?";
        return new CensorRequest(text);
    }

}
