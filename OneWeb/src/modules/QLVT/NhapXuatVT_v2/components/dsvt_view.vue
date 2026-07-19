<template>
  <!-- popupDSVT -->
  <div>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Danh sách vật tư</div>
        <div class="close -ap icon-close" @click="$bvModal.hide('popupDSVT')" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="btnFilter_Click">
            <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Lấy Serial </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="info-row">
          <DataGrid ref="grvVT" class="table-result table-gachle" v-bind:columns="grvVT.header" v-bind:dataSource="grcVT.list" :showFilter="true" dataKeyField="" :showColumnCheckbox="true" :enabledSelectFirstRow="false" @selectedItemsChanged="grvVT_Checkbox" :allowPaging="true" :enablePagingServer="false" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  data() {
    return {
      grvVT: {
        header: [
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true, allowSorting: true, freeze: 'Left' },
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true, allowSorting: true, textAlign: 'Right' },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true, allowSorting: true }
        ],
        list: []
      }
    }
  },
  computed: {},
  async mounted() {},
  watch: {},
  methods: {
    grvVT_Checkbox(dataItem) {
      this.DSVTChecked = dataItem
    }
  },
  created() {
    window.addEventListener('keydown', this.keyDownHandler)
  },
  destroyed() {
    window.removeEventListener('keydown', this.keyDownHandler)
  }
}
</script>
<style scoped></style>
