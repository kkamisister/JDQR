package com.example.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SocketRequest<T> {

	@NonNull
	Long requestUserId;

	@NonNull
	String responseChannel;

	T content;

}