export function transformCellText ({ text, column }) {
  if (text) {
    if (typeof (text) === 'string') {
      if (text === '') {
        return '无'
      } else {
        return text
      }
    } else {
      return text
    }
  } else {
    // 解决自定义展开表格出现'无'的问题
    if (column.customRender) {
      return text
    } else {
      return '无'
    }
  }
}
