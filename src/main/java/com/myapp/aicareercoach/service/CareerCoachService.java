package com.myapp.aicareercoach.service;

import com.myapp.aicareercoach.dto.CareerCoachRequest;
import com.myapp.aicareercoach.dto.CareerCoachResponse;
import com.myapp.aicareercoach.dto.llm.GeminiRequest;
import com.myapp.aicareercoach.dto.llm.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareerCoachService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public CareerCoachResponse getCoaching(CareerCoachRequest request) {
        try {
            String prompt = createPrompt(request);
            GeminiRequest geminiRequest = new GeminiRequest(prompt);

            GeminiResponse geminiResponse = webClient.post()
                    .uri(apiUrl + "?key=" + apiKey) // Gemini는 API 키를 쿼리 파라미터로 전달
                    .header("Content-Type", "application/json")
                    .bodyValue(geminiRequest)
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .block();

            String generatedContent = geminiResponse.getGeneratedText();
            return parseResponse(generatedContent);

        } catch (WebClientResponseException e) {
            // API 에러 발생 시 사용자에게 친절한 메시지 반환
            System.err.println("API Error: " + e.getResponseBodyAsString());
            return new CareerCoachResponse(
                    List.of("API 호출 중 오류가 발생했습니다."),
                    "잠시 후 다시 시도해주세요. 상태코드: " + e.getStatusCode()
            );
        }
    }

    private String createPrompt(CareerCoachRequest request) {
        // 프롬프트는 OpenAI와 동일하게 사용해도 잘 작동합니다.
        return String.format(
                """
                # ROLE
                You are a senior developer and a professional career coach. Your task is to provide personalized interview questions and a learning path for a job seeker based on their resume information.
    
                # RESUME INFORMATION
                - Career Summary: %s
                - Job Description: %s
                - Tech Skills: %s
    
                # INSTRUCTIONS
                1.  Based on the resume information, generate 5 in-depth, personalized interview questions.
                2.  Suggest a concrete and practical learning path to enhance the job seeker's capabilities.
                3.  Provide the output in the following format, separating sections with '###'.
    
                # OUTPUT FORMAT
                ###QUESTIONS###
                1. [Question 1]
                2. [Question 2]
                3. [Question 3]
                4. [Question 4]
                5. [Question 5]
                ###LEARNINGPATH###
                [Your detailed learning path recommendation here.]
                """,
                request.getCareerSummary(),
                request.getJobDescription(),
                String.join(", ", request.getTechSkills())
        );
    }

    private CareerCoachResponse parseResponse(String content) {
        // 응답 파싱 로직은 OpenAI와 동일하게 사용 가능
        if (content == null || content.isEmpty()) {
            return new CareerCoachResponse(List.of("응답 생성 실패"), "AI로부터 유효한 응답을 받지 못했습니다.");
        }

        String[] parts = content.split("###LEARNINGPATH###");
        String questionsPart = parts[0].replace("###QUESTIONS###", "").trim();
        String learningPath = parts.length > 1 ? parts[1].trim() : "학습 경로를 생성하지 못했습니다.";

        List<String> questions = Arrays.stream(questionsPart.split("\\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());

        return new CareerCoachResponse(questions, learningPath);
    }
}