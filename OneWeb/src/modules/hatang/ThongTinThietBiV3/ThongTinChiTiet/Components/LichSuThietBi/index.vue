<template src="./index.template.html"></template>

<script>
import thietBiApi from '../../../api/ThietBi.api.js'
export default {
  name: 'LichSuThietBi',
  props: {
    deviceRelationHistory: {
      type: Array,
      default: () => []
    },
    deviceTypeInfo: {
      type: Object,
      required: true
    },
    deviceId: {
      type: Number
    }
  },
  watch: {
    deviceId: {
      immediate: true,
      handler(newVal) {
        if (newVal) {
          this.loadCongSuat();
        }
      }
    }
  },
  data() {
    return {
      deviceCongSuat: [],
      isCongSuat: false
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
    CongSuatCols() {
      return [
        { fieldName: 'STT', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'GIATRI', headerText: 'Công suất thực tế', allowFiltering: true },
        { fieldName: 'NGUOI_CN', headerText: 'Người đo', allowFiltering: true },
        { fieldName: 'NGAY_CN', headerText: 'Thời điểm đo', allowFiltering: true }
      ]
    }
  },
  methods: {
    async loadCongSuat() {
      this.isCongSuat = false;
      if(this.deviceTypeInfo.isServer || this.deviceTypeInfo.isServerBlade || this.deviceTypeInfo.isTuDia || this.deviceTypeInfo.isNetwork)
      {
        this.isCongSuat = true;
        this.deviceCongSuat = (await this.getDeviceCongSuat()) || [];
      }
    },
    async getDeviceCongSuat() {
      return await thietBiApi.getLichSuCongSuat(this.$root.context, this.deviceId);
    }
  }
}
</script>
