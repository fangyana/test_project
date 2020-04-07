export function formatter(params) {
  const itemData = ['1000米', '1000米跑', '负重登6楼', '单杠卷身上', '10米x4', '坐位体前屈', '3000米', '5000米', '10米拖拽', '30米拖重', '10楼负重', '坐位体前屈', '单杠吊卷腿', '60米肩梯']
  const item5 = ['4*10米往返跑', '2分钟双腿深蹲起立', '5*10米折返', '坐位体前屈', '2分钟双腿深蹲起立', '5000米负重', '5*40米折返搬运重物']
  let newParamsName = ''
  const paramsNameNumber = params.length
  let provideNumber = 4
  let rowNumber = Math.ceil(paramsNameNumber / provideNumber)
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
  itemData.forEach(item => {
    if (params == item) {
      newParamsName = params
    }
  })
  item5.forEach(item => {
    if (params == item) {
      newParamsName = ''
      provideNumber = 5
      rowNumber = Math.ceil(paramsNameNumber / provideNumber)
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
    }
  })
  return newParamsName
}
