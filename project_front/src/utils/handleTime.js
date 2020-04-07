// import resultRule from '@/static/sysItem/resultRule'
export function handleDate(date) {
  let dateTime = new Date(date);
  dateTime = dateTime.getFullYear() + "-" + (dateTime.getMonth() + 1) + "-" + dateTime.getDate();
  return dateTime
}
export function handleTime(time) {
  if (time <= 10) {
    time = '暂无'
  } else if (time.length >= 10) {
    time = time.substring(0, 10)
  }
  return time
}
export function formatter(params) {
  const itemData = ['1000米', '10米x4', '3000米', '5000米']
  let newParamsName = ''
  var paramsNameNumber = params.length
  var provideNumber = 3
  var rowNumber = Math.ceil(paramsNameNumber / provideNumber)
  if (paramsNameNumber > provideNumber) {
    for (var p = 0; p < rowNumber; p++) {
      var tempStr = ''
      var start = p * provideNumber
      var end = start + provideNumber
      if (p == rowNumber - 1) {
        tempStr = params.substring(start, paramsNameNumber)
      } else {
        tempStr = params.substring(start, end) + '\n'
      }
      newParamsName += tempStr
    }
  } else {
    newParamsName = params
  }
  if (params == '3000米') {
    newParamsName = params
  }
  if (params == '10米x4') {
    newParamsName = params
  }
  if (params == '1000米') {
    newParamsName = params
  }
  if (params == '5000米') {
    newParamsName = params
  }
  return newParamsName
}
// export function formatSeconds(value, obj, bool) {
//   const timeObj = {
//     secondTime: 0,
//     minuteTime: 0,
//     hourTime: 0
//   }
//   let secondTime = parseInt(value)// 秒
//   let minuteTime = 0// 分
//   let hourTime = 0// 小时
//   resultRule.map(item => {
//     if (item.sysItemName === obj.itemName) {
//       if (bool === true) {
//         if (secondTime > 60) { // 如果秒数大于60，将秒数转换成整数
//           // 获取分钟，除以60取整数，得到整数分钟
//           minuteTime = parseInt(secondTime / 60)
//           // 获取秒数，秒数取佘，得到整数秒数
//           secondTime = parseInt(secondTime % 60)
//           // 如果分钟大于60，将分钟转换成小时
//           if (minuteTime > 60) {
//             // 获取小时，获取分钟除以60，得到整数小时
//             hourTime = parseInt(minuteTime / 60)
//             // 获取小时后取佘的分，获取分钟除以60取佘的分
//             minuteTime = parseInt(minuteTime % 60)
//           }
//         }
//         var result = '' + parseInt(secondTime) + '秒'

//         if (minuteTime > 0) {
//           result = '' + parseInt(minuteTime) + '分' + result
//         }
//         if (hourTime > 0) {
//           result = '' + parseInt(hourTime) + '小时' + result
//         }
//       } else {
//         if (secondTime > 60) { // 如果秒数大于60，将秒数转换成整数
//           // 获取分钟，除以60取整数，得到整数分钟
//           minuteTime = parseInt(secondTime / 60)
//           // 获取秒数，秒数取佘，得到整数秒数
//           secondTime = parseInt(secondTime % 60)
//           // 如果分钟大于60，将分钟转换成小时
//           if (minuteTime > 60) {
//             // 获取小时，获取分钟除以60，得到整数小时
//             hourTime = parseInt(minuteTime / 60)
//             // 获取小时后取佘的分，获取分钟除以60取佘的分
//             minuteTime = parseInt(minuteTime % 60)
//           }
//         }
//         timeObj.secondTime = parseInt(secondTime)
//         if (minuteTime > 0) {
//           timeObj.minuteTime = parseInt(minuteTime)
//         }
//         if (hourTime > 0) {
//           timeObj.hourTime = parseInt(hourTime)
//         }
//       }
//     }
//   })
//   return timeObj
// }
