package com.edrevel.learningpath.controller;

import com.edrevel.learningpath.dto.EvaluateRequestDto;
import com.edrevel.learningpath.dto.EvaluateResponseDto;
import com.edrevel.learningpath.dto.LearningPathDto;
import com.edrevel.learningpath.service.LearningPathService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/learning-paths")
public class LearningPathController {

    private final LearningPathService learningPathService;

    public LearningPathController(LearningPathService learningPathService) {
        this.learningPathService = learningPathService;
    }

    @PostMapping
    public LearningPathDto save(@Valid @RequestBody LearningPathDto dto) {
        return learningPathService.save(dto);
    }

    @GetMapping("/{id}")
    public LearningPathDto getById(@PathVariable String id) {
        return learningPathService.getById(id);
    }

    @PostMapping("/{id}/evaluate")
    public EvaluateResponseDto evaluate(
            @PathVariable String id, @Valid @RequestBody EvaluateRequestDto request) {
        return learningPathService.evaluate(id, request);
    }
}
