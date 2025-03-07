package br.com.example.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "users")
public class UserModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String userName;
  private String email;
  private String password;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
