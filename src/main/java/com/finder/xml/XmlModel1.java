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
public class XmlModel1 {
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
        private List<Item1> item1;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item1 {
        // 기관 코드
        @XmlElement(name = "hpid")
        private String hpid;

        // 병상 수
        @XmlElement(name = "hvec")
        private Integer hvec;

        // 응급실 당직의 직통 연락처
        @XmlElement(name = "hv1")
        private String hv1;

        // 소아 당직의 직통 연락처
        @XmlElement(name = "hv12")
        private String hv12;

        // 구급차 가용 여부
        @XmlElement(name = "hvamyn")
        private String hvamyn;

        // CT 가용 여부
        @XmlElement(name = "hvctayn")
        private String hvctayn;

        // MRI 가용 여부
        @XmlElement(name = "hvmriayn")
        private String hvmriayn;
    }
}
