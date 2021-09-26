package cn.silently9527.coupons.classify.rest;

import cn.silently9527.coupons.classify.api.CateApi;
import cn.silently9527.coupons.utils.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mi")
@AllArgsConstructor
public class CateResource {

    private CateApi cateApi;

    @GetMapping("/load_cate")
    public ResponseEntity loadCate() {
        return ResponseEntity.success(cateApi.loadCate());
    }

}
