<template src="./template.html"></template>

<style src="./CollectIncurredDebt.scss"></style>

<style scoped>
.e-grid .e-rowcell.cuoclapdat,
.cuoclapdat {
  font-weight: bold;
  color: blue;
}

.e-grid .e-rowcell.vatlapdat,
.vatlapdat {
  font-weight: bold;
  color: cadetblue;
}

.e-grid .e-rowcell.tongtien,
.tongtien {
  font-weight: bold;
  color: red;
}
</style>

<style src="../../../../assets/vendor/font-awesome/css/font-awesome.min.css"></style>
<style src="../../../../assets/vendor/ap8/css/style.css"></style>
<style src="../../../../assets/vendor/nucleo/outline/css/style.css"></style>
<style src="../../../../assets/vendor/nucleo/glyph/css/style.css"></style>
<style src="../../../../assets/vendor/bootstrap/css/bootstrap.min.css"></style>
<style src="../../../../assets/vendor/bootstrap-table/bootstrap-table.min.css"></style>
<style src="../../../../assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css"></style>
<style src="../../../../assets/vendor/select2/css/select2.min.css"></style>
<style src="../../../../assets/vendor/oneicon/style.css"></style>
<style src="../../../../assets/css/style.css"></style>

<script src="../../../../assets/vendor/jquery/jquery-3.5.1.slim.min.js"></script>
<script src="../../../../assets/vendor/jquery/moment.min.js"></script>
<script src="../../../../assets/vendor/jquery/popper.min.js"></script>
<script src="../../../../assets/vendor/select2/js/select2.min.js"></script>
<script src="../../../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../../../assets/vendor/highchart/highcharts.js"></script>
<script src="../../../../assets/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="../../../../assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min.js"></script>
<script src="../../../../assets/vendor/jquery/split.js"></script>
<script src="../../../../assets/js/custom.js"></script>

<script>
import api from './api/index.js'
import { DichVuVienThong, TRANGBI, DUNG_THU, LoaiHopDong, TrangThaiHD, LoaiHinhTB, ThoiHan, KHOANMUC_TT, KieuCaiDatDVGT, THUESO, TRANGTHAI_DONGBO, KieuLapDat, LOAI_DV, Loai_KH, HINHTHUC_TC, KIEU_HD, KIEU_KH, PHANLOAI_KH, Loai_NV, LOAISO, LOAI_KV, Loai_KM } from '@/const/enums'
const DatabaseConstants = { DB2: 'CSS' }
import SearchContractModal from '@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi/ModalChonDiaChi.vue'
/*import ChonDonVi from "@/modules/EXTS/HOPDONG/DichChuyenNew/components/ThongTinKhachHang/ChonDonVi/index.vue";*/
import ChonDonVi from '@/modules/contract/setup/CollectIncurredDebt/ChonDonVi/index.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import token_var from '../../../../plugins/token.js'
import moment from 'moment'
import { Icon } from '@iconify/vue2'
import { Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Toolbar } from '@syncfusion/ej2-vue-grids'
import frmGiaoViec from '@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV_V2.vue'

//import Split from "@/modules/contract/setup/HandoverCompleteProfile/components/split.js";
import TModal from '@/components/qt_components/tmodal/index.vue'
export default {
  components: { SearchContractModal, ModalChonDiaChi, ChonDonVi, KDatePicker, frmGiaoViec, TModal, Icon },
  data() {
    return {
      tthd_id: null,
      tbKetQuaHC: [],
      showKetQuaHoanCong: false,
      phieu_id: null,
      ngay_ht: null,
      dvgt: [],
      p_tien: null,
      ngay_gv: null,
      hdkh_id: null,
      dichvuvt_id: null,
      rawLoaiHinh: [],
      header: {
        title: 'THU TIỀN PHÁT SINH',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Thu tiền phát sinh',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      enablePhiTTN: false,
      phiTTN: 0,
      loaikh_id_new: 0,
      tongtienExtended: 0,
      tentocdo: '',
      tenmuccuoc: '',
      button: {
        nhapmoi: true,
        ghilai: true,
        huy: true,
        xoahd: true,
        thanhtoan: true,
        themtb: true,
        xoatb: true
      },
      options: {
        phuluc: [],
        tienkhoan: [],
        dichvu: [],
        loaihinh: [],
        kieuyeucau: [],
        donviTT: [],
        donviQL: []
      },
      config: {
        phuluc: [
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true,
            width: 120
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'diachi_tb',
            headerText: 'Địa chỉ thuê bao',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true
          },
          {
            fieldName: 'tongtien',
            headerText: 'Tổng tiền',
            allowFiltering: true,
            textAlign: 'right',
            allowSorting: true,

            type: 'Number',
            format: 'N0',
            customAttributes: { class: 'tongtien' }
          }
        ],

        tienkhoan: [
          {
            fieldName: 'tentat_kmtt',
            headerText: 'Khoản mục TT',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: true,
            allowEditing: false
          },
          {
            fieldName: 'tongtien',
            textAlign: 'right',
            allowEditing: true,
            editType: 'numericedit',
            format: 'N',
            customAttributes: { class: 'tongtien' },
            headerText: 'Tổng tiền',
            allowFiltering: false,
            allowSorting: false,
            edit: 'tongtienParam'
          },
          {
            fieldName: 'tien',
            textAlign: 'right',
            allowEditing: true,
            editType: 'numericedit',
            format: 'N',
            customAttributes: { class: 'cuoclapdat' },
            headerText: 'Tiền',
            allowFiltering: false,
            allowSorting: false,
            edit: 'tienParam'
          },
          {
            fieldName: 'vat',
            textAlign: 'right',
            format: 'N',
            customAttributes: { class: 'vatlapdat' },
            headerText: 'Vat',
            allowFiltering: false,
            allowSorting: false
          }
        ]
      },
      model: {
        phuluc: {},
        tienkhoan: [],
        ngay_yc: moment(new Date()).format('DD/MM/YYYY'),
        ngay_lhd: moment(new Date()).format('DD/MM/YYYY'),
        dvtt: 0,
        dvql: 0,
        dichvu: 0,
        loaihinh: 0
      },
      txtMaTB: '',
      txtMaGD: '',
      txtMaHD: '',
      txtMaKH: '',
      txtSoDT: '',
      txtTenKH: '',
      txtDiaChiKH: '',
      txtTenTB: '',
      txtTenTT: '',
      txtDiaChiTT: '',
      txtDiaChiLD: '',
      txtDiaChiTB: '',
      txtSoAo: '',
      txtGhiChu: '',
      hdtb_id: 0,
      thuebao_id: 0,
      khachhang_id: 0,
      hdkh_id: 0,
      hdkh_cha_id: 0,
      hdtt_id: 0,
      txtMaGD_Cha: '',
      vtocdo_id: 0,
      vmuccuoc_id: 0,
      disabledMaGD_Cha: false,
      vdoituong_id: 0,
      dsDBTB: null,

      userId: '',
      nhanvien_id: 0,
      vma_tinh: '',
      donvi_id: 0,
      may_cn: 0,
      phieu_id_giaoviec: null,
      dsHDKH: {},
      dsHDTT: {},
      dsHDTB: {},
      dsHDTBDV: [],
      dsCTTienHD: [],

      quytrinh_id: 0,
      dtControl: null,
      ct_tienhd_id: 0,

      insertHDKH_status: true,
      insertHDTT_status: true,
      insertHDTB_status: true,
      insertHDTBDV_status: true,
      insertCT_TIENHD_status: true,

      updateHDKH_status: true,
      updateHDTT_status: true,
      updateHDTB_status: true,
      updateHDTBDV_status: true,
      updateCT_TIENHD_status: true,

      dichvuvt_id: 0,
      LoaiHopDong_THU_KHAC: LoaiHopDong.THU_KHAC,
      TrangThaiHD_MOI: TrangThaiHD.MOI,
      LoaiHopDong_DAT_MOI: LoaiHopDong.DAT_MOI,
      TrangThaiHD_HOAN_THANH: TrangThaiHD.HOAN_THANH,

      insertHDTB_themtb_status: true,
      insertHDTBDV_themtb_status: true,
      insertCT_TIENHD_themtb_status: true,

      success_code: 'BSS-00000000',
      txtTongTienPLHD: 0,
      txtTienKM: 0,
      txtVatKM: 0,
      txtTongTienKM: 0,
      preData: {},

      button: {
        nhapmoi: true, //
        ghilai: true, //
        huy: true, //
        xoahd: true, //
        thanhtoan: true, //
        themtb: true,
        xoatb: true
      },

      current: 0,
      checkchecktienkhoan: [],
      huonggiao_tn: 0
    }
  },

  provide: {
    grid: [Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Toolbar]
  },
  props: ['dulieu'],

  created() {},
  async mounted() {
    // console.log(this.$route.query);
    this.txtMaTB = this.$route.query.ma_tb
    setTimeout(() => {
      this.preData = this.$route.query
      if(this.$route.query.loaitb_id){
      this.model.dichvu = this.rawLoaiHinh.find(x => x.LOAITB_ID == this.$route.query.loaitb_id).DICHVUVT_ID
      this.model.loaihinh = this.$route.query.loaitb_id
      this.model.kieuyeucau = 136
      this.txtMaTB_KeyPress({ key: 'Enter' })
    }
    }, 2000)
    await this.getDichvu()
    await this.getDonviTT()
    await this.getDonviQL()
    await this.load_cbbLoaiHinh()
    

    //this.getTTKM(0)
    this.HienThi_KhoanMuc_ThanhToan(0)

    this.SetButton(-1)

    if(document.getElementById('ngay_yc'))
    document.getElementById('ngay_yc').readOnly = true
    if(document.getElementById('ngay_lhd'))
    document.getElementById('ngay_lhd').readOnly = true

    this.tongtienExtended = this.addCommas(parseInt(this.txtTongTienKM.toString().replace(/\,/g, '')) + parseInt(this.phiTTN))

    // get params from url
    
    //this.jsClose();
  },

  computed: {
    dulieugrid() {
      return JSON.parse(JSON.stringify(this.options.tienkhoan))
    },
    loaihinhComputed() {
      return this.rawLoaiHinh
        .filter((x) => x.DICHVUVT_ID == this.model.dichvu)
        .map((x) => {
          return {
            id: x.LOAITB_ID,
            text: x.LOAIHINH_TB
          }
        })
    }
  },

  watch: {
    thuebao_id: {
      handler(val) {
        this.load_dvgt(val)
      },
      deep: true
    },
    'model.kieuyeucau': {
      handler(val) {
        console.log('abc')
      },
      deep: true
    },
    // "enablePhiTTN": {
    //   handler(val) {
    //     if(val)
    //       this.txtTongTienKM = this.addCommas (this.txtTongTienKM.replace(/\,/g, '')*1 + this.phiTTN*1)
    //     else
    //       this.txtTongTienKM = this.addCommas (this.txtTongTienKM.replace(/\,/g, '')*1)
    //   },
    //   deep: true
    // },
    phiTTN: {
      handler(val) {
        this.phiTTN = this.addCommas(val.replace(/\,/g, ''))
        this.tongtienExtended = this.addCommas(parseInt(this.txtTongTienKM.replace(/\,/g, '')) + parseInt(val.replace(/\,/g, '')))
      },
      deep: true
    },
    'options.phuluc': {
      handler(val) {
        let tong = 0
        for (let i = 0; i < this.options.phuluc.length; i++) {
          tong += this.options.phuluc[i].tongtien
        }
        this.txtTongTienPLHD = Number(tong)
          .toLocaleString()
          .replace('.', ',')
      },
      deep: true
    },

    // 'model.dichvu': {
    //   handler(val) {
    //     this.cboDichVu_VT_EditValueChanged()
    //   },
    //   deep: true
    // },
    preData: {
      handler(val) {
        if (val.dichvuvt_id) {
          this.model.dichvu = val.dichvuvt_id
          // console.log('a');
          this.model.loaihinh = val.loaitb_id
          // console.log(this.model.loaihinh);
          this.txtMaTB = val.ma_tb
          this.txtMaTB_KeyPress({ key: 'Enter' })
        }
      },
      deep: true
    },
    // 'txtMaTB': {
    //   handler(val) {
    //     if (val.trim() == '') return
    //     this.txtMaTB_KeyPress({ key: 'Enter' })
    //   },
    //   deep: true
    // },
    'model.loaihinh': {
      handler(val) {
        this.cboLoaiHinhTB_EditValueChanged()
      },
      deep: true
    },
    // 'model.loaihinh'(val) {
    //   this.cboLoaiHinhTB_EditValueChanged()
    // },

    'options.tienkhoan': {
      handler(val) {
        this.txtVatKM = 0
        this.txtTienKM = 0
        this.txtTongTienKM = 0
        for (let i = 0; i < this.options.tienkhoan.length; i++) {
          this.txtVatKM += this.options.tienkhoan[i].vat
          this.txtTienKM += this.options.tienkhoan[i].tien
          this.txtTongTienKM += this.options.tienkhoan[i].tongtien
        }
        this.txtVatKM = Number(this.txtVatKM)
          .toLocaleString()
          .replace('.', ',')
        this.txtTienKM = Number(this.txtTienKM)
          .toLocaleString()
          .replace('.', ',')
        //this.txtTongTienKM = Number(this.txtTongTienKM).toLocaleString().replace('.', ',')
        this.txtTongTienKM = this.addCommas(this.txtTongTienKM)
      },
      deep: true
    }
  },
  methods: {
    testfunc() {
      this.showKetQuaHoanCong = true
    },
    async tsbtnGiaoViec_Click() {
      console.log('phieu_id =', this.hdkh_id)
      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.phieu_id_giaoviec, this.$root.token.getDonViID(), this.$root.token.getNhanVienID(), moment().format('DD/MM/YYYY'))
      this.$refs.frnGiaoPhieuNV.vma_tb = this.txtMaTB
      this.$refs.frnGiaoPhieuNV.vhdtb_id = this.hdtb_id
      this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiao_tn
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load()
        this.$refs.popupfrnGiaoPhieuNV.show()
      })
    },
    async giaoviec_success() {
      // let rs_ds = await api.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, {
      //   phieu_id: this.phieu_id,
      //   nhanvien_id: this.$root.token.getNhanVienID(),
      //   kieu_id: 2,
      // });
      // this.ds = rs_ds.data ? rs_ds.data.data : [];
      // this.HienThiDSNV(this.ds);
    },
    addCommas(nStr) {
      nStr += ''
      var x = nStr.split('.')
      var x1 = x[0]
      var x2 = x.length > 1 ? '.' + x[1] : ''
      var rgx = /(\d+)(\d{3})/
      while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2')
      }
      return x1 + x2
    },
    async load_dvgt(thuebao_id) {
      this.dvgt = ''
      try {
        this.loading(true)
        let rsn = await this.axios.post('/web-hopdong/chuyendich/lay_ds_sudung_dvgt', {
          THUEBAO_ID: thuebao_id
        })
        if (rsn.data.data && rsn.data.data.length > 0) {
          if (this.dvgt != '') this.dvgt += `\n${rsn.data.data[0].ten_dvgt}`
          else this.dvgt += rsn.data.data[0].ten_dvgt
        }
      } catch (err) {
        console.log(err)
      } finally {
        console.log(this.dvgt)
        this.loading(false)
      }
    },
    txtMaGD_KeyPress: async function(event) {
      try {
        if (event.key == 'Enter') {
          this.loading(true)

          if (this.txtMaGD.trim() == '') return
          var json_data_lay_ds_hopdong_theo_ma_gd = JSON.stringify({
            in_ma_gd: this.txtMaGD,
            in_loaihd_id: LoaiHopDong.THU_KHAC,
            in_donvi_id: this.$root.token.getDonViID(),
            in_tthd_id: 0,
            in_nhanvien_id: this.$root.token.getNhanVienID(),
            in_donvi_dl_id: 0
          })
          var link_lay_ds_hopdong_theo_ma_gd = '/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd'
          const files_lay_ds_hopdong_theo_ma_gd = await this.axios.post(link_lay_ds_hopdong_theo_ma_gd, json_data_lay_ds_hopdong_theo_ma_gd, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          //this.Clear()
          if (files_lay_ds_hopdong_theo_ma_gd.data.data && files_lay_ds_hopdong_theo_ma_gd.data.error_code == this.success_code) this.HienThiTTHopDongKH(files_lay_ds_hopdong_theo_ma_gd.data.data)
          this.load_dvgt(files_lay_ds_hopdong_theo_ma_gd.data.data[0].thuebao_id)

          this.loading(false)
        }
      } catch (ex) {
        this.loading(true)

        if (this.txtMaGD.trim() == '') return
        var json_data_lay_ds_hopdong_theo_ma_gd = JSON.stringify({
          in_ma_gd: this.txtMaGD,
          in_loaihd_id: LoaiHopDong.THU_KHAC,
          in_donvi_id: this.$root.token.getDonViID(),
          in_tthd_id: TrangThaiHD.MOI,
          in_nhanvien_id: this.$root.token.getNhanVienID(),
          in_donvi_dl_id: 0
        })
        var link_lay_ds_hopdong_theo_ma_gd = '/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd'
        const files_lay_ds_hopdong_theo_ma_gd = await this.axios.post(link_lay_ds_hopdong_theo_ma_gd, json_data_lay_ds_hopdong_theo_ma_gd, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        //this.Clear()
        if (files_lay_ds_hopdong_theo_ma_gd.data.data && files_lay_ds_hopdong_theo_ma_gd.data.error_code == this.success_code) this.HienThiTTHopDongKH(files_lay_ds_hopdong_theo_ma_gd.data.data)

        this.loading(false)
      }
    },
    async load_cbbLoaiHinh() {
      var link_CSS_LOAIHINH_TB = '/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'
      const files_CSS_LOAIHINH_TB = await this.axios.get(link_CSS_LOAIHINH_TB, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      if (files_CSS_LOAIHINH_TB.data.data && files_CSS_LOAIHINH_TB.data.error_code == this.success_code) {
        var resObj = files_CSS_LOAIHINH_TB.data.data
        this.rawLoaiHinh = resObj
      }
    },
    txtMaTB_KeyPress: async function(event) {
      if (event.key == 'Enter') {
        try {
          this.loading(true)
          //this.Clear()
          if (this.txtMaTB.trim() == '') return
          this.txtMaTB = this.clearTheSpace(this.txtMaTB)
          if (this.txtMaTB != '') {
            var json_data_lay_ds_hopdong_theo_matb = JSON.stringify({
              dichVuVtId: this.model.dichvu,
              donViDlId: 0,
              loaiHdId: LoaiHopDong.THU_KHAC,
              loaiTbId: 0,
              maTb: this.txtMaTB,
              ttHdId: 0
            })
            var link_lay_ds_hopdong_theo_matb = '/web-hopdong/hopdong/lay_ds_hopdong_theo_matb'
            const files_lay_ds_hopdong_theo_matb = await this.axios.post(link_lay_ds_hopdong_theo_matb, json_data_lay_ds_hopdong_theo_matb, {
              headers: {
                'Content-Type': 'application/json'
              }
            })
            this.phieu_id = files_lay_ds_hopdong_theo_matb.data.data[0].phieucskh_id
            this.tthd_id = files_lay_ds_hopdong_theo_matb.data.data[0].tthd_id
            //this.Clear()
            if (files_lay_ds_hopdong_theo_matb.data.data && files_lay_ds_hopdong_theo_matb.data.data.length > 0) {
              console.log('tthd_id =', files_lay_ds_hopdong_theo_matb.data.data[0].tthd_id)
              this.HienThiTTHopDongKH(files_lay_ds_hopdong_theo_matb.data.data)
            }
            if (files_lay_ds_hopdong_theo_matb.data.data.length == 0) {
              this.hdtb_id = 0
            }
          }
        } catch (error) {
          console.log(error)
          this.loading(false)
        } finally {
          this.loading(false)
          await this.HienThiTT_DanhBa(this.txtMaTB)
          this.HienThi_KhoanMuc_ThanhToan(this.hdtb_id)
        }
      }
    },

    getDichvu() {
      api.getDichvu(this.axios).then((res) => {
        this.options.dichvu = res.data.data ? res.data.data : []
        if (this.options.dichvu) {
          this.model.dichvu = this.options.dichvu[0].DICHVUVT_ID
        }
      })
    },

    getDonviTT() {
      api
        .getDonviTT(this.axios, {
          loaidv_id: 2
        })
        .then((res) => (this.options.donviTT = res.data.data ? res.data.data : []))
    },

    getDonviQL() {
      api
        .getDonviTT(this.axios, {
          loaidv_id: 3
        })
        .then((res) => (this.options.donviQL = res.data.data ? res.data.data : []))
    },

    getPhuluc() {
      api
        .getPhuluc(this.axios, {
          vhdkh_id: 1610215,
          vtthd_id: 1
        })
        .then((res) => {
          this.options.phuluc = res.data.data ? res.data.data : []
          //this.model.phuluc = this.options.phuluc ? this.options.phuluc[0] : {}
        })
    },

    getTTKM(vhdtb_id) {
      // console.log(vhdtb_id);
      api
        .getTTKM(this.axios, {
          vhdtb_id,
          vkieuld_id: this.model.kieuyeucau,
          vthuebao_id: this.thuebao_id
        })
        .then((res) => (this.options.tienkhoan = res.data.data ? res.data.data : []))
    },

    async HienThi_KhoanMuc_ThanhToan(vhdtb_id) {
      try {
        if (!this.model.kieuyeucau) return
        if (isNaN(vhdtb_id)) return
        var json_data_lay_ds_kmtt_thukhac = JSON.stringify({
          vhdtb_id: vhdtb_id,
          vkieuld_id: this.model.kieuyeucau,
          vthuebao_id: this.thuebao_id
        })
        var link_lay_ds_kmtt_thukhac = '/web-quantri/thutienphatsinh/lay_ds_kmtt_thukhac_v2'
        const files_lay_ds_kmtt_thukhac = await this.axios.post(link_lay_ds_kmtt_thukhac, json_data_lay_ds_kmtt_thukhac, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_lay_ds_kmtt_thukhac.data.data && files_lay_ds_kmtt_thukhac.data.error_code == this.success_code) this.options.tienkhoan = files_lay_ds_kmtt_thukhac.data.data
        else this.options.tienkhoan = []
      } catch (err) {
        this.$toast.error(err.message || 'Lỗi không xác định')
      }
    },

    async HienThiTTHopDongKH(ds) {
      this.thuebao_id = ds[0].thuebao_id
      if (ds[0].tthd_id != 1 && ds[0].tthd_id != 2) {
        return
      }
      try {
        if (ds.length > 0) {
          this.thuebao_id = ds[0].thuebao_id
          this.loaikh_id_new = ds[0].loaikh_id
          this.hdkh_id = ds[0].hdkh_id
          this.tthd_id = ds[0].tthd_id
          this.vmuccuoc_id = ds[0].muccuoc_id
          this.hdtb_id = ds[0].hdtb_id
          this.phieu_id = ds[0].phieucskh_id
          this.ngay_ht = ds[0].ngay_ht
          this.hdkh_id = ds[0].hdkh_id
          this.ngay_gv = ds[0].ngay_yc
          this.p_tien = ds[0].tongtien
          console.log('phieu_id =', this.phieu_id)
          if (ds[0].hdkh_cha_id != 0) {
            this.hdkh_cha_id = ds[0].hdkh_cha_id

            var json_data_map_id_ma_gd = JSON.stringify({
              in_dk: 'where hdkh_id = ' + this.hdkh_cha_id,
              in_id_neo: 'ma_gd',
              in_table: DatabaseConstants.DB2 + '.hd_khachhang'
            })
            var link_map_id_ma_gd = '/web-danhba/gan_nguongcuocnong/map_id'
            const files_map_id_ma_gd = await this.axios.post(link_map_id_ma_gd, json_data_map_id_ma_gd, {
              headers: {
                'Content-Type': 'application/json'
              }
            })

            if (files_map_id_ma_gd.data.data && files_map_id_ma_gd.data.error_code == this.success_code) this.txtMaGD_Cha = files_map_id_ma_gd.data.data
          } else {
            this.hdkh_cha_id = 0
            this.txtMaGD_Cha = ''
          }
          this.txtMaGD = ds[0].ma_gd
          this.txtMaHD = ds[0].ma_hd
          this.txtMaKH = ds[0].ma_kh
          if (ds[0].khachhang_id != 0) this.khachhang_id = ds[0].khachhang_id
          this.txtTenKH = this.ChuanHoaTen(ds[0].ten_kh)
          this.txtDiaChiKH = ds[0].diachi_kh
          this.txtSoDT = ds[0].so_dt

          this.model.ngay_yc = this.formatDate(this.getDate(ds[0].ngay_yc), 'DD/MM/YYYY HH:mm:ss')
          // this.model.ngay_lhd = this.formatDate(this.getDate(ds[0].ngaylap_hd), 'DD/MM/YYYY HH:mm:ss')
          this.model.ngay_lhd = moment(ds[0].ngaylap_hd, 'DD/MM/YYYY hh:mm:ss').format('DD/MM/YYYY')
          console.log(ds[0])
          //this.model.ngay_yc = moment(new Date(ds[0].ngay_yc)).format("DD/MM/YYYY HH:mm:ss")
          //this.model.ngay_lhd = moment(new Date(ds[0].ngaylap_hd)).format("DD/MM/YYYY HH:mm:ss")

          var json_data_map_id_hdtt_id = JSON.stringify({
            in_dk: 'where hdkh_id = ' + this.hdkh_id,
            in_id_neo: 'hdtt_id',
            in_table: DatabaseConstants.DB2 + '.hd_thanhtoan'
          })
          var link_map_id_hdtt_id = '/web-danhba/gan_nguongcuocnong/map_id'
          const files_map_id_hdtt_id = await this.axios.post(link_map_id_hdtt_id, json_data_map_id_hdtt_id, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_map_id_hdtt_id.data.data && files_map_id_hdtt_id.data.error_code == this.success_code) this.hdtt_id = files_map_id_hdtt_id.data.data

          this.txtTenTT = ds[0].ten_tt != null ? ds[0].ten_tt : ''
          this.txtDiaChiTT = ds[0].diachi_tt != null ? ds[0].diachi_tt : ''

          await this.sleep(500)

          var json_data_lay_ds_hdtb_thukhac = JSON.stringify({
            vhdkh_id: this.hdkh_id,
            vtthd_id: this.tthd_id
          })
          var link_lay_ds_hdtb_thukhac = '/web-quantri/thutienphatsinh/lay_ds_hdtb_thukhac'
          const files_lay_ds_hdtb_thukhac = await this.axios.post(link_lay_ds_hdtb_thukhac, json_data_lay_ds_hdtb_thukhac, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          await this.HienThi_KhoanMuc_ThanhToan(ds[0].ma_tb)
          // console.log(files_lay_ds_hdtb_thukhac.data.data)
          if (files_lay_ds_hdtb_thukhac.data.data && files_lay_ds_hdtb_thukhac.data.error_code == this.success_code) await this.HienThiDanhSacHDTB(files_lay_ds_hdtb_thukhac.data.data)

          this.SetButton(3)
        } else {
          this.SetButton(0)
        }
      } catch (err) {
        this.$toast.error(err.message || 'Lỗi không xác định')
      }
    },

    async HienThiTT_DanhBa(ma_tb) {
      // if(this.tthd_id != null &&(this.tthd_id != 1 || this.tthd_id != 2)){
      //   console.log(this.tthd_id);
      //   return;
      // }
      // console.log('a');
      var json_data_lay_danhba_theo_matb = JSON.stringify({ ma_tb: ma_tb, dichvuvt_id: this.model.dichvu, donvi_dl_id: 0 })
      var link_lay_danhba_theo_matb = '/web-quantri/thutienphatsinh/lay_danhba_theo_matb_thutienphatsinh'
      const files_lay_danhba_theo_matb = await this.axios.post(link_lay_danhba_theo_matb, json_data_lay_danhba_theo_matb, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      if (files_lay_danhba_theo_matb.data.data && files_lay_danhba_theo_matb.data.error_code == this.success_code) {
        let ds = files_lay_danhba_theo_matb.data.data
        this.dsDBTB = ds
        this.dichvuvt_id = ds.dichvuvt_id
        if (this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb, '0', this.model.dichvu, LoaiHopDong.THU_KHAC) == true) return

        this.txtMaKH = ds.ma_kh
        this.txtMaHD = ds.ma_hd
        this.khachhang_id = ds.khachhang_id
        this.thuebao_id = ds.thuebao_id
        this.txtTenKH = this.ChuanHoaTen(ds.ten_kh)
        this.txtDiaChiKH = this.ChuanHoaTen(ds.diachi_kh)
        this.txtSoDT = ds.so_dt
        this.txtSoAo = ds.ma_lt
        this.txtMaTB = ds.ma_tb
        this.loaikh_id_new = ds.loaikh_id
        this.txtTenTB = this.ChuanHoaTen(ds.ten_tb)
        this.txtDiaChiTB = ds.diachi_tb
        this.txtDiaChiLD = ds.diachi_ld
        this.txtTenTT = ds.ten_tt
        this.txtDiaChiTT = ds.diachi_tt
        this.loaitb_id = ds.loaitb_id
        this.model.loaihinh = this.loaitb_id
        this.donvi_id = ds.donviql_id
        this.tentocdo = ds.ten_tocdo
        this.tenmuccuoc = ds.tenmuccuoc
        this.model.dvtt = ds.donvi_id

        this.model.dvql = this.donvi_id
        /*this.model.ngay_yc = new Date().toLocaleString('en-GB', { dateStyle: 'short' })*/
        this.model.ngay_yc = moment(new Date()).format('DD/MM/YYYY')

        // this.model.ngay_lhd = this.formatDate(this.getDate(ds.ngaylap_hd), 'DD/MM/YYYY')
        this.model.ngay_lhd = moment(ds.ngaylap_hd, 'YYYY-MM-DD hh:mm:ss').format('DD/MM/YYYY')
        console.log(ds.ngaylap_hd)
        console.log(this.model.ngay_lhd)
        //this.model.ngay_lhd = moment(new Date(ds.ngaylap_hd)).format("DD/MM/YYYY")
        this.vdoituong_id = ds.doituong_id

        this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id)

        //this.secondFunction()

        await this.HT_GIAODIEN_DONVI()
        var json_data_sp_lay_ds_dbtb_dv = JSON.stringify({
          thuebao_id: this.thuebao_id
        })
        var link_sp_lay_ds_dbtb_dv = '/web-hopdong/dichvu-ca/sp_lay_ds_dbtb_dv'
        const files_sp_lay_ds_dbtb_dv = await this.axios.post(link_sp_lay_ds_dbtb_dv, json_data_sp_lay_ds_dbtb_dv, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_sp_lay_ds_dbtb_dv.data.data && files_sp_lay_ds_dbtb_dv.data.error_code == this.success_code) {
          for (let key in this.dtControl) {
            if (this.dtControl.hasOwnProperty(key)) {
              if (this.dtControl[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                var dr = files_sp_lay_ds_dbtb_dv.data.data.filter((x) => x.loaidv_id == LOAI_DV.TRAM_VT)
                if (dr.length > 0) {
                  let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                  element.value = dr[0].donvi_id
                }
              }
            }
          }
        }
      } else {
        let filter = this.options.dichvu.filter((item) => item.DICHVUVT_ID === this.model.dichvu)
        this.$toast.error('Không tìm thấy thông tin về Số máy/Acc ' + ma_tb + ' đối với dịch vụ ' + filter[0].TEN_DVVT)
      }
    },

    KIEMTRA_MA_TB_LAP_HOPDONG_KHAC: async function(ma_tb, ma_gd, vdichvuvt_id, vloaihd_id) {
      let result = false
      let data = {
        ma_tb: ma_tb,
        ma_gd: ma_gd,
        dichvuvt_id: vdichvuvt_id,
        loaihd_id: vloaihd_id
      }
      try {
        if (ma_tb != '') {
          let ds = []
          ds = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac', data)
          if (ds.data.data && ds.data.error_code == this.success_code) {
            result = true
            let message = 'Số máy/Acc: ' + ma_tb + ' đang được lập bởi hợp đồng (phụ lục) ' + ds[0]['TEN_LOAIHD'] + ' có mã giao dịch ' + ds[0]['MA_GD'] + ' do User ' + ds[0]['NGUOI_CN'] + ' thuộc đơn vị ' + ds[0]['TEN_DV'] + ' thực hiện vào ngày ' + ds[0]['NGAY_YC'] + ''

            this.$toast.error(message || 'Lỗi không xác định')
          }
        }

        return result
      } catch (ex) {
        if (ex.toString().includes('discarded')) {
          return await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac', data)
        }

        return result
      }
    },

    ChuanHoaTen(ten) {
      if (!ten) return ''
      let tenNew = ''
      let strCon = ten.trim()
      let strXL = ''
      let k = 0
      let found = true
      if (strCon.length < 1) return
      strCon = strCon.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = strCon.indexOf(' ')
          if (k > 0) {
            strXL = strCon.substring(0, k)
            strCon = strCon.substring(k + 1)
            strXL = this.ChuyenChuHoa(strXL)
            if (tenNew !== '') {
              tenNew += ' ' + strXL
            } else tenNew = strXL
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXL = strCon
          strXL = this.ChuyenChuHoa(strXL)
          if (tenNew !== '') {
            tenNew = tenNew + ' ' + strXL
          } else {
            tenNew = strXL
          }
        }
        return tenNew
      } catch (e) {
        return ten
      }
    },

    clearTheSpace(st) {
      let st1 = ''
      st = st.trim()
      while (st.length != 0) {
        st += ' '
        let i = st.indexOf(' ')
        st1 += st[0] + st.substring(1, i)
        st = st.substring(i + 1).trim()
      }
      return st1.trim()
    },

    Catdaunhay(str) {
      str = str.replace("'", '')
      let st = str.split('').join(',')
      return st
    },

    async HienThiDanhSacHDTB(ds) {
      try {
        if (ds.length > 1) this.enableButton('tsbtnXoaTB')
        else this.disableButton('tsbtnXoaTB')

        //await this.sleep(100);
        //this.options.phuluc = []
        //await this.sleep(200);
        this.options.phuluc = ds
      } catch (err) {
        this.$toast.error(err.message || 'Lỗi không xác định')
      }
    },

    SetButton(kieu) {
      this.disableButton('tsbtnNhapMoi')
      this.disableButton('tsbtnGhiLai')
      this.disableButton('tsbtnXoa')
      this.disableButton('tsbtnHuyBo')
      this.disableButton('tsbtnThanhToan')

      if (kieu == -1) {
        //Bat dau
        if(document.getElementById('txtMaTB'))
        document.getElementById('txtMaTB').focus()
        this.enableButton('tsbtnGhiLai')
        this.enableButton('tsbtnHuyBo')
      }

      if (kieu == 0) {
        //Bat dau
        document.getElementById('txtMaTB').focus()
        this.enableButton('tsbtnNhapMoi')
        this.Clear()
      }

      if (kieu == 1) {
        //Them moi
        document.getElementById('txtMaTB').focus()
        this.enableButton('tsbtnGhiLai')
        this.enableButton('tsbtnHuyBo')
        this.Clear()
      }

      if (kieu == 2) {
        //Huy
        document.getElementById('txtMaTB').focus()
        this.enableButton('tsbtnNhapMoi')
        this.enableButton('tsbtnXoa')
        this.Clear()
      }

      if (kieu == 3) {
        //Edit
        document.getElementById('txtMaTB').focus()
        this.enableButton('tsbtnNhapMoi')
        this.enableButton('tsbtnXoa')
        this.enableButton('tsbtnGhiLai')
        this.enableButton('tsbtnHuyBo')
        this.enableButton('tsbtnThanhToan')
      }
      if (kieu == 4) {
        //Edit ( chưa thanh toán )
        document.getElementById('txtMaTB').focus()
        this.enableButton('tsbtnNhapMoi')
        this.enableButton('tsbtnXoa')
        this.enableButton('tsbtnGhiLai')
        this.enableButton('tsbtnHuyBo')
      }
    },

    Clear() {
      this.txtMaGD = ''
      this.txtMaHD = ''
      this.txtMaKH = ''
      this.txtSoDT = ''
      this.txtTenKH = ''
      this.txtDiaChiKH = ''
      this.phieu_id = null
      this.ngay_ht = null
      this.p_tien = null
      this.ngay_gv = null
      this.txtTenTB = ''
      this.txtTenTT = ''
      this.txtDiaChiTT = ''
      this.txtDiaChiLD = ''
      this.txtDiaChiTB = ''
      this.txtSoAo = ''
      this.tthd_id = null
      this.txtGhiChu = ''
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.khachhang_id = 0
      this.hdkh_id = 0
      this.hdkh_cha_id = 0
      this.hdtt_id = 0
      this.txtMaGD_Cha = ''
      this.phiTTN = 0
      this.tentocdo = ''
      this.tenmuccuoc = ''
      this.enablePhiTTN = false
      this.vtocdo_id = 0
      this.vmuccuoc_id = 0
      this.vdoituong_id = 0
      this.model.ngay_lhd = moment(new Date()).format('DD/MM/YYYY')
      this.model.ngay_yc = moment(new Date()).format('DD/MM/YYYY')

      this.options.phuluc = []
      this.getTTKM(0)
      this.dsDBTB = null

      if(document.getElementById('divComboLoaiDV'))
      document.getElementById('divComboLoaiDV').innerHTML = ''
    },

    firstFunction() {
      return new Promise((resolve, reject) => {
        let y = 0
        setTimeout(() => {
          for (let i = 0; i < 1000; i++) {
            y++
          }
          // console.log('Loop completed.')
          resolve(y)
        }, 1000)
        this.HT_GIAODIEN_DONVI()
      })
    },

    async secondFunction() {
      const result = await this.firstFunction()

      var json_data_sp_lay_ds_dbtb_dv = JSON.stringify({
        thuebao_id: this.thuebao_id
      })
      var link_sp_lay_ds_dbtb_dv = '/web-hopdong/dichvu-ca/sp_lay_ds_dbtb_dv'
      const files_sp_lay_ds_dbtb_dv = await this.axios.post(link_sp_lay_ds_dbtb_dv, json_data_sp_lay_ds_dbtb_dv, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      //alert('2')
      if (files_sp_lay_ds_dbtb_dv.data.data && files_sp_lay_ds_dbtb_dv.data.error_code == this.success_code) {
        for (let key in this.dtControl) {
          if (this.dtControl.hasOwnProperty(key)) {
            if (this.dtControl[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
              var dr = files_sp_lay_ds_dbtb_dv.data.data.filter((x) => x.loaidv_id == LOAI_DV.TRAM_VT)

              if (dr.length > 0) {
                let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                element.value = dr[0].donvi_id
              }
            }
          }
        }
      }
    },

    async HT_GIAODIEN_DONVI() {
      var json_data_sp_ht_giaodien_donvi_theo_loaihd = JSON.stringify({
        dichvuvt_id: this.dichvuvt_id,
        kieuld_id: this.model.kieuyeucau,
        loaidv_id: LOAI_DV.GIAODICH,
        loaihd_id: 17,
        loaihinhtb_id: this.model.loaihinh,
        muccuoc: this.vmuccuoc_id,
        tocdo: this.vtocdo_id

        //"dichvuvt_id": 4,
        //"tocdo": 4218,
        //"muccuoc": 1,
        //"loaihinhtb_id": 58,
        //"kieuld_id": 722,
        //"loaidv_id": 4,
        //"loaihd_id": 1
      })
      var link_sp_ht_giaodien_donvi_theo_loaihd = '/web-hopdong/dichvu-ca/sp_ht_giaodien_donvi_theo_loaihd'
      const files_sp_ht_giaodien_donvi_theo_loaihd = await this.axios.post(link_sp_ht_giaodien_donvi_theo_loaihd, json_data_sp_ht_giaodien_donvi_theo_loaihd, {
        headers: {
          'Content-Type': 'application/json'
        }
      })

      if (files_sp_ht_giaodien_donvi_theo_loaihd.data.data && files_sp_ht_giaodien_donvi_theo_loaihd.data.error_code == this.success_code) this.dtControl = files_sp_ht_giaodien_donvi_theo_loaihd.data.data
      else this.dtControl = null
      await this.addElement()
      //alert('1')
    },

    async addElement() {
      //await this.sleep(1000);
      // document.getElementById('divComboLoaiDV').innerHTML = ''
      if (this.dtControl == null) return
      for (let key = 0; key < this.dtControl.length; key++) {
        if (document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id']) == null) {
          var div_inforow = document.createElement('div')
          div_inforow.setAttribute('class', 'info-row')

          var div_keyw100 = document.createElement('div')
          div_keyw100.setAttribute('class', 'key w100')
          var lable = document.createElement('lable')
          lable.innerHTML = this.dtControl[key]['ten_loaidv']

          div_keyw100.appendChild(lable)
          div_inforow.appendChild(div_keyw100)

          var select = document.createElement('select')
          select.setAttribute('class', 'col-md-12 form-control')
          select.setAttribute('id', 'cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
          select.setAttribute('tag', this.dtControl[key]['loaidv_dich_id'])
          select.setAttribute('onclick', 'return false;')
          //alert('1')
          var json_data_sp_ht_donvi_combobox = JSON.stringify({
            loaidv_id: this.dtControl[key]['loaidv_dich_id']
          })
          var link_sp_ht_donvi_combobox = '/web-quantri/thutienphatsinh/sp_ht_donvi_combobox'
          const files_sp_ht_donvi_combobox = await this.axios.post(link_sp_ht_donvi_combobox, json_data_sp_ht_donvi_combobox, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_sp_ht_donvi_combobox.data.data && files_sp_ht_donvi_combobox.data.error_code == this.success_code) {
            for (let key1 in files_sp_ht_donvi_combobox.data.data) {
              var option = document.createElement('option')
              option.value = files_sp_ht_donvi_combobox.data.data[key1]['donvi_id']
              option.label = files_sp_ht_donvi_combobox.data.data[key1]['ten_dv']
              select.appendChild(option)
            }
          }

          var div_value = document.createElement('div')
          div_value.setAttribute('class', 'value')
          div_value.appendChild(select)

          div_inforow.appendChild(div_value)

          var div_inputcontainer = document.createElement('div')
          div_inputcontainer.setAttribute('class', 'input-container')
          div_inputcontainer.appendChild(div_inforow)

          var div = document.createElement('div')
          div.setAttribute('class', 'col-md-12' + ' cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
          div.setAttribute('style', 'padding: 0')
          div.appendChild(div_inputcontainer)

          var foo = document.getElementById('divComboLoaiDV')
          foo.appendChild(div)
          //await this.sleep(1000);
        }
      }

      await this.sleep(100)
      for (let key = 0; key < this.dtControl.length; key++) {
        var foo = document.getElementsByClassName('col-md-12' + ' cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
        for (let key1 = 0; key1 < foo.length; key1++) {
          if (key1 != 0) foo[key1].remove()
        }
      }
    },

    async clickButton(key) {
      if (key == 'nhapmoi') {
        this.SetButton(1)
      } else if (key == 'ghilai') {
        this.CapNhat()
      } else if (key == 'huy') {
        this.SetButton(2)
      } else if (key == 'xoahd') {
        this.tsbtnXoa_Click()
      } else if (key == 'thanhtoan') {
        let user_ipcc = ''
        if (this.$route.query.user_ipcc && this.$route.query.user_ipcc != '') {
          user_ipcc = this.$route.query.user_ipcc
        }

        this.$router.push({
          name: 'Payment',
          params: { magd: this.txtMaGD, ob_id: this.preData.ob_id, user_ipcc: user_ipcc }
        })
      } else if (key == 'laymagd') {
        this.$refs.popupSearchContract_magd.showModal()
      } else if (key == 'laymagdcha') {
        this.$refs.popupSearchContract_magdcha.showModal()
      } else if (key == 'themtb') {
        this.tsbtnThemTB_Click()
      } else if (key == 'xoatb') {
        this.tsbtnXoaTB_Click()
      }
    },

    async kiemtra_dulieu() {
      console.log('b')
      //alert(this.thuebao_id)
      if (this.thuebao_id != 0) {
        var json_data_lay_danhba_theo_matb = JSON.stringify({ ma_tb: this.txtMaTB, dichvuvt_id: this.model.dichvu, donvi_dl_id: null })
        var link_sp_lay_danhba_theo_matb = '/web-quantri/thutienphatsinh/lay_danhba_theo_matb_thutienphatsinh'
        const files_lay_danhba_theo_matb = await this.axios.post(link_sp_lay_danhba_theo_matb, json_data_lay_danhba_theo_matb, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (!files_lay_danhba_theo_matb.data.data) {
          this.$toast.error('Không tìm thấy thông tin về mã thuê bao ' + this.txtMaTB)
          console.log('Không tìm thấy thông tin về mã thuê bao ' + this.txtMaTB)
          return false
        }
        if (files_lay_danhba_theo_matb.data.data && files_lay_danhba_theo_matb.data.error_code == this.success_code) {
          if (files_lay_danhba_theo_matb.data.data.length == 0) {
            let filter = this.options.dichvu.filter((item) => item.DICHVUVT_ID === this.model.dichvu)
            this.$toast.error('Không tìm thấy thông tin về mã thuê bao ' + this.txtMaTB + ' đối với dịch vụ ' + filter[0].TEN_DVVT)
            return false
          } else {
            this.loaikh_id_new = files_lay_danhba_theo_matb.data.data.loaikh_id
            this.vmuccuoc_id = files_lay_danhba_theo_matb.data.data.muccuoc_id
          }
        } else {
          this.$toast.error('Không tìm thấy thông tin về mã thuê bao ' + this.txtMaTB)
          return false
        }
      }

      if (this.model.dichvu != DichVuVienThong.DI_DONG && this.thuebao_id == 0) {
        this.$toast.error('Chưa có thuê bao ID cho dịch vụ ' + this.model.dichvu + ' . Bạn cần nhập mã thuê bao và ấn Enter !')
        return false
      }

      if (this.txtMaTB == '') {
        this.$toast.error('Bạn chưa nhập Mã thuê bao !')
        return false
      }

      if (document.getElementById('tsbtnNhapMoi').style.pointerEvents == 'none') {
        var json_data_sp_kiemtra_dulieu_thutienphatsinh = JSON.stringify({
          p_tocdo_id: null,
          p_dichvuvt_id: this.model.dichvu,
          p_kieu: 1,
          p_loaihd_id: 17,
          p_kieuld_id: null,
          p_loaitb_id: null,
          p_ma_tb: this.txtMaTB,
          p_muccuoc_id: null
        })
        var link_sp_kiemtra_dulieu_thutienphatsinh = '/web-quantri/thutienphatsinh/sp_kiemtra_dulieu_thutienphatsinh'
        const files_sp_kiemtra_dulieu_thutienphatsinh = await this.axios.post(link_sp_kiemtra_dulieu_thutienphatsinh, json_data_sp_kiemtra_dulieu_thutienphatsinh, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        debugger

        if (files_sp_kiemtra_dulieu_thutienphatsinh.data.data && files_sp_kiemtra_dulieu_thutienphatsinh.data.error_code == this.success_code) {
          //var isEmpty = Object.values(files_sp_kiemtra_dulieu_thutienphatsinh.data.data[0]).every(x => x === null || x === '');
          if (files_sp_kiemtra_dulieu_thutienphatsinh.data.data[0].scount > 0) {
            debugger
            this.$toast.error('"Thuê bao đang được lập hợp đồng thu tiền phát sinh chưa hoàn thành. Bạn không thể thêm mới !"')
            debugger
            return false
          }
        }
      }

      if (this.model.dvtt == '') {
        this.$toast.error('Chưa có đơn vị quản lý địa chỉ thanh toán !')
        return false
      }

      if (this.model.dvql == '') {
        this.$toast.error('Chưa có đơn vị quản lý địa chỉ thuê bao !')
        return false
      }

      if (this.txtTenKH == '') {
        this.$toast.error('Bạn chưa nhập tên khách hàng !')
        return false
      }

      if (this.txtDiaChiKH == '') {
        this.$toast.error('Bạn chưa nhập địa chỉ khách hàng !')
        return false
      }

      if (this.txtTenTT == '') {
        this.$toast.error('Bạn chưa nhập tên thanh toán !')
        return false
      }

      if (this.txtDiaChiTT == '') {
        this.$toast.error('Bạn chưa nhập địa chỉ thanh toán !')
        return false
      }

      if (this.txtTenTB == '') {
        this.$toast.error('Bạn chưa nhập tên thuê bao !')
        return false
      }

      if (this.txtDiaChiTB == '') {
        this.$toast.error('Bạn chưa nhập địa chỉ thuê bao !')
        return false
      }

      if (this.txtDiaChiLD == '') {
        this.$toast.error('Bạn chưa nhập địa chỉ lắp đặt !')
        return false
      }

      let istienkhoan = false
      for (let i = 0; i < this.options.tienkhoan.length; i++) {
        if (String(this.options.tienkhoan[i].tongtien).trim() != '0' && String(this.options.tienkhoan[i].tongtien).trim() != '') {
          istienkhoan = true
          break
        }
      }
      if (istienkhoan == false) {
        this.$toast.error('Bạn chưa nhập tiền khoản mục thanh toán !')
        return false
      }

      //let ischecktienkhoan = false
      //for (let i = 0; i < this.checkchecktienkhoan.length; i++) {
      //  if (this.checkchecktienkhoan[i] == 0) {
      //    ischecktienkhoan = true
      //    break
      //  }
      //}
      //if (ischecktienkhoan == false) {
      //  this.$toast.error("Bạn chưa nhập tiền khoản mục thanh toán !")
      //  return false
      //}
      debugger
      return true
    },

    async CapNhat() {
      try {
        if ((await this.kiemtra_dulieu()) == false) {
          debugger
          return
        }
        debugger
        await this.loading(true)

        var json_data_lay_huonggiao_tiepnhan = JSON.stringify({
          kieuLdId: this.model.kieuyeucau,
          loaiHdId: LoaiHopDong.THU_KHAC,
          loaiTbId: this.loaitb_id,
          mucCuocId: this.vmuccuoc_id,
          tocDoId: this.vtocdo_id
        })
        var link_lay_huonggiao_tiepnhan = '/web-hopdong/hopdong/lay_huonggiao_tiepnhan'
        const files_lay_huonggiao_tiepnhan = await this.axios.post(link_lay_huonggiao_tiepnhan, json_data_lay_huonggiao_tiepnhan, {
          headers: {
            'Content-Type': 'application/json'
          }
        })

        if (files_lay_huonggiao_tiepnhan.data.data && files_lay_huonggiao_tiepnhan.data.error_code == this.success_code) {
          if (files_lay_huonggiao_tiepnhan.data.data.length == 0) {
            this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return
          }
          if (files_lay_huonggiao_tiepnhan.data.data['huonggiao_id']) this.huonggiao_tn = files_lay_huonggiao_tiepnhan.data.data['huonggiao_id']
          if (files_lay_huonggiao_tiepnhan.data.data['quytrinh_id']) this.quytrinh_id = files_lay_huonggiao_tiepnhan.data.data['quytrinh_id']
        }

        if (document.getElementById('tsbtnNhapMoi').style.pointerEvents == 'none') {
          await this.TaoDuLieu(true)
          debugger
          if ((await this.ThemMoi_HopDong_Thukhac()) == true) {
            this.$toast.success('Thêm mới hợp đồng thành công !')
          }
        } else {
          await this.TaoDuLieu(false)
          debugger
          if ((await this.CapNhat_HopDong_Thukhac()) == true) {
            this.$toast.success('Cập nhật hợp đồng thành công !')
          }
        }

        var json_data_lay_ds_hopdong_theo_ma_gd = JSON.stringify({
          in_loaihd_id: LoaiHopDong.THU_KHAC,
          in_ma_gd: this.txtMaGD,
          in_tthd_id: TrangThaiHD.MOI,
          in_donvi_dl_id: 0
        })
        var link_lay_ds_hopdong_theo_ma_gd = '/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd'
        const files_lay_ds_hopdong_theo_ma_gd = await this.axios.post(link_lay_ds_hopdong_theo_ma_gd, json_data_lay_ds_hopdong_theo_ma_gd, {
          headers: {
            'Content-Type': 'application/json'
          }
        })

        debugger
        if (files_lay_ds_hopdong_theo_ma_gd.data.data && files_lay_ds_hopdong_theo_ma_gd.data.error_code == this.success_code) {
          await this.HienThiTTHopDongKH(files_lay_ds_hopdong_theo_ma_gd.data.data)
          this.HienThiTT_DanhBa(this.txtMaTB)
        }

        await this.sleep(100)
        await this.$refs.gridDanhSach_HDTB.setCurrentSelectedRow(this.current)

        await this.SetButton(3)
        await this.loading(false)
      } catch (err) {
        this.$toast.error(err.message)
      }
    },

    async ThemMoi_HopDong_Thukhac() {
      var ob_id = this.preData.ob_id
      var json_data = JSON.stringify({
        vds_thongtin: JSON.stringify([
          {
            OB_ID: ob_id
          }
        ]),
        vds_phi_ttn: JSON.stringify([
          {
            PHI_TTN_CBX: this.enablePhiTTN ? 1 : 0,
            PHI_TTN: this.enablePhiTTN ? this.phiTTN.replace(/,/g, '') : 0,
            TIEN: this.txtTienKM.replace(/,/g, ''),
            VAT: this.txtVatKM.replace(/,/g, ''),
            TONGTIEN: this.enablePhiTTN ? this.tongtienExtended.replace(/,/g, '') : this.txtTongTienKM.replace(/,/g, ''),
            KIEULD_ID: this.model.kieuyeucau,
            KHOANMUC_TT: ''
          }
        ]),
        dscttienhd: JSON.stringify(this.dsCTTienHD),
        dshdkh: JSON.stringify(this.dsHDKH),
        dshdtb: JSON.stringify(this.dsHDTB),
        dshdtbdv: JSON.stringify(this.dsHDTBDV),
        dshdtt: JSON.stringify(this.dsHDTT),
        huonggiao_tn: this.huonggiao_tn,
        vkieu: 0
      })
      var link_fn_capnhat_thutienphatsinh = '/web-quantri/thutienphatsinh/fn_capnhat_thutienphatsinh'
      const files_fn_capnhat_thutienphatsinh = await this.axios.post(link_fn_capnhat_thutienphatsinh, json_data, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      debugger
      if (files_fn_capnhat_thutienphatsinh.data.data && files_fn_capnhat_thutienphatsinh.data.data != 1) {
        this.$toast.error(files_fn_capnhat_thutienphatsinh.data.data || 'Lỗi không xác định')
        return false
      }
      if (files_fn_capnhat_thutienphatsinh.data.data && files_fn_capnhat_thutienphatsinh.data.error_code == this.success_code && files_fn_capnhat_thutienphatsinh.data.data == '1') return true
      else return false
    },

    async CapNhat_HopDong_Thukhac() {
      var ob_id = this.preData.ob_id
      var json_data = JSON.stringify({
        vds_thongtin: JSON.stringify([
          {
            OB_ID: ob_id
          }
        ]),
        vds_phi_ttn: JSON.stringify([
          {
            PHI_TTN_CBX: this.enablePhiTTN ? 1 : 0,
            PHI_TTN: this.enablePhiTTN ? this.phiTTN.replace(/,/g, '') : 0,
            TIEN: this.txtTienKM.replace(/,/g, ''),
            VAT: this.txtVatKM.replace(/,/g, ''),
            TONGTIEN: this.enablePhiTTN ? this.tongtienExtended.replace(/,/g, '') : this.txtTongTienKM.replace(/,/g, ''),
            KIEULD_ID: this.model.kieuyeucau,
            KHOANMUC_TT: ''
          }
        ]),
        dscttienhd: JSON.stringify(this.dsCTTienHD),
        dshdkh: JSON.stringify(this.dsHDKH),
        dshdtb: JSON.stringify(this.dsHDTB),
        dshdtbdv: JSON.stringify(this.dsHDTBDV),
        dshdtt: JSON.stringify(this.dsHDTT),
        huonggiao_tn: this.huonggiao_tn,
        vkieu: 1
      })
      var link_fn_capnhat_thutienphatsinh = '/web-quantri/thutienphatsinh/fn_capnhat_thutienphatsinh'
      const files_fn_capnhat_thutienphatsinh = await this.axios.post(link_fn_capnhat_thutienphatsinh, json_data, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      debugger
      if (files_fn_capnhat_thutienphatsinh.data.data && files_fn_capnhat_thutienphatsinh.data.data != 1) {
        this.$toast.error(files_fn_capnhat_thutienphatsinh.data.data || 'Lỗi không xác định')
        return false
      }
      if (files_fn_capnhat_thutienphatsinh.data.data && files_fn_capnhat_thutienphatsinh.data.error_code == this.success_code && files_fn_capnhat_thutienphatsinh.data.data == '1') return true
      else return false
    },

    async TaoDuLieu(themmoi) {
      await this.TaoDuLieu_HDKH(themmoi)
      await this.TaoDuLieu_HDTT(themmoi)
      await this.TaoDuLieu_HDTB(themmoi)
      await this.TaoDuLieu_HDTBDV(themmoi)
      await this.TaoDuLieu_CT_TIENHD(true)
    },

    async TaoDuLieu_HDKH(themmoi) {
      if (themmoi == true) {
        var json_data_get_keys = JSON.stringify({
          keyname: 'HD_KHACHHANG'
        })
        var link_get_keys = '/web-hopdong/khoiphucthanhly/get_keys'
        const files_get_keys = await this.axios.post(link_get_keys, json_data_get_keys, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_get_keys.data.data && files_get_keys.data.error_code == this.success_code) this.hdkh_id = files_get_keys.data.data

        ////////////////
        var link_sinh_magd = '/web-hopdong/chuyendich/sinh_magd?loaihd_id=' + LoaiHopDong.THU_KHAC
        const files_sinh_magd = await this.axios.get(link_sinh_magd, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_sinh_magd.data.data && files_sinh_magd.data.error_code == '0') this.txtMaGD = files_sinh_magd.data.data
        else this.$toast.error(files_sinh_magd.data.message.split(':')[1].split('ORA')[0])
      }
      //this.dsHDKH = [{ "BOSUNGTB_ID": 0, "CHUCDANH_DD": "", "CTV_ID": 0, "DACTRUNG": "", "DANTOC_ID": 0, "DIACHI_KH": this.txtDiaChiKH, "DIACHI_NDD": "", "DONVI_ID": token_var.getDonViID(), "EMAIL": "", "GHICHU": "", "GIOITINH": 0, "HDKH_CHA_ID": this.hdkh_cha_id, "HDKH_ID": this.hdkh_id, "HOKHAU": "", "HTTTHI_ID": 0, "IP_CN": "", "KENHTN_ID": 0, "KHACHHANG_ID": this.khachhang_id, "KHLON_ID": 1, "KH_KT3": 0, "KIEUHD_ID": 0, "LOAIGT_ID": 0, "LOAIHD_ID": LoaiHopDong.THU_KHAC, "LOAIKH_ID": 0, "MAY_CN": token_var.getMaNhanVien(), "MA_DUAN": "", "MA_GD": this.txtMaGD, "MA_KH": this.txtMaKH, "MST": "", "MST_KH": "", "NGANHNGHE_ID": 0, "NGAYCAP": moment(new Date()).format("DD/MM/YYYY"), "NGAYHETHAN_GT": moment(new Date()).format("DD/MM/YYYY"), "NGAYLAP_HD": moment(new Date(this.model.ngay_lhd)).format("DD/MM/YYYY HH:mm:ss"), "NGAY_CN": moment(new Date()).format("DD/MM/YYYY"), "NGAY_HK": moment(new Date()).format("DD/MM/YYYY"), "NGAY_SN": moment(new Date()).format("DD/MM/YYYY"), "NGAY_YC": moment(new Date(this.model.ngay_yc)).format("DD/MM/YYYY HH:mm:ss"), "NGUOI_CN": token_var.getUserName(), "NGUOI_DD": "", "NHANVIENGT_ID": 0, "NHANVIEN_ID": Number(token_var.getNhanVienID()), "NOICAP": "", "NOICAP_HK": "", "PHANLOAIKH_ID": 0, "PHIEUCSKH_ID": 0, "QUOCTICH_ID": 0, "SO_DT": this.txtSoDT, "SO_FAX": "", "SO_GT": "", "STK": "", "TEN_KH": this.txtTenKH, "TNC1_ID": 0, "TNC2_ID": 0, "TNC3_ID": 0, "TNC4_ID": 0, "WEBSITE": "" }]
      this.dsHDKH = [
        {
          BOSUNGTB_ID: 0,
          CHUCDANH_DD: '',
          CTV_ID: 0,
          DACTRUNG: '',
          DANTOC_ID: 0,
          DIACHI_KH: this.txtDiaChiKH,
          DIACHI_NDD: '',
          DONVI_ID: token_var.getDonViID(),
          EMAIL: '',
          GHICHU: '',
          GIOITINH: 0,
          HDKH_CHA_ID: this.hdkh_cha_id,
          HDKH_ID: this.hdkh_id,
          HOKHAU: '',
          HTTTHI_ID: 0,
          IP_CN: '',
          KENHTN_ID: 0,
          KHACHHANG_ID: this.khachhang_id,
          KHLON_ID: 1,
          KH_KT3: 0,
          KIEUHD_ID: 0,
          LOAIGT_ID: 0,
          LOAIHD_ID: LoaiHopDong.THU_KHAC,
          LOAIKH_ID: this.loaikh_id_new,
          MAY_CN: token_var.getMaNhanVien(),
          MA_DUAN: '',
          MA_GD: this.txtMaGD,
          MA_KH: this.txtMaKH,
          MA_HD: this.txtMaHD,
          MST: '',
          MST_KH: '',
          NGANHNGHE_ID: 0,
          NGAYCAP: moment(new Date()).format('DD/MM/YYYY'),
          NGAYHETHAN_GT: moment(new Date()).format('DD/MM/YYYY'),
          NGAYLAP_HD: this.formatDate(this.getDate(this.model.ngay_lhd), 'DD/MM/YYYY'),
          NGAY_CN: moment(new Date()).format('DD/MM/YYYY'),
          NGAY_HK: moment(new Date()).format('DD/MM/YYYY'),
          NGAY_SN: moment(new Date()).format('DD/MM/YYYY'),
          NGAY_YC: this.formatDate(this.getDate(this.model.ngay_yc), 'DD/MM/YYYY'),
          NGUOI_CN: token_var.getUserName(),
          NGUOI_DD: '',
          NHANVIENGT_ID: 0,
          NHANVIEN_ID: Number(token_var.getNhanVienID()),
          NOICAP: '',
          NOICAP_HK: '',
          PHANLOAIKH_ID: 0,
          PHIEUCSKH_ID: 0,
          QUOCTICH_ID: 0,
          SO_DT: this.txtSoDT,
          SO_FAX: '',
          SO_GT: '',
          STK: '',
          TEN_KH: this.txtTenKH,
          TNC1_ID: 0,
          TNC2_ID: 0,
          TNC3_ID: 0,
          TNC4_ID: 0,
          WEBSITE: ''
        }
      ]

      if (themmoi == false) this.$forceUpdate()
    },

    async TaoDuLieu_HDTT(themmoi) {
      if (themmoi == true) {
        var json_data_get_keys = JSON.stringify({
          keyname: 'HD_THANHTOAN'
        })
        var link_get_keys = '/web-hopdong/khoiphucthanhly/get_keys'
        const files_get_keys = await this.axios.post(link_get_keys, json_data_get_keys, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_get_keys.data.data && files_get_keys.data.error_code == this.success_code) this.hdtt_id = files_get_keys.data.data
      }

      this.dsHDTT = [
        {
          CHU_TK: '',
          DIACHI_BC: '',
          DIACHI_CT: '',
          DIACHI_TT: this.txtDiaChiTT,
          DONVI_ID: this.model.dvtt,
          DV_QHNS: '',
          EMAIL: '',
          GHICHU: '',
          HDKH_ID: this.hdkh_id,
          HDTT_ID: this.hdtt_id,
          HTTC_ID: 0,
          HTTT_ID: 2,
          IP_CN: '',
          MACHUONG: '',
          MATB_DD: '',
          MAY_CN: token_var.getMaNhanVien(),
          MA_NDKT: '',
          MA_TT: '',
          MN_KT: '',
          MN_NSNN: '',
          MST: '',
          NGANHANG_ID: 0,
          NGAY_CN: moment(new Date()).format('DD/MM/YYYY'),
          NGUOI_CN: token_var.getUserName(),
          SO_DT: '',
          STK: '',
          TEN_TT: this.txtTenTT,
          THANHTOAN_ID: 0,
          TIEN_DM: 0,
          TUYENTHU_ID: 0
        }
      ]

      if (themmoi == false) this.$forceUpdate()
    },

    async TaoDuLieu_HDTB(themmoi) {
      if (themmoi == true) {
        var json_data_get_keys = JSON.stringify({
          keyname: 'HD_THUEBAO'
        })
        var link_get_keys = '/web-hopdong/khoiphucthanhly/get_keys'
        const files_get_keys = await this.axios.post(link_get_keys, json_data_get_keys, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_get_keys.data.data && files_get_keys.data.error_code == this.success_code) this.hdtb_id = files_get_keys.data.data
      }

      this.dsHDTB = [
        {
          CHUYENMANG: 0,
          DIACHI_LD: this.txtDiaChiLD,
          DIACHI_TB: this.txtDiaChiTB,
          DICHVUVT_ID: this.dichvuvt_id,
          DOITUONG_CT_ID: 0,
          DOITUONG_ID: this.vdoituong_id,
          DONVI_CUOI_ID: 0,
          DONVI_DAU_ID: 0,
          DONVI_ID: this.model.dvql,
          GHICHU: this.txtGhiChu,
          GOIKT_ID: 0,
          HDKH_ID: this.hdkh_id,
          HDTB_CHA_ID: 0,
          HDTB_ID: this.hdtb_id,
          HDTT_ID: this.hdtt_id,
          HTCM_ID: 0,
          IP_CN: '',
          KIEULD_ID: this.model.kieuyeucau,
          KIEU_TB: '',
          LOAITB_ID: this.model.loaihinh,
          MAY_CN: token_var.getMaNhanVien(),
          MA_TB: this.txtMaTB,
          MUCUOCTB_ID: 0,
          MUCUOC_TB: 0,
          NGAY_CN: moment(new Date()).format('DD/MM/YYYY'),
          NGUOI_CN: token_var.getUserName(),
          NHACC_CU_ID: 0,
          QUYTRINH_ID: this.quytrinh_id,
          SOLUONG: 0,
          SOTHANG_CK: 0,
          STATUS: '',
          TBHD_PL: 0,
          TEN_TB: this.txtTenTB,
          THUEBAO_CHA_ID: 0,
          THUEBAO_ID: this.thuebao_id,
          TRANGTHAIYC_ID: 0,
          TTHD_ID: TrangThaiHD.MOI
        }
      ]

      if (themmoi == false) this.$forceUpdate()
    },

    async TaoDuLieu_HDTBDV(themmoi) {
      this.dsHDTBDV = []

      for (let key in this.dtControl) {
        if (this.dtControl.hasOwnProperty(key)) {
          var rowHDTBDV = {}
          rowHDTBDV.HDTB_ID = this.hdtb_id

          let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
          rowHDTBDV.LOAIDV_ID = element.getAttribute('tag')
          rowHDTBDV.DONVI_ID = element.value
          rowHDTBDV.KIEUDV_ID = 2
          this.dsHDTBDV.push(rowHDTBDV)
        }
      }
    },

    async TaoDuLieu_CT_TIENHD(themmoi) {
      this.dsCTTienHD = []

      for (let i = 0; i < this.options.tienkhoan.length; i++) {
        if (themmoi == true) {
          var json_data_get_keys = JSON.stringify({
            keyname: 'CT_TIENHD'
          })
          var link_get_keys = '/web-hopdong/khoiphucthanhly/get_keys'
          const files_get_keys = await this.axios.post(link_get_keys, json_data_get_keys, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_get_keys.data.data && files_get_keys.data.error_code == this.success_code) this.ct_tienhd_id = files_get_keys.data.data
        }
        var rowCTTHD = {}
        rowCTTHD.CT_TIENHD_ID = this.ct_tienhd_id
        rowCTTHD.TIEN = this.options.tienkhoan[i].tien
        rowCTTHD.VAT = this.options.tienkhoan[i].vat
        rowCTTHD.LOAI_ID = 1
        rowCTTHD.ID = this.options.tienkhoan[i].khoanmuctt_id
        rowCTTHD.HDTB_ID = this.hdtb_id
        rowCTTHD.KHOANMUCTT_ID = this.options.tienkhoan[i].khoanmuctt_id
        this.dsCTTienHD.push(rowCTTHD)
      }
    },

    async HienThiTTDBMoRong(thuebao_id, dichvuvt_id) {
      //this.$set(this.form, "vtocdo_id", 0);
      //this.$set(this.form, "vmuccuoc_id", 0);
      //this.$set(this.form, "trangbi_id", 0);
      let data = {}
      if (dichvuvt_id == 1) {
        //Cố định
        data = await this.axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid', {
          thuebao_id: thuebao_id
        })
      }
      if (dichvuvt_id == 4) {
        //ADSL
        data = await this.axios.post('web-ccdv/xuly_nghiemthubaohong/lay_ds_db_adsl_theo_tbid', {
          thuebao_id: thuebao_id
        })
      }
      if (dichvuvt_id == 15 || dichvuvt_id == 13 || dichvuvt_id == 14 || dichvuvt_id == 16) {
        //ADSL
        data = await this.axios.post('web-hopdong/hopdong/lay_ds_danhba_cntt', {
          thuebao_id: thuebao_id
        })
      }
      if (dichvuvt_id == 2) {
        //Di động
        data = await this.axios.post('web-hopdong/tachnhapthuebao/lay_ds_db_dd_theo_tbid', {
          thuebao_id: thuebao_id
        })
      }
      if (dichvuvt_id == 8) {
        //MEGAWAN
        data = await this.axios.post('web-hopdong/hopdong/lay_ds_danhba_megawan', {
          thuebao_id: thuebao_id
        })
      }
      if (dichvuvt_id == 9) {
        //TSL
        data = await this.axios.post('web-hopdong/hopdong/lay_ds_danhba_tsl', {
          thuebao_id: thuebao_id
        })
      }
      if (dichvuvt_id == 10) {
        //Gphone
        data = await this.axios.post('web-hopdong/hopdong/lay_ds_danhba_gp', {
          thuebao_id: thuebao_id
        })
      }
      if (dichvuvt_id == 12) {
        //Megaeys
        data = await this.axios.post('web-hopdong/hopdong/lay_ds_danhba_megaeys', {
          thuebao_id: thuebao_id
        })
      }
      if (dichvuvt_id == 50) {
        //CNTT
        data = await this.axios.post('web-hopdong/hopdong/lay_ds_danhba_cntt', {
          thuebao_id: thuebao_id
        })
      }

      if (data.data && data.data.data) {
        let rs = data.data.data
        if (rs.length > 0) {
          if (rs[0].tocdo_ID) this.vtocdo_id = rs[0].tocdo_ID
          if (rs[0].muccuoc_ID) this.vmuccuoc_id = rs[0].muccuoc_ID
          //if (rs[0].trangbi_ID)
          //  this.$set(this.form, "trangbi_id", rs[0].trangbi_ID);
        }
      }
    },

    HienThiTTHopDongTB(ds) {
      // console.log(ds);
      if (ds.length > 0) {
        this.dsDBTB = ds
        this.hdtb_id = ds[0].hdtb_id
        if (ds[0].thuebao_id != '') this.thuebao_id = ds[0].thuebao_id
        this.txtMaTB = ds[0].ma_tb
        this.txtTenTB = this.ChuanHoaTen(ds[0].ten_tb)
        this.txtDiaChiTB = this.ChuanHoaTen(ds[0].diachi_tb)
        this.txtDiaChiLD = this.ChuanHoaTen(ds[0].diachi_ld)
        this.dichvuvt_id = ds[0].dichvuvt_id
        this.model.dichvu = this.dichvuvt_id
        this.loaitb_id = ds[0].loaitb_id
        this.model.loaihinh = this.loaitb_id
        this.tentocdo = ds[0].ten_tocdo
        this.tenmuccuoc = ds[0].tenmuccuoc
        this.vdoituong_id = ds[0].doituong_id
        this.model.kieuyeucau = ds[0].kieuld_id
        this.txtGhiChu = ds[0].ghichu
        this.model.dvql = ds[0].donvi_id
        this.HienThiTTMoRong(this.hdtb_id, this.dichvuvt_id)
      }
    },

    async HienThiTTMoRong(hdtb_id, dichvuvt_id) {
      let ds
      this.vtocdo_id = 0
      this.vmuccuoc_id = 0
      this.txtSoAo = ''
      switch (dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          var link_lay_ds_hopdong_thuebao_codinh = '/web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh?hdtbId=' + hdtb_id + ''
          ds = await this.axios.get(link_lay_ds_hopdong_thuebao_codinh, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          break
        case DichVuVienThong.ADSL:
          break
        case DichVuVienThong.TRUNGTAM_DULIEU:
          var link_lay_ds_hopdong_thuebao_adsl = '/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=' + hdtb_id + ''
          ds = await this.axios.get(link_lay_ds_hopdong_thuebao_adsl, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (ds.data.data && ds.data.error_code == this.success_code) {
            this.vtocdo_id = ds[0].tocdo_id
            this.vmuccuoc_id = ds[0].muccuoc_id
            this.txtSoAo = ds[0].ma_lt
          }

          break
        case DichVuVienThong.EMAIL:
          var link_lay_ds_hopdong_thuebao_adsl = '/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=' + hdtb_id + ''
          ds = await this.axios.get(link_lay_ds_hopdong_thuebao_adsl, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (ds.data.data && ds.data.error_code == this.success_code) {
            this.vtocdo_id = ds[0].tocdo_id
            this.vmuccuoc_id = ds[0].muccuoc_id
          }
          break
        case DichVuVienThong.MEGA_EYES:
          var link_lay_ds_hopdong_thuebao_adsl = '/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=' + hdtb_id + ''
          ds = await this.axios.get(link_lay_ds_hopdong_thuebao_adsl, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (ds.data.data && ds.data.error_code == this.success_code) this.vtocdo_id = ds[0].tocdo_id
          break
        case DichVuVienThong.MEGAWAN:
          var link_lay_ds_hopdong_thuebao_mgwan = '/web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan?hdtbId=' + hdtb_id + ''
          ds = await this.axios.get(link_lay_ds_hopdong_thuebao_mgwan, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (ds.data.data && ds.data.error_code == this.success_code) {
            this.vtocdo_id = ds[0].tocdo_id
            this.vmuccuoc_id = 0
          }
          break
        case DichVuVienThong.TSL:
          var json_data_lay_ds_hdtb_tsl_theo_hdtb_id = JSON.stringify({
            hdtb_id: hdtb_id
          })
          var link_lay_ds_hdtb_tsl_theo_hdtb_id = '/web-hopdong/DiemChiaTrungGianTSL/lay_ds_hdtb_tsl_theo_hdtb_id'
          ds = await this.axios.post(link_lay_ds_hdtb_tsl_theo_hdtb_id, json_data_lay_ds_hdtb_tsl_theo_hdtb_id, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (ds.data.data && ds.data.error_code == this.success_code) {
            this.vtocdo_id = ds[0].tocdo_id
            this.vmuccuoc_id = 0
            this.txtSoAo = ds[0].ma_lt
          }
          break
        case DichVuVienThong.GPHONE:
          var link_lay_ds_hopdong_thuebao_gp = '/web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp?hdtbId=' + hdtb_id + ''
          ds = await this.axios.get(link_lay_ds_hopdong_thuebao_gp, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          break
        default:
          var link_lay_ds_hopdong_thuebao_codinh = '/web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh?hdtbId=' + hdtb_id + ''
          ds = await this.axios.get(link_lay_ds_hopdong_thuebao_codinh, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          break
      }
    },
    changeKieuYeuCau() {
      if (this.model.kieuyeucau == 136) {
        this.txtMaGD_Cha = ''
        this.disabledMaGD_Cha = true
      } else {
        this.disabledMaGD_Cha = false
      }
      this.HienThi_KhoanMuc_ThanhToan(this.hdtb_id)
    },
    async gridDanhSach_FocusedRowChanged(val) {
      // console.log(val)
    },

    async gridview_DanhSach_HDTB_FocusedRowChanged(val) {
      try {
        console.log(val)
        await this.loading(true)
        this.txtVatKM = 0
        this.txtTienKM = 0

        this.txtTongTienKM = 0
        // this.model.kieuyeucau = val.data.kieuld_id
        // console.log(val.data.kieuld_id);
        //alert(val)
        let phieu_id_giaoviec = await this.axios.post('/web-tratruoc/ob_ghtt/sp_lay_giaophieu_hd_thutienphatsinh', {
          vhdtb_id: val.data.hdtb_id
        })
        if (phieu_id_giaoviec.data.data && phieu_id_giaoviec.data.data.length) this.phieu_id_giaoviec = phieu_id_giaoviec.data.data[0].phieu_id
        this.hdtb_id = val.data.hdtb_id
        var json_data_lay_ds_hopdong_tb_theo_hdtb_id = JSON.stringify({
          in_hdtb_id: this.hdtb_id
        })
        var link_lay_ds_hopdong_tb_theo_hdtb_id = '/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id'
        const files_lay_ds_hopdong_tb_theo_hdtb_id = await this.axios.post(link_lay_ds_hopdong_tb_theo_hdtb_id, json_data_lay_ds_hopdong_tb_theo_hdtb_id, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_lay_ds_hopdong_tb_theo_hdtb_id.data.data && files_lay_ds_hopdong_tb_theo_hdtb_id.data.error_code == this.success_code) await this.HienThiTTHopDongTB(files_lay_ds_hopdong_tb_theo_hdtb_id.data.data)

        await this.HienThi_KhoanMuc_ThanhToan(this.hdtb_id)
        await this.HT_GIAODIEN_DONVI()

        for (let key in this.dtControl) {
          if (this.dtControl.hasOwnProperty(key)) {
            let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
            let loaidv_id_var = element.getAttribute('tag')

            var json_data_sp_lay_ds_hdtb_dv = JSON.stringify({
              hdtb_id: this.hdtb_id,
              loaidv_id: loaidv_id_var
            })
            var link_sp_lay_ds_hdtb_dv = '/web-ccdv/khaibaoims/sp_lay_ds_hdtb_dv'
            const files_sp_lay_ds_hdtb_dv = await this.axios.post(link_sp_lay_ds_hdtb_dv, json_data_sp_lay_ds_hdtb_dv, {
              headers: {
                'Content-Type': 'application/json'
              }
            })
            if (files_sp_lay_ds_hdtb_dv.data.data && files_sp_lay_ds_hdtb_dv.data.error_code == this.success_code) element.value = files_sp_lay_ds_hdtb_dv.data.data[key].donvi_id
          }
        }
        let res = await this.axios.post(
          '/web-quantri/thutienphatsinh/lay_ds_kmtt_thukhac_phittn_thutienphatsinh',
          {
            vhdtb_id: this.hdtb_id,
            vkieuld_id: this.model.kieuyeucau
          },
          {
            headers: {
              'Content-Type': 'application/json'
            }
          }
        )
        if (res.data && res.data.data && res.data.data[0] && res.data.data[0].phi_ttn > 0) {
          this.enablePhiTTN = true
          this.phiTTN = this.addCommas(res.data.data[0].phi_ttn)
        } else {
          this.enablePhiTTN = false
          this.phiTTN = 0
        }

        const hdkh_id = this.hdkh_id
        let rs = await this.axios.get('web-quantri/thutienphatsinh/lay_tien_vat_khachhang_thutienphatsinh?vhdkh_id=' + hdkh_id)
        if (rs.data.data && rs.data.data.length > 0) {
          this.txtVatKM = this.addCommas(rs.data.data[0].vat)
          this.txtTienKM = this.addCommas(rs.data.data[0].tien)
          this.txtTongTienKM = this.addCommas(rs.data.data[0].tongtien)
          if (this.enablePhiTTN && this.phiTTN > 0) {
            this.tongtienExtended = this.addCommas(rs.data.data[0].tongtien)
          }
        }
        this.SetButton(3)
      } catch (ex) {
        this.$toast.error(ex.message || 'Có lỗi xảy ra')
      } finally {
        await this.loading(false)
        // setTimeout(() => {
        //   this.model.kieuyeucau = val.data.kieuld_id
        // }, 1000)
      }
    },

    async cboDichVu_VT_EditValueChanged() {
      if (this.model.dichvu == '' || this.options.dichvu == '') return

      this.dichvuvt_id = this.model.dichvu
      var link_CSS_LOAIHINH_TB = '/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'
      const files_CSS_LOAIHINH_TB = await this.axios.get(link_CSS_LOAIHINH_TB, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      if (files_CSS_LOAIHINH_TB.data.data && files_CSS_LOAIHINH_TB.data.error_code == this.success_code) {
        var json = []
        var resObj = files_CSS_LOAIHINH_TB.data.data
        for (var key in resObj) {
          if (resObj[key].DICHVUVT_ID == this.dichvuvt_id) {
            json.push(resObj[key])
          }
        }
        this.options.loaihinh = json
        if (this.options.loaihinh && this.options.loaihinh.length > 0) this.model.loaihinh = this.options.loaihinh[0].LOAITB_ID
      } else this.options.loaihinh = []
    },

    async cboLoaiHinhTB_EditValueChanged() {
      // if (this.model.loaihinh == '' || this.options.loaihinh == '') return

      this.loaitb_id = this.model.loaihinh
      var link_lay_ds_kieuld_thanhly = '/web-hopdong/thaydoitocdoadsl/lay_ds_kieuld_thanhly?loaitbId=' + this.loaitb_id + '&loaihdId=' + LoaiHopDong.THU_KHAC
      const files_lay_ds_kieuld_thanhly = await this.axios.get(link_lay_ds_kieuld_thanhly, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      if (files_lay_ds_kieuld_thanhly.data.data && files_lay_ds_kieuld_thanhly.data.error_code == this.success_code) {
        this.options.kieuyeucau = files_lay_ds_kieuld_thanhly.data.data
        // if (this.options.kieuyeucau) this.model.kieuyeucau = this.options.kieuyeucau[0].ID
      } else this.options.kieuyeucau = []
      debugger
      await this.HT_GIAODIEN_DONVI()
    },

    async tsbtnXoa_Click() {
      this.$confirm('Bạn thật sự muốn xóa hợp đồng không ?', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'delete'
      }).then(async () => {
        await this.loading(true)
        try {
          var json_data_xoa_hdkh = JSON.stringify({
            vhdkh_id: this.hdkh_id
          })
          var link_xoa_hdkh = '/web-quantri/thutienphatsinh/xoa_hdkh'
          const files_xoa_hdkh = await this.axios.post(link_xoa_hdkh, json_data_xoa_hdkh, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_xoa_hdkh.data.data && files_xoa_hdkh.data.error_code == this.success_code && files_xoa_hdkh.data.data == '1') {
            this.$toast.success('Xóa thành công!')
            this.SetButton(0)
          } else if (files_xoa_hdkh.data.error_code != this.success_code && files_xoa_hdkh.data.message_detail.includes('Chỉ được xóa hợp đồng làm trong ngày')) {
            this.$toast.error('Chỉ được xóa hợp đồng làm trong ngày !')
          } else {
            this.$toast.error('Xóa không thành công: ' + files_xoa_hdkh.data.message_detail.split(':')[1].split('(')[0] || 'Có lỗi xảy ra')
          }
        } catch (err) {
          // if(err.data.error_code != this.success_code && err.data.message_detail.includes("Chỉ được xóa hợp đồng làm trong ngày")){
          //   this.$toast.error("Chỉ được xóa hợp đồng làm trong ngày !")
          // }
          // else{
          console.log(err);
          this.$toast.error(err.message || 'Có lỗi xảy ra')
          // }
        }
        await this.loading(false)
      })
    },

    acceptSearchContract_magd(item) {
      this.txtMaGD = item.ma_gd
      if (this.txtMaGD && this.txtMaGD.trim() != '') {
        this.txtMaGD_KeyPress()
      }
    },

    acceptSearchContract_magdcha(item) {
      if (this.hdkh_id == item.hdkh_id) {
        this.$toast.error('Không được chọn chính hợp đồng đang thực hiện làm hợp đồng cha !')
        return
      }
      this.txtMaGD_Cha = item.ma_gd
      this.hdkh_cha_id = item.hdkh_id
    },

    async tsbtnThemTB_Click() {
      if ((await this.kiemtra_dulieu()) == false) {
        return
      }
      try {
        // force enter matb
        await this.txtMaTB_KeyPress({ key: 'Enter' })

        await this.loading(true)
        if (this.hdkh_id == 0) {
          this.$toast.error('Chưa có hợp đồng khách hàng. Bạn không thể thêm thuê bao !')
          this.loading(false)
          return
        }
        var json_data_kiemtra_hdtb_khi_themmoi = JSON.stringify({
          maTb: this.txtMaTB,
          hdkhId: this.hdkh_id,
          dichVuVtId: this.model.dichvu,
          loaiTbId: 0
        })
        var link_kiemtra_hdtb_khi_themmoi = '/web-hopdong/hopdong/kiemtra_hdtb_khi_themmoi'
        const files_kiemtra_hdtb_khi_themmoi = await this.axios.post(link_kiemtra_hdtb_khi_themmoi, json_data_kiemtra_hdtb_khi_themmoi, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        debugger
        if (files_kiemtra_hdtb_khi_themmoi.data.data && files_kiemtra_hdtb_khi_themmoi.data.error_code == this.success_code && files_kiemtra_hdtb_khi_themmoi.data.data == '1') {
          this.$toast.error('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ')
          this.loading(false)
          return
        }

        var json_data_lay_huonggiao_tiepnhan = JSON.stringify({
          kieuLdId: this.model.kieuyeucau,
          loaiHdId: LoaiHopDong.THU_KHAC,
          loaiTbId: this.loaitb_id,
          mucCuocId: this.vmuccuoc_id,
          tocDoId: this.vtocdo_id
        })
        var link_lay_huonggiao_tiepnhan = '/web-hopdong/hopdong/lay_huonggiao_tiepnhan'
        const files_lay_huonggiao_tiepnhan = await this.axios.post(link_lay_huonggiao_tiepnhan, json_data_lay_huonggiao_tiepnhan, {
          headers: {
            'Content-Type': 'application/json'
          }
        })

        if (files_lay_huonggiao_tiepnhan.data.data && files_lay_huonggiao_tiepnhan.data.error_code == this.success_code) {
          if (files_lay_huonggiao_tiepnhan.data.data.length == 0) {
            this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return
          }
          if (files_lay_huonggiao_tiepnhan.data.data['huonggiao_id']) this.huonggiao_tn = files_lay_huonggiao_tiepnhan.data.data['huonggiao_id']
          if (files_lay_huonggiao_tiepnhan.data.data['quytrinh_id']) this.quytrinh_id = files_lay_huonggiao_tiepnhan.data.data['quytrinh_id']
        }

        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_HDTBDV(true)
        await this.TaoDuLieu_CT_TIENHD(true)
        if ((await this.ThemMoi_HopDongTB_Thukhac()) == true) {
          this.$toast.success('Thêm mới hợp đồng thành công !')
        }

        ////////////////////////////////////////////

        var json_data_lay_ds_hopdong_theo_ma_gd = JSON.stringify({
          in_loaihd_id: LoaiHopDong.THU_KHAC,
          in_ma_gd: this.txtMaGD,
          in_tthd_id: TrangThaiHD.MOI,
          in_donvi_dl_id: 0
        })
        var link_lay_ds_hopdong_theo_ma_gd = '/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd'
        const files_lay_ds_hopdong_theo_ma_gd = await this.axios.post(link_lay_ds_hopdong_theo_ma_gd, json_data_lay_ds_hopdong_theo_ma_gd, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_lay_ds_hopdong_theo_ma_gd.data.data && files_lay_ds_hopdong_theo_ma_gd.data.error_code == this.success_code) {
          await this.HienThiTTHopDongKH(files_lay_ds_hopdong_theo_ma_gd.data.data)
          // this.$toast.success("Thêm thuê bao thành công !")
        }

        //api.lay_ds_hopdong_theo_ma_gd(this.axios, {
        //  in_loaihd_id: LoaiHopDong.THU_KHAC,
        //  in_ma_gd: this.txtMaGD,
        //  in_tthd_id: TrangThaiHD.MOI,
        //  in_donvi_dl_id: 0
        //})
        //  .then((res) => {
        //    if (res.data.data && res.data.error_code == this.success_code) {
        //      this.HienThiTTHopDongKH(res.data.data)
        //      this.$toast.success("Thêm thuê bao thành công !")
        //    }

        //  });
        debugger
        await this.sleep(100)
        await this.$refs.gridDanhSach_HDTB.setCurrentSelectedRow(this.options.phuluc.length - 1)

        await this.loading(false)
      } catch (err) {
        this.$toast.error(err.message || 'Lỗi không xác định')
      } finally {
        await this.loading(false)
        this.txtMaTB_KeyPress({ key: 'Enter' })
      }
    },
    async hc(hdtb_id) {
      try {
        this.loading(true)
        var Data = {}
        Data.vhdtb_id = hdtb_id
        Data.vngay_ht = moment(new Date()).format('DD/MM/YYYY hh:mm:ss')
        let r = await this.axios.post('/web-tratruoc/ob_ghtt/hoancong_hopdong_thukhac', Data)
        return r.data.data
      } catch (error) {
        this.$toast.error('Lỗi khi cập nhật dữ liệu: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async onHoanCongClick() {
      const confirm = await this.$confirm(`Bạn thật sự muốn hoàn công không?`, 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      })
      if (!confirm) return
      if (this.options.phuluc.length == 0) {
        this.$toast.error('Chưa có hợp đồng nào được chọn!')
        return
      }
      console.log(this.options.phuluc)
      this.tbKetQuaHC = []
      for (let i = 0; i < this.options.phuluc.length; i++) {
        const kq = {}
        kq.somay = this.options.phuluc[i].ma_tb
        kq.note = await this.hc(this.options.phuluc[i].hdtb_id)
        if (kq.note == 'OK') {
          kq.status = 'Thành công'
        } else {
          kq.status = 'Thất bại'
        }
        this.tbKetQuaHC.push(kq)
      }
      this.showKetQuaHoanCong = true
      this.SetButton(0)
    },
    async ThemMoi_HopDongTB_Thukhac() {
      let json_ds_hdtb = JSON.stringify(this.dsHDTB)
      let json_ds_hdtb_dv = JSON.stringify(this.dsHDTBDV)
      let json_phi_ttn = JSON.stringify([
        {
          PHI_TTN_CBX: this.enablePhiTTN ? 1 : 0,
          PHI_TTN: this.enablePhiTTN ? this.phiTTN.replace(/,/g, '') : 0,
          TIEN: this.txtTienKM.replace(/,/g, ''),
          VAT: this.txtVatKM.replace(/,/g, ''),
          TONGTIEN: this.enablePhiTTN ? this.tongtienExtended.replace(/,/g, '') : this.txtTongTienKM.replace(/,/g, ''),
          KIEULD_ID: this.model.kieuyeucau,
          KHOANMUC_TT: ''
        }
      ])
      let json_ds_ct_tienhd = JSON.stringify(this.dsCTTienHD)
      let huonggiao_tn = this.huonggiao_tn
      let input = {
        vds_phi_ttn: json_phi_ttn,
        vdshdtb: json_ds_hdtb,
        vdshdtbdv: json_ds_hdtb_dv,
        vdscttienhd: json_ds_ct_tienhd,
        vhuonggiao_tn: huonggiao_tn
      }
      // console.log(input);
      const res = await this.axios.post('/web-quantri/thutienphatsinh/fn_themtb_thutienphatsinh', input, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      if (res.data.data && res.data.error_code == this.success_code && res.data.data == 'OK') {
        return true
      } else {
        return false
      }
      //Thêm mới hợp đồng thuê bao
      // var json_data_sp_insert_hd_thuebao = JSON.stringify({
      //   p_js_hd_thuebao: this.dsHDTB
      // });
      // var link_sp_insert_hd_thuebao = "/web-quantri/thutienphatsinh/sp_insert_hd_thuebao"
      // const files_sp_insert_hd_thuebao = await this.axios.post(link_sp_insert_hd_thuebao, json_data_sp_insert_hd_thuebao, {
      //   headers: {
      //     'Content-Type': 'application/json'
      //   }
      // });
      // if (files_sp_insert_hd_thuebao.data.data && files_sp_insert_hd_thuebao.data.error_code == this.success_code && files_sp_insert_hd_thuebao.data.data == "1")
      //   this.insertHDTB_themtb_status = true

      // //Thêm mới hợp đồng thuê bao dịch vụ
      // var json_data_sp_insert_hdtb_dv = JSON.stringify({
      //   p_js_hdtb_dv: this.dsHDTBDV
      // });
      // var link_sp_insert_hdtb_dv = "/web-quantri/thutienphatsinh/sp_insert_hdtb_dv"
      // const files_sp_insert_hdtb_dv = await this.axios.post(link_sp_insert_hdtb_dv, json_data_sp_insert_hdtb_dv, {
      //   headers: {
      //     'Content-Type': 'application/json'
      //   }
      // });
      // if (files_sp_insert_hdtb_dv.data.data && files_sp_insert_hdtb_dv.data.error_code == this.success_code && files_sp_insert_hdtb_dv.data.data == "1")
      //   this.insertHDTBDV_themtb_status = true

      // //Thêm mới CT Tiền hợp đồng
      // var json_data_sp_insert_ct_tienhd = JSON.stringify({
      //   p_js_ct_tienhd: this.dsCTTienHD
      // });
      // var link_sp_insert_ct_tienhd = "/web-quantri/thutienphatsinh/sp_insert_ct_tienhd"
      // const files_sp_insert_ct_tienhd = await this.axios.post(link_sp_insert_ct_tienhd, json_data_sp_insert_ct_tienhd, {
      //   headers: {
      //     'Content-Type': 'application/json'
      //   }
      // });
      // if (files_sp_insert_ct_tienhd.data.data && files_sp_insert_ct_tienhd.data.error_code == this.success_code && files_sp_insert_ct_tienhd.data.data == "1")
      //   this.insertCT_TIENHD_themtb_status = true

      // if (this.insertHDTB_themtb_status == true && this.insertHDTBDV_themtb_status == true && this.insertCT_TIENHD_themtb_status == true) {
      //   return true
    },

    async tsbtnXoaTB_Click() {
      try {
        if (this.txtMaTB == '') {
          this.$toast.error('Bạn phải chọn thuê bao cần xóa trên lưới dữ liệu !')
          return
        }

        var json_data_sp_lay_ds_hdtb_khac_moi_theo_hdtb_id = JSON.stringify({
          p_hdtb_id: this.hdtb_id
        })
        var link_sp_lay_ds_hdtb_khac_moi_theo_hdtb_id = '/web-quantri/thutienphatsinh/sp_lay_ds_hdtb_khac_moi_theo_hdtb_id'
        const files_sp_lay_ds_hdtb_khac_moi_theo_hdtb_id = await this.axios.post(link_sp_lay_ds_hdtb_khac_moi_theo_hdtb_id, json_data_sp_lay_ds_hdtb_khac_moi_theo_hdtb_id, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_sp_lay_ds_hdtb_khac_moi_theo_hdtb_id.data.data && files_sp_lay_ds_hdtb_khac_moi_theo_hdtb_id.data.error_code == this.success_code) {
          this.$toast.error('Thuê bao không ở trạng thái mới (hoặc thuê bao đã kích hoạt). Không được xóa!')
          return
        }

        this.$confirm('Bạn thật sự muốn xóa thuê bao không ?', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'delete'
        }).then(async () => {
          await this.loading(true)
          await this.XoaHDTB()
          await this.loading(false)
        })
      } catch (err) {
        this.$toast.error(err.message || 'Lỗi không xác định')
      }
    },

    async XoaHDTB() {
      try {
        var json_data_xoa_hdtb = JSON.stringify({
          hdtb_id: this.hdtb_id
        })
        var link_xoa_hdtb = '/web-hopdong/khoiphucthanhly/xoa_hdtb'
        const files_xoa_hdtb = await this.axios.post(link_xoa_hdtb, json_data_xoa_hdtb, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_xoa_hdtb.data.data && files_xoa_hdtb.data.error_code == this.success_code) this.$toast.success('Xóa dữ liệu hợp đồng thuê bao thành công !')

        api
          .lay_ds_hopdong_theo_ma_gd(this.axios, {
            in_loaihd_id: LoaiHopDong.THU_KHAC,
            in_ma_gd: this.txtMaGD,
            in_tthd_id: TrangThaiHD.MOI,
            in_donvi_dl_id: 0
          })
          .then((res) => {
            if (res.data.data && res.data.error_code == this.success_code) {
              this.HienThiTTHopDongKH(res.data.data)
              this.$toast.success('Xóa thuê bao thành công !')
            }
          })
      } catch (err) {
        this.$toast.error(err.message || 'Lỗi không xác định')
      }
    },

    acceptDiaChiKH: async function(data) {
      //console.log(data.diachimoi)
      //console.log(data.tinh_id)
      //console.log(data.quan_id)
      //console.log(data.phuong_id)
      //console.log(data.pho_id)
      //console.log(data.ap_id)
      //console.log(data.khu_id)
      //console.log(data.dac_diem_id)
      //console.log(data.so_nha)

      let ds
      this.txtDiaChiKH = data.diachimoi
      if (this.hdkh_id == 0) {
        debugger
        if (this.txtDiaChiTT == '') {
          debugger
          var json_data_lay_ds_donviql = JSON.stringify({
            vap_id: data.ap_id != null ? data.ap_id : 0,
            vpho_id: data.pho_id != null ? data.pho_id : 0,
            vkieu: 1,
            vkhu_id: data.khu_id != null ? data.khu_id : 0,
            vphuong_id: data.phuong_id != null ? data.phuong_id : 0
          })
          var link_lay_ds_donviql = '/web-quantri/thutienphatsinh/lay_ds_donviql'
          const files_lay_ds_donviql = await this.axios.post(link_lay_ds_donviql, json_data_lay_ds_donviql, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_lay_ds_donviql.data.data && files_lay_ds_donviql.data.error_code == this.success_code) ds = files_lay_ds_donviql.data.data
          if (ds.length < 0) {
            this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán!')
            return
          } else if (ds.length == 1) this.model.dvtt = ds[0].donvi_id
          else {
            let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$root.token.getDonViID(), 1, ds)
            if (resolvePromise.ok) this.model.dvtt = resolvePromise.data.donviql_id
          }
          this.txtDiaChiTT = this.txtDiaChiKH
        }
      }

      if (this.txtDiaChiTB == '') {
        this.txtDiaChiTB = this.txtDiaChiKH
      }

      if (this.txtDiaChiLD == '') {
        if (this.txtDiaChiLD == '') {
          var json_data_lay_ds_donvi_theo_loaidv_diachild = JSON.stringify({
            loaidvId: LOAI_DV.TRAM_VT,
            phuongId: data.phuong_id,
            phoId: data.pho_id,
            apId: data.ap_id,
            khuId: data.khu_id
          })
          var link_lay_ds_donvi_theo_loaidv_diachild = '/web-thicong/thuebao/lay_ds_donvi_theo_loaidv_diachild'
          const files_lay_ds_donvi_theo_loaidv_diachild = await this.axios.post(link_lay_ds_donvi_theo_loaidv_diachild, json_data_lay_ds_donvi_theo_loaidv_diachild, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_lay_ds_donvi_theo_loaidv_diachild.data.data && files_lay_ds_donvi_theo_loaidv_diachild.data.error_code == this.success_code) ds = files_lay_ds_donvi_theo_loaidv_diachild.data.data

          if (ds.length <= 0) {
            this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt!')
            this.model.dvql = null
            return
          } else if (ds.length == 1) {
            this.model.dvql = ds[0].donviql_id

            for (let key in this.dtControl) {
              if (this.dtControl.hasOwnProperty(key)) {
                if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                  var json_data_sp_lay_ds_donvi_tramtc_banthietbi = JSON.stringify({
                    p_donviql_id: this.model.dvql,
                    p_loaidv_id: this.dtControl[key]['loaidv_dich_id']
                  })
                  var link_sp_lay_ds_donvi_tramtc_banthietbi = '/web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi'
                  const files_sp_lay_ds_donvi_tramtc_banthietbi = await this.axios.post(link_sp_lay_ds_donvi_tramtc_banthietbi, json_data_sp_lay_ds_donvi_tramtc_banthietbi, {
                    headers: {
                      'Content-Type': 'application/json'
                    }
                  })
                  if (files_sp_lay_ds_donvi_tramtc_banthietbi.data.data && files_sp_lay_ds_donvi_tramtc_banthietbi.data.error_code == this.success_code) {
                    let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                    if (element) element.innerHTML = ''
                    let option
                    for (let key1 in files_sp_lay_ds_donvi_tramtc_banthietbi.data.data) {
                      option.value = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['donvi_id']
                      option.label = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['ten_dv']
                      element.appendChild(option)
                    }
                    element.value = ds[0].donvi_id
                    break
                  }
                }
              }
            }
          } else {
            let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$root.token.getDonViID(), 1, ds)
            if (resolvePromise.ok) {
              this.model.dvql = resolvePromise.data.donviql_id
              for (let key in this.dtControl) {
                if (this.dtControl.hasOwnProperty(key)) {
                  if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                    var json_data_sp_lay_ds_donvi_tramtc_banthietbi = JSON.stringify({
                      p_donviql_id: this.model.dvql,
                      p_loaidv_id: this.dtControl[key]['loaidv_dich_id']
                    })
                    var link_sp_lay_ds_donvi_tramtc_banthietbi = '/web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi'
                    const files_sp_lay_ds_donvi_tramtc_banthietbi = await this.axios.post(link_sp_lay_ds_donvi_tramtc_banthietbi, json_data_sp_lay_ds_donvi_tramtc_banthietbi, {
                      headers: {
                        'Content-Type': 'application/json'
                      }
                    })
                    if (files_sp_lay_ds_donvi_tramtc_banthietbi.data.data && files_sp_lay_ds_donvi_tramtc_banthietbi.data.error_code == this.success_code) {
                      let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                      if (element) element.innerHTML = ''
                      let option
                      for (let key1 in files_sp_lay_ds_donvi_tramtc_banthietbi.data.data) {
                        option.value = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['donvi_id']
                        option.label = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['ten_dv']
                        element.appendChild(option)
                      }
                      element.value = resolvePromise.data.in_donvi_id
                      break
                    }
                  }
                }
              }
            } else {
              for (let key in this.dtControl) {
                if (this.dtControl.hasOwnProperty(key)) {
                  if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                    let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                    element.value = ''
                    break
                  }
                }
              }
              return
            }
          }
        }
        this.txtDiaChiLD = this.txtDiaChiKH
      }
    },

    acceptDiaChiTT: async function(data) {
      let ds
      this.txtDiaChiTT = data.diachimoi
      var json_data_lay_ds_donviql = JSON.stringify({
        vap_id: data.ap_id != null ? data.ap_id : 0,
        vpho_id: data.pho_id != null ? data.pho_id : 0,
        vkieu: 1,
        vkhu_id: data.khu_id != null ? data.khu_id : 0,
        vphuong_id: data.phuong_id != null ? data.phuong_id : 0
      })
      var link_lay_ds_donviql = '/web-quantri/thutienphatsinh/lay_ds_donviql'
      const files_lay_ds_donviql = await this.axios.post(link_lay_ds_donviql, json_data_lay_ds_donviql, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      if (files_lay_ds_donviql.data.data && files_lay_ds_donviql.data.error_code == this.success_code) ds = files_lay_ds_donviql.data.data
      if (ds.length < 0) {
        this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán!')
        return
      } else if (ds.length == 1) this.model.dvtt = ds[0].donvi_id
      else {
        let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$root.token.getDonViID(), 1, ds)
        if (resolvePromise.ok) this.model.dvtt = resolvePromise.data.donviql_id
      }
      //this.txtDiaChiTT = data.diachimoi

      if (this.txtDiaChiTB == '') {
        this.txtDiaChiTB = this.txtDiaChiTT
      }

      if (this.txtDiaChiLD == '') {
        if (this.txtDiaChiLD == '') {
          var json_data_lay_ds_donvi_theo_loaidv_diachild = JSON.stringify({
            loaidvId: LOAI_DV.TRAM_VT,
            phuongId: data.phuong_id,
            phoId: data.pho_id,
            apId: data.ap_id,
            khuId: data.khu_id
          })
          var link_lay_ds_donvi_theo_loaidv_diachild = '/web-thicong/thuebao/lay_ds_donvi_theo_loaidv_diachild'
          const files_lay_ds_donvi_theo_loaidv_diachild = await this.axios.post(link_lay_ds_donvi_theo_loaidv_diachild, json_data_lay_ds_donvi_theo_loaidv_diachild, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_lay_ds_donvi_theo_loaidv_diachild.data.data && files_lay_ds_donvi_theo_loaidv_diachild.data.error_code == this.success_code) ds = files_lay_ds_donvi_theo_loaidv_diachild.data.data

          if (ds.length <= 0) {
            this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt!')
            this.model.dvql = null
            return
          } else if (ds.length == 1) {
            this.model.dvql = ds[0].donviql_id

            for (let key in this.dtControl) {
              if (this.dtControl.hasOwnProperty(key)) {
                if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                  var json_data_sp_lay_ds_donvi_tramtc_banthietbi = JSON.stringify({
                    p_donviql_id: this.model.dvql,
                    p_loaidv_id: this.dtControl[key]['loaidv_dich_id']
                  })
                  var link_sp_lay_ds_donvi_tramtc_banthietbi = '/web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi'
                  const files_sp_lay_ds_donvi_tramtc_banthietbi = await this.axios.post(link_sp_lay_ds_donvi_tramtc_banthietbi, json_data_sp_lay_ds_donvi_tramtc_banthietbi, {
                    headers: {
                      'Content-Type': 'application/json'
                    }
                  })
                  if (files_sp_lay_ds_donvi_tramtc_banthietbi.data.data && files_sp_lay_ds_donvi_tramtc_banthietbi.data.error_code == this.success_code) {
                    let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                    if (element) element.innerHTML = ''
                    let option
                    for (let key1 in files_sp_lay_ds_donvi_tramtc_banthietbi.data.data) {
                      option.value = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['donvi_id']
                      option.label = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['ten_dv']
                      element.appendChild(option)
                    }
                    element.value = ds[0].donvi_id
                    break
                  }
                }
              }
            }
          } else {
            let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$root.token.getDonViID(), 1, ds)
            if (resolvePromise.ok) {
              this.model.dvql = resolvePromise.data.donviql_id
              for (let key in this.dtControl) {
                if (this.dtControl.hasOwnProperty(key)) {
                  if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                    var json_data_sp_lay_ds_donvi_tramtc_banthietbi = JSON.stringify({
                      p_donviql_id: this.model.dvql,
                      p_loaidv_id: this.dtControl[key]['loaidv_dich_id']
                    })
                    var link_sp_lay_ds_donvi_tramtc_banthietbi = '/web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi'
                    const files_sp_lay_ds_donvi_tramtc_banthietbi = await this.axios.post(link_sp_lay_ds_donvi_tramtc_banthietbi, json_data_sp_lay_ds_donvi_tramtc_banthietbi, {
                      headers: {
                        'Content-Type': 'application/json'
                      }
                    })
                    if (files_sp_lay_ds_donvi_tramtc_banthietbi.data.data && files_sp_lay_ds_donvi_tramtc_banthietbi.data.error_code == this.success_code) {
                      let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                      if (element) element.innerHTML = ''
                      let option
                      for (let key1 in files_sp_lay_ds_donvi_tramtc_banthietbi.data.data) {
                        option.value = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['donvi_id']
                        option.label = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['ten_dv']
                        element.appendChild(option)
                      }
                      element.value = resolvePromise.data.in_donvi_id
                      break
                    }
                  }
                }
              }
            } else {
              for (let key in this.dtControl) {
                if (this.dtControl.hasOwnProperty(key)) {
                  if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                    let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                    element.value = ''
                    break
                  }
                }
              }
              return
            }
          }
        }
        this.txtDiaChiLD = this.txtDiaChiKH
      }
    },

    acceptDiaChiTB: async function(data) {
      this.txtDiaChiTB = data.diachimoi
      if (this.txtDiaChiLD == '') {
        let ds
        var json_data_lay_ds_donvi_theo_loaidv_diachild = JSON.stringify({
          loaidvId: LOAI_DV.TRAM_VT,
          phuongId: data.phuong_id,
          phoId: data.pho_id,
          apId: data.ap_id,
          khuId: data.khu_id
        })
        var link_lay_ds_donvi_theo_loaidv_diachild = '/web-thicong/thuebao/lay_ds_donvi_theo_loaidv_diachild'
        const files_lay_ds_donvi_theo_loaidv_diachild = await this.axios.post(link_lay_ds_donvi_theo_loaidv_diachild, json_data_lay_ds_donvi_theo_loaidv_diachild, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (files_lay_ds_donvi_theo_loaidv_diachild.data.data && files_lay_ds_donvi_theo_loaidv_diachild.data.error_code == this.success_code) ds = files_lay_ds_donvi_theo_loaidv_diachild.data.data

        if (ds.length <= 0) {
          this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt!')
          this.model.dvql = ''
          return
        } else if (ds.length == 1) {
          this.model.dvql = ds[0].donviql_id

          for (let key in this.dtControl) {
            if (this.dtControl.hasOwnProperty(key)) {
              if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                var json_data_sp_lay_ds_donvi_tramtc_banthietbi = JSON.stringify({
                  p_donviql_id: this.model.dvql,
                  p_loaidv_id: this.dtControl[key]['loaidv_dich_id']
                })
                var link_sp_lay_ds_donvi_tramtc_banthietbi = '/web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi'
                const files_sp_lay_ds_donvi_tramtc_banthietbi = await this.axios.post(link_sp_lay_ds_donvi_tramtc_banthietbi, json_data_sp_lay_ds_donvi_tramtc_banthietbi, {
                  headers: {
                    'Content-Type': 'application/json'
                  }
                })
                if (files_sp_lay_ds_donvi_tramtc_banthietbi.data.data && files_sp_lay_ds_donvi_tramtc_banthietbi.data.error_code == this.success_code) {
                  let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                  if (element) element.innerHTML = ''
                  let option
                  for (let key1 in files_sp_lay_ds_donvi_tramtc_banthietbi.data.data) {
                    option.value = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['donvi_id']
                    option.label = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['ten_dv']
                    element.appendChild(option)
                  }
                  element.value = ds[0].donvi_id
                  break
                }
              }
            }
          }
        } else {
          let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$root.token.getDonViID(), 1, ds)
          if (resolvePromise.ok) {
            this.model.dvql = resolvePromise.data.donviql_id
            for (let key in this.dtControl) {
              if (this.dtControl.hasOwnProperty(key)) {
                if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                  var json_data_sp_lay_ds_donvi_tramtc_banthietbi = JSON.stringify({
                    p_donviql_id: this.model.dvql,
                    p_loaidv_id: this.dtControl[key]['loaidv_dich_id']
                  })
                  var link_sp_lay_ds_donvi_tramtc_banthietbi = '/web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi'
                  const files_sp_lay_ds_donvi_tramtc_banthietbi = await this.axios.post(link_sp_lay_ds_donvi_tramtc_banthietbi, json_data_sp_lay_ds_donvi_tramtc_banthietbi, {
                    headers: {
                      'Content-Type': 'application/json'
                    }
                  })
                  if (files_sp_lay_ds_donvi_tramtc_banthietbi.data.data && files_sp_lay_ds_donvi_tramtc_banthietbi.data.error_code == this.success_code) {
                    let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                    if (element) element.innerHTML = ''
                    let option
                    for (let key1 in files_sp_lay_ds_donvi_tramtc_banthietbi.data.data) {
                      option.value = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['donvi_id']
                      option.label = files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['ten_dv']
                      element.appendChild(option)
                    }
                    element.value = resolvePromise.data.in_donvi_id
                    break
                  }
                }
              }
            }
          } else {
            for (let key in this.dtControl) {
              if (this.dtControl.hasOwnProperty(key)) {
                if (ds[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                  let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                  element.value = ''
                  break
                }
              }
            }
            return
          }
        }
        this.txtDiaChiLD = this.txtDiaChiKH
      }
    },

    acceptDiaChiLD: async function(data) {
      this.txtDiaChiLD = data.diachimoi
      let ds
      var json_data_lay_ds_donvi_theo_loaidv_diachild = JSON.stringify({
        loaidvId: LOAI_DV.TRAM_VT,
        phuongId: data.phuong_id,
        phoId: data.pho_id,
        apId: data.ap_id,
        khuId: data.khu_id
      })
      var link_lay_ds_donvi_theo_loaidv_diachild = '/web-thicong/thuebao/lay_ds_donvi_theo_loaidv_diachild'
      const files_lay_ds_donvi_theo_loaidv_diachild = await this.axios.post(link_lay_ds_donvi_theo_loaidv_diachild, json_data_lay_ds_donvi_theo_loaidv_diachild, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      if (files_lay_ds_donvi_theo_loaidv_diachild.data.data && files_lay_ds_donvi_theo_loaidv_diachild.data.error_code == this.success_code) {
        debugger
        ds = files_lay_ds_donvi_theo_loaidv_diachild.data.data
      }
      /*debugger*/

      if (ds.length <= 0) {
        this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt!')
        this.model.dvql = ''
        return
      } else if (ds.length == 1) {
        this.model.dvql = ds[0].donviql_id

        for (let key in this.dtControl) {
          if (this.dtControl.hasOwnProperty(key)) {
            if (this.dtControl[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
              var json_data_sp_lay_ds_donvi_tramtc_banthietbi = JSON.stringify({
                p_donviql_id: this.model.dvql,
                p_loaidv_id: this.dtControl[key]['loaidv_dich_id']
              })
              var link_sp_lay_ds_donvi_tramtc_banthietbi = '/web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi'
              const files_sp_lay_ds_donvi_tramtc_banthietbi = await this.axios.post(link_sp_lay_ds_donvi_tramtc_banthietbi, json_data_sp_lay_ds_donvi_tramtc_banthietbi, {
                headers: {
                  'Content-Type': 'application/json'
                }
              })
              debugger
              if (files_sp_lay_ds_donvi_tramtc_banthietbi.data.data && files_sp_lay_ds_donvi_tramtc_banthietbi.data.error_code == this.success_code) {
                let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                if (element) element.innerHTML = ''

                for (let key1 in files_sp_lay_ds_donvi_tramtc_banthietbi.data.data) {
                  const option = document.createElement('option')
                  option.setAttribute('value', files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['donvi_id'])
                  option.setAttribute('label', files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['ten_dv'])
                  element.appendChild(option)
                }
                element.value = ds[0].donvi_id
                break
              }
            }
          }
        }
      } else {
        let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$root.token.getDonViID(), 1, ds)
        if (resolvePromise.ok) {
          this.model.dvql = resolvePromise.data.donviql_id
          for (let key in this.dtControl) {
            if (this.dtControl.hasOwnProperty(key)) {
              if (this.dtControl[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                var json_data_sp_lay_ds_donvi_tramtc_banthietbi = JSON.stringify({
                  p_donviql_id: this.model.dvql,
                  p_loaidv_id: this.dtControl[key]['loaidv_dich_id']
                })
                var link_sp_lay_ds_donvi_tramtc_banthietbi = '/web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi'
                const files_sp_lay_ds_donvi_tramtc_banthietbi = await this.axios.post(link_sp_lay_ds_donvi_tramtc_banthietbi, json_data_sp_lay_ds_donvi_tramtc_banthietbi, {
                  headers: {
                    'Content-Type': 'application/json'
                  }
                })
                if (files_sp_lay_ds_donvi_tramtc_banthietbi.data.data && files_sp_lay_ds_donvi_tramtc_banthietbi.data.error_code == this.success_code) {
                  let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                  if (element) element.innerHTML = ''

                  for (let key1 in files_sp_lay_ds_donvi_tramtc_banthietbi.data.data) {
                    const option = document.createElement('option')
                    option.setAttribute('value', files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['donvi_id'])
                    option.setAttribute('label', files_sp_lay_ds_donvi_tramtc_banthietbi.data.data[key1]['ten_dv'])
                    element.appendChild(option)
                  }
                  element.value = resolvePromise.data.in_donvi_id
                  break
                }
              }
            }
          }
        } else {
          for (let key in this.dtControl) {
            if (this.dtControl.hasOwnProperty(key)) {
              if (this.dtControl[key]['loaidv_dich_id'] == LOAI_DV.TRAM_VT) {
                let element = document.getElementById('cbo' + this.dtControl[key]['loaidv_dich_id'] + '.' + this.dtControl[key]['thutu_id'])
                element.value = ''
                break
              }
            }
          }
          return
        }
      }
    },

    grid_ActionComplete(args) {
      if (args.data != args.previousData) {
        var val = args.data
        var oldval = args.previousData
        var tyle_vat = 10
        if (val.tongtien != oldval.tongtien && val.vat == oldval.vat) {
          val.vat = Math.round((val.tongtien * tyle_vat) / (100 + tyle_vat))
          val.tien = Math.round(val.tongtien - val.vat)
          this.options.tienkhoan[args.rowIndex].vat = val.vat
          this.options.tienkhoan[args.rowIndex].tien = val.tien
          this.options.tienkhoan[args.rowIndex].tongtien = val.tongtien
          this.$forceUpdate()
        } else if (val.tien != oldval.tien && val.vat == oldval.vat) {
          val.vat = Math.round((val.tien * tyle_vat) / 100)
          val.tongtien = Math.round(val.tien + val.vat)
          this.options.tienkhoan[args.rowIndex].vat = val.vat
          this.options.tienkhoan[args.rowIndex].tongtien = val.tongtien
          this.options.tienkhoan[args.rowIndex].tien = val.tien
          this.$forceUpdate()
        }

        this.txtVatKM = 0
        this.txtTienKM = 0
        this.txtTongTienKM = 0
        //for (let i = 0; i < this.options.tienkhoan.length; i++) {
        //  this.txtVatKM += this.options.tienkhoan[i].vat
        //  this.txtTienKM += this.options.tienkhoan[i].tien
        //  this.txtTongTienKM += this.options.tienkhoan[i].tongtien
        //}
        this.$forceUpdate()
      }
    },

    enableButton(ele) {
      let control_name = document.getElementById(ele)
      if (control_name != null) {
        if (control_name.classList.contains('non-active')) {
          control_name.classList.remove('non-active')
          control_name.classList.add('active')
        }
        document.getElementById(ele).style.pointerEvents = 'auto'
      }
    },

    disableButton(ele) {
      let control_name = document.getElementById(ele)
      if (control_name != null) {
        if (control_name.classList.contains('active')) {
          control_name.classList.remove('active')
          control_name.classList.add('non-active')
        }
        document.getElementById(ele).style.pointerEvents = 'none'
      }
    },

    selectionOptions: function() {
      let result = this.selectionSettings
      if (result == undefined || result == null) result = {}
      if (this.showColumnCheckbox) {
        result.type = 'Multiple'
        result.enableToggle = false
      } else {
        if (result.type == undefined) result.type = 'Single'
        if (result.enableToggle == undefined) result.enableToggle = false
      }
      return result
    },

    async tienkhoan_grid_rowSelected(args) {
      this.checkchecktienkhoan[args.rowIndex] = 0
    },

    async tienkhoan_grid_rowDeselected(args) {
      this.checkchecktienkhoan[args.rowIndex] = 1
    },

    focusItem(index, data) {
      this.current = index
    },

    sleep(ms) {
      return new Promise((resolve) => setTimeout(resolve, ms))
    },

    getDate(strDate, format) {
      if (format) {
        return moment(strDate, format).toDate()
      } else {
        return moment(strDate, 'DD/MM/YYYY HH:mm:ss').toDate()
      }
    },

    formatDate(date, format) {
      if (format) {
        return moment(date).format(format)
      } else {
        return moment(date).format('DD/MM/YYYY HH:mm:ss')
      }
    }

    //jsOpen() {
    //  Split(['boxLeft', 'boxRight'], {
    //    sizes: [70, 30],
    //    gutterSize: 16,
    //    onDragEnd: function (sizes) {

    //    },
    //  })

    //  this.clearGutter()
    //},
    //jsClose() {
    //  Split(['boxLeft', 'boxRight'], {
    //    sizes: [100, 0],
    //    gutterSize: 0,
    //    onDragEnd: function (sizes) {

    //    },
    //  })

    //  document.getElementById("boxLeft").style.width = "calc(100% - 0px)"
    //  this.clearGutter()
    //},
    //clearGutter() {
    //  const elements = document.getElementsByClassName("gutter-horizontal");
    //  while (elements.length > 1) {
    //    for (let i = 0; i < elements.length; i++) {
    //      elements[i].parentNode.removeChild(elements[i]);
    //    }
    //  }
    //},
  }
}

//window.addEventListener("load", function () {
//  Split(['boxLeft1', 'boxRight1'], {
//    sizes: [30, 70],
//    gutterSize: 16,
//    onDragEnd: function (sizes) {

//    },
//  })
//  Split(['boxTop', 'boxBottom'], {
//    sizes: [50, 50],
//    direction: 'vertical',

//  });
//  jQuery('#tabs a')[1].click();
//});
</script>
<style>
.row-eq-height {
  display: flex;
}

.none-padding {
  padding: 0 !important;
}

.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}

li.non-active a {
  color: #d3d3d3 !important;
}

input[type='text'][disabled] {
  color: black;
}

.container-box {
  padding: 0px 7px;
}
.disabled{
  pointer-events: none !important;
  cursor: not-allowed;
}
</style>
