package com.edrevel.learningpath.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "content_components")
public class ContentComponent {

    @Id
    @Column(length = 100)
    private String id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 280)
    private String shortDescription;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false)
    private Integer approximateDurationMinutes;

    private Integer assessmentMaxScore;
    private Integer assessmentPassingScore;
    private Integer unitRecommendedMinutes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getApproximateDurationMinutes() {
        return approximateDurationMinutes;
    }

    public void setApproximateDurationMinutes(Integer approximateDurationMinutes) {
        this.approximateDurationMinutes = approximateDurationMinutes;
    }

    public Integer getAssessmentMaxScore() {
        return assessmentMaxScore;
    }

    public void setAssessmentMaxScore(Integer assessmentMaxScore) {
        this.assessmentMaxScore = assessmentMaxScore;
    }

    public Integer getAssessmentPassingScore() {
        return assessmentPassingScore;
    }

    public void setAssessmentPassingScore(Integer assessmentPassingScore) {
        this.assessmentPassingScore = assessmentPassingScore;
    }

    public Integer getUnitRecommendedMinutes() {
        return unitRecommendedMinutes;
    }

    public void setUnitRecommendedMinutes(Integer unitRecommendedMinutes) {
        this.unitRecommendedMinutes = unitRecommendedMinutes;
    }
}
