package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.f.pro.data.datascope.DataScope;
import com.f.pro.domain.SysJob;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SysJobMapper extends BaseMapper<SysJob> {

    IPage<SysJob> selectJobPage(IPage<SysJob> page, @Param("query") SysJob sysJob, DataScope dataScope);

    /**
     * 查询岗位上的人员有几位
     *
     * @param jobId
     * @return
     */
    @Select("SELECT count(1) from sys_user where del_flag=0 and job_id = #{jobId}")
    Integer getUserCountByJobId(@Param("jobId") Integer jobId);
}
