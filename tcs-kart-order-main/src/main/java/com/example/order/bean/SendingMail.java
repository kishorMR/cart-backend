package com.example.order.bean;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendingMail {
	
	private String recipient;
	private String messageBody;
	private String subject;
	private String attachment;
}
