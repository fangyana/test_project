package com.f.pro.vo.dept;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DeptTreeVo {
    private int id;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptTreeVo> children;
}
