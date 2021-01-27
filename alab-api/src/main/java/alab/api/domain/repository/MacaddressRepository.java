package alab.api.domain.repository;

import alab.api.domain.object.Macaddress;

import java.util.List;

public interface MacaddressRepository {

    /**
     * MACアドレス情報検索
     * @return MACアドレス情報全件
     */
    List<Macaddress> findAll();

    /**
     * MACアドレス情報検索
     * @param userid 検索したいユーザーID
     * @return ユーザーIDに紐づくMACアドレス情報
     */
    List<Macaddress> findByUserid(String userid);

    /**
     * MACアドレス情報検索
     * @param macaddress 検索したいMACアドレス
     * @return MACアドレス情報
     */
    Macaddress findByMacaddress(String macaddress);

    /**
     * MACアドレス情報作成、更新
     * @param macaddress 作成、更新対象のMACアドレス情報
     * @return 作成、更新後のMACアドレス情報
     */
    Macaddress save(Macaddress macaddress);

    /**
     * MACアドレス情報削除
     * @param userid 削除したいユーザーID
     */
    void deleteByUserid(String userid);

    /**
     * MACアドレス情報削除
     * @param macaddress 削除したいMACアドレス
     */
    void deleteByMacaddress(String macaddress);
}
