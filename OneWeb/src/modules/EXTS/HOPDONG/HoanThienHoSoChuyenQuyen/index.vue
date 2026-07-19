<template src="./index.html">
</template>

<script>
import Modal from './Modal/index.vue'
import ChonHopDong from './ChonHopDong/index.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import DateTimeLib from "../../../../utils/DateTimeLib";
import { required } from 'vuelidate/lib/validators'
import Enum from "./Enum";
import vdcService from "./service/vdcService";
import vasc from "./service/vascService";
import service from './service/vtnService';
import ccbsService from './service/ccbsService';

const DatabaseConstants = { DB2: "CSS" };
export default {
    components: { Modal, BssErrorMarker, BssRequiredMarker, ChonHopDong, Enum, DateTimeLib },
    validations: {
    //   arrayCBO: {
    //     cboTrangThai: {
    //       required
    //     },
    //     cboKieuCC: {
    //       required
    //     },
    //     cboHoSo: {
    //         required
    //     },
    //   },
      modelInput: {
        txtMaGD: {
          required
        },

        // ghiChu: {
        //   maxLength: maxLength(4000)
        // }
      },
    //   arrayDate: {
    //     startDate: {
    //       required,
    //       maxValue(val, {endDate}){
    //         return new Date(endDate) >= new Date(val);
    //       }
    //     },
    //     endDate: {
    //       required,
    //     },
    //     fromDate: {
    //       required,
    //       maxValue(val, {toDate}){
    //         return new Date(toDate) >= new Date(val);
    //       }
    //     },
    //     toDate: {
    //       required,
    //     },
    //   },

    },
    data: function() {
        return {
            Tag: -1,
            dsThaoTac: [],
            dsDBKH: [],
            activetab: 1,
            kieugoi_id: 0,
            labelFunctionTitle: '',
            modelDateTime: {
                dtpNgayLHD: new Date(),
                dtpNgayYC: new Date(),
                dtpNgayTN: new Date(),
                dtpTuNgay : new Date(),
                dtpDenNgay: new Date(),
                dateFormat: 'dd/MM/yyyy',
                enableDate: false
            },
            modelCbo: {
                cboGiayToKH: null,
                vcboGiayToKH: [],
                cboQuocTich: null,
                vcboQuocTich: [],
                cboNganhNgheLoc: null,
                vcboNganhNgheLoc: [],
                cboDanToc: null,
                vcboDanToc: [],
                cboLoaiKH2: null,
                vcboLoaiKH2: [],
                cboNganHangKH: null,
                vcboNganHangKH: [],
                cboDonViQL: null,
                vcboDonViQL: [],
                cboHTTT: null,
                vcboHTTT: [],
                cboNganHang: null,
                vcboNganHang: [],
                cboTT: null,
                vcboTT: [],
                cboDaiVT: null,
                vcboDaiVT: [],
                cboDoituongTB: null,
                vcboDoituongTB: [],
                cboDichVuVT: null,
                vcboDichVuVT: [],
                cboLoaihinhTB: null,
                vcboLoaihinhTB: [],
                cboKieuLD: null,
                vcboKieuLD: [],
                cboMucCuocTB: null,
                vcboMucCuocTB: [],
                cboGoiCuocDD: null,
                vcboGoiCuocDD: [],
                cboDTVinaPhone: null,
                vcboDTVinaPhone: [],
                cboGoiCuocAdsl: null,
                vcboGoiCuocAdsl: []
            },
            dataSend: {},
            pnlAdsl: false,
            pnlDD: false,
            dataHD: {},
            dtThueBao: [],
            dtList: [],
            selectedThueBao: null,
            lblNgaySN:'Ngày sinh',
            modelCkeckBox: {
                chkNganHangKH: false,
                disalbeGT: false,
                disalbeDN: false
            },
            modelInput: {
                txtPhanLoaiKH:'',
                txtMaGD: '',
                txtMaHD: '',
                txtTenKH: '',
                txtDiaChiKH: '',
                txtMaKH: '',
                txtSoGT: '',
                txtNgayHetHan: '',
                txtNgaySN: '',
                txtNgayCap: '',
                txtNoiCap: '',
                txtNguoiDD: '',
                txtChucDanhDD: '',
                txtDiaChiNDD: '',
                txtTelKH: '',
                txtFaxKH: '',
                txtHoKhau: '',
                txtNoiCapHK: '',
                txtNgayCapHK: '',
                txtMaSTKH: '',
                txtSoTKKH: '',
                txtDactrung: '',
                txtGhiChuKH: '',
                txtMaTT: '',
                txtMaTBDD: '',
                txtTenTT: '',
                txtMaNVTC: '',
                txtMaTuyen: '',
                txtDiaChiTT: '',
                txtSTK: '',

                txtMST: '',
                txtDiaChiCT: '',
                txtDiaChiBC: '',
                txtGhiChuTT: '',
                txtMaTB: '',
                txtTenTB: '',
                txtDiaChiTB: '',
                txtDiachiLD: '',

                txtGhiChuTB: '',
                lvDvgt:'',
                lvDvkhac:'',
                lvwThanhToan: '',
            },
            checkrdo : 'rdoNo',
            tsbtnChuyenDB: false,
            tsbtnHuyBo: false,
            isEnabledTsbtnChuyenDB: false,
            isEnabledTsbtnHuyBo: false,

            inputValue: {
                dichvuvt_id: -1,
                loaitb_id: -1,
                kieugoi_id: 0,
                tthd_id: 0,
                httt_id: -1,
                hdkh_id: -1,
                hdtt_id: -1,
                hdtb_id: -1,
                khachhang_id: -1,
                thanhtoan_id: -1,
                thuebao_id: -1,

                ma_tb: "",

                diachikh_id: -1,
                apkh_id: -1,
                phokh_id: -1,
                phuongkh_id: -1,
                quankh_id: -1,
                tinhkh_id: -1,
                sonhakh: -1,

                diachitt_id: -1,
                phott_id: -1,
                aptt_id: -1,
                phuongtt_id: -1,
                quantt_id: -1,
                tinhtt_id: -1,
                sonhatt: -1,

                phoct_id: -1,
                ngoct_id: -1,
                phuongct_id: -1,
                quanct_id: -1,
                tinhct_id: -1,
                sonhact: -1,

                diachitb_id: -1,
                photb_id: -1,
                aptb_id: -1,
                phuongtb_id: -1,
                quantb_id: -1,
                tinhtb_id: -1,
                kt_taobang: 0,
                sonhatb: -1,
                blocktb: '',
                tangtb: '',
                sophongtb: '',
                motadctb: '',

                /// <summary>
                /// TuấnNA thêm khai báo các biến của địa chỉ lắp đặt -- Ngày 29/07/2010
                /// </summary>
                diachild_id: -1,
                phold_id: -1,
                apld_id: -1,
                phuongld_id: -1,
                quanld_id: -1,
                tinhld_id: -1,
                sonhald: -1,
                blockld: '',
                tangld: '',
                sophongld: '',
                motadcld: '',

                tb_mytv: '',
                tb_mega: '',
                tb_cd: '',
                tb_dd: '',
                madoicap: '',


                /// TuấnNA thêm khai báo biến ma_gd -- Ngày 09/08/2010
                ma_gd: '',

                /// TuấnNA thêm khai báo biến dùng cho hàm hoàn thiện thuê bao MyTV -- Ngày 29/08/2010
                ten_kh: '',
                ngay_sn: '',
                gioitinh: '',
                checkgioitinh: '',
                diachi_kh: '',
                diachi_ld: '',
                so_gt: '',

                email: '',
                sofax: '',
                matb_Mytv: '',

                goi_id: 0,
                cd: '',
                gp: '',
                dd: '',
                mega: '',
                mytv: '',
                ftth: '',

                ///TuấnNA khai báo biến dùng chung -- Ngày 12/08/2010
                tuyenthu_id: 0,

                //TuấnNA thêm khai báo cho nút ngân sách nhà nước --- Ngày 12/11/2010
                DV_QHNS: '',
                MACHUONG: '',
                MN_KT: '',
                MA_NDKT: '',
                MN_NSNN: '',
                TIEN_DM: '',

                //TuấnNA thêm code khai báo các biến tiểu ngành
                TNC1_ID: 0,
                TNC2_ID: 0,
                TNC3_ID: 0,
                TNC4_ID: 0,
                nganhnghe_id: 0,
                //Lamnt: Them dataset để thực hiện đồng bộ thông tin thanh toán lên VinaPhone
                strerrorVinaphone: '',
                nhanvientc_id: 0,
                PHANLOAIKH_ID: -1
            },
            dsDK_DVGT: [],
            dsDK_DVK: [],
            dsCTM_TBI: [],
            kieukh_canhan: false, //true: ca nhan; false: doanh nghiep
        }
    },
    computed: {

    },
    methods: {
        tsbtnHuyBo_Click: function (){
          if (this.isEnabledTsbtnHuyBo)
            this.SetButton(0)
        },
      onEnterMaGD: async function () {
        try {
          this.loading(true);
          if (this.modelInput.txtMaGD != '') {
            let input = this.modelInput.txtMaGD.trim()
            let ds = null
            // let donvi_dl_id = 0
            console.log("donvi_dl_id:", this.$auth.getDonViDuLieuID())
            if (this.$auth.getDonViDuLieuID() == 0) {
              ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(input, Enum.LoaiHopDong.CHUYEN_QUYEN, 0, this.inputValue.tthd_id, 0, 0)
            } else {
              ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(input, Enum.LoaiHopDong.CHUYEN_QUYEN, this.$root.token.getDonViID(), this.inputValue.tthd_id, this.$root.token.getNhanVienID(), 0)
              // await this.HienThiTTHopDongKH(ds);//
            }
            if (ds && ds.length == 0) {
              this.$root.toastError("Không tìm thấy thông tin theo mã giao dịch:" + input)
              return
            }
            await this.HienThiTTHopDongKH(ds);//
          }
        } catch (error) {
          console.log(error)
        } finally {
          this.loading(false);
        }

      },
      LAY_DS_HOPDONG_THEO_MA_GD: async function (ma_gd, loaihd_id, donvi_id, tthd_id, nhanvien_id, donvi_dl_id) {
        let rs = await this.$root.context.post('web-ccdv/hoanthienhschuyenquyen/lay_ds_hopdong_theo_ma_gd', {
          ma_gd: ma_gd,
          loaihd_id: loaihd_id,
          donvi_id: donvi_id,
          tthd_id: tthd_id,
          nhanvien_id: nhanvien_id,
          donvi_dl_id: donvi_dl_id
        })
        //await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-hopdong-theomagd', data)
        // if (rs.data.length > 0) //mo rem 2 dong nay va dong tren sau
        return rs.data;
        // else
        //     return null
      },
        // dùng cho tra cứu
        LAY_DS_HOPDONG_THEO_MA_GD_1: async function (ma_gd, loaihd_id, tthd_id) {
          let rs = await this.$root.context.post('web-ccdv/hoanthienhschuyenquyen/lay_ds_hopdong_theo_ma_gd1', {
            ma_gd: ma_gd,
            loaihd_id: loaihd_id,
            tthd_id: tthd_id
          })
          //await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-hopdong-theomagd', data)
          // if (rs.data.length > 0) //mo rem 2 dong nay va dong tren sau
          return rs.data;
          // else
          //     return null
        },
        selectedRowChanged1: async function(data){},
        selectedRowChanged2: async function (data) {
          if (!!data && this.dtThueBao.length) {
            this.selectedThueBao = data;
            let hdtb_id = data[0]
            if (hdtb_id) {
              let dsFilter = this.dtThueBao.filter(i => i.HDTB_ID == hdtb_id)
              if (dsFilter.length)
                this.modelInput.txtMaTB = dsFilter[0].MA_TB
              try {
                this.loading(true)
                let rs = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_hopdong_tb_theo_hdtb_id', {
                  hdtb_id: hdtb_id
                })
                await this.HienThiTTHopDongTB(rs.data)
              }catch (e) {

              }finally {
                this.loading(false)
              }
            }
          }
        },
        ChuanHoaTen: function(ten)
        {
            if(ten!=null){
            let convertToArray = ten.toLowerCase().split(' ');
            let result = convertToArray.map(function(val) {
            return val.replace(val.charAt(0), val.charAt(0).toUpperCase());
            });
            return result.join(' ');
            }else
            return ''
        },
        //#region HienThiTTHopDongKH
        HienThiTTHopDongKH: async function(ds)
        {
            try
            {
                //if (ds.length > 0)
                if (ds != null && ds.length >0)
                {
                    ///TuấnNA thêm code kiểm tra hợp đồng chứa thuê bao di động -- Ngày 09/08/2010
                    ///--> Nếu hợp đồng chứa thuê bao di động thì không hiển thị nút Ghilại
                    ///--> Không cho phép sửa các thông tin trong hợp đồng chứa thuê bao di động
                    // if (new TCHopDongFacade2().KIEMTRA_HD_CO_THUEBAO_DD(ds[0].ma_gd) == 1)
                    // {
                    //     tsbtnGhiLai.Enabled = false;
                    // }

                    this.inputValue.hdkh_id = ds[0].HDKH_ID
                    this.modelInput.txtMaGD = ds[0].MA_GD;
                    ///Tuấn thêm code lấy thông tin cho biến ma_gd -- Ngày 09/08/2010
                    this.inputValue.ma_gd = ds[0].MA_GD;

                    this.modelInput.txtMaKH =  ds[0].MA_KH

                    if (ds[0].KHACHHANG_ID)
                        this.inputValue.khachhang_id =  ds[0].KHACHHANG_ID
                    else
                        this.inputValue.khachhang_id = 0;
                    this.modelInput.txtTenKH = this.ChuanHoaTen(ds[0].TEN_KH);

                    this.modelInput.txtDiaChiKH = this.ChuanHoaTen(ds[0].DIACHI_KH);

                    await this.HienThiTTDiaChi(1);
                    this.modelInput.txtTelKH = ds[0].SO_DT;
                    this.modelInput.txtFaxKH = ds[0].SO_FAX;

                    this.modelInput.txtHoKhau = ds[0].HOKHAU;
                    ///TuấnNA comment code số liên quan ( từ hd_khachhang-->hd_thanhtoan)--Ngày 07/08/2010
                    this.modelInput.txtNoiCapHK = ds[0].NOICAP_HK;

                    ///TuấnNA thêm code lấy thông tin cho hàm hoàn thiện thuê bao MyTV --- Ngày 29/08/2010
                    this.inputValue.ten_kh = ds[0].TEN_KH
                    this.inputValue.diachi_kh = ds[0].DIACHI_KH
                    this.inputValue.gioitinh = ds[0].GIOITINH
                    this.inputValue.so_gt = ds[0].SO_GT
                    this.inputValue.email = ds[0].EMAIL
                    this.inputValue.sofax = ds[0].SO_FAX
                    this.modelInput.txtMaSTKH = ds[0].MST



                    if (ds[0].NGAY_HK != null)
                    {
                        this.modelInput.txtNgayCapHK = DateTimeLib.toDateDisplay(ds[0].NGAY_HK);//Convert.ToDateTime(ds[0].ngay_hk).ToString("dd/MM/yyyy");
                    }
                    else
                    {
                        this.modelInput.txtNgayCapHK = "";
                    }

                    if (ds[0].NGAY_SN != null)
                    {
                        this.modelInput.txtNgaySN = DateTimeLib.toDateDisplay(ds[0].NGAY_SN);//Convert.ToDateTime(ds[0].ngay_sn).ToString("dd/MM/yyyy");
                        ///TuấnNA thêm code lấy thông tin cho hàm hoàn thiện thuê bao MyTV --- Ngày 29/08/2010
                        this.inputValue.ngay_sn = DateTimeLib.toDateDisplay(ds[0].NGAY_SN);//Convert.ToDateTime(ds[0].ngay_sn).ToString("dd/MM/yyyy");
                    }
                    else
                    {
                        this.modelInput.txtNgaySN = "";
                        this.inputValue.ngay_sn = "";///---> MyTV
                    }
                    this.modelDateTime.dtpNgayLHD = DateTimeLib.toDateDisplay(ds[0].NGAYLAP_HD);//Convert.ToDateTime(ds[0].ngaylap_hd);
                    this.modelInput.txtSoGT = ds[0].SO_GT
                    if (ds[0].NGAYCAP != null)
                    {
                        this.modelInput.txtNgayCap = DateTimeLib.toDateDisplay(ds[0].NGAYCAP);//Convert.ToDateTime(ds[0].ngaycap).ToString("dd/MM/yyyy");
                    }
                    else
                    {
                        this.modelInput.txtNgayCap = "";
                    }

                    this.modelCbo.cboGiayToKH = ds[0].LOAIGT_ID
                    this.modelInput.txtNoiCap = ds[0].NOICAP

                    this.modelInput.txtMaHD = ds[0].MA_HD
                    if (ds[0].NGAY_YC != null)
                    {
                        this.modelDateTime.dtpNgayYC = DateTimeLib.toDateDisplay(ds[0].NGAY_YC);//Convert.ToDateTime(ds[0].ngay_yc);
                    }
                    else
                    {
                        this.modelDateTime.dtpNgayYC = DateTimeLib.toDateDisplay(new Date())
                    }

                    this.modelInput.txtNguoiDD = this.ChuanHoaTen(ds[0].NGUOI_DD);
                    this.modelInput.txtChucDanhDD = this.ChuanHoaTen(ds[0].CHUCDANH_DD);

                    this.modelInput.txtGhiChuKH = ds[0].GHICHU

                    if (ds[0].loaikh_id != 0) {
                      // this.modelCbo.cboLoaiKH2 = ds[0].LOAIKH_ID;
                      let grid = this.$refs['cboLoaiKH2'].$children[0].grid
                      let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id == ds[0].LOAIKH_ID)
                      if (idx > -1) {
                        this.$nextTick(() => {
                          grid.selectRow(idx)
                        })
                      } else {
                        this.$nextTick(() => {
                          grid.selectRow(0)
                        })
                      }
                    }

                    try
                    {
                        let rs = await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/map_id_phanloaikh_id_neo', {
                          hdkh_id: this.inputValue.hdkh_id
                        })
                        let strPLKH = rs.data;
                        if (strPLKH || strPLKH == 0)
                        {
                            this.inputValue.PHANLOAIKH_ID = strPLKH
                        }
                    }
                    catch (Exception)
                    {
                    }
                    this.updateOption(ds[0].GIOITINH)

                    if (ds[0].QUOCTICH_ID != null)
                    {
                        this.modelCbo.cboQuocTich = ds[0].QUOCTICH_ID

                    }
                    else
                         this.modelCbo.cboQuocTich = 0;
                    if (ds[0].NGANHNGHE_ID != null)
                    {
                        this.modelCbo.cboNganhNgheLoc = ds[0].NGANHNGHE_ID
                        this.inputValue.nganhnghe_id = ds[0].NGANHNGHE_ID
                    }
                    else
                        this.modelCbo.cboNganhNgheLoc =  this.modelCbo.vcboNganhNgheLoc.length > 0 ? this.modelCbo.vcboNganhNgheLoc[0].nganhnghe_id : null
                    if (ds[0].DANTOC_ID != null)
                    {
                        this.modelCbo.cboDanToc = ds[0].DANTOC_ID
                    }
                    else
                        this.modelCbo.cboDanToc = 0;

                    this.modelInput.txtDiaChiNDD = this.ChuanHoaTen(ds[0].DIACHI_NDD);

                    if (ds[0].NGAYHETHAN_GT != null)
                    {
                        this.modelInput.txtNgayHetHan = DateTimeLib.toDateDisplay(ds[0].NGAYHETHAN_GT);//Convert.ToDateTime(ds[0].ngayhethan_gt).ToString("dd/MM/yyyy");
                    }
                    else
                    {
                        this.modelInput.txtNgayHetHan = "";
                    }
                    this.modelInput.txtDactrung = ds[0].DACTRUNG
                    this.modelInput.txtSoTKKH = ds[0].STK
                    if (ds[0].NGANHANG_ID != 0 && ds[0].NGANHANG_ID != null)
                    {
                        this.modelCkeckBox.chkNganHangKH = true;
                        this.modelCbo.cboNganHangKH = ds[0].NGANHANG_ID
                    }
                    //TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
                    if (ds[0].TNC1_ID != null)
                    {
                        this.inputValue.TNC1_ID = ds[0].TNC1_ID;
                    }
                    if (ds[0].TNC2_ID != null)
                    {
                        this.inputValue.TNC2_ID = ds[0].TNC2_ID
                    }
                    if (ds[0].TNC3_ID != null)
                    {
                        this.inputValue.TNC3_ID = ds[0].TNC3_ID
                    }
                    if (ds[0].TNC4_ID != null)
                    {
                        this.inputValue.TNC4_ID = ds[0].TNC4_ID
                    }
                    //

                    await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id);

                    await this.HienThiTTHopDongTT(this.inputValue.hdkh_id);
                  let res = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_theo_hdkh_tthd', {
                    hdkh_id: this.inputValue.hdkh_id,
                    tthd_id: this.inputValue.tthd_id,
                  })
                  await this.HienThiTTHopDongTB(res.data);

                  await this.HienThiDanhSacHDTT();
                  // let rs = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_theo_hdkh_tthd', {
                  //   hdkh_id: this.inputValue.hdkh_id,
                  //   tthd_id: this.inputValue.tthd_id,
                  // })
                  this.dtThueBao = res.data
                  this.SetButton(3);
                }
                else
                {
                    this.SetButton(0);
                }
            }
            catch (exp)
            {
              console.log(exp)
                //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
            }
        },
        HienThiTTHopDongTT: async function(hdkh_id){
            let res = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay-ds-hdtt-theohdid', {
              hdkh_id: hdkh_id
            })
            try
            {
                if (res.data.length > 0)
                {
                    let ds = res.data
                    this.inputValue.hdtt_id = ds[0].HDTT_ID;
                    this.modelInput.txtMaTT = ds[0].MA_TT;
                    if (ds[0].THANHTOAN_ID != null || ds[0].THANHTOAN_ID != 0)
                        this.inputValue.thanhtoan_id = ds[0].THANHTOAN_ID;
                    else
                        this.inputValue.thanhtoan_id = 0;
                    this.modelInput.txtTenTT = this.ChuanHoaTen(ds[0].TEN_TT);
                    this.modelInput.txtDiaChiTT = this.ChuanHoaTen(ds[0].DIACHI_TT);
                    this.modelInput.txtDiaChiBC = this.ChuanHoaTen(ds[0].DIACHI_BC);
                    await this.HienThiTTDiaChi(2);
                    this.modelInput.txtMST = ds[0].MST;
                    this.modelInput.txtSTK = ds[0].STK;
                    if (ds[0].NGANHANG_ID != null)
                        this.modelCbo.cboNganHang = ds[0].NGANHANG_ID
                    this.modelCbo.cboHTTT = ds[0].HTTT_ID;
                    this.modelCbo.cboDonViQL = ds[0].DONVI_ID;
                    await this.HT_DonVi_loaidv_Combobox_TT(this.modelCbo.cboDonViQL)
                    this.modelInput.txtMaTBDD = ds[0].MATB_DD

                    ///TuấnNA thêm code hiển thị một số thông tin thanh toán -- Ngày 08/08/2010
                    this.modelInput.txtGhiChuTT = ds[0].GHICHU;
                    this.modelInput.txtDiaChiCT = this.ChuanHoaTen(ds[0].DIACHI_CT);


                    //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
                    this.inputValue.DV_QHNS = ds[0].DV_QHNS;
                    this.inputValue.MACHUONG = ds[0].MACHUONG;
                    this.inputValue.MN_KT = ds[0].MN_KT;
                    this.inputValue.MA_NDKT = ds[0].MA_NDKT;
                    this.inputValue.MN_NSNN = ds[0].MN_NSNN;
                    if (ds[0].TIEN_DM != null)
                        this.inputValue.TIEN_DM =ds[0].TIEN_DM;
                    else
                        this.inputValue.TIEN_DM = 0;
                    //

                    ///tuấnNA thêm code lấy thông tin tuyến thu trong bảng HDTT --- Ngày 31/08/2010
                    if (ds[0].NHANVIEN_ID != null)
                    {
                        this.inputValue.nhanvientc_id = ds[0].NHANVIEN_ID;
                        // let rs = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
                        //     id_neo: 'ma_nv',
                        //     table: 'admin.nhanvien',
                        //     dk: 'where nhanvien_id = ' + ds[0].NHANVIEN_ID
                        // })
                        let rs1 = await this.MAP_ID_MANV_NEO(ds[0].NHANVIEN_ID)
                        this.modelInput.txtMaNVTC =  rs1;
                    }
                    else
                    {
                        this.inputValue.nhanvientc_id = 0;
                        this.modelInput.txtMaNVTC = "";
                    }
                    if (ds[0].TUYENTHU_ID != null)
                    {
                        this.inputValue.tuyenthu_id = ds[0].TUYENTHU_ID;
                        let rs2 = await this.MAP_ID_TUYENTHU_ID_NEO(ds[0].TUYENTHU_ID)
                        this.modelInput.txtMaTuyen = rs2;
                    }
                    else
                    {
                        this.inputValue.tuyenthu_id = 0;
                        this.modelInput.txtMaTuyen = "";
                    }
                }
            }
            catch (exp)
            {
                this.$root.$toast.error(' '+exp)
                //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
            }
        },
        HienThiTTHopDongTB: async function(ds){
            try
            {
                if (ds.length > 0)
                {
                    // let ds = res.data
                    this.inputValue.hdtb_id = ds[0].HDTB_ID
                    if (ds[0].THUEBAO_ID != null)
                        this.inputValue.thuebao_id = ds[0].THUEBAO_ID
                    else
                        this.inputValue.thuebao_id = 0;
                    this.modelInput.txtMaTB = ds[0].MA_TB
                    this.inputValue.ma_tb = ds[0].MA_TB
                    this.modelInput.txtTenTB = this.ChuanHoaTen(ds[0].TEN_TB);
                    this.modelInput.txtDiaChiTB = this.ChuanHoaTen(ds[0].DIACHI_TB);
                    await this.HienThiTTDiaChi(3);
                    let temp = await this.TTCT_LoaiHinh_TB(ds[0].LOAITB_ID)
                    this.inputValue.dichvuvt_id = temp[0].dichvuvt_id;
                    this.modelCbo.cboDichVuVT =  temp[0].dichvuvt_id;
                    this.inputValue.loaitb_id = ds[0].LOAITB_ID
                    this.modelCbo.cboLoaihinhTB = ds[0].LOAITB_ID;
                    this.modelCbo.cboKieuLD = ds[0].KIEULD_ID;
                    this.modelCbo.cboDoituongTB = ds[0].DOITUONG_ID;
                    this.modelCbo.cboTT = ds[0].HDTT_ID;

                    this.modelCbo.cboDaiVT = ds[0].DONVI_ID;
                    if (ds[0].MUCUOCTB_ID != null)
                    {
                        await this.HT_MucCuocTB_Combobox(ds[0].MUCUOCTB_ID);
                    }

                    this.dsDK_DVGT = await this.LAY_DS_DANGKY_DVGT(this.inputValue.hdtb_id)
                    await this.HT_DichVuGT();
                    this.dsDK_DVK = await this.LAY_DS_DANGKY_DVK(this.inputValue.hdtb_id, 1)
                    await this.HT_DichVuKhac();
                    this.dsCTM_TBI = await this.LAY_DS_MUA_TBI(this.inputValue.hdtb_id)
                    await this.HienThiTTMoRong(ds[0].MA_TB, this.inputValue.hdtb_id, temp[0].dichvuvt_id);
                    if (ds[0].TTHD_ID == Enum.TrangThaiHD.DA_THI_CONG_XONG || ds[0].TTHD_ID == Enum.TrangThaiHD.HTHS_BANCHEO)
                        this.isEnabledTsbtnChuyenDB = true;
                    else
                        this.isEnabledTsbtnChuyenDB = false;
                    ///TuấnNA thêm code lấy thông tin thuê bao -- Ngày 08/08/2010
                    this.modelInput.txtGhiChuTB = ds[0].GHICHU;
                    this.modelInput.txtDiachiLD = this.ChuanHoaTen(ds[0].DIACHI_LD);
                    ///TuấnNA thêm code lấy thông tin thuê bao để hoàn thiện lên MyTV --- Ngày 29/08/2010
                    this.inputValue.matb_Mytv = ds[0].MA_TB
                    this.inputValue.diachi_ld = ds[0].DIACHI_LD

                }
            }
            catch (exp)
            {
              console.log(exp)
                 this.$root.$toast.error(' '+exp)
                //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
            } finally {
            }
        },
        TTCT_LoaiHinh_TB: async function(loaitb_id){
            // let data = {
            //     P_LOAITB_ID: loaitb_id,
            //     PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.LAY_LOAIHINH_TB_THEO_LOAITB_IB'
            // }
            let ds = await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/ttct-loaihinh-tb', {loaitb_id:loaitb_id})
            return ds.data
        },
        //#region Hiển thị dịch vụ GT, khác
        HT_DichVuGT: async function()
        {
            let str = ''
            this.modelInput.lvDvgt = ''
            for (let i = 0; i < this.dsDK_DVGT.length; i++)
            {
                let ds = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/ttct_dichvugt', {
                  dichvugt_id: this.dsDK_DVGT[i].DICHVUGT_ID
                })
                str = ds.data[0].TEN_DVGT
                if (this.dsDK_DVGT[i].TTTH == "1")
                    str = str + " (Đã CĐ)" +"\n";
                else
                    str = str + " (Chưa CĐ)"+"\n";
            }

            this.modelInput.lvDvgt = str
        },
        HT_DichVuKhac: async function()
        {
            let str  = ''
            this.modelInput.lvDvkhac = ''
            for (let i = 0; i < this.dsDK_DVK.length; i++)
            {
                let ds = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/ttct_dichvukhac', {
                  dichvugt_id: this.dsDK_DVK[i].DVKHAC_ID
                })
                 str = ds.data[0].TEN_DVKHAC + '\n';
            }
            this.modelInput.lvDvkhac = str

        },
        //#endregion
        LAY_DS_DANGKY_DVGT: async function(hdtb_id){
            let ds = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_dangky_dvgt', {
              hdtb_id: hdtb_id
            })
            return ds.data
        },
        LAY_DS_DANGKY_DVK: async function(hdtb_id, type){
            let ds = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_dangky_dvk', {
              hd_id: hdtb_id,
              loaiid: type,
            })
            return ds.data
        },
        LAY_DS_MUA_TBI: async function(hdtb_id){
            let ds = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_mua_tbi', {
              hdtb_id: hdtb_id
            })
            return ds.data
        },
        HienThiDanhSacHDTT: async function(){
            this.dtList = []
            let res = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay-ds-hdtt-theohdid', {
              hdkh_id: this.inputValue.hdkh_id
            })
            this.dtList = res.data
        },
        // //Khong dung toi ham nay
        // HienThiDanhSacHDTB: async function(ds){
        // },
        HienThiTTDiaChi: async function(kieu){
            let res = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay-ds-diachi-theohdid', {
              hdkh_id: this.inputValue.hdkh_id
            })
            if (res.data.length > 0)
            {
                let ds = res.data                
                if (kieu == 1)
                {

                    this.inputValue.diachikh_id = ds[0].diachi_id;
                    this.inputValue.tinhkh_id = ds[0].tinh_id
                    this.inputValue.quankh_id = ds[0].quan_id
                    this.inputValue.phuongkh_id = ds[0].phuong_id
                    if (ds[0].pho_id != null)
                        this.inputValue.phokh_id = ds[0].pho_id
                    else
                        this.inputValue.phokh_id = 0;
                    ///TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
                    ///// HoangPKN : lấy thông tin ngõ : 16/05/2012
                    if (ds[0].ap_id != null)
                        this.inputValue.apkh_id = ds[0].ap_id
                    else
                        this.inputValue.apkh_id = 0;
                    this.inputValue.sonhakh = this.ChuanHoaTen(ds[0].sonha);
                }

                if (kieu == 2)
                {
                    this.inputValue.diachitt_id = ds[0].diachi_id
                    this.inputValue.tinhtt_id = ds[0].tinh_id
                    this.inputValue.quantt_id = ds[0].quan_id
                    this.inputValue.phuongtt_id = ds[0].phuong_id
                    if (ds[0].pho_id != null)
                        this.inputValue.phott_id = ds[0].pho_id
                    else
                       this.inputValue.phott_id = 0;
                    ///TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
                    /// Hoang pkn : lấy thêm ngõ : 16/05/2012
                    if (ds[0].ap_id != null)
                        this.inputValue.aptt_id = ds[0].ap_id
                    else
                        this.inputValue.aptt_id = 0;
                    this.inputValue.sonhatt = this.ChuanHoaTen(ds[0].sonha)
                }

                if (kieu == 3)
                {
                    this.inputValue.diachitb_id = ds[0].diachi_id
                    this.inputValue.tinhtb_id = ds[0].tinh_id
                    this.inputValue.quantb_id = ds[0].quan_id
                    this.inputValue.phuongtb_id = ds[0].phuong_id
                    if (ds[0].pho_id != null)
                        this.inputValue.photb_id = ds[0].pho_id
                    else
                        this.inputValue.photb_id = 0;
                    ///TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
                    /// hoangpkn : 16/05/2012 : lấy thêm ngõ
                    if (ds[0].ap_id != null)
                        this.inputValue.aptb_id = ds[0].ap_id
                    else
                        this.inputValue.aptb_id = 0;
                    this.inputValue.sonhatb = this.ChuanHoaTen(ds[0].sonha);
                    // if (data.Table.Columns.Contains("block"))
                    //     this.inputValue.blocktb = data["block"].ToString();
                    // if (data.Table.Columns.Contains("tang"))
                    //     this.inputValue.tangtb = data["tang"].ToString();
                    // if (data.Table.Columns.Contains("phong"))
                    //     this.inputValue.sophongtb = data["phong"].ToString();
                    // if (data.Table.Columns.Contains("mota"))
                    //     this.inputValue.motadctb = data["mota"].ToString();

                    /// Tuấn thêm code lấy địa chỉ lắp đặt cho thuê bao -- Ngày 29/07/2010
                    if (ds[0].diachild_id != null)
                        this.inputValue.diachild_id = ds[0].diachild_id
                    else
                        this.inputValue.diachild_id = 0;

                    if (ds[0].tinhld_id != null)
                        this.inputValue.tinhld_id = ds[0].tinhld_id
                    else
                        this.inputValue.tinhld_id = 0;

                    if (ds[0].quanld_id != null)
                        this.inputValue.quanld_id = ds[0].quanld_id
                    else
                        this.inputValue.quanld_id = 0;

                    if (ds[0].phuongld_id != null)
                        this.inputValue.phuongld_id = ds[0].phuongld_id
                    else
                        this.inputValue.phuongld_id = 0;

                    if (ds[0].phold_id != null)
                        this.inputValue.phold_id = ds[0].phold_id
                    else
                        this.inputValue.phold_id = 0;

                    if (ds[0].apld_id != null )
                        this.inputValue.apld_id = ds[0].apld_id
                    else
                        this.inputValue.apld_id = 0;
                    this.inputValue.sonhald = this.ChuanHoaTen(ds[0].sonha_ld);
                    // if (data.Table.Columns.Contains("block_ld"))
                    //     this.inputValue.blockld = data["block_ld"].ToString();
                    // if (data.Table.Columns.Contains("tang_ld"))
                    //     this.inputValue.tangld = data["tang_ld"].ToString();
                    // if (data.Table.Columns.Contains("phong_ld"))
                    //     this.inputValue.sophongld = data["phong_ld"].ToString();
                    // if (data.Table.Columns.Contains("mota_ld"))
                    //     this.inputValue.motadcld = data["mota_ld"].ToString();
                }
            }
        },
        LAY_DS_HDTB_CD_THEO_HDTB_ID: async function (hdtb_id){
            // let data = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     in_hdtb_id: hdtb_id,
            //     PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_CD_THEO_HDTB_ID'
            // }
            return await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_cd_theo_hdtb_id', {
              hdtb_id: hdtb_id
            })
        },
        LAY_DS_HDTB_DD_THEO_HDTB_ID: async function (hdtb_id){
            // let data = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     in_hdtb_id: hdtb_id,
            //     PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_DD_THEO_HDTB_ID'
            // }
            return await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_dd_theo_hdtb_id', {
              hdtb_id: hdtb_id
            })

        },
        LAY_DS_HDTB_ADSL_THEO_HDTB_ID: async function (hdtb_id){
            // let data = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     in_hdtb_id: hdtb_id,
            //     PROCEDURE_NAME: 'CSS.TRACUU_HOPDONG.LAY_DS_HDTB_ADSL_THEO_HDTB_ID'
            // }
            return await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_adsl_theo_hdtb_id', {
              hdtb_id: hdtb_id
            })
        },
        lay_ds_hdtb_GP_theo_hdtb_id: async function (hdtb_id){
          return await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_gp_theo_hdtb_id', {
            hdtb_id: hdtb_id
          })
        },
        HienThiTTMoRong: async function(ma_tb, hdtb_id, dichvuvt_id)
        {
            let ds = []
            switch (dichvuvt_id)
            {
                case Enum.DichVuVienThong.CO_DINH:
                    ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.DI_DONG:
                    ds = await this.LAY_DS_HDTB_DD_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.ADSL:
                case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
                case Enum.DichVuVienThong.DICHVU_CNTT:
                case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
                case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
                    ds = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
                    break;
                case Enum.DichVuVienThong.GPHONE:
                    ds = await this.lay_ds_hdtb_GP_theo_hdtb_id(hdtb_id);
                    break;
                default:
                    ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
                    break;
            }

            if (ds.data.length > 0)
            {
                switch (dichvuvt_id)
                {
                    case Enum.DichVuVienThong.CO_DINH:
                        this.modelInput.txtMaTB = ma_tb;
                        break;
                    case Enum.DichVuVienThong.DI_DONG:
                        this.modelInput.txtMaTB = ma_tb;
                        ///TuấnNA thêm code hiển thị các thông tin về di động
                        ///--Ngày 09/08/2010
                        this.modelCbo.cboGoiCuocDD = ds.data[0].GOICUOC_ID;
                        this.modelCbo.cboDTVinaPhone =  ds.data[0].DOITUONG_ID_NEO;
                        break;
                    case Enum.DichVuVienThong.ADSL:
                    case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
                    case Enum.DichVuVienThong.DICHVU_CNTT:
                    case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
                    case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
                        this.modelInput.txtMaTB = ma_tb;
                        this.modelInput.txtEmailAdsl = ds.data[0].EMAIL;
                        await this.HT_Tocdo_Adsl_Combobox(ds.data[0].TOCDO_ID)
                        this.modelCbo.cboGoiCuocAdsl = ds.data[0].TOCDO_ID;
                        this.modelCbo.cboMucCuoc = ds.data[0].MUCCUOC_ID
                        break;
                    case Enum.DichVuVienThong.GPHONE:
                        break;
                    default:
                        this.modelInput.txtMaTB = ma_tb;
                        break;
                }
            }
        },
        //#endregion
        //#region
        KiemTraDL_ChuyenVaoDB: function()
        {
            if (this.modelInput.txtMaKH == "")
            {
                this.$root.$toast.error('Hãy sinh mã khách hàng !')
                //txtMaKH.Focus();
                 this.$refs['ma-kh'].focus()
                return false;
            }

            if (this.modelInput.txtMaTT == "")
            {
                this.$root.$toast.error('Hãy sinh mã thanh toán !')
                //txtMaTT.Focus();
                this.$refs['ma-tt'].focus()
                return false;
            }
            return true;
        },
        CAPNHAT_STATUS_HDTB: async function(hdtb_id, status){
          let rs = await this.$root.context.post(`web-ccdv/hoanthienhschuyenquyen/capnhat_status_hdtb?hdtb_id=${hdtb_id}&status=${status}`)
          return rs.data[0].KETQUA
        },
      HoanThienMyTV: async function () {
          try {
            // CheckDataFacade checkdata = new CheckDataFacade();
            // VascService = new VASCService(tt_nd.user_vasc, tt_nd.pass_vasc, tt_nd.diemgiaodich_vasc);
            let Result = 0;
            let Name = this.ChuanHoa_XML(this.inputValue.ten_kh);//tên KH
            let Birthday = this.inputValue.ngay_sn; // ngày sinh KH dt.Rows[k]["ngay_sn"]).ToString("dd/MM/yyyy")
            let Sex = "";//Giới tính : Nam --> 0, Nữ --> 1; không giới tính --> 0
            if (this.inputValue.gioitinh != "1") {
              Sex = "0";
            } else {
              Sex = "1";
            }
            let FirstAdd = this.ChuanHoa_XML(this.inputValue.diachi_kh); //địa chỉ KH
            let SecondAdd = this.ChuanHoa_XML(this.inputValue.diachi_ld);//Dịa chỉ Lđặt
            let IDNo = this.ChuanHoa_XML(this.inputValue.so_gt); //Số GT khách hàng
            // nếu điện thoại lhệ> 8 -->tellno = "" & cellno = điện thoại liên hệ  và ngược lại
            let TelNo = "";
            let CellNo = "";
            if (this.modelInput.txtTelKH.length > 8)///Di dong
            {
              TelNo = "";
              CellNo = this.modelInput.txtTelKH;
            } else {
              TelNo = this.modelInput.txtTelKH;
              CellNo = "";
            }

            let Email = this.ChuanHoa_XML(this.inputValue.email);//Email KH
            let YM = "";// trống
            let Website = "";//trống
            let FaxNo = this.inputValue.sofax;//số Fax KH
            let IPTVAccount = this.ChuanHoa_XML(this.inputValue.matb_Mytv);//Mã TB dsHDTB
            let AreaCode = this.$root.context.user.getProperty('tentat_vasc')
            //string IPTVPW = "";// default lsn123 public
            Result = await vasc.ChangeSubscriber(Name, Birthday, Sex, FirstAdd, SecondAdd, IDNo, TelNo,
              CellNo, AreaCode, Email, YM, Website, FaxNo, IPTVAccount);
            if (Result.errorCode == 0 && Result.data.result == 0) {
              this.$root.toastSuccess('Thực hiện hoàn thiện thuê bao MyTV trên VASC thành công!')
              //Cập nhật Status ---> 5
              await this.CAPNHAT_STATUS_HDTB(this.inputValue.hdtb_id, Enum.TRANGTHAI_DONGBO.DONGBO_CM);
            }
          }catch (e) {
            this.$root.toastError(e.message)
            console.log(e)
          }
      },
        CO_THUEBAO_DIDONG: async function(thanhtoan_id){
            let kq = false;
            try {
                let rs = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_thongtin_tbao', {
                  thanhtoan_id: thanhtoan_id
                })
                if (rs.data.length > 0)
                    kq = true;
                else
                    kq = false;
            } catch (error) {
                kq = false;
            }
            return kq;
        },
      LAY_DS_DBKH_THEO_KHID: async function (khachhang_id) {
        let rs = await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_dbkh_theo_khid", {
          khachhang_id: khachhang_id
        })
        return rs.data
      },
        MAP_ID_QUAN_ID_NEO: async function () {
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_id_quan_id_neo", {
            quan_id: this.inputValue.quankh_id
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].quan_id_neo
          } else return null
        },
        MAP_ID_MANV_NEO: async function (nhanvienid) {
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_id_manv_neo", {
            nhanvien_id: nhanvienid
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].ma_nv
          } else return null
        },
        MAP_ID_TUYENTHU_ID_NEO: async function(tuyenthuid){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_tuyenthu_id_neo", {
            tuyenthu_id: tuyenthuid
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].ma_tuyen
          } else return null
        },
        MAP_ID_PHUONG_ID_NEO: async function(){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_id_phuong_id_neo", {
            quan_id: this.inputValue.quankh_id,
            phuong_id: this.inputValue.phuongkh_id,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].phuong_id_neo
          } else return null
        },
        MAP_ID_PHO_ID_NEO: async function(){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_id_pho_id_neo", {
            phuong_id: this.inputValue.phuongkh_id,
            pho_id: this.inputValue.phokh_id,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].pho_id_neo
          } else return null
        },
        MAP_ID_USER_NEO: async function(){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_id_user_neo", {
            nguoidung_id: this.$root.token.getNguoiDungID(),
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].user_neo
          } else return null
        },
        MAP_MA_TT_NEO: async function(ma_tt){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_ma_tt_neo", {
            ma_tt: ma_tt,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].ma_tt_neo
          } else return null
        },
        MAP_MANV_VNP: async function(ma_nv){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_manv_vnp", {
            ma_nv: ma_nv,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].manv_vnp
          } else return null
        },
        MAP_LOAIGT_ID_NEO: async function(input){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_loaigt_id_neo", {
            loaigt_id: input,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].loaigt_id_neo
          } else return null
        },
        MAP_LOAIKH_ID_NEO: async function(input){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_loaikh_id_neo", {
            loaikh_id: input,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].loaikh_id_neo
          } else return null
        },
        MAP_HTTT_ID_NEO: async function(input){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_httt_id_neo", {
            httt_id: input,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].httt_id_neo
          } else return null
        },
        MAP_DONVI_ID_NEO: async function(input){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_donvi_id_neo", {
            donvi_id: input,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].donvi_id_neo
          } else return null
        },
        MAP_MABC_ID_NEO: async function(input){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_mabc_id_neo", {
            donvi_id: input,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].mabc_id_neo
          } else return null
        },
        MAP_TNC2_ID_NEO: async function(input){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/map_tnc2_id_neo", {
            tnc2_id: input,
          })
          if (rs && rs.data && rs.data.length > 0) {
            return rs.data[0].tnc2_id_neo
          } else return null
        },
        // ts_hoanthien_hd_cn_kh: async function(psma_tuyen, psdangkytv, pshoct_id, psuserid, pstaikhoan,
        //                                         pskh_lon, psghichu, pshuongtt_id1, psma_qhns, psnoicapgt1,
        //                                         pstennv, psnganhang_id, pskyten, psuantt_id, psdangkydb,
        //                                         psloaikh, psdonviql0,psonhatt_id, psnganhnghe, pssogt1,
        //                                         pshott_id, psuutien, pslydo_anh, psngaycapgt, psdiachitt,
        //                                         psuanct_id, tentat_ccbs, psgioitinh, psma_kh0, psmsthue,
        //                                         pssogiayto, psngaycapgt1, psdiachict, pssodaidien, psdienthoailh,
        //                                         psmacq, pstentt, psdonviql, psnguoidaidien, pshuongtt_id, psonhact_id,
        //                                         psemail, psmabc, psnoicapgt, psdiadiemtt, psten_tuyen, psma_kh,
        //                                         psloaigtid, psnguoi_gt, psloaigtid1, psngaysinh, pskhrr,
        //                                         pscoquan,pschuyenkhoan, psmanv)
        // {
        //     let result = ccbsService.ts_hoanthien_hd_cn_kh(psma_tuyen, psdangkytv, pshoct_id, psuserid, pstaikhoan,
        //                                         pskh_lon, psghichu, pshuongtt_id1, psma_qhns, psnoicapgt1,
        //                                         pstennv, psnganhang_id, pskyten, psuantt_id, psdangkydb,
        //                                         psloaikh, psdonviql0,psonhatt_id, psnganhnghe, pssogt1,
        //                                         pshott_id, psuutien, pslydo_anh, psngaycapgt, psdiachitt,
        //                                         psuanct_id, tentat_ccbs, psgioitinh, psma_kh0, psmsthue,
        //                                         pssogiayto, psngaycapgt1, psdiachict, pssodaidien, psdienthoailh,
        //                                         psmacq, pstentt, psdonviql, psnguoidaidien, pshuongtt_id, psonhact_id,
        //                                         psemail, psmabc, psnoicapgt, psdiadiemtt, psten_tuyen, psma_kh,
        //                                         psloaigtid, psnguoi_gt, psloaigtid1, psngaysinh, pskhrr,
        //                                         pscoquan,pschuyenkhoan, psmanv);
        //     return result.data;
        // },
        LAY_TT_NHANVIEN_TC: async function(input){
          let rs = await this.$root.context.get("/web-ccdv/hoanthienhschuyenquyen/lay_tt_nhanvien_tc", {
            manv_tc: input,
          })
          return rs.data
        },
      UPDATE_HDKH_VINAPHONE: async function () {
        console.log("UPDATE_HDKH_VINAPHONE")
        let status = false;
        try {
          this.inputValue.strerrorVinaphone = "";
          //XmlNode result;

          let rs1 = await this.MAP_ID_USER_NEO()
          let PSUSERID = this.ChuanHoa_XML(rs1 || rs1 == 0 ? rs1.toString() : "-1");
          if (PSUSERID == "-1") {
            this.$root.$toast.error('Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }

          let PSMAKHCU = (this.modelInput.txtMaTT+'').trim()
          let rs = await this.MAP_MA_TT_NEO(PSMAKHCU)
          let PSMA_KH = rs || rs == 0 ? rs : "-1"

          if (PSMA_KH == "-1") {
            PSMA_KH = (this.modelInput.txtMaTT+'').trim()
          }
          //Lấy PSMACQ
          let PSMACQ = "";

          //TuanNA: zem code cũ -- ngày 02.12.2011
          let PSCOQUAN = this.ChuanHoa_XML((this.modelInput.txtTenKH+'').trim());
          //Tên thanh toán
          let PSTENTT = this.ChuanHoa_XML((this.modelInput.txtTenTT+'').trim());//(idsHDTT.Tables[0].Rows[i]["ten_tt"].ToString());
          let PSSODAIDIEN = (this.modelInput.txtMaTBDD+'').trim();// idsHDTT.Tables[0].Rows[i]["matb_dd"].ToString();
          let PSNGUOIDAIDIEN = this.ChuanHoa_XML((this.dsDBKH[0].NGUOI_DD+'').trim());//danh bạ
          let PSDIENTHOAILH = this.ChuanHoa_XML((this.dsDBKH[0].SO_DT+'').trim());//danh bạ
          let PSPHAI = this.dsDBKH[0].GIOITINH;//Lấy dữ liệu từ danh bạ
          let PSEMAIL = this.ChuanHoa_XML(this.dsDBKH[0].EMAIL);//danh bạ

          let PSNGAYSINH = "";
          // if (this.modelInput.txtNgaySN != "  /  /")
          if (this.modelInput.txtNgaySN != null) {
            //TuanNA: zem code cũ -- ngày 02.12.2011
            PSNGAYSINH = this.modelInput.txtNgaySN;//Convert.ToDateTime(txtNgaySN.Text).ToString("dd/MM/yyyy");//danh bạ
          }
          //TuanNA: zem code cũ -- ngày 02.12.2011
          let PSSOGIAYTO = this.ChuanHoa_XML(this.modelInput.txtSoGT);//danh bạ
          let PSNOICAPGT = this.ChuanHoa_XML(this.modelInput.txtNoiCap);//danh bạ
          let PSNGAYCAPGT = "";
          if (this.modelInput.txtNgayCap != null) {
            PSNGAYCAPGT = this.modelInput.txtNgayCap;//Convert.ToDateTime(txtNgayCap.Text).ToString("dd/MM/yyyy");//danh bạ
          }
          let PSSOGT1 = "";
          let PSNOICAPGT1 = "";
          let PSNGAYCAPGT1 = "";

          rs = await this.MAP_ID_QUAN_ID_NEO()
          let PSUANCT_ID = rs ? rs : "-1"
          if (PSUANCT_ID == "-1") {
            this.$root.$toast.error("Thông tin về Quận/Huyện  của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }

          rs = await this.MAP_ID_PHUONG_ID_NEO()
          let PSHUONGCT_ID = rs || rs == 0 ? rs : "-1"
          if (PSHUONGCT_ID == "-1") {
            this.$root.$toast.error("Thông tin về Phường/Xã của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }
          rs = await this.MAP_ID_PHO_ID_NEO()

          let PSHOCT_ID = rs || rs == 0 ? rs : "-1"
          if (PSHOCT_ID == "-1") {
            this.$root.$toast.error("Thông tin về Phố/Thôn/Xóm của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }
          let PSONHACT_ID = this.ChuanHoa_XML((this.inputValue.sonhakh + '').trim());
          let PSDIACHICT = this.ChuanHoa_XML((this.modelInput.txtDiaChiKH + '').trim());
          let PSMSTHUE = this.ChuanHoa_XML((this.modelInput.txtMST + '').trim());
          let PSTAIKHOAN = this.ChuanHoa_XML((this.modelInput.txtSTK + '').trim());

          rs = await this.MAP_ID_QUAN_ID_NEO()
          let PSUANTT_ID = rs ? rs : "-1"
          if (PSUANTT_ID == "-1") {
            this.$root.$toast.error("Thông tin về Quận/Huyện của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }
          rs = await this.MAP_ID_PHUONG_ID_NEO()
          let PSHUONGTT_ID = rs || rs == 0 ? rs : "-1"
          if (PSHUONGTT_ID == "-1") {
            this.$root.$toast.error("Thông tin về Phường/Xã của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }

          rs = await this.MAP_ID_PHO_ID_NEO()
          let PSHOTT_ID = rs || rs == 0 ? rs : "-1"
          if (PSHOTT_ID == "-1") {
            this.$root.$toast.error("Thông tin Phố/Thôn/Xóm của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }

          let PSONHATT_ID = this.ChuanHoa_XML((this.inputValue.sonhatt + '').trim());
          let PSDIACHITT = this.ChuanHoa_XML((this.modelInput.txtDiaChiTT + '').trim());

          let PSDANGKYTV = "0";
          let PSDANGKYDB = "1";//Bổ sung sau
          let PSKHRR = "0";
          //let rs2 = await this.MAP_ID_USER_NEO()
          // chuaw thay dung let PSNGUOICN = this.ChuanHoa_XML(rs2[0].USER_NEO);
          //Chuaw thay dung let PSMAYCN = this.GetIPAndComputerName();
          let rs2 = await this.MAP_MANV_VNP((this.modelInput.txtMaNVTC + '').trim())
          let PSMANV = rs2 ? rs2 : "-1"
          if (PSMANV == "-1")
            PSMANV = "";
          //Lấy tên nhân viên thu cước
          let ten_nv = "";
          if (PSMANV) {
            let tentat = await this.LAY_TT_NHANVIEN_TC(PSMANV)
            ten_nv = tentat && tentat.length ? tentat[0].ten_nv : "";
          }

          let PSTENNV = ten_nv;
          let PSGHICHU = this.ChuanHoa_XML((this.modelInput.txtGhiChuTT + '').trim());

          rs2 = await this.MAP_LOAIGT_ID_NEO(this.modelCbo.cboGiayToKH)
          let PSLOAIGTID = rs2 || rs2 == 0 ? rs2 : "-1"
          let PSLOAIGTID1 = "1";

          rs2 = await this.MAP_LOAIKH_ID_NEO(this.modelCbo.cboLoaiKH2)
          let PSLOAIKH = rs2 || rs2 == 0 ? rs2 : "-1" //danh bạ

          rs2 = await this.MAP_HTTT_ID_NEO(this.inputValue.httt_id)
          let PSDIADIEMTT = rs2 || rs2 == 0 ? rs2 : "-1"
          let PSNGANHANG = "";
          let PSCHUYENKHOAN = "0";

          //Đơn vị quản lý id --> map : donvi_id_neo
          rs2 = await this.MAP_DONVI_ID_NEO(this.modelCbo.cboDonViQL)
          let PSDONVIQL = rs2 || rs2 ==0 ? rs2 : "-1"
          if (PSDONVIQL == "-1") {
            this.$root.$toast.error("Thông tin về đơn vị quản lý chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }
          rs2 = await this.MAP_MABC_ID_NEO(this.modelCbo.cboDonViQL)
          let PSMABC = rs2 || rs2 == 0 ? rs2 : "-1"
          if (PSMABC == "-1") {
            this.$root.$toast.error("Thông tin về mã bưu cục chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.")
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }

          let PSKHLON = "1";
          let PSUUTIEN = "0";
          //TuanNA: Zem code cũ -- ngày 02/12/2011
          rs2 = await this.MAP_TNC2_ID_NEO(this.inputValue.TNC2_ID)
          let PSNGANHNGHE = rs2 || rs2 == 0 ? rs2 : "-1"
          let PSDOIDVQLTB = "0";
          let PSMAT = this.ChuanHoa_XML(this.modelInput.txtMaTuyen);
          let PSTENT = PSMANV;
          let PSKYTEN = "";
          let PSNGUOI_GT = "";


          let PSMA_TUYEN = "";
          let PSTEN_TUYEN = "";
          let PSKH_LON = "1";
          let PSMA_QHNS = "";
          let PSNGANHANG_ID = "0";
          let PSLYDO_ANH = "1"; // khong can upload.
          let PSGIOITINH = "";

          PSGIOITINH = PSPHAI;
          let tentat_ccbs = ''
          let kq = await ccbsService.ts_hoanthien_hd_cn_kh(PSMA_TUYEN, PSDANGKYTV, PSHOCT_ID, PSUSERID, PSTAIKHOAN, PSKH_LON,
            PSGHICHU, PSHUONGTT_ID, PSMA_QHNS, PSNOICAPGT1, PSTENNV, PSNGANHANG_ID, PSKYTEN, PSUANTT_ID, PSDANGKYDB,
            PSLOAIKH, PSDONVIQL, PSONHATT_ID, PSNGANHNGHE, PSSOGT1, PSHOTT_ID, PSUUTIEN, PSLYDO_ANH, PSNGAYCAPGT,
            PSDIACHITT, PSUANCT_ID, tentat_ccbs, PSGIOITINH, PSMA_KH, PSMSTHUE, PSSOGIAYTO, PSNGAYCAPGT1, PSDIACHICT,
            PSSODAIDIEN, PSDIENTHOAILH, PSMACQ, PSTENTT, PSDONVIQL, PSNGUOIDAIDIEN, PSHUONGTT_ID, PSONHACT_ID, PSEMAIL,
            PSMABC, PSNOICAPGT, PSDIADIEMTT, PSTEN_TUYEN, PSMA_KH, PSLOAIGTID, PSNGUOI_GT, PSLOAIGTID1, PSNGAYSINH,
            PSKHRR, PSCOQUAN, PSCHUYENKHOAN, PSMANV);
          //int n;
          if (kq && kq.error_code == "BSS-00000000") {
            status = true;
            return true;
          } else {
            this.$root.$toast.error("Không lấy được dữ liệu trả về từ VNP.")
            if (kq && (kq.message_detail || kq.message))
              this.$root.$toast.error(`Có lỗi khi thực hiện:${kq.message}\n${kq.message_detail}`)
            this.inputValue.strerrorVinaphone = "error";
            return false;
          }

          // switch (kq) {
          //   case null:
          //     this.$root.$toast.error("Không lấy được dữ liệu trả về từ VNP")
          //     this.inputValue.strerrorVinaphone = "error";
          //     return false;
          //   default:
          //     if (kq != 'OK') {
          //       this.$root.$toast.error("Có lỗi khi thực hiện " + kq)
          //       this.inputValue.strerrorVinaphone = "error";
          //       return false;
          //     }
          //     status = true;
          //     return true;
          // }
        } catch (ex) {
          console.log(ex)
          status = false;
          this.inputValue.strerrorVinaphone = ex;
          // this.$root.$toast.error("Lỗi xảy ra trong quá trình cập nhật danh bạ di động trên Vinaphone. Mã khách hàng " + this.modelInput.txtMaKH + " ,mã thuê bao " + this.modelInput.txtMaTB + " " + ex)
        }
        return status;
      },
        Kiemtra_doituong_vdc: async function(hdtb_id, loaihinhtb_id)
        {
            let rs = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_soluong_hdtb', {
              hdtb_id: hdtb_id
            })
            let sl = rs.data[0].sl
            if (sl > 0)
                if (loaihinhtb_id == Enum.LoaiHinhTB.INTERNET_MYTV)
                {
                  let rs = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_doituong_hdtb', {
                    hdtb_id: hdtb_id
                  })
                    if (rs.data.length > 0)
                        return true;
                    else
                        return false;
                }
                else
                {
                    return true;
                }
            else
                return false;

        },
        ChuanHoa_XML: function (str) {
            let result = "";
            if (!str) return  result
            result = str.replace("&", "&amp");
            result = result.replace("<", "&lt");
            result = result.replace(">", "&gt");
            result = result.replace("'", "&apos");
            result = result.replace(String.fromCharCode(34), "&quot");
            return result;
        },
      ThayDoi_DoiTuong_Visa: async function (doituong_id) {
        try {
          let account = this.ChuanHoa_XML(this.modelInput.txtMaTB);
          let rs = await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/map_id_doituong_id_vdc_neo', {
            doituong_id: doituong_id
          })
          let kq = await vdcService.ChangeCustomerType(account, DateTimeLib.toDateDisplay(new Date()), rs.data);
          if (kq.errorCode == 0 && kq.data == "ok") return true
          else {
            let error = kq.faultString
            if (error.includes("Không có sự thay đổi")) { //Đã tồn tại ngày rồi
              return true;
            } else {
              this.$root.$toast.error("Có lỗi xảy ra: " + error)
              return false;
            }
          }
        } catch (ex) {
          return false;
        }
      },
      ThayDoi_NhomKhachHang_UuTien_VASC: async function (vma_tb, sPromo_id) {
        try {
          let IPTVAccount = vma_tb;
          // let sPromo_id = "0";
          // if (vdoituong_id == Enum.DoiTuong.Y_TE) sPromo_id = "DT02";

          let kq = await vasc.ChangePromotions(IPTVAccount, sPromo_id);
          if (kq.errorCode == 0 && kq.data.result == 0) return true
          else {
            let error = kq.data.errordesc
            this.$root.$toast.error("Có lỗi xảy ra: " + error)
            return false;
          }
        } catch (ex) {
          console.log(ex)
          //this.$root.toastError("" + ex);
          return false;
        }
      },
        CapNhat_NVTH_ID: async function (hdtb_id){
            try {
                let rs = await this.$root.context.get(
                "/web-ccdv/hoanthien-hs-tachnhap-tbao/capnhat_nvth/"+hdtb_id
                );
                return true;
            } catch (error) {
                return false;
            } finally {
            }
        },
        CapNhat_TT_Phieu_HTHS: async function (hdtb_id){
            try {
                let rs = await this.$root.context.get(
                "/web-ccdv/hoanthien-hs-tachnhap-tbao/capnhat_tt_phieu_hths/"+hdtb_id
                );
                return true;
            } catch (error) {
                return false;
            } finally {
            }
        },
        HoanThienHoSo: async function (hdtb_id, may_cn, ngay_cn, ma_nd) {
          await this.$root.context.post('web-ccdv/hoanthienhschuyenquyen/hoanthienhoso', {
            hdtb_id: hdtb_id,
            may_cn: may_cn,
            nguoi_cn: ma_nd
          })
        },
      hoangcong_hopdong_bancheo: async function (hdtb_id) {
        try {
          let rs = await this.$root.context.post(`web-ccdv/hoanthienhschuyenquyen/hoancong_thicong_2?hdtb_id=${hdtb_id}`)
          let tinhtc_id = 0;
          let ds = rs.data
          if (ds.length > 0 && ds[0].hdtb_ban > 0) { // bên thi công hoàn công update dữ liệu bên bán
            // let tinh = await this.tinh_thicong(ds[0].TINHBAN);
            tinhtc_id = ds[0].tinhtc;

            // HopDong bcbase = new HopDong();
            // bcbase = ActivateType<HopDong>(tinh.ip_dll + bcfacade.HopDong);

            await this.$root.context.post('web-ccdv/hoanthienhschuyenquyen/hoancong_thicong_v3', {
              HDTB_BAN: ds[0].hdtb_ban,
              KHID_TC: ds[0].khachhang_tc_id,
              TTID_TC: ds[0].thanhtoan_tc_id,
              TBID_TC: ds[0].thuebao_tc_id,
              TINH_TC: ds[0].tinhtc_id,
            })

          } else {//bên bán hoàn thiện

            let ds2 = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/loaihd_id_sel', {
              hdtb_id: hdtb_id
            })
            if (ds2.data.length != 0) {//anhnt thêm điều kiện thuebao_id_thicong != -1 cho chỉ giao net
              if (ds2.data[0].loaihd_id == Enum.LoaiHopDong.DAT_MOI && ds2.data[0].thuebao_id_thicong != -1) {
                //if (ds2.data[0].dichvuvt_id != Enum.DichVuVienThong.TSL)
                if (!(ds2.data[0].dichvuvt_id == Enum.DichVuVienThong.TSL || ds2.data[0].dichvuvt_id == 25)) {
                  // let tinh = await this.tinh_thicong(ds2.data[0].TINH_THICONG);
                  await this.$root.context.post('web-ccdv/hoanthienhschuyenquyen/bancheo_ins', {
                    thuebao_id: ds2.data[0].thuebao_id_thicong,
                    thuebao_kn_id: ds2.data[0].thuebao_id_ban,
                    tinh_kn_id: ds2.data[0].tinh_ban,
                  })
                } else {
                  for (let k = 0; k < ds2.data.length; k++) {
                    await this.$root.context.post('web-ccdv/hoanthienhschuyenquyen/bancheo_ins', {
                      thuebao_id: ds2.data[k].thuebao_id_thicong,
                      thuebao_kn_id: ds2.data[k].thuebao_id_ban,
                      tinh_kn_id: ds2.data[k].tinh_ban,
                    })
                  }
                }
              }
            }
          }
        } catch (e) {
          console.log(e)
        }
      },
        KT_TaoDB_Thang: async function(){
            let ngay_cn = DateTimeLib.toDateDisplayWithFormat(new Date(),'yyyyMM')
            let vkyhoadon = "" //yyyyMMck
            let thang = parseInt(ngay_cn.substring(4, 6))
            let nam =   parseInt(ngay_cn.substring(0, 4))
            if (thang == 1)
            {
                thang = 12
                nam = nam - 1
            }
            else
            {
                thang = thang - 1
            }
            if (thang.toString().length > 1)
            {
                vkyhoadon = nam.toString() + thang.toString() + "01"
            }
            else
            {
                vkyhoadon = nam.toString() + "0" + thang.toString() + "01"

            }
            let rs =  await this.demKyHoaDon(vkyhoadon)

            return rs;
        },
        demKyHoaDon: async function(vkyhoadon){
            try {
                let rs = await this.$root.context.get(
                "/web-ccdv/hoanthien-hs-tachnhap-tbao/ktra_taodb_thang",
                {kyhoadon:vkyhoadon}
                );
                if (rs.data != null) {
                return rs.data[0].kq
                }
            } catch (error) {
                return 0
            } finally {
            }
        },
      HoanThienHS: async function () {
        try {
          this.loading(true)
          if (this.dtThueBao.length > 0) {
            // if (tabThongTin.SelectedIndex == 0)
            //     tabThongTin.SelectedIndex = 1;

            if (this.selectedThueBao.length <= 0) {
              this.$root.$toast.error(' Hãy chọn thuê bao hoàn thiện')
              return;
            }
            let dsSelectedTB = this.dtThueBao.filter(i => this.selectedThueBao.includes(i.HDTB_ID))
            for (let t = 0; t < this.selectedThueBao.length; t++) {
              //if (lvwThueBao.Items[t].Checked)
              // {
              this.inputValue.hdtb_id = dsSelectedTB[t].HDTB_ID//Convert.ToInt32(lvwThueBao.Items[t].SubItems[1].Text);
              this.modelInput.txtMaTB = dsSelectedTB[t].MA_TB//lvwThueBao.Items[t].SubItems[2].Text;
              await this.HienThiTTHopDongTB_V2();
              if (dsSelectedTB[t].NGAY_TT == null) {
                this.$root.$toast.error('Chưa có ngày thanh toán. Hãy kiểm tra lại!')
                return;
              }
              let kt_taobang = await this.KT_TaoDB_Thang();
              if (kt_taobang == 0)
              {
                  this.$root.$toast.error("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !")
                  return;
              }
              let check = this.KiemTraDL_ChuyenVaoDB()
              if (!check) return;
              let dongbo_test = await this.KIEMTRA_OPEN_SYNC_VINA("DONGBO_TEST") // hàm dùng chung
              if (dongbo_test == false) {
                //#region Hoàn thiện vinaphone
                ///TuấnNA thêm code đồng bộ hoàn thiện vinaphone -- Ngày 12/08/2010
                if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.DI_DONG && this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.DIDONGTRASAU) {
                  // Thay đổi check cờ OPEN_SYNC_VINA_TRASAU từ tham số config db theo chỉ đạo Lamnt
                  let OPEN_SYNC_VINA_TRASAU = await this.KIEMTRA_OPEN_SYNC_VINA("OPEN_SYNC_VINA_TRASAU")
                  if (OPEN_SYNC_VINA_TRASAU) {
                  // if (Enum.TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU) {
                    this.$root.$toast.error("Bạn phải hoàn thiện ở menu: Lập hợp đồng-> đồng bộ dữ liệu-> đồng bộ dữ liệu di động lên vinaphone")
                    return;
                    //#region CUONG REM CODE
                    // DataSet ds = new DataSet();
                    // ds = tchopdong.LAY_DS_HDTB_THEO_HDKH_TTHD(hdkh_id, tthd_id);
                    // string ma_gd_neo = ma_gd;// dsHDKH.Tables[0].Rows[0]["ma_gd"].ToString();
                    // string matb = "";
                    // ///TuấnNA sửa code lấy row[index] đã chọn trên lưới --- > hoàn thiện
                    // int k = 0; ///biến đánh dấu
                    // for (int i = 0; i < ds.Tables.Count; i++)
                    // {
                    //     if (Convert.ToInt64(ds.Tables[0].Rows[i]["hdtb_id"].ToString()) == hdtb_id)
                    //     {
                    //         k = i;
                    //     }
                    // }
                    // matb = "84" + ds.Tables[0].Rows[k]["ma_tb"].ToString();
                    // if (matb.Length > 0)
                    // {
                    //     for (int j = 0; j < 3; j++)
                    //     {


                    //         //#region Hàm mới
                    //         string kq = Ts_laphd_chuyenquyen_sudung_hoanthienhd(ma_gd_neo, matb);
                    //         if (kq == ma_gd_neo)
                    //         {
                    //             ///TuấnNA sửa code cập nhật Status cho từng thuê bao trong hợp đồng
                    //             ///--- Ngày 17/08/2010
                    //             obj.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_HT);
                    //             Message_Box.ShowTB("Đã cập nhật danh bạ VinaPhone thành công!");
                    //             break;
                    //         }
                    //         if (j == 2)
                    //         {
                    //             Message_Box.ShowError("Có lỗi xảy ra trong quá trình cập nhật danh bạ lên VinaPhone.\n" + kq);
                    //             return;
                    //         }
                    //         //#endregion
                    //     }
                    // }
                    //#endregion END  CUONG REM CODE
                  } else {
                    await this.CAPNHAT_STATUS_HDTB(this.inputValue.hdtb_id, Enum.TRANGTHAI_DONGBO.DONGBO_HT);
                  }
                }

                  //#endregion

                //#region Hoàn thiện MyTV
                else if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL && this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.INTERNET_MYTV) {
                  await this.HoanThienMyTV();
                }
                //#endregion

                //#region Đồng bộ di động
                //  Lamnt: Với trường hợp chuyển quyền một thuê bao <> di động cho 1 khách hàng cũ, và ghép chung hóa đơn
                //với thuê bao di động của khách hàng này. nếu đồng thời thay đổi thông tin thanh toán của khách hàng
                //thì cũng phải đồng bộ thông tin này lên VinaPhone
                if (this.modelCbo.cboDichVuVT != Enum.DichVuVienThong.DI_DONG) {
                  // Thay đổi check cờ OPEN_SYNC_VINA_TRASAU từ tham số config db theo chỉ đạo Lamnt
                  let OPEN_SYNC_VINA_TRASAU = await this.KIEMTRA_OPEN_SYNC_VINA("OPEN_SYNC_VINA_TRASAU")
                  if (OPEN_SYNC_VINA_TRASAU) {
                    if (this.inputValue.khachhang_id != 0) //Khách hàng nhận quyền phải là khách hàng cũ

                    {
                      if (await this.CO_THUEBAO_DIDONG(this.inputValue.thanhtoan_id)) //Mã thanh toán có thuê bao di động
                      {
                        //Đồng bộ dữ liệu thông tin thanh toán lên Vinaphone
                        this.dsDBKH = await this.LAY_DS_DBKH_THEO_KHID(this.inputValue.khachhang_id);

                        for (let i = 0; i < 4; i++) {
                          if (await this.UPDATE_HDKH_VINAPHONE()) { // TODO: ccbsService.ts_hoanthien_hd_cn_kh
                            break;
                          }
                        }
                        if (this.inputValue.strerrorVinaphone == "") {
                          this.$root.toastSuccess("Cập nhật dữ liệu trên Vinaphone thành công");
                        } else {
                          this.$root.$toast.error("Có lỗi xảy ra trong quá trình cập nhật dữ liệu lên VinaPhone!" + this.inputValue.strerrorVinaphone)
                          return;
                        }
                      }
                    }
                  }
                }

                //#endregion

                //#region Thay đổi đối tượng trên Visa
                if ((this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL) &&
                  (this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.INTERNET_ADSL ||
                    this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.INTERNET_FTTH ||
                    this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.WIFI_FIBER)) {
                  //Hieutc: 06.04.2012: Kiểm tra nếu có sự thay đổi về đối tượng vdc thì mới
                  if (await this.Kiemtra_doituong_vdc(this.inputValue.hdtb_id, Enum.LoaiHinhTB.INTERNET_ADSL)) {
                    if (!await this.ThayDoi_DoiTuong_Visa(this.modelCbo.cboDoituongTB)) {
                      this.$root.$toast.error("Có lỗi trong quá trình thay đổi đối tượng khách hàng trên visa !")
                      return;
                    } else {
                      this.$root.toastSuccess("Thay đổi đối tượng khách hàng trên visa thành công !");
                    }
                  }
                }
                //#endregion

                //#region Thay doi nhom khach hang uu tien tren VASC
                if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL && this.modelCbo.cboLoaihinhTB == Enum.LoaiHinhTB.INTERNET_MYTV) {
                  if (await this.Kiemtra_doituong_vdc(this.inputValue.hdtb_id, Enum.LoaiHinhTB.INTERNET_MYTV)) {
                    let promo_id = "0";
                    if (this.modelCbo.cboDoituongTB == Enum.DoiTuong.Y_TE)
                      promo_id = "DT02";
                    if (!await this.ThayDoi_NhomKhachHang_UuTien_VASC(this.modelInput.txtMaTB, promo_id)) {
                      this.$root.$toast.error("Có lỗi trong quá trình thay đổi nhóm khách hàng ưu tiên trên VASC !")
                      return;
                    } else {
                      this.$root.toastSuccess("Thay đổi nhóm khách hàng ưu tiên trên VASC thành công !");
                    }
                  }
                }
                //#endregion

                //#region Hoàn thiện IMS

                //let service = {}//new WinUI.VNPTService.VTNService(tt_nd.user_vtn, tt_nd.pass_vtn);


                // if (this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.IMS) {
                //   if (this.inputValue.loaitb_id != Enum.LoaiHinhTB.IMS_POTS && this.inputValue.loaitb_id != Enum.LoaiHinhTB.IMS_SIP) {
                //     let result = service.ChuyenQuyen(this.inputValue.ma_tb, this.inputValue.hdtt_id, this.inputValue.hdtb_id);
                //     if (result == "ok") {
                //       this.$root.toastSuccess("Đã cập nhật danh bạ VTN thành công!");
                //     } else {
                //       this.$root.toastError("Có lỗi xảy ra trong quá trình cập nhật danh bạ lên VTN.\nLỗi: " + result);
                //       return;
                //     }
                //   }
                // }
                //# endregion
              }
              await this.CapNhat_NVTH_ID(this.inputValue.hdtb_id);
              await this.CapNhat_TT_Phieu_HTHS(this.inputValue.hdtb_id);
              // let ip = await this.getIp()
              await this.HoanThienHoSo(this.inputValue.hdtb_id, this.$auth.getUserName(), DateTimeLib.toDateDisplay(new Date()), this.$auth.getUserName());

              if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO")) {
                try {
                  await this.hoangcong_hopdong_bancheo(this.inputValue.hdtb_id);
                } catch (ex) {
                  let error = ex && ex.response && ex.response.message_detail ? ex.response.message_detail : ""
                  await this.$root.context.post("web-hopdong/goi-da-dichvu/log_action", {
                    vuser_name:this.$auth.getUserName(),
                    vcomputer_name:this.$auth.getUserName(),
                    vaction_name: "frmHTHSThayDoiTocDo",
                    vkey_parameter:`hdtb_id=${this.inputValue.hdtb_id},ngay_cn=${DateTimeLib.toDateDisplay(new Date())}, Err : ${error}`,
                    vserver_name:this.$auth.getUserName(),
                    vnote:" Hoàn công cố định"
                  })
                  // tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action",
                  //     "vuser_name", tt_nd.ma_nd,
                  //     "vcomputer_name", tt_nd.may_cn,
                  //     "vaction_name", "frmHTHSThayDoiTocDo",
                  //     "vkey_parameter", "hdtb_id=" + hdtb_id.ToString() + ",ngay_cn=" + tt_nd.ngay_cn + ", Err : " + ex.ToString(),
                  //     "vserver_name", tt_nd.ip,
                  //     "vnote", " Hoàn công cố định");
                  this.$root.$toast.error(ex.response.data.message_detail)
                }
              }

              if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CQ
                || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CQ
                || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_2M
                || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_THUONG
                || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
                let vhdtb_id = 0;
                for (let i = 0; i < this.dtThueBao.length; i++) {
                  if (this.dtThueBao[i].THUEBAO_CHA_ID == this.inputValue.thuebao_id) {
                    vhdtb_id = this.dtThueBao[i].HDTB_ID
                    await this.CapNhat_NVTH_ID(vhdtb_id);
                    await this.CapNhat_TT_Phieu_HTHS(vhdtb_id);
                    // let ip = await this.getIp()
                    await this.HoanThienHoSo(vhdtb_id, this.$root.token.getUserName(), DateTimeLib.toDateDisplay(new Date()), this.$root.token.getUserName());
                    if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO")) {
                      try {
                        await this.hoangcong_hopdong_bancheo(vhdtb_id);
                      } catch (ex) {
                        let error = ex && ex.response && ex.response.message_detail ? ex.response.message_detail : ""
                        await this.$root.context.post("web-hopdong/goi-da-dichvu/log_action", {
                          vuser_name:this.$auth.getUserName(),
                          vcomputer_name:this.$auth.getUserName(),
                          vaction_name: "frmHTHSThayDoiTocDo",
                          vkey_parameter:`hdtb_id=${this.inputValue.hdtb_id},ngay_cn=${DateTimeLib.toDateDisplay(new Date())}, Err : ${error}`,
                          vserver_name:this.$auth.getUserName(),
                          vnote:" Hoàn công cố định"
                        })
                        this.$root.$toast.error(ex.response.data.message_detail)
                      }
                    }
                  }
                }
              }

              // }
            }
            this.$root.toastSuccess("Cập nhật vào danh bạ thành công !");
            let ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.modelInput.txtMaGD, Enum.LoaiHopDong.CHUYEN_QUYEN, 0, this.inputValue.tthd_id, 0, 0)
            await this.HienThiTTHopDongKH(ds);//
          }
        } catch (exp) {
          this.$root.toastError(exp.response.data.message_detail)
        } finally {
          this.loading(false)
        }
      },
        getIp:async function(){
            try{
                const response = await fetch("https://jsonip.com/")
                const data = await response.json();
                return data.ip;
            }catch(e) {
                return null
            }
        },
      async KIEMTRA_OPEN_SYNC_VINA(keyname) {
        let kq = await this.$root.context.get("web-ccdv/hoanthienhschuyenquyen/kiemtra_open_sync_vina", {
          keyname: keyname
        })
        if (kq.data && kq.data.length > 0) {
          return kq.data[0].KEY_VALUE
        } else {
          return false
        }
      },
      HienThiTTHopDongTB_V2: async function () {
        try {
          let ds = await this.$root.context.get('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_tb_theo_hdtb_id', {
            hdtb_id: this.inputValue.hdtb_id
          })
          if (ds.data.length > 0) {
            ds = ds.data
            this.inputValue.hdtb_id = ds[0].HDTB_ID;
            if (ds[0].THUEBAO_ID != null)
              this.inputValue.thuebao_id = ds[0].THUEBAO_ID
            else
              this.inputValue.thuebao_id = 0;
            this.modelInput.txtMaTB = ds[0].MA_TB
            this.inputValue.ma_tb = ds[0].MA_TB;
            this.modelInput.txtTenTB = this.ChuanHoaTen(ds[0].TEN_TB);
            this.modelInput.txtDiaChiTB = this.ChuanHoaTen(ds[0].DIACHI_TB);
            let temp = await this.TTCT_LoaiHinh_TB(ds[0].LOAITB_ID)
            this.inputValue.dichvuvt_id = temp[0].dichvuvt_id;
            this.modelCbo.cboDichVuVT = temp[0].dichvuvt_id;
            this.inputValue.loaitb_id = ds[0].LOAITB_ID
            this.modelCbo.cboLoaihinhTB = ds[0].LOAITB_ID;
            this.modelCbo.cboKieuLD = ds[0].KIEULD_ID
            this.modelCbo.cboDoituongTB = ds[0].DOITUONG_ID
            this.modelCbo.cboTT = ds[0].HDTT_ID
            this.modelCbo.cboDaiVT = ds[0].DONVI_ID

            await this.HienThiTTMoRong(ds[0].MA_TB, this.inputValue.hdtb_id, temp[0].dichvuvt_id);
            if (ds[0].TTHD_ID == Enum.TrangThaiHD.DA_THI_CONG_XONG || ds[0].TTHD_ID == Enum.TrangThaiHD.HTHS_BANCHEO)
              this.tsbtnChuyenDB = true;
            else
              this.tsbtnChuyenDB = false;
            ///TuấnNA thêm code lấy thông tin thuê bao -- Ngày 08/08/2010
            this.modelInput.txtGhiChuTB = ds[0].GHICHU;
            this.modelInput.txtDiachiLD = this.ChuanHoaTen(ds[0].DIACHI_LD);
            ///TuấnNA thêm code lấy thông tin thuê bao để hoàn thiện lên MyTV --- Ngày 29/08/2010
            this.inputValue.matb_Mytv = ds[0].RMA_TB;
            this.inputValue.diachi_ld = ds[0].DIACHI_LD;

            let rs = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_thaotac', {
              hdtb_id: this.inputValue.hdtb_id
            })
            this.dsThaoTac = rs.data

          }
        } catch (exp) {
          console.log(exp)
          //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
        }
      },
        PHAILAM: function (CODE) {
            let dr = this.dsThaoTac.filter((x) => x.ENABLE == 1 && x.CODE == CODE);
            return dr.length > 0;
        },
        //#endregion
      lay_dk_bang_dichvuvt: async function () {
            try {
                let rs = await this.$root.context.get(
                "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_dk_bang_dichvuvt",
                );
                return rs.data;
            } catch (error) {
                return "";
            } finally {
            }
        },
      lay_dk_bang_doituong: async function () {
            try {
                let rs = await this.$root.context.get(
                "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_dk_bang_doituong",
                );
                return rs.data;
            } catch (error) {
                return "";
            } finally {
            }
        },
        // checkMapId: async function (id_neo, table, filter) {
        // try {
        //     if (!filter.includes("where")) filter = " where " + filter;
        //     let rs = await this.$root.context.post(
        //     "/web-hopdong/goi-da-dichvu/fn_map_id",
        //     {
        //         id_neo: id_neo,
        //         table: table,
        //         dk: filter,
        //     }
        //     );
        //     if (rs.data != null) return rs.data;
        //     else return "";
        // } catch (error) {
        //     return "";
        // } finally {
        // }
        // },
        tsbtnChuyenDB_Click: async function (){
          if (this.isEnabledTsbtnChuyenDB)
            await this.HoanThienHS();
        },
        HT_DichVuVT_Combobox: async function (){
            //let tag = await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-dangky-cho-bang', {tenbang: 'DICHVU_VT' })
            let tag = await this.lay_dk_bang_dichvuvt();
            let input = tag != null ? tag : '';
            await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-dvvt', {thamso: input }).then(
                async (res)=> {

                    this.modelCbo.vcboDichVuVT = res.data;
                    // this.modelCbo.cboDichVuVT = res.data[0].dichvuvt_id
                    if (this.modelCbo.vcboDichVuVT.length > 0) {
                        this.modelCbo.cboDichVuVT = res.data[0].dichvuvt_id
                        await this.getLoaiHinhThueBao()
                        //await this.onchangeDVVT()
                    }
                }
            )
        },
        HT_MucCuocTB_Combobox: async function (mucuoctb_id){

            // let data = {
            //     P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
            //     MUCUOCTB_ID: mucuoctb_id,
            //     PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.MUCCUOCTB'
            // }
          let muccuoc = ''
            if (mucuoctb_id) muccuoc = mucuoctb_id
            let res = await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/ht-muccuoctb-combobox', {mucuoctb_id:muccuoc})
            this.modelCbo.vcboMucCuocTB = res.data
            if (res.data.length > 0)
                this.modelCbo.cboMucCuocTB = res.data[0].mucuoctb_id
        },
        HT_DonVi_loaidv_Combobox: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaidv',{loaidv_id: 3}).then(
                res=> {
                  if (res.data.length) {
                    this.modelCbo.cboDaiVT = res.data[0].donvi_id
                  }
                  this.modelCbo.vcboDaiVT = res.data
                }
            ).catch(error => {
                this.$root.$toast.error(' '+error)
            });

        },
        HT_LoaiGT_Combobox: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaigt').then(res=> {
              if (res.data.length)
                this.modelCbo.cboGiayToKH = res.data[0].loaigt_id
              this.modelCbo.vcboGiayToKH = res.data
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_DoiTuong_Combobox: async function(){
            //let tag = await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-dangky-cho-bang', {tenbang: 'DOITUONG' })
            let tag = await this.lay_dk_bang_doituong();
            let input = tag != null ? tag : '';
            await this.$root.context.post('/web-ccdv/hoanthienhschuyenquyen/lay-ds-doituong', {thamso: input}).then(res=> {
              if (res.data.length)
                this.modelCbo.cboDoituongTB = res.data[0].doituong_id
              this.modelCbo.vcboDoituongTB = res.data
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_NganHang_Combobox_LD: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-nganhang').then(res=> {
                this.modelCbo.vcboNganHangKH = res.data
              if (res.data.length)
                this.modelCbo.cboNganHangKH = res.data[0].nganhang_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_HinhThucTT_Combobox: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-httt').then(res=> {
                this.modelCbo.vcboHTTT = res.data
              if (res.data.length)
                this.modelCbo.cboHTTT = res.data[0].httt_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        cboHTTT_SelectedValueChanged: async function(){
            if (this.modelCbo.vcboHTTT.length > 0)
            {
                //if (cboHTTT.SelectedValue.ToString() == "System.Data.DataRowView") return;
                this.inputValue.httt_id = this.modelCbo.cboHTTT

                if (parseInt(httt_id) == Enum.HinhThucThanhToan.UYNHIEM_THU)
                {
                    // cboNganHang.Enabled = true;
                    this.modelCkeckBox.chkNganHangKH = true
                }
                else
                {
                    // cboNganHang.Enabled = false;
                    this.modelCkeckBox.chkNganHangKH = false
                }
            }
        },
        Lay_ds_loaikh: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaikh', {nhomloaikh_id: 0}).then(res=> {
                this.modelCbo.vcboLoaiKH2 = res.data
                if (res.data.length > 0) {
                  // this.modelCbo.cboLoaiKH2 = res.data[0].LOAIKH_ID
                  let grid = this.$refs['cboLoaiKH2'].$children[0].grid
                  let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id == this.modelCbo.vcboLoaiKH2)
                  if (idx > -1) {
                    this.$nextTick(() => {
                      grid.selectRow(idx)
                    })
                  } else {
                    this.$nextTick(() => {
                      grid.selectRow(0)
                    })
                  }
                }
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_Tocdo_Adsl_Combobox: async function(tocdo_id){ // chỉ để view nên chỉ lấy theo id để tăng hiệu suất chứ không lấy full bảng
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-tocdocadsl', {
              tocdo_id:tocdo_id
            }).then(res=> {
                this.modelCbo.vcboGoiCuocAdsl = res.data
              if (res.data.length)
                this.modelCbo.cboGoiCuocAdsl = res.data[0].tocdo_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_MucCuoc_Adsl_Combobox: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-muccuoc-adsl').then(res=> {
                this.modelCbo.vcboMucCuocTB = res.data
              if (res.data.length)
                this.modelCbo.cboMucCuocTB = res.data[0].muccuoc_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_DonVi_loaidv_Combobox_TT: async function(donvi_id){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-loaidv', {loaidv_id: 2, donvi_id: donvi_id ? donvi_id : 0}).then(res=> {
                this.modelCbo.vcboDonViQL = res.data
              if (res.data.length)
                this.modelCbo.cboDonViQL = res.data[0].donvi_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_DanToc_Combobox: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-dantoc').then(res=> {
                this.modelCbo.vcboDanToc = res.data
              if (res.data.length)
                this.modelCbo.cboDanToc = res.data[0].dantoc_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_QuocTich_Combobox: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-quoctich').then(res=> {
                this.modelCbo.vcboQuocTich = res.data
                this.modelCbo.cboQuocTich = res.data[0].quoctich_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_NganhNgheLoc_Combobox: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-nganhnghe',{ma: 'NGANHNGHE'}).then(res=> {
                this.modelCbo.vcboNganhNgheLoc = res.data
              if (res.data.length)
                this.modelCbo.cboNganhNgheLoc = res.data[0].nganhnghe_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_NganHang_Combobox_KH: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-nganhang').then(res=> {
                this.modelCbo.vcboNganHang = res.data
              if (res.data.length)
                this.modelCbo.cboNganHang = res.data[0].nganhang_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_DT_VinaPhone_Combobox: async function(){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-vinaphone', {loaikh_id: 0}).then(res=> {
                this.modelCbo.vcboDTVinaPhone = res.data
              if (res.data.length)
                this.modelCbo.cboDTVinaPhone = res.data[0].doituong_id_neo
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        HT_GoiCuoc_DD_Combobox: async function(loaitb_id){
            let rs = await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-goicuoc', {loaitb_id: loaitb_id});
            this.modelCbo.vcboGoiCuocDD = rs.data
                if (rs.data.length > 0)
                    this.modelCbo.cboGoiCuocDD = rs.data[0].GOICUOC_ID
        },
        HienThiDS_HDTT_CBO: async function(hdkh_id){
            await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-hdtt-theohdid1', {hdkh_id: hdkh_id}).then(res=> {
                this.modelCbo.vcboTT = res.data
              if (res.data.length)
                this.modelCbo.cboTT = res.data[0].hdtt_id
            }).catch(error => {
                this.$root.$toast.error(' '+error)
            });
        },
        SetButton: function(kieu){
            this.$v.$touch()
            ///TuấnNA thêm code cho tsbtnGhiLai -- Ngày 09/08/2010
            this.isEnabledTsbtnChuyenDB = false;
            this.isEnabledTsbtnHuyBo = false;
            if (kieu == -1)//Bat dau
            {
                // if (this.kieugoi_id == Enum.KIEUGOI_CN_HOPDONG.DA_THI_CONG_XONG){
                //     this.isEnabledTsbtnChuyenDB = true;
                //     this.isEnabledTsbtnHuyBo = true;
                // }
                // if (this.$v.$invalid && !this.$v.modelInput.txtMaGD.required) {
                //     //this.$root.$toast.error('Trường tên hồ sơ bắt buộc nhập dữ liệu.')
                //     return;
                // }
              this.$refs.txtMaGD.focus()
            }
            if (kieu == 0)//Bat dau
            {
                // if (this.$v.$invalid && !this.$v.modelInput.txtMaGD.required) {
                //     //this.$root.$toast.error('Trường tên hồ sơ bắt buộc nhập dữ liệu.')
                //     return;
                // }
              this.$refs.txtMaGD.focus()
              this.Clear();
            }

            if (kieu == 1)//Them moi
            {
                // if (this.$v.$invalid && !this.$v.modelInput.txtMaGD.required) {
                //     //this.$root.$toast.error('Trường tên hồ sơ bắt buộc nhập dữ liệu.')
                //     return;
                // }
              this.$refs.txtMaGD.focus()
              this.Clear();
            }

            if (kieu == 2)//Huy
            {
               // if (this.$v.$invalid && !this.$v.modelInput.txtMaGD.required) {
               //      //this.$root.$toast.error('Trường tên hồ sơ bắt buộc nhập dữ liệu.')
               //      return;
               //  }
              this.isEnabledTsbtnChuyenDB = true;
              this.$refs.txtMaGD.focus()
              this.Clear();
            }

            if (kieu == 3)//Edit
            {
                // if (this.$v.$invalid && !this.$v.modelInput.txtMaGD.required) {
                //     //this.$root.$toast.error('Trường tên hồ sơ bắt buộc nhập dữ liệu.')
                //     return;
                // }
              this.$refs.txtMaGD.focus()

              // if (this.kieugoi_id == Enum.KIEUGOI_CN_HOPDONG.DA_THI_CONG_XONG){
                    this.isEnabledTsbtnChuyenDB = true;
                    this.isEnabledTsbtnHuyBo = true;
                // }

            }
        },
        Clear: function()
        {
            this.inputValue.hdkh_id = 0;
            this.modelInput.txtMaGD = "";
            this.modelInput.txtMaKH = "";
            this.inputValue.khachhang_id = 0;
            this.modelInput.txtTenKH = "";
            this.modelInput.txtDiaChiKH = "";
            this.modelInput.txtMaTBDD = "";
            this.modelInput.txtDiaChiBC = "";
            this.inputValue.tinhkh_id = 0;
            this.inputValue.quankh_id = 0;
            this.inputValue.phuongkh_id = 0;
            this.inputValue.phokh_id = 0;
            this.inputValue.apkh_id = 0;
            this.inputValue.sonhakh = "";
            this.modelInput.txtTelKH = "";
            this.modelInput.txtFaxKH = "";

            this.modelInput.txtNgaySN = "";
            this.modelDateTime.dtpNgayLHD = DateTimeLib.toDateDisplay(new Date())
            this.modelInput.txtSoGT = "";
            this.modelInput.txtNgayCap = "";
            this.modelInput.txtNoiCap = "";
            this.modelCbo.cboGiayToKH = 0;
            this.modelCbo.vcboTT = null;
            this.dtThueBao = [];
            this.dtList = [];
            this.modelInput.txtMaTuyen = "";

            this.inputValue.hdtt_id = 0;
            this.modelInput.txtMaTT = "";
            this.inputValue.thanhtoan_id = 0;
            this.modelInput.txtTenTT = "";
            this.modelInput.txtDiaChiTT = "";
            this.inputValue.tinhtt_id = 0;
            this.inputValue.quantt_id = 0;
            this.inputValue.phuongtt_id = 0;
            this.inputValue.phott_id = 0;
            this.inputValue.aptt_id = 0;
            this.inputValue.sonhatt = "";
            this.modelInput.txtMST = "";
            this.modelInput.txtSTK = "";
            this.modelCbo.cboHTTT = 0;
            this.modelCbo.cboDonViQL = 0;
            this.modelCbo.cboNganHang = 0;

            this.inputValue.hdtb_id = 0;
            this.inputValue.thuebao_id = 0;
            this.modelInput.txtTenTB = "";
            this.modelInput.txtDiaChiTB = "";
            this.inputValue.tinhtb_id = 0;
            this.inputValue.quantb_id = 0;
            this.inputValue.phuongtb_id = 0;
            this.inputValue.photb_id = 0;
            this.inputValue.aptb_id = 0;
            this.inputValue.sonhatb = "";
            this.inputValue.blocktb = "";
            this.inputValue.tangtb = "";
            this.inputValue.sophongtb = "";
            this.inputValue.motadctb = "";
            this.modelInput.lvDvgt = ''
            this.modelInput.lvDvkhac = ''

            this.modelCbo.cboLoaihinhTB = this.modelCbo.vcboLoaihinhTB.length ? this.modelCbo.vcboLoaihinhTB[0].LOAITB_ID : null;
            if (this.modelCbo.cboKieuLD != -1)
            {
                this.modelCbo.cboKieuLD = this.modelCbo.vcboKieuLD.length ? this.modelCbo.vcboKieuLD[0].KIEULD_ID : null;
            }

            this.modelCbo.cboDoituongTB = this.modelCbo.vcboDoituongTB.length ? this.modelCbo.vcboDoituongTB[0].doituong_id : null;
            ///TuấnNA thêm code Clear giá trị các biến, đối tượng trong form --- Ngày 31/08/2010
            this.modelInput.txtDiaChiCT = "";
            this.modelInput.txtDiachiLD = "";
            this.modelCkeckBox.chkNganHangKH = false;
            //rdoGTNone.Checked = true;
            this.modelInput.txtDiaChiNDD = "";
            this.modelInput.txtNguoiDD = "";
            this.modelCbo.cboDanToc = this.modelCbo.vcboDanToc.length ? this.modelCbo.vcboDanToc[0].dantoc_id : null;
            this.modelCbo.cboQuocTich = this.modelCbo.vcboQuocTich.length ? this.modelCbo.vcboQuocTich[0].quoctich_id : null;
            this.modelCbo.cboNganhNgheLoc = this.modelCbo.vcboNganhNgheLoc.length ? this.modelCbo.vcboNganhNgheLoc[0].nganhnghe_id : null
            this.modelInput.txtMaNVTC = "";
            this.modelInput.txtMaHD = "";
            this.modelInput.txtMaTB = "";

            this.inputValue.ten_kh = "";
            this.inputValue.ngay_sn = "";
            this.inputValue.gioitinh = "";
            this.inputValue.diachi_kh = "";
            this.inputValue.diachi_ld = "";
            this.inputValue.so_gt = "";

            this.inputValue.email = "";
            this.inputValue.sofax = "";
            this.inputValue.matb_Mytv = "";
            this.modelInput.txtDactrung = "";
            this.modelInput.txtHoKhau = "";
            this.modelInput.txtMaSTKH = "";
            this.modelInput.txtSoTKKH = "";
            this.modelInput.txtGhiChuTB = "";
            this.modelInput.txtGhiChuKH = "";
            this.modelInput.txtGhiChuTT = "";
            this.modelInput.txtNoiCapHK = "";

            this.modelInput.txtChucDanhDD = "";

            //TuấnNA thêm code clear ngân sách nhà nước --- Ngày 12/11/2010
            this.inputValue.DV_QHNS = "";
            this.inputValue.MACHUONG = "";
            this.inputValue.MN_KT = "";
            this.inputValue.MA_NDKT = "";
            this.inputValue.MN_NSNN = "";
            this.inputValue.TIEN_DM = 0;

            this.inputValue.TNC1_ID = 0;
            this.inputValue.TNC2_ID = 0;
            this.inputValue.TNC3_ID = 0;
            this.inputValue.TNC4_ID = 0;
            this.inputValue.nganhnghe_id = 0;
        },
        frmHTHSChuyenQuyen_Load: async function(){
            try {
               try
                {
                    if (this.kieugoi_id == 0){
                       this.kieugoi_id = parseInt(this.Tag)
                    }
                }
                catch (er)
                {
                    this.kieugoi_id = 0;
                }
                this.HienThiGiaoDien(this.kieugoi_id)//this.kieugoi_id
                this.modelDateTime.dtpNgayLHD = DateTimeLib.toDateDisplay(new Date())
                this.SetButton(-1);
                await this.HT_DichVuVT_Combobox();
                await this.HT_DonVi_loaidv_Combobox();
                await this.HT_LoaiGT_Combobox()
                await this.HT_DoiTuong_Combobox();
                await this.HT_NganHang_Combobox_LD();
                await this.HT_HinhThucTT_Combobox();
                await this.Lay_ds_loaikh()
                // await this.HT_Tocdo_Adsl_Combobox();
                await this.HT_MucCuoc_Adsl_Combobox();
                await this.HT_DonVi_loaidv_Combobox_TT();
                // ///TuấnNA thêm code hiển thị một số thông tin mới thêm trên form -- Ngày 08/08/2010
                await this.HT_DanToc_Combobox();
                await this.HT_QuocTich_Combobox();
                await this.HT_NganhNgheLoc_Combobox();
                await this.HT_NganHang_Combobox_KH();
                await this.HT_DT_VinaPhone_Combobox();
                await this.HT_GoiCuoc_DD_Combobox(1);

                this.modelDateTime.dtpNgayYC = DateTimeLib.toDateDisplay(new Date())

            } catch (error) {
               this.$root.$toast.error("" +error.response.data.message_detail)
            }
        },
        cbb_selectedChanged: async function(data1){
            if (data1){
                // this.modelCbo.cboLoaiKH2 = data1.loaikh_id
                try
                {
                    // this.loading(true)
                    if (!data1.loaikh_id || data1.loaikh_id == -1)
                        return;
                    let kieukh_id = 0;
                    let kieukh = "";
                    let dsLoai = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_loai_khachhang', {
                      loaikh_id: data1.loaikh_id
                    })
                    if (dsLoai != null && dsLoai.data.length > 0)
                    {
                        kieukh = dsLoai.data[0].LOAI
                    }

                    if (kieukh != "" && kieukh != null)
                        kieukh_id = kieukh
                    this.modelInput.txtPhanLoaiKH = "";

                    if (kieukh_id == Enum.KIEU_KH.CA_NHAN)
                    {
                        let dsPL = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ploai_khachhang', {
                          kieukh_id: Enum.PHANLOAI_KH.CA_NHAN
                        })
                        if (dsPL != null && dsPL.data.length > 0)
                        {
                            this.inputValue.PHANLOAIKH_ID = dsPL.data[0].PHANLOAIKH_ID
                        }

                        this.lblNgaySN = "Ngày sinh";
                        this.modelCkeckBox.disalbeGT = false
                        this.modelCkeckBox.disalbeDN = true
                        this.checkrdo = 'rdoF';
                        this.modelInput.txtPhanLoaiKH = dsPL.data[0].MA_PLKH
                        this.kieukh_canhan = true
                    }
                    else
                    {
                        let dsPL = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ploai_khachhang', {
                          kieukh_id: Enum.PHANLOAI_KH.CA_NHAN
                        })
                        if (dsPL != null && dsPL.data.length > 0)
                        {
                            this.inputValue.PHANLOAIKH_ID = dsPL.data[0].PHANLOAIKH_ID
                        }

                        this.lblNgaySN = "Ngày TL";
                        this.modelCkeckBox.disalbeGT = true
                        this.modelCkeckBox.disalbeDN = false
                        this.checkrdo = 'rdoNo';
                        this.modelInput.txtPhanLoaiKH = dsPL.data[0].MA_PLKH
                        this.kieukh_canhan = false
                    }
                }
                catch (exx)
                {
                  console.log(exx)
                }
                finally {
                  // this.loading(false)
                }
            }
        },
        HienThiGiaoDien: function(kieugoi_id){
            this.inputValue.tthd_id = kieugoi_id

            this.tsbtnChuyenDB = false;
            this.tsbtnHuyBo = false;
            if (kieugoi_id == Enum.KIEUGOI_CN_HOPDONG.DIEUHANH_THI_CONG)
            {
                this.tsbtnHuyBo = true;
            }

            if (kieugoi_id == Enum.KIEUGOI_CN_HOPDONG.DANG_THI_CONG)
            {
                this.tsbtnHuyBo = true;
            }

            if (kieugoi_id == Enum.KIEUGOI_CN_HOPDONG.DA_THI_CONG_XONG || kieugoi_id == 26)
            {
                this.tsbtnHuyBo = true;
                this.tsbtnChuyenDB = true;
                console.log("🚀 ~ file: index.vue ~ line 2662 ~ HienThiGiaoDien:function ~ kieugoi_id", kieugoi_id)
            }
        },
        onclickTab: function (currentActivetab) {
            try {
                // this.loading(true)
                this.activetab = currentActivetab;
                //this.hienThiDanhSachCatChuyen(currentActivetab, 0);
            } catch (error) {
                this.$root.toastError(' '+error);
            }
            finally {
                // this.loading(false)
            }
        },
        openPopup: function (){
            if (this.modelCbo.vcboLoaiKH2.length > 0){
                let selectedKH = this.modelCbo.vcboLoaiKH2.filter(obj=>obj.loaikh_id == this.modelCbo.cboLoaiKH2);
                 let selectedDslam = [...selectedKH][0]
                 let kieukh_id = -1
                 let nhomlkh_id = -1
                if (selectedDslam != null){
                    kieukh_id = selectedDslam.loai
                    nhomlkh_id = selectedDslam.nhomlkh_id
                }
                this.dataSend = {
                    loaikh_id: this.modelCbo.cboLoaiKH2,
                    kieukh_id: kieukh_id,
                    nhomlkh_id: nhomlkh_id
                }
            }
            this.$bvModal.show('popupThueBao')
        },
        chonKH: function (data) {
          let grid = this.$refs['cboLoaiKH2'].$children[0].grid
          let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id == data)
          if (idx > -1) {
            this.$nextTick(() => {
              grid.selectRow(idx)
            })
          } else {
            this.$nextTick(() => {
              grid.selectRow(0)
            })
          }
          // this.modelCbo.cboLoaiKH2 = data
        },
        openPopupHD: function (){
            this.dataHD = {
                loaihd_id: Enum.LoaiHopDong.CHUYEN_QUYEN,
                dichvuvt_id: 0,
                tthd_id: this.inputValue.tthd_id
            }
            this.$bvModal.show('popupHD')
        },
      chonHD: async function (data) {
        this.modelInput.txtMaGD = data
        //await this.onEnterMaGD();
        this.$refs.txtMaGD.focus()
        if (this.modelInput.txtMaGD) {
          let ds
          try {
            this.loading(true)
            if (this.inputValue.tthd_id != 0) {
              ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.modelInput.txtMaGD, Enum.LoaiHopDong.CHUYEN_QUYEN,
                this.$root.token.getDonViID(), this.inputValue.tthd_id, this.$root.token.getNhanVienID(), 0)
            } else {
              ds = await this.LAY_DS_HOPDONG_THEO_MA_GD_1(this.modelInput.txtMaGD, Enum.LoaiHopDong.CHUYEN_QUYEN, 0)
            }
            if (ds && ds.length == 0) {
              this.$root.toastError("Không tìm thấy thông tin theo mã giao dịch:" + this.modelInput.txtMaGD)
              return
            }
            await this.HienThiTTHopDongKH(ds)
          } catch (e) {

          } finally {
            this.loading(false)
          }
        }
      },

        changeOption: function() {

            if (this.checkrdo == 'rdoF') {
                this.inputValue.gioitinh = 0
            }
            else if (this.checkrdo == 'rdoM'){
                this.inputValue.gioitinh = 1
            }
            else if (this.checkrdo == 'rdoNo')
                this.inputValue.gioitinh = 2
        },
        updateOption: function(gioitinh) {
            if (gioitinh == 0)
            {
                this.checkrdo = 'rdoF';
            }
            else if (gioitinh == 1)
                this.checkrdo = 'rdoM';
            else
                this.checkrdo = 'rdoNo';
        },
        GiaoDien_DichVuVT: function(dichvuvt_id){
            // thispnlInt.Visible = false;
            this.pnlAdsl = false;
            this.pnlDD = false;

            switch (parseInt(dichvuvt_id))
            {
                case Enum.DichVuVienThong.CO_DINH:
                    break;
                case Enum.DichVuVienThong.DI_DONG:///TuấnNA thêm code hiển thị panel Di động -- Ngày 08/08/2010
                    this.pnlDD = true;
                    break;
                case Enum.DichVuVienThong.ADSL:
                case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
                case Enum.DichVuVienThong.DICHVU_CNTT:
                case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
                case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
                    this.pnlAdsl = true;
                    break;
                default:
                    break;
            }
        },
        onchangeDVVT: async function () {
            if (this.modelCbo.vcboDichVuVT.length > 0)
            {
              try {
                let dichvuvt_id = this.modelCbo.cboDichVuVT
                this.loading(true)
                await this.getLoaiHinhThueBao()
                await this.HT_GoiCuoc_DD_Combobox(this.modelCbo.cboLoaihinhTB);
                this.GiaoDien_DichVuVT(dichvuvt_id);
              }catch (e) {

              }finally {
                this.loading(false)
              }
            }
        },
        HT_Kieu_LD: async function (loaitb_id, loaihd_id) {
          let dsLoai = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_kieu_ld_new', {
            loaihd_id: loaihd_id,
            loaitb_id: loaitb_id
          })
          this.modelCbo.vcboKieuLD = []
          this.modelCbo.vcboKieuLD = dsLoai.data
          if (dsLoai.data.length > 0) {
            if (!this.modelCbo.cboKieuLD) this.modelCbo.cboKieuLD = dsLoai.data[0].KIEULD_ID
          }

        },
        cboLoaihinhTB_SelectedValueChanged: async function(){
          if (this.modelCbo.vcboLoaihinhTB.length > 0)
            {
              try {
                this.loading(true)
                await this.HT_Kieu_LD(this.modelCbo.cboLoaihinhTB, Enum.LoaiHopDong.CHUYEN_QUYEN);
              }catch (e) {

              }finally {
                this.loading(false)
              }
            }
        },
        getLoaiHinhThueBao: async function() {
            let res = await this.$root.context.get('web-ccdv/hoanthienhschuyenquyen/lay_ds_loaihinh_tb', {
              dichvuvt_id: this.modelCbo.cboDichVuVT
            })
            this.modelCbo.vcboLoaihinhTB = res.data
            if (res.data.length > 0) {
                if (!this.modelCbo.cboLoaihinhTB) this.modelCbo.cboLoaihinhTB = res.data[0].LOAITB_ID
                // await this.cboLoaihinhTB_SelectedValueChanged()
            }
        },
    },
    created: async function(){
        this.Tag = this.$route.fullPath.split('tag=') && this.$route.fullPath.split('tag=').length ? this.$route.fullPath.split('tag=')[1] : '0'
        vasc.setupRoot(this.$root)
        vdcService.setupRoot(this.$root)
        ccbsService.setupRoot(this.$root)
    },
    mounted: async function() {
        try {
            this.loading(true);
            await this.frmHTHSChuyenQuyen_Load()
        } catch (error) {
          console.log(error)
        } finally {
            this.loading(false);
        }
    },
  watch: {
    'modelCbo.cboDichVuVT': async function (val) {
      if (val) {
        await this.getLoaiHinhThueBao()
        await this.HT_GoiCuoc_DD_Combobox(this.modelCbo.cboLoaihinhTB);
        this.GiaoDien_DichVuVT(val);
      }
        // await this.onchangeDVVT()
    },
    'modelCbo.cboLoaihinhTB': async function (val) {
      if (val) {
        await this.HT_Kieu_LD(val, Enum.LoaiHopDong.CHUYEN_QUYEN);
      }
        // await this.cboLoaihinhTB_SelectedValueChanged()
    },
  }
}
</script>

<style scoped>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
.black-bold {
  font-weight: bold;
  color: black;
}
</style>
