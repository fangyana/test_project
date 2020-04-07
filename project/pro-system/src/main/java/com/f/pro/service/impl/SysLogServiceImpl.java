package com.f.pro.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.data.datascope.DataScope;
import com.f.pro.domain.SysLog;
import com.f.pro.mapper.SysLogMapper;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysDeptService;
import com.f.pro.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Autowired
    private ISysDeptService deptService;

    @Override
    public IPage<SysLog> selectLogList(Integer page, Integer pageSize, Integer type, String userName) {
        SysLog sysLog = new SysLog();
        sysLog.setType(type);
        sysLog.setUserName(userName);
        sysLog.setDeptId(SecurityUtil.getUser().getDeptId());
        sysLog.setDeptList(deptService.selectDeptIds(sysLog.getDeptId()));
        return baseMapper.selectLogList(new Page<>(page, pageSize), sysLog, new DataScope());
    }

    @Override
    public boolean removeById(Serializable id) {
        SysLog sysLog = new SysLog();
        sysLog.setId(id.hashCode());
        sysLog.setDelFlag("1");
        return retBool(baseMapper.updateById(sysLog));
    }
}
