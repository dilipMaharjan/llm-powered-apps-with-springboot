package com.dmed.llm_powered_apps_with_springboot.model;

import java.util.List;

public record MovieLeadActor(
        String movieTitle,
        List<String> leadActors
) {
}