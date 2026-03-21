package com.logsense.platform.analysis.enums;

public enum UsageStatus {

    SUCCESS,   // 정상 호출 및 응답 완료
    FAILED,    // 호출 실패 (에러 발생)
    TIMEOUT    // 응답 지연으로 인한 타임아웃
}
