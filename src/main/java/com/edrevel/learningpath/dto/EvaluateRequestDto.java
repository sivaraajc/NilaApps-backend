package com.edrevel.learningpath.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

public record EvaluateRequestDto(
        @NotBlank String currentNodeId,
        Map<String, LearnerNodeProgressDto> progress) {}
