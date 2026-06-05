package com.edrevel.learningpath.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AssessmentMetadataDto(
        @NotNull @Min(1) Integer maxScore,
        @NotNull @Min(0) Integer passingScore) {}
