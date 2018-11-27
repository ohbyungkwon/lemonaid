package com.demo.lemonaid.demo.Error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
