package com.edrevel.learningpath.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComponentDto(
        @NotBlank @Size(max = 100) String id,
        @NotBlank @Size(max = 150) String title,
        @NotBlank @Size(max = 280) String shortDescription,
        @NotBlank String type,
        @NotNull @Min(1) @Max(600) Integer approximateDurationMinutes,
        ComponentMetadataDto metadata) {}
