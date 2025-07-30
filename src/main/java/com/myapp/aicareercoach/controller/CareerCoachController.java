package com.myapp.aicareercoach.controller;

import com.myapp.aicareercoach.dto.CareerCoachRequest;
import com.myapp.aicareercoach.dto.CareerCoachResponse;
import com.myapp.aicareercoach.service.CareerCoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coach") // API 엔드포인트 경로
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 만들어줍니다 (의존성 주입).
public class CareerCoachController {

    private final CareerCoachService careerCoachService;

    @PostMapping
    public ResponseEntity<CareerCoachResponse> getCoaching(@RequestBody CareerCoachRequest request) {
        CareerCoachResponse response = careerCoachService.getCoaching(request);
        return ResponseEntity.ok(response);
    }
}