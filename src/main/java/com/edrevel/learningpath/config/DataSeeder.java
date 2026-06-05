package com.edrevel.learningpath.config;

import com.edrevel.learningpath.dto.LearningPathDto;
import com.edrevel.learningpath.model.ContentComponent;
import com.edrevel.learningpath.repository.ContentComponentRepository;
import com.edrevel.learningpath.repository.LearningPathRepository;
import com.edrevel.learningpath.service.LearningPathService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ContentComponentRepository componentRepository;
    private final LearningPathRepository learningPathRepository;
    private final LearningPathService learningPathService;
    private final ObjectMapper objectMapper;

    public DataSeeder(
            ContentComponentRepository componentRepository,
            LearningPathRepository learningPathRepository,
            LearningPathService learningPathService,
            ObjectMapper objectMapper) {
        this.componentRepository = componentRepository;
        this.learningPathRepository = learningPathRepository;
        this.learningPathService = learningPathService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (componentRepository.count() == 0) {
            seedComponents();
        }
        seedExamplePath();
    }

    private void seedComponents() {
        addComponent("cmp-assess-math-1", "Math Module 1 Assessment",
                "Baseline math diagnostic used to route learners.", "assessment", 35, 100, 50, null);
        addComponent("cmp-unit-math-2-easy", "Math Module 2 - Easy",
                "Foundational math remediation unit.", "unit", 35, null, null, 30);
        addComponent("cmp-unit-math-2-advanced", "Math Module 2 - Advanced",
                "Accelerated math enrichment unit.", "unit", 35, null, null, 40);
        addComponent("cmp-assess-reading-1", "Reading Module 1 Assessment",
                "Reading comprehension baseline assessment.", "assessment", 30, 100, 60, null);
        addComponent("cmp-unit-reading-remediation", "Reading Remediation Unit",
                "Targeted reading skills practice.", "unit", 45, null, null, 40);
        addComponent("cmp-unit-reading-advanced", "Reading Advanced Unit",
                "Advanced reading and analysis.", "unit", 50, null, null, 45);
    }

    private void addComponent(
            String id,
            String title,
            String desc,
            String type,
            int minutes,
            Integer maxScore,
            Integer passingScore,
            Integer recommended) {
        ContentComponent c = new ContentComponent();
        c.setId(id);
        c.setTitle(title);
        c.setShortDescription(desc);
        c.setType(type);
        c.setApproximateDurationMinutes(minutes);
        c.setAssessmentMaxScore(maxScore);
        c.setAssessmentPassingScore(passingScore);
        c.setUnitRecommendedMinutes(recommended);
        componentRepository.save(c);
    }

    private void seedExamplePath() throws Exception {
        ClassPathResource resource = new ClassPathResource("seed/learning-path.example.json");
        try (InputStream in = resource.getInputStream()) {
            LearningPathDto example = objectMapper.readValue(in, LearningPathDto.class);
            learningPathRepository.findById(example.getId()).ifPresent(learningPathRepository::delete);
            learningPathService.save(example);
        }
    }
}
