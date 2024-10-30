package com.example.backend.notification.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.backend.common.redis.repository.RedisHashRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	// 테이블 ID별로 구독자를 관리하는 맵
	private final ConcurrentHashMap<String, List<SseEmitter>> tableEmitters = new ConcurrentHashMap<>();

	public SseEmitter subscribe(String tableId){

		SseEmitter emitter = new SseEmitter(600000L);

		// 새로운 구독자 추가
		if(!tableEmitters.containsKey(tableId)){
			List<SseEmitter> sseEmitters = new CopyOnWriteArrayList<>();
			sseEmitters.add(emitter);
			tableEmitters.put(tableId,sseEmitters);
		}
		else{
			List<SseEmitter> sseEmitters = tableEmitters.get(tableId);
			sseEmitters.add(emitter);
			tableEmitters.put(tableId,sseEmitters);
		}

		// 연결 종료 시 해당 emitter만 구독 목록에서 제거
		emitter.onCompletion(() -> {
			List<SseEmitter> sseEmitters = tableEmitters.get(tableId);
			log.warn("emitter : {}",emitter);
			sseEmitters.remove(emitter);
			log.warn("sseEmitters.size() : {}",sseEmitters.size());
			if (sseEmitters.isEmpty()) {
				tableEmitters.remove(tableId); // 리스트가 비어 있으면 tableId도 삭제
			}
			else tableEmitters.put(tableId,sseEmitters);
		});

		emitter.onTimeout(() -> {
			List<SseEmitter> sseEmitters = tableEmitters.get(tableId);
			sseEmitters.remove(emitter);
			if (sseEmitters.isEmpty()) {
				tableEmitters.remove(tableId); // 리스트가 비어 있으면 tableId도 삭제
			}
		else tableEmitters.put(tableId,sseEmitters);
		});

		return emitter;
	}

	public void sentToClient(String topicId, Object data){
		List<SseEmitter> emitters = tableEmitters.get(topicId);
		log.warn("여기서 터지나?");
		// for(SseEmitter emitter : emitters){
		// 	log.warn("emitter : {}",emitter);
		// }
		if(emitters != null){
			synchronized(emitters){

				for(SseEmitter emitter : emitters){
					try{
						log.warn("emitter@@@@@ : {}",emitter);
						emitter.send(SseEmitter.event()
							.name("cart_items")
							.data(data, MediaType.APPLICATION_JSON)
							.reconnectTime(1000L));

						log.warn("보냈다");
					}catch(IOException e){
						emitters.remove(emitter);
					}
				}
			}
		}
	}

}
