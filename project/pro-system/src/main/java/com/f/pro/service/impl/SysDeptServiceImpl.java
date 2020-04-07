package com.f.pro.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.common.exception.BaseException;
import com.f.pro.data.datascope.DataScope;
import com.f.pro.domain.SysDept;
import com.f.pro.dto.dept.AddDeptDTO;
import com.f.pro.dto.dept.EditDeptDTO;
import com.f.pro.mapper.SysDeptMapper;
import com.f.pro.security.util.ProUtil;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysDeptService;
import com.f.pro.service.ISysUserService;
import com.f.pro.vo.dept.DeptTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;


    @Override
    public List<SysDept> selectDeptList() {
//        List<SysDept> deptList = baseMapper.selectList(Wrappers.<SysDept>lambdaQuery().select(SysDept::getDeptId, SysDept::getName, SysDept::getParentId, SysDept::getSort, St::getCreateTime));
        Integer deptId = SecurityUtil.getUser().getDeptId();
        List<Integer> deptIdList = this.selectDeptIds(deptId);// 查询所有子级部门集合
        List<SysDept> deptList = baseMapper.selectSysDeptList(deptIdList, new DataScope());
        if (deptList != null && deptList.size() == 1) return deptList;
        List<SysDept> sysDeptList = deptList.stream()
                .filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtil.isNull(sysDept.getParentId()))
                .peek(sysDept -> sysDept.setLevel(0))
                .collect(Collectors.toList());
        if (sysDeptList.size() == 0) {
            sysDeptList = deptList.stream().filter(sysDept -> sysDept.getDeptId() == deptId).peek(sysDept -> sysDept.setLevel(0)).collect(Collectors.toList());
        }
        ProUtil.findChildren(sysDeptList, deptList);
        return sysDeptList;
    }

    @Transactional
    public boolean insert(AddDeptDTO dto) {
        SysDept entity = new SysDept();
        BeanUtils.copyProperties(dto, entity);
        entity.setCreateBy(SecurityUtil.getUser().getUserId());
        Boolean result = deptService.save(entity);
        if (org.springframework.util.ObjectUtils.isEmpty(entity.getDeptNo())) {
            SysDept dept = new SysDept();
            dept.setDeptId(entity.getDeptId());
            dept.setDeptNo(entity.getDeptId().toString());
            deptService.saveOrUpdate(dept);
        }
        return result;
    }


    @Override
    public boolean updateDeptById(EditDeptDTO dto) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dto, sysDept);
        return this.updateById(sysDept);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        // 部门层级删除
        List<Integer> idList = this.list(Wrappers.<SysDept>query().lambda().eq(SysDept::getParentId, id)).stream().map(SysDept::getDeptId).collect(Collectors.toList());
        // 删除自己
        idList.add((Integer) id);
        // 根据idList查询是否部门存在相关人员信息；存在则返回提示先清空人员
        Integer irs = userService.getIrsByDeptIds(idList);
        if (irs > 0) throw new BaseException("当前及下级机构存在人员信息，请先解除人员绑定状态！", 500);
        // 修改部门的删除标记
        List<SysDept> sysDeptList = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            SysDept sysDept = new SysDept();
            sysDept.setDeptId(idList.get(i));
            sysDept.setDelFlag("1");
            sysDeptList.add(sysDept);
        }
        return super.updateBatchById(sysDeptList);
//        return super.removeByIds(idList);
    }

    @Override
    public String selectDeptNameByDeptId(int deptId) {
        return baseMapper.selectOne(Wrappers.<SysDept>query().lambda().select(SysDept::getName).eq(SysDept::getDeptId, deptId)).getName();
    }

    @Override
    public List<SysDept> selectDeptListByDeptName(String deptName) {
        return null;
    }

    @Override
    public List<Integer> selectDeptIds(int deptId) {
        SysDept department = this.getDepartment(deptId);
        List<Integer> deptIdList = new ArrayList<>();
        if (department != null) {
            deptIdList.add(department.getDeptId());
            addDeptIdList(deptIdList, department);
        }
        return deptIdList;
    }

    @Override
    public List<DeptTreeVo> getDeptTree() {
//        List<SysDept> deptList = baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        Integer deptId = SecurityUtil.getUser().getDeptId();
        List<Integer> deptIdList = this.selectDeptIds(deptId);// 查询所有子级部门集合
        List<SysDept> deptList = baseMapper.selectSysDeptList(deptIdList, new DataScope());
        if (deptList != null && deptList.size() == 1)
            return deptList.stream().map(sysDept -> {
                DeptTreeVo deptTreeVo = new DeptTreeVo();
                deptTreeVo.setId(sysDept.getDeptId());
                deptTreeVo.setLabel(sysDept.getName());
                return deptTreeVo;
            }).collect(Collectors.toList());
        List<DeptTreeVo> collect = buildFirstTree(deptList, deptId);
        ProUtil.findChildren1(collect, deptList);
        return collect;
    }

    @Override
    public Integer getDeptId(String deptNo) {
        return baseMapper.getDeptId(deptNo);
    }

    @Override
    public String getDeptNo(Integer deptId) {
        return baseMapper.getDeptNo(deptId);
    }

    /**
     * 根据部门ID获取该部门及其下属部门树
     */
    private SysDept getDepartment(Integer deptId) {
        List<SysDept> departments = baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        Map<Integer, SysDept> map = departments.stream().collect(
                Collectors.toMap(SysDept::getDeptId, department -> department));

        for (SysDept dept : map.values()) {
            SysDept parent = map.get(dept.getParentId());
            if (parent != null) {
                List<SysDept> children = parent.getChildren() == null ? new ArrayList<>() : parent.getChildren();
                children.add(dept);
                parent.setChildren(children);
            }
        }
        return map.get(deptId);
    }

    private void addDeptIdList(List<Integer> deptIdList, SysDept department) {
        List<SysDept> children = department.getChildren();
        if (children != null) {
            for (SysDept d : children) {
                deptIdList.add(d.getDeptId());
                addDeptIdList(deptIdList, d);
            }
        }
    }


    @Override
    public List<DeptTreeVo> getDeptOrgTree() {
        //        List<SysDept> deptList = baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        Integer deptId = SecurityUtil.getUser().getDeptId();
        List<Integer> deptIdList = this.selectDeptIds(deptId);// 查询所有子级部门集合
        List<SysDept> deptList = baseMapper.selectDeptOrgList(deptIdList, new DataScope());
        if (deptList != null && deptList.size() == 1)
            return deptList.stream().map(sysDept -> {
                DeptTreeVo deptTreeVo = new DeptTreeVo();
                deptTreeVo.setId(sysDept.getDeptId());
                deptTreeVo.setLabel(sysDept.getName());
                return deptTreeVo;
            }).collect(Collectors.toList());
        List<DeptTreeVo> collect = buildFirstTree(deptList, deptId);
        ProUtil.findChildren1(collect, deptList);
        return collect;
    }

    /**
     * 获取一级组织机构信息
     *
     * @param deptList
     * @param deptId
     * @return
     */
    private List<DeptTreeVo> buildFirstTree(List<SysDept> deptList, Integer deptId) {
        List<DeptTreeVo> collect = deptList.stream().filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtil.isNull(sysDept.getParentId()))
                .map(sysDept -> {
                    DeptTreeVo deptTreeVo = new DeptTreeVo();
                    deptTreeVo.setId(sysDept.getDeptId());
                    deptTreeVo.setLabel(sysDept.getName());
                    return deptTreeVo;
                }).collect(Collectors.toList());
        if (collect != null && collect.size() == 0) {
            collect = deptList.stream().filter(sysDept -> sysDept.getDeptId() == deptId)
                    .map(sysDept -> {
                        DeptTreeVo deptTreeVo = new DeptTreeVo();
                        deptTreeVo.setId(sysDept.getDeptId());
                        deptTreeVo.setLabel(sysDept.getName());
                        return deptTreeVo;
                    }).collect(Collectors.toList());
        }
        return collect;
    }
}
