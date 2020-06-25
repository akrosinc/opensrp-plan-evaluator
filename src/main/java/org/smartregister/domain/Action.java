package org.smartregister.domain;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by samuelgithengi on 4/29/19.
 */
@NoArgsConstructor
@Setter
@Getter
public class Action {

    private String identifier;

    private int prefix;

    private String title;

    private String description;

    private String code;

    private ExecutionPeriod timingPeriod;

    private String reason;

    private String goalId;

    private SubjectConcept subjectCodableConcept;

    private String taskTemplate;

    private Set<Trigger> trigger;

    private Set<Condition> condition;

    private String definitionUri;

    private Set<DynamicValue> dynamicValue;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SubjectConcept {
        private String text;
    }
}
