import request from '@/utils/request'

// 获取区域列表
export function getAreaList(parms) {
  return request({
    url: '/basics/getAreaList',
    method: 'get',
    params: parms
  })
}
// 获取年级列表
export function getGradeList(parms) {
  return request({
    url: '/basics/getGradeList',
    method: 'get',
    params: parms
  })
}
// 获取指定学校的班级列表， 新增学生使用
export function getClassList(parms) {
  return request({
    url: '/basics/getClassList',
    method: 'get',
    params: parms
  })
}
