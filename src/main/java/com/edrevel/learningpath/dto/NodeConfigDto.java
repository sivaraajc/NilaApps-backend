package com.edrevel.learningpath.dto;

public record NodeConfigDto(
        Integer approximateDurationMinutes,
        AssessmentMetadataDto assessment) {}
