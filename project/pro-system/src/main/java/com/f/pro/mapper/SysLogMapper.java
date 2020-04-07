package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.pro.data.datascope.DataScope;
import com.f.pro.domain.SysLog;
import org.apache.ibatis.annotations.Param;

public interface SysLogMapper extends BaseMapper<SysLog> {
    IPage<SysLog> selectLogList(Page page, @Param("query") SysLog SysLog, DataScope dataScope);
}
