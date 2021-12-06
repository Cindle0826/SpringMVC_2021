package com.mvc.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.entity.Exam;

@Controller
@RequestMapping("/exam")
public class ExamController {
	private static List<Exam> exams = new CopyOnWriteArrayList<>(); // 表單

	@RequestMapping(value = { "/", "/index" })
	public String index(Model model) {
		Exam e = new Exam();
		model.addAttribute("exam", e); // 給表單
		model.addAttribute("exams", exams); // 給資料呈現
		model.addAttribute("action", "create");
		return "exam"; //回傳jsp
	}

	// CRUD create,Read,Update,Delete
	@RequestMapping(value = "/create")
	public String create(Exam exam,Model model) {
		exams.add(exam); // 新增
		System.out.println("新增成功" + exam.getId());
//		System.out.println(exams);
		return "redirect:/mvc/exam/"; // 重導
	}

	@RequestMapping(value = "/get/{id}")
	public String get(@PathVariable(value = "id") String id, Model model) {
		Optional<Exam> optExam = exams.stream().filter(s -> s.getId().equals(id)).findFirst();
		model.addAttribute("exam", optExam.isPresent() ? optExam.get() : new Exam()); // 給表單使用
		model.addAttribute("exams", exams); // 給呈現使用資料
		model.addAttribute("action", "update");
		return "exam";
	}

	/*
	 * oExam 原本的資料 表單傳來 exam 要修改的資料
	 */
	@RequestMapping(value = "/update")
	public String update(Exam exam) {
		Optional<Exam> optExam = exams.stream().filter(s -> s.getId().equals(exam.getId())).findFirst();
		if (optExam.isPresent()) {
			Exam oExam = optExam.get();
			oExam.setName(exam.getName());
			oExam.setSlot(exam.getSlot());
			oExam.setPay(exam.getPay());
			oExam.setNote(exam.getNote());
		}
		return "redirect:/mvc/exam/"; // 重導到首頁
	}

	// Delete
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") String id, Model model) {
		Optional<Exam> optExam = exams.stream().filter(s -> s.getId().equals(id)).findFirst();
		if(optExam.isPresent()) {
			exams.remove(optExam.get());
		}
		System.out.println("刪除成功~~");
		return "redirect:/mvc/exam/"; // 重導到首頁
	}

}
