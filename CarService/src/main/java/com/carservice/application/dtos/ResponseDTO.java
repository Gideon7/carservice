/**
 * 
 */
package com.carservice.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Giddytech
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
	private String status;
	private String message;
	private Object data;
}
