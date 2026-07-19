<template src="./index.template.html"></template>
<script>
export default {
  name: 'ModalThemMoiTuyenDuong',
  props: {
    //
  },
  data: function () {
    return {
      locationRightClickMap: null,
      dsLoaiTuyenDuong: [],
      dataSelected: {
        tenTuyenDuong: '',
        loaiTuyenDuong: null,
        toaDoTuyenDuong: []
      }
    }
  },
  mounted: async function () {},
  methods: {
    focusMyElement: async function () {
      this.dsLoaiTuyenDuong = [
        { id: 1, key: 'LINE_HIGHWAY', name: 'Đường cao tốc, đường quốc lộ' },
        { id: 2, key: 'LINE_PROVINCE', name: 'Đường tỉnh' },
        { id: 3, key: 'LINE_DISTRICT', name: 'Đường huyện' },
        { id: 4, key: 'LINE_WARD', name: 'Đường xã' },
        { id: 5, key: 'LINE_MAIN', name: 'Đường trục chính' },
        { id: 6, key: 'LINE_CAR', name: 'Đường ô tô' },
        { id: 7, key: 'LINE_OTHER', name: 'Loại đường khác' }
      ]
    },
    show: async function (locationRightClickMap) {
      this.locationRightClickMap = locationRightClickMap
      this.$refs.modalThemTuyenDuong.show()
    },
    close: async function () {
      this.$refs.modalThemTuyenDuong.hide()
    },
  }
}
</script>

