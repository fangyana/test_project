package com.f.pro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysLog;

import java.io.Serializable;

public interface ISysLogService extends IService<SysLog> {

    /**
     * 分页查询日志
     *
     * @param page
     * @param pageSize
     * @param type
     * @return
     */
    IPage<SysLog> selectLogList(Integer page, Integer pageSize, Integer type, String userName);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    @Override
    boolean removeById(Serializable id);
}
