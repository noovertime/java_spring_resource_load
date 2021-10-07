package com.noovertime.demo.rsc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class LoadService {
	private static final String FILE_NAME = "hello.txt";

	@PostConstruct
	public void init() {

		log.debug("loadResourceUtil ----- ");
		try {
			this.loadResourceUtil();
		}
		catch(Exception ex) {
			log.error("실패로 종료 : {}", ex.getMessage());
		}


		log.debug("loadClassPathResource1 ----- ");
		try {
			this.loadClassPathResource1();
		}
		catch(Exception ex) {
			log.error("실패로 종료 : {}", ex.getMessage());
		}

		log.debug("loadClassPathResource2 ----- ");
		try {
			this.loadClassPathResource2();
		}
		catch(Exception ex) {
			log.error("실패로 종료 : {}", ex.getMessage());
		}
	}

	/**
	 * ResourceUtil로 접근
	 * @throws Exception 처리 중 예외
	 */
	private void loadResourceUtil() throws Exception {
		File file = ResourceUtils.getFile( "classpath:" + FILE_NAME );
		this.logFile( file );
	}

	/**
	 * ClassPathResource로 접근
	 * @throws Exception 처리 중 예외
	 */
	private void loadClassPathResource1() throws Exception {
		ClassPathResource rsc = new ClassPathResource( FILE_NAME );
		this.logFile( rsc.getFile() );
	}

	/**
	 * ClassPathResource로 접근
	 * @throws Exception 처리 중 예외
	 */
	private void loadClassPathResource2() throws Exception {
		ClassPathResource rsc = new ClassPathResource( FILE_NAME );
		byte[] bytes = FileCopyUtils.copyToByteArray( rsc.getInputStream());
		log.debug("contents ==> {}", new String( bytes, StandardCharsets.UTF_8));
	}

	/**
	 * 파일 정보 출력
	 * @param file 대상 파일
	 * @throws Exception 처리 중 예외
	 */
	private void logFile(File file) throws Exception {
		if( !file.exists()) {
			log.error("존재하지 않는 파일. file={}", file.getAbsolutePath());
			return;
		}


		log.debug("file path : {}", file.getAbsolutePath());
		try ( BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( file ))) ) {
			log.debug("contents ==> {}", FileCopyUtils.copyToString( reader ));
		}
	}
}
