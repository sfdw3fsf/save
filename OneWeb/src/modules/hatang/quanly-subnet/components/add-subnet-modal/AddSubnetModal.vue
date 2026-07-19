<template src="./add-subnet-modal.html"></template>
<style src="../subnet-modal/subnet-modal.scss"></style>
<script>
import IpStore from '../ip-store/IpStore.vue'
import SubnetCon from '../subnet-con/SubnetCon.vue'
import ThongTinChung from '../thongtin-chung/ThongTinChung.vue'

export default {
  name: 'AddSubnetModal',

  components: {
    SubnetCon,
    IpStore,
    ThongTinChung
  },

  props: {
    item: { type: Object, default: () => ({}) },
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
      activeTab: 1,
      mode: 'Add'
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
    },

    changeTab(index) {
      this.activeTab = index
    }
  },

  computed: {}
}
</script>
