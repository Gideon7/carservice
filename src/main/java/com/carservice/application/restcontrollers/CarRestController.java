/**
 * 
 */
package com.carservice.application.restcontrollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carservice.application.dtos.CarDTO;
import com.carservice.application.dtos.ResponseDTO;
import com.carservice.application.services.CarsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ojo Gideon .O 8th February, 2022
 *
 */
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/car")
@RestController
@Tag(name = "Car Service Api endpoints", description = "These endpoints manages car retrieval, addition and filtering process")
@AllArgsConstructor
@Slf4j
public class CarRestController {
	
	private final CarsService carService;
	
	  @RequestMapping(value = {"/new"}, method = {RequestMethod.POST}, produces = {"application/json"})
	  @Operation(summary = "Add Car Model", description = "This api adds a new car model")
	  @ApiResponses({
		  @ApiResponse(responseCode = "200", description = "Car Model Added Successfully", 
				  content = {@Content(schema = @Schema(implementation = ResponseDTO.class))}), 
		  @ApiResponse(responseCode = "428", description = "PRECONDITION REQUIRED! EMPTY TEXTFIELDS DETECTED", 
		  		  content = {@Content(schema = @Schema(implementation = ResponseDTO.class))}), 
		  @ApiResponse(responseCode = "500", description = "Adding New Car Model Failed", 
		  		  content = {@Content(schema = @Schema(implementation = ResponseDTO.class))})})
	  public ResponseEntity<?> addCarModel(@RequestBody CarDTO carDTO) {
	    log.info("API Call To Add Car Model");
	    try {
	      ResponseDTO response = new ResponseDTO();
	      
	      ResponseDTO retValue = carService.addCar(carDTO);
	      if (retValue.getStatus().equalsIgnoreCase("EMPTY_TEXTFIELDS")) {
	         return new ResponseEntity<>(retValue, HttpStatus.PRECONDITION_REQUIRED);
	      } 
	      if (retValue.getStatus().equalsIgnoreCase("SUCCESS")) {
	        return new ResponseEntity<>(retValue, HttpStatus.OK);
	      } 
	      response.setStatus("FAILURE");
	      response.setMessage("Adding New Car Model Failed");
	      return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
	    } catch (Exception e) {
	      log.error("Exception occurred " + e);
	      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    } 
	  }
	  
	  @RequestMapping(value = {"/fetch"}, method = {RequestMethod.GET}, produces = {"application/json"})
	  @Operation(summary = "Get All Cars", description = "This Service Fetches All Cars")
	  @ApiResponses({
		  @ApiResponse(responseCode = "200", description = "Operation Successful", 
				  content = {
						  @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDTO.class)))}), 
		  @ApiResponse(responseCode = "500", description = "Internal Server Error", 
		  		  content = {@Content(schema = @Schema(implementation = ResponseDTO.class))})})
	  public ResponseEntity<?> fetchAllCars() {
	    log.info("API Call To Fetch All Cars");
	    try {
	       ResponseDTO retValue = carService.fetchAllCars();
	       if (retValue.getStatus().equalsIgnoreCase("SUCCESS")) {
	    	   return new ResponseEntity<>(retValue, HttpStatus.OK);
	       }
	       ResponseDTO response = ResponseDTO.builder().status("FAILURE").message("Unable To Fetch Cars").build();
	       return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	      log.error("Exception occurred " + e);
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	  }
	  
	  @RequestMapping(value = {"/{brandName}"}, method = {RequestMethod.GET}, produces = {"application/json"})
	  @Operation(summary = "Get All Cars By Brand", description = "This Service Fetches All Cars By Given Brand Name")
	  @ApiResponses({
		  @ApiResponse(responseCode = "200", description = "Operation Successful", 
				  content = {
						  @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDTO.class)))}), 
		  @ApiResponse(responseCode = "500", description = "Internal Server Error", 
		  		  content = {@Content(schema = @Schema(implementation = ResponseDTO.class))})})
	  public ResponseEntity<?> fetchAllCars(@PathVariable String brandName) {
	    log.info("API Call To Fetch All Cars By Brand Name");
	    try {
	       ResponseDTO retValue = carService.fetchAllCarsByBrand(brandName);
	       if (retValue.getStatus().equalsIgnoreCase("SUCCESS")) {
	    	   return new ResponseEntity<>(retValue, HttpStatus.OK);
	       }
	       ResponseDTO response = ResponseDTO.builder().status("FAILURE").message("Unable To Fetch Cars").build();
	       return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	      log.error("Exception occurred " + e);
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	  }
	  
	  @RequestMapping(value = {"/filter"}, method = {RequestMethod.GET}, produces = {"application/json"})
	  @Operation(summary = "Filter Cars", description = "This Service Filters All Cars")
	  @ApiResponses({
		  @ApiResponse(responseCode = "200", description = "Operation Successful", 
				  content = {
						  @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDTO.class)))}), 
		  @ApiResponse(responseCode = "500", description = "Internal Server Error", 
		  		  content = {@Content(schema = @Schema(implementation = ResponseDTO.class))})})
	  public ResponseEntity<?> filterAllCars(@RequestParam @Parameter(description = "color/brand/vin/year") String param, @RequestParam String order) {
	    log.info("API Call To Fetch All Cars");
	    try {
	       ResponseDTO retValue = carService.filterCarsByParam(param, order);
	       if (retValue.getStatus().equalsIgnoreCase("SUCCESS")) {
	    	   return new ResponseEntity<>(retValue, HttpStatus.OK);
	       }
	       ResponseDTO response = ResponseDTO.builder().status("FAILURE").message("Unable To Fetch Cars").build();
	       return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	      log.error("Exception occurred " + e);
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	  }
	  
	  @RequestMapping(value = {"/{vin}"}, method = {RequestMethod.GET}, produces = {"application/json"})
	  @Operation(summary = "Fetch Car Model By VIN", description = "This Service Fetches a particular Car Model By Car VIN")
	  @ApiResponses({
		  @ApiResponse(responseCode = "200", description = "Operation Successful", 
				  content = {
						  @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDTO.class)))}), 
		  @ApiResponse(responseCode = "500", description = "Internal Server Error", 
		  		  content = {@Content(schema = @Schema(implementation = ResponseDTO.class))})})
	  public ResponseEntity<?> fetchCar(@PathVariable String vin) {
	    log.info("API Call To Fetch Car Vin");
	    try {
	       ResponseDTO retValue = carService.getCarByVin(vin);
	       if (retValue.getStatus().equalsIgnoreCase("SUCCESS")) {
	    	   return new ResponseEntity<>(retValue, HttpStatus.OK);
	       }
	       else if (retValue.getStatus().equalsIgnoreCase("SUCCESS")) {
	    	   return new ResponseEntity<>(retValue, HttpStatus.NOT_FOUND);
	       }
	       ResponseDTO response = ResponseDTO.builder().status("FAILURE").message("Unable To Fetch Car").build();
	       return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	      log.error("Exception occurred " + e);
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	  }
	
}
