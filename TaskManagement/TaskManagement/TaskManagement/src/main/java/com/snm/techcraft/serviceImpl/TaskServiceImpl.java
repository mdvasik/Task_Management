package com.snm.techcraft.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.snm.techcraft.model.Task;
import com.snm.techcraft.model.UserInfo;
import com.snm.techcraft.model.WorkCategory;
import com.snm.techcraft.repository.TaskRepository;
import com.snm.techcraft.service.TaskService;
import com.snm.techcraft.util.SNMResponse;
import com.snm.techcraft.util.SNMUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepo;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUBADMIN') or hasRole('ROLE_USER')")
    public SNMResponse createNewTask(Task task) {
		 try {
		        Optional<Task> existingTaskOptional = taskRepo.findByName(task.getName());
		        if (existingTaskOptional.isPresent()) {
		            return new SNMResponse("Task with the same name already exists", 400, null);
		     }
		        
            if (!entityManager.contains(task.getWorkCategory())) {
                WorkCategory managedWorkCategory = entityManager.find(WorkCategory.class, task.getWorkCategory().getWorkCategoryId());
                task.setWorkCategory(managedWorkCategory);
            }

            task.setAssignDate(LocalDate.now());
            task.setCreateOn(LocalDate.now());
            
            Task createdTask = taskRepo.save(task);

            return new SNMResponse("Task created successfully", 200, createdTask);
        } catch (Exception e) {
            return new SNMResponse("Error creating task", 500, e.getMessage());
        }
    }

	 @Override
		public SNMResponse getSingleTaskById(int taskId) {
	    	Optional<Task> optionalTask = this.taskRepo.findById((int) taskId);
			if (optionalTask.isPresent()) {
				return SNMUtil.makeResponse(optionalTask, "Task found successfully.", 200);
			} else {
				return SNMUtil.makeResponse(null, "Task not found", 404);
			}
		}

	  @Override
		public List<SNMResponse> getTaskByName(String name) {
	    	List<SNMResponse> responses = new ArrayList<>();
			if (name != null && !name.isEmpty()) {
				List<Task> tasks = taskRepo.findByNameContaining(name);
				if (!tasks.isEmpty()) {
					for (Task task : tasks) {
						responses.add(SNMUtil.makeResponse(task, "Similar task found", 200));
					}
				} else {
					responses.add(SNMUtil.makeResponse(null, "No similar tasks found", 404));
				}
			} else {
				responses.add(SNMUtil.makeResponse(null, "Invalid input: Task name is null or empty", 400));
			}
			return responses;
		}

	  @Override
	  @Transactional
	  public SNMResponse updateTask(Task updatedTask, Integer taskId) {
	      try {
	          Task existingTask = entityManager.find(Task.class, taskId);

	          if (existingTask != null) {
	              existingTask.setName(updatedTask.getName());
	              if (updatedTask.getWorkCategory() != null && !entityManager.contains(updatedTask.getWorkCategory())) {
	                  WorkCategory managedWorkCategory = entityManager.find(WorkCategory.class, updatedTask.getWorkCategory().getWorkCategoryId());
	                  existingTask.setWorkCategory(managedWorkCategory);
	              } else {
	                  existingTask.setWorkCategory(updatedTask.getWorkCategory());
	              }
	              existingTask.setSubCategory(updatedTask.getSubCategory());
	              existingTask.setClient(updatedTask.getClient());
	              existingTask.setDescription(updatedTask.getDescription());
	              existingTask.setAssignName(updatedTask.getAssignName());
	              existingTask.setCoWork(updatedTask.getCoWork());
	              existingTask.setStatus(updatedTask.getStatus());
	              existingTask.setNotes(updatedTask.getNotes());
	              existingTask.setAssignDate(updatedTask.getAssignDate());
	              existingTask.setTaskEndDate(updatedTask.getTaskEndDate());
	              existingTask.setPaymentStatus(updatedTask.getPaymentStatus());
	              existingTask.setCreatedBy(updatedTask.getCreatedBy());
	              existingTask.setCreateOn(updatedTask.getCreateOn());
	              existingTask.setUpdateBy(updatedTask.getUpdateBy());

	              // Set the updateOn field to the current date
	              existingTask.setUpdateOn(LocalDate.now());

	              existingTask.setDeleted(updatedTask.isDeleted());

	              entityManager.merge(existingTask);

	              return SNMUtil.makeResponse(existingTask, "Task updated successfully", 200);
	          } else {
	              return SNMUtil.makeResponse(null, "Task not found with id: " + taskId, 404);
	          }
	      } catch (Exception e) {
	          // Handle any exceptions
	          return SNMUtil.makeResponse(null, "An error occurred while updating the task: " + e.getMessage(), 500);
	      }
	  }
	  
	  @Override
		public SNMResponse deleteTaskById(int taskId) {
			Optional<Task> optionalTask = taskRepo.findById(taskId);
			if (optionalTask.isPresent()) {
				Task task = optionalTask.get();
				task.setDeleted(true);
				taskRepo.save(task);
				return SNMUtil.makeResponse(task, "Task deleted successfully.", 200);
			}
			return SNMUtil.makeResponse(null, "Task not found", 404);
		}


//	  @Override
//	  public List<SNMResponse> getTaskList(String status, UserInfo assignName, int page, int size) {
//	      // Create a Pageable object for pagination
//	      Pageable pageable = PageRequest.of(page, size);
//
//	      // Check if both taskStatus and assignName are null
//	      if (status == null && assignName == null) {
//	          // Return list of all tasks if both parameters are null
//	          Page<Task> taskPage = taskRepo.findAll(pageable);
//	          // Convert the list of tasks to SNMResponse objects and return
//	          return convertToResponse(taskPage.getContent(), "All task list retrieved successfully", 200);
//
//	      } else if (status != null && assignName != null) {
//	          // If both taskStatus and assignName are provided, query tasks based on both
//	          return getTasksListCountByStatusAndAssignee(status, assignName, pageable);
//
//	      } else if (status != null) {
//	          // If only taskStatus is provided, query tasks based on taskStatus only
//	          return findByTaskStatus(status, pageable);
//
//	      } else if (assignName != null) {
//	          // If only assignName is provided, query tasks based on assignName only
//	          return findByAssignName(assignName, pageable);
//
//	      } else {
//	          // If neither taskStatus nor assignName is provided, return a response with
//	          return List.of(SNMUtil.makeResponse(null, "Data not found", 400));
//	      }
//	  }
//
//	  private List<SNMResponse> findByTaskStatus(String status, Pageable pageable) {
//	      // Query tasks based on taskStatus
//	      Page<Task> statusTask = taskRepo.findByTaskStatus(status, pageable);
//	      // Convert the list of tasks to SNMResponse objects and return
//	      return convertToResponse(statusTask.getContent(), "Task Status retrieved successfully", 200);
//	  }
//
//	  private List<SNMResponse> findByAssignName(UserInfo assignName, Pageable pageable) {
//	      // Query tasks based on assignName
//	      Page<Task> assignTaskPage = taskRepo.findByAssignName(assignName, pageable);
//	      // Convert the list of tasks to SNMResponse objects and return
//	      return convertToResponse(assignTaskPage.getContent(), "Assign Task retrieved successfully", 200);
//	  }
//
//	  private List<SNMResponse> getTasksListCountByStatusAndAssignee(String status, UserInfo assignName, Pageable pageable) {
//	      // Query tasks based on taskStatus and assignName
//	      Page<Task> taskPage = taskRepo.findByTaskStatusAndAssignName(status, assignName, pageable);
//	      // Convert tasks to SNMResponse objects and return
//	      return convertToResponse(taskPage.getContent(), "TaskStatus and AssignId retrieved successfully", 200);
//	  }
//
//	  // Assuming this method is unchanged
//	  public List<SNMResponse> convertToResponse(List<Task> tasks, String message, int statusCode) {
//	      List<SNMResponse> responseList = new ArrayList<>();
//	      for (Task task : tasks) {
//	          SNMResponse response = new SNMResponse();
//	          response.setData(task); // Assuming the data field in SNMResponse can hold a Task object
//	          // You can set other properties of SNMResponse if needed
//	          responseList.add(SNMUtil.makeResponse(response, message, statusCode));
//	      }
//	      return responseList;
//	  }
	  
	  @Override
		public List<SNMResponse> getTaskList(String taskStatus, UserInfo assignName, int page, int size) {
		    // Create a Pageable object for pagination
		    Pageable pageable = PageRequest.of(page, size);

		    // Check if both taskStatus and assignName are null
		    if (taskStatus == null && assignName == null) {
		        // Return list of all tasks if both parameters are null
		        Page<Task> taskPage = taskRepo.findAll(pageable);
		        // Convert the page of tasks to SNMResponse objects and return
		        return Collections.singletonList(SNMUtil.makeResponse(taskPage.getContent(), "All task list retrieved successfully", 200));
		    } else if (taskStatus != null && assignName != null) {
		        // If both taskStatus and assignName are provided, query tasks based on both
		        Page<Task> taskPage = taskRepo.findByTaskStatusAndAssignName(taskStatus, assignName, pageable);
		        return Collections.singletonList(SNMUtil.makeResponse(taskPage.getContent(), "TaskStatus and AssignId retrieved successfully", 200));
		    } else if (taskStatus != null) {
		        // If only taskStatus is provided, query tasks based on taskStatus only 
		        Page<Task> taskPage = taskRepo.findByTaskStatus(taskStatus, pageable);
		        return Collections.singletonList(SNMUtil.makeResponse(taskPage.getContent(), "TaskStatus  retrieved successfully", 200));
		    } else if (assignName != null) {
		        // If only assignName is provided, query tasks based on assignName only
		        Page<Task> taskPage = taskRepo.findByAssignName(assignName, pageable);
		        return Collections.singletonList(SNMUtil.makeResponse(taskPage.getContent(), "AssignId retrieved successfully", 200));
		    } else {
		        // If neither taskStatus nor assignName is provided, return a response with status code 400
		        return List.of(SNMUtil.makeResponse(null, "Data not found", 400));
		    }
		}
	  
	  @Override
	  public List<Task> filterTasks(String name, Integer workCategoryId, Integer subCategoryId, Integer client,
	                                 Integer assignNameId, String status, String paymentStatus) {
	      return taskRepo.filterTasks(name, workCategoryId, subCategoryId, client, assignNameId,
	                                   status, paymentStatus);
	  }


	  @Override
		public SNMResponse taskStatuUpdateById(int taskId, Task updateTaskStatus) {
			Optional<Task> optionalTaskStatus = this.taskRepo.findById(taskId);

			if (optionalTaskStatus.isPresent()) {
				Task existingTaskStatus = optionalTaskStatus.get();

				existingTaskStatus.setStatus(updateTaskStatus.getStatus());
				Task saveTaskStatus = this.taskRepo.save(existingTaskStatus);

				return SNMUtil.makeResponse(saveTaskStatus, "Task status updated successfully for task ID: " + taskId,
						200);
			}

			return SNMUtil.makeResponse(null, "Task not found with id: " + taskId, 404);
		}

	  @Override
		public SNMResponse getUserTaskCountById(UserInfo assignName) {
					Optional<Task> optionalUser = this.taskRepo.findById(assignName.getId());
					if (optionalUser.isPresent()) {
						int userId = assignName.getId();

						List<Object[]> taskCounts = taskRepo.countPendingAndCompletedTasks(userId);

						Long pendingTasks = (Long) taskCounts.get(0)[0]; 
						Long completedTasks = (Long) taskCounts.get(0)[1];

						Map<String, Long> taskCountMap = new HashMap<>();
						taskCountMap.put("Total Pending task", pendingTasks); 
						taskCountMap.put("Total Completed task", completedTasks); 

						return SNMUtil.makeResponse(taskCountMap, "Task count successfully", 200);
					} else {
						return SNMUtil.makeResponse(null, "User not found", 404);
					}
				}

}