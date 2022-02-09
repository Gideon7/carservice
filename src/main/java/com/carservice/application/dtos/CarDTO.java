/**
 * 
 */
package com.carservice.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ojo Gideon .O 8th February, 2022
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {
	private String vin;
	private String brand;
	private Integer year;
	private String color;
}
