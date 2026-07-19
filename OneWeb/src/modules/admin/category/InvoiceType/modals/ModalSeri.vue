<template>
  <ejs-dialog
    :enableResize='true'
    :isModal="true"
    :visible="false"
    ref="modalSeri"
    :header='"Thông tin seri điện tử"'
    showCloseIcon=true
    :target="'.main-wrapper'"
    width='60%'
    height="800"
    @open="openedModalSeri"
  >
    <div class="list-actions-top">
      <ul class="list"> <li> <a href="#" @click.prevent="updateSeriClick"> <span class="icon one-save"></span>Ghi lại </a> </li> </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin hóa đơn</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w70">Mẫu số</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    popupWidth="auto"
                    ref="comboboxMauSo"
                    :enabled="false"
                    v-model="selectedMauSo"
                    :dataSource="danhSachMauSo"
                    :allowFiltering='true'
                    :fields="{ text: 'ten_loaihd', value: 'loaihoadon_id' }"
                    @filtering="onFilteringDropDownList($event, danhSachMauSo, 'ten_loaihd')"
                  ></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w70">Hệ thống</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    popupWidth="auto"
                    v-model="selectedHeThong"
                    :dataSource="danhSachHeThong"
                    :allowFiltering='true'
                    :fields="{ text: 'hethong', value: 'hethong_id' }"
                    @filtering="onFilteringDropDownList($event, danhSachHeThong, 'hethong')"
                    @change="selectedHeThongChange"
                  ></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w70">Sử dụng</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    :enabled="isEnableComboBoxSudung"
                    popupWidth="auto"
                    v-model="selectedSuDung"
                    :dataSource="danhSachSuDung"
                    :allowFiltering='true'
                    :fields="{ text: 'sudung', value: 'sudung_id' }"
                    @filtering="onFilteringDropDownList($event, danhSachSuDung, 'sudung')"
                  ></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>
      <div class="box-form grid-ext grid-ext-freeze">
        <div class="legend-title">Danh sách seri</div>
        <ejs-grid
          ref="tableSeri"
          :dataSource="danhSachSeri"
          :allowPaging="true"
          :pageSettings="pageSettings"
          :allowSorting="false"
          :allowFiltering="true"
          :allowSelection="true"
          :selectionSettings="selectionOptionsSingle"
          :selectedRowIndex="0"
          :pagerTemplate='pagerTemplate'
          gridLines="Both"
        >
          <e-columns>
            <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                      filterTemplate="<span></span>"
                      width="26" maxWidth="26" minWidth="26" textAlign="center"
                      :customAttributes="{class: 'column-selected-flag'}"
                      :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
            <e-column field="stt" headerText="STT" :filter="filterOptions" width="70"></e-column>
            <e-column field="seri" headerText="Seri" :filter="filterOptions"></e-column>
            <e-column field="tongso" headerText="Tổng số" :filter="filterOptions"></e-column>
            <e-column field="tuso" headerText="Từ số" :filter="filterOptions"></e-column>
            <e-column field="denso" headerText="Đến số" :filter="filterOptions"></e-column>
            <e-column field="sohientai" headerText="Số HĐ hiện tại" :filter="filterOptions"></e-column>
            <e-column field="soconlai" headerText="Số HĐ còn lại" :filter="filterOptions"></e-column>
            <e-column field="ngay_sd" headerText="Ngày SD" :filter="filterOptions" width="170"></e-column>
            <e-column field="trangthai" headerText="Trạng thái" :filter="filterOptions"></e-column>
            <e-column field="status" headerText="Status" :filter="filterOptions"></e-column>
            <e-column field="sudung" headerText="Sử dụng ĐHSX" :filter="filterOptions"></e-column>
            <e-column field="seri_id" headerText="Seri ID" :filter="filterOptions"></e-column>
          </e-columns>
        </ejs-grid>
      </div>
      <div >

      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import { pagingAndFilter } from '../../Staff/mixins/pagingAndFilter'

import {
  getDanhSachHeThong, getDanhSachMauSo, getDanhSachSeri, getDanhSachSeriPortal, getDanhSachSuDung, updateSeri
} from '../api/apiModalSeri'
import * as moment from 'moment'
const TRANG_THAI_SU_DUNG = {
  '1': 'Chưa sử dụng',
  '2': 'Đang sử dụng',
  '3': 'Không sử dụng'
}

export default {
  mixins: [pagingAndFilter],
  name: 'ModalSeri',
  components: {},
  props: {
    loaiHoaDonId: {
      type: Number,
      default: 0
    }
  },
  data: () => ({
    danhSachHeThong: [],
    selectedHeThong: null,
    danhSachMauSo: [],
    selectedMauSo: null,
    danhSachSuDung: [],
    selectedSuDung: null,
    danhSachSeri: [],
    isEnableComboBoxSudung: false,
    thongTu78: false
  }),
  methods: {
    show () {
      this.$refs.modalSeri.show()
    },
    async selectedHeThongChange () {
      this.loading(true)
      if (this.selectedHeThong === 1) {
        await this.loadDataPortal()
      } else {
        this.danhSachSeri = await getDanhSachSeri(this.axios, this.loaiHoaDonId).catch(err => {
          this.loading(false)
          this.$toast.error(err.data.message_detail)
        })
        if (this.danhSachSeri.length === 0) {
          this.$toast.error('Không có dữ liệu')
        }
        this.isEnableComboBoxSudung = true
      }
      this.loading(false)
    },

    async loadDataPortal () {
      this.isEnableComboBoxSudung = false
      const mauSo = this.danhSachMauSo.find(element => element.loaihoadon_id === this.loaiHoaDonId)
      if (!mauSo) {
        this.$toast.error('Không khớp mẫu số')
        this.danhSachSeri = []
        return
      }

      const response = await getDanhSachSeriPortal(this.axios, mauSo.ten_loaihd)


      if (response.data.faultString === 'success') {
        this.danhSachSeri = response.data.data.split(';').map(rawRow => {
          const rawSeri = rawRow.split('-')
          return {
            seri_id: '',
            stt: rawSeri[0],
            seri: rawSeri[1],
            tongso: rawSeri[2],
            tuso: rawSeri[3],
            denso: rawSeri[4],
            sohientai: rawSeri[5],
            soconlai: rawSeri[6],
            status: TRANG_THAI_SU_DUNG[rawSeri[8]] || '',
            sudung: '',
            ngay_sd: rawSeri[7],
            trangthai: rawSeri[8]
          }
        })
        if (this.danhSachSeri.length === 0) {
          this.$toast.error('Không có dữ liệu')
        }
      } else {
        this.$toast.error(response.data.faultString)
        this.danhSachSeri = []
      }
    },
    async openedModalSeri () {
      this.loading(true)
      // Tất cả các tỉnh đã dùng thông tư 78 nên mặc định = true
      // this.thongTu78 = await queryData(this.axios, {
      //   id_neo: "ten_ts",
      //   in_table: "admin.thamso_md",
      //   in_dk: "where ma_ts = 'HDDT_THONGTU_78'"
      // }) === '1'
      // const kieuMauSo = this.thongTu78 ? 4 : 2
      this.danhSachMauSo = await getDanhSachMauSo(this.axios, 4)

      if (this.danhSachMauSo && this.danhSachMauSo.length > 0) {
        this.selectedMauSo = this.loaiHoaDonId // this.danhSachMauSo[0].loaihoadon_id
      }

      this.danhSachSuDung = await getDanhSachSuDung(this.axios)

      if (this.danhSachSuDung && this.danhSachSuDung.length > 0) {
        this.selectedSuDung = this.danhSachSuDung[0].sudung_id
      }

      this.danhSachHeThong = await getDanhSachHeThong(this.axios)
      if (this.danhSachHeThong && this.danhSachHeThong.length > 0) {
        this.selectedHeThong = this.danhSachHeThong[0].hethong_id
      }
      this.loading(false)
    },

    async updateSeriClick () {
      const selectedRecord = this.$refs.tableSeri.getSelectedRecords()
      if (!selectedRecord || selectedRecord.length === 0) {
        return
      }

      let sudung = selectedRecord[0].trangthai === '1' || selectedRecord[0].trangthai === '2' ? 1 : 0

      if (this.selectedHeThong !== 1) {
        sudung = this.selectedSuDung
      }
      let ngay_ph = ''

      if (!selectedRecord[0].ngay_sd) {
        ngay_ph = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      } else {
        ngay_ph = moment(selectedRecord[0].ngay_sd, 'YYYY-MM-DD hh:mm:ss').format('DD/MM/YYYY HH:mm:ss')
      }

      const param = {
        den_so: selectedRecord[0].denso,
        hethong_id: this.selectedHeThong,
        loaihoadon_id: this.selectedMauSo,
        ngay_ph,
        seri_id: selectedRecord[0].seri_id,
        seri_name: selectedRecord[0].seri,
        soluong: selectedRecord[0].tongso,
        sudung,
        thutu: selectedRecord[0].stt,
        trangthai: selectedRecord[0].trangthai,
        tu_so: selectedRecord[0].tuso
      }
      this.loading(true)
      const resultSave = await updateSeri(this.axios, param).catch(err => {
        this.$toast.error(err.data.message_detail)
      }).finally(() => {
        this.loading(false)
      })
      if (resultSave.data.faultString && resultSave.data.faultString !== 'success') {
        this.$toast.error(resultSave.data.faultString)
      } else {
        this.$toast.success('Cập nhật dữ liệu thành công !')
      }
    }
  }
}
</script>
