package com.logsense.platform.incident.enums;

public enum LogRole {

    SAMPLE,             // 대표 샘플 로그 (Incident 요약용)
    CONTEXT,            // 전후 맥락 파악용 로그
    ROOT_CAUSE_HINT     // 원인 추정에 도움 되는 핵심 로그
}