package com.finder.service;

import com.finder.repository.BedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BedService {
    private final BedRepository bedRepository;

    @Transactional(readOnly = true)
    public Integer getBedCount(String hpID) {
        Integer bedCount = bedRepository.findByHpID(hpID).getCount();

        if (bedCount == null || bedCount < 0) {
            bedCount = 0;
        }

        return bedCount;
    }
}
