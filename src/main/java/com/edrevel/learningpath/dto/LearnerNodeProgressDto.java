package com.edrevel.learningpath.dto;

public record LearnerNodeProgressDto(
        Boolean completed,
        Boolean passed,
        Double score,
        Double timeSpentMinutes,
        Double percentageCompletion) {}
