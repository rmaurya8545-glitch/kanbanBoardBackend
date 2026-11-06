package kanban_backend.controller;

import kanban_backend.entity.Task;
import kanban_backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {"http://localhost:5173","https://task-manager-alpha-six-83.vercel.app"})
public class TaskController {

    @Autowired
    private TaskService taskService;

//    @GetMapping
//    public List<Task> getAllTasks(@RequestParam(required = false) Long userId){
//        if(userId != null){
//            return taskService.getTasksByUserId(userId);
//        }
//        return taskService.getAllTasks();
     @GetMapping
     public List<Task> getTasks(@RequestParam Long userId) {
     return taskService.getTasksByUserId(userId);
}

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestParam Long userId, @RequestBody Task task) {
        return taskService.updateTask(id, userId, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, @RequestParam Long userId) {
        taskService.deleteTask(id, userId);
    }
}
