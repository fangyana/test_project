import request from '@/utils/request'

// 获取当前机构拥有的上班课级列表 分页
export function getClassList(parms) {
    return request({
        url: '/class',
        method: 'get',
        params: parms
    })
}

// 新增上课班级
export function addClass(data) {
    return request({
        url: '/class',
        method: 'post',
        data: data
    })
}

// 编辑上课班级
export function editClass(data) {
    return request({
        url: '/class',
        method: 'put',
        data: data
    })
}

// 获取当前机构拥有的上班课级列表, ！！数据统计使用
export function getList() {
    return request({
        url: '/class/getList',
        method: 'get',
    })
}