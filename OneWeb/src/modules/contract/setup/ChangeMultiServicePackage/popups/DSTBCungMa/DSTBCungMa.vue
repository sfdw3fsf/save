<template>
  <b-modal
    ref="popupDSTBCungMa"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="frmDSTBCungMa_Load"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chọn thuê bao
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible">
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id">
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin khách hàng</div>
          <DataGrid ref="dgrList" class="table-result table-gachle" v-bind:columns="gridviewHoaDonPhatDuoc.headers"
                    v-bind:dataSource="gridviewHoaDonPhatDuoc.data" :showColumnCheckbox="false"
                    @selectedRowChanged="gridviewHoaDonPhatDuoc_FocusedRowChanged"
                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                    :enabled-select-first-row="true"></DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import DichVuVT from './DichVuVT'
import LoaiHinhTB from './LoaiHinhTB'

export default {
  name: 'DSTBCungMa',
  components: {
    DichVuVT,
    LoaiHinhTB
  },
  data () {
    return {
      actions: [
        {
          id: 'tsbtnChapNhan',
          name: 'Chấp nhận',
          active: true,
          icon_class: 'icon nc-icon-glyph ui-1_check-circle-08',
          visible: true
        }
      ],
      DialogResult: false,
      gridviewHoaDonPhatDuoc: {
        data: [],
        headers: [
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'DICHVUVT_ID',
            headerText: 'Dịch vụ',
            allowFiltering: true,
            allowSorting: false,
            template: function () {
              return {
                template: DichVuVT
              }
            }
          },
          {
            fieldName: 'LOAITB_ID',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false,
            template: function () {
              return {
                template: LoaiHinhTB
              }
            }
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        visible: false,
        disabled: false
      }
    }
  },
  methods: {
    frmDSTBCungMa_Load () {
      this.DialogResult = false
    },
    hideModal () {
      this.$refs.popupDSTBCungMa.hide()
    },
    showModal (danhsach) {
      this.gridviewHoaDonPhatDuoc.data = danhsach
      this.$refs.popupDSTBCungMa.show()
    },
    gridviewHoaDonPhatDuoc_FocusedRowChanged (item) {
      if (item != null) {
        this.gridviewHoaDonPhatDuoc.selectItem = item
      }
    },
    tsbtnChapNhan_Click () {
      this.DialogResult = true
      this.gridviewHoaDonPhatDuoc.data = this.gridviewHoaDonPhatDuoc.data.filter(item => item.thuebao_id == this.gridviewHoaDonPhatDuoc.selectItem.thuebao_id)
      this.hideModal()
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    receiverData (args) {

    },
    onActionClick (action) {
      if (action.id === 'tsbtnChapNhan') {
        this.tsbtnChapNhan_Click()
      }
    },
    getDialogResult () {
      return this.DialogResult
    },
    getDanhSach () {
      return this.gridviewHoaDonPhatDuoc.data
    }
  }
}
</script>

<style scoped>

</style>
