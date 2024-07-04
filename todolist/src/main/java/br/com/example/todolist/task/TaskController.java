package br.com.example.todolist.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private ITaskRepository taskRepositoy;

  @SuppressWarnings("rawtypes")
  @PostMapping("/create")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    taskModel.setIdUser((UUID) idUser);
    LocalDateTime currentDate = LocalDateTime.now();

    if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(401)
          .body("Tanto a data de início quanto a data de término devem ser maiores que a data atual.");
    }

    if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(401)
          .body("A data de início não pode ser posterior a data de término.");
    }

    var taskCreated = this.taskRepositoy.save(taskModel);
    return ResponseEntity.status(201).body(taskCreated);
  }

  @SuppressWarnings("rawtypes")
  @GetMapping("")
  public ResponseEntity list(HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    List<TaskModel> list = this.taskRepositoy.findByIdUser((UUID) idUser);

    return ResponseEntity.status(200).body(list);
  }

  @SuppressWarnings("rawtypes")
  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {

    var idUser = request.getAttribute("idUser");
    taskModel.setIdUser((UUID) idUser);
    taskModel.setId(id);
    var taskCurrente = this.taskRepositoy.save(taskModel);

    return ResponseEntity.status(200).body(taskCurrente);

  }

  @SuppressWarnings("rawtypes")
  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable UUID id, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    List<TaskModel> listTasks = this.taskRepositoy.findByIdUser((UUID) idUser);

    List<TaskModel> arrayTask = new ArrayList<>();

    for (TaskModel item : listTasks) {
      System.out.println(item);

      if (item.getId().equals(id)) {
        arrayTask.add(item);
      }
    }

    if (arrayTask.isEmpty()) {
      return ResponseEntity.status(404).body("Não foi possível localizar a task");
    }

    taskRepositoy.deleteById(id);
    return ResponseEntity.status(200).body(null);
  }
}
