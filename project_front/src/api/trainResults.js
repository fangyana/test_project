import request from '@/utils/request'

// 体育课模式--获取数据接口
export function getREModelTrainData(data) {
  return request({
    url: '/trainResults/getREModelTrainData',
    method: 'post',
    data: data
  })
}

// 自由练习模式-获取数据接口
export function getFreeModelTrainData(data) {
  return request({
    url: '/trainResults/getFreeModelTrainData',
    method: 'post',
    data: data
  })
}

// 精准度模式--数据分页查询
export function getAccuracyModelPage(data) {
  return request({
    url: '/trainResults/getAccuracyModelPage',
    method: 'post',
    data: data
  })
}
// 精准度模式--获取个人详情数据接口
export function getAccuracyModelDetail(data) {
  return request({
    url: '/trainResults/getAccuracyModelDetail',
    method: 'post',
    data: data
  })
}
// 绕桩模式--获取数据分页接口
export function getAPModelPage(data) {
  return request({
    url: '/trainResults/getAPModelPage',
    method: 'post',
    data: data
  })
}
// 绕桩模式--获取个人详情数据
export function getAPModelPersonalDetailInfo(data) {
  return request({
    url: '/trainResults/getAPModelPersonalDetailInfo',
    method: 'post',
    data: data
  })
}
// 娱乐模式--获取分页数据接口
export function getENTModelPage(data) {
  return request({
    url: '/trainResults/getENTModelPage',
    method: 'post',
    data: data
  })
}
// 娱乐模式--详情数据接口
export function getENTModelDetail(configId) {
  return request({
    url: '/trainResults/getENTModelDetail/' + configId,
    method: 'get'
  })
}
