package org.example.tasktracker.Controller;


import org.example.tasktracker.API.ApiResponse;
import org.example.tasktracker.Model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    ArrayList<Task> tasks = new ArrayList<>();


    @GetMapping("/get")
    public ResponseEntity<?> getTasks() {
        if(tasks.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task List is Empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PostMapping("/add")
    public ApiResponse addTask(@RequestBody Task task) {
        tasks.add(task);
        return new ApiResponse("Task Added Successfully");
    }


    @PutMapping("/update/{index}")
    public ResponseEntity<String> updateTask(@RequestBody Task task, @PathVariable int index) {
        if (tasks.isEmpty() || index < 0 || index > tasks.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Index Out Of Bound");
        }
        tasks.set(index, task);
        return ResponseEntity.status(HttpStatus.OK).body("Task Updated");
    }


    @DeleteMapping("/delete/{index}")
    public ResponseEntity<String> deleteTask(@PathVariable int index) {
        if (tasks.isEmpty() || index < 0 || index > tasks.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index Out Of Bound");
        }
        tasks.remove(index);
        return ResponseEntity.status(HttpStatus.OK).body("Task Removed.");
    }

    @PutMapping("/change/status/{index}")
    public ApiResponse changeStatus(@PathVariable int index) {
        if (tasks.isEmpty()) {
            return new ApiResponse("Tasks Are Empty");
        }
        tasks.get(index).setStatus(!tasks.get(index).isStatus());
            return new ApiResponse("Status Changed.");

    }


    @GetMapping("/find/{title}")
    public ApiResponse findByTitle(@PathVariable String title) {
        if (tasks.isEmpty()) {
            return new ApiResponse("Tasks Are Empty");
        }
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTitle().equalsIgnoreCase(title)) {
                return new ApiResponse("Task Found At Index : " + i);
            }
        }
        return new ApiResponse("Task with given Title not found");
    }


}
