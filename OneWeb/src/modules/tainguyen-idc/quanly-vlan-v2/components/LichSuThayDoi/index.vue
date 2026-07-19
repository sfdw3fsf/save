<template src="./index.html"></template>
<script>
import vlan from '../../api/vlan'
export default {
  name: 'LichSuThayDoi',
  props: {
    vlanId: {
      type: Number
    }
  },
  data() {
      return {
        deviceRelationHistory: []
    }
  },
  computed: {
    historyCols() {
      return [
        { fieldName: 'STT', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'HANHDONG', headerText: 'Tên tác động', allowFiltering: true },
        { fieldName: 'TENCOT', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'GIATRI_CU', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'GIATRI_MOI', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_CN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'NGAY_CN', headerText: 'Thời điểm', allowFiltering: true }
      ]
    },
  },
  async mounted() {
    await this.getLichSuThayDoi();
  },
  methods: {
    async getLichSuThayDoi() {
      this.deviceRelationHistory = await vlan.getLichSuThayDoi(this.$root.context, this.vlanId);
    }
  }
}
</script>
