package com.f.pro.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.f.pro.common.exception.BaseException;
import com.f.pro.data.datascope.DataScope;
import com.f.pro.domain.SysJob;
import com.f.pro.mapper.SysJobMapper;
import com.f.pro.service.ISysDeptService;
import com.f.pro.service.ISysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements ISysJobService {
    @Autowired
    private ISysDeptService deptService;

    @Override
    public IPage<SysJob> selectJobList(int page, int pageSize, String jobName) {
//        LambdaQueryWrapper<SysJob> jobLambdaQueryWrapper = Wrappers.<SysJob>lambdaQuery();
//        if (StringUtils.isNotEmpty(jobName)) {
//            jobLambdaQueryWrapper.eq(SysJob::getJobName, jobName).eq(SysJob::getDelFlag, 0);
//        }
        SysJob sysJobDto = new SysJob();
        sysJobDto.setJobName(jobName);
        IPage<SysJob> sysJobIPage = baseMapper.selectJobPage(new Page<>(page, pageSize), sysJobDto, new DataScope());
        List<SysJob> sysJobList = sysJobIPage.getRecords();
        if (CollectionUtil.isNotEmpty(sysJobList)) {
            List<SysJob> collect = sysJobList.stream().peek(sysJob -> sysJob.setDeptName(deptService.selectDeptNameByDeptId(sysJob.getDeptId()))).sorted((SysJob o1, SysJob o2) -> (o1.getSort() - o2.getSort())).collect(Collectors.toList());
            return sysJobIPage.setRecords(collect);
        }
        return sysJobIPage;
    }

    @Override
    public List<SysJob> selectJobListByDeptId(Integer deptId) {
        return baseMapper.selectList(Wrappers.<SysJob>lambdaQuery().select(SysJob::getId, SysJob::getJobName).eq(SysJob::getDeptId, deptId).eq(SysJob::getDelFlag, "0"));
    }

    @Override
    public String selectJobNameByJobId(Integer jobId) {
        return baseMapper.selectOne(Wrappers.<SysJob>lambdaQuery().select(SysJob::getJobName).eq(SysJob::getId, jobId)).getJobName();
    }

    @Override
    public boolean removeById(Serializable id) {
        // 1.判断岗位是否被人引用
        Integer userCount = baseMapper.getUserCountByJobId(id.hashCode());
        if (userCount > 0) throw new BaseException("请先解除相关人员绑定状态！", 500);
        // 2.更改岗位的状态为删除状态
        SysJob sysJob = new SysJob();
        sysJob.setId(id.hashCode());
        sysJob.setDelFlag("1");
        return SqlHelper.retBool(baseMapper.updateById(sysJob));
    }
}
