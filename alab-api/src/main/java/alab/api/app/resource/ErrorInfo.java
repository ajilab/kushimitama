package alab.api.app.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value(staticConstructor="of")
public class ErrorInfo {
    @JsonProperty("Message")
    private String msg;
    @JsonProperty("Detail")
    private String detail;
    @JsonProperty("Code")
    private String code;
}
