package com.edrevel.learningpath.dto;

import jakarta.validation.constraints.NotNull;

public record PositionDto(@NotNull Double x, @NotNull Double y) {}
