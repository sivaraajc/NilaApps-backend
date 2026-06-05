package com.edrevel.learningpath.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ConditionGroupDto(
        @NotBlank String operator,
        @NotNull @Valid List<ConditionRuleDto> rules) {}
