package com.edrevel.learningpath.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PathNodeDto(
        @NotBlank @Size(max = 100) String id,
        @NotBlank @Size(max = 100) String componentId,
        @NotBlank String type,
        @NotBlank @Size(max = 150) String label,
        @Size(max = 1000) String description,
        @NotNull @Valid PositionDto position,
        NodeConfigDto config) {}
