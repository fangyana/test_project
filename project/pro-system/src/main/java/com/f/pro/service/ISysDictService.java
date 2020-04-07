package com.f.pro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysDict;
import com.f.pro.dto.dict.EditDictDTO;

import java.io.Serializable;
import java.util.List;

public interface ISysDictService extends IService<SysDict> {
    /**
     * 分页查询字典列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    IPage<SysDict> selectDictList(int page, int pageSize);

    /**
     * 修改字典
     *
     * @param dto
     * @return
     */
    boolean updateDict(EditDictDTO dto);


    /**
     * @param name
     * @return
     */
    List<SysDict> selectDictDetailList(String name);

    /**
     * 根据字典名称删除
     *
     * @param name
     * @return
     */
    boolean deleteDictByName(String name);


    /**
     * 根据主键Id删除字典
     *
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);
}
