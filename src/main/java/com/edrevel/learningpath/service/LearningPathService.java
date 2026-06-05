package com.edrevel.learningpath.service;

import com.edrevel.learningpath.dto.EvaluateRequestDto;
import com.edrevel.learningpath.dto.EvaluateResponseDto;
import com.edrevel.learningpath.dto.LearningPathDto;
import com.edrevel.learningpath.dto.PathEdgeDto;
import com.edrevel.learningpath.mapper.LearningPathMapper;
import com.edrevel.learningpath.model.LearningPath;
import com.edrevel.learningpath.repository.LearningPathRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LearningPathService {

    private final LearningPathRepository repository;
    private final LearningPathMapper mapper;
    private final ConditionEvaluatorService evaluator;

    public LearningPathService(
            LearningPathRepository repository,
            LearningPathMapper mapper,
            ConditionEvaluatorService evaluator) {
        this.repository = repository;
        this.mapper = mapper;
        this.evaluator = evaluator;
    }

    @Transactional(readOnly = true)
    public LearningPathDto getById(String id) {
        LearningPath entity =
                repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Learning path not found"));
        return mapper.toDto(entity);
    }

    @Transactional
    public LearningPathDto save(LearningPathDto dto) {
        validateGraph(dto);
        LearningPath entity;
        if (dto.id() != null && repository.existsById(dto.id())) {
            entity = repository.findById(dto.id()).orElseThrow();
            mapper.updateEntity(entity, dto);
        } else {
            entity = mapper.toEntity(dto);
        }
        LearningPath saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public EvaluateResponseDto evaluate(String pathId, EvaluateRequestDto request) {
        LearningPathDto path = getById(pathId);
        List<PathEdgeDto> outgoing = path.edges().stream()
                .filter(e -> e.sourceNodeId().equals(request.currentNodeId()))
                .sorted(Comparator.comparingInt(e -> e.priority() != null ? e.priority() : Integer.MAX_VALUE))
                .toList();

        for (PathEdgeDto edge : outgoing) {
            if (Boolean.TRUE.equals(edge.isDefault())
                    || evaluator.evaluateGroup(edge.conditions(), request.progress())) {
                return new EvaluateResponseDto(
                        edge.targetNodeId(),
                        edge.id(),
                        edge.label() != null ? edge.label() : "Matched edge " + edge.id());
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No matching transition from current node");
    }

    private void validateGraph(LearningPathDto dto) {
        if (dto.nodes() == null || dto.nodes().size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least two nodes are required");
        }
        if (dto.edges() == null || dto.edges().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one edge is required");
        }
    }
}
