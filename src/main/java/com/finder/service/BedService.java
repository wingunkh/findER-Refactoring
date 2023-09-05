package com.finder.service;

import com.finder.domain.Bed;
import com.finder.dto.BedDataDto;
import com.finder.repository.BedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BedService {
    private final BedRepository bedRepository;

    public Integer findByNameAndTime(String name) {
        LocalDateTime now = LocalDateTime.now().minusMinutes(1);
        Bed bed = bedRepository.findByNameAndTime(name, now);
        System.out.println(now.getHour() + ":" + now.getMinute());

        if(bed == null) return null; // 나중에 삭제
        return bed.getCount();
    }

    // 병상수 관련 최근 데이터 조회
    public BedDataDto findByRecentV2(String name) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime before4Time = currentTime.minusHours(4).minusMinutes(1);

        // 현재 시간의 4시간 전부터 현재 시간까지의 Bed 데이터
        List<Bed> fourAgoBeds = bedRepository.findByRecent(name, before4Time, currentTime);

        // 병상 이용가능 했던 시간
        int count = 0;
        for (Bed bed : fourAgoBeds) {
            if(bed.getCount() > 0) count += 1;
        }

        int hour = count / 60;
        int minute = count - (hour * 60);
        String successTime = hour + "시간 " + minute + "분"; // output

        double percent = (count / 240.) * 100; // output
        double otherPercent = 100 - percent; // output

        // 소수점 첫째 자리까지 반올림
        percent = Math.round(percent * 10.0) / 10.0;
        otherPercent = Math.round(otherPercent * 10.0) / 10.0;

        // 현재 시간의 2시간 전부터 현재 시간까지의 Bed 데이터
        LocalDateTime before2Time = currentTime.minusHours(2).minusMinutes(1);
        List<Bed> twoAgoBeds = bedRepository.findByRecent(name, before2Time, currentTime);
        List<Integer> twoAgoList = new ArrayList<>();

        int curHour = currentTime.getHour() - 2; // 현재 시간의 2시간 전 Hour
        int curMin = currentTime.getMinute(); // 현재 시간의 Minute

        for(Bed bed : twoAgoBeds) {
            if(bed.getLocalDateTime().getHour() == curHour && bed.getLocalDateTime().getMinute() == curMin) {
                twoAgoList.add(bed.getCount());
                System.out.println(curHour + ":" + curMin);
                curMin += 15;
                if(curMin>=60) {
                    curMin -= 60;
                    curHour += 1;
                    if(curHour == 24) curHour = 0;
                }
            }
        }

        if(twoAgoList.size() == 8) twoAgoList.add(twoAgoBeds.get(twoAgoBeds.size() - 1).getCount());

        LocalDateTime time = twoAgoBeds.get(twoAgoBeds.size() - 1).getLocalDateTime();
        System.out.println(time.getHour() + ":" + time.getMinute());

        return new BedDataDto(successTime, percent, otherPercent, twoAgoList,  null);
    }
}
