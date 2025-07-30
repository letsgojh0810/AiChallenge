package com.myapp.aicareercoach.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * AI 커리어 코칭을 요청하기 위한 데이터 전송 객체(DTO)
 */
@Data
@ToString
public class CareerCoachRequest {

    private String careerSummary;
    private String jobDescription;
    private List<String> techSkills;

}