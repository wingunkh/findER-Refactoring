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
        if(bed == null) bed = bedRepository.findByNameAndTime(name, now.minusMinutes(1));
        if(bed == null) return 0; // 기존 시간, 1분전 모두 데이터가 없을 경우 0 반환

        System.out.println(now.getHour() + ":" + now.getMinute());
        return bed.getCount();
    }

    // 병상수 관련 최근 데이터 조회
    public BedDataDto findByRecentV2(String name) {
        LocalDateTime currentTime = LocalDateTime.now().minusMinutes(1);
        LocalDateTime before2Time = currentTime.minusHours(2);
        System.out.println("currTime = "  + currentTime.getHour() + " : " + currentTime.getMinute());

        // 현재 시간의 2시간 전부터 현재 시간까지의 Bed 데이터
        List<Bed> twoAgoBeds = bedRepository.findByRecent(name, before2Time, currentTime);

        // 시간 순 정렬
        Collections.sort(twoAgoBeds, new Comparator<Bed>() {
            @Override
            public int compare(Bed o1, Bed o2) {
                return o1.getLocalDateTime().compareTo(o2.getLocalDateTime());
            }
        });

        // 병상 이용가능 했던 시간
        System.out.println("twoAgoBeds = " + twoAgoBeds.size());
        int count = 0;
        int size = twoAgoBeds.size();
        LocalDateTime pre = null;
        if(size > 0) pre = twoAgoBeds.get(0).getLocalDateTime().minusMinutes(1);

        for (int i = 0; i < size; i++) {
            Bed bed = twoAgoBeds.get(i);
            if(bed.getLocalDateTime().equals(pre.plusMinutes(1))) {
                if(bed.getCount() > 0) count += 1;
            } else { // 중간이 빈 경우
                if (i - 1 > 0) {
                    if(twoAgoBeds.get(i - 1).getCount() > 0) count +=1;
                    if(bed.getCount() > 0) count += 1;
                }
            }
            pre = bed.getLocalDateTime();
        }

        int hour = count / 60;
        int minute = count - (hour * 60);
        String successTime = hour + "시간 " + minute + "분"; // output
        System.out.println("count = " + count);
        double percent = (count / 120.) * 100; // output
        double otherPercent = 100 - percent; // output

        // 소수점 첫째 자리까지 반올림
        percent = Math.round(percent * 10.0) / 10.0;
        otherPercent = Math.round(otherPercent * 10.0) / 10.0;

        int curHour = 0;
        int curMin = 0;
        if(twoAgoBeds.size() > 0) {
            curHour = twoAgoBeds.get(0).getLocalDateTime().getHour(); // 현재 시간의 2시간 전 Hour
            curMin = twoAgoBeds.get(0).getLocalDateTime().getMinute(); // 현재 시간의 Minute
        }

        List<Integer> twoAgoList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Bed bed = twoAgoBeds.get(i);
            LocalDateTime lt = bed.getLocalDateTime().minusMinutes(1);
            if(curHour == lt.getHour() && curMin == lt.getMinute()) {
                if (i - 1 >= 0) { // 이전 데이터가 있는 경우
                    if(twoAgoBeds.get(i - 1).getLocalDateTime().equals(lt.minusMinutes(1))) { // 1분전 데이터가 있는 경우
                        if (twoAgoBeds.get(i - 1).getCount() < 0) twoAgoList.add(0);
                        else twoAgoList.add(twoAgoBeds.get(i - 1).getCount());
                        System.out.println(twoAgoBeds.get(i - 1).getLocalDateTime().getHour() + " || " + twoAgoBeds.get(i - 1).getLocalDateTime().getMinute());
                    } else twoAgoList.add(0);
                } else { // 이전 데이터가 없는 경우
                    twoAgoList.add(0);
                }

                System.out.println("빈 데이터 => " + curHour + ":" + curMin);
                curMin += 15;
                if(curMin>=60) {
                    curMin -= 60;
                    curHour += 1;
                    if(curHour == 24) curHour = 0;
                }
            }
            if(bed.getLocalDateTime().getHour() == curHour && bed.getLocalDateTime().getMinute() == curMin) {
                if(bed.getCount() < 0) twoAgoList.add(0);
                else twoAgoList.add(bed.getCount());
                System.out.println(curHour + ":" + curMin);
                curMin += 15;
                if(curMin>=60) {
                    curMin -= 60;
                    curHour += 1;
                    if(curHour == 24) curHour = 0;
                }
            }
        }

        if(twoAgoList.size() < 8) { // 데이터가 부족한 경우
            int s = twoAgoList.size();
            for (int i = 0; i <= 8 - s; i++) twoAgoList.add(0);
        } else if(twoAgoList.size() == 8) {
            Integer count1 = twoAgoBeds.get(twoAgoBeds.size() - 1).getCount();
            if(count1 < 0) twoAgoList.add(0);
            else twoAgoList.add(count1);

            LocalDateTime time = twoAgoBeds.get(twoAgoBeds.size() - 1).getLocalDateTime();
            System.out.println("\n" + time.getHour() + ":" + time.getMinute());
        }

        return new BedDataDto(successTime, percent, otherPercent, twoAgoList,  null);
    }
}
