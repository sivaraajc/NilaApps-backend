package com.edrevel.learningpath.dto;

public record EvaluateResponseDto(
        String nextNodeId,
        String matchedEdgeId,
        String reason) {}
