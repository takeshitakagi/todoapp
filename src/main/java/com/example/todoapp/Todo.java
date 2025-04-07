package com.example.todoapp;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String task;
    private boolean completed;

    // デフォルトコンストラクタ
    public Todo(){

    }

    // コンストラクタ
    public Todo(Long id, String task, boolean completed){
        this.id = id;
        this.task = task;
        this.completed = completed;
    }

    // ゲッターとセッター
    public  Long getId(){
        return  id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTask(){
        return task;
    }

    public void setTask(String task){
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
