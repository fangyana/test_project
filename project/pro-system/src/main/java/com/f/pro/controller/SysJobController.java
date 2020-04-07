package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.domain.SysJob;
import com.f.pro.dto.job.AddJobDTO;
import com.f.pro.dto.job.EditJobDTO;
import com.f.pro.log.annotation.SysLog;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "岗位信息接口对接控制器")
@RestController
@RequestMapping("/job")
public class SysJobController {

    @Autowired
    private ISysJobService jobService;

    @ApiOperation(value = "保存岗位", notes = "保存岗位")
    @SysLog(descrption = "保存岗位")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:job:add')")
    public R save(@RequestBody AddJobDTO dto) {
        SysJob sysJob = new SysJob();
        BeanUtils.copyProperties(dto, sysJob);
        sysJob.setCreateBy(SecurityUtil.getUser().getUserId());
        return R.ok(jobService.save(sysJob));
    }

    @ApiOperation(value = "根据id逻辑删除岗位", notes = "根据id逻辑删除岗位")
    @ApiImplicitParam(paramType = "path", name = "id", value = "岗位id", required = true, dataType = "Integer")
    @SysLog(descrption = "根据id删除岗位")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:job:delete')")
    public R delete(@PathVariable("id") Integer id) {
        return R.ok(jobService.removeById(id));
    }

    @ApiOperation(value = "更新岗位", notes = "更新岗位")
    @SysLog(descrption = "更新岗位")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:job:update')")
    public R update(@RequestBody EditJobDTO dto) {
        SysJob sysJob = new SysJob();
        BeanUtils.copyProperties(dto, sysJob);
        return R.ok(jobService.updateById(sysJob));
    }

    @ApiOperation(value = "获取岗位分页列表", notes = "获取岗位分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "jobName", value = "岗位名称", required = true, dataType = "String")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('sys:job:view')")
    public R getList(Integer page, Integer pageSize, @RequestParam(defaultValue = "") String jobName) {
        return R.ok(jobService.selectJobList(page, pageSize, jobName));
    }

    @ApiOperation(value = "根据部门id获取岗位列表", notes = "根据部门id获取岗位列表")
    @ApiImplicitParam(paramType = "path", name = "id", value = "部门id", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public R selectJobListByDeptId(@PathVariable("id") Integer deptId) {
        return R.ok(jobService.selectJobListByDeptId(deptId));
    }

}
