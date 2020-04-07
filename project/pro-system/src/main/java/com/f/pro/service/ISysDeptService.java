package com.f.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysDept;
import com.f.pro.dto.dept.AddDeptDTO;
import com.f.pro.dto.dept.EditDeptDTO;
import com.f.pro.vo.dept.DeptTreeVo;

import java.io.Serializable;
import java.util.List;

public interface ISysDeptService extends IService<SysDept> {

    /**
     * 查询部门信息
     *
     * @return
     */
    List<SysDept> selectDeptList();

    boolean insert(AddDeptDTO dto);

    /**
     * 更新部门
     *
     * @param dto
     * @return
     */
    boolean updateDeptById(EditDeptDTO dto);


    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据部门id查询部门名称
     *
     * @param deptId
     * @return
     */
    String selectDeptNameByDeptId(int deptId);

    /**
     * 根据部门名称查询
     *
     * @param deptName
     * @return
     */
    List<SysDept> selectDeptListByDeptName(String deptName);

    /**
     * 通过此部门id查询于此相关的部门ids
     *
     * @param deptId
     * @return
     */
    List<Integer> selectDeptIds(int deptId);

    /**
     * 获取部门树
     *
     * @return
     */
    List<DeptTreeVo> getDeptTree();

    /**
     * 根据机构编号获取系统得机构编号
     *
     * @param deptNo
     * @return
     */
    Integer getDeptId(String deptNo);

    /**
     * 根据系统编号获取得机构代码编号
     *
     * @param deptId
     * @return
     */
    String getDeptNo(Integer deptId);

    /**
     * 获取机构类型部门树（不包含学校类型）
     *
     * @return
     */
    List<DeptTreeVo> getDeptOrgTree();
}
