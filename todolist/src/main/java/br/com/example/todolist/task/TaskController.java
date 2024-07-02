package br.com.example.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private ITaskRepository taskRepositoy;

  @SuppressWarnings("rawtypes")
  @PostMapping("/create")
  public ResponseEntity create(@RequestBody TaskModel taskModel) {
    var taskCreate = this.taskRepositoy.save(taskModel);
    return ResponseEntity.status(201).body(taskCreate);
  }
}
