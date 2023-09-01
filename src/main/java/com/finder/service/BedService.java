package com.finder.service;

import com.finder.domain.Bed;
import com.finder.dto.BedDataDto;
import com.finder.repository.BedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // 병상수 관련 최근 데이터 조회 (v1 => 쿼리로 해결)
    public BedDataDto findByRecentV1(String name) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime before4Time = currentTime.minusHours(4);

        // 현재 시간의 4시간 전부터 현재 시간까지의 Bed 데이터
        List<Bed> fourAgoBeds = bedRepository.findByRecent(name, before4Time, currentTime);

        int count = 0;
        // 병상 이용가능 했던 시간
        for (Bed bed : fourAgoBeds) {
            if(bed.getCount() > 0) count += 1;
        }

        int hour = count / 60;
        int minute = count - (hour * 60);
        String successTime = hour + "시간 " + minute + "분"; // output

        double percent = (count / 240) * 100; // output
        double otherPercent = 100 - percent; // output

        // 현재 시간의 2시간 전부터 현재 시간까지의 Bed 데이터
        LocalDateTime before2Time = currentTime.minusHours(2);
        LocalDateTime before1Time = currentTime.minusHours(1);
        List<Bed> twoAgoBeds = bedRepository.findByNewRecent(name, before2Time, before1Time);
        Bed b1 = bedRepository.findByNameAndTime(name, before2Time);
        System.out.println(b1.getTime().getHour() +":"+b1.getTime().getMinute());
        for (Bed oneAgoBed : twoAgoBeds) {
            System.out.println(oneAgoBed.getTime().getHour() + ":" + oneAgoBed.getTime().getMinute());
        }
        List<Integer> twoAgoList = new ArrayList<>(); // output
        twoAgoList.add(b1.getCount());
        for (Bed twoAgoBed : twoAgoBeds) {
            twoAgoList.add(twoAgoBed.getCount());
        }

        // 현재 시간의 1시간 전부터 현재 시간까지의 Bed 데이터
        List<Bed> oneAgoBeds = bedRepository.findByNewRecent(name, before1Time, currentTime);
        Bed b2 = bedRepository.findByNameAndTime(name, before1Time);
        System.out.println(b2.getTime().getHour() +":"+b2.getTime().getMinute());
        for (Bed oneAgoBed : oneAgoBeds) {
            System.out.println(oneAgoBed.getTime().getHour() + ":" + oneAgoBed.getTime().getMinute());
        }
        List<Integer> oneAgoList = new ArrayList<>(); // output
        oneAgoList.add(b2.getCount());
        Bed b3 = bedRepository.findByNameAndTime(name, currentTime.minusMinutes(1));
        oneAgoList.add(b3.getCount());
        System.out.println(b3.getTime().getHour() +":"+b3.getTime().getMinute());
        for (Bed oneAgoBed : oneAgoBeds) {
            oneAgoList.add(oneAgoBed.getCount());
        }

        return new BedDataDto(successTime, percent, otherPercent, twoAgoList, oneAgoList);
    }

    // 병상수 관련 최근 데이터 조회 (v2 => 조건검사)
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

        double percent = (count / 240) * 100; // output
        double otherPercent = 100 - percent; // output

        // 현재 시간의 2시간 전부터 현재 시간까지의 Bed 데이터
        LocalDateTime before2Time = currentTime.minusHours(2).minusMinutes(1);
        List<Bed> twoAgoBeds = bedRepository.findByRecent(name, before2Time, currentTime);
        List<Integer> twoAgoList = new ArrayList<>();

        int curHour = currentTime.getHour() - 2; // 현재 시간의 2시간 전 Hour
        int curMin = currentTime.getMinute(); // 현재 시간의 Minute

        for(Bed bed : twoAgoBeds) {
            if(bed.getTime().getHour() == curHour && bed.getTime().getMinute() == curMin) {
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

        LocalDateTime time = twoAgoBeds.get(twoAgoBeds.size() - 1).getTime();
        System.out.println("\n" + time.getHour() + ":" + time.getMinute());

        return new BedDataDto(successTime, percent, otherPercent, twoAgoList,  null);
    }
}
