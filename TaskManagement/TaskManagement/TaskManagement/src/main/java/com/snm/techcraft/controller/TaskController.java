package com.snm.techcraft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snm.techcraft.model.Task;
import com.snm.techcraft.model.UserInfo;
import com.snm.techcraft.service.TaskService;
import com.snm.techcraft.util.SNMResponse;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @PostMapping("/createTask")
    public SNMResponse createTask(@RequestBody Task task) {
        return taskService.createNewTask(task);
    }
    
    @GetMapping("/singleTask/{taskId}")
	public SNMResponse  getSingleTask(@PathVariable int taskId) {
		return this.taskService.getSingleTaskById(taskId);
	}
    
    @GetMapping("/singleTaskName/{taskName}")
	public List<SNMResponse> getTaskName(@PathVariable String name) {
		return this.taskService.getTaskByName(name);
	}
    
    @PutMapping("/updatetask/{taskId}")
    public SNMResponse updateTask(@RequestBody Task updatedTask,Integer taskId) {
    	return taskService.updateTask(updatedTask,taskId);
    }
    
    @GetMapping("/allTaskList")
	public List<SNMResponse> getAllTask(@RequestParam(required = false, name = "taskStatus") String status,
			                                @RequestParam(required = false, name = "assignName") UserInfo assignName,
			                                @RequestParam(defaultValue = "0", name = "page") int page,
			                                @RequestParam(defaultValue = "10", name = "size") int size) {
		return this.taskService.getTaskList(status, assignName, page, size);
	}
    
    @DeleteMapping("/deleteTask/{taskId}")
	public SNMResponse deleteTask(@PathVariable int taskId) {
		return this.taskService.deleteTaskById(taskId);
	}
    
    @GetMapping("/searchFilter")
    public List<Task> filterTasks(@RequestParam(required = false, name = "taskName") String name,
                                  @RequestParam(required = false, name = "workCategoryId") Integer workCategoryId,
                                  @RequestParam(required = false, name = "subCategoryId") Integer subCategoryId,
                                  @RequestParam(required = false, name = "client") Integer client,
                                  @RequestParam(required = false, name = "assignNameId") Integer assignNameId,
                                  @RequestParam(required = false, name = "taskStatus") String status,
                                  @RequestParam(required = false, name = "paymentStatus") String paymentStatus) {

        return taskService.filterTasks(name, workCategoryId, subCategoryId, client, 
                                       assignNameId, status, paymentStatus);
    }
    
    @PutMapping("/updateTaskStatus/{taskId}")
	 public SNMResponse taskStatuUpdate(@PathVariable int taskId, @RequestBody Task updateTaskStatus) {
		 return this.taskService.taskStatuUpdateById(taskId, updateTaskStatus);
	 }
    
    @GetMapping("/userTaskCont/{id}")
	 public SNMResponse userTaskCountById(@PathVariable("id") UserInfo assignName) {
		 return this.taskService.getUserTaskCountById(assignName);
	 }
    
    


   
 

}
