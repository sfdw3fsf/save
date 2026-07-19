<template src="./index.template.html"></template>

<script>
export default {
  name: 'LichSuThietBi',
  props: {
    deviceRelationHistory: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      currentTab: 1
    }
  },
  computed: {
    historyCols() {
      return [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'TACDONG', headerText: 'Tên tác động', allowFiltering: true },
        { fieldName: 'FIELD', headerText: 'Trường thông tin thay đổi', allowFiltering: true },
        { fieldName: 'OLD_VALUE', headerText: 'Giá trị cũ', allowFiltering: true },
        { fieldName: 'NEW_VALUE', headerText: 'Giá trị mới', allowFiltering: true },
        { fieldName: 'NGUOI_CN', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'NGAY_CN', headerText: 'Thời điểm', allowFiltering: true }
      ]
    }
  },
  methods: {
    refreshHistory() {
      if (this.$refs.grdHistory) {
        this.$refs.grdHistory.refresh()
      }
    }
  }
}
</script>
