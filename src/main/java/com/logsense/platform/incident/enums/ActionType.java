package com.logsense.platform.incident.enums;

public enum ActionType {

    CREATE,           // Incident 생성
    ANALYZE_START,    // 분석 시작
    RESOLVE,          // 장애 해결 처리
    RETRY             // 재시도 (AI 분석 또는 처리 재시도)
}