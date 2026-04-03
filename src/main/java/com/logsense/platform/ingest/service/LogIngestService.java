package com.logsense.platform.ingest.service;

import com.logsense.platform.ingest.dto.LogIngestRequest;
import com.logsense.platform.ingest.dto.RawLogMessage;

public interface LogIngestService {

    RawLogMessage ingest(LogIngestRequest request);
}
