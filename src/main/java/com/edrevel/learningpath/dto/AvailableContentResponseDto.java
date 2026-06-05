package com.edrevel.learningpath.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AvailableContentResponseDto(
        @NotNull @Valid List<ComponentDto> items,
        @NotNull Integer totalCount) {}
