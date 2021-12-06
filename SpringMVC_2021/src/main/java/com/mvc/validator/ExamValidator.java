package com.mvc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mvc.entity.Exam;

@Component
public class ExamValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// 判斷當前 class 是不是 Exam 的類別
		return Exam.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Exam e = (Exam) target;
		// 判定 id 不可以是 null 或空的
		if(e.getId() == null || e.getId().trim().length() == 0) {
			// field, errorCode, defaultMessage
			// field : 要驗證的物件變數
			// errorCode : 錯誤名稱(通常是指 errorMessage.properties 所設定的名稱)
			// defaultMessage : 預設的錯誤訊息
			errors.rejectValue("id", "exam.id.required", "id 不可空白");
		}
		
		if(e.getName() == null || e.getName().trim().length() == 0) {
			errors.rejectValue("name", "exam.name.required", "請選擇考試代號");
		}
		
		if(e.getSlot() == null || e.getSlot().length == 0) {
			errors.rejectValue("slot", "exam.slot.required", "請選擇考試時段");
		}
		
		if(e.getPay() == null || e.getPay().trim().length() == 0) {
			errors.rejectValue("pay", "exam.pay.required", "請選擇繳費狀況");
		}
	}
	
	

}
