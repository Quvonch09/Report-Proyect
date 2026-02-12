package sfera.reportproyect.dto;

import java.util.Map;

public record CategoryStatusStat(String category, Map<String, Long> counts) {}
