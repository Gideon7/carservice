/**
 * 
 */
package com.carservice.application.services.implementation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.carservice.application.dtos.CarDTO;
import com.carservice.application.dtos.ResponseDTO;
import com.carservice.application.services.CarsService;
import com.carservice.application.utils.CarUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ojo Gideon .O 8th February, 2022
 *
 */
@Service
@Slf4j
public class CarsServiceImplementation implements CarsService {
	
	private final CarUtils carApiCall;
	
	public CarsServiceImplementation(CarUtils carApiCall) {
		this.carApiCall = carApiCall;
	}

	@Override
	public ResponseDTO addCar(CarDTO carDTO) {
		log.info("Add New Car To List");
		ResponseDTO response = new ResponseDTO();
		try {
			if (carDTO.getBrand() == null || carDTO.getBrand().isEmpty() || carDTO.getColor() == null || carDTO.getColor().isEmpty() || carDTO.getVin() == null || carDTO.getVin().isEmpty() ||
					carDTO.getYear() == null || carDTO.getYear() == 0) {
				response.setStatus("EMPTY_TEXTFIELDS");
				response.setMessage("Fill Up Empty TextFields");
				return response;
			}
			List<CarDTO> cars = carApiCall.getCars();
			cars.add(carDTO);
			response.setStatus("SUCCESS");
			response.setMessage("Car Added Successfully");
			response.setData(cars);
			return response;
		}catch(Exception e) {
			log.error("Unable To Implement: "+ e.getMessage());
			response.setStatus("FAILURE");
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@Override
	public ResponseDTO fetchAllCars() {
		log.info("Fetch All Cars");
		
		ResponseDTO response = new ResponseDTO();
		List<CarDTO> cars = carApiCall.getCars();
		var arrangedResult = cars.stream().sorted(Comparator.comparingInt(CarDTO::getYear).reversed()).collect(Collectors.toList());
		response.setStatus("SUCCESS");
		response.setMessage("Cars Fetched Successfully");
		response.setData(arrangedResult);
		return response;
	}

	@Override
	public ResponseDTO fetchAllCarsByBrand(String brandName) {
		log.info("Fetch All Cars By Brand Name");
		
		ResponseDTO response = new ResponseDTO();
		List<CarDTO> cars = carApiCall.getCars();
		
		var filteredResults = cars.stream().filter(car -> car.getBrand().equalsIgnoreCase(brandName)).sorted(Comparator.comparingInt(CarDTO::getYear).reversed()).collect(Collectors.toList());
		response.setStatus("SUCCESS");
		response.setMessage("Cars Fetched Successfully");
		response.setData(filteredResults);
		return response;
	}

	@Override
	public ResponseDTO filterCarsByParam(String param, String order) {
		log.info("Filter Cars By Color");
		List<CarDTO> cars = carApiCall.getCars();
		List<CarDTO> filteredResults = new ArrayList<>();
		switch (param) {
		case "color":
			if (order.equalsIgnoreCase("asc")) {
				filteredResults = cars.stream().sorted(Comparator.comparing(CarDTO::getColor)).collect(Collectors.toList());
				break;
			}
			filteredResults = cars.stream().sorted(Comparator.comparing(CarDTO::getColor).reversed()).collect(Collectors.toList());
			break;
		case "brand":
			if (order.equalsIgnoreCase("asc")) {
				filteredResults = cars.stream().sorted(Comparator.comparing(CarDTO::getBrand)).collect(Collectors.toList());
				break;
			}
			filteredResults = cars.stream().sorted(Comparator.comparing(CarDTO::getBrand).reversed()).collect(Collectors.toList());
			break;
		case "vin":
			if (order.equalsIgnoreCase("asc")) {
				filteredResults = cars.stream().sorted(Comparator.comparing(CarDTO::getVin)).collect(Collectors.toList());
				break;
			}
			filteredResults = cars.stream().sorted(Comparator.comparing(CarDTO::getVin).reversed()).collect(Collectors.toList());
			break;
		case "year":
			if (order.equalsIgnoreCase("asc")) {
				filteredResults = cars.stream().sorted(Comparator.comparingInt(CarDTO::getYear)).collect(Collectors.toList());
				break;
			}
			filteredResults = cars.stream().sorted(Comparator.comparingInt(CarDTO::getYear).reversed()).collect(Collectors.toList());
			break;
		}
		ResponseDTO response = ResponseDTO.builder().status("SUCCESS").message("Cars Filtered Successfully").data(filteredResults).build();
		return response;
		
	}

	@Override
	public ResponseDTO getCarByVin(String vin) {
		log.info("Fetch Car By VIN");
		List<CarDTO> cars = carApiCall.getCars();
		var carRecord = cars.stream().filter(car -> car.getVin().equalsIgnoreCase(vin)).findAny().orElse(null);
		if (carRecord != null) {
			ResponseDTO response = ResponseDTO.builder().status("SUCCESS").message("Car Fetched Successfully").data(carRecord).build();
			return response;
		}
		ResponseDTO response = ResponseDTO.builder().status("NOT_FOUND").message("Car Model with VIN: "+vin+" Not Found").build();
		return response;
	}

}
