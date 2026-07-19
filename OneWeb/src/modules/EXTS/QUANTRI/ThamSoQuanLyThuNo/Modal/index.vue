<template src="./index.html"></template>
<script>
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import Calculator from '@/components/Calculator'
import { L10n } from '@syncfusion/ej2-base'
import TSBCTemplate from './component/TSBCTemplate.vue'
L10n.load({
  'vi-VN': {
    'dropdowns': {
      'noRecordsTemplate': "Không có dữ liệu",
      'actionFailureTemplate': ""
    }
  }
})
const
  Loai_NV = {
    THUNGAN:17,
  },
  LOAI_DV = {
    TO_THU_CUOC:32,
    QUAYTHU:18
  }
export default {
  name: "PopupThamSoQuanLyThuNo",
  components:{Calculator},
  props: {
    id: String,
  },
  provide: {
    multiselect: [CheckBoxSelection]
  },
  data:function (){
    return {
      nguoidung_id:null,
      donvi_id:null,
      dblSettingval:null,
      dsCacKN:[],
      dsKhoanNoQD:[],
      kt_load:true,
      sValue: "",
      CT_TSMD_BLOB_Data:{},
      CT_TSMD_Data:{},

      ds_KV_DaGan_tmp:[],
      ds_KV_ChuaGan_tmp:[],
      ds_QT_ChuaGan_tmp:[],
      ds_QT_DaGan_tmp:[],

      dsHinhThucTT:[],
      dsNganHang:[],
      dsLoai_CD:[],
      dsLoai_KhieuNai_Config:[],
      ds_hinhthuc_config:[],
      dsHinhThucKhoa:[],
      dsKieuMo:[],
      dsDichVuVT:[],
      dsLoaiHinhMo:[],
      dsLoaiHinhKhoa:[],
      dsThuNgan:[],
      dsChuKy:[],

      dgvKV_ChuaGan:[],
      dgvKV_DaGan:[],
      dgvQuayThu_ChuaGan:[],
      dgvQuayThu_DaGan:[],
      gridThamSo:[],
      gridTSIn:[],
      grcMacdinh:[],

      cboHinhThucTT:null,
      cboNganHang:null,
      cboLoai_CD:null,
      cboHinhThuc_KN:null,
      cboHinhThucKhoa:null,
      cboHinhThucMo:null,
      cboKieuMo:null,
      cboDichVuMo:null,
      cboDichVuKhoa:null,
      cboLoaiHinhKhoa:null,
      cboLoaiHinhMo:null,
      cboThungan:null,
      cboChuKy:null,
      cboDauKy:null,
      cboPhatSinh:null,

      chkLoaiTBKhoa:{
        Checked:false,
      },
      chkMoMayTheoKV:{
        Checked:false,
      },
      chkMoMayTheoQuayThu:{
        Checked:false,
      },
      chkInDungThue:{
        Checked:false,
      },
      chkInLDKhongThue:{
        Checked:false,
      },
      chkInTenThuQuy:{
        Checked:false,
      },
      chkkhogiayphieuthu:{
        Checked:false,
      },
      chkTrongNgay:{
        Checked:false,
      },
      chkLoaiTBMo:{
        Checked:false,
      },
      dtpKyHD:null,
      txtTienGHM:0,
      isCalculatorShow:false,
      txtQuyen:0,
      txtSeri:null,
      txtSoSeri:0,
      txtTenGiaoDich:null,
      txtDiaChiGiaoDich:null,
      txtSoDT:null,
      txtSoFax:null,
      txtSoTK:null,
      txtNganHang:null,
      txtTKVietel:null,
      txtTKPostel:null,
      txtSoDong:null,
      txtKhoangCachDoc:null,
      txtKhoangCachNgang:null,
      txtThueGTGT:null,
      tabControl:{
        SelectedIndex:null,
      },
      grcMacdinhRowTemplate:function (e){
        return {
          template: TSBCTemplate,
        }
      }
    }
  },
  methods:{
    async focusElement() {
      //frmQLNConfiguration_Load

      try {
        this.loading(true)

        // main
        let kyhoadon = await this.LAY_THANG_NO()
        if (!this.isNullOrEmpty(kyhoadon) && kyhoadon != null){
          kyhoadon = kyhoadon.substr(4,2) + "/" + kyhoadon.substr(0, 4);
          await this.RegWrite("kyhoadon",kyhoadon)
        }
        kyhoadon = "01"
        if (!this.isNullOrEmpty(kyhoadon) && kyhoadon != null)
          await this.RegWrite("chuky", kyhoadon);
        this.tabSelected("tabGQKN",6)
        this.tabSelected("tabXLTB",4)
        //Load thông tin hình thức thanh toán
        this.dsHinhThucTT = await this.Hienthi_DS_HTTT(1)
        this.$nextTick(() =>{
          this.$refs['cboHinhThucTT'].$children[0].grid.selectRow(0)
        })
        //Load thông tin ngân hàng
        this.dsNganHang = await this.Lay_ds_nganhang_gachno()
        this.$nextTick(() =>{
          this.$refs['cboNganHang'].$children[0].grid.selectRow(0)
          //Hiển thị combo loại chủ đề KN
          this.$refs['cboLoai_CD'].$children[0].grid.selectRow(0)
        })
        //Hiển thị combo hình thức kn
        this.ds_hinhthuc_config = await this.DS_HINHTHUC_CONFIG()
        this.$nextTick(() =>{
          this.$refs['cboHinhThuc_KN'].$children[0].grid.selectRow(0)
        })
        //Combo hinh thức khóa máy
        this.cboHinhThucKhoa = this.dsHinhThucKhoa[2].kieunhac_id
        //Combo hinh thức mở máy
        this.cboHinhThucMo = this.dsHinhThucKhoa[2].kieunhac_id
        this.txtTienGHM = 0
        //Combo kiểu mở
        this.cboKieuMo = this.dsKieuMo[0].kieumo_id
        //Combo dịch vụ mở
        this.dsDichVuVT = await this.HT_DS_DichVuVT(this.$auth.getNguoiDungID())
        this.cboDichVuMo = this.dsDichVuVT.length >0 ? this.dsDichVuVT[0].dichvuvt_id : null
        //Combo dịch vụ khóa
        this.cboDichVuKhoa = this.dsDichVuVT.length >0 ? this.dsDichVuVT[0].dichvuvt_id : null
        await this.LoadTT()
        //Combo loại hình mở
        this.dsLoaiHinhMo = await this.getDsLoaiHinh(this.cboDichVuMo)
        //Combo loại hình khóa
        this.dsLoaiHinhKhoa = await this.getDsLoaiHinh(this.cboDichVuKhoa)
        let loaihinh_khoa = await this.RegRead("loaihinh_khoa","")
        if (!this.isNullOrEmpty(loaihinh_khoa)){
          this.chkLoaiTBKhoa.Checked = true
          this.cboLoaiHinhKhoa = [...loaihinh_khoa];
        }else {
          this.cboLoaiHinhKhoa = ""
          this.chkLoaiTBKhoa.Checked = false
        }
        //Combo thu ngân
        this.dsThuNgan = await this.getDSThuNgan(Loai_NV.THUNGAN, this.$auth.getNhanVienID(), this.$auth.getNguoiDungID())
        // if (this.dsThuNgan.length > 0) this.$refs['cboThungan'].$children[0].grid.selectRow(0)
        if (this.dsThuNgan.length > 0) {
          let thungan_id = await this.RegRead("thungan_id","")
          if (!this.isNullOrEmpty(thungan_id)){
            setTimeout(async () => {
              let idx = this.dsThuNgan.findIndex(i => i.nhanvien_id == thungan_id)
              if (idx > -1) this.$refs['cboThungan'].$children[0].grid.selectRow(idx)
            },50)
          }else {
            this.$nextTick(() =>{
              this.$refs['cboThungan'].$children[0].grid.selectRow(0)
            })
          }
        }

        //Khu vực mở máy
        let ds_khuvuc = await this.RegRead("ds_khuvuc","")
        let dtKhuVuc = await this.HT_DonVi_loaidv_Combobox_HPG(LOAI_DV.TO_THU_CUOC)
        if (dtKhuVuc.length > 0){
          if (ds_khuvuc){
            this.ds_KV_ChuaGan_tmp = []
            this.ds_KV_DaGan_tmp = []
            this.chkMoMayTheoKV.Checked = true
            let dtKV_ChuaGan = dtKhuVuc.filter(item => !ds_khuvuc.includes(item.donvi_id))
            if (dtKV_ChuaGan.length > 0){
              let dtT = [...dtKV_ChuaGan]
              this.ds_KV_ChuaGan_tmp = []
              this.ds_KV_ChuaGan_tmp = dtT

              this.dgvKV_ChuaGan = this.ds_KV_ChuaGan_tmp
            }else this.dgvKV_ChuaGan = []
            let dtKV_DaGan = dtKhuVuc.filter(item => ds_khuvuc.includes(item.donvi_id))
            if (dtKV_DaGan.length > 0){
              let dtT = [...dtKV_DaGan]
              this.ds_KV_DaGan_tmp = []
              this.ds_KV_DaGan_tmp = dtT
              this.dgvKV_DaGan = this.ds_KV_DaGan_tmp
            }else this.dgvKV_DaGan = []
          }else {
            this.chkMoMayTheoKV.Checked = false
            this.dgvKV_DaGan = []
            this.ds_KV_ChuaGan_tmp = []
            this.ds_KV_ChuaGan_tmp = dtKhuVuc

            this.dgvKV_ChuaGan = this.ds_KV_ChuaGan_tmp
          }
        }
        //Quầy thu mở máy
        let ds_quay = await this.RegRead("ds_quay", "")
        let dtQuayThu = await this.HT_DonVi_loaidv_Combobox_HPG(LOAI_DV.QUAYTHU)
        if (dtQuayThu.length > 0){
          if (ds_quay){
            this.ds_QT_ChuaGan_tmp = []
            this.ds_QT_DaGan_tmp = []
            this.chkMoMayTheoQuayThu.Checked = true
            let dtQT_ChuaGan = dtQuayThu.filter(item => !ds_quay.includes(item.donvi_id))
            if (dtQT_ChuaGan.length > 0){
              let dtT = [...dtQT_ChuaGan]
              this.ds_QT_ChuaGan_tmp = []
              this.ds_QT_ChuaGan_tmp = dtT
              this.dgvQuayThu_ChuaGan = this.ds_QT_ChuaGan_tmp
            }else this.dgvQuayThu_ChuaGan = []
            let dtQT_DaGan = dtQuayThu.filter(item => ds_quay.includes(item.donvi_id))
            if (dtQT_DaGan.length > 0){
              let dtT = [...dtQT_DaGan]
              this.ds_QT_DaGan_tmp = []
              this.ds_KV_DaGan_tmp = dtT

              this.dgvQuayThu_DaGan = this.ds_KV_DaGan_tmp
            }else this.dgvQuayThu_DaGan = []
          }else {
            this.chkMoMayTheoQuayThu.Checked = false
            this.dgvQuayThu_DaGan = []
            this.ds_QT_ChuaGan_tmp = []
            this.ds_QT_ChuaGan_tmp = dtQuayThu

            this.dgvQuayThu_ChuaGan = this.ds_QT_ChuaGan_tmp
          }
        }
        this.tabSelected("tabHT",0)
        this.kt_load = false;
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async LoadTT() {
      let count = 0
      try {
        // this.loading(true)
        setTimeout(()=>{},20)
        this.nguoidung_id = this.$auth.getNguoiDungID()
        this.donvi_id = this.$auth.getDonViID()
        let httt_id = await this.RegRead("httt_id", "")
        if (!this.isNullOrEmpty(httt_id)) {
          let idx = this.dsHinhThucTT.findIndex(i => i.httt_id == httt_id)
          if (idx > -1) {
            this.$nextTick(() =>{
              this.$refs['cboHinhThucTT'].$children[0].grid.selectRow(idx)
            })
          }
        }
        let nganhang_id = await this.RegRead("nganhang_id", "")
        if (!this.isNullOrEmpty(nganhang_id)) {
          let idx = this.dsNganHang.findIndex(i => i.nganhang_id == nganhang_id)
          if (idx > -1)
            this.$nextTick(() =>{
              this.$refs['cboNganHang'].$children[0].grid.selectRow(idx)
            })
        }
        //tab he thong
        let kyhoadon = await this.RegRead("kyhoadon", "")
        console.log("kyhoadon:", kyhoadon)
        if (!this.isNullOrEmpty(kyhoadon))
          this.dtpKyHD = kyhoadon
        else {
          let temp = await this.LAY_THANG_NO()
          this.dtpKyHD = temp.substr(0, 2) + "/" + temp.substr(2, 4);
        }
        //Combo chu kỳ nợ
        let time_split = this.dtpKyHD.split('/') //MM/yyyy
        this.dsChuKy = await this.HT_Cbo_ChuKyNo_Theo_kyhoadon(time_split[1]+time_split[0]) // yyyyMM
        if (this.dsChuKy.length > 0) this.cboChuKy = this.dsChuKy[0].chuky
        else {
          this.$root.toastError("Không có thông tin về chu kỳ!")
          // return
        }
        let kycuoc = ""
        let chuky = await this.RegRead("chuky","")
        if (!this.isNullOrEmpty(chuky)){
          kycuoc = chuky
          if (kycuoc != "" && kycuoc != null)
            this.cboChuKy = kycuoc
        }
        let indungthue = await this.RegRead("indungthue","")
        if (!this.isNullOrEmpty(indungthue)){
          this.chkInDungThue.Checked = false
        }else this.chkInDungThue.Checked = indungthue == "1"

        if (!this.isNullOrEmpty(indungthue)){
          this.chkInLDKhongThue.Checked = false
        }else this.chkInLDKhongThue.Checked = indungthue == "1"

        let intenthuquy = await this.RegRead("intenthuquy","")
        if (!this.isNullOrEmpty(intenthuquy)){
          this.chkInTenThuQuy.Checked = false
        }else this.chkInTenThuQuy.Checked = intenthuquy == "1"

        let ingiaya5 = await this.RegRead("ingiaya5","")
        if (!this.isNullOrEmpty(ingiaya5)){
          this.chkkhogiayphieuthu.Checked = false
        }else this.chkkhogiayphieuthu.Checked = ingiaya5 == "1"

        let quyen = await this.RegRead("quyen", "")
        if (!this.isNullOrEmpty(quyen))
          this.txtQuyen = quyen
        let seri = await this.RegRead("seri", "")
        if (!this.isNullOrEmpty(seri))
          this.txtSeri = seri
        let soseri = await this.RegRead("soseri", "")
        if (!this.isNullOrEmpty(soseri))
          this.txtSoSeri = soseri
        //Hiển thị danh sách tham số hệ thống
        let dt = await this.LAY_DS_THAMSO_MACDINH(this.$auth.getNguoiDungID(), 5)
        for (let item of dt){
          item.chon = !this.isNullOrEmpty(await this.RegRead(item.ma_ts.toLowerCase(), ""));
          item.count = 1 // sum
        }
        this.gridThamSo = dt
        await this.LoadTSBC()
        //tab Tai khoan giao dich
        let TenGiaoDich = await this.RegRead("TenGiaoDich", "")
        if (!this.isNullOrEmpty(TenGiaoDich))
          this.txtTenGiaoDich = TenGiaoDich;
        let DiaChiGiaoDich = await this.RegRead("DiaChiGiaoDich", "")
        if (!this.isNullOrEmpty(DiaChiGiaoDich))
          this.txtDiaChiGiaoDich = DiaChiGiaoDich;
        let SoDT = await this.RegRead("SoDT", "")
        if (!this.isNullOrEmpty(SoDT))
          this.txtSoDT = SoDT;
        let SoFax = await this.RegRead("SoFax", "")
        if (!this.isNullOrEmpty(SoFax))
          this.txtSoFax = SoFax;
        let SoTK = await this.RegRead("SoTK", "")
        if (!this.isNullOrEmpty(SoTK))
          this.txtSoTK = SoTK;
        let NganHang = await this.RegRead("NganHang", "")
        if (!this.isNullOrEmpty(NganHang))
          this.txtNganHang = NganHang;
        let TKVietel = await this.RegRead("TKVietel", "")
        if (!this.isNullOrEmpty(TKVietel))
          this.txtTKVietel = TKVietel;
        let TKPostel = await this.RegRead("TKPostel", "")
        if (!this.isNullOrEmpty(TKPostel))
          this.txtTKPostel = TKPostel;
        let SoDong = await this.RegRead("SoDong", "")
        if (!this.isNullOrEmpty(SoDong))
          this.txtSoDong = SoDong;
        let KhoangCachDoc = await this.RegRead("KhoangCachDoc", "")
        if (!this.isNullOrEmpty(KhoangCachDoc))
          this.txtKhoangCachDoc = KhoangCachDoc;
        let KhoangCachNgang = await this.RegRead("KhoangCachNgang", "")
        if (!this.isNullOrEmpty(KhoangCachNgang))
          this.txtKhoangCachNgang = KhoangCachNgang;

        //tab Dieu chinh no
        let dauky = await this.RegRead("dauky", "")
        if (this.isNullOrEmpty())
          this.cboDauKy = 0;
        else
          this.cboDauKy = dauky
        let phatsinh = await this.RegRead("phatsinh", "")
        if (this.isNullOrEmpty(phatsinh))
          this.cboPhatSinh = 0;
        else
          this.cboPhatSinh = phatsinh
        let thue_gtgt = await this.RegRead("thue_gtgt", "")
        if (!this.isNullOrEmpty(thue_gtgt))
          this.txtThueGTGT = thue_gtgt
        //Tab XuLyThueBao
        this.txtTienGHM = await this.RegRead("tienGHM", 0)
        let kieukhoa = await this.RegRead("kieukhoa","")
        if (!this.isNullOrEmpty(kieukhoa))
          this.cboHinhThucKhoa = kieukhoa
        let kieumo = await this.RegRead("kieumo","")
        if (!this.isNullOrEmpty(kieumo))
          this.cboHinhThucMo = kieumo
        let kieuno_mo = await this.RegRead("kieuno_mo","")
        if (!this.isNullOrEmpty(kieuno_mo))
          this.cboKieuMo = kieuno_mo
        let motrongngay = await this.RegRead("motrongngay","")
        if (!this.isNullOrEmpty(motrongngay))
          this.chkTrongNgay.Checked = motrongngay == 1;
        else this.chkTrongNgay.Checked = false;
        let dichvu_mo = await this.RegRead("dichvu_mo","")
        if (!this.isNullOrEmpty(dichvu_mo))
          this.cboDichVuMo = dichvu_mo
        else this.cboDichVuMo = this.dsDichVuVT.length >0 ? this.dsDichVuVT[0].dichvuvt_id : null
        this.cboLoaiHinhKhoa = null
        let loaihinh_mo = await this.RegRead("loaihinh_mo","")
        if (!this.isNullOrEmpty(loaihinh_mo)){
          this.chkLoaiTBMo.Checked = true;
          this.cboLoaiHinhKhoa = loaihinh_mo
        }else {
          this.cboLoaiHinhMo = null
          this.chkLoaiTBMo.Checked = false;
        }
        let dichvu_khoa = await this.RegRead("dichvu_khoa","")
        if (!this.isNullOrEmpty(dichvu_khoa))
          this.cboDichVuKhoa = dichvu_khoa
        else this.cboDichVuKhoa = this.dsDichVuVT.length >0 ? this.dsDichVuVT[0].dichvuvt_id : null
        this.cboLoaiHinhKhoa = null
        let loaihinh_khoa = await this.RegRead("loaihinh_khoa","")
        if (!this.isNullOrEmpty(loaihinh_khoa)){
          this.chkLoaiTBKhoa.Checked = true;
          this.cboLoaiHinhKhoa = loaihinh_khoa
        }else {
          this.cboLoaiHinhKhoa = null
          this.chkLoaiTBKhoa.Checked = false;
        }
        //Tab DS_ThamSoIn
          //Hiển thị danh sách tham số hệ thống
        let dt1 = await this.LAY_DS_THAMSO_MACDINH(this.$auth.getNguoiDungID(),6)
        for (let item of dt1){
          let ma_ts_val = await this.RegRead(item.ma_ts.toLowerCase(),"")
          if (!this.isNullOrEmpty(ma_ts_val))
            item.giatri = ma_ts_val
          item.chon = false
        }
        this.gridTSIn = dt1
        //Tab Giải quyết khiếu nại
        let loai_chu_de = await this.RegRead("loai_chu_de","")
        if (this.isNullOrEmpty(loai_chu_de))
          this.$nextTick(() =>{
            this.$refs['cboLoai_CD'].$children[0].grid.selectRow(0)
          })
        else {
          let idx = this.dsLoai_CD.findIndex(i => i.loai_id == loai_chu_de)
          if (idx > -1)
            this.$nextTick(() =>{
              this.$refs['cboLoai_CD'].$children[0].grid.selectRow(idx)
            })
        }
        let hinhthuc_kn = await this.RegRead("hinhthuc_kn","")
        if (this.isNullOrEmpty(hinhthuc_kn))
          this.$nextTick(() =>{
            this.$refs['cboHinhThuc_KN'].$children[0].grid.selectRow(0)
          })
        else {
          let idx = this.ds_hinhthuc_config.findIndex(i => i.htkn_id == hinhthuc_kn)
          if (idx > -1)
            this.$nextTick(() =>{
              this.$refs['cboHinhThuc_KN'].$children[0].grid.selectRow(idx)
            })
        }
        //Khu vực mở máy
        let ds_khuvuc = await this.RegRead("ds_khuvuc","")
        let dtKhuVuc = this.HT_DonVi_loaidv_Combobox_HPG(LOAI_DV.TO_THU_CUOC)
        if (dtKhuVuc.length > 0){
          if (ds_khuvuc){
            this.ds_KV_ChuaGan_tmp = []
            this.ds_KV_DaGan_tmp = []
            this.chkMoMayTheoKV.Checked = true
            let dtKV_ChuaGan = dtKhuVuc.filter(item => !ds_khuvuc.includes(item.donvi_id))
            if (dtKV_ChuaGan.length > 0){
              let dtT = [...dtKV_ChuaGan]
              this.dgvKV_ChuaGan = dtT
              this.ds_KV_ChuaGan_tmp = []
              this.ds_KV_ChuaGan_tmp = dtT
            }else this.dgvKV_ChuaGan = []
            let dtKV_DaGan = dtKhuVuc.filter(item => ds_khuvuc.includes(item.donvi_id))
            if (dtKV_DaGan.length > 0){
              let dtT = [...dtKV_DaGan]
              this.dgvKV_DaGan = dtT
              this.ds_KV_DaGan_tmp = []
              this.ds_KV_DaGan_tmp = dtT
            }else this.dgvKV_DaGan = []
          }else {
            this.chkMoMayTheoKV.Checked = false
            this.dgvKV_ChuaGan = dtKhuVuc
            this.dgvKV_DaGan = []
            this.ds_KV_ChuaGan_tmp = []
            this.ds_KV_ChuaGan_tmp = dtKhuVuc
          }
        }
        //Quầy thu mở máy
        let ds_quay = await this.RegRead("ds_quay", "")
        let dtQuayThu = await this.HT_DonVi_loaidv_Combobox_HPG(LOAI_DV.QUAYTHU)
        if (dtQuayThu.length > 0){
          if (ds_quay){
            this.ds_QT_ChuaGan_tmp = []
            this.ds_QT_DaGan_tmp = []
            this.chkMoMayTheoQuayThu.Checked = true
            let dtQT_ChuaGan = dtQuayThu.filter(item => !ds_quay.includes(item.donvi_id))
            if (dtQT_ChuaGan.length > 0){
              let dtT = [...dtQT_ChuaGan]
              this.dgvQuayThu_ChuaGan = dtT

              this.ds_QT_ChuaGan_tmp = []
              this.ds_QT_ChuaGan_tmp = dtT
            }else this.dgvQuayThu_ChuaGan = []
            let dtQT_DaGan = dtQuayThu.filter(item => ds_quay.includes(item.donvi_id))
            if (dtQT_DaGan.length > 0){
              let dtT = [...dtQT_DaGan]
              this.dgvQuayThu_DaGan = dtT

              this.ds_QT_DaGan_tmp = []
              this.ds_KV_DaGan_tmp = dtT
            }else this.dgvQuayThu_DaGan = []
          }else {
            this.chkMoMayTheoQuayThu.Checked = false
            this.dgvQuayThu_ChuaGan = dtQuayThu
            this.dgvQuayThu_DaGan = []

            this.ds_QT_ChuaGan_tmp = []
            this.ds_QT_ChuaGan_tmp = dtQuayThu
          }
        }
      } catch (e) {
        console.log(e)
      } finally {
        // this.loading(false)
      }
    },
    async LoadTSBC() {
      try {
        // this.loading(true)
        this.grcMacdinh = await this.GET_THAMSO_MACDINH(7, this.$auth.getNguoiDungID())
        // this.grcMacdinh[1].loai_dk = 1
        // this.grcMacdinh[2].loai_dk = 2
        // this.grcMacdinh[3].loai_dk = 3
        // this.grcMacdinh[4].loai_dk = 7
        // this.grcMacdinh[4].giatri = '1'
      }catch (e) {

      }finally {
        // this.loading(false)
      }
    },
    async HienThiTrangThai() {
      this.dblSettingval = await this.RegRead("cackhoanno", "2450592256")
    },
    async tsbtnGhiLai_Click() {
      try {
        this.loading(true)
        switch (this.tabControl.SelectedIndex) {
          case 0:
            let idxs = this.$refs['gridThamSo'].grid.getSelectedRowIndexes()
            for (let idx of idxs)
              this.gridThamSo[idx].chon = true
            if (!await this.CapNhatHeThong())
              return
            break
          case 1:
            await this.CapNhatTSCT();
            break
          case 2:
            await this.CapnhatTKGD()
            break
          case 3:
            this.CapnhatCacKhoanNo()
            break
          case 4:
            await this.CapNhatXuLyTB()
            break
          case 5:
            if (!await this.CapNhatTSIn())
              return
            break
          case 6:
            await this.CapNhat_ThamSo_GQKN()
            break
        }
        await this.LoadTT()
        this.$root.toastSuccess("Đã ghi xong giá trị")
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async RegRead(valueName, defaultValue) {
      try {
        let res = await this.$root.context.post("web-ecms/webRegistry/getValue", {
          keyName: valueName
        })
        return res.data
      }catch (e) {
        return defaultValue
      }
      // let obj = JSON.parse(localStorage.getItem("OneBSS-ThamSoQuanLyThuNo-" + this.$auth.getNguoiDungID() + "-" + this.$auth.getPhanVungID()))
      // if (obj)
      //   return obj[valueName]
      // else return defaultValue
    },
    async RegWrite(valueName, value) {
      if (value == null || value == "") return
      await this.$root.context.post("web-ecms/webRegistry/setValue", {
        keyName: valueName,
        value: value
      }).catch(e => {
        throw e
      })
      // let obj = JSON.parse(localStorage.getItem("OneBSS-ThamSoQuanLyThuNo-" + this.$auth.getNguoiDungID() + "-" + this.$auth.getPhanVungID()))
      // if (!obj)
      //   obj = {}
      // obj[valueName] = value
      // localStorage.setItem("OneBSS-ThamSoQuanLyThuNo-" + this.$auth.getNguoiDungID() + "-" + this.$auth.getPhanVungID(), JSON.stringify(obj))
    },
    async CapNhat_ThamSo_GQKN() {
      await this.RegWrite("loai_chu_de", this.cboLoai_CD)
      await this.RegWrite("hinhthuc_kn", this.cboHinhThuc_KN)
    },
    async CapNhatHeThong() {
      try {
        // this.loading(true)
        await this.RegWrite("quyen", this.txtQuyen)
        await this.RegWrite("seri", this.txtSeri)
        await this.RegWrite("soseri", this.txtSoSeri)
        await this.RegWrite("kyhoadon", this.dtpKyHD)
        await this.RegWrite("chuky", this.cboChuKy)
        if (this.chkInDungThue.Checked)
          await this.RegWrite("indungthue", 1);
        else
          await this.RegWrite("indungthue", 0);
        if (this.chkInLDKhongThue.Checked)
          await this.RegWrite("inkhongthue", 1);
        else
          await this.RegWrite("inkhongthue", 0);
        if (this.chkInTenThuQuy.Checked)
          await this.RegWrite("intenthuquy", 1);
        else
          await this.RegWrite("intenthuquy", 0);
        if (this.chkkhogiayphieuthu.Checked)
          await this.RegWrite("ingiaya5", 1);
        else
          await this.RegWrite("ingiaya5", 0);
        let idxHinhThucTT = this.dsHinhThucTT.findIndex(i => i.httt_id == this.cboHinhThucTT)
        if (idxHinhThucTT > 0) //cboHinhThucTT.ItemIndex > 0
          await this.RegWrite("httt_id", this.cboHinhThucTT);
        else await this.RegWrite("httt_id", null);
        let idxNganHang = this.dsNganHang.findIndex(i => i.nganhang_id == this.cboNganHang)
        if (idxNganHang > 0)
          await this.RegWrite("nganhang_id", this.cboNganHang);
        else
          await this.RegWrite("nganhang_id", null);
        let idxThuNgan = this.dsThuNgan.findIndex(i => i.nhanvien_id == this.cboThungan)
        if (idxThuNgan > 0)
          await this.RegWrite("thungan_id", this.cboThungan);
        else
          await this.RegWrite("thungan_id", null);
        for (let row of this.gridThamSo) {
          let vthamso = row.ma_ts
          if (row.chon) {
            await this.RegWrite(vthamso.toLowerCase(), 1);
            await this.CAPNHAT_THAMSO_MACDINH(vthamso, this.nguoidung_id, 1);
          }else{
            await this.RegWrite(vthamso.toLowerCase(), null)
            await this.CAPNHAT_THAMSO_MACDINH(vthamso, this.nguoidung_id, 0);
          }
        }
        return true;
      } catch (e) {
        if (e.response.data.message_detail) this.$root.toastError(e.response.data.message_detail)
        else this.$root.toastError("Có lỗi xảy ra khi cập nhật hệ thống")
        return false;
      }finally {
        // this.loading(false)
      }
    },
    async CapNhatTSCT() {
      try {
        // this.loading(true)
        for (let item of this.grcMacdinh) {
          let ma_ts = item.ma_ts
          if (item.loai_dk == 6) {
            if (item.blob_val) {
              await this.DeleteBlob(this.$auth.getNguoiDungID(),ma_ts)
              this.TaoDuLieuBlob(ma_ts, this.$auth.getNguoiDungID(),item.blob_val)
              if(this.CT_TSMD_BLOB_Data.length > 0) await this.InsertBlob(this.CT_TSMD_BLOB_Data[0])
            }
          }else {
            let giatri = item.giatri
            await this.Delete(this.$auth.getNguoiDungID(),ma_ts)
            this.TaoDuLieu(ma_ts, this.$auth.getNguoiDungID(), giatri)
            if (this.CT_TSMD_Data.length > 0) await this.Insert(this.CT_TSMD_Data[0])
          }
        }
        // this.$root.toastSuccess("Cập nhật tham số thành công!")
      } catch (e) {
        if (e.response.data.message_detail)
          this.$root.toastError("Lỗi: "+ e.response.data.message_detail)
        else this.$root.toastError("Có lỗi xảy ra khi cập nhật tham số")
        throw e
      }finally {
        // this.loading(false)
      }
    },
    TaoDuLieuBlob(ma_ts, nguoidung_id, giatri){ // giatri byte[]
      this.CT_TSMD_BLOB_Data = []
      let row = {}
      row.nguoidung_id = nguoidung_id;
      row.ma_ts = ma_ts;
      row.giatri = giatri;
      this.CT_TSMD_BLOB_Data.push(row)
    },
    async DeleteBlob(nguoidung_id, ma_ts){
      await this.$root.context.post("web-quantri/thamso-quanly-thuno/ct_tsmd_blob-delete",{
        nguoidung_id:nguoidung_id,
        ma_ts:ma_ts
      }).then(res =>{
        if (res.message_detail) throw res
      }).catch(e => {throw e})
    },
    async InsertBlob(CT_TSMD_BLOB_Data){
      await this.$root.context.post("web-quantri/thamso-quanly-thuno/ct_tsmd_blob-insert",CT_TSMD_BLOB_Data).then(res =>{
        if (res.message_detail) throw res
      }).catch(e => {throw e})
    },
    async Delete(nguoidung_id, ma_ts){
      await this.$root.context.post("web-quantri/thamso-quanly-thuno/ct_tsmd-delete",{
        nguoidung_id:nguoidung_id,
        ma_ts:ma_ts
      }).then(res =>{
        if (res.message_detail) throw res
      }).catch(e => {throw e})
    },
    async Insert(CT_TSMD_Data){
      await this.$root.context.post("web-quantri/thamso-quanly-thuno/ct_tsmd-insert",CT_TSMD_Data).then(res =>{
        if (res.message_detail) throw res
      }).catch(e => {throw e})
    },
    TaoDuLieu(ma_ts, nguoidung_id, giatri){ // giatri string
      try {
        if (giatri instanceof Date){
          // giatri is date
          giatri = this.ddmmyyyy(giatri)
        }
        this.CT_TSMD_Data = []
        let row = {}
        row.nguoidung_id = nguoidung_id;
        row.ma_ts = ma_ts;
        row.giatri = giatri;
        this.CT_TSMD_Data.push(row)
      }catch (e) {

      }
    },
    ddmmyyyy(date) {

      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y}`
    },
    getDateElements(date) {

      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
      }
    },
    async CapnhatTKGD() {
      await this.RegWrite("TenGiaoDich", this.txtTenGiaoDich);
      await this.RegWrite("DiaChiGiaoDich", this.txtDiaChiGiaoDich);
      await this.RegWrite("SoDT", this.txtSoDT);
      await this.RegWrite("SoFax", this.txtSoFax);
      await this.RegWrite("SoTK", this.txtSoTK);
      await this.RegWrite("NganHang", this.txtNganHang);
      await this.RegWrite("TKVietel", this.txtTKVietel);
      await this.RegWrite("TKPostel", this.txtTKPostel);
      await this.RegWrite("SoDong", this.txtSoDong);
      await this.RegWrite("KhoangCachDoc", this.txtKhoangCachDoc);
      await this.RegWrite("KhoangCachNgang", this.txtKhoangCachNgang);
    },
    CapnhatCacKhoanNo(){
      let dblTmp
      this.dblSettingval = 0;
      dblTmp = 1;
    },
    CapnhatKhoanNoQD(){},
    async CapNhatDCNo() {
      await this.RegWrite("dauky", this.cboDauKy);
      await this.RegWrite("phatsinh", this.cboPhatSinh);
      await this.RegWrite("thue_gtgt", (this.txtThueGTGT + '').trim());
    },
    async CapNhatXuLyTB() {
      await this.RegWrite("tienGHM", this.txtTienGHM);
      await this.RegWrite("kieukhoa", this.cboHinhThucKhoa);
      await this.RegWrite("kieumo", this.cboHinhThucMo);
      await this.RegWrite("kieuno_mo", this.cboKieuMo);
      if (this.chkTrongNgay.Checked)
        await this.RegWrite("motrongngay", 1);
      else
        await this.RegWrite("motrongngay", 0);

      await this.RegWrite("dichvu_mo", this.cboDichVuMo);
      if (this.chkLoaiTBMo.Checked)
        await this.RegWrite("loaihinh_mo", this.cboLoaiHinhMo);
      else
        await this.RegWrite("loaihinh_mo", null);
      await this.RegWrite("dichvu_khoa", this.cboDichVuKhoa);
      if (this.chkLoaiTBKhoa.Checked)
        await this.RegWrite("loaihinh_khoa", this.cboLoaiHinhKhoa);
      else
        await this.RegWrite("loaihinh_khoa", null);
      let ds_khuvuc = "";
      if (this.chkMoMayTheoKV.Checked) {
        for (let i = 0; i < this.dgvKV_DaGan.length; i++)
          ds_khuvuc += this.dgvKV_DaGan[i]['donvi_id'] + ",";
        if (ds_khuvuc.length > 0)
          ds_khuvuc = ds_khuvuc.substr(0, ds_khuvuc.length - 1);

        await this.RegWrite("ds_khuvuc", ds_khuvuc);
      } else
        await this.RegWrite("ds_khuvuc", null);
      let ds_quay = "";
      if (this.chkMoMayTheoQuayThu.Checked) {
        for (let i = 0; i < this.dgvQuayThu_DaGan.length; i++)
          ds_quay += this.dgvQuayThu_DaGan[i]["donvi_id"] + ",";
        if (ds_quay.length > 0)
          ds_quay = ds_quay.substr(0, ds_quay.length - 1);

        await this.RegWrite("ds_quay", ds_quay);
      } else
        await this.RegWrite("ds_quay", null);
    },
    async CapNhatTSIn() {
      try {
        let idxs = this.$refs['gridTSIn'].grid.getSelectedRowIndexes()
        // for (let idx of idxs)
        //   this.gridTSIn[idx].chon = true
        console.log(idxs.length)
        if (idxs.length <= 0) {
          this.$root.toastError("Bạn chưa chọn tham số trên lưới để cập nhật");
          return false;
        }
        for (let idx of idxs){
          let vthamso = this.gridTSIn[idx].ma_ts,
            vgiatri = this.gridTSIn[idx].giatri
          await this.CAPNHAT_THAMSO_MACDINH(vthamso, this.nguoidung_id, vgiatri)
          await this.RegWrite(vthamso.toLowerCase(), vgiatri);
        }
        // for (let i = 0; i < this.gridTSIn.length; i++)
        //   if (this.gridTSIn[i].chon) {
        //     let vthamso = this.gridTSIn[i].ma_ts,
        //       vgiatri = this.gridTSIn[i].giatri
        //     await this.CAPNHAT_THAMSO_MACDINH(vthamso, this.nguoidung_id, vgiatri)
        //     this.RegWrite(vthamso.toLowerCase(), vgiatri);
        //   }
      }catch (e) {
        if (e.response.data.message_detail) this.$root.toastError("Có lỗi: "+ e.response.data.message_detail)
        else this.$root.toastError("Có lỗi xảy ra khi cập nhật tham số in")
      }
      return true;
    },
    Unformat_tien(s){
      s = s.toLowerCase().trim();
      let ketqua = ""
      for (let i = 0; i < s.length; i++){
        if (s[i] != "," && s[i] != "." && s[i] != " ")
        {
          ketqua = ketqua + s[i];
        }
      }
      return ketqua.trim()
    },
    // async cboDichVuKhoa_EditValueChanged() {
    //   let dichvuvt_id
    //   if (this.cboDichVuKhoa) {
    //     dichvuvt_id = parseInt(this.cboDichVuKhoa + '', 10)
    //     if (!this.kt_load) {
    //       // Combo loại hình khóa
    //       this.cboLoaiHinhKhoa = null
    //       this.dsLoaiHinhKhoa = await this.getDsLoaiHinh(this.cboDichVuKhoa)
    //     }
    //   }
    // },
    async LAY_DS_THAMSO_MACDINH(nguoidung_id, kieu_id){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/lay-ds-thamso-macdinh",{
        nguoidung_id:nguoidung_id,
        kieu_id:kieu_id
      }).catch(e => {
        throw e
      })
      return rs.data
    },
    async HT_Cbo_ChuKyNo_Theo_kyhoadon(namthang){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/ht-cbo-chukyno-theo-kyhoadon",{namthang:namthang}).catch(e => {
        throw e
      })
      return rs.data
    },
    async LAY_THANG_NO() {
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/laythangno").catch(e => {
        throw e
      })
      return rs.data.result
    },
    async Hienthi_DS_HTTT(sudung){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/hienthi-ds-httt",{sudung:sudung}).catch(e =>{
        throw e
      })
      return rs.data
    },
    async Lay_ds_nganhang_gachno(){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/lay-ds-nganhang-gachno").catch(e =>{
        throw e
      })
      return rs.data
    },
    async DS_HINHTHUC_CONFIG(){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/lay-ds-hinhthuc-kn").catch(e =>{
        throw e
      })
      return rs.data
    },
    async HT_DS_DichVuVT(nguoidung_id){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/hienthi-ds-dichvuvt",{nguoidung_id: nguoidung_id}).catch(e =>{
        throw e
      })
      return rs.data
    },
    async getDsLoaiHinh(dichvuvt_id){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/ds-loaihinh",{dichvuvt_id: dichvuvt_id}).catch(e =>{
        throw e
      })
      return rs.data
    },
    async getDSThuNgan(loainv_id, nhanvien_id, nguoidung_id){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/ds-thungan",{
        loainv_id: loainv_id,
        nhanvien_id:nhanvien_id,
        nguoidung_id:nguoidung_id
      }).catch(e =>{
        throw e
      })
      return rs.data
    },
    async HT_DonVi_loaidv_Combobox_HPG(loaidv_id){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/ht-donvi-loaidv",{loaidv_id: loaidv_id}).catch(e =>{
        throw e
      })
      return rs.data
    },
    async GET_THAMSO_MACDINH(kieu_id, nguoidung_id){
      let rs = await this.$root.context.get("web-quantri/thamso-quanly-thuno/get-thamso-macdinh",{
        kieu_id: kieu_id,
        nguoidung_id:nguoidung_id
      }).catch(e =>{
        throw e
      })
      return rs.data
    },
    async CAPNHAT_THAMSO_MACDINH(ma_ts, nguoidung_id, giatri){
      await this.$root.context.post("web-quantri/thamso-quanly-thuno/capnhat-thamso-macdinh",{
        vma_ts: ma_ts,
        vnguoidung_id:nguoidung_id,
        vgiatri:giatri
      }).catch(e =>{
        throw e
      })
    },
    isNullOrEmpty: function (value) {
      return (
        value == undefined || value == null || value.toString().trim() == ""
      );
    },
    tabSelected(tabId, tabIdx){
      document.getElementById(tabId).click();
      this.tabControl.SelectedIndex = tabIdx
    },
    gridThamSoRowSelected(args){
      // console.log(args)
      // this.gridThamSo[args.rowIndex].chon = true
    },
    gridThamSoRowDeselected(args){
      // console.log(args)
      // this.gridThamSo[args.rowIndex].chon = false
    },
    onCellSavedGridTSIn(){
      let gridTSIn = this.$refs['gridTSIn']
      let changes = gridTSIn.getBatchChanges()
      gridTSIn.batchUpdate(changes)
    },
    btnNext_KV_Click(){
      if (this.dgvKV_DaGan.length == 0) return
      let idxs = this.$refs['dgvKV_DaGan'].grid.getSelectedRowIndexes()
      //insert
      for (let idx of idxs){
        this.dgvKV_ChuaGan.push(this.dgvKV_DaGan[idx])
      }
      // remove
      this.dgvKV_DaGan = this.dgvKV_DaGan.filter((item, idx) => !idxs.includes(idx))
      //refresh
      this.dgvKV_ChuaGan = [...this.dgvKV_ChuaGan]
      this.dgvKV_DaGan = [...this.dgvKV_DaGan]
    },
    btnLast_KV_Click(){
      if (this.dgvKV_DaGan.length == 0)
        return
      //insert
      this.dgvKV_ChuaGan = [...this.dgvKV_ChuaGan,...this.dgvKV_DaGan]
      //remove
      this.dgvKV_DaGan = []
    },
    btnPrevious_KV_Click(){
      if (this.dgvKV_ChuaGan.length == 0) return
      let idxs = this.$refs['dgvKV_ChuaGan'].grid.getSelectedRowIndexes()
      //insert
      for (let idx of idxs){
        this.dgvKV_DaGan.push(this.dgvKV_ChuaGan[idx])
      }
      // remove
      this.dgvKV_ChuaGan = this.dgvKV_ChuaGan.filter((item, idx) => !idxs.includes(idx))
      //refresh
      this.dgvKV_ChuaGan = [...this.dgvKV_ChuaGan]
      this.dgvKV_DaGan = [...this.dgvKV_DaGan]
    },
    btnFirst_KV_Click(){
      if (this.dgvKV_ChuaGan.length == 0) return
      //insert
      this.dgvKV_DaGan = [...this.dgvKV_DaGan,...this.dgvKV_ChuaGan]
      //remove
      this.dgvKV_ChuaGan = []
    },
    btnNext_Click(){
      if (this.dgvQuayThu_DaGan.length == 0) return
      let idxs = this.$refs['dgvQuayThu_DaGan'].grid.getSelectedRowIndexes()
      //insert
      for (let idx of idxs){
        this.dgvQuayThu_ChuaGan.push(this.dgvQuayThu_DaGan[idx])
      }
      // remove
      this.dgvQuayThu_DaGan = this.dgvQuayThu_DaGan.filter((item, idx) => !idxs.includes(idx))
      //refresh
      this.dgvQuayThu_ChuaGan = [...this.dgvQuayThu_ChuaGan]
      this.dgvQuayThu_DaGan = [...this.dgvQuayThu_DaGan]
    },
    btnLast_Click(){
      if (this.dgvQuayThu_DaGan.length == 0)
        return
      //insert
      this.dgvQuayThu_ChuaGan = [...this.dgvQuayThu_ChuaGan,...this.dgvQuayThu_DaGan]
      //remove
      this.dgvQuayThu_DaGan = []
    },
    btnPrevious_Click(){
      if (this.dgvQuayThu_ChuaGan.length == 0) return
      let idxs = this.$refs['dgvQuayThu_ChuaGan'].grid.getSelectedRowIndexes()
      //insert
      for (let idx of idxs){
        this.dgvQuayThu_DaGan.push(this.dgvQuayThu_ChuaGan[idx])
      }
      // remove
      this.dgvQuayThu_ChuaGan = this.dgvQuayThu_ChuaGan.filter((item, idx) => !idxs.includes(idx))
      //refresh
      this.dgvQuayThu_ChuaGan = [...this.dgvQuayThu_ChuaGan]
      this.dgvQuayThu_DaGan = [...this.dgvQuayThu_DaGan]
    },
    btnFirst_Click(){
      if (this.dgvQuayThu_ChuaGan.length == 0) return
      //insert
      this.dgvQuayThu_DaGan = [...this.dgvQuayThu_DaGan,...this.dgvQuayThu_ChuaGan]
      //remove
      this.dgvQuayThu_ChuaGan = []
    },
    /***
     * Tab THAM SỐ BÁO CÁO
     */
    updateDataSource: function (dataItem) {
      let item = this.grcMacdinh.find((x) => x.ma_ts == dataItem.ma_ts);
      this.updateData(item, dataItem);
    },
    updateData: function (item, newItem) {
      if (item != null) {
        let keys = Object.keys(newItem);
        for (let i = 0; i < keys.length; i++) {
          const key = keys[i];
          if (item[key] != newItem[key]) item[key] = newItem[key];
        }
      }
    },
  },
  watch:{
    cboDichVuKhoa:async function (val) {
      if (val) {
        if (!this.kt_load) {
          // Combo loại hình khóa
          this.cboLoaiHinhKhoa = null
          try {
            this.loading(true)
            this.dsLoaiHinhKhoa = await this.getDsLoaiHinh(val)
          }catch (e) {

          }finally {
            this.loading(false)
          }
        }
      }
    },
    cboDichVuMo:async function (val) {
      if (val) {
        if (!this.kt_load) {
          // Combo loại hình mở
          this.cboLoaiHinhKhoa = null
          try {
            this.loading(true)
            this.dsLoaiHinhMo = await this.getDsLoaiHinh(val)
          }catch (e) {

          }finally {
            this.loading(false)
          }
        }
      }
    }
  },
  mounted() {
    this.dsLoai_KhieuNai_Config = [
      {
        loai_id:1, ten_loai:'Khiếu nại Đại lý',
      },
      {
        loai_id:2, ten_loai:'Khiếu nại Nghiệp vụ',
      },
    ]
    this.dsHinhThucKhoa = [
      {
        kieunhac_id:0, kieunhac:'Nợ đầu kỳ'
      },
      {
        kieunhac_id:1, kieunhac:'Tổng nợ'
      },
      {
        kieunhac_id:5, kieunhac:'Nợ phát sinh'
      },
    ]
    this.dsKieuMo = [
      {
        kieumo_id:0, kieumo:"Mở theo từng số máy"
      },
      {
        kieumo_id:1, kieumo:"Mở theo mã thanh toán"
      },
    ]
  }
}
</script>

<style>
.text-rose {
  color: #FF007F !important;
}
.e-grid .e-rowcell {
  overflow: inherit !important;
}
.grid-thamso .e-checkbox-wrapper .e-stop::before,
.grid-thamso .e-css.e-checkbox-wrapper .e-stop::before {
  content: '\e7a5'!important;
  background: #fff;
  color: #0176FF;
  border-color: #0176FF;
  font-size: 14px;
}
</style>
