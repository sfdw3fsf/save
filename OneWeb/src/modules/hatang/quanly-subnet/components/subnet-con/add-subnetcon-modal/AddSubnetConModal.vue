<template src="./add-subnetcon-modal.html"> </template>

<script>
export default {
  name: 'AddSubnetConModal',

  components: {},

  props: {
    item: { type: Object, default: () => ({}) },
    mode: { type: String, default: 'VIEW' },
    visible: { type: Boolean, default: false },

    loaiIPList: {
      type: Array,
      default: () => []
    },
    phanLoaiIPList: {
      type: Array,
      default: () => []
    },
    IDCList: {
      type: Array,
      default: () => []
    },
    VRFList: {
      type: Array,
      default: () => []
    },
    ZoneList: {
      type: Array,
      default: () => []
    },
    VlanList: {
      type: Array,
      default: () => []
    },
    SubnetList: {
      type: Array,
      default: () => []
    },
    QuyHoachSubnetList: {
      type: Array,
      default: () => []
    },
    lichSuCapPhatList: {
      type: Array,
      default: () => []
    },
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
        tenSubnet: '',
        loaiIp: 'ipv4',
        phanLoaiIp: 'public',
        subnetMask: '',
        idc: '',
        vrf: '',
        zone: '',
        vlan: '',
        thietBiVatLy: '',
        noiBo: '',
        mucDich: '',
        thamChieu: '',

        hieuLuc: false,
        quangBa: false,
        tuDong: false,
        ccdv: '',
        subnet: '/24',
        gateway: '/24',
        htm: '',
        subnetCha: '',
        donVi: '',
        ghiChu: ''
      }
    }
  },

  methods: {
    close() {
      this.$emit('close')
    },
    save() {
      const valid = this.$refs.thongtin.validateThongTinChung()
      if (!valid) return

      const formData = this.$refs.thongtin.form // lấy form của ThongTinChung

      this.$emit('save', formData)
    }
  },

  computed: {}
}
</script>
