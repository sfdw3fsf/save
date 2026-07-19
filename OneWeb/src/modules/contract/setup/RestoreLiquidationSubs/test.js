async capnhat(){
    try {
      if (this.tsbtnNhapMoi.Enabled == false) {
        var rsLapHDKhac = await api.kiemtra_ma_tb_lap_hopdong_khac(this.txtMaTB.Text.trim(), "0", Number(this.cboDichVuVT.SelectedValue), LOAI_HOPDONG.KHOIPHUC_SD)
        rsLapHDKhac = rsLapHDKhac.filter(r => r.loaihd_id == LOAI_HOPDONG.KHOIPHUC_SD)
        if (rsLapHDKhac && rsLapHDKhac.length > 0) {
          this.$toast.error('Thuê bao đã lập hợp đồng khác: \n - Tên hợp đồng: ' + rsLapHDKhac[0]['ten_loaihd'])
          this.loading(false)
          return
        }
        var statusVdc = false
        if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL) {
          var loaihinhTB = Number(this.cboLoaihinhTB.SelectedValue)
          if (loaihinhTB == LOAIHINH_THUEBAO.INTERNET_1260 || loaihinhTB == LOAIHINH_THUEBAO.INTERNET_ADSL || loaihinhTB == LOAIHINH_THUEBAO.INTERNET_FTTH || loaihinhTB == LOAIHINH_THUEBAO.WIFI_FIBER) {
          }
          // TODO: goi service ??? object[] ketqua = vdcService.TraCuuThueBao(txtMaTB.Text.trim())
          // if (!KiemTraAccountVisa()) return
        }
        var dshg = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id, this.tocdo_id, this.muccuoc_id, Number(this.cboKieuLD.SelectedValue), 1, Number(this.cboDichVuVT.SelectedValue))
        var huonggiaoTN = 0
        this.quytrinh_id = 0
        this.luong_id = 0
        if (dshg.length <= 0) {
          this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          this.loading(false)
          return
        }
        huonggiaoTN = dshg[0]['huonggiao_id'] ? Number(dshg[0]['huonggiao_id']) : 0
        this.quytrinh_id = dshg[0]['quytrinh_id'] ? Number(dshg[0]['quytrinh_id']) : 0
        this.luong_id = dshg[0]['luong_id'] ? Number(dshg[0]['luong_id']) : 0
        var result = await this.TaoDuLieu(true)
        if (!result) {
          this.$toast.error('Lỗi tạo dữ liệu hđ thanh lý!')
          this.loading(false)
          return
        }
        if (this.loi_sinh_magd) {
          this.loi_sinh_magd = false
          this.loading(false)
          return
        }
        // #region Kích hoạt Account dùng thử lên Visa
        // TODO: goi web service ???
        // if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL)
        // {
        //     var loaihinh_tb = Number(this.cboLoaihinhTB.SelectedValue)
        //     if (loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_1260 || loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_ADSL || loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_FTTH || loaihinh_tb == LOAIHINH_THUEBAO.WIFI_FIBER)
        //     {
        //         if (DONGBO_TEST.TEST == false)
        //         {
        //             VDCService vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa)
        //             var account = this.txtMaTB.Text.trim()
        //             var contex = objCheckData.MAP_ID('ma_lhtb_vdc', 'loaihinh_tb', 'where loaitb_id =' + this.cboLoaihinhTB.SelectedValue.toString())
        //             //nhapt : 17/01/2018: thực hiện hủy đặt chỗ và đặt chỗ lại
        //             #region "Detail :nhapt : 17/01/2018"
        //             //vì trước khi cập nhật đã kiểm tra thuê bao đó đã được sử dụng hay chưa nên k sợ ảnh hưởng đến trường hợp đã sử dụng
        //             //nhiều trường hợp xảy ra; đặt chỗ khác phương thức hoặc user
        //             //nên chương trình k đặt chỗ lại được nữa
        //             //đã thực hiện test trên thuê bao đã đặt chỗ và chưa đặt chỗ
        //             //=> trả về thành công
        //             #endregion
        //             vdcService.HuyDatChoMegaVNN(account)
        //             statusVdc = vdcService.DatChoMegaVNN(account, contex, this.$auth.getNgayCapNhat().AddDays(7))
        //             if (!statusVdc)
        //             {
        //                 this.$toast.error('Có lỗi xảy ra trong quá trình đặt chỗ Account " + account + " lên Visa.Bạn hãy vui lòng thực hiện lại chức năng này!')
        //                 return
        //             }
        //             check_tb_adsl = true;//Hiếu thêm biến kiểm tra thuê bao ADSL đã được đặt chỗ hay chưa. 5.10.2010
        //         }
        //         else
        //         {
        //             statusVdc = true
        //             check_tb_adsl = true
        //         }
        //     }
        // }
        // #region Insert dữ liệu vào DataBase và hiển thị
        // TODO: chua co api
        // - khoiphuc.ThemMoi_HopDong_V2
        // - khoiphuc.ThemMoi_HopDong_V3
        // if (CHUYENDOI_LH_DUNGTHU == 1)
        //     khoiphuc.ThemMoi_HopDong_V3(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, huonggiao_tn, dsHDTB_IMS, dsHDTB_KV, dsHDTB_CNTT, dsHDTB_DUNGTHU, dsHDTB_TSL)
        // else
        //     khoiphuc.ThemMoi_HopDong_V2(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, huonggiao_tn, dsHDTB_IMS, dsHDTB_KV, dsHDTB_CNTT, dsHDTB_TSL)
        var rs = await api.themmoi_hopdong_khoiphucthanhly_v2(this.dsHDKH, this.dsHDTB, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsDK_DVGT, this.dsDK_DVKHAC, this.dsCTTHD, this.dsHDTBDV, huonggiaoTN, this.dsHDTB_IMS, this.dsHDTB_KV, this.dsHDTB_CNTT, this.dsHDTB_TSL)
        if (rs.error_code != SUCCESS_CODE) {
          this.$toast.error(rs.message)
          this.loading(false)
          return
        }
        if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL && statusVdc == true) {
          // Gọi hàm cập nhật trạng thái HDTB = 1: Đã đặt chỗ thành công trên VDC
          // obj.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
          await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
        }
        this.$toast.success('Thêm mới hợp đồng thành công !')
        try {
          // TODO: cho update api khoiphuc.Capnhat_goikt(dsHDTB)
          // khoiphuc.Capnhat_goikt(dsHDTB)
        } catch (Exception) {
        }
        // var dsHD = await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
        var dsHD = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TRANGTHAI_HOPDONG.MOI)
        dsHD = dsHD.map(r => this.formatJson(r))
        this.hienThiDanhSacHDTB(dsHD)
        this.setButton(3)      
        this.loading(false)
        return
      } else {
        // DataSet dshg = bangts.LayHuongGiao_TiepNhan(LOAI_HOPDONG.KHOIPHUC_SD, loaitb_id, tocdo_id, muccuoc_id, Number(this.cboKieuLD.SelectedValue))
        var dshg = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id, this.tocdo_id, this.muccuoc_id, Number(this.cboKieuLD.SelectedValue), 1, Number(this.cboDichVuVT.SelectedValue))
        var huonggiaoTN = 0 // TODO: đợi uncomment bên dưới
        this.quytrinh_id = 0
        this.luong_id = 0
        if (dshg.length <= 0) {
          this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          this.loading(false)
          return
        }
        huonggiaoTN = dshg[0]['huonggiao_id'] ? Number(dshg[0]['huonggiao_id']) : 0 // TODO: đợi uncomment bên dưới
        this.quytrinh_id = dshg[0]['quytrinh_id'] ? Number(dshg[0]['quytrinh_id']) : 0
        this.luong_id = dshg[0]['luong_id'] ? Number(dshg[0]['luong_id']) : 0
        var result = await this.TaoDuLieu(false)
        if (!result) {
          this.$toast.error('Lỗi tạo dữ liệu hđ thanh lý!')
          this.loading(false)
          return
        }
        // #region Đặt chỗ lại cho Account ADSL
        // TODO: goi server ???
        // if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL)
        // {
        //     var loaihinh_tb = Number(this.cboLoaihinhTB.SelectedValue)
        //     if (loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_1260 || loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_ADSL || loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_FTTH || loaihinh_tb == LOAIHINH_THUEBAO.WIFI_FIBER)
        //     {
        //         if (DONGBO_TEST.TEST == false)
        //         {
        //             VDCService vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa)
        //             string account = this.txtMaTB.Text.trim()
        //             if (account.trim().length == 0)
        //             {
        //                 this.$toast.error('Account không được trống. Hãy kiểm tra lại!')
        //                 this.txtMaTB.focus()
        //                 return
        //             }
        //             if (!vdcService.DatChoLaiMegaVNN(account, this.$auth.getNgayCapNhat().AddDays(7)))
        //             {
        //                 this.$toast.error('Có lỗi xảy ra trong quá trình đặt chỗ lại cho Account " + account + " lên Visa.Bạn hãy vui lòng thử lại!')
        //                 return
        //             }
        //             else
        //             {
        //                 this.$toast.error('Đặt chỗ lại cho Account " + account + " lên Visa thành công!')
        //             }
        //         }
        //     }
        // }
        // #region Cập nhật dữ liệu vào DataBase và hiển thị
        // khoiphuc.CapNhat_HopDong(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV)
        // TODO: chua co api CapNhat_HopDong & CapNhat_HopDong_v3
        // if (CHUYENDOI_LH_DUNGTHU == 1) {
        //   khoiphuc.CapNhat_HopDong_v3(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, dsHDTB_IMS, dsHDTB_DUNGTHU)
        // } else {
        //     khoiphuc.CapNhat_HopDong(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, dsHDTB_IMS)
        // }
        var input = {
          dsHDKH: this.dsHDKH, 
          dsHDTB: this.dsHDTB, 
          dsHDTB_ADSL: this.dsHDTB_ADSL, 
          dsHDTB_CD: this.dsHDTB_CD, 
          dsHDTB_GP: this.dsHDTB_GP, 
          dsHDTB_MGW: this.dsHDTB_MGW, 
          dsDK_DVGT: this.dsDK_DVGT, 
          dsDK_DVKHAC: this.dsDK_DVKHAC, 
          dsCTTHD: this.dsCTTHD, 
          dsHDTBDV: this.dsHDTBDV, 
          dsHDTB_IMS: this.dsHDTB_IMS, 
          dsHDTB_DUNGTHU: this.dsHDTB_DUNGTHU
        }
        var res_capnhat = ''
        if (this.CHUYENDOI_LH_DUNGTHU == 1) {
          res_capnhat = await api.capnhat_hopdong_khoiphuc_v3(input)
        } else {
          res_capnhat = await api.capnhat_hopdong_khoiphuc(input)
        }
        
        if (!res_capnhat || res_capnhat.error_code != "BSS-00000000") {
          this.$toast.error("Lỗi cập nhật hợp đồng thuê bao Khôi phục thanh lý: " + res_capnhat.message_detail);
          this.loading(false)
          return;
        }
        if (Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_1260 ||
          Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_ADSL ||
          Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_FTTH ||
          Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.WIFI_FIBER) {
          await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
        }
        // hoangpkn : 14/06/2014
        // Đối với loại hợp đồng có thay đổi tốc độ, mức cước
        // Phải lấy lại huonggiao_tn để update vào bảng giaophieu
        var resUp =  await api.update_huonggiao_tiepnhan(this.hdtb_id, huonggiaoTN)
        // end hoàng thêm
        this.setButton(3)
        try {
          // TODO: dang doi update api Capnhat_goikt
          // khoiphuc.Capnhat_goikt(dsHDTB)
        } catch (ex) {
        }
        var dsHD = await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
        this.hienThiDanhSacHDTB(dsHD)
        this.loading(false)
        return
      }
    } catch (e) {
      this.loading(false)
    }
  },

  async TaoDuLieu (themmoi) {
    try {
      var result = true;
      result = result == true ? await this.TaoDuLieu_HDKH(themmoi) : false
      result = result == true ? await this.TaoDuLieu_HDTB(themmoi) : false
      if (this.CHUYENDOI_LH_DUNGTHU == 1 && this.chkDungThu.Checked) {
        result = result == true ? await this.TaoDuLieu_HDTB_DUNGTHU(themmoi) : false
      }
      result = result == true ? await this.TaoDuLieu_THONGTIN_TT(themmoi) : false
      result = result == true ? await this.TaoDuLieu_CT_TIENHD(true) : false
      // //
      result = result == true ? await this.TaoDuLieu_DKDV() : false
      // CapNhat_HDTB_ID_DangKyDV()
      // CapNhat_HDTB_ID_HDTB_KV()
      this.dsHDTB_ADSL = []
      this.dsHDTB_TSL = []
      this.dsHDTB_CD = []
      this.dsHDTB_GP = []
      this.dsHDTB_IMS = []
      this.dsHDTB_MGW = []
      this.dsHDTBDV = []
      this.dsHDTB_CNTT = []
      result = result == true ? await this.TaoDuLieu_HDTBDV(true) : false
      switch (this.dichvuvt_id) {
        case DICHVU_VIENTHONG.CO_DINH:
          result = result == true ? await this.TaoDuLieu_HDTB_CD(themmoi) : false
          break
        case DICHVU_VIENTHONG.IMS:
          result = result == true ? await this.TaoDuLieu_HDTB_IMS(themmoi) : false
          break
        case DICHVU_VIENTHONG.ADSL:
          result = result == true ? await this.TaoDuLieu_HDTB_ADSL(themmoi) : false
          break
        case DICHVU_VIENTHONG.GPHONE:
          result = result == true ? await this.TaoDuLieu_HDTB_GP(themmoi) : false
          break
        case DICHVU_VIENTHONG.MEGAWAN:
        case DICHVU_VIENTHONG.METRONET:
          result = result == true ? await this.TaoDuLieu_HDTB_MGWAN(themmoi) : false
          break
        case DICHVU_VIENTHONG.TSL:
          result = result == true ? await this.TaoDuLieu_HDTB_TSL(themmoi) : false
          break
        case DICHVU_VIENTHONG.DICHVU_CNTT:
        case DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT:
        case DICHVU_VIENTHONG.TRUNGTAM_DULIEU:
        case DICHVU_VIENTHONG.HOINGHI_TRUYENHINH:
          result = result == true ? await this.TaoDuLieu_HDTB_CNTT(themmoi) : false
          break
        default:
          break
      }
      return result
    } catch (e) {
      return false
    }
  },

  async TaoDuLieu_HDKH (themmoi) {
    try {
      var vmagd_temp = ''
      this.dsHDKH = []
      if (themmoi == true) { this.hdkh_id = await api.get_keys('HD_KHACHHANG') }
      var rowHDKH = {}
      // var dsDBKH = await api.lay_ds_dbkh_theo_khid(this.khachhang_id)
      // // cmUlt.CopyRow(tcdanhba.LAY_DS_DBKH_THEO_KHID(khachhang_id).Tables[0].Rows[0], rowHDKH)
      // if (dsDBKH.length > 0) {
      //   rowHDKH = {...dsDBKH[0]}
      // }
      rowHDKH.HDKH_ID = this.hdkh_id
      if (themmoi == true) {
        if (this.ts_sinhma_gd_theo_donvi) {
          vmagd_temp = await api.sinh_magd_v2(this.$auth.getDonViID(), LOAI_HOPDONG.KHOIPHUC_SD)
        } else {
          vmagd_temp = await api.sinh_magd(LOAI_HOPDONG.KHOIPHUC_SD)
        }
        // TODO: kiem tra ket qua tra ve cua api sinh_magd & sinh_magd_v2
        // if (vmagd_temp.includes("ERROR:"))
        // {
        //     this.$toast.success(vmagd_temp.split(':')[1])
        //     loi_sinh_magd = true
        // }
        this.txtMaGD.Text = vmagd_temp
        if (!this.txtMaHD.Text) {
          var rsMaHD = await api.lay_ma_hd(this.$auth.getDonViID())
          this.txtMaHD.Text = rsMaHD.ma_hd || ''
        }
        rowHDKH.MA_GD = this.txtMaGD.Text
        rowHDKH.MA_HD = this.txtMaHD.Text
        rowHDKH.NGAY_YC = this.$auth.getNgayCapNhat()
      } else {
        rowHDKH.MA_GD = this.txtMaGD.Text
        rowHDKH.MA_HD = this.txtMaHD.Text
        rowHDKH.NGAY_YC = this.dtpNgayYeuCau.Value
      }
      if (this.chkCTV.Checked && this.txtCTV.Text) { rowHDKH.CTV_ID = this.ctv_id }
      if (this.chkNguoiGT.Checked && this.txtNguoiGT.Text) { rowHDKH.NHANVIENGT_ID = this.nguoigt_id }
      rowHDKH.MA_KH = this.txtMaKH.Text
      rowHDKH.KHACHHANG_ID = this.khachhang_id
      rowHDKH.TEN_KH = this.txtTenKH.Text
      rowHDKH.DIACHI_KH = this.txtDiaChiKH.Text
      rowHDKH.KHLON_ID = this.khlon_id// 1
      rowHDKH.DONVI_ID = await this.$auth.getDonViID()
      rowHDKH.NHANVIEN_ID = await this.$auth.getNhanVienID()
      rowHDKH.LOAIHD_ID = LOAI_HOPDONG.KHOIPHUC_SD
      rowHDKH.NGUOI_CN = await this.$auth.getMaNhanVien()
      rowHDKH.NGAY_CN = await this.$auth.getNgayCapNhat()
      rowHDKH.MAY_CN = await this.$auth.getMachine()
      rowHDKH.IP_CN = await this.$auth.getIP()
      rowHDKH.NGAYLAP_HD = this.dtpNgayLapHD.Value
      rowHDKH.SO_DT = this.txtSoDT.Text
      if (this.hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
        this.hdkh_cha_id = 0
      }
      // PNLINH B2A 02/05/2020
      if (this.VPHIEUCSKH_ID != 0) {
        rowHDKH.PHIEUCSKH_ID = this.VPHIEUCSKH_ID
      }
      this.dsHDKH.push(rowHDKH)
      return true
    } catch (e) {
      return false
    }
  },
  async TaoDuLieu_HDTBDV (themmoi) {
    try {
      this.dsHDTBDV = []
      var dtTemp = await api.LAY_DONVI_THEO_LOAIDV_DIACHI_LD(this.thuebao_id)
      for (var i = 0; i < dtTemp.length; i++) {
        var row = {}
        row.HDTB_ID = this.hdtb_id
        row.LOAIDV_ID = Number(dtTemp[i]["loaidv_id"])
        row.DONVI_ID = Number(dtTemp[i]["donvi_id"])
        row.KIEUDV_ID = Number(dtTemp[i]["kieudv_id"])
        if (Number(dtTemp[i]["loaidv_id"]) == LOAI_DONVI.TRAM_VT &&
              Number(this.cboDichVuVT.SelectedValue) != DICHVU_VIENTHONG.TSL) {
          if (this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH) { row.DONVI_ID = Number(this.cboTram_CD.SelectedValue) }
          if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) { row.DONVI_ID = Number(this.cboVeTinh_Adsl.SelectedValue) }
          if (this.dichvuvt_id == DICHVU_VIENTHONG.IMS) { row.DONVI_ID = Number(this.cboTram_IMS.SelectedValue) }
          if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET) { row.DONVI_ID = Number(this.cboTram_MGW.SelectedValue) }
          if (row.DONVI_ID == 0) {
            this.$toast.error('Bạn chưa chọn đơn vị Trạm VT!')
            return false
          }
        }
        this.dsHDTBDV.push(row)
      }
      return true
    } catch (e) { return false }
  },

  async TaoDuLieu_HDTB (themmoi) {
    try {
      this.dsHDTB = []
      if (themmoi == true) {
        this.hdtb_id = await api.get_keys("HD_THUEBAO")
        if (this.dsHDTB_KV && this.dsHDTB_KV.HDTB_KV && this.dsHDTB_KV.HDTB_KV.length > 0) {
          for (var item of this.dsHDTB_KV.HDTB_KV) {
            item['HDTB_ID'] = this.hdtb_id
          }
        }
      }
      var rowHDTB = {}
      rowHDTB['HDTB_ID'] = this.hdtb_id
      rowHDTB['HDKH_ID'] = this.hdkh_id
      rowHDTB['THUEBAO_ID'] = this.thuebao_id
      if (this.thuebao_cha_id != 0) { rowHDTB['THUEBAO_CHA_ID'] = this.thuebao_cha_id }
      rowHDTB['MA_TB'] = this.txtMaTB.Text
      rowHDTB['TEN_TB'] = this.txtTenTB.Text
      rowHDTB['DIACHI_TB'] = this.txtDiaChiTB.Text
      rowHDTB['KIEULD_ID'] = Number(this.cboKieuLD.SelectedValue)
      rowHDTB['LOAITB_ID'] = Number(this.cboLoaihinhTB.SelectedValue)
      if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL) {
        rowHDTB['DONVI_ID'] = Number(this.cboDaiVT_ADSL.SelectedValue)
      } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH) {
        rowHDTB['DONVI_ID'] = Number(this.cboDaiVT_CD.SelectedValue)
      } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.IMS) {
        rowHDTB['DONVI_ID'] = Number(this.cboDaiVT_IMS.SelectedValue)
      } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.GPHONE) {
        rowHDTB['DONVI_ID'] = Number(this.cboDVQLGP.SelectedValue)
      } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.MEGAWAN ||
                Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.METRONET) {
        rowHDTB['DONVI_ID'] = Number(this.cboDVQL_MGW.SelectedValue)
      } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
                Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.DICHVU_CNTT ||
                Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH ||
                Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.TRUNGTAM_DULIEU) {
        rowHDTB['DONVI_ID'] = this.donvi_ql_cntt
      } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.TSL) {
        rowHDTB['DONVI_ID'] = Number(this.cboDaiVT_CD.SelectedValue)
      }
      rowHDTB['DOITUONG_ID'] = this.doituong_id
      if (this.CHUYENDOI_LH_DUNGTHU == 1 && this.chkDungThu.Checked) { rowHDTB['DOITUONG_ID'] = DUNG_THU.DOITUONG_TB }
      rowHDTB['TTHD_ID'] = TRANGTHAI_HOPDONG.MOI
      rowHDTB['DICHVUVT_ID'] = Number(this.cboDichVuVT.SelectedValue)
      rowHDTB['KIEU_TB'] = '0' // Kiểu thuê bao thường
      rowHDTB['NGUOI_CN'] = await this.$auth.getMaNhanVien()
      rowHDTB['NGAY_CN'] = await this.$auth.getNgayCapNhat()
      rowHDTB['MAY_CN'] = await this.$auth.getMachine()
      // rowHDTB['DUNGTHU'] = 0
      // HungHM-27/06/2010: bổ xung thêm các column cho bảng HD_ThueBao
      rowHDTB['DIACHI_LD'] = this.txtDiaChiLD.Text
      rowHDTB['GHICHU'] = this.txtGhiChu.Text
      rowHDTB['STATUS'] = TRANGTHAI_DONGBO.CHUADONGBO.toString()
      var rsktt = await api.kt_thaotac(this.luong_id, 'STATUS_TONGDAI_CD')
      if (!rsktt || rsktt.error_code != "BSS-00000000") {
        this.$toast.error(rsktt.message_detail);
        return false;
      }
      if (Number(rsktt.data) > 0) { rowHDTB['STATUS'] = "0" }
      rowHDTB['QUYTRINH_ID'] = this.quytrinh_id
      if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH) {
        if (this.cboMucCuocTB_CD.Items.length > 0) {
          rowHDTB['MUCUOCTB_ID'] = Number(this.cboMucCuocTB_CD.SelectedValue)
          var rsMapId = await api.fn_tt_muccuoc_tb(this.cboMucCuocTB_CD.SelectedValue, 1)
          rowHDTB['MUCUOC_TB'] = parseFloat(rsMapId)
        }
      }
      if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL) {
        if (this.cboMucCuocTB_ADSL.Items.length > 0) {
          rowHDTB['MUCUOCTB_ID'] = Number(this.cboMucCuocTB_ADSL.SelectedValue)
          var rsMapId = await api.fn_tt_muccuoc_tb(this.cboMucCuocTB_ADSL.SelectedValue, 1)
          rowHDTB['MUCUOC_TB'] = parseFloat(rsMapId)
        }
      }
      if (this.doituong_id == DUNG_THU.DOITUONG_TB && this.doituong_ct_id != 0 && this.doituong_ct_id != undefined) { // đối tượng dùng thử kptl -> đổi đối tượng luôn
        rowHDTB['DOITUONG_ID'] = this.doituong_ct_id
      }
      this.dsHDTB.push(rowHDTB)
      return true
    } catch (e) { return false }
  },