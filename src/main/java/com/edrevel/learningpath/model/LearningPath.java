package com.edrevel.learningpath.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "learning_paths")
public class LearningPath {

    @Id
    @Column(length = 100)
    private String id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 20)
    private String status;

    private Integer version;

    private Double canvasZoom;
    private Double canvasOffsetX;
    private Double canvasOffsetY;

    @OneToMany(mappedBy = "learningPath", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private List<PathNode> nodes = new ArrayList<>();

    @OneToMany(mappedBy = "learningPath", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("priority ASC, id ASC")
    private List<PathEdge> edges = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getCanvasZoom() {
        return canvasZoom;
    }

    public void setCanvasZoom(Double canvasZoom) {
        this.canvasZoom = canvasZoom;
    }

    public Double getCanvasOffsetX() {
        return canvasOffsetX;
    }

    public void setCanvasOffsetX(Double canvasOffsetX) {
        this.canvasOffsetX = canvasOffsetX;
    }

    public Double getCanvasOffsetY() {
        return canvasOffsetY;
    }

    public void setCanvasOffsetY(Double canvasOffsetY) {
        this.canvasOffsetY = canvasOffsetY;
    }

    public List<PathNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<PathNode> nodes) {
        this.nodes = nodes;
    }

    public List<PathEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<PathEdge> edges) {
        this.edges = edges;
    }
}
