<style scoped src="./frmPhanAnhBH.scss"></style>
<template>
  <div>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings"
                ref="frmPhanAnhBH" :position="position" :close="closeDialog" :header="'Phản ánh báo hỏng'"
                showCloseIcon="true" width="80%" :target="targetDialog">
      <div id="frmPhanAnhBHDiv">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a v-bind:class="{ disabled: !tsbNhapMoi_Enabled }" href="#" @click.prevent="tsbNhapMoi_Click"> <span
                class="icon ui-1_circle-remove nc-icon-glyph"></span> Nhập mới</a>
            </li>
            <li>
              <a v-bind:class="{ disabled: !tsbCapNhat_Enabled }" href="#" @click.prevent="tsbCapNhat_Click"> <span
                class="icon ui-1_circle-remove nc-icon-glyph"></span> Cập nhật</a>
            </li>
            <li>
              <a v-bind:class="{ disabled: !tsbClear_Enabled }" href="#" @click.prevent="tsbClear_Click"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Xóa</a>
            </li>
            <li>
              <a href="#" v-show="btnGuiSMS" @click.prevent="btnGuiSMS_Click"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Gửi SMS</a>
            </li>
            <li>
              <a href="#" v-show="tsbtnHenKH" @click.prevent="tsbtnHenKH_Click"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Hẹn KH</a>
            </li>
            <li>
              <a href="#" @click.prevent="tsbtnTraCuu_Click"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Tra cứu</a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Trạm VT</div>
                  <div class="value">
                    <input type="text" v-model="txtTramVT" class="form-control fw6" readonly="readonly" />
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key">Người PA</div>
                      <div class="value">
                        <input type="text" ref="reftxtNguoiPA" v-model="txtNguoiPA" class="form-control fw6" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key">Ngày phản ánh</div>
                      <div class="value">
                        <date-picker :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="dtpNgayPA" :type="typeFormat" :disabled-date="disabledRange">
                          <template #icon-calendar>
                            <span class="icon one-calendar"></span>
                          </template>
                        </date-picker>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Điện thoại</div>
                  <div class="value">
                    <input type="text" v-model="txtDienThoaiLH" class="form-control fw6" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key">Nội dung</div>
                  <div class="value">
                    <input type="text" v-model="txtNoiDung" ref="reftxtNoiDung" class="form-control fw6" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="row">
                  <DataGrid v-bind:columns="grdDanhSach.headers" :enabledSelectFirstRow="true" v-bind:dataSource="grdDanhSach.data" :showColumnCheckbox="false" :pageSize="5" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
                </div>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="row">
              <DataGrid
                v-bind:columns="gridviewDanhSach.headers"
                @selectedItemsChanged="gridviewDanhSach_selectedItemsChanged"
                @rowClicked="gridviewDanhSach_FocusedRowChanged"
                :selectionSettings="selectionOptions"
                :enabledSelectFirstRow="true"
                v-bind:dataSource="gridviewDanhSach.data"
                :showColumnCheckbox="true"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
              >
              </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </ejs-dialog>
    <frmNhacViecBH ref="reffrmNhacViecBH2" :params="frmNhacViecBHInput"></frmNhacViecBH>
    <frmHenKH ref="reffrmHenKH2" :params="frmHenKHInput" @closeHenKH="closeHenKH"></frmHenKH>
    <frmHenKHV2 ref="reffrmHenKHV22" :params="frmHenKHV2Input" @closeHenKH="closeHenKH"></frmHenKHV2>
    <ejs-dialog :enableResize="true" :visible="false" ref="reffrmTraCuuPhieuKN2" :position="position" :animationSettings="animationSettings" :header="'Tra cứu chi tiết xử lý khiếu nại'" showCloseIcon="true" target="#app .main-wrapper" width="99%">
      <div class="popupKN">
        <frmTraCuuPhieuKN ref="dialogObjTraCuuPhieuKN2"></frmTraCuuPhieuKN>
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import api from '../Api'
import frmApi from './PopUpApi.js'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import frmNhacViecBH from './frmNhacViecBH.vue'
import frmHenKH from './frmHenKH.vue'
import frmHenKHV2 from './frmHenKH_V2.vue'
import frmTraCuuPhieuKN from '@/modules/complaint/SearchComplainDetail/index.vue'

export default {
  name: 'frmPhanAnhBH',
  components: { moment, DatePicker, api, frmApi, ExportDataModal, frmNhacViecBH, frmHenKH, frmHenKHV2, frmTraCuuPhieuKN },
  props: {
    params: {
      type: Object,
      default: function () {
        return null
      }
    },
    targetDialog: {
      type: String,
      default: function () {
        return '#app .main-wrapper'
      }
    }
  },
  data() {
    return {
      tsbNhapMoi_Enabled: true,
      tsbCapNhat_Enabled: false,
      tsbClear_Enabled: true,
      tsbtnHenKH_Enable: true,
      ttkd_hen: 0,
      KIEMTRA_QUYEN_NHAP_KHGOI: -1,
      XACMINH_HEN_BAOHONG: -1,
      Quyen_Duoc_hen: 0,
      PHANANH_BH_HIEN_NV_KT: -1,
      vbaohong_id_t: 0,
      thuebao_id: 0,
      ma_tb: '',
      panelDataHeight: '200',
      tg: '',
      /// <summary>
      /// 1: TTVT nhắc; 2: TTKD nhắc
      /// </summary>
      kieu_nhac: 0,
      nguoi_bh: '',
      dienthoai_lh: '',
      noidung_bh: '',
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      typeFormat: 'datetime',
      target: '#app .main-wrapper', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      txtTramVT: '',
      txtNguoiPA: '',
      dtpNgayPA: '',
      txtDienThoaiLH: '',
      txtNoiDung: '',
      frmNhacViecBHInput: {},
      frmHenKHInput: {},
      frmHenKHV2Input: {},
      selectionOptions: {
        checkboxMode: 'ResetOnRowClick',
        enableToggle: true
      },
      grdDanhSach: {
        headers1: [
          {
            fieldName: 'ma_nv',
            headerText: 'Mã nhân viên',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_nv',
            headerText: 'Tên nhân viên',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'so_dt',
            headerText: 'Số điện thoại',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhiemvu',
            headerText: 'Nhiệm vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        headers: [
          {
            fieldName: 'khuvuc',
            headerText: 'Khu vực',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_kv',
            headerText: 'Mã khu vực',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_nv',
            headerText: 'Mã nhân viên',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhiemvu',
            headerText: 'Nhiệm vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'so_dt',
            headerText: 'Số điện thoại',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: []
      },
      gridviewDanhSach: {
        headers: [
          {
            fieldName: 'nguoi_pa',
            headerText: 'Người Gọi',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_cn',
            headerText: 'Ngày Gọi',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'noidung',
            headerText: 'Nội Dung',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Tên Đơn Vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguoi_cn',
            headerText: 'Người CN',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      result: false,
      tsbtnHenKH: true,
      btnGuiSMS: true,
    }
  },
  watch: {
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  computed: {
    grdDanhSach_headers: function () {
      if (this.grdDanhSach.data != null && this.grdDanhSach.data.length > 0) {
        let item = this.grdDanhSach.data[0]
        let keys = Object.keys(item)
        // console.log('grdDanhSach_headers', keys)
        if (keys != null && keys.length > 0) {
          if (keys.filter((x) => x == 'khuvuc')) return this.grdDanhSach.headers
          else return this.grdDanhSach.headers1
        }
      }
      if (this.PHANANH_BH_HIEN_NV_KT == 1) {
        return this.grdDanhSach.headers1
      }
      return this.grdDanhSach.headers
    }
  },
  methods: {
    openDialog() {
      this.getDataFromProp()
      this.Loading = true
      Promise.all([this.frmPhanAnhBH_Load()])
        .then((r) => {
          console.log(r)
        })
        .catch((err) => {
          console.log(err)
        })
        .finally(() => {
          this.Loading = false
          this.$refs.frmPhanAnhBH.show()
        })
    },
    closeDialog() {
      this.$emit('closefrmPhanAnhBH', this.result)
    },
    isNumber: function (evt) {
      evt = evt ? evt : window.event
      var charCode = evt.which ? evt.which : evt.keyCode
      if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode !== 46) {
        evt.preventDefault()
      } else {
        return true
      }
    },
    async tsbNhapMoi_Click() {
      this.tg = 'PHANANH_BH'
      this.tsbCapNhat_Enabled = true
      this.tsbNhapMoi_Enabled = false
    },
    async tsbCapNhat_Click() {
      try {
        if (this.txtNoiDung == '') {
          this.ShowError(`Chưa nhập nội dung phản ánh!`)
          this.$refs.reftxtNoiDung.focus()
          return
        }
        this.Loading = true
        await this.sp_capnhat_phananh_bh()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbClear_Click() {
      if (this.gridviewDanhSach.selectedItems == null || this.gridviewDanhSach.selectedItem.length == 0) {
        this.ShowError(`Bạn chưa chọn danh sách cần xóa`)
        return
      }
      this.$bvModal
        .msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          let dsphananh_id = this.gridviewDanhSach.selectedItems.map((x) => x.phananh_id).join()
          let response = await this.xoa_phananh_bh_theo_id(dsphananh_id)
          if (response) await this.LOAD_GRID_PHANANH()
        })
    },
    async map_phieuid(baohong_id) {
      let input = { id_neo: 'phieu_id', in_table: 'baohong.giaophieu', in_dk: `where baohong_id= ${baohong_id}  and ttph_id=1 and rownum=1` }
      let data = this.GetData(await api.map_id(this.axios, input))
      return data
    },
    async btnGuiSMS_Click() {
      //       frmNhacViecBH frm = new frmNhacViecBH();
      //  frm.loaidv_nhan_id = "0";
      //                 frm.baohong_id = Convert.ToInt64(vbaohong_id_t);
      //                 frm.kieugoi_id = 3;
      //                 frm.phieu_id = Convert.ToInt64(dt.Rows[0][0].ToString());
      //                 frm.thuebao_id = thuebao_id;
      //                 if (kieu_nhac != 0)
      //                     frm.kieu_nhac = kieu_nhac;
      //                 frm.ma_tb = ma_tb;
      //                 frm.ShowDialog();
      try {
        let phieu_id1 = await this.map_phieuid(this.vbaohong_id_t)
        if (phieu_id1 == -1 || phieu_id1 <= 0) return
        this.frmNhacViecBHInput.loaidv_nhan_id = '0'
        this.frmNhacViecBHInput.baohong_id = this.vbaohong_id_t
        this.frmNhacViecBHInput.kieugoi_id = 3
        this.frmNhacViecBHInput.phieu_id = phieu_id1
        this.frmNhacViecBHInput.thuebao_id = this.thuebao_id
        if (this.kieu_nhac != 0) this.frmNhacViecBHInput.kieu_nhac = this.kieu_nhac
        this.frmNhacViecBHInput.ma_tb = this.ma_tb
        this.$refs.reffrmNhacViecBH2.openDialog()
      } catch (er) {
        this.ShowError(`${err.message}`)
      }
    },
    async tsbtnHenKH_Click() {
      try {
        if (this.XACMINH_HEN_BAOHONG == -1) {
          this.frmHenKHInput.baohong_id = this.vbaohong_id_t
          this.frmHenKHInput.phieu_id = 0
          this.frmHenKHInput.Quyen_Duoc_hen = this.Quyen_Duoc_hen
          console.log('tsbtnHenKH_Click', 'frmHenKH2', this.frmHenKHInput)
          this.$refs.reffrmHenKH2.openDialog()
        } else {
          this.frmHenKHV2Input.baohong_id = this.vbaohong_id_t
          this.frmHenKHV2Input.phieu_id = 0
          this.frmHenKHV2Input.Quyen_Duoc_hen = this.Quyen_Duoc_hen
          console.log('tsbtnHenKH_Click', 'frmHenKH2_V2', this.frmHenKHV2Input)
          this.$refs.reffrmHenKHV22.openDialog()
        }
      } catch (ex) {
        this.ShowError(ex.message)
      }
    },
    async tsbtnTraCuu_Click() {
      try {
        // this.$refs.dialogObjTraCuuPhieuKN.frmTraCuuPhieuKN(this.txtMaTB)
        // this.$refs.dialogObjTraCuuPhieuKN.kieu_frm = 1
        // this.$refs.dialogObjTraCuuPhieuKN.txtMaTB_KeyUp()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.$refs.reffrmTraCuuPhieuKN2.show()
      }
    },
    getDataFromProp() {
      if (this.params != null) {
        let keys = Object.keys(this.params)
        for (const k of keys) {
          console.log('getDataFromProp:', k, ',value:', this.params[k])
          if (this.params[k] != null && this.params[k] != undefined) this[k] = this.params[k]
        }
      }
    },
    async frmPhanAnhBH_Load() {
      console.log('frmPhanAnhBH_Load')
      let ds_kt1 = await this.lay_ds_thamso_macdinh(0) //new ThamSo_MDFacade().LAY_DS_THAMSO_MD();
      console.log('ds_kt1', ds_kt1)
      if (ds_kt1 != null && ds_kt1.length > 0) {
        let str_vtemp = 'KIEMTRA_QUYEN_NHAP_KHGOI'
        let det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)
        //       ds_kt1.Tables[0].AsEnumerable()
        //                               .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();

        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'] == '1') this.KIEMTRA_QUYEN_NHAP_KHGOI = 1
        }

        str_vtemp = 'XACMINH_HEN_BAOHONG'
        det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)
        //       det = ds_kt1.Tables[0].AsEnumerable()
        //                               .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();

        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'] == '1') this.XACMINH_HEN_BAOHONG = 1
        }

        str_vtemp = 'PHANANH_BH_HIEN_NV_KT'
        det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)
        //       det = ds_kt1.Tables[0].AsEnumerable()
        //                               .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();

        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'] == '1') this.PHANANH_BH_HIEN_NV_KT = 1
        }
      }

      this.tsbtnHenKH = true
      let donvi_id = this.$root.token.getDonViID()
      let ds = await this.lay_ds_thamso_macdinh_dv() // new ThamSo_MDFacade().LAY_DS_THAMSO_MACDINH_DV(tt_nd.donvi_id, 4);
      let str_vtemp2 = 'TTKD_CAPNHAT_HEN'
      let det2 = ds.filter((x) => x.ma_ts == str_vtemp2 && x.donvi_id == donvi_id)
      //   let det2 = ds.Tables[0].AsEnumerable()
      //                           .Where(x => x["ma_ts"].ToString() == str_vtemp2 && x["donvi_id"].ToString() == tt_nd.donvi_id.ToString()).ToList();
      if (det2 != null && det2.length > 0) {
        let row = det2[0]
        if (row['ten_ts'] == '1') {
          if (this.ttkd_hen == 2) this.Quyen_Duoc_hen = 2
          else this.Quyen_Duoc_hen = 1
        }
      }

      if (this.vbaohong_id_t != '') {
        //         let strBH = 'select nguoi_bh,dienthoai_lh, ghichu_hong from ' + DatabaseConstants.DB7 + '.baohong where baohong_id=' + vbaohong_id_t
        //         let dsBH = bangts.GetDataSql(strBH)
        let dsBH = await this.lay_baohong(this.vbaohong_id_t)
        if (dsBH != null && dsBH.length > 0) {
          this.txtNguoiPA = dsBH[0]['nguoi_bh']
          this.txtDienThoaiLH = dsBH[0]['dienthoai_lh']
          this.txtNoiDung = dsBH[0]['ghichu_hong']
        }
      }
      await this.LOAD_GRID_PHANANH()
      this.$refs.reftxtNguoiPA.focus()
    },
    async LOAD_GRID_PHANANH() {
      let dts = await this.lay_ds_phananh()
      if (dts != null && dts.length > 0) {
        this.txtNguoiPA = dts[0]['nguoi_pa'] // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "NGUOI_PA").ToString();
        this.txtDienThoaiLH = dts[0]['so_dt'] // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "SO_DT").ToString();
        this.txtNoiDung = dts[0]['noidung'] // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "NOIDUNG").ToString();
        this.dtpNgayPA = dts[0]['ngay_cn'] // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "NGAY_CN").ToString();
      }
      let dsTTVT = await this.lay_tram_vt_baohong(this.vbaohong_id_t)
      if (dsTTVT != null && dsTTVT.length > 0) {
        this.txtTramVT = dsTTVT[0]['ten_dv']
      }
      this.gridviewDanhSach.data = dts
      console.log('PHANANH_BH_HIEN_NV_KT', this.PHANANH_BH_HIEN_NV_KT)
      // this.PHANANH_BH_HIEN_NV_KT=1
      if (this.PHANANH_BH_HIEN_NV_KT == 1) {
        // {"ten_dv":"Tổ Kỹ thuật Hải An","ma_nv":"HPG000921","ten_nv":"Lê Thanh Phương","so_dt":"0947344469","nhiemvu":"Kiểm tra và xử lý"}
        this.grdDanhSach.data = await this.lay_ds_nhanvienth_bh()
        //         grdDanhSach.DataSource = new BaoHong_Facade().Lay_ds_nhanvienth_bh(Convert.ToInt64(vbaohong_id_t)).Tables[0]
        //         grdViewDanhSach.BestFitColumns()
      } else {
        this.grdDanhSach.data = await this.sp_lay_nhanvien_kv_theo_thuebao_id()
        //         grdDanhSach.DataSource = new TraCuuDanhBaFacade().LAY_DS_NHANVIEN_KV_THEO_THUEBAO_ID(thuebao_id).Tables[0]
        //         grdViewDanhSach.BestFitColumns()
      }
    },

    async lay_tram_vt_baohong(baohong_id) {
      let data = this.GetData(
        await api.lay_tram_vt_baohong(this.axios, baohong_id)
      )
      return data
    },

    async lay_baohong(baohong_id) {
      // let data = this.GetData(
      //   await api.lay_dulieu_bang_theo_dieukien(this.axios, {
      //     schema: 'baohong',
      //     table_name: 'baohong',
      //     list_of_cols: 'nguoi_bh,dienthoai_lh, ghichu_hong',
      //     where: `baohong_id= ${baohong_id}`,
      //     order: ''
      //   })
      // )
      let data = this.GetData(
        await api.sp_tt_baohong(this.axios, {
          p_param: baohong_id,
          p_type: 2
        })
      )
      return data
    },
    async lay_ds_thamso_macdinh_dv() {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = {
        vdonvi_id: donviId,
        vkieu_id: 4
      }
      let data = this.GetData(await api.lay_ds_thamso_macdinh_dv(this.axios, input))
      return data
    },
    async lay_ds_thamso_macdinh(kieu) {
      let input = {
        kieu_id: kieu
      }
      let data = this.GetData(await api.lay_ds_thamso_macdinh(this.axios, input))
      return data
    },
    xoa_phananh_bh_theo_id: async function (dsphananh_id) {
      var input = { dsphananh_id: dsphananh_id }
      let response = await frmApi.xoa_phananh_bh_theo_id(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        return true
      } else {
        this.ShowError(`${response.data.error_code} ${response.data.message}`)
        return false
      }
    },
    clear() {
      this.txtNguoiPA = ''
      this.txtNoiDung = ''
      this.txtDienThoaiLH = ''
      this.dtpNgayPA = ''
      this.$refs.reftxtNguoiPA.focus()
    },
    sp_capnhat_phananh_bh: async function () {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let nguoidungid = this.$root.token.getNguoiDungID()
      let phanvungid = this.$root.token.getPhanVungID()
      let username = this.$root.token.getUserName()
      var input = { phanvung_id: phanvungid, baohong_id: this.vbaohong_id_t, nguoi_pa: this.txtNguoiPA, so_dt: this.txtDienThoaiLH, noidung: this.txtNoiDung, nguoi_cn: username, may_cn: 'web', ip_cn: '127.0.0.1', donvi_id: donviId, nhanvien_id: nhanvienId, nguoidung_id: nguoidungid }
      let response = await frmApi.sp_capnhat_phananh_bh(this.axios, input)
      if ((response.data.error === 200 || response.data.error === '200') && response.data.error_code == 'BSS-00000000') {
        this.ShowSuccess(`Cập nhật thành công!`)
        this.clear()
        await this.LOAD_GRID_PHANANH()
        this.tg = ''
        this.tsbCapNhat_Enabled = false
        this.tsbNhapMoi_Enabled = true
        //         if (response.data.data != null) {
        //           this.ShowSuccess(`Cập nhật thành công!`)
        //         } else {
        //           this.ShowError(`${response.data.error_code}-${response.data.message}`)
        //         }
      } else {
        this.ShowError(`${response.data.error_code}-${response.data.message}`)
      }
    },
    sp_lay_nhanvien_kv_theo_thuebao_id: async function () {
      var input = { thuebao_id: this.thuebao_id }
      return this.GetData(await frmApi.sp_lay_nhanvien_kv_theo_thuebao_id(this.axios, input))
    },
    lay_ds_nhanvienth_bh: async function () {
      var input = { baohong_id: this.vbaohong_id_t }
      return this.GetData(await frmApi.lay_ds_nhanvienth_bh(this.axios, input))
    },
    lay_ds_phananh: async function () {
      var input = { baohong_id: this.vbaohong_id_t }
      return this.GetData(await frmApi.lay_ds_phananh(this.axios, input))
    },
    gridviewDanhSach_selectedItemsChanged(items) {
      this.gridviewDanhSach.selectedItems = items
    },
    gridviewDanhSach_FocusedRowChanged(i, item) {
      this.gridviewDanhSach.selectedItem = item
      console.log('gridviewDanhSach_FocusedRowChanged', item)
      try {
        this.txtNguoiPA = item.nguoi_pa // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "NGUOI_PA").ToString();
        this.txtDienThoaiLH = item.so_dt // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "SO_DT").ToString();
        this.txtNoiDung = item.noidung // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "NOIDUNG").ToString();
        if (item.ngay_cn != null && item.ngay_cn != '') this.dtpNgayPA = moment(item.ngay_cn, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "NGAY_CN").ToString();
      } catch (err) {
        console.log('gridviewDanhSach_FocusedRowChanged-Err', err)
      }
    },
    closeHenKH() {},
    disabledRange: function (date) {
      return date > new Date()
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
