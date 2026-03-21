package com.logsense.platform.incident.enums;

public enum Severity {

    LOW,        // 영향도가 낮은 경미한 장애
    MEDIUM,     // 일부 기능 영향이 있는 장애
    HIGH,       // 주요 기능에 영향이 있는 장애
    CRITICAL    // 서비스 전체에 영향이 있는 심각한 장애
}