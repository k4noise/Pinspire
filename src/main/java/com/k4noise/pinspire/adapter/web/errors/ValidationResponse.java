package com.k4noise.pinspire.adapter.web.errors;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ValidationResponse(String error, String field, Object value, String message) { }