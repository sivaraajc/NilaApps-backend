package com.edrevel.learningpath.service;

import com.edrevel.learningpath.dto.ConditionGroupDto;
import com.edrevel.learningpath.dto.ConditionRuleDto;
import com.edrevel.learningpath.dto.LearnerNodeProgressDto;
import com.edrevel.learningpath.dto.ScoreRangeDto;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ConditionEvaluatorService {

    public boolean evaluateGroup(ConditionGroupDto group, Map<String, LearnerNodeProgressDto> progress) {
        if (group.rules() == null || group.rules().isEmpty()) {
            return true;
        }
        boolean and = "AND".equalsIgnoreCase(group.operator());
        if (and) {
            return group.rules().stream().allMatch(rule -> evaluateRule(rule, progress));
        }
        return group.rules().stream().anyMatch(rule -> evaluateRule(rule, progress));
    }

    public boolean evaluateRule(ConditionRuleDto rule, Map<String, LearnerNodeProgressDto> progress) {
        LearnerNodeProgressDto nodeProgress = progress != null ? progress.get(rule.sourceNodeId()) : null;
        if (nodeProgress == null) {
            return false;
        }
        return switch (rule.metric()) {
            case "completion" -> compareBoolean(nodeProgress.completed(), rule.operator(), rule.value());
            case "passed" -> compareBoolean(nodeProgress.passed(), rule.operator(), rule.value());
            case "score" -> compareNumber(nodeProgress.score(), rule.operator(), rule.value());
            case "score_range" -> inRange(nodeProgress.score(), rule.range());
            case "time_spent_minutes" ->
                    compareNumber(nodeProgress.timeSpentMinutes(), rule.operator(), rule.value());
            case "percentage_completion" ->
                    compareNumber(nodeProgress.percentageCompletion(), rule.operator(), rule.value());
            default -> false;
        };
    }

    private boolean compareBoolean(Boolean actual, String operator, Object expected) {
        if (actual == null || expected == null) {
            return false;
        }
        boolean exp = Boolean.TRUE.equals(expected) || "true".equals(String.valueOf(expected));
        return switch (operator) {
            case "eq" -> actual.equals(exp);
            case "ne" -> !actual.equals(exp);
            default -> false;
        };
    }

    private boolean compareNumber(Double actual, String operator, Object expected) {
        if (actual == null || expected == null) {
            return false;
        }
        double exp = ((Number) expected).doubleValue();
        return switch (operator) {
            case "eq" -> actual == exp;
            case "ne" -> actual != exp;
            case "gt" -> actual > exp;
            case "gte" -> actual >= exp;
            case "lt" -> actual < exp;
            case "lte" -> actual <= exp;
            default -> false;
        };
    }

    private boolean inRange(Double score, ScoreRangeDto range) {
        if (score == null || range == null) {
            return false;
        }
        boolean minOk = Boolean.FALSE.equals(range.minInclusive()) ? score > range.min() : score >= range.min();
        boolean maxOk = Boolean.FALSE.equals(range.maxInclusive()) ? score < range.max() : score <= range.max();
        return minOk && maxOk;
    }
}
