<template src="./RegMultiServicePackage.html"></template>
<style src="./RegMultiServicePackage.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import frmNguoiGT from "@/modules/contract/setup/SeparationSubscriber/popups/frmNguoiGT.vue"
import {
  NHOM_LNV,
  TrangThaiHD,
  LoaiHopDong,
  LoaiHinhTB,
  KieuLapDat,
  TRANGTHAI_DONGBO
} from "@/const/enums";
import searchAccount from '@/modules/search/subscriber/SearchAccount'
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import ThongTinEmail from "../ChangeContractOwner/popup/ThongTinEmail.vue";
import ChonCongTacVienModal from "@/modules/contract/setup/SurveyRequest/popups/ChonCongTacVien/ChonCongTacVienModal.vue";
import ChonNguoiGTModal from "@/modules/contract/setup/SurveyRequest/popups/ChonNguoiGT/ChonNguoiGTModal.vue";
import ThueBaoCungMa from '@/modules/EXTS/HOPDONG/DichChuyenNew/components/ThongTinThueBao/Popups/ThueBaoCungMa/index.vue'

import moment from 'moment'
import { async, Promise, reject } from 'q';
//
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    API,DatePicker,
    frmNguoiGT,
    searchAccount,
    SearchContractModal,
    ThongTinEmail,
    ChonCongTacVienModal,
    ChonNguoiGTModal,
    ThueBaoCungMa
},
  name: 'RegMultiServicePackage',
  mounted () {
      this.frmDangKyGoiDaDV_Load()
      this.$refs.txtMaGD.focus()
  },
  watch : {
    //   'cboLoaiHinhThueBao.value'(newValue, oldValue) {
    //       this.LoadDS_Goi()
    //   }
  },
  data () {
    return {
        target: document.querySelector('body'),
        settingSelect2 : {
            language: 'vi'
        },
        cboDichVuVienThong : {
          list : [],
          value : 0,
          disable: false,
        },
        cboLoaiHinhThueBao : {
          list : [],
          value : 0,
          name : '',
          disable: false,
        },
        cboKieuLapDat : {
          list : [],
          value : 280,
          disable: false,
        },
        danhSachThueBaoGoiTable : {
            list : [],
            header : [
                {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width : 'auto'}, 
                {fieldName: 'ten_tb' , headerText: 'Tên thuê bao', allowFiltering: true, width : 'auto'}, 
                {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, width : 'auto'},
                {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, width : 'auto'},
                {fieldName: 'ten_goi', headerText: 'Gói cước', allowFiltering: true, width : 'auto'},
            ],
            value : {},
            isEnabled : true,
        },
        cboDanhSachGoi : {
          list : [],
          value : 0,
          textField: "ten_goi",
          valueField: "goi_id",
          panelDataHeight: "auto",
          headers: [
            {
                fieldName: "ma_goi",
                headerText: "Mã gói",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ten_goi",
                headerText: "Tên gói",
                allowFiltering: true,
                allowSorting: false,
            },
            {
                fieldName: "ten_cv",
                headerText: "Tên công văn",
                allowFiltering: true,
                allowSorting: false,
            }
          ],
        },
        cboHieuLuc : {
            list : [
                {
                    'id' : 0,
                    'text' : 'Tức thì'
                },
                {
                    'id' : 1,
                    'text' : 'Tháng sau'
                },
                {
                    'id' : 2,
                    'text' : 'Khác'
                }
            ],
            value : 0,
            disable: false,
        },
        det : [],
        danhSachThamSoMacDinh : [],
        ts_sinhma_gd_theo_donvi : false,
        khong_batbuoc_ctv : 0,
        batbuoc_gioithieu_dichchuyen : 0,
        VPHIEUCSKH_ID : 0,
        VPHIEUCSKHDV_ID : 0,

        dichvuvt_id : 0,
        hdkh_id : 0,
        nhomtb_id : 0,
        hdtb_id :  0,
        donvi_id : 0,
        khachhang_id : 0,
        thuebao_id : 0,
        goi_id : 0,
        khlon_id : 0,
        ten_tb : "",
        diachi_ld : "",
        loaitb_id : 0,
        doituong_id : 0,
        dtpNgayLHD: new Date(),
        dateFormat: 'DD/MM/YYYY HH:mm:ss',
        dateFormatMonth: 'DD/MM/YYYY',
        typeFormat: 'date',
        typeFormatMonth: 'month',
        dateConfig: {
            dateFormat: 'd/m/Y',
            altFormat: 'd/m/Y'
        },
        params: {
            isIPtinh: 0,
            p_today: new Date(),
            disable: true,
            p_today_string : moment(new Date()).format('DD/MM/YYYY'),
        },
        huonggiaotn_id : 0,//-- Huong giao yeu cau dang ky goi da dv
        quytrinh_id : 0,
        loi_sinh_magd : false,
        ma_kh : '',
        txtMaKH : '',
        txtTenKH : '',
        txtMoTa : '',
        txtDiaChiKH : '',
        txtMaTB : '',
        txtGhiChu : '',
        txtSoCA : '',
        txtMaSoThueCA : '',
        DichVuVienThong : {
            TATCA : -1,
            CO_DINH : 1,
            DI_DONG : 2,
            ADSL : 4,
            DD_TRATRUOC : 5,
            EMAIL : 6,
            METRONET : 7,
            MEGAWAN : 8,
            TSL : 9,
            GPHONE : 10,
            IMS : 11,
            MEGA_EYES : 12,
            HOINGHI_TRUYENHINH : 13,  
            ANTOAN_BAOMAT_TT : 14,
            DICHVU_CNTT : 15,
            TRUNGTAM_DULIEU : 16,
            HATANG_VIENTHONG : 19,

            IMS_DOITAC : 21,
            BRCD_DOITAC : 22,
            MGW_DOITAC : 23,
            TSL_DOITAC : 24
        },
        LoaiHinhTB : {
            DIENTHOAI_CD : 1,
            MEGA : 11,
            LEASEDLINE : 22,
            MEGAWAN_ADSL : 23
        },
        dgrList : [],
        nguoigt_id: "0",
        ctv_id : "0",
        donvi_ctv_id : "0",
        loainv_ctv_id : "0",

        donvi_nguoi_gt_id: "0",
        loainv_nguoi_gt_id: "0",
        position: { X: 'center', Y: 'top' },
        modalSearch: {},
        DONGBO_TEST : {
            TEST : false
        },
        tsbtnNhapMoi : false,
        tsbtnGhiLai : false,
        tsbtnXoa : false,
        tsbtnHuyBo : false,
        tsbtnThemTB : false,
        tsbtnXoaTB : false,
        tsbtnCapNhatDB : false,
        filterbox: {
            ctv: {
            value: "",
            ischecked: false,
            ma_ctv: "",
            ctv_id: 0,
            },
            nguoigt: {
            value: "",
            ischecked: false,
            nguoigt_id: "",
            },
        },
        chkChuNhom : false,
        txtSoThang : "",
        dsHDTB : {},
        dsHDKH : {},
        hdtbObj : {},
        objHdtb : {},
        dsHDTBGOI : {},
        magoi_vdc : "",
        magoi_vasc : "",
        matocdo_vasc : "",
        ds_matb_dd : "",
        _dt_goi_ccbs : [],
        kichhoat : false,
        kichhoat_ccbs : false,
        dtList : [],
        arrKichHoat : [],
        goi_neo_id : ""
    }
  },
  computed: {
    
    
  },
  methods : {
    showAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    showSuccess(mesage) {
        this.$toast.success(mesage);
    },

    getData: function (response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '0') {
        return response.data.data
      } else if (response.data.error_code == "BSS-00000204") {

      } else {
        this.$toast.error('Lỗi ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        console.log(response.data.error_code)
      }
      return []
    },

    showPopupSearchContract(){
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item){
    //   this.khachhang.magd = item.ma_gd;
    //   if(this.khachhang.magd && this.khachhang.magd.trim()!="")
    //     this.MaGDChanged();
    },


    async frmDangKyGoiDaDV_Load() {
        let input = {}
        let str_vtemp = ''
        this.getListDichVuVienThong()
        await this.getListLoaiHinhThuebao()
        this.getListKieuLapDat()
        this.LoadDS_Goi()
        let thamSoMacDinh = this.getData(await API.ds_thamso_md(this.axios, input))

        if (thamSoMacDinh && thamSoMacDinh.length>0) {
            let arrFilter = thamSoMacDinh.filter(item => {
                return item.ma_ts == 'SINH_MA_GD_THEO_DV'
            })
            if (arrFilter.length > 0) {
                this.ts_sinhma_gd_theo_donvi = true
            } else {
                this.ts_sinhma_gd_theo_donvi = false
            }
        }

        if (thamSoMacDinh && thamSoMacDinh.length>0) {
            this.det = thamSoMacDinh.filter(item => {
                return item.ma_ts == 'KHONGBATBUOC_CTV'
            })

            if (this.det.length > 0) {
                if (this.det[0].ten_ts == 1) {
                    this.khong_batbuoc_ctv = 1
                }
            }
            this.det = thamSoMacDinh.filter(item => {
                return item.ma_ts == 'BATBUOC_GIOITHIEU_DICHCHUYEN'
            })

            if (this.det.length > 0) {
                if (this.det[0].ten_ts == 1) {
                    this.batbuoc_gioithieu_dichchuyen = 1
                }
            }
        }
        this.setButton(-1)
    },

    async LoadDS_Goi() {
        let input = {
            "loaitb_id" : this.cboLoaiHinhThueBao.value
        }
        let data = this.getData(await API.lay_ds_goi_cv_vnp_v1(this.axios, input))
        return new Promise((resolve, reject) => {
            if (data && data.length >= 0) {
                this.cboDanhSachGoi.list = data
            }
            resolve(true)
        })
    },
    acceptSearchContract(item) {
      this.$refs.txtMaGD.value = item.hopdong.ma_gd ? item.hopdong.ma_gd : "";
      this.$refs.txtMaTB.value = item.thuebao.ma_tb ? item.thuebao.ma_tb : ""
      this.$refs.popupFrmTraCuuHopDong.hide();
    },
    setButton(kieu) {
        this.tsbtnNhapMoi = false;
        this.tsbtnGhiLai = false;
        this.tsbtnXoa = false;
        this.tsbtnHuyBo = false;
        this.tsbtnThemTB = false;
        this.tsbtnXoaTB = false;
        this.tsbtnCapNhatDB = false;


        if (kieu == -1)//Bat dau
        {
            // txtMaTB.Focus();
            this.tsbtnGhiLai = true
            this.tsbtnHuyBo = true
        }

        if (kieu == 0)//Bat dau
        {
            // txtMaTB.Focus();
            this.tsbtnNhapMoi = true
            this.Clear()
        }

        if (kieu == 1)//Them moi
        {
            // txtMaTB.Focus();
            this.tsbtnGhiLai = true;
            this.tsbtnHuyBo = true;
            this.Clear();
        }

        if (kieu == 2)//Huy
        {
            // txtMaGD.Focus();
            this.tsbtnNhapMoi = true;
            this.tsbtnXoa.Enabled = true;
            this.Clear();
        }

        if (kieu == 3)//Edit
        {
            // txtMaGD.Focus();

            this.tsbtnNhapMoi = true;
            this.tsbtnXoa = true;
            this.tsbtnGhiLai = true;
            this.tsbtnHuyBo = true;
            this.tsbtnXoaTB = true;
            this.tsbtnThemTB = true;
            this.tsbtnCapNhatDB = true;
            if (this.dgrList.length > 1) {
                this.tsbtnXoaTB = true;
            }
            else {
                this.tsbtnXoaTB = false;
            }
        }
    },
    async getListDichVuVienThong() {
        let input = {}
        let data = this.getData(await API.CSS_DICHVU_VT(this.axios, input))

        if (data && data.length > 0) {
            this.cboDichVuVienThong.list = data.map(item => {
                return {
                    id : item.DICHVUVT_ID,
                    text : item.TEN_DVVT
                }
            })
            this.cboDichVuVienThong.value = this.cboDichVuVienThong.list[0].id
            this.dichvuvt_id = this.cboDichVuVienThong.value

        }
    },
     async getListKieuLapDat() {
        let input = {}
        let data = this.getData(await API.CSS_KIEU_LD(this.axios, input))


        if (data && data.length > 0) {
            let arr = data.map(item => {
                return {
                    id : item.KIEULD_ID,
                    text : item.TEN_KIEULD
                }
            })


            let res = arr.filter(item => {
                return item.id == 280
            })
            this.cboKieuLapDat.list = res
            this.cboKieuLapDat.value = this.cboKieuLapDat.list[0].id

        }
    },

    async getListLoaiHinhThuebao() {
        let input = {}
        let data = this.getData(await API.CSS_LOAIHINH_TB(this.axios, input))

        if (data && data.length > 0) {
            this.cboLoaiHinhThueBao.list = data.map(item => {
                return {
                    id : item.LOAITB_ID,
                    text : item.LOAIHINH_TB,
                }
            })
            this.cboLoaiHinhThueBao.value = this.cboLoaiHinhThueBao.list[0].id
            this.cboLoaiHinhThueBao.name = this.cboLoaiHinhThueBao.list[0].text

        }
    },

    async txtMaTB_KeyPress() {
        if (this.txtMaTB != "")
        {
            let input = {
                'dichVuVtId' : this.cboDichVuVienThong.value,
                'donViDlId' : this.$root.token.getDonViDuLieuID(),
                'loaiHdId' : 27,
                'loaiTbId' : this.cboLoaiHinhThueBao.value,
                'maTb' : this.txtMaTB,
                'ttHdId' : TrangThaiHD.MOI
            }

            let ds = this.getData(await API.lay_ds_hopdong_theo_matb(this.axios, input))

            if (ds && ds.length > 0) {
                this.HienThiTTHopDongKH(ds);
            }
            else {
                this.HienThiTT_DanhBa(this.txtMaTB);
            }
        }
        // await this.LoadDS_Goi()
    },

    async txtMaGD_KeyPress() {
        if (this.$refs.txtMaGD.value != "") {
            let input1 = {
                "ma_gd": this.$refs.txtMaGD.value,
                "loaihd_id":LoaiHopDong.DANGKY_GOI_DADV,
                "donvi_id":this.$root.token.getDonViID(),
                "tthd_id":1,
                "nhanvien_id":this.$root.token.getNhanVienID(),
                "donvi_dl_id":this.$root.token.getDonViDuLieuID()
            }
            let ds = this.getData(await API.lay_ds_hopdong_theo_ma_gd(this.axios, input1))
            this.HienThiTTHopDongKH(ds)
        }
    },

    async HienThiTTHopDongKH(ds) {
        if (ds.length > 0)
        {
            this.hdkh_id = ds[0].hdkh_id
            this.$refs.txtMaGD.value = ds[0].ma_gd
            this.txtMaKH = ds[0].ma_kh
            if (ds[0].khachhang_id != "") {
                this.khachhang_id = ds[0].khachhang_id
            }
            this.khlon_id = ds[0].khlon_id
            this.txtTenKH = ds[0].ten_kh
            this.txtDiaChiKH = ds[0].diachi_kh
            if (ds[0].ngay_yc!= "") {
                this.dtpNgayYeuCau = ds[0].ngay_yc
            } 
            this.txtGhiChu = ds[0].ghichu
            this.txtMaTB = ds[0].ma_tb
            //cboTenGoi2.ed
            if (ds[0].ctv_id)
            {
                // this.chkCTV_CheckedChanged()
                this.filterbox.ctv.ischecked = true;
                this.filterbox.ctv.ctv_id = ds[0].ctv_id
                // let res = await this.axios.post("/web-hopdong/thanhly/thanh_ly_map_id", {
                //     id_neo: "ten_nv",
                //     in_table: "admin.nhanvien",
                //     in_dk: "where nhanvien_id=" + this.ctv_id,
                // });
                let res = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien", {
                    "param" : this.filterbox.ctv.ctv_id,
                    "type" : 3
                })

                this.filterbox.ctv.value = res.data.data ? res.data.data : ''
            }
            else
            {
                this.filterbox.ctv.value = "";
                this.filterbox.ctv.ischecked = false;
            }

            if (ds[0].nhanviengt_id)
            {
                // this.chkNguoiGT_CheckedChanged()
                this.filterbox.nguoigt.ischecked = true;
                this.filterbox.nguoigt.nguoigt_id = ds[0].nhanviengt_id
                let res = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien", {
                    "param" : this.filterbox.nguoigt.nguoigt_id,
                    "type" : 3
                })
                this.filterbox.nguoigt.value = res.data.data ? res.data.data : ''
            }
            else
            {
                this.filterbox.nguoigt.value = "";
                this.filterbox.nguoigt.ischecked = false;
            }
            //PNLINH: B2A 06/05/2020
            if (ds[0].PHIEUCSKH_ID)
            {
                this.VPHIEUCSKH_ID = ds[0].PHIEUCSKH_ID
            }
            //
            this.setButton(3)
            this.HienThiDanhSacHDTB();
            // SendKeys.Send("{TAB}");
        }
        else
        {
            this.dgrList = null;
            this.setButton(0);
        }
    },


    async HienThiTT_DanhBa(ma_tb) {
        try {
            let input = {
                "in_ma_tb": ma_tb,
                "in_dichvuvt_id": this.cboDichVuVienThong.value,
                "in_donvi_dl_id": 0
            }

            let data = this.getData(await API.lay_danhba_theo_matb(this.axios, input))

            if (data && data.length > 0) {
                if (data.length > 1)
                {
                    // frmDSTBCungMa f = new frmDSTBCungMa();
                    // f.ds = ds;
                    // if (f.ShowDialog() == DialogResult.OK)
                    //     ds = f.ds;
                    let result = await this.$refs.thuebaocungma.show(data)
                    data = result.itemSelected
                }
                this.thuebao_id = data[0].thuebao_id

                //Thông tin khách hàng
                this.txtMaKH = data[0].ma_kh
                // if (data[0].ngaylap_hd != "") {
                //     this.dtpNgayLHD = moment(data[0].ngaylap_hd).format('DD/MM/YYYY')
                // }
                // else {
                //     this.dtpNgayLHD = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss')
                // }
                this.khachhang_id = data[0].khachhang_id
                this.txtTenKH = data[0].ten_kh
                this.txtDiaChiKH = data[0].diachi_kh
                //Thông tin thuê bao
                this.txtMaTB = data[0].ma_tb
                this.ten_tb = data[0].ten_tb
                this.diachi_ld = data[0].diachi_ld
                this.loaitb_id = data[0].loaitb_id
                this.cboLoaiHinhThueBao.value = this.loaitb_id;
                await this.LoadDS_Goi()
                this.donvi_id = data[0].donviql_id
                this.doituong_id = data[0].doituong_id
                this.khlon_id = data[0].khlon_id

                this.hdtb_id = 0;
                    
            } else {
                if (this.dichvuvt_id == this.DichVuVienThong.DI_DONG)
                {
                    let str = ""
                    let _dt = []
                    if (this.hdkh_id != 0)
                    {
                        let input1 = {
                            "hdkh_id":this.hdkh_id
                        }
                        _dt = this.getData(await API.dstb_dk_goi_dadv_theo_hdkh(this.axios, input1))
                        if (_dt  && _dt.length > 0)
                        {
                            this.thuebao_id = 0;
                            this.khachhang_id = 0;
                            this.txtMaKH = _dt[0].ma_kh
                            this.dtpNgayLHD = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss')
                            this.txtTenKH = _dt[0].ten_kh
                            this.txtDiaChiKH = _dt[0].diachi_kh

                            this.ten_tb = _dt[0].ten_tb
                            this.diachi_ld = _dt[0].diachi_ld
                            this.loaitb_id = this.cboLoaiHinhThueBao.value
                            this.donvi_id = _dt[0].donviql_id
                            this.doituong_id = _dt[0].doituong_id
                            this.khlon_id = _dt[0].khlon_id
                        }
                    }
                    else
                    {
                        let input2 = {
                            "goi_id": this.cboDanhSachGoi.value
                        }
                        // _dt = this.getData(await API.lay_sl_theo_goi_id(this.axios, input2))
                        _dt = await this.axios.get('/web-hopdong/hopdong/lay_sl_theo_goi_id', { params: { goi_id: this.cboDanhSachGoi.value } })
                        console.log('_dt is ', _dt)
                        if (_dt != null && parseInt(_dt.data.data) > 0)
                        {
                            this.showAlert("Bạn phải nhập thông tin của các thuê bao khác trong gói trước!");
                            return;
                        }
                        else {
                            // console.log('this is text ', this.$refs.loaiHinhTbId)
                            // let tenLoaiHinh = this.cboLoaiHinhThueBao.list.map(item => {
                            //     return item.id = 
                            // })
                            this.showAlert("Không tìm thấy thông tin về mã thuê bao " + this.txtMaTB + " đối với loại hình " + this.cboLoaiHinhThueBao.name);
                        }
                    }
                }
                else
                {
                    console.log('this is text ', this.$refs.loaiHinhTbId)
                    this.showAlert("Không tìm thấy thông tin về mã thuê bao " + this.txtMaTB + " đối với loại hình " + this.cboLoaiHinhThueBao.name);
                }
            }
        } catch(e) {
            this.showAlert(e)
        }
        
    },

    async HienThiDanhSacHDTB() {
        let input = {
            "hdkh_id": this.hdkh_id
        }
        let dtList = this.getData(await API.dstb_dk_goi_dadv_theo_hdkh(this.axios, input))
        this.dtList = dtList

        if (dtList && dtList.length > 0)
        {
            this.dgrList = dtList
            this.danhSachThueBaoGoiTable.list = dtList
            this.txtMaTB = dtList[0].ma_tb ? dtList[0].ma_tb : ''
            this.cboLoaiHinhThueBao.value = dtList[0].loaitb_id ? dtList[0].loaitb_id : ''
            await this.LoadDS_Goi()
            this.cboDanhSachGoi.value = dtList[0].goi_id ? dtList[0].goi_id : 0
            this.cboDichVuVienThong.value = dtList[0].dichvuvt_id ? dtList[0].dichvuvt_id : ''
            if (this.dgrList.length <= 1)
            {
                this.tsbtnXoaTB = false;
            }
            else
            {
                this.tsbtnXoaTB = true;
                this.tsbtnThemTB = true;
            }
            // this.LoadDS_Goi()
        }
        else
        {
            this.dgrList = []
            this.setButton(1)
        }
        // this.LoadDS_Goi()
    },
    async cboDichVuVT_SelectedValueChanged() {
        if (this.cboDichVuVienThong.list.length > 0)
        {
            // console.log("run in this")
            // if (cboDichVuVT.SelectedValue.ToString() == "System.Data.DataRowView") return;
            this.dichvuvt_id = this.cboDichVuVienThong.value
            // bangts.HT_LoaiHinh_TB_Combobox(cboLoaihinhTB, dichvuvt_id);
            let input = {}
            let data = this.getData(await API.CSS_LOAIHINH_TB(this.axios, input))

            if (data && data.length > 0) {
                let filterLoaiHinhTb = data.filter(item => {return item.DICHVUVT_ID == this.dichvuvt_id})
                this.cboLoaiHinhThueBao.list = filterLoaiHinhTb.map(item => {
                    return {
                        id : item.LOAITB_ID,
                        text : item.LOAIHINH_TB,
                    }
                })
                // this.cboLoaiHinhThueBao.value = this.cboLoaiHinhThueBao.list[0].id

            }
            switch (+this.dichvuvt_id)
            {
                case this.DichVuVienThong.CO_DINH:
                    this.cboLoaiHinhThueBao.value = this.LoaiHinhTB.DIENTHOAI_CD;
                    break;
                case this.DichVuVienThong.ADSL:
                    this.cboLoaiHinhThueBao.value = this.LoaiHinhTB.MEGA;
                    break;
                case this.DichVuVienThong.TSL:
                    this.cboLoaiHinhThueBao.value = this.LoaiHinhTB.LEASEDLINE;
                    break;
                case this.DichVuVienThong.MEGAWAN:
                    this.cboLoaiHinhThueBao.value = this.LoaiHinhTB.MEGAWAN_ADSL;
                    break;
            }

            let tenLoaiHinh = this.cboLoaiHinhThueBao.list.filter(item => {
              return item.id == this.cboLoaiHinhThueBao.value
            });

            this.cboLoaiHinhThueBao.name = tenLoaiHinh[0].text ? tenLoaiHinh[0].text : ''

            await this.LoadDS_Goi();
        }
    },
    async cboLoaihinhTB_SelectedValueChanged() {
        if (this.cboLoaiHinhThueBao.list.length > 0)
        {
            if (this.cboLoaiHinhThueBao.value == null) return;

            let tenLoaiHinh = this.cboLoaiHinhThueBao.list.filter(item => {
                return item.id == this.cboLoaiHinhThueBao.value
            })
            this.cboLoaiHinhThueBao.name = tenLoaiHinh[0].text ? tenLoaiHinh[0].text : ''
            // bangts.HT_Kieu_LD_Combobox(cboKieuLD, 0, LoaiHopDong.DANGKY_GOI_DADV);
            // let input = {
            //     "loaitb_id": this.cboLoaiHinhThueBao.value
            // }

            // let data = this.getData(await API.lay_ds_goi_cv_vnp_v1(this.axios, input))

            // if (data && data.length > 0) {

            // }
            await this.LoadDS_Goi()
        }
    },

    async cboTenGoi2_EditValueChanged() {
        if (this.cboDanhSachGoi.value == null){
                return
            }
            let goi_id = this.cboDanhSachGoi.value
            let input = {
                "goi_id": goi_id
            }

            let data = this.getData(await API.sp_lay_mota_goidadv(this.axios, input))
            let mota = "";
            if (data && data.length > 0) {
                mota = data[0].mota ? data[0].mota : ''
            }
            this.txtMoTa = mota
    },

    chkNguoiGT_CheckedChanged() {
      if (this.$refs.chkNguoiGT.checked) {
        console.log(this.$refs.frmNguoiGT)
        let f = this.$refs.frmNguoiGT
        f.nhomlnv_id = NHOM_LNV.GIOITHIEU_HD;
        f.donvi_id = this.donvi_nguoi_gt_id;
        f.loainv_id = this.loainv_nguoi_gt_id;
        f.ctv_id = this.nguoigt_id;
        f.ShowDialog();
      } else {
        this.nguoigt_id = 0;
        this.$refs.txtNguoiGT.value = "";
      }
    },
    chonNhanvien(f) {
        if (this.$refs.chkCTV.checked) {
            this.$refs.txtCTV.value = f.ten_nv;
            this.ctv_id = f.nhanvien_id;
            this.donvi_ctv_id = f.donvi_id ? f.donvi_id : "0";
            this.loainv_ctv_id = f.loainv_id ? f.loainv_id : "0";

            if (this.ctv_id == 0) this.$refs.chkCTV.checked = false
        }

        if (this.$refs.chkNguoiGT.checked) {
            this.$refs.txtNguoiGT.value = f.ten_nv;
            this.nguoigt_id = f.nhanvien_id;
            this.loainv_nguoi_gt_id = f.loainv_id ? f.loainv_id : "0";
            this.donvi_nguoi_gt_id = f.donvi_id ? f.donvi_id : "0";

            if (this.nguoigt_id == 0) this.$refs.chkNguoiGT.checked = false;
        }
    },
    searchAccountDialogClose(data) {
      console.log(data)
      if (data != null) {
        this.txtMaTB = data.ma_tb
        this.loadDanhbaTheoMaTB(this.txtMaTB)
      }
      this.$refs.searchAccountDialog.hide()
    },
    bntLayMaKh_Click() {
        console.log('run in this')
        this.$refs.searchAccountDialog.show()
    },
    btnLayMaGD_Click() {
      this.modalSearch = {
        loai_hd_id : LoaiHopDong.DAT_MOI,
        trangthai_hd : TrangThaiHD.MOI
      }
      this.$refs.searchContractModal.clearData();
      this.$refs.searchContractModal.loadPopup();
      this.$refs.popupFrmTraCuuHopDong.show();
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.ischecked) {
        this.$refs.chonCongTacVienModal.showModal();
        /* this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien"
          );
        this.popupComponentName = "popupCTV";
        this.Popup("popupComponents"); */
      }
    },
    ShowPopupNguoiGT() {
      if (this.filterbox.nguoigt.ischecked) {
        this.$refs.chonNguoiGTModal.showModal();
        /* this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu"
          );
        this.popupComponentName = "popupNGT";
        this.Popup("popupComponents"); */
      }
    },
    acceptCTV(data) {
      Object.assign(this.filterbox.ctv, data);
      this.filterbox.ctv.value =
        this.filterbox.ctv.ma_ctv + " - " + this.filterbox.ctv.ten_ctv;
      this.filterbox.ctv.ctv_id = this.filterbox.ctv.ctv_id;
    },
    acceptNguoiGT(data) {
      Object.assign(this.filterbox.nguoigt, data);
      this.filterbox.nguoigt.value =
        this.filterbox.nguoigt.ma_nguoi_gt +
        " - " +
        this.filterbox.nguoigt.ten_nguoi_gt;
      this.filterbox.nguoigt.nguoigt_id = this.filterbox.nguoigt.nguoi_gt_id;
    },
    async loadDanhbaTheoMaTB(ma_tb) {
      try {
        this.loading(true);
        let data = await this.axios.post(
          "/web-baohong/TiepNhanBaoHong/lay_danhba_theo_matb",
          {
            vma_tb: ma_tb,
          }
        );
        if (data.data.data.length > 0) {
          this.gridDanhsachlapkem = data.data.data;
          let formData = data.data.data[0];
          for (let key in formData) {
            if(key == 'ngay_sd') {
              this.$set(this.form, key, moment(formData[key]).format("DD/MM/YYYY HH:mm:ss"));
            } else
            this.$set(this.form, key, formData[key]);
          }
          this.dtpHentu
        } else {
          this.$toast.error("Không tìm thấy thông tin!");
        }
        this.loading(false);
      } catch (e) {
        this.loading(false);
      }
    },

    tsbtnHuyBo_Click() {
        this.setButton(0)
        this.dgrList = []
        this.ma_kh = ''
        this.txtGhiChu = ''

    },
    tsbtnNhapMoi_Click(){
        this.setButton(1)
    },
    tsbtnGhiLai_Click() {
        this.CapNhat()
    },

    async KiemTraDL_KhachHang() {
        if (this.$refs.txtMaGD.value != "")
        {
            let checkMaKh = await this.getData(API.kiemtra_makh_matt_magd(this.axios, {
                "kieu": 3,
                "loaiHdId": LoaiHopDong.DANGKY_GOI_DADV,
                "maKh": this.$refs.txtMaGD.value
            }))
            if (checkMaKh == 0)
            {
                this.showAlert("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
                this.$refs.txtMaGD.focus();
                return false;
            }
        }
        if (this.khong_batbuoc_ctv == 0)
        {
            if (!this.filterbox.ctv.ischecked || this.filterbox.ctv.value === "" || this.filterbox.ctv.ctv_id == 0)
            {
                this.showAlert("Bạn chưa chọn nhân viên phát triển!");
                this.$refs.txtCTV.focus();
                return false;
            }

        }
        if (this.batbuoc_gioithieu_dichchuyen == 1)
        {
            if (!this.$refs.chkNguoiGT.checked || this.$refs.txtNguoiGT.value === "" || this.nguoigt_id == 0)
            {
                this.showAlert("Bạn chưa chọn người giới thiệu hợp đồng/phụ lục");
                this.$refs.txtNguoiGT.focus();
                return false;
            }
        }

        if (!this.filterbox.ctv.ischecked || this.filterbox.ctv.value === "" || this.filterbox.ctv.ctv_id == 0)
        {
            this.showAlert("Bạn chưa chọn nhân viên phát triển!");
            this.$refs.txtCTV.focus();
            return false;
        }
        if (this.$refs.chkNguoiGT.checked && this.$refs.txtNguoiGT.value === "" && this.nguoigt_id == 0)
        {
            this.showAlert("Bạn chưa chọn nhân viên giới thiệu hợp đồng!");
            this.$refs.txtNguoiGT.focus();
            return false;
        }
        return true;
    },

    async TaoDuLieu(themmoi) {
        return new Promise(async (resolve, reject) => {
            await this.TaoDuLieu_HDKH(themmoi);
            if (this.loi_sinh_magd) return;
            await this.TaoDuLieu_HDTB(themmoi);
            await this.TaoDuLieu_HDTB_GOI_DADV(themmoi);
            resolve(true)
        })
    },
    async TaoDuLieu_HDKH(themmoi)
    {
        let vmagd_temp = "";
        //b.hdkh_id, b.ma_gd, b.ma_kh, b.khachhang_id, b.ten_kh, b.diachi_kh, b.ngaylap_hd, b.ghichu
        let dsHDKH = []
        // dsHDKH.HD_KHACHHANG = []
        if (themmoi == true)
        {
            this.hdkh_id = this.getData(await API.get_keys(this.axios, {
                "keyname" : "HD_KHACHHANG"
            }))
            this.nhomtb_id = this.getData(await API.get_keys(this.axios, {
                "keyname" : "NHOMTB"
            }))

        }
        let rowHDKH = {}
        rowHDKH.HDKH_ID = this.hdkh_id;
        if (themmoi == true)
        {
            if (this.ts_sinhma_gd_theo_donvi)
                vmagd_temp = this.getData(await API.sinh_magd_v2(this.axios, {
                    'donvi_id' : this.$root.token.getDonViID(),
                    'loaihd_id' : LoaiHopDong.DANGKY_GOI_DADV
                }))
            else
                vmagd_temp = this.getData(await API.sinh_magd(this.axios, {
                    'loaihd_id' : LoaiHopDong.DANGKY_GOI_DADV
                }))
            if (vmagd_temp.includes("ERROR:"))
            {
                this.showAlert(vmagd_temp.split(':')[1]);
                this.loi_sinh_magd = true;
            }
            this.$refs.txtMaGD.value = vmagd_temp;
            rowHDKH.MA_GD = this.$refs.txtMaGD.value;
        }
        else
        {
            rowHDKH.MA_GD = this.$refs.txtMaGD.value;
        }
        rowHDKH.MA_KH = this.txtMaKH;
        if (this.khachhang_id > 0)
            rowHDKH.KHACHHANG_ID = this.khachhang_id;
        rowHDKH.KHLON_ID = this.khlon_id;
        rowHDKH.TEN_KH = this.txtTenKH
        rowHDKH.DIACHI_KH = this.txtDiaChiKH
        rowHDKH.NGAYLAP_HD = this.dtpNgayLHD;
        rowHDKH.NGAY_YC = this.dtpNgayYeuCau
        rowHDKH.GHICHU = this.txtGhiChu
        rowHDKH.DONVI_ID = this.$root.token.getDonViID()
        rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID(),
        rowHDKH.LOAIHD_ID = LoaiHopDong.DANGKY_GOI_DADV;
        rowHDKH.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
        rowHDKH.NGUOI_CN = this.$root.token.getUserName()
        rowHDKH.MAY_CN = await this.$root.token.getMachine()
        rowHDKH.IP_CN = this.$root.token.getIP()
        if (this.filterbox.ctv.ischecked && this.filterbox.ctv.value)
        {
            rowHDKH.CTV_ID = this.filterbox.ctv.ctv_id;
        }
        // if (this.$refschkNguoiGT.Checked && this.txtNguoiGT)
        // {
        //     rowHDKH.NHANVIENGT_ID = this.nguoigt_id;
        // }
        //
        //PNLINH B2A 02/05/2020
        if (this.VPHIEUCSKH_ID != 0)
        {
            rowHDKH.PHIEUCSKH_ID = this.VPHIEUCSKH_ID;

        }
        dsHDKH.push(rowHDKH)
        this.dsHDKH = dsHDKH
    },

    async TaoDuLieu_HDTB(themmoi) {
        let dsHDTB = []
        // dsHDTB.HD_THUEBAO = []
        let dsGOIDV_HDTB = []

        let mergeObj = []
        // dsGOIDV_HDTB.GOI_DADV_HDTB = []
        if (themmoi == true) {
            this.hdtb_id = this.getData(await API.get_keys(this.axios, {
                "keyname" : "HD_THUEBAO"
            }))
        }
        // HD_THUEBAO_DATA.HD_THUEBAORow rowHDTB = dsHDTB.HD_THUEBAO.NewHD_THUEBAORow();
        // GOI_DADV_HDTB_DATA.GOI_DADV_HDTBRow rowGOI = dsGOIDV_HDTB.GOI_DADV_HDTB.NewGOI_DADV_HDTBRow();
        let rowHDTB = {}
        let rowGOI = {}
        rowHDTB.HDTB_ID = this.hdtb_id;
        rowHDTB.HDKH_ID = this.hdkh_id;
        if (this.thuebao_id > 0)
            rowHDTB.THUEBAO_ID = this.thuebao_id;
        rowHDTB.MA_TB = this.txtMaTB;
        rowHDTB.TEN_TB = this.ten_tb;
        rowHDTB.DIACHI_LD = this.diachi_ld;

        rowHDTB.KIEULD_ID = this.cboKieuLapDat.value
        rowHDTB.DOITUONG_ID = this.doituong_id;
        rowHDTB.TTHD_ID = TrangThaiHD.MOI;
        rowHDTB.LOAITB_ID = this.loaitb_id;
        rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
        rowHDTB.DONVI_ID = this.donvi_id;
        rowHDTB.NGUOI_CN = this.$root.token.getUserName()
        rowHDTB.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
        rowHDTB.MAY_CN = await this.$root.token.getMachine()

        //rowHDTB.GOI_ID = Convert.ToInt32(cboTenGoi2.SelectedValue);
        //rowHDTB.NHOMTB_ID = nhomtb_id;
        //rowHDTB.CHUNHOM = chkChuNhom.Checked ? 1 : 0;
        // vinhpv lay huonggiao_id
        let dset = this.getData(await API.layhuonggiao_tiepnhan_2(this.axios, {
            "loaihd_id" : LoaiHopDong.DANGKY_GOI_DADV,
            "kieuld_id" : this.cboKieuLapDat.value
        }))
        if (dset && dset.length > 0)
        {
            this.huonggiaotn_id = dset[0].huonggiao_id
            this.quytrinh_id = dset[0].quytrinh_id
        }
        rowHDTB.QUYTRINH_ID = this.quytrinh_id
        dsHDTB.push(rowHDTB)

        //VinhPV them tao du lieu cho GOI_DADV_HDTB
        rowGOI.HDTB_ID = this.hdtb_id;
        //rowGOI.GOI_ID = Convert.ToInt32(cboTenGoi2.SelectedValue);
        rowGOI.GOI_ID = this.cboDanhSachGoi.value
        let thang_huong = this.cboHieuLuc.value
        if (thang_huong == 2) {
            thang_huong = this.txtSoThang
        }
        rowGOI.THANG_HUONG = thang_huong;

        dsGOIDV_HDTB.push(rowGOI)
        this.dsHDTB = dsHDTB
        this.dsHDTBGOI = dsGOIDV_HDTB

        this.objHdtb = {...rowHDTB, ...rowGOI}
        mergeObj.push({...rowHDTB, ...rowGOI})
        this.dsHDTB = mergeObj
        // mergeObj.push(obj)
        // this.hdtbObj = mergeObj
        
    },

    async TaoDuLieu_HDTB_GOI_DADV() {
        let dsHDTBGOI = []
        let rowGOI = {}
        //rowGOI.GOI_ID = Convert.ToInt32(cboTenGoi2.SelectedValue);
        rowGOI.GOI_ID = this.cboDanhSachGoi.value
        rowGOI.NHOMTB_ID = this.nhomtb_id
        rowGOI.CHUNHOM = this.chkChuNhom ? 1 : 0
        rowGOI.HDTB_ID = this.hdtb_id

        let thang_huong = this.cboHieuLuc.value
        if (thang_huong == 2) {
            thang_huong = this.txtSoThang
        }
        rowGOI.THANG_HUONG = thang_huong;
        let dset = this.getData(await API.layhuonggiao_tiepnhan_2(this.axios, {
            "loaihd_id" : LoaiHopDong.DANGKY_GOI_DADV,
            "kieuld_id" : this.cboKieuLapDat.value
        }))
        if (dset && dset.length > 0)
        {
            this.huonggiaotn_id = dset[0].huonggiao_id
        }
        dsHDTBGOI.push(rowGOI)
        this.dsHDTBGOI = dsHDTBGOI
    },

    async CapNhat() {
        let checkDuLieuKhachHang = await this.KiemTraDL_KhachHang()
        if (!checkDuLieuKhachHang) {
            return
        }
        
        try {
            if(this.cboLoaiHinhThueBao.value == LoaiHinhTB.DIDONGTRASAU) {
                let checkDD = await this.$axios.get('/tichhop/mnp/checkParticipant?sdt=' + this.txtMaTB)
                    if(checkDD.data.data != "01") {
                        this.$toast.error('Thuê bao không thuộc quản lý của Vinaphone Vui lòng kiểm tra lại!')
                        return
                    }
                
            }
        }catch(e) {
            console.log("Lỗi SPS")
            console.log(e)
        }

        if (this.tsbtnNhapMoi == false)
        {
            let gda_dv = this.getData(await API.kiemtra_dangky_goi_dadv(this.axios, {
                "goi_id": this.cboDanhSachGoi.value,
                "thuebao_id": this.thuebao_id,
                "chunhom": this.chkChuNhom ? 1 : 0,
                "hdkh_id": this.hdkh_id,
                "nhomtb_id": this.nhomtb_id
            }))
            if (gda_dv != "1")
            {
                this.showAlert(gda_dv);
                return;
            }


            if (this.cboKieuLapDat.value == KieuLapDat.DANGKY_GOI_DADV ||
                this.cboKieuLapDat.value == KieuLapDat.THEM_TB_GOI_DADV)
            {
                let kt_hieuluc = this.getData(await API.kt_goi_dadv_hieuluc(this.axios, {
                    "goi_id": this.cboDanhSachGoi.value,
                    "vhdtb_id": 0,
                    "vkieuld_id": this.cboKieuLapDat.value,
                    "vthuebao_id": this.thuebao_id
                }))
                if (kt_hieuluc != "1")
                {
                    this.showAlert(kt_hieuluc);
                    return;
                }
                //nhapt: thực hiện ghép lệnh của c Thoa vào kiểm tra
                //không cho phép 1 thuê bao thực hiện đồng thời đăng ký 2 gói
                //y/c jira DLC & PYN
                let _hieuluc = 0;
                if (this.cboHieuLuc.value == 0)
                    _hieuluc = 0;
                else if (this.cboHieuLuc.value == 1)
                    _hieuluc = 1;
                else if (this.cboHieuLuc.value == 2)
                {
                    if (this.txtSoThang === "")
                    {
                        this.showAlert("Hãy nhập số tháng có hiệu lực!");
                        return;
                    }
                    _hieuluc = this.txtSoThang
                }
                let kt_dkgoi = this.getData(await API.kiemtra_dk_trunggoi(this.axios, {
                    "goi_id": this.cboDanhSachGoi.value,
                    "thuebao_id": this.thuebao_id,
                    "hdtb_id": 0,
                    "thanghuong": _hieuluc
                }))
                if (kt_dkgoi != "OK")
                {
                    this.showAlert(kt_dkgoi);
                    return;
                }
            }

            await this.TaoDuLieu(true);
            if (this.loi_sinh_magd)
            {
                this.loi_sinh_magd = false;
                return;
            }
            // this.getData(await API.sp_themmoi_hdtb_goidadv(this.axios, {
            //     'json_hdkh' : JSON.stringify(this.dsHDKH),
            //     'json_hdtb' : JSON.stringify(this.dsHDTB),
            //     'json_goi' : JSON.stringify(this.dsHDTBGOI),
            //     'huonggiao_id' : this.huonggiaotn_id
            // }))
            let res = await this.axios.post('/web-hopdong/dk_goi_dadichvu/sp_themmoi_hdtb_goidadv', {
                'json_hdkh' : JSON.stringify(this.dsHDKH),
                'json_hdtb' : JSON.stringify(this.dsHDTB),
                'json_goi' : JSON.stringify(this.dsHDTBGOI),
                'huonggiao_id' : this.huonggiaotn_id
            })
            if (res) {
                if(res.data.error_code != 'BSS-00000000') {
                    this.showAlert('Có lỗi xảy ra : ' + res.data.message_detail)
                    return
                }
            }
        }
        else
        {
            await this.TaoDuLieu(false);
            // this.getData(await API.sp_update_hdtb_goidadv(this.axios, {
            //     "json_goi": JSON.stringify(this.dsHDTBGOI),
            //     "json_hdkh": JSON.stringify(this.dsHDKH),
            //     "json_hdtb": JSON.stringify(this.dsHDTB),
            // }))
            // goida_dv.CapNhat_HopDong(dsHDKH, dsHDTB, dsHDTBGOI);

            let res = await this.axios.post('/web-hopdong/dk_goi_dadichvu/sp_update_hdtb_goidadv', {
                "json_goi": JSON.stringify(this.dsHDTBGOI),
                "json_hdkh": JSON.stringify(this.dsHDKH),
                "json_hdtb": JSON.stringify(this.dsHDTB),
            })
            if (res) {
                if(res.data.error_code != 'BSS-00000000') {
                    this.showAlert('Có lỗi xảy ra : ' + res.data.message_detail)
                    return
                }
            }
            
        }
        try
        {
            if (this.filterbox.ctv.ischecked) {
                // tchopdong.TUDONG_GAN_CTV_TINHLUONG_KHDN(hdkh_id, khachhang_id, hdtb_id, ctv_id, tt_nd.ngay_cn, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                this.getData(await API.tudong_gan_ctv_tinhluong_khdn(this.axios, {
                    "hdkh_id": this.hdkh_id,
                    "khachhang_id": this.khachhang_id,
                    "hdtb_id": this.hdtb_id,
                    "ctv_id": this.filterbox.ctv.ctv_id
                }))
            }
        }
        catch (e)
        { 
            console.log(e)
        }

        this.setButton(3);
        this.showSuccess("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!");
        if (this.VPHIEUCSKHDV_ID > 0)
        {
            this.getData(await API.b2a_ht_phieucskhdv(this.axios, {
                'phieucskhdv_id' : this.VPHIEUCSKHDV_ID,
                'kieu' : 1
            }))
            // new TruyenDanFacade().EXECUTE_PROC("{?DB2}.b2a_ht_phieucskhdv",
            // "vphieucskhdv_id", VPHIEUCSKHDV_ID,
            // "vnhanvien_id", tt_nd.nhanvien_id,
            // "vdonvi_id", tt_nd.donvi_id,
            // "vnguoi_cn", tt_nd.ma_nd,
            // "vip_cn", tt_nd.ip,
            // "vmay_cn", tt_nd.may_cn,

            // "vkieu", 1);
        }
        // let input1 = {
        //     'dichVuVtId' : this.cboDichVuVienThong.value,
        //     'donViDlId' : this.$root.token.getDonViID(),
        //     'loaiHdId' : 27,
        //     'loaiTbId' : this.cboLoaiHinhThueBao.value,
        //     'maTb' : this.txtMaTB,
        //     'ttHdId' : 1
        // }
        let input1 = {
            "ma_gd": this.$refs.txtMaGD.value,
            "loaihd_id":LoaiHopDong.DANGKY_GOI_DADV,
            "donvi_id":this.$root.token.getDonViID(),
            "tthd_id":1,
            "nhanvien_id":this.$root.token.getNhanVienID(),
            "donvi_dl_id":this.$root.token.getDonViID()
        }
        let ds = this.getData(await API.lay_ds_hopdong_theo_ma_gd(this.axios, input1))
        if (ds && ds.length > 0) {
            this.HienThiTTHopDongKH(ds);
        }

        // HienThiTTHopDongKH(tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(txtMaGD.Text, LoaiHopDong.DANGKY_GOI_DADV, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0));
    },

    async XoaHDTB() {
        // laphd.XOA_HDTB(hdtb_id);
        let data = this.getData(await API.xoa_hdtb(this.axios, {
            "hdtb_id" : this.hdtb_id
        }))
        this.dgrList = []
        this.HienThiDanhSacHDTB()
    },
    
    async tsbtnCapNhatDB_Click() {
        let obj = {
            "HDKH_ID": this.hdkh_id
        }
        let data = await this.axios.post('/web-hopdong/goi-da-dichvu/fn_hoanthien_hoso', {
            "p_data_hdkh" : JSON.stringify(obj),
            "p_cmd" : "",
            "goi_id" : this.cboDanhSachGoi.value,
            "txtSoCA" : this.txtSoCA,
            "txtMaSoThueCA" : this.txtMaSoThueCA,
            "hdkh_id" : this.hdkh_id,
            "nhomtb_id" : this.nhomtb_id,
            "dtList" : this.dtList
        })
        if (data) {
            if (data.data.error_code === 'BSS-00000000') {
                this.showSuccess('Hoàn thiện đăng ký gói đa dịch vụ thành công !')
                this.dgrList = []
                this.danhSachThueBaoGoiTable.list = []
                this.setButton(0)
            } else {
                if (data.data.message) {
                    try {
                        let mess = JSON.parse(data.data.message)
                        this.showAlert(mess.MESSAGE ? mess.MESSAGE : data.data.message)
                    } catch(e) {
                        this.showAlert(data.data.message)
                    }
                }
            }
        }
    },

    dgrList_SelectionChanged(obj) {
        console.log('obj is ', obj)
        if (this.dgrList.length <= 0) return;
        if (this.dtList.length > 0)
        {
            //let k = dgrList.CurrentRow.Index;
            this.hdtb_id = obj.hdtb_id ? obj.hdtb_id : ''
            this.$refs.txtMaTB.value = obj.ma_tb ? obj.ma_tb : ''
            if (obj.chunhom) {
                this.chkChuNhom = true
            }
            else {
                this.chkChuNhom = false
            }

            // let thang_huong = Convert.ToInt32("0" + dtList.Rows[k]["thang_huong"].ToString());
            // if (thang_huong == 0 || thang_huong == 1)
            // {
            //     cboHieuLuc.SelectedValue = thang_huong;
            // }
            // else
            // {
            //     cboHieuLuc.SelectedValue = 2;
            //     txtSoThang.Text = thang_huong.ToString();
            // }
            if (obj.thuebao_id)
            {
                this.thuebao_id = obj.thuebao_id
            }
            else {
                this.thuebao_id = 0
            }
            this.dichvuvt_id = obj.dichvuvt_id ? obj.dichvuvt_id : 0
            this.cboDichVuVienThong.value = this.dichvuvt_id
            this.goi_id = obj.goi_id ? obj.goi_id : 0
            //cboTenGoi2.SelectedValue = goi_id;

            //cboTenGoi2.ItemIndex = cboTenGoi2.Properties.GetDataSourceRowIndex("GOI_ID", goi_id);


            this.ten_tb = obj.ten_tb ? obj.ten_tb : ''
            this.diachi_ld = obj.diachi_ld ? obj.diachi_ld : ''
            this.loaitb_id = obj.loaitb_id ? obj.loaitb_id : 0
            this.cboLoaiHinhThueBao.value = this.loaitb_id
            this.donvi_id = obj.donvi_id
            this.doituong_id = obj.doituong_id
            this.nhomtb_id = obj.nhomtb_id
            this.cboDanhSachGoi.value = this.goi_id
            this.setButton(3)
        }
    },

    async KichHoatVASC(maTb, matocdo_vasc, magoi_vasc, lydo, vCombo_fiberCD) {

        if (matocdo_vasc == "") {
            this.showAlert("Gói dịch vụ MyTV này chưa được đồng bộ với VASC. Bạn không thể tiếp tục cập nhật dữ liệu.");
        }
        let data = this.getData(await API.changeMegaVNN(this.axios, {
            "iptvaccount": maTb,
            "megaMyTV": magoi_vasc,
            "combo_FiberCD": vCombo_fiberCD
        }))

        if (data == 0) {
            return true
        }
        return false
    },
    async KichHoat_RegistAddOn(mytvAcc) {
        try
            {
                
                let dskplus = this.getData(await API.lay_tt_dangky_k_addon(this.axios, {
                    "vhdtb_id": this.hdtb_id
                }))

                let service_cd = "";
                let pack_cd = "";
                let subpack_cd = "";
                let unit_price = 0;
                let reason = this.txtGhiChu;
                let payment_type = "1"; // 1: Trả sau; 2: Trả trước (Tạm thời với các dịch vụ K+/CAB/FIM+/Danet/CLG hiện giờ mặc định đều là trả sau)
                let pre_period = "";       // Áp dụng trả trước (Chưa sử dụng)
                let pre_value = 0;           // Áp dụng trả trước (Chưa sử dụng)
                let pre_expire_time = "";  // Áp dụng trả trước (Chưa sử dụng)

                if (dskplus != null && dskplus.length > 0)
                {
                    service_cd = dskplus[0]["service_cd"] ? dskplus[0]["service_cd"] : ''
                    pack_cd = dskplus[0]["pack_cd"] ? dskplus[0]["pack_cd"] : ''
                    subpack_cd = dskplus[0]["subpack_cd"] ? dskplus[0]["subpack_cd"] : ''
                    unit_price = parseInt(dskplus[0]["unit_price"])

                    // let resultRegistKplus = vasc.RegistAddOn(mytvAcc, tt_nd.tentat, service_cd, pack_cd, subpack_cd, unit_price, reason, payment_type, pre_expire_time, pre_value, pre_expire_time);
                    let resultRegistKplus = this.getData(await API.registAddOn(this.axios, {
                        "username": mytvAcc,
                        "zipcode": await this.$root.token.getThongTinNguoiDung().tentat,
                        "service_cd": service_cd,
                        "pack_cd": pack_cd,
                        "subpack_cd": subpack_cd,
                        "unit_price": unit_price,
                        "reason": reason,
                        "payment_type": payment_type,
                        "pre_period": pre_expire_time,
                        "pre_value": pre_value,
                        "pre_expire_time": pre_expire_time

                    }))
                    if (parseInt(resultRegistKplus[0]) != 1)
                    {
                        try
                        {
                            // tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDV", "vkey_parameter",
                            // "ma_tb=" + IPTVAccount + ",KQ=" + resultRegistKplus[0].ToString() + ",Errordesc=" + resultRegistKplus[1].ToString(), "vserver_name", tt_nd.ip, "vnote", " Ln:1279: Lỗi kích hoạt");
                        }
                        catch (e)
                        {
                            console.log(e)
                        }

                        this.showAlert("Có lỗi khi kích hoạt K+ Add-on lên VASC");
                        return false;
                    }
                    else
                    {
                        try
                        {
                            // tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDV", "vkey_parameter",
                            // "ma_tb=" + IPTVAccount + ",KQ=" + Convert.ToInt32(resultRegistKplus[0]) + ",Errordesc=" + resultRegistKplus[1].ToString(), "vserver_name", tt_nd.ip, "vnote", " Ln:1284: Kich hoạt cập nhật dl dưới tỉnh thành công");
                        }
                        catch (e)
                        {
                        }
                    }
                    return true;
                }
                else
                {
                    try
                    {
                        // tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDV", "vkey_parameter",
                        // "ma_tb=" + IPTVAccount + ",hdtb_id=" + hdtb_id, "vserver_name", tt_nd.ip, "vnote", " Ln:1305: dskplus.Tables[0].Rows.Count <= 0");
                    }
                    catch (e)
                    {
                    }
                }
                return true;
            }
            catch (e)
            {
                //Message_Box.Show(ex.Message);
                try
                {
                    // tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action", "vuser_name", tt_nd.ma_nd, "vcomputer_name", tt_nd.may_cn, "vaction_name", "frmDangKyGoiDaDV", "vkey_parameter",
                    // "ma_tb=" + mytvAcc + ",hdtb_id=" + hdtb_id, "vserver_name", tt_nd.ip, "vnote", " Ln:1318: " + ex.Message);
                }
                catch (Exception)
                {
                }
                return false;
            }
    },
    tsbtnThoat_Click() {

    },
    Clear() {
        this.hdkh_id = 0;
        this.nhomtb_id = 0;
        this.$refs.txtMaGD.value = "";
        this.txtMaKH = "";
        this.khachhang_id = 0;
        this.txtTenKH = "";
        this.txtDiaChiKH = "";
        this.dtpNgayYeuCau = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss');
        this.khlon_id = 0;
        this.hdtb_id = 0;
        this.thuebao_id = 0;
        this.cboKieuLapDat.value = 280;
        this.txtNguoiGT = "";
        this.txtCTV = "";
        this.nguoigt_id = 0;
        this.ctv_id = 0;

        // txtMaTB.Focus();
        this.txtGhiChu = "";
    },
    async tsbtnXoaTB_Click() {
        // if (!string.IsNullOrEmpty(txtMaTB.Text))
        // {
        //     if (MessageBox.Show("Bạn thật sự muốn xóa thuê bao không ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return;
        //     XoaHDTB();
        // }
        // else
        // {
        //     Message_Box.ShowTB("Bạn phải chọn thuê bao cần xóa trên lưới dữ liệu !");
        // }
    },

    tsbtnThemTB_Click() {
        this.ThemHDThueBao()
    },

    async ThemHDThueBao() {
        
        try {
            if(this.cboLoaiHinhThueBao.value == LoaiHinhTB.DIDONGTRASAU) {
                let checkDD = await this.$axios.get('/tichhop/mnp/checkParticipant?sdt=' + this.txtMaTB)
                if(checkDD.data.data != "01") {
                    this.$toast.error('Thuê bao không thuộc quản lý của Vinaphone Vui lòng kiểm tra lại!')
                    return
                }   
            }
        }catch(e) {
            console.log("Lỗi SPS")
            console.log(e)
        }

        let input = {
            'ma_tb' : this.txtMaTB,
            'thuebao_id' : this.thuebao_id,
            'hdkh_id' : this.hdkh_id,
            'khachhang_id' : this.khachhang_id,
            'dichvuvt_id' : this.dichvuvt_id,
            'loaitb_id' : this.loaitb_id,
            'goi_id' : this.cboDanhSachGoi.value,
            'is_chunhom' : this.chkChuNhom ? 1 : 0,
            'nhomtb_id' : this.nhomtb_id,
            'kieuld_id' : this.cboKieuLapDat.value,
            'hieuluc' : this.cboHieuLuc.value
        }
        let checkAddHopDong = this.getData(await API.fn_kiemtra_add_hdtb_goidadv(this.axios, input))
        if (checkAddHopDong != '0') {
            this.showAlert(checkAddHopDong)
            return;
        }

        await this.TaoDuLieu_HDTB(true);
        await this.TaoDuLieu_HDTB_GOI_DADV(true);
        // goida_dv.ThemMoi_HopDongTB(dsHDTB, dsHDTBGOI, huonggiaotn_id);
        
        let data = this.getData(await API.sp_add_hdtb_goi_dadv(this.axios, {
            'json_hdtb' : JSON.stringify(this.dsHDTB),
            'json_goi_dadv' : JSON.stringify(this.dsHDTBGOI),
            'huonggiaotn_id' : this.huonggiaotn_id
        }))
        this.setButton(3);
        this.showSuccess('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')

        let input1 = {
            "ma_gd": this.$refs.txtMaGD.value,
            "loaihd_id":LoaiHopDong.DANGKY_GOI_DADV,
            "donvi_id":this.$root.token.getDonViID(),
            "tthd_id":1,
            "nhanvien_id":this.$root.token.getNhanVienID(),
            "donvi_dl_id":this.$root.token.getDonViDuLieuID()
        }
        let ds = this.getData(await API.lay_ds_hopdong_theo_ma_gd(this.axios, input1))
        this.HienThiTTHopDongKH(ds)
    },

    tsbtnXoa_Click() {
        this.$confirm('Bạn chắc chắn muốn xóa?',
            '',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'delete'
            }).then(async () => {
              let data = await this.axios.post('/web-hopdong/hopdong/khoa_mo_thuebao_no_cuoc/xoa_hopdong', {
                "list" : [
                    {
                        "hdkh_id": this.hdkh_id,
                        "loaihd_id": LoaiHopDong.DANGKY_GOI_DADV
                    }
                ]
              })

              this.danhSachThueBaoGoiTable.list = []
              this.setButton(0);
            }).catch(err => {
                console.log(err)
            })
    },

    tsbtnGiaHanKPlus_Click() {
        try
        {
            this.GIAHAN_GOI_DADV_KPLUS();
        }
        catch (e)
        {
            this.showAlert(e)
        }
    },

    async GIAHAN_GOI_DADV_KPLUS() {
        let _dtGoiKplus = this.getData(await API.sp_giahan_kplus_p1(this.axios, {
            'ma_tb' : this.$refs.txtMaTB.value,
            'goi_id' : this.cboDanhSachGoi.value
        }))

        if (_dtGoiKplus.length > 0) {
            this.$confirm('Thuê bao này đã đăng ký gói khuyến mại Kplus, bạn có muốn gia hạn?',
            '',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
            }).then(async () => {
              let data = await this.axios.post('/web-hopdong/dk_goi_dadichvu/giahan_goi_dadv_kplus', {
                "ma_tb" : this.$refs.txtMaTB.value
              })

              if (data) {
                if (data.data.error_code === 'BSS-00000000') {
                    this.showSuccess(data.data.message)
                } else {
                    this.showAlert(data.data.message)
                }
              }
            }).catch(err => {
                console.log(err)
            })
        }


    },

    tsbtnEmail_Click() {
        this.$bvModal.show("ThongTinEmail")
        // if (this.hdkh_id != 0)
        // {
        //     this.$bvModal.show("ThongTinEmail")
        // }
    },
    chkCTV_CheckedChanged() {
        if (this.$refs.chkCTV.checked) {
            console.log(this.$refs.frmNguoiGT)
            let f = this.$refs.frmNguoiGT
            f.nhomlnv_id = NHOM_LNV.CONGTACVIEN;
            f.donvi_id = this.donvi_ctv_id;
            f.loainv_id = this.loainv_ctv_id;
            f.ctv_id = this.ctv_id;

            f.ShowDialog();
        } else {
            this.ctv_id = 0;
            this.$refs.txtCTV.value = "";
        }
    }
}
}

</script>

<style>
    .searchAccountDialogClass .e-dlg-header-content {
        display: none;
    }
</style>
