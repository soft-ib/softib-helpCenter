package com.softib.helpcenter.util;

 import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HelpCenterUtils {
	private static final String PUSH_SERVER_URI ="http://soft-ib-push-server:8785/push";

	private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

 	public static double compare (String first, String second) {
		JaroWinklerDistance j = new JaroWinklerDistance();
		return j.apply(first, second);
 
	}
	 public static void pushNotification(String username, String message) {
		 RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    MessageBean msg = new MessageBean();
		    msg.setUsername(username);
		    msg.setContent(message);
		   String body;
		try {
			body = OBJECT_MAPPER.writeValueAsString(msg);
		
		    HttpEntity<String> request = 
		    	      new HttpEntity<String>(body, headers);
		    restTemplate.postForEntity(PUSH_SERVER_URI, request, String.class);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	 }
	 private static ObjectMapper createObjectMapper() {
			return new ObjectMapper();
		}
}
