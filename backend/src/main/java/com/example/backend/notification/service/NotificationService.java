package com.example.backend.notification.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	// 테이블 ID별로 구독자를 관리하는 맵
	private final ConcurrentHashMap<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

	public int getSubscriberSize(String topicId){
		List<SseEmitter> list = emitters.getOrDefault(topicId,new ArrayList<>());
		return list.size();
	}

	public SseEmitter subscribe(String topicId){

		SseEmitter emitter = new SseEmitter(600000L);

		// 새로운 구독자 추가
		if(!emitters.containsKey(topicId)){
			List<SseEmitter> sseEmitters = new CopyOnWriteArrayList<>();
			sseEmitters.add(emitter);
			emitters.put(topicId,sseEmitters);
		}
		else{
			List<SseEmitter> sseEmitters = emitters.get(topicId);
			sseEmitters.add(emitter);
			emitters.put(topicId,sseEmitters);
		}

		// 연결 종료 시 해당 emitter만 구독 목록에서 제거
		emitter.onCompletion(() -> {
			List<SseEmitter> sseEmitters = emitters.get(topicId);
			log.warn("emitter : {}",emitter);
			sseEmitters.remove(emitter);
			log.warn("sseEmitters.size() : {}",sseEmitters.size());
			if (sseEmitters.isEmpty()) {
				emitters.remove(topicId); // 리스트가 비어 있으면 tableId도 삭제
			}
			else emitters.put(topicId,sseEmitters);
		});

		emitter.onTimeout(() -> {
			List<SseEmitter> sseEmitters = emitters.get(topicId);
			sseEmitters.remove(emitter);
			if (sseEmitters.isEmpty()) {
				emitters.remove(topicId); // 리스트가 비어 있으면 tableId도 삭제
			}
		else emitters.put(topicId,sseEmitters);
		});

		// //dummy data 전송
		// sentToClient(topicId,"connected");
		return emitter;
	}

	public void sentToClient(String topicId, Object data){
		List<SseEmitter> emitters = this.emitters.get(topicId);
		if(emitters != null){
			synchronized(emitters){
				for(SseEmitter emitter : emitters){
					try{
						emitter.send(SseEmitter.event()
							.name("sse")
							.data(data, MediaType.APPLICATION_JSON)
							.reconnectTime(1000L));

					}catch(IOException e){
						emitters.remove(emitter);
					}
				}
			}
		}
	}

}
