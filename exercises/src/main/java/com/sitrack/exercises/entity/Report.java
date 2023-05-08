package com.sitrack.exercises.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report {
    String loginCode;
    String reportDate;
    String reportType;
    Double latitude;
    Double longitude;
    Double gpsDop;
    Integer healding;
    Double speed;
    String speedLabel;
    Integer gpsSatellites;
    String text;
    String textLabel;
}
