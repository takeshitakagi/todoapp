package com.example.todoapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoController {

    // サンプルデータ
    private List<Todo> todos = new ArrayList<>();

    // ホームページを表示（TODOリスト）
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("todos", todos); // ModelにTODOリストを追加
        return "index"; // index.htmlを返す
    }

    // TODOアイテムを追加
    @PostMapping("/add")
    public String addTodo(@RequestParam("task") String task){
        Todo newTodo = new Todo((long) (todos.size() + 1), task, false);
        todos.add(newTodo);
        return "redirect:/"; // TODOリストページにリダイレクト
    }

    // TODOアイテムを削除
    @PostMapping("/delete")
    public String deleteTodo(@RequestParam("id") Long id){
        todos.removeIf(todo -> todo.getId().equals(id));
        return "redirect:/"; // TODOリストページにリダイレクト
    }

    @PostMapping("/toggle")
    public String toggleTodo(@RequestParam("id") Long id){
        for(Todo todo : todos){
            if(todo.getId().equals(id)){
                todo.setCompleted(!todo.isCompleted());
                break;
            }
        }
    return "redirect:/";
    }
}
