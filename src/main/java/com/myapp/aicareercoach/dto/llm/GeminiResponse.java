package com.myapp.aicareercoach.dto.llm;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GeminiResponse {
    private List<Candidate> candidates;

    /**
     * Gemini 응답에서 실제 텍스트 내용을 편리하게 추출하기 위한 헬퍼 메소드입니다.
     * @return 생성된 텍스트 또는 빈 문자열
     */
    public String getGeneratedText() {
        // 응답이 유효하고, 내용이 비어있지 않은지 확인
        if (candidates != null && !candidates.isEmpty() &&
                candidates.get(0).getContent() != null &&
                candidates.get(0).getContent().getParts() != null &&
                !candidates.get(0).getContent().getParts().isEmpty()) {
            return candidates.get(0).getContent().getParts().get(0).getText();
        }
        // 유효한 응답이 없을 경우 빈 문자열 반환
        return "";
    }

    // --- 내부 클래스 (JSON 구조 매핑용) ---
    @Getter
    @Setter
    public static class Candidate {
        private Content content;
    }

    @Getter
    @Setter
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @Setter
    public static class Part {
        private String text;
    }
}