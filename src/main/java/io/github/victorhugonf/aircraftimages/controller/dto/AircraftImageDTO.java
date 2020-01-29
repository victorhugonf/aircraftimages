package io.github.victorhugonf.aircraftimages.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AircraftImageDTO{
    private String registration;
    private String imageLink;
}
