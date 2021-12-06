package com.mvc.controller;


import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entity.User;

@Controller
@RequestMapping(value = "/hi")
public class Hello {
	
	@RequestMapping(value = "/hello") // 網址
	@ResponseBody // 回傳值
	public String hello() {
		return "hello";
	}

	/*
	 * value = "name",defaultValue = "unknow",required = false 變數名稱 name , 預設名稱
	 * unknow , false 可以空值 true 不能
	 */
	@RequestMapping("/sayhi")
	@ResponseBody
	public String sayhi(@RequestParam(value = "name", defaultValue = "unknow", required = false) String name,
			@RequestParam("age") Integer age) {
		return "hello " + name + " , " + age;
	}

	/*
	 * Lab: 路徑:/bmi?h=170&w=60 帶入: h 與 w 兩個參數 結果: bmi = 20.76
	 */
	@RequestMapping("/bmi")
	@ResponseBody
	public String bmi(@RequestParam(value = "h") double h, @RequestParam(value = "w") double w) {
		double result = w / Math.pow(h / 100, 2);
		return String.format("Bmi = %.2f ", result);
	}

	/*
	 * PathVariable 路徑 : /exam/75 -> 結果:75 pass 路徑 : /exam/45 -> 結果:45 fali
	 */
	@RequestMapping(value = "/exam/{score}")
	@ResponseBody
	public String exam(@PathVariable("score") Integer score) {
		return score + " " + ((score >= 60) ? "Pass" : "Fail");
	}

	/*
	 * Lab: PathVariable + requestParm 路徑: /calc/add?x=30&y=20 -> 結果 : 50 路徑:
	 * /calc/sub?x=30&y=20 -> 結果 : 10 路徑: /calc/sub?y=20 -> 結果 : 20 路徑:
	 * /calc/sub?x=0&y=20 -> 結果 : -20 路徑: /calc/add -> 結果 : 0
	 */
	@RequestMapping(value = "calc/{select}")
	@ResponseBody
	public String calc(@PathVariable("select") String select,
			@RequestParam(value = "x", required = false) Optional<Integer> x,
			@RequestParam(value = "y", required = false) Optional<Integer> y) {
		if (x.isPresent() && y.isPresent()) {
			switch (select) {
			case "add":
				return x.get() + y.get() + " ";
			case "sub":
				return x.get() - y.get() + " ";
			default:
				return "none";
			}
		}
		if (x.isPresent()) {
			return x.get() + "";
		}
		if (y.isPresent()) {
			return y.get() + "";
		}
		return "0";
	}
	
	/*
	 * 常見的 PathVariable 萬用字元 * 任意多字, ? 任意一字
	 */
	@RequestMapping(value = "/any/*/java?")
	@ResponseBody
	public String any() {
		return "any";
	}
	
	/*
	 * 得到多筆資料
	 * 路徑 : /age?=a=18&a=19&a=20
	 * 結果 :age of average = 19
	 */
	@RequestMapping("/age")
	@ResponseBody
	public String age(@RequestParam("a") List<Integer> ageList) {
		double avg = ageList.stream().mapToInt(s -> s).average().getAsDouble();
		return String.format("%s , age of average = %d", ageList,(int)avg);
	}
	
	/*
	 * Lab
	 * 得到多筆 score 資料
	 * 網址輸入: /max?score=80&score=100&score=50
	 * 網址輸入: /min?score=80&score=100&score=50
	 * 結果得到: max score = 100
	 * IntSummaryStatistics 統計API
	 */
	@RequestMapping(value = "{select}")
	@ResponseBody
	public String calc2(@PathVariable(value = "select")String select,@RequestParam(value = "score") List<Integer> score) {
		IntSummaryStatistics iss = score.stream().mapToInt(s ->s).summaryStatistics();
		switch (select) {
		case "max":
//			return "max =  " +score.stream().mapToInt(s -> s).max().getAsInt() ;	
			return "max = " + iss.getMax();
		case "min":
//			return "min =  " +score.stream().mapToInt(s -> s).min().getAsInt() ;
			return "min = " + iss.getMin();
		}
		return "0";
	}
	
	/*
	 * Map 參數
	 * 網址輸入 : /mix?name=John&score=100&age=18&pass=true
	 * 網址輸入 : /mix?name=Mary&score=90&age=20&level=2
	 */
	@RequestMapping("/mix")
	@ResponseBody
	public String mix(@RequestParam Map<String, String> map) {
		return map.toString();
	}
	
	/*
	 * Java pojo 物件
	 * 網址輸入: /user?name=John&age=18
	 */
	@RequestMapping("/user")
	@ResponseBody
	public String getUser(@Valid User user) {
		return user.toString();
	}
}
	


