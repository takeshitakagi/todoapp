package com.example.todoapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 完了チェック機能
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

    // 編集フォームを表示
    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable("id") Long id, Model model){
        Todo todo = todos.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
        if(todo != null){
            model.addAttribute("todo", todo); // 編集するTODOをModelに渡す
            return "edit"; // edit.htmlを返す
        }
        return "redirect:/"; // 見つからなかったらリダイレクト
    }

    // 編集内容を保存
    @PostMapping("/edit")
    public String editTodo(@RequestParam("id") Long id, @RequestParam("task") String task){
        for (Todo todo : todos){
            if(todo.getId().equals(id)){
                todo.setTask(task); // タスクの名前を更新
                break;
            }
        }
        return "redirect:/"; // 編集後にリダイレクト
    }
}
