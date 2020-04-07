package com.f.pro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.domain.SysDict;
import com.f.pro.dto.dict.EditDictDTO;
import com.f.pro.mapper.SysDictMapper;
import com.f.pro.service.ISysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public IPage<SysDict> selectDictList(int page, int pageSize) {
        Page<SysDict> dictPage = new Page<>(page, pageSize);
        IPage<SysDict> sysDictIPage = baseMapper.selectPage(dictPage, Wrappers.<SysDict>lambdaQuery().select(SysDict::getId, SysDict::getName, SysDict::getDescription, SysDict::getRemark, SysDict::getCreateTime)
                .eq(SysDict::getDelFlag, 0));
        sysDictIPage.setRecords(sysDictIPage.getRecords().stream().filter(sysDict -> StringUtils.isNotEmpty(sysDict.getDescription())).collect(Collectors.toList()));
        return sysDictIPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDict(EditDictDTO dto) {
        if (ObjectUtil.isNull(dto.getValue())) {
            // 先查询所有的含有的主键 然后批量修改
            List<SysDict> sysDictList = baseMapper.selectList(Wrappers.<SysDict>lambdaQuery().select(SysDict::getId)
                    .eq(SysDict::getName, baseMapper.selectById(dto.getId()).getName()).eq(SysDict::getDelFlag, 0));
            List<SysDict> collect = sysDictList.stream().map(sysDict1 -> {
                SysDict sysDict = new SysDict();
                sysDict.setId(sysDict1.getId());
                sysDict.setName(dto.getName());
                return sysDict;
            }).collect(Collectors.toList());
            return updateBatchById(collect);
        }
        SysDict sysDict = new SysDict();
        BeanUtil.copyProperties(dto, sysDict);
        return baseMapper.updateById(sysDict) > 0;
    }

    @Override
    public List<SysDict> selectDictDetailList(String name) {
        return baseMapper.selectList(Wrappers.<SysDict>lambdaQuery().select(SysDict::getId, SysDict::getName, SysDict::getValue, SysDict::getLabel, SysDict::getSort)
                .eq(SysDict::getName, name).eq(SysDict::getDelFlag, 0))
                .stream().filter(sysDict -> StringUtils.isNotEmpty(sysDict.getValue())).collect(Collectors.toList());

    }

    @Override
    public boolean deleteDictByName(String name) {
        return baseMapper.delete(Wrappers.<SysDict>lambdaQuery().isNull(SysDict::getValue).eq(SysDict::getName, name)) > 0;
    }

    @Override
    public boolean removeById(Serializable id) {
        SysDict entity = new SysDict();
        entity.setId(id.hashCode());
        entity.setDelFlag(1);
        return super.saveOrUpdate(entity);
//        return super.removeById(id);
    }
}
