package com.agritrading.AgritradingApplication.util;

import com.agritrading.AgritradingApplication.dto.FarmerDTO;
import com.agritrading.AgritradingApplication.model.Farmers;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class MapFarmerDTO {
    public static FarmerDTO map(Farmers Farmer){
        return FarmerDTO.builder()
                .farmerId(Farmer.getFarmerId())
                .name(Farmer.getName())
                .farmLocation(Farmer.getFarmLocation())
                .contactInfo(Farmer.getContactInfo())
                .farmType(Farmer.getFarmType())
                .certification(Farmer.getCertification())
                .build();

    }

    public static List<FarmerDTO> map(List<Farmers> farmers){
        return farmers.stream().map(MapFarmerDTO::map).collect(Collectors.toList());

    }
}
