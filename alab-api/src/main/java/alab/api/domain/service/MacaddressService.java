package alab.api.domain.service;

import alab.api.domain.object.Macaddress;
import alab.api.domain.repository.MacaddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MacaddressService {

    private final MacaddressRepository macaddressRepository;

    /**
     * MACアドレス情報検索
     * @return MACアドレス情報全件
     */
    public List<Macaddress> findAll() {
        return this.macaddressRepository.findAll();
    }

    /**
     * MACアドレス情報検索
     * @param userid 検索したいユーザーID
     * @return ユーザーIDに紐づくMACアドレス情報
     */
    public List<Macaddress> findByUserid(String userid) {
        return this.macaddressRepository.findByUserid(userid);
    }

    /**
     * MACアドレス情報検索
     * @param macaddress 検索したいMACアドレス
     * @return MACアドレス情報
     */
    public Macaddress findByMacaddress(String macaddress) {
        return this.macaddressRepository.findByMacaddress(macaddress);
    }

    /**
     * MACアドレス情報作成、更新
     * @param macaddress 作成、更新対象のMACアドレス情報
     * @return 作成、更新後のMACアドレス情報
     */
    public Macaddress save(Macaddress macaddress) {
        return this.macaddressRepository.save(macaddress);
    }

    /**
     * MACアドレス情報削除
     * @param userid 削除したいユーザーID
     */
    public void deleteByUserid(String userid) {
        this.macaddressRepository.deleteByUserid(userid);
    }

    /**
     * MACアドレス情報削除
     * @param macaddress 削除したいMACアドレス
     */
    public void deleteByMacaddress(String macaddress) {
        this.macaddressRepository.deleteByMacaddress(macaddress);
    }
}
