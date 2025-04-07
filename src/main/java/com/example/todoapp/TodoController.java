package com.example.todoapp;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

   @Autowired
   private TodoRepository todoRepository;

    // ホームページを表示（TODOリスト）
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("todos", todoRepository.findAll()); // ModelにTODOリストを追加
        return "index"; // index.htmlを返す
    }

    // TODOアイテムを追加
    @PostMapping("/add")
    public String addTodo(@RequestParam("task") String task){
        Todo newTodo = new Todo(null, task, false);
        todoRepository.save(newTodo);
        return "redirect:/"; // TODOリストページにリダイレクト
    }

    // TODOアイテムを削除
    @PostMapping("/delete")
    public String deleteTodo(@RequestParam("id") Long id){
        todoRepository.deleteById(id);
        return "redirect:/"; // TODOリストページにリダイレクト
    }

    // 完了チェック機能
    @PostMapping("/toggle")
    public String toggleTodo(@RequestParam("id") Long id){
        Todo todo = todoRepository.findById(id).orElse(null);
        if(todo != null){
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
        }
    return "redirect:/";
    }

    // 編集フォームを表示
    @GetMapping("/edit")
    public String editTodoForm(@RequestParam("id") Long id, Model model){
        Todo todo = todoRepository.findById(id).orElse(null); // DBから取得
        if(todo != null){
            model.addAttribute("todo", todo); // 編集するTODOをModelに渡す
            return "edit"; // edit.htmlを返す
        }
        return "redirect:/"; // 見つからなかったらリダイレクト
    }

    // 編集内容を保存
    @PostMapping("/edit")
    public String editTodo(@RequestParam("id") Long id, @RequestParam("task") String task){
        Todo todo = todoRepository.findById(id).orElse(null);
        if(todo != null){
            todo.setTask(task);
            todoRepository.save(todo);
        }
        return "redirect:/"; // 編集後にリダイレクト
    }
}
