package com.edrevel.learningpath.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PathEdgeDto(
        @NotBlank @Size(max = 100) String id,
        @NotBlank @Size(max = 100) String sourceNodeId,
        @NotBlank @Size(max = 100) String targetNodeId,
        @Size(max = 150) String label,
        Integer priority,
        Boolean isDefault,
        @NotNull @Valid ConditionGroupDto conditions) {}
