package com.example.springboot.controller;

import com.example.springboot.scheduled.TaskDefinition;
import com.example.springboot.scheduled.TaskDefinitionRunable;
import com.example.springboot.scheduled.TaskSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/schedule")
@Scope("prototype")
public class JobSchedulingController {
    @Autowired
    private TaskSchedulingService taskSchedulingService;

    static int countInitiate;

    public JobSchedulingController() {
        countInitiate++;
        System.out.println("Bean scheduling initiate count " + countInitiate);
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> listJobs(){
        return ResponseEntity.ok(TaskDefinitionRunable.list);
    }

    @GetMapping("/create")
    public String createTask(Model model){
        TaskDefinition taskDefinition = new TaskDefinition();
        model.addAttribute("taskDefinition",taskDefinition);
        model.addAttribute("jobs",taskSchedulingService.getJobsMap());
        return "/web/listSchedule";
    }

    @PostMapping("/create")
    public String scheduleATask(@ModelAttribute TaskDefinition taskDefinition, RedirectAttributes redirectAttributes) {
        TaskDefinitionRunable taskDefinitionRunable = new TaskDefinitionRunable();
        taskDefinitionRunable.setTaskDefinition(taskDefinition);
        String id = UUID.randomUUID().toString();
        taskSchedulingService.scheduleATask(id, taskDefinitionRunable, taskDefinition.getCronExpression());
        redirectAttributes.addAttribute("message","Create job: "+id);
        return "redirect:/schedule/create";
    }

    @GetMapping("/remove")
    public String removeJob(@RequestParam("id") String jobId, RedirectAttributes redirectAttributes) {
        taskSchedulingService.removeScheduledTask(jobId);
        redirectAttributes.addAttribute("message","Remove job: "+jobId);
        return "redirect:/schedule/create";
    }
}
