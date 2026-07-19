<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li @click="btnFilter_Click">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Lấy Serial </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <DataGrid ref="grvVT" v-bind:columns="grvVT.header" v-bind:dataSource="grvVT.list" :showFilter="true" :showColumnCheckbox="true" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" />
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import api from './api'
import DateTimeHelper from '../../helpers/dateHelper'
import { convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault } from '../../helpers'
import { FORM_CONFIG } from '../../const'

export default {
  emits: ['onBtnLaySerialClicked'],
  props: {
    v_grvVT: {
      type: [Array],
      defaul: () => {
        return []
      },
      require: false
    }
    // kieu: {
    //   type: [String, Number],
    //   defaul: null,
    //   require: false
    // },
    // dsCT_ChungTu: {
    //   type: [String, Number],
    //   defaul: null,
    //   require: false
    // }
  },
  data() {
    return {
      localLoading: 0,
      grvVT: {
        header: [
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true, allowSorting: true, freeze: 'Left' },
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', width: 250, allowFiltering: true, allowSorting: true, textAlign: 'Right' },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', width: 650, allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: []
      }
    }
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        console.log('result')
        console.log(result.data.data)
        return result.data.data ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    btnFilter_Click() {
      let selectedItems = this.$refs.grvVT.grid.getSelectedRecords()
      if(!selectedItems.length) {
        this.$toast.warning('Vui lòng chọn vật tư!')
        return
      }
      this.$emit('onBtnLaySerialClicked', {grvVTSelectedItems: selectedItems})
    }
  },
  async mounted() {
    this.grvVT.list = this.v_grvVT
  }
}
</script>
<style scoped></style>
