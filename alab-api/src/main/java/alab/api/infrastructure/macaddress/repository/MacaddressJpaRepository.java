package alab.api.infrastructure.macaddress.repository;

import alab.api.domain.object.Macaddress;
import alab.api.infrastructure.macaddress.entity.MacaddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * JPA利用インターフェース
 */
public interface MacaddressJpaRepository extends JpaRepository<MacaddressEntity, String> {

    List<MacaddressEntity> findByUserid(String userid);

    void deleteByUserid(String userid);

}
