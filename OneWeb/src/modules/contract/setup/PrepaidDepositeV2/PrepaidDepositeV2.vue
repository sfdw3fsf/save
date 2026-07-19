<template src="./PrepaidDepositeV2.html"></template>
<style scoped src="./PrepaidDepositeV2.scss"></style>
<script>
import Vue from 'vue'
import moment from 'moment'
import DateTimeLib from '@/utils/DateTimeLib'
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import breadcrumb from "@/components/breadcrumb";
import KDataGrid from "@/components/kylq_components/KDataGrid";
import SearchContractModal from "@/modules/contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import {
  LoaiHopDong,
  TrangThaiHD,
  DichVuVienThong,
  TRANGBI,
  KieuLapDat,
  KHOANMUC_TT,
  Loai_KM,
  LoaiHinhTB,
} from "@/modules/admin/category/MultiServicePackage/enum.js";
import api_installnewsubs from "@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js";
import api from "./API.js";

export default {
  name: "PrepaidDepositeV2",
  components: {
    breadcrumb,
    KDataGrid,
    DatePickerPlugin,
    DatePicker,
    SearchContractModal,
    SearchAccount,
  },
  props: ["dulieu"],
  data() {
    return {
      button: {
        tsbtnNhapMoi: true,
        tsbtnGhiLai: true,
        tsbtnXoa: true,
        tsbtnHuyBo: true,
        tsbtnThemTB: false,
        tsbtnXoaTB: false,
        tsbtnChapNhanTT: false,
        tsbtnHuyBoTT: false
      },
      header: {
        title: "Đặt cọc trả trước",
        list: [
          {
            name: "Đặt cọc trả trước",
            link: { name: "Ui.cards" },
          },
        ],
      },
      tag: '',
      formWidth: 0,
      re_render: 1,
      dateFormat: "DD/MM/YYYY",
      monthFormat: "MM/YYYY",
      position: { X: "center", Y: "center" },
      keyenter: 0, //0: maGD; 1: maTB
      isMaTb_enter: false,
      tieuchi_id: "",
      thongtin_tracuu: "",
      tt_nd: {},
      // Popup
      popupComponentHeader: "",
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed,
      },
      popupComponentName: "",
      //  
      param_dstb:[], // Tú thêm     
      // leftForm
      txtMaGD: {value: '', Enabled: true},
      dtpNgayYeuCau: {value: moment(new Date()).format("DD/MM/YYYY"),},
      txtMaTB: {value: ''},
      cboKieuLD: {value: 0, Items: []},
      cboDichVuVT: {value: 0, text: '', Items: []},
      cboLoaiHinhTB: {value: 0, text: '', Items: []},
      cboGoiCuocAdsl: {value: 0, text: '', Items: []},
      txtGoiDaDV: {value: ''},
      txtTenTB: {value: ''},
      txtTenKH: {value: ''},
      txtDiaChiKH: {value: ''},
      txtDiaChiLD: {value: ''},
      // rightForm
      cboNhomDC: {value: 0, Items: [], Enabled: true},
      cboTraTruoc: {value: 0, Items: [], Enabled: true},
      cboSoThangDC: {value: 0, Items: [], Enabled: true},
      cboCTTraTruoc: {value: 0, Items: [], Enabled: true},
      txtCuocDC: {value: 0, Enabled: false},
      txtTienTD: {value: 0, Enabled: false},
      dtpNgayBDDC: {value: moment(new Date()).format("DD/MM/YYYY"),},
      dtpNgayKTDC: {value: moment(new Date()).format("DD/MM/YYYY"),},
      txtGhiChu: {value: ''},
      // Tham so mac dinh
      filterbox: {
        ctv: {
          ctv_id: "",
          chkCTV: false,
          txtCTV: "",
        },
        nguoigt: {
          nguoigt_id: "",
          chkNguoiGT: false,
          txtNguoiGT: "",
        },
      },
      khong_load_ctv: 0,
      khong_ht_tracuudb: 0,
      nhap_CTV: 0,
      check_chuanhoa_cntt: false,
      kiemtra_no_tratruoc: 0,
      phainhap_thoihan: false,
      _MACDINH_THOAIDC_LUITHANG: 0,
      MACDINH_CTV_NGUOIDUNG: -1,      
      chkTB_CungNhom: true,
      // TT hidden
      ma_gd: '',
      loaihd_id: 31,      
      khachhang_id: 0,
      hdkh_id: 0,
      ma_kh: "",
      nhomtb_id: 0,
      hdtb_id: 0,
      thuebao_id: 0,      
      goi_id: 0,
      chuyenmang: 0,
      doituong_id: 0,
      phuongld_id: 0,
      phold_id: 0,
      apld_id: 0,
      khuld_id: 0,
      tocdo_id: 0,
      muccuoc_id: 0,
      trangbi_id: 0,      
      tthd_id: 0,
      huonggiaotn_id: 0,
      quytrinh_id: 0,
      key_gridTB: 1,
      key_gridHD: 1,
      // Tao du lieu
      ds_tien_khoanmuc: [],
      KHUYENMAI_HDTB: [],
      DATCOC_HDTB: [],
      CT_TIENHD: [],
      // Table
      gridDSThueBao: {
        list: [],
        groupSettings: {
          columns: ['NHOMTB_LABEL']
        },
        header: [
          {
            fieldName: "IS_CHECK",
            headerText: "",
            allowFiltering: false,            
            textAlign: 'Center',
            width: '60',
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="checkbox" class="check" style="width: 18px; height: 18px" v-model="data.IS_CHECK" @change='onCheck'>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },           
                  },
                  methods: {
                    onCheck: function (val) {                    
                      this.parent.onCheckNhomTB(this.data)
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: "NHOMTB_LABEL",
            headerText: "Nhóm TB",
            allowFiltering: true,
            textAlign: 'Center',
            width: '100',
            isGroupedColumn: true
          },
          {
            fieldName: "NHOMTB_ID",
            headerText: "Nhóm TB",
            allowFiltering: true,
            textAlign: 'Center',
            width: '100',
          },
          {
            fieldName: "MA_TB",
            headerText: "Số máy/Acc",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_TB",
            headerText: "Tên thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "LOAIHINH_TB",
            headerText: "Loại hình",
            allowFiltering: true,
          },
          {
            fieldName: "TOCDO",
            headerText: "Tốc độ",
            allowFiltering: true,
          },
          {
            fieldName: "GOI_DADV",
            headerText: "Gói đa DV",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridDSCTTraTruoc: {
        list: [],
        groupSettings: {
          columns: ['MA_TB']
        },
        header: [      
          {
            fieldName: "MA_TB",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            isGroupedColumn: true
          },          
          {
            fieldName: "TEN_CTKM",
            headerText: "Chi tiết TT",
            allowFiltering: true,
            width: '350'
          },          
          {
            fieldName: "DATCOC_CSD",
            headerText: "Cước ĐC",
            allowFiltering: true,            
            width: '150',
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="text" class="form-control text" :value="cp_tien_dc" :disabled="data.KIEU_TRATRUOC == 1 ? false : true" 
                        @keypress="isNumber($event)" @change="onChangeText">
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_tien_dc: {
                      get() {
                        var tien_dc = this.data.DATCOC_CSD || this.data.DATCOC_CSD == 0
                          ? this.numberWithCommas(tien_dc.toString())
                          : "";
                        return tien_dc;
                      },
                      set(value) {
                        const replaced = value.replaceAll(",", "");
                        this.data.DATCOC_CSD = Number(replaced);
                      },
                    },
                  },
                  methods: {
                    onChangeText: function (e) {
                      let cuoc_dc = Number(e.target?.value || 0)
                      this.data.DATCOC_CSD = Number(cuoc_dc);
                      this.parent.templateCuocDCChange(this.data, cuoc_dc)
                    },
                    isNumber(evt) {
                      evt = evt ? evt : window.event;
                      var charCode = evt.which ? evt.which : evt.keyCode;
                      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                        evt.preventDefault();
                      } else {
                        return true;
                      }
                    },
                    numberWithCommas(n) {
                      var parts = n.toString().split(".");
                      return (
                        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
                        (parts[1] ? "." + parts[1] : "")
                      );
                    },
                  },
                },
              };
            },
          },          
          {
            fieldName: "TIEN_TD",
            headerText: "Tiền/Tháng",
            allowFiltering: true,
            width: '150',
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "HUONG_DC",
            headerText: "Số tháng",
            allowFiltering: true,
            width: '100'
          },
          {
            fieldName: "NGAY_BDDC",
            headerText: "Ngày BĐĐC",
            allowFiltering: true,
            width: '150',
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="text" class="form-control text" :value="data.NGAY_BDDC" :disabled="data.LOAI_BDDC == 3 ? false : true" @change="onChangeText">
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    onChangeText: function (e) {
                      try {
                        let ngay_bddc = e.target.value
                        if (!ngay_bddc || !moment(ngay_bddc,'DD/MM/YYYY', true).isValid()) {
                          this.$toast.error('Bạn phải nhập ngày có dạng DD/MM/YYYY!')
                          return false
                        }
                        this.parent.templateNgayBDDCChange(this.data, ngay_bddc)
                      } catch (err) {
                        console.log('onChangeText > NGAY_BDDC', err)
                      }
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: "NGAY_KTDC",
            headerText: "Ngày KTĐC",
            allowFiltering: true,
            width: '150',
          },
          {
            fieldName: "VAT_DC",
            headerText: "VAT ĐC",
            allowFiltering: true,
            width: '100',
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "TIEN_DC",
            headerText: "Tiền ĐC TB",
            allowFiltering: true,
            width: '150',
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "TYLE_VAT",
            headerText: "Tỷ lệ VAT",
            allowFiltering: true,
            width: '150'
          },
          {
            fieldName: "NGUOI_CN",
            headerText: "Người CN",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridDSHopDong: {
        list: [],
        groupSettings: {
          columns: ['NHOMTB_LABEL']
        },
        header: [
          {
            fieldName: "IS_CHECK",
            headerText: "",
            allowFiltering: false,            
            textAlign: 'Center',
            width: '60',
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="checkbox" class="check" style="width: 18px; height: 18px" v-model="data.IS_CHECK" @change='onCheck'>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },           
                  },
                  methods: {
                    onCheck: function (val) {                    
                      this.parent.onCheckNhomTB_HD(this.data)
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: "NHOMTB_LABEL",
            headerText: "Nhóm TB",
            allowFiltering: true,
            textAlign: 'Center',
            width: '100',
            isGroupedColumn: true
          },
          {
            fieldName: "NHOMTB_ID",
            headerText: "Nhóm TB",
            allowFiltering: true,
            textAlign: 'Center',
            width: '100',
          },
          {
            fieldName: "MA_TB",
            headerText: "Số máy/Acc",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_TB",
            headerText: "Tên thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "LOAIHINH_TB",
            headerText: "Loại hình",
            allowFiltering: true,
          },
          {
            fieldName: "TOCDO",
            headerText: "Tốc độ",
            allowFiltering: true,
          },
          {
            fieldName: "GOI_DADV",
            headerText: "Gói đa DV",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridDSCTTraTruoc_HD: {
        list: [],
        groupSettings: {
          columns: ['NHOM_DATCOC_LABEL']
        },
        header: [
          {
            fieldName: "IS_CHECK",
            headerText: "",
            allowFiltering: false,            
            textAlign: 'Center',
            width: '60',
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="checkbox" class="check" style="width: 18px; height: 18px" v-model="data.IS_CHECK" @change='onCheck'>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },           
                  },
                  methods: {
                    onCheck: function (val) {                    
                      this.parent.onCheckNhomDC_HD(this.data)
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: "NHOM_DATCOC_LABEL",
            headerText: "Nhóm ĐC",
            allowFiltering: true,
            isGroupedColumn: true
          }, 
          {
            fieldName: "MA_TB",
            headerText: "Số máy/Acc",
            allowFiltering: true,
          },          
          {
            fieldName: "TEN_CTKM",
            headerText: "Chi tiết TT",
            allowFiltering: true,
            width: '350'
          },          
          {
            fieldName: "DATCOC_CSD",
            headerText: "Cước ĐC",
            allowFiltering: true,            
            width: '150',
            type: "Number",
            format: "N0",
          },          
          {
            fieldName: "TIEN_TD",
            headerText: "Tiền/Tháng",
            allowFiltering: true,
            width: '150',
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "HUONG_DC",
            headerText: "Số tháng",
            allowFiltering: true,
            width: '100'
          },
          {
            fieldName: "NGAY_BDDC",
            headerText: "Ngày BĐĐC",
            allowFiltering: true,
            width: '150',
          },
          {
            fieldName: "NGAY_KTDC",
            headerText: "Ngày KTĐC",
            allowFiltering: true,
            width: '150',
          },
          {
            fieldName: "VAT_DC",
            headerText: "VAT ĐC",
            allowFiltering: true,
            width: '100',
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "TIEN_DC",
            headerText: "Tiền ĐC TB",
            allowFiltering: true,
            width: '150',
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "TYLE_VAT",
            headerText: "Tỷ lệ VAT",
            allowFiltering: true,
            width: '150'
          },
          {
            fieldName: "NGUOI_CN",
            headerText: "Người CN",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
    };
  },
  created() {
    this.$root.$on("bv::modal::shown", (bvEvent, modalId) => {
      document.getElementsByClassName("modal-content").forEach(function (v) {
        v.removeAttribute("tabindex");
      });
    });    
  },
  async mounted() {
    this.loading(true);
    try {
      this.formWidth = Number(window.innerWidth)
      if (this.$route.params && this.$route.params.tag !== null && this.$route.params.tag !== undefined) {
        this.tag = this.$route.params.tag
      }
      if (this.$route.query && this.$route.query.tag !== null && this.$route.query.tag !== undefined) {
        this.tag = this.$route.query.tag
      }
      if (this.dulieu && this.dulieu.tag !== null && this.dulieu.tag !== undefined) {
        this.tag = this.dulieu.tag
      }
      if (this.tag == '1') {
        this.loaihd_id = LoaiHopDong.DAT_MOI
        this.ma_gd = this.dulieu?.ma_gd || this.$route.query?.ma_gd || null
        this.txtMaGD.value = this.ma_gd
        this.txtMaGD.Enabled = false
        this.cboCTTraTruoc.Enabled = false
      }
            
      await Promise.all([this.frmDK_TraTruoc_Load()]);
      if (this.ma_gd) await this.txtMaGD_KeyPress()
    } catch (ex) {
      this.loading(false);
      console.log(ex);
    } finally {
      this.loading(false);
    }
  },  
  computed: {
    cp_dichvuvt_id: {
      get() {
        return Number(this.cboDichVuVT.value);
      },
      set(value) {
        this.cboDichVuVT.value = Number(value);
      },
    },    
    cp_CuocDC: {
      get() {
        var txtCuocDC = this.txtCuocDC.value
          ? this.numberWithCommas(this.txtCuocDC.value.toString())
          : "0";
        return txtCuocDC;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocDC.value = Number(replaced);
      },
    },
    cp_TienTD: {
      get() {
        var txtTienTD = this.txtTienTD.value
          ? this.numberWithCommas(this.txtTienTD.value.toString())
          : "0";
        return txtTienTD;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtTienTD.value = Number(replaced);
      },
    },
  },
  watch: {
    'formWidth'(val) {
      if (val) this.re_render += 1
    },
    cp_dichvuvt_id(newval, oldval) {
      // if (oldval && newval && newval != oldval) this.onChangeDVVT(newval);
    },
    'dtpNgayBDDC.value'(val) {
      if (this.dtpNgayBDDC.value && this.cboSoThangDC.value) {
        let ngay = moment(this.dtpNgayBDDC.value, 'DD/MM/YYYY').add(parseInt(this.cboSoThangDC.value), 'M')
        let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
        this.dtpNgayKTDC.value = moment(ngayktdc).format('DD/MM/YYYY')
      }
    },
    'cboSoThangDC.value'(val) {
      try {        
        if (this.dtpNgayBDDC.value && this.cboSoThangDC.value) {
          let ngay = moment(this.dtpNgayBDDC.value, 'DD/MM/YYYY').add(parseInt(this.cboSoThangDC.value), 'M')
          let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
          this.dtpNgayKTDC.value = moment(ngayktdc).format('DD/MM/YYYY')
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:277 ~ error:', error)
      }
    },
  },
  methods: {
    toLowerCases (ds) {
      if (ds === undefined) return []
      if (ds.length === 0) return []
      let dt = ds.map(item => {
        let newItem = {}
        Object.keys(item).forEach(key => newItem[key.toLowerCase()] = item[key])
        return newItem
      })
      return dt
    },
    toUpperCases (ds) {
      if (ds === undefined) return []
      if (ds.length === 0) return []
      let dt = ds.map(item => {
        let newItem = {}
        Object.keys(item).forEach(key => newItem[key.toUpperCase()] = item[key])
        return newItem
      })
      return dt
    },
    frmDK_TraTruoc_Load: async function () {
      this.tt_nd = {
        phanvung_id: Number(await this.$root.token.getPhanVungID()),
        donvi_id: Number(await this.$root.token.getDonViID()),
        nhanvien_id: Number(await this.$root.token.getNhanVienID()),
        ip_cn: await this.$root.token.getIP(),
        may_cn: await this.$root.token.getMachine(),
        nguoi_cn: await this.$root.token.getUserName(),
        ngay_cn: await this.$auth.getNgayCN()
      };
      console.log('frmDK_TraTruoc_Load > tt_nd',this.tt_nd)
      
      await Promise.all([
        this.loadKieuLD(),
        this.SetButton(-1),
        this.getDS_DICHVUVT(),
        this.getDANHMUC({loai: 'DS_NHOM_DATCOC', option: ''}).then((data) => {
          console.log('getDANHMUC > DS_NHOM_DATCOC', data)
          this.cboNhomDC.Items = data
          this.cboNhomDC.value = data[0]?.NHOM_DATCOC_ID
        })
      ]);
      
      await this.lay_tienkhoanmuc_loaihd()     
      this.cp_dichvuvt_id = this.cboDichVuVT.Items && this.cboDichVuVT.Items.length > 0 ? this.cboDichVuVT.Items[0].id : this.cp_dichvuvt_id;

      await Promise.all([
        this.getDANHMUC({loai: 'DS_LOAIHINH_TB', option: this.cp_dichvuvt_id}).then((data) => {
          console.log('getDANHMUC > DS_LOAIHINH_TB', data)
          this.cboLoaiHinhTB.Items = data.map(e => ({...e, id: e.LOAITB_ID, text: e.LOAIHINH_TB}))
        }),
        this.getDANHMUC({loai: 'DS_TOCDO_INTERNET', option: this.cp_dichvuvt_id}).then((data) => {
          console.log('getDANHMUC > DS_TOCDO_INTERNET', data)
          this.cboGoiCuocAdsl.Items = data.map(e => ({...e, id: e.TOCDO_ID, text: e.THUONGHIEU}))
        }),
        this.set_thamso_macdinh()
      ]);
    },
    set_thamso_macdinh: async function () {
      var ds = this.GetData(await api.LAY_DS_THAMSO_MD(this.axios, 0));
      if (ds && ds.length > 0) {
        var str_vtemp = "NHAP_CTV";
        var det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1") this.nhap_CTV = 1;
        }

        str_vtemp = "MACDINH_THOAIDC_LUITHANG";  // hoangpkn : 24/12/2018
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          this._MACDINH_THOAIDC_LUITHANG = 1;
        }

        str_vtemp = "CHECK_CHUANHOA_CNTT"; // hoangpkn : 18/03/2020. Check thue bao CNTT can chuan hoa
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.check_chuanhoa_cntt = true;
        }

        str_vtemp = "KIEMTRA_NO_TRATRUOC";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.kiemtra_no_tratruoc = 1;
        }

        str_vtemp = "KHONG_LOAD_CTV";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1") this.khong_load_ctv = 1;
        }

        str_vtemp = "PHAINHAP_THOIHAN";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.phainhap_thoihan = true;
        }

        str_vtemp = "KHONG_HIENTHI_TRACUU_DB_TB_DATCOC"; // hoangpkn : 13/09/2016 Thêm cho HCM chỉ được chọn 1 trong 2 loại Khuyến mại hoặc đặt cọc
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.khong_ht_tracuudb = 1;
        }

        str_vtemp = "MACDINH_CTV_NGUOIDUNG";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.MACDINH_CTV_NGUOIDUNG = 1;
        }

        if (this.MACDINH_CTV_NGUOIDUNG == 1) {
          this.filterbox.ctv.ctv_id = Number(this.tt_nd.nhanvien_id);
          var promiseCTV = this.getNhanvien_nvID(this.filterbox.ctv.ctv_id);
          this.filterbox.ctv.txtCTV = await promiseCTV;
          this.filterbox.ctv.chkCTV = true;
        }
      }
    },
    getNhanvien_nvID: async function (nhanvien_id) {
      let data = this.GetData(
        await api.getNhanvien_nvID(this.axios, nhanvien_id)
      );
      if (data && data.length > 0) {
        return data[0].NAME;
      } else {
        return "";
      }
    },
    // Kieu LD
    loadKieuLD: async function () {
      this.cboKieuLD.Items = []
      let response = await api.sp_load_KieuLD(this.axios);
      if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
      ) {
          this.cboKieuLD.Items = response.data.data;
      } else if (response.data.error_code == "BSS-00000204") {

      } else {
          this.ShowError("Lỗi lấy DS kiểu yêu cầu " + response.data.message_detail);
      }
    },        
    onChangeKieuLD: async function () {
      var ts_loaihd_id = this.GetData(
        await api.map_id_dk_tratruoc(this.axios, {
          p_type: 2, // loaihd theo kieuld
          p_param: Number(this.cboKieuLD.value),
        })
      );
      var loaihd_id = 0
      if (ts_loaihd_id && ts_loaihd_id.length > 0) this.loaihd_id = Number(ts_loaihd_id)
      await this.lay_tienkhoanmuc_loaihd()
    },
    lay_tienkhoanmuc_loaihd: async function () {
      var input = {
        loaihd_id: Number(this.loaihd_id),
      };
      let data = this.GetData(
        await api.sp_lay_tienkhoanmuc_loaihd(this.axios, input)
      );
      if (data && data.length > 0) {
        this.ds_tien_khoanmuc = this.toUpperCases(data);
      } else {
        this.ds_tien_khoanmuc = [];
        this.$toast.error("Không lấy được thông tin tiền khoản mục đặt cọc tham chiếu, hãy liên hệ admin để xử lý!");
      }
    },
    // Dichvu VT
    async getDS_DICHVUVT () {
      try {
        var data = {VNHANVIEN_ID: this.$root.token.getNhanVienID()}
        var res = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_ds_dichvuvt', data
        )
        if (res.data && res.data.length > 0) {
          let ds_dichvu = this.toUpperCases(res.data)
          console.log('getDS_DICHVUVT', ds_dichvu)
          this.cboDichVuVT.Items = ds_dichvu.map(e => ({...e, id: e.DICHVUVT_ID, text: e.TEN_DVVT}))
        } else { this.cboDichVuVT.Items = [] }
      } catch (error) {
        this.cboDichVuVT.Items = []
        console.log('getDS_DICHVUVT', error)
      }
    },
    onChangeDVVT: async function (value) {
      this.loading(true);
      try {
        await Promise.all([
          this.getDANHMUC({loai: 'DS_LOAIHINH_TB', option: this.cp_dichvuvt_id}).then((data) => {
            console.log('getDANHMUC > DS_LOAIHINH_TB', data)
            this.cboLoaiHinhTB.Items = data.map(e => ({...e, id: e.LOAITB_ID, text: e.LOAIHINH_TB}))
          }),
          this.getDANHMUC({loai: 'DS_TOCDO_INTERNET', option: this.cp_dichvuvt_id}).then((data) => {
            console.log('getDANHMUC > DS_TOCDO_INTERNET', data)
            this.cboGoiCuocAdsl.Items = data.map(e => ({...e, id: e.TOCDO_ID, text: e.THUONGHIEU}))
          }),
        ]);
        
        if (this.cboLoaiHinhTB.Items && this.cboLoaiHinhTB.Items.length > 0) {
          var filter = this.cboLoaiHinhTB.Items.filter(
            (e) => e.id == Number(this.cboLoaiHinhTB.value)
          );
          if (filter.length <= 0)
            this.cboLoaiHinhTB.value = this.cboLoaiHinhTB.Items[0].id;
        }
        if (this.cboGoiCuocAdsl.Items && this.cboGoiCuocAdsl.Items.length > 0) {
          var filter = this.cboGoiCuocAdsl.Items.filter(
            (e) => e.id == Number(this.cboGoiCuocAdsl.value)
          );
          if (filter.length <= 0) {
            this.cboGoiCuocAdsl.value = this.cboGoiCuocAdsl.Items[0].id;
            this.tocdo_id = this.cboGoiCuocAdsl.Items[0].id;
          }
              
        }
      } catch (e) {
        this.loading(false);
      }

      this.loading(false);
    },
    // Loaihinh TB
    onChangeLoaiHinhTB: async function () {},
    // Tocdo
    // Nhóm Đặt cọc
    async getDANHMUC (data) {
      try {
        var rs = null
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', data
        )
        return rs.data
      } catch (error) {
        console.log('getDANHMUC', error)
      }
      return null
    },
    onChangeNhomDC: async function () {
      try {
        let input = {
          kieu_tk: 1,
          hdtb_id: this.hdtb_id,
          loaitb_id: this.cboLoaiHinhTB.value,
          dichvuvt_id: this.cp_dichvuvt_id,
          nhom_dc_id: this.cboNhomDC.value,
          doituong_id: this.doituong_id,
          trangbi_id: this.trangbi_id,
          tocdo_id: this.tocdo_id,
          muccuoc_id: this.muccuoc_id,
          kieuld_id: this.cboKieuLD.value,
          khuyenmai_id: this.cboTraTruoc.value,
          huong_dc: this.cboSoThangDC.value,
          param_ds_tb : this.param_dstb  // Tú thêm
        }
        let data = await this.LAY_DS_TRATRUOC(input)
        this.cboTraTruoc.Items = data?.DS_COMBO_DC || []
      } catch (e) {} finally {}
    },
    // Tra truoc
    async LAY_DS_TRATRUOC (data) {
      try {
        var input = {
          "KIEU_TK": data.kieu_tk,
          "HDTB_ID": data.hdtb_id,
          "LOAITB_ID": data.loaitb_id,
          "DICHVUVT_ID": data.dichvuvt_id,
          "NHOM_DC_ID": data.nhom_dc_id,
          "DOITUONG_ID": data.doituong_id,
          "TRANGBI_ID": data.trangbi_id,
          "TOCDO_ID": data.tocdo_id,
          "MUCCUOC_ID": data.muccuoc_id,
          "KIEULD_ID": data.kieuld_id,
          "KHUYENMAI_ID": data.khuyenmai_id,
          "HUONG_DC": data.huong_dc,
          "DS_TB":data.param_ds_tb // Tú thêm
        }
        var res = await this.$root.context.post(
          '/web-hopdong/lapdatmoi/fn_lay_tt_danhmuc_frmdatcocds', {vds_timkiem: JSON.stringify(input) }
        )
        if (res.error_code != 'BSS-00000000' && res.error_code != 'BSS-00000204') {
          this.$toast.error(res.message_detail ? res.message_detail : res.message)
          return []
        }
        if (res.data) {
          return res.data
        } else { return [] }
      } catch (error) {
        console.log('LAY_DS_TRATRUOC', error)
        return []
      }
    },
    btnChon_TraTruoc_Click: function () {},
    onChangeTraTruoc: async function () {
      try {
        let input = {
          kieu_tk: 2,
          hdtb_id: this.hdtb_id,
          loaitb_id: this.cboLoaiHinhTB.value,
          dichvuvt_id: this.cp_dichvuvt_id,
          nhom_dc_id: this.cboNhomDC.value,
          doituong_id: this.doituong_id,
          trangbi_id: this.trangbi_id,
          tocdo_id: this.tocdo_id,
          muccuoc_id: this.muccuoc_id,
          kieuld_id: this.cboKieuLD.value,
          khuyenmai_id: this.cboTraTruoc.value,
          huong_dc: this.cboSoThangDC.value,
          param_ds_tb : this.param_dstb  // Tú thêm
        }
        let data = await this.LAY_DS_TRATRUOC(input)
        this.cboSoThangDC.Items = data && data.DS_HUONG_DC && data.DS_HUONG_DC.length > 0 ? data.DS_HUONG_DC.map(e => ({id: e.HUONG_DC, text: e.HUONG_DC})) : []
      } catch (e) {} finally {}
    },
    onChangeSoThang: async function () {
      try {
        if (this.cboCTTraTruoc.Enabled) {
          let input = {
            kieu_tk: 3,
            hdtb_id: this.hdtb_id,
            loaitb_id: this.cboLoaiHinhTB.value,
            dichvuvt_id: this.cp_dichvuvt_id,
            nhom_dc_id: this.cboNhomDC.value,
            doituong_id: this.doituong_id,
            trangbi_id: this.trangbi_id,
            tocdo_id: this.tocdo_id,
            muccuoc_id: this.muccuoc_id,
            kieuld_id: this.cboKieuLD.value,
            khuyenmai_id: this.cboTraTruoc.value,
            huong_dc: this.cboSoThangDC.value
          }
          let data = await this.LAY_DS_TRATRUOC(input)
          this.cboCTTraTruoc.Items = data?.DS_CT_TRATRUOC || []
        } else {
          this.cboCTTraTruoc.Items = []
          this.cboCTTraTruoc.value = 0          
        }
      } catch (e) {} finally {}
    }, 
    // CT Tra truoc
    btnChon_CTTraTruoc_Click: function () {},
    onChangeCTTraTruoc: function() {
      try {
        let ctkm = this.cboCTTraTruoc.Items.find(e => e.CHITIETKM_ID == this.cboCTTraTruoc.value)
        this.txtCuocDC.value = ctkm.DATCOC_CSD
        this.txtTienTD.value = ctkm.TIEN_TD

        if (Number(ctkm.THANG_BDDC) > 0) {
          this.dtpNgayBDDC.value = DateTimeLib.toDateObjectWithFormat(moment(ctkm.THANG_BDDC).startOf('month'), 'DD/MM/YYYY')
        } else {
          this.dtpNgayBDDC.value = moment(new Date(this.tt_nd.ngay_cn)).format('DD/MM/YYYY')
        }

        let ngay = moment(this.dtpNgayBDDC.value, 'DD/MM/YYYY').add(parseInt(this.cboSoThangDC.value), 'M')
        let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
        this.dtpNgayKTDC.value = moment(ngayktdc).format('DD/MM/YYYY')

        console.log(ngay,ngayktdc)
      } catch (e) {
        console.log('onChangeCTTraTruoc', e)
      } finally {}
    },
    btnChiTiet_Click: function () {},
    
    onChangeNgayBDDC(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayBDDC.value = "01/01/2000";
      } else {
        this.dtpNgayBDDC.value = value_;
      }
    },
    onChangeNgayKTDC(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayKTDC.value = "01/01/2000";
      } else {
        this.dtpNgayKTDC.value = value_;
      }
    },
    onChangeNgayYC(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayYeuCau.value  = "01/01/2000";
      } else {
        this.dtpNgayYeuCau.value  = value_;
      }
    }, 
    btnLayTT_click: async function () {
      this.Clear()
      await this.txtMaGD_KeyPress()
    },
    tsbtnNhapMoi_Click: function () {
      this.isMaTb_enter = false;
      this.SetButton(1);
    },
    tsbtnGhiLai_Click: async function () {
      if (this.button.tsbtnNhapMoi == false) {
        var kiemtra_Dulieu = await this.Kiemtra_Dulieu(true);
        if (!kiemtra_Dulieu) return;
        
      } else {
        var kiemtra_Dulieu = await this.Kiemtra_Dulieu(false);
        if (!kiemtra_Dulieu) return;
        
      }
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn ghi lại dữ liệu không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.CapNhat();
            this.isMaTb_enter = false;
            this.loading(false);
          }
        })
        .catch((e) => {
          this.ShowError("Lỗi ghi lại dữ liệu");
          this.loading(false);
        });
    },
    CapNhat: async function () {
      this.loading(true);
      try {
        let result = await this.TaoDuLieu()
        if (result) {
          let input = {
            KHUYENMAI_HDTB: this.KHUYENMAI_HDTB,
            DATCOC_HDTB: this.DATCOC_HDTB,
            CT_TIENHD: this.CT_TIENHD
          }
          console.log('TaoDuLieu', input, JSON.stringify(input))
          var res = await this.$root.context.post(
            '/web-hopdong/lapdatmoi/fn_them_tbdatcoc_frmdatcocds', {vds_input: JSON.stringify(input) }
          )
          if (res.error_code == 'BSS-00000000') {
            this.$toast.success('Ghi lại thành công!')
            let hd_check = this.gridDSThueBao.list.filter(e => e.IS_CHECK)[0]
            let nhomdc_check = {NHOM_DATCOC_ID: Number(this.cboNhomDC.value), IS_CHECK: true}
            await this.btnLayTT_click()

            await this.onCheckNhomTB_HD(hd_check)            
            await this.onCheckNhomDC_HD(nhomdc_check)
          } else {
            this.$toast.error('Lỗi ghi lại: ' + (res.message_detail ? res.message_detail : res.message))
          }
        }        
        this.loading(false);
      } catch (exp) {
        console.log(exp);
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    async Kiemtra_Dulieu(themmoi) {      
      // if (this.nhap_CTV == 1) {
      //   if (
      //     !this.filterbox.ctv.chkCTV ||
      //     this.filterbox.ctv.txtCTV.toString().trim() == ""
      //   ) {
      //     this.$toast.error("Hãy chọn cộng tác viên");
      //     return false;
      //   }
      // }
      if (!this.gridDSThueBao.list || this.gridDSThueBao.list.length == 0 || this.gridDSThueBao.list.filter(e => e.IS_CHECK).length == 0) {
        this.$toast.error('Bạn chưa chọn thuê bao đặt cọc!')
        return false
      }
      if (!this.cboNhomDC.value) {
        this.$toast.error('Bạn chưa chọn Nhóm đặt cọc!')
        return false
      }
      if (!this.cboTraTruoc.value) {
        this.$toast.error('Bạn chưa chọn Trả trước!')
        return false
      }
      if (!this.cboSoThangDC.value) {
        this.$toast.error('Bạn chưa chọn Số tháng đặt cọc!')
        return false
      }
      if (this.cboCTTraTruoc.Enabled && !this.cboCTTraTruoc.value) {
        this.$toast.error('Bạn chưa chọn Chi tiết trả trước!')
        return false
      }
      if (!this.gridDSCTTraTruoc.list || this.gridDSCTTraTruoc.list.length == 0) {
        this.$toast.error('Không có thông tin chi tiết trả trước của thuê bao đặt cọc!')
        return false
      }
      for (let thuebao of this.gridDSThueBao.list.filter(e => e.IS_CHECK)) {        
        if (thuebao.THUEBAO_ID) {
          var SP_CHECK_TT_HDTB = await this.SP_CHECK_TT_HDTB(thuebao.THUEBAO_ID)
          if (SP_CHECK_TT_HDTB.length > 0) {
            this.$toast.error('Thuê bao đang có hợp đồng/ phụ lục: ' + SP_CHECK_TT_HDTB[0].ten_loaihd + ', mã giao dịch: ' + SP_CHECK_TT_HDTB[0].ma_gd + ' chưa hoàn thiện!')
            return false
          }

          let Kiemtra_loaitb_id_db_thuebao = await this.Kiemtra_loaitb_id_db_thuebao(thuebao.THUEBAO_ID, thuebao.MA_TB, thuebao.DICHVUVT_ID, thuebao.LOAITB_ID)
          if (!Kiemtra_loaitb_id_db_thuebao) {
            this.$toast.error('Hãy kiểm tra lại loại hình/dịch vụ/trạng thái của thuê bao. Thuê bao phải ở trạng thái hoạt động bình thường.' + thuebao.MA_TB + '')
            return false
          }
          if (themmoi) {
            if (thuebao.DICHVUVT_ID === 13 || thuebao.DICHVUVT_ID === 14 || thuebao.DICHVUVT_ID === 15 || thuebao.DICHVUVT_ID === 16) {
              if (this.check_chuanhoa_cntt) {
                let check_chuahoa_cntt = await this.check_chuahoa_cntt(thuebao.THUEBAO_ID, 31)
                if (check_chuahoa_cntt) {
                  this.$root.$toast.warning('Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.')
                  return false
                }
              }
            }
            if (this.kiemtra_no_tratruoc === 1) {
              let tienno = await this.TraCuu_No_MaTB(thuebao.THUEBAO_ID)
              if (tienno > 0) {
                this.$root.$toast.warning('Thuê bao: ' + thuebao.MA_TB + ' còn nợ: ' + parseFloat(tienno) + ' vnđ.\nPhải thanh toán trước khi thực hiện!')
                return false
              }
            }
          }
        }        
        let ds_cttt = this.gridDSCTTraTruoc.list.filter(e => e.HDTB_ID == thuebao.HDTB_ID)
        if (ds_cttt.length > 1) {
          this.$toast.error(`${thuebao.MA_TB} có ${ds_cttt.length} chi tiết trả trước, bạn hãy kiểm tra lại!`)
          return false
        }
        let tt_tratruoc = this.gridDSCTTraTruoc.list.find(e => e.HDTB_ID == thuebao.HDTB_ID)
        if (!tt_tratruoc || !tt_tratruoc.DATCOC_CSD || Number(tt_tratruoc.DATCOC_CSD) <= 0) {
          this.$toast.error(`${tt_tratruoc.MA_TB}: Tiền đặt cọc phải lớn hơn 0!`)
          return false
        }
        if (!tt_tratruoc || !tt_tratruoc.TIEN_TD || Number(tt_tratruoc.TIEN_TD) <= 0) {
          this.$toast.error(`${tt_tratruoc.MA_TB}: Hãy kiểm tra lại số tiền trừ dần /tháng!`)
          return false
        }
      }
      return true;
    },
    async Kiemtra_loaitb_id_db_thuebao (thuebao_id, vma_tb, vdichvuvt_id, vloaitb_id) {
      try {
        var data = {
          VTHUEBAO_ID: thuebao_id,
          VMA_TB: vma_tb,
          VDICHVUVT_ID: vdichvuvt_id,
          VLOAITB_ID: vloaitb_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_loaitb_id_db_thuebao', data)
        if (rps.data.length > 0) {
          if (parseInt(rps.data[0].sl) > 0) return true
          else return false
        } else return false
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2157 ~ Kiemtra_loaitb_id_db_thuebao ~ error:', error)
        return false
      }
    },
    async check_chuahoa_cntt (thuebao_id, loaihd_id) {
      try {
        var data = {
          thuebao_id,
          loaihd_id
        }
        var rps = await this.$root.context.post('/web-hopdong/chuyendich/check_chuahoa_cntt', data)
        return rps.data.toString() === '1'
      } catch (error) {
        return false
      }
    },
    async TraCuu_No_MaTB (thuebao_id) {
      var result = 0
      try {
        if (thuebao_id !== 0) {
          var no_tb = await this.TRACUUNO_THUEBAO(thuebao_id).toString()
          result = parseFloat(no_tb)
        } else {
          this.$root.$toast.warning('Chưa có thông tin về thuê bao!')
        }
      } catch (error) {

      }
      return result
    },
    async TRACUUNO_THUEBAO (thuebao_id) {
      try {
        var data = {
          vthuebao_id: thuebao_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/tracuuno_thuebao', data)
        if (rps.data.length > 0) {
          return rps.data[0].no_thuebao
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async SP_CHECK_TT_HDTB (thuebao_id) {
      try {
        var data = {
          VTHUEBAO_ID: thuebao_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_check_tt_hdtb', data)
        return rps.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2169 ~ SP_CHECK_TT_HDTB ~ error:', error)
        return []
      }
    },
    TaoDuLieu: async function () {
      this.KHUYENMAI_HDTB = []
      this.DATCOC_HDTB = []
      this.CT_TIENHD = []
      let result = this.TAO_KHUYENMAI_HDTB()
      if (!result) {
        this.$toast.error('Lỗi TAO_KHUYENMAI_HDTB!')
        return false
      }

      result = this.TAO_DATCOC_HDTB()
      if (!result) {
        this.$toast.error('Lỗi TAO_DATCOC_HDTB!')
        return false
      }

      result = await this.TAO_CT_TIENHD()
      if (!result) {
        this.$toast.error('Lỗi TAO_CT_TIENHD!')
        return false
      }
      return true
    },
    TAO_KHUYENMAI_HDTB: function () {
      try {
        let ds_tbkm = this.gridDSThueBao.list.filter(e => e.IS_CHECK && !e.THUEBAO_ID)
        if (ds_tbkm && ds_tbkm.length > 0) {
          for (let item of ds_tbkm) {
            let row = this.gridDSCTTraTruoc.list.find(e => e.HDTB_ID == item.HDTB_ID)
            row.NHOM_TB_ID = item?.NHOMTB_ID || null
            row.HDTB_ID = row?.HDTB_ID || null
            row.MA_TB = item?.MA_TB || null
            row.THUEBAO_ID = item?.THUEBAO_ID || null
            row.NHOM_DATCOC_ID = row?.NHOM_DATCOC_ID || this.cboNhomDC.value
            row.KHUYENMAI_ID = row?.KHUYENMAI_ID || null
            row.CHITIETKM_ID = row?.CHITIETKM_ID || null
            row.KHUYENMAI_ID_NEO = row?.KHUYENMAI_ID_NEO || null
            row.KIEU_YC = 1
            row.THANG_BD = row?.THANG_BD || null
            row.THANG_KT = row?.THANG_KT || null
            row.THANG_HUONGKM = row?.HUONG_KM || null
            row.THANG_BDDC = row?.THANG_BDDC || null
            row.THANG_KTDC = row?.THANG_KTDC || null
            row.THANG_HUONGDC = row?.HUONG_DC || null
            row.THANG_BDTG = row?.THANG_BDTG || null
            row.THANG_KTTG = row?.THANG_KTTG || null
            row.SOTHANG_TG = row?.SOTHANG_TG || null
            row.TIEN_TG = row?.TIEN_TG || 0
            row.TYLE_KMLD = row?.TYLE_KMLD || 0
            row.TYLE_SD = row?.TYLE_SD || 0
            row.TYLE_TB = row?.TYLE_TB || 0
            row.DATCOC_CSD = row?.DATCOC_CSD || 0
            row.TIEN_TD = row?.TIEN_TD || 0
            row.RKM_ID = row?.RKM_ID || null
            row.TIEN_SD = row?.TIEN_SD || 0
            row.TIEN_TB = row?.TIEN_TB || 0
            row.CUOC_SD = row?.CUOC_SD || 0
            row.CUOC_TB = row?.CUOC_TB || 0
            row.NGAY_HUY = row?.NGAY_HUY || null
            row.NGAY_BD = row?.NGAY_BD || null
            row.NGAY_KT = row?.NGAY_KT || null
            row.NGAY_BDDC = row?.NGAY_BDDC || null
            row.NGAY_KTDC = row?.NGAY_KTDC || null
            this.KHUYENMAI_HDTB.push(row)
          }
        }
        return true
      } catch (e) { return false }
    },
    TAO_DATCOC_HDTB: function () {
      try {
        let ds_tbdc = this.gridDSThueBao.list.filter(e => e.IS_CHECK && e.THUEBAO_ID)
        if (ds_tbdc && ds_tbdc.length > 0) {
          for (let item of ds_tbdc) {
            let row = this.gridDSCTTraTruoc.list.find(e => e.HDTB_ID == item.HDTB_ID)
            row.NHOM_TB_ID = item?.NHOMTB_ID || null
            row.HDTB_ID = row?.HDTB_ID || null
            row.MA_TB = item?.MA_TB || null
            row.THUEBAO_ID = row?.THUEBAO_ID || item?.THUEBAO_ID || null
            row.THUEBAO_DC_ID = row?.THUEBAO_DC_ID || item?.THUEBAO_ID || null
            row.NHOM_DATCOC_ID = row?.NHOM_DATCOC_ID || this.cboNhomDC.value
            row.NGAY_DK = row?.NGAY_DK || null
            row.THANG_BD = row?.THANG_BD || null
            row.THANG_KT = row?.THANG_KT || null
            row.THANG_BD_MG = row?.THANG_BD_MG || null
            row.THANG_KT_MG = row?.THANG_KT_MG || null
            row.GHICHU = row?.GHICHU || null
            row.CHITIETKM_ID = row?.CHITIETKM_ID || null
            row.THANG_THOAITRA = row?.THANG_THOAITRA || null
            row.THUEBAO_THOAITRA_ID = row?.THUEBAO_THOAITRA_ID || null
            row.CUOC_DC = row?.CUOC_DC || row?.DATCOC_CSD || 0
            row.TIEN_TD = row?.TIEN_TD || 0
            row.RKM_ID = row?.RKM_ID || null
            row.FKEY = row?.FKEY || null
            row.TIEN_SD = row?.TIEN_SD || 0
            row.CUOC_SD = row?.CUOC_SD || 0
            row.TIEN_TB = row?.TIEN_TB || 0
            row.CUOC_TB = row?.CUOC_TB || 0
            row.TYLE_SD = row?.TYLE_SD || 0
            row.TYLE_TB = row?.TYLE_TB || 0
            row.THANG_BDTG = row?.THANG_BDTG || null
            row.THANG_KTTG = row?.THANG_KTTG || null
            row.TIEN_TG = row?.TIEN_TG || null
            row.NGAY_THOAI = row?.NGAY_THOAI || null
            row.NGAY_BD = row?.NGAY_BD || null
            row.NGAY_KT = row?.NGAY_KT || null
            row.THANG_BDDC = row?.THANG_BDDC || null
            row.THANG_KTDC = row?.THANG_KTDC || null
            row.NGAY_BDDC = row?.NGAY_BDDC || null
            row.NGAY_KTDC = row?.NGAY_KTDC || null
            this.DATCOC_HDTB.push(row)
          }
        }
        return true
      } catch (e) { return false }
    },
    TAO_CT_TIENHD: async function () {
      try {
        if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
          let kmtt_datcoc = this.ds_tien_khoanmuc.filter(e => Number(e.KHOANMUCTT_ID) == KHOANMUC_TT.KMTT_DATCOC)
          if (kmtt_datcoc.length > 0) {
            for (let item of this.gridDSCTTraTruoc.list) {
              let themmoi = this.gridDSThueBao.list.find(e => e.HDTB_ID == item.HDTB_ID).THUEBAO_ID ? false : true
              let rowCTTHD = {}
              rowCTTHD.CT_TIENHD_ID = await this.GET_KEYS("CT_TIENHD");
              rowCTTHD.HDTB_ID = item?.HDTB_ID || null
              rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DATCOC
              rowCTTHD.PHIEUTT_ID = item?.PHIEUTT_ID || null
              rowCTTHD.ID = item?.CHITIETKM_ID || null
              rowCTTHD.LOAI_ID = themmoi ? 4 : 1;
              rowCTTHD.TYLE_VAT = item?.TYLE_VAT || 0
              rowCTTHD.TYLE_VAT_ID = item?.TYLE_VAT_ID || 0
              rowCTTHD.TIEN = Math.round(item.DATCOC_CSD * 100 / (100 + Number(item.TYLE_VAT)))
              rowCTTHD.VAT = Math.round(item.DATCOC_CSD * Number(item.TYLE_VAT) / (100 + Number(item.TYLE_VAT)))
              this.CT_TIENHD.push(rowCTTHD)
            }
          }
        }
        return true
      } catch (e) { 
        console.log('TAO_CT_TIENHD', e)
        return false 
      }
    },
    GET_KEYS: async function(key) {
      var input = {
        keyname: key
      }
      let response = await api.getKey(this.axios, input)
      if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
        return Number(response.data.data)
      } else {
        this.$toast.error('Có lỗi trong quá trình tạo key mới')
        return
      }
    },
    tsbtnXoa_Click: async function () {
      this.loading(true);
      try {
        //Xoa_datcoc();
        if (!this.gridDSHopDong.list || this.gridDSHopDong.list.length == 0 || this.gridDSHopDong.list.filter(e => e.IS_CHECK).length == 0) {
          this.$toast.error('Bạn chưa chọn hợp đồng để xóa!')
          return false
        }
        if (Number(this.tthd_id) > 1) {
          var promise_tthd = this.trangthai_hd();
          var tthd = await promise_tthd;
          this.ShowError("Hợp đồng " + tthd + " bạn không thể xóa!");
          return false;
        }
        if (!this.gridDSCTTraTruoc_HD.list || this.gridDSCTTraTruoc_HD.list.length == 0 || this.gridDSCTTraTruoc_HD.list.filter(e => e.IS_CHECK).length == 0) {
          this.$toast.error('Bạn chưa chọn Chi tiết trả trước để xóa!')
          return false
        }
        this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xóa dữ liệu không?", {
            title: "Thông báo",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then(async (v) => {
            if (v) {
              this.loading(true);
              let input = this.gridDSHopDong.list.filter(e => e.IS_CHECK).map(e => ({
                HDTB_ID: e?.HDTB_ID || null,
                THUEBAO_ID: e?.THUEBAO_ID || null,
                MA_TB: e?.MA_TB || null,
                CHITIETKM_ID: this.gridDSCTTraTruoc_HD.list.find(ct => ct.HDTB_ID == e.HDTB_ID && ct.IS_CHECK)?.CHITIETKM_ID || null,
                NHOM_DATCOC_ID: this.gridDSCTTraTruoc_HD.list.find(ct => ct.HDTB_ID == e.HDTB_ID && ct.IS_CHECK)?.NHOM_DATCOC_ID || null,
                NHOM_TB_ID: e?.NHOMTB_ID || null,
              }))
              console.log('XOA_HD_DATCOC', input)
              let chk = await this.XOA_HD_DATCOC(input)
              if (chk) {
                this.$root.$toast.success('Xóa hợp đồng đặt cọc trả trước thành công!')
                await this.txtMaGD_KeyPress()
                this.SetButton(0)
              }
              this.loading(false);
            }
          })
          .catch((e) => {
            console.log(e);
            this.ShowError("Lỗi xóa dữ liệu");
            this.loading(false);
          });
      } catch (e) {
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    async XOA_HD_DATCOC (data) {
      try {
        let input = {vds_input: JSON.stringify({DS_TB: data})}
        let res = await this.$root.context.post('/web-hopdong/lapdatmoi/fn_xoa_tbdatcoc_frmdatcocds', input)
        console.log('🚀 ~ file: index.vue:605 ~ XOA_HD_DATCOC ~ rs.data:', res.data)
        if (res.error_code == 'BSS-00000000') {
          return true
        } else {
          this.$toast.error('Lỗi xóa hợp đồng: ' + (res.message_detail ? res.message_detail : res.message))
          return false
        }        
      } catch (error) {
        console.log('🚀 ~ file: index.vue:607 ~ XOA_HD_DATCOC ~ error:', error)
        this.$toast.error('Lỗi xóa hợp đồng: ' + JSON.stringify(error))
        return false
      }
    },
    tsbtnHuyBo_Click: async function () {
      this.isMaTb_enter = false;
      this.SetButton(0);
    },
    tsbtnThemTB_Click: async function () {
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn thêm thuê bao không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(false);
          }
        })
        .catch((e) => {
          console.log(e);
          this.ShowError("Lỗi thêm thuê bao");
          this.loading(false);
        });
    },
    tsbtnXoaTB_Click: async function () {
      if (this.tthd_id > 1) {
        var promise_tthd = this.trangthai_hd();
        var tthd = await promise_tthd;
        this.ShowError("Hợp đồng " + tthd + " bạn không thể xóa!");
        return;
      }
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn xóa dữ liệu không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);

            this.loading(false);
          }
        })
        .catch((e) => {
          console.log(e);
          this.ShowError("Lỗi xóa dữ liệu");
          this.loading(false);
        });
    },    
    trangthai_hd: async function () {
      let data = this.GetData(await api.getds_trangthaihd(this.axios));
      if (data && data.length > 0) {
        var hd = data.filter((e) => e.ID == this.tthd_id);
        if (hd.length > 0) {
          return hd[0].NAME;
        } else {
          return "";
        }
      }
    },
    tsbtnChapNhanTT_Click: async function () {
      try {
        this.loading(true)
        if (!this.cboNhomDC.value) {
          this.$toast.error('Bạn chưa chọn Nhóm đặt cọc!')
          return
        }
        if (!this.cboTraTruoc.value) {
          this.$toast.error('Bạn chưa chọn Trả trước!')
          return
        }
        if (!this.cboSoThangDC.value) {
          this.$toast.error('Bạn chưa chọn Số tháng đặt cọc!')
          return
        }
        let data_datcoc = await this.LAY_TT_TBDATCOC_FRMDATCOCDS()
        if (data_datcoc && data_datcoc.DS_TB_TRATRUOC && data_datcoc.DS_TB_TRATRUOC.length > 0) {        
          this.txtCuocDC.value = 0
          this.txtTienTD.value = 0  
          let ds_cttratruoc = data_datcoc.DS_TB_TRATRUOC
          for (let ctkm of ds_cttratruoc) {
            ctkm.MA_TB = `${ctkm.HDTB_ID} . ${this.gridDSThueBao.list.find(e => e.HDTB_ID == ctkm.HDTB_ID)?.MA_TB}` || null
            if (Number(ctkm.THANG_BDDC) > 0) {
              ctkm.NGAY_BDDC = DateTimeLib.toDateObjectWithFormat(moment(ctkm.THANG_BDDC).startOf('month'), 'DD/MM/YYYY')
            } else {
              ctkm.NGAY_BDDC = moment(new Date(this.tt_nd.ngay_cn)).format('DD/MM/YYYY')
              ctkm.THANG_BDDC = Number(moment(new Date(this.tt_nd.ngay_cn)).format('YYYYMM'))
            }

            let ngay = moment(ctkm.NGAY_BDDC, 'DD/MM/YYYY').add(parseInt(ctkm.HUONG_DC), 'M')
            let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
            ctkm.NGAY_KTDC = moment(ngayktdc).format('DD/MM/YYYY')
            ctkm.THANG_KTDC = Number(moment(ngayktdc).format('YYYYMM'))

            ctkm.VAT_DC = Math.round(ctkm.DATCOC_CSD * Number(ctkm.TYLE_VAT) / (100 + Number(ctkm.TYLE_VAT)))
            ctkm.TIEN_DC = Math.round(ctkm.DATCOC_CSD * 100 / (100 + Number(ctkm.TYLE_VAT)))

            this.txtCuocDC.value += ctkm.DATCOC_CSD
            this.txtTienTD.value += ctkm.TIEN_TD
          }
          this.gridDSCTTraTruoc.list = ds_cttratruoc
        } else {
          this.txtCuocDC.value = 0
          this.txtTienTD.value = 0  
          this.gridDSCTTraTruoc.list = []
        }
        this.button.tsbtnGhiLai = this.gridDSCTTraTruoc.list && this.gridDSCTTraTruoc.list.length > 0 ? true : false // disabled - false : true
      } catch (e) {
        console.log('tsbtnChapNhanTT_Click', e)
      } finally {
        this.loading(false)
      }
    },
    tsbtnHuyBoTT_Click: function () {
      this.txtCuocDC.value = 0
      this.txtTienTD.value = 0  
      this.gridDSCTTraTruoc.list = []
    },
    // MaGD
    txtMaGD_KeyPress: async function () {      
      try {
       // this.ma_gd = this.txtMaGD.value
        this.loading(true)
        this.keyenter = 0;
        let input = {
          kieu_tk: 1,
          ds_tk: [],
          ma_gd: this.ma_gd,
          loaihd_id: this.loaihd_id,
          chuyenmang: this.chuyenmang,
          thuebao_id: this.thuebao_id,
          goi_id: this.goi_id,
          nhomdc_id: this.cboNhomDC.value
        }
        await this.LAY_DS_TB_FRMDATCOCDS(input)

        let input1 = {
          kieu_tk: 2,
          ds_tk: [],
          ma_gd: this.ma_gd,
          loaihd_id: this.loaihd_id,
          chuyenmang: this.chuyenmang,
          thuebao_id: this.thuebao_id,
          goi_id: this.goi_id,
          nhomdc_id: this.cboNhomDC.value
        }
        await this.LAY_DS_TB_FRMDATCOCDS(input1)
        
        let first_row = {}
        if (this.gridDSHopDong.list && this.gridDSHopDong.list.length > 0) {
          first_row = this.gridDSHopDong.list.filter(e => e.LOAITB_ID == 58)
          if (first_row.length > 0) {
            first_row = first_row[0] 
          } else {
            first_row = this.gridDSHopDong.list[0]
          } 
        } else if (this.gridDSThueBao.list && this.gridDSThueBao.list.length > 0) {
          first_row = this.gridDSThueBao.list.filter(e => e.LOAITB_ID == 58)
          if (first_row.length > 0) {
            first_row = first_row[0] 
          } else {
            first_row = this.gridDSThueBao.list[0]
          } 
        }
        this.nhomtb_id = first_row?.NHOMTB_ID || null
        this.hdtb_id = first_row?.HDTB_ID || null
        this.txtMaTB.value = first_row?.MA_TB || null
        this.thuebao_id = first_row?.THUEBAO_ID || null
        this.goi_id = first_row?.GOI_ID || null
        this.chuyenmang = first_row?.CHUYENMANG || null
        this.tocdo_id = first_row?.TOCDO_ID || null
        this.cboGoiCuocAdsl.value = first_row?.TOCDO_ID || null
        this.cboGoiCuocAdsl.text = first_row?.TOCDO || null
        this.cp_dichvuvt_id = first_row?.DICHVUVT_ID || null
        this.cboDichVuVT.text = this.cboDichVuVT?.Items.find(e => e.DICHVUVT_ID == this.cp_dichvuvt_id)?.text || null
        this.cboLoaiHinhTB.value = first_row?.LOAITB_ID || null
        this.cboLoaiHinhTB.text = first_row?.LOAIHINH_TB || null
        this.loaihd_id = first_row?.LOAIHD_ID || null
        this.cboKieuLD.value = first_row?.KIEULD_ID || null
        this.txtTenTB.value = first_row?.TEN_TB || null
        this.txtTenKH.value = first_row?.TEN_KH || null
        this.txtDiaChiKH.value = first_row?.DIACHI_KH || null
        this.txtDiaChiLD.value = first_row?.DIACHI_LD || null
        this.txtGoiDaDV.value = first_row && first_row['GOI_ID'] ? `[${first_row['GOI_ID']}] - ${first_row['GOI_DADV']}]` : null
        this.doituong_id = first_row?.DOITUONG_ID || null
        this.muccuoc_id = first_row?.MUCCUOC_ID || null
        this.trangbi_id = first_row?.TRANGBI_ID || null
        this.cboNhomDC.value = first_row?.NHOMDC_ID || null
        this.cboTraTruoc.value = first_row?.KHUYENMAI_ID || null
        this.cboSoThangDC.value = first_row?.HUONG_DC || null
      } catch (e) {} finally { this.loading(false) }
    },
    btnLayMaGD_Click: function () {
      this.keyenter = 0;
      this.tieuchi_id =
        "kh.ma_kh in (select ma_kh from css.hd_khachhang where ma_gd =  ':values' and phanvung_id = kh.phanvung_id)";
      this.thongtin_tracuu = this.txtMaGD.value;
      this.$refs.popupSearchContract.showModal();
    },    
    acceptTimKiemHDTB: async function (item) {
      if (item) {
        this.ma_gd = item.ma_gd
        this.txtMaGD.value = item.ma_gd;
        this.$refs.txtMaGD.focus();
        if (this.txtMaGD.value && this.txtMaGD.value != "") {
          // this.gridDSHopDong.list = this.LAY_DS_HOPDONG_THEO_MA_GD(this.ma_gd);
        }
      }
    },
    onCheckNhomTB_HD: async function (dataItem) {
      try {
        this.loading(true)
        this.cboCTTraTruoc.Enabled = false
        if (!dataItem.NHOMTB_ID || !this.chkTB_CungNhom) {
          this.cboCTTraTruoc.Enabled = true
        } else {
          let ds = this.gridDSHopDong.list.filter(e => e.NHOMTB_ID == dataItem.NHOMTB_ID)
          if (ds.length == 1) {
            this.cboCTTraTruoc.Enabled = true
          }
        }
        if (this.chkTB_CungNhom && dataItem.NHOMTB_ID) {
          for (let item of this.gridDSHopDong.list) {
            if (item.NHOMTB_ID == dataItem.NHOMTB_ID) {
              item.IS_CHECK = dataItem.IS_CHECK
              item.NUM_CHECK = dataItem.IS_CHECK ? 0 : 1
              item.NHOMTB_LABEL = item.NUM_CHECK + '.' + (item?.NHOMTB_ID || 0)
            } else {
              item.IS_CHECK = false 
              item.NUM_CHECK = 1
              item.NHOMTB_LABEL = item.NUM_CHECK + '.' + (item?.NHOMTB_ID || 0)
            }
          }        
        } else {
          for (let item of this.gridDSHopDong.list) {
            if (item.HDTB_ID == dataItem.HDTB_ID) {
              item.IS_CHECK = dataItem.IS_CHECK
              item.NUM_CHECK = dataItem.IS_CHECK ? 0 : 1
              item.NHOMTB_LABEL = item.NUM_CHECK + '.' + (item?.NHOMTB_ID || 0)
            } else {
              item.IS_CHECK = false
              item.NUM_CHECK = 1
              item.NHOMTB_LABEL = item.NUM_CHECK + '.' + (item?.NHOMTB_ID || 0)
            }
          }        
        }
        let ds = this.gridDSHopDong.list.sort((a, b) => Number(a.NHOMTB_LABEL) - Number(b.NHOMTB_LABEL))
        this.gridDSHopDong.list = ds
        this.key_gridHD ++
        if (dataItem.IS_CHECK) {
          let first_row = dataItem.NHOMTB_ID ? this.gridDSHopDong.list.filter(e => e.IS_CHECK)[0] : dataItem
          this.nhomtb_id = first_row?.NHOMTB_ID || null
          this.hdtb_id = first_row?.HDTB_ID || null
          this.txtMaTB.value = first_row?.MA_TB || null
          this.thuebao_id = first_row?.THUEBAO_ID || null
          this.goi_id = first_row?.GOI_ID || null
          this.chuyenmang = first_row?.CHUYENMANG || null
          this.tocdo_id = first_row?.TOCDO_ID || null
          this.cboGoiCuocAdsl.value = first_row?.TOCDO_ID || null
          this.cboGoiCuocAdsl.text = first_row?.TOCDO || null
          this.cp_dichvuvt_id = first_row?.DICHVUVT_ID || null
          this.cboDichVuVT.text = this.cboDichVuVT?.Items.find(e => e.DICHVUVT_ID == this.cp_dichvuvt_id)?.text || null
          this.cboLoaiHinhTB.value = first_row?.LOAITB_ID || null
          this.cboLoaiHinhTB.text = first_row?.LOAIHINH_TB || null
          this.loaihd_id = first_row?.LOAIHD_ID || null
          this.cboKieuLD.value = first_row?.KIEULD_ID || null
          this.txtTenTB.value = first_row?.TEN_TB || null
          this.txtTenKH.value = first_row?.TEN_KH || null
          this.txtDiaChiKH.value = first_row?.DIACHI_KH || null
          this.txtDiaChiLD.value = first_row?.DIACHI_LD || null
          this.txtGoiDaDV.value = first_row && first_row['GOI_ID'] ? `[${first_row['GOI_ID']}] - ${first_row['GOI_DADV']}]` : null
          this.doituong_id = first_row?.DOITUONG_ID || null
          this.muccuoc_id = first_row?.MUCCUOC_ID || null
          this.trangbi_id = first_row?.TRANGBI_ID || null
          this.cboNhomDC.value = first_row?.NHOMDC_ID || null
          this.cboTraTruoc.value = first_row?.KHUYENMAI_ID || null
          this.cboSoThangDC.value = first_row?.HUONG_DC || null
          
          let ds_tk = this.gridDSHopDong.list.filter(e => e.IS_CHECK).map(e => ({HDTB_ID: e?.HDTB_ID || null, THUEBAO_ID: e?.THUEBAO_ID || null}))
          let input = {
            kieu_tk: 3,
            ds_tk: ds_tk,
            ma_gd: this.ma_gd,
            loaihd_id: this.loaihd_id,
            chuyenmang: this.chuyenmang,
            thuebao_id: this.thuebao_id,
            goi_id: this.goi_id,
            nhomdc_id: this.cboNhomDC.value
          }
          await this.LAY_DS_TB_FRMDATCOCDS(input)
          if (!this.gridDSCTTraTruoc_HD.list || this.gridDSCTTraTruoc_HD.list.length == 0) {
            this.$toast.error('Không lấy được thông tin đặt cọc trả trước của nhóm thuê bao vừa chọn, bạn hãy liên hệ admin để xử lý!')
            return false
          }
        } else {
          let first_row = null
          this.nhomtb_id = first_row?.NHOMTB_ID || null
          this.hdtb_id = first_row?.HDTB_ID || null
          this.txtMaTB.value = first_row?.MA_TB || null
          this.thuebao_id = first_row?.THUEBAO_ID || null
          this.goi_id = first_row?.GOI_ID || null
          this.chuyenmang = first_row?.CHUYENMANG || null
          this.tocdo_id = first_row?.TOCDO_ID || null
          this.cboGoiCuocAdsl.value = first_row?.TOCDO_ID || null
          this.cboGoiCuocAdsl.text = first_row?.TOCDO || null
          this.cp_dichvuvt_id = first_row?.DICHVUVT_ID || null
          this.cboDichVuVT.text = first_row?.DICHVUVT_ID || null
          this.cboLoaiHinhTB.value = first_row?.LOAITB_ID || null
          this.cboLoaiHinhTB.text = first_row?.LOAIHINH_TB || null
          this.loaihd_id = first_row?.LOAIHD_ID || null
          this.cboKieuLD.value = first_row?.KIEULD_ID || null
          this.txtTenTB.value = first_row?.TEN_TB || null
          this.txtTenKH.value = first_row?.TEN_KH || null
          this.txtDiaChiKH.value = first_row?.DIACHI_KH || null        
          this.txtDiaChiLD.value = first_row?.DIACHI_LD || null
          this.txtGoiDaDV.value = first_row && first_row['GOI_ID'] ? `[${first_row['GOI_ID']}] - ${first_row['GOI_DADV']}]` : null
          this.doituong_id = first_row?.DOITUONG_ID || null
          this.muccuoc_id = first_row?.MUCCUOC_ID || null
          this.trangbi_id = first_row?.TRANGBI_ID || null
          this.cboNhomDC.value = first_row?.NHOMDC_ID || null
          this.cboTraTruoc.value = first_row?.KHUYENMAI_ID || null
          this.cboSoThangDC.value = first_row?.HUONG_DC || null
          this.txtCuocDC.value = 0
          this.txtTienTD.value = 0  
          this.gridDSCTTraTruoc_HD.list = []
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + JSON.stringify(e))
      } finally { this.loading(false) }
    },
    onCheckNhomDC_HD: async function (dataItem) {
      try {
        this.loading(true)
        for (let item of this.gridDSCTTraTruoc_HD.list) {
          if (item.NHOM_DATCOC_ID == dataItem.NHOM_DATCOC_ID) {
            item.IS_CHECK = dataItem.IS_CHECK
            item.NUM_CHECK = dataItem.IS_CHECK ? 0 : 1
            item.NHOMDC_LABEL = item.NUM_CHECK + '.' + (item?.NHOM_DATCOC_ID || 0)
          } else {
            item.IS_CHECK = false 
            item.NUM_CHECK = 1
            item.NHOMDC_LABEL = item.NUM_CHECK + '.' + (item?.NHOM_DATCOC_ID || 0)
          }
        }
        let ds = this.gridDSCTTraTruoc_HD.list.sort((a, b) => Number(a.NHOMDC_LABEL) - Number(b.NHOMDC_LABEL))
        this.gridDSCTTraTruoc_HD.list = ds
        this.key_gridHD ++
        if (dataItem.IS_CHECK) {
          let row_ct = dataItem.NHOM_DATCOC_ID ? this.gridDSCTTraTruoc_HD.list.filter(e => e.IS_CHECK)[0] : dataItem
          this.cboNhomDC.value = row_ct?.NHOM_DATCOC_ID || null
          this.cboTraTruoc.value = row_ct?.KHUYENMAI_ID || null
          this.cboSoThangDC.value = row_ct?.HUONG_DC || null          
          this.txtCuocDC.value = row_ct?.TONG_TIENDC || null
          this.txtTienTD.value = row_ct?.TONG_TIENTD || null
          this.dtpNgayBDDC.value = moment(new Date(row_ct.NGAY_BDDC)).format('DD/MM/YYYY')
          this.dtpNgayKTDC.value = moment(new Date(row_ct.NGAY_KTDC)).format('DD/MM/YYYY')
          await Promise.all([this.onChangeNhomDC(), this.onChangeTraTruoc()]);
          if (this.cboCTTraTruoc.Enabled) {
            this.cboCTTraTruoc.value = row_ct?.CHITIETKM_ID || null
            await Promise.all([this.onChangeSoThang()]);
          }
        } else {
          let row_ct = null
          this.cboNhomDC.value = row_ct?.NHOM_DATCOC_ID || null
          this.cboTraTruoc.value = row_ct?.KHUYENMAI_ID || null
          this.cboSoThangDC.value = row_ct?.HUONG_DC || null          
          this.txtCuocDC.value = row_ct?.TONG_TIENDC || null
          this.txtTienTD.value = row_ct?.TONG_TIENTD || null
          this.dtpNgayBDDC.value = moment(new Date(row_ct.NGAY_BDDC)).format('DD/MM/YYYY')
          this.dtpNgayKTDC.value = moment(new Date(row_ct.NGAY_KTDC)).format('DD/MM/YYYY')
          await Promise.all([this.onChangeNhomDC(), this.onChangeTraTruoc()]);
          if (this.cboCTTraTruoc.Enabled) {
            this.cboCTTraTruoc.value = row_ct?.CHITIETKM_ID || null
            if (this.cboSoThangDC.value) await Promise.all([this.onChangeSoThang()]);
          }
        }
      } catch (e) {
        this.$toast.error('Có lỗi: ' + JSON.stringify(e))
      } finally { this.loading(false) }
    },
    gridDSHopDong_FocusedRowChanged: async function (data) {
      this.loading(true);
      try {
        if (data && data.length > 0) {
          this.SetButton(3);
        } else {
          this.SetButton(1);
        }
      } catch (e) {
        console.log(e);
        this.loading(false);
      }
      this.loading(false);
    },
    // MaTB
    txtMaTB_KeyPress: function () {
      this.$toast.warning('Chức năng đang chờ xây dựng!')
      this.keyenter = 1;
    },
    btnTKMaTB_Click: function () {
      this.keyenter = 1;
      this.ShowSearchAccount();
    },
    ShowSearchAccount: function () {
      this.popupComponentAttr = {
        vma_tb: this.txtMaTB.value,
        vdichvuvt_id: this.cboDichVuVT.value,
      };
      this.popupComponent = () =>
        import("@/modules/search/subscriber/SearchAccount");
      this.popupComponentName = "SearchAccount";
      this.popupComponentHeader = "";
      this.Popup("popupComponents1");
    },
    LAY_TT_TBDATCOC_FRMDATCOCDS: async function () {
      try {
        let ds_thuebao = this.gridDSThueBao.list.filter(e => e.IS_CHECK)
        var vds_tb = ds_thuebao.map(e => ({
          "HDTB_ID": e?.HDTB_ID || null,
          "THUEBAO_ID": e?.THUEBAO_ID || null,
          "MA_TB": e?.MA_TB || null,
          "LOAITB_ID": e?.LOAITB_ID || null,
          "NHOMTB_ID": e?.NHOMTB_ID || null, 
          "TRANGBI_ID": e?.TRANGBI_ID || null, 
          "TOCDO_ID": e?.TOCDO_ID || null,
          "DOITUONG_ID": e?.DOITUONG_ID || null,
          "MUCCUOC_ID": e?.MUCCUOC_ID || null,
          "KIEULD_ID": e?.KIEULD_ID || null,
          "GOI_ID": e?.GOI_ID || null,
          "CHITIETKM_ID": e?.CHITIETKM_ID || null
        }))
        var input = {
          "KIEU_TH": 1,
          "KHUYENMAI_ID": this.cboTraTruoc.value,
          "NHOM_DC_ID": this.cboNhomDC.value,
          "HUONG_DC": this.cboSoThangDC.value,
          "DS_TB": vds_tb
        }
        var res = await this.$root.context.post(
          '/web-hopdong/lapdatmoi/fn_lay_tt_tbdatcoc_frmdatcocds', {vds_timkiem: JSON.stringify(input) }
        )
        if (res.error_code != 'BSS-00000000' && res.error_code != 'BSS-00000204') {
          this.$toast.error(res.message_detail ? res.message_detail : res.message)
          return null
        }
        console.log('LAY_TT_TBDATCOC_FRMDATCOCDS', res.data)
        if (res.data) {
          return res.data
        } else { 
          return null
        }
      } catch (e) {
        console.log('LAY_TT_TBDATCOC_FRMDATCOCDS', e)
        return null
      } finally {

      }
    },
    LAY_DS_TB_FRMDATCOCDS: async function (data) {
      try {
        if (data.kieu_tk == 1) {
          this.gridDSThueBao.list = []
        } else if (data.kieu_tk == 1) {
          this.gridDSHopDong.list = []
        } else if (data.kieu_tk == 3) {
          this.gridDSCTTraTruoc_HD.list = []
        }
        var input = {
          "KIEU_TK": data.kieu_tk,
          "DS_TK": data.ds_tk,
          "MA_TK": data.ma_gd,
          "LOAIHD_ID": data.loaihd_id,
          "CHUYENMANG": data.chuyenmang, 
          "THUEBAO_ID": data.thuebao_id, 
          "GOI_ID": data.goi_id,
          "NHOM_DATCOC_ID": data.nhomdc_id,
          "DONVI_ID": this.tt_nd.donvi_id,
          "NHANVIEN_ID": this.tt_nd.nhanvien_id,
          "DONVI_DL_ID": this.tt_nd.donvi_ql_id
        }
        var res = await this.$root.context.post(
          '/web-hopdong/lapdatmoi/fn_lay_ds_tb_frmdatcocds', {vds_timkiem: JSON.stringify(input) }
        )
        if (res.error_code != 'BSS-00000000' && res.error_code != 'BSS-00000204') {
          this.$toast.error(res.message_detail ? res.message_detail : res.message)
          return
        }
        if (res.data) {        
          // Tú thêm param DS_TB
          // if (res.data.DS_TB_HD.length > 0) {
          //   let output = res.data.DS_TB_HD
          //   output.map(x =>{
          //     this.param_dstb.push({ HDTB_ID : x.HDTB_ID, LOAITB_ID :x.LOAITB_ID, DICHVUVT_ID: x.DICHVUVT_ID, DOITUONG_ID : x.DOITUONG_ID, TRANGBI_ID:x.TRANGBI_ID, TOCDO_ID: x.TOCDO_ID, MUCCUOC_ID:x.MUCCUOC_ID })
          //   })
          // }
          // hết thêm
          if (data.kieu_tk == 1) {
            if (res.data.DS_TB_HD.length > 0) {
              let ds = res.data.DS_TB_HD
              this.gridDSThueBao.list = ds.map(e => ({...e, IS_CHECK: false, NUM_CHECK: 1, NHOMTB_LABEL: '1 . ' + (e?.NHOMTB_ID || 0)}))
                                      .sort((a, b) => Number(a.NHOMTB_LABEL) - Number(b.NHOMTB_LABEL))
            }            
          } else if (data.kieu_tk == 2) {
            if (res.data.DS_TB_HD.length > 0) {
              let ds = res.data.DS_TB_HD
              this.gridDSHopDong.list = ds.map(e => ({...e, IS_CHECK: false, NUM_CHECK: 1, NHOMTB_LABEL: '1 . ' + (e?.NHOMTB_ID || 0)}))
                                      .sort((a, b) => Number(a.NHOMTB_LABEL) - Number(b.NHOMTB_LABEL))
            }            
          } else if (data.kieu_tk == 3) {            
            let ds = res.data.DS_DATCOC_HDTB.concat(res.data.DS_KHUYENMAI_HDTB)
            let group_tongtien = []
            ds.reduce(function(res, value) {
              if (!res[value.NHOM_DATCOC_ID]) {
                res[value.NHOM_DATCOC_ID] = { 
                  NHOM_DATCOC_ID: value.NHOM_DATCOC_ID, 
                  TONG_TIENDC: 0, 
                  TONG_TIENTD: 0, 
                };
                group_tongtien.push(res[value.NHOM_DATCOC_ID])
              }
              res[value.NHOM_DATCOC_ID].TONG_TIENDC += value.DATCOC_CSD;
              res[value.NHOM_DATCOC_ID].TONG_TIENTD += value.TIEN_TD;
              return res;
            }, {});
            for (let ctkm of ds) {              
              let tongtien = group_tongtien.find(e => e.NHOM_DATCOC_ID == ctkm.NHOM_DATCOC_ID)
              ctkm.TONG_TIENDC = tongtien.TONG_TIENDC
              ctkm.TONG_TIENTD = tongtien.TONG_TIENTD
              ctkm.VAT_DC = Math.round(ctkm.DATCOC_CSD * Number(ctkm.TYLE_VAT) / (100 + Number(ctkm.TYLE_VAT)))
              ctkm.TIEN_DC = Math.round(ctkm.DATCOC_CSD * 100 / (100 + Number(ctkm.TYLE_VAT)))
              ctkm.NHOM_DATCOC_LABEL = `${ctkm?.TEN_NHOM || null} - Tiền đặt cọc: ${this.numberWithCommas(tongtien.TONG_TIENDC.toString())} - Tiền/tháng: ${this.numberWithCommas(tongtien.TONG_TIENTD.toString())}`
              ctkm.MA_TB = `${this.gridDSHopDong.list.find(e => e.HDTB_ID == ctkm.HDTB_ID)?.MA_TB}` || null 
              ctkm.IS_CHECK = false
              ctkm.NUM_CHECK = 1
              ctkm.NHOMDC_LABEL = 1 + '.' + ctkm.NHOM_DATCOC_ID
            }
            this.gridDSCTTraTruoc_HD.list = ds
          }          
        } else { 
        }
      } catch (e) {
        console.log('LAY_DS_TB_FRMDATCOCDS', e)
      } finally {

      }
    },
    templateCuocDCChange: function (dataItem, cuoc_dc) {
      for (let item of this.gridDSCTTraTruoc.list) {
        if (item['CHITIETKM_ID'] == dataItem['CHITIETKM_ID']) {
          item.DATCOC_CSD = cuoc_dc
          item.VAT_DC = Math.round(item.DATCOC_CSD * Number(item.TYLE_VAT) / (100 + Number(item.TYLE_VAT)))
          item.TIEN_DC = Math.round(item.DATCOC_CSD * 100 / (100 + Number(item.TYLE_VAT)))
          item.TIEN_TD = Math.round(item.DATCOC_CSD / item.HUONG_DC ? item.HUONG_DC : 1)
        }
        this.txtCuocDC.value += item.DATCOC_CSD
        this.txtTienTD.value += item.TIEN_TD
      }
    },
    templateNgayBDDCChange: function (dataItem, ngay_bddc) {
      console.log('templateNgayBDDCChange', dataItem, ngay_bddc)
      if (ngay_bddc && Number(moment(ngay_bddc, 'DD/MM/YYYY').format('YYYYMMDD')) < Number(moment(new Date(this.tt_nd.ngay_cn)).format('YYYYMMDD'))) {
        this.$toast.error('Ngày bắt đầu đặt cọc không được bé hơn ngày hiện tại!')
        this.dtpNgayBDDC.value = moment(new Date(this.tt_nd.ngay_cn)).format('DD/MM/YYYY')
      } else {
        this.dtpNgayBDDC.value = ngay_bddc
      }
      let ngay = moment(this.dtpNgayBDDC.value, 'DD/MM/YYYY').add(parseInt(this.cboSoThangDC.value), 'M')
      let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
      this.dtpNgayKTDC.value = moment(ngayktdc).format('DD/MM/YYYY')

      for (let item of this.gridDSCTTraTruoc.list) {
        if (dataItem['NHOM_DATCOC_ID'] && item['NHOM_DATCOC_ID'] == dataItem['NHOM_DATCOC_ID']) {
          item.NGAY_BDDC = ngay_bddc
          item.NGAY_KTDC = moment(ngayktdc).format('DD/MM/YYYY')
        } else if (item['CHITIETKM_ID'] == dataItem['CHITIETKM_ID']) {
          item.NGAY_BDDC = ngay_bddc
          item.NGAY_KTDC = moment(ngayktdc).format('DD/MM/YYYY')
        }
      }
      this.key_gridTB ++
    },
    onCheckNhomTB: function (dataItem) {
      console.log('onCheckNhomTB', dataItem)      
      this.cboCTTraTruoc.Enabled = false
      if (!dataItem.NHOMTB_ID || !this.chkTB_CungNhom) {
        this.cboCTTraTruoc.Enabled = true
      } else {
        let ds = this.gridDSThueBao.list.filter(e => e.NHOMTB_ID == dataItem.NHOMTB_ID)
        if (ds.length == 1) {
          this.cboCTTraTruoc.Enabled = true
        }
      }
      if (this.chkTB_CungNhom && dataItem.NHOMTB_ID) {
        for (let item of this.gridDSThueBao.list) {
          if (item.NHOMTB_ID == dataItem.NHOMTB_ID) {
            item.IS_CHECK = dataItem.IS_CHECK
            item.NUM_CHECK = dataItem.IS_CHECK ? 0 : 1
            item.NHOMTB_LABEL = item.NUM_CHECK + '.' + (item?.NHOMTB_ID || 0)
          } else {
            item.IS_CHECK = false 
            item.NUM_CHECK = 1
            item.NHOMTB_LABEL = item.NUM_CHECK + '.' + (item?.NHOMTB_ID || 0)
          }
        }        
      } else {
        for (let item of this.gridDSThueBao.list) {
          if (item.HDTB_ID == dataItem.HDTB_ID) {
            item.IS_CHECK = dataItem.IS_CHECK
            item.NUM_CHECK = dataItem.IS_CHECK ? 0 : 1
            item.NHOMTB_LABEL = item.NUM_CHECK + '.' + (item?.NHOMTB_ID || 0)
          } else {
            item.IS_CHECK = false
            item.NUM_CHECK = 1
            item.NHOMTB_LABEL = item.NUM_CHECK + '.' + (item?.NHOMTB_ID || 0)
          }
        }        
      }
      // Tú thêm 6/5/2023
      var select_boxdstb = this.gridDSThueBao.list.filter(item => item.IS_CHECK === true)
      if (select_boxdstb.length >0 ) {
        select_boxdstb.map(x =>{
              this.param_dstb.push({ HDTB_ID : x.HDTB_ID, LOAITB_ID :x.LOAITB_ID, DICHVUVT_ID: x.DICHVUVT_ID, DOITUONG_ID : x.DOITUONG_ID, TRANGBI_ID:x.TRANGBI_ID, TOCDO_ID: x.TOCDO_ID, MUCCUOC_ID:x.MUCCUOC_ID, GOI_ID:x.GOI_ID})
            })
      }
      else this.param_dstb =[]
      // Tú hết thêm
      let ds = this.gridDSThueBao.list.sort((a, b) => Number(a.NHOMTB_LABEL) - Number(b.NHOMTB_LABEL))
      this.gridDSThueBao.list = ds
      this.key_gridTB ++
      if (dataItem.IS_CHECK) {
        let first_row = dataItem.NHOMTB_ID ? this.gridDSThueBao.list.filter(e => e.IS_CHECK)[0] : dataItem
        this.nhomtb_id = first_row?.NHOMTB_ID || null
        this.hdtb_id = first_row?.HDTB_ID || null
        this.txtMaTB.value = first_row?.MA_TB || null
        this.thuebao_id = first_row?.THUEBAO_ID || null
        this.goi_id = first_row?.GOI_ID || null
        this.chuyenmang = first_row?.CHUYENMANG || null
        this.tocdo_id = first_row?.TOCDO_ID || null
        this.cboGoiCuocAdsl.value = first_row?.TOCDO_ID || null
        this.cboGoiCuocAdsl.text = first_row?.TOCDO || null
        this.cp_dichvuvt_id = first_row?.DICHVUVT_ID || null
        this.cboDichVuVT.text = this.cboDichVuVT?.Items.find(e => e.DICHVUVT_ID == this.cp_dichvuvt_id)?.text || null
        this.cboLoaiHinhTB.value = first_row?.LOAITB_ID || null
        this.cboLoaiHinhTB.text = first_row?.LOAIHINH_TB || null
        this.loaihd_id = first_row?.LOAIHD_ID || null
        this.cboKieuLD.value = first_row?.KIEULD_ID || null
        this.txtTenTB.value = first_row?.TEN_TB || null
        this.txtTenKH.value = first_row?.TEN_KH || null
        this.txtDiaChiKH.value = first_row?.DIACHI_KH || null
        this.txtDiaChiLD.value = first_row?.DIACHI_LD || null
        this.txtGoiDaDV.value = first_row && first_row['GOI_ID'] ? `[${first_row['GOI_ID']}] - ${first_row['GOI_DADV']}]` : null
        this.doituong_id = first_row?.DOITUONG_ID || null
        this.muccuoc_id = first_row?.MUCCUOC_ID || null
        this.trangbi_id = first_row?.TRANGBI_ID || null
        this.cboNhomDC.value = first_row?.NHOMDC_ID || null
        this.cboTraTruoc.value = first_row?.KHUYENMAI_ID || null
        this.cboSoThangDC.value = first_row?.HUONG_DC || null
        this.txtCuocDC.value = 0
        this.txtTienTD.value = 0  
        this.gridDSCTTraTruoc.list = []
      } else {
        let first_row = null
        this.nhomtb_id = first_row?.NHOMTB_ID || null
        this.hdtb_id = first_row?.HDTB_ID || null
        this.txtMaTB.value = first_row?.MA_TB || null
        this.thuebao_id = first_row?.THUEBAO_ID || null
        this.goi_id = first_row?.GOI_ID || null
        this.chuyenmang = first_row?.CHUYENMANG || null
        this.tocdo_id = first_row?.TOCDO_ID || null
        this.cboGoiCuocAdsl.value = first_row?.TOCDO_ID || null
        this.cboGoiCuocAdsl.text = first_row?.TOCDO || null
        this.cp_dichvuvt_id = first_row?.DICHVUVT_ID || null
        this.cboDichVuVT.text = first_row?.DICHVUVT_ID || null
        this.cboLoaiHinhTB.value = first_row?.LOAITB_ID || null
        this.cboLoaiHinhTB.text = first_row?.LOAIHINH_TB || null
        this.loaihd_id = first_row?.LOAIHD_ID || null
        this.cboKieuLD.value = first_row?.KIEULD_ID || null
        this.txtTenTB.value = first_row?.TEN_TB || null
        this.txtTenKH.value = first_row?.TEN_KH || null
        this.txtDiaChiKH.value = first_row?.DIACHI_KH || null        
        this.txtDiaChiLD.value = first_row?.DIACHI_LD || null
        this.txtGoiDaDV.value = first_row && first_row['GOI_ID'] ? `[${first_row['GOI_ID']}] - ${first_row['GOI_DADV']}]` : null
        this.doituong_id = first_row?.DOITUONG_ID || null
        this.muccuoc_id = first_row?.MUCCUOC_ID || null
        this.trangbi_id = first_row?.TRANGBI_ID || null
        this.cboNhomDC.value = first_row?.NHOMDC_ID || null
        this.cboTraTruoc.value = first_row?.KHUYENMAI_ID || null
        this.cboSoThangDC.value = first_row?.HUONG_DC || null
        this.txtCuocDC.value = 0
        this.txtTienTD.value = 0  
        this.gridDSCTTraTruoc.list = []
      }
      this.button.tsbtnGhiLai = this.gridDSCTTraTruoc.list && this.gridDSCTTraTruoc.list.length > 0 ? true : false // disabled - false : true
    },
    gridDSThueBao_FocusedRowChanged: async function (data) {
      this.loading(true);
      try {
        if (data && data.length > 0) {
        }
      } catch (e) {
        console.log(e);
        this.loading(false);
      }
      this.loading(false);
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.chkCTV) {
        this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien"
          );
        this.popupComponentName = "popupCTV";
        this.popupComponentHeader = "Chọn cộng tác viên";
        this.Popup("popupComponents1");
      } else {
        this.filterbox.ctv.txtCTV = "";
        this.filterbox.ctv.ctv_id = -1;
      }
    },
    ShowPopupNguoiGT() {
      if (this.filterbox.nguoigt.chkNguoiGT) {
        this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu"
          );
        this.popupComponentName = "popupNGT";
        this.popupComponentHeader = "Chọn người giới thiệu";
        this.Popup("popupComponents1");
      } else {
        this.filterbox.nguoigt.txtNguoiGT = "";
        this.filterbox.nguoigt.nguoigt_id = -1;
      }
    },
    ShowPopupChonKhuyenMai() {
      var dulieu = {};
      dulieu.loai = Loai_KM.TraTruoc;
      dulieu.dt = this.cboCTTraTruoc.Items;
      this.popupComponentData = dulieu;
      this.popupComponent = () =>
        import(
          "@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai"
        );
      this.popupComponentName = "popup-ChonKhuyenMai";
      this.popupComponentHeader = "";
      this.Popup("popupComponents1");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents1");
        return;
      }
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "popupCTV":
            this.filterbox.ctv.txtCTV = val.ten_ht;
            this.filterbox.ctv.ctv_id = val.nhanvien_id;
            break;
          case "popupNGT":
            this.filterbox.nguoigt.txtNguoiGT = val.ten_ht;
            this.filterbox.nguoigt.nguoigt_id = val.nhanvien_id;
            break;
          case "SearchAccount":
            if (val.ma_tb) this.txtMaTB.value = val.ma_tb;
            else this.txtMaTB.value = "";
            if (val.ma_kh) this.ma_kh = val.ma_kh;
            else this.ma_kh = "";
            break;
          case "popup-ChonKhuyenMai":
            this.cboTraTruoc.value = val.khuyenmai.khuyenmai_id;
            break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.$toast.error("" + ex.data.message_detail);
        else this.$toast.error("" + ex);
      } finally {
        this.ClosePopup("popupComponents1");
        this.loading(false);
      }
    },
    SetButton: function (kieu) {
      this.button.tsbtnNhapMoi = false;
      this.button.tsbtnGhiLai = false;
      this.button.tsbtnXoa = false;
      this.button.tsbtnHuyBo = false;
      if (kieu == -1) {
        //Bat dau
        this.button.tsbtnGhiLai = true;
        this.button.tsbtnHuyBo = true;
      }
      if (kieu == 0) {
        //Bat dau
        this.$refs.txtMaTB.focus();
        this.button.tsbtnNhapMoi = true;
        this.Clear();
      }
      if (kieu == 1) {
        //Them moi
        this.$refs.txtMaTB.focus();
        this.button.tsbtnGhiLai = true;
        this.button.tsbtnHuyBo = true;
        this.Clear();
      }
      if (kieu == 2) {
        //Huy
        this.button.tsbtnNhapMoi = true;
        this.button.tsbtnXoa = true;
        this.Clear();
      }
      if (kieu == 3) {
        //Edit
        this.button.tsbtnNhapMoi = true;
        this.button.tsbtnXoa = true;
        this.button.tsbtnGhiLai = true;
        this.button.tsbtnHuyBo = true;
      }
    },
    Clear: function () {
      // TT thue bao
      if (this.tag != '1') this.txtMaGD.value = "";
      this.txtMaTB.value = "";
      this.txtTenTB.value = "";
      this.txtGoiDaDV.value = "";
      this.txtDiaChiLD.value = "";
      // TT tra truoc
      this.cboNhomDC.value = 0
      this.cboTraTruoc.value = 0
      this.cboSoThangDC.value = 0;
      this.dtpNgayBDDC.value = moment(new Date(this.tt_nd.ngay_cn)).format('DD/MM/YYYY')
      this.dtpNgayKTDC.value = moment(new Date(this.tt_nd.ngay_cn)).format('DD/MM/YYYY')
      this.txtCuocDC.value = 0;
      this.txtTienTD.value = 0;      
      this.txtGhiChu.value = "";
      // Table
      this.gridDSThueBao.list = [];
      this.gridDSCTTraTruoc.list = []
      this.gridDSHopDong.list = [];
      this.gridDSCTTraTruoc_HD.list = []
      // TT hidden
      this.khachhang_id = 0
      this.hdkh_id = 0
      this.ma_kh = ""
      this.nhomtb_id = 0
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.goi_id = 0
      this.chuyenmang = 0
      this.doituong_id = 0
      this.phuongld_id = 0;
      this.phold_id = 0;
      this.apld_id = 0;
      this.khuld_id = 0;
      this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI; //default khách hàng tự trang bị
      this.tocdo_id = 0;
      this.muccuoc_id = 0;
      this.tthd_id = 0
      this.huonggiaotn_id = 0
      this.quytrinh_id = 0
      
      this.filterbox.nguoigt.chkNguoiGT = false;
      if (this.khong_load_ctv == 0) this.filterbox.ctv.chkCTV = false;
    },
    GET_KEYS: async function(key) {
      var input = {
        keyname: key
      }
      let response = await api.getKey(this.axios, input)
      if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
        return Number(response.data.data)
      } else {
        this.$toast.error('Có lỗi trong quá trình tạo key mới')
        return
      }
    },
    GetData: function (response) {
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        // console.log(response.data.message);
        // this.ShowError(response.data.message);
        return [];
      }
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    ChuanHoaTen(ten) {
      if (!ten) return "";
      var ten_new = "";
      var str_con = ten.trim();
      var str_xl = "";
      var k = 0;
      var found = true;
      if (str_con.length < 1) return;
      str_con = str_con.replaceAll(/\s+/g, " ");
      try {
        while (found) {
          k = str_con.indexOf(" ");
          if (k > 0) {
            str_xl = str_con.substring(0, k);
            str_con = str_con.substring(k + 1);
            str_xl = this.ChuyenChuHoa(str_xl);
            if (ten_new != "") ten_new += " " + str_xl;
            else ten_new = str_xl;
            found = true;
          } else found = false;
        }
        if (str_con != "") {
          str_xl = str_con;
          str_xl = this.ChuyenChuHoa(str_xl);
          if (ten_new != "") ten_new = ten_new + " " + str_xl;
          else ten_new = str_xl;
        }
        return ten_new;
      } catch (e) {
        return ten;
      }
    },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
  },
};
</script>
