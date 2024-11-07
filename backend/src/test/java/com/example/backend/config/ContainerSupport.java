package com.example.backend.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ContainerSupport {

	private static final MySQLContainer MY_SQL_CONTAINER;
	private static final String REDIS_IMAGE = "redis:6-alpine";
	private static final int REDIS_PORT = 6379;
	private static final GenericContainer REDIS;

	static {
		// Docker host 설정
		// System.setProperty("DOCKER_HOST", "unix:///var/run/docker.sock");
		// System.setProperty("testcontainers.reuse.enable", "true"); // 컨테이너 재사용 활성화
		// System.setProperty("testcontainers.ryuk.disabled", "true"); // Ryuk 비활성화


		MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8"))
			.withDatabaseName("ssafy")
			.withUsername("ssafy")
			.withPassword("ssafy")
			.withReuse(true);

		REDIS = new GenericContainer<>(DockerImageName.parse(REDIS_IMAGE))
			.withExposedPorts(REDIS_PORT)
			.withReuse(true);

		MY_SQL_CONTAINER.start();
		REDIS.start();
	}
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.datasource.url", () -> "jdbc:tc:mysql:8:///ssafy");
		registry.add("spring.datasource.username", () -> "ssafy");
		registry.add("spring.datasource.password", () -> "ssafy");
		registry.add("spring.data.redis.host", REDIS::getHost);
		registry.add("spring.data.redis.port", () -> REDIS.getMappedPort(REDIS_PORT).toString());

	}

}
