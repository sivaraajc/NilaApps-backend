package com.edrevel.learningpath.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConditionRuleDto(
        @NotBlank @Size(max = 100) String id,
        @NotBlank String sourceType,
        @NotBlank @Size(max = 100) String sourceNodeId,
        @NotBlank String metric,
        @NotBlank String operator,
        Object value,
        ScoreRangeDto range) {}
