package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.domain.SysDict;
import com.f.pro.dto.dict.AddDictDTO;
import com.f.pro.dto.dict.EditDictDTO;
import com.f.pro.log.annotation.SysLog;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysDictService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "字典信息接口对接控制器")
@RestController
@RequestMapping("/dict")
public class SysDictController {

    @Autowired
    private ISysDictService dictService;

    @ApiOperation(value = "保存字典信息", notes = "保存字典信息")
    @SysLog(descrption = "保存字典信息")
    @PostMapping
    public R save(@RequestBody AddDictDTO dto) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dto, sysDict);
        sysDict.setCreateBy(SecurityUtil.getUser().getUserId());
        return R.ok(dictService.save(sysDict));
    }

    @ApiOperation(value = "获取字典分页列表集合", notes = "获取字典分页列表集合")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "条数", required = true, dataType = "Integer")
    })
    @SysLog(descrption = "查询字典集合")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:dipt:view')")
    public R getList(Integer page, Integer pageSize) {
        return R.ok(dictService.selectDictList(page, pageSize));
    }

    @ApiOperation(value = "根据名称获取字典值详情", notes = "根据名称获取字典值详情")
    @ApiImplicitParam(paramType = "query", name = "name", value = "名字", required = true, dataType = "String")
    @GetMapping("/getDictDetailList")
    public R selectDictDetailList(@RequestParam String name) {
        return R.ok(dictService.selectDictDetailList(name));
    }

    @ApiOperation(value = "更新字典", notes = "更新字典")
    @SysLog(descrption = "更新字典")
    @PutMapping
    public R update(@RequestBody EditDictDTO dto) {
        return R.ok(dictService.updateDict(dto));
    }

    @ApiOperation(value = "根据id逻辑删除字典", notes = "根据id逻辑删除字典")
    @ApiImplicitParam(paramType = "path", name = "id", value = "字典id", required = true, dataType = "int")
    @SysLog(descrption = "根据id删除字典")
    @DeleteMapping("{id}")
    public R delete(@PathVariable("id") int id) {
        return R.ok(dictService.removeById(id));
    }

    @ApiOperation(value = "根据name删除字典", notes = "根据name删除字典")
    @ApiImplicitParam(paramType = "query", name = "name", value = "名字", required = true, dataType = "String")
    @SysLog(descrption = "根据name删除字典")
    @DeleteMapping("/delete")
    public R deleteName(@RequestParam String name) {
        return R.ok(dictService.deleteDictByName(name));
    }

}
