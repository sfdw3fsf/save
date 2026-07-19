import moment from 'moment'
import { DATE_FORMAT } from './constants'

export function createRow(list, data) {
  // Gán tmpId mới
  const lastTmpId = list.length ? list[list.length - 1].tmpId ?? 0 : 0
  data.tmpId = lastTmpId + 1
  // data.phanloai = portType

  list.push({ ...data })
}

export function updateRow(list, data, pickDataCallback, root) {
  const index = list.findIndex((item) => item.id == data.id || item.tmpId == data.tmpId)

  if (index === -1) {
    return root.toastError('Không tìm thấy item cần cập nhật.')
  }

  list.splice(index, 1, {
    ...list[index],
    ...pickDataCallback(data)
  })
}

export function formatDate(date) {
  const m = moment(date)
  return m.isValid() ? m.format(DATE_FORMAT) : moment().format(DATE_FORMAT)
}
