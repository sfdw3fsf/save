<template>
  <b-modal
    ref="popupDangKyLyDoHuyBC"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
    @hide="handleHiddenModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chọn lý do hủy dịch vụ
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
          <div class="col-8">
            <div class="info-row">
              <div class="key w120">Kiểu lắp đặt:</div>
              <div class="value">
                <Select2 ref="cboTinh" :disabled='false' v-model="cboKieuLD.SelectedValue"
                         :options="cboKieuLD.list" :settings="settingSelect2"/>
              </div>
            </div>
            <div class="info-row">
              <div class="key w120">Nhóm hủy:</div>
              <div class="value">
                <Select2 ref="cboTinhCuoi" :disabled='false' v-model="cboNhomHuy.SelectedValue"
                         :options="cboNhomHuy.list" :settings="settingSelect2"/>
              </div>
            </div>
          </div>
          <div class="col-4"></div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Lý do hủy bán
          </div>
          <div class="row">
            <div class="col-12">
              <DataGrid ref="gridviewDanhSach1" v-bind:columns="dgvLyDoHuyBan.headers"
                        v-bind:dataSource="dgvLyDoHuyBan.data" :showColumnCheckbox="false"
                        :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                        @rowClicked="dgvLyDoHuyBan_SelectRow"
                        :enabled-select-first-row="false"
              ></DataGrid>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Lý do hủy thi công
          </div>
          <div class="row">
            <div class="col-12">
              <DataGrid ref="gridviewDanhSach2" v-bind:columns="dgvLyDoHuyTC.headers"
                        v-bind:dataSource="dgvLyDoHuyTC.data" :showColumnCheckbox="false"
                        :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                        @rowClicked="dgvLyDoHuyTC_SelectRow"
                        :enabled-select-first-row="false"
              ></DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>`
  </b-modal>
</template>

<script>
import DangKyLyDoHuyBCAPI from './DangKyLyDoHuyBCAPI'
import {LOAI_HOPDONG} from '../../../../../constants'
import Vue from 'vue'
import moment from 'moment'

export default {
  name: 'DangKyLyDoHuyBC',
  data () {
    return {
      actions: [
        {
          id: 'tsbtnThem',
          name: 'Thêm LD',
          active: true,
          icon_class: 'one-mobile-plus',
          visible: true
        },
        {
          id: 'tsbtnChapNhan',
          name: 'Chấp nhận',
          active: true,
          icon_class: 'icon ui-1_check-circle-08 nc-icon-glyph',
          visible: true
        }
      ],
      settingSelect2: {
        language: 'vi'
      },
      cboKieuLD: {
        list: [],
        SelectedValue: 0
      },
      cboNhomHuy: {
        list: [],
        SelectedValue: 0
      },
      dgvLyDoHuyBan: {
        data: [],
        headers: [
          {
            fieldName: 'stt',
            headerText: 'Stt',
            allowFiltering: true,
            allowSorting: false,
            width: '40',
            textAlign: 'center'
          },
          {
            fieldName: 'lydohuy',
            headerText: 'Lý do hủy',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'noidung',
            headerText: 'Nội dung',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dangky',
            headerText: 'Chọn',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'center',
            width: '70',
            template: function () {
              return {
                template: Vue.component('DangKyTemplate1', {
                  template: `<input type="checkbox" class="check" v-model="data.dangky">`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          }
        ],
        selected: 0,
        selectItem: null,
        checked: []
      },
      dgvLyDoHuyTC: {
        data: [],
        headers: [
          {
            fieldName: 'stt',
            headerText: 'Stt',
            allowFiltering: true,
            allowSorting: false,
            width: '40',
            textAlign: 'center'
          },
          {
            fieldName: 'lydohuy',
            headerText: 'Lý do hủy',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'noidung',
            headerText: 'Nội dung',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dangky',
            headerText: 'Chọn',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'center',
            width: '70',
            template: function () {
              return {
                template: Vue.component('DangKyTemplate2', {
                  template: `<input type="checkbox" class="check" v-model="data.dangky">`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          }
        ],
        selected: 0,
        selectItem: null,
        checked: []
      },
      chapnhan: false,
      ds: [],
      hdkh_id: 0,
      lapmoi: true,
      hdtb_id: 0,
      loaitb_id: 0,
      loaild_id: 0,
      vkieu: 1,
      tinhthicong_id: 0,
      thongsotc: {},
      dsDS: [],
      dsDK_HUY: []
    }
  },
  watch: {
    'cboNhomHuy.SelectedValue': async function (newValue) {
      await this.DanhSach(newValue)
    }
  },
  methods: {
    handleHiddenModal () {
      this.cboKieuLD.list = []
      this.cboNhomHuy.list = []
      this.dgvLyDoHuyTC.data = []
      this.dgvLyDoHuyBan.data = []
      this.cboNhomHuy.SelectedValue = 0
    },
    async handleShowModal () {
      try {
        let ds = []
        if (this.loaild_id == 2) {
          ds = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinhthicong_id, LOAI_HOPDONG.THAY_DOI_DV, this.loaitb_id)
        } else {
          ds = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinhthicong_id, LOAI_HOPDONG.CHAMDUT_SD, this.loaitb_id)
        }
        if (ds.length > 0) {
          this.cboKieuLD.list = ds.map(item => {
            return {
              id: item.kieuld_id,
              text: item.ten_kieuld
            }
          })
          this.cboKieuLD.SelectedValue = this.cboKieuLD.list[0].id
        } else {
          this.cboKieuLD.list = []
        }
        let dsNH = await this.sp_layds_nhomhuy(this.vkieu, this.loaild_id, this.loaitb_id)
        if (dsNH.length > 0) {
          this.cboNhomHuy.list = dsNH.map(item => {
            return {
              id: item.nhomhuy_id,
              text: item.ten_nhom
            }
          })
          this.cboNhomHuy.SelectedValue = this.cboNhomHuy.list[0].id
        }
        this.dsDS = []
        this.chapnhan = false
      } catch (error) {
        console.log(error)
      }
    },
    dgvLyDoHuyTC_SelectRow (index, item) {
      try {
        if (this.lapmoi) {
          if (index >= 0) {
            if (item['dangky'] == 0) {
              for (let i = 0; i < this.dgvLyDoHuyTC.data.length; i++) {
                this.dgvLyDoHuyTC.data[i]['dangky'] = 0
              }
              this.dgvLyDoHuyTC.data[index]['dangky'] = 1
            }
            this.dgvLyDoHuyTC.data = [...this.dgvLyDoHuyTC.data]
          }
        }
      } catch (error) {
        console.log(error)
      }
    },
    dgvLyDoHuyBan_SelectRow (index, item) {
      try {
        if (this.lapmoi) {
          if (index >= 0) {
            if (item['dangky'] == 0) {
              for (let i = 0; i < this.dgvLyDoHuyBan.data.length; i++) {
                this.dgvLyDoHuyBan.data[i]['dangky'] = 0
              }
              this.dgvLyDoHuyBan.data[index]['dangky'] = 1
            }
            this.dgvLyDoHuyBan.data = [...this.dgvLyDoHuyBan.data]
          }
        }
      } catch (error) {
        console.log(error)
      }
    },
    showModal (tinhthicongid, indsDKHUY, hdtbid, lapmoi, loaildid, loaitbid) {
      this.tinhthicong_id = tinhthicongid
      this.ds = indsDKHUY
      this.hdtb_id = hdtbid
      this.lapmoi = lapmoi
      this.loaild_id = loaildid
      this.loaitb_id = loaitbid
      this.setVisibleButton('tsbtnThem', false)
      this.$refs.popupDangKyLyDoHuyBC.show()
    },
    async DanhSach (nhomhuyid) {
      try {
        let ds = await this.LAY_DS_LYDO_HUY(this.loaild_id, this.loaitb_id, nhomhuyid, this.vkieu)
        this.dsDS.push(ds)
        let dstemp = await this.LAY_DS_LYDO_HUY(this.loaild_id, this.loaitb_id, nhomhuyid, this.vkieu)
        this.dsDS.push(dstemp)
        for (let i = 0; i < 2; i++) {
          for (let k = 0; k < this.dsDS[i].length; k++) {
            if (this.dsDS[i].length <= 0) {
              break
            }
            if (this.ds.length != 0) {
              if (this.ds[i].length <= 0) {
                break
              }
              for (let j = 0; j < this.ds[i].length; j++) {
                if (this.dsDS[i][k]['lydohuy_id'] == this.ds[i][j]['lydohuy_id']) {
                  this.dsDS[i][k]['dangky'] = 1
                  this.dsDS[i][k]['noidung'] = this.ds[i][j]['noidung']
                }
              }
            }
          }
        }
        console.log('dsDs', this.dsDS)
        this.dgvLyDoHuyBan.data = this.dsDS[0]
        this.dgvLyDoHuyTC.data = this.dsDS[1]
      } catch (error) {
        console.log(error)
      }
    },
    setVisibleButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].visible = status
      }
    },
    hideModal () {
      this.$refs.popupDangKyLyDoHuyBC.hide()
    },
    tsbtnCapNhan_Click () {
      try {
        this.thongsotc.kieuld_id = Number(this.cboKieuLD.SelectedValue)
        this.dsDK_HUY = []
        let dsDKHUYTEMP = []
        if (this.dgvLyDoHuyBan.data.filter(item => item.dangky == 1).length > 1) {
          this.$root.$toast.warning('Chỉ được chọn 1 lý do hủy!')
          return
        }
        if (this.dgvLyDoHuyTC.data.filter(item => item.dangky == 1).length > 1) {
          this.$root.$toast.warning('Chỉ được chọn 1 lý do hủy TC!')
          return
        }
        if (this.dgvLyDoHuyBan.data.filter(item => item.dangky == 1).length != 1 && this.dgvLyDoHuyTC.data.filter(item => item.dangky == 1).length != 1) {
          this.$root.$toast.warning('Phải chọn lý do hủy bên bán và bên thi công!')
          return
        }
        this.dsDS[0] = this.dgvLyDoHuyBan.data
        this.dsDS[1] = this.dgvLyDoHuyTC.data
        for (let k = 0; k < this.dsDS.length; k++) {
          dsDKHUYTEMP = []
          for (let j = 0; j < this.dsDS[k].length; j++) {
            if (this.dsDS[k][j]['dangky'] == 1) {
              let row = {}
              row.lydohuy_id = this.dsDS[k][j]['lydohuy_id']
              row.hdtb_id = this.hdtb_id
              row.noidung = this.dsDS[k][j]['noidung']
              row.ngay_yc = moment(new Date()).format('DD/MM/YYYY')
              dsDKHUYTEMP.push(row)
              break
            }
          }
          this.dsDK_HUY.push(dsDKHUYTEMP)
        }
        this.$emit('form-close', this.dsDK_HUY)
        this.hideModal()
      } catch (error) {
        console.log(error)
      }
    },
    onActionClick (item) {
      if (item.id == 'tsbtnChapNhan') {
        this.tsbtnCapNhan_Click()
      }
    },
    async SP_HT_KIEULD_LOAITB_BANCHEO (tinhid, loaiid, loaitb) {
      let req = {
        'tinhthicong_id': tinhid,
        'loaihd': loaiid,
        'loaitb': loaitb
      }
      return this.GetDataList(await DangKyLyDoHuyBCAPI.SP_HT_KIEULD_LOAITB_BANCHEO(this.axios, req))
    },
    async sp_layds_nhomhuy (kieu, loaild, loaitbid) {
      let req = {
        'kieu': kieu,
        'loaild_id': loaild,
        'loaitb_id': loaitbid
      }
      return this.GetDataList(await DangKyLyDoHuyBCAPI.sp_layds_nhomhuy(this.axios, req))
    },
    async SP_MOBILE_LAYDS_NHOMHUY (kieu, loaild, loaitbid) {
      let req = {
        'kieu': kieu,
        'loaild_id': loaild,
        'loaitb_id': loaitbid
      }
      return this.GetDataList(await DangKyLyDoHuyBCAPI.SP_MOBILE_LAYDS_NHOMHUY(this.axios, req))
    },
    async LAY_DS_LYDO_HUY (loaildid, loaitbid, nhomhuyid, kieu) {
      let req = {
        'loaild_id': loaildid,
        'loaitb_id': loaitbid,
        'nhomhuy_id': nhomhuyid,
        'kieu': kieu
      }
      return this.GetDataList(await DangKyLyDoHuyBCAPI.LAY_DS_LYDO_HUY(this.axios, req))
    },
    GetDataList: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
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
    }
  }
}
</script>

<style scoped>

</style>
