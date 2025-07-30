package com.myapp.aicareercoach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * AI 커리어 코칭 결과를 담는 데이터 전송 객체(DTO)
 */
@Getter
@Setter
@NoArgsConstructor      // (1) JSON 처리를 위한 기본 생성자
@AllArgsConstructor     // (2) 서비스 로직에서 객체 생성을 위한 전체 필드 생성자
public class CareerCoachResponse {

    /**
     * 생성된 맞춤 면접 질문 리스트
     */
    private List<String> interviewQuestions;

    /**
     * 개인 맞춤형 학습 경로 제안
     */
    private String learningPath;
}