package alab.api.app.controller;

import alab.api.app.resource.MacaddressBody;
import alab.api.domain.object.Macaddress;
import alab.api.domain.service.MacaddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/macaddress")
public class MacaddressController {

    private final MacaddressService macaddressService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /**
     * MACアドレス情報を全件取得する
     * @return MACアドレス情報
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Macaddress> findAll() {
        return this.macaddressService.findAll();
    }

    /**
     * 指定されたユーザーIDにてMACアドレス情報を検索する
     * @param userid 検索したいユーザーID
     * @return MACアドレス情報
     */
    @GetMapping("{userid}")
    @ResponseStatus(HttpStatus.OK)
    public List<Macaddress> findByUserid(@PathVariable("userid") String userid) {
        return this.macaddressService.findByUserid(userid);
    }

    /**
     * MACアドレス情報作成、更新
     * @param macaddressBody リクエストボディ
     * @return 更新後のMACアドレス情報
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Macaddress save(@RequestBody @Validated MacaddressBody macaddressBody) {
        return this.macaddressService.save(macaddressBody.toDomainMacaddress());
    }

    /**
     * MACアドレス情報削除
     * @param userid 削除したいユーザID
     */
    @DeleteMapping("{userid}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserid(@PathVariable("userid") String userid) {
        this.macaddressService.deleteByUserid(userid);
    }
}
