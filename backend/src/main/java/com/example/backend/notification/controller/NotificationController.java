// package com.example.backend.notification.controller;
//
// import static com.example.backend.order.dto.CartRequest.*;
//
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
// import com.example.backend.notification.service.NotificationService;
//
// import lombok.RequiredArgsConstructor;
//
// @RestController
// @RequiredArgsConstructor
// public class NotificationController {
//
// 	private final NotificationService sseService;
//
// 	@GetMapping("/cart/subscribe")
// 	public SseEmitter subscribe(@RequestParam("topicId") String topicId){
// 		return sseService.subscribe(topicId);
// 	}
//
// 	@PostMapping("/cart/addItem")
// 	public void addItemToCart(@RequestParam("topicId") String topicId, @RequestBody ProductInfo productInfo){
// 		sseService.addItem(topicId, productInfo);
// 	}
//
// }
