package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.security.util.RedisUtil;
import com.f.pro.vo.RedisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(tags = "redis缓存信息接口对接控制器", hidden = true)
@RestController
@RequestMapping("/redis")
public class SysRedisController {

    @Autowired
    private RedisUtil redisUtil;

//    @ApiOperation(value = "获取所有key", notes = "获取所有key")
    @GetMapping
    public R getAllByPage(Pageable pageable) {
        List<RedisVo> redisList = redisUtil.getAll();
        int totalElements = redisList.size();
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        int fromIndex = pageable.getPageSize() * pageable.getPageNumber();
        int toIndex = pageable.getPageSize() * (pageable.getPageNumber() + 1);
        if (toIndex > totalElements) {
            toIndex = totalElements;
        }
        List<RedisVo> indexObjects = redisList.subList(fromIndex, toIndex);
        Page<RedisVo> page = new PageImpl<>(indexObjects, pageable, totalElements);
        return R.ok(page);
    }

//    @ApiOperation(value = "批量删除", notes = "批量删除")
    @DeleteMapping("/delKeys")
    public R delByKeys(@RequestBody List<String> keys) {
        return R.ok(redisUtil.removeKey(keys));
    }

}
