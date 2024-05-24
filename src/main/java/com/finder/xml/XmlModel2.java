package com.finder.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class XmlModel2 {
    @XmlElement(name = "body")
    private Body body;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {
        @XmlElement(name = "items")
        private Items items;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Items {
        @XmlElement(name = "item")
        private List<Item2> item2;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item2 {
        // 기관명
        @XmlElement(name = "dutyName")
        private String dutyName;

        // 주소
        @XmlElement(name = "dutyAddr")
        private String dutyAddr;

        // 대표 전화
        @XmlElement(name = "dutyTel1")
        private String dutyTel1;

        // 응급실 전화
        @XmlElement(name = "dutyTel3")
        private String dutyTel3;

        // 간이 약도
        @XmlElement(name = "dutyMapimg")
        private String dutyMapimg;

        // 병원 경도
        @XmlElement(name = "wgs84Lon")
        private Double wgs84Lon;

        // 병원 위도
        @XmlElement(name = "wgs84Lat")
        private Double wgs84Lat;

        // 진료 과목
        @XmlElement(name = "dgidIdName")
        private String dgidIdName;
    }
}
