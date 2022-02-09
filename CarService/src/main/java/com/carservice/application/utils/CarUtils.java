/**
 * 
 */
package com.carservice.application.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.carservice.application.dtos.CarDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ojo Gideon 8th February 2022
 *
 */
@Component
@Slf4j
public class CarUtils {
	
	@Autowired
	WebClient.Builder webClient;
	
	@Value("${car.api}")
    private String carApi;
	
	public List<CarDTO> getCars(){
		log.info("Api Call To Fetch All Cars");
		try {
			List<CarDTO> cars = new ArrayList<>();
			  String result =webClient
			            .build()
			            .get()
			            .uri(carApi)
			            .retrieve()
			            .bodyToMono(String.class)
			            .block();
			 JsonParser parser = new JsonParser();
             JsonElement responseElement = parser.parse(result);
             JsonArray array = responseElement.getAsJsonArray();
             int stages = array.size();
             for (int i = 0; i < stages; i++) {
            	 JsonElement jsonObj = array.get(i);
                 JsonObject jsonObj2 = jsonObj.getAsJsonObject();
                 String vin = jsonObj2.get("vin").getAsString();
                 String brand = jsonObj2.get("brand").getAsString();
                 int year = jsonObj2.get("year").getAsInt();
                 String color = jsonObj2.get("color").getAsString();
                 CarDTO carDTO = CarDTO.builder().vin(vin).brand(brand).year(year).color(color).build();
                 cars.add(carDTO);
             }
             return cars;
		}catch(Exception e) {
			log.error("Unable To Call Api: Exception Occured: "+ e.getMessage());
			return new ArrayList<CarDTO>();
		}
	}
}
