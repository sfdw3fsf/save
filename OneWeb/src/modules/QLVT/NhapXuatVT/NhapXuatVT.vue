<template src='./NhapXuatVT.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import NhapXuatVTAPI from  '../api/NhapXuatVTAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/nhapxuatvt'
import select2 from '@/components/Select2.vue'
import { Query } from '@syncfusion/ej2-data';

import XLSX from "xlsx";
import frmGanSeri from '../frmGanSeri/frmGanSeri.vue'
import frmChungTu_TBao from '../frmChungTu_TBao'
import frmTaoSerial from '../frmTaoSerial'
import DSVatTu from '../DSVatTu'

import TraCuuDonHangConHan from '../TraCuuDonHangConHan'
import frmVatTuTrongKho from '../frmVatTuTrongKho'
import frmNhapText from '../frmNhapText/frmNhapText.vue'
import frmNhapCap from '../frmNhapCap'
import frmChonThietBiCT from "../frmChonTBi_CT/ChonThietBiCT.vue";
import {previewPrint} from "../../../utils/util";
import { currency } from '@/filters/currency'
import frmGanSeri_Tbi from '../frmGanSeri_Tbi/frmGanSeri_Tbi.vue'
import frmGiaoPhieu from "../frmGiaoPhieu/frmGiaoPhieu.vue";
import ChonThietBiCT from "../frmChonTBi_CT/ChonThietBiCT.vue";
import frmChonPhieuDeNghi from '../frmChonPhieuDeNghi/frmChonPhieuDeNghi.vue'
import TraCuuTTDongBo from '../TraCuuTTDongBo'
import frmNhapDieuChinh from '../frmNhapDieuChinh'
import NhanPhieuLuanChuyen from '../NhanPhieuLuanChuyen'
import DongBoCCDC from '../DongBoCCDC'
import { TooltipPlugin } from "@syncfusion/ej2-vue-popups";
import { Tooltip } from '@syncfusion/ej2-popups';
import frmNhapDieuChinh_HNI from '../NhapXuatVT_v2/components/frmNhapDieuChinh'

Vue.use(TooltipPlugin);
var DialogResultObj = {
    None: 0,
    OK: 1,
    Cancel: 2,
    Abort: 3,
    Retry: 4,
    Ignore: 5,
    Yes: 6,
    No: 7,
}
const URL_PSIS = "https://vendor.psis.vnpt.vn/QLMSEXTERNALAPI/api/Home/OpenFile?id="
export default {

  components: { frmNhapCap,
                frmNhapText, 
                TraCuuDonHangConHan, 
                frmVatTuTrongKho, 
                DSVatTu,
                frmTaoSerial, 
                frmChungTu_TBao,
                frmGanSeri, 
                XLSX, 
                breadcrumb, 
                appSelect2: select2,
                frmChonThietBiCT,
                frmGiaoPhieu,
                ChonThietBiCT,
                frmGanSeri_Tbi,
                frmChonPhieuDeNghi,
                TraCuuTTDongBo,
                frmNhapDieuChinh,
                DongBoCCDC,
                NhanPhieuLuanChuyen,
                frmNhapDieuChinh_HNI
  },
  name: "NhapXuatVT",
  mounted() {
    document.addEventListener("click", (event) => {     
      if (typeof this.$refs["title-du-an"] == "undefined") {
        return;
      }
      if (this.$refs["title-du-an"].contains(event.target)) {
        this.showDuAn = this.showDuAn == "none" ? "block" : "none";
      } else if (this.$refs["table-du-an"].contains(event.target)) {
        if (event.target.tagName.toLowerCase() == "td") {
          this.showDuAn = "none";
        } else {
          this.showDuAn = "block";
        }
      } else {
        this.showDuAn = "none";
      }

      if (this.showDuAn == "block") {
        this.popper = new Popper(
          this.$refs["title-du-an"],
          this.$refs["table-du-an"],
          {
            placement: "bottom-start",
            modifiers: [
              {
                name: "offset",
                options: {
                  offset: [0, 50],
                },
              },
            ],
          }
        );
      } else {
        if (this.popper) {
          this.popper.destroy();
        }
        this.popper = null;
      }
    });

    document.addEventListener("click", (event) => {     
      if (typeof this.$refs["title-hdpo"] == "undefined") {
        return;
      }
      if (this.$refs["title-hdpo"].contains(event.target)) {
        this.showDHPO = this.showDHPO == "none" ? "block" : "none";
      } else if (this.$refs["table-hdpo"].contains(event.target)) {
        if (event.target.tagName.toLowerCase() == "td") {
          this.showDHPO = "none";
        } else {
          this.showDHPO = "block";
        }
      } else {
        this.showDHPO = "none";
      }

      if (this.showDHPO == "block") {
        this.popper = new Popper(
          this.$refs["title-hdpo"],
          this.$refs["table-hdpo"],
          {
            placement: "bottom-start",
            modifiers: [
              {
                name: "offset",
                options: {
                  offset: [0, 50],
                },
              },
            ],
          }
        );
      } else {
        if (this.popper) {
          this.popper.destroy();
        }
        this.popper = null;
      }
    });

    document.addEventListener("click", (event) => {     
      if (typeof this.$refs["title-dvsd"] == "undefined") {
        return;
      }
      if (this.$refs["title-dvsd"].contains(event.target)) {
        this.showDVSD = this.showDVSD == "none" ? "block" : "none";
      } else if (this.$refs["table-dvsd"].contains(event.target)) {
        if (event.target.tagName.toLowerCase() == "td") {
          this.showDVSD = "none";
        } else {
          this.showDVSD = "block";
        }
      } else {
        this.showDVSD = "none";
      }

      if (this.showDVSD == "block") {
        this.popper = new Popper(
          this.$refs["title-dvsd"],
          this.$refs["table-dvsd"],
          {
            placement: "bottom-start",
            modifiers: [
              {
                name: "offset",
                options: {
                  offset: [0, 50],
                },
              },
            ],
          }
        );
      } else {
        if (this.popper) {
          this.popper.destroy();
        }
        this.popper = null;
      }
    });

    document.addEventListener("click", (event) => {     
      if (typeof this.$refs["title-dvql"] == "undefined") {
        return;
      }
      if (this.$refs["title-dvql"].contains(event.target)) {
        this.showDVQL = this.showDVQL == "none" ? "block" : "none";
      } else if (this.$refs["table-dvql"].contains(event.target)) {
        if (event.target.tagName.toLowerCase() == "td") {
          this.showDVQL = "none";
        } else {
          this.showDVQL = "block";
        }
      } else {
        this.showDVQL = "none";
      }

      if (this.showDVQL == "block") {
        this.popper = new Popper(
          this.$refs["title-dvql"],
          this.$refs["table-dvql"],
          {
            placement: "bottom-start",
            modifiers: [
              {
                name: "offset",
                options: {
                  offset: [0, 50],
                },
              },
            ],
          }
        );
      } else {
        if (this.popper) {
          this.popper.destroy();
        }
        this.popper = null;
      }
    });
  }, 
  computed: {
    ...mapState("nhapxuatvt", statePropertyName),
    ...mapGetters("nhapxuatvt", getterName),
    soph: {
      get() {
        return this.gridviewChungTu_FocusedRow ? this.gridviewChungTu_FocusedRow["SOPHIEU"] : ""
      }
    },
    ma_pda_chungtu: {
      get() {
        return this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["MA_PDA"] ? this.gridviewChungTu_FocusedRow["MA_PDA"] : ""
      }
    },
    nghiepvu_id_chungtu: {
      get() {
        return this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["NGHIEPVU_ID"] ? this.gridviewChungTu_FocusedRow["NGHIEPVU_ID"] : "-1"
      }
    },
    PTAggregatesColumns () {
      return [        
        { field: 'TIEN', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.SUMGiaTriConLai },
      
      ]
    },
  },
  data() {
    return {
        header: {
            title: "NHẬP, XUẤT, ĐIỀU CHUYỂN VẬT TƯ",
            list: [
            { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
            {
                name: "Nhập, xuất, điều chuyển vật tư",
                link: { name: "Ui.buttons" },
            },
            ],
        },
        _vloai: 1,
        chitietCTData_insert: [],
        chitietCTData_update: [],
        chungtuData: [],
        chungtu_id: -0,
        chungtu_tbiData: [],
        ctct_id: null,
        dsQuyen: [],
        DSMauIn: [],
        p_mauIn: "",
        contractNumber: "",
        tenNguoiNhan: "",
        maBC: "",

        dtChungTu: [],
        dtTB: [],
        dtThietBi: [],
        dtVatTu: [],

        dtLuuVT: [],
        dtCopyCT: [],
        dtCopyCTCT: [],
        dtVatTuXuat: [],
        
        khotu_id: 0,
        kieugoi_id: null,
        copyct: false,

        kieunhap: null,
        slohang: "",
        svattu_id: "",
        t_kho: "",
        t_kho_id: 0,

        ten_khotu: "",
        vchungtu_id: "",

        vttnx_id: 0,
        donhang_ids:[],
        dtDonHang:[],
        dtDulieu: [],
        dtNotInsert: [],
        dtThemVT: [],
        dtVT_LE: [],
        cbo_tinhtrang: [],
        gridTinhTrang: [],
        file: "",
        nddh: "",
        ttph_id: null,
        ngay_dh: null,

        dc_ct_noibo: false,
        ds_loc_vattu: [],
        ds_md: [],       
        showDuAn: 'none',
        showDHPO: 'none',
        showDVSD: 'none',
        showDVQL: 'none',
        frmNhapDieuChinh_HNI: { visible: false, input: {} },   
        isRenderNDC:false,  
        params:{
          p_txtSoPhieu: "",
          p_dtpNgayKy: new Date(),
          p_ckbSendSMS: true,
          p_LoaiChungTu: "",
          p_dtpNgayDK: new Date(),         
          p_MucDich: "",
          p_NghiepVu: "",
          p_Ma_PDA: "",
          p_txtGhiChuHD: "",

          p_ckbTuKho: false,
          p_TuKho: "",
          p_ckbDenKho: false,
          p_DenKho: "",
          p_dtpNgayGuiTT: new Date(),
          p_dtpNgayNhanTT: new Date(),
          p_txtLenhVanBan: "",
          p_dtpNgayVanBan: new Date(),

          p_CongTrinh: "",
          p_dtpNgayChuyenKT: new Date(),
          p_txtNoiDung: "",
          p_grcFileCV:"",

          p_dtpTuNgay: new Date((new Date()).getTime() - 7*24*60*60*1000), //ngày yêu cầu từ
          p_dtpDenNgay: new Date(), // đến ngày
          p_chkAll_Phieu: false,
          p_ckb_nd: false,
          p_ckb_xacNhan: false,
          p_VatTu: "",
          p_txtQuetSerial: "",
          p_TinhTrang: "",
          p_HopDong: "",
          p_DuAn: "-1",
          p_ckbDuAn: true,
          p_txtPhieuDeNghi: "",
          p_DonViFMIS: "",

          p_MaPhieu: "",
          p_LocDCDonVi : "",
          p_LocDCLoaiCT: "",
          p_Tinh:"",
          pageNumberTQ: 1,
          txtTK_MaCSHT : "",
          txtTK_TenCSHT: "",

          p_IMS_CSHT : "",
          p_Ma_DV_SD : "",
          text_Ma_DV_SD : "",
          p_Ma_DV_QL : "",
          text_Ma_DV_QL : "",
          p_HD_XuatIMS : "",
          text_HD_XuatIMS: "",
          p_HDPOIMS: "",

          rdioIMS : "0",
          rdioFMIS : "0",
          p_ckbTTALL: false,

          p_txtLenhDieuChuyen: "",
          p_LenhChuyenId: null
        },
        
        OpenCboMa_PDA: false,
        DSFileCV : [],
        cboDonVi_FMIS: [],
        cboLoaiChungTu: [],
        cboKieuPhieu: [],
        cboMucDich: [],
        cboNghiepVu: [],
        cboMa_PDA: [],
        cboTuKho: [], // value global        
        cboDenKho: [],
        cboCongTrinh: [],
        CboVatTu: [],
        cboLoaiKho: [],
        cboLocVattu: [],
        cboHopDong: [],
        cbo_DuAn: [],
        
        gridChungTu: [],
        gridBienBanIMS: [],
        grcMa_PDA: [],
        gridVTGroup: [],
        gridVatTu: [],
        gridThietBi: [],
        grcLocVattu: [],
        
        DS_VT_THUHOI_Checked: [],
        DSDieuChinhVT_Checked: [],
        listChungTuChecked: [],
        quytrinh_id: 0,
        huonggiao_id: 0,
        
      tag: "",
      Tag: "1",
      kiemtra_quyen: false,
      boimau: false,
      IN_BM_KYSO: false,
      qr_code: false,
      chonvattu_lohang: false,
      diachi_mac: false,
      b_mathung: false,
      chondvi_fmis : false,         
      // Danh mục loại kho
      LocVattu_checked : [],
      LOAI_KHO: {
        KHO_THUHOI: 15,
        KHO_BAOHANH: 6,
        KHO_TOTRUONG: 14,
        KHO_GUI_BAOHANH: 16,
        KHO_TRA_BAOHANH: 17,
        KHO_NHA_CC: 2,
        KHO_NV: 5,

        KHO_VATTU_SUACHUA: 530,
        KHO_VATTU_BAOHANH: 540,
      },
      // Danh mục quyền QLVT
      DS_QUYEN_VT: {
        HOANTHIEN_CHUNGTU: 1794,
        CAPNHAT_CHUNGTU: 1795,
        QUYENDUYET_VT: 2080,
        QUYENDUYET_TTVT: 2700,
        QUYENDUYET_TTCU: 2701,
        QUYENXUAT_KHO: 2081,
        QUYENLAYVT_DH: 2505,
        QUYET_DOIMACT: 3501,
        QUYET_XETDUYET: 3505,
      },
      // Danh mục loại kho
      MUCDICH_VT: {
        NHAP_BAOHANH: "1.4",
        XUAT_BAOHANH: "2.4",
        MUASAM: "N_MS",
        CHUYEN_CP: "2.XCP",
        KDV_DONGIA: "N.KDV_DG",
      },
      Enabled: {
        cboLoaiChungTu: false,

        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnXoa: false,
        tsbtnHuyBo: false,
        tsbtnHoanThanh: false,
        tstbtnDonHang: false,
        tsbtnThemVT: false,
        tsbtnSinhSr: false,
        tsbtnCopyCT: false,
        tsbtnDongBo: false,
        tsbtnBBGNNhap: false,
        tsbtnPhieuNhap: false,
        tsbtnPhieuXuat: false,
        tsbtnBBGNXuat: false,
        tsbtnPhieuChuyen: false,
        tstPhieuTUVT: false,
        tsbtnInXuatDVTC: false,


        gridViewVatTu: {
          SOLUONG: true
        },
        btnSeri_ChiaSL: false,
        btnTaoTbi: false,

        colTKDU: false,
        
        cboMa_DV_SD : false,
        cboIMS_CSHT : false,
        cboMa_DV_QL : false,
        cboHD_XuatIMS : false
      },
      ReadOnly: {
        CboVatTu: false,
        cboDenKho: false,
        cboTuKho: false,

      },

      Visible: {


        tsbtnNhapMoi: true,
        tsbtnXoa: true,
        tsbtnGhiLai: true,
        tsbtnHuyBo: true,
        tsbtnThemVT: true,
        tstbtnDonHang: true,
        tsbtnHoanThanh: true,
        tsbtnSinhSr: true,
        tsbtnCopyCT: true,
        tsbtnDongBo: true,
        tsbtnUpPXK: true,
        btnXoaHetVT: true,
        simpleButton1: true,

        ThongTinHDDA: true,
        colMac: false,
        colDGSua: false,
        colMaThung: false,        
        layoutbtnTBi: false, // visible btnChonTamUng
        layoutControlItem29: false,// visbile btnChuyenTT
        layoutControlItem13: false,// visible txtNDDK_ButtonClick

        layoutCtrlPhieuDN: false,
        layoutBtnChonPhieuDN: false,
        tsbtnDongBoIMS: false,
        layoutControlDuAn: false,
        layoutControlDS_Vattu: false,
        layoutControlDieuChinh: false,
        layoutControlBBBG: false,

        popupMa_PDA: false

      },

      Text: {
        label7: "NHẬP VẬT TƯ"
      },

      status: 0,
      kieu_td: 0,
      kho_nhan_goc: 0,
      vma_md: "",

      splitContainer1: {
        Panel2Collapsed: false
      },
      vtdk: [],
      check_ct: true,

      arrayImportFile: {
        filexcel:null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: []
      },
      SumChon1: 0,
      sl1: 0,
      vchungtu_id_temp: 0,
      sophieu_temp: "",
      SumChon: 0,
      sl0: 0,
      // ------------------ bổ sung code 12/12/2021  -------------------
      // checkData: 
      bbbgiao: [],
      duan_ims_clone: [],
      dieuchuyen_clone: [],
      sohoa_qlvt: false,
      sohoa_qlvt_dual: false,
      vattu_ims: false,
      batbuoc_chst: false,
      check_user_tao_chungtu: false, // LDG dùng đầu tiên
      doi_mucdich_chungtu: false,
      cboNghiepVu: [],
      cboTinh: [],
      cboMa_DV_SD: [],
      cboMa_DV_QL: [],
      
      cboHD_XuatIMS: [],
      cboMaPhieu: [],
      cboLocDCLoaiCT: [],
      cboLocDCDonVi: [],
      cboHDPOIMS: [],
      cboDuanIMS: [],
      gridDSMa_PDA: [],
      gridDieuChinhVT: [],
      gridDS_VT_THUHOI: [],
      grcMa_CSHT : [],

      tabSPDA: 1,


      // ------------------- -------------------

      filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true, },


      checkAllChungTu: false,
      colCheckboxAllChungTu: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllChungTu" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },            
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },

      colCheckboxChungTu: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.listChungTuChecked"
                                    :value="data.CHUNGTU_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },

      checkAllLVT: false,
      colCheckboxAllLVT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllLVT" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },            
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      cboLocVattu_checked : [],
      colCheckboxLVT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.cboLocVattu_checked"
                                    :value="data.vatTuID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      customTemplate: function() {
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },
      templateOptions : {
        create: function (args) {
          if(['STT','btnXoaVT','btnEditTbi'].includes(args.column.field)){
            return '<span class="fa input-icon-right"></span>'
          } 
          
          return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
        },
        write: function (args) {
        }
      },

      ChonPDATemplate: function () {
        return {
          template: Vue.component('ChonPDATemplate', {
              template: `<button class="btn btn-main pad4 lh14 w60 btn-primary"  @click="ChonPDA_ButtonClick">
                            <span class="act -ap icon-tick"></span>
                        </button>`,
              data: function () { return { data: {} }; },
              computed: {
                
              },
              methods:{
                async ChonPDA_ButtonClick(){
                  console.log(this.$parent.$parent.$parent , " = parent")
                  await this.$parent.$parent.$parent.$parent.$parent.btnChon_PDA_ButtonClick(this.data)
                }
              }

          })
        }
      },

      ChonBBBGTemplate: function () {
        return {
          template: Vue.component('ChonBBBGTemplate', {
              template: `<button class="btn btn-main pad4 lh14 w60 btn-primary"  @click="chonBBBG_ButtonClick">
                            <span class="act -ap icon-tick"></span>
                        </button>`,
              data: function () { return { data: {} }; },
              computed: {
                
              },
              methods:{
                async chonBBBG_ButtonClick(){
                  console.log(this.$parent.$parent.$parent , " = parent")
                  await this.$parent.$parent.$parent.$parent.$parent.btn_chonBBBG_ButtonClick(this.data)
                }
              }

          })
        }
      },
      btnTTDBTemplate: function () {
        return {
            template: Vue.component('btnTTDBTemplate', {
                template: `<div style="text-align:center">
                            <button class="btn btn-main lh14 pad3" @click="TraCuuTTDongBo" title="Tra cứu trạng thái đồng bộ">
                              <span class="act -ap  icon  one-search text-white"></span>  
                            </button>
                          </div>`,
                data: function () { return { data: {} }; },
                computed: {
                  
                },
                methods:{
                  async TraCuuTTDongBo(){
                    await this.$parent.$parent.$parent.TraCuuTTDongBo(this.data)
                  }
                }

            })
        }
      },
      btnTraCuuChungTuTemplate: function () {
        return {
            template: Vue.component('btnTraCuuChungTuTemplate', {
                template: `<div style="text-align:center">
                            <button class="btn btn-main lh14 pad3" @click="TraCuuChungTuDeNghi"  v-if="showBtn" title="Tra cứu chứng từ đề nghị">
                              <span class="act -ap  icon  one-search text-white"></span>  
                            </button>
                          </div>`,
                data: function () { return { data: {} }; },
                computed: {
                  showBtn: {
                    get(){                      
                      return this.data.MA_DN != null 
                    }
                  }
                },
                methods:{
                  async TraCuuChungTuDeNghi(){
                    await this.$parent.$parent.$parent.TraCuuChungTuDeNghi(this.data)
                  }
                }

            })
        }
      },
      cRowSelected: function() {
        return {
          template : Vue.component('columntemplate', {
            template : '<span class="fa"></span>'
          })  // end of template
        }
      },
      editSettings: { allowEditing: true, mode: 'Batch'},
      selectionSettings: {  type: 'Single', checkboxOnly: true },
      XoaVTTemplate: function () {
            return {
                template: Vue.component('XoaVTTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="gridviewVatTu_btnXoaVT">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async gridviewVatTu_btnXoaVT(){
                        await this.$parent.$parent.$parent.gridviewVatTu_btnXoaVT(this.data)
                      }

                    }
                })
            }
        },
      XoatbITemplate: function () {
            return {
                template: Vue.component('XoatbITemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoatbI_Click">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnXoatbI_Click(){
                        await this.$parent.$parent.$parent.gridViewThietBi_ShownEditor(this.data)
                      }

                    }
                })
            }
        },
     
      btnTBTemplate: function () {
            return {
                template: Vue.component('btnTBTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnTB" @click="btnTB_ButtonClick">
                                  <span class="act -ap icon-chevron-thin-down"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    computed: {
                      btnTB: {
                        get(){
                          if (parseInt(this.data["SPVT"] || 0) > 0 || parseInt(this.data["SPBH"] || 0) > 0){
                            return true
                          }
                          return false // true =>test
                        }
                      }
                    },
                    methods:{
                      async btnTB_ButtonClick(){
                        await this.$parent.$parent.$parent.btnTB_ButtonClick(this.data)
                      }
                    }

                })
            }
        },
      btnEdit_Template: function () {
        return {
          template: Vue.component('btnEdit_Template', {
            template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnEdit" @click="btnEdit_ButtonClick">
                          <span class="act -ap icon-chevron-thin-right"></span>
                      </button>`,
            data: function () { return { data: {} }; },
            computed: {
              btnEdit: {
                get(){
                  if ( this.data["LOAITBI_ID"] && this.data["LOAITBI_ID"].toString() == "3" ){
                    return true
                  }
                  return false // true =>test
                }
              }
            },
            methods:{
              async btnEdit_ButtonClick(){
                await this.$parent.$parent.$parent.gridviewVatTu_TachCap(this.data)
              }
            }

          })
        }
      },
        
      iconStatus: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<span :class="icon" v-bind:style="{ color: colorText }"></span>`,                        
            data () { 
              return { 
                data: {},
                icon: "",
                colorText: ""
              }; 
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            },
            created:async function () {                    
              if(this.data.STATUS == "0" || this.data.STATUS == "1") {
                this.icon = this.data.STATUS == "1" ? "act -ap  icon-tick f30" : "text-warning one-alert f20"
                this.colorText = this.data.STATUS == "1" ? "green" : "orange"
              }             
						},
            methods: {
              
            },
          })
        }
      },
      isTab1: true,
      gridChungTuPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
      gridTinhTrangPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
      gridVatTuPage : {
              page: 0,
              maxSize: 10,
              totalElement: 0,
              totalPages: 0,
              sort: null,
              propertiesSort: null,
              currentRow: 0
          },
      gridThietBiPage : {
              page: 0,
              maxSize: 10,
              totalElement: 0,
              totalPages: 0,
              sort: null,
              propertiesSort: null,
              currentRow: 0
          },

        
      showLocVT: false,
      selectedLocVT: "",
      // showDSVatTu: false,
      grcVT: [],
      animationSettings: { effect: 'Zoom' },

      showHopDong: false,
      selectedHopDong: '',
      gridviewChungTu_FocusedRow: {},
      gridViewThietBi_FocusedRow: {},
      gridViewVatTu_FocusedRow: {},
      DSTuKho: [],
      DSDenKho: [],
      cboDenKho_Temp: [],

      LOAIPHIEU_DENGHI_VATTU: {
        PHIEU_DANGKY_NHUCAU: 1,
        PHIEU_DENGHI_NHAPKHO_TT: 2,
        PHIEU_DENGHI_MUALE: 3,
        PHIEU_NHAPKHO: 4,
        PHIEU_DENGHI_CHUYENKHO: 5,
        PHIEU_CHUYENKHO: 6,
        PHIEU_DENGHI_XUATKHO: 7,
        PHIEU_XUATKHO: 8,
        PHIEU_DENGHI_QUYETTOAN: 9,
        PHIEU_DENGHI_THUHOI_TB: 10,
        PHIEU_DENGHI_THUHOI_CT: 11
      },
       tooltip: Tooltip

    };
  },
  methods: {
    ...mapActions("nhapxuatvt", actionName),
    ...mapMutations("nhapxuatvt", mutationName),


    onClose: function(e) {
      this.tooltip.close();
    },

    onBeforeRender : function(args) {
      this.tooltip.content = "" 
      this.tooltip.content = args.target.textContent;
      this.tooltip.dataBind();
      // var result = this.$refs.cboTuKho.dataSource;   
      // var resultDenKho = this.$refs.cboDenKho.dataSource;     
      // var resultMucDich = this.$refs.cboMucDich.dataSource;  
      // var resultNghiepVu = this.$refs.cboNghiepVu.dataSource; 
      

      // this.tooltip.content = ""  
      // // Từ kho   
      // for (var e of result) {        
      //   if (e.tenKho === args.target.textContent) {          
      //     this.tooltip.content = e.tenKho;
      //     this.tooltip.dataBind();          
      //     break;
      //   }
      // }
      
      // // Đến kho
      // for (var e of resultDenKho) {        
      //   if (e.tenKho === args.target.textContent) {          
      //     this.tooltip.content = e.tenKho;
      //     this.tooltip.dataBind();          
      //     break;
      //   }
      // }

      // // Mục đích
      // for (var e of resultMucDich) {            
      //   if (e.mucDich === args.target.textContent) {          
      //     this.tooltip.content = e.mucDich;
      //     this.tooltip.dataBind();          
      //     break;
      //   }
      // }

      // //Nghiệp vụ
      // for (var e of resultNghiepVu) {            
      //   if (e.mucDich === args.target.textContent) {          
      //     this.tooltip.content = e.mucDich;
      //     this.tooltip.dataBind();          
      //     break;
      //   }
      // }

    },
    onDropdownCreate:  function(args) {  
      this.tooltip = new Tooltip({
          content: '',      
          target: '.e-list-item',     
          position: 'top center',     
          beforeRender: this.onBeforeRender
      });
      this.tooltip.appendTo('body');
    },

    SUMGiaTriConLai() {
      var TongTienPB = 0
      this.gridVatTu.forEach(e => {
        TongTienPB += parseInt(e.TIEN)
      })
      return currency(TongTienPB);
    },
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    f_StringToDateTime(value,format='DD/MM/YYYY HH:mm:ss'){
      return moment(value,format).toDate();
    },
    async gridChungTuPageHandler(e) {
      this.gridChungTuPage.page = e.pageIndex
      this.gridChungTuPage.maxSize = e.pageSize
      await this.Load_ds_chungtu()
    },
    gridTinhTrangPageHandler(e) {
      this.gridChungTuPage.page = e.pageIndex
      this.gridChungTuPage.maxSize = e.pageSize
    },
    gridVatTuPageHandler(e) {
      this.gridVatTuPage.page = e.pageIndex
      this.gridVatTuPage.maxSize = e.pageSize
    },
    async gridThietBiPageHandler(e) {
      this.gridThietBiPage.page = e.pageIndex
      this.gridThietBiPage.maxSize = e.pageSize
      await this.Load_ds_thietbi()
    },
    grcVT_selectedRow(data){
      if (data){
        this.params.p_VatTu = data.VATTU_ID
      }
    },
    cboHopDong_selectedRow(data){
      if (data){
        this.params.p_HopDong = data.hdmsId
      }
    },
    async cboLocVattu_selected(data){
      if (data){
        this.selectedLocVT = data.tenVatTu
        this.params.p_LocVattu = data.vatTuID;
      }
    },
    // cboLocVattu_checked: function (dataItem) {  
    //   this.cboLocVattu_checked =  dataItem
      
    // },
    async Load_quyen(){
      this.dsQuyen = await this.getDSQuyenNguoiDung({nguoiDungId : this.$auth.getNguoiDungID()})
    },
    // Combo vật tư
    async LOAD_POPUP(){
      this.ds_loc_vattu = await this.getDSVatTu({vatTuId: 26})
      this.grcLocVattu = this.ds_loc_vattu
      this.cboLocVattu = this.ds_loc_vattu
      this.cboLocVattu_checked = []
      this.checkAllLVT = false
    },
    async Loc_Kho(){
      let loc_tukho = this.DSTuKho.filter(a=>a.trangThai == 1 && a.loaiKhoId != this.LOAI_KHO.KHO_NV.toString())
      if (this.params.p_ckbTuKho){
        if (this.DSTuKho.length && this.DSTuKho[0].hasOwnProperty('quyenKg') ){
          loc_tukho = loc_tukho.filter(b=>b.quyenKg == '1')
        }  
      }

      let loc_denkho = this.DSDenKho.filter(a=>a.trangThai == 1 &&  a.loaiKhoId != this.LOAI_KHO.KHO_NV.toString())
      if (this.params.p_ckbDenKho){
        if (this.DSDenKho.length && this.DSDenKho[0].hasOwnProperty('quyenKg') ){
          loc_denkho = loc_denkho.filter(b=>b.quyenKg == '1')
        }  
      }
   
      this.cboTuKho = loc_tukho
      
      this.cboDenKho = loc_denkho     
    },
    async Load_Cbo(){
      // ko hien thi nhap le

      if (this.tag == "3"){
        this.cboLoaiChungTu = await this.getDanhMucLoaiCT_V2({})
      }
      else{
        this.cboLoaiChungTu = await this.getDanhMucLoaiCT({})
      }
      this.cboLoaiChungTu.length ? this.params.p_LoaiChungTu= this.cboLoaiChungTu[0].loaiCtId : ''
    
      let DSAllKho = await this.getDanhMucKho({nhanVienId: this.$auth.getNhanVienID()})
      this.DSTuKho = DSAllKho
      this.DSDenKho = DSAllKho
      
      this.ngay_dh = null
   

      this.cboMucDich = await this.getDanhMucMucDich({nhomMdId: 3})
      
      this.ds_md = this.cboMucDich    

      this.cboCongTrinh = await this.getDSCongTrinh({})
     
      await this.Loc_Kho()

      // -------------  ------------------
      this.cboNghiepVu = await this.getDanhMucNghiepVu({nhomMdId: [20,21]})
      this.cboTinh = await this.getDSTinh()        

      this.cboMa_DV_SD = await this.getDSDonViSuDung({kieu: 0})     

      this.cboMa_DV_QL = await this.getDSDonViSuDung({kieu: 1})
      console.log(this.cboMa_DV_QL , " = this.cboMa_DV_QL")
      // ------------  ------------------

      this.params.p_VatTu = ""
   
      // this.ngay_dh = DateTime.MinValue
    },    
    async frmNhapXuatVT_Load(){
      //tham so so hoa vat tu
      this.cboDonVi_FMIS =  await this.getDSDonViFMIS()
      this.params.p_DonViFMIS = this.cboDonVi_FMIS.length > 0 ? this.cboDonVi_FMIS[0].MA_DV : ""
     
      let dsTS = await this.getDSThamSoMacDinhTheoMaTS({maTS: "DHSX_SOHOA_QLVT"});
     
      if (dsTS.length && dsTS[0]["TEN_TS"] == "1"){
        this.sohoa_qlvt = true
      }
          
      //cho phep chay song song, quy trình số hóa hoặc quy trình thông thường
      //
      if (dsTS.length && dsTS[0]["TEN_TS"] == "2"){
        this.sohoa_qlvt_dual = true;
      }
      // hiện khi ở HNI
      this.Visible.colDGSua = this.$auth.getMaTinh() == 'HNI'
    
      this.loading(true)
      await this.AN_GROUP(true)

      if (this.Tag.toString()){
          this.tag = this.Tag.toString();
      }

      if ((this.sohoa_qlvt || this.sohoa_qlvt_dual) && 
      (this.params.p_LoaiChungTu != null && (this.params.p_LoaiChungTu.toString() == "2" 
      || this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "99"))){
        this.Visible.layoutCtrlPhieuDN = this.Visible.layoutBtnChonPhieuDN = true;
      }
      else{
        this.Visible.layoutCtrlPhieuDN = this.Visible.layoutBtnChonPhieuDN = false;
      }

      await this.Load_quyen()
      await this.Load_Cbo()
      await this.Load_ds_chungtu()
      // if (this.gridChungTu.length > 0){      
      //   await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID)
      // }           
      
      await this.LOAD_POPUP()
      
      // Lấy TS mặc định
      this.Visible.colMac = false
      let ds = await this.getThamSoMacDinh({kieuId: 0})
      if (ds.length){
        if (this.setThamSo("KIEMTRA_QUYENXN",ds)){
          this.kiemtra_quyen = true
        }
        
        if (this.setThamSo("MAU_CHUNGTU_QLVT",ds)){
          this.boimau = true
        }

        // if (this.setThamSo("QLVT_MAC",ds)){
        //   this.diachi_mac = true
        //   this.Visible.colMac = tru  e
        // }
        if (this.setThamSo("QLVT_MATHUNG",ds)){
          this.b_mathung = true
        }
        if (this.setThamSo("KDV_SUA_DG",ds)){
          this.sua_dg_kdv = true
        }
        if (this.setThamSo("VATTU_IMS",ds)){
          this.vattu_ims = true
        }
        if (this.setThamSo("BATBUOC_CHST",ds)){
          this.batbuoc_chst = true
        }
        if (this.setThamSo("CHECK_USER_TAO_CHUNGTU",ds)){
          this.check_user_tao_chungtu = true
        }
        if (this.setThamSo("DOI_MUCDICH_CHUNGTU",ds)){
          this.doi_mucdich_chungtu = true
        }

        if (this.setThamSo("CHONDVI_FMIS",ds)){
          this.chondvi_fmis = true
        }
        if (this.setThamSo("IN_BM_KYSO",ds)){
          this.IN_BM_KYSO = true
        }
      }
     
      if (this.$auth.getMaTinh() == "HNI"){
        this.Visible.layoutControlItem13 = true
        this.ReadOnly.txtNDDK = true
        this.Visible.xtraTabPage3 = true
        this.Visible.layoutControlItem29 = true
        this.cbo_tinhtrang = [
          {TINHTRANG_ID: 0, TINHTRANG: ''},
          {TINHTRANG_ID: 1, TINHTRANG: 'Mới'},
          {TINHTRANG_ID: 2, TINHTRANG: 'Cũ - Hỏng'},
          {TINHTRANG_ID: 3, TINHTRANG: 'Cũ - Sử dụng được'},
        ]
        this.cboTinhTrang = this.cbo_tinhtrang
      }
      else{
        this.Visible.layoutControlItem13 = false
        this.Visible.xtraTabPage3 = false
        this.Visible.layoutControlItem29 = false
      }

  
      this.loading(false)
    },
    setThamSo(str_vtemp,ds){
      let det = ds.filter(a=>a.maTS == str_vtemp)         
      if (det.length){
        if (det[0].tenTS.trim() == "1"){
          return true
        }
      }
      return false
    },
    
    async Load_ds_chungtu(){
      // có điều kiên kieugoi_id == 1 nhưng func vẫn giống nhau nên xóa
      // var res = await this.getDSChungTu_V2({
      //   "tatCa": this.params.p_chkAll_Phieu ? 1 : 0,
      //   "donViId": this.$auth.getDonViID(),
      //   "nhanVienId": this.$auth.getNhanVienID(),
      //   "tuNgay": this.f_DateToString(this.params.p_dtpTuNgay,"DD/MM/yyyy"),
      //   "denNgay": this.f_DateToString(this.params.p_dtpDenNgay,"DD/MM/yyyy"),          
      //   "pageNum": this.gridChungTuPage.page,
      //   "pageSize": this.gridChungTuPage.maxSize,
      //   "totalRow": 1 // 1 ko phân trang server, 0 phân trang server
      // })

      var res = await this.getDSChungTu_V3({
        "tatCa": this.params.p_chkAll_Phieu ? 1 : 0,
        "donViId": this.$auth.getDonViID(),
        "nhanVienId": this.$auth.getNhanVienID(),
        "tuNgay": this.f_DateToString(this.params.p_dtpTuNgay,"DD/MM/yyyy"),
        "denNgay": this.f_DateToString(this.params.p_dtpDenNgay,"DD/MM/yyyy"),          
        "pageNum": this.gridChungTuPage.page,
        "pageSize": this.gridChungTuPage.maxSize,
        "totalRow": 1, // 1 ko phân trang server, 0 phân trang server
        chua_xacnhan: this.params.p_ckb_xacNhan ? 1 : 0
      })
      this.dtChungTu = []   
      var DSChungTu = []  
      if (Object.keys(res).length){   
        this.dtChungTu = res.data || []
        DSChungTu = res.data || []
        this.gridChungTuPage.page = res.page
        this.gridChungTuPage.maxSize = res.maxSize
        this.gridChungTuPage.totalElement = res.totalElement
        this.gridChungTuPage.totalPages = res.totalPages
      }

      if (this.dtChungTu.length){        
        this.SetButton(3)
      }
      else{      
        this.SetButton(1)
      }
      
      if (this.params.p_ckb_nd){
        if (this.dtChungTu.length){     
          var q = this.dtChungTu.filter(a=>a.NGUOI_CN.toString() == this.$auth.getUserName()) //ttnd.ma_nd
          if (q.length){
            DSChungTu = q
            this.gridChungTuPage.totalElement = q.length

            if (this.dtChungTu.length){
              await this.gridviewChungTu_FocusedRowChanged(this.dtChungTu[0])
              this.SetButton(3)
            }
            else{
              this.SetButton(1)
            }
          }
          else{
            DSChungTu = []
            this.SetButton(1)
          }
        }
      }
      this.gridChungTu = DSChungTu
      this.listChungTuChecked = [] 
    },
    async Load_ds_vattu(){
      if(this.vchungtu_id == null || this.vchungtu_id == ""){
        return
      }
      this.dtVatTu = await this.getDSVatTuChungTu({chungTuId: this.vchungtu_id})      
      this.gridVatTu = this.dtVatTu
      this.gridVatTu_DataSourceChanged()
    },
    async LAY_DS_VTCT_SERIAL(){
      if(this.vchungtu_id == null || this.vchungtu_id == ""){
        return
      }
      let ds = await this.getDSVatTuChungTuSerial({chungTuId: this.vchungtu_id})
      if (ds.length && this.Enabled.tsbtnNhapMoi && this.Enabled.tsbtnGhiLai){
        this.grcVT = ds
        if (this.ttph_id.toString() == "4"){
          this.ReadOnly.CboVatTu = true
        }
        else{
          this.ReadOnly.CboVatTu = false
        }
      }
      else if (!this.Enabled.tsbtnNhapMoi || (!ds.length && this.Enabled.tsbtnNhapMoi && this.Enabled.tsbtnGhiLai)){
        this.ReadOnly.CboVatTu = true
        this.params.p_VatTu = ""
      }
    },
    async Load_ds_thietbi(){
      if(this.vchungtu_id == null || this.vchungtu_id == ""){
        return
      }
      let res = await this.getDSThietBiChungTu({chungTuId: this.vchungtu_id})
      // gridViewThietBi.ClearColumnsFilter();
      this.dtThietBi = res.data || []
      this.gridThietBiPage.page = res.page
      this.gridThietBiPage.maxSize = res.maxSize
      this.gridThietBiPage.totalElement = res.totalElement
      this.gridThietBiPage.totalPages = res.totalPages

      this.gridThietBi = this.dtThietBi
      this.gridThietBi_ConvertData()
      if (this.dtThietBi.length && this.dtThietBi[0].hasOwnProperty('MATHUNG')){
        this.Visible.colMaThung = true
      }
      else{
        this.Visible.colMaThung = false
      }
    },
    async load_tinhtrang(){
      var dttemp = this.kieu == 0 ? await this.getDSThietBiChungTu({chungTuId: this.vchungtu_id}) : this.dtThietBi
      if (dttemp.length && dttemp[0].hasOwnProperty('TINHTRANG')){
        dttemp.forEach(a=>{
          if (a.TINHTRANG == null || !a.TINHTRANG.toString()){
            a.TINHTRANG = 0
          }
        })
      }
      this.gridTinhTrang = dttemp
    },
 
    async  gridviewChungTu_FocusedRowChanged_sender(row){   
      try{  
        this.loading(true)
        this.t_kho_id = 0
        this.t_kho = ""
        this.Visible.layoutbtnTBi = false
        if (row){
          await this.gridviewChungTu_FocusedRowChanged(row)
        }
        else{
          this.Visible.tsbtnTraLai = false
          this.SetButton(1)
          this.Clear()
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },
    async gridviewChungTu_FocusedRowChanged(row){
      console.log(row , " = row")
      this.gridviewChungTu_FocusedRow = row
      this.Visible.tsbtnTraLai = false
      this.kho_nhan_goc = 0
      this.vchungtu_id = row.CHUNGTU_ID == null ? 0 : parseInt(row.CHUNGTU_ID)
      this.dtLuuVT = await this.getDSVatTuChungTuV1({chungTuId: this.vchungtu_id})
      this.dtVatTuXuat = this.dtLuuVT

      this.ttph_id = parseInt(row.TTPH_ID)
      // ------------------ ----------------------
      this.params.p_txtPhieuDeNghi = row.MA_DN || ""

      // ------------------ ----------------------
      this.params.p_txtLenhDieuChuyen = row.LENH_CHUYEN 
      this.params.p_LenhChuyenId = row.LENHCHUYEN_ID 
      this.kieunhap = 0
      this.params.p_txtSoPhieu = row.SOPHIEU == null ? "" :  row.SOPHIEU.toString()
      this.params.p_txtNoiDung = row.ND_GIAO == null ? "" : row.ND_GIAO.toString()
      this.kieu_td = row.KIEU == null ? "" : row.KIEU.toString()

      this.params.p_CongTrinh =  ""
      if ( this.dtChungTu.length && this.dtChungTu[0].hasOwnProperty("CONGTRINH_ID")){
        this.params.p_CongTrinh = row.CONGTRINH_ID
      }
      
      if(row.LOAI == "1"){
        this.params.p_LoaiChungTu = row.LOAICT_ID == null ? 0 : parseInt(row.LOAICT_ID)
      }
      else{
        this.params.p_LoaiChungTu = 99
      }

      if (this.$auth.getMaTinh() == 'HNI'){
        this.params.p_txtNDDK = row.NDDK
      }

      this.params.p_MucDich = row.MUCDICH_ID == null ? 0 : parseInt(row.MUCDICH_ID)

      var d_s = await this.getThongTinNgayDH({chungTuId: this.vchungtu_id})
      if(d_s.length){
        this.ngay_dh = new Date(d_s[0].NgayNH)
      }
      var d_sngay = await this.getThongTinNgayXuat({chungTuId: this.vchungtu_id})
      if (d_sngay.length){
        this.params.p_dtpNgayGuiTT = ""
        if (d_sngay[0].ngayGuiTT && d_sngay[0].ngayGuiTT.toString()){
          console.log(d_sngay[0].ngayGuiTT , " = ngayGuiTT")
          this.params.p_dtpNgayGuiTT = this.f_StringToDate(d_sngay[0].ngayGuiTT,'DD-MM-YYYY HH:mm:ss')
        }

        this.params.p_dtpNgayNhanTT = ""
        if (d_sngay[0].ngayNhanTT && d_sngay[0].ngayNhanTT.toString()){
          if (this.kiemtra_quyen){
            this.Visible.tsbtnTraLai = true
          }
          this.params.p_dtpNgayNhanTT = this.f_StringToDate(d_sngay[0].ngayNhanTT,'DD-MM-YYYY HH:mm:ss')
        }

      }
      this.vma_md = ""
      this.dc_ct_noibo= false

      var  d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
      if (d.length){
        this.vma_md = d[0].toString()
      }
      if (this.vma_md == "1.NBH"){
        this.splitContainer1.Panel2Collapsed = false
      }
      else if (this.vma_md == "DC_CT" && (this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2")){
        this.dc_ct_noibo = true
        this.Visible.layoutbtnTBi = true
      }
      else{
        this.splitContainer1.Panel2Collapsed = true
      }
      
      this.params.p_TuKho = row.KHO_GIAO_ID == null ? "" : row.KHO_GIAO_ID

      if(row.STATUS != null && row.STATUS.toString() == "1"){
        this.status = 1
      }else{
        this.status = 0
      }

      this.t_kho_id = row.KHO_GIAO_ID == null ? 0 : parseInt(row.KHO_GIAO_ID)
      this.t_kho = row.KHO_GIAO_ID == null ? "" : row.KHO_GIAO.toString()

      this.params.p_DenKho = row.KHO_NHAN_ID == null ? "" : parseInt(row.KHO_NHAN_ID)
      if(row.KHO_NHAN_ID != null && row.KHO_NHAN_ID.toString()){
        this.kho_nhan_goc = parseInt(row.KHO_NHAN_ID)
      }

      this.params.p_dtpNgayDK = this.f_StringToDate(row.NGAY_CT,'DD-MM-YYYY HH:mm:ss')
      let kt_kieu = await this.getKieuChungTu({chungTuId: this.vchungtu_id})
      this.Enabled.gridViewVatTu.SOLUONG = true
      if(kt_kieu.length && parseInt(kt_kieu[0]["COUNT(CTCT.CTCT_ID)"])){
        this.Enabled.gridViewVatTu.SOLUONG = false
      }

      await this.Load_ds_vattu()
      await this.Load_ds_thietbi()
      await this.load_tinhtrang(1)

      this.params.p_txtNoiDung = row.ND_GIAO == null ? "" : row.ND_GIAO.toString()
      
      this.SetButton(3)
      await this.LAY_DS_VTCT_SERIAL()

      this.Visible.colDGSua = false
      if (this.sua_dg_kdv){
        if (this.vma_md == "N.KDV_DG"){
          this.Visible.colDGSua = true
        }
      }


      if (this.Enabled.tsbtnNhapMoi && this.params.p_LoaiChungTu.toString() == "99"){
        await this.AN_GROUP(false)
      }
      else{
        await this.AN_GROUP(true)
      }

      // ---------- ---------------
      if (this.nghiepvu_id_chungtu == "292" || this.nghiepvu_id_chungtu == "293" || this.nghiepvu_id_chungtu == "294"){
        this.Visible.tsbtnDongBoIMS = true;
      }
      else{
        this.Visible.tsbtnDongBoIMS = false;
      }
      this.params.p_NghiepVu = parseInt(this.nghiepvu_id_chungtu)
      this.params.p_Ma_PDA = this.ma_pda_chungtu
      this.params.p_IMS_CSHT = row.MA_CSHT || ""

      this.params.p_Ma_DV_SD = row.MA_DV_SD || ""
      var Object_Ma_DV_SD = this.cboMa_DV_SD.filter(a=> a.MA_DV_SD == this.params.p_Ma_DV_SD)
      this.params.text_Ma_DV_SD = Object_Ma_DV_SD.length > 0 ? Object_Ma_DV_SD[0].TEN_DV : ""

      this.params.p_Ma_DV_QL = row.MA_DV_QL || ""  
      var Object_Ma_DV_QL = this.cboMa_DV_QL.filter(a=> a.MA_DV_QL == this.params.p_Ma_DV_QL)
      this.params.text_Ma_DV_QL = Object_Ma_DV_QL.length > 0 ? Object_Ma_DV_QL[0].TEN_DV : ""

      this.params.p_HD_XuatIMS = row.CONTRACT_INFO_ID || ""
      // ---------- ---------------
      await this.load_ds_file_cv()
    },
    async load_ds_file_cv(){
      this.DSFileCV = []
      let DSFileResponse = await this.getDSFileChungTu(this.vchungtu_id)  
      for(let e of DSFileResponse){
        if(e != null){          
          if(e.FILE_CV.includes(URL_PSIS)){
            let rowFile = e.FILE_CV.split(URL_PSIS)
            this.DSFileCV .push({
              linkFile: e.FILE_CV,
              nameFile: rowFile[rowFile.length - 1],
            }); 
          }else{
            let rowFile = e.FILE_CV.split("|")
            rowFile.forEach((element) => {
              if (element != "") {
                var urlSplit = element.split("/");        
                this.DSFileCV .push({
                  linkFile: element,
                  nameFile: urlSplit[urlSplit.length - 1],
                });            
              }
            });     
          }            
        }
      }
    },
    async onClickDeleteFile(item) {
      try{
        if(this.vchungtu_id == 0 || this.vchungtu_id == null || this.vchungtu_id == "" ){
          this.$toast.error("Chưa chọn chứng từ!")
          return
        }  
        if(this.kho_nhan_goc == 7070 && this.vma_md == 'N_MS'){
          this.$toast.error("Chứng từ giao hàng từ PSIS không thể xóa file CV !")
          return
        }
        let kiemTraXoa = await this.kiemTraXoaFileChungTu({
          chungTuId: this.vchungtu_id,
          kieu: 1,
          fileCV: item.linkFile
        })
        if(kiemTraXoa != "1"){
          this.$toast.error(kiemTraXoa)
          return
        }
        this.loading(true);
        let mucDich = await this.getMaMucDichTheoID({mucDichId: this.gridviewChungTu_FocusedRow.MUCDICH_ID})
       
        if(mucDich.length == 0 || mucDich[0].toString() == null || mucDich[0].toString() == ""){
          this.$toast.error("Không lấy được mã mục đích")
          return
        }

        
        var deleteFile = await this.deleteFile({ fileSource: item.linkFile });
        var listFile = [];
        this.DSFileCV.forEach((e) => {
          if (item.linkFile != e.linkFile) {
            listFile.push(e.linkFile);
          }
        });
        let ketQua =  await this.uploadFileCVChungTu({
          loaiChungTu: this.gridviewChungTu_FocusedRow.LOAICT_ID,
          mdSpecial: mucDich[0].toString(),
          chungTuId: this.vchungtu_id,
          result: listFile.join('|'),
          nguoiCn: '',
          mayCn: '',
          ipCn: ''
        })
        await this.load_ds_file_cv()
        this.$toast.success("Xóa file thành công!");
        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
      
    },
    async onClickDownFile(item) { 
      try{     
        if(item) {  
          let URL = ""           
          if(item.linkFile.includes(URL_PSIS)){
            URL = item.linkFile
          }else{
            var checkFile = await this.checkFileExist({fileSource : item.linkFile})        
          
            if(checkFile.message == "Success" && checkFile.data.data){
              let getLink = await this.getLinkFile({fileSource : item.linkFile}) 
              URL = getLink.data.name                 
            }else{  
              this.$toast.error("File không tồn tại trên hệ thống!")           
              return  
            }
          }    
          
          const downloadLink = document.createElement("a");      
          downloadLink.href = URL;       
          downloadLink.download = item.fileName;    
          downloadLink.target = "_blank";
          downloadLink.click();   
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async chooseFile() {
      try{        
        if(this.vchungtu_id == 0 || this.vchungtu_id == null || this.vchungtu_id == ""){
          this.$toast.error("Chưa chọn chứng từ!")
          return
        }  
        if(this.kho_nhan_goc == 7070 && this.vma_md == 'N_MS'){ //PSIS Kho nhận : MSCP_KHOCHUNG  và Mã Mục đích: N_MS
          this.$toast.error("Chứng từ giao hàng từ PSIS không thể thêm file CV!")
          return
        }
        let kiemTraXoa = await this.kiemTraXoaFileChungTu({
          chungTuId: this.vchungtu_id,
          kieu: 2,
          fileCV: ""
        })
        if(kiemTraXoa != "1"){
          this.$toast.error(kiemTraXoa)
          return
        }
        this.$refs["ref-choose-file"].$el.querySelector("input").click();
      }catch(e){
        this.$toast.error(e.data.message)
      }
    },
    async onChangeFileVB(){
      try{
        this.loading(false);
        let fileChooser = this.$refs["ref-choose-file"];
        let choosedFiles = fileChooser.$el.querySelector("input").files;
        if (choosedFiles.length == 0) {
          fileChooser.reset();
          return;
        }
        choosedFiles = [...choosedFiles];
        fileChooser.reset();

      
        let formData = new FormData();
        for (let i = 0; i < choosedFiles.length; i++) {
          
          let item = choosedFiles[i];
          let CheckFile = this.DSFileCV.filter(e=> e.nameFile.toLowerCase() == item.name.toLowerCase())
          if(CheckFile.length > 0){
            this.$toast.error("Tên file đã tồn tại trong đơn hàng này!")
            return
          }          
          formData.append("file", item);
        }        
        
        let mucDich = await this.getMaMucDichTheoID({mucDichId: this.gridviewChungTu_FocusedRow.MUCDICH_ID})
       
        if(mucDich.length == 0 || mucDich[0].toString() == null || mucDich[0].toString() == ""){
          this.$toast.error("Không lấy được mã mục đích")
          return
        }

        let paths = await this.uploadFile(formData);

        if (paths.length == 0) {
          this.$toast.error("Upload file thất bại!");
          this.loading(false);
          return;
        }
        console.log(paths , " = paths")
        for (let i = 0; i < paths.length; i++) {
          if (paths[i].status) {
            var urlSplit = paths[i].message.relativeUrl.split("/");
            this.DSFileCV.push({
              linkFile: paths[i].message.relativeUrl,
              nameFile: urlSplit[urlSplit.length - 1],
            });
          }
        }
        let listFile = []
        this.DSFileCV.forEach((e) => {
          listFile.push(e.linkFile)
        })
        let ketQua =  await this.uploadFileCVChungTu({
          loaiChungTu: this.gridviewChungTu_FocusedRow.LOAICT_ID,
          mdSpecial: mucDich[0].toString(),
          chungTuId: this.vchungtu_id,
          result: listFile.join('|'),
          nguoiCn: '',
          mayCn: '',
          ipCn: ''
        })
        await this.load_ds_file_cv()
        this.$toast.success("Upload file thành công!");        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },
    async SetButton(kieu){
      this.copyct = false
      this.Enabled.tsbtnNhapMoi = false
      this.Enabled.tsbtnGhiLai = false
      this.Enabled.tsbtnXoa = false
      this.Enabled.tsbtnHuyBo = false
      this.Enabled.tsbtnHoanThanh = false
      this.Enabled.tstbtnDonHang = false
      this.Enabled.tsbtnThemVT = false
      this.Enabled.tsbtnSinhSr = false
      this.Enabled.tsbtnCopyCT = false
      this.Enabled.tsbtnDongBo = false

      if (kieu == -1){//Bat dau
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true
      }
      //Bat dau
      if (kieu == 0){
        this.$refs.txtSoPhieu ? this.$refs.txtSoPhieu.focus() : ''
        this.Enabled.tsbtnNhapMoi = true
        this.Clear()
      }
      //Them moi
      if (kieu == 1){
        this.vchungtu_id = 0
        this.$refs.txtSoPhieu ? this.$refs.txtSoPhieu.focus() : ''
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true
        this.Enabled.tstbtnDonHang = true
        this.Enabled.tsbtnThemVT = true
        this.Clear()
        this.dtVatTu = []
        this.dtThietBi = []
        this.ttph_id = 1
        this.ReadOnly.CboVatTu = true
        this.ngay_dh = null
      }
      //Huy
      if (kieu == 2){
        this.Enabled.tsbtnNhapMoi = true
        this.Enabled.tsbtnXoa = true
        this.Clear()
      }
      //Edit
      if (kieu == 3){
        this.Enabled.tsbtnNhapMoi = true
        this.Enabled.tsbtnXoa = true
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true

        this.Enabled.tsbtnThemVT = (this.ttph_id != 4)
        this.Enabled.tstbtnDonHang = (this.params.p_LoaiChungTu && this.params.p_LoaiChungTu.toString() == "1" && (this.ttph_id != 4))
        // ----------  -------------
       
        var vchungtu_id = this.vchungtu_id == null || this.vchungtu_id == "" ? 0 : this.vchungtu_id // đoạn này tránh truyền rỗng lên API
        let kt_phieu_qt = await this.KT_PHIEU_QT({chungTuId: vchungtu_id})
        
        if (this.sohoa_qlvt && this.ttph_id != 17
            && ((kt_phieu_qt != "1"
            && (this.params.p_LoaiChungTu == "2" || this.params.p_LoaiChungTu == "3" || this.params.p_LoaiChungTu == "99"))
            || (this.params.p_LoaiChungTu == "2" && kt_phieu_qt == "1"))){
            this.Enabled.tsbtnHoanThanh = false;
        }
        else{
          if (this.sohoa_qlvt_dual && this.ttph_id != 17 && ((this.params.p_txtPhieuDeNghi != null && this.params.p_txtPhieuDeNghi != ""
              && kt_phieu_qt != "1"
              && (this.params.p_LoaiChungTu == "2" || this.params.p_LoaiChungTu == "3" || this.params.p_LoaiChungTu == "99"))
              || (this.params.p_LoaiChungTu == "2" && kt_phieu_qt == "1"))
              ){
                this.Enabled.tsbtnHoanThanh = false;
              }
            else{
              this.Enabled.tsbtnHoanThanh = (this.ttph_id != 4)
            }
        }
        // ----------  -------------
        // this.Enabled.tsbtnHoanThanh = (this.ttph_id != 4)

        this.Enabled.tsbtnSinhSr = true
        this.Enabled.tsbtnCopyCT = true
        this.Enabled.tsbtnDongBo = true
      }

      // Load quyền
      this.Visible.tsbtnNhapMoi = false
      this.Visible.tsbtnXoa = false
      this.Visible.tsbtnGhiLai = false
      this.Visible.tsbtnHuyBo = false
      this.Visible.tsbtnThemVT = false
      this.Visible.tstbtnDonHang = false
      this.Visible.tsbtnHoanThanh = false
      this.Visible.tsbtnSinhSr = false
      this.Visible.tsbtnCopyCT = false
      this.Visible.tsbtnDongBo = false // quyen dong bo
      this.Visible.btnXoaHetVT = false
      this.Visible.simpleButton1 = false

      if (this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU)){
        this.Visible.tsbtnNhapMoi = true
        this.Visible.tsbtnXoa = true
        this.Visible.tsbtnGhiLai = true
        this.Visible.tsbtnHuyBo = true
        this.Visible.tsbtnThemVT = true
        this.Visible.tstbtnDonHang = true
        this.Visible.tsbtnCopyCT = true
        this.Visible.tsbtnDongBo = true
        this.Visible.btnXoaHetVT = true
      }
      if (this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
        this.Visible.tsbtnHoanThanh = true
        this.Visible.tsbtnSinhSr = true
        this.Visible.simpleButton1 = true
      }
    },
    Clear(){
      
      this.dc_ct_noibo = false

      this.params.p_txtSoPhieu = ""
      this.params.p_LoaiChungTu = null
      this.params.p_MucDich = null
      this.params.p_TuKho = null
      this.params.p_DenKho = null
      this.params.p_txtNoiDung = ""
      this.params.p_dtpNgayDK = new Date()
      
      this.gridVatTu = []
      this.gridVatTu_DataSourceChanged()
      this.gridThietBi = []
      this.dtLuuVT = []

      this.copyct = false
      this.params.p_dtpNgayGuiTT = ""
      this.params.p_dtpNgayNhanTT = ""
      this.params.p_txtNDDK = ""
      this.DSFileCV = []
      this.vma_md = ""
      this.gridTinhTrang = []
      this.Visible.layoutbtnTBi = false
      // btnChonTamUng
      // --------- ---------
      this.params.p_txtPhieuDeNghi = "";
      this.params.p_Ma_PDA = "";
      this.params.p_NghiepVu = -1;
      // --------- ---------

      this.params.p_txtLenhDieuChuyen = ""
      this.params.p_LenhChuyenId = null
    },
    async CapNhatChiTiet_CT(themmoi){
      if (this.kieunhap.toString() == "0" && this.kieugoi_id.toString() != "2"){
        await this.TaoDuLieu_CT()
        // Tạo func để covert variable phù hợp API
        
        await this.InsertUpdateChiTietChungTu(this.chitietCTData_insert)
        
        await this.InsertUpdateChiTietChungTu(this.chitietCTData_update)
        
        let vkden_id = !this.params.p_DenKho.toString() ? 0 : parseInt(this.params.p_DenKho)
        if (!themmoi){
          await this.CapNhatDuLieuChungTuTBi({
            khoDenId: vkden_id,
            chungTuId: this.vchungtu_id
          })
        }
      }

      // Nhập từ đơn hàng
      if (this.kieunhap.toString() == "1" && this.kieugoi_id.toString() != "2"){
        await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
        this.dtDonHang.forEach(async (item)=>{
          let body = {
            "chungTuId": this.vchungtu_id,
            "ctdhId": item.CTDH_ID,
            "loHang": item.LOHANG.toString() || '',
            "nguoiCn": this.$auth.getUserName(),
            "mayCn": this.$auth.getUserName(),
            "ipCn": "10.59.90.123"//ttnd.ip
          }
          
          await this.CapNhatDonHangChungTu(body)
        })
      }
      // Nhập từ kho
      if ((this.kieunhap.toString() == "2" && this.kieugoi_id.toString() != "2") || this.dc_ct_noibo){
        if (!this.copyct){
          await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
          this.dtThemVT.forEach(async (item)=>{
            await this.CapNhatVatTuChungTu({
              "khoId": this.params.p_TuKho,
              "vatTuId": item.VATTU_ID,
              "soLuong": item.SOLUONG,
              "allVatTu": item.TATCA,
              "chungTuId": this.vchungtu_id,
              "loHang": item.LOHANG,
              "nguoiCn": this.$auth.getUserName(),
              "mayCn": this.$auth.getUserName(),
              "ipCn": "10.59.90.123"//ttnd.ip
            })
            if (item.hasOwnProperty('TRANGTHAI')){
              let state = item["TRANGTHAI"] == "" ? "" : (item["TRANGTHAI"].toString() == "Đã có đầu vào" ? "1" : "0")
              console.log('====> TRANG THAI', item["TRANGTHAI"])
              await this.CapNhatChiTietChungTu({
                "trangThai": state,
                "vatTuId": item["VATTU_ID"],
                "chungTuId": this.vchungtu_id,
                "loHang": item["LOHANG"]
              })
            }
          })
        }
        else{
          await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
          this.dtCopyCTCT.forEach(async (item)=>{
            await this.CapNhatVatTuChungTu({
              "khoId": this.params.p_TuKho,
              "vatTuId": item.VATTU_ID,
              "soLuong": item.SOLUONG,
              "allVatTu": 0,
              "chungTuId": this.vchungtu_id,
              "loHang": item.LOHANG,
              "nguoiCn": this.$auth.getUserName(),
              "mayCn": this.$auth.getUserName(),
              "ipCn": "10.59.90.123"//ttnd.ip
            })
          })
        }
      }
      // Nhập lẻ
      if (this.kieugoi_id.toString() == "2"){
        if (this.kieunhap.toString() == "3"){
          await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
          this.dtVT_LE.forEach(async (item)=>{
            await this.CapNhatVatTuMuaLe({
              "vatTuId": parseInt(item["VATTU_ID"]),
              "chungTuId": this.vchungtu_id,
              "loHang": item["LOHANG"] || "",
              "donGia": parseFloat(item["DONGIA"]),
              "tien": parseInt(item["TIEN"]),
              "soLuong": parseFloat(item["SOLUONG"]),
              "hanBH": item["HAN_BH"] || "",
              "nguoiCn": this.$auth.getUserName(),
              "mayCn": this.$auth.getUserName(),
              "ipCn": "10.59.90.123"//ttnd.ip
            })
          })
        }
        else{
          await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
          this.dtVatTu.forEach(async (item)=>{
            await this.CapNhatVatTuMuaLe({
              "vatTuId": parseInt(item["VATTU_ID"]),
              "chungTuId": this.vchungtu_id,
              "loHang": item["LOHANG"] || "",
              "donGia": parseFloat(item["DONGIA"]),
              "tien": parseInt(item["TIEN"]),
              "soLuong": parseFloat(item["SOLUONG"]),
              "hanBH": item["HAN_BH"] || "",
              "nguoiCn": this.$auth.getUserName(),
              "mayCn": this.$auth.getUserName(),
              "ipCn": "10.59.90.123"//ttnd.ip
            })
          })
        }
      }
    },
    async tsbtnThoat_Click(){
      // Close()
    },
    async tsbtnNhapMoi_Click(){
      this.SetButton(1)
    },

    async KiemTraGhiLai(){
      if (this.sohoa_qlvt && (this.params.p_LoaiChungTu == "2" || this.params.p_LoaiChungTu == "3" || this.params.p_LoaiChungTu == "99")){
        if (this.params.p_txtPhieuDeNghi == null || this.params.p_txtPhieuDeNghi == "") { 
          this.$toast.error("Chưa nhập phiếu đề nghị"); 
          return false; 
        }
      }
      // Kiểm tra dữ liệu
      if (!this.Enabled.tsbtnNhapMoi){ // Nhập mới

        this.params.p_dtpDenNgay = this.params.p_dtpNgayDK
        this.params.p_dtpTuNgay = new Date(this.params.p_dtpNgayDK.getTime() - 7*24*60*60*1000)
        if (! await this.KiemTraDuLieu(true, false, "GHILAI")){ 
          return false
        }        
        if (this.kiemtra_quyen && !await this.KIEMTRA_QUYEN_XN(5,this.params.p_TuKho) ){
          return false
        }

      }else{ // Cập nhật

        if (! await this.KiemTraDuLieu(false, false, "GHILAI")){ 
          return false
        }          
        if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)){ 
          return false
        }
        if (this.kiemtra_quyen) {

          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){
             return false
          }

          if (this.params.p_TuKho.toString() != this.t_kho_id.toString() &&  ! await this.KIEMTRA_QUYEN_XN(5, parseInt(this.params.p_TuKho))){            
            return  false          
          }
        }        
      }
      return true
    },
    async tsbtnGhiLai_Click(){
      try{        
        let KiemTraGhiLai = await this.KiemTraGhiLai()
        if(!KiemTraGhiLai){
          return
        }

        let ds_chitiet = this.TaoDSChiTiet()
        let ds_dangky = []
        for(let e of this.vtdk){
          ds_dangky.push({
            vtdk_id : e.VTDK_ID,
            sl_duyet : e.SL_DUYET
          })
        }
        if(ds_chitiet.length == 0){
          this.$toast.error("Chưa chọn danh sách vật tư!")
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn ghi lại!", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true)

       
        
        let ketQua = await this.ghiLaiAllInOne({
          chungtu_id: this.Enabled.tsbtnNhapMoi ? this.vchungtu_id : null,           
          loaict_id: this.params.p_LoaiChungTu, 
          mucdich_id:this.params.p_MucDich, 
          loai: this._vloai, 
          kho_giao_id: this.params.p_TuKho, 
          kho_nhan_id: this.params.p_DenKho,           
          ngay_ct: this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss'), 
          nd_giao: this.params.p_txtNoiDung, 
          congtrinh_id: this.params.p_CongTrinh, 
          ma_pda: this.params.p_Ma_PDA, 
          nghiepvu_id: this.params.p_NghiepVu, 
          ma_dv_sd: this.params.p_Ma_DV_SD, 
          ma_csht: this.params.p_IMS_CSHT, 
          ma_dv_ql: this.params.p_Ma_DV_QL, 
          ma_hd_ims: this.params.text_HD_XuatIMS, 
          hd_id_ims: this.params.p_HD_XuatIMS,
          kieugoi_id: this.kieugoi_id,
          kieunhap: this.kieunhap,
          phieu_denghi: this.params.p_txtPhieuDeNghi,
          ds_chitiet: ds_chitiet,
          ds_dangky: ds_dangky.length > 0 ? ds_dangky : [],
          sendsms: this.params.p_ckbSendSMS ? 1 : 0,
          luanchuyen_id : this.params.p_LenhChuyenId,
        }) 
        if("CHUNGTU_ID" in ketQua){       
          let chungTuFocus = this.Enabled.tsbtnNhapMoi ? this.vchungtu_id : ketQua.CHUNGTU_ID   
          console.log(chungTuFocus, " = chungTuFocus")
          this.$toast.success("Cập nhật thành công")  
          await this.Load_ds_chungtu()
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", chungTuFocus)
          this.copyct = false
        }else{
          this.$toast.error("Có lỗi xảy ra khi ghi lại, liên hệ admin xử lý!")  
        }
          
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    TaoDSChiTiet(){
      let ds_chitiet = []    
      if(this.kieunhap == 0 && this.kieugoi_id != 2){

        for(let e of this.dtVatTu){
          ds_chitiet.push({
            ctct_id: e.CTCT_ID,
            vattu_id: e.VATTU_ID,
            lohang: e.LOHANG,
            soluong: e.SOLUONG,
            dongia: e.DONGIA,
            tien: e.TIEN,
            vat: e.VAT,
            sothang_pb: e.SOTHANG_PB
          })
        }        
      }
      if(this.kieunhap == 1 && this.kieugoi_id != 2){ // Nhâp từ đơn hàng

        for(let e of this.dtDonHang){
          ds_chitiet.push({
            ctdh_id: e.CTDH_ID,
            lohang: e.LOHANG             
          })
        }

      }
      if(this.kieunhap == 2 && this.kieugoi_id != 2 || this.dc_ct_noibo){  // Nhập từ kho
        if(!this.copyct){

          for(let e of this.dtThemVT){
            ds_chitiet.push({
              vattu_id: e.VATTU_ID,
              lohang: e.LOHANG,  
              kho_id: this.params.p_TuKho,
              soluong: e.SOLUONG,
              tatca: e.TATCA,
              trangthai : "TRANGTHAI" in e ? e.TRANGTHAI : "" // có cột thì truyền k thì truyền rỗng          
            })
          }
        }else{

          for(let e of this.dtCopyCTCT){
            ds_chitiet.push({
              vattu_id: e.VATTU_ID,
              lohang: e.LOHANG,  
              kho_id: this.params.p_TuKho,
              soluong: e.SOLUONG,
              tatca: 0                     
            })
          }
        }
      }
      if(this.kieugoi_id == 2){  // Nhập lẻ
        if(this.kieunhap == 3){

          for(let e of this.dtVT_LE){
            ds_chitiet.push({
              vattu_id: e.VATTU_ID,
              lohang: e.LOHANG,  
              dongia: e.DONGIA,
              soluong: e.SOLUONG,
              tien: e.TIEN,
              han_bh : e.HAN_BH                     
            })
          }
        }else{

          for(let e of this.dtVatTu){
            ds_chitiet.push({
              vattu_id: e.VATTU_ID,
              lohang: e.LOHANG,  
              dongia: e.DONGIA,
              soluong: e.SOLUONG,
              tien: e.TIEN,
              han_bh : e.HAN_BH                      
            })
          }
        }
      }
      return ds_chitiet
    },
    async tsbtnGhiLai_Click_OLD(){
      try{
        // ------- -------
        //pnlinh: bắt buộc chọn phiếu đề nghị liên kết nếu số hóa qtr
        if (this.sohoa_qlvt && (this.params.p_LoaiChungTu == "2" || this.params.p_LoaiChungTu == "3" || this.params.p_LoaiChungTu == "99")){
            if (this.params.p_txtPhieuDeNghi == null || this.params.p_txtPhieuDeNghi == "") { 
              this.$toast.error("Chưa nhập phiếu đề nghị"); 
              return; 
            }
          }
        // ------- -------
        

        let dtp = this.params.p_dtpNgayDK
        if (!this.Enabled.tsbtnNhapMoi){
          if (! await this.KiemTraDuLieu(true, false, "GHILAI")){ return}
          this.params.p_dtpDenNgay = dtp
          this.params.p_dtpTuNgay = new Date(dtp.getTime() - 7*24*60*60*1000)
          if (this.kiemtra_quyen){
            if (! await this.KIEMTRA_QUYEN_XN(5,this.params.p_TuKho)){ return }
          }

          var isBoxConfirm = false;
          await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn ghi lại!", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

          if (!isBoxConfirm) {
            return;
          }

          await this.TaoDuLieu(true, false)
                  
          // kieu 1 them moi
          await this.InsertUpdateChungTu(1,this.chungtuData)

          await this.CapNhatChiTiet_CT(true) // cap ctct vao chung tu moi tao

          await this.GAN_DENGHI_CHUNGTU({
            loaiCtId: this.vloaiphieu_id,
            chungTuId: this.vchungtu_id,
            maDn: this.params.p_txtPhieuDeNghi,
            soHoaQlvt: this.sohoa_qlvt,
            soHoaQlvtDual: this.sohoa_qlvt_dual
          })

          await Finish.call(this)
          // Quý add thêm thông báo 
          this.$toast.success("Thêm mới thành công")

          
        }else{
          if (! await this.KiemTraDuLieu(false, false, "GHILAI")){ return }
          
          if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)){ return }
          if (this.kiemtra_quyen) {
            if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }

            if (this.params.p_TuKho != this.t_kho_id){
              if (! await this.KIEMTRA_QUYEN_XN(5, parseInt(this.params.p_TuKho))){ return }
            }
          }

          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có chắc chắn muốn ghi lại! ", {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } else {
                return;
              }
            });

          if (!isBoxConfirm) {
            return;
          }
          await this.TaoDuLieu(false, false)                  
          let k = await this.XoaChungTuBaoHanh({
            "kieu": 2,
            "chungTuId": this.vchungtu_id,
            "chungTuCtId": 0,
            "thietBiId": 0
          })
          if (k != "1"){
            this.$toast.error(k)
            return
          }
          if (this.ttph_id  == 4){
            let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
            if (s.toString() != "1"){
              this.$toast.error(s.toString())
              return
            }

            if(this.params.p_ckbSendSMS){
              try {
                await this.GuiSMSChungTu({
                  tuDong: 2,
                  chungTuId: this.vchungtu_id,                         
                })
              } catch (error) {
                this.$toast.error("Có lỗi khi send smsm tự động")
              }
            }

            s = await this.XoaPhieuDaHoanThanh({
                  "chungTuId": this.vchungtu_id,
                  "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                  "mayCn": "quyPc",//ttnd.may_cn
                  "ipCn": "10.59.90.123",//ttnd.ip
              })
            if (s.toString() != "1"){
              this.$toast.error("Có lỗi trong quá trình xóa phiếu!")
              return
            }
          }
          
          // func convert key cho hợp API
          // kieu 0 cap nhat
          await this.InsertUpdateChungTu(0, this.chungtuData)
          // await this.CapNhatDuLieuChungTu(this.chungtuData)

          if (this.kieugoi_id == 2){
            let mucdich_id = parseInt(this.params.p_MucDich)
            var d = []
            this.params.p_MucDich ? d = await this.getMaMucDichTheoID({mucDichId: mucdich_id}) : ''
            if (d.length){
              if (d[0] != "1.N" && d[0] != "N.DK" && d[0] != "1.NBH"
                  && d[0] != "1.3" && d[0] != "1.NL"
                  && d[0] != "N.VT" && d[0] != "N.KDV"
                  && d[0] != "N_MS" && d[0] != "N.KDV_DG"){
                  var tongtien = this.dtVatTu.map(p=>parseInt(p.TIEN) || 0 ).reduce((sum,b)=>sum + parseInt(b.TIEN),0)
                  if (tongtien > 100000000){
                    await this.Load_ds_vattu()
                    this.$toast.error("Đơn hàng lẻ không được nhập quá 100,000,000")
                    return
                  }
                }
            }
          }
          await this.CapNhatChiTiet_CT(false)

          await this.GAN_DENGHI_CHUNGTU({
            loaiCtId: this.vloaiphieu_id,
            chungTuId: this.vchungtu_id,
            maDn: this.params.p_txtPhieuDeNghi
          })

          await Finish.call(this)
          // Quý add thêm thông báo   
          this.$toast.success("Cập nhật thành công")
          // ------------- --------------
          
          
        }

        async function Finish(){
          let vchungtu_tmp_id = this.vchungtu_id
          if (this.vtdk.length){
            await this.CAPNHAT_DANGKY(vchungtu_tmp_id)
          }
          await this.Load_ds_chungtu()
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
          this.copyct = false

          var dtable = await this.getDSChiTietChungTu({})
          if (dtable.length && dtable[0].hasOwnProperty('TRANGTHAI')){
            var d = await this.getMaMucDichTheoChungTu({chungTuId: this.vchungtu_id})
            if (this.params.p_LoaiChungTu.toString() == "99" && d != null && (d.toString() == "N.KDV" || d.toString() == this.MUCDICH_VT.KDV_DONGIA)){
              await this.CapNhatTrangThaiChiTietCT({chungTuId: this.vchungtu_id})
              await this.Load_ds_vattu()
            }
          }
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async CAPNHAT_DANGKY(chungtu){
      var q = this.vtdk[0].DANGKY_ID.toString()
      await this.CapNhatDangKy({chungTuId: this.vchungtu_id,  dangKyId: q})
      let xdoc = []
      this.vtdk.forEach(a=>xdoc.push({
        VTDK_ID:  a.VTDK_ID,
        SL_DUYET: a.SL_DUYET
      }))
      await this.CapNhatVatTuDangKy({
          "dataDangky": JSON.stringify(xdoc)
      })
    },
    async tsbtnHuyBo_Click(){
      this.SetButton(0)
  },
  async tsbtnXoa_Click(){
    var isBoxConfirm = false;
    await this.$bvModal
      .msgBoxConfirm("Bạn có chắc chắn muốn xóa chứng từ " + this.soph + " không!", {
        title: "Thông báo",
        centered: true,
        size: "md",
        okTitle: "Đồng ý",
        cancelTitle: "Hủy",
      })
      .then((value) => {
        if (value) {
          isBoxConfirm = true;
        } else {
          return;
        }
      });

    if (!isBoxConfirm) {
      return;
    }

    var frm = this.$refs.frmNhapText
    await frm.Load_frmNhapText()
    await frm.frmNhapText_3("Bạn hãy lựa chọn kiểu xóa chứng từ", 2)
    frm.Text.title = "Danh sách kiểu xóa chứng từ"
    frm.functionName = 'tsbtnXoa_Click'
    this.$refs.popup_frmNhapText.show()
  },
  async XacNhan_frmNhapText(){
      var frm = this.$refs.frmNhapText
      if (frm.DialogResult == DialogResultObj.OK && frm.functionName == 'tsbtnXoa_Click'){
        try{
          this.loading(true)
          let ketQua = await this.xoaChungTu3In1({
            chungtu_id: this.vchungtu_id,
            kieuxoa: frm.vkieu_luachon,
            sendmsm : this.params.p_ckbSendSMS ? 1 : 0,
            donvi_fmis: this.chondvi_fmis ?  this.params.p_DonViFMIS : "" 
          })
          let message = "Xóa chứng từ thành công!"
          if(frm.vkieu_luachon == "2"){
            message = "Xóa đồng bộ kế toán thành công!"
          }else if(frm.vkieu_luachon == "3"){
            message = "Xóa đồng IMS thành công!"
          }
          this.$toast.success(message)
          await this.Load_ds_chungtu()
          if (this.gridChungTu.length > 0){      
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID)
          }           
        }catch(e){
          this.$toast.error(e.data.message)
        }finally{
          this.loading(false)
        }
      }
    },
    // async tsbtnXoa_Click(){
    //   try {           

    //     if (this.vchungtu_id == 0){
    //       this.$toast.error("Bạn phải chọn chứng từ để xóa")
    //       return
    //     }
    //     if (! await this.KiemTraHanChotCT()){// Kiếm tra hạn chốt ok thì xóa
    //       return
    //     }
    //     if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }
    //     // ---------- -----------        
    //     if (this.check_user_tao_chungtu){
    //       if (!await this.KiemTraNguoiCN()){
    //         this.$toast.error("Bạn không được xóa chứng từ của " + this.gridviewChungTu_FocusedRow["NGUOI_CN"] + " !")
    //         return
    //       }
    //     }
    //     // ---------- -----------

        
    //     // ---------- -----------
    //     if (this.sohoa_qlvt || (this.sohoa_qlvt_dual && this.params.p_txtPhieuDeNghi != null && this.params.p_txtPhieuDeNghi != "")){
    //         let vkq = await this.KIEMTRA_XOA_CHUNGTU({chungTuId: this.vchungtu_id});
    //         if (vkq != "1"){
    //             this.$toast.error(vkq);
    //             return;
    //         }
    //     }
    //     // ---------- -----------

    //     var str = ""
    //     var d = await this.getSoPhieu({chungTuId: this.vchungtu_id})
    //     if (d.length){
    //       str = d[0].toString() + " "
    //     }
    //     await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn xóa chứng từ " + str + "không!", {
    //       "okVariant": 'success',
    //       "okTitle": 'Đồng ý',
    //       "cancelTitle": 'Hủy',
    //       "centered": true,
    //       })
    //       .then(async(value) => {
    //         if(value){
    //           var frm = this.$refs.frmNhapText
    //           await frm.Load_frmNhapText()
    //           await frm.frmNhapText_3("Bạn hãy lựa chọn kiểu xóa chứng từ", 2)
    //           frm.Text.title = "Danh sách kiểu xóa chứng từ"
    //           frm.functionName = 'tsbtnXoa_Click'
    //           this.$refs.popup_frmNhapText.show()

    //         }
    //       })
    //       .catch(err => {
    //           // An error occurred
    //       })
    //   }catch(e){
    //     this.$toast.error(e.data.message)
    //   }finally{
    //     this.loading(false)
    //   }
    // },
    // area_chuanhoa(){
    //   var area = ""
    //   if(this.$auth.getMaTinh() == "BCN"){
    //     area = "BKN"
    //   }else if(this.$auth.getMaTinh() == "VTU"){
    //     area = "BRA"
    //   }else if(this.$auth.getMaTinh() == "HAG"){
    //     area = "HGI"
    //   }else if(this.$auth.getMaTinh() == "DAN"){
    //     area = "DNO"
    //   }else{
    //     area = this.$auth.getMaTinh()
    //   }
    //   return area
    // },
    // async XacNhan_frmNhapText(){
    //   var frm = this.$refs.frmNhapText
    //   if (frm.DialogResult == DialogResultObj.OK && frm.functionName == 'tsbtnXoa_Click'){
    //     // Xóa toàn bộ 
    //     try{
    //       this.loading(true)
    //       if (frm.vkieu_luachon == "1"){
    //         if (this.kiemtra_quyen){
    //           if (!await this.KiemTraNguoiCN()){
    //             this.$toast.error("Bạn không được xóa chứng từ của " + this.gridviewChungTu_FocusedRow["NGUOI_CN"] + " !")
    //             return
    //           }
    //           if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)) { return }
    //         }
    //         let s = ""
    //         if (this.ttph_id == 4){
    //           s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
    //           if (s.toString() != "1"){
    //             this.$toast.error(s.toString())
    //             return
    //           }
    //         }
    //         // Xóa Đồng bộ
    //         let db_kt = await this.KiemTraXoaDongBo({
    //           chungTuId: this.vchungtu_id,           
    //           ipCn: frm.vkieu_luachon,
    //           maDv: this.chondvi_fmis ?  this.params.p_DonViFMIS : ""
    //         })

            
    //         //Nếu tồn tại chứng từ tập đoàn thì phải xóa đồng bộ thành công
    //         if (db_kt != "xoa"){ 
    //           if (db_kt == "ok"){
    //             this.$toast.success("Xóa chứng từ kế toán tập đoàn thành công!")
    //           }
    //           else{
    //             this.$toast.error("Xóa chứng từ kế toán tập đoàn có lỗi:" + db_kt + "!")
    //             if (this.vchungtu_id != 0){
    //               this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.vchungtu_id)
    //             }
    //             await this.Load_ds_chungtu();
    //             return
    //           }
    //         }

    //         // Xóa IMS
    //         var area = this.area_chuanhoa()
    //         var kt_xoa_ims = await this.kiemTraXoaIMS(this.vchungtu_id)
    //         if(kt_xoa_ims != "1"){
    //           var xoa_ims = await this.KiemTraTrangThaiIMS({
    //             soPhieu: this.soph + "|" + area,
    //             iMode: "1"
    //           })
    //           if(xoa_ims == "2"){// = 0 = 1 bỏ qua ko cần show thông báo
    //             this.$toast.error("Chứng từ đã xác nhận, không xóa được trên IMS !  Liên hệ admin IMS để xử lý !");
    //             return
    //           } 
    //         }

    //         if(this.params.p_ckbSendSMS && this.ttph_id == 4){
    //             try {
    //               await this.GuiSMSChungTu({
    //                 tuDong: 2,
    //                 chungTuId: this.vchungtu_id,                        
    //               })
    //             } catch (error) {
    //               this.$toast.error("Có lỗi khi send smsm tự động")
    //             }
    //         }
              
    //         s = await this.XoaChungTuBaoHanh({
    //           "kieu": 1,
    //           "chungTuId": this.vchungtu_id,
    //           "chungTuCtId": 0,
    //           "thietBiId": 0
    //         })
    //         if (s.toString() != "1"){
    //           this.$toast.error(s)
    //           return
    //         }
    //         s = await this.XoaPhieuDaHoanThanh({
    //           "chungTuId": this.vchungtu_id,
    //           "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
    //           "mayCn": this.$auth.getUserName(),//ttnd.may_cn
    //           "ipCn": "10.59.90.123"//ttnd.ip
    //         })
    //         if (s.toString() != "1"){
    //           this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s + "")
    //           return
    //         }
    //         await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
    //         // -----------  -----------------
    //         if (this.sohoa_qlvt || this.sohoa_qlvt_dual){
    //           await this.XOA_GIAOPHIEU_CHUNGTU({
    //             chungTuId: this.vchungtu_id
    //           })
    //         }
    //         // -----------  -----------------

    //         var t = await this.XoaChungTu({chungTuId: this.vchungtu_id})
    //         this.$toast.success("Xóa thành công!")
    //         if(this.vchungtu_id != 0){
    //           await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.vchungtu_id);
    //         }
    //         await this.Load_ds_chungtu()
    //         if (!this.gridChungTu.length){
    //           return
    //         }
    //         else{
    //           await this.Load_ds_vattu()
    //           await this.Load_ds_thietbi()
    //         }
    //       }
    //       // Xóa Đồng bộ
    //       // Chỉ xóa tập đoàn
    //       if(frm.vkieu_luachon == "2"){
    //         let s = ""
    //         if (this.ttph_id == 4){
    //           s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
    //           if (s.toString() != "1"){
    //             this.$toast.error(s.toString())
    //             return
    //           }
    //         }
    //         let db_kt = await this.KiemTraXoaDongBo({
    //           chungTuId: this.vchungtu_id,              
    //           ipCn: frm.vkieu_luachon,
    //           maDv: this.chondvi_fmis ?  this.params.p_DonViFMIS : ""
    //         })
    //         //Nếu tồn tại chứng từ tập đoàn thì phải xóa đồng bộ thành công
    //         if (db_kt != "xoa"){ 
    //           if (db_kt == "ok"){
    //             this.$toast.success("Xóa chứng từ kế toán tập đoàn thành công!")
    //           }
    //           else{
    //             this.$toast.error("Xóa chứng từ kế toán tập đoàn có lỗi:" + db_kt + "!")              
    //           }
    //         }
    //         var vchungtu_temp_id = this.vchungtu_id          
    //         await this.Load_ds_chungtu()
    //         if(vchungtu_temp_id != 0){
    //           await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_temp_id);
    //         }
    //         if (!this.gridChungTu.length){
    //           return
    //         }else{
    //           await this.Load_ds_vattu()
    //           await this.Load_ds_thietbi()
    //         }
    //       }

    //       if(frm.vkieu_luachon == "3"){
    //         let s = ""
    //         if (this.ttph_id == 4){
    //           s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
    //           if (s.toString() != "1"){
    //             this.$toast.error(s.toString())
    //             return
    //           }
    //         }
    //         var area = this.area_chuanhoa()
    //         var xoa_ims = await this.KiemTraTrangThaiIMS({
    //           soPhieu: this.soph + "|" + area,
    //           iMode: "1"
    //         })
    //         if(xoa_ims == "1"){
    //           var up_trangthai = await this.capNhatBBBGIMS({
    //             chungTuId: this.vchungtu_id,
    //             kieu: 1
    //           })
    //           this.$toast.success("Xóa chứng từ thành công trên IMS !")
    //         }else if(xoa_ims == "2"){
    //           this.$toast.error("Chứng từ đã xác nhận, không xóa được trên IMS !  Liên hệ admin IMS để xử lý !")
    //         }else{
    //           this.$toast.error("Chứng từ không tồn tại trên IMS !")
    //         }

    //         var vchungtu_temp_id = this.vchungtu_id          
    //         await this.Load_ds_chungtu()
    //         if(vchungtu_temp_id != 0){
    //           await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_temp_id);
    //         }
    //       }
    //     }catch(e){
    //       this.$toast.error(e.data.message)
    //     }finally{
    //       this.loading(false)
    //     }
    //   }
    // },
    async KiemTraNguoiCN(){
      return this.gridviewChungTu_FocusedRow.NGUOI_CN == this.$auth.getUserName() //ttnd.ma_nd
    },
    async KiemTraDuLieu(themmoi,traphieu, tag=""){
      if (!themmoi){
        // -------------- ---------------
        if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")
          || this.nghiepvu_id_chungtu == "288" || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282")){
            this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được sửa")
            return false
          }
        // -------------- ---------------
        if (! await this.KiemTraNguoiCN() && !traphieu){
          this.$toast.error("Bạn không được sửa chứng từ của người khác!")
          this.$refs.cboLoaiChungTu.focusIn()
          return false
        }
        let s  = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        if (s.toString() != "1"){
          this.$toast.error(s.toString())
          return false
        }
        // ------------- ----------------
        if (this.sohoa_qlvt || (this.sohoa_qlvt_dual && this.params.p_txtPhieuDeNghi != null && this.params.p_txtPhieuDeNghi != "")){
          let vkq = await this.KIEMTRA_TRANGTHAI_CT({chungTuId: this.vchungtu_id});
          if (vkq != "1"){
              this.$toast.error(vkq);
              return false;
          }
        }
        // ------------- ----------------
      }
      // ------------- ----------------
      if (!themmoi && this.doi_mucdich_chungtu){
        let mdid = this.gridviewChungTu_FocusedRow.MUCDICH_ID
        if (mdid != this.params.p_MucDich){
          if (this.gridVatTu.length > 0){
              this.$toast.error("Chứng từ đã có vật tư, không thể đổi sang mục đích khác !");
              return false;
          }
        }
      }
      // ------------- ----------------
      if (themmoi && this.ngay_dh != null){//&& ngay_dh != DateTime.MinValue
        if (this.f_StringToDate(this.ngay_dh,'DD/MM/YYYY') > this.params.p_dtpNgayDK ){
          this.$toast.error("Ngày chứng từ phải lớn hơn ngày yêu cầu nhập kho (" + this.f_DateToString(this.ngay_dh)  + ")")
          return false
        }
      }
      if (this.ttph_id == 4 && !themmoi){
        let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
        if (s.toString() != "1"){
          this.$toast.error(s.toString())
          this.$refs.cboLoaiChungTu.focusIn()
          return false
        }
        if (this.params.p_DenKho.toString() != this.kho_nhan_goc.toString()){
          this.$toast.error("Bạn ghi lại phải giữ nguyên đúng kho đến (trạng thái DD có thể sửa kho đến)")
          this.params.p_DenKho = this.kho_nhan_goc
          return false
        }
      }

      if (!!!this.params.p_LoaiChungTu && this.params.p_LoaiChungTu !== 0){
        this.$toast.error("Bạn chưa chọn loại chứng từ!")
        this.$refs.cboLoaiChungTu.focusIn()
        return false
      }

      if (!!!this.params.p_MucDich && !this.params.p_MucDich !== 0){
        this.$toast.error("Bạn chưa chọn Mục đích!")
        this.$refs.cboMucDich.focusIn()
        return false
      }
      
      if (this.params.p_TuKho === null || this.params.p_TuKho === ""){
        this.$toast.error("Bạn chưa chọn từ kho!")
        this.$refs.cboTuKho.focusIn()
        return false
      }
      // Bổ sung cho hoàn trả

      let xuat_hoantra = false
      this.dc_ct_noibo  = false
      if (this.ds_md.length){
        var t = this.ds_md.filter(a=>a.MUCDICH_ID == this.params.p_MucDich)
        if (t.length && t[0]["maMd"].toString() == "X.HTVT"){
          if (this.params.p_LoaiChungTu.toString() == "2" || this.params.p_LoaiChungTu.toString() == "3" ){
            xuat_hoantra = true
          }
        }

        if (t.length && t[0]["maMd"].toString() == "DC_CT"){
          if (!!this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2"){
            this.dc_ct_noibo = true
          }
        }

      }
      
      if ((!!!this.params.p_DenKho && !this.params.p_DenKho !== 0) && !xuat_hoantra){
        this.$toast.error("Bạn chưa chọn đến kho!")
        this.$refs.cboDenKho.focusIn()
        return false
      }
      if (xuat_hoantra){
        if(this.params.p_DenKho.toString()){
          this.$toast.error("Xuất hoàn trả vật tư bạn không được chọn kho!")
          this.$refs.cboDenKho.focusIn()
          this.params.p_DenKho = ""
          return false
        }
      }

      if(!xuat_hoantra && !this.dc_ct_noibo && this.params.p_TuKho == this.params.p_DenKho){
        this.$toast.error("Kho từ và kho đến không thể giống nhau!")
        this.$refs.cboTuKho.focusIn()
        return false
      }
      if (this.dc_ct_noibo){
        if (this.params.p_TuKho != this.params.p_DenKho){
          this.$toast.error("Kho từ và kho đến phải giống nhau!")
          this.$refs.cboTuKho.focusIn()
          return false
        }
        let dt = this.cboTuKho
        if(dt.filter(a=>a.KHO_ID == this.params.p_TuKho && a.LOAIKHO_ID == "14").length == 0){
          this.$toast.error("Chỉ áp dụng mục đích này cho loại kho tổ trưởng")
          return false
        }
        if (!this.params.p_CongTrinh.toString()){
          this.$toast.error("Chưa chọn công trình")
          return false
        }
      }

      
      if (!await this.KiemTraHanChotCT()){
        return false
      }

      if (this.params.p_txtNoiDung.length > 500){
        this.$toast.error("Nội dung nhập quá dài (<500 kí tự) !")
        return false
      }
      // ----------- -------------
      if (tag != "GHILAI_PDA" && tag != "BBBG" && tag != "THUHOITS"){
        if(this.gridVatTu.length == 0){
          this.$toast.error("Bạn cần phải chọn danh sách vật tư cho chứng từ!")
          return false
        }
      }
      // ----------- -------------
      
      if(this.Enabled.tsbtnNhapMoi){
        if (this.soph.startsWith("QT")){
          this.$toast.error("Chứng từ quyết toán không thể sửa!")
          return false
        }
        if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return false
        }
      }
      return true
    },
    async TaoDuLieu(themmoi,traphieu){
      this.chungtuData = []
      let row = {}
      if (themmoi){
        // this.vchungtu_id = await this.getKeys({
        //     "keyName" : "CHUNGTU_ID",
        //     "numBlockSize" : 1,
        //     "numRetry":  10
        //   })
        this.vchungtu_id = 0
      }
      row.CHUNGTU_ID = this.vchungtu_id
      if (themmoi){
        if (this.kieugoi_id == 1){
          this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV3({
            "khoGiaoId": parseInt(this.params.p_TuKho),
            "khoNhanId": parseInt(this.params.p_DenKho),
            "loaiCtId": parseInt(this.params.p_LoaiChungTu),
            "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
            "donViNdId": this.$auth.getDonViID(),
            "maNd": this.$auth.getUserName(),//ttnd.ma_nd
          })
          row.LOAICT_ID = parseInt(this.params.p_LoaiChungTu)
        }
        else{
          this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV3({
            "khoGiaoId": parseInt(this.params.p_TuKho),
            "khoNhanId": parseInt(this.params.p_DenKho),
            "loaiCtId": 1,
            "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
            "donViNdId": this.$auth.getDonViID(),
            "maNd": this.$auth.getUserName(),//ttnd.ma_nd
          })
          row.LOAICT_ID = 1
        }
      }
      else{
        if (this.f_StringToDate(this.gridviewChungTu_FocusedRow["NGAY_CT"],'DD-MM-YYYY hh:mm:ss').getFullYear().toString() != this.params.p_dtpNgayDK.getFullYear().toString()){
          if (this.kieugoi_id == 1){
            this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV3({
              "khoGiaoId": parseInt(this.params.p_TuKho),
              "khoNhanId": parseInt(this.params.p_DenKho),
              "loaiCtId": parseInt(this.params.p_LoaiChungTu),
              "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
              "donViNdId": this.$auth.getDonViID(),
              "maNd": this.$auth.getUserName(),//ttnd.ma_nd
            })
          }
          else{
            this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV3({
              "khoGiaoId": parseInt(this.params.p_TuKho),
              "khoNhanId": parseInt(this.params.p_DenKho),
              "loaiCtId": 1,
              "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
              "donViNdId": this.$auth.getDonViID(),
              "maNd": this.$auth.getUserName(),//ttnd.ma_nd
            })
          }
        }
        if (this.kieugoi_id == 2){
          row.LOAICT_ID = 1
        }
        else{
          row.LOAICT_ID = this.params.p_LoaiChungTu
        }
      }
      row.SOPHIEU = this.params.p_txtSoPhieu
      row.MUCDICH_ID = parseInt(this.params.p_MucDich)
      row.KHO_GIAO_ID = parseInt(this.params.p_TuKho)
      let xuat_hoantra = false
      if (this.ds_md.length){
        var t = this.ds_md.filter(a=>a.mucDichId.toString() == this.params.p_MucDich.toString())
        if (t.length > 0 && t[0]["maMd"].toString() == "X.HTVT"){
          if(this.params.p_LoaiChungTu.toString() == "2" || this.params.p_LoaiChungTu.toString() == "3"){
            xuat_hoantra = true
          }
        }
      }
      if (!xuat_hoantra){
        row.KHO_NHAN_ID = parseInt(this.params.p_DenKho)
      }
      row.NHANVIEN_GIAO_ID = this.$auth.getNhanVienID()
      row.DONVI_NHAN_ID = this.$auth.getDonViID()
      row.DONVI_GIAO_ID = this.$auth.getDonViID()
      row.NGUOI_CN = this.$auth.getUserName()
      if (traphieu){
        row.NGUOI_CN = this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["NGUOI_CN"] || ""
        row.NHANVIEN_GIAO_ID = this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["NHANVIEN_GIAO_ID"] || this.$auth.getNhanVienID()
        row.DONVI_NHAN_ID = this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["DONVI_NHAN_ID"] || this.$auth.getDonViID()
        row.DONVI_GIAO_ID = this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["DONVI_GIAO_ID"] || this.$auth.getDonViID()
      }
      if (this.params.p_CongTrinh.toString()){
        row.CONGTRINH_ID = parseInt(this.params.p_CongTrinh)
      }
      row.ND_GIAO = this.params.p_txtNoiDung
      if (this.params.p_dtpNgayDK.getHours() == 0 && this.params.p_dtpNgayDK.getMinutes() == 0){
        this.params.p_dtpNgayDK = this.params.p_dtpNgayDK.setMinutes(this.params.p_dtpNgayDK.getMinutes() + 1)
      }
      row.NGAY_CT = this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss')
      row.TTPH_ID = 1
      row.LOAI = this._vloai
      row.NGAY_CN = this.f_DateToString(new Date(),'DD/MM/YYYY')
      row.IP_CN = ''//ttnd.ip
      row.MAY_CN = ''//ttnd.may_cn;

      row.NGHIEPVU_ID = ''
      row.MA_DV_SD = ''
      row.MA_CSHT = ''
      row.MA_DV_QL = ''
      row.HDMS_ID = ''
      row.MA_PDA = ''
      // ------------- ------------
      //có tham số thì lấy combo nghiệp vụ
      if (this.vattu_ims){
        if (this.params.p_NghiepVu != null && this.params.p_NghiepVu != ""){
          if (this.params.p_NghiepVu != "-1"){
            row.NGHIEPVU_ID = parseInt(this.params.p_NghiepVu);
          }


          if (this.params.p_NghiepVu == "292" || this.params.p_NghiepVu == "293" || this.params.p_NghiepVu == "294"){
            if (this.params.p_Ma_DV_SD != null && this.params.p_Ma_DV_SD != ""){
              row.MA_DV_SD = this.params.p_Ma_DV_SD;
            }
                
            if (this.params.p_IMS_CSHT != ""){
              row.MA_CSHT = this.params.p_IMS_CSHT;
            }
                
            if (this.params.p_Ma_DV_QL != null && this.params.p_Ma_DV_QL != ""){
              row.MA_DV_QL = this.params.p_Ma_DV_QL
            }
                
            if (this.params.p_HD_XuatIMS != null && this.params.p_HD_XuatIMS != ""){
              if (this.params.p_HD_XuatIMS != null && this.params.p_HD_XuatIMS != ""){
                let key_hd = await this.Insert_HDMS({ 
                  maHd: this.params.text_HD_XuatIMS,
                  contractInfoId: this.params.p_HD_XuatIMS
                })
                row.HDMS_ID = key_hd;
              }
            }
          }
        }
        if (this.params.p_Ma_PDA != "")
            row.MA_PDA = this.params.p_Ma_PDA;
      }
      // ------------- ------------

      this.chungtuData.push(row)
    },
    async TaoDuLieu_CT(){
      this.chitietCTData_insert = []
      this.chitietCTData_update = []
      for (let dr of this.dtVatTu){
        if (dr["CTCT_ID"].toString() == "0"){
          let ri = {}
          // this.ctct_id = await this.getKeys({
          //   "keyName" : "CTCT_ID",
          //   "numBlockSize" : 1,
          //   "numRetry":  10
          // })
          this.ctct_id = 0
          ri.CTCT_ID = this.ctct_id
          ri.VATTU_ID = parseInt(dr["VATTU_ID"])
          ri.CHUNGTU_ID = this.vchungtu_id
          ri.LOHANG = dr["LOHANG"]
          ri.DONGIA = parseInt(dr["DONGIA"])
          ri.TIEN = Math.round(parseInt(dr["TIEN"]))
          ri.VAT = parseInt(dr["VAT"])
          ri.SOLUONG = parseInt(dr["SOLUONG"])
          if(dr["SOTHANG_PB"].toString()){
            ri.SOTHANG_PB = parseInt(dr["SOTHANG_PB"])
          }
          ri.NGUOI_CN = this.$auth.getUserName()
          ri.NGAY_CN = this.f_DateToString(new Date(),'DD/MM/YYYY HH:mm:ss')
          ri.MAY_CN = this.$auth.getUserName //ttnd.may_cn
          ri.IP_CN = '10.59.90.123' //ttnd.ip
          this.chitietCTData_insert.push(ri)
        }
        else{
          let ru = {}
          this.ctct_id = parseInt(dr["CTCT_ID"])
          ru.CTCT_ID = this.ctct_id
          ru.VATTU_ID = parseInt(dr["VATTU_ID"])
          ru.CHUNGTU_ID = this.vchungtu_id
          ru.LOHANG = dr["LOHANG"].toString()
          ru.DONGIA = parseInt(dr["DONGIA"])
          ru.TIEN = Math.round(parseInt(dr["TIEN"]))
          ru.VAT = parseInt(dr["VAT"])
          ru.SOLUONG = parseInt(dr["SOLUONG"])
          if(dr["SOTHANG_PB"].toString()){
            ru.SOTHANG_PB = parseInt(dr["SOTHANG_PB"])
          }
          ru.NGUOI_CN = this.$auth.getUserName()
          ru.NGAY_CN = this.f_DateToString(new Date(),'DD/MM/YYYY HH:mm:ss')
          ru.MAY_CN = this.$auth.getUserName //ttnd.may_cn
          ru.IP_CN = '10.59.90.123' //ttnd.ip
          this.chitietCTData_update.push(ru)
        }
      }
      
    },
    async TaoDuLieu_Tbi(){
      let chungtu_tbiData = []
      for (let item of this.dtThietBi){
        let ri = {}
        let vthietbi_id = 0

        if(item["THIETBI_ID"].toString() != "0"){
          vthietbi_id = parseInt(item["THIETBI_ID"])
        }
        else{
          vthietbi_id = await this.getKeys({
            "keyName" : "THIETBI_ID",
            "numBlockSize" : 1,
            "numRetry":  10
          })
        }

        ri.THIETBI_ID = vthietbi_id
        ri.SERIAL = item["SERIAL"].toString()
        ri.MA_TBI = item["MA_TBI"].toString()
        ri.DONGIA = parseInt(item["DONGIA"])
        let vctct_id = 0

        let lohang = item["LOHANG"].toString()
        let vattu_id = item["VATTU_ID"].toString()

        var a = this.chitietCTData_insert.filter(p=>p.LOHANG.toString() == lohang && p.VATTU_ID.toString() == vattu_id)
        if (a.length){
          vctct_id = parseInt(a[0]["CTCT_ID"])
        }
        else{
          var b = this.chitietCTData_update.filter(p=>p.LOHANG.toString() == lohang && p.VATTU_ID.toString() == vattu_id)
          if (b.length){
            vctct_id = parseInt(b[0]["CTCT_ID"])
          }
        }
        ri.CTCT_ID = vctct_id
        chungtu_tbiData.push(ri)
      }
      return chungtu_tbiData
    },
    async KiemTraHanChotCT(){
      let vkho_nhan_id = 0
      let vkho_giao_id = 0
      let ngay_ct = this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY')

      if (this.params.p_TuKho !=  null && this.params.p_TuKho != ""){
        vkho_giao_id = parseInt(this.params.p_TuKho)
      }
      if (this.params.p_DenKho !=  null  && this.params.p_DenKho != ""){
        vkho_nhan_id = parseInt(this.params.p_DenKho)
      }
      let s = await this.KiemTraHanChungTu({
        "chungTuId": this.vchungtu_id,
        "khoGiaoId": vkho_giao_id,
        "khoNhanId": vkho_nhan_id,
        "ngayCt": ngay_ct
      })
      
      if (s.toString() != "1"){
        this.$toast.error("Đã quá hạn sửa chứng từ!")
        return false
      }
      return true
    },
    async tsbtnThemVT_Click(){
      try{
        this.loading(false)
        if (this.params.p_Ma_PDA != ""){        
          if (this.params.p_NghiepVu != "292" && this.params.p_NghiepVu != "293" && this.params.p_NghiepVu != "294"){
            this.$toast.error("Bạn không được chọn vật tư với thông tin từ dự án/ số phiếu");
            return;
          }
        }
        // ------------- --------------
        if (this.vma_md == this.MUCDICH_VT.MUASAM){
          this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép chọn vật tư")
          return
        }
        if (this.vma_md == this.MUCDICH_VT.CHUYEN_CP){
          this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép chọn vật tư")
          return
        }

        // quy bo sung thong bao cho muc dich
        if (this.params.p_MucDich == null || this.params.p_MucDich == ''){
          this.$toast.error("Bạn chưa chọn mục đích!")
          return
        }
        
        if (this.kieugoi_id == 1){
          let mamd = ""
          let mucdich_id = this.params.p_MucDich || 0
          var d = []
          this.params.p_MucDich ? d = await this.getMaMucDichTheoID({mucDichId: mucdich_id}) : ''
          if (d.length){
            mamd = d[0].toString()
          }
          if (mamd == "CCDC.TL"){}
          else{
            // quy bo sung thong bao cho kho       
            if (this.params.p_TuKho === null || this.params.p_TuKho === ""){
              this.$toast.error("Bạn chưa chọn từ kho!")
              return
            }

            this.vtdk = []
            var f = this.$refs.frmVatTuTrongKho
            await f.Clear()
            f.Visible.tsbtnXuatExcelTbi = false
            f.vkho_id = parseInt(this.params.p_TuKho || 0) 
            f.tt = 0
            try {
              var a = await this.getIDLoaiKho({khoId: parseInt(this.params.p_TuKho || 0)})
              if (a.length){ 
                if (a[0].toString() == this.LOAI_KHO.KHO_TOTRUONG.toString()){
                  f.tt = 1
                } 
              }  
            } catch (error) {
              f.tt = 0
            }
            f.kieu = 1
            f.dtLuuVT = this.dtLuuVT
            this.ten_khotu = this.TenTuKho(this.params.p_TuKho)
            f.vngay_ct = this.params.p_dtpNgayDK
            if (!this.Enabled.tsbtnNhapMoi){
              f.vchungtu_id = 0
            }
            else{
              f.vchungtu_id = this.vchungtu_id
            }
            f.vkho_den_id = 0
            if (this.params.p_DenKho != null){
              f.vkho_den_id = parseInt(this.params.p_DenKho)
            }
            f.functionName = 'tsbtnThemVT_Click'
            await f.ShowDialog()
            // XacNhan_frmVatTuTrongKho
          }

        }
        else{
          // Chỉ chọn Vật tư
          var f = this.$refs.frmDSVatTu
          await f.resetParams()
          let mucdich_id = parseInt(this.params.p_MucDich || 0)
          let zvma_md = ""
          var d = []
          this.params.p_MucDich ? d = await this.getMaMucDichTheoID({mucDichId: mucdich_id}) : ''
          if (d.length){
            zvma_md = d[0].toString()
          }
          f.ma_md = zvma_md
          f.kieu = 1
          f.dtLuuVT = this.dtLuuVT
          await f.ShowDialog()
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async XacNhan_frmVatTuTrongKho(){
      let f = this.$refs.frmVatTuTrongKho
      if (f.XacNhan && f.functionName == 'tsbtnThemVT_Click'){

        this.dtLuuVT = f.dt
        this.dtThemVT = []
        for (let item of f.dt){
          let r = {}
          r.TATCA = f.checkAll ? 1: 0
          r.CTCT_ID = 0
          r.LOHANG = item.LOHANG
          r.VATTU_ID = item.VATTU_ID
          r.MA_VT = item.MA_VT
          r.TEN_VT = item.TEN_VT
          r.DVI_TINH = item.DVI_TINH
          r.SOLUONG= parseFloat(item.SL_XUAT)
          r.DONGIA = parseFloat(item.DONGIA)
          r.TIEN = parseInt(parseFloat(item.SL_XUAT)* parseFloat(item.DONGIA))
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI
          r.LOAITBI_ID = item.LOAITBI_ID
          r.TRANGTHAI = item.hasOwnProperty('TRANGTHAI') ? item["TRANGTHAI"] : ""
          this.dtThemVT.push(r)
        }
        this.gridVatTu = this.dtThemVT
        this.gridVatTu_DataSourceChanged()
        this.khotu_id = f.vkho_id
        this.Enabled.gridViewVatTu.SOLUONG = true
        this.kieunhap = 2
        if (f.coDS){
          this.vtdk = f.ds_dangky
        }
      }
      if (f.XacNhan && f.functionName == 'LAY_CHUNGTU'){
        this.dtLuuVT = f.dt
      let dtThemVT_v2 = []
      for (let item of f.dt){
        let r = {}
        r["TATCA"] = f.checkAll ? 1 : 0;
        r["CTCT_ID"] = 0;
        r["LOHANG"] = item["LOHANG"]
        r["VATTU_ID"] = item["VATTU_ID"]
        r["MA_VT"] = item["MA_VT"]
        r["TEN_VT"] = item["TEN_VT"]
        r["DVI_TINH"] = item["DVI_TINH"]
        r["SOLUONG"] = item["SL_XUAT"] == null || item["SL_XUAT"] == "" ? 0 : parseFloat(item["SL_XUAT"])
        r["DONGIA"] = item["DONGIA"] == null || item["DONGIA"] == "" ? 0 : parseFloat(item["DONGIA"]) 
        r["TIEN"] = r["SOLUONG"] *  r["DONGIA"]
        r["SOTHANG_PB"] = item["SOTHANG_PB"] == null || item["SOTHANG_PB"] == "" ? 0 : parseFloat(item["SOTHANG_PB"]) 
        r["LOAI_TBI"] = item["LOAI_TBI"];
        r["LOAITBI_ID"] = item["LOAITBI_ID"];
        r["HAN_BH"] = "";
        
        dtThemVT_v2.push(r)
        }
        for (let dr of dtThemVT_v2){
          dr["CTCT_ID"] = 0
        }
        this.gridVatTu = dtThemVT_v2
        this.gridVatTu_DataSourceChanged()
        this.dtVatTu = dtThemVT_v2
        this.khotu_id = f.vkho_id
        this.params.p_TuKho = this.khotu_id
        this.Enabled.gridViewVatTu.SOLUONG = true
        this.kieunhap = 4
        this.kieugoi_id = 2
      }
    },
    async XacNhan_frmVatTuTrongKho_V2(){
      var f = this.$refs.frmVatTuTrongKho_V2
      if (f.xacnhan){
        this.gridVTGroup = f.ds_VTGroup
        this.ds_Gan = f.ds_Gan
        this.dtLuuVT = f.dt_VATTU
        for (let item of f.dt_VATTU){
          let r = {}
          r.TATCA = 0
          r.CTCT_ID = 0
          r.LOHANG = item.LOHANG.toString()
          r.VATTU_ID = parseInt(item.VATTU_ID)
          r.MA_VT = item.MA_VT.toString()
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()

          r.SOLUONG= 0
          r.DONGIA = parseFloat(item.DONGIA)
          r.TIEN = parseInt(item.DONGIA)
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID
          r.KHO_ID = item.KHO_ID
          r.MAY_CN = this.$auth.getUserName()//ttnd.may_cn
          r.NGUOI_CN = this.$auth.getUserName()//ttnd.ma_nd
          r.IP_CN = '10.59.90.123' //ttnd.ip
          r.TY_LE = item.TY_LE.toString()
          r.MAKHOTD = item.MAKHOTD.toString()
          r.LOAIKHO = item.LOAIKHO.toString()
          r.MATHETS = item.MATHETS.toString()
          r.MATHUNG = item.MATHUNG.toString()
          r.GHICHU_HD = item.GHICHU.toString()
          r.CHATLUONG = item.CHATLUONG.toString()
          r.NGUON_GOC = item.NGUON_GOC.toString()
          r.CHATLUONG_ID = ""
          r.DONGTBI_ID = ""
          r.HETHONG_ID = ""
          this.dtThemVT.push(r)
        }
        this.gridVatTu = this.dtThemVT
        this.gridVatTu_DataSourceChanged()
        this.khotu_id = f.vkho_id
        this.kieunhap = 2
        this.Enabled.gridViewVatTu.SOLUONG = true
      }
    },
    async XacNhan_frmDSVatTu(){
      var f = this.$refs.frmDSVatTu
      if (f.XacNhan){
        this.dtLuuVT = f.dt
        this.dtVT_LE = []
        let lohang = ""
        let zvma_md = ""
        var d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
        if (d.length){
          zvma_md = d[0].toString()
        }
        let seq_LH = await this.getKeys({
            "keyName" : "LOHANG",
            "numBlockSize" : 1,
            "numRetry":  10
        })
        if (zvma_md == "N.DK"){
          lohang = "DK" + seq_LH
        }
        else if (zvma_md == "N.KDV"){
          lohang = "KDV" + seq_LH
        }
        else if (zvma_md == "N.KDV_DG"){
          lohang = "KDV"
        }
        //  ------------- -------------
        else if (zvma_md == "1.N"){
          lohang = "N" + seq_LH
        }
        else if (zvma_md == "1.NBH"){
          lohang = "NBH" + seq_LH
        }
        else if (zvma_md == "1.NL"){
          lohang = "NL" + seq_LH
        }
        //  ------------- -------------
        else{
          lohang = seq_LH
        }
       

        for (let item of f.dt){
          let r = {}
          let lohang2 = ""
          let lohang_temp = ""
          if (zvma_md != "N.KDV_DG"){
            var q = f.dt.filter(a=>a.VATTU_ID.toString() == item["VATTU_ID"].toString() && (a.LOHANG == item["LOHANG"] || !a.LOHANG)).length
            if (q > 1){
              lohang2 = await this.getKeys({
                  "keyName" : "LOHANG",
                  "numBlockSize" : 1,
                  "numRetry":  10
              })
            }
          }

          r.CTCT_ID = 0
          
          lohang_temp = lohang
          if (lohang2){
            lohang_temp = lohang2
          }
          if  (item["LOHANG"].trim() && zvma_md != "N.DK" && zvma_md != "N.KDV" && zvma_md != "N.KDV_DG"){
            console.log(item["LOHANG"] , " = item")
            r.LOHANG = await this.tao_lohang(item["LOHANG"],lohang_temp)
          }
          else if (zvma_md == "N.KDV_DG"){
            r.LOHANG = lohang_temp + await this.getKeys({
                  "keyName" : "LOHANG",
                  "numBlockSize" : 1,
                  "numRetry":  10
              })
          }
          else{
            r.LOHANG = lohang_temp
          }          
          r.MA_VT = item.MA_VT.toString()
          r.VATTU_ID = parseInt(item.VATTU_ID.toString())
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()
          r.SOLUONG= parseFloat(item.SOLUONG)
          r.DONGIA = parseFloat(item.DONGIA)
          r.TIEN = parseFloat(item.TIEN)
          
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID

          if (item.HAN_BH){
            r.HAN_BH = item.HAN_BH//dd/MM/yyyy
          }
          
          this.dtVT_LE.push(r)
        }
        this.gridVatTu = this.dtVT_LE
        this.gridVatTu_DataSourceChanged()
        this.kieunhap = 3; //Nhap lẻ
      }
    },
    async tao_lohang(prefix,lohang){
      var d = await this.getMaPrefixLoHang({loHang: prefix})
      if (d.length){
        if (d[0] == "1"){
          return prefix.trim() + lohang
        }
        else{
          return prefix.trim()
        }
      }
      return lohang
    },
    async tstbtnDonHang_Click(){
      if (this.vma_md.toString() == this.MUCDICH_VT.MUASAM.toString()){
        this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép chọn từ đơn hàng")
        return 
      }
      if (this.vma_md.toString() == this.MUCDICH_VT.CHUYEN_CP.toString()){
        this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép chọn từ đơn hàng")
        return
      }
      var f = this.$refs.frmDonHangTT
      f.dataResponse.DSVatTuChiTiet = []
      f.taskParent = "NhapXuatVT"
      await f.loadDataDonHang()
      this.$refs.popup_frmDonHangTT.show()
   
    },
    async XacNhan_frmDonHangTT(){
      var f = this.$refs.frmDonHangTT
      if (f.XacNhan){
        this.dtDonHang = []
        let lohang = await this.getKeys({
            "keyName" : "LOHANG",
            "numBlockSize" : 1,
            "numRetry":  10
        })
        for (let item of f.dt){
          let r = {}
          r.CTCT_ID = 0
          r.CTDH_ID = parseInt(item.CTDH_ID)
          r.LOHANG = lohang
          r.MA_VT = item.MA_VT.toString()
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()
          r.SOLUONG= parseInt(item.SL_CHUAN)
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = Math.round(parseInt(item.SL_CHUAN)*parseInt(item.DONGIA))
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID
          
          this.dtDonHang.push(r)
        }
        this.gridVatTu = this.dtDonHang
        this.gridVatTu_DataSourceChanged()
        this.params.p_TuKho = f.kho_id
        this.kieunhap = 1; //Nhap tu don hang
        this.Enabled.gridViewVatTu.SOLUONG = false
        this.ngay_dh = f.ngay_nhap
        this.params.p_dtpNgayDK = new Date()
        let nk = ""
        if (f.ngay_ky != ""){
          nk = " ngày " + f.ngay_ky
        }
        let tenTuKho = ''
        if (this.params.p_TuKho){
          if (this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho)){
            tenTuKho = this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).tenKho
          }
        }
        this.nddh = "Nhập vật tư hợp đồng : " + f.ma_hd + ", " + nk + ", số hóa đơn " + f.so_hd + " , từ " + tenTuKho
        
        await this.KhoiTaoNoiDung()
      }
    },
    async btnXoaVT_Click(){
      // DHSX đang comment
    },
    async btnXoaVT_ButtonClick(row){
      await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa vật tư của chứng từ này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                // let gridViewVatTu_FocusedRow = this.$refs.gridViewVatTu.getSelectedIndexs()
                // this.$refs.gridViewVatTu.deleteRecord(gridViewVatTu_FocusedRow)
                let deletedRow = this.gridVatTu.splice(row.index,1)
                let id = deletedRow.CTCT_ID
                if (id.toString()){
                  await this.XoaChiTietChungTu({ctctId: id})
                }
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async txtExcelTbiMau_Click(){
      try{
        this.loading(true)
        let dt = [{
            'LOHANG': "",
            'MA_VT': "",
            'TEN_VT': "",
            'DVI_TINH': "",
            'MA_TBI': "",
            'SERIAL': "",
            'SOLUONG': "",
            'DONGIA': "",
            'GHICHU': "",
            'SERIAL_GP': "",
          }]
        if (this.diachi_mac){
          dt[0].MAC = ''
        }
        if (this.b_mathung){
          dt[0].MATHUNG = ''
        }


        if (this.vchungtu_id > 0 && this.dtVatTu.length){
          let a = this.dtVatTu.filter(p=>p.LOAITBI_ID.toString() == "2")
          // quy bo sung, neu co du lieu thi xoa header mac dinh
          a.length ? dt = [] : ''
          for (var item of a){
            let sl = parseInt(item.SOLUONG)
            for (let i = 0; i < sl; i++){
              let r = {
                LOHANG: item.LOHANG.toString(),
                MA_VT: item.MA_VT.toString(),
                TEN_VT: item.TEN_VT.toString(),
                DVI_TINH: item.DVI_TINH.toString(),
                MA_TBI: "",
                SERIAL: "", 
                SOLUONG: 1,
                DONGIA: parseInt(item.DONGIA),
                GHICHU: "",
                SERIAL_GP: "",
              }
              if (this.diachi_mac){
                r.MAC = ""
              }
              if (this.b_mathung){
                r.MATHUNG = ""
              }
              dt.push(r)
            }
          }

          // ---------------- ------------------
          if (this.nghiepvu_id_chungtu == "288" && this.ma_pda_chungtu != ""){          
            var loc_kq_ims = await this.getDSSerialNullIMS({
              chungTuId: this.vchungtu_id,
              data: dt,
              kieu: 1
            })
            dt = loc_kq_ims;
          }
          // ---------------- ------------------
        }

        let data1 = XLSX.utils.json_to_sheet(dt);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "ThietBi"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "fileNhapTbi.xlsx");
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async btnExcelTbi_Click(){
      if (this.vchungtu_id == 0){
        this.$toast.error("Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!")
        return
      }
      if(this.Enabled.tsbtnNhapMoi){
        // ------------ ------------------
        if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")
            || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282")){
            this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được sửa");
            return;
        }
        // ------------ ------------------
        if (this.soph.startsWith("QT")){
            this.$toast.error("Chứng từ quyết toán không thể sửa!")
            return
        }
        let KTra_SuaThuHoi = await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})
        if(KTra_SuaThuHoi.includes("ko")){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return
        }
        if (this.kiemtra_quyen){
          var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
          if (!ds_dt.length ){
            this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
            return
          }
          else{
            if(ds_dt[0] != null  && ds_dt[0] != ""){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể nhập thiết bị")
              return
            }
          }
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
        }
      }

      if (!this.dtVatTu.length){
        this.$toast.error("Bạn chưa nhập vật tư chứng từ! Hãy nhập vật tư chứng từ và ghi lại, trước khi nhập thiết bị!") 
        return
      }
      // this.$refs.fileUpload.$el.click()
      document.getElementById('fileUpload').click()
      // đọc file
      // OpenFileDialog
    },

    async btnExcelVT_Click(){     
      document.getElementById('fileUploadVT').click()      
    },

    async onChangeVT() {
      this.arrayImportFile.filexcel = this.$refs.fileUploadVT.$refs.input.files[0]
      let json_list = []
      try {
        this.loading(true)
        if (this.checkfile()) {
          let tencot
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: "binary" })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers  = data.shift() // bỏ dòng header đầu tiên trong data
            
            // then build the json for each row
            var result = data.map(function(a) {
                var jsonRow = {};
                a.forEach(function(cellValue, cellIndex) { 
                    jsonRow[headers[cellIndex]] = cellValue;
                });
                headers.forEach(b=> {
                  if (!Object.keys(jsonRow).includes(b)){
                    jsonRow[b] = ''
                  }
                })
                return jsonRow;
            });
            
            var ketQua = await this.uploadFileVatTu({
              kieu: 1,
              chungtu_id: this.chungtu_id,
              kho_id:this.params.p_TuKho,
              ngay_ct: this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss'),
              ds_vattu: result
            })
            if('ERROR' in ketQua &&  ketQua.ERROR.length > 0){
              this.$toast.error("Có " + ketQua.ERROR.length + " vật tư thêm vào không thành công!" )
              await this.SaveXlsAndOpenFile("VatTuLoi.xlsx", "sheet1", ketQua.ERROR)
            }
            if('OK' in ketQua &&  ketQua.OK.length > 0){
              ketQua.OK.forEach(item=>{
                item.SOLUONG = parseFloat(item.SOLUONG)
                item.DONGIA = parseFloat(item.DONGIA)
                item.TIEN = parseInt(item.TIEN)
              })
              this.gridVatTu = ketQua.OK
              this.gridVatTu_DataSourceChanged()          
              console.log(this.gridVatTu , " = this.gridVatTu")
              this.$refs.gridViewVatTu.grid.refreshColumns()
              this.$toast.success("Thêm " + ketQua.OK.length + " vật tư thành công!" )
            }
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error)
        return
      } finally {
        this.$refs.fileUpload.reset()
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!")
        return false
      }
    
      if (
        this.arrayImportFile.filexcel.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" &&
        this.arrayImportFile.filexcel.type != "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!")
        return false
      }
      return true
    },
    async onChange() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload.$refs.input.files[0]
      
      let json_list = []
      try {
        this.loading(true)
        if (this.checkfile()) {
          let tencot
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: "binary" })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers  = data.shift() // bỏ dòng header đầu tiên trong data
            
            // then build the json for each row
            var result = data.map(function(a) {
                var jsonRow = {};
                a.forEach(function(cellValue, cellIndex) { 
                    jsonRow[headers[cellIndex]] = cellValue;
                });
                headers.forEach(b=> {
                  if (!Object.keys(jsonRow).includes(b)){
                    jsonRow[b] = ''
                  }
                })
                return jsonRow;
            });
            
            // --------------- --------------
            console.log(result , " = result")
            let DSThietBi = []
            for(let e of result){
              if(e.MA_VT == "" && e.SERIAL == "" == e.LOHANG == ""){
                continue
              }
              DSThietBi.push(e)
            }
            if (this.nghiepvu_id_chungtu == "288" && this.ma_pda_chungtu != ""){
                var go = await this.CapNhatSerialNullIMS({
                  "kieu": 0,
                  "data": DSThietBi,
                  "chungTuId": this.vchungtu_id
                })
                if (go.toString() == "1"){
                  await this.Load_ds_thietbi()
                }
                else{
                  this.$toast.error(go)
                }
            }
            // --------------- --------------
            else{
              await this.CapNhatExcelTbi_v2(DSThietBi)
            }
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error)
        return
      } finally {
        this.$refs.fileUpload.reset()
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async CapNhatExcelTbi_v2(dt){
      try{
        if (this.diachi_mac && dt.length && !dt[0].hasOwnProperty("MAC")){
          this.$toast.error("Không tìm thấy cột địa chỉ MAC. Bạn hãy lấy lại file mẫu")
          return
        }
        this.loading(true)
        let loi = ""
        
        let updateSerialArray = []
        for (let item of dt){
          let data = {
            "loHang": item.LOHANG,
            "maVatTu": item.MA_VT,
            "tenVatTu": item.TEN_VT,
            "donViTinh": item.DVI_TINH,
            "maThietBi": item.MA_TBI,
            "serial": item.SERIAL,
            "soLuong": 1,
            "ghiChu": item.GHICHU,
            "serialGp": item.SERIAL_GP,
            "mac": this.diachi_mac ? item.MAC : "" ,
            "maThung": this.b_mathung ? item.MATHUNG : ""
          }
         

          updateSerialArray.push(data)
        }
        
        // try {
        //   await this.CapNhatSerialV2_1(updateSerialArray) 
        //   } catch (error) {
        //   loi = error
        // }
        
        
        let dt1 = []
       
        dt1 = await this.CapNhatSerialV2({
          "chungTuId": this.vchungtu_id,
          "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
          "mayCn": this.$auth.getUserName(),//ttnd.may_cn
          "ipCn": "10.59.90.123",//ttnd.ip
          "listSerialInfo": updateSerialArray
        })
       
        // if (loi != ""){
        //   this.$toast.error("File excel đọc vào bị lỗi " + loi)
        //   return
        // }
        
        if (dt1.length){
          this.$toast.error("Có " + dt1.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!")
          await this.SaveXlsAndOpenFile("fileSaiDuLieu.xlsx", "sheet1", dt1)
        }else{
          await this.Load_ds_thietbi()
          await this.load_tinhtrang(1)
        }        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async SaveXlsAndOpenFile(fileName,sheetName,exportData){
      if (!exportData.length){
        this.$toast.error("Không có bản ghi nào để xuất!")
        return 
      }
      let data1 = XLSX.utils.json_to_sheet(exportData);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, sheetName); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, fileName);
    },
    async CapNhatExcelTbi(dt){
      // try {
        //Kiểm tra dữ liệu thiết bị
        let dtSai = []
        let dt1 = await this.getDSThietBiChungTu({chungTuId: 0})
        for (let item of dt){
          let ghichu = ""
          let vattu_id = 0
          let dvitinh_id = 0
          let vctct_id = 0
          let vthietbi_id = 0
          let vthietbi_cha_id = 0
          let ma_vt = item["MA_VT"].toString().trim()
          let lohang = item["LOHANG"].toString().trim()
          let ma_tbi = item["MA_TBI"].toString().trim()
          let serial = item["SERIAL"].toString().trim()
          let dvi_tinh = item["DVI_TINH"].toString().trim()
          let soluong = 0
          let dongia = 0
          let ten_vt = item["TEN_VT"].toString().trim()

          if (item["SOLUONG"].toString()){
            soluong = parseInt(item["SOLUONG"]) 
          }
          //Kiểm tra mã vật tư có tồn tại trong db không
          let dtkt = await this.KiemTraMaVatTu({maVt: ma_vt})
          if (!dtkt.length){
            ghichu = "Mã vật tư không tồn tại; "
          }
          else{
            vattu_id = parseInt(dtkt[0]["vatTuID"])
            ten_vt = dtkt[0]["tenVatTu"]
          }
          // Kiểm tra đơn vị tính
          dtkt = await this.KiemTraDonViTinh({donViTinh: dvi_tinh.toUpperCase().trim()})
          if (!dtkt.length){
            ghichu = "Đơn vị tính không tồn tại; "
          }
          else{
            dvitinh_id = dtkt[0]["dviTinhId"]
            dvi_tinh = dtkt[0]["dviTinh"]
          }
          //Kiểm tra mã vật tư, lô hàng có tồn tại trong chứng từ không
          dtkt = await this.KiemTraTonVatTuCT({
            "chungTuId": this.vchungtu_id,
            "vatTuId": vattu_id,
            "loHang": lohang
          })
          if (!dtkt.length){
            ghichu = "Vật tư và lô hàng không tồn tại trong chứng từ; "
          }
          else{
            vctct_id = dtkt[0]["chiTietCtId"]
            dongia = dtkt[0]["donGia"]
          }
          if (ghichu){
            let rsai = []
            for (let i = 0; i < dt.length;i++){
              rsai[i] = item[i]
            }
            // rsai["GHICHU_LOI"] = ghichu
            dtSai.push(...rsai)
          }
          else{
            let r = {}
            r["CTCT_ID"] = vctct_id
            r["LOHANG"] = lohang
            r["VATTU_ID"] = vattu_id
            r["TEN_VT"] = ten_vt
            r["DVITINH_ID"] = dvitinh_id
            r["DVI_TINH"] = dvi_tinh
            r["MA_TBI"] = ma_tbi
            r["SERIAL"] = serial
            r["SOLUONG"] = soluong
            r["DONGIA"] = dongia
            dt1.push(r)
          }
        }
        for (let table of dt1){
          await this.CapNhatChungTuThietBi({
            "ctctId": table.CTCT_ID,
            "thietBiId": table.THIETBI_ID || 0,
            "thietBiChaId": table.THIETBI_CHA_ID || 0,
            "maThietBi": table.MA_TBI,
            "serial": table.SERIAL,
            "soLuong": table.SOLUONG,
            "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
            "mayCn": this.$auth.getUserName(),//ttnd.may_cn
            "ipCn": "10.59.90.123"//ttnd.ip
          })
        }
        await this.Load_ds_thietbi()
        if (dtSai.length){
          this.$toast.error("Có " + dtSai.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!")
          await this.SaveXlsAndOpenFile('fileSaiDuLieuThietBi.xlsx',"sheet1",dtSai)
        }
        
      // } catch (error) {
      //   this.$toast.error("File excel không đúng định dạng!: ")
      // }
    },
    async btnFileExelMauVT_Click(){
      let dt= [{
        MA_DH: '',
        LOHANG: '',
        MA_VT: '',
        TEN_VT: '',
        DVI_TINH: '',
        MA_TBI: '',
        SERIAL: '',
        SOLUONG: 0,
        DONGIA: 0,
        SOTHANG_PB: 0,
      }]
      await this.SaveXlsAndOpenFile("fileNhapVtu.xlsx","ThietBi",dt)
    },
    async btnCapNhatExcelVtu(dt){
      // try {
        //Kiểm tra dữ liệu thiết bị
        let dtSai = []
        for (let item of dt){
          let ghichu = ""
          let vattu_id = 0
          let dvitinh_id = 0
          let vctct_id = 0
          let thietbi_id = 0
          if(item.hasOwnProperty('THIETBI_ID')){
            thietbi_id = parseInt(item.THIETBI_ID)
          }
          let ma_vt = item["MA_VT"].toString().trim()
          let lohang = item["LOHANG"].toString().trim()
          let ma_tbi = item["MA_TBI"].toString().trim()
          let serial = item["SERIAL"].toString().trim()
          let dvi_tinh = item["DVI_TINH"].toString().trim()
          let soluong = 0
          let dongia = 0
          let ten_vt = item["TEN_VT"].toString().trim()

          if (item["SOLUONG"].toString()){
            soluong = parseInt(item["SOLUONG"]) 
          }
          if (item["DONGIA"].toString()){
            soluong = parseInt(item["DONGIA"]) 
          }
          //Kiểm tra mã vật tư có tồn tại trong db không
          let dtkt = await this.KiemTraMaVatTu({maVt: ma_vt})
          if (!dtkt.length){
            ghichu = "Mã vật tư không tồn tại; "
          }
          else{
            vattu_id = parseInt(dtkt[0]["vatTuID"])
            ten_vt = dtkt[0]["tenVatTu"]
          }
          // Kiểm tra đơn vị tính
          dtkt = await this.KiemTraDonViTinh({donViTinh: dvi_tinh.toUpperCase().trim()})
          if (!dtkt.length){
            ghichu = "Đơn vị tính không tồn tại; "
          }
          else{
            dvitinh_id = dtkt[0]["dviTinhId"]
            dvi_tinh = dtkt[0]["dviTinh"]
          }
          if (ghichu){
            let rsai = []
            for (let i = 0; i < dt.length;i++){
              rsai[i] = item[i]
            }
            // rsai["GHICHU"] = ghichu
            dtSai.push(...rsai)
          }
          else{
            //Nhập thiết bị
            if(!this.dtThietBi.length){
              this.dtThietBi = await this.getDSThietBiChungTu({chungTuId: 0})
            }
            let r = {}
            r["CTCT_ID"] = vctct_id
            r["THIETBI_ID"] = thietbi_id
            r["LOHANG"] = lohang
            r["VATTU_ID"] = vattu_id
            r["TEN_VT"] = ten_vt
            r["DVITINH_ID"] = dvitinh_id
            r["DVI_TINH"] = dvi_tinh
            r["MA_TBI"] = ma_tbi
            r["SERIAL"] = serial
            r["SOLUONG"] = soluong
            r["DONGIA"] = dongia
            this.dtThietBi.push(r)
          }
        }
        // Cập nhật vật tư
         var vt = dtThietBi.filter(p=>
         p["LOHANG"].toString() == lohang.toString() && 
         p["VATTU_ID"].toString() == vattu_id.toString() && 
         p["MA_VT"].toString() == ma_vt.toString() && 
         p["TEN_VT"].toString() == ten_vt.toString() && 
         p["DVITINH_ID"].toString() == dvitinh_id.toString() && 
         p["DVI_TINH"].toString() == dvi_tinh.toString() && 
         p["MA_TBI"].toString() == ma_tbi.toString() &&
         p["SERIAL"].toString() == serial.toString() &&
         p["SOLUONG"].toString() == soluong.toString() &&
         p["DONGIA"].toString() == dongia.toString()
         )
         for (let item of vt){
            if (this.dtVatTu.length){
              this.dtVatTu = getDSTraCuuVatTuChungTu({chungTuId: 0})
            }
            let r = {}
            r["CTCT_ID"] = 0
            r["LOHANG"] = item.LOHANG
            r["VATTU_ID"] = item.VATTU_ID
            r["SOLUONG"] = item.SOLUONG
            r["DONGIA"] = item.DONGIA
            r["TIEN"] = parseInt(parseFloat(item.DONGIA)*parseFloat(item.SOLUONG))
            r["VAT"] = parseFloat(item.DONGIA)*parseFloat(item.SOLUONG)*0.1
            r["MA_VT"] = item.MA_VT
            r["TEN_VT"] = item.TEN_VT
            r["DVITINH_ID"] = item.DVITINH_ID
            r["DVI_TINH"] = item.DVI_TINH
            this.dtVatTu.push(r)  
         }

         if(this.dtSai.length){
           this.$toast.error("Có " + dtSai.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!")
           await this.SaveXlsAndOpenFile("fileSaiDuLieuThietBi.xlsx","sheet1",dtSai)
         }
         this.gridVatTu = this.dtVatTu
         this.gridVatTu_DataSourceChanged()
         this.gridThietBi = this.dtThietBi

         this.gridThietBi_ConvertData()

      // } catch (error) {
      //   this.$toast.error("File excel không đúng định dạng!:")
      // }
    },
    gridThietBi_ConvertData(){
      this.gridThietBi.forEach(item=>{
        item.SOLUONG = parseFloat(item.SOLUONG)
        item.DONGIA = parseFloat(item.DONGIA)
        item.TIEN = parseInt(item.TIEN)
      })
    },
    async btnNhapExcelVT_Click() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload.$refs.input.files[0]
      
      let json_list = []
      try {
        this.loading(true)
        if (this.checkfile()) {
          let tencot
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: "binary" })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers  = data.shift() // bỏ dòng header đầu tiên trong data
            
            // then build the json for each row
            var result = data.map(function(a) {
                var jsonRow = {};
                a.forEach(function(cellValue, cellIndex) { 
                    jsonRow[headers[cellIndex]] = cellValue;
                });
                return jsonRow;
            });
            
            await this.btnCapNhatExcelVtu(result)
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error)
        return
      } finally {
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async btnXoaHetVT_Click(){
      if(!this.gridVatTu.length){
        this.$toast.error("Không có dữ liệu vật tư để xóa")
        return
      }
      if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)){ return }
      if (! await this.KiemTraHanChotCT()){ return }
      
      if (this.Enabled.tsbtnNhapMoi){
        // -------------- ---------------------
        if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")
            || this.nghiepvu_id_chungtu == "288" || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282")){
          this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được sửa");
          return;
        }
        // -------------- ---------------------
        if (! await this.KiemTraNguoiCN()){
          this.$toast.error("Bạn không được sửa chứng từ của người khác!")
          this.$refs.cboLoaiChungTu.focusIn()
          return
        }
      }
    
      if (this.Enabled.tsbtnNhapMoi){
        if(this.kiemtra_quyen){
          var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
          if (!ds_dt.length){
            this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
            return
          }
          else{
            if(ds_dt[0] != null && ds_dt[0] != ""){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
              return
            }
          }
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
        }
        if (this.soph.startsWith("QT")){
          this.$toast.error("Chứng từ quyết toán không thể sửa!")
          return
        }
        if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko")){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return
        }
        let s = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        if (s.toString() != "1"){
          this.$toast.error(s.toString())
          return
        }
      }

      await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa hết vật tư của chứng từ này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                if (!this.Enabled.tsbtnNhapMoi){
                  // for (int i = 0; i < gridViewVatTu.RowCount; )
                  //   gridViewVatTu.DeleteRow(i);
                  this.gridVatTu = []
                  this.gridVatTu_DataSourceChanged()
                  return
                }

                if (this.ttph_id == 4){
                  let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                  if (s != "1"){
                    this.$toast.error(s.toString())
                    return
                  }
                  s = await this.XoaPhieuDaHoanThanh({
                    "chungTuId": this.vchungtu_id,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                    "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                    "ipCn": "10.59.90.123"//ttnd.ip
                  })
                  if (s != "1"){
                    this.$toast.error("Có lỗi trong quá trình xóa phiếu!")
                    return
                  }
                }

                let k = await this.XoaChungTuBaoHanh({
                  "kieu": 2,
                  "chungTuId": this.vchungtu_id,
                  "chungTuCtId": 0,
                  "thietBiId": 0
                })
                if (k != "1"){
                  this.$toast.error(k)
                  return
                }
                let id = this.gridviewChungTu_FocusedRow.CTCT_ID
                this.dtVatTu = []
                this.dtThietBi = []

                await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
                let cttemp_id = this.vchungtu_id
                await this.Load_ds_chungtu()
                await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", cttemp_id)
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async KIEMTRA_THAYDOI(vct_id){
      // try {
          var xttph = await this.getThongTinTrangThaiPhieu({chungTuId: vct_id})
          if (!xttph.length){
            this.$toast.error("Không lấy được thông tin trạng thái phiếu")
            return false
          }
          if (xttph.length){
            if(this.ttph_id.toString() != xttph[0].ttphId.toString()){
              this.$toast.error("Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin")
              return false
            }
            
            if(xttph[0].maMd.toString() == this.MUCDICH_VT.MUASAM.toString()){
              this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép sửa")
              return false
            }
            if(xttph[0].maMd.toString() == this.MUCDICH_VT.CHUYEN_CP.toString()){
              this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép sửa")
              return false
            }
                        
            return true
          }
          return true

      // } catch (error) {
      //   this.$toast.error("Có lỗi khi lấy thông tin trạng thái phiếu")
      //   return false
      // }
    },
    async KIEMTRA_THAYDOI_V2(vct_id,cap){
      // try {
        var xttph = await this.getThongTinTrangThaiPhieu({chungTuId: vct_id})
        if(!xttph.length){
          this.$toast.error("Không lấy được thông tin trạng thái phiếu")
          return false
        }
        if(xttph.length){
          if(this.ttph_id.toString() != xttph[0].ttphId.toString()){
            this.$toast.error("Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin")
            return false
          }
          return true
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi lấy thông tin trạng thái phiếu" + " - chúng từ " + this.soph)
      //   return false
      // }
    },
    // Nếu nhập lẻ + kiểu là điều chỉnh ==> tự động hoàn thiện phiếu xuất (là phiếu cha) trước, sau đó hoàn thiện phiếu nhập
    
    async tsbtnHoanThanh_Click(){
      try{
        this.loading(true)    
        if (! await this.KiemTraHanChotCT()){ return }
        if(!this.params.p_DenKho.toString()){
          this.$toast.error("Không có kho đến không thể hoàn thành")
          return
        }

        if(!this.gridVatTu.length){
          this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
          return 
        }
        if (this.kiemtra_quyen){
          if (! await this.KIEMTRA_QUYEN_XN(3, this.kho_nhan_goc)){ return }
          var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
          if(ds_dt.length == 0 ){
            this.$toast.error("Không có dữ liệu ngày nhận không thể cập nhật ngày gửi của kho")
            return
          }
          else{
            if(ds_dt[0] == null || ds_dt[0] == ""){
              this.$toast.error("Chưa có ngày nhận thực tế vật tư không thể hoàn thành")
              return
            }
          }
         
          let nv_th = this.gridviewChungTu_FocusedRow.NGUOI_TH == null ? "" : this.gridviewChungTu_FocusedRow.NGUOI_TH
          if (nv_th != "" && nv_th != this.$auth.getUserName()){//ttnd.ma_nd
            this.$toast.error("Bạn không được quyền hoàn thiện chứng từ do người khác xác nhận")
            return
          }
        }

        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }

        var t = await this.getIDTrangThaiPhieu({chungTuId: this.vchungtu_id})
        if(!t.length){
          this.$toast.error("Không lấy được thông tin chứng từ để hoàn thành")
          return
        }
        if (t[0].toString() == "4"){
          this.$toast.error("Chứng từ đã hoàn thành không thể hoàn thành nữa. Bạn hãy lấy lại thông tin")
          return
        }
        // bổ sung để callback => quy
        let isContinue = true
        var kiemtra_canhbao = await this.KiemTraCanhBaoHoanThienCT({chungTuId: this.vchungtu_id})
        if (kiemtra_canhbao != "1"){
          if (kiemtra_canhbao == "QUES"){
            this.loading(false)
            var isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm("Có vật tư chưa gán hợp đồng. Bạn có muốn tiếp tục hoàn thiện chứng từ ?", {
                title: "Thông báo",
                centered: true,
                size: "md",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              })
              .then((value) => {
                if (value) {
                  isBoxConfirm = true;
                } else {
                  return;
                }
              });

            if (!isBoxConfirm) {
              return;
            }            
            this.loading(true)
              
          }
          else if (kiemtra_canhbao == "NOTI"){
            isContinue = false
            this.$toast.error("Có vật tư chưa gán hợp đồng. Không thể hoàn thiện chứng từ !")
            return
          }
          else{
            isContinue = false
            this.$toast.error(kiemtra_canhbao)
            return
          }
        }
        if (isContinue){
          var kiemtra_qtth = await this.KiemTraHoanThanhCT({chungTuId: this.vchungtu_id})
          if (kiemtra_qtth.toString() != "1"){
            this.$toast.error(kiemtra_qtth.toString())
            return
          }
          let s = await this.KiemTraHoanThienCT({chungTuId: this.vchungtu_id})
          
          if (s == "1"){
            
            let a = await this.HoanThienChungTu({
              "chungTuId" : this.vchungtu_id,
              "nhanVienId" : this.$auth.getNhanVienID(),
              "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
              "mayCN" : this.$auth.getUserName(),//ttnd.may_cn
              "ipCN" : "10.59.90.123"//ttnd.ip
              })
            
            this.$toast.success("Đã hoàn thiện chứng từ thành công!")
            if (this.params.p_ckbSendSMS){
              try {
                await this.GuiSMSChungTu({
                  tuDong: 1,
                  chungTuId: this.vchungtu_id,               
                })
              } catch (error) {
                this.$toast.error("Có lỗi khi send smsm tự động")
              }
            }
            let vchungtu_tmp_id = this.vchungtu_id
            await this.Load_ds_chungtu()
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
            await this.Load_ds_vattu()
            await this.Load_ds_thietbi()

          }
          else{
            this.$toast.error(s)
          }
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async simpleButton1_Click(){
      try{
        if (this.Enabled.tsbtnNhapMoi){
          if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")
          || this.nghiepvu_id_chungtu == "288" || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282")){
              this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được sửa");
              return;
          }
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có chắc chán muốn xóa hết thiết bị của chứng từ này!", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }

        this.loading(true)
        if (! await this.KiemTraHanChotCT()){ return }

        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id)) { return }

        let KTra_SuaThuHoi = await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})
        if(KTra_SuaThuHoi.includes("ko")){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return
        }
        
        if (this.kiemtra_quyen){
          var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
          if(!ds_dt.length ){
            this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
            return
          }
          else{
            if(ds_dt[0] != null && ds_dt[0] != ""){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
              return
            }
          }
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return}

        }

        if (this.ttph_id == 4){

          if (this.vts_sohoa == "1")
          {
            this.$toast.error("Phiếu đã hoàn thành, bạn không thể thay đổi!");
            return;
          }


          let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
          if (s != "1"){
            this.$toast.error(s.toString())
            return
          }
          s = await this.XoaPhieuDaHoanThanh({
            "chungTuId": this.vchungtu_id,
            "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
            "mayCn": this.$auth.getUserName(),//ttnd.may_cn
            "ipCn": "10.59.90.123",//ttnd.ip
          })
          if (s != "1"){
            this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
            return
          }
        }

        let k = await this.XoaChungTuBaoHanh({
            "kieu": 3,
            "chungTuId": this.vchungtu_id,
            "chungTuCtId": 0,
            "thietBiId": 0
          })
        if (k.toString() != "1"){
          this.$toast.error(k)
          return
        }
        this.dtThietBi = []
        await this.XoaTatCaChungTuThietBi({chungTuId: this.vchungtu_id})
        
        // quy sửa
        await this.Load_ds_thietbi()
        let cttemp_id = this.vchungtu_id
        await this.Load_ds_chungtu()
        await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", cttemp_id)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
       
    },
    async btnXuatExcelVT_Click(){
      await this.SaveXlsAndOpenFile("VatTu-" + this.params.p_txtSoPhieu + ".xlsx", "sheet1", this.dtVatTu)
    },
    async simpleButton2_Click(){
      await this.SaveXlsAndOpenFile("ThietBi-" + this.params.p_txtSoPhieu.replace("/", "-") + ".xls", "sheet1", this.dtThietBi)
    },
    // async tsbtnSinhSerial_Click(){
    //   if (!!this.gridViewVatTu_FocusedRow){ return }
    //   var f = this.$refs.frmTaoSerial
    //   f.vctct_id = vctct_id

    //   f.ShowDialog()
      
    // },
    cboDonViNhan_EditValueChanged(){

    },
    async tsbtnPhieuNhap_Click(kieu){      
      try{
       
        if(this.vchungtu_id == 0 || this.vchungtu_id == null){
          this.$toast.error("Không có dữ liệu in phiếu");
          return
        }
        this.loading(true)
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=${kieu}`,
          method: "POST",
          responseType: "blob",
          data: {
            chungTuId: this.vchungtu_id,
            nhanVien: "",
            donVi: "",
            maBc: "PNK"     
          }
        }).then((response) => {
          if(kieu == 'pdf'){
            previewPrint(response.data);
          }else{
            const url = URL.createObjectURL(
              new Blob([response.data], {
                type: "application/vnd.ms-excel",
              })
            );
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", "PhieuNhap_" + this.params.p_txtSoPhieu);
            document.body.appendChild(link);
            link.click();
          }
          
        });
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
   
    async onClickXacNhanInPhieu(){
      this.$bvModal.hide('popupTenNguoiNhan');
      if(this.maBC == "PXK"){

      }else if(this.maBC == "PCK"){

      }
      else if(this.maBC == "PTU"){

      }
      this.loading(true)
      const baseUrl = process.env.API;
      await this.axios({
        url:
          `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: "",
          donVi: "" ,
          maBc: this.maBC  
        }
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false)
    },
    async tsbtnPhieuXuat_Click(kieu){
      try{
        if(this.vchungtu_id == 0 || this.vchungtu_id == null){
          this.$toast.error("Không có dữ liệu in phiếu");
          return
        }
      
        if(this.$auth.getPhanVungID().toString() == "54" || this.$auth.getMaTinh().toString() == "TGG"){
          this.tenNguoiNhan = ""
          this.maBC = "PXK"
          this.$bvModal.show('popupTenNguoiNhan')
        }
        else{
          this.loading(true)
          const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=${kieu}`,
            method: "POST",
            responseType: "blob",
            data: {
              chungTuId: this.vchungtu_id,
              nhanVien: "",
              donVi: "",
              maBc: "PXK"  
            }
          }).then((response) => {
            if(kieu == 'pdf'){
              previewPrint(response.data);
            }else{
              const url = URL.createObjectURL(
                new Blob([response.data], {
                  type: "application/vnd.ms-excel",
                })
              );
              const link = document.createElement("a");
              link.href = url;
              link.setAttribute("download", "PhieuXuat_" + this.params.p_txtSoPhieu);
              document.body.appendChild(link);
              link.click();
            }
          });
          this.loading(false)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async txtQuetSerial_KeyPress(){
      // ---------------- --------------
      if (this.Enabled.tsbtnNhapMoi){
        if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")
        || this.nghiepvu_id_chungtu == "288" || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282")){
            this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được điều chỉnh");
            return;
        }
      }
      // ---------------- --------------
      let seri = this.params.p_txtQuetSerial.trim().toUpperCase()
      
      let vkho_id = parseInt(this.params.p_TuKho)
      // Kiểm tra seri có tồn tại trong hệ thống không.
      let dt = await this.KiemTraSerialTonTai({serial: seri})
      if(!dt.length){
        this.$toast.error("Serial không tồn tại trong hệ thống!")
        return
      }
      if(!parseInt(dt[0].slTon || 0) ){
        this.$toast.error("Serial đã được cấp cho thuê bao!")
        return
      }
      if(dt[0].khoId.toString() != vkho_id){
        this.$toast.error("Serial không nằm trong kho: " + this.TenTuKho(this.params.p_TuKho))
        return
      }
      //Kiểm tra serial có đang đc cấp ở chứng từ nào đang dang dở không
      let dt1 = await this.KiemTraSerialDangCap({serial: seri})
      if(dt1.length){
        this.$toast.error("Trạng thái của serial đang được sử dụng ở chứng từ: " + dt1[0]["soPhieu"] + " ngày " + dt1[0]["ngayCT"])
        return
      }
      // Kiểm tra lô hàng của serial có tồn tại trong chứng từ này không
      let dt2 = await this.KiemTraLoHangCuaSerial({serial: seri,chungTuId: this.vchungtu_id})
      if(!dt2.length){
        this.$toast.error("Lô hàng " + dt[0]["loHang"] + " của Serial không tồn tại trong chứng từ này! ")
        return
      }
      await this.CapNhatChungTuThietBi({
        "ctctId": dt2[0].chiTietCtId,
        "thietBiId": dt[0].thietBiId || 0,
        "thietBiChaId": dt[0].thietBiChaId || 0,
        "maThietBi": dt[0].maTbi,
        "serial": seri,
        "soLuong": 1,
        "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
        "mayCn": this.$auth.getUserName(),//ttnd.may_cn
        "ipCn": "10.59.90.123"//ttnd.ip
      })
      
      await this.Load_ds_thietbi()
      
      this.params.p_txtQuetSerial = ""
    },
    async repositoryItemButtonEdit3_ButtonClick(){
      await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa thiết bị này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                this.$refs.gridViewThietBi.deleteRecord(this.gridViewThietBi_FocusedRow)
                let id = this.gridViewThietBi_FocusedRow.THIETBI_ID
                let ct_id = this.gridViewThietBi_FocusedRow.CTCT_ID
                if (id > 0){
                  await this.XoaCTThietBiTheoTBi({ctctId: ct_id,thietBiId: id})
                }
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async gridViewVatTu_CustomSummaryCalculate(){
      // DHSX comment
    },
    async KhoiTaoNoiDung(){
      
      if (this.Enabled.tsbtnNhapMoi){ return }
      let nd = ""
      if (this.kieunhap == 1){
        this.params.p_txtNoiDung = this.nddh
      }
      else{
        switch (parseInt(this.params.p_LoaiChungTu)){
          case 1:
              nd = "Nhập từ " + this.TenTuKho(this.params.p_TuKho)
              break;
          case 2:
              nd = "Xuất đến " + this.TenDenKho(this.params.p_DenKho)
              break;
          case 3:
              nd = "Chuyển đến " + this.TenDenKho(this.params.p_DenKho)
              break;
          case 4:
              nd = "Nhập CCDC từ " + this.TenTuKho(this.params.p_TuKho) + " đến " + this.TenDenKho(this.params.p_DenKho)
              break;
          case 99:
              nd = "Nhập lẻ từ " + this.TenTuKho(this.params.p_TuKho) + " đến " + this.TenDenKho(this.params.p_DenKho)
              break;
          default:
              break;
        }
        this.params.p_txtNoiDung = nd
      }
    },
    async KiemTraKho(){
      if (!this.Enabled.tsbtnNhapMoi && !this.params.p_TuKho.toString()){
        k_goc = parseInt(this.params.p_TuKho)
        if(this.$refs.gridViewVatTu.getDataRows().length){
          if(this.params.p_TuKho.toString() != this.khotu_id.toString()){
            await this.$bvModal.msgBoxConfirm(" Nếu bạn thay đổi sang kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).tenKho + " danh sách vật tư đã chọn sẽ được xóa", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  if (value){
                    this.dtThemVT = []
                    this.gridVatTu = []
                    this.gridVatTu_DataSourceChanged()
                  }
                  else{
                    this.params.p_TuKho = k_goc
                  }
              })
              .catch(err => {
                  // An error occurred
              })
          }
        }
      }

      if(this.Enabled.tsbtnNhapMoi && this.Enabled.tsbtnGhiLai){
        if(this.$refs.gridViewVatTu.getDataRows().length){
          if(this.params.p_TuKho.toString() != this.t_kho_id.toString() && this.t_kho_id != 0){
            await this.$bvModal.msgBoxConfirm("Nếu bạn thay đổi sang kho " + this.TenTuKho(this.params.p_TuKho) + " danh sách vật tư đã chọn sẽ được xóa", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  if (value){
                    this.dtThemVT = []
                    this.gridVatTu = []
                    this.gridVatTu_DataSourceChanged()
                  }
                  else{
                    this.params.p_TuKho = this.t_kho_id
                  }
              })
              .catch(err => {
                  // An error occurred
              })
          }
        }
      }
    },
    async cboTuKho_EditValueChanged(){
      // console.log('DDDDDDDDDDDD')
      // if (dataItem == null || dataItem == ''){ return }
      // this.params.p_TuKho = dataItem.khoId
      await this.KhoiTaoNoiDung()
    },
    async cboDenKho_EditValueChanged(){
      // if (dataItem == null || dataItem == ''){ return }
      // this.params.p_DenKho = dataItem.khoId
      await this.KhoiTaoNoiDung()
    },
    async btnXoatbI_Click(){
      // DHSX chưa có code
    },
    async btnXoatbI_MouseUp(){
      // DHSX chưa có code
    },
    async gridViewVatTu_SelectionChanged(){
      // DHSX chưa có code
    },
    // cần  thêm
    async gridviewVatTu_btnXoaVT(row){
      try{        
        if (row){
          this.gridViewVatTu_FocusedRow = row
          
          if (this.copyct == false){
            if (!this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU)){
              this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
              return
            }
            if (this.kiemtra_quyen){
              var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
              if(ds_dt.length == 0 ){
                this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
                return
              }else{
                if(ds_dt[0] != null && ds_dt[0] != ''){
                  this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
                  return
                }
              }
              if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
            }
            if (! await this.KiemTraHanChotCT()) { return }
            if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)) { return }

            if(this.Enabled.tsbtnNhapMoi){
              if (! await this.KiemTraNguoiCN()){
                this.$toast.error("Bạn không được sửa chứng từ của người khác!")
                this.$refs.cboLoaiChungTu.focusIn()
                return
              }
              // --------------- -------------------
              if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")
              || this.nghiepvu_id_chungtu == "288" || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282")){
                  this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được sửa");
                  return;
              }
              // --------------- -------------------                            
            }


            if(this.Enabled.tsbtnNhapMoi){
              
              if (this.soph.startsWith("QT")){
                this.$toast.error("Chứng từ quyết toán không thể sửa!")
                return
              }
              if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
                this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
                return
              }
              let z = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
              if (z.length && z[0].toString() != "1"){
                this.$toast.error(z[0])
                return
              }
            }
            let isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không ?", {
                title: "Thông báo",
                centered: true,
                size: "md",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              })
              .then((value) => {
                isBoxConfirm = value
              });

            if (!isBoxConfirm) {
              return;
            }
           
            this.loading(true)          
            if(!this.Enabled.tsbtnNhapMoi){
              this.gridVatTu.splice(this.gridViewVatTu_FocusedRow.index,1)
              return
            }
            
            if (this.ttph_id == 4){
              let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
              if (s.toString() != "1"){
                this.$toast.error(s.toString())
                return
              }
              s = await this.XoaPhieuDaHoanThanh({
                  "chungTuId": this.vchungtu_id,
                  "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                  "mayCn": "quyPc",//ttnd.may_cn
                  "ipCn": "10.59.90.123",//ttnd.ip
              })
              if (s != "1"){
                this.$toast.error("Có lỗi trong quá trình xóa phiếu! " + s)
                return
              }
            }

            let index = this.gridViewVatTu_FocusedRow
            let id = this.gridViewVatTu_FocusedRow.CTCT_ID
            let k = await this.XoaChungTuBaoHanh({
              "kieu": 2,
              "chungTuId": this.vchungtu_id,
              "chungTuCtId": id,
              "thietBiId": 0
            })
            if (k != "1"){
              this.$toast.error(k)
              return
            }
            // this.$refs.gridViewVatTu.deleteRecord(row)
            this.gridVatTu.splice(index,1)
            if(id > 0){ 
              await this.XoaChiTietChungTu({ctctId: id})
              await this.Load_ds_vattu()
              await this.Load_ds_thietbi()
            }
            // view.FocusedColumn = view.Columns[0];
            if(this.Enabled.tsbtnNhapMoi){
              let ctemp_id = this.vchungtu_id
              console.log("Load_ds_chungtu XOA")
              await this.Load_ds_chungtu()
              await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", ctemp_id)
            }
       
          }
          else{
            this.$refs.gridViewVatTu.deleteRecord(row)
            // gridViewVatTu.FocusedColumn = gridViewVatTu.Columns["LOHANG"]
            this.$refs.gridViewVatTu.grid.refreshColumns()
          }
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async FocusGridViewRow(grid, field, value){
      if (grid == 'gridviewChungTu'){
        let indexs = this.gridChungTu.findIndex(a=>a[field] != null && a[field].toString() == value.toString())
        if (indexs != -1) {
          if (!(
              (indexs + 1) > (this.$refs[grid].pageIndexData * this.$refs[grid].pageSizeData) &&
             ((indexs + 1) <= ((this.$refs[grid].pageIndexData + 1 ) * this.$refs[grid].pageSizeData))
             )){
             this.$refs[grid].$refs.pagination.gotoPage(parseInt(indexs /this.$refs[grid].pageSizeData ))
          }
          let self = this
          let ind = parseInt(indexs%this.$refs[grid].pageSizeData)
          setTimeout(() => {
            self.$refs[grid].grid.selectRow(ind,true)
          }, 1000);
        }
        
      }
    },
    async gridviewVatTu_TachCap(row){
      // quy bổ sung set gridViewVatTu_FocusedRow khi click nút tách cáp
      this.gridViewVatTu_FocusedRow = row 
      // Tach Cap khi edit row (da tao chung tu)
      if (row && this.Enabled.tsbtnNhapMoi){
        //Kiem tra de bat form
        if (this.dc_ct_noibo){
          this.$toast.error("Điều chuyển nội bộ công trình không thể tách chọn cuộn cáp")
          return
        }
        if (!this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU) && !this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
          this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
          return
        }
        if (! await this.KiemTraHanChotCT()){ return}
        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }
        if (this.Enabled.tsbtnNhapMoi){
          if (this.kiemtra_quyen){
            if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
          }
          // ------------ ------------
          if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")
          || this.nghiepvu_id_chungtu == "288" || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282")){
            this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được sửa");
            return;
          }
          // ------------ ------------
          
          if (this.soph.startsWith("QT")){
            this.$toast.error("Chứng từ quyết toán không thể sửa!")
            return
          }
          if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
            this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
            return false
          }
        }
        if(row.LOAITBI_ID.toString() != "3"){
          this.$toast.error("Chức năng này chỉ dành cho loại thiết bị là cuộn cáp")
          return
        }
        let s = await this.getKieuNhapCap({vatTuId: row.VATTU_ID.toString(), 
          khoId: this.params.p_TuKho.toString(),
          loHang: row.LOHANG })
        if (s == "1"){

          // quy them
          let isCallback = true
          // Nhập cáp
          let vctct_id = parseInt(row.CTCT_ID)
          let d = await this.getDanhSachCap({ctctId: vctct_id})
          if (d != null && d.toString() != "0"){
            await this.$bvModal.msgBoxConfirm("Cáp này đã được tách. Bạn có muốn tiếp tục tách lại số lượng cuộn không?", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  // No
                  if (!value){
                    isCallback = false
                  }
              })
              .catch(err => {
                  // An error occurred
              })
          }

          if (isCallback){
            if (this.ttph_id.toString() == "4"){
              let k = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
              if (k.toString() != "1"){
                this.$toast.error(k.toString())
                return
              }
              k = await this.XoaPhieuDaHoanThanh({
                    "chungTuId": this.vchungtu_id,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                    "mayCn": "quyPc",//ttnd.may_cn
                    "ipCn": "10.59.90.123",//ttnd.ip
                })
              if (k.toString() != "1"){
                this.$toast.error("Có lỗi trong quá trình xóa phiếu!")
                return
              }
            }

            var frm = this.$refs.frmNhapCap
            frm.vctct_id = vctct_id
            frm.tong_sl = row.SOLUONG
            frm.ten_vt = row.MA_VT + " - " + row.TEN_VT
            frm.Open()
          }
        }
        else{
          // Chọn cáp
          var frm = this.$refs.frmGanSeri
          await frm.frmGanSeri_2() // frmGanSeri(1)
          frm.functionName = 'gridViewVatTu_ShownEditor'
          
          frm.Text.title = "Chọn/Cắt cuộn cáp"
          frm.params.p_txtTenKho = this.TenTuKho(this.params.p_TuKho)
          frm.vkho_id = parseInt(this.params.p_TuKho)
          frm.vkho_nhan_id = parseInt(this.params.p_DenKho) || 0
          frm.vctct_id = parseInt(this.gridViewVatTu_FocusedRow.CTCT_ID)
          frm.soluong_tong = parseFloat(this.gridViewVatTu_FocusedRow.SOLUONG)
          frm.vattu_id = parseInt(this.gridViewVatTu_FocusedRow.VATTU_ID)
          frm.lohang = this.gridViewVatTu_FocusedRow.LOHANG
          frm.soluong_ton = await this.getTongSoLuongTonTheoCTCT({ctctId:this.gridViewVatTu_FocusedRow.CTCT_ID })
          await frm.ShowDialog()
          // XacNhan_frmGanSeri
        }
      }
    },
   
    async XacNhan_frmNhapCap(){
      var frm = this.$refs.frmNhapCap
      if (frm.xacnhan){
        let kq = await this.TachCuonCap({
          "kieu" : 1,
          "khoId" : this.params.p_TuKho,
          "khoNhanId" : this.params.p_DenKho,
          "ctctId" : frm.vctct_id,
          "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
          "mayCN" : this.$auth.getUserName(),//ttnd.may_cn
          "ipCN" : "10.59.90.123",//ttnd.ip
          "data" : JSON.stringify(frm.data)
        })
        if (kq != "1"){
          this.$toast.error(kq)
        }
        else{
          this.$toast.success("Tách cuộn cáp thành công")
        }

        if(this.Enabled.tsbtnNhapMoi){
          let ctemp_id = this.vchungtu_id
          await this.Load_ds_chungtu()
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", ctemp_id)
        }
      
      }
    },
    async XacNhan_frmGanSeri(){
      try{
        this.loading(true)
        var frm = this.$refs.frmGanSeri
        if (frm.xacnhan && frm.functionName == 'gridViewVatTu_ShownEditor'){
          if (this.Enabled.tsbtnNhapMoi){
              let ctemp_id = this.vchungtu_id
              await this.Load_ds_chungtu()
              await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", ctemp_id)
          }
        }
        if (frm.xacnhan && frm.functionName == 'btnFilter_Click'){
          await this.Load_ds_thietbi()
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async  gridViewThietBi_ShownEditor(row){
      // Xoa ThietBi
      if (row){
        this.gridViewThietBi_FocusedRow = row
        if (!this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
          this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
          return
        }
        if (! await this.KiemTraHanChotCT()){ return }
        if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)){ return }
        
        let s  = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        if (s.toString() != "1"){
          this.$toast.error(s.toString())
          return 
        }
        
        if (this.Enabled.tsbtnNhapMoi){
          // -------------- -------------
          if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")
          || this.nghiepvu_id_chungtu == "288" || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282")){
            this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được sửa");
            return;
          }
          // -------------- -------------
          
          if (this.soph.startsWith("QT")){
            this.$toast.error("Chứng từ quyết toán không thể sửa!")
            return
          }
          if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
            this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
            return false
          }
        
          if (this.kiemtra_quyen){
            var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})         
            if (!ds_dt.length ){
              this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
              return
            }
            if(ds_dt[0] != null && ds_dt[0] != ''){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
              return
            }
            if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
          }
        }

        if (this.ttph_id == 4){
            let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
            if (s.toString() != "1"){
              this.$toast.error(s.toString())
              return
            }
            s = await this.XoaPhieuDaHoanThanh({
                  "chungTuId": this.vchungtu_id,
                  "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                  "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                  "ipCn": "10.59.90.123",//ttnd.ip
              })
            if (s.toString() != "1"){
              this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
              return
            }
        }

        await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa thiết bị này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                // let row = this.gridViewThietBi_FocusedRow
                let id = parseInt(this.gridViewThietBi_FocusedRow.THIETBI_ID)
                let ct_id = parseInt(this.gridViewThietBi_FocusedRow.CTCT_ID)
                let k = await this.XoaChungTuBaoHanh({
                  "kieu": 4,
                  "chungTuId": this.vchungtu_id,
                  "chungTuCtId": ct_id,
                  "thietBiId": id
                })
                if (k != "1"){
                  this.$toast.error(k)
                  return
                }
                // this.$refs.gridViewThietBi.deleteRecord(row)
                // this.gridThietBi.splice(this.gridViewThietBi_FocusedRow.index,1)
                if (id > 0){
                  await this.XoaCTThietBiTheoTBi({ctctId: ct_id,thietBiId: id})
                                    
                  await this.Load_ds_thietbi()
                  
                }
                // view.FocusedColumn = view.Columns[0]
                let ctemp_id = this.vchungtu_id
                await this.Load_ds_chungtu()
                await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", ctemp_id)
              }
          })
          .catch(err => {
              // An error occurred
          })

      }
    },
    async tsbtnCopyCT_Click(){
      try {
        if (this.vma_md == this.MUCDICH_VT.MUASAM){
          this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép cóp")
          return
        }
        if (this.vma_md == this.MUCDICH_VT.CHUYEN_CP){
          this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép cóp")
          return
        }
        let cp_chungtu_id = 0
        cp_chungtu_id = this.vchungtu_id
        this.Clear()
        this.SetButton(1)
        this.dtCopyCT = await this.CopyChungTuV2({chungTuId: cp_chungtu_id,kieu: 1})
        this.dtCopyCTCT = await this.CopyChungTuV2({chungTuId: cp_chungtu_id,kieu: 2})
        if(this.dtCopyCT.length){
          await this.load_copy_ct(this.dtCopyCT)
        }
        if(this.dtCopyCTCT.length){
          this.dtVatTu = this.dtCopyCTCT
          this.gridVatTu = this.dtVatTu
          this.gridVatTu_DataSourceChanged()
        }
        this.copyct = true

      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async load_copy_ct(dt){
      for (let r of dt){
        if(r["LOAI"].toString() == "2"){
          this.params.p_LoaiChungTu = 99
        }
        else{
          this.params.p_LoaiChungTu = parseInt(r["LOAICT_ID"].toString())
        }

        if (r["LOAICT_ID"].toString() == "99"){
          this.kieunhap = 3
        }
        else{
          this.kieunhap = 2
        }
        this.params.p_MucDich = parseInt(r["MUCDICH_ID"].toString())
        if(r["LOAICT_ID"].toString() == "2"){
          this.params.p_TuKho = parseInt(r["KHO_GIAO_ID"].toString())
          this.params.p_DenKho = parseInt(r["KHO_NHAN_ID"].toString())
        }
        else{
          // điều kiện như có như không - Hiền hồ
          this.params.p_TuKho = parseInt(r["KHO_GIAO_ID"].toString())
          this.params.p_DenKho = parseInt(r["KHO_NHAN_ID"].toString())
        }
        this.params.p_txtNoiDung = r["ND_GIAO"].toString()
      }
    },
    async PhieuLuanChuyen_ButtonClick(){      
      if(this.params.p_MucDich != 2264){ // X.LUANCHUYEN - Luân chuyển vật tư các tỉnh
        this.$toast.error("Phải chọn mục đích X.LUANCHUYEN để dùng!")
        return
      }      
      let f = this.$refs.frmNhanPhieuLuanChuyen;
      f.parentForm = 'NhapXuat'
      f.loaiChungTuId = this.params.p_LoaiChungTu
      await f.LoadForm()
      this.$refs.popup_frmPhieuLuanChuyen.show()
    },
    CloseLuanChuyen(){
      this.$refs.popup_frmPhieuLuanChuyen.hide()
      let f = this.$refs.frmNhanPhieuLuanChuyen;
      if(f.xacnhan){
        this.params.p_txtLenhDieuChuyen = f.params.maDN
        this.params.p_LenhChuyenId = f.lenhchuyen_id
      }      
    },
    async cboLoaiChungTu_EditValueChanged(){
      try{
        this.loading(true)
        if(this.params.p_LoaiChungTu == null || this.params.p_LoaiChungTu == ''){ return }
        // --------------------- ------------------     
        if (this.sohoa_qlvt || this.sohoa_qlvt_dual){
          this.Visible.layoutCtrlPhieuDN = true
          this.Visible.layoutBtnChonPhieuDN = true
        }
        else{
          this.Visible.layoutCtrlPhieuDN = false
          this.Visible.layoutBtnChonPhieuDN = false
        }
        // --------------------- ------------------
    
        if(this.params.p_LoaiChungTu.toString() == "99"){
          this._vloai = 2
          this.Text.label7 = "NHẬP VẬT TƯ MUA LẺ"
          this.kieugoi_id = 2
          
          this.DSTuKho = await this.getDSKhoVatTuLe({nhanVienId: this.$auth.getNhanVienID(),donViId: this.$auth.getDonViID()})
          console.log(this.DSTuKho , " = dt")
      
          if (this.$auth.getMaTinh() == 'HNI'){
            this.cbo_tinhtrang = [
              {TINHTRANG_ID: 0, TINHTRANG: ''},
              {TINHTRANG_ID: 1, TINHTRANG: 'Mới'},
              {TINHTRANG_ID: 2, TINHTRANG: 'Cũ - Hỏng'},
              {TINHTRANG_ID: 3, TINHTRANG: 'Cũ - Sử dụng được'},
            ]
            this.repoTinhTrang = this.cbo_tinhtrang
            this.Enabled.xtraTabPage3 = true
          }
          
        }
        else{
          this._vloai = 1
          this.Text.label7 = "NHẬP, XUẤT, ĐIỀU CHUYỂN VẬT TƯ"
          this.kieugoi_id = 1
          this.DSTuKho = await this.getDanhMucKho({nhanVienId: this.$auth.getNhanVienID()})
         
          if (this.$auth.getMaTinh() == 'HNI'){
            this.Enabled.xtraTabPage3 = false
          }
      
        }
        await this.Loc_Kho()
        this.Enabled.tstbtnDonHang = false
        this.Enabled.tsbtnPhieuNhap = false
        this.Enabled.tsbtnPhieuXuat = false
        this.Enabled.tsbtnPhieuChuyen = false
        this.Enabled.tstPhieuTUVT = false
        
        switch (parseInt(this.params.p_LoaiChungTu)){
          case 1: 
            this.Enabled.tstbtnDonHang = true
            this.Enabled.tsbtnPhieuNhap = true
            break;
          case 2:
            this.Enabled.tsbtnPhieuXuat = true
            this.Enabled.tstbtnDonHang = false
            break;
          case 3:
            this.Enabled.tsbtnPhieuChuyen = true
            this.Enabled.tstPhieuTUVT = true
            this.Enabled.tstbtnDonHang = false
            break;
          case 99:
            this.Enabled.tsbtnPhieuNhap = true
            break;
          default:
            break;
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },
    async tsbtnPhieuChuyen_Click(kieu){
      try{
        if(this.vchungtu_id == 0 || this.vchungtu_id == null){
          this.$toast.error("Không có dữ liệu in phiếu");
          return
        }
      
        if(this.$auth.getPhanVungID().toString() == "54" || this.$auth.getMaTinh().toString() == "TGG"){
          this.tenNguoiNhan = ""
          this.maBC = "PCK"
          this.$bvModal.show('popupTenNguoiNhan')
        }
        else{
          this.loading(true)
          const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=${kieu}`,
            method: "POST",
            responseType: "blob",
            data: {
              chungTuId: this.vchungtu_id,
              nhanVien: "",
              donVi: "", 
              maBc : "PCK"   

            }
          }).then((response) => {
            if(kieu == 'pdf'){
              previewPrint(response.data);
            }else{
              const url = URL.createObjectURL(
                new Blob([response.data], {
                  type: "application/vnd.ms-excel",
                })
              );
              const link = document.createElement("a");
              link.href = url;
              link.setAttribute("download", "PhieuChuyen_" + this.params.p_txtSoPhieu);
              document.body.appendChild(link);
              link.click();
            }
          });
          this.loading(false)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async gridViewVatTu_CellValueChanged(arg,gridViewVatTu_EditRow){
      let soluong = parseInt(arg.rowData.SOLUONG)
      let dongia = parseInt(arg.rowData.DONGIA)
      let tien = parseInt(arg.rowData.TIEN)
      let vattu_id = parseInt(arg.rowData.VATTU_ID)
      

      if (arg.columnName == 'SOLUONG'){
          tien = arg.value * dongia // arg.value new edited value 
          gridViewVatTu_EditRow.TIEN = tien
          gridViewVatTu_EditRow.SOLUONG = arg.value//
      }
      if (arg.columnName == 'TIEN'){
          gridViewVatTu_EditRow.TIEN = arg.value
      }
      this.$refs.gridViewVatTu.grid.refreshColumns()
    },
    async gridViewVatTu_ValidateRow(arg){
      //modify cho phù hợp vs web
              
      let gridViewVatTu_EditRow = this.gridVatTu.find(a=>a.CTCT_ID == arg.rowData.CTCT_ID)
      
      let soluong = gridViewVatTu_EditRow.SOLUONG
      let dongia = gridViewVatTu_EditRow.DONGIA
      let tien = gridViewVatTu_EditRow.TIEN

      if (arg.columnName == 'SOLUONG'){
        soluong = arg.value
      }
      else if (arg.columnName == 'DONGIA'){
        dongia = arg.value
      }
      else if (arg.columnName == 'TIEN'){
        tien = arg.value
      }else if (arg.columnName == 'DONGIA_SUA'){
        
        let gridVatTu = this.$refs.gridViewVatTu;
        let changes = gridVatTu.getBatchChanges();
        if (changes.changedRecords[0].DONGIA_SUA < 0 ){
          changes.changedRecords[0].DONGIA_SUA = 0
          this.$toast.error("Đơn giá được sửa không âm!")          
        }
        gridVatTu.batchUpdate(changes);
        this.$refs.gridViewVatTu.grid.refreshColumns()
        return
      }
      
      let lohang = gridViewVatTu_EditRow.LOHANG
      let ctct_id = gridViewVatTu_EditRow.CTCT_ID
      let vattu_id = gridViewVatTu_EditRow.VATTU_ID
      let kho_id = this.params.p_TuKho
      
      if (arg.columnName == 'SOLUONG'){
        if (arg.value <= 0){
            this.$toast.error("Số lượng cấp phải lớn hơn 0!")
            this.$refs.gridViewVatTu.grid.closeEdit()
            this.$refs.gridViewVatTu.grid.refreshColumns()
            return
        }
      }

      let s = await this.KiemTraTonKho({
        "khoId" : kho_id,
        "soLuong" : soluong,
        "vatTuId" : vattu_id,
        "loHang" : lohang,
        "ctctId" : ctct_id,
        "ngayCT" : this.f_DateToString(this.params.p_dtpNgayDK)
      })
      if (s != "1"){
        this.$toast.error("Lỗi: Xuất quá lượng tồn kho của vật tư!")
        this.$refs.gridViewVatTu.grid.refreshColumns()
        return
      }
      
      let tien_goc = soluong * dongia;

      if (arg.columnName == 'TIEN'){
        if (this.$auth.getMaTinh() == "BTE"){
          if (Math.abs(tien - tien_goc) > 10 && !lohang.startsWith("DK")){
            this.$toast.error("Bạn không thể sửa quá số tiền so với số tiền gốc 10 đồng !")
            this.$refs.gridViewVatTu.grid.refreshColumns()
            return
          }
        }
        else if (Math.abs(tien - tien_goc) > 10){
          // đang lỗi ko refresh grid đc do freeze column
          this.$refs.gridViewVatTu.grid.refreshColumns()
          // this.$refs.gridViewVatTu.grid.refresh()
          this.$toast.error("Bạn không thể sửa quá số tiền so với số tiền gốc 10 đồng !")
          return
        }
      }

      await this.gridViewVatTu_CellValueChanged(arg,gridViewVatTu_EditRow)

      console.log(this.gridVatTu ,  "=  gridVatTu")
    },
    async gridViewVatTu_ValidatingEditor(){
      // DHSX chưa có action
    },
    async gridViewVatTu_InvalidRowException(){
      // e.ExceptionMode = ExceptionMode.Ignore;
    },
    async btnXuatTonTbi_Click(){
      try{
        this.loading(true)
        if(!this.dtVatTu.length){
          this.$toast.error("Chứng từ được tạo chưa có vật tư!")
          return 
        }
        let dt = await this.getDSTonKhoTheoCT({chungTuId: this.vchungtu_id})
        await this.SaveXlsAndOpenFile("Thietbi-" + this.vchungtu_id + ".xlsx", "sheet1", dt)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async gridViewThietBi_CellValueChanged(arg){
      try{
        let changes = this.$refs.gridViewThietBi.getBatchChanges();
        if (changes.changedRecords.length == 0) {
          return;
        }
        if (arg.columnName == "GHICHU") {
          this.loading(true)
          let ketqua = await this.CapNhatGhiChuCTTB({
            ghiChu: changes.changedRecords[0].GHICHU,
            ctctId: changes.changedRecords[0].CTCT_ID,
            thietBiId: changes.changedRecords[0].THIETBI_ID,
          })
          await this.Load_ds_thietbi()
        }
      }catch(e){
      this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async btnTimKiem_Click(){
      try{
        this.loading(true)
        await this.LamMoi()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async LamMoi(){      
      await this.Load_ds_chungtu()
      if (this.gridChungTu.length > 0){      
        await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID)
      }
      // Đã gọi trong
      // await this.Load_ds_vattu()
      // await this.Load_ds_thietbi()
      // await this.LAY_DS_VTCT_SERIAL()   
    },
    async btnFilter_Click(){
      
      if (!this.grcVT.length){
        this.$toast.error("Không có vật tư để chọn")
        this.params.p_VatTu = ""
        return
      }
      else{
        if (this.dc_ct_noibo){
          this.$toast.error("Điều chuyển nội bộ không thể chọn serial ở đây")
          return
        }
        
        if (this.kiemtra_quyen){
          var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})        
          if (ds_dt.length == 0){
            this.$toast.error("Không có dữ liệu ngày nhận không thể  lấy serial")
            return
          }
          else{
            if(ds_dt[0] != null && ds_dt[0] != ""){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể lấy serial")
              return
            }
          }
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
        }

        let vt = ""
        let lh = ""
        let dem = 0

        // thay thế với code mới
        // for (let i = 0; i < this.grcVT.DataRowCount; i++){
        //   let dr = grvVT[i]
        //   if (dr["ISCHECK"].toString() == "1"){
        //     dem += 1;
        //     vt += dr["VATTU_ID"] + ",";
        //     lh += "'" + dr["LOHANG"].toString().replace("'", "''") + "'" + ",";
        //   }
        // }
        dem = this.$refs.grvVT.getSelectedRecords().length
        vt = this.$refs.grvVT.getSelectedRecords().map(a=>a.VATTU_ID).join(",")
        lh = this.$refs.grvVT.getSelectedRecords().map(a=>a.LOHANG).join(",")
        if (dem > 0){
          // if (vt.length > 1){
          //   vt = vt.trimEnd(", ");//.ToCharArray()
          //   lh = lh.trimEnd(", ");//.ToArray()
          // }
          this.svattu_id = vt;
          this.slohang = lh;

          // this.showDSVatTu = false
          this.$bvModal.hide('popupDSVT');

          var f = this.$refs.frmGanSeri
          await f.frmGanSeri()
          this.loading(true)
          f.functionName = 'btnFilter_Click'
          f.tag = "0"
          f.vlohang = this.slohang;
          f.vvattu_id = this.svattu_id;
          f.vkho_id = this.t_kho_id;
          f.vchungtu_id = this.vchungtu_id;         
          await f.frmGanSeri_Load()
          this.$refs.popup_frmGanSeri.show()
          this.loading(false)
          // XacNhan_frmGanSeri
        }
        else{
          this.$toast.error("Không có vật tư nào được chọn")
        }
      }
      
    },
    // async XacNhan_frmGanSeri_V2(){
    //   await this.Load_ds_thietbi()
    //   if (this.Visible.xTabTongHop && this.chonvattu_lohang){
    //     await this.Load_ds_vattu()
    //     await this.NAP_DS_VT_GROUP()
    //   }
    // },
    async chkAll_Phieu_CheckedChanged(){
      this.params.p_chkAll_Phieu = !this.params.p_chkAll_Phieu 
      try{        
        this.loading(true)
        await this.LamMoi()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async ckb_xacNhan(){ 
      this.params.p_ckb_xacNhan = !this.params.p_ckb_xacNhan     
      try{        
        this.loading(true)
        await this.LamMoi()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async ckbCTCT_CheckedChanged(){
      // grcVT.CloseEditor()
    },
    async toolStripButton1_Click(){
      // DHSX chua co action
    },
    async vbtnRefresh_MouseClick(){
      try{
        this.loading(true)
        await this.Load_ds_chungtu()
        if (this.gridChungTu.length > 0){      
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID)
        }       
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async gridVatTu_DataSourceChanged(){
      if (this.kieugoi_id != 2){
        this.ReadOnly.cboLoaiChungTu = this.gridVatTu.length != 0
        this.ReadOnly.cboTuKho = this.gridVatTu.length != 0
      }
      else{
        this.ReadOnly.cboTuKho = false
        this.ReadOnly.cboLoaiChungTu = false
      }
      
    },
    async tsbtnDongBo_Click(){
      try{        

        if(this.gridChungTu.length == 0){
          this.$toast.error("Không có thông tin chứng từ để đồng bộ dữ liệu tập đoàn")
          return
        }

        if(this.listChungTuChecked.length == 0){
          this.$toast.error("Bạn chưa chọn chứng từ để đồng bộ")
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có chắc chắn muốn đồng bộ "+ this.listChungTuChecked.length + " chứng từ lên hệ thống FMIS không?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "md",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return
        }

        this.loading(true)
        var loi_excel = []
        var DSChungTuChecked = this.gridChungTu.filter(a=>this.listChungTuChecked.includes(a.CHUNGTU_ID))

        for(let e of DSChungTuChecked){
          
          if(this.$auth.getMaTinh() == "HNI"){
            let trangThai = await this.getThongTinTrangThaiPhieu({chungTuId: e.CHUNGTU_ID})
            if(trangThai.length == 0 || trangThai[0].ttphId != "4"){
              loi_excel.push({
                CHUNGTU_ID: e.CHUNGTU_ID,
                SOPHIEU: e.SOPHIEU,
                LYDO: "Chứng từ chưa hoàn thành, không thể đồng bộ."
              })  
              continue
            }
          }

          if (this.$auth.getUserName() == "" || this.$auth.getUserName() == null){
            loi_excel.push({
              CHUNGTU_ID: e.CHUNGTU_ID,
              SOPHIEU: e.SOPHIEU,
              LYDO: "Lỗi không lấy được người dùng, liên hệ admin xử lý (FE)"
            })  
            continue 
          } 

          let db_kt = await this.KiemTraDongBo({
              chungTuId: e.CHUNGTU_ID,
              tenDv: this.chondvi_fmis ?  this.params.p_DonViFMIS : "",   
              nguoiCn: this.$auth.getUserName()    
          })

          if (db_kt != "ok"){
            loi_excel.push({
              CHUNGTU_ID: e.CHUNGTU_ID,
              SOPHIEU: e.SOPHIEU,
              LYDO: db_kt
            })   
          } 
        }

        if(loi_excel.length == 0){
          this.$toast.success("Đồng bộ thành công " + DSChungTuChecked.length + "/" + DSChungTuChecked.length + " phiếu");        
        }else{
          this.$toast.error("Có " + loi_excel.length + " chứng từ lỗi");         
          await this.SaveXlsAndOpenFile('LoiDongBoFIMS.xlsx',"sheet1",loi_excel)
        }  
        await this.btnTimKiem_Click()

      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    // REM sử dụng hàm mới
    // async tsbtnDongBo_Click(){
    //   try{
    //     if (this.vchungtu_id > 0){
    //       this.loading(true)
    //       var d = await this.getChungTuTapDoan({chungTuId: this.vchungtu_id})          
    //       async function DongBo(){
    //         console.log(this.chondvi_fmis , " = this.chondvi_fmis")
    //         if(this.chondvi_fmis && (this.params.p_DonViFMIS == null || this.params.p_DonViFMIS == "")){
    //           this.$toast.error("Bạn hãy chọn đơn vị FMIS cần đồng bộ!")
    //           return
    //         }
    //         let db_kt = await this.KiemTraDongBo({
    //           chungTuId: this.vchungtu_id,
    //           tenDv: this.chondvi_fmis ?  this.params.p_DonViFMIS : "",
    //           // maNd: this.$auth.getUserName(),//ttnd.ma_nd
    //           // mayCn: this.$auth.getUserName(),//ttnd.may_cn
    //           // ip: '10.59.90.123'//ttnd.ip
    //         })
    //         if (db_kt == "ok"){
    //           this.$toast.success("Cập nhật chứng từ và đồng bộ kế toán tập đoàn thành công!")
    //         }
    //         else{
    //           if(!this.Enabled.tsbtnNhapMoi){
    //             this.loading(false)
    //             await this.$bvModal.msgBoxConfirm("Việc đồng bộ dữ liệu với kế toán tập đoàn có lỗi: " + db_kt + " Bạn có muốn lưu lại chứng từ này không !", {
    //             "okVariant": 'success',
    //               "okTitle": 'Đồng ý',
    //               "cancelTitle": 'Hủy',
    //               "centered": true,
    //               })
    //               .then(async(value) => {
    //                   this.loading(true)
    //                   if (value){
    //                     if (this.ttph_id == 4){
    //                       let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
    //                       if (s == null || s.toString() != "1"){
    //                         this.$toast.error(s.toString())
    //                         return
    //                       }
    //                       s = await this.XoaPhieuDaHoanThanh({
    //                         "chungTuId": this.vchungtu_id,
    //                         "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
    //                         "mayCn": this.$auth.getUserName(),//ttnd.may_cn
    //                         "ipCn": "10.59.90.123"//ttnd.ip
    //                       })
    //                       if (s == null || s != "1"){
    //                         this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
    //                         return
    //                       }
    //                     }
    //                     await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
    //                     this.vchungtu_id = 0
    //                     await this.XoaDuLieuChungTu({chungTuId: this.vchungtu_id})
    //                     this.$toast.success("Xóa thành công!")
    //                   }                        
    //               })
    //               .catch(err => {
    //                   // An error occurred
    //               })
    //           }
    //           this.$toast.error("Dữ liệu đồng bộ với tập đoàn có lỗi: " + db_kt)
    //         }
    //         let vchungtu_tmp_id = this.vchungtu_id
    //         await this.Load_ds_chungtu()
    //         await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
    //       }
    //       if (d.toString() != "-1"){
    //         this.loading(false)
    //         await this.$bvModal.msgBoxConfirm("Chứng từ này đã đồng bộ với tđ rồi. Bạn có chắc chắn muốn đồng bộ lại dữ liệu với tập đoàn không!", {
    //         "okVariant": 'success',
    //         "okTitle": 'Đồng ý',
    //         "cancelTitle": 'Hủy',
    //         "centered": true,
    //         })
    //         .then(async(value) => {
    //             if (value){
    //               this.loading(true)
    //               await DongBo().call(this)
    //             }
    //         })
    //         .catch(err => {
    //             // An error occurred
    //         })
    //       }
    //       else{
    //         this.loading(false)
    //         await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn đồng bộ chứng từ này dữ liệu với tập đoàn không!", {
    //         "okVariant": 'success',
    //           "okTitle": 'Đồng ý',
    //           "cancelTitle": 'Hủy',
    //           "centered": true,
    //           })
    //           .then(async(value) => {
    //               if (value){
    //                 this.loading(true)
    //                 await DongBo.call(this)
    //               }
    //           })
    //           .catch(err => {
    //               // An error occurred
    //           })
    //       }
          
    //     }
    //     else{
    //       this.$toast.error("Bạn cần chọn 1 chứng từ để đồng bộ")
    //     }
    //   }catch(e){
    //     this.$toast.error(e.data.message)
    //   }finally{
    //     this.loading(false)
    //   }
    // },
    async gridviewChungTu_CustomDrawCell(){
      // chịu ,làm sau
    },
    async tsbtnSinhSr_Click(){
      
      if (!("CTCT_ID" in this.gridViewVatTu_FocusedRow)){
        return
      }
      let vctct_id =  this.gridViewVatTu_FocusedRow.CTCT_ID //491585
      var f = this.$refs.frmTaoSerial
      f.vctct_id = vctct_id
      await f.frmTaoSerial_Load()
      await f.ShowDialog()
    },
    async toolStripMenuItem3_Click(){
      if(!this.gridThietBi.length){
        this.$toast.error("Chứng từ này ko có thiết bị")
        return
      }
      let ds = this.gridThietBi
      var q = ds.filter(a=>a.SERIAL != null && a.SERIAL.toString())
      if(q.length ){
        var f = this.$refs.frmTaoSerial
        f.kieu = 1;
        f.dsCT_ChungTu = q
        f.ShowDialog()
      }
    },
    async btnTB_ButtonClick(data){
      var frm =  this.$refs.frmChungTu_TBao      
      frm.kieu = data.SPBH > 0 ? 1 : 0
      frm.chungtu_id = data.CHUNGTU_ID //233254
      await frm.frmChungTu_TBao_Load()
      frm.ShowDialog()
    },    
    async gridviewChungTu_CustomRowCellEdit(args){
      // if (e.Column.Name == "DSTB" && e.RowHandle >= 0)
      // {
      //     if (Convert.ToInt64(gridviewChungTu.GetRowCellValue(e.RowHandle, "SPVT").toString()) > 0 ||
      //         Convert.ToInt64(gridviewChungTu.GetRowCellValue(e.RowHandle, "SPBH").toString()) > 0)
      //         e.RepositoryItem = btnTB;
      //     else
      //         e.RepositoryItem = emptyEditor;
      // }
    },
    async tstPhieuTUVT_Click(kieu){
      try{
        if(this.vchungtu_id == 0 || this.vchungtu_id == null){
          this.$toast.error("Không có dữ liệu in phiếu");
          return
        }
      
        if(this.$auth.getPhanVungID().toString() == "54" || this.$auth.getMaTinh().toString() == "TGG"){
          this.tenNguoiNhan = ""
          this.maBC = "PTU"
          this.$bvModal.show('popupTenNguoiNhan')
        }
        else{
          this.loading(true)
          const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=${kieu}`,
            method: "POST",
            responseType: "blob",
            data: {
              chungTuId: this.vchungtu_id,
              nhanVien: "",
              donVi: "",
              maBc: "PTU"
            }
          }).then((response) => {
            if(kieu == 'pdf'){
              previewPrint(response.data);
            }else{
              const url = URL.createObjectURL(
                new Blob([response.data], {
                  type: "application/vnd.ms-excel",
                })
              );
              const link = document.createElement("a");
              link.href = url;
              link.setAttribute("download", "PhieuTUVT_" + this.params.p_txtSoPhieu);
              document.body.appendChild(link);
              link.click();
            }
          });         
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tstPhieuDA07_Click(){    
      if(this.vchungtu_id == 0 || this.vchungtu_id == null){
        this.$toast.error("Chưa chọn chứng từ!")
        return
      }  
      this.DSMauIn = await this.getDSPhieuInDeAn07()     
      this.$bvModal.show('popupDSMauIn')
    },
    async XacNhanInPhieuDA07(){
      this.$bvModal.hide('popupDSMauIn')
      // let test = await this.getParamsBaoCao(this.p_mauIn)
      // console.log(test , " = test")      
      try{
        this.loading(true);
        let ds_tieuchi = [
          { 
            name: 'p_phanvung_id',
            value: this.$auth.getPhanVungID()
          },
          {
            name: 'p_chungtu_id',
            value: this.vchungtu_id
          },
          {
            name: 'p_nhanvien',
            value: this.$auth.getNhanVienID()
          },
          {
            name: 'p_donvi',
            value: this.$auth.getDonViID()
          }
        ]        
     
        if(['PCK_NOIBO','-PCK_NOIBO','BC_QLVT_0070'].includes(this.p_mauIn)){
          ds_tieuchi = [
            { 
              name: 'vphanvung_id',
              value: this.$auth.getPhanVungID()
            },
            {
              name: 'vchungtu_id',
              value: this.vchungtu_id
            },
            {
              name: 'vnhanvien',
              value: this.$auth.getNhanVienID()
            },
            {
              name: 'vdonvi',
              value: this.$auth.getDonViID()
            }
          ]     
        }
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            ma_bc: this.p_mauIn,
            ds_tieuchi: ds_tieuchi
          }                
        }).then((response) => {
          previewPrint(response.data);
        });
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async gridViewVatTu_CustomRowCellEdit(){
      // if (e.Column == TachCap && e.RowHandle >= 0)	
      // {	
      //     if (gridViewVatTu.GetRowCellValue(e.RowHandle, "LOAITBI_ID").toString() == "3")	
      //         e.RepositoryItem = btnEdit;	
      //     else	
      //         e.RepositoryItem = emptyEditor;	
      // }
    },
    async btnTaoTbi_Click(){
      if (this.vchungtu_id == 0){
        this.$toast.error("Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!")
        return 
      }
      if(this.Enabled.tsbtnNhapMoi){
        // --------------- ----------------
        //zem 288 vì có TH nhập vào serial null và add sau 
        if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "")                        
          || this.nghiepvu_id_chungtu == "286" || this.nghiepvu_id_chungtu == "282"))
        {
          this.$toast.error("Chứng từ tạo từ dự án IMS/QLTS không được sửa")
          return
        }
        // --------------- ----------------
        if (this.soph.startsWith("QT")){
          this.$toast.error("Chứng từ quyết toán không thể sửa!")
          return
        }
        if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return false
        }
        let s = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        
        if (s.toString() != "1"){
          this.$toast.error(s.toString())
          return false
        }
        if (this.ttph_id == 4){
          this.$toast.error("Chứng từ đã hoàn thành không thể tạo thiết bị")
          return
        }
      }

      if (!this.dtVatTu.length){
        this.$toast.error("Bạn chưa nhập vật tư chứng từ! Hãy nhập vật tư chứng từ và ghi lại, trước khi nhập thiết bị!")
        return
      }
      
      let ds_gan = []
      if (this.Enabled.tsbtnNhapMoi){
        if (this.kiemtra_quyen){
          if (! await this.KIEMTRA_QUYEN_XN(4, this.t_kho_id)){ return }
        }
        if(this.gridThietBi.length){
          var d_s = this.gridThietBi
          var q = d_s.filter(l=>l["SERIAL"] != null && l["SERIAL"].toString())
          if (q.length){
            ds_gan = q
          }
        }
        let mucdich_id = parseInt(this.params.p_MucDich)
        let vma_md = ""
        var d = []
        this.params.p_MucDich ? d = await this.getMaMucDichTheoID({mucDichId: mucdich_id}) : ''
        if (d.length){
          vma_md = d[0]
        }
        if(!this.gridVatTu.length){
          this.$toast.error("Danh sách vật tư chưa có. Không thể tạo được thiết bị")
          return
        }
        let dt = []
        if (this.vchungtu_id > 0 && this.dtVatTu.length){
          var a = this.dtVatTu.filter(p=>p["LOAITBI_ID"] != null && p["LOAITBI_ID"].toString() == "2")
          for (let item of a){
            var sl = parseInt(item["SOLUONG"])
            for (let i = 0; i < sl; i++){
              let r = {}
              r["LOHANG"] = item["LOHANG"].toString()
              r["MA_VT"] = item["MA_VT"].toString()
              r["TEN_VT"] = item["TEN_VT"].toString()
              r["DVI_TINH"] = item["DVI_TINH"].toString()
              r["SERIAL"] = ""
              r["SOLUONG"] = 1
              r["DONGIA"] = parseInt(item["DONGIA"])
              dt.push(r)
            }
          }
          let k = 0;

          var checkDel = false
          for(let d_ of dt){
            for(let i in ds_gan ){
              if(d_["SERIAL"].toString() == "" &&
                ds_gan[i]["LOHANG"].toString() == d_["LOHANG"].toString() &&
                ds_gan[i]["MA_VT"].toString() == d_["MA_VT"].toString() &&
                ds_gan[i]["DONGIA"].toString() == d_["DONGIA"].toString()){
                  d_["SERIAL"] = ds_gan[i]["SERIAL"].toString();
                  if (this.b_mathung){
                    d_["MATHUNG"] = ds_gan[i]["MATHUNG"].toString();
                  }  
                  console.log(ds_gan[i] , " = d_2")
                  console.log(ds_gan , " = ds_gan")
                  ds_gan.splice(i,1)
                  console.log(ds_gan , " = ds_gan remove")
                  checkDel = true
                  break;
                }
            }
            if(checkDel){
              break
            }
          }

        }
        var frm = this.$refs.GanSeri_Tbi
        if(this.nghiepvu_id_chungtu == "288" && this.ma_pda_chungtu != ""){
          var loc_kq_ims = await this.getDSSerialNullIMS({
            chungTuId: this.vchungtu_id,
            data: dt,
            kieu: 0
          })
          frm.dt = loc_kq_ims;
          frm.dt_luu = loc_kq_ims;
        }else{
          frm.dt = dt
          frm.dt_luu = dt
        }
        frm.open()
        //Trung nối form ở đây, xử lý xong gọi XacNhan_GanSeri_Tbi
        //////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////
        // if(this.nghiepvu_id_chungtu == "288" && this.ma_pda_chungtu != ""){
        //   // call API lay_ds_serial_null_ims
        //   frm.dt = loc_kq_ims
        //   frm.dt_luu = loc_kq_ims
        // }else{
        //   frm.dt = dt
        //   frm.dt_luu = dt
        // }
        // frm.dt = dt
        // frm.dt_luu = dt
        // frm.ds_ = ds_gan
        // frm.show()
        // closeGanSeri_Tbi

      }



      
    },
    async XacNhan_frmQLTS(){
      var qlts = this.$refs.frmQLTS
      if(qlts.xacnhan){
        await this.Load_ds_vattu()
        await this.Load_ds_thietbi()
      }
    },
    async closeGanSeri_Tbi(){
      var frm = this.$refs.GanSeri_Tbi
      if (frm.xacnhan){
        if(this.nghiepvu_id_chungtu == "288" && this.ma_pda_chungtu != ""){
          var go = await this.CapNhatSerialNullIMS({
            "kieu": 0,
            "data": JSON.stringify(frm.kq),
            "chungTuId": this.vchungtu_id
          })
          if (go.toString() == "1"){
            await this.Load_ds_thietbi()
          }
          else{
            this.$toast.error(go)
          }
        }
        else{
          await this.CapNhatExcelTbi_v2(frm.kq)
        }
      }
    },
    async cboTuKho_ButtonClick(){
      var checkMucDich = this.cboMucDich.filter(a=> a.mucDichId == this.params.p_MucDich && a.maMd == "N-X.DCMSL")
      if(this.params.p_LoaiChungTu == 99 && this.$auth.getPhanVungID() == 21 && checkMucDich.length > 0){ // bổ sung theo task https://cntt.vnpt.vn/browse/IT360-1037027
        if(!this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Bạn phải có chứng từ nhập rồi mới có thể chọn chứng từ xuất!")
          return
        }
        this.frmNhapDieuChinh_HNI.input.tag = 1
        this.frmNhapDieuChinh_HNI.input.vma_md = "N-X.DCMSL"
        this.frmNhapDieuChinh_HNI.input.vchungtu_gan = this.vchungtu_id
        this.isRenderNDC = true
        this.$refs.popup_frmNhapDieuChinh_HNI.show()
      }else{
        var checkMucDich = this.cboMucDich.filter(a=> a.mucDichId == this.params.p_MucDich && a.maMd == "N.VTPSIS")
        if(checkMucDich.length > 0){
          if(this.Enabled.tsbtnNhapMoi){
            if(this.ttph_id == 4){
              this.$toast.error("Chứng từ đã hoàn thành, không thể chỉnh sửa !")
              return
            }
            if(! await this.KiemTraNguoiCN()){
              this.$toast.error("Bạn không được xóa chứng từ của " + this.gridviewChungTu_FocusedRow.NGUOI_CN + " !")
              return
            }
          }else{

          }

          var f = this.$refs.frmNhapDieuChinh
          f.tag = 4
          f.vma_md = 'N.VTPSIS'
          if(this.Enabled.tsbtnNhapMoi){
            f.vchungtu_luu_tam = this.vchungtu_id
            f.vchungtu_id = this.gridviewChungTu_FocusedRow.CHUNGTU_CHA_ID
          }
          f.openNhapDieuChinh()
        }else{
          if (!!!this.params.p_MucDich || !this.params.p_MucDich.toString()){
            this.$toast.error("Bạn phải chọn mục đích")
            return
          }
          var d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
          if(d.length){
            if(d[0].toString() == "1.NBH" && this.dsQuyen.includes(99)){
              await this.LAY_CHUNGTU()
            }
            else{
              this.$toast.error("Bạn phải chọn mục đích là 1.NBH hoặc bạn không có quyền thiết bị bảo hành")
              return
            }
          }
        }  
      }    
    },
    async XacNhan_frmNhapDieuChinh(){
      try{
        var f = this.$refs.frmNhapDieuChinh
        if (f.xacnhan){ 
          this.$toast.success("Tạo chứng từ điều chỉnh thành công !")      
          await this.Load_ds_chungtu()
          if (this.gridChungTu.length > 0){      
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID)
          }             
        } 
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async XacNhan_frmNhapDieuChinh_HNI(f){
      try{        
        if (f.xacnhan){ 
          this.$toast.success("Tạo chứng từ điều chỉnh thành công !")      
          await this.Load_ds_chungtu()
          if (this.gridChungTu.length > 0){      
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID)
          }             
        } 
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async LAY_CHUNGTU(){
      try {
        var f = this.$refs.frmVatTuTrongKho
        await f.Clear()
        f.vkho_id = parseInt(this.params.p_TuKho)
        f.kieu = 2
        f.vngay_ct = this.params.p_dtpNgayDK
        f.dtLuuVT = this.dtLuuVT
        this.ten_khotu = this.TenTuKho(this.params.p_TuKho)
        f.vngay_ct = this.params.p_dtpNgayDK
        if (!this.Enabled.tsbtnNhapMoi){
          this.vchungtu_id = 0
        }
        else{
          f.vchungtu_id = this.vchungtu_id
        }
        f.functionName = 'LAY_CHUNGTU'
        f.ShowDialog()
        // XacNhan_frmVatTuTrongKho
      } catch (error) {
        this.$toast.error("Lỗi lấy chứng từ và vật tư")
      }
    },
    async tsbtnTroGiup_Click(){

    },
    async ckb_nd_CheckedChanged(){
      await this.LamMoi()
    },
    async tsbtnChiTietSR_Click(kieu){
      try{
        if(this.vchungtu_id == 0 || this.vchungtu_id == null){
          this.$toast.error("Không có dữ liệu chi tiết!");
          return
        }
        this.loading(true)
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=${kieu}`,
          method: "POST",
          responseType: "blob",
          data: {
            chungTuId: this.vchungtu_id,
            nhanVien: "",
            donVi: "",
            maBc: "CT_SRVT"   
          }
        }).then((response) => {
          if(kieu == 'pdf'){
            previewPrint(response.data);
          }else{
            const url = URL.createObjectURL(
              new Blob([response.data], {
                type: "application/vnd.ms-excel",
              })
            );
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", "PhieuSR_" + this.params.p_txtSoPhieu);
            document.body.appendChild(link);
            link.click();
          }
        });
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnSMS_Click(){
      try {
        if (!this.gridChungTu.length){
          this.$toast.error("Không có danh sách chứng từ để send sms")
          return
        }        
        
        if (this.gridviewChungTu_FocusedRow == {}){
          this.$toast.error("Bạn chưa chọn chứng từ cần send sms")
          return
        }
       

        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn có muốn send sms cho người quản lý kho nhận của chứng từ " + this.params.p_txtSoPhieu + " không", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
        })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {        
          return;
        }

        var ct_id = parseInt(this.gridviewChungTu_FocusedRow.CHUNGTU_ID)
        var kq = await this.GuiSMSChungTu({
          tuDong: 0,
          chungTuId: ct_id                 
        })
        if (kq.toString().includes("OK")){
          this.$toast.success("Send sms thành công")
        }
        else{
          this.$toast.error("Send sms thất bại " + kq)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnInPhieu_Click(){

    },
    async KIEMTRA_QUYEN_XN(kieu,kho_id){
      // try {
        //Nếu là 1 là kho giao
        var dt = await this.getThongTinQuyenTrenKho({nhanVienId: this.$auth.getNhanVienID(),khoId: kho_id})
        if (!dt.length){
          this.$toast.error("Không lấy được thông tin quyền xác nhận trên kho " + this.TenTuKho(this.params.p_TuKho))
          return false
        }
        if (kieu == 1){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận ngày gửi trên kho " + this.TenTuKho(this.params.p_TuKho))
            return false
          }
        }
        if (kieu == 2){
          if(dt[0].quyenKn.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận ngày nhận trên kho " + this.TenDenKho(this.params.p_DenKho))
            return false
          }
        }

        if (kieu == 3){
          if(dt[0].quyenKn.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận trên kho " + this.TenDenKho(this.params.p_DenKho)+ ".Bạn không thể hoàn công.")
            return false
          }
        }

        if (kieu == 4){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền tạo thiết bị trên kho " + this.TenTuKho(this.params.p_TuKho) + ".Bạn không thể hoàn công.")
            return false
          }
        }
        if (kieu == 5){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận trên kho " + this.TenTuKho(this.params.p_TuKho) + ".Bạn không thể xóa/sửa chứng từ.")
            return false
          }
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra quyền xác nhận ")
      // }
    },
    async dtpNgayGuiTT_ButtonClick(){
      try {
        this.loading(true)
        if(!this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Chỉ được phép cập nhật ngày gửi khi đã có chứng từ")
          return
        }
        if(! await this.KiemTraHanChotCT()){
          return
        }
        
        if(!this.gridVatTu.length){
          this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
          return
        }
        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }
        if (! await this.KIEMTRA_QUYEN_XN(1, this.t_kho_id)){ return }
        if (this.ttph_id.toString() == "4"){
          this.$toast.error("Chứng từ đã hoàn thiện không thể cập nhật ngày gửi")
          return
        }
        var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
        if (!ds_dt.length){
          this.$toast.error("Không có dữ liệu ngày nhận không thể cập nhật ngày gửi của kho")
          return
        }
        else{
          if(ds_dt[0] != null  && ds_dt[0] != ""){
            this.$toast.error("Đã có dữ liệu ngày nhận bạn không thể cập nhật ngày gửi đi")
            return
          }
        }
        var s = await this.KiemTraHoanThienCT({chungTuId: this.vchungtu_id})
        if (s == "1"){
          var ngayGui =  this.params.p_dtpNgayGuiTT != null ? this.f_DateToString(this.params.p_dtpNgayGuiTT,"DD/MM/YYYY HH:mm:ss") : ""
          var z = await this.CapNhatNgayXacNhanCT({
            "ngayXN" : this.f_DateToString(new Date(),'DD/MM/YYYY HH:mm:ss'),
            "ngayGuiTT" : ngayGui,
            "nguoiXn" : ngayGui == "" ? "" : this.$auth.getUserName(),
            "chungTuId" : this.vchungtu_id
          })
          if (parseInt(z)){    
            let  vchungtu_tmp_id  = this.vchungtu_id  
            this.$toast.success("Cập nhật ngày gửi thành công") 
            await this.Load_ds_chungtu()
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)           
          }else{
            this.$toast.error("Cập nhật ngày gửi thất bại " + z)
          }
        }
        else{
          this.$toast.error(s)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async dtpNgayNhanTT_ButtonClick(){
      try{
        this.loading(true)
        if(!this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Chỉ được phép cập nhật ngày nhận khi đã có chứng từ")
          return
        }
        if(! await this.KiemTraHanChotCT()){
          return
        }
        
        if(!this.gridVatTu.length){
          this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
          return
        }
        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){return }
        if (this.ttph_id.toString() == "4"){
            this.$toast.error("Chứng từ đã hoàn thiện không thể cập nhật ngày nhận")
            return
        }
        if (! await this.KIEMTRA_QUYEN_XN(2, this.kho_nhan_goc)){
            return
        }
        var ds_dt = await this.getThongTinNgayGui({chungTuId: this.vchungtu_id})
        if(ds_dt == null || ds_dt == ""){
          this.$toast.error("Không có dữ liệu ngày gửi không thể cập nhật ngày nhận của kho")
          return
        }
        else{       
          if(this.params.p_dtpNgayNhanTT != "" && this.params.p_dtpNgayNhanTT != null) { 
            if(this.f_StringToDateTime(ds_dt) >= this.params.p_dtpNgayNhanTT){
              this.$toast.error("Ngày nhận phải lớn hơn hoặc bằng ngày gửi thực tế")
              return
            }   
          }         
        }

        let s = await this.KiemTraHoanThienCT({chungTuId: this.vchungtu_id})
        if (s.toString() == "1"){
          var ngayNhan =  this.params.p_dtpNgayNhanTT != null ? this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY HH:mm:ss') : ""
          let z = await this.CapNhatNVThucHienCT({
            "nhanVienTHId" : this.$auth.getNhanVienID(),
            "nguoiTH" : this.$auth.getUserName(),
            "ngayNhanTT" : ngayNhan,
            "chungTuId" : this.vchungtu_id
          })
          if (parseInt(z)){
            this.$toast.success("Cập nhật ngày nhận thành công")  
            let vchungtu_tmp_id = this.vchungtu_id
            await this.Load_ds_chungtu()
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)        
          }
          else{
            this.$toast.error("Cập nhật ngày nhận thất bại " + z)
          }
        }
        else{
          this.$toast.error(s)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnTraLai_Click(){
      try {
        this.loading(true)
        if (this.vchungtu_id == 0){
            this.$toast.error("Bạn phải chọn chứng từ để xóa")
            return
        }
        if (! await this.KiemTraHanChotCT()){ return }
        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){
            return;
        }
        if (this.kiemtra_quyen){
          if (! await this.KIEMTRA_QUYEN_XN(2, this.kho_nhan_goc)){ return }
          if (this.ttph_id != 4){
            this.$toast.error("Chứng từ chưa được hoàn thành không thể trả phiếu.")
            return
          }

          if (! await this.KiemTraDuLieu(false, true, "TRALAI")){ return }
          
          this.loading(false)
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có chắc chắn muốn trả phiếu không!", {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } else {
                return;
              }
            });

          if (!isBoxConfirm) {
            return;
          }
          this.loading(true)
          await this.CapNhatXoaNgayNhanCT({chungTuId: this.vchungtu_id}) 

          await this.TaoDuLieu(false, true)
          let k = await this.XoaChungTuBaoHanh({
            "kieu": 2,
            "chungTuId": this.vchungtu_id,
            "chungTuCtId": 0,
            "thietBiId": 0
          })
          if (k != "1"){
            this.$toast.error(k)
            return
          }

          if (this.ttph_id == 4){
            let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
            if (s != "1"){
              this.$toast.error(s.toString())
              return
            }
            if (this.params.p_ckbSendSMS){
              try {
                await this.GuiSMSChungTu({
                  tuDong: 2,
                  chungTuId: this.vchungtu_id,
                
                })
              } catch (error) {
                this.$toast.error("Có lỗi khi send smsm tự động")
              }
            }
            s = await this.XoaPhieuDaHoanThanh({
              "chungTuId": this.vchungtu_id,
              "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
              "mayCn": this.$auth.getUserName(),//ttnd.may_cn
              "ipCn": "10.59.90.123"//ttnd.ip
            })
            if (s.toString() != "1"){
              this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
              return
            }
          }
                          
          let listChiTietCt = []    
          this.chungtuData.forEach(item=>{
            listChiTietCt.push({
              "chungTuId": item.CHUNGTU_ID,
              "nghiepVuId" : item.NGHIEPVU_ID,
              "loaiCtId": item.LOAICT_ID,
              "soPhieu": item.SOPHIEU,
              "mucDichId": item.MUCDICH_ID,
              "khoGIaoId": item.KHO_GIAO_ID,
              "khoNhanId": item.KHO_NHAN_ID,
              "nhanVienGiaoId": item.NHANVIEN_GIAO_ID,
              "donViNhanId": item.DONVI_GIAO_ID,
              "donViGiaoId": item.DONVI_NHAN_ID,
              "nguoiCn": item.NGUOI_CN,
              "congTrinhId": item.CONGTRINH_ID || '',
              "noiDungGiao": item.ND_GIAO,
              "ngayCt": item.NGAY_CT,
              "ttPhId": item.TTPH_ID,
              "loai": item.LOAI,
              "maPda": item.MA_PDA,
              "maCsht" : item.MA_CSHT,
              "maDvSd" : item.MA_DV_SD,
              "hdmsId" : item.HDMS_ID,
              "maDvQl" : item.MA_DV_QL,
            })
          })
        let body = {
          // "nguoiCN": chungTuData.NGUOI_CN,
          "mayCN": "",
          "ngayCN": "",
          "ipCN": "",
          "listChiTietCt": listChiTietCt,
          "kieu": 0 // update
        }
          await this.CapNhatChungTu(body)
          
          await this.CapNhatChiTiet_CT(false)
          let vchungtu_tmp_id = this.vchungtu_id
          if(this.vtdk.length){
            await this.CAPNHAT_DANGKY(vchungtu_tmp_id)
          }
          await this.Load_ds_chungtu()
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
          this.copyct = false
          
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async inKimPhiếuToolStripMenuItem_Click(){
      // chịu, làm sau
    },
    async BtnOkOnClick(){
      // ((sender as Button).Parent as Form).DialogResult = DialogResult.OK;
    },
    async FrmOnShown(){
      // (sender as Form).Size = new Size(300, 170);
    },
    async CkbIn_CheckedChanged(){
      // gridviewChungTu.CloseEditor();
    },
    async toolStripButton1_Click_1(){
      
    },
    async ckbTuKho_CheckedChanged(){
      await this.Loc_Kho()
    },
    async ckbDenKho_CheckedChanged(){
      await this.Loc_Kho()
    },
    async txtNDDK_ButtonClick(){
      // if (gridviewChungTu.FocusedRowHandle >= 0)	
      // {	
      //     var frm = new frmXemVB("Nội dung đăng ký", gridviewChungTu.GetFocusedRowCellValue("NDDK").toString(), false);	
      //     frm.ShowDialog(this);	
      // }
    },
    async btnDelete_VTDK_Click(){
      await this.AN_GROUP(true)
    },
    async AN_GROUP(mo){
      if(mo){
        this.Visible.ThongTinHDDA = false
      }
      else{
        this.Visible.ThongTinHDDA = true
       
        if(!this.cboHopDong.length){
          this.cboHopDong = await this.getDSHopDongMuaSam({})
          
          if(this.cboHopDong.length > 0){
            setTimeout(() => {
              this.$refs['cboHopDong'].$children[0].selectRowByRowData(this.cboHopDong[0].hdmsId);
            }, 300);            
          }
          this.cbo_DuAn = await this.getDSDuAn({chkDuAn: this.params.p_ckbDuAn ? 1 : 0}) 
        }
        this.params.p_txtGhiChuHD = ""
      }
    },
    cboHopDong_DDChanged(value) {   
      this.params.p_HopDong = value.hdmsId;
    },
    async splitterItem1_DoubleClick(){
      if(this.params.p_LoaiChungTu){
        if(this.params.p_LoaiChungTu.toString() == "99"){
          await this.AN_GROUP(false)
        }
      }
    },
    async ckbDuAn_CheckedChanged(){     
      
      this.params.p_ckbDuAn = !this.params.p_ckbDuAn
      this.cbo_DuAn = await this.getDSDuAn({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})  
     
    },
   
    async KIEMTRA(kieu){
      // try {
        if(!this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Bạn chỉ có thể gán gỡ khi đang ở trạng thái cập nhật chứng từ")
          return false
        }
        if (! await this.KiemTraNguoiCN()){
          this.$toast.error("Bạn không được cập nhật chứng từ của người khác")
          return false
        }
        if(!this.gridVatTu.length){
          this.$toast.error("Không có thông tin chi tiết vật tư")
          return false
        }
        var ds = this.gridVatTu
        if(ds.length == 0){
          this.$toast.error("Không có thông tin chi tiết vật tư")
          return false
        }
        if(this.$refs.gridViewVatTu.getSelectedRecords().length == 0){
          this.$toast.error("Bạn chưa chọn vật tư cần gán")
          return false
        }
        if (kieu == 1){
          if(this.params.p_HopDong == null || this.params.p_HopDong == ""){
            this.$toast.error("Bạn chưa chọn thông tin hợp đồng")
            return false
          }
          //Bỏ đoạn này do đã decode
          // if(this.params.p_DuAn == null || this.params.p_DuAn == ""){
          //   this.$toast.error("Bạn chưa chọn thông tin dự án")
          //   return false
          // }
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra gán/ gỡ HĐ ")
      // }
    },
    async btnGan_Click(){
      try {
        this.loading(true)
        if (! await this.KIEMTRA(1)){ return }
        var ds = this.gridVatTu
        var t = this.$refs.gridViewVatTu.getSelectedRecords()
        
        let json_list = JSON.stringify(t)
        await this.GanHDDuAnChiTietCT({
          "hdmsId" : this.params.p_HopDong,
          "duAnId" : this.params.p_DuAn == "" || this.params.p_DuAn == null ? -1 : this.params.p_DuAn,
          "ghiChu" : this.params.p_txtGhiChuHD,
          "data" : json_list
        })
        
        this.$toast.success("Cập nhập thông tin thành công")
        await this.Load_ds_vattu()
      } catch (error) {
        this.$toast.error(error.data.message)
      }finally{
        this.loading(false)
      }
    },
    async btnGo_Click(){
      try {
        this.loading(true)
        if (! await this.KIEMTRA(0)){ return }
        var ds = this.gridVatTu
        var t = this.$refs.gridViewVatTu.getSelectedRecords()
        
        let json_list = JSON.stringify(t)
        if (this.vma_md == this.MUCDICH_VT.KDV_DONGIA){
          var kt = await this.KiemTraChiTietVatTuCoDauVao({
            "hdmsId" : "",
            "duAnId" : "",
            "ghiChu" : "",
            "data" : json_list
          })
          if (kt.toString() != "0"){
            this.$toast.error("Chi tiết vật tư đã có đầu vào không thể gỡ thông tin hợp đồng dự án")
            return
          }
        }
        await this.GoGanHDDuAnCTCT({ 
          "hdmsId" : "",
          "duAnId" : "",
          "ghiChu" : "",
          "data" : json_list})        
        this.$toast.success("Xóa thông tin hợp đồng dự án thành công")
        await this.Load_ds_vattu()
      } catch (error) {
        this.$toast.error(error.data.message)
      }finally{
        this.loading(false)
      }
    },
    async btnRef_Click(){    
      this.cbo_DuAn = await this.getDSDuAn({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})   
      if(this.params.p_ckbDuAn){       
        this.cbo_DuAn.shift()
      }
    },
    async gridChungTu_Click(){
      // DHSX đang empty
    },
    async gridviewTinhTrang_CellValueChanged(){
      // await this.CapNhatTinhTrangCTTBi({
      //   "tinhTrang" : 1,
      //   "thietBiId" : 1195932,
      //   "ctctId" : 855235
      // })
    },
    async ckbTTALL_CheckedChanged(){
      console.log(this.params.p_ckbTTALL , " = this.params.p_ckbTTALL")
      if (this.params.p_ckbTTALL){
        if (this.params.p_TinhTrang == null && this.params.p_TinhTrang == "" && this.params.p_TinhTrang == 0){
          for(let e of this.gridTinhTrang){
            if(e.TINHTRANG == null || e.TINHTRANG == "" || e.TINHTRANG == 0){
              e.TINHTRANG = this.params.p_TinhTrang
            }
          }
          this.$refs.gridviewTinhTrang.grid.refreshColumns()
          this.params.p_ckbTTALL = false
        }
        else{
          this.params.p_ckbTTALL = false
        }
      }
    },
    
    async XacNhan_frmGanSeri_v2(){
      var f = this.$refs.frmGanSeri_v2
      if (f.xacnhan){
        await this.Load_ds_thietbi()
      }
    },
    async gridViewVatTu_CustomRowCellEdit(){
      // làm sau
    },
    async btnChonTamUng_Click(){
      if (this.Enabled.tsbtnNhapMoi && this.dc_ct_noibo){
        var frm = this.$refs.frmChonTBi_CT
        frm.vcongtrinh_id = parseInt(this.params.p_CongTrinh)
        
        frm.vchungtu_id = this.vchungtu_id
        frm.vkho_id = parseInt(this.params.p_TuKho)
        frm.openDialog()
      }
    },
    async XacNhan_frmChonTBi_CT(value){
      var frm = this.$refs.frmChonTBi_CT;
      if (value) {
          let response = await NhapXuatVTAPI.getNoiDungGiaoChungTu(
              this.axios,
              { chungTuId: this.vchungtu_id }
          );
          if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined
          ) {
              this.params.p_txtNoiDung = response.data.data;
          }
          await this.Load_ds_thietbi();
      }
    },
    async btnChuyenTT_Click(){
      try{
        if (! await this.KiemTraNguoiCN()){
          this.$toast.error("Bạn không phải người lập chứng từ, không thể sử dụng chức năng này!")
          return
        }
        let loai_chungtu = this.gridviewChungTu_FocusedRow["LOAI"]
        if ((this.vma_md == "" || (this.vma_md != this.MUCDICH_VT.KDV_DONGIA && this.vma_md != "N.KDV")) && loai_chungtu == "2"){
          this.$toast.error("Mục đích không phải nhập không đầu vào ! Không thể sử dụng chức năng !")
          return
        }
        if (!this.gridVatTu.length){
          this.$toast.error("Không có vật tư ! Không thể sử dụng chức năng !")
          return
        }
        let vattu_ischeck = this.gridVatTu
        var kiem_tra = this.$refs.gridViewVatTu.getSelectedRecords()
        if (!kiem_tra.length){
          this.$toast.error("Bạn chưa tích chọn dòng vật tư nào !")
          return
        }
        // Match a string that starts with abc, similar to LIKE 'KDV%'
        kiem_tra = this.$refs.gridViewVatTu.getSelectedRecords().filter(a=> a.LOHANG.match(/^KDV.*$/))
        if (!kiem_tra.length){
          this.$toast.error("Bạn chỉ có thể chọn lô hàng KDV !")
          return
        }
        
        //-- ban đầu khi update sẽ cho sửa đơn giá lần đầu, sau có đầu vào thì chặn ko cho update tiếp
        
        var codauvao_kiemtra = this.$refs.gridViewVatTu.getSelectedRecords().filter(a=> a.LOHANG.match(/^KDV.*$/) && a.TRANGTHAI_SO == "1")
        if (codauvao_kiemtra.length){
          this.$toast.error("Danh sách chọn chứa vật tư có đầu vào. Hãy kiểm tra lại !")
          return
        }
        // nhập lẻ
        if (loai_chungtu == "2"){
          let note_dg = this.vma_md == this.MUCDICH_VT.KDV_DONGIA ? "(và đơn giá)" : ""
          let isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Chuyển trạng thái vật tư ở nhập lẻ sẽ chuyển trạng "
                + "thái vật tư " + note_dg + " ở tất cả phiếu xuất sau nó!\r\n Bạn có thật sự muốn chuyển trạng thái của "
                + kiem_tra.length + " vật tư không?", {
              title: 'Thông báo',
              centered: true,
              size: 'md',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              }
            });

          if (!isBoxConfirm) {
            return;
          }
          this.loading(true)
          let data = []
          kiem_tra.forEach(a=>data.push({
            "ctctId" : a.CTCT_ID,
            "loHang" : a.LOHANG,
            "donGia" : a.DONGIA_SUA,
            "vatTuId" : a.VATTU_ID
          }))          
          await this.CapNhatLoHang({
              "checkMucDich" : this.vma_md == this.MUCDICH_VT.KDV_DONGIA ? 1 : 0,
              "listLoHangIn" : data
            })
          this.$toast.success("Chuyển trạng thái thành công !")
          await this.Load_ds_vattu()
          await this.Load_ds_thietbi()
        }
        // xuất - chuyển - nhập đơn hàng
        else{
          this.loading(true)
          let data = []
          kiem_tra.forEach(a=>data.push({
            VATTU_ID: a.VATTU_ID,
            LOHANG: a.LOHANG
          }))
          let json_list = JSON.stringify(data)
          var res = await this.getDSChungTuNhapLe({
            "data" : json_list
          })
          this.$toast.error("Chứng từ xuất/chuyển không thể chuyển trạng thái vật tư ! Hãy tìm chứng từ nhập lẻ:\r\n" + res[0].SOPHIEU )
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async grvLocVattu_CellValueChanged(){
      let tt = "";
      for (let dr of this.$refs.grvLocVattu.getSelectedRecords()){
          tt += dr["TEN_VT"] + ",";
          
      }
      tt = tt.trimEnd(",".toCharArray());

      if (tt != ""){
          cboLocVattu.Text = tt;
      }
      else{
          cboLocVattu.Text = tt;
      }
    },
    async ckbHTTT__CheckedChanged(){

    },
    async gridViewVatTu_CustomDrawCell(){
      // làm sau
    },
    
    async XacNhan_frmLoaiKho_MaKho(){
      var f = this.$refs.frmLoaiKho_MaKho
      if (f.xacnhan){
        if (this.Enabled.tsbtnNhapMoi && this.nghiepvu_id_chungtu != ""
          && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
            for (let  z of f.dt_dasua){
              if (z.LOAIKHO != "" && z.LOAIKHO != null){
                await this.CapNhatLoaiKhoTapDoan({loaiKhoTdId: z.LOAIKHO, ctctId: parseInt(z.CTCT_ID)})
              }
              if (z.MAKHOTD != "" && z.MAKHOTD != null){
                await this.CapNhatKhoTapDoan({khoTdId: z.MAKHOTD,ctctId: parseInt(z.CTCT_ID)})
              }
            }
            this.$toast.success("Cập nhật loại kho - mã kho thành công !")
            await this.Load_ds_vattu()
          }
        else{
          this.gridVatTu = f.dt_dasua
          this.gridVatTu_DataSourceChanged()
        }
      }
    },
    async simpleButton3_Click(){
      try {
        this.loading(true)        
           
        var json_vattu = []
        for(let e of  this.cboLocVattu_checked){
          json_vattu.push({
          VATTU_ID: e
        })
        }
        let dsKhac = [{
          STATUS_FMIS : this.params.rdioFMIS,
          STATUS_IMS: this.params.rdioIMS,
          CKB_TATCA: this.params.p_chkAll_Phieu  ? 1 : 0,
          CKB_NGUOIDUNG: this.params.p_ckb_nd ? 1 : 0,
          MA_ND : this.$auth.getUserName()
        }]
        this.dtChungTu = await this.getDSChungTuTheoVatTu({
          "donViId" : this.$auth.getDonViID(),
          "nhanVienId" : this.$auth.getNhanVienID(),
          "dsVatTu": json_vattu,
          "dsKhac" :  dsKhac,
          "tuNgay" : this.f_DateToString(this.params.p_dtpTuNgay,'DD/MM/YYYY'),
          "denNgay" : this.f_DateToString(this.params.p_dtpDenNgay,'DD/MM/YYYY')
        })
        this.gridChungTu = this.dtChungTu
        this.listChungTuChecked = []
        var kieu = this.dtChungTu.length > 0 ? 3 : 1
        this.SetButton(kieu);
            
          
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnGiaoPhieu_Click(){
      if (this.vchungtu_id > 0){
        var qt = await this.getQuyTrinhHuongGiaoTheoCT({chungTuId: this.vchungtu_id})   
        if(qt.length == 0){
          this.$toast.error("Quy trình chưa được thiết lập")
        }
        else{
          this.quytrinh_id = parseInt(qt[0]["QUYTRINH_ID"])
          this.huonggiao_id = parseInt(qt[0]["HUONGGIAO_ID"])
          var d = await this.getHuongGiaoTiepTheo({huongGiaoId: this.huonggiao_id})
          if (d.length){
            let huonggiao_tieptheo_id = parseInt(d[0]["HUONGGIAO_ID"])
            var frm = this.$refs.frmGiaoPhieu
            frm.frmGiaoPhieuHG(huonggiao_tieptheo_id)
            frm.parentForm = "NhapXuatVT"
            frm.openDialog()
          }
        }
      }else{
        this.$toast.error("Chưa chọn chứng từ!")
      }
    },
    async closeGiaoPhieu(){
      try{
        this.loading(true)
        await this.Load_ds_chungtu()
        if (this.gridChungTu.length > 0){      
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID)
        }            
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
       
    onFilteringTuKho(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('tenKho', 'contains', e.text, true) : query;
      e.updateData(this.cboTuKho, query);
    },
    onFilteringDenKho(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('tenKho', 'contains', e.text, true) : query;
      e.updateData(this.cboDenKho, query);
    },
    async InsertUpdateChungTu(kieu, chungTuData){
      let listChiTietCt = []      
      chungTuData.forEach(item=>{
        listChiTietCt.push({
          "chungTuId": item.CHUNGTU_ID,
          "nghiepVuId" : item.NGHIEPVU_ID,
          "loaiCtId": item.LOAICT_ID,
          "soPhieu": item.SOPHIEU,
          "mucDichId": item.MUCDICH_ID,
          "khoGIaoId": item.KHO_GIAO_ID,
          "khoNhanId": item.KHO_NHAN_ID,
          "nhanVienGiaoId": item.NHANVIEN_GIAO_ID,
          "donViNhanId": item.DONVI_GIAO_ID,
          "donViGiaoId": item.DONVI_NHAN_ID,
          "nguoiCn": item.NGUOI_CN,
          "congTrinhId": item.CONGTRINH_ID || '',
          "noiDungGiao": item.ND_GIAO,
          "ngayCt": item.NGAY_CT,
          "ttPhId": item.TTPH_ID,
          "loai": item.LOAI,
          "maPda": item.MA_PDA,
          "maCsht" : item.MA_CSHT,
          "maDvSd" : item.MA_DV_SD,
          "hdmsId" : item.HDMS_ID,
          "maDvQl" : item.MA_DV_QL,
        })
      })
      let body = {
        // "nguoiCN": chungTuData.NGUOI_CN,
        "mayCN": chungTuData[0].MAY_CN,
        "ngayCN": chungTuData[0].NGAY_CN,
        "ipCN": chungTuData[0].IP_CN,
        "listChiTietCt": listChiTietCt,
        "kieu": kieu
      }
      // tao chung tu => gan chung tu id = 0
      // API se tra ve chungTuId
      let newChungTuId = await this.ThemMoiChungTu(body)
      if (kieu == 1){
        this.vchungtu_id = newChungTuId
      }
      console.log('AAAAA',this.vchungtu_id)
      
    },
    async InsertUpdateChiTietChungTu(chiTietCTData){
      if(chiTietCTData.length == 0){
        return
      }
      let listChiTietCt = []
      chiTietCTData.forEach(item=>{
        listChiTietCt.push({
          "ctctId": item.CTCT_ID,
          "vatTuId": item.VATTU_ID,
          "chungTuId": item.CHUNGTU_ID,
          "loHang": item.LOHANG,
          "donGia": item.DONGIA,
          "tien": item.TIEN,
          "vat": item.VAT,
          "soLuong": item.SOLUONG,
          "soThangPB": item.SOTHANG_PB
        })
      })
 
      let body = {
        "nguoiCN": this.$auth.getUserName(),
        "mayCN": "",
        "ngayCN": this.f_DateToString(new Date(),'DD/MM/YYYY'),
        "ipCN": "",
        "listChiTietCt": listChiTietCt
      }
      console.log('BBBBB',this.vchungtu_id)
      await this.ThemMoiDuLieuChiTietCT(body)
    },
    TenTuKho(value){
      let obj = this.$refs.cboTuKho.getDataByValue(value)
        if (!obj){
          return ''
        }
        return obj.tenKho
    },
    TenDenKho(value){
      let obj = this.$refs.cboDenKho.getDataByValue(value)
        if (!obj){
          return ''
        }
        return obj.tenKho
    },
    async selected_gridVatTuRow(data){      
      if(data){
        this.gridViewVatTu_FocusedRow = data
      }
    },
    
    
    async txtPhieuDeNghi_DoubleClick(){
      try{
        if (this.params.p_LoaiChungTu == null || this.params.p_LoaiChungTu == ""){
            this.$toast.error("Chưa chọn loại chứng từ");
            return;
        }
        this.loading(true)       
        let frmChon = this.$refs.frmChonPhieuDeNghi
        frmChon.Clear()
        frmChon.ma_phieu = this.params.p_txtPhieuDeNghi == null || this.params.p_txtPhieuDeNghi == "" ? "" : this.params.p_txtPhieuDeNghi;
        
        frmChon.loaiphieu_id = await this.chuyendoi_loaict_loaiphieu_denghi();
        frmChon.txtLoaiPhieu = this.gridviewChungTu_FocusedRow.LOAIPHIEU_ID
        frmChon.txtMaPhieu = frmChon.ma_phieu
        await frmChon.frmChonPhieuDeNghi_Load()        
        this.$refs.popup_frmChonPhieuDeNghi.show();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
        
      
    },
    async XacNhan_frmChonPhieuDeNghi(){
      this.$refs.popup_frmChonPhieuDeNghi.hide();
      let frmChon = this.$refs.frmChonPhieuDeNghi
      if (frmChon.xacnhan){
          this.params.p_txtPhieuDeNghi = frmChon.ma_phieu;
      }
    },
    async chuyendoi_loaict_loaiphieu(){
      let vloaiphieu_id = 0;
      switch (this.params.p_LoaiChungTu)
      {
          case "N":
              vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_NHAPKHO;
              break;
          case "2":
              vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_XUATKHO;
              break;
          case "3":
              vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_CHUYENKHO;
              break;
          case "99":
              vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_NHAPKHO;
              break;
          default:
              break;
      }
      return vloaiphieu_id;
    },
    async chuyendoi_loaict_loaiphieu_denghi(){
      let vloaiphieu_id = 0;
      switch (this.params.p_LoaiChungTu.toString())
      {
          case "N":
              vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_NHAPKHO_TT;
              break;
          case "2":
              vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_XUATKHO;
              break;
          case "3":
              vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_CHUYENKHO;
              break;
          case "99":
              vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_MUALE;
              break;
          default:
              break;
      }
      return vloaiphieu_id;
    },
    async btnChonPhieuDN_Click(){
      await this.txtPhieuDeNghi_DoubleClick()
    },
    async cboNghiepVu_EditValueChanged(){
      if(this.params.p_NghiepVu == null){
        return
      }
      this.params.p_Ma_PDA = ""
      this.grcMa_PDA = []
      this.cboHDPOIMS = []
      this.cboDuanIMS = []
      this.gridBienBanIMS = []
      this.gridDieuChinhVT = []
      this.params.p_IMS_CSHT = ""
      this.params.p_Ma_DV_SD = ""
      this.params.text_Ma_DV_SD = ""
      this.params.p_Ma_DV_QL = ""
      this.params.text_Ma_DV_QL = ""
      this.params.p_HD_XuatIMS = ""
      this.params.text_HD_XuatIMS = ""
      this.params.p_MaPhieu = ""
      this.params.p_IMS_CSHT = ""
      this.duan_ims_clone = []
      this.bbbgiao = []
      this.dieuchuyen_clone = []
      this.params.p_DuanIMS = ""
      this.contractNumber = ""
      this.params.p_HDPOIMS = ""

      var nghiepvu_ = await this.getMaMucDichTheoID({mucDichId: this.params.p_NghiepVu})
 
      if(!nghiepvu_.length){ return }
      
      let ma_nghiepvu = nghiepvu_[0]     
      if (ma_nghiepvu == "NV.BBBGIMS"){
        this.Visible.layoutControlDuAn = false
        this.Visible.layoutControlDS_Vattu = false
        this.Visible.layoutControlDieuChinh = false
        this.Visible.layoutControlBBBG = true
        this.Visible.layoutMaPhieu = false
        this.OpenCboMa_PDA = true
        this.tabSPDA = 3
      }
      else if (ma_nghiepvu == "NV.DUAN"){
        this.Visible.layoutControlDuAn = true
        this.Visible.layoutControlDS_Vattu = false
        this.Visible.layoutControlDieuChinh = false
        this.Visible.layoutControlBBBG = false
        this.Visible.layoutMaPhieu = false

        this.tabSPDA = 1
        this.OpenCboMa_PDA = true

        this.Enabled.cboMa_DV_SD = false
        this.Enabled.cboIMS_CSHT = false
        this.Enabled.cboMa_DV_QL = false
        this.Enabled.cboHD_XuatIMS = false
      }else if (ma_nghiepvu == "NV.XUATIMS" || ma_nghiepvu == "NV.ACAP" || ma_nghiepvu == "NV.CHUYENNGUON"){
        this.Visible.layoutControlDuAn = true
        this.Visible.layoutControlDS_Vattu = false
        this.Visible.layoutControlDieuChinh = false
        this.Visible.layoutControlBBBG = false
        this.Visible.layoutMaPhieu = false

        this.tabSPDA = 1
        this.OpenCboMa_PDA = true

        this.Enabled.cboMa_DV_SD = true
        this.Enabled.cboIMS_CSHT = true
        this.Enabled.cboMa_DV_QL = true
        this.Enabled.cboHD_XuatIMS = true
      }else if (ma_nghiepvu == "NV.THUHOI" || ma_nghiepvu == "NV.THAYTHE" ){
        this.Visible.layoutControlDuAn = false
        this.Visible.layoutControlDS_Vattu = true
        this.Visible.layoutControlDieuChinh = false
        this.Visible.layoutControlBBBG = false
        this.Visible.layoutMaPhieu = true
        this.OpenCboMa_PDA = true 
        this.tabSPDA = 4      
      }else if (ma_nghiepvu == "NV.DIEUCHUYEN"  ){
        this.Visible.layoutControlDuAn = false
        this.Visible.layoutControlDS_Vattu = false
        this.Visible.layoutControlDieuChinh = true
        this.Visible.layoutControlBBBG = false
        this.Visible.layoutMaPhieu = true
        this.OpenCboMa_PDA = true     
        this.tabSPDA = 2  
      }else{
        this.OpenCboMa_PDA = false 
      }
    },
    async v_btnRef_Click(){
      try{
        this.loading(true)      
        this.grcMa_PDA = []
        this.cboHDPOIMS = []
        this.cboDuanIMS = []
        this.gridBienBanIMS = []
        this.gridDS_VT_THUHOI = []
        this.duan_ims_clone = []
        this.bbbgiao = []
        this.dieuchuyen_clone = []
        if(this.params.p_NghiepVu == null || this.params.p_NghiepVu == ""){
          this.$toast.error("Chưa chọn nghiệp vụ!")
          return
        }
        var nghiepvu_ = await this.getMaMucDichTheoID({mucDichId: this.params.p_NghiepVu})
      
        if (nghiepvu_.length == 0){ return }

        let ma_nghiepvu = nghiepvu_[0]


        if (ma_nghiepvu == "NV.DUAN" || ma_nghiepvu == "NV.XUATIMS" || ma_nghiepvu == "NV.ACAP"
                    || ma_nghiepvu == "NV.CHUYENNGUON"){
          var dt = await this.getDSRefIMS({
            mucDichId: this.params.p_NghiepVu,
            kieuGoiId: this.kieugoi_id,
            maPda: this.params.p_Ma_PDA,
            khoId: this.params.p_DenKho
          })       
          if(dt != null && dt != ""){
            var data = JSON.parse(dt)
            if("NewDataSet" in data && "Table" in data.NewDataSet){
              this.grcMa_PDA = data.NewDataSet.Table
              console.log(this.grcMa_PDA , " = this.grcMa_PDA")
            }else{
              this.grcMa_PDA = []
            }
          }else{
            this.grcMa_PDA = []
          }
        } 


        if(ma_nghiepvu == "NV.THUHOI" && this.kieugoi_id == 2){         

          if(this.params.p_MucDich == null || this.params.p_MucDich == ""){
            this.$toast.error("Bạn chưa chọn mục đích !")
            return
          }

          if(this.params.p_TuKho === null || this.params.p_TuKho === "" ){
            this.$toast.error("Bạn chưa chọn từ kho !")
            return
          }

          if(this.params.p_DenKho === null || this.params.p_DenKho === "" ){
            this.$toast.error("Bạn chưa chọn đến kho !")
            return
          }

          if(this.params.p_Ma_PDA == null || this.params.p_Ma_PDA == "" ){
            this.$toast.error("Bạn chưa nhập số phiếu trên QLTS !")
            return
          }

          var dt = await this.getDSRefIMS({
            mucDichId: this.params.p_NghiepVu,
            kieuGoiId: this.kieugoi_id,
            maPda: this.params.p_Ma_PDA,
            khoId: this.params.p_DenKho
          })
          this.gridDS_VT_THUHOI = dt
          console.log(dt, " = dt")
        }
        if (ma_nghiepvu == "NV.DIEUCHUYEN"){
          if(this.params.p_Ma_PDA == null || this.params.p_Ma_PDA == "" ){
            this.$toast.error("Bạn chưa nhập số phiếu trên QLTS !")
            return
          }
          this.params.p_LocDCDonVi = 0

          var dt = await this.getDSRefIMS({
            mucDichId: this.params.p_NghiepVu,
            kieuGoiId: this.kieugoi_id,
            maPda: this.params.p_Ma_PDA,
            khoId: this.params.p_DenKho
          })
          this.dieuchuyen_clone = dt
          this.gridDieuChinhVT = dt
        }

        if(ma_nghiepvu == "NV.BBBGIMS"){          

          if(this.params.p_MucDich == null || this.params.p_MucDich == ""){
            this.$toast.error("Bạn chưa chọn mục đích !")
            return
          }

          if(this.params.p_TuKho === null || this.params.p_TuKho === "" ){
            this.$toast.error("Bạn chưa chọn từ kho !")
            return
          }

          if(this.params.p_DenKho === null || this.params.p_DenKho === "" ){
            this.$toast.error("Bạn chưa chọn đến kho !")
            return
          }
          if(this.kieugoi_id ==2){
            var find_csht = await this.getMaCoSoHaTang({khoId: this.params.p_DenKho})
            if(find_csht == null || find_csht.length == 0){
              this.$toast.error("Kho đến không có mã CSHT ! Hãy kiểm tra lại !")
              return
            }
          }else{
            this.$toast.error("Loại chứng từ phải là nhập lẻ!")
            return
          }
          var dt = await this.getDSRefIMS({
            mucDichId: this.params.p_NghiepVu,
            kieuGoiId: this.kieugoi_id,
            maPda: this.params.p_Ma_PDA,
            khoId: this.params.p_DenKho
          })
          if(dt != null && dt != ""){
            var data = JSON.parse(dt)
            if("NewDataSet" in data && "Table" in data.NewDataSet){
              this.cboDuanIMS = data.NewDataSet.Table
              console.log(this.cboDuanIMS , " = this.grcMa_PDA")
            }else{
              this.cboDuanIMS = []
            }
          }else{
            this.cboDuanIMS = []
          } 
          this.duan_ims_clone = this.cboDuanIMS        
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },
    async cboDuanIMS_EditValueChanged(row){
      try{
        this.params.p_DuanIMS = row.ProjectCode       
        this.loading(true)
        if(this.params.p_DuanIMS == null || this.params.p_DuanIMS == ""){
          this.$toast.error("Chưa chọn dự án!")
          return
        }
        var ma_coso_hatang = ""
        if(this.kieugoi_id == 2){
          var find_csht = await this.getMaCoSoHaTang({khoId : this.params.p_DenKho})      
          if(find_csht != null){
            ma_coso_hatang = find_csht
          }
        }else{        
          return
        }

        let dt_hdpo = await this.getContractInfo({
          maDuan: this.params.p_DuanIMS,
          maCsht: ma_coso_hatang
        })

        if(dt_hdpo == null || dt_hdpo == ""){
          this.$toast.error("Không có thông tin hợp đồng - PO")
          this.cboHDPOIMS = []
          this.gridBienBanIMS = []
          this.contractNumber = ""
          this.params.p_HDPOIMS = ""
          return
        }else{
          var data = JSON.parse(dt_hdpo)
          this.cboHDPOIMS = []       
          if("NewDataSet" in data && data.NewDataSet != "" && "Table" in data.NewDataSet){   

            if(Array.isArray(data.NewDataSet.Table)) { // trả về dạng List Object
              this.cboHDPOIMS = data.NewDataSet.Table 
            }else{    
              this.cboHDPOIMS.push(data.NewDataSet.Table)          // trả về dạng object            
            }   
            this.cboHDPOIMS.unshift({
              PO: "",
              ContractName: "",
              SignOffDate: "",
              ContractInfoID: "",
              ParentContractInfo: "",
              ContractNumber: ""
            })   
          }else{
            this.gridBienBanIMS = []
            this.contractNumber = ""
            this.params.p_HDPOIMS = ""
          }
          console.log(this.cboHDPOIMS , " = this.cboHDPOIMS")
        }
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async cboHD_XuatIMS_EditValueChanged(row){
      this.params.p_HD_XuatIMS = row.ContractInfoID
      this.params.text_HD_XuatIMS = row.ContractNumber
    },

    async cboMa_DV_SD_EditValueChanged(row){
      this.params.p_Ma_DV_SD = row.MA_DV_SD
      this.params.text_Ma_DV_SD = row.TEN_DV      
    },

    async cboMa_DV_QL_EditValueChanged(row){
      this.params.p_Ma_DV_QL = row.MA_DV_QL
      this.params.text_Ma_DV_QL = row.TEN_DV      
    },

    async cboHDPOIMS_EditValueChanged(row){
      try{
        this.params.p_HDPOIMS = row.ContractInfoID
        this.contractNumber = row.ContractNumber
        if (this.params.p_HDPOIMS == "" || this.params.p_HDPOIMS == null){
          this.gridBienBanIMS = []
          return
        }
        var ma_coso_hatang = ""
        if(this.kieugoi_id == 2){
          var find_csht = await this.getMaCoSoHaTang({khoId : this.params.p_DenKho})
          if(find_csht != null ){
            ma_coso_hatang = find_csht
          }
        }else{        
          return
        }
        var HDPOIMSFilter = this.cboHDPOIMS.filter(a=>a.ContractInfoID == this.params.p_HDPOIMS )
        var ParentContractInfo = HDPOIMSFilter.length > 0 ? HDPOIMSFilter[0].ParentContractInfo : ""

        let dt_keys = await this.getAssetByKeys({
          maDuan: this.params.p_DuanIMS,
          contractNumber: ParentContractInfo,
          poNumber: this.params.p_HDPOIMS,
          maCsht: ma_coso_hatang
        })
        this.gridBienBanIMS = []
        if(dt_keys == null || dt_keys == ""){        
          return
        }else{
          var data = JSON.parse(dt_keys)  
          this.bbbgiao = []       
          if("NewDataSet" in data && "Table" in data.NewDataSet){         
            if(Array.isArray(data.NewDataSet.Table)) { // trả về dạng List Object
               this.bbbgiao = data.NewDataSet.Table  
            }else{              // trả về dạng object
              this.bbbgiao.push(data.NewDataSet.Table)
            }       
          }
        }
        var list_AcceptanceGeneralName = []
        console.log(this.bbbgiao , " = this.bbbgiao")
        //remove duplicate AcceptanceGeneralName        
        for(const e of this.bbbgiao){
          if(!list_AcceptanceGeneralName.includes(e.AcceptanceGeneralName)){
            list_AcceptanceGeneralName.push(e.AcceptanceGeneralName)
            this.gridBienBanIMS.push({AcceptanceGeneralName : e.AcceptanceGeneralName })
          }
        }
        
             
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    
    async cboMa_PDA_ButtonClick(){
      this.params.p_Ma_PDA = ""

      this.grcMa_PDA = [];
      this.cboHDPOIMS = [];
      this.cboDuanIMS = [];
      this.gridBienBanIMS = [];
      this.gridDS_VT_THUHOI = [];
      // duan_ims_clone.Clear();
      // bbbgiao.Clear();
      // dieuchuyen_clone.Clear();
      this.params.p_Ma_DV_SD = "";
      this.params.text_Ma_DV_SD = ""
    },
    async cboIMS_CSHT_ButtonClick(){
      this.params.p_IMS_CSHT = ""
    },
    async btn_chonBBBG_ButtonClick(row){
      try{

        if(this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Bạn chưa nhập mới không thể chọn BBBG !")
          return
        }

        if (! await this.KiemTraDuLieu(true, false, "BBBG")) {return};

        let ma_bbbg = row.AcceptanceGeneralName
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Ban có chắc chắn muốn chọn biên bản bàn giao " + ma_bbbg + " không? ", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }

        if(this.bbbgiao.length == 0){
          this.$toast.error("Không có dữ liệu BBBG (row)")
          return
        }
        this.loading(true)
        let loc_dl = this.bbbgiao.filter(a=> a.AcceptanceGeneralName == ma_bbbg)
        // let loc_dl = this.gridBienBanIMS.filter(a=> a.AssetCodeType == "30.01.09.82.01")
        if(loc_dl.length > 0 && this.kieugoi_id == 2){
          let project = this.duan_ims_clone.filter(a=>a.ProjectCode == this.params.p_DuanIMS)
          var ketqua_kt = await this.getDSVatTuTheoNghiepVu({
            kieu: 1,
            ds: loc_dl,
            maPda: project.length > 0 ? project[0].ProjectCode : null,
            tenPda: project.length > 0 ? project[0].ProjectName : null,
            pdaId: project.length > 0 ? project[0].ProjectID : null
          })
          console.log(ketqua_kt," = ketqua_kt")
          if(!("kq" in ketqua_kt)){
            this.$toast.error("Đã có lõi xảy ra, vui lòng thử lại!")
          }
          if("kq" in ketqua_kt && ketqua_kt.kq != "1"){
            this.$toast.error(ketqua_kt.kq)
            return
          }
 
          var Object_PO = this.cboHDPOIMS.filter(a=> a.ContractInfoID == this.params.p_HDPOIMS)
          var _ketqua = await this.SinhChungTuIMSBienBan({
            kieu: 1,
            khoNhanId: this.params.p_DenKho,
            ngayCt: this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss'),
            ndGiao: this.params.p_txtNoiDung,
            nghiepVuId: this.params.p_NghiepVu,
            maPda: this.params.p_DuanIMS,
            maHd: this.contractNumber,
            contractInfoId : this.params.p_HDPOIMS,
            maPo: Object_PO.length > 0 ? Object_PO.PO : "" ,
            bbBg: ma_bbbg,
            dsVt: JSON.parse(ketqua_kt.dsChiTiet),
            dsChiTiet: JSON.parse(ketqua_kt.dsCtTbi),
            nguoiCn: this.$auth.getUserName(),//ttnd.may_cn
            mayCn: "",//ttnd.may_cn
            ipCn: "",//ttnd.ip
            khoGiaoId: this.params.p_TuKho,
            loaiCtId: 1,
            donViGiaoId: this.$auth.getDonViID(),
            donViNhanId: this.$auth.getDonViID(),
            loaiPhieuId: null,
            mucDichId: this.params.p_MucDich
          })
          if (_ketqua.toString().startsWith("OK")){
            this.$toast.success("Tạo chứng từ thành công")
            let vchungtu_tmp_id = parseInt(_ketqua.toString().split('-')[1])
            await this.Load_ds_chungtu()    
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)            
          }
          else{
            this.$toast.error("Tạo chứng từ thất bại " + _ketqua.toString())
          }       
        }else{
          this.$toast.error("Không tìm thấy biên bản bàn giao: " + ma_bbbg)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    
    async btnCapNhatVattu_ThuHoi_Click(){
      try{
        this.loading(true)
        if(this.params.p_NghiepVu == null || this.params.p_NghiepVu == ""){
          this.$toast.error("Chưa chọn nghiệp vụ")
          return
        }
        if(this.params.p_NghiepVu == "286" && this.kieugoi_id == 2){
          if(this.Enabled.tsbtnNhapMoi){
            this.$toast.error("Bạn chưa nhập mới không thể chọn mã csht !")
            return
          }
          if(this.gridDS_VT_THUHOI.length == 0){
            this.$toast.error("Không có dữ liệu vật tư tài sản !")
            return
          }
          if(this.DS_VT_THUHOI_Checked.length == 0){
            this.$toast.error("Bạn chưa chọn dòng vật tư nào !")
            return
          }
          if (! await this.KiemTraDuLieu(true, false, "THUHOITS")) { return }
          let loc_csht = await this.kiemTraLocCSHT({
            data: this.DS_VT_THUHOI_Checked,
            khoId: this.params.p_DenKho
          })
          if(loc_csht.length > 0){
            var isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm("Kho " + this.params.p_DenKho + " đang có mã csht khác " 
                            + loc_csht[0].MA_CSHT + ". Bạn có muốn tiếp tục không? ", {
                title: "Thông báo",
                centered: true,
                size: "md",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              })
              .then((value) => {
                if (value) {
                  isBoxConfirm = true;
                } else {
                  return;
                }
              });

            if (!isBoxConfirm) {
              return;
            }
          }

          let t_ctct = []
          let t_ctbi = []
          for (let dr of this.DS_VT_THUHOI_Checked){
            if(dr.SERIAL != ""){
              var z = t_ctct.filter(l=>l.MA_VT == dr.MA_VT && l.DONGIA.toString() == dr.DONGIA.toString())
              if(z.length > 0){
                z[0].SL_NHAP = parseInt(z[0].SL_NHAP) + parseInt(dr.SOLUONG)
              }else{
                t_ctct.push({
                  MA_VT : dr.MA_VT,
                  DONGIA : parseInt(dr.DONGIA),
                  SL_NHAP : parseInt(dr.SOLUONG),
                  VMA_PDA: dr.MADUAN
                })
              }
              t_ctbi.push({
                MA_VT : dr.MA_VT,
                SOLUONG : -1,
                ID_CHITIET : dr.IDCHITIET,
                SERIAL: dr.SERIAL
              })
            }else{
              t_ctct.push({
                MA_VT : dr.MA_VT,
                DONGIA : parseInt(dr.DONGIA),
                SL_NHAP : parseInt(dr.SOLUONG),
                VMA_PDA: dr.MADUAN
              })
              t_ctbi.push({
                MA_VT : dr.MA_VT,
                SOLUONG : -1,
                ID_CHITIET : dr.IDCHITIET,
                SERIAL: ""
              })
            }          
            
          }
          var _ketqua = await this.SinhChungTuIMSBienBan({
            kieu: 2,
            khoNhanId: this.params.p_DenKho,
            ngayCt: this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss'),
            ndGiao: this.params.p_txtNoiDung,
            nghiepVuId: this.params.p_NghiepVu,
            maPda: row.ProjectCode,
            maHd: null ,
            maPo: null,
            bbBg: null,
            dsVt: ketqua_,
            dsChiTiet: [{}],
            nguoiCn: this.$auth.getUserName(),//ttnd.may_cn
            mayCn: "",//ttnd.may_cn
            ipCn: "",//ttnd.ip
            khoGiaoId: this.params.p_TuKho,
            loaiCtId: 1,
            donViGiaoId: this.$auth.getDonViID(),
            donViNhanId: this.$auth.getDonViID(),
            loaiPhieuId: null,
            mucDichId: this.params.p_MucDich
          })
          if (_ketqua.toString().startsWith("OK")){
            this.$toast.success("Tạo chứng từ thành công")
            let vchungtu_tmp_id = parseInt(_ketqua.toString().Split('-')[1])
            await this.Load_ds_chungtu()      
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)          
          }
          else{
            this.$toast.error("Tạo chứng từ thất bại " + _ketqua.toString())
          }          
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }      
    },
    openPopupCSHT(){
      this.$bvModal.show('popupCSHT')
    },
    closePopupCSHT(){     
      this.$bvModal.hide('popupCSHT') 
      this.$refs.popup_cboMa_PDA.show()      
    },
    pictureEdit1_Click(){
      this.NAP_DS_CSHT()
    },
    revious_CHST(){
      this.params.pageNumberTQ = this.params.pageNumberTQ == 1 ? 1 : this.params.pageNumberTQ - 1
      this.NAP_DS_CSHT()
    },
    next_CHST(){
      this.params.pageNumberTQ = this.params.pageNumberTQ == 1000 ? 1000 : this.params.pageNumberTQ + 1
      this.NAP_DS_CSHT()
    },
    onChangePageNumberTQ(){
      if(this.params.pageNumberTQ < 1){
        this.params.pageNumberTQ = 1
      }else if(this.params.pageNumberTQ > 1000){
        this.params.pageNumberTQ = 1000
      }
      this.NAP_DS_CSHT()
    },
    async NAP_DS_CSHT(){
      try{
        this.loading(true)
        this.grcMa_CSHT = []
        var tinh = this.params.p_Tinh == null || this.params.p_Tinh == "" ? this.$auth.getMaTinh() : this.params.p_Tinh
        var data = await this.getDSCSHT({ //sửa lại theo API tichhop
          name: this.params.txtTK_TenCSHT,
          page_index: parseInt(this.params.pageNumberTQ),
          ma_csht: this.params.txtTK_MaCSHT.trim()  == "" ? "" : "['" + this.params.txtTK_MaCSHT.trim() + "']"  ,
          ma_tinh: "['" + tinh + "']" ,
          page_size: null,
          rims_status: null
        })
      
        if('message' in data && data.message == "successfully"  && 'data' in data && data.data.length > 0 && "json_station" in data.data[0]){
          this.grcMa_CSHT = data.data[0].json_station
        }else{
          this.grcMa_CSHT = []
          this.$toast.error("Không thể lấy dữ liệu CSHT!");
        }       
      }catch(e){
         this.$toast.error("Đã có lỗi xảy ra, vui lòng thử lại!");
      }finally{
        this.loading(false)
      }
    },
    clearCSHT(){
      this.params.p_IMS_CSHT = ""
    },
    async gridDSMa_PDAFocus(row){
      if(row){
        if(this.params.p_NghiepVu == "292" || this.params.p_NghiepVu == "293" || this.params.p_NghiepVu == "294" ){
          this.params.p_IMS_CSHT = row.ma_csht
        }        
      }else{
        this.params.p_IMS_CSHT = ""
      }
    },
    async repositoryItemButtonEdit6_ButtonClick(){
      if (this.params.p_NghiepVu == "292" || this.params.p_NghiepVu == "293" || this.params.p_NghiepVu == "294"){
        this.params.p_IMS_CSHT = this.grvMa_CSHT_FocusedRow.MA_CSHT
      }
    },
    
    convertDateTime_DDMMYYYY_to_YYYYMMDD(datetime) {
      datetime = datetime.split(" ");
      var thisDate = datetime[0].split("-");
      var newDate = [thisDate[2], thisDate[1], thisDate[0]].join("/");
      return newDate;
    },
    async tsbtnDongBoIMS_Click(){
      try{
        this.loading(true)
        if(this.gridChungTu.length == 0){
          this.$toast.error("Không có dữ liệu chứng từ !")             
          return  
        }
        if(this.listChungTuChecked.length == 0){
          this.$toast.error("Bạn chưa chọn chứng từ cần đồng bộ !")             
          return  
        }
        var dem = 0
        
        var DSChungTuChecked = this.gridChungTu.filter(a=>this.listChungTuChecked.includes(a.CHUNGTU_ID))
        var loi_excel = []
        for(const dr of DSChungTuChecked){
          var ketqua = ""
          var check = await this.kiemTraChungTuDongBo(dr.CHUNGTU_ID)         
          if(check.length == 0){
            ketqua += "Không tìm thấy chứng từ ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })
            continue
          }

          var ma_md = check[0].ma_md
          var ma_pda = check[0].ma_pda
          var ma_csht = check[0].ma_csht
          var ma_dv_ql = check[0].ma_dv_ql
          var ma_dv_sd = check[0].ma_dv_sd
          var ma_hd_po = check[0].ma_hd_po
          var contract_info_id = check[0].contract_info_id
          var ngay_ct = check[0].ngay_ct

          if(!["NV.XUATIMS","NV.ACAP","NV.CHUYENNGUON"].includes(ma_md)){
            ketqua += "Bạn chưa chọn đúng nghiệp vụ ! Không thể đồng bộ lên IMS ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })
            continue          
          }

          if(ma_pda == null || ma_pda == ""){
            ketqua += "Chưa có mã dự án! Không thể đồng bộ lên IMS ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })
            continue           
          }

          if(ma_md != "NV.CHUYENNGUON" && this.batbuoc_chst && (ma_csht == null || ma_csht == "")){
            ketqua += "Chưa có mã CSHT! Không thể đồng bộ lên IMS ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })
            continue         
          }

          if(ma_dv_sd == null || ma_dv_sd == ""){
            ketqua += "Chưa có mã đơn vị sử dụng! Không thể đồng bộ lên IMS ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })
            continue          
          }

          if(ma_dv_ql == null || ma_dv_ql == ""){
            ketqua += "Chưa có mã đơn vị quản lý! Không thể đồng bộ lên IMS ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
            loi_excel.push({
                CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })
            continue           
          }
          if(ma_md != "NV.ACAP"){
            if(ma_hd_po == null || ma_hd_po == ""){
              ketqua += "Chưa có mã hợp đồng! Không thể đồng bộ lên IMS ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
              loi_excel.push({
                CHUNGTU_ID: dr.CHUNGTU_ID,
                SOPHIEU: dr.SOPHIEU,
                LYDO: ketqua
              })
              continue           
            }

            if(contract_info_id == null || contract_info_id == ""){
              ketqua += "Chưa có contract_info_id ! Liên hệ với Admin ERP để xử lý ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
              loi_excel.push({
                CHUNGTU_ID: dr.CHUNGTU_ID,
                SOPHIEU: dr.SOPHIEU,
                LYDO: ketqua
              })
              continue           
            }
          }          

          var chitiet_vattu = await this.kiemTraDongBoIMS(this.vchungtu_id)
          if(chitiet_vattu.length == 0){
            ketqua += "Không có chi tiết vật tư thiết bị! Không thể đồng bộ lên IMS ! (" + dr.CHUNGTU_ID + " - " + dr.SOPHIEU + ") \r\n"
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })      
            continue           
          }
          var i_mode = (ma_md == "NV.XUATIMS") || (ma_md == "NV.CHUYENNGUON") ? "1" : "0"
          var dongbo = await this.xuLyDongBoIMS({
            projectCode: ma_pda,
            contractNumber: contract_info_id,
            soPhieu: this.soph,
            ngayCt: ngay_ct,
            maDvQl: ma_dv_ql,
            maDvSd: ma_dv_sd,
            maCsHt: ma_csht,
            iMode: i_mode,
            chiTietVatTu: chitiet_vattu
          })

          if(dongbo == "Thực hiện thành công"){      
            var up_trangthai = await this.capNhatBBBGIMS({
              chungTuId: this.vchungtu_id,
              kieu: 0
            })
            dem += 1;
          }else{              
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: dongbo
            })      
          }
         
        }

        if(dem == DSChungTuChecked.length){
          this.$toast.success("Đồng bộ thành công " + dem + "/" + dem + " phiếu");        
        }else{
          this.$toast.error("Có " + loi_excel.length + " chứng từ lỗi");         
          await this.SaveXlsAndOpenFile('LoiDongBoIMS.xlsx',"sheet1",loi_excel)
        }  
        await this.btnTimKiem_Click()
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false)
      }    
    },
    txtNoiDung_DoubleClick(){

    },
    async cboMaPhieu_ButtonClick(){
      try{
        this.loading(true)
        if(this.params.p_MucDich == null || this.params.p_MucDich == ""){
          this.$toast.error("Chưa chọn mục đích!")
          return
        }
        var data = await this.getDSPhieuNV(this.params.p_MucDich) 
        if(data == "null"){
          this.cboMaPhieu = []
        }else{
          this.cboMaPhieu = JSON.parse(data)
        }
     
      }catch(e){
        this.cboMaPhieu = []
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },
    cboMaPhieu_EditValueChanged(){      
      this.params.p_Ma_PDA = this.params.p_MaPhieu == null ? "" : this.params.p_MaPhieu
    },
    async cboLocDCDonVi_EditValueChanged(){
      if(this.params.p_LocDCDonVi == "0" || this.params.p_LocDCDonVi == null){
        this.cboLocDCDonVi = []
        this.gridDieuChinhVT = this.dieuchuyen_clone
        return
      }
      this.cboLocDCLoaiCT = await this.getDanhMucLoaiChungTu(this.params.p_LocDCDonVi)
    },
    async cboLocDCLoaiCT_EditValueChanged(){
      if(this.params.p_LocDCLoaiCT == null || this.params.p_LocDCLoaiCT == ""){
        return
      }
      this.gridDieuChinhVT = await this.kiemTraSoLuongQLTS({
        kieu: 0,
        data: this.dieuchuyen_clone,
        maPda: this.params.p_Ma_PDA,
        timKiem: this.params.p_LocDCDonVi,
        loaiCtId: this.params.p_LocDCLoaiCT
      })
    },
    repositoryItemCheckEdit3_CheckedChanged(){

    },
    
    repositoryItemCheckEdit2_CheckedChanged(){

    },
    DS_VT_THUHOI_CheckBox: function (dataItem) {    
      this.DS_VT_THUHOI_Checked = dataItem   
    },
    DieuChinhVT_CheckBox: function (dataItem) {    
      this.DSDieuChinhVT_Checked = dataItem   
    },
    async btnCapNhatDieuChinh_Click(){
      try{
        this.loading(true)
        if(this.params.p_LocDCDonVi == "0" || this.params.p_LocDCDonVi == null){
          this.$toast.error("Bạn chưa chọn điều kiện lọc đơn vị !");
          return
        }

        if(this.params.p_LocDCLoaiCT == "" || this.params.p_LocDCLoaiCT == null){
          this.$toast.error("Bạn chưa lọc dữ liệu vật tư điều chuyển !");
          return
        }

        if(this.params.p_MucDich == "" || this.params.p_MucDich == null){
          this.$toast.error("Bạn chưa chọn mục đích !");
          return
        }

        if(this.params.p_TuKho == "" || this.params.p_TuKho == null){
          this.$toast.error("Bạn chưa chọn từ kho !");
          return
        }

        if(this.params.p_DenKho == "" || this.params.p_DenKho == null){
          this.$toast.error("Bạn chưa chọn đến kho !");
          return
        }


        if(this.params.p_TuKho == this.params.p_DenKho ){
          this.$toast.error("Kho từ không được giống kho đến !");
          return
        }

        let locdv = this.this.params.p_LocDCDonVi
        let loc_ct = this.this.params.p_LocDCLoaiCT

        let c_kt_tukho = await this.kiemTraKhoTon({
          khoId: this.params.p_TuKho,
          kieu: 0
        })

        let c_kt_denkho = await this.kiemTraKhoTon({
          khoId: this.params.p_DenKho,
          kieu: 0
        })

        if(loc_ct == "1" || loc_ct == "2" || loc_ct == "3"){
          if(c_kt_tukho == 1){
            this.$toast.error("Bạn phải chọn kho từ là kho có tồn !");
            return
          }
          if(c_kt_denkho == 1){
            this.$toast.error("Bạn phải chọn kho đến là kho có tồn !");
            return
          }
        }else if (loc_ct == "99"){
          if(c_kt_tukho == 0){
            this.$toast.error("Nhập lẻ, bạn phải chọn kho từ là kho không có tồn !");
            return
          }
          if(c_kt_denkho == 0){
            this.$toast.error("Nhập lẻ, bạn phải chọn kho đến là kho có tồn !");
            return
          }
        }

        if(this.gridDieuChinhVT.length == 0){
          this.$toast.error("Không có dòng dữ liệu vật tư nào !");
          return
        }
        
        if(this.DSDieuChinhVT_Checked.length == 0){
          this.$toast.error("Bạn chưa tích chọn dòng vật tư nào !");
          return
        }

        let vattu_ok = []
        if(loc_ct == "1" || loc_ct == "2" || loc_ct == "3"){
          var k___ = await this.KiemTraDonGiaIMS({
            kieu: 1,
            data: this.DSDieuChinhVT_Checked,
            khoId: this.params.p_TuKho
          })
          if(k___.length == 0){
            this.$toast.error("Vật tư đã chọn không có tồn trên ĐHSX !")
            return;
          }
          var ds_vattu_ton = []
          for (let e of k___){
            ds_vattu_ton.push(e.VATTU_ID)
          }
          var dt_ton_kho = await this.getDSVatTuTonKho({
            tagForm: 0,
            khoId: this.params.p_TuKho,
            ngayCt: this.f_DateToString(this.params.p_dtpNgayDK),
            chungTuId: 0,
            dsVatTuTon: ds_vattu_ton.join(",")
          })
          if(dt_ton_kho.length == 0){
            this.$toast.error(this.params.p_Ma_PDA + " không tồn vật tư ở kho " + this.params.p_TuKho);
            return;
          }
          vattu_ok = k___
        }else{
          vattu_ok = this.DSDieuChinhVT_Checked
        }

        let loc_csht = await this.kiemTraLocCSHT({
          data: this.DSDieuChinhVT_Checked,
          khoId: this.params.p_DenKho
        })
        if(loc_csht.length > 0){
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Kho " + this.params.p_DenKho + " đang có mã csht khác " 
                          + loc_csht[0].MA_CSHT + ". Bạn có muốn tiếp tục không? ", {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } else {
                return;
              }
            });

          if (!isBoxConfirm) {
            return;
          }
        }

        var ketqua_ = ""
        if(loc_ct == "99"){
          let t_ctct = []
          let t_ctbi = []
          for (let dr of vattu_ok){
            if(dr.SOLUONG == 0 || dr.SOLUONG == ""){
              continue
            }
            if(dr.SERIAL != "" && dr.SERIAL != null){
              var z = t_ctct.filter(a=> a.MA_VT == dr.MAVTHH && a.DONGIA == dr.DONGIA)
              if(z.length > 0){
                z[0].SL_NHAP = parseInt(z[0].SL_NHAP) + parseInt(dr.SOLUONG)
              }else{
                t_ctct.push({
                  MA_VT : dr.MAVTHH,
                  DONGIA : parseInt(dr.DONGIA),
                  SL_NHAP : parseInt(dr.SOLUONG),
                  VMA_PDA: dr.MADUAN
                })
              }
              t_ctbi.push({
                MA_VT : dr.MAVTHH,
                SOLUONG : 1,
                ID_CHITIET : dr.IDCHITIET,
                SERIAL: dr.SERIAL
              })
            }else{
              t_ctct.push({
                MA_VT : dr.MAVTHH,
                DONGIA : parseInt(dr.DONGIA),
                SL_NHAP : parseInt(dr.SOLUONG),
                VMA_PDA: dr.MADUAN
              })
              t_ctbi.push({
                MA_VT : dr.MAVTHH,
                SOLUONG : dr.SOLUONG,
                ID_CHITIET : dr.IDCHITIET,
                SERIAL: ""
              })
            }

          }

          var ketqua_ = await this.sinhDieuChuyenQLTS({
            kieu: this.params.p_LocDCDonVi,
            khoNhanId: this.params.p_DenKho,
            ngayCt: this.f_DateToString(this.params.p_dtpNgayDK),
            ndGiao: "Điều chuyển QLTS",
            nghiepVuId: this.params.p_NghiepVu,
            maPda: this.params.p_Ma_PDA,           
            dsVt: t_ctct,
            dsChiTiet: t_ctbi,
            nguoiCn: this.$auth.getUserName(),
            mayCn: "",
            ipCn: "",
            khoGiaoId: this.params.p_TuKho,
            loaiCtId: this.params.p_LocDCLoaiCT,
            donViGiaoId: this.$auth.getDonViID(),
            donViNhanId: this.$auth.getDonViID(),
            loaiPhieuId: null,
            mucDichId: this.params.p_MucDich
          })
        }else{
          var ketqua_ = await this.sinhDieuChuyenQLTS({
            kieu: this.params.p_LocDCDonVi,
            khoNhanId: this.params.p_DenKho,
            ngayCt: this.f_DateToString(this.params.p_dtpNgayDK),
            ndGiao: "Điều chuyển QLTS",
            nghiepVuId: this.params.p_NghiepVu,
            maPda: this.params.p_Ma_PDA,           
            dsVt: vattu_ok,
            dsChiTiet: [{}],
            nguoiCn: this.$auth.getUserName(),
            mayCn: "",
            ipCn: "",
            khoGiaoId: this.params.p_TuKho,
            loaiCtId: this.params.p_LocDCLoaiCT,
            donViGiaoId: this.$auth.getDonViID(),
            donViNhanId: this.$auth.getDonViID(),
            loaiPhieuId: null,
            mucDichId: this.params.p_MucDich
          })
        }
        if (_ketqua.toString().startsWith("OK")){
          this.$toast.success("Tạo chứng từ thành công")
          let vchungtu_tmp_id = parseInt(_ketqua.toString().Split('-')[1])
          await this.Load_ds_chungtu()   
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)                       
        }
        else{
          this.$toast.error("Tạo chứng từ thất bại " + _ketqua.toString())
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false)
      }
    },
    SelectedTabSPDA(kieu){
      this.tabSPDA = kieu
    },
    cboMa_PDA_DoubleClick(){
      // this.$refs.popup_cboMa_PDA.show()
      if(!this.OpenCboMa_PDA){
        this.$toast.error("Nghiệp vụ này không có mã dự án!");
        return
      }
      this.$bvModal.show('popupSPDA')
    },

    async TraCuuTTDongBo(data){
      console.log(data , " = TraCuuTTDongBo")
      var f = this.$refs.frmTraCuuTTDongBo
      f.txtSoPhieu = data.SOPHIEU
      f.vchungtu_id = data.CHUNGTU_ID
      f.OpenDialog()
    },
    async TraCuuChungTuDeNghi(data){     
      const routeData = this.$router.resolve({path: 'TraCuuDeNghi', query: { phieu_de_nghi: data.SOPHIEU,loaidn_id: 0 }})
      window.open(routeData.href, '_blank');
    },
    showDSVatTu(){      
      this.$bvModal.show('popupDSVT');
    },

    async btnChon_PDA_ButtonClick(row){
      try{
        
        console.log(row, " = btnChon_PDA_ButtonClick")        
        if(this.params.p_NghiepVu == null || this.params.p_NghiepVu == ""){
          this.$toast.error("Chưa chọn nghiệp vụ!")
          return
        }
        if(this.params.p_NghiepVu == "280"){
          if(this.Enabled.tsbtnNhapMoi){
            this.$toast.error("Bạn chưa nhập mới không thể chọn dự án!")
            return
          }         
          if (! await this.KiemTraDuLieu(true, false, "GHILAI_PDA")){ return }

          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Ban có chắc chắn muốn chọn mã dự án " +  row.ProjectCode + " không? ", {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } 
            });

          if (!isBoxConfirm) {              
            return;
          }

          this.loading(true)
          if(this.kieugoi_id == 2){
            this.params.p_Ma_PDA = row.ProjectCode
            var dt_temp = await this.getDSAssetTheoMaDuAn(this.params.p_Ma_PDA)
            
            // không có dữ liệu trả về đúng chuẩn nên nhắm mắt code
            if(dt_temp != null && dt_temp != ""){
              var t_ct = {
                VNGAY_CT : this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss'),
                VND_GIAO : this.params.p_txtNoiDung,
                VMUCDICH_ID: this.params.p_MucDich,
                VKIEUPHIEU_ID : null
              }
              var to_js = []
              for(const e of dt_temp){
                to_js.push({
                  AssetCodeType: e.AssetCodeType,
                  CodeAcceptance: e.CodeAcceptance
                })
              }
              var ketqua_kt = await this.getDSVatTuTheoNghiepVu({
                kieu: 2,
                ds: to_js,
                maPda: null,
                tenPda: null,
                pdaId: null
              })
              if(ketqua_kt != "1"){
                this.$toast.error(ketqua_kt)
                return
              }

              var t_ctct = []
              var t_ctbi = []
              for (let dr of dt_temp){
                if(dr.SerialNumberOffice != ""){
                  var z = t_ctct.filter(l=>l.MA_VT == dr.CodeAcceptance && l.DONGIA != dr.PriceForeign)
                  if(z.length > 0){
                    z[0].SL_NHAP = z[0].SL_NHAP + parseInt(dr.AssetNumber)
                  }else{
                    t_ctct.push({
                      MA_VT : dr.CodeAcceptance,
                      DONGIA : parseInt(dr.PriceForeign),
                      SL_NHAP : parseInt(dr.AssetNumber),
                      VMA_PDA : row.ProjectCode,
                      VTEN_PDA : row.ProjectName,
                      VPDA_ID : row.ProjectID,
                    })
                  }

                  t_ctbi.push({
                    MA_VT : dr.CodeAcceptance,
                    SOLUONG : 1,
                    ID_CHITIET : dr.AssetCode,
                    SERIAL : dr.SerialNumberOffice,
                    MA_NHOM : row.AssetCodeType,                 
                  })

                }else{
                  t_ctct.push({
                    MA_VT : dr.CodeAcceptance,
                    DONGIA : parseInt(dr.PriceForeign),
                    SL_NHAP : parseInt(dr.AssetNumber),
                    VMA_PDA : row.ProjectCode,
                    VTEN_PDA : row.ProjectName,
                    VPDA_ID : row.ProjectID,
                  })

                  t_ctbi.push({
                    MA_VT : dr.CodeAcceptance,
                    SOLUONG : -1,
                    ID_CHITIET : dr.AssetCode,
                    SERIAL : "",
                    MA_NHOM : row.AssetCodeType,                 
                  })
                }
                
                var ketqua_ = await this.SinhCTQuanLyTaiSan({
                  kieu: 1,
                  khoNhanId: this.params.p_DenKho,
                  ngayCt: this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss'),
                  ndGiao: this.params.p_txtNoiDung,
                  nghiepVuId: this.params.p_NghiepVu,
                  maPda: row.ProjectCode,
                  dsVt: t_ctct,
                  dsChiTiet: t_ctbi,
                  nguoiCn: this.$auth.getUserName(),
                  mayCn: "",
                  ipCn: "",
                  khoGiaoId: this.params.p_TuKho,
                  loaiCtId: 1,
                  donViGiaoId: this.$auth.getDonViID(),
                  donViNhanId: this.$auth.getDonViID(),
                  loaiPhieuId: null,
                  mucDichId: params.p_MucDich
                })
                if(ketqua_.startsWith('OK')){
                  this.$toast.success("Tạo chứng từ thành công")
                  let vchungtu_tmp_id = parseInt(ketqua_.toString().Split('-')[1])
                  await this.Load_ds_chungtu()
                  await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
                }else{
                  this.$toast.error("Tạo chứng từ thất bại " + ketqua_)
                }                
              }
            }else{              
              this.$toast.error("Không có dữ liệu tài sản ngắn hạn!")
            }
            
          }else if( this.params.p_LoaiChungTu == "2" ){
            this.params.p_Ma_PDA = row.ProjectCode
            var dt_temp = await this.getDSAssetTheoMaDuAn(this.params.p_Ma_PDA)
            
            // không có dữ liệu trả về đúng chuẩn nên nhắm mắt code
            if(dt_temp != null && dt_temp != ""){
              var ketqua_ = await this.KiemTraDonGiaIMS({
                kieu: 0,
                data: dt_temp,
                khoId: this.params.p_TuKho
              })
              if(ketqua_.length == 0){
                this.$toast.error(this.params.p_Ma_PDA + " không có vật tư nào thay đổi đơn giá, không thể tạo chứng từ xuất điều chỉnh đơn giá !")
                return;
              }
              var dtVatTu = await this.getDSVatTuTonKho({
                tagForm: 0,
                khoId: this.params.p_TuKho,
                ngayCt: this.f_DateToString(this.params.p_dtpNgayDK),
                chungTuId: 0,
                dsVatTuTon: ketqua_.join(",")
              })
              if(dtVatTu.length == 0){
                this.$toast.error(this.params.p_Ma_PDA + " không tồn vật tư khác đơn giá ở kho " + this.params.p_TuKho);
                return;
              }

              var _ketqua = await this.SinhChungTuIMSBienBan({
                kieu: 2,
                khoNhanId: this.params.p_DenKho,
                ngayCt: this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss'),
                ndGiao: this.params.p_txtNoiDung,
                nghiepVuId: this.params.p_NghiepVu,
                maPda: row.ProjectCode,
                maHd: null ,
                maPo: null,
                bbBg: null,
                dsVt: ketqua_,
                dsChiTiet: [{}],
                nguoiCn: this.$auth.getUserName(),//ttnd.may_cn
                mayCn: "",//ttnd.may_cn
                ipCn: "",//ttnd.ip
                khoGiaoId: this.params.p_TuKho,
                loaiCtId: 1,
                donViGiaoId: this.$auth.getDonViID(),
                donViNhanId: this.$auth.getDonViID(),
                loaiPhieuId: null,
                mucDichId: this.params.p_MucDich
              })
              if (_ketqua.toString().startsWith("OK")){
                this.$toast.success("Tạo chứng từ thành công")
                let vchungtu_tmp_id = parseInt(_ketqua.toString().Split('-')[1])
                await this.Load_ds_chungtu()             
                await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)   
              }
              else{
                this.$toast.error("Tạo chứng từ thất bại " + _ketqua.toString())
              }
            }
          }
        }else if( this.params.p_NghiepVu == "292" || this.params.p_NghiepVu == "293" || this.params.p_NghiepVu == "294"){
          this.params.p_Ma_PDA = row.ProjectCode
          var new_chst = ""
          if(this.params.p_NghiepVu != "294" && this.batbuoc_chst){
            var find_csht = await this.getMaCoSoHaTang({khoId: this.params.p_TuKho})
            if(find_csht == null){
              this.$toast.error("Từ kho phải là kho có CSHT, hãy kiểm tra lại !")
              return
            }
            new_chst = find_csht
          }
          let dt_hdpo = await this.getContractInfo({
            maDuan: this.params.p_Ma_PDA,
            maCsht: new_chst
          })
          this.cboHD_XuatIMS = []
          if(dt_hdpo == "" || dt_hdpo == null){
            this.$toast.error("Không có thông tin hợp đồng - PO")            
            return
          }else{
            var data = JSON.parse(dt_hdpo)
            if("NewDataSet" in data && "Table" in data.NewDataSet){             
              if(Array.isArray(data.NewDataSet.Table)) { // trả về dạng List Object
                this.cboHD_XuatIMS = data.NewDataSet.Table 
              }else{    
                this.cboHD_XuatIMS.push(data.NewDataSet.Table)          // trả về dạng object            
              }   
            }
          }
          
          this.cboHD_XuatIMS.unshift({
            PO: "",
            ContractName: "",
            SignOffDate: "",
            ContractInfoID: "",
            ParentContractInfo: "",
            ContractNumber: ""
          })
          console.log(this.cboHD_XuatIMS , " = this.cboHD_XuatIMS")
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async dtpNgayDK_ButtonClick(){
      try{
        if(this.vchungtu_id == 0 || !this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Từ kho phải là kho có CSHT, hãy kiểm tra lại !")
          return
        }
        var check = await this.capNhatNgayChungTu({
          ngayCt : this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss'),
          chungTuId :  this.vchungtu_id
        })        
        for (let e of this.gridChungTu){
          if(e.CHUNGTU_ID == this.vchungtu_id){
            e.NGAY_CT = this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY HH:mm:ss')
            break
          }
        }
        this.$refs.gridviewChungTu.grid.refreshColumns()
        this.$toast.success("Cập nhật ngày chứng từ thành công")
      }catch(e){
        this.$toast.error(e.data.message)
      }
    },
    async dieuchinh_vt_excel(kieu){
      if(this.vchungtu_id <= 0){
        this.$toast.error("Chưa chọn chứng từ!")
        return
      }
      if(this.gridVatTu.length == 0){
        this.$toast.error("Chưa có vật tư chứng từ")
        return
      }
      if(this.ttph_id == 4){
        this.$toast.error("Chứng từ đã hoàn thiện ! Không thể dùng chức năng này !")
        return
      }
      document.getElementById('fileUploadGhiChu').click()
    },
    async onChangeGhiChu(){
      this.arrayImportFile.filexcel = this.$refs.fileUploadGhiChu.$refs.input.files[0]
      
      let json_list = []
      try {
        this.loading(true)
        if (this.checkfile()) {
          let tencot
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: "binary" })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers  = data.shift() // bỏ dòng header đầu tiên trong data
            
            // then build the json for each row
            var result = data.map(function(a) {
                var jsonRow = {};
                a.forEach(function(cellValue, cellIndex) { 
                    jsonRow[headers[cellIndex]] = cellValue;
                });
                headers.forEach(b=> {
                  if (!Object.keys(jsonRow).includes(b)){
                    jsonRow[b] = ''
                  }
                })
                return jsonRow;
            });
            
            // --------------- --------------        
            if(result.length == 0){
              this.$toast.error("File không có dữ liệu")
              return
            }else{
              if(!("CTCT_ID" in result[0] && "GHI_CHU_MOI" in result[0])){
                this.$toast.error("Cấu trúc file phải có 2 cột CTCT_ID và GHI_CHU_MOI!")
                return
              }
            }

            var kq_kt = await this.KiemTraVatTuDocfile({
              "data": result,
              "chungTuId": this.vchungtu_id,
              "kieu": 3
            })
         
            if (kq_kt.toString().includes("OK~")){
              this.$toast.success("Đã cập nhật ghi chú thành công!")
              await this.Load_ds_vattu()
            }
            else{
              this.$toast.error(kq_kt.toString())
              return
            }
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error)
        return
      } finally {
        this.$refs.fileUploadGhiChu.reset()
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async tsbtnExcelGhiChu_Click(){
      this.dieuchinh_vt_excel(3)
    },
    async tsbtnDongBoQLTS_Click(){
      if(this.vchungtu_id <= 0){
        this.$toast.error("Chưa chọn chứng từ không thể đồng bộ!")
        return
      }
      this.$refs.DongBoCCDC.sophieu = this.params.p_txtSoPhieu
      this.$refs.DongBoCCDC.chungtu_id = this.vchungtu_id
      this.$refs.DongBoCCDC.dtpNgayDK = this.params.p_dtpNgayDK
      this.$refs.DongBoCCDC.Enabled.tsbtnDongBoQLTS = this.gridviewChungTu_FocusedRow.CCDC_QLTS_ID == null || this.gridviewChungTu_FocusedRow.CCDC_QLTS_ID == ""
      this.$refs.DongBoCCDC.openDialog()
    }
  },
  created: async function() {
    try{   
      this.loading(true)
      this.Tag = ["1","2"].includes(this.$route.query.Tag) ? this.$route.query.Tag : "1"      
      await this.frmNhapXuatVT_Load()
      this.gridChungTu.length ? await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID) : ""
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
  },
  watch: {
    listChungTuChecked: function (value) {
      this.listChungTuChecked = value;  
    },
    checkAllChungTu: function (value) {
      if (value) {
        for (const e of this.gridChungTu) {
          this.listChungTuChecked.push(e.CHUNGTU_ID);
        }
      } else {
        this.listChungTuChecked = [];
      }     
    },

    cboLocVattu_checked: function (value) {
      this.cboLocVattu_checked = value;        
    },
    checkAllLVT: function (value) {
      if (value) {
        for (const e of this.cboLocVattu) {
          this.cboLocVattu_checked.push(e.vatTuID);
        }
      } else {
        this.cboLocVattu_checked = [];
      }     
    },
  },
  destroyed () {
  }
};
</script>
<style>

  .e-grid .e-content {
    height: auto !important;
  }  
  .pda_table{
    position: absolute;
    z-index: 1;
    max-height: 200px;
    background: aliceblue;
    overflow-y: scroll;
    right: 0;
  }
  #frmNhapDieuChinh_HNI_dialog-content{
    flex: none !important;
  }
</style>