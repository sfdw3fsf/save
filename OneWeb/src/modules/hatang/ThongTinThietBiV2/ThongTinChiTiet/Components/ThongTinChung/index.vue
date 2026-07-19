<template src="./index.template.html"></template>
<script>
import { validationMixin, ValidationRulesFactory } from '../../validation.js'

export default {
  name: 'ThongTinChung',
  mixins: [validationMixin],
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    deviceTypeList: {
      type: Array,
      default: () => []
    },
    customerTypeList: {
      type: Array,
      default: () => []
    },
    equipmentCategoryList: {
      type: Array,
      default: () => []
    },
    discTypeList: {
      type: Array,
      default: () => []
    },
    IPAddressList: {
      type: Array,
      default: () => []
    },
    parentDeviceList: {
      type: Array,
      default: () => []
    },
    isServer: {
      type: Boolean,
      default: false
    },
    isNetwork: {
      type: Boolean,
      default: false
    },
    isDiskShelf: {
      type: Boolean,
      default: false
    },
    isIDC: {
      type: Boolean,
      default: false
    },
    isOther: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    'currentItem.loaithietbi_id': function(val) {
      this.controls.select_loaithietbi_id.invalid = !val
      this.$emit('device-type-changed', val)
    }
  },
  data() {
    return {
      validationRules: {
        txt_ten: ValidationRulesFactory.createTextRule('Tên thiết bị không được để trống'),
        select_loaithietbi_id: ValidationRulesFactory.createSelectRule('Loại thiết bị không được để trống.'),
        select_loaitudia_id: ValidationRulesFactory.createConditionalRule(
          'Loại tủ đĩa không được để trống.',
          () => this.isDiskShelf
        ),
        select_chungloaithietbi_id: ValidationRulesFactory.createConditionalRule(
          'Chủng loại thiết bị không được để trống.',
          () => this.isNetwork
        ),
        select_thietbicha_id: ValidationRulesFactory.createConditionalRule(
          'Thiết bị cha không được để trống với loại phụ trợ.',
          () => this.isOther
        )
      }
    }
  },
  methods: {
    normalizer(node) {
      return {
        id: node.ID,
        label: node.IP
      }
    },
    getValueForControl(controlKey) {
      const valueMap = {
        'txt_ten': this.currentItem.ten,
        'select_loaithietbi_id': this.currentItem.loaithietbi_id,
        'select_loaitudia_id': this.currentItem.loaitudia_id,
        'select_chungloaithietbi_id': this.currentItem.chungloaithietbi_id,
        'select_thietbicha_id': this.currentItem.thietbicha_id
      }
      
      return valueMap[controlKey]
    }
  }
}
</script>
<style scoped>
.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}
</style>
