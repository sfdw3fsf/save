<template src="./view-edit-ipstore-modal.html"> </template>

<script>
export default {
  name: 'ViewEditIpStoreModal',

  components: {},

  props: {
    item: { type: Object, default: () => ({}) },
    mode: { type: String, default: 'VIEW' },
    visible: { type: Boolean, default: false },

    lichSuCapPhatColumns: {
      type: Array,
      default: () => []
    },
    lichSuThayDoiList: {
      type: Array,
      default: () => []
    },
    lichSuThayDoiColumns: {
      type: Array,
      default: () => []
    },
    ipInvalid: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      animationSettings: { effect: 'Zoom' },
      form: {
        id: '',
        ip: '',
        loaiIp: '',
        phanLoaiIp: '',
        idc: '',
        mucDich: '',
        trangThaiCcdv: '',
        noiBoKhachHang: 'noiBo',
        donViQuanLy: '',
        hieuLuc: false,
        ghiChu: ''
      },

      mucDichList: [],
      donViQuanLyList: [],
      idcList: [],
      loaiIPList: [],
      phanLoaiIpList: []
    }
  },

  methods: {
    close() {
      this.$emit('close')
    },
    save() {
      this.$emit('save', this.form)
    }
  },

  computed: {}
}
</script>
