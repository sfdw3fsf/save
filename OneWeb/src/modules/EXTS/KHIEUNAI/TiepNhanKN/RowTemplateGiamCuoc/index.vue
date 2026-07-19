<template src="./index.html"></template>
<script>
import Calculator from '@/components/Calculator'
import { mapState, mapActions, mapGetters } from 'vuex'
import {TiepNhanKN, getterName, actionName, statePropertyName } from './../store/index'
import EventBus from '@/utils/eventBus'
export default {
  name: 'RowTemplateGiamCuoc',
  components: { Calculator },
  data: function () {
    return {
      rowFocused: false,
      isCalculatorTienShow: false,
      isCalculatorTienGCShow: false
    }
  },
  computed: {
    ...mapState('khieunai/TiepNhanKN', statePropertyName),
    ...mapState('khieunai', ['TiepNhanKN']),
    ...mapGetters('khieunai/TiepNhanKN', getterName),
    grid: function () {
      return this.$parent
    },
    formChinh: function () {
      return this.grid.$parent.$parent.$parent
    },
    DS_ThangGC: function () {
      return this.DSThangGC
    },
    DS_ThangApDung: function () {
      return this.DSThangApDung
    },
    isShowForm: function () {
      if (this.isAddNew) {
        return this.data.FOCUSED === true
      } else {
        return true
      }
    },
    isAddNew: function () {
      return this.data.ID === '-1'
    }
  },
  watch: {
    'data.TIEN': function (val) {
      this.tinhTheoTongTien()
    },
    'data.TIEN_GC': function (val) {
      this.tinhTheoTien()
    }
  },
  created: async function () {
  },
  methods: {
    ...mapActions('khieunai/TiepNhanKN', actionName),
    row_clickAddNew: function (args) {
      this.formChinh.focusRowThemNo(true)
    },
    updateData: function () {
      this.setDataGC(this.data)
    },
    newRow_Enter: async function (args) {
      if (this.isAddNew) {
        console.log('🚀 ~ file: index.vue ~ line 61 ~ this.data', this.data)
        if (this.data.KY_GC === 0 || this.data.THANG_GC === 0) {
          return
        }
        if (this.data.TIEN <= 0 || this.data.TIEN_GC <= 0 || this.data.VAT_GC <= 0) {
          return
        }
        this.themNo()
      } else {
        this.updateData()
      }
    },
    tinhTheoTien: function (args) {
      var tien_gc = this.data.TIEN_GC
      var tongtien = Math.round(tien_gc + (tien_gc * 0.1))
      var vat = tongtien - tien_gc
      this.data.TIEN = tongtien
      this.data.VAT_GC = vat
      this.setDataGC(this.data)
    },
    tinhTheoTongTien: function (args) {
      var tongtien = this.data.TIEN
      var tien_gc = Math.round(tongtien / 1.1)
      var vat = tongtien - tien_gc
      this.data.TIEN_GC = tien_gc
      this.data.VAT_GC = vat
      this.setDataGC(this.data)
    },
    themNo: function () {
      this.formChinh.themNo(this.data)
    },
    btn_click: async function (args) {
      if (this.isAddNew) {
        this.formChinh.focusRowThemNo(false)
      } else {
        await this.delDataGC(this.data.index)
      }
    }
  }
}
</script>
<style>
.text-right .e-numerictextbox {
  text-align: right;
  padding-right: 7px !important;
}
.e-grid.e-responsive .e-rowcell, .e-grid.e-responsive .e-headercelldiv {
  white-space: inherit !important;
}
.text-rose {
  color: #FF007F !important;
}
.calutekn .e-rowcell {
  overflow: inherit !important;
}
.calutekn .list-calc{
    white-space: initial;
}
</style>
