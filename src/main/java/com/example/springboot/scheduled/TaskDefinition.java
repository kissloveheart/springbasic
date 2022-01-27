package com.example.springboot.scheduled;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDefinition {
    private String cronExpression;
    private String actionType;
    private String data;
}
