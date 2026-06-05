package com.edrevel.learningpath.service;

import com.edrevel.learningpath.dto.AvailableContentResponseDto;
import com.edrevel.learningpath.dto.ComponentDto;
import com.edrevel.learningpath.mapper.LearningPathMapper;
import com.edrevel.learningpath.repository.ContentComponentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComponentService {

    private final ContentComponentRepository repository;
    private final LearningPathMapper mapper;

    public ComponentService(ContentComponentRepository repository, LearningPathMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public AvailableContentResponseDto getAvailableContent() {
        List<ComponentDto> items = repository.findAll().stream().map(mapper::toDto).toList();
        return new AvailableContentResponseDto(items, items.size());
    }
}
