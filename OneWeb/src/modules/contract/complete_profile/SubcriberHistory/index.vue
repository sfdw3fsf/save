<template src="./index.html"></template>
<script>
import state from './define.js'
import searchAccount from '@/modules/search/subscriber/SearchAccount'
import VueDateExt from '@/modules/contract/setup/DeclareLandline/components/VueDateExt'
import Select2Ext from '@/modules/contract/setup/DeclareLandline/components/Select2Ext'
import XemBaoCao from '@/modules/print/XemBaoCao/XemBaoCao.vue'
import Vue from 'vue'
import moment from 'moment'
import { DichVuVienThong } from '@/const/enums'
import { DetailRow, Resize } from '@syncfusion/ej2-vue-grids'
import DataGridCustom from '@/modules/contract/setup/DeclareLandline/components/DataGridCustom'
import frmTraCuu_ThueBao from '@/modules/complaint/SearchComplainDetail/popups/frmTraCuu_ThueBao.vue'
import downloadExcel from 'vue-json-excel'
import XLSX from 'xlsx'
import api from './api'
export default {
  data() {
    return {
      ...state,
      mau_excel: [],
      input_ma_nd: '',
      file: [],
      danhsach_excel: [],
      danhsachloi_docfile: [],
      danhsach_thoaman: [],
      exportExcelFields: {
        manv_key: 'manv_key',
        ma_tb: 'ma_tb',
        ghichu: 'ghichu',
        henden: 'henden',
        noi_dung: 'noi_dung',
        hentu: 'hentu',
        key: 'key',
        khoaphieu: 'khoaphieu'
      },
      chkTinhTrang: false,
      tinhtrang_id: 1999,
      tinhTrangData: null,
      title: 'Tra cứu lý lịch thuê bao',
      label: 'Mẫu file excel: MA_TB, NOIDUNG, HENTU, HENDEN,KEY,MANV_KEY',
      detailTemplate: function () {
        return {
          template: Vue.component('detailTemplate', {
            template: `
            <b-tabs content-class="mt-3 custom-tabs">
              <b-tab v-if="dataSource1.length > 0" title="Nguyên nhân hỏng" active>
                <DataGrid :dataSource="dataSource1" :columns="collum1"></DataGrid>
              </b-tab>
              <b-tab v-if="dataSource2.length > 0" title="Chi tiết tồn">
                <DataGrid :dataSource="dataSource2" :columns="collum2"></DataGrid>
              </b-tab>
              <b-tab v-if="dataSource3.length > 0" title="Chi tiết nhân viên thực hiện">
                <DataGrid :dataSource="dataSource3" :columns="collum3"></DataGrid>
              </b-tab>
            </b-tabs>`,
            data: function () {
              return {
                data: {},
                collum1: [
                  {
                    fieldName: 'loaihong',
                    headerText: 'Loại hỏng',
                    allowSorting: false,
                    width: '150px',
                    textAlign: 'center'
                  },
                  {
                    fieldName: 'ct_hong',
                    headerText: 'Chi tiết hỏng',
                    allowSorting: false,
                    width: '150px',
                    textAlign: 'center'
                  }
                ],
                collum2: [
                  {
                    fieldName: 'ten_dv',
                    headerText: 'Đơn vị báo tồn',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'ten_nv',
                    headerText: 'Nhân viên báo tồn',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'ngay_bt',
                    headerText: 'Ngày báo tồn',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'nhomton',
                    headerText: 'Nhóm tồn',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'ct_ton',
                    headerText: 'Chi tiết tồn',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'ghichu',
                    headerText: 'Ghi chú tồn',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  }
                ],
                collum3: [
                  {
                    fieldName: 'ten_dv',
                    headerText: 'Đơn vị',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'ten_nv',
                    headerText: 'Nhân viên',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'so_dt',
                    headerText: 'Điện thoại',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'ngaygiao',
                    headerText: 'Ngày giao',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'nhiemvu',
                    headerText: 'Nhiệm vụ',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  },
                  {
                    fieldName: 'ghichu',
                    headerText: 'Ghi chú tồn',
                    allowSorting: false,
                    width: 'auto',
                    textAlign: 'center',
                    autoFit: 'true'
                  }
                ],
                dataSource1: [],
                dataSource2: [],
                dataSource3: []
              }
            },
            mounted() {
              this.getChitiethong()
              this.getChitietton()
              this.getChitietNV()
            },
            methods: {
              async getChitiethong() {
                try {
                  let request = []
                  request.push({
                    BAOHONG_ID: this.data.baohong_id
                  })
                  let data = await this.axios.post('/web-tracuu/lylichthuebao/sp_baohong_chitiet_hong', {
                    js_data: JSON.stringify(request)
                  })
                  this.dataSource1 = data.data.data
                } catch (e) {}
              },
              async getChitietton() {
                try {
                  let request = []
                  request.push({
                    BAOHONG_ID: this.data.baohong_id
                  })
                  let data = await this.axios.post('/web-tracuu/lylichthuebao/sp_baohong_chitiet_ton', {
                    js_data: JSON.stringify(request)
                  })

                  this.dataSource2 = data.data.data
                } catch (e) {}
              },
              async getChitietNV() {
                try {
                  let request = []
                  request.push({
                    BAOHONG_ID: this.data.baohong_id
                  })
                  let data = await this.axios.post('/web-tracuu/lylichthuebao/sp_baohong_nv_thuchien', {
                    js_data: JSON.stringify(request)
                  })

                  this.dataSource3 = data.data.data
                } catch (e) {}
              }
            }
          })
        }
      }
    }
  },
  mounted() {
    if (this.$route.query.tag && this.$route.query.tag == '1') {
      this.title = 'ĐIỀU BẢO DƯỠNG THUÊ BAO'
      this.$refs.tsbtnDieuBD.style.display = this.$refs.tsbtnDieuTheoFile.style.display = this.$refs.tsbtnFileMau.style.display = this.$refs.chkDieuKey.style.display = null
      this.$refs.lbMauFile.style.display = 'flex'
    }
    this.frmLyLichThueBao_Shown()
  },
  provide: {
    grid: [DetailRow, Resize]
  },
  components: {
    searchAccount,
    VueDateExt,
    XemBaoCao,
    Select2Ext,
    DataGridCustom,
    frmTraCuu_ThueBao,
    downloadExcel
  },
  methods: {
    async txtMaTB_KeyDown() {
      this.loading(true)
      this.CLEAR()
      await this.NAP_THONGTIN_THUEBAO(0)
      this.loading(false)
    },

    CLEAR() {
      this.$refs.txtDiaChiTB.value = ''
      this.$refs.txtToQL.value = ''
      this.$refs.txtThoQL.value = ''
      this.$refs.txtNgaySD.value = ''
      // this.$refs.txtGiaTri.value = ''
      this.$refs.txtDoDaiDay.value = ''
      this.$refs.gridThueBaoKem.dataSource = []
      this.gridDanhsachBaohong = []
      this.$refs.gridGianDo.dataSource = []
    },
    async HT_THONGSO_KYTHUAT(in_thuebao_id, in_dichvuvt_id) {
      try {
        let rs = await this.axios.post('/web-tracuu/lylichthuebao/sp_baohong_ds_thongsokt', {
          dichvuvt_id: in_dichvuvt_id,
          thuebao_id: in_thuebao_id
        })
        let string = ''
        rs.data.data.forEach((item) => {
          string += '<b>' + item.ten_truong + '</b>: ' + (item.giatri != null ? item.giatri : '') + '<br>'
        })
        this.$refs.wbrTSKT.innerHTML = string
      } catch (err) {
        console.log(err)
        this.$refs.wbrTSKT.innerHTML = 'Chưa có dữ liệu'
      }
    },
    async HT_GIANDO_CAP(in_thuebao_id, dv) {
      //LAY_DANDOCAP_MANGNGOAIVI
      let dbDs = null
      console.log(String(dv), DichVuVienThong.CO_DINH)
      switch (Number(dv)) {
        case DichVuVienThong.CO_DINH:
          console.log('vào đây')
          dbDs = await this.axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid', {
            thuebao_id: in_thuebao_id
          })
          break
        case DichVuVienThong.ADSL:
          dbDs = await this.axios.post('web-ccdv/xuly_nghiemthubaohong/lay_ds_db_adsl_theo_tbid', {
            thuebao_id: in_thuebao_id
          })
          break
        case DichVuVienThong.IMS:
          dbDs = await this.axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_ims_theo_tbid', {
            thuebao_id: in_thuebao_id
          })
          break
        case DichVuVienThong.MEGAWAN:
          dbDs = await this.axios.post('/web-hopdong/hopdong/lay_ds_danhba_megawan', {
            params: {
              thueBaoId: in_thuebao_id
            }
          })
          break
        case DichVuVienThong.TSL:
          dbDs = await this.axios.post('/web-hopdong/chuyendich-tsl/lay_ds_db_tsl_theo_tbid', {
            params: {
              thuebao_id: in_thuebao_id
            }
          })
          break
        default:
          break
      }
      console.log(dbDs, 'dbDs')
      if (!dbDs || !dbDs.data || !dbDs.data.data) {
        return
        this.loading(false)
      }
      dbDs = dbDs.data.data
      let vketcuoi_id = 0,
        vt1 = 0,
        vt2 = 0
      vketcuoi_id = dbDs[0].ketcuoi_id
      vt1 = dbDs[0].vitri ? dbDs[0].vitri : ''
      vt2 = dbDs[0].vitri_2 ? dbDs[0].vitri_2 : ''
      if (vt1 == 0) {
        this.loading(false)
        return
      }
      var ds_soi1 = []
      var ds_soi2 = []
      try {
        let rs = await this.axios.post('/web-tracuu/lylichthuebao/sp_dandocap_mangngoaivi', {
          vcapvao_id: 0,
          vdoidayvao: 0,
          vjumper_vao: vt1,
          vketcuoi_id: vketcuoi_id
        })
        ds_soi1 = rs.data.data
        if (vt2) {
          rs = await this.axios.post('/web-tracuu/lylichthuebao/sp_dandocap_mangngoaivi', {
            vcapvao_id: 0,
            vdoidayvao: 0,
            vjumper_vao: vt2,
            vketcuoi_id: vketcuoi_id
          })
          ds_soi2 = rs.data.data
        }
      } catch (err) {}

      var col1 = ds_soi1.map((item) => ({ ...item, soicap: 1 }))
      var col2 = ds_soi2.map((item) => ({ ...item, soicap: 2 }))
      console.log(col1.concat(col2))
      this.$refs.gridGianDo.dataSource = col1.concat(col2)
    },
    frmTraCuu_ThueBao_Close(item) {
      this.vdvvt_id = item.dichvuvt_id
      this.thuebao_id = item.thuebao_id
      this.ma_tk = item.ma_tb
      this.LoadDS_KhieuNai(this.thuebao_id, this.ma_tk, this.$refs.dtpKyHD.value)
    },
    async NAP_THONGTIN_THUEBAO(dichvuvt_id) {
      return new Promise(async (rs, rj) => {
        if (!this.$refs.txtMaTB.value) {
          this.loading(false)
          return
        }
        try {
          let vma_tb = this.$refs.txtMaTB.value
          let dv = 0,
            loaitb_id = 0
          let rs = await this.axios.post('web-baohong/TiepNhanBaoHong/lay_danhba_theo_matb', {
            vma_tb: this.$refs.txtMaTB.value
          })
          var dt = rs.data.data
          let index = 0

          if (dt.length > 0) {
            if (dt.length == 1) index = 0
            else {
              this.$refs.frmTraCuu_ThueBao.frmTraCuu_ThueBao(ds)
              this.$refs.frmTraCuu_ThueBao.show()
            }
          } else {
            this.$toast.error('Không tìm thấy thông tin!')
            this.loading(false)
            return
          }

          this.$refs.txtTenTB.value = dt[index]['ten_tb']
          this.$refs.txtDiaChiTB.value = dt[index]['diachi_tb']
          this.$refs.txtNgaySD.value = dt[index]['ngay_sd']
          if (dt[index].toql) this.$refs.txtToQL.value = dt[index]['toql']
          if (dt[index].nv_ql) this.$refs.txtThoQL.value = dt[index]['nv_ql']

          vma_tb = dt[index]['ma_tb']
          dv = dt[index]['dichvuvt_id']
          loaitb_id = dt[index]['loaitb_id']

          this.$refs.cboDichVu.value = dt[index]['dichvuvt_id']
          await this.cboDichVu_EditValueChanged()
          this.$refs.cboLoaiHinh.value = dt[index]['loaitb_id']
          this.thuebao_id = dt[index]['thuebao_id']
          this.khachhang_id = dt[index]['khachhang_id']

          try {
            // lblGoiKT.value = "";
            // string laygoi_kt = "select ten_goi from " + DatabaseConstants.DB2 + ".goi_kt a where a.goikt_id="
            //     + DatabaseConstants.DB2 + ".lay_goikt_id(" + thuebao_id + ", -1)";
            // DataSet dsGoi = new BangThamSo().GetSQL(laygoi_kt, "");
            // if (dsGoi != null && dsGoi.Tables[0].Count > 0)
            // {
            //     lblGoiKT.value = dsGoi.Tables[0][0][0]
            // }
          } catch (Exception) {}

          await this.HienThi_DS_ThueBao_CungCap(this.thuebao_id, dv)
          await this.HienThi_LichSu_BaoHong(vma_tb, dv)
          await this.HT_THONGSO_KYTHUAT(this.thuebao_id, dv)
          await this.HT_GIANDO_CAP(this.thuebao_id, dv)
          await this.HienThi_DanhSach_Thuebao(this.khachhang_id, this.thuebao_id)
        } catch (err) {
          this.$toast.error('' + err.message)
        }

        rs(true)
      })
    },

    async HienThi_DS_ThueBao_CungCap(vma_tb, dv) {
      return new Promise(async (rs, rj) => {
        let dsTB = await this.axios.post('web-quantri/thongtin-cap/sp_lay_ds_tb_cung_madoicap', {
          vthuebao_id: vma_tb,
          vkieu: 1
        })

        this.$refs.gridThueBaoKem.dataSource = dsTB.data && dsTB.data.data ? dsTB.data.data : []
        rs(true)
      })
    },

    async HienThi_LichSu_BaoHong(vma_tb, dv) {
      return new Promise(async (rs, rj) => {
        try {
          let data = await this.axios.post('/web-tracuu/tracuubaohong/sp_lay_ds_baohong_theo_ma_tb', {
            dichvuvt_id: dv,
            ma_tb: vma_tb
          })
          if (data.data.data.length > 0) {
            this.gridDanhsachBaohong = data.data.data.map((item) => ({
              ...item,
              ngay_bh: moment(item.ngay_bh).isValid() ? moment(item.ngay_bh).format('DD/MM/YYYY HH:mm:ss') : item.ngay_bh
            }))
          } else {
            this.gridDanhsachBaohong = []
          }
        } catch (err) {
          this.$toast.error('' + err.message)
        }
        rs(true)
      })
    },
    async HienThi_DanhSach_Thuebao(vkhachhang_id, vthuebao_id) {
      return new Promise(async (rs, rj) => {
        try {
          let dt = await this.axios.post('/web-tracuu/lylichthuebao/lay_ds_tb_cntt_khid', {
            vkhachhang_id: vkhachhang_id,
            vthuebao_id: vthuebao_id
          })
          this.$refs.grid_DanhSach.dataSource = dt.data.data
        } catch (err) {
          this.$toast.error('Có lỗi : ' + err.message)
        }
        rs(true)
      })
    },

    async frmLyLichThueBao_Shown() {
      let rs = await this.axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT')
      this.$refs.cboDichVu.dataSource = rs.data.data
      this.$refs.cboDichVu.dataTextField = 'TEN_DVVT'
      this.$refs.cboDichVu.dataValueField = 'DICHVUVT_ID'

      let kq = await api.lay_tinhtrang_dieu_bd(this.axios, {})
      this.tinhTrangData = kq.data.data
        ? kq.data.data.map((e) => {
            return { id: e.tinhtrang_id, text: e.tinhtrang }
          })
        : []
    },
    async cboDichVu_EditValueChanged() {
      return new Promise(async (resolve, reject) => {
        try {
          try {
            let rs = await this.axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB')
            this.$refs.cboLoaiHinh.dataSource = rs.data.data.filter((item) => item.DICHVUVT_ID == this.$refs.cboDichVu.value && item.LOAIHINH_TB != null && item.LOAITB_ID != null)
            this.$refs.cboLoaiHinh.dataTextField = 'LOAIHINH_TB'
            this.$refs.cboLoaiHinh.dataValueField = 'LOAITB_ID'
            console.log(this.$refs.cboLoaiHinh.dataSource)
          } catch (e) {
            console.log(e)
          }

          if (this.$refs.cboDichVu.value == null) {
            this.$refs.pnlDs_ThueBao.style.display = 'none'
            this.$refs.pnlGianDo.style.display = null
          }
          if (!this.$refs.cboDichVu.value || this.$refs.cboDichVu.value == '-1') {
            this.$refs.pnlDs_ThueBao.style.display = 'none'
            this.$refs.pnlGianDo.style.display = null
          }

          if (this.$refs.cboDichVu.value == DichVuVienThong.HOINGHI_TRUYENHINH || this.$refs.cboDichVu.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.$refs.cboDichVu.value == DichVuVienThong.DICHVU_CNTT || this.$refs.cboDichVu.value == DichVuVienThong.TRUNGTAM_DULIEU) {
            this.$refs.pnlDs_ThueBao.style.display = null
            this.$refs.pnlGianDo.style.display = 'none'
          } else {
            this.$refs.pnlDs_ThueBao.style.display = 'none'
            this.$refs.pnlGianDo.style.display = null
          }
        } catch (err) {
          console.log(err)
          resolve(true)
        }
        resolve(true)
      })
    },
    txtMaTB_ButtonClick() {
      var f = new frmTraCuuDanhBa(0, 1)
      f.ShowDialog()
      if (f.chapnhan) {
        this.txtMaTB.value = f.ma_tb
        this.txtMaTB.Focus()
        txtMaTB_KeyDown(txtMaTB, new KeyEventArgs(Keys.Enter))
      }
    },
    txtMaTB_ButtonClick: function () {
      this.$refs.searchAccountDialog.show()
    },
    searchAccountDialogClose(data) {
      if (data != null) {
        this.$refs.txtMaTB.value = data.ma_tb
        this.txtMaTB_KeyDown()
      }
      this.$refs.searchAccountDialog.hide()
    },
    tsbtnInPhieu_Click() {
      this.dsGiaTriHD = []
      this.dsGiaTriHD.push(this.thuebao_id)
      this.$refs.popupXemBaoCao.XemNgay()
    },
    async toolStripButton1_Click() {
      let data = await this.axios.get('web-hopdong/ly_lich_thue_bao/thong_tin_mau_file')
      this.mau_excel = [data.data.data]
      console.log(this.mau_excel)
    },
    frmLyLichThueBao_Load_1() {
      if (Tag == '1') {
        labelFunctionTitle.value = 'ĐIỀU BẢO DƯỠNG THUÊ BAO'
        Text = 'Điều bảo dưỡng thuê bao'
        tsbtnDieuBD.Visible = tsbtnDieuTheoFile.Visible = tsbtnFileMau.Visible = chkDieuKey.style.display = null
        lbMauFile.style.display = null
      }
      pnlGianDo.Dock = DockStyle.Fill
      pnlDs_ThueBao.Dock = DockStyle.Fill
      pnlDs_ThueBao.style.display = 'none'
      pnlGianDo.style.display = null
    },
    async tsbtnDieuBD_Click() {
      if (this.thuebao_id > 0) {
        this.loading(true)
        try {
          if (!this.chkTinhTrang || this.tinhtrang_id == null) {
            this.$toast.warning('Vui lòng chọn tình trạng!')
            this.loading(false)
            return
          }
          let ngayhentu = ''
          let ngayhenden = ''
          if (this.$refs.dtpDenTu.checked) {
            if (moment(this.$refs.dtpDenTu.value) <= moment()) {
              this.$toast.error('Giờ hẹn không được nhỏ hơn giờ hiện tại!')
              this.loading(false)
              return
            }
            if (dtpDenTu.value >= dtpHenDen.value) {
              this.$toast.error('Giờ hẹn đến không được nhỏ hơn giờ hẹn từ!')
              this.loading(false)
              return
            }

            ngayhentu = this.$refs.dtpDenTu.value
            ngayhenden = this.$refs.dtpHenDen.value
          }
          console.log(this.thuebao_id)
          if (this.$refs.txtGhiChu.value == '') {
            this.$toast.warning('Ghi chú không được để trống!')
            this.loading(false)
            return
          }
          if (!confirm('Bạn có chắc chắn muốn điều điều bảo dưỡng không?')) {
            this.loading(false)
            return
          }
          let vdieukey = '0'
          if (this.$refs.chkDieuKey.checked) vdieukey = '1'

          if (this.$refs.cboDichVu.value == DichVuVienThong.HOINGHI_TRUYENHINH || this.$refs.cboDichVu.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.$refs.cboDichVu.value == DichVuVienThong.DICHVU_CNTT || this.$refs.cboDichVu.value == DichVuVienThong.TRUNGTAM_DULIEU) {
            if (this.$refs.gvDanhSach.dataSource.length <= 0) {
              this.$toast.error('Không có thuê bao để điều bảo dưỡng.')
              this.loading(false)
              return
            }
            let lst_chon = this.$refs.grid_DanhSach.getSelectedRecords()
            if (lst_chon.length == 0) {
              this.$toast.error('Bạn hãy chọn thuê bao để điều bảo dưỡng')
              this.loading(false)
              return
            }

            let json_list = JSON.stringify(lst_chon)
            console.log(json_list)
            let rs = await this.axios.post('web-hopdong/ly_lich_thue_bao/dieu_hong_cntt_thuebao_id', {
              vds_json_tb: json_list,
              vdonvi_nhan_id: this.$root.token.getDonViID(),
              vnhanvien_nhan_id: this.$root.token.getNhanVienID(),
              vtinhtrang_id: 303,
              vkhoaphieu: 2,
              vghichu: this.$refs.txtGhiChu.value,
              hentu: ngayhentu,
              henden: ngayhenden,
              vdieukey: vdieukey
            })
            vkt == rs.data.data
            if (vkt != 'ok') {
              this.$toast.error(vkt)
              this.$refs.txtGhiChu.value = ''
              this.$refs.dtpDenTu.checked = false
            } else {
              this.$toast.error('Điều bảo dưỡng thành công thuê bao')
            }
          } else {
            let rs = await this.axios.post('web-hopdong/ly_lich_thue_bao/dieu_hong_theo_thuebao_id_v3', {
              vthuebao_id: this.thuebao_id,
              vdonvi_nhan_id: this.$root.token.getDonViID(),
              vnhanvien_nhan_id: this.$root.token.getNhanVienID(),
              vtinhtrang_id: this.chkTinhTrang ? this.tinhtrang_id : 301,
              vkhoaphieu: 2,
              vghichu: this.$refs.txtGhiChu.value,
              hentu: ngayhentu,
              henden: ngayhenden,
              vdieukey: vdieukey
            })
            let s = rs.data.data

            if (s != 'ok') {
              this.$toast.error(s)
              this.$refs.txtGhiChu.value = ''
              this.$refs.dtpDenTu.checked = false
            } else {
              this.$toast.success('Điều bảo dưỡng thành công thuê bao ' + this.$refs.txtMaTB.value)
            }
          }
        } catch (e) {
          if (e.data && e.data.message) {
            this.$toast.error(e.data.message)
          } else if (e.response && e.response.data && e.response.data.message) {
            this.$toast.error(e.response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi')
          }
          console.log(e)
        }
        this.loading(false)
      }
    },
    tsbtnInPhieu_HH_Click() {
      this.dsGiaTriHD = []
      this.dsGiaTriHD.push(this.thuebao_id)
      this.$refs.popupXemBaoCao.XemNgay()
    },
    SearchDanhBa: function () {
      this.$refs.searchAccountDialog.show()
    },
    searchAccountDialogClose(data) {
      console.log(data)
      if (data != null) {
        this.$refs.txtMaTB.value = data.ma_tb
        this.txtMaTB_KeyDown()
      }
      this.$refs.searchAccountDialog.hide()
    },
    // txtMaTB_KeyDown(event) {
    //   this.form = {
    //     dichvuvt_id: this.form.dichvuvt_id,
    //     loaitb_id: this.form.loaitb_id
    //   }
    //   if (event.target.value) this.loadDanhbaTheoMaTB(event.target.value)
    // },
    async getCboDichvuVt() {
      try {
        let data = await this.axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT')
        this.cboDichVuVT = data.data.data
      } catch (e) {
        console.log(e)
      }
    },
    async getCboLoaihinh() {
      try {
        let data = await this.axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB')
        this.cboLoaihinhTB = data.data.data
        if (this.cboLoaihinhTB.length > 0) this.form.loaitb_id = this.cboLoaihinhTB[0].LOAITB_ID
      } catch (e) {
        console.log(e)
      }
    },
    async loadDanhbaTheoMaTB(ma_tb) {
      try {
        this.loading(true)
        let data = await this.axios.post('/web-baohong/TiepNhanBaoHong/lay_danhba_theo_matb', {
          vma_tb: ma_tb
        })
        if (data.data.data.length > 0) {
          this.gridDanhsachlapkem = data.data.data
          let formData = data.data.data[0]
          for (let key in formData) {
            if (key == 'ngay_sd') {
              this.$set(this.form, key, moment(formData[key]).format('DD/MM/YYYY HH:mm:ss'))
            } else this.$set(this.form, key, formData[key])
          }
          this.dtpHentu
        } else {
        }
        this.loading(false)
      } catch (e) {
        this.loading(false)
      }
    },
    async loadDanhsachlichsu() {
      try {
        this.loading(true)
        if (this.gridDanhsachlapkem.length <= 0) {
          this.loading(false)
          return
        }
        this.getThongsoKythuat()
        this.getGiandocap()
        let data = await this.axios.post('/web-tracuu/tracuubaohong/sp_lay_ds_baohong_theo_ma_tb', {
          dichvuvt_id: this.form.dichvuvt_id,
          ma_tb: this.form.ma_tb
        })
        if (data.data.data.length > 0) {
          this.gridDanhsachBaohong = data.data.data.map((item) => ({
            ...item,
            ngay_bh: moment(item.ngay_bh).isValid() ? moment(item.ngay_bh).format('DD/MM/YYYY HH:mm:ss') : item.ngay_bh
          }))
        } else {
          this.gridDanhsachBaohong = []
        }
        this.loading(false)
      } catch (e) {
        this.loading(false)
      }
    },
    async getThongsoKythuat() {
      try {
        let data = await this.axios.post('/web-tracuu/lylichthuebao/sp_baohong_ds_thongsokt', {
          dichvuvt_id: this.form.dichvuvt_id,
          thuebao_id: this.form.thuebao_id
        })
        if (data.data.data.length > 0) {
          this.thongso = data.data.data
        } else {
        }
      } catch (e) {}
    },
    async getGiandocap() {
      try {
        let dbDs = null
        console.log(this.form.dichvuvt_id)
        console.log(this.DichVuVienThong.ADSL)
        switch (String(this.form.dichvuvt_id)) {
          case String(this.DichVuVienThong.CO_DINH):
            dbDs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_codinh', {
              params: {
                thueBaoId: this.form.thuebao_id
              }
            })
            break
          case String(this.DichVuVienThong.ADSL):
            dbDs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_adsl', {
              params: {
                thueBaoId: this.form.thuebao_id
              }
            })
            break
          case String(this.DichVuVienThong.IMS):
            dbDs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_ims', {
              params: {
                thueBaoId: this.form.thuebao_id
              }
            })
            break
          case String(DichVuVienThong.MEGAWAN):
            dbDs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_megawan', {
              params: {
                thueBaoId: this.form.thuebao_id
              }
            })
            break
          case String(this.DichVuVienThong.TSL):
            dbDs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_tsl', {
              params: {
                thueBaoId: this.form.thuebao_id
              }
            })
            break
        }
        console.log(dbDs.data.data)
        if (!dbDs || dbDs.data.data.length == 0) {
          this.loading(false)
          return
        }

        let dbDS_rs = dbDs.data.data[0]
        let ds_soi1 = null
        console.log(dbDS_rs)
        try {
          if (dbDS_rs.vitri) {
            ds_soi1 = await this.axios.post('/web-tracuu/lylichthuebao/sp_dandocap_mangngoaivi', {
              vcapvao_id: 0,
              vdoidayvao: 0,
              vjumper_vao: dbDS_rs.vitri && dbDS_rs.vitri != null ? bDS_rs.vitri : '',
              vketcuoi_id: dbDS_rs.ketcuoi_id
            })
          }
          let ds_soi2 = null
          if (dbDS_rs.vitri_2) {
            ds_soi2 = await this.axios.post('/web-tracuu/lylichthuebao/sp_dandocap_mangngoaivi', {
              vcapvao_id: 0,
              vdoidayvao: 0,
              vjumper_vao: dbDS_rs.vitri_2 && dbDS_rs.vitri_2 != null ? bDS_rs.vitri_2 : '',
              vketcuoi_id: dbDS_rs.ketcuoi_id
            })
          }
        } catch (err) {}
        ds_soi1 = ds_soi1 ? ds_soi1.data.data : []
        ds_soi1.map((item) => (item.soicap = 1))
        ds_soi2 = ds_soi2 ? ds_soi2.data.data : []
        ds_soi2.map((item) => (item.soicap = 2))

        let merged = ds_soi1.concat(ds_soi2)
        this.gridGiando = merged
      } catch (e) {
        console.log(e)
      }
    },
    tsbtnDieuTheoFile_Click() {
      if (!this.chkTinhTrang || this.tinhtrang_id == null) {
        this.$toast.warning('Vui lòng chọn tình trạng!')
        return
      }
      this.$refs.fileInput.value = null
      this.$refs.fileInput.click()
      // try
      // {
      //     WinUI.WinUIBangTuDien.LoadExcel ec = new WinUI.WinUIBangTuDien.LoadExcel();
      //     if (ec.BrowseFile())
      //     {
      //         //file = ec.fPath;
      //         dtDulieu = ec.GetDataExcel().Tables[0];
      //         CapNhatExcel(dtDulieu);
      //     }
      // }
      // catch (err)
      // {
      //     this.$toast.error("Lỗi định dạng file excel, bạn xem lại!");
      // }
    },
    onChangeFile(event) {
      this.input_ma_nd = event.target.value.split('\\').pop()
      this.file = event.target.files[0]
      this.readFileExcel(this.file)
    },
    readFileExcel() {
      this.danhsach_excel = []
      this.danhsachloi_docfile = []
      this.danhsach_thoaman = []
      try {
        this.loading(true)
        if (this.checkfile()) {
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            console.log(e)
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            var data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            const key = data[0]
            var newArr = data.filter((item, i) => i != 0)
            var newObjectArr = newArr.map((item) => {
              var temp = {}
              Object.values(key).map((v, k) => {
                temp[v] = item[k]
              })

              return temp
            })
            console.log('TEMP', newObjectArr)
            this.thucthi(newObjectArr)
            //return
          }
          reader.readAsBinaryString(this.file)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra khi đọc file')
      } finally {
        this.loading(false)
      }
    },
    async thucthi(data) {
      if (!confirm('Bạn có chắc chắn muốn điều theo file không?')) {
        return
      }
      var errData = []
      var errKTT = []
      for (let i = 0; i < data.length; i++) {
        let item = data[i]
        let dsT = await this.sp_kiemtra_tb_co_tontai(item.ma_tb)
        var thuebao_id = 0
        if (dsT && dsT.length > 0) {
          thuebao_id = dsT[0].thuebao_id
        } else {
          errKTT.push(item.ma_tb)
          errData.push(item)
          continue
        }
        let rs = await this.dieu_hong_theo_thuebao_id_key({
          vthuebao_id: thuebao_id,
          vdonvi_nhan_id: this.$root.token.getDonViID(),
          vnhanvien_nhan_id: this.$root.token.getNhanVienID(),
          vtinhtrang_id: this.chkTinhTrang ? this.tinhtrang_id : 301,
          vkhoaphieu: item.khoaphieu,
          vghichu: item.ghichu ? item.ghichu : '',
          hentu: item.hentu,
          henden: item.henden,
          vdieukey: item.key,
          vmanv_key: item.manv_key
        })
        if (rs && rs != 'ok') {
          errData.push(item)
        }
      }
      let strKTT = errKTT.toString()
      //this.$toast.error(`Không tồn tại thuê bao: ${strKTT}`)
      this.$toast.success('Đã điều thành công ' + (data.length - errData.length) + ' thuê bao!')
      if (errData.length > 0) {
        this.$confirm(' Có  ' + errData.length + ' thuê bao không điều được. Bạn có muốn xuất excel để kiểm tra lại?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          this.SaveXlsAndOpenFile('DanhSachThuebaoCapNhatKhongThanhCong.xls', '', errData)
        })
      }
    },
    async SaveXlsAndOpenFile(fileName, sheetName, datta) {
      let data1 = XLSX.utils.json_to_sheet(datta)
      let wb = XLSX.utils.book_new()
      XLSX.utils.book_append_sheet(wb, data1, sheetName)
      XLSX.writeFile(wb, fileName)
    },
    checkfile() {
      if (this.file == null) {
        this.$toast.error('Chưa chọn file excel')
        return false
      }
      if (this.file.type != 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' && this.file.type != 'application/vnd.ms-excel') {
        this.$toast.error('File import không đúng định dạng xlsx!')
        return false
      }
      return true
    },
    async sp_kiemtra_tb_co_tontai(ma_tb) {
      try {
        this.loading(true)
        let response = await this.axios.post('web-tracuu/tracuu-thuebao/sp_kiemtra_tb_co_tontai', {
          ma_tb: ma_tb,
          //loaitb_id:58,
          loaitb_id: 0
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async dieu_hong_theo_thuebao_id_key(data) {
      try {
        this.loading(true)
        let response = await this.axios.post('web-baohong/baohong/dieu_hong_theo_thuebao_id_key', data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return 'Lỗi'
        }
      } catch (e) {
        this.loading(false)
        return 'Lỗi'
      }
    }
  }
}
</script>
<style>
.SubcriberHistory .e-grid .e-toolbar {
  border-top: 0px;
}
.SubcriberHistory .e-detailcell {
  padding: 10px;
}

.SubcriberHistory .nav-tabs .nav-item.show .nav-link,
.nav-tabs .nav-link {
  background-color: #f5f5f5;
  border-color: #dee2e6 #dee2e6 #fff;
  border-left: 0px;
}
.SubcriberHistory-popup .page-content {
  top: 130px !important;
}

.SubcriberHistory .tabs {
  max-width: 1230px;
}

.SubcriberHistory .box-form {
  overflow: scroll;
}
</style>
