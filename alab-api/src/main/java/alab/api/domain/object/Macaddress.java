package alab.api.domain.object;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Macaddress {
    private String macaddress;
    private String userid;
    private String biko;
}
