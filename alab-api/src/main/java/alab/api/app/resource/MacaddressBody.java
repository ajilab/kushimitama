package alab.api.app.resource;

import alab.api.domain.object.Macaddress;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MacaddressBody {
    @NotBlank
    @Pattern(regexp = "[0-9]*")
    @Size(min = 5, max = 5)
    private String userid;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]*")
    @Size(min = 12, max = 12)
    private String macaddress;

    @NotBlank
    @Size(max = 80)
    private String biko;

    public Macaddress toDomainMacaddress() {
        return Macaddress.builder()
                .userid(this.userid)
                .macaddress(this.macaddress)
                .biko(this.biko)
                .build();
    }
}
