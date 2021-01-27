package alab.api.infrastructure.macaddress.repository;

import alab.api.domain.exception.NotFoundException;
import alab.api.domain.object.Macaddress;
import alab.api.domain.repository.MacaddressRepository;
import alab.api.infrastructure.macaddress.entity.MacaddressEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 永続化実装クラス
 * ドメインオブジェクトをEntityに変換してJPAをラップする
 */
@Repository
@RequiredArgsConstructor
public class MacaddressRepositoryImpl implements MacaddressRepository {

    private final MacaddressJpaRepository macaddressJpaRepository;

    @Override
    public List<Macaddress> findAll() {
        return this.macaddressJpaRepository.findAll()
                .stream()
                .map(macaddressEntity -> macaddressEntity.toDomainMacaddress())
                .collect(Collectors.toList());
    }

    @Override
    public List<Macaddress> findByUserid(String userid) {
        return this.macaddressJpaRepository.findByUserid(userid)
                .stream()
                .map(macaddressEntity -> macaddressEntity.toDomainMacaddress())
                .collect(Collectors.toList());
    }

    @Override
    public Macaddress findByMacaddress(String macaddress) {
        return this.macaddressJpaRepository.findById(macaddress)
                .orElseThrow(() -> new NotFoundException(String.format("%s is not found.",macaddress)))
                .toDomainMacaddress();
    }

    @Override
    public Macaddress save(Macaddress macaddress) {
        return this.macaddressJpaRepository.save(MacaddressEntity.build(macaddress))
                .toDomainMacaddress();
    }

    @Override
    public void deleteByUserid(String userid) {
        try {
            this.macaddressJpaRepository.deleteByUserid(userid);
        } catch (EmptyResultDataAccessException e) {
            // 削除しようとしたUseridが存在しない
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteByMacaddress(String macaddress) {
        try {
            this.macaddressJpaRepository.deleteById(macaddress);
        } catch (EmptyResultDataAccessException e) {
            // 削除しようとしたmacaddressが存在しない
            throw new NotFoundException(e.getMessage());
        }
    }
}
