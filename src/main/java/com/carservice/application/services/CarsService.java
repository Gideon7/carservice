/**
 * 
 */
package com.carservice.application.services;

import com.carservice.application.dtos.CarDTO;
import com.carservice.application.dtos.ResponseDTO;

/**
 * @author Ojo Gideon .O 8th February, 2022
 *
 */
public interface CarsService {
	ResponseDTO addCar(CarDTO carDTO);
	ResponseDTO getCarByVin(String vin);
	ResponseDTO fetchAllCars();
	ResponseDTO fetchAllCarsByBrand(String brandName);
	ResponseDTO filterCarsByParam(String param, String order);
}
