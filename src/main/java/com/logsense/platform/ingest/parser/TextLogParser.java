package com.logsense.platform.ingest.parser;

import com.logsense.platform.ingest.dto.RawLogMessage;

public interface TextLogParser {

    RawLogMessage parse(String rawLogLine);
}
