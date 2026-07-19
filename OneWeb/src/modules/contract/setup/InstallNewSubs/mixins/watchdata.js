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

    'DiaChiLDCLONE': {
        handler: async function(newval,oldval) {
          if(!this.watcherActive) return;
          if((!oldval && newval) || newval.QUAN_ID!=oldval.QUAN_ID || newval.PHUONG_ID!=oldval.PHUONG_ID || newval.PHO_ID!=oldval.PHO_ID || newval.AP_ID!=oldval.AP_ID || newval.KHU_ID!=oldval.KHU_ID || newval.DACDIEM_ID!=oldval.DACDIEM_ID) {
            if(this.TinhKhac==0 && this.TinhKhacLD==0) {
              var pdata1 = {"DT_CONTROLS":this.DT_CONTROLS.list,"PHIEUHS_ID":this.PHIEUHS_ID?this.PHIEUHS_ID:0,"PHUONG_ID":newval.PHUONG_ID?newval.PHUONG_ID:0,"PHO_ID":newval.PHO_ID?newval.PHO_ID:0,"AP_ID":newval.AP_ID?newval.AP_ID:0,"KHU_ID":newval.KHU_ID?newval.KHU_ID:0,"DACDIEM_ID":newval.DACDIEM_ID?newval.DACDIEM_ID:0,"TTND_DV_ID":this.$root.token.getNguoiDungID(),"HDKH_ID":this.khachhang.hdkh_id,"CTV_ID":this.filterbox.ctv.value,"NGUOIGT_ID":this.filterbox.nguoigt.value}
              await API.post_fn_lay_donvi_theo_loaidv_diachi_ld_v2(this.axios, { "ds_para":JSON.stringify(pdata1) }).then(
              (response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  var dulieuapi = JSON.parse(response.data.data);
                  if(dulieuapi['RESULT']['DS_GT_DVI'] && dulieuapi['RESULT']['DS_GT_DVI'].length>0 && this.DT_CONTROLS.data.length==dulieuapi['RESULT']['DS_GT_DVI'].length)
                  {
                    this.DT_CONTROLS.value = dulieuapi['RESULT']['DS_GT_DVI'];
                    for(var i=0;i<this.DT_CONTROLS.data.length; i++) {
                      if(this.DT_CONTROLS.data[i] && this.DT_CONTROLS.data[i].length>0 && !this.DT_CONTROLS.value[i]) {
                        this.DT_CONTROLS.value[i] = this.DT_CONTROLS.data[i][0].DONVI_ID;
                        this.DT_CONTROLS.tagvalue[i] = this.DT_CONTROLS.data[i][0].LOAIDV_ID;
                      }
                    }
                    this.DT_CONTROLS.dichvu_vt = this.listbox.dichvu_vt.value;
                    this.DT_CONTROLS.key += 1;
                  }
                }
              });
              if(this.listbox.dichvu_vt.value==DichVuVienThong.CO_DINH)
                this.buttons.btnChonSM = true;
            }
            var pdata = {"KETCUOI_ID":this.KetCuoi_ID,"TINHKHAC":this.thuebao.tinhkhac,"KIEULD_ID":this.listbox.kieu_ld.value,"PHUONG_ID":newval.PHUONG_ID,"PHO_ID":newval.PHO_ID,"AP_ID":newval.AP_ID,"KHU_ID":newval.KHU_ID,"DACDIEM_ID":newval.DACDIEM_ID,"KHUVUC_THEO_KETCUOI":this.KHUVUC_THEO_KETCUOI};
            await API.post_fn_lay_tt_th_diachi_ld(this.axios, { "ds_para":JSON.stringify(pdata) }).then(
            async (response) => {
              if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                var apidata = JSON.parse(response.data.data);
                if(apidata['RESULT']) apidata = apidata['RESULT'];
                if(apidata['DS_DVI']) this.XuLyDonViLD(apidata['DS_DVI']);
                if(apidata['DS_KV'] && apidata['DS_KV'].length>0) {
                  this.thuebao.khuvuc = apidata['DS_KV'][0]['TEN_KV'];
                  this.thuebao.khuvuc_id = apidata['DS_KV'][0]['KHUVUC_ID'];
                  this.dsHDTB_KV = [{
                    HDTB_ID : this.hdtb_id?this.hdtb_id:0,
                    KHUVUC_ID : this.thuebao.khuvuc_id,
                    LOAIKV_ID  : LOAI_KV.KHUVUC_DIABAN,
                    NGAY_CN : new Date().toLocaleDateString('en-GB'),
                    MAY_CN : this.$root.token.getMaNhanVien(),
                    NGUOI_CN : this.$root.token.getNguoiDungID(),
                    IP_CN : "xxx",
                    KIEUKV_ID : 0
                  }];
                } else { // FIXME THANGDM1 them case else nay de load thong tin khu vuc tu diachiLD neu khong load duoc o buoc tren!
                  var rs = await this.$root.context.post("/web-hopdong/thaydoiloaihinhtbcodinhims/sp_lay_khuvuc_theo_diadanh",{
                    loaikv_id:  4,
                    quan_id:  newval.QUAN_ID,
                    phuong_id:  newval.PHUONG_ID,
                    pho_id:  newval.PHO_ID?newval.PHO_ID:0,
                    ap_id:  newval.AP_ID?newval.AP_ID:0,
                    khu_id:  newval.KHU_ID?newval.KHU_ID:0,
                    dacdiem_id:  newval.DACDIEM_ID?newval.DACDIEM_ID:0,
                    tramtc_id: 0
                  });
                  if(rs.data.length > 0) {
                    this.thuebao.khuvuc = rs.data[0].ten_kv;
                    this.thuebao.khuvuc_id = rs.data[0].khuvuc_id;
                    this.dsHDTB_KV = [{
                      HDTB_ID : this.hdtb_id?this.hdtb_id:0,
                      KHUVUC_ID : this.thuebao.khuvuc_id,
                      LOAIKV_ID  : LOAI_KV.KHUVUC_DIABAN,
                      NGAY_CN : new Date().toLocaleDateString('en-GB'),
                      MAY_CN : this.$root.token.getMaNhanVien(),
                      NGUOI_CN : this.$root.token.getNguoiDungID(),
                      IP_CN : "xxx",
                      KIEUKV_ID : 0
                    }];
                  }
                }
              }
            });
            await this.HT_DS_KhuyenMai_Combobox();
            await this.LoadMucCuocTB();
            await API.post_sp_layds_tb_trung_dcld(this.axios, { "diachi_ld":this.diachiLD.DIACHI }).then(
            (response) => {
              if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                var dlist = response.data.data;
                if(dlist && dlist.length>(this.buttonSet==1?0:1)) {
                  this.$bvModal.msgBoxConfirm(`Tìm thấy ${dlist.length} thuê bao trong danh bạ trùng địa chỉ lắp đặt. Bạn có muốn xem thông tin các thuê bao này?`, {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Không đồng ý',
                  }).then(async v => {
                    if(v)
                    {
                      this.popupComponent = ()=>import('../Popups/popupChonGrid');
                      this.popupComponentName = "popupTrungDC";
                      this.popupComponentData = {"ds":dlist,cols:[
                        {fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: false, allowSorting: false},
                        {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: false, allowSorting: false},
                        {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: false, allowSorting: false},
                        {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: false, allowSorting: false},
                        {fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: false, allowSorting: false},
                      ],keycol: "DONVIQL_ID",allowExit:false,tieude:"Các thuê bao tại địa chỉ "+this.diachiLD.DIACHI,tieudebang:"Địa chỉ: "+this.diachiLD.DIACHI,}
                      this.Popup('popupComponents');
                    }
                  });
                }
              }
            });
          }
        }, deep:true
      },
      'listbox.doituong.value': function(val) { if(!this.watcherActive || !val || val<=0) return; this.LoadMucCuocTB(); this.HT_DS_KhuyenMai_Combobox(); },
      'listbox.loaihinh_tb.value': async function(val) {
        if(!this.watcherActive || !val || val<=0) return;
        this.LoadMucCuocTB();
        this.chkLuongGiuKenh = false;

        if (this.listbox.dichvu_vt.value == DichVuVienThong.ADSL || this.listbox.dichvu_vt.value == DichVuVienThong.MEGA_EYES || this.listbox.dichvu_vt.value == DichVuVienThong.BRCD_DOITAC) {
          this.loading(true);
          try {
            await API.post_sp_lay_ds_thietbi_dc_lhtb(this.axios, { "kieu": 1, "loaitb_id": val }).then(
            (response) => {
              if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.listbox.cboLoaiTB.list = response.data.data;
                if(this.listbox.cboLoaiTB.list && this.listbox.cboLoaiTB.list.length>0 && this.listbox.cboLoaiTB.list.filter(x=>x.thietbidc_id>0).length>0)
                    this.listbox.cboLoaiTB.value = this.listbox.cboLoaiTB.list.filter(x=>x.thietbidc_id>0)[0].thietbidc_id;
              }
            });
          } catch(ex) { this.$toast.error("" + ex); if(this.debug) console.error(ex); }
          finally { this.loading(false); }
        }
        await this.GiaoDien_DichVuVT(this.listbox.dichvu_vt.value*1)
        if (this.listbox.loaihinh_tb.value == LoaiHinhTB.CAPQUANG_TRANG_TSL)
            this.thuebao.tsl.txtSoSoiQuang_Enabled = true;
        else
            this.thuebao.tsl.txtSoSoiQuang_Enabled = false;
        // TODO line 23118 chua thuc hien, lien quan den Di_Dong
        // if (dichvuvt_id == DichVuVienThong.DI_DONG)
        // {
        //     bangts.HT_GoiCuoc_DD_Combobox(cboGoiCuocDD, loaitb_id);

        //     if (loaitb_id == LoaiHinhTB.DIDONGTRATRUOC) // vuth update fix 1509
        //     {
        //         cboGoiCuocDD.Enabled = false;
        //     }
        //     else
        //     {
        //         cboGoiCuocDD.SelectedValue = GOICUOC_DD.POSTPAID;
        //     }
        // }

        // TODO tam bo qua doan 23132 - 23198, chi bat tat giao dien -> chua xu ly
        if (val == LoaiHinhTB.KTR_DOICAP_DONG || val == LoaiHinhTB.KTR_DOICAP_QUANG || val == LoaiHinhTB.KTR_Leasedline_E1 || val == LoaiHinhTB.KTR_Leasedline_FE || val == LoaiHinhTB.KTR_Leasedline_GE || val == LoaiHinhTB.KTR_Leasedline_nx64k || val == LoaiHinhTB.KTR_Leasedline_STM1 || val == LoaiHinhTB.KTR_Nail_up || val == LoaiHinhTB.KTR_TRUCTHONG_DONG || val == LoaiHinhTB.KTR_TRUCTHONG_QUANG)
        {
            this.chkLuongGiuKenh = true;
        }

        if ([DichVuVienThong.ADSL,DichVuVienThong.BRCD_DOITAC,DichVuVienThong.DICHVU_CNTT, DichVuVienThong.TRUNGTAM_DULIEU,DichVuVienThong.HOINGHI_TRUYENHINH, DichVuVienThong.ANTOAN_BAOMAT_TT,DichVuVienThong.HATANG_VIENTHONG, DichVuVienThong.MEGA_EYES].indexOf(this.listbox.dichvu_vt.value*1)>=0)
        {
            var dk = "sudung = 1";
            // this.cboGoiCuocAdsl.EditValueChanged -= new System.EventHandler(this.cboGoiCuocAdsl_EditValueChanged);
            if (this.listbox.loaihinh_tb.value == LoaiHinhTB.FSECURE) {
              // bangts.HT_Tocdo_Fsecure_GRID(cboGoiCuocAdsl, this.listbox.loaihinh_tb.value, dk);
              this.loading(true);
              try {
                await API.post_sp_ht_tocdo_fsecure(this.axios, { "loaitb_id": val }).then(async (response) => {
                  if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.listbox.cboGoiCuocAdsl.list = response.data.data;
                    if(this.listbox.cboGoiCuocAdsl.list && this.listbox.cboGoiCuocAdsl.list.length>0) {
                      this.listbox.cboGoiCuocAdsl.value = this.listbox.cboGoiCuocAdsl.list.filter(x=>x.tocdo_id>0)[0].tocdo_id;
                      await this.cboGoiCuocAdsl_SelectedValueChanged(this.listbox.cboGoiCuocAdsl.value);
                    }
                  }
                });
              } catch(ex) { this.$toast.error("" + ex); if(this.debug) console.error(ex); } finally { this.loading(false); }
            }
            else //nhapt sua theo y/c C.Phuong 04012017
            {
              this.loading(true);
              try {
                await API.post_sp_ht_tocdo_adsl_theo_loaitb(this.axios, { "congnghe_id": this.listbox.congnghe.value?this.listbox.congnghe.value:0, "kieuld_id": this.listbox.kieu_ld.value, "loaitb_id": val }).then(async (response) => {
                  if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.listbox.cboGoiCuocAdsl.list = response.data.data;
                    if(this.listbox.cboGoiCuocAdsl.list && this.listbox.cboGoiCuocAdsl.list.length>0)
                      this.listbox.cboGoiCuocAdsl.value = this.listbox.cboGoiCuocAdsl.list.filter(x=>x.tocdo_id>0)[0].tocdo_id;
                      await this.cboGoiCuocAdsl_SelectedValueChanged(this.listbox.cboGoiCuocAdsl.value);
                      // this.listbox.cboGoiCuocAdsl.values = [this.listbox.cboGoiCuocAdsl.value];
                  }
                });
              } catch(ex) { this.$toast.error("" + ex); if(this.debug) console.error(ex); } finally { this.loading(false); }
            }
            // this.cboGoiCuocAdsl.EditValueChanged += new System.EventHandler(this.cboGoiCuocAdsl_EditValueChanged);
            // cboGoiCuocAdsl_SelectedValueChanged(cboGoiCuocAdsl, null);
        }
        if (this.listbox.dichvu_vt.value == DichVuVienThong.MEGAWAN || this.listbox.dichvu_vt.value == DichVuVienThong.TSL || this.listbox.dichvu_vt.value == DichVuVienThong.METRONET)
        {
          await this.SinhMaDVVT();
        }

        if(!this.watcherActive)
          this.HT_GIAODIEN_DONVI();
        //HungHM - 31/08/2010 - Bổ xung ngày hết hạn đặt chỗ ( chỉ ADSL, Internet 126x, ADSL mới hiện lên)
        if (val == LoaiHinhTB.INTERNET_1260 || val == LoaiHinhTB.INTERNET_ADSL || val == LoaiHinhTB.INTERNET_FTTH || val == LoaiHinhTB.WIFI_FIBER || val == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || val == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
        {
            this.thuebao.adsl.dtpHanDatCho_Visible = true;
            this.thuebao.adsl.btnKiemTraAccount_visible = true;
        }
        else
        {
            this.thuebao.adsl.dtpHanDatCho_Visible = false;
            this.thuebao.adsl.btnKiemTraAccount_visible = false;
        }
        this.thuebao.adsl.txtAccountADSL = "";
        this.thuebao.adsl.txtEmailAdsl = "";
        // if (dichvuvt_id == DichVuVienThong.IMS_DOITAC)
        //     txtMaTBIMS.Text = "";
        if (this.listbox.dichvu_vt.value == DichVuVienThong.MGW_DOITAC)
            this.thuebao.mgwan.somay = "";
        if (this.listbox.dichvu_vt.value == DichVuVienThong.TSL_DOITAC)
            this.thuebao.tsl.txtMaTSL.Text = "";

        //HungHM - 05/08/2010 - Sinh mã MyTV
        if (this.thuebao.adsl.txtAccountADSL.trim().length == 0 && (val == LoaiHinhTB.INTERNET_MYTV || val == LoaiHinhTB.INTERNET_MYTV_B2B || val == LoaiHinhTB.HTVC_FIBERVNN_ANALOG || val == LoaiHinhTB.HTVC_FIBERVNN_DIGITAL || val == LoaiHinhTB.WIFI_MESH || val == LoaiHinhTB.INDOOR_CAM || val == LoaiHinhTB.OUTDOOR_CAM))
          this.SinhMaDVVT();

        this.LoadMucCuocTB();

        // ///TuanNA: Thêm code lấy thông tin cước thuê kênh, thuê cổng đối với thuê bao Megawan
        // ///Ngày 07.05.2012
        // if (dichvuvt_id == DichVuVienThong.MEGAWAN || dichvuvt_id == DichVuVienThong.TSL || dichvuvt_id == DichVuVienThong.METRONET
        //     || dichvuvt_id == DichVuVienThong.MGW_DOITAC || dichvuvt_id == DichVuVienThong.TSL_DOITAC)
        // {
        //     txtCuoc_ISP.Text = "0";
        //     txtCuoc_NIX.Text = "0";
        //     txtSoLuong_IP.Text = "0";
        //     txtCuoc_IP.Text = "0";

        //     if (loaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP)
        //     {
        //         label38.Text = "Tốc độ tổng";
        //         cboPhanLoaiMGW.Enabled = true;
        //         cboPhanLoaiMGW.SelectedIndex = 0;
        //         cboPhanTachTD.Enabled = true;
        //         cboPhanTachTD.SelectedIndex = 0;

        //         cboTocDo.Enabled = true;
        //         cboTocDo.EditValue = 0;
        //         cboTocDo_PIR.Enabled = false;
        //         chkTocDo_PIR.Checked = false;
        //         cboTocDo_PIR.EditValue = 0;
        //         chkTocDo_PIR.Enabled = false;

        //         txtCuoc_ISP.Enabled = true;
        //         txtCuoc_NIX.Enabled = true;
        //         txtSoLuong_IP.Enabled = true;
        //         txtCuoc_IP.Enabled = true;
        //         //chkTocDo_ISP.Enabled = true;
        //         //chkTocDo_NIX.Enabled = true;

        //         if (Convert.ToInt32(cboPhanTachTD.SelectedValue) == 1)
        //         {
        //             chkTocDo_ISP.Enabled = true;
        //             chkTocDo_NIX.Enabled = true;

        //             cboTocDo_ISP.EditValue = 0;
        //             cboTocDo_NIX.EditValue = 0;
        //             cboTocDo_ISP.Text = "";
        //             cboTocDo_NIX.Text = "";

        //             txtCuoc_NIX.Text = "0";
        //             txtCuoc_ISP.Text = "0";
        //         }
        //         else
        //         {
        //             chkTocDo_ISP.Enabled = false;
        //             chkTocDo_NIX.Enabled = false;

        //             cboTocDo_ISP.EditValue = 0;
        //             cboTocDo_NIX.EditValue = 0;
        //             cboTocDo_ISP.Text = "";
        //             cboTocDo_NIX.Text = "";

        //             txtCuoc_NIX.Text = "0";
        //             txtCuoc_ISP.Text = "0";
        //         }

        //         //HienThi_CTK_NIX_ISP(2);
        //         //HienThi_CTK_NIX_ISP(3);
        //     }
        //     else
        //     {
        //         label38.Text = "Tốc độ CIR";
        //         cboPhanLoaiMGW.Enabled = false;
        //         cboPhanLoaiMGW.SelectedIndex = 0;
        //         cboPhanTachTD.Enabled = false;
        //         cboPhanTachTD.SelectedIndex = 0;

        //         cboTocDo.Enabled = true;
        //         cboTocDo.EditValue = 0;
        //         cboTocDo_PIR.Enabled = false;
        //         cboTocDo_PIR.EditValue = 0;
        //         chkTocDo_PIR.Enabled = true;
        //         chkTocDo_PIR.Checked = false;

        //         cboTocDo_ISP.Enabled = false;
        //         cboTocDo_NIX.Enabled = false;
        //         txtCuoc_ISP.Enabled = false;
        //         txtCuoc_NIX.Enabled = false;
        //         txtSoLuong_IP.Enabled = false;
        //         txtCuoc_IP.Enabled = false;
        //         chkTocDo_ISP.Enabled = false;
        //         chkTocDo_NIX.Enabled = false;
        //         chkTocDo_ISP.Checked = false;
        //         chkTocDo_NIX.Checked = false;

        //         cboTocDo_ISP.EditValue = 0;
        //         cboTocDo_NIX.EditValue = 0;
        //         cboTocDo_ISP.Text = "";
        //         cboTocDo_NIX.Text = "";

        //         txtCuoc_NIX.Text = "0";
        //         txtCuoc_ISP.Text = "0";
        //     }
        //     HienThi_CTK_CTC();
        // }

        // LAY_DS_DVGT_MACDINH(loaitb_id);
        // LAY_DS_DVK_MACDINH_TB(loaitb_id);

        // if (loaitb_id == LoaiHinhTB.INTERNET_MYTV || loaitb_id == LoaiHinhTB.INTERNET_MYTV_B2B)
        // {
        //     pnlMyTV.Visible = true;
        //     txtEmailAdsl.Enabled = false;
        // }
        // else
        // {
        //     pnlMyTV.Visible = false;
        //     txtEmailAdsl.Enabled = true;
        // }

        // if (loaitb_id == LoaiHinhTB.COSO_HATANG)
        //     pnlCSHT.Visible = true;
        // else
        //     pnlCSHT.Visible = false;

        // if (loaitb_id == LoaiHinhTB.HTVC_FIBERVNN_ANALOG || loaitb_id == LoaiHinhTB.HTVC_FIBERVNN_DIGITAL
        //     || loaitb_id == LoaiHinhTB.MEGACAMERA)
        // {
        //     pnlHTVC.Visible = true;
        //     txtEmailAdsl.Enabled = false;
        // }
        // else
        // {
        //     pnlHTVC.Visible = false;
        //     txtEmailAdsl.Enabled = true;
        // }

        // if (loaitb_id == LoaiHinhTB.FSECURE)
        // {
        //     pnlFsecure.Visible = true;
        //     txtEmailAdsl.Enabled = false;
        // }
        // else
        // {
        //     pnlFsecure.Visible = false;
        //     txtEmailAdsl.Enabled = true;
        // }

        // if (loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
        // {
        //     pnlFiber_TK.Visible = true;
        //     txtEmailAdsl.Enabled = false;
        // }
        // else
        // {
        //     pnlFiber_TK.Visible = false;
        //     txtEmailAdsl.Enabled = true;
        // }

        // if (loaitb_id == LoaiHinhTB.INTERNET_SGTV || loaitb_id == LoaiHinhTB.CAMERA_HOCDUONG)
        // {
        //     pnlDynamicInt.Top = pnlFTTH.Top + 5;
        // }
        // else if (loaitb_id == LoaiHinhTB.INTERNET_FTTH || loaitb_id == LoaiHinhTB.WIFI_FIBER || loaitb_id == LoaiHinhTB.INTERNET_ADSL
        //     || loaitb_id == LoaiHinhTB.INTERNET_MYTV || loaitb_id == LoaiHinhTB.INTERNET_MYTV_B2B || loaitb_id == LoaiHinhTB.HTVC_FIBERVNN_ANALOG
        //     || loaitb_id == LoaiHinhTB.HTVC_FIBERVNN_DIGITAL || loaitb_id == LoaiHinhTB.MEGACAMERA || loaitb_id == LoaiHinhTB.COSO_HATANG)
        // {
        //     pnlDynamicInt.Top = pnlMyTV.Top + pnlMyTV.Height + 2;
        // }
        // else if (loaitb_id == LoaiHinhTB.FSECURE || loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
        // {
        //     pnlDynamicInt.Top = pnlFsecure.Bottom + 2;
        // }

        // if (loaitb_id == LoaiHinhTB.INTERNET_FTTH || loaitb_id == LoaiHinhTB.WIFI_FIBER || loaitb_id == LoaiHinhTB.INTERNET_ADSL)
        // {
        //     txtTTCuoc.Enabled = false;
        //     pnlFTTH.Visible = true;
        // }
        // else
        // {
        //     pnlFTTH.Visible = false;
        // }
        // if (dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT
        //     || dichvuvt_id == DichVuVienThong.DICHVU_CNTT
        //     || dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU
        //     || dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH)
        //     LoadThongTin_DV_CNTT(loaitb_id);
        // //ds_tien_khoanmuc = bangts.Lay_tienkhoanmuc_loaihd(LoaiHopDong.DAT_MOI);
        // DataTable dt = new DataTable();
        // dt = dsDanhMuc.Tables["TIEN_KM"].Copy();
        // dt.TableName = "danhsach";
        // ds_tien_khoanmuc.Tables.Remove("danhsach");
        // ds_tien_khoanmuc.Tables.Add(dt);
        // dt = null;
        // if (!kt_load)
        //     LayTienTheoKhoanMuc();
        // dsCTM_TBI = new CT_MUA_TBI_DATA();
        // HT_ThietBi();
        // HT_DichVuGT();
        // dtiendatcoc_km = 0;
        // dtiendatcoc_tt = 0;
        // dtienkmld = 0;
        // dtienkmdc = 0;
        // dvatkmld = 0;
        // dvatkmdc = 0;
        // dsHDTB_CT = new List<HD_THUEBAO_CT>();
        // HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);

        // KT_GOI_CBO_LOAIHINH = false;

        // //Thực hiện theo SRV https://cntt.vnpt.vn/browse/VNPSR-11947
        // txtTGDungThu.Text = "30";
        // if (vkieukh_id_ == KIEU_KH.DOANH_NGHIEP) //Khách hàng doanh nghiệp và kiểm tra thời gian lập hợp đồng từ ngày 20/05/2020-30/06/2020
        // {
        //     if (tt_nd.ngay_cn >= Convert.ToDateTime("20/05/2020") && tt_nd.ngay_cn <= Convert.ToDateTime("30/06/2020")
        //         && this.listbox.loaihinh_tb.value == LoaiHinhTB.INTERNET_FTTH)
        //     {
        //         txtTGDungThu.Text = "60";
        //     }
        // }
        // if (dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH
        //     || dichvuvt_id == DichVuVienThong.DICHVU_CNTT || dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU)
        // {
        //     if ((((DataRow)((cboLoaihinhTB.SelectedItem as DataRowView).Row))["CHOPHEP_DT"] ?? "0").ToString() == "1")
        //     {
        //         txtTGDungThu.Text = ((DataRow)((cboLoaihinhTB.SelectedItem as DataRowView).Row))["SONGAY_DT"].ToString();
        //     }
        // }
      },
      'listbox.trangbi.valuecd': function() { if(!this.watcherActive) return; this.LoadMucCuocTB(); },
      'NgayYeuCau': function() { if(!this.watcherActive) return; this.LoadMucCuocTB(); },
      // FIXME comment tam lai do form lag
      'diachiBC.DIACHI': function(newval) { if(newval) this.LoadNVThuCuoc(); },
      'diachiTT.DIACHI': function(newval) { if(newval)this.LoadNVThuCuoc(); },
      // 'diachiKH.DIACHI': function(oldval, newval) { if(newval)this.LoadNVThuCuoc(); },
      // //'listbox.donvi_ql.value': function(oldval, newval) { this.LoadNVThuCuoc(); }, // xem lai vi khong logic, da chuyen data sang dong duoi
      'listbox.donvi_ql.valueql': function(newval) { if(!this.watcherActive) return; if(newval) this.LoadNVThuCuoc(); },
      'listbox.donvi_ql.value': async function(newval) {
        if(!this.watcherActive) return;
        if(!newval) return;
        for(var i=0;i<this.DT_CONTROLS.list.length;i++)
        {
          if(this.DT_CONTROLS.list[i]['LOAIDV_DICH_ID']==LOAI_DV.TRAM_VT ||this.DT_CONTROLS.list[i]['LOAIDV_DICH_ID']==LOAI_DV.TRAM_CAP_PORT)
          await API.post_sp_lay_ds_tram_thicong_theo_donvi(this.axios, { "donvi_ql_id": newval, "loaidv_id": LOAI_DV.TRAM_VT }).then(
          (response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DT_CONTROLS.data[i] = this.UpperCasePropertyList(response.data.data);
              // if(!this.DT_CONTROLS.value[i] || this.DT_CONTROLS.value[i]<=0) {
              //   this.DT_CONTROLS.value[i] = this.DT_CONTROLS.data[i][0]['DONVI_ID'];
              // } else {
                var fdata = this.DT_CONTROLS.data[i].filter(x=>x.DONVI_ID==this.DT_CONTROLS.value[i] && x.DONVI_ID>0);
                if(fdata && fdata.length>0)
                  this.DT_CONTROLS.value[i] = fdata[0]['DONVI_ID'];
                else
                  this.DT_CONTROLS.value[i] = this.DT_CONTROLS.data[i][0]['DONVI_ID'];
              // }
              this.DT_CONTROLS.key += 1;
            }
          });
        }
      },

      'filterbox.tratruoc.ischecked': function(val) { if(!this.watcherActive) return; if(val && this.filterbox.tratruoc.list && this.filterbox.tratruoc.list.length>0) this.filterbox.tratruoc.value=this.filterbox.tratruoc.list[0].khuyenmai_id; else this.filterbox.tratruoc.value=0; },
      'filterbox.khuyenmai.ischecked': function(val) { if(!this.watcherActive) return; if(val && this.filterbox.khuyenmai.list && this.filterbox.khuyenmai.list.length>0) this.filterbox.khuyenmai.value=this.filterbox.khuyenmai.list[0].khuyenmai_id; else this.filterbox.khuyenmai.value=0; },

      'listbox.hinhthuc_tt.value': function(newval) { if(!this.watcherActive) return; if(newval) this.LoadNVThuCuoc(); },

      // 'tieunganh.search': function(val,oldval) {
      //   if(!this.watcherActive) return;
      //   try {
      //     this.loading(true);
      //     var found = false;
      //     if(!val && oldval) {
      //       if(this.tieunganh.dsTNC1 && this.tieunganh.dsTNC1.length>0 && this.tieunganh.TNC1_ID!=this.tieunganh.dsTNC1[0].tnc1_id) {
      //         this.tieunganh.TNC1_ID=this.tieunganh.dsTNC1[0].tnc1_id
      //         found = true;
      //       }
      //       if(!found && this.tieunganh.dsTNC2 && this.tieunganh.dsTNC2.length>0 && this.tieunganh.TNC2_ID!=this.tieunganh.dsTNC2[0].tnc2_id) {
      //         this.tieunganh.TNC2_ID=this.tieunganh.dsTNC2[0].tnc2_id
      //         found = true;
      //       }
      //       if(!found && this.tieunganh.dsTNC3 && this.tieunganh.dsTNC3.length>0 && this.tieunganh.TNC3_ID!=this.tieunganh.dsTNC3[0].tnc3_id) {
      //         this.tieunganh.TNC3_ID=this.tieunganh.dsTNC3[0].tnc3_id
      //         found = true;
      //       }
      //       if(!found && this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0 && this.tieunganh.TNC4_ID!=this.tieunganh.dsTNC4[0].tnc4_id) {
      //         this.tieunganh.TNC4_ID=this.tieunganh.dsTNC4[0].tnc4_id
      //         found = true;
      //       }
      //     }
      //     else {
      //       var dsDL = [];
      //       var cid = -1;
      //       if(this.tieunganh.dsTNC1 && this.tieunganh.dsTNC1.length>0) {
      //         cid = this.tieunganh.TNC1_ID;
      //         dsDL = this.tieunganh.dsTNC1.filter(x=>((x.tnc1_id==val || x.ten_tnc1.indexOf(val)>=0) && x.tnc1_id!=cid));
      //         if(dsDL && dsDL.length>0) {
      //           this.tieunganh.TNC1_ID = dsDL[0].tnc1_id;
      //           found = true;
      //         }
      //       }
      //       if(!found && this.tieunganh.dsTNC2 && this.tieunganh.dsTNC2.length>0) {
      //         cid = this.tieunganh.TNC2_ID;
      //         dsDL = this.tieunganh.dsTNC2.filter(x=>((x.tnc2_id==val || x.ten_tnc2.indexOf(val)>=0) && x.tnc2_id!=cid));
      //         if(dsDL && dsDL.length>0) {
      //           this.tieunganh.TNC2_ID = dsDL[0].tnc2_id;
      //           found = true;
      //         }
      //       }
      //       if(!found && this.tieunganh.dsTNC3 && this.tieunganh.dsTNC3.length>0) {
      //         cid = this.tieunganh.TNC3_ID;
      //         dsDL = this.tieunganh.dsTNC3.filter(x=>((x.tnc3_id==val || x.ten_tnc3.indexOf(val)>=0) && x.tnc3_id!=cid));
      //         if(dsDL && dsDL.length>0) {
      //           this.tieunganh.TNC3_ID = dsDL[0].tnc3_id;
      //           found = true;
      //         }
      //       }
      //       if(!found && this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0) {
      //         cid = this.tieunganh.TNC4_ID;
      //         dsDL = this.tieunganh.dsTNC4.filter(x=>((x.tnc4_id==val || x.ten_tnc4.indexOf(val)>=0) && x.tnc4_id!=cid));
      //         if(dsDL && dsDL.length>0) {
      //           this.tieunganh.TNC4_ID = dsDL[0].tnc4_id;
      //           found = true;
      //         }
      //       }
      //     }
      //   } catch(ex) {} finally { this.loading(false); }
      // },

      // 'tieunganh.TNC1_ID':function(newval) {
      //   if(!this.watcherActive) return;
      //   API.post_lay_ds_tieunganh(this.axios, { "vkieu":2, "vnganhnghe_id": this.listbox.nganhngheloc.value, "vtc_id": newval }).then((response) => {
      //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
      //       this.tieunganh.dsTNC2 = response.data.data;
      //       if(this.tieunganh.dsTNC2 && this.tieunganh.dsTNC2.length>0) this.tieunganh.TNC2_ID=this.tieunganh.dsTNC2[0].tnc2_id;
      //       else this.tieunganh.TNC2_ID = 0;
      //     }
      //     else { this.tieunganh.dsTNC2 = []; this.tieunganh.TNC2_ID = 0; }
      //   });
      // },
      // 'tieunganh.TNC2_ID':function(newval) {
      //   if(!this.watcherActive) return;
      //   API.post_lay_ds_tieunganh(this.axios, { "vkieu":3, "vnganhnghe_id": this.listbox.nganhngheloc.value, "vtc_id": newval }).then((response) => {
      //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
      //       this.tieunganh.dsTNC3 = response.data.data;
      //       if(this.tieunganh.dsTNC3 && this.tieunganh.dsTNC3.length>0) this.tieunganh.TNC3_ID=this.tieunganh.dsTNC3[0].tnc3_id;
      //       else this.tieunganh.TNC3_ID = 0;
      //     }
      //     else { this.tieunganh.dsTNC3 = []; this.tieunganh.TNC3_ID = 0; }
      //   });
      // },
      // 'tieunganh.TNC3_ID':function(newval) {
      //   if(!this.watcherActive) return;
      //   API.post_lay_ds_tieunganh(this.axios, { "vkieu":4, "vnganhnghe_id": this.listbox.nganhngheloc.value, "vtc_id": newval }).then((response) => {
      //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
      //       this.tieunganh.dsTNC4 = response.data.data;
      //       if(this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0) this.tieunganh.TNC4_ID=this.tieunganh.dsTNC4[0].tnc4_id;
      //       else this.tieunganh.TNC4_ID;
      //     }
      //     else { this.tieunganh.dsTNC4 = []; this.tieunganh.TNC4_ID = 0; }
      //   });
      // },
      // 'tieunganh.TNC4_ID':function(newval) {
      //   if(!this.watcherActive) return;
      //   API.post_lay_ds_tieunganh(this.axios, { "vkieu":5, "vnganhnghe_id": this.listbox.nganhngheloc.value, "vtc_id": newval }).then((response) => {
      //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
      //       this.tieunganh.TNCMOTA = response.data.data.map(x=>x.mota).join("\n");
      //     } else this.tieunganh.TNCMOTA = "";
      //   });
      // },

      'diachiTT': {
        handler: function(oldval,newval) { //Đảo ngược do code bên dưới bị ngược
          // ### recent
          if(!this.watcherActive) return;
          var pdata = {"DONVIQL_HTTT":this.DONVIQL_HTTT,"TINH_ID":oldval.TINH_ID,"DACDIEM_ID":oldval.DACDIEM_ID,"QUANG_ID":oldval.QUAN_ID,"PHUONG_ID":oldval.PHUONG_ID,"PHO_ID":oldval.PHO_ID,"AP_ID":oldval.AP_ID,"KHU_ID":oldval.KHU_ID,"LOAIKH_ID":this.listbox.loai_kh.value,"HTTT_ID":this.listbox.hinhthuc_tt.value,"PHANTAP_KH":this.PHANTAP_KH,"TINHTT_ID":newval.TINH_ID,"QUANTT_ID":newval.QUAN_ID,"PHUONGTT_ID":newval.PHUONG_ID,"DACDIEMTT_ID":newval.DACDIEM_ID,"PHOTT_ID":newval.PHO_ID,"APTT_ID":newval.AP_ID,"KHUTT_ID":newval.KHU_ID,"KHACHHANG_ID":this.khachhang.khachhang_id,"NGANHNGHELOC":this.listbox.nganhngheloc.value,"COUNT_DVI_QL_ID":this.listbox.donvi_ql.length }
          API.post_fn_lay_tt_diachi_thanhtoan(this.axios,{"ds_para": JSON.stringify(pdata) }).then(
          (response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              var apidata = JSON.parse(response.data.data);
              if(apidata['RESULT']) apidata = apidata['RESULT'];
              if(apidata['DS_DVI'])
                this.XuLyDonViQL(apidata['DS_DVI']);
            }
          });
        },
        deep: true,
      },

      'listbox.loai_kh.value': async function(newval) {
        if(!this.watcherActive) return;
        try {
        if(newval==-1) return;
        //TODO bo qua dong 31562-31567 do khong phai CO_DINH, thuoc luong DI_DONG
        var drPLKH = this.listbox.phanloai_kh.list.filter(x=>x.loaikh_id==newval);
        if (this.nhomplkh <= 1)//chỉ thay đổi nếu nhóm pl là ko theo doanh thu
        {
            this.PHANLOAIKH_ID = 0;
            this.khachhang.plkh = "";

            if (drPLKH.length > 0)
            {
                this.PHANLOAIKH_ID = drPLKH[0]["phanloaikh_id"];
                this.khachhang.plkh = drPLKH[0]["ma_plkh"];
            }
            else
            {
                this.vkieukh_id_ = 0;
                var kieukh_id = 0;
                var kieukh = "";

                kieukh = this.listbox.loai_kh.list.filter(x=>x.loaikh_id==newval)[0]['loai'];//.Select("LOAIKH_ID=" + cboLoaiKH.EditValue)[0]["LOAI"];

                if (kieukh)
                {
                    this.vkieukh_id_ = kieukh_id = kieukh;
                }

                if (kieukh_id == KIEU_KH.CA_NHAN)
                {
                    var dr_PLKH = this.listbox.phanloai_kh.list.filter(x=>x.hienthi_md==PHANLOAI_KH.CA_NHAN);//dsDanhMuc.Tables["PHANLOAI_KH"].Select("hienthi_md = " + PHANLOAI_KH.CA_NHAN);
                    if (dr_PLKH.length > 0)
                    {
                        this.PHANLOAIKH_ID = dr_PLKH[0]["phanloaikh_id"];
                        this.khachhang.plkh = dr_PLKH[0]["ma_plkh"];
                    }
                }
                else
                {

                    var dr_PLKH = this.listbox.phanloai_kh.list.filter(x=>x.hienthi_md==PHANLOAI_KH.DOANH_NGHIEP);
                    if (dr_PLKH.length > 0)
                    {
                        this.PHANLOAIKH_ID = Convert.ToInt32(dr_PLKH[0]["phanloaikh_id"]);
                        this.khachhang.plkh = dr_PLKH[0]["ma_plkh"];
                    }
                }
            }
        }
        else
        {
            //region Kiểm tra mã ngân sách
            var kieukh = this.listbox.loai_kh.list.filter(x=>x.loaikh_id==newval)[0]['loai'];//.Select("LOAIKH_ID=" + cboLoaiKH.EditValue)[0]["LOAI"];
            if (kieukh)
                this.vkieukh_id_ = kieukh;
        }
        this.thuebao.tgdungthu = 30;
        //Thực hiện theo SRV https://cntt.vnpt.vn/browse/VNPSR-11947
        if (this.vkieukh_id_ == KIEU_KH.DOANH_NGHIEP) //Khách hàng doanh nghiệp và kiểm tra thời gian lập hợp đồng từ ngày 20/05/2020-30/06/2020
        {
            if ((new Date()).getTime() >= (new Date(2020,5,20)).getTime() && (new Date()).getTime() <= (new Date(2020,6,30)).getTime()
                && this.listbox.loaihinh_tb.value == LoaiHinhTB.INTERNET_FTTH)
            {
                this.thuebao.tgdungthu = 60;
            }
        }

        // hoangpkn : 25/10/2017 Thêm nhân viên quản lý
        var pdata = { "ds_para": JSON.stringify({"PHANTAP_KH":this.PHANTAP_KH,"DICHVUVT_ID":this.listbox.dichvu_vt.value,"LOAIKH_ID":this.listbox.loai_kh.value,"PHUONGTT_ID":this.diachiTT.PHUONG_ID?this.diachiTT.PHUONG_ID:0,"PHOTT_ID":this.diachiTT.PHO_ID?this.diachiTT.PHO_ID:0,"APTT_ID":this.diachiTT.AP_ID?this.diachiTT.AP_ID:0,"KHUTT_ID":this.diachiTT.KHU_ID?this.diachiTT.KHU_ID:0,"DONVIQL_HTTT":this.DONVIQL_HTTT,"HTTT_ID":this.listbox.hinhthuc_tt.value,"KHACHHANG_ID":this.khachhang.khachhang_id,"DACDIEMTT_ID":this.diachiTT.DACDIEM_ID?this.diachiTT.DACDIEM_ID:0,"CBO_DVIQL_COUNT":1,"NGANHNGHELOC_ID":this.listbox.nganhngheloc.value,"DOITUONG_ID":this.listbox.doituong.value}) } //CBO_DVIQL_COUNT la dem so luong item con trong combobox, vi so luong luon >0 nen de default 1
        await API.post_fn_lay_tt_combo_loaikh(this.axios, pdata).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            var apidata = JSON.parse(response.data.data);
            if(apidata['RESULT']) apidata = apidata['RESULT'];
            if(apidata['DONVQL_ID']) this.listbox.donvi_ql.valueql=apidata['DONVQL_ID'];
            if(apidata['DS_CT_KM']) this.dsKM_HDTB = apidata['DS_CT_KM'];
            if(apidata['DS_DVI'])
              this.XuLyDonViQL(apidata['DS_DVI']);
          }
        });
        if(this.diachiTT.PHUONG_ID!=0 && (this.diachiTT.PHO_ID!=0 || this.diachiTT.AP_ID!=0 || this.diachiTT.KHU_ID!=0))
        {
          this.LoadNVThuCuoc();
            // HT_NVTC_TheoDC(quantt_id, phuongtt_id, phott_id, aptt_id, khutt_id, Convert.ToInt32(cboDonViQL.SelectedValue), dacdiemtt_id); //Tuong duong ham tren

            if (this.DONVIQL_HTTT == 1)
                this.HienThi_NVQL_AM_HTTT();
            else
                this.HienThi_NVQL_AM();
        }

        // #region Lấy chi tiết khuyến mại dùng thử fill vào khuyenmai_hdtb
        if (this.listbox.loai_kh.value != -1)
        {
          if (this.listbox.doituong.value == DUNG_THU.DOITUONG_TB)
          {
            var dsTemp = [];
            await API.post_lay_chitiet_km_dungthu_v2(this.axios, {"vloaikh_id":this.listbox.loai_kh.value}).then(
            (response) => {
              if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                dsTemp = response.data.data;
              }
            });

            if (dsTemp.length>0)
            {
              if (dsTemp[0]["khuyenmai_id"])
              {
                this.filterbox.khuyenmai.value = dsTemp[0]["khuyenmai_id"];
                this.filterbox.khuyenmai.ischecked = true;
              }
              else
                this.filterbox.khuyenmai.ischecked = false;

              // DateTime tmp;
              var ithang_huongdc = 0, ithang_huongkm = 0, ithang_tg = 0;
              var dtiendatcoc_csd = 0, dtien_td = 0;
              this.dsKM_HDTB = [];

              for (var i = 0; i < dsTemp.length; i++)
              {
                ithang_huongdc = 0; ithang_huongkm = 0; ithang_tg = 0;
                dtiendatcoc_csd = 0; dtien_td = 0;
                var dr = {};
                if(dsTemp[i]["huong_dc"]) ithang_huongdc = dsTemp[i]["huong_dc"];
                if(dsTemp[i]["huong_km"]) ithang_huongkm = dsTemp[i]["huong_km"];
                if(dsTemp[i]["sothang_tg"]) ithang_tg = dsTemp[i]["sothang_tg"];
                if(dsTemp[i]["datcoc_csd"]) dtiendatcoc_csd = dsTemp[i]["datcoc_csd"];
                if(dsTemp[i]["tien_td"]) dtien_td = dsTemp[i]["tien_td"];
                dr.THANG_HUONGDC = ithang_huongdc;
                dr.THANG_HUONGKM = ithang_huongkm;
                dr.SOTHANG_TG = ithang_tg;
                dr.DATCOC_CSD = dtiendatcoc_csd;
                dr.TIEN_TD = dtien_td;

                if (dsTemp[i]["kieu_bdkm"] == "3")
                {
                  try {
                    var tmp = moment(dsTemp[i]["thang_bd"], 'YYYYMM').add(ithang_huongkm,'M').format("YYYYMM");
                    dr.THANG_BD = dsTemp[i]["thang_bd"];
                    dr.THANG_KT = tmp;
                  } catch(ex) {}
                }
                if (dsTemp[i]["kieu_bdtg"] == "3")
                {
                  try {
                    var tmp = moment(dsTemp[i]["thang_bdtg"], 'YYYYMM').add(ithang_tg,'M').format("YYYYMM");
                    dr.THANG_BDTG = dsTemp[i]["thang_bdtg"];
                    dr.THANG_KTTG = tmp;
                  } catch(ex) {}
                }
                if (dsTemp[i]["kieu_bddc"] == "3")
                {
                  try {
                    var tmp = moment(dsTemp[i]["thang_bddc"], 'YYYYMM').add(ithang_huongkm,'M').format("YYYYMM");
                    dr.THANG_BDDC = dsTemp[i]["thang_bddc"];
                    dr.THANG_KTDC = tmp;
                  } catch(ex) {}
                }
                //longdx: bổ sung thông tin tỷ lệ km
                dr.TYLE_KMLD = dsTemp[i]["tyle_kmld"];
                dr.TYLE_SD = dsTemp[i]["tyle_sd"];
                dr.TYLE_TB = dsTemp[i]["tyle_tb"];
                dr.HDTB_ID = this.hdtb_id;//-1;
                dr.CHITIETKM_ID = dsTemp[i]["chitietkm_id"];
                dr.KHUYENMAI_ID = dsTemp[i]["khuyenmai_id"];
                //dr.KHUYENMAI_ID_NEO = dsTemp[i]["khuyenmai_id_neo"];
                dr.KIEU_YC = 1;
                this.dsKM_HDTB.push(dr);

                //Lấy tiền khuyến mại
                if (this.kmld.tien_kmld < dsTemp[i]["tien_kmld"])
                    this.kmld.tien_kmld = dsTemp[i]["tien_kmld"];
                if (this.kmld.vat_kmld < dsTemp[i]["vat_kmld"])
                    this.kmld.vat_kmld = dsTemp[i]["vat_kmld"];
                if (this.kmld.tyle_kmld < dsTemp[i]["tyle_kmld"])
                    this.kmld.tyle_kmld = dsTemp[i]["tyle_kmld"];

                if (this.kmld.tien_datcoc < dsTemp[i]["datcoc_csd"])
                    this.kmld.tien_datcoc = dsTemp[i]["datcoc_csd"];
              }
            }
          }
        }
        // #endregion
        } catch(ex) {
          var dsPL = this.listbox.phanloai_kh.list.filter(x=>x.hienthi_md==PHANLOAI_KH.CA_NHAN);
          if (dsPL && dsPL.length > 0)
          {
            this.PHANLOAIKH_ID = dsPL[0]['phanloaikh_id'];
            this.khachhang.plkh = dsPL[0]['ma_plkh'];
          }
        }
      },

      'listbox.kieu_ld.value': function(newval) {
        if(!this.watcherActive) return;
        this.defaultCongNghe(newval);
        this.HT_GIAODIEN_DONVI();
      },

      'buttonSet':function(newval) {
        if(!this.watcherActive) return;
        switch(newval) {
          case -1:
            this.filterbox.ctv.ischecked = false;
            this.filterbox.nguoigt.ischecked = false;
            this.listbox.madaidien.value = 0;
            this.listbox.madaidien.list = [];
            this.buttons.tsbtnGhiLai = true;
            this.buttons.tsbtnHuyBo = true;
            break;
          case 0:
            this.watcherActive = false;
            Object.assign(this.$data, this.$options.data.call(this));
            this.buttonSet=0;
            this.FormLoad();
            this.buttons.tsbtnNhapMoi = true;
            break;
          case 1:
            this.watcherActive = false;
            Object.assign(this.$data, this.$options.data.call(this));
            this.buttonSet=1;
            this.FormLoad();
            this.buttons.tsbtnGhiLai = true;
            this.buttons.tsbtnHuyBo = true;
            this.buttons.tsbtnThemTT = false;
            this.buttons.tsbtnXoaTT = false;
            break;
          case 2: // Nut huy 18281
            this.watcherActive = false;
            Object.assign(this.$data, this.$options.data.call(this));
            this.buttonSet=2;
            this.FormLoad();
            this.buttons.tsbtnNhapMoi = true;
            this.buttons.tsbtnXoa = true;
            break;
          case 3: // Nut edit
            this.buttons.tsbtnNhapMoi = true;
            this.buttons.tsbtnXoa = true
            this.buttons.tsbtnGhiLai = true
            this.buttons.tsbtnHuyBo = true
            this.buttons.tsbtnXoaTB = true
            this.buttons.tsbtnThemTB = true
            this.buttons.tsbtnThanhToan = true
            this.buttons.inPhụLụcToolStripMenuItem = false
            break;
          case 4:
            this.buttons.tsbtnGhiLai = true;
            this.buttons.tsbtnHuyBo = true;
            break;
        }
      },
      'listbox.dichvu_vt.value':async function(newval,oldval) {
        if(!this.watcherActive) return;
        if(!(newval!= oldval && newval>0)) return;
        this.loading(true);
        try {
          if([DichVuVienThong.MEGAWAN, DichVuVienThong.TSL, DichVuVienThong.METRONET, DichVuVienThong.MGW_DOITAC, DichVuVienThong.TSL_DOITAC].indexOf(parseInt(newval))>=0)
          {
            if(!this.kiemtra_dm_mgwan_tsl){
              var daivt_id = this.listbox.donvi_ql.value;
              await API.post_lay_danhmuc_lapmoi_mgw_tsl(this.axios,{ "vchuoi": "LOAI_KENH|TOCDO_KENH|LOAI_CAP|THOIHAN|DOITAC|TINH_KN|LOAI_MD|TRAM_VT_DAU-donvi_id = "+daivt_id+"|TRAM_VT_CUOI-donvi_id = "+daivt_id }).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.listbox.donvi_dau.list = response.data.data['tram_vt_dau'];
                  if(this.listbox.donvi_dau.list && this.listbox.donvi_dau.list.length>0)
                    this.listbox.donvi_dau.value = this.listbox.donvi_dau.list[0].donvi_id;
                  this.listbox.donvi_cuoi.list = response.data.data['tram_vt_cuoi'];
                  if(this.listbox.donvi_cuoi.list && this.listbox.donvi_cuoi.list.length>0)
                    this.listbox.donvi_cuoi.value = this.listbox.donvi_cuoi.list[0].donvi_id;
                  this.listbox.tinh_kn.list = response.data.data['tinh_kn'];
                  if(this.listbox.tinh_kn.list && this.listbox.tinh_kn.list.filter(x=>x.tinh_id>0).length>0)
                    this.listbox.tinh_kn.value = this.listbox.tinh_kn.list.filter(x=>x.tinh_id>0)[0].tinh_id;
                  try { this.listbox.tinh_kn.value = this.$root.token.getPhanVungID(); } catch(ex) {}
                  this.listbox.loaikenh.list = response.data.data['loai_kenh'];
                  if(this.listbox.loaikenh.list && this.listbox.loaikenh.list.length>0)
                    this.listbox.loaikenh.value = this.listbox.loaikenh.list[0].loaikenh_id;
                  this.listbox.tocdokenh.list = response.data.data['tocdo_kenh'];
                  if(this.listbox.tocdokenh.list && this.listbox.tocdokenh.list.length>0)
                    this.listbox.tocdokenh.value = this.listbox.tocdokenh.list[0].tocdo_id;
                  this.listbox.doitactsl.list = response.data.data['doitac'];
                  if(this.listbox.doitactsl.list && this.listbox.doitactsl.list.length>0)
                    this.listbox.doitactsl.value = this.listbox.doitactsl.list[0].doitac_id;
                  this.listbox.doitacwan.list = response.data.data['doitac'];
                  if(this.listbox.doitacwan.list && this.listbox.doitacwan.list.length>0)
                    this.listbox.doitacwan.value = this.listbox.doitacwan.list[0].doitac_id;
                  this.listbox.loaimodem.list = response.data.data['loai_md'];
                  if(this.listbox.loaimodem.list && this.listbox.loaimodem.list.length>0)
                    this.listbox.loaimodem.value = this.listbox.loaimodem.list[0].loaimd_id;
                  this.listbox.loaicap.list = response.data.data['loai_cap'];
                  if(this.listbox.loaicap.list && this.listbox.loaicap.list.length>0)
                    this.listbox.loaicap.value = this.listbox.loaicap.list[0].loaicap_id;
                }
              });
              if(this.thamso_nguoidung.chuquanmd_id) { // Tham so nay khong lay duoc tu DB o thoi diem code do khong co du lieu (select * from admin.ct_tsmd where ma_ts='CHUQUAN_ID' and phanvung_id=26;)
                this.listbox.chu_quan.valuewan = this.thamso_nguoidung.chuquanmd_id;
                this.listbox.chu_quan.valuetsl = this.thamso_nguoidung.chuquanmd_id;
              }
              this.kiemtra_dm_mgwan_tsl = true;

              //FORMLOAD default value
              if(this.listbox.phanloai_dv.list && this.listbox.phanloai_dv.list.filter(pl=>(pl.loaitb_id==134)).length>0)
                this.listbox.phanloai_dv.value = this.listbox.phanloai_dv.list.filter(pl=>(pl.loaitb_id==134))[0].phanloai_id;
              if(this.listbox.loai_tb.list && this.listbox.loai_tb.list.filter(tb=>tb.dichvuvt_id==this.listbox.dichvu_vt.value).length>0)
                this.listbox.loai_tb.valuemgwan = this.listbox.loai_tb.list.filter(tb=>tb.dichvuvt_id==this.listbox.dichvu_vt.value)[0].thietbidc_id;
              this.loaisowan = 1;

              //if([DichVuVienThong.MEGAWAN, DichVuVienThong.METRONET, DichVuVienThong.MGW_DOITAC].indexOf(parseInt(newval))>=0)
            }
            //Default thuebao
            this.listbox.loaihinh_tb.value = LoaiHinhTB.MEGAWAN_ADSL;// 23;
            this.listbox.kieu_ld.value = KieuLapDat.DM_MGW_DOCLAP;// 42
          }
          if(newval==DichVuVienThong.CO_DINH) {
            if(!this.kiemtra_dm_codinh) {
              this.kiemtra_dm_codinh = true;
              this.listbox.chu_quan.valuecd = this.thamso_nguoidung.chuquanmd_id;
              this.listbox.chu_quan.valuedlcd = this.thamso_nguoidung.chuquanmd_id;
            }
            //Default thuebao
            this.listbox.loaihinh_tb.value = LoaiHinhTB.DIENTHOAI_CD;
            this.listbox.kieu_ld.value = KieuLapDat.DM_CODINH;
          }
          if(newval==DichVuVienThong.IMS || newval==DichVuVienThong.IMS_DOITAC){
            if(!this.kiemtra_dm_ims) {
              this.kiemtra_dm_ims=true;
              this.listbox.chu_quan.valueims = this.thamso_nguoidung.chuquanmd_id;
              this.listbox.chu_quan.valuedlims = this.thamso_nguoidung.chuquanmd_id;
            }
          }
          if([DichVuVienThong.ADSL, DichVuVienThong.MEGA_EYES, DichVuVienThong.HATANG_VIENTHONG, DichVuVienThong.BRCD_DOITAC].indexOf(parseInt(newval))>=0){
            if(!this.kiemtra_dm_adsl){
              this.kiemtra_dm_adsl=true;
              await API.post_lay_danhmuc_lapmoi_internet(this.axios,{ "vchuoi": "KIEUTRA|THANGTRA|MUCCUOC" }).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.listbox.cboKieuTra.list = response.data.data['kieutra'];
                  if(this.listbox.cboKieuTra.list && this.listbox.cboKieuTra.list.length>0) this.listbox.cboKieuTra.value = this.listbox.cboKieuTra.list[0].kieutra_id;
                  this.listbox.cboThangTra.list = response.data.data['thangtra'];
                  if(this.listbox.cboThangTra.list && this.listbox.cboThangTra.list.length>0) this.listbox.cboThangTra.value = this.listbox.cboThangTra.list[0].thang_id;
                  this.listbox.muccuoc.list = response.data.data['muccuoc'];
                  if(this.listbox.muccuoc.list && this.listbox.muccuoc.list.length>0) this.listbox.muccuoc.value = this.listbox.muccuoc.list[0].muccuoc_id;
                  if(this.thamso_nguoidung.chuquanmd_id && this.thamso_nguoidung.chuquanmd_id>0) {
                    this.listbox.chu_quan.valueadsl = this.thamso_nguoidung.chuquanmd_id;
                    this.listbox.chu_quan.valuedladsl = this.thamso_nguoidung.chuquanmd_id;
                  }
                  else {
                    this.listbox.chu_quan.valueadsl = this.listbox.chu_quan.list[0].chuquan_id;
                    this.listbox.chu_quan.valuedladsl = this.listbox.chu_quan.list[0].chuquan_id;
                  }
                }
              });
            }
            //Default thuebao
            this.listbox.loaihinh_tb.value = LoaiHinhTB.INTERNET_FTTH;// 58;
            this.listbox.kieu_ld.value = KieuLapDat.THUE_BAO_QUANG;// 51
          }
          if(newval==DichVuVienThong.DI_DONG)
          {
            if(!this.kiemtra_dm_didong){
              this.kiemtra_dm_didong = true;
              this.listbox.chu_quan.valuedd = 11;
            }
          }
          if(newval==DichVuVienThong.GPHONE)
          {
            if(!this.kiemtra_dm_gphone){
              this.kiemtra_dm_gphone = true;
              //this.listbox.chu_quan.valuegp = this.thamso_nguoidung.chuquanmd_id;
            }
          }
          switch(newval*1) {
            case DichVuVienThong.ADSL:
            case DichVuVienThong.BRCD_DOITAC:
              this.tocdo_id = 0;
              if(this.thuebao.adsl.ckTuCaiADSL)
                this.kh_cd = 1;
              else this.kh_cd = 0;
              //if(this.listbox.loai)
              break;
          }
        }catch(ex) { this.$toast.error("" + ex); if(this.debug) console.error(ex); } finally {
          await this.HT_GIAODIEN_DONVI();
          await this.HT_DS_KhuyenMai_Combobox();
          this.loading(false);
        }
      }
}
