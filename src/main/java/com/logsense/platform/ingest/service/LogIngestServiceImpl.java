package com.logsense.platform.ingest.service;

import com.logsense.platform.common.exception.CustomException;
import com.logsense.platform.common.exception.ErrorCode;
import com.logsense.platform.ingest.dto.LogIngestRequest;
import com.logsense.platform.ingest.dto.RawLogMessage;
import com.logsense.platform.ingest.parser.TextLogParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LogIngestServiceImpl implements LogIngestService {

    private final TextLogParser textLogParser;

    @Override
    public RawLogMessage ingest(LogIngestRequest request) {
        validate(request);
        return textLogParser.parse(request.rawLogLine());
    }

    private void validate(LogIngestRequest request) {
        if (request == null) {
            throw new CustomException(ErrorCode.INVALID_LOG_INGEST_REQUEST);
        }

        if (!StringUtils.hasText(request.rawLogLine())) {
            throw new CustomException(ErrorCode.EMPTY_RAW_LOG_LINE);
        }
    }
}