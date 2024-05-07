package com.finder.service;

import com.finder.domain.Bed;
import com.finder.repository.BedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BedService {
    private final BedRepository bedRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> getBedCountAndBedTime(String hpID) {
        Bed bed = bedRepository.findByHpID(hpID);
        Integer bedCount = bed.getCount();
        String bedTime = bed.getTime();
        Map<String, Object> bedCountAndBedTime = new HashMap<>();

        if (bedCount == null || bedCount < 0) {
            bedCount = 0;
        }

        bedCountAndBedTime.put("bedCount", bedCount);
        bedCountAndBedTime.put("bedTime", bedTime);

        return bedCountAndBedTime;
    }
}
