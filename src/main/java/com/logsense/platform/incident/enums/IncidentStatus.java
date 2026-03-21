package com.logsense.platform.incident.enums;

public enum IncidentStatus {

    OPEN,        // 장애가 감지되어 생성된 초기 상태
    ANALYZING,   // AI 분석 또는 추가 분석 진행 중
    RESOLVED,    // 장애가 해결된 상태
    FAILED       // 분석 실패 또는 처리 실패 상태
}