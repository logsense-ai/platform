package com.logsense.platform.incident.enums;

public enum AnalyzeRequestStatus {

    PENDING,      // 분석 요청 대기 상태
    PROCESSING,   // 분석 진행 중
    SUCCESS,      // 분석 완료
    FAILED        // 분석 실패
}