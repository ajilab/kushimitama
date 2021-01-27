package alab.api.infrastructure.macaddress.entity;

import alab.api.domain.object.Macaddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ARSMMACADDR")
public class MacaddressEntity {
    @Builder.Default
    @Column(name = "table_id")
    private String tableId = "MMAC";

    @Id
    @Column(name = "macaddress")
    private String macaddress;

    @Column(name = "userid")
    private String userid;

    @Column(name = "biko")
    private String biko;

    @Builder.Default
    @Column(name = "create_by")
    private String createBy = "ARSWEB";

    @Builder.Default
    @Column(name = "create_date")
    private java.sql.Timestamp createDate = new java.sql.Timestamp(System.currentTimeMillis());

    @Builder.Default
    @Column(name = "update_by")
    private String updateBy = "ARSWEB";

    @Builder.Default
    @Column(name = "update_date")
    private java.sql.Timestamp updateDate = new java.sql.Timestamp(System.currentTimeMillis());

    /**
     * ドメインオブジェクトからインスタンスを生成
     * @param macaddress ドメインオブジェクト
     * @return MacaddressEntity
     */
    public static MacaddressEntity build(Macaddress macaddress) {
        return MacaddressEntity.builder()
                .macaddress(macaddress.getMacaddress())
                .userid(macaddress.getUserid())
                .biko(macaddress.getBiko())
                .build();

    }

    /**
     * ドメインオブジェクトへの変換
     * @return ドメインオブジェクト
     */
    public Macaddress toDomainMacaddress() {
        return Macaddress.builder()
                .macaddress(this.getMacaddress())
                .userid(this.getUserid())
                .biko(this.getBiko())
                .build();
    }
}
