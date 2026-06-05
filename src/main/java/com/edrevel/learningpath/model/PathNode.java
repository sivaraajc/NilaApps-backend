package com.edrevel.learningpath.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "path_nodes")
public class PathNode {

    @Id
    @Column(length = 100)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id", nullable = false)
    private LearningPath learningPath;

    @Column(nullable = false, length = 100)
    private String componentId;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 150)
    private String label;

    @Column(length = 1000)
    private String description;

    private Double positionX;
    private Double positionY;

    private Integer configDurationMinutes;
    private Integer configAssessmentMaxScore;
    private Integer configAssessmentPassingScore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LearningPath getLearningPath() {
        return learningPath;
    }

    public void setLearningPath(LearningPath learningPath) {
        this.learningPath = learningPath;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPositionX() {
        return positionX;
    }

    public void setPositionX(Double positionX) {
        this.positionX = positionX;
    }

    public Double getPositionY() {
        return positionY;
    }

    public void setPositionY(Double positionY) {
        this.positionY = positionY;
    }

    public Integer getConfigDurationMinutes() {
        return configDurationMinutes;
    }

    public void setConfigDurationMinutes(Integer configDurationMinutes) {
        this.configDurationMinutes = configDurationMinutes;
    }

    public Integer getConfigAssessmentMaxScore() {
        return configAssessmentMaxScore;
    }

    public void setConfigAssessmentMaxScore(Integer configAssessmentMaxScore) {
        this.configAssessmentMaxScore = configAssessmentMaxScore;
    }

    public Integer getConfigAssessmentPassingScore() {
        return configAssessmentPassingScore;
    }

    public void setConfigAssessmentPassingScore(Integer configAssessmentPassingScore) {
        this.configAssessmentPassingScore = configAssessmentPassingScore;
    }
}
