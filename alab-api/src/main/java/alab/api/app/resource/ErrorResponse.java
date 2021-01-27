package alab.api.app.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ErrorResponse {
    @JsonProperty("ErrorInfo")
    private ErrorInfo errorInfo;

    public ErrorResponse(String message, String detail, String code) {
        this.errorInfo = ErrorInfo.of(message, detail, code);
    }

}
