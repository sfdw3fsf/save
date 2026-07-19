<template>
  <div class="modal-content popup-box">
    <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thông tin danh sách port 
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div> -->

    <div class="popup-body">
      <div class="row">
        <div class="box-form">
          <DataGrid ref="gridDanhSachVatTu" v-bind:columns="input_data.headers" v-bind:dataSource="input_data.list" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabled-select-first-row="true"></DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import API from '../API'
export default {
  name: 'TraCuuPortHDTB',
  props: {
    input_data: {
      type: Object,
      default: 0
    }
  },
  components: {},
  data() {
    return {
      DsPort: {
        headers: [
          { fieldName: 'slot', headerText: 'Slot' },
          { fieldName: 'port', headerText: 'Port' },
          { fieldName: 'vpi', headerText: 'Vpi/Svlan' },
          { fieldName: 'vci', headerText: 'Vci/Cvlan' },
          { fieldName: 'rack', headerText: 'Rack' },
          { fieldName: 'shelf', headerText: 'Shelf' },
          { fieldName: 'system', headerText: 'System' },
          { fieldName: 'tendslam', headerText: 'Dslam' },
          { fieldName: 'vitri', headerText: 'Vị trí Port' },
          { fieldName: 'ten_card', headerText: 'Card' },
          { fieldName: 'selfid01', headerText: 'SelfID' },
          { fieldName: 'selfid02', headerText: 'OLT Slot' },
          { fieldName: 'selfid03', headerText: 'OLT Port' },
          { fieldName: 'selfid04', headerText: 'ONU ID' },
          { fieldName: 'adsl_port', headerText: 'Adsl port' },
          { fieldName: 'port_mdf', headerText: 'Port MDF' },
          { fieldName: 'ketcuoi', headerText: 'Spliter' }
        ],
        list: []
      }
    }
  },
  computed: {},
  methods: {
    showModal() {
      this.$refs['popupTraCuuPortHDTB'].show()
    },
    hideModal() {
      this.$refs['popupTraCuuPortHDTB'].hide()
    },

    handleShowModal() {}
  }
}
</script>
