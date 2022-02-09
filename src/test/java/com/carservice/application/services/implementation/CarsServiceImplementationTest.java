/**
 * 
 */
package com.carservice.application.services.implementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.carservice.application.dtos.CarDTO;
import com.carservice.application.dtos.ResponseDTO;
import com.carservice.application.utils.CarUtils;

/**
 * @author Ojo Gideon .O 9th February, 2022
 *
 */
class CarsServiceImplementationTest {
	
	CarsServiceImplementation carServiceImpl;
	CarUtils carApiCall;
	
	@BeforeEach
	void setUp() throws Exception {
		carServiceImpl = Mockito.mock(CarsServiceImplementation.class);
		carApiCall = Mockito.mock(CarUtils.class);
	}

	/**
	 * Test method for {@link com.carservice.application.services.implementation.CarsServiceImplementation#addCar(com.carservice.application.dtos.CarDTO)}.
	 */
	@Test
	final void testAddCar() {
		CarDTO carTest = CarDTO.builder().brand("SKoda").color("Black").vin("fr234rt").year(2022).build();
		 List<CarDTO> cars = new ArrayList<>();
		 cars = carApiCall.getCars();
		 cars.add(carTest);
		 
		 ResponseDTO response = new ResponseDTO();
		 when(carServiceImpl.addCar(carTest)).thenReturn(response);
		 assertThat(cars).contains(carTest);
	}

}
