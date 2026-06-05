package com.edrevel.learningpath.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record LearningPathDto(
        @Size(max = 100) String id,
        @NotBlank @Size(max = 150) String name,
        @Size(max = 1000) String description,
        @NotBlank String status,
        Integer version,
        CanvasDto canvas,
        @NotNull @Valid List<PathNodeDto> nodes,
        @NotNull @Valid List<PathEdgeDto> edges) {}
