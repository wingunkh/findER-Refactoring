package com.finder.xml;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
        private String wgs84Lon;

        // 병원 위도
        @XmlElement(name = "wgs84Lat")
        private String wgs84Lat;
    }
}