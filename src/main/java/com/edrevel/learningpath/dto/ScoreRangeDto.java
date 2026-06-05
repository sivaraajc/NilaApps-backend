package com.edrevel.learningpath.dto;

import jakarta.validation.constraints.NotNull;

public record ScoreRangeDto(
        @NotNull Double min,
        @NotNull Double max,
        Boolean minInclusive,
        Boolean maxInclusive) {}
