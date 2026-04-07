package com.logsense.platform.ingest.controller;

import com.logsense.platform.common.BasicResponseDto;
import com.logsense.platform.ingest.dto.LogIngestRequest;
import com.logsense.platform.ingest.dto.RawLogMessage;
import com.logsense.platform.ingest.service.LogIngestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
@Tag(name = "Log Ingest", description = "로그 수집 API")
public class LogIngestController {

    private final LogIngestService logIngestService;

    @PostMapping("/ingest")
    @Operation(
            summary = "원시 로그 한 줄 수집",
            description = "rawLogLine 문자열을 받아 파싱된 로그 정보를 반환합니다."
    )
    public BasicResponseDto<RawLogMessage> ingestLog(
            @Valid @RequestBody LogIngestRequest request
    ) {
        RawLogMessage result = logIngestService.ingest(request);

        return BasicResponseDto.success("로그 수집 및 파싱이 완료되었습니다.", result);
    }
}