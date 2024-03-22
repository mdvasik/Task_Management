package com.snm.techcraft.service;

import java.util.List;

import com.snm.techcraft.model.Task;
import com.snm.techcraft.model.UserInfo;
import com.snm.techcraft.util.SNMResponse;

public interface TaskService {

	SNMResponse createNewTask(Task task);
	
	SNMResponse getSingleTaskById(int taskId);
	
	List<SNMResponse> getTaskByName(String name);

	List<SNMResponse> getTaskList(String status, UserInfo assignName, int page, int size);

	SNMResponse updateTask(Task updatedTask,Integer taskId);

	SNMResponse deleteTaskById(int taskId);
	
	List<Task> filterTasks(String name, Integer workCategoryId, Integer subCategoryId, Integer client,
			               Integer assignNameId, String status, String paymentStatus);

	SNMResponse taskStatuUpdateById(int taskId, Task updateTaskStatus);

	SNMResponse getUserTaskCountById(UserInfo assignName);
}