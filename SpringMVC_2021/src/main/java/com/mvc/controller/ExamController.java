package com.mvc.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;

import static java.util.stream.Collectors.counting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.entity.Exam;
import com.mvc.entity.ExamName;
import com.mvc.entity.ExamPay;
import com.mvc.entity.ExamSlot;
import com.mvc.validator.ExamValidator;

@Controller
@RequestMapping("/exam")
public class ExamController {
	private static List<Exam> exams = new CopyOnWriteArrayList<>(); // 表單
	private static List<ExamName> examNames = new ArrayList<>();
	static {
		examNames.add(new ExamName("808", "1Z0-808"));
		examNames.add(new ExamName("809", "1Z0-809"));
		examNames.add(new ExamName("809", "1Z0-900"));
		examNames.add(new ExamName("819", "1Z0-819"));
	}
	
	private static List<ExamSlot> examSlots = new CopyOnWriteArrayList<>();
	static {
		examSlots.add(new ExamSlot("A","上午 "));
		examSlots.add(new ExamSlot("B","中午 "));
		examSlots.add(new ExamSlot("C","下午 "));
	}
	
	private static List<ExamPay> examPays = new CopyOnWriteArrayList<>();
	static {
		examPays.add(new ExamPay(true, "已繳"));
		examPays.add(new ExamPay(false, "未"));
	}

	@Autowired
	private ExamValidator examValidator;

	@RequestMapping(value = { "/", "/index" })
	public String index(Model model) {
		Exam e = new Exam();
		model.addAttribute("exam", e); // 給表單
		model.addAttribute("exams", exams); // 給資料呈現
		model.addAttribute("action", "create");
		model.addAttribute("examNames", examNames);
		model.addAttribute("examSlots", examSlots);
		model.addAttribute("examPays", examPays);
		model.addAttribute("stat", getStat());
		model.addAttribute("stat2", getStat2());

		return "exam"; // 回傳jsp
	}

	// CRUD create,Read,Update,Delete
	@RequestMapping(value = "/create")
	public String create(Exam exam, BindingResult result, Model model) {
		System.out.println("新增成功" + exam.getId());
//		System.out.println(exams);
		// 驗證 exam 物件
		examValidator.validate(exam, result);
		// 驗證結果是否有錯誤 ?
		if (result.hasErrors()) {
			model.addAttribute("exams", exams); // 給資料呈現
			model.addAttribute("action", "create");
			model.addAttribute("examNames", examNames);
			model.addAttribute("examSlots", examSlots);
			model.addAttribute("examPays", examPays);
			model.addAttribute("stat", getStat());
			model.addAttribute("stat2", getStat2());
			return "exam";
		}
		exams.add(exam); // 新增
		return "redirect:/mvc/exam/"; // 重導
	}

	@RequestMapping(value = "/get/{id}")
	public String get(@PathVariable(value = "id") String id, Model model) {
		Optional<Exam> optExam = exams.stream().filter(s -> s.getId().equals(id)).findFirst();
		model.addAttribute("exam", optExam.isPresent() ? optExam.get() : new Exam()); // 給表單使用
		model.addAttribute("exams", exams); // 給呈現使用資料
		model.addAttribute("action", "update");
		model.addAttribute("examNames", examNames);
		model.addAttribute("examSlots", examSlots);
		model.addAttribute("examPays", examPays);
		model.addAttribute("stat", getStat());
		model.addAttribute("stat2", getStat2());
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
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		exams.removeIf(e -> e.getId().equals(id));
		return "redirect:/mvc/exam/";
	}

	private Map<String, Long> getStat() {
		// 1. 各科考試報名人數
		return exams.stream().collect(groupingBy(Exam::getName, counting()));
	}

	private Map<String, Long> getStat2() {
		// 2. 考試付款狀況
		return exams.stream().collect(groupingBy(Exam::getPay, counting()));
	}
}