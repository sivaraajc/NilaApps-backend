package com.edrevel.learningpath.mapper;

import com.edrevel.learningpath.dto.AssessmentMetadataDto;
import com.edrevel.learningpath.dto.CanvasDto;
import com.edrevel.learningpath.dto.ComponentDto;
import com.edrevel.learningpath.dto.ComponentMetadataDto;
import com.edrevel.learningpath.dto.ConditionGroupDto;
import com.edrevel.learningpath.dto.LearningPathDto;
import com.edrevel.learningpath.dto.NodeConfigDto;
import com.edrevel.learningpath.dto.PathEdgeDto;
import com.edrevel.learningpath.dto.PathNodeDto;
import com.edrevel.learningpath.dto.PositionDto;
import com.edrevel.learningpath.dto.UnitMetadataDto;
import com.edrevel.learningpath.model.ContentComponent;
import com.edrevel.learningpath.model.LearningPath;
import com.edrevel.learningpath.model.PathEdge;
import com.edrevel.learningpath.model.PathNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class LearningPathMapper {

    private final ObjectMapper objectMapper;

    public LearningPathMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ComponentDto toDto(ContentComponent entity) {
        ComponentMetadataDto metadata = null;
        if ("assessment".equals(entity.getType())) {
            metadata = new ComponentMetadataDto(
                    new AssessmentMetadataDto(entity.getAssessmentMaxScore(), entity.getAssessmentPassingScore()),
                    null);
        } else if (entity.getUnitRecommendedMinutes() != null) {
            metadata = new ComponentMetadataDto(null, new UnitMetadataDto(entity.getUnitRecommendedMinutes()));
        }
        return new ComponentDto(
                entity.getId(),
                entity.getTitle(),
                entity.getShortDescription(),
                entity.getType(),
                entity.getApproximateDurationMinutes(),
                metadata);
    }

    public LearningPathDto toDto(LearningPath entity) {
        CanvasDto canvas = null;
        if (entity.getCanvasZoom() != null || entity.getCanvasOffsetX() != null || entity.getCanvasOffsetY() != null) {
            canvas = new CanvasDto(entity.getCanvasZoom(), entity.getCanvasOffsetX(), entity.getCanvasOffsetY());
        }
        return new LearningPathDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getVersion(),
                canvas,
                entity.getNodes().stream().map(this::toDto).toList(),
                entity.getEdges().stream().map(this::toDto).toList());
    }

    public PathNodeDto toDto(PathNode node) {
        NodeConfigDto config = null;
        if (node.getConfigDurationMinutes() != null || node.getConfigAssessmentMaxScore() != null) {
            AssessmentMetadataDto assessment = null;
            if (node.getConfigAssessmentMaxScore() != null) {
                assessment = new AssessmentMetadataDto(
                        node.getConfigAssessmentMaxScore(), node.getConfigAssessmentPassingScore());
            }
            config = new NodeConfigDto(node.getConfigDurationMinutes(), assessment);
        }
        return new PathNodeDto(
                node.getId(),
                node.getComponentId(),
                node.getType(),
                node.getLabel(),
                node.getDescription(),
                new PositionDto(node.getPositionX(), node.getPositionY()),
                config);
    }

    public PathEdgeDto toDto(PathEdge edge) {
        try {
            ConditionGroupDto conditions =
                    objectMapper.readValue(edge.getConditionsJson(), ConditionGroupDto.class);
            return new PathEdgeDto(
                    edge.getId(),
                    edge.getSourceNodeId(),
                    edge.getTargetNodeId(),
                    edge.getLabel(),
                    edge.getPriority(),
                    edge.getIsDefault(),
                    conditions);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid conditions JSON for edge " + edge.getId(), e);
        }
    }

    public LearningPath toEntity(LearningPathDto dto) {
        LearningPath entity = new LearningPath();
        entity.setId(dto.id() != null ? dto.id() : generateId());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setStatus(dto.status());
        entity.setVersion(dto.version() != null ? dto.version() : 1);
        if (dto.canvas() != null) {
            entity.setCanvasZoom(dto.canvas().zoom());
            entity.setCanvasOffsetX(dto.canvas().offsetX());
            entity.setCanvasOffsetY(dto.canvas().offsetY());
        }
        for (PathNodeDto nodeDto : dto.nodes()) {
            PathNode node = toNodeEntity(nodeDto, entity);
            entity.getNodes().add(node);
        }
        for (PathEdgeDto edgeDto : dto.edges()) {
            PathEdge edge = toEdgeEntity(edgeDto, entity);
            entity.getEdges().add(edge);
        }
        return entity;
    }

    public void updateEntity(LearningPath entity, LearningPathDto dto) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setStatus(dto.status());
        entity.setVersion(dto.version() != null ? dto.version() : entity.getVersion());
        if (dto.canvas() != null) {
            entity.setCanvasZoom(dto.canvas().zoom());
            entity.setCanvasOffsetX(dto.canvas().offsetX());
            entity.setCanvasOffsetY(dto.canvas().offsetY());
        }
        entity.getNodes().clear();
        entity.getEdges().clear();
        for (PathNodeDto nodeDto : dto.nodes()) {
            entity.getNodes().add(toNodeEntity(nodeDto, entity));
        }
        for (PathEdgeDto edgeDto : dto.edges()) {
            entity.getEdges().add(toEdgeEntity(edgeDto, entity));
        }
    }

    private PathNode toNodeEntity(PathNodeDto dto, LearningPath path) {
        PathNode node = new PathNode();
        node.setId(dto.id());
        node.setLearningPath(path);
        node.setComponentId(dto.componentId());
        node.setType(dto.type());
        node.setLabel(dto.label());
        node.setDescription(dto.description());
        node.setPositionX(dto.position().x());
        node.setPositionY(dto.position().y());
        if (dto.config() != null) {
            node.setConfigDurationMinutes(dto.config().approximateDurationMinutes());
            if (dto.config().assessment() != null) {
                node.setConfigAssessmentMaxScore(dto.config().assessment().maxScore());
                node.setConfigAssessmentPassingScore(dto.config().assessment().passingScore());
            }
        }
        return node;
    }

    private PathEdge toEdgeEntity(PathEdgeDto dto, LearningPath path) {
        PathEdge edge = new PathEdge();
        edge.setId(dto.id());
        edge.setLearningPath(path);
        edge.setSourceNodeId(dto.sourceNodeId());
        edge.setTargetNodeId(dto.targetNodeId());
        edge.setLabel(dto.label());
        edge.setPriority(dto.priority());
        edge.setIsDefault(dto.isDefault());
        try {
            edge.setConditionsJson(objectMapper.writeValueAsString(dto.conditions()));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid edge conditions", e);
        }
        return edge;
    }

    private String generateId() {
        return "lp-" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}
