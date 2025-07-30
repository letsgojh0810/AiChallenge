package com.myapp.aicareercoach.dto.llm;

import lombok.Data;

import java.util.List;

// Gemini API에 보낼 요청 본문
@Data
public class GeminiRequest {
    private final List<Content> contents;

    public GeminiRequest(String prompt) {
        Part part = new Part(prompt);
        Content content = new Content(List.of(part));
        this.contents = List.of(content);
    }

    // 내부 클래스로 JSON 구조를 표현
    private record Content(List<Part> parts) {}
    private record Part(String text) {}
}