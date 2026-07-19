<template>
  <div class="">
    <!-- action header -->
    <div class="list-action-top">
      <vue-bread-crumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <el-tooltip class="item" effect="light" content="Nhập mới" placement="bottom">
            <li>
              <a href="#" :class="{ disabled: disabledBtnNew }" @click.prevent="createNewThueBao"> <span class="icon one-file-plus"></span>Nhập mới </a>
            </li>
          </el-tooltip>
          <el-tooltip class="item" effect="light" content="Ghi lại" placement="bottom">
            <li>
              <a href="#" @click.prevent="saveThueBaoUuDai"> <span class="icon one-save"></span>Ghi lại </a>
            </li>
          </el-tooltip>
          <el-tooltip class="item" effect="light" content="Huỷ" placement="bottom">
            <li>
              <a href="#" :class="{ disabled: disabledBtnCancel }" @click.prevent="cancelAction"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ </a>
            </li>
          </el-tooltip>
          <el-tooltip class="item" effect="light" content="Excel mẫu" placement="bottom">
            <li>
              <a href="#" :class="{ disabled: disabledBtnExcelMau }" @click.prevent="exportExcelMau"> <span class="icon one-excel"></span>Excel mẫu </a>
            </li>
          </el-tooltip>
          <el-tooltip class="item" effect="light" content="Nhập Excel" placement="bottom">
            <li>
              <a href="#" :class="{ disabled: disabledBtnNhapExcel }" @click="$refs.file.click()" id="btnImportExcel">
                <span class="icon one-excel"></span>
                Nhập Excel
              </a>
            </li>
          </el-tooltip>
          <input ref="file" type="file" hidden @change="importExcel($event)" />
        </ul>
      </div>
    </div>
    <!-- content page -->
    <div class="page-content">
      <!-- thong tin uu dai -->
      <div class="information-thue-bao-uu-dai">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">
                <div class="title-main">Thông tin thuê bao</div>
                <!-- <div class="title-sub">Trạng thái dữ liệu:</div> -->
              </div>
              <div class="row">
                <div class="info-row col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Dịch vụ VT</div>
                    <div>
                      <SelectExt
                        :dataSource="dichVuVTList"
                        v-model="selectedDichVuVT"
                        :allowFiltering="true"
                        dataTextField="ten_dvvt"
                        dataValueField="dichvuvt_id"
                        @change="changeDV"
                      />
                    </div>
                  </div>
                </div>
                <div class="info-row col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại hình TB</div>
                    <div>
                      <SelectExt :dataSource="loaiHinhTBFilterByDichVuVT" v-model="selectedLoaiHinhTB" :allowFiltering="true" dataTextField="LOAIHINH_TB" dataValueField="LOAITB_ID" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key somay">Số máy/Acc <span class="required">*</span></div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click="btnSearchAccountClick">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <b-form-input class="so-may-acc" @keydown.enter.native="txtMaTB_KeyPress" v-model="valueSoMayAcc" ref="refSoMayAcc"></b-form-input>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="value">
                  <vue-input v-model="tenTB" :labelWidth="'100'" ref="refTenTB" label="Tên TB" :class="{ disableInput: !isCreate }"></vue-input>
                </div>
              </div>
              <div class="info-row">
                <div class="value">
                  <vue-input v-model="diaChiTB" :labelWidth="'100'" label="Địa chỉ TB" ref="refDiaChiTB" :class="{ disableInput: !isCreate }"></vue-input>
                </div>
              </div>
              <div class="info-row">
                <div class="value">
                  <vue-input :labelWidth="'100'" v-model="ghiChu" label="Ghi chú"></vue-input>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin ưu đãi</div>
              <div class="info-row value">
                <div class="select-custom">
                  <vue-select v-model="selectedCongVan" :labelWidth="'140'" :options="congVanList" labelField="NAME" valueField="ID" label="Công văn"></vue-select>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row value">
                    <div class="select-custom">
                      <vue-select :labelWidth="'140'" :options="nguonKMList" v-model="selectedNguonKM" labelField="NAME" valueField="ID" label="Nguồn KM"></vue-select>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Tháng BĐ</div>
                    <div class="value date-icon">
                      <date-picker style="width: 98%; margin-left: 4px" format="MM/YYYY" v-model="thangBĐUuDai"></date-picker>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <el-tooltip effect="light" content="Tiền hàng tháng khách hàng phải trả (cả VAT)" placement="bottom">
                    <div class="info-row">
                      <div class="key w140">Mức cước trả thêm</div>
                      <div class="value color-number-input">
                        <input type="text" class="form-control tright fw6 text-bold" v-model="mucCuocTraThem" @blur="formatCurrencyMucCuocTraThem" />
                      </div>
                    </div>
                  </el-tooltip>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Tháng KT</div>
                    <div class="value date-icon">
                      <date-picker style="width: 98%; margin-left: 4px" format="MM/YYYY" v-model="thangKTUuDai"></date-picker>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Giảm cước TB(%)</div>
                    <div class="value color-number-input">
                      <input type="text" class="form-control tright text-bold" v-model="giamCuocTB" @input="changeGiamCuocTB" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w85">Cước TB</div>
                    <div class="value color-number-input">
                      <input type="text" class="form-control tright text-bold" v-model="cuocTB" @blur="formatCurrencyCuocTB" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Giảm cước SD(%)</div>
                    <div class="value color-number-input">
                      <input type="text" class="form-control tright text-bold" v-model="giamCuocSD" @input="changeGiamCuocSD" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w85">Cước SD</div>
                    <div class="value color-number-input">
                      <input type="text" class="form-control tright text-bold" v-model="cuocSD" @blur="formatCurrencyCuocSD" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <dialog-search-account ref="dlgSearchAccount" :isDialog="true" :vma_tb="vma_tb" @on-select-customer="handleSelectTBFromAccountDialog" />
        </div>
      </div>
      <!-- thong tin tim kiem -->
      <thong-tin-tim-kiem></thong-tin-tim-kiem>
      <!-- danh sach thue bao -->
      <div class="list-thue-bao">
        <danh-sach-thue-bao @currentRecord="currentRecord" />
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import DatePicker from 'vue2-datepicker'
import XLSX from 'xlsx'
import { CURRENT_DATA_THUE_BAO_DEFAULT, DATA_TEST_IMPORT } from './constants'
import DialogSearchAccount from './SoMayAcc/DialogSearchAccount.vue'
// import { DATA_CURRENT, TrangThaiHD, DICHVU_VIENTHONG, LOAIHINH_THUEBAO, LoaiHopDong, LOAI_DONVI } from './constants'
import ThongTinTimKiem from './part/ThongTinTimKiem.vue'
import util from './helper/UtilsJS'
import api from './api'
import axios from '../../../../plugins/axiosPlugin'
import { exportExcel } from './helper/export-excel'
export default {
  name: 'ThueBaoUuDai',
  components: {
    DatePicker,
    DialogSearchAccount,
    DanhSachThueBao: () => import('./part/DanhSachThueBao.vue'),
    SearchAccount: () => import('./SearchAccount/SearchAccount.vue'),
    ThongTinTimKiem
  },
  data() {
    return {
      /* data header action */
      header: {
        title: 'BỔ SUNG THUÊ BAO ƯU ĐÃI PHỤC VỤ TÍNH CƯỚC',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      /* btn action disable */
      disabledBtnNew: false,
      disabledBtnCancel: false,
      disabledBtnExcelMau: false,
      disabledBtnNhapExcel: false,
      disabledBtnSearch: false,
      isCreate: false,
      isShowDialogTraCuu: false,
      /* input thong tin thue bao */
      selectedDichVuVT: null,
      selectedLoaiHinhTB: null,
      selectedCongVan: 0,
      selectedNguonKM: 0,
      valueSoMayAcc: '',
      thueBaoId: 0,
      tenTB: '',
      diaChiTB: '',
      ghiChu: '',
      thangBĐUuDai: '',
      thangKTUuDai: '',
      mucCuocTraThem: 0,
      giamCuocTB: 0,
      cuocTB: 0,
      giamCuocSD: 0,
      cuocSD: 0,
      /* thang bd & kt custom format YYYY/MM. Ex: 202102 */
      thangBD: 0,
      thangKT: 0,
      /* nhập excel */
      listImportExcel: [],
      listDonotImport: [],
      /* filter loai hình tb by dichvu_vt */
      loaiHinhTBFilterByDichVuVT: [],
      /* data current default */
      currentThueBao: CURRENT_DATA_THUE_BAO_DEFAULT,
      numberMucCuocTraThem: 0,
      numberCuocTB: 0,
      numberCuocSD: 0,
      //check IP máy, ten máy
      machine_cn: '',
      ip_cn: '',
      vma_tb: ''
    }
  },
  async created() {
    await this.initializeDichVuVTList()
    await this.initializeLoaiHinhTB()
    await this.initializeCongVanList()
    await this.initializeNguonKMList()
  },
  computed: {
    ...mapGetters('mapPreferentialSubscriber', ['dichVuVTList', 'loaiHinhTB', 'congVanList', 'nguonKMList', 'danhSachThueBaoUuDai', 'thueBaoItem', 'dsThongTinHopDongTB', 'soMayAccountTK']),
    root_nguoi_cn: function () {
      return this.$root.token.getUserName()
    }
  },
  methods: {
    ...mapActions('mapPreferentialSubscriber', [
      'initializeDichVuVTList',
      'initializeLoaiHinhTB',
      'initializeCongVanList',
      'initializeNguonKMList',
      'checkThueBaoUuDai',
      'saveThueBaoUuDaiNew',
      'updateThueBaoUuDai',
      'getDanhBaTheoMaThueBao',
      'getThongTinHopDong',
      'fetchDanhSachThueBaoUuDai',
      'setListDanhSachThueBao',
      'fetchHopDongTheoMaThueBao'
    ]),
    btnSearchAccountClick() {
      this.$refs.dlgSearchAccount.show(true)
    },
    /* chọn data từ danh sách thuê bao */
    currentRecord(data) {
      if (!data) return this.$toast.error('Không tìm thấy bản ghi nào! ')
      // console.log('HHHH', data)
      if (Object.keys(data).length !== 0 || data !== undefined) {
        this.currentThueBao = data
        this.selectedDichVuVT = data.dichvuvt_id
        this.loaiHinhTBFilterByDichVuVT = this.loaiHinhTB.filter((item) => item.DICHVUVT_ID === parseInt(this.selectedDichVuVT))
        this.selectedLoaiHinhTB = parseInt(data.loaitb_id)
        // this.selectedCongVan = data.congvan_id
        this.valueSoMayAcc = data.ma_tb
        this.tenTB = data.ten_tb
        this.diaChiTB = data.diachi_ld
        this.ghiChu = data.ghichu
        this.selectedCongVan = data.congvan_id
        this.selectedNguonKM = data.nguonkm_id
        this.thangBĐUuDai = util.momentFormatParse(data.thang_bdshow)
        this.thangKTUuDai = util.momentFormatParse(data.thang_ktshow)
        this.mucCuocTraThem = data.muccuoc
        this.giamCuocTB = data.tyle_ctb
        this.cuocTB = data.cuoc_tb
        this.giamCuocSD = data.tyle_csd
        this.cuocSD = data.cuoc_sd
        this.numberCuocTB = parseInt(data.cuoc_tb)
        this.numberCuocSD = parseInt(data.cuoc_sd)
        this.thueBaoId = data.thuebao_id
        // const currencyMucCuocTraThem = this.formatCurrency(this.mucCuocTraThem);
        const currencyMucCuocTraThem = this.mucCuocTraThem
        // this.numberMucCuocTraThem = parseInt(this.mucCuocTraThem);
        this.numberMucCuocTraThem = this.mucCuocTraThem
        this.mucCuocTraThem = currencyMucCuocTraThem
        // const currencyCuocTB = this.formatCurrency(this.cuocTB);
        const currencyCuocTB = this.cuocTB
        this.numberCuocTB = parseInt(this.cuocTB)
        this.cuocTB = currencyCuocTB
        // const currencyCuocSD = this.formatCurrency(this.cuocSD);
        const currencyCuocSD = this.cuocSD
        this.numberCuocSD = parseInt(this.cuocSD)
        this.cuocSD = currencyCuocSD
      }
      if (Object.keys(data).length === 0 || data === undefined) {
        console.log('HERE:', this.valueSoMayAcc)
        this.selectedLoaiHinhTB = 0
        this.selectedCongVan = 0
        this.tenTB = ''
        this.diaChiTB = ''
        this.ghiChu = ''
        this.selectedCongVan = 0
        this.selectedNguonKM = 0
        this.thangBĐUuDai = ''
        this.thangKTUuDai = ''
        this.mucCuocTraThem = 0
        this.giamCuocTB = 0
        this.cuocTB = 0
        this.giamCuocSD = 0
        this.cuocSD = 0
      }
    },
    /* format currency VND muc cuoc tb */
    formatCurrencyCuocTB() {
      const currency = util.formatCurrency(this.cuocTB)
      if (currency === 'NaN') {
        this.$toast.error('Cước thuê bao phải là số!')
        return
      }
      this.numberCuocTB = parseInt(this.cuocTB)
      this.cuocTB = currency
    },
    /* format currency VND muc cuoc tra them */
    formatCurrencyMucCuocTraThem() {
      const currency = util.formatCurrency(this.mucCuocTraThem)
      if (!currency) {
        this.$toast.error('Mức cước trả thêm phải là số!')
        return
      }
      this.numberMucCuocTraThem = parseInt(this.mucCuocTraThem)
      this.mucCuocTraThem = currency
    },
    /* format currency VND cuoc tb */
    formatCurrencyCuocSD() {
      const currency = util.formatCurrency(this.cuocSD)
      if (currency === 'NaN') {
        this.$toast.error('Cước SD phải là số!')
        return
      }
      this.numberCuocSD = parseInt(this.cuocSD)
      this.cuocSD = currency
    },
    /* filter loai hinh tb theo id của dịch vu vt */
    changeDV() {
      this.loaiHinhTBFilterByDichVuVT = this.loaiHinhTB.filter(
        item => item.DICHVUVT_ID === parseInt(this.selectedDichVuVT)
      );
      this.selectedLoaiHinhTB = this.loaiHinhTBFilterByDichVuVT[0].LOAITB_ID;
    },
    /* validate max % = 100 */
    changeGiamCuocTB() {
      if (Number(this.giamCuocTB) || this.giamCuocTB === '0' || this.giamCuocTB === '') {
        if (this.giamCuocTB > 100) {
          this.giamCuocTB = 0
        }
      } else {
        this.giamCuocTB = 0
      }
    },
    /* validate max % = 100 */
    changeGiamCuocSD() {
      if (Number(this.giamCuocSD) || this.giamCuocSD === '0' || this.giamCuocSD === '') {
        if (this.giamCuocSD > 100) {
          this.giamCuocSD = 0
        }
      } else {
        this.giamCuocSD = 0
      }
    },
    /* kiểm tra thuê bao đã tồn tại DB chưa và có trùng ngày ưu đãi không */
    async checkCanCreateThueBao() {
      this.thangBD = util.formatDateToOb(this.thangBĐUuDai).val
      this.thangKT = util.formatDateToOb(this.thangKTUuDai).val
      const variables = {
        thuebao_ud_id: 0,
        vma_tb: this.valueSoMayAcc.trim(),
        vloaitb_id: this.selectedLoaiHinhTB,
        vthang_bd: parseInt(this.thangBD),
        vthang_kt: parseInt(this.thangKT)
      }
      const res = await this.checkThueBaoUuDai(variables)
      if (parseInt(res) === 0) return true
      else return false
    },
    /* khi nhấn button nhập mới */
    createNewThueBao() {
      this.disabledBtnNew = true
      this.disabledBtnExcelMau = true
      this.disabledBtnNhapExcel = true
      this.disabledBtnSearch = true
      this.isCreate = true
      this.resetDataParams()
    },
    /* reset lại data input ở phần thông tin thuê bao */
    resetDataParams() {
      // this.selectedDichVuVT = this.dichVuVTList[0].dichvuvt_id;
      // this.selectedLoaiHinhTB = this.loaiHinhTBFilterByDichVuVT[0].LOAITB_ID;
      this.selectedCongVan = this.congVanList[0].ID
      this.selectedNguonKM = this.nguonKMList[0].ID
      this.valueSoMayAcc = ''
      this.thueBaoId = 0
      this.tenTB = ''
      this.diaChiTB = ''
      this.ghiChu = ''
      this.thangBĐUuDai = ''
      this.thangKTUuDai = ''
      this.mucCuocTraThem = 0
      this.giamCuocTB = 0
      this.cuocTB = 0
      this.giamCuocSD = 0
      this.cuocSD = 0
    },
    /* action khi nhấn nút Hủy */
    cancelAction() {
      this.disabledBtnNew = false
      this.disabledBtnExcelMau = false
      this.disabledBtnNhapExcel = false
      this.disabledBtnSearch = false
      this.isCreate = false
      this.resetDataParams()
      /* reset data cuoc */
      this.numberMucCuocTraThem = 0
      this.numberCuocTB = 0
      this.numberCuocSD = 0
    },
    /* action khi nhấn button export excel mẫu */
    exportExcelMau() {
      window.location.href = '/static/files/Mau_File_Import_ThueBaoUuDai.xlsx'
    },
    /* 1. import excel
    - Doc ngay BD, thang BD, congvan_id, nguonkm_id, tu giao dien
    - check Thue bao co ton tai trong danh ba hay ko : theo maTB + dich vu ID? => ko co push item vao mang loi > add ly do: Không tồn tại thuê bao trong danh bạ!
    - check thubao Id:
                }
    - kiem tra Dữ liệu bị trùng khoảng thời gian ưu đãi hay khong -> neu co push item vao mang loi -> add them ly do: Dữ liệu bị trùng khoảng thời gian ưu đãi!
    - Import data: show so luong item import duocj
    - show danh sach cac item bi loi-> chuyen ra file excel
    */
    async importExcel(event) {
      const file = event.target.files ? event.target.files[0] : null
      //check kiem tra ngay BD, ngay KT
      this.thangBD = util.formatDateToOb(this.thangBĐUuDai).val
      this.thangKT = util.formatDateToOb(this.thangKTUuDai).val
      if (file) {
        this.$root.loading(true)
        const reader = new FileReader()
        reader.onload = async (e) => {
          /* Parse data */
          const bstr = e.target.result
          const wb = XLSX.read(bstr, { type: 'binary' })
          /* Get first worksheet */
          const wsname = wb.SheetNames[0]
          const ws = wb.Sheets[wsname]
          /* Convert array of arrays */
          const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
          this.$root.loading(true)
          try {
            // check thiếu cột hay sai tên cột hay ko
            if (!this.checkColumnExcel(data[0])) {
              this.$root.loading(false)
              return this.$toast.error('Lỗi định dạng file excel. Vui lòng kiểm tra lại file excel!')
            }
            // check thue bao co ton tai
            else {
              await this.handleImportExcel(data)
              await this.refetchDataDanhSachThueBao()
            }
          } catch (e) {
            this.$root.loading(false)
            throw e
          }
          this.$root.loading(false)
        }
        reader.readAsBinaryString(file)
      } else {
        return
      }
    },
    /* 2. check xem đủ tên cột hay chưa */
    checkColumnExcel(dataNameTable) {
      if (dataNameTable) {
        return (
          dataNameTable.includes('MA_TB') &&
          dataNameTable.includes('LOAITB_ID') &&
          dataNameTable.includes('DICHVU') &&
          dataNameTable.includes('TYLE_CTB') &&
          dataNameTable.includes('CUOC_TB') &&
          dataNameTable.includes('TYLE_CSD') &&
          dataNameTable.includes('CUOC_SD') &&
          dataNameTable.includes('TYLE_SODEP') &&
          dataNameTable.includes('TIEN_SODEP') &&
          dataNameTable.includes('TYLE_CUOCIP') &&
          dataNameTable.includes('CUOC_IP') &&
          dataNameTable.includes('MUCCUOC')
        )
      }
      return false
    },
    checkDataExcel(item) {
      const checkIsNumber = (str) => {
        const number = parseInt(str)
        console.log(`${str} --- ${number} ===>`, typeof number === 'number');
        return !isNaN(number)
      }
      return (
        checkIsNumber(item['MUCCUOC']) &&
        checkIsNumber(item['TYLE_CUOCIP']) &&
        checkIsNumber(item['CUOC_TB']) &&
        checkIsNumber(item['TYLE_CSD']) &&
        checkIsNumber(item['CUOC_SD']) &&
        checkIsNumber(item['CUOC_IP']) &&
        checkIsNumber(item['TYLE_SODEP']) &&
        checkIsNumber(item['TIEN_SODEP']) &&
        checkIsNumber(item['TYLE_CTB'])
      )
    },
    /* 3. gắn key vào value map cho đúng thành array */
    async handleImportExcel(data) {
      // convert từ excel to json
      for (let index = 0; index < data.length - 1; index++) {
        let r = {}
        let keys = data[0]
        let values = data[index + 1]
        for (let i = 0; i < keys.length; i++) {
          r[keys[i]] = values[i]
        }
        await this.checkDataImportExcelThueBao(r)
        // this.listImportExcel.push(r)
      }
      if (this.listImportExcel.length > 0) {
        this.$toast.success(`Import thành công ${this.listImportExcel.length} bản ghi`)
      }
      if (this.listDonotImport.length > 0) {
        this.$root.loading(false)
        this.$bvModal
          .msgBoxConfirm(`Có item  ${this.listDonotImport.length} lỗi!, bạn có muốn export ra danh sách không?`, {
            title: 'Xác nhận hành động',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then( (v) => {
            if(v)
              exportExcel(this.listDonotImport, 'DanhSachThueBaoUuDaiImportKhongThanhCong')
            return v
          })
      }
    },
    /* 5. import từng row data excel 
    - Kiem tra xem có lỗi trùng ngày bd, kt hay ko
    - Neu khong loi se push vao mang de insert vao db
    - neu loi thi insert vao mang de show len thong bao
    */
    async checkDataImportExcelThueBao(rowData) {
      if(!this.checkDataExcel(rowData)){
          rowData['LY_DO'] = 'Lỗi định dạng dữ liệu '
         return this.listDonotImport.push(rowData);
      }
      const maTB = rowData['MA_TB']
      const loaihinhTBId = rowData['LOAITB_ID']
      const loaiHinhTBName = rowData['DICHVU']
      let tb = this.loaiHinhTB.find((t) => {
        return (t.LOAITB_ID == loaihinhTBId) & (t.LOAIHINH_TB.toLowerCase() == loaiHinhTBName.toLowerCase())
      })
     if(!tb){
      tb = this.loaiHinhTB.find((t) => {
        return (t.LOAITB_ID == loaihinhTBId)
      })
     }
      const resCheckExits = await api.traCuuDanhBaTheoMaTB(axios, {
        maTb: maTB,
        dichVuVtId: tb['DICHVUVT_ID'] ? tb['DICHVUVT_ID'] : 1
      })
      if (resCheckExits.data && resCheckExits.data.data) {
        rowData['THUEBAO_ID'] = resCheckExits.data.data['thuebao_id']
        rowData['DICHVUVT_ID'] = tb['DICHVUVT_ID']
      } else {
        rowData['LY_DO'] = 'Không tồn tại thuê bao trong danh bạ!'
        return this.listDonotImport.push(rowData)
      }
      const resCheckCreate = await this.checkThueBaoUuDai({
        // thuebao_ud_id: 0,
        vma_tb: maTB,
        vloaitb_id: loaihinhTBId,
        vthang_bd: this.thangBD,
        vthang_kt: this.thangKT
      })
      //neu ko co loi thi convert item de insert
      if (parseInt(resCheckCreate) === 0) {
        try {
          await this.handleInsertRowExcel2DB(rowData)
          // this.listImportExcel.push(rowData)
        } catch (e) {
          rowData['LY_DO'] = `Lỗi dữ liệu nhập! ${e.toString()}`
          delete rowData['DICHVUVT_ID']
          delete rowData['THUEBAO_ID']
          this.listDonotImport.push(rowData)
          console.log(e)
          // throw(e)
        }
      } else {
        rowData['LY_DO'] = 'Dữ liệu bị trùng khoảng thời gian ưu đãi!'
        delete rowData['DICHVUVT_ID']
        delete rowData['THUEBAO_ID']
        return this.listDonotImport.push(rowData)
      }
    },
    /* 5. import item to database ==> call api update */
    async handleInsertRowExcel2DB(item) {
      const variablesNew = {
        thuebao_id: item['THUEBAO_ID'],
        ma_tb: item['MA_TB'],
        dichvuvt_id: item['DICHVUVT_ID'],
        loaitb_id: item['LOAITB_ID'],
        thang_bd: parseInt(this.thangBD),
        thang_kt: parseInt(this.thangKT),
        congvan_id: parseInt(this.selectedCongVan),
        ghichu: this.ghiChu,
        nguonkm_id: parseInt(this.selectedNguonKM),
        muccuoc: parseInt(item['MUCCUOC']),
        tyle_cuocip: parseInt(item['TYLE_CUOCIP']),
        cuoc_tb: parseInt(item['CUOC_TB']),
        tyle_csd: parseInt(item['TYLE_CSD']),
        cuoc_sd: parseInt(item['CUOC_SD']),
        tinh_dt: 1,
        tyle_ctb: parseInt(item['TYLE_CTB']),
        tyle_sodep: parseInt(item['TYLE_SODEP']),
        tien_sodep: parseInt(item['TIEN_SODEP']),
        cuoc_ip: parseInt(item['CUOC_IP'])
      }
      try {
        await this.saveThueBaoUuDaiNew(variablesNew)
        //day du lieu vao mang
        this.listImportExcel.push(variablesNew)
      } catch (error) {
        console.log(error)
        delete item['DICHVUVT_ID']
        delete item['THUEBAO_ID']
        item['LY_DO'] = 'Lỗi dữ liệu nhập: ' + error.toString()
        this.listDonotImport.push(item)
        // lo
        // throw(error)
      }
    },
    /* khi nhập Số máy Acc nhấn enter để generate tên & địa chỉ thuê bao */
    async txtMaTB_KeyPress() {
      try {
        if (!this.valueSoMayAcc) {
          this.$toast.error('Bạn chưa nhập số máy. Vui Lòng nhập số máy!')
          this.$refs.refSoMayAcc.focus()
          return false
        }
        const param = { maTb: this.valueSoMayAcc, dichVuVtId: this.selectedDichVuVT ? this.selectedDichVuVT : 13 }
        this.$root.loading(true)
        await this.fetchHopDongTheoMaThueBao(param)
        // console.log('danhSachThueBaoUuDai:', this.danhSachAccount.length === 0, this.valueSoMayAcc)
        if (!this.soMayAccountTK) {
          // console.log("sdkbvchsdj");
          const nameDV = this.dichVuVTList.find((item) => item.dichvuvt_id === parseInt(this.selectedDichVuVT))
          // console.log('nameDV:', nameDV)
          this.$toast.error(`Không tìm thấy thông tin thuê bao ${this.valueSoMayAcc.trim()} đối với dịch vụ ${nameDV.ten_dvvt}!`)
        } else {
          this.tenTB = this.soMayAccountTK['ten_kh']
          this.diaChiTB = this.soMayAccountTK['diachi_kh']
          this.ghiChu = this.soMayAccountTK['ghichu']
          this.selectedLoaiHinhTB = parseInt(this.soMayAccountTK['loaitb_id'])
          this.thueBaoId = this.soMayAccountTK['thuebao_id']
        }
      } catch (e) {
        this.$root.loading(false)
        this.$toast.error(`Không tìm thấy thông tin thuê bao ${this.valueSoMayAcc.trim()} `)
        throw e
      }
      this.$root.loading(false)
    },
    /* validate input của thông tin thuê bao */
    isValidForm() {
      if (!this.selectedDichVuVT) {
        this.$toast.error('Dịch vụ VT không được bỏ trống!')
        return false
      }
      if (!this.selectedLoaiHinhTB) {
        this.$toast.error('Loại hình TB không được bỏ trống!')
        return false
      }
      if (!this.valueSoMayAcc) {
        this.$toast.error('Số máy thuê bao không được bỏ trống!')
        this.$refs.refSoMayAcc.$el.focus()
        return false
      }
      if (!this.tenTB) {
        this.$toast.error('Tên thuê bao không được bỏ trống!')
        this.$refs.refTenTB.$el.focus()
        return false
      }
      if (!this.diaChiTB) {
        this.$toast.error('Địa chỉ thuê bao không được bỏ trống!')
        this.$refs.refDiaChiTB.$el.focus()
        return false
      }
      if (!this.selectedCongVan) {
        this.$toast.error('Công văn không được bỏ trống!')
        return false
      }
      if (!this.selectedNguonKM) {
        this.$toast.error('Nguồn KM không được bỏ trống!')
        return false
      }
      if (!this.thangBĐUuDai) {
        this.$toast.error('Tháng BĐ không được bỏ trống!')
        return false
      }
      if (!this.thangKTUuDai) {
        this.$toast.error('Tháng KT không được bỏ trống!')
        return false
      }
      //validate tháng ưu đãi
      if (this.thangBĐUuDai && this.thangKTUuDai) {
        const dateBD = util.formatDateToOb(this.thangBĐUuDai)
        const currentTime = util.formatDateToOb(new Date())
        const dateKT = util.formatDateToOb(this.thangKTUuDai)
        if (!dateBD) {
          this.$toast.error('Kiểm tra lại tháng bắt đầu!')
          return false
        }
        if (!dateKT) {
          this.$toast.error('Kiểm tra lại tháng kết thúc!')
          return false
        }
        if (parseInt(dateKT.val) < parseInt(dateBD.val)) {
          this.$toast.error('Tháng kết thúc không được nhỏ hơn tháng bắt đầu!')
          return false
        }
        if (dateBD && parseInt(currentTime.val) > parseInt(dateBD.val)) {
          this.$toast.error('Tháng bắt đầu không được nhỏ hơn tháng hiện tại!')
          return false
        }
      }
      return true
    },
    /* fetch lại data table khi create or update thuê bao */
    async refetchDataDanhSachThueBao() {
      const variables = {
        nguoi_cn: this.root_nguoi_cn.trim(),
        vdichvuvt_id: 0,
        vkieu: 0,
        vloaitb_id: 0,
        vmatb: '',
        vthang_bd: '',
        vthang_kt: ''
      }
      await this.fetchDanhSachThueBaoUuDai(variables)
    },
    /* lưu thông tin tạo mới hoặc update thuê bao */
    async saveThueBaoUuDai() {
      if (!this.isValidForm()) {
        return
      }
      this.$root.loading(true)
      /* check thue bao exist */
      const isCanCreate = await this.checkCanCreateThueBao()
      /* create new thue bao */
      if (!isCanCreate && this.isCreate) {
        this.$root.loading(false)
        this.$toast.error('Dữ liệu bị trùng khoảng thời gian ưu đãi!')
        return;
      }
      if (isCanCreate && this.isCreate) {
        /* param */
        const variablesNew = {
          thuebao_id: this.thueBaoId,
          ma_tb: this.valueSoMayAcc.trim(),
          ten_tb: this.tenTB,
          dichvuvt_id: this.selectedDichVuVT,
          loaitb_id: this.selectedLoaiHinhTB,
          thang_bd: parseInt(this.thangBD),
          thang_kt: parseInt(this.thangKT),
          congvan_id: parseInt(this.selectedCongVan),
          ghichu: this.ghiChu,
          nguonkm_id: parseInt(this.selectedNguonKM),
          muccuoc: this.numberMucCuocTraThem,
          
          cuoc_tb: this.numberCuocTB?this.numberCuocTB:0,
          tyle_csd: parseInt(this.giamCuocSD),
          cuoc_sd: this.numberCuocSD?this.numberCuocSD:0,
          tinh_dt: 1,
          tyle_ctb: parseInt(this.giamCuocTB),
          tyle_cuocip: 0,
          tyle_sodep: 0,
          tien_sodep: 0,
          cuoc_ip: 0
        }
        try {
         const response = await api.saveThueBaoUuDai(this.axios, variablesNew);
         const { error_code } = response.data
         this.$root.loading(false)
          if (error_code === 'BSS-00000000') {
            this.$toast.success('Tạo dữ liệu thành công!', 'title')
          } else {
            this.$toast.error(`Có lỗi xaỷ ra: ${response.data.message}`, 'title')
          }
        //  const ress =  await this.saveThueBaoUuDaiNew(variablesNew)
          // this.$root.loading(false)
          await this.refetchDataDanhSachThueBao()
          this.isCreate = false
          /* able button action */
          this.disabledBtnNew = false
          this.disabledBtnExcelMau = false
          this.disabledBtnNhapExcel = false
          this.disabledBtnSearch = false
          /* reset data cuoc */
          this.numberMucCuocTraThem = 0
          this.numberCuocTB = 0
          this.numberCuocSD = 0
        } catch (error) {
          this.$root.loading(false)
          this.$toast.error('Tạo dữ liệu bị lỗi!')
        }
      } else {
        /* update thue bao da ton tai */
        ///Kiểm tra xem có đúng user không, phải đúng user mới được thay đổi thông tin
        if (this.root_nguoi_cn != this.currentThueBao.nguoi_cn) {
          this.$root.loading(false)
          return this.$toast.error('Bạn không được thay đổi thông tin do user ' + this.currentThueBao.nguoi_cn + ' cập nhật!')
          // Message_Box.ShowWarning("Bạn không được thay đổi thông tin do user " + in_user_cn + " cập nhật");
        }
        const variablesUpdate = {
          js_data: [
            {
              THUEBAO_UD_ID: parseInt(this.currentThueBao.thuebao_ud_id),
              THUEBAO_ID: parseInt(this.currentThueBao.thuebao_id),
              RKM_ID: parseInt(this.currentThueBao.rkm_id),
              MA_TB: this.currentThueBao.ma_tb,
              DICHVUVT_ID: parseInt(this.selectedDichVuVT),
              LOAITB_ID: parseInt(this.selectedLoaiHinhTB),
              THANG_BD: parseInt(this.thangBD),
              THANG_KT: parseInt(this.thangKT),
              TYLE_CTB: parseInt(this.giamCuocTB),
              CUOC_TB: parseInt(this.numberCuocTB),
              TYLE_CSD: parseInt(this.giamCuocSD),
              CUOC_SD: parseInt(this.numberCuocSD),
              MUCCUOC: parseInt(this.numberMucCuocTraThem),
              TYLE_CUOCIP: parseInt(this.currentThueBao.tyle_cuocip),
              TYLE_SODEP: parseInt(this.currentThueBao.tyle_sodep),
              TIEN_SODEP: parseInt(this.currentThueBao.tien_sodep),
              CUOC_IP: parseInt(this.currentThueBao.cuoc_ip),
              CONGVAN_ID: parseInt(this.selectedCongVan),
              GHICHU: this.ghiChu ? this.ghiChu : '',
              MAY_CN: this.currentThueBao.may_cn,
              NGUOI_CN: this.root_nguoi_cn, // Fix nguoi_cn dang login
              NGUONKM_ID: parseInt(this.selectedNguonKM),
              TINH_DT: 1
            }
          ]
        }
        try {
          await this.updateThueBaoUuDai(variablesUpdate)
          this.$toast.success('Cập nhật dữ liệu thành công!')
          await this.refetchDataDanhSachThueBao()
          /* reset data cuoc */
          this.numberMucCuocTraThem = 0
          this.numberCuocTB = 0
          this.numberCuocSD = 0
        } catch (error) {
          this.$root.loading(false)
          this.$toast.error('Cập nhật dữ liệu bị lỗi!')
        }
      }
      this.$root.loading(false)
    },
    /**
     * Chon item khi select danh sach popup so may
     * thueBaoId: 0,
      tenTB: '',
      diaChiTB
     */
    handleSelectTBFromAccountDialog(ac) {
      if (!ac) {
        //show loi
        return
      }
      if (ac) {
        this.valueSoMayAcc = ac.ma_tb
        this.thueBaoId = ac['thuebao']['thuebao_id']
        this.tenTB = ac['thuebao']['ten_tb']
        this.diaChiTB = ac['thuebao']['diachi_tb']
        this.selectedLoaiHinhTB = parseInt(this.soMayAccountTK['loaitb_id'])
      }
    }
  }
}
</script>
<style scoped>
.legend-title {
  display: flex;
  justify-content: space-between;
}
.title-sub,
.color-number-input input {
  color: #d32f2f !important;
}
/deep/ .modal-dialog {
  max-width: 35vw;
}
.so-may-acc {
  background-color: #fff9eb;
}
.item-radio {
  margin-bottom: 20px;
}
.disableInput {
  cursor: pointer;
  pointer-events: none;
}
.date-icon {
  position: relative;
}
.icon-date {
  position: absolute;
  right: 16px;
  transform: translateY(-150%);
}
.icon-date i {
  font-size: 16px;
}
.text-bold {
  font-weight: 600 !important;
}
.somay {
  width: 105px;
}
</style>