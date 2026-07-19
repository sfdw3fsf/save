<template>
  <b-modal
    ref="popupLocDuLieu"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form" v-show="true">
          <div class="legend-title">DANH SÁCH DỮ LIỆU IMPORT</div>
          <KDataGrid
            ref="girdDataImport"
            :columns="columnsImport"
            :dataSource="dataSourcesImport"
            :enable-paging-server="false"
            :showFilter="true"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="false"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
  import KDataGrid from '@/components/kylq_components/KDataGrid'
  import ActionTop from '@/components/kylq_components/ActionTop.vue'

  export default {
    name: 'LocDuLieuModal',
    props: {
      propColumnsImport: {
        type: Array,
        default: () => []
      },
      propDataSourcesImport: {
        type: Array,
        default: () => []
      },
      Role: {
        type: Number,
        default: 2
      },
    },
    components: {
      KDataGrid,
      ActionTop
    },
    data() {
      return {
        columnsImport: null,
        dataSourcesImport: [],
        actions: [
          {
            id: 'huy',
            name: 'Đóng',
            active: true,
            icon_class: 'nc-icon-glyph ui-1_circle-remove'
          }

        ],
        trangthai_selected: 2,
        checkRaBanCheo: false,
        indeterminate: false,
        checkTimTheoFile: false,
        file_selected: null,
        ds_files: []
      }
    },
    mounted() {
      this.columnsImport = this.propColumnsImport
      this.dataSourcesImport = this.propDataSourcesImport
    },
    methods: {
      showModal() {
        this.$refs['popupLocDuLieu'].show()
      },
      hideModal() {
        this.$refs['popupLocDuLieu'].hide()
      },
      onActionClick(action) {
        switch (action.id) {
          case 'huy':
            this.hideModal()
            break
          default:
            this.hideModal()
            break
        }
      },
      handleShowModal() {

      }
    }

  }
</script>
