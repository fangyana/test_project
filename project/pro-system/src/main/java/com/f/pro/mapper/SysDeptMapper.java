package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.pro.data.datascope.DataScope;
import com.f.pro.domain.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description:
 */
@Repository
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 获取所有部门列表
     *
     * @param dataScope
     * @return
     */
    List<SysDept> selectSysDeptList(@Param("deptIdList") List<Integer> deptIdList, DataScope dataScope);

    Integer getDeptId(@Param("deptNo") String deptNo);

    String getDeptNo(@Param("deptId") Integer deptId);

    /**
     * 获取机构类型部门树（不包含学校类型）
     *
     * @param dataScope
     * @return
     */
    List<SysDept> selectDeptOrgList(@Param("deptIdList") List<Integer> deptIdList, DataScope dataScope);
}
