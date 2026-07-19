<template>
  <b-modal
    ref="popupDuAnHDTB"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Cập nhật dự án cho hợp đồng thuê bao
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
        <div class="info-row">
          <div class="key w90">Dự án</div>
          <div class="value">
            <ComboboxGridCustom :valueField="DmDuAn.valueField" :textField="DmDuAn.textField"
                                v-model="DmDuAn.selectDuAn" :value="DmDuAn.selectDuAn"
                                :columns="DmDuAn.headers"
                                :panelDataHeight="DmDuAn.panelDataHeight" :enabledSelectFirstRow="false"
                                :dataSource="DmDuAn.data">
            </ComboboxGridCustom>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import ComboboxGridCustom from '../../../../../admin/category/Department/components/ComboboxGrid'
import DuAnHDTBAPI from './DuAnHDTBAPI'

export default {
  name: 'DuAnHDTB',
  props: ['vhdtb_id'],
  components: {ComboboxGridCustom},
  data () {
    return {
      actions: [
        {
          id: 'tsbtnGhiLai',
          name: 'Ghi lại (F9)',
          active: true,
          icon_class: 'one-save',
          visible: true
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa',
          active: true,
          icon_class: 'one-trash',
          visible: true
        }
      ],
      DmDuAn: {
        data: [],
        textField: 'ten_duan',
        valueField: 'duan_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'duan_id',
            headerText: 'duan_id',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'ma_duan',
            headerText: 'Mã dự án',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_duan',
            headerText: 'Tên dự án',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'congnang',
            headerText: 'Công năng',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          }
        ],
        selectDuAn: null
      },
      duan_id: 0
    }
  },
  methods: {
    handleShowModal () {
      this.vhdtb_id = this.vhdtb_id ? this.vhdtb_id : 0
      this.LAY_DS_DUAN()
      this.LAY_DS_HDTB_BY_ID(this.vhdtb_id)
    },
    async LAY_DS_DUAN () {
      let data = this.GetData(await DuAnHDTBAPI.LAY_DS_DUAN(this.axios))
      if (data.length > 0) {
        this.DmDuAn.data = data
      }
    },
    async LAY_DS_HDTB_BY_ID (hdtbId) {
      let params = {
        hdtbId: hdtbId
      }
      let data = this.GetData(await DuAnHDTBAPI.LAY_DS_HDTB_BY_ID(this.axios, params))
      if (data.length > 0) {
        if (data[0].duan_id) {
          this.duan_id = Number.parseInt(data[0].duan_id)
          this.DmDuAn.selectDuAn = this.duan_id
        }
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    showModal () {
      this.$refs.popupDuAnHDTB.show()
    },
    hideModal () {
      this.$refs.popupDuAnHDTB.hide()
    },
    async tsbtnGhiLai_Click () {
      if (this.DmDuAn.selectDuAn === null) {
        this.$root.$toast.warning('Bạn chưa chọn dự án !')
        return
      }
      let req = {
        'hdtb_id': this.vhdtb_id,
        'duan_id': this.DmDuAn.selectDuAn
      }
      await DuAnHDTBAPI.SP_UPDATE_DUAN_HDTB(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$root.$toast.success('Cập nhật dự án thành công !')
        } else {
          this.$root.$toast.warning(response.data.message)
        }
      })
      this.hideModal()
    },
    async tsbtnXoa_Click () {
      let req = {
        'hdtb_id': this.vhdtb_id,
        'duan_id': 0
      }
      await DuAnHDTBAPI.SP_UPDATE_DUAN_HDTB(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$root.$toast.success('Xóa dự án thành công !')
        } else {
          this.$root.$toast.warning(response.data.message)
        }
      })
      this.hideModal()
    },
    onActionClick (action) {
      if (action.id === 'tsbtnGhiLai') {
        this.tsbtnGhiLai_Click()
      } else if (action.id === 'tsbtnXoa') {
        this.tsbtnXoa_Click()
      }
    }
  }
}
</script>

<style scoped>

</style>
