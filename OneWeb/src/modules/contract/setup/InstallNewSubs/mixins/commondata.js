import { required, minValue, maxValue } from 'vuelidate/lib/validators'
import {
  DichVuVienThong,
  TRANGBI,
  DUNG_THU,
  LoaiHopDong,
  TrangThaiHD,
  LoaiHinhTB,
  ThoiHan,
  KHOANMUC_TT,
  KieuCaiDatDVGT,
  THUESO,
  TRANGTHAI_DONGBO,
  KieuLapDat,
  LOAI_DV,
  Loai_KH,
  HINHTHUC_TC,
  KIEU_HD,
  KIEU_KH,
  PHANLOAI_KH,
  Loai_NV,
  LOAISO,
  LOAI_KV,
  Loai_KM,
  LOAINODE_MGWAN,
  KIEULAP,
  FSECURE_KM_12_MONTHS,
  MUCCUOC,
  LOAITBI_ADSL,
} from "../Enums";
import moment from 'moment'
export default {
    validations: {
        diachiKH:
        {
        DIACHI: {
            required
        },
        },
        diachiHK:
        {
        DIACHI: {
            required
        },
        },
        diachiNDD:
        {
        DIACHI: {
            required
        },
        },
        diachiTT:
        {
        DIACHI: {
            required
        },
        },
        diachiCT:
        {
        DIACHI: {
            required
        },
        },
        diachiBC:
        {
        DIACHI: {
            required
        },
        },
        diachiTB:
        {
        DIACHI: {
            required
        },
        },
        diachiLD:
        {
        DIACHI: {
            required
        },
        },
        diachiKN:
        {
        DIACHI: {
            required
        },
        },
        diachiDau:
        {
        DIACHI: {
            required
        },
        },
        diachiCuoi:
        {
        DIACHI: {
            required
        },
        },
    },
    data () {
        return {
          debug: true,
          IS_HD_TUVAN: false,
          watcherActive: false,
          mgw_mtn_ktr_hoa: false,
          LoaiHinhTB: LoaiHinhTB,
          ItemList: [],
          ma_tb_ht: '',
          LOAINODE_MGWAN: LOAINODE_MGWAN,
          popupComponent: null,
          popupComponentData: null,
          popupComponentAttr: {},
          popupComponentEvts: {
            'form-close':this.popupClosed,
            'acceptChangeCTV':this.popupClosed,
            'acceptChangeNGT':this.popupClosed,
            'PopUpCongTacVien_Thoat':this.popupClosed,
          },
          popupComponentName: '',
          ma_tb: "",
          ma_tb_ao_ht_cuoi: '',
          ma_tb_ao_ht: '',
          hdkhtn_id: 0,
          Tag: "",
          iKieu: 0,//0: Lắp mới, 1: Sửa khuyến mại
          header: {
            title: 'Lắp đặt mới',
            list: [
              //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
              { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
              { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
            ]
          },
          // datatable: {
          //   dtPhanLoaiKH: [],
          // },
          thamsomacdinh: {},
          thamso_nguoidung: {
            chuquanmd_id: 0,
          },
          DONGBO_TEST: {TEST:false},
          PHANTAP_KH: 0,
          DONVIQL_HTTT: 0,
          KHUVUC_THEO_KETCUOI: this.KHUVUC_THEO_KETCUOI,
          CHONSO_MOI: false,
          CHONSO_MOI_V2: false,
          kieuld_fsecure_kem_fiber: 0,
          hdtb_id: 0,
          hdtt_id: 0, //TODO tim cach load hdtt_id
          ma_tb_ao: '',
          _sl_cha: 0,
          PHANLOAIKH_ID: -1,
          nhomplkh: 0,
          vkieukh_id_: 0,  //biến toàn cục kiểm tra KH cá nhân hay doanh nghiệp
          nhap_tocdo_thuc_adsl: 0,
          ht_loc_cbo_tram: false,
          ht_khl: 0,
          loaisowan:0,
          loaisoisdn:1,
          kh_cd: 0,
          ltb_adsl_id: 1,
          kt_load: true,
          tocdo_id: 0,
          trangbi_id: 0,
          tocdo_cu_id: 0,
          tocdo_moi_id: 0,
          muccuoc_id: 0,
          kiemtra_dm_mgwan_tsl:false,
          kiemtra_dm_codinh: false,
          kiemtra_dm_ims: false,
          kiemtra_dm_adsl: false,
          kiemtra_dm_didong: false,
          kiemtra_dm_gphone: false,
          ht_loc_cbo_tram: false,
          frmthuebao: '',
          DichVuVienThong:DichVuVienThong,
          buttons: {
            tsbtnXoa: false,
            tsbtnGhiLai: false,
            tsbtnHuyBo: false,
            tsbtnThanhToan: false,
            inPhụLụcToolStripMenuItem: false,
            tsbtnXoaTB:false,
            tsbtnThemTB: false,
            tsbtnThemTT: false,
            tsbtnXoaTT: false,
            btnChonSM: true,
            chkLapDatBS: false,
            btnThongTinKhac: false,
            btnChonSoIMS: true,
            btnHuongKNWan: false,
            tsbtnNhapMoi: false,
          },
          diachikh_id: 0,
          diachitt_id: 0,
          diachibc_id: 0,
          diachitb_id: 0,
          diachild_id: 0,
          diachiKH: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiHK: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiNDD: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiTT: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiCT: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiBC: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiTB: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiLD: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiKN: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiDau: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          diachiCuoi: {
            DIACHI: null,
            TINH_ID:0,
            QUAN_ID:0,
            PHUONG_ID:0,
            PHO_ID:0,
            AP_ID:0,
            KHU_ID:0,
            DACDIEM_ID:0,
            SO_NHA:null,
            Lng: 0,
            Lat: 0,
            BLOCK: null,
            TANG: null,
            PHONG: null,
            MOTA: null,
          },
          cbvmp: 0,
          NgayYeuCau: moment(new Date()).format("DD/MM/YYYY"),
          NgayLapHD: moment(new Date()).format("DD/MM/YYYY"),
          NgayCap: null,//moment(new Date()).format("DD/MM/YYYY"),
          NgayHH: null,//moment(new Date()).format("DD/MM/YYYY"),
          NgaySinh: null,//moment(new Date()).format("DD/MM/YYYY"),
          NgayCapHK: null,//moment(new Date()).format("DD/MM/YYYY"),
          NgaySinhTB: null,//moment(new Date()).format("DD/MM/YYYY"),
          //NgayCV: moment(new Date()).format("DD/MM/YYYY"),
          TGThueTuNgay:null,//moment(new Date()).format(this.datetimeFormat),
          TGThueDenNgay:null,//moment(new Date()).format(this.datetimeFormat),
          kt_thuenganhan: false,
          dateFormat: "DD/MM/YYYY",
          datetimeFormat: "DD/MM/YYYY HH:mm",
          iKenhTN: 1,
          _fiberBoSung: '',
          maduan: '',
          VPHIEUCSKH_ID: 0,
          IS_HD_TUVAN: false,
          dtienld: 0,
          dvatld: 0,
          dtiencd: 0,
          dvatcd: 0,
          vtyle_vat_id_dnhm: 0,
          vtyle_vat_dnhm: 0,
          nhanvien_ql_am_id: 0,
          m_dsThueBao_DichVu: [],
          vtyle_vat_cd: 10,
          vtyle_vat_id_cd: 1,
          listbox: {
            trangbi: { list:[], valuewan:TRANGBI.VIENTHONG_TRANGBI,valuetsl:0, valuecd:3, valueims:0, valueadsl:0, valuedd:0, valuegp:0, },
            loai_tb: { list:[], value:0, valuedau:0, valuecuoi:0, valuemgwan:0, },
            chu_quan: { list:[], value:0, valuewan:0, valueldmgwan:0, valuetsl:0, valuecd:143, valueldcd:9, valueims:0, valueldims:0, valueadsl:0, valueldadsl:0, valuedd:0, valuegp:0, },
            tien_km: { list:[], value:0, },
            doituong: { list:[], value:"", },
            dichvu_vt: { list:[], value:DichVuVienThong.CO_DINH, },
            loaihinh_tb: { list:[], value:LoaiHinhTB.DIENTHOAI_CD, },
            loai_khl: { list:[], value:0, },
            loai_gt: { list:[], value:0, },
            phanloai_kh: { list:[], value:0, },
            loai_kh: { list:[], value:null, },
            nganhang: { list:[], value:0, valuekh:0, sotaikhoankh:'',sotaikhoan:'', ischecked:false,ischeckedkh:false,isnganhangnn:false, },
            hinhthuc_tt: { list:[], value:0, },
            hinhthuc_tc: { list:[], value:0, valueims:0, },
            dantoc: { list:[], value:0, },
            nganhnghe: { list:[], value:0, },
            nganhngheloc: { list:[], value:0, },
            quoctich: { list:[], value:0, },
            hinhthuc_tthi: { list:[], value:0, },
            kieu_hd: { list:[], value:0, },
            goi_kt: { list:[], value:0, },
            donvi_ql: { list:[], value:0,valueql:0 },
            kieu_ld: { list:[], value:KieuLapDat.DM_CODINH, },
            nhacc_cu: { list:[], value:0, ischecked:false, },
            congnghe: { list:[], value:0, },
            hinhthuc_cm: { list:[], value:0, },
            thoihan: { list:[], value:0,valuetsl:0,valuewan:0,valuecd:0,valueadsl:0,valueims:0 },
            loai_csht: { list:[], value:0, },
            doitac: { list:[], valueims:0, valuecsht:0, valueinternet:0, valuecd:0, ischeckedcd:false, ischeckedadsl:false, },
            cboLoaiIP: { list:[], value:0, values: [], valueitems: [], },
            phanloai_dv: { list:[], value:0, },
            ht_chitietdv_ims: { list:[], value:0, },
            ht_phantach_tocdo: { list:[], value:0, },
            trangthai_yc: {list:[],value:0,},
            donvi_dau: {list:[],value:0,},
            donvi_cuoi: {list:[],value:0,},
            tinh_kn: {list:[],value:0,},
            loaikenh: {list:[],value:0, valuetsl:0,},
            tocdokenh: { list:[],valuecir:0,valuepir:0,valuetsl:0,valueisp:0,valuenix:0, ischeckedpir:false, ischeckedisp:false, ischeckednix:false, },
            doitactsl: {list:[], value:0, chkDoiTacTSL: false, },
            doitacwan: {list:[], value:0, isenabled:false,},
            loaimodem: {list:[], value:0,},
            loaicap: {list:[], value:0,valuedau:0,valuecuoi:0},
            muccuoc: {list:[], value:0, isenabled: true, },
            muccuoc_tb: { list:[], value:0, },
            thanhtoan: {list:[],value:""},
            thanhtoancbo: {list:[],value:""},
            danhba: { list:[], value:0, },
            dshopdong_chuaht: {list:[],header:[],value:0},
            madaidien: {list:[], value:null},
            cboLoaiTB: { list:[], value: 0, },
            cboGoiCuocAdsl: { list:[], value:0, },
            cboTocDoThucADSL: { list:[], value:0, },
            cboHDChinh: { list:[], value:0, },
            cboDbChinh: { list:[], value:0, },
            cboDoiTac_CSHT: { list:[], value:0, },
            cboLoai_CSHT: { list:[], value:0, },
            cboKieuTra: { list:[], value:0, },
            cboThangTra: { list:[], value:0, },
          },
          tieunganh: {
            dsTNC1: [],
            dsTNC2: [],
            dsTNC3: [],
            dsTNC4: [],
            TNC1_ID: 0,
            TNC2_ID: 0,
            TNC3_ID: 0,
            TNC4_ID: 0,
            TNCMOTA: '',
          },
          thanhToanId: 0, //la maTT khi chon 1 phan tu o bang thanh toan
          // searchDate: {
          //   isEnabled: false,
          //   fromDate: new Date(),
          //   toDate: new Date(),
          //   dateconfig: {
          //     altFormat: 'd/m/Y',
          //     altInput: true,
          //     dateFormat: 'd/m/Y',
          //     allowInput:true,
          //   },
          // },
          filterbox: {
            ctv: {
              value: '',
              ischecked: false,
              ma_ctv: '',
            },
            nguoigt: {
              value: '',
              ischecked: false,
              nguoigt_id: '',
            },
            khuyenmai: {
              list: [],
              value: '',
              ischecked: false,
            },
            tratruoc: {
              list: [],
              value: '',
              ischecked: false,
            },
          },
          dateconfig: {
            altFormat: 'd/m/Y',
            altInput: true,
            dateFormat: 'd/m/Y',
            allowInput:true,
          },
          datetimeconfig: {
            altFormat: 'd/m/Y H:i:S',
            altInput: true,
            dateFormat: 'd/m/Y H:i:S',
            allowInput:true,
            enableTime: true,
          },
          popupbox: {
            nvtc: 0,
          },
          khachhang: {
            makh: '',
            sogt: '',
            magd: '',
            mahd: '',
            khachhang_id: 0,
            tenkh: '',
            nguoidd: '',
            dienthoai: '',
            fax: '',
            chucvu: '',
            noicaphk: '',
            noicap: '',
            gioitinh: -1,
            masothue: '',
            ghichu: '',
            dactrung: '',
            PHANLOAIKH_ID: -1,
            hdkh_id: 0,
            plkh: '',
            nhom_plkh:0,
          },
          thuebao: { //###thuebao
            codinh: {
              somay: '',
              sochinh: '',
              dgvTienCK: {
                list: [],
                headers: [{fieldName:'tuthang',headerText:'Từ tháng', allowFiltering:true},{fieldName:'denthang',headerText:'Đến tháng',allowFiltering:true},{fieldName:'tien_ck',headerText:'Tiền cam kết',allowFiltering:true, allowHtml:true}],
              },
              arraySoMay: [],
            },
            mgwan: {
              somay: '',
              soao: '',
              matn: '',
              madoicap: '',
              huongkn: '',
              vhuongkn: '',
              vrfvfi: '',
              lanip: '',
              wanip: '',
              mahdttkd: '',
              luonggiukenh: false,
              chkLuongGiuKenh: false,
              cuoctk: 0,
              cuoctk_dt: 0,
              cuoctc: 0,
              cuoctc_dt: 0,
              hopdong_dt: '',
              cuoctbi: 0,
              cuocht: 0,
              soluongip: 0,
              cuocip: 0,
              cuocisp: 0,
              cuocnix: 0,
            },
            adsl: {
              tientra: 0,
              btnKiemTraAccount_visible: true,
              txtAccountADSL: '',
              txtSoAoAdsl: '',
              txtMaTN_ADSL: '',
              txtMaDoiCap_ADSL: '',
              validadslacc: false,
              txtEmailAdsl: '',
              chkTocDoThuc: false,
              ckTuCaiADSL: false,
              dtpHanDatCho: moment(new Date()).format("DD/MM/YYYY"),
              dtpHanDatCho_Visible: true,
              txtDienThoai_Fsecure: '',
              txtSL_Tivi: 0,
              txtCuocDT_Internet: 0,
              txtSerialSTB: '',
              dtpNgayTra: null,
              txtTTCuoc: '',
              txtCSD_FIBER_TK: 0,
              txtCTB_FIBER_TK: 0,
              pass_adsl: '',
              txtAccountADSL_ReadOnly: false,
              kh_cd_fsecure: false,
              isHopDong: 1,
              txtMaTB_KN:''
            },
            tsl: {
              txtMaTSL: {
                ReadOnly: false,
                Text: '',
              },
              txtMaHD_TTKD_TSL: '',
              txtSoSoiQuang: 0,
              txtSoSoiQuang_Enabled: true,
              txtCuocTKDT_TSL: 0,
              txtCuocTCDT_TSL: 0,
              txtMaHD_DT_TSL: '',
              txtCuocHT_TSL: 0,
              txtCuocTBI_TSL: 0,
              txtCuocTC_TSL: 0,
              txtCuocTK_TSL: 0,
              diachidau_id: 0,
              diachicuoi_id: 0,
              txtSoAoTSL_Dau: '',
              txtSoAoTSL_Cuoi: '',
              txtVrf_Dau: '',
              txtVrf_Cuoi: '',
              txtSiteIDTSL_Dau: '',
              txtSiteIDTSL_Cuoi: '',
            },
            thuebao_id: 0,
            tentb: '',
            ghichu: '',
            tbhd_pl: 0,
            thuebao_id_cha: 0,
            loaisochinh: 0,
            hdtb_id_cha: 0,
            thangck: 0,
            doituong_ct_id: 0,
            tgdungthu: 0,
            tinhkhac: 0,
            khuvuc: '',
            khuvuc_id: 0,
            check_tb_adsl: 0,
            statusVdc: false,
            cvlct: false,
            congvan: "",
            NgayCVChecked: false,
            NgayCV: moment(new Date()).format("DD/MM/YYYY"),
            crm: "",
            erp: "",
            vtn: "",
          },
          thanhtoan: {
            matt: '',
            tentt: '',
            thanhtoan_id: 0,
            masothue: '',
            sodienthoai: '',
            email: '',
            chutk: '',
            tuyenthu_id: 0,
            matuyen: '',
            manvtc: '',
            tuyenthu_httt: 0,
            nhanvientc_id:0,
            ghichu: '',
            hdmig_id: 0,
            mig_id: 0,
            mainghep: '',
            matb_dd: 0,
            manvql: '',
          },
          ma_vnpt: -1,
          thuebaocd: {
            matn: '',
            sothang_thueso: 0,
            noidung_thueso: '',
            kieu_thueso: 0,
            madoicap: null,
            cuocdoitac: 0,
          },
          nganhang: {
            TIEN_DM: 0,
            DV_QHNS: 0,
            MACHUONG: 0,
            MN_KT: 0,
            MA_NDKT: 0,
            MN_NSNN: 0,
          },
          thietbi: {
            tien_tbi: 0,
            vat_tbi: 0,
            tien_kmtbi: 0,
            vat_kmtbi: 0,
            tien_tgtb_tragop: 0,
            vat_tgtb_tragop: 0,
            tien_tgtb_tratruoc: 0,
            vat_tgtb_tratruoc: 0,
            dtien_kmtb: 0,
            dvat_kmtb: 0,
            dtientbi: 0,
            dvattbi: 0,
          },
          dsHDTB: [],
          dsHDTBGOI: [],
          dsDvKhacChose: [],
          dsDvMaster: [],
          dsDichVuKhac: [],
          dsDiaChi: [],
          dsDiaChiHDKH: [],
          dsDiaChiHDTT: [],
          dsDiaChiHDTB: [],
          dsDK_DVGT: [],
          dsTien_DatCoc: [],
          dsDK_DVKHAC: [],
          dsDK_DVK_TB: [],
          dsDK_DVK_TT: [],
          dsCTM_TBI: [],
          dsKM_HDTB: [],
          dsDC_HDTB: [],
          DS_CTKM_LTBI: [],
          dsHDTB_CD: [],
          dsHDTBDV: [],
          dsCAMKET_HDTB: [],
          dsHDTBDV_Fsecure: [],
          dgrKhuyenMai: [],
          //dgvTienCK: [],
          dtControl: [],
          dsHDTB_KV: [],
          // dsHDTB_KV_tmp: [],
          dsHDTB_KV_ThemTB: [],
          dsHDKH_Sub: [],
          dsHDKH: [],
          dsHDTT: [],
          dsCTTHD: [],
          dsHDTT_NV: [],
          dsHDTB_DUNGTHU: [],
          dsTRAGOP_TBI_HDTB: [],
          ds_tien_khoanmuc: [],
          ds_tien_khoanmuc_bd: [],
          dsHDTB_DD: [],
          dsHDTB_INT: [],
          dsHDTB_ADSL: [],
          dsHDTB_MEWAN: [],
          dsHDTB_TSL: [],
          dsHDTB_GP: [],
          dsTTTT: [],
          dsTien_HDTB: [],
          dsHDTB_IMS: [],
          dsHDTB_CNTT: [],
          dsHDTB_SUB: [],
          dsHDTB_CT: [],
          dsHDTB_LoaiIp: [],
          nganhnghe_id: 0,
          lvwThueBao: [],
          lvwThanhToan: [],
          lvDvkhac: {list:[],value:0},
          lvDvkhac_TT: {list:[],value:0},
          lvDvgt: { list:[], value: null },
          lvMuaThietbi: { list:[], value: null },
          gridEditors: [],
          DT_CONTROLS: {
            dichvu_vt: 0,
            list: [],
            data: [],
            value: [],
            tagvalue: [],
            key: 0,
          },
          kmld: {
            tien_kmld: 0,
            vat_kmld: 0,
            tien_datcoc: 0,
            dtienkmld: 0,
            dvatkmld: 0,
            tong_kmld: 0,
            tyle_kmld: 0,
            dtienkmdc:0,
            dvatkmdc:0,
            tyle_kmld_tt: 0,
            dtiendatcoc_tt:0,
            dvatdatcoc_tt: 0,
            tong_kmld_tt: 0,
            tien_kmld_tt:0,
            vat_kmld_tt:0,

            dtiendatcoc_km:0,
            dvatdatcoc_km:0,
          },
          dvgt: {
            tien_dv: 0,
            vat_dv: 0,
            tien_km_dvgt: 0,
            vat_km_dvgt: 0,
          },
          dichvu: {
            dtien_dv: 0,
            dvat_dv: 0,
          },
          tongtienhd: 0,
          tongtienkm: 0,
          tongvatkm: 0,
          STATUS_VDC: null,
          buttonSet: null,
          hdkh_cha_id: 0,
          chkMienCuocTB: false,
          chkVSVX: false,
          ts_sinhma_gd_theo_donvi: false,
          TUDONG_SINH_SOAO: false,//false,
          TD_GANTHEM_HDTB_DV: false,
          bDuLieuKS: false, // chi bang true khi lay du lieu tu btnLayDulieuKS_Click
          phieucskhdv_id: 0,
          loi_sinh_magd: false,
          KetCuoi_ID: -1,
          loainv_ctv_hrm: 0,
          loainv_ngt_hrm: 0,
          ts_kt_tthdkhdn: 0,
          ts_kt_ltb_hrm: false,
          huonggiao_tn: 0,
          quytrinh_tn: 0,
          luong_id: 0,
          words: '',
          vkieuhd_id: 0,
        }
      },
    computed: {
        cloaihinh_tb() {
          if(!this.listbox.loaihinh_tb.list || this.listbox.loaihinh_tb.list.length<=0) return [];
          var list = this.listbox.loaihinh_tb.list.filter(lh=>(lh.dichvuvt_id==this.listbox.dichvu_vt.value)).map(lh=>({id:lh.loaitb_id,text:lh.loaihinh_tb}));
          if(!list || list.length<=0) {
            this.listbox.loaihinh_tb.value = 0;
            return [];
          }
          var i=0;
          for(;i<list.length;i++) {
            if(list[i].id==this.listbox.loaihinh_tb.value) return list;
          }
          if(i>=list.length)
            this.listbox.loaihinh_tb.value = list[0].id;
          return list;
        },
        ckieu_ld() {
          if(!this.listbox.kieu_ld.list || this.listbox.kieu_ld.list.length<=0) return [];
          var list = this.listbox.kieu_ld.list.filter(ld=>(ld.loaitb_id==this.listbox.loaihinh_tb.value)).map(ld=>({id:ld.kieuld_id,text:ld.ten_kieuld}));
          if(!list || list.length<=0) {
            this.listbox.kieu_ld.value = 0;
            return [];
          }
          var i=0;
          for(;i<list.length;i++) if(list[i].id==this.listbox.kieu_ld.value) return list;
          if(i>=list.length)
            this.listbox.kieu_ld.value = list[0].id;
          return list;
        },
        ccongnghe() {
          if(!this.listbox.congnghe.list || this.listbox.congnghe.list.length<=0) return [];
          var list = this.listbox.congnghe.list.filter(cn=>(cn.kieuld_id==this.listbox.kieu_ld.value)).map(cn=>({id:cn.congnghe_id,text:cn.congnghe}));
          if(!list || list.length<=0) {
            this.listbox.congnghe.value = 0;
            return [];
          }
          var i=0;
          for(;i<list.length;i++) if(list[i].id==this.listbox.congnghe.value) return list;
          if(i>=list.length)
            this.listbox.congnghe.value = list[0].id;
          return list;
        },
        DiaChiLDCLONE() {
          return JSON.parse(JSON.stringify(this.diachiLD));
        },
        ma_lhtb_vdc() {
          try { return this.listbox.loaihinh_tb.list.filter(x=>x.loaitb_id==this.listbox.loaihinh_tb.value)[0]['ma_lhtb_vdc']*1; }
          catch (ex) { return 0; }
        },
        kieu_ld_kieu() {
          try { return this.listbox.kieu_ld.list.filter(x=>x.kieuld_id==this.listbox.kieu_ld.value)[0]['kieu']*1; }
          catch (ex) { return 0; }
        },
        DT_DV() {
          this.DT_CONTROLS.key;
          return idx => this.DT_CONTROLS.data[idx].map(c=>({id:c.DONVI_ID,text:c.TEN_DV}))
        },
        loai_kh_tree: {
          get() {
            var listkieukh = [...new Set(this.listbox.loai_kh.list.map(x=>x.kieu_kh))].map(x=>({ label:x, id:x }));
            for(var kieukh of listkieukh) {
              kieukh.children = [...new Set(this.listbox.loai_kh.list.filter(x=>x.kieu_kh==kieukh.label).map(x=>x.ten_nhom))].map(x=>({label:x,id:x}));
              for(var nhomkh of kieukh.children) {
                nhomkh.children = this.listbox.loai_kh.list.filter(x=>x.kieu_kh==kieukh.label && x.ten_nhom==nhomkh.label).map(x=>({id:x.loaikh_id,label:x.ten_loaikh,cate:kieukh.label,subcate:nhomkh.label}));
              }
            }
            return listkieukh;
          }
        },
        TinhKhac: {
          get() {
            var tk = this.listbox.loaihinh_tb.list.filter(lh=>(lh.dichvuvt_id==this.listbox.dichvu_vt.value && lh.loaitb_id==this.listbox.loaihinh_tb.value))
            if(tk && tk.length>0 && tk[0] && tk[0]['tinhkhac'])
              return tk[0]['tinhkhac']
            else return 0; // Mac dinh bien nay la bien global 0
          }
        },
        TinhKhacLD: {
          get() {
            var tk = this.listbox.kieu_ld.list.filter(x=>x.kieuld_id==this.listbox.kieu_ld.value);
            if(tk && tk.length>0 && tk[0] && tk[0]['tinhkhac'])
              return tk[0]['tinhkhac']
            else return 0;
          }
        },
        // cant_delete:{
        //   get() {
        //     var dsfile = this.gridbox.filehoso.list.filter(x=>x.ischecked);
        //     return (!dsfile || dsfile.length<=0)
        //   },

        // }
      },
  }
