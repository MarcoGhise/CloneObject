package it.blog.CloneObject.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JsonSerializator {

	@Autowired
	ObjectMapper objectMapper;
	
	public <T> String serializeObject(T obj) {
		try {
			/*
			 * Serialize Object
			 */
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("Serialize Object", e);
			return "";
		}
	}
	

	public <T> T deSerializeObject(String json, Class<T> valueType) {
		try {
			/*
			 * Deserialize Object
			 */
			return objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			log.error("Deserialize Object", e);
			return null;
		}
	}
}
