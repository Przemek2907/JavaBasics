package pl.zochowski.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionInfo {
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public ExceptionInfo(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
