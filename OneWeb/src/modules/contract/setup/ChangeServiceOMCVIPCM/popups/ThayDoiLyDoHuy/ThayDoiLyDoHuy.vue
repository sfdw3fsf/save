<template>
  <b-modal
    ref="popupThayDoiLyDoHuy"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @hide="hideModal"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> {{ headerTitle }}
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
        <div class="row">
          <div class="col-md-12 col-12 mb-2">
            <div class="info-row">
              <div class="key w90">Nhóm hủy</div>
              <div class="value">
                <div class="select-custom">
                  <Select2 ref="cboNhomHuy" :disabled='false' v-model="cboNhomHuy.value"
                           :options="cboNhomHuy.list" :settings="settingSelect2"/>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-12 col-12">
            <div class="info-row">
              <DataGrid ref="gridviewDanhSach" v-bind:columns="gridviewDanhSach.headers"
                        v-bind:dataSource="gridviewDanhSach.list" :showColumnCheckbox="true"
                        :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedItemsChanged="selectedItemsChanged"
                        :enabled-select-first-row="false"
              ></DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ThayDoiLyDoHuyAPI from './ThayDoiLyDoHuyAPI'

export default {
  name: 'ThayDoiLyDoHuy',
  props: {
    inputData: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  data () {
    return {
      headerTitle: 'Thay đổi lý do hủy thuê bao (Dành cho khối kỹ thuật)',
      actions: [
        {
          id: 'tsbtnChapNhan',
          name: 'Chấp nhận',
          active: true,
          icon_class: 'icon ui-1_check-circle-08 nc-icon-glyph',
          visible: true
        }
      ],
      cboNhomHuy: {
        list: [],
        disable: false,
        value: 0
      },
      settingSelect2: {
        language: 'vi'
      },
      gridviewDanhSach: {
        list: [],
        headers: [
          {
            fieldName: 'STT',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: false,
            width: 60,
            textAlign: 'center'
          },
          {
            fieldName: 'LYDOHUY',
            headerText: 'Lý do hủy',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left'
          },
          {
            fieldName: 'NOIDUNG',
            headerText: 'Nội dung',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left'
          }
        ],
        selected: 0,
        checked: [],
        selectItem: null
      },
      chapnhan: false,
      hdkh_id: 0,
      lapmoi: false,
      hdtb_id: 0,
      loaitb_id: 0,
      loaild_id: 0,
      vkieu: 1,
      v_kieu_lydohuy_lhtb: 2 // Default là loại của NVKT TTVT nhập
    }
  },
  watch: {
    'cboNhomHuy.value': function (newValue) {
      this.cboNhomHuy_SelectedIndexChanged(newValue)
    }
  },
  methods: {
    LoadDataFromProps () {
      if (this.inputData != null) {
        this.loaitb_id = this.inputData.loaitb_id ? this.inputData.loaitb_id : 0
        this.loaild_id = this.inputData.loaild_id ? this.inputData.loaild_id : 0
        this.hdtb_id = this.inputData.hdtb_id ? this.inputData.hdtb_id : 0
        this.vkieu = this.inputData.vkieu ? this.inputData.vkieu : 0
        this.v_kieu_lydohuy_lhtb = this.inputData.v_kieu_lydohuy_lhtb ? this.inputData.v_kieu_lydohuy_lhtb : 2
        this.hdkh_id = this.inputData.hdkh_id ? this.inputData.hdkh_id : 0
      }
    },
    showModal () {
      this.$refs.popupThayDoiLyDoHuy.show()
    },
    handleShowModal () {
      this.LoadDataFromProps()
      this.LAY_DS_NHOM_LYDO_HUY()
    },
    handleHideModal () {
      this.clean_data()
    },
    hideModal () {
      this.$refs.popupThayDoiLyDoHuy.hide()
    },
    clean_data () {
      this.loaitb_id = 0
      this.loaild_id = 0
      this.hdtb_id = 0
      this.vkieu = 0
      this.v_kieu_lydohuy_lhtb = 2
      this.gridviewDanhSach.list = []
      this.gridviewDanhSach.checked = []
      this.hdkh_id = 0
    },
    async LAY_DS_NHOM_LYDO_HUY () {
      let params = {
        'kieu': this.vkieu,
        'loaild_id': this.loaild_id,
        'loaitb_id': this.loaitb_id
      }
      let data = this.GetData(await ThayDoiLyDoHuyAPI.LAY_DS_NHOM_LYDO_HUY(this.axios, {
        p_ds_para: JSON.stringify(params)
      }))
      if (data !== null) {
        let datas = JSON.parse(data.p_js_returns).DS_NHOMHUY
        if (datas.length > 0) {
          this.cboNhomHuy.list = datas.map(item => {
            return {
              id: item.NHOMHUY_ID,
              text: item.TEN_NHOM
            }
          })
          this.cboNhomHuy.value = this.cboNhomHuy.list[0].id
        }
      }
    },
    cboNhomHuy_SelectedIndexChanged (newValue) {
      this.LAY_DS_LYDO_HUY(newValue)
    },
    async LAY_DS_LYDO_HUY (nhomhuyid) {
      let params = {
        'kieu': this.vkieu,
        'loaild_id': this.loaild_id,
        'loaitb_id': this.loaitb_id,
        'nhomhuy_id': nhomhuyid
      }
      let data = this.GetData(await ThayDoiLyDoHuyAPI.LAY_DS_LYDO_HUY(this.axios, {
        p_ds_para: JSON.stringify(params)
      }))
      if (data !== null) {
        let datas = JSON.parse(data.p_js_returns).DS_LYDOHUY
        if (datas.length > 0) {
          this.gridviewDanhSach.list = datas
        }
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    selectedItemsChanged (args) {
      console.log(args)
      this.gridviewDanhSach.checked = args
    },
    async tsbtnCapNhan_Click () {
      if (this.gridviewDanhSach.list.length <= 0) {
        return
      }
      if (this.gridviewDanhSach.checked.length <= 0) {
        this.$root.$toast.warning('Chưa chọn lý do hủy.')
        return
      }
      let dsLyDoHuy = this.gridviewDanhSach.checked.map(item => {
        return {
          LYDOHUY_ID: item.LYDOHUY_ID,
          NOIDUNG: item.NOIDUNG
        }
      })
      let req = {
        'hdtb_id': this.hdtb_id,
        'loaild_id': this.loaild_id,
        'loaitb_id': this.loaitb_id,
        'nhomhuy_id': this.cboNhomHuy.value,
        'kieu': this.vkieu,
        'js_lydohuy': JSON.stringify(dsLyDoHuy),
        'kieu_lydohuy_lhtb': this.v_kieu_lydohuy_lhtb,
        'ip': await this.$root.token.getIP(),
        'ma_nd': this.$root.token.getUserName(),
        'may_cn': await this.$root.token.getMachine()
      }
      let request = { 'p_ds_para': JSON.stringify(req) }
      let data = this.GetData(await ThayDoiLyDoHuyAPI.FN_THAYDOILYDOHUY_CAPNHAT(this.axios, request))
      if (data !== null) {
        let kq = data.kq
        if (kq === '1') {
          this.$emit('success', kq)
          this.$root.$toast.success('Cập nhật dữ liệu thành công!')
          this.hideModal()
        } else {
          let result = JSON.parse(data.p_js_returns)
          this.$root.$toast.warning(result.MESSAGE)
        }
      }
    },
    onActionClick (action) {
      if (action.id === 'tsbtnChapNhan') {
        this.tsbtnCapNhan_Click()
      }
    }
  }
}
</script>

<style scoped>

</style>
