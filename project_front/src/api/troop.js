import request from '@/utils/request'

// 获取用户列表
export function getTroopList(prams) {
  return request({
    url: '/troop',
    method: 'get',
    params: parms
  })
}
