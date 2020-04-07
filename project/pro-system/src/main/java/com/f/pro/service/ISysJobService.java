package com.f.pro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysJob;

import java.io.Serializable;
import java.util.List;

public interface ISysJobService extends IService<SysJob> {
    /**
     * 分页查询岗位列表
     *
     * @param page
     * @param pageSize
     * @param jobName
     * @return
     */
    IPage<SysJob> selectJobList(int page, int pageSize, String jobName);


    /**
     * 根据部门id查询所属下的岗位信息
     *
     * @param deptId
     * @return
     */
    List<SysJob> selectJobListByDeptId(Integer deptId);


    String selectJobNameByJobId(Integer jobId);

    @Override
    boolean removeById(Serializable id);
}
