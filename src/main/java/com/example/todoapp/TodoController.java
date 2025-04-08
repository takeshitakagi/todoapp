package com.example.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@Controller
public class TodoController {

   @Autowired
   private TodoRepository todoRepository;

    // ホームページを表示（TODOリスト）
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("todo", new Todo());
        model.addAttribute("todos", todoRepository.findAll()); // ModelにTODOリストを追加
        return "index"; // index.htmlを返す
    }

    // TODOアイテムを追加
    @PostMapping("/add")
    public String addTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("todos", todoRepository.findAll()); // エラー時もリストを表示
            return "index"; // エラー時は同じページに戻す
        }

        todoRepository.save(todo);
        return "redirect:/";
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
    public String editTodo(@Valid Todo todo, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("todo", todo);
            return "edit"; // エラーがある場合は編集フォームを再表示
        }

        todoRepository.save(todo); // バリデーションを通過したら保存
        return "redirect:/"; // 編集後にリダイレクト
    }
}
