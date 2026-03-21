package com.logsense.platform.analysis.enums;

public enum AiAnalysisStatus {

    PENDING,    // AI 분석 요청 생성됨 (대기 상태)
    SUCCESS,    // AI 분석 정상 완료
    FAILED      // AI 분석 실패 (에러 또는 타임아웃)
}