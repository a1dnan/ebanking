package com.aalil.ebanking.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OperationNoPermittedException extends RuntimeException{

    private final String errorMsg;
    private final String operationId;
    private final String source;
    private final String dependency;

}
