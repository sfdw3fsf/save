<template src="./Process.html"></template>
<style src="./Process.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

import select2 from "@/components/Select2.vue";
import Vue from 'vue';
import { MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);
import authProcess from './popups/authProcess.vue';
import baoHanhThietBi from './popups/baoHanhThietBi.vue';
import luongPhieu from './popups/luongPhieu.vue';
import frmThemLuongPhieu from './popups/frmThemLuongPhieu.vue';
import IntersectionDirectionModal from '../../../admin/category/IntersectionDirection/IntersectionDirectionModal.vue'
import gridView from '@/components/Shared/gridview.vue'
import PasswordView from '@/modules/admin/category/GrantUserPermission/components/password-view.vue'
//
export default {
  provide: {
    multiselect: [CheckBoxSelection],
  },
  components: {
    breadcrumb,
    VueFlatPickr,
    API,
    authProcess,
    baoHanhThietBi,
    luongPhieu,
    IntersectionDirectionModal,
    gridView,
    PasswordView
  },
  name: 'Process',
  mounted () {
    this.frmCapNhatQuyTrinh_Load();
    this.getDanhSachTinh()
    this.getDanhSachPhanVungDaGan()
    this.getDanhSachPhanVungChuaGan()

    this.showLoaiHinhKenhGan = true
    this.showKieuLapDat = true
    this.showThueBaoCoDinh = true
  },
  data () {
    return {
      header: {
        title: 'Cập nhật quy trình',
        list: [
          { name: 'Quy trình - Nghiệp vụ', link: { name: 'Ui.cards' } },
          {
            name: 'Quy trình',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      textQuyTrinh : "",
      showCboLoaiHinhThueBao : false,
      showLoaiHinhKenhGan : false,
      showKhieuNai : false,
      showThueBaoCoDinh : false,
      showVatTu : false,
      showKieuLapDat : false,
      showTocDoThueBao : false,
      settingSelect2: {
        language: 'vi'
      },
      cboNhomQuyTrinh : {
        list: [],
        value: "",
        disable: false,
      },

      cboLoaiHopDong : {
        list : [],
        value : 0,
        disable :false
      },

      cboDichVuVienThong : {
        list :[],
        value : 0,
        disable : false
      },
      cboLoaiHinhThueBao : {
        list :[],
        value : "",
        disable : false
      },
      cboDanhSachTinh : {
        list : [],
        value : "",
        disable :false
      },
      cboDanhSachGanTinh : {
        list : [],
        value : "",
      },
      tenQuyTrinh : "",
      thuTu : "",
      checkTheoYeuCau : false,
      checkNoCuoc : false,

      danhSachQuyTrinhTable: {
        list: [],
        header: [ {fieldName: 'stt', headerText: 'STT', allowFiltering: true, width: '50'}, 
                  {fieldName: 'quytrinh_id', headerText: 'ID', allowFiltering: 'auto', width: '100'}, 
                  {fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: 'auto'},
                  {fieldName: 'quytrinh', headerText: 'Quy Trình', allowFiltering: 'auto', width : '300'}, 
                ],
        value: {},
        isEnabled: true,
      },

      grid_LoaiTBCDChuaGan : {
        list: [],
        header: [ 
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình chưa gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      grid_LoaiTBCDDaGan : {
        list: [],
        header: [ 
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình đã gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },

      ds_kieuld_chuagan : {
        list: [],
        header: [ 
          {fieldName: 'TEN_KIEULD', headerText: 'Kiểu lắp đặt chưa gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      ds_kieuld_dagan : {
        list: [],
        header: [ 
          {fieldName: 'TEN_KIEULD', headerText: 'Kiểu lắp đặt đã gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },

      ds_vattu_chuagan : {
        list: [],
        header: [ 
          {fieldName: 'TEN_VT', headerText: 'Vật tư chưa gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },

      ds_vattu_dagan : {
        list: [],
        header: [ 
          {fieldName: 'TEN_VT', headerText: 'Vật tư đã gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      ds_loaikenh_chuagan : {
        list: [],
        header: [ 
          {fieldName: 'LOAI_KENH', headerText: 'Loại kênh chưa gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      ds_loaikenh_dagan : {
        list: [],
        header: [ 
          {fieldName: 'LOAI_KENH', headerText: 'Loại kênh đã gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      ds_cdkn_chuagan : {
        list: [],
        header: [ 
          {fieldName: 'TENCHUDE', headerText: 'Tên khiếu nại chưa gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      ds_cdkn_dagan : {
        list: [],
        header: [ 
          {fieldName: 'TENCHUDE', headerText: 'Tên khiếu nại đã gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      grid_TocDoChuaGan : {
        list: [],
        header: [ 
          {fieldName: 'THUONGHIEU', headerText: 'Tốc độ/ Gói cước chưa gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      grid_TocDoDaGan : {
        list: [],
        header: [ 
          {fieldName: 'THUONGHIEU', headerText: 'Tốc độ/ Gói cước đã gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
      danh_sach_tinh_da_gan : {
        list: [],
        header: [ 
          // {fieldName: 'isCheck',type: 'checkbox',headerText: '',allowFiltering: false ,width: 'auto'},
          {fieldName: 'tentinh', headerText: 'Danh sách tỉnh đã gán', allowFiltering: true, width: 'auto'},
        ],
        value: {},
        isEnabled: true,
      },
      danh_sach_tinh_chua_gan : {
        list: [],
        header: [ 
          // {fieldName: 'isCheck',type: 'checkbox', allowFiltering: false,headerText: '', width: 'auto'},
          {fieldName: 'tentinh', headerText: 'Danh sách tỉnh chưa gán', allowFiltering: true, width: 'auto'}, 
        ],
        value: {},
        isEnabled: true,
      },
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
        TSL_DOITAC : 24,
      },
      NHOM_QT : {
        DIEUHANH_THICONG : 1,
        BAOHONG : 2,
        GIAIQUYET_KN : 3,
        BAOHANH_TBI : 4,
        SLSC_CNTT : 5,
        LUANCHUYEN_HOSO : 6,
        QUANLY_VATTU : 7,
        CAITAO_BAODUONG : 8
      },
      kt_load : false,
      LoaiHinhTB : {
        INTERNET_ADSL : 11
      },
      quyTrinhId : 0,
      tsbtnNhapMoi : false,
      tsbtnGhiLai : false,
      tsbtnHuyBo : false,
      tsbtnXoa : false,
      tsbtnThoat : false,
      checkDsGanTinh : false,
      p_tinh: null,
      dsTinh: [],
      luongPhieuDialog: false,
      hasPermission: false,
    }
  },
  computed: {
    showLoaiHinhThuebaoClass: function() {
        return this.showCboLoaiHinhThueBao ? 'd-show' : 'd-none';
    },
    showLoaiHinhKenhGanClass() {
      return this.showLoaiHinhKenhGan ? 'd-show' : 'd-none';
    },
    showKhieuNaiClass() {
      return this.showKhieuNai ? 'd-show' : 'd-none'
    },
    showVatTuClass() {
      return this.showVatTu ? 'd-show' : 'd-none'
    },
    showThueBaoCoDinhClass() {
      return this.showThueBaoCoDinh ? 'd-show' : 'd-none'
    },
    showKieuLapDatClass() {
      return this.showKieuLapDat ? 'd-show' : 'd-none'
    },
    showTocDoThueBaoClass() {
      return this.showTocDoThueBao ? 'd-show' : 'd-none'
    }
    
  },
  methods: {
    async luongPhieu_OnClick() {
      this.luongPhieuDialog = true
      this.$refs.luongPhieu.show()
    },
    async luongPhieu_OnClose() {
      this.luongPhieuDialog = false
    },
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    showSuccess(mesage) {
      this.$toast.success(mesage);
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    openAuthProcess() {
      this.$refs.authProcess.show();
    },

    openModalLuongPhieu() {
      // this.$refs.luongPhieu.show();
      this.luongPhieu_OnClick()
      
    },

    openModalHuongGiao() {
      this.$refs.intersectionDirectionModal.showModal()
    },

    async frmCapNhatQuyTrinh_Load() {
      await this.loadFormQuyTrinh()
      await this.HienThiDS();
      // this.getDanhSachPhanVungDaGan()
      // this.getDanhSachPhanVungChuaGan()
    },

    async getDanhSachPhanVungDaGan () {
      let input = {
        "vquytrinh_id": this.quyTrinhId,
        "vkieu": 1
      }
      let data = this.GetData(await API.danh_sach_phan_vung(this.axios, input))
      this.danh_sach_tinh_da_gan.list = data ? data : [] 
    },

    async getDanhSachPhanVungChuaGan () {
      let input = {
        "vquytrinh_id": this.quyTrinhId,
        "vkieu": 0
      }
      let data = this.GetData(await API.danh_sach_phan_vung(this.axios, input))
      // if (data.length > 0) {
      //   data.map(obj => ({ ...obj, isCheck: 'false' }))
      //   this.danh_sach_tinh_chua_gan.list = data.map(obj => ({ ...obj, isCheck: false }))
      // }
      this.danh_sach_tinh_chua_gan.list = data ? data : []
    },

    async thaoTacPhanVung(kieu) {
      let phanVungGanGo = []
      let phanVungIdArr = []
      if (kieu == 0) {
        phanVungGanGo = this.$refs.danhSachTinhChuaGan.getSelectedRecords()
      } else {
        phanVungGanGo = this.$refs.danhSachTinhDaGan.getSelectedRecords()
      }
      let currentPhanVung = Number(this.$root.token.getPhanVungID())
      if (phanVungGanGo.length > 0) {
        phanVungGanGo.map(item => {
          phanVungIdArr.push(Number(item.phanvung_id))
        })
      }

      if (!phanVungIdArr.includes(currentPhanVung)) {
        this.ShowAlert('Không được phép gán tỉnh khác phân vùng của user đăng nhập')
        return
      }

      let phanVung = []
      if (phanVungGanGo.length > 0) {
        phanVungGanGo.map(item => {
          phanVung.push(
            {
              'PHANVUNG_ID' : item.phanvung_id
            }
          )
        })
      }

      let obj = {
        KIEU : kieu,
        PHANVUNG : phanVung
      }

      let input = {
        "p_quytrinh_id": this.quyTrinhId,
        "p_ds_para": JSON.stringify(obj)
      }

      let data = this.GetData(await API.thao_tac_phan_vung(this.axios, input))
      data = JSON.parse(data)
      if (data && data.ERROR_CODE) {
        this.showSuccess('Thao tác thành công')
        this.getDanhSachPhanVungDaGan()
        this.getDanhSachPhanVungChuaGan()
      } else {
        this.ShowAlert('Có lỗi xảy ra')
      }
    },

    async getDanhSachTinh(){
      let input = {}
      let data = this.GetData(await API.CSS_TINH(this.axios, input))
      if (data.length > 0) {
        this.cboDanhSachTinh.list = data.map(item => {
          return {
            id : item.TINH_ID,
            text : item.TENTINH,
            phanVungId: item.PHANVUNG_ID
          }
        })
        this.cboDanhSachTinh.value = this.$root.token.getPhanVungID();
        this.cboDanhSachGanTinh.list = this.cboDanhSachTinh.list

        this.dsTinh = data
      }

    },
    changeTinh() {
      this.LoadLoaiHinhTBCD()
      this.HIENTHI_QUYTRINH_KLD(this.quyTrinhId)
      // this.LoadChuDeKN()
      this.HIENTHI_QUYTRINH_VATTU()
      this.Load_LoaiHinhTB_ADSL(this.cboLoaiHinhThueBao.value)
    },
    async cboDichVuVT_SelectedValueChanged() {
    // console.log(this.DichVuVienThong);
    console.log('tiendeptrai', this.cboDichVuVienThong.value);
      if (this.cboDichVuVienThong.list.length > 0)
        {
            if (this.cboDichVuVienThong.value == "") return;
            


            //Nếu là dịch vụ cố định + gphone + ims thì hiển thị panel cố định
            if (this.cboDichVuVienThong.value == this.DichVuVienThong.CO_DINH
                || this.cboDichVuVienThong.value == this.DichVuVienThong.GPHONE
                || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS
                || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS_DOITAC)
            {
                this.showCboLoaiHinhThueBao = false;
                this.showTocDoThueBao = false;
                this.showLoaiHinhKenhGan = true;
                this.showThueBaoCoDinh = true;
            }
            else
            {
                //bangts.HT_LoaiHinh_TB_DatMoi_Combobox(cboLoaiHinhTB, dichvuvt_id);
                //bangts.HT_DS_GRIDLOOKUP(cboLoaiHinhTB, bangts.Lay_LoaiHinh_TB_DatMoi(dichvuvt_id).Tables[0], "LOAIHINH_TB", "LOAITB_ID", true, 2, 400);
                let thongTinNguoiDungObj  = {
                  NGUOIDUNG_ID : this.$root.token.getNhanVienID()
                }

                let input = {
                  p_ds_para : JSON.stringify(thongTinNguoiDungObj)
                }
                let data = this.GetData(await API.load_form_quy_trinh(this.axios, input));
                data = JSON.parse(data)
                if (data.RESULT.LOAI_TB.length > 0) {
                  let arr = data.RESULT.LOAI_TB.filter(item => {
                    return item.DICHVUVT_ID == this.cboDichVuVienThong.value
                  })
                  if (arr.length > 0) {
                    this.cboLoaiHinhThueBao.list = arr.map(item => {
                      return {
                        id : item.LOAITB_ID,
                        text : item.LOAIHINH_TB,
                      }
                    })
                    this.cboLoaiHinhThueBao.list.sort((a,b) => {
                      return a.id - b.id
                    })
                    this.cboLoaiHinhThueBao.value = this.cboLoaiHinhThueBao.list[0] ? this.cboLoaiHinhThueBao.list[0].id : '';
                    this.cboLoaiHinhTB_EditValueChanged()
                  } else {
                    this.cboLoaiHinhThueBao.list = []
                    this.grid_TocDoDaGan.list = []
                    this.grid_TocDoChuaGan.list = []
                  }
                }
                if (this.cboDichVuVienThong.value == this.DichVuVienThong.ADSL)
                {
                    this.cboLoaiHinhThueBao.value = this.LoaiHinhTB.INTERNET_ADSL;
                }
                this.showCboLoaiHinhThueBao = true;
                this.showTocDoThueBao = true;
                this.showLoaiHinhKenhGan = false;
                this.showThueBaoCoDinh = false;
            }
        }
        this.HienThiDS();
    },
    // cboNhomQT_SelectedValueChanged() {
    //   if (this.cboNhomQuyTrinh.list.length <= 0) return;
    //   if (this.cboNhomQuyTrinh.value == this.NHOM_QT.BAOHANH_TBI)
    //   {
    //       splitContainerControl2.PanelVisibility = DevExpress.XtraEditors.SplitPanelVisibility.Panel1;
    //       splitContainerControl4.PanelVisibility = DevExpress.XtraEditors.SplitPanelVisibility.Both;
    //   }
    //   else
    //   {
    //       splitContainerControl2.PanelVisibility = DevExpress.XtraEditors.SplitPanelVisibility.Both;
    //       splitContainerControl4.PanelVisibility = DevExpress.XtraEditors.SplitPanelVisibility.Panel1;
    //   }
    //   if (this.cboNhomQuyTrinh.value == this.NHOM_QT.GIAIQUYET_KN)
    //   {
    //       this.showKhieuNai = true;
    //       this.showKieuLapDat = false;
    //       this.LoadChuDeKN();

    //       this.grid_LoaiTBCDDaGan.list = [];
    //       this.grid_LoaiTBCDChuaGan.list = [];
    //       this.grid_TocDoDaGan.list = [];
    //       this.grid_TocDoChuaGan.list = [];
    //   }
    //   else
    //   {
    //       this.showKhieuNai = false;
    //       this.showKieuLapDat = true;
    //   }

    //   if (!this.kt_load)
    //       this.HienThiDS();
    // },

    cboLoaiHD_EditValueChanged() {
      if (this.cboLoaiHopDong.value == null) return;
      this.HienThiDS();
    },

    async HienThiDS() {
      await this.Lay_DS_QuyTrinh(this.cboLoaiHopDong.value, this.cboDichVuVienThong.value, this.cboNhomQuyTrinh.value);
      if (this.danhSachQuyTrinhTable.list.length > 0) {
        this.gview_DanhSach_FocusedRowChanged(0, this.danhSachQuyTrinhTable.list[0]);
      } else {

        this.danhSachQuyTrinhTable.list = [];
        this.SetButton(1)
        this.Clean()
        this.LoadLoaiHinhTBCD();
        this.HIENTHI_QUYTRINH_KLD(this.quyTrinhId);
        this.HIENTHI_QUYTRINH_VATTU(this.quyTrinhId);
        // this.LoadChuDeKN();
      }
    },

    changeYeuCau(e) {
      this.checkNoCuoc = false
    },
    changeNoCuoc(e) {
      console.log('run in this')
      this.$nextTick(() => {
        this.checkTheoYeuCau = false
      })
    },

    gview_DanhSach_FocusedRowChanged(obj) {
      // if (index < 0) return;
      // let obj = this.danhSachQuyTrinhTable.list[index] ? this.danhSachQuyTrinhTable.list[index] : {};
      console.log('obj is ', obj.data)
      if (obj.data) {
        if (obj.data.quytrinh_id)
        {
          let quytrinh_id = obj.data.quytrinh_id;
          this.cboNhomQuyTrinh.value = obj.data.nhom_qt_id ? obj.data.nhom_qt_id : ''
          this.quyTrinhId = quytrinh_id
          this.tenQuyTrinh = obj.data.quytrinh ? obj.data.quytrinh : ''
          
          this.thuTu = obj.data.thutu ? obj.data.thutu : ''
          this.tenQuyTrinh = obj.data.quytrinh ? obj.data.quytrinh : ''
          this.textQuyTrinh = obj.data.command ? obj.data.command : ''
          let kieu = obj.data.kieu ? obj.data.kieu : '';
          this.getDanhSachPhanVungDaGan()
          this.getDanhSachPhanVungChuaGan()
          if (kieu == 0) {
            this.checkTheoYeuCau = true
            this.checkNoCuoc = false
          }
          else {
            this.checkNoCuoc = true
            this.checkTheoYeuCau = false
          }

          
          if (this.cboNhomQuyTrinh.value == this.NHOM_QT.DIEUHANH_THICONG)
          {
            this.cboLoaiHopDong.value = obj.data.loaihd_id;
            ////dichvuvt_id = objquytrinh.Lay_DVVT_theo_quytrinh_id(quytrinh_id);
            //dichvuvt_id = Convert.ToInt32(dtList.Rows[nIndex]["dichvuvt_id"].ToString());
            //cboDichVuVT.SelectedValue = dichvuvt_id;
            if (this.cboDichVuVienThong.value == this.DichVuVienThong.ADSL
                || this.cboDichVuVienThong.value == this.DichVuVienThong.DI_DONG
                || this.cboDichVuVienThong.value == this.DichVuVienThong.METRONET
                || this.cboDichVuVienThong.value == this.DichVuVienThong.MEGAWAN
                || this.cboDichVuVienThong.value == this.DichVuVienThong.TSL
                || this.cboDichVuVienThong.value == this.DichVuVienThong.MEGA_EYES
                || this.cboDichVuVienThong.value == this.DichVuVienThong.TRUNGTAM_DULIEU
                || this.cboDichVuVienThong.value == this.DichVuVienThong.ANTOAN_BAOMAT_TT
                || this.cboDichVuVienThong.value == this.DichVuVienThong.DICHVU_CNTT
                || this.cboDichVuVienThong.value == this.DichVuVienThong.HOINGHI_TRUYENHINH
                || this.cboDichVuVienThong.value == this.DichVuVienThong.BRCD_DOITAC
                || this.cboDichVuVienThong.value == this.DichVuVienThong.MGW_DOITAC
                || this.cboDichVuVienThong.value == this.DichVuVienThong.TSL_DOITAC
                || this.cboDichVuVienThong.value == 26
                || this.cboDichVuVienThong.value == 25
                || this.cboDichVuVienThong.value == this.DichVuVienThong.HATANG_VIENTHONG)
            {
                this.Load_LoaiHinhTB_ADSL(this.cboLoaiHinhThueBao.value)
                if (this.cboDichVuVienThong.value == this.DichVuVienThong.MEGAWAN ||
                    this.cboDichVuVienThong.value == this.DichVuVienThong.METRONET ||
                    this.cboDichVuVienThong.value == this.DichVuVienThong.TSL ||
                    this.cboDichVuVienThong.value == 26 // đa điểm
                    || this.cboDichVuVienThong.value == 25 // điểm điểm
                    )
                {
                    this.LoadLoaiKenh();
                    //splitContainerControl3.Panel2.Visible = true;
                    this.showLoaiHinhKenhGan = true
                }
                else
                {
                  this.showLoaiHinhKenhGan = false
                    //splitContainerControl3.Panel2.Visible = false;
                }
            }
            this.LoadLoaiHinhTBCD();
            this.HIENTHI_QUYTRINH_KLD(quytrinh_id);
            // this.LoadChuDeKN();
          }
          else if (this.cboNhomQuyTrinh.value == this.NHOM_QT.GIAIQUYET_KN)
          {
            this.LoadLoaiHinhTBCD();
            this.HIENTHI_QUYTRINH_KLD(quytrinh_id);
            this.LoadChuDeKN();
          }
          else if (this.cboNhomQuyTrinh.value == this.NHOM_QT.BAOHONG)
          {
              //cboLoaiHD.SelectedValue = Convert.ToInt32(dtList.Rows[nIndex]["loaihd_id"].ToString());

              ////dichvuvt_id = objquytrinh.Lay_DVVT_theo_quytrinh_id(quytrinh_id);
              //dichvuvt_id = Convert.ToInt32(dtList.Rows[nIndex]["dichvuvt_id"].ToString());
              //cboDichVuVT.SelectedValue = dichvuvt_id;
              if (this.cboDichVuVienThong.value == this.DichVuVienThong.ADSL
                  || this.cboDichVuVienThong.value== this.DichVuVienThong.BRCD_DOITAC
                  || this.cboDichVuVienThong.value == this.DichVuVienThong.MEGAWAN
                  || this.cboDichVuVienThong.value == this.DichVuVienThong.METRONET
                  || this.cboDichVuVienThong.value== this.DichVuVienThong.MGW_DOITAC
                  || this.cboDichVuVienThong.value == this.DichVuVienThong.TSL
                  ||this.cboDichVuVienThong.value== this.DichVuVienThong.TSL_DOITAC
                  || this.cboDichVuVienThong.value == this.DichVuVienThong.MEGA_EYES
                  )
              {
                  this.Load_LoaiHinhTB_ADSL(this.cboLoaiHinhThueBao.value);
              }
              this.LoadLoaiHinhTBCD();
              this.HIENTHI_QUYTRINH_KLD(quytrinh_id);
              // this.LoadChuDeKN();
              
              //this.cboLoaiHinhTB_EditValueChanged(null, null);
              
          }
          else if (this.cboNhomQuyTrinh.value == this.NHOM_QT.BAOHANH_TBI)
          {
              this.HIENTHI_QUYTRINH_VATTU(quytrinh_id);
          }
          this.SetButton(3);
          return;
        }
      }
    },

    LoadChuDeKN: async function () {
      let obj = {
        QUYTRINH_ID : this.quyTrinhId,
      }

      let input = {
        "phanvung_id": this.cboDanhSachTinh.value,
        "p_ds_para": JSON.stringify(obj)
      }

      let data = this.GetData(await API.fn_loadchudekn_capnhat_quytrinh(this.axios, input));
      data = JSON.parse(data);
      if (data && Object.keys(data).length > 0) {
        if (data.ERROR_CODE == 1) {
          if (data.RESULT.KN_CHUAGAN && data.RESULT.KN_CHUAGAN.length > 0) {
            this.ds_cdkn_chuagan.list = data.RESULT.KN_CHUAGAN
          }
          if (data.RESULT.KN_DAGAN && data.RESULT.KN_DAGAN.length > 0) {
            this.ds_cdkn_dagan.list = data.RESULT.KN_DAGAN
          }
        }
      }
    },

    cboLoaiHinhTB_EditValueChanged() {
      if (this.cboLoaiHinhThueBao.value != "")
      {
          let loaitb_id = this.cboLoaiHinhThueBao.value
          this.Load_LoaiHinhTB_ADSL(loaitb_id);
      }
    },

    HIENTHI_QUYTRINH_KLD : async function(quytrinh_id) {
      if (this.cboDichVuVienThong.list.length <=0) return;
      if (this.cboDichVuVienThong.value == "") return;
      let dichvuvt_id = this.cboDichVuVienThong.value;

      let obj = {
        DICHVUVT_ID : dichvuvt_id,
        QUYTRINH_ID : quytrinh_id,
        LOAIHD_ID : this.cboLoaiHopDong.value
      }

      let input = {
        "phanvung_id": this.cboDanhSachTinh.value,
        "p_ds_para" : JSON.stringify(obj)
      }

      let data = this.GetData(await API.fn_ht_quytrinh_kld_capnhat_quytrinh(this.axios, input));
      data = JSON.parse(data);
      if (data && Object.keys(data).length > 0) {
        if (data.ERROR_CODE == 1) {
          if (data.RESULT.KLD_CHUAGAN && data.RESULT.KLD_CHUAGAN.length >= 0) {
            this.ds_kieuld_chuagan.list = data.RESULT.KLD_CHUAGAN
          }
          if (data.RESULT.KLD_DAGAN && data.RESULT.KLD_DAGAN.length >= 0) {
            this.ds_kieuld_dagan.list = data.RESULT.KLD_DAGAN
          }
        }
      }
    },
    HIENTHI_QUYTRINH_VATTU : async function(quytrinh_id) {
      if (this.cboDichVuVienThong.list.length <=0) return;
      if (this.cboDichVuVienThong.value == "") return;
      let dichvuvt_id = this.cboDichVuVienThong.value;

      let obj = {
        DICHVUVT_ID : dichvuvt_id,
        QUYTRINH_ID : quytrinh_id
      }

      let input = {
        "phanvung_id": this.cboDanhSachTinh.value,
        "p_ds_para" : JSON.stringify(obj)
      }

      let data = this.GetData(await API.fn_ht_vattu_quytrinh(this.axios, input));
      data = JSON.parse(data);
      if (data && Object.keys(data).length > 0) {
        if (data.ERROR_CODE == 1) {
          if (data.RESULT.VT_CHUAGAN && data.RESULT.VT_CHUAGAN.length > 0) {
            this.ds_vattu_chuagan.list = data.RESULT.VT_CHUAGAN
          }
          if (data.RESULT.VT_DAGAN && data.RESULT.VT_DAGAN.length > 0) {
            this.ds_vattu_dagan.list = data.RESULT.VT_DAGAN
          }
        }
      }
    },

    LoadLoaiKenh: async function() {
      let obj = {
        QUYTRINH_ID : this.quyTrinhId,
      }

      let input = {
        "phanvung_id": this.cboDanhSachTinh.value,
        "p_ds_para" : JSON.stringify(obj)
      }

      let data = this.GetData(await API.fn_loadloaikenh_capnhat_quytrinh(this.axios, input));
      data = JSON.parse(data);

      if (data && Object.keys(data).length > 0) {
        if (data.ERROR_CODE == 1) {
          if (data.RESULT.LK_CHUAGAN && data.RESULT.LK_CHUAGAN.length >= 0) {
            this.ds_loaikenh_chuagan.list = data.RESULT.LK_CHUAGAN
          }
          if (data.RESULT.LK_DAGAN && data.RESULT.LK_DAGAN.length >= 0) {
            this.ds_loaikenh_dagan.list = data.RESULT.LK_DAGAN
          }
        }
      }
    },
    LoadLoaiHinhTBCD: async function () {
      if (this.cboDichVuVienThong.value == this.DichVuVienThong.CO_DINH || this.cboDichVuVienThong.value == this.DichVuVienThong.GPHONE
                || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS_DOITAC)
            {
              let obj = {
                DICHVUVT_ID : this.cboDichVuVienThong.value,
                QUYTRINH_ID : this.quyTrinhId
              }

              let input = {
                phanvung_id : this.cboDanhSachTinh.value,
                p_ds_para : JSON.stringify(obj)
              }

              let data = this.GetData(await API.fn_load_lhtbcd_capnhat_quytrinh(this.axios, input));
              data = JSON.parse(data);
              if (data && Object.keys(data).length > 0) {
                if (data.RESULT.DS_CHUAGAN) {
                    this.grid_LoaiTBCDChuaGan.list = data.RESULT.DS_CHUAGAN
                  }
                  if (data.RESULT.DS_DAGAN) {
                    this.grid_LoaiTBCDDaGan.list = data.RESULT.DS_DAGAN
                  }
              }
            }
    },
    //danh sach loai hinh thue bao toc do
    Load_LoaiHinhTB_ADSL: async function (loaitb_id) {
      let obj = {
        DICHVUVT_ID : this.cboDichVuVienThong.value,
        QUYTRINH_ID : this.quyTrinhId,
        LOAITB_ID : loaitb_id
      }
      let input = {
        "phanvung_id" : this.cboDanhSachTinh.value,
        "p_ds_para" : JSON.stringify(obj)
      }

      let data = this.GetData(await API.fn_load_loaihinhtb_adsl_capnhat_quytrinh(this.axios, input));
      data = JSON.parse(data);

      if (data) {
        if (data.RESULT.TD_CHUAGAN) {
          this.grid_TocDoChuaGan.list = data.RESULT.TD_CHUAGAN
        }
        if (data.RESULT.TD_DAGAN) {
          this.grid_TocDoDaGan.list = data.RESULT.TD_DAGAN
        }
      }
    },

    cboNhomQT_SelectedValueChanged() {
      if (this.cboNhomQuyTrinh.list.length <= 0) return;
      if (this.cboNhomQuyTrinh.value == this.NHOM_QT.BAOHANH_TBI)
      {
          // splitContainerControl2.PanelVisibility = DevExpress.XtraEditors.SplitPanelVisibility.Panel1;
          this.showCboLoaiHinhThueBao = true;
          this.showTocDoThueBao = true;
          this.showThueBaoCoDinh = true;
          //splitContainerControl4.PanelVisibility = DevExpress.XtraEditors.SplitPanelVisibility.Both;
          this.showVatTu = true
      }
      else
      {
          //splitContainerControl2.PanelVisibility = DevExpress.XtraEditors.SplitPanelVisibility.Both;
          //splitContainerControl4.PanelVisibility = DevExpress.XtraEditors.SplitPanelVisibility.Panel1;
          this.showThueBaoCoDinh = true;
          this.showCboLoaiHinhThueBao = true;
          this.showTocDoThueBao = true;
          this.showKhieuNai = true;
          this.showKieuLapDat = true
      }
      if (this.cboNhomQuyTrinh.value == this.NHOM_QT.GIAIQUYET_KN)
      {
          this.showKhieuNai = true;
          this.showKieuLapDat = false;
          this.LoadChuDeKN();

          this.grid_LoaiTBCDDaGan.list = [];
          this.grid_LoaiTBCDChuaGan.list = [];
          this.grid_TocDoDaGan.list = [];
          this.grid_TocDoChuaGan.list = [];
      }
      else
      {
          this.showKhieuNai = false;
          this.showKieuLapDat = true;
      }

      if (!this.kt_load) {
        this.HienThiDS();
      }
    },


    Lay_DS_QuyTrinh : async function (loaihd_id, dichvuvt_id, nhom_qt_id) {
      let input = {
        p_loaihd_id : loaihd_id,
        p_dichvuvt_id : dichvuvt_id,
        p_nhom_qt_id : nhom_qt_id
      }

      let data = this.GetData(await API.danh_sach_quy_trinh(this.axios, input));
      // data = JSON.parse(data);
      if (data.length > 0) {
        this.danhSachQuyTrinhTable.list = data ? data : [];
      } else {
        this.danhSachQuyTrinhTable.list = []
      }
    },

    loadFormQuyTrinh : async function () {
      let thongTinNguoiDungObj  = {
        NGUOIDUNG_ID : this.$root.token.getNhanVienID()
      }

      let input = {
        p_ds_para : JSON.stringify(thongTinNguoiDungObj)
      }
      let data = this.GetData(await API.load_form_quy_trinh(this.axios, input));
      data = JSON.parse(data);
      console.log('data in load tt quy trinh is ', data)
      if (Object.keys(data).length > 0) {
        if (data.ERROR_CODE == 1) {
          if (data.RESULT.DVVT.length > 0) {
            this.cboDichVuVienThong.list = data.RESULT.DVVT.map(item => {
              return {
                id : item.DICHVUVT_ID,
                text : item.TEN_DVVT
              }
            })
            this.cboDichVuVienThong.list.sort((a,b) => {
              return a.id - b.id
            })
            this.cboDichVuVienThong.value = this.cboDichVuVienThong.list[0].id;
          }

          if (data.RESULT.LOAI_HD.length > 0) {
            this.cboLoaiHopDong.list = data.RESULT.LOAI_HD.map(item => {
              return {
                id : item.LOAIHD_ID,
                text : item.TEN_LOAIHD
              }
            })
            this.cboLoaiHopDong.list.sort((a,b) => {
              return a.id - b.id
            })
            this.cboLoaiHopDong.value = this.cboLoaiHopDong.list[0].id;
          }

          if (data.RESULT.NHOM_QT.length > 0) {
            this.cboNhomQuyTrinh.list = data.RESULT.NHOM_QT.map(item => {
              return {
                id : item.NHOM_QT_ID,
                text : item.TEN_NHOM
              }
            })
            this.cboNhomQuyTrinh.list.sort((a, b) => {
              return a.id - b.id
            })
            this.cboNhomQuyTrinh.value = this.cboNhomQuyTrinh.list[0].id;
          }

          if (data.RESULT.LOAI_TB.length > 0) {
            this.cboLoaiHinhThueBao.list = data.RESULT.LOAI_TB.map(item => {
              return {
                id : item.LOAITB_ID,
                text : item.LOAIHINH_TB,
                dichVuVienThongId : item.DICHVUVT_ID
              }
            })
            this.cboLoaiHinhThueBao.list.sort((a,b) => {
              return a.id - b.id
            })
            this.cboLoaiHinhThueBao.value = this.cboLoaiHinhThueBao.list[0].id;
          }
        }
        
      }

    },

    openBaoHanhThietBi() {
      this.$refs.baoHanhThietBi.show();
    },
    //button functon
    SetButton(kieu)
    {
      this.tsbtnNhapMoi = false;
      this.tsbtnGhiLai = false;
      this.tsbtnXoa = false;
      this.tsbtnHuyBo = false;
      if (kieu == -1)//Bat dau
      {
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
      }
      if (kieu == 0)//Bat dau
      {
        this.tsbtnNhapMoi = true;
      }
      if (kieu == 1)//Them moi
      {
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
      }
      if (kieu == 2)//Huy
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
      }
      if (kieu == 3)//Edit
      {
        this.tsbtnNhapMoi = true;
        this.tsbtnXoa = true;
        this.tsbtnGhiLai = true;
        this.tsbtnHuyBo = true;
      }
    },
    Clean()
    {
      this.quyTrinhId = 0;
      this.textQuyTrinh = "";
      this.tenQuyTrinh = "";
      this.thuTu = "";
    },
    tsbtnGhiLai_Click() {
      this.capNhat();
    },
    capNhat: async function() {
      if (!this.KiemTra_DuLieu()) return;
      if (this.tsbtnNhapMoi == false)
      {
          // this.TaoDuLieu(true);
          let input = {
            "p_ds_para": JSON.stringify(await this.TaoDuLieu(true))
          }
          //new QuyTrinhFacade().Insert(quytrinh_id, dsquytrinh, dsquytrinh_kieuld, dsquytrinh_lhtb, dsquytrinh_cdkn, dsquytrinh_loai_kenh,dsquytrinh_vattu);
          let data = this.GetData(await API.fn_ins_upd_quytrinh(this.axios, input));
          data = JSON.parse(data);
          if (data && data.ERROR_CODE) {
            this.showSuccess("Thêm mới Quy trình vào cơ sở dữ liệu tỉnh thành công !")
          } else {
            console.log(data)
          }
      }
      else
      {
          let input = {
            "p_ds_para": JSON.stringify(await this.TaoDuLieu(false))
          }
          //new QuyTrinhFacade().Insert(quytrinh_id, dsquytrinh, dsquytrinh_kieuld, dsquytrinh_lhtb, dsquytrinh_cdkn, dsquytrinh_loai_kenh,dsquytrinh_vattu);
          let data = this.GetData(await API.fn_ins_upd_quytrinh(this.axios, input));
          data = JSON.parse(data);
          if (data.ERROR_CODE) {
            this.showSuccess("Cập nhật Quy trình vào cơ sở dữ liệu tỉnh thành công !")
          } else {
            console.log(data)
          }
      }
      this.HienThiDS();
      this.SetButton(3);
    },
    KiemTra_DuLieu() {
      if (this.tenQuyTrinh == "")
      {
        this.ShowAlert("Tên quy trình không được để trống !")
        return false;
      }
      if (this.textQuyTrinh == "")
      {
        this.ShowAlert("Quy trình không được để trống !");
        // txtQuyTrinh.Focus();
        return false;
      }
      if (this.thuTu == "")
      {
        this.ShowAlert("Hãy nhập thứ tự")
        // txtThuTu.Focus();
        return false;
      }
      if (isNaN(+this.thuTu)) {
        this.ShowAlert("Dữ liệu nhập vào phải là số!")
        return false;
      }
      return true;
    },
    async TaoDuLieu(themmoi) {
      let quyTrinh = []
      await this.TaoDuLieu_QuyTrinh(themmoi).then(function(value) {
        quyTrinh = value
      })
      // console.log('quy trinh is ', quyTrinh);
      let dsquytrinh_lhtb = [];
      let dsquytrinh_kieuld = [];
      let dsquytrinh_cdkn = []
      let dsquytrinh_loai_kenh = [];
      let dsquytrinh_vattu = [];
      // if (this.cboNhomQuyTrinh.value != this.NHOM_QT.GIAIQUYET_KN)
      // {
      //     dsquytrinh_lhtb = this.TaoDuLieu_QuyTrinh_LHTB(themmoi);
      //     if (this.cboNhomQuyTrinh.value == this.NHOM_QT.DIEUHANH_THICONG)
      //     {
      //         dsquytrinh_kieuld = this.TaoDuLieu_QuyTrinh_KIEULD(themmoi);
      //         if (this.cboDichVuVienThong.value == this.DichVuVienThong.MEGAWAN ||
      //             this.cboDichVuVienThong.value == this.DichVuVienThong.METRONET ||
      //             this.cboDichVuVienThong.value == this.DichVuVienThong.TSL) {
      //               dsquytrinh_loai_kenh = this.TaoDuLieu_QuyTrinh_LoaiKenh(themmoi);
      //             }
      //     }
      //     else if (this.cboNhomQuyTrinh.value == this.NHOM_QT.BAOHANH_TBI)
      //     {
      //         dsquytrinh_vattu = this.TaoDuLieu_QuyTrinh_VATTU(themmoi);
      //     }
      // }
      // else {
      //   dsquytrinh_cdkn = this.TaoDuLieu_QuyTrinh_CDKN(themmoi);
      // }
      let obj = {
        THEMMOI : themmoi,
        QUYTRINH : quyTrinh,
        // QUYTRINH_KIEULD : dsquytrinh_kieuld,
        // QUYTRINH_LHTB : dsquytrinh_lhtb,
        // QT_CHUDEKN : dsquytrinh_cdkn,
        // QUYTRINH_LOAI_KENH : dsquytrinh_loai_kenh,
        // QUYTRINH_VT : dsquytrinh_vattu
      }
      return obj;
    },
    TaoDuLieu_QuyTrinh : async function(themmoi) {
      let row = [];
      let obj = {};
      let input = {
        "keyname": "QUYTRINH_ID",
        "phanvung_id": 0
      }
      if (themmoi) {
        let qtId = this.GetData(await API.get_keys(this.axios, input))
        obj.QUYTRINH_ID = qtId
      } else {
        obj.QUYTRINH_ID = this.quyTrinhId
      }
      obj.NHOM_QT_ID = this.cboNhomQuyTrinh.value
      obj.QUYTRINH = this.tenQuyTrinh
      obj.COMMAND = this.textQuyTrinh
      obj.THUTU = this.thuTu
      if (this.checkTheoYeuCau) {
        obj.KIEU = 0
      } else {
        obj.KIEU = 1
      }

      if (this.cboNhomQuyTrinh.value != this.NHOM_QT.GIAIQUYET_KN) {
        obj.DICHVUVT_ID = this.cboDichVuVienThong.value
          if (this.cboNhomQuyTrinh.value == this.NHOM_QT.DIEUHANH_THICONG)
              obj.LOAIHD_ID = this.cboLoaiHopDong.value
          //HuongNX bổ sung
          if (this.cboNhomQuyTrinh.value == this.NHOM_QT.BAOHANH_TBI)
              obj.LOAIHD_ID = this.cboLoaiHopDong.value
      }
      row.push(obj)
      return row
    },
    TaoDuLieu_QuyTrinh_LHTB(themmoi) {
      let row = []
      if (this.cboDichVuVienThong.value == this.DichVuVienThong.CO_DINH || this.cboDichVuVienThong.value == this.DichVuVienThong.GPHONE
          || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS_DOITAC) // Nếu không phải là dich vụ internet
          if (this.grid_LoaiTBCDDaGan.list.length > 0) {
            this.grid_LoaiTBCDChuaGan.list.map(item => {
              let obj = {}
              obj.LOAITB_ID = item['LOAITB_ID']
              obj.QUYTRINH_ID = this.quyTrinhId
              obj.TOCDO_ID = 0
              obj.MUCCUOC_ID = 0
              row.push(obj)
            })
          }
      else
        if (this.grid_TocDoDaGan.list.length>0)
        {
          this.grid_TocDoDaGan.list.map(item => {
            let obj = {}
            obj.LOAITB_ID = item['LOAITB_ID']
            obj.QUYTRINH_ID = this.quyTrinhId
            obj.TOCDO_ID = item['TOCDO_ID']
            obj.MUCCUOC_ID = item['MUCCUOC_ID']
            row.push(obj)
          })
        }
      return row
    },

    TaoDuLieu_QuyTrinh_KIEULD(themmoi) {
      let row = []
      if (this.ds_kieuld_dagan.list.length > 0) {
        this.ds_kieuld_dagan.list.map(item => {
          let obj = {}
          obj.KIEULD_ID = item['KIEULD_ID'],
          obj.QUYTRINH_ID = this.quyTrinhId
          row.push(obj)
        })
      }
      return row
    },

    TaoDuLieu_QuyTrinh_VATTU(themmoi)
    {
        let row = []
        if (this.ds_vattu_dagan.list.length > 0) {
          this.ds_vattu_dagan.list.map(item => {
            let obj = {}
            obj.VATTU_ID = item['VATTU_ID'],
            obj.QUYTRINH_ID = this.quyTrinhId
            row.push(obj)
          })
        }
        return row
    },
    TaoDuLieu_QuyTrinh_CDKN(themmoi)
    {
        let row = []
        if (this.ds_cdkn_dagan.list.length > 0) {
          this.ds_cdkn_dagan.list.map(item => {
            let obj = {}
            obj.CHUDEKN_ID = item['CHUDEKN_ID'],
            obj.QUYTRINH_ID = this.quyTrinhId
            row.push(obj)
          })
        }
        return row
    },
    tsbtnNhapMoi_Click() {
      this.SetButton(1);
      this.Clean();
    },
    tsbtnHuyBo_Click() {
      this.SetButton(3);
      this.Clean();
    },
    tsbtnXoa_Click() {
      this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn Xóa quy trình này không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        let obj = {
          QUYTRINH_ID : this.quyTrinhId
        }
        let input = JSON.stringify(obj)
        API.fn_xoa_quytrinh(this.axios, {"p_ds_para" : input}).then((response) => {
          console.log('response.data is ', response.data)
          if (response.data.data) {
            if(JSON.parse(response.data.data).ERROR_CODE) {
              this.showSuccess("Xóa Quy trình trên dữ liệu viễn thông tỉnh thành công !");
              this.HienThiDS();
            }
            else
            {
              this.ShowAlert("Quy trình này đã được gán hướng giao. Bạn không thể xóa !");
            }
          }
        }
        ).catch(function(){
          console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
        });
      }
      });
    },
    async thaoTacGanGoAllQuyTrinhNghiepVu(kieu, tenNghiepVu) {
      let phanVung = []
      let duLieuNghiepVu = []
      if (this.checkDsGanTinh) {
        if (this.cboDanhSachGanTinh.value.length > 0) {
          this.cboDanhSachGanTinh.value.map(item => {
            phanVung.push({
              'PHANVUNG_ID' : item
            })
          })
        }
      } else {
        phanVung.push({
          'PHANVUNG_ID' : this.cboDanhSachTinh.value
        })
      }
      let obj = {
        'KIEU' : kieu,
        'NGHIEPVU' : tenNghiepVu,
        'QUYTRINH_ID' : this.quyTrinhId,
        'DS_PHANVUNG' : phanVung
      }
      if (kieu == 0) {
        if (tenNghiepVu == 'QT_LHTB') {
          // if (this.$refs.LoaiTBCDChuaGan.getSelectedRecords() && this.$refs.LoaiTBCDChuaGan.getSelectedRecords().length > 0) {
          //   obj['QUYTRINH_NV'] =  this.$refs.LoaiTBCDChuaGan.getSelectedRecords()
          // }
          // else if (obj['QUYTRINH_NV'] =  this.$refs.tocdo_chuagan.getSelectedRecords() && this.$refs.tocdo_chuagan.getSelectedRecords().length > 0) {
          //   obj['QUYTRINH_NV'] =  this.$refs.tocdo_chuagan.getSelectedRecords()
          // }

          if (this.cboDichVuVienThong.value == this.DichVuVienThong.CO_DINH || this.cboDichVuVienThong.value == this.DichVuVienThong.GPHONE
                || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS_DOITAC) {
                if (this.grid_LoaiTBCDChuaGan.list && this.grid_LoaiTBCDChuaGan.list.length > 0) {
                  obj['QUYTRINH_NV'] = this.grid_LoaiTBCDChuaGan.list
                  if (obj['QUYTRINH_NV'].length > 0) {
                    obj['QUYTRINH_NV'].map(item => {
                      item.QUYTRINH_ID = this.quyTrinhId,
                      item.TOCDO_ID = 0,
                      item.MUCCUOC_ID = 1
                    })
                  }
                }
          } else {
            if (obj['QUYTRINH_NV'] =  this.grid_TocDoChuaGan.list && this.grid_TocDoChuaGan.list.length > 0)
            {
                obj['QUYTRINH_NV'] = this.grid_TocDoChuaGan.list
                if (obj['QUYTRINH_NV'].length > 0) {
                  obj['QUYTRINH_NV'].map(item => {
                    item.QUYTRINH_ID = this.quyTrinhId
                  })
                }
            }
          }

        }
      } else {
        if (tenNghiepVu == 'QT_LHTB') {
          if (this.cboDichVuVienThong.value == this.DichVuVienThong.CO_DINH || this.cboDichVuVienThong.value == this.DichVuVienThong.GPHONE
                || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS_DOITAC) {
                if (this.grid_LoaiTBCDDaGan.list && this.grid_LoaiTBCDDaGan.list.length > 0) {
                  obj['QUYTRINH_NV'] = this.grid_LoaiTBCDDaGan.list
                  if (obj['QUYTRINH_NV'].length > 0) {
                    obj['QUYTRINH_NV'].map(item => {
                      item.QUYTRINH_ID = this.quyTrinhId,
                      item.TOCDO_ID = 0,
                      item.MUCCUOC_ID = 1
                    })
                  }
                }
          } else {
            if (obj['QUYTRINH_NV'] =  this.grid_TocDoDaGan.list && this.grid_TocDoDaGan.list.length > 0)
            {
                obj['QUYTRINH_NV'] = this.grid_TocDoDaGan.list
                
                if (obj['QUYTRINH_NV'].length > 0) {
                  obj['QUYTRINH_NV'].map(item => {
                    item.QUYTRINH_ID = this.quyTrinhId
                  })
                }
            }
          }
        }
      }

      let input = {
        "phanvung_id" : this.cboDanhSachTinh.value,
        "p_ds_para" : JSON.stringify(obj)
      }

      let result = await API.thao_tac_quy_trinh_nghiep_vu(this.axios, input)
      if (result) {
        if(result.data.data && JSON.parse(result.data.data).ERROR_CODE) {
          this.showSuccess('Gán/gỡ nghiệp vụ thành công!')
          // this.LoadChuDeKN()
          this.HIENTHI_QUYTRINH_KLD(this.quyTrinhId)
          this.HIENTHI_QUYTRINH_VATTU()
          this.LoadLoaiKenh()
          this.LoadLoaiHinhTBCD()
          this.Load_LoaiHinhTB_ADSL(this.cboLoaiHinhThueBao.value)
        }
      }
    },
    async thaoTacGanGoQuyTrinhNghiepVu(kieu, tenNghiepVu) {
      let phanVung = []
      let duLieuNghiepVu = []
      if (this.checkDsGanTinh) {
        if (this.cboDanhSachGanTinh.value.length > 0) {
          this.cboDanhSachGanTinh.value.map(item => {
            phanVung.push({
              'PHANVUNG_ID' : item
            })
          })
        }
      } else {
        phanVung.push({
          'PHANVUNG_ID' : this.cboDanhSachTinh.value
        })
      }
      let obj = {
        'KIEU' : kieu,
        'NGHIEPVU' : tenNghiepVu,
        'QUYTRINH_ID' : this.quyTrinhId,
        'DS_PHANVUNG' : phanVung
      }
      if (kieu == 0) {
        if (tenNghiepVu == 'QT_LHTB') {
          // if (this.$refs.LoaiTBCDChuaGan.getSelectedRecords() && this.$refs.LoaiTBCDChuaGan.getSelectedRecords().length > 0) {
          //   obj['QUYTRINH_NV'] =  this.$refs.LoaiTBCDChuaGan.getSelectedRecords()
          // }
          // else if (obj['QUYTRINH_NV'] =  this.$refs.tocdo_chuagan.getSelectedRecords() && this.$refs.tocdo_chuagan.getSelectedRecords().length > 0) {
          //   obj['QUYTRINH_NV'] =  this.$refs.tocdo_chuagan.getSelectedRecords()
          // }

          if (this.cboDichVuVienThong.value == this.DichVuVienThong.CO_DINH || this.cboDichVuVienThong.value == this.DichVuVienThong.GPHONE
                || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS_DOITAC) {
                if (this.$refs.LoaiTBCDChuaGan.getSelectedRecords() && this.$refs.LoaiTBCDChuaGan.getSelectedRecords().length > 0) {
                  obj['QUYTRINH_NV'] = this.$refs.LoaiTBCDChuaGan.getSelectedRecords()
                  if (obj['QUYTRINH_NV'].length > 0) {
                    obj['QUYTRINH_NV'].map(item => {
                      item.QUYTRINH_ID = this.quyTrinhId,
                      item.TOCDO_ID = 0,
                      item.MUCCUOC_ID = 0
                    })
                  }
                }
          } else {
            if (obj['QUYTRINH_NV'] =  this.$refs.tocdo_chuagan.getSelectedRecords() && this.$refs.tocdo_chuagan.getSelectedRecords().length > 0)
            {
                obj['QUYTRINH_NV'] = this.$refs.tocdo_chuagan.getSelectedRecords()
                if (obj['QUYTRINH_NV'].length > 0) {
                  obj['QUYTRINH_NV'].map(item => {
                    item.QUYTRINH_ID = this.quyTrinhId
                  })
                }
            }
          }

        }
        if (tenNghiepVu == 'QT_KIEULD') {
          obj['QUYTRINH_NV'] =  this.$refs.kieuld_chuagan.getSelectedRecords()
          if (obj['QUYTRINH_NV'].length > 0) {
            obj['QUYTRINH_NV'].map(item => {
              item.QUYTRINH_ID = this.quyTrinhId
            })
          }
        }
        if (tenNghiepVu == 'QT_CDKN') {
          obj['QUYTRINH_NV'] =  this.$refs.chuDeKhieuNaiChuaGan.getSelectedRecords()
          if (obj['QUYTRINH_NV'].length > 0) {
            obj['QUYTRINH_NV'].map(item => {
              item.QUYTRINH_ID = this.quyTrinhId
            })
          }
        }
        if (tenNghiepVu == 'QT_LOAIKENH') {
          obj['QUYTRINH_NV'] =  this.$refs.loaiKenhChuaGan.getSelectedRecords()
          if (obj['QUYTRINH_NV'].length > 0) {
            obj['QUYTRINH_NV'].map(item => {
              item.QUYTRINH_ID = this.quyTrinhId
            })
          }
        }
        if (tenNghiepVu == 'QT_VATTU') {
          obj['QUYTRINH_NV'] =  this.$refs.vatTuChuaGan.getSelectedRecords()
          if (obj['QUYTRINH_NV'].length > 0) {
            obj['QUYTRINH_NV'].map(item => {
              item.QUYTRINH_ID = this.quyTrinhId
            })
          }
        }
        // if (tenNghiepVu == 'QT_LHTB') {
        //   obj['QUYTRINH_NV'] =  this.$refs.tocdo_chuagan.getSelectedRecords()
        //   if (obj['QUYTRINH_NV'].length > 0) {
        //     obj['QUYTRINH_NV'].map(item => {
        //       item.QUYTRINH_ID = this.quyTrinhId
        //     })
        //   }
        // }
      } else {
        if (tenNghiepVu == 'QT_LHTB') {
          if (this.cboDichVuVienThong.value == this.DichVuVienThong.CO_DINH || this.cboDichVuVienThong.value == this.DichVuVienThong.GPHONE
                || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS || this.cboDichVuVienThong.value == this.DichVuVienThong.IMS_DOITAC) {
                if (this.$refs.LoaiTBCDDaGan.getSelectedRecords() && this.$refs.LoaiTBCDDaGan.getSelectedRecords().length > 0) {
                  obj['QUYTRINH_NV'] = this.$refs.LoaiTBCDDaGan.getSelectedRecords()
                  if (obj['QUYTRINH_NV'].length > 0) {
                    obj['QUYTRINH_NV'].map(item => {
                      item.QUYTRINH_ID = this.quyTrinhId,
                      item.TOCDO_ID = 0,
                      item.MUCCUOC_ID = 0
                    })
                  }
                }
          } else {
            if (obj['QUYTRINH_NV'] =  this.$refs.tocdo_dagan.getSelectedRecords() && this.$refs.tocdo_dagan.getSelectedRecords().length > 0)
            {
                obj['QUYTRINH_NV'] = this.$refs.tocdo_dagan.getSelectedRecords()
                
                if (obj['QUYTRINH_NV'].length > 0) {
                  obj['QUYTRINH_NV'].map(item => {
                    item.QUYTRINH_ID = this.quyTrinhId
                  })
                }
            }
          }
        }
        if (tenNghiepVu == 'QT_KIEULD') {
          obj['QUYTRINH_NV'] =  this.$refs.kieuld_dagan.getSelectedRecords()
          if (obj['QUYTRINH_NV'].length > 0) {
            obj['QUYTRINH_NV'].map(item => {
              item.QUYTRINH_ID = this.quyTrinhId
            })
          }
        }
        if (tenNghiepVu == 'QT_CDKN') {
          obj['QUYTRINH_NV'] =  this.$refs.chuDeKhieuNaiDaGan.getSelectedRecords()
          if (obj['QUYTRINH_NV'].length > 0) {
            obj['QUYTRINH_NV'].map(item => {
              item.QUYTRINH_ID = this.quyTrinhId
            })
          }
        }
        if (tenNghiepVu == 'QT_LOAIKENH') {
          obj['QUYTRINH_NV'] =  this.$refs.loaiKenhDaGan.getSelectedRecords()
          if (obj['QUYTRINH_NV'].length > 0) {
            obj['QUYTRINH_NV'].map(item => {
              item.QUYTRINH_ID = this.quyTrinhId
            })
          }
        }
        if (tenNghiepVu == 'QT_VATTU') {
          obj['QUYTRINH_NV'] =  this.$refs.vatTuDaGan.getSelectedRecords()
          if (obj['QUYTRINH_NV'].length > 0) {
            obj['QUYTRINH_NV'].map(item => {
              item.QUYTRINH_ID = this.quyTrinhId
            })
          }
        }
        // if (tenNghiepVu == 'QT_LHTB') {
        //   obj['QUYTRINH_NV'] =  this.$refs.tocdo_dagan.getSelectedRecords()
        //   if (obj['QUYTRINH_NV'].length > 0) {
        //     obj['QUYTRINH_NV'].map(item => {
        //       item.QUYTRINH_ID = this.quyTrinhId
        //     })
        //   }
        // }
      }

      let input = {
        "phanvung_id" : this.cboDanhSachTinh.value,
        "p_ds_para" : JSON.stringify(obj)
      }

      try {
        this.loading(true)
        let result = await API.thao_tac_quy_trinh_nghiep_vu(this.axios, input)
        if (result) {
          if(result.data.data && JSON.parse(result.data.data).ERROR_CODE) {
            this.showSuccess('Gán/gỡ nghiệp vụ thành công!')
            // this.LoadChuDeKN()
            this.HIENTHI_QUYTRINH_KLD(this.quyTrinhId)
            this.HIENTHI_QUYTRINH_VATTU()
            this.LoadLoaiKenh()
            this.LoadLoaiHinhTBCD()
            this.Load_LoaiHinhTB_ADSL(this.cboLoaiHinhThueBao.value)
          }
        }
      } catch(e) {
        this.$toast.error('Có lỗi xẩy ra')
      }
      finally {
        this.loading(false);
      }
    },
  },
};

</script>
