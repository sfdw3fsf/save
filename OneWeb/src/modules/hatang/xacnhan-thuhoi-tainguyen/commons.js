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
