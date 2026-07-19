<template src="./index.template.html"></template>
<script>
export default {
  name: 'ThongTinChiTiet_ChungLoaiTuDia',
  components: {
    ThongTinChung: () => import('./Components/ThongTinChung/index.vue'),
    ThongTinPort: () => import('./Components/ThongTinPort/index.vue'),
    ThongTinKheCam: () => import('./Components/ThongTinKheCam/index.vue'),
    ThongTinNguon: () => import('./Components/ThongTinNguon/index.vue')
  },
  props: {
    visible: { type: Boolean, default: false },
    currentMode: { type: String, required: true },
    form: { type: Object, required: true },
    controls: { type: Object, required: true },
    loaiTuDiaOptions: { type: Array, required: true },
    hangSanXuatList: { type: Array, required: true },
    vaiTroThietBiList: { type: Array, required: true },
    fullDepthOptions: { type: Array, required: true },
    moduleList: { type: Array, required: true },
    mucDichSuDungList: { type: Array, required: true },
    chuanDauNoiList: { type: Array, required: true },
    loaiCongOptions: { type: Array, required: true },
    tabIndex: { type: Number, default: 1 }
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    onChangeTab(idx) {
      this.$emit('change-tab', idx)
    },
    onSave() {
      this.$emit('save')
    },
    closeModal() {
      this.$emit('close')
    }
  }
}
</script>

