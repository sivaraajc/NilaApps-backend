package com.edrevel.learningpath.dto;

import jakarta.validation.constraints.Min;

public record UnitMetadataDto(@Min(1) Integer recommendedMinutes) {}
