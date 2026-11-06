package kanban_backend.service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Objects;

import kanban_backend.entity.Task;
import kanban_backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

//    public List<Task> getAllTasks() {
//        return taskRepository.findAll();
//    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task createTask(Task task) {

        if (task.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Long userId, Task updatedTask) {
        Task task = findOwnedTask(id, userId);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());
        task.setAssignee(updatedTask.getAssignee());
        task.setDueDate(updatedTask.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id, Long userId) {
        taskRepository.delete(findOwnedTask(id, userId));
    }

    private Task findOwnedTask(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        if (!Objects.equals(task.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your task");
        }
        return task;
    }
}