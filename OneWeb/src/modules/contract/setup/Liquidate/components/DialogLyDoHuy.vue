<template>
  <ejs-dialog :enableResize='true' :allowDragging="true" :visible="false" ref="modal" :header='"Chọn lý do hủy dịch vụ"'
    showCloseIcon=true :target="'.main-wrapper'" width='80%' :position="{ X: 'center', Y: '10' }">
    <div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="kieuThaoTac == 0">
            <a @click.prevent="onAccept">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận (F5)
            </a>
          </li>
          <li v-if="kieuThaoTac == 1" :class="{'disabled': !(hdtbId != undefined && hdtbId != null && hdtbId > 0)}">
            <a @click.prevent="onUpdate">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận (F5)
            </a>
          </li>
        </ul>

      </div>
      <div class="popup-body">
        <div class="box-form">
          <div v-if="banCheo" class="info-row">
            <div class="key w80">Kiểu lắp đặt</div>
            <div class="value">
              <ejs-dropdownlist :showClearButton="false" v-model="cboKieuLapDat.SelectedValue"
                :dataSource="cboKieuLapDat.DataSource" :fields="{ text: 'ten_kieuld', value: 'kieuld_id' }"
                :allowFiltering='true'></ejs-dropdownlist>
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Nhóm hủy</div>
            <div class="value">
              <ejs-dropdownlist :showClearButton="false" v-model="cboNhomHuy.SelectedValue"
                :dataSource="cboNhomHuy.DataSource" :allowFiltering='true' :fields="{ text: 'NAME', value: 'ID' }"
                @change="onNhomHuyChanged()"></ejs-dropdownlist>
            </div>
          </div>
          <div v-if="banCheo" class="key w200 tb mt-2 legend-title">Lý do hủy bán</div>
          <div class="table-content">
            <ejs-grid ref="gridLyDoHuy" :dataSource="gridLyDoHuy.DataSource" :allowPaging="true"
              :pageSettings="pageSettings" :allowSorting="false" :allowFiltering="true" :allowTextWrap="true"
              gridLines="Both">
              <e-columns>
                <e-column field="stt" headerText="Stt" width=80></e-column>
                <e-column field="lydohuy_id" :visible="false"></e-column>
                <e-column field="lydohuy" headerText="Lý do hủy"></e-column>
                <e-column field="noidung" headerText="Nội dung"></e-column>
                <e-column type='checkbox' width='50'></e-column>
              </e-columns>
            </ejs-grid>
          </div>
          <div v-if="banCheo" class="key w200 tb mt-2 legend-title">Lý do hủy thi công</div>
          <div v-if="banCheo" class="table-content">
            <ejs-grid ref="gridLyDoHuyThiCong" :dataSource="gridLyDoHuyThiCong.DataSource" :allowPaging="true"
              :pageSettings="pageSettings" :allowSorting="false" :allowFiltering="true" :allowTextWrap="true"
              gridLines="Both">
              <e-columns>
                <e-column field="stt" headerText="Stt" width=80></e-column>
                <e-column field="lydohuy_id" :visible="false"></e-column>
                <e-column field="lydohuy" headerText="Lý do hủy"></e-column>
                <e-column field="noidung" headerText="Nội dung"></e-column>
                <e-column type='checkbox' width='50'></e-column>
              </e-columns>
            </ejs-grid>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import axios from "axios"
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'

const API = {
  layDsKieuLapDatBC (tinh_tc_id, loaitb_id, loaihd_id) {
    return axios.post('web-bancheo/danhmuc/load_dm_kieuld', {
      "tinhthicong_id": tinh_tc_id,
      "ploaitb_ld": loaitb_id,
      "ploaitb": loaitb_id,
      "vloaihd": loaihd_id
    }).then(rs => rs.data.data)
  },
  layDsKieuLapDat () {
    return axios.get('web-quantri/danhmuc-chung/CSS_KIEU_LD').then(rs => rs.data.data)
  },
  layDsNhomHuy (loaildId, loaitbId, kieu) {
    return axios.post('web-hopdong/thanhly/lay_ds_nhomhuy_theo_loaitb', {
      'loaild_id': loaildId,
      'loaitb_id': loaitbId,
      'kieu': kieu
    }).then(rs => rs.data.data)
  },
  layDsLyDoHuy (loaildId, loaitbId, nhomHuyId, kieu) {
    return axios.post('web-hopdong/thanhly/lay_ds_lydo_huy', {
      'loaild_id': loaildId,
      'loaitb_id': loaitbId,
      'nhomhuy_id': nhomHuyId,
      'kieu': kieu
    }).then(rs => rs.data.data)
  },
  capNhat (hdtbId, loaildId, loaitbId, nhomHuyId, dsLyDoHuy, kieuLyDoHuy, ip, maNd, mayCn, kieu) {
    return axios.post('web-hopdong/thaydoilydohuy/fn_thaydoilydohuy_capnhat', { "p_ds_para": JSON.stringify({
      "hdtb_id": hdtbId,
      "loaild_id": loaildId,
      "loaitb_id": loaitbId,
      "nhomhuy_id": nhomHuyId,
      "kieu": kieu,
      "js_lydohuy": dsLyDoHuy,
      "kieu_lydohuy_lhtb": kieuLyDoHuy,
      "ip": ip,
      "ma_nd": maNd,
      "may_cn": mayCn
    })})
  }
}

export default {
  mixins: [pagingAndFilter],
  props: {
    tinhtc: {
      type: Object,
      default: {}
    },
    loaildID: {
      type: Number,
      default: 0
    },
    loaitbID: {
      type: Number,
      default: 0
    },
    hdtbId: {
      type: Number,
      default: 0
    },
    kieuThaoTac: {
      type: Number, // 0: select, 1: cap chat/thay doi
      default: 0
    },
    banCheo: false,
    kieuLapDat: { DataSource: [], SelectedValue: null }
  },
  data: () => ({
    pageSettings: { pageSizes: [10, 50, 100, 200], pageCount: 4, pageSize: 10 },
    cboNhomHuy: { DataSource: [], SelectedValue: null },
    gridLyDoHuy: { DataSource: [] },
    tt_nd: {
      ma_nd: '',
      ma_tinh: '',
      may_cn: '',
      ip: '',
      ngay_cn: ''
    },
    tinhthicong_id: 0,
    loaitb_id: 0,
    cboKieuLapDat: { DataSource: [], SelectedValue: null },
    gridLyDoHuyThiCong: { DataSource: [] }
  }),
  watch: {
    "kieuLapDat.SelectedValue": {
      handler (val) {
        if (val) {
          this.cboKieuLapDat.SelectedValue = val
        }
      }
    },
    "tinhtc.tinhthicong_id": {
      handler (val) {
        if (val) {
          // this.layDsKieuLapDat()
          this.tinhthicong_id = val 
        }
      }
    },
    "loaitbID"(val) {
      if (val) this.loaitb_id = val
    }
  },
  mounted () {
    window['app'] = this
    this.tt_nd.ma_tinh = this.$auth.getMaTinh()
    this.tt_nd.ma_nd = this.$auth.getUserName()
    this.tt_nd.ngay_cn = this.$auth.getNgayCapNhat()
    this.$auth.getIP().then(rs => { this.tt_nd.ip = rs })
    this.$auth.getMachine().then(rs => { this.tt_nd.may_cn = rs })

    this.layDsNhomHuy()
    if (this.tinhtc && this.tinhtc.tinhthicong_id && this.loaitbID) {
      this.layDsKieuLapDat()
    }
  },
  methods: {
    layDsNhomHuy () {
      API.layDsNhomHuy(this.loaildID, this.loaitbID, 1).then(rs => {
        this.cboNhomHuy.DataSource = rs || []
        if (rs && rs.length > 0) {
          this.cboNhomHuy.SelectedValue = rs[0].ID
        }
      })
    },
    show (visible) {
      if (visible) {
        this.$refs.modal.show()
        this.layDsNhomHuy()
      } else {
        this.$refs.modal.hide()
      }
    },
    onAccept () {
      if (this.banCheo) {
        const cancellationReasons = this.$refs.gridLyDoHuy.getSelectedRecords() || []
        const constructionCancellationReasons = this.$refs.gridLyDoHuyThiCong.getSelectedRecords() || []
        if (cancellationReasons.length == 0 || constructionCancellationReasons.length == 0) {
          this.$toast.warning('Phải chọn lý do hủy bên bán và bên thi công!')
          return
        }
      }

      let kieuLd = null
      if (this.kieuLapDat && this.kieuLapDat.SelectedValue != null && this.kieuLapDat.SelectedValue != undefined) {
        kieuLd = this.kieuLapDat.SelectedValue
      }

      const lydohuy = this.$refs.gridLyDoHuy.getSelectedRecords().map(e => ({ ...e, NGAY_YC: this.tt_nd.ngay_cn }))
      this.$emit('onAccept', lydohuy, this.cboKieuLapDat.SelectedValue)//kieuLd)
      this.show(false)
    },
    onUpdate () {
      let dsLyDoHuy = this.$refs.gridLyDoHuy.getSelectedRecords()
      if (!dsLyDoHuy || dsLyDoHuy.length == 0) {
        this.$toast.error('Bạn chưa chọn lý do hủy để cập nhật')
        return
      }

      dsLyDoHuy = dsLyDoHuy.map(r => ({ LYDOHUY_ID: r.lydohuy_id, NOIDUNG: r.noidung }))
      const lydohuy = this.$refs.gridLyDoHuy.getSelectedRecords().map(e => ({ ...e, NGAY_YC: this.tt_nd.ngay_cn }))

      API.capNhat(this.hdtbId, this.loaildID, this.loaitbID, this.cboNhomHuy.SelectedValue, dsLyDoHuy, 0, this.tt_nd.ip, this.tt_nd.ma_nd, this.tt_nd.may_cn, 0).then(rs => {
        this.$toast.success('Cập nhật lý do hủy thành công')
        this.$emit('onAccept', lydohuy)
        this.show(false)
      })
    },
    onNhomHuyChanged () {
      API.layDsLyDoHuy(this.loaildID, this.loaitbID, this.cboNhomHuy.SelectedValue, 1).then(rs => {
        this.gridLyDoHuy.DataSource = rs
        this.gridLyDoHuyThiCong.DataSource = rs
      })
    },
    layDsKieuLapDat () {
      API.layDsKieuLapDatBC(this.tinhthicong_id, this.loaitb_id, 4).then(rs => {
        this.cboKieuLapDat.DataSource = rs
        if (rs.length > 0) {
          this.cboKieuLapDat.SelectedValue = rs[0].kieuld_id
        }
      })
    }
  }
}
</script>
<style scoped>
.list-actions-top .disabled a {
  color: #6c757d;
}

.tb {
  font-weight: bold;
}

.mt-2 {
  margin-top: 20px !important;
}
</style>
