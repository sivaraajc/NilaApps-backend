package com.edrevel.learningpath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LearningPathApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void componentsEndpointReturnsSeedData() throws Exception {
        mockMvc.perform(get("/api/components"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(6))
                .andExpect(jsonPath("$.items[0].id").exists());
    }

    @Test
    void learningPathCanBeLoaded() throws Exception {
        mockMvc.perform(get("/api/learning-paths/lp-sat-adaptive-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SAT Adaptive Path"))
                .andExpect(jsonPath("$.nodes.length()").value(5));
    }

    @Test
    void evaluateRoutesToAdvancedOnPass() throws Exception {
        String body =
                """
                {
                  "currentNodeId": "node-math-1",
                  "progress": {
                    "node-math-1": {
                      "completed": true,
                      "passed": true,
                      "score": 85
                    }
                  }
                }
                """;
        mockMvc.perform(post("/api/learning-paths/lp-sat-adaptive-001/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nextNodeId").value("node-math-2-advanced"));
    }
}
