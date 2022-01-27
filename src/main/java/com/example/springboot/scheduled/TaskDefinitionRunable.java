package com.example.springboot.scheduled;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskDefinitionRunable implements Runnable {
    private TaskDefinition taskDefinition;
    public static List<String> list;
    static {
        list = new ArrayList<>();
    }

    @Override
    public void run() {
        list.add( new Date() +" Running action: " + taskDefinition.getActionType() + " With Data: " + taskDefinition.getData());
    }

}
