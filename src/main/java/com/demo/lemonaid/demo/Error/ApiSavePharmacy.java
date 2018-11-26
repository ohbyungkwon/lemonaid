package com.demo.lemonaid.demo.Error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiSavePharmacy {
    private int id;
    private String name;
    private double lat;
    private double lon;
    private String deviceId;
}
