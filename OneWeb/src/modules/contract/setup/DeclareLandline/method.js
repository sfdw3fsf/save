frmHoanCongCoDinh_Load()
{
    try {
        let str = "";
        try {
            let Tag = this.$route.query.tag
            if (Tag) {
                str = Tag.ToString();
                let words = str.split(' ');
                if (words.length >= 1)
                    this.tthd_id = Number(words[0]);
                if (words.length >= 2) {
                    this.dsloaihd_id = words[1];
                    this.dsloaihd_id = dsloaihd_id.replaceAll(';', ',');
                }
                if (words.length >= 3) {
                    this.dsdichvuvt_id = words[2];
                    this.dsdichvuvt_id = dsdichvuvt_id.replaceAll(';', ',');
                }
            }
            else {
                this.tthd_id = TrangThaiHD.DANG_THI_CONG;
                this.dsloaihd_id = "1";
                this.dsdichvuvt_id = "1";
            }
        }
        catch
        {
            this.tthd_id = TrangThaiHD.DANG_THI_CONG;
            this.dsloaihd_id = "1";
            this.dsdichvuvt_id = "1";
        }
        //endregion
        this.wbrTSKT.innerHTML = "<html><body style='font: 9.75pt Tahoma; margin: 5px; background-color://E3EFFF'></body></html>";

        //region Lấy các tham số mặc đinh
        let dsThamSo = await api.getThamSoMacDinh(this.axios, {
            ds_ma_ts: '["KHONGHOANCONG_CHUA_THANHTOAN"]',
          });
        if (dsThamSo.Tables[0].Rows.Count > 0) {
            str_vtemp = "KHONGHOANCONG_CHUA_THANHTOAN";
            List < DataRow > det = dsThamSo.Tables[0].AsEnumerable()
                .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();
            if (det.Count > 0) {
                        DataRow row = (DataRow)det[0];
                if (row["ten_ts"].ToString().Trim() == "1")
                    khong_hoancong_chua_thanhtoan = 1;
            }
        }
        //endregion

        //region Lấy dữ liệu danh mục
        WindowState = FormWindowState.Maximized;

        [] arrTableName = new [] { "KENHTHU", "KIEU_HD", "LOAI_HD", "LOAI_CAP", "NHANVIEN", "QUYTRINH" };
        object[] args = new object[] { "vchuoi", "KENHTHU|KIEU_HD|LOAI_HD|LOAI_CAP|NHANVIEN-donvi_id = " + tt_nd.donvi_id + "|QUYTRINH" };
        dsDanhMuc = new BangTuDienFacade().THUCTHI_PROCEDURE("Lay_danhmuc_thicong_codinh", DatabaseConstants.DB2, "bangtudien_common", arrTableName, args);
        bangts.HT_COMBOBOX(cboKenhThu, dsDanhMuc.Tables["KENHTHU"], "kenhthu", "kenhthu_id");
        bangts.HT_COMBOBOX(cboKieu_HD, dsDanhMuc.Tables["KIEU_HD"], "kieu_hd", "kieuhd_id");
        bangts.HT_COMBOBOX(cboLoaiHD, dsDanhMuc.Tables["LOAI_HD"], "ten_loaihd", "loaihd_id");
        bangts.HT_COMBOBOX(cboLoaiCap, dsDanhMuc.Tables["LOAI_CAP"], "loaicap", "loaicap_id");
        bangts.HT_COMBOBOX(cboNguoiGV, dsDanhMuc.Tables["NHANVIEN"], "ten_nv", "nhanvien_id");
        cboNguoiGV.SelectedValue = tt_nd.nhanvien_id;

                DateTime ngay_hientai = tt_nd.ngay_cn;
        dtpNgayBG.Value = ngay_hientai;
        dtpNgayGV.Value = ngay_hientai;
        dtpNgayGV.Enabled = false;
        ds_tramvt = bangts.Lay_DS_DONVI_NE(0, LOAI_DV.TRAM_VT);  // Sửa lại. lấy theo Trạm thi công chứ ko lấy theo Host nữa
        HienThiDanhSachHDTB();
        //this.BeginInvoke(new MethodInvoker(Hienthi_MauChu_DataGridView));
        //this.BeginInvoke(new MethodInvoker(HienThiAnhTrangThai));//nhapt thêm 2/06/2015               

        //inDex_load = -1;
        //if (matb_link != "")
        //    for (int i = 0; i < dtList.Rows.Count; i++)
        //        if (dtList.Rows[i]["ma_tb"].ToString() == matb_link)
        //        {
        //            inDex_load = i;
        //            break;
        //        }
        //if (inDex_load != 0)
        //{
        //    gvDanhSach.FocusedRowHandle = -1;
        //    if (dtList.Rows.Count == 1)
        //        inDex_load = 0;
        //    gvDanhSach.FocusedRowHandle = inDex_load;
        //    gvDanhSach_FocusedRowChanged(this, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(-1, 0));
        //    gvDanhSach.SelectCell(inDex_load, gvDanhSach.Columns[1]);
        //}
        if (tthd_id == TrangThaiHD.DA_THI_CONG_XONG) {
            tsbtnHoanThienHS.Visible = true;
        }
        kt_load = true;
        //endregion
    }
    catch (err) {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    }
}
//endregion

HienThiGiaoDien(int luong_id)
{
    tsbtnKQ_DoKiem.Visible = false;
    tsbtnDuAn.Visible = false;
    tsbtnHoanCong.Visible = false;
    tsbtnGiaoPhieu.Visible = false;
    btnPhieuTC.Visible = false;
    tsbtnTraPhieu.Visible = false;
    tsbtnHoanThanh.Visible = false;
    tsbtnVatTu.Visible = false;
    tsbtnVatTuMoi.Visible = false;
    tsbtnGiaoPhieu.Visible = false;
    btnChonNe.Enabled = false;
    btnChonNe_TuDong.Enabled = false;
    tsbtnHoanThienHS.Visible = false;

    txtTuCapGoc.Enabled = false;
    txtDoiCapGoc.Enabled = false;
    txtCapGoc.Enabled = false;
    txtHopCapNgon.Enabled = false;
    txtTuCapNgon.Enabled = false;
    txtDoiCapNgon.Enabled = false;
    txtCapNgon.Enabled = false;
    txtTuyenCap.Enabled = false;
    txtLienTu.Enabled = false;
    txtSoMetDay.Enabled = false;

    chkLayTSo.Enabled = false;
    tsbtnKichHoat.Visible = false;
    tsbtnKichHoat.Enabled = false;
    tsbtnGiaoViec_KBB.Visible = false;
    tsbtnSua_KenhThu.Visible = false;
    //dtgDanhSach.Columns["sochinh"].Visible = false;
    try {
        // str = "";
        //str += " select a.thaotac_id, b.thaotac, a.enable, b.code";
        //str += " from " + DatabaseConstants.DB2 + ".luong_thaotac a, " + DatabaseConstants.DB2 + ".thaotac b";
        //str += " where a.thaotac_id = b.thaotac_id";
        //str += " and a.luong_id = " + luong_id;

        //dtThaoTac = bangts.GetDataSql(str);
        dtThaoTac = (from DataRow DSTONG
            in dsDanhMuc.Tables["QUYTRINH"].Rows
                             where DSTONG["loaihd_id"].ToString() == loaihd_id.ToString()
            && DSTONG["luong_id"].ToString() == luong_id.ToString()
            && DSTONG["tthd_id"].ToString() == tthd_id.ToString()
                             group DSTONG by new { thaotac_id = DSTONG["thaotac_id"].ToString(), thaotac = DSTONG["thaotac"].ToString(), enable = DSTONG["enable"].ToString(), code = DSTONG["code"].ToString() } into g
                             select new CS_ThaoTac { thaotac_id = g.Key.thaotac_id, thaotac = g.Key.thaotac, enable = g.Key.enable, code = g.Key.code }).ToList();

        if (dtThaoTac.Count > 0) {
            for (int i = 0; i < dtThaoTac.Count; i++)
            {
                //str = "";
                //str += " select * from " + DatabaseConstants.DB2 + ".thaotac_control where thaotac_id = '" + dtThaoTac.Rows[i]["thaotac_id"].ToString() + "'";
                //dtControl = bangts.GetDataSql(str);
                dtControl = (from DataRow DSTONG
                    in dsDanhMuc.Tables["QUYTRINH"].Rows
                                     where DSTONG["loaihd_id"].ToString() == loaihd_id.ToString()
                    && DSTONG["luong_id"].ToString() == luong_id.ToString()
                    && DSTONG["tthd_id"].ToString() == tthd_id.ToString()
                    && DSTONG["thaotac_id"].ToString() == dtThaoTac[i].thaotac_id.ToString()
                                     orderby int.Parse(DSTONG["quytrinh_id"].ToString())
                                     select DSTONG).CopyToDataTable();
                if (dtControl.Rows.Count > 0) {
                    for (int j = 0; j < dtControl.Rows.Count; j++)
                    {
                        control_name = dtControl.Rows[j]["control_name"].ToString();
                        Control[] ctrl = this.Controls.Find(control_name, true);
                        if (ctrl.length > 0) {
                            switch (ctrl[0].GetType().FullName) {
                                case "System.Windows.Forms.Button":
                                    if (dtThaoTac[i].enable.ToString() == "1")
                                        ctrl[0].Enabled = true;
                                    else
                                        ctrl[0].Enabled = false;
                                    break;
                                case "System.Windows.Forms.TextBox":
                                    if (dtThaoTac[i].enable.ToString() == "1") {
                                        ctrl[0].Enabled = true;
                                        ctrl[0].BackColor = Color.Cornsilk;
                                        ctrl[0].ForeColor = Color.Maroon;
                                    }
                                    else
                                        ctrl[0].Enabled = false;
                                    break;
                                case "System.Windows.Forms.CheckBox":
                                    if (dtThaoTac[i].enable.ToString() == "1")
                                        ctrl[0].Enabled = true;
                                    else
                                        ctrl[0].Enabled = false;
                                    break;
                                default:
                                    break;
                            }
                        }
                        else {
                            ToolStripItem[] toolStrip = tlstpTop.Items.Find(control_name, true);
                            if (toolStrip.length > 0) {
                                switch (toolStrip[0].GetType().FullName) {
                                    case "System.Windows.Forms.ToolStripButton":
                                        if (dtThaoTac[i].enable.ToString() == "1")
                                            toolStrip[0].Visible = true;
                                        else
                                            toolStrip[0].Visible = false;
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi : " + ex);
    }
}

//region HienThiDanhSachHDTB
HienThiDanhSachHDTB()
{
    try {
        bangts.showLoading(this, 0);
                int vloai_id = 0;// 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
        if (rdoPhieuMoi.Checked)
            vloai_id = 0;
        else
            vloai_id = 1;
        dsIn = new DataSet();
        dsIn = giaophieutc.LAY_DS_PHIEU_HOANCONG_119(DichVuVienThong.CO_DINH, tthd_id, (int)tt_nd.nhanvien_id, "", 0,
            Number(cboLoaiHD.SelectedValue), 0, 0, huonggiao_id, vloai_id);
        dtList = dsIn.Tables[0];
        gridDanhSach.DataSource = dtList;
        if (dtList.Rows.Count > 0) {
            lblTongS.Text = "Tổng số: 0/" + dtList.Rows.Count + "";
        }
        else {
            tsbtnTraPhieu.Enabled = false;
            tsbtnChapNhan.Enabled = false;
            tsbtnHoanCong.Enabled = false;
            tsbtnHoanThanh.Enabled = false;
            tsbtnHoanThienHS.Enabled = false;
            Clean();
        }
        bangts.closeLoading(this);
    }
    catch (err) {
        bangts.closeLoading(this);
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    }
}
//endregion

//region HienThiDanhSachHDTB_TK_LOAIDV
HienThiDanhSachHDTB_TK_LOAIDV(int vloaidv_id, int vdonvi_tk_id)
{
    try {
        bangts.showLoading(this, 0);
                int vloai_id = 0;// 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
        if (rdoPhieuMoi.Checked)
            vloai_id = 0;
        else
            vloai_id = 1;
        dsIn = new DataSet();
        dsIn = giaophieutc.LAY_DS_PHIEU_HOANCONG_119_THEO_DV(DichVuVienThong.CO_DINH, tthd_id, (int)tt_nd.nhanvien_id, "", 0,
            Number(cboLoaiHD.SelectedValue), 0, 0, huonggiao_id, vloai_id, vloaidv_id, vdonvi_tk_id);
        gridDanhSach.DataSource = dtList = dsIn.Tables[0];
        if (dtList.Rows.Count > 0) {
            lblTongS.Text = "Tổng số: 0/" + dtList.Rows.Count + "";
        }
        else {
            tsbtnTraPhieu.Enabled = false;
            tsbtnChapNhan.Enabled = false;
            tsbtnHoanCong.Enabled = false;
            tsbtnHoanThanh.Enabled = false;
            tsbtnHoanThienHS.Enabled = false;
            Clean();
        }
        bangts.closeLoading(this);
    }
    catch (err) {
        bangts.closeLoading(this);
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    }
}
//endregion
Clean()
{
    lblLienHe.Text = "Liên hệ:";
    txtNhanVien.Text = "";
    txtNV_TiepThi.Text = "";
    //cboKieuHD.SelectedValue = KIEU_HD.TAI_NHA;
    txtTongTien.Text = "0";
    hdtb_id = 0;
    hdkh_id = 0;
    phieu_id = 0;
    txtKHLon.Text = "";
    txtMaGD.Text = "";
    txtMaTB.Text = "";
    txtMaTB_Cu.Text = "";
    txtTenTB.Text = "";
    txtDiaChiLD.Text = "";
    txtDiaChiLD_Cu.Text = "";
    txtNE.Text = "";
    txtTBLapKem.Text = "";
    txtSoDT.Text = "";
    txtKhuvuc.Text = "";
    txtTuCapGoc.Text = "";
    txtDoiCapGoc.Text = "";
    txtCapGoc.Text = "";
    txtHopCapNgon.Text = "";
    txtTuCapNgon.Text = "";
    txtDoiCapNgon.Text = "";
    txtCapNgon.Text = "";
    txtTuyenCap.Text = "";
    txtLienTu.Text = "";
    txtSoMetDay.Text = "0";

    txtGhiChu.Text = "";
    txtThongTinTra.Text = "";
    txtLyDoTra.Text = "";
    txtSoMayTN.Text = "";
    chkNgayBG.Checked = false;
            DateTime ngay_hientai = tt_nd.ngay_cn;
    dtpNgayBG.Value = ngay_hientai;
    dtpNgayGV.Value = ngay_hientai;
    chkNgayGV.Checked = false;
    gridGiaoviec.DataSource = null;
    gridDichvuGT.DataSource = null;
    txtKieuLD.Text = "";
    txtDiaChiTB.Text = "";
    txtNDGiao.Text = "";
    daikv_id = 0;
    host_id = 0;
    tramvt_id = 0;
    tramtb_id = 0;
    ne_id = 0;
    ne_id_cu = 0;
    txtTramNE.Text = "";
    lblTongS.Text = "Tổng số: 0/0";

    wbrTSKT.Document.Body.InnerHtml = "";
    txtHostLD.Text = "";
    txtHostVL.Text = "";
    txtHostLD_Cu.Text = "";
}

gvDanhSach_FocusedRowChanged(DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs e)
{
    try {
        tsbtnChapNhan.Enabled = false;
        tsbtnTraPhieu.Enabled = false;
        if (e.FocusedRowHandle == DevExpress.XtraGrid.GridControl.AutoFilterRowHandle)
            Clean();

        if (e.FocusedRowHandle < 0) return;
                //if (!kt_load) return;
                DataRow dr = gvDanhSach.GetFocusedDataRow();
        phieu_id = Convert.ToInt64(dr["phieu_id"]);
        //nhapt 14/07/2015
        //Thực hiện lấy check
        //dsIn.Tables[0].Rows[nIndex]["stt"] = "1";
        //Hiển thị danh sách nhân viên
        ds = tchopdong.LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieu_id, tt_nd.nhanvien_id, 2);
        HienThiDSNV(ds);
        HienThiTTHopDongTB(dsIn);
        chkLayTSo.Checked = false;
    }
    catch (Exception ex)
    {
        Message_Box.ShowError(ex.ToString());
    }
}

//region HienThiTTHopDongTB
HienThiTTHopDongTB(ds)
{
    if (ds.Tables[0].Rows.Count > 0) {
                //int nIndex = gvDanhSach.FocusedRowHandle;
                DataRow dr = gvDanhSach.GetFocusedDataRow();
        tsbtnChapNhan.Enabled = true;
        tsbtnTraPhieu.Enabled = true;
        ne_id = 0;
        ne_id_cu = 0;

        hdtb_cha_id = 0;
        thuebao_cha_id = 0;

        txtTramNE.Text = "";
        hdtb_id = Convert.ToInt64(dr["hdtb_id"]);
        if (dr["thuebao_id"].ToString() != "")
            thuebao_id = Convert.ToInt64(dr["thuebao_id"]);
        else
            thuebao_id = 0;

        hdkh_id = Convert.ToInt64(dr["hdkh_id"]);
                ds_tien = new DataSet();
        ds_tien = new ThanhToanHDFacade().LAY_TONGTIEN_THEO_HDKH_ID(hdkh_id);
        if (ds_tien.Tables[0].Rows.Count > 0)
            txtTongTien.Text = Convert.ToDecimal(ds_tien.Tables[0].Rows[0]["tongtien"]).ToString(Format.FM_TIEN_VND);
        else
            txtTongTien.Text = "0";

        txtNhanVien.Text = bangts.LAY_THONGTIN_NHANVIEN(Convert.ToInt64(dr["nhanvien_id"]));

        if (dr["ctv_id"].ToString() != "")
            txtNV_TiepThi.Text = bangts.LAY_THONGTIN_NHANVIEN_DV(Convert.ToInt64(dr["ctv_id"]));
        else
            txtNV_TiepThi.Text = "";

                DataTable dt_kenhthu = new DataTable();
        dt_kenhthu = bangts.Lay_kenhthu(hdkh_id);
        if (dt_kenhthu.Rows.Count > 0 && dt_kenhthu.Rows[0][0].ToString() != "")
            cboKenhThu.SelectedValue = Number(dt_kenhthu.Rows[0]["kenhthu_id"].ToString());

        if (dr["kieuhd_id"].ToString() != "")
            cboKieu_HD.SelectedValue = Number(dr["kieuhd_id"].ToString());
        else
            cboKieu_HD.SelectedValue = KIEU_HD.TAI_GD;
        //Lấy thông tin liên hệ lắp đặt thuê bao
        lblLienHe.Text = "Liên hệ:" + bangts.Lay_thongtin_lh_lapdat(hdkh_id);
        if (lblLienHe.Text == "")
            lblLienHe.Text = "Liên hệ:";

        phieu_id = Convert.ToInt64(dr["phieu_id"]);

        if (dr["giohen_tu"].ToString() != "")
            chkHen.Checked = true;
        else
            chkHen.Checked = false;

        txtThongTinTra.Text = dr["nd_tra_con"].ToString();
        if (dr["lydotra_con_id"].ToString() != "") {
            txtLyDoTra.Text = checkdata.MAP_ID("lydotra", DatabaseConstants.DB2 + ".lydotra_tc ", "where lydotra_id = " + dr["lydotra_con_id"].ToString());
        }
        else
            txtLyDoTra.Text = "";

        //dstra = new DataSet();
        //dstra = bangts.HT_PHIEU_TRA(phieu_id);
        //if (dstra.Tables[0].Rows.Count > 0)
        //{
        //    txtThongTinTra.Text = dstra.Tables[0].Rows[0]["nd_tra"].ToString();
        //    int lydotra_id = Number(dstra.Tables[0].Rows[0]["lydotra_id"].ToString());
        //    txtLyDoTra.Text = checkdata.MAP_ID("lydotra", DatabaseConstants.DB2 + ".lydotra_tc ", "where lydotra_id = " + lydotra_id);
        //}
        //else
        //{
        //    txtThongTinTra.Text = "";
        //    txtLyDoTra.Text = "";
        //}

        loaihd_id = Number(dr["loaihd_id"]);
        loaitb_id = Number(dr["loaitb_id"]);
        txtMaGD.Text = dr["ma_gd"].ToString();
        txtMaTB.Text = dr["ma_tb"].ToString();
        txtTenTB.Text = CommonFunction.ChuanHoaTen(dr["ten_tb"].ToString());
        //nhapt them thông tin khu vực 24/03/2017
        txtKhuvuc.Text = dr["ten_kv"].ToString();
        txtDiaChiLD.Text = CommonFunction.ChuanHoaTen(dr["diachi_ld"].ToString());
        if (loaihd_id != LoaiHopDong.DAT_MOI) {
            txtDiaChiLD_Cu.Text = dr["diachild_cu"].ToString();
            txtMaTB_Cu.Text = dr["ma_tb_cu"].ToString();
        }
        else {
            txtDiaChiLD_Cu.Text = "";
            txtMaTB_Cu.Text = "";
        }

        txtSoDT.Text = dr["so_dt"].ToString();
        txtDiaChiTB.Text = CommonFunction.ChuanHoaTen(dr["diachi_tb"].ToString());
        txtGhiChu.Text = dr["ghichu"].ToString();
        txtKieuLD.Text = dr["ten_kieuld"].ToString();
        txtThoiGianThueSo.Text = dr["tg_thueso"].ToString();
        txtSoTruot.Text = dr["sotruot"].ToString();
        txtNDThongBao.Text = dr["noidung_tb"].ToString();

        // hoangpkn : 20/03/2015 : sửa cho Trung kế
        //if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.DAT_MOI)
        //{
        //    if (loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ 
        //        || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ)
        //        dtgDanhSach.Columns["ma_tb_cha"].Visible = true;
        //    else
        //        dtgDanhSach.Columns["ma_tb_cha"].Visible = false;
        //}
        gvDanhSach.Columns["MA_TB_CHA"].Visible = true;
        //dtgDanhSach.Columns["ma_tb_cha"].Visible = true;
        // end hoang sửa
        if (!.IsNullOrEmpty(dr["hdtb_cha_id"].ToString()) || !.IsNullOrEmpty(dr["thuebao_cha_id"].ToString())) {
            sochinh = false;
            if (!.IsNullOrEmpty(dr["hdtb_cha_id"].ToString()))
                hdtb_cha_id = Convert.ToInt64(dr["hdtb_cha_id"].ToString());
            else
                thuebao_cha_id = Convert.ToInt64(dr["thuebao_cha_id"].ToString());
        }
        else
            sochinh = true;

        //DataTable dt = tchopdong.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id).Tables[0];
        //if (dt.Rows.Count > 0)
        //{
        txtNE.Text = dr["ne"].ToString();
        if (!.IsNullOrEmpty(dr["ne_id"].ToString())) {
            ne_id = Convert.ToInt64(dr["ne_id"].ToString());
            ne_id_cu = Convert.ToInt64(dr["ne_id"].ToString());
            s = checkdata.MAP_ID("ten_dv", DatabaseConstants.DB2 + ".sone a, " + DatabaseConstants.DB1 + ".donvi b", "where a.donvi_id = b.donvi_id and a.ne_id = " + ne_id + "");
            if (s != "-1")
                txtTramNE.Text = s;
        }

        tramvt_id = 0;
        str = "select * from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + hdtb_id + " and loaidv_id = " + LOAI_DV.TRAM_VT + " and kieudv_id = 2";
                DataTable dtTemp = bangts.GetDataSql(str);
        if (dtTemp.Rows.Count > 0)
            tramvt_id = Number(dtTemp.Rows[0]["donvi_id"].ToString());

        host_id = 0;
        str = "select * from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + hdtb_id + " and loaidv_id = " + LOAI_DV.HOST + " and kieudv_id = 2";
        dtTemp = bangts.GetDataSql(str);
        if (dtTemp.Rows.Count > 0)
            host_id = Number(dtTemp.Rows[0]["donvi_id"].ToString());

        tramtb_id = 0;
        if (dr["tramtb_id"].ToString() != "")
            tramtb_id = Number(dr["tramtb_id"].ToString());
        //}

        if (dr["phanloaikh_id"].ToString() != "") {
            phanloai_kh = checkdata.MAP_ID("TEN_PLKH", DatabaseConstants.DB2 + ".phanloai_kh", "where phanloaikh_id = " + dr["phanloaikh_id"].ToString());
            txtKHLon.Text = phanloai_kh;
        }
        else
            txtKHLon.Text = "";

        // dữ liệu mới
        txtTuyenCap.Text = dr["tuyencap"].ToString();
        txtLienTu.Text = dr["lientu"].ToString();
        txtTuCapNgon.Text = dr["tucap_p"].ToString();
        if (dr["doicap_p"].ToString() != "-1")
            txtDoiCapNgon.Text = dr["doicap_p"].ToString();
        else
            txtDoiCapNgon.Text = "";

        txtHopCapNgon.Text = dr["hopcap_p"].ToString();
        if (dr["loaicap_id"].ToString() != "")
            cboLoaiCap.SelectedValue = Number(dr["loaicap_id"].ToString());
        txtCapGoc.Text = dr["cap_goc"].ToString();
        txtCapNgon.Text = dr["cap_p"].ToString();
        if (dr["doicap_goc"].ToString() != "-1")
            txtDoiCapGoc.Text = dr["doicap_goc"].ToString();
        else
            txtDoiCapGoc.Text = "";
        txtTuCapGoc.Text = dr["tucap_goc"].ToString();
        // end du lieu moi

        txtSoMetDay.Text = dr["culy"].ToString();
        if (txtSoMetDay.Text == "")
            txtSoMetDay.Text = "0";
        txtSoMayTN.Text = dr["matb_tn"].ToString();
        txtNDGiao.Text = dr["nd_giao"].ToString();
        txtNoiDungTH.Text = dr["nd_thuchien"].ToString();
        status = dr["status"].ToString();

        tsbtnHuyKH.Visible = false;
        if (PHAILAM(luong_id, "TSBTNHUYKH")) {
            tsbtnHuyKH.Visible = true;
        }

        //nhapt thêm : 17/03/2017
        /// Thực hiện ẩn/hiện nút thay đổi thông tin liên hệ 
        /// trên chức năng của trạm; để trạm cập nhật lại thông tin liên hệ sai
        tsbtnThongTinLH.Visible = false;
        if (PHAILAM(luong_id, "TSBTNTHONGTINLH")) {
            tsbtnThongTinLH.Visible = true;
        }
        if (dr["status"] != null && dr["status"] != DBNull.Value) {
            if (Number(dr["status"]) == TRANGTHAI_DONGBO.DONGBO_SERVICE)
                tsbtnHuyKH.Enabled = true;
            else
                tsbtnHuyKH.Enabled = false;
        }

        vdstb_lapkem = "";
        vdstb_lapkem = tcchung.LAY_THUEBAO_LAPKEM(hdtb_id, Convert.ToInt64(dr["madoicap"].ToString()));
        if (vdstb_lapkem == "null")
            vdstb_lapkem = "";
        txtTBLapKem.Text = vdstb_lapkem;

        txtHostLD.Text = "";
        txtHostVL.Text = "";
        txtHostLD_Cu.Text = "";
        if (PHAILAM(luong_id, "HIENTHI_HOST")) {
            //Nếu có tham số này thì hiển thị thông tin host lắp đặt, host vật lý                    
            var dt = tcchung.lay_thongtin_host(hdtb_id, 0);
            if (dt.Rows.Count > 0) {
                txtHostLD.Text = dt.Rows[0]["host_ld"].ToString();
                txtHostVL.Text = dt.Rows[0]["host_vl"].ToString();
                txtHostLD_Cu.Text = dt.Rows[0]["host_ld_cu"].ToString();

                cboTramVeTinh.Properties.DataSource = null;
                if (tramvt_id != 0) {
                    //var t = ds_tramvt.Tables[0].Select("DONVI_QL_ID=" + dt.Rows[0]["hostld_id"].ToString());
                    var t = ds_tramvt.Tables[0].Select("DONVI_QL_ID=" + tramvt_id);
                    cboTramVeTinh.Properties.DataSource = t.length > 0 ? t.CopyToDataTable() : null;
                }
                else
                    cboTramVeTinh.Properties.DataSource = ds_tramvt.Tables[0];

                cboTramVeTinh.Properties.ValueMember = "DONVI_ID";
                cboTramVeTinh.Properties.DisplayMember = "TEN_DV";
                cboTramVeTinh.Properties.PopupFormWidth = 400;
                cboTramVeTinh.Properties.View.GridControl.Size = new Size(20, 20);

                cboTramVeTinh.Properties.TextEditStyle = DevExpress.XtraEditors.Controls.TextEditStyles.Standard;
                cboTramVeTinh.Properties.View.OptionsView.ShowAutoFilterRow = true;
                cboTramVeTinh.Properties.ImmediatePopup = true;
                if (tramtb_id != 0)
                    cboTramVeTinh.EditValue = tramtb_id;
                else {
                    cboTramVeTinh.EditValue = 0;
                    cboTramVeTinh.Text = "";
                }
            }
            else
                cboTramVeTinh.Properties.DataSource = null;
        }
        else
            cboTramVeTinh.Text = txtTramNE.Text;


        toolTip1.SetToolTip(txtHostLD, txtHostLD.Text.Trim());
        toolTip1.SetToolTip(txtHostLD_Cu, txtHostLD_Cu.Text.Trim());
        toolTip1.SetToolTip(txtHostVL, txtHostVL.Text.Trim());

        if (PHAILAM(luong_id, "CAPNHAT_NE")) {
            if (loaitb_id == LoaiHinhTB.PABX // minhnt thêm pabx cho agg, bpc
                || loaitb_id == LoaiHinhTB.ISDN2B_CQ || loaitb_id == LoaiHinhTB.ISDN2B_CD
                || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
                || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
                || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) {
                if (sochinh == true) {
                    btnChonNe.Enabled = true;
                    btnChonNe_TuDong.Enabled = true;
                }
                else {
                    if (PHAILAM(luong_id, "CHON_NE_SO_PHU")) {
                        btnChonNe.Enabled = true;
                        btnChonNe_TuDong.Enabled = true;
                    }
                    else {
                        btnChonNe.Enabled = false;
                        btnChonNe_TuDong.Enabled = false;
                    }
                }
                //KNV||04/08/2016 ẩn nút chọn NE nếu đã kích hoạt tổng đài(tránh trường hợp kích hoạt xong lại chọn NE khác)
                btnChonNe.Enabled = status != TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString();
                btnChonNe_TuDong.Enabled = status != TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString();
            }
            else {
                btnChonNe.Enabled = sochinh;
                btnChonNe_TuDong.Enabled = sochinh;
            }
        }

        dtpNgayBG.Enabled = true;
        chkNgayBG.Checked = true;
        if (dr["ngay_ht_gp"].ToString() != "")
            dtpNgayBG.Value = Convert.ToDateTime(dr["ngay_ht_gp"]);
        else
            dtpNgayBG.Value = tt_nd.ngay_cn.AddSeconds(30);

        //Nếu phải kích hoạt tổng đài
        if (PHAILAM(luong_id, "KICHHOAT_TD")) {
            if (dr["ngay_ht_gp"].ToString() != "") {
                if (status == TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString()) {
                    tsbtnGiaoPhieu.Enabled = true;
                    tsbtnKichHoat.Enabled = false;
                }
                else {
                    tsbtnGiaoPhieu.Enabled = false;
                    tsbtnKichHoat.Enabled = true;
                }
            }
            else {
                tsbtnGiaoPhieu.Enabled = false;
                tsbtnKichHoat.Enabled = false;
            }
        }

        //Nếu phải giao phiếu
        if (PHAILAM(luong_id, "GIAOPHIEU")) {
            if (dr["ngay_ht_gp"].ToString() != "") {
                if (PHAILAM(luong_id, "KICHHOAT_TD")) {
                    if (status == TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString()) {
                        tsbtnGiaoPhieu.Enabled = true;
                        tsbtnKichHoat.Enabled = false;
                    }
                    else {
                        tsbtnGiaoPhieu.Enabled = false;
                        tsbtnKichHoat.Enabled = true;
                    }
                }
                else
                    tsbtnGiaoPhieu.Enabled = true;
            }
            else {
                tsbtnGiaoPhieu.Enabled = false;
                tsbtnKichHoat.Enabled = false;
            }
        }

        //Nếu phải kích hoạt tổng đài
        if (PHAILAM(luong_id, "HOANCONG")) {
            if (dr["ngay_ht_gp"].ToString() != "" && status == TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString())
                tsbtnHoanCong.Enabled = true;
            else
                tsbtnHoanCong.Enabled = false;
        }
        if (PHAILAM(luong_id, "HOANTHIENHOSO")) {
            if (dr["ngay_ht_gp"].ToString() != "")
                tsbtnHoanThienHS.Enabled = true;
            else
                tsbtnHoanThienHS.Enabled = false;
        }

        if (!PHAILAM(luong_id, "GIAOVIEC")) {
            chkNgayBG.Checked = true;
            dtpNgayBG.Enabled = false;
            chkNgayBG.Enabled = false;

            chkNgayGV.Checked = true;
            dtpNgayGV.Enabled = false;
            chkNgayGV.Enabled = false;
        }

        if (PHAILAM(luong_id, "CAPNHAT_CAP") && dr["chan"].ToString() == "1")
            tsddbIn.Enabled = false;
        else
            tsddbIn.Enabled = true;

        if (ds.Tables[0].Rows[0]["caidat_dv"].ToString() != "0") {
            checkTTCaiDatDVDK.Checked = true;
        }
        else
            checkTTCaiDatDVDK.Checked = false;
        //if (loaihd_id == LoaiHopDong.DI_CHUYEN)
        //{
        //    if (!giaophieutc.KIEMTRA_TRAMVT1(hdtb_id)) //Nếu khác trạm
        //        txtNE.Enabled = true;
        //}
        if (PHAILAM(luong_id, "CHECK_OMC")) // Nếu có thao tác kiểm tra OMC thì -> làm
        {
            try {

                var kq = checkdata.GET_VALUE_FUNC("{?DB2}.KIEMTRA_GIAO_OMC", "vhdtb_id", hdtb_id, "vloaihinh_tb", loaitb_id);
                if (kq == null) {
                    Message_Box.ShowWarning("Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo");
                    tsbtnHoanCong.Visible = false;
                    tsbtnKichHoat.Visible = false;
                    tsbtnHuyKH.Visible = false;
                    return;
                }
                else if (kq.ToString() == "2")// bo qua
                {
                    if (dr["ngay_ht_gp"].ToString() != "")
                        tsbtnHoanCong.Enabled = true;
                    tsbtnHoanCong.Visible = true;
                    tsbtnKichHoat.Visible = true;
                    tsbtnHuyKH.Visible = true;
                            goto Hoancong;
                }
                else if (kq.ToString() == "1") // giao OMC thi hien nut hoan cong
                {
                    //Giaophieu
                    if (dr["ngay_ht_gp"].ToString() != "")
                        tsbtnHoanCong.Enabled = true;
                    tsbtnHoanCong.Visible = true;
                    tsbtnKichHoat.Visible = false;
                    tsbtnHuyKH.Visible = false;
                }
                else {
                    tsbtnHoanCong.Visible = false;
                    tsbtnKichHoat.Visible = false;
                    tsbtnHuyKH.Visible = false;
                    return;
                }



            }
            catch (Exception ex)
            {
                Message_Box.ShowWarning("Có lỗi khi gọi hàm kiểm thông tin thuê bao " + ex);
                tsbtnHoanCong.Visible = false;
                tsbtnKichHoat.Visible = false;
                tsbtnHuyKH.Visible = false;
            }
        }
        Hoancong:
        HienThiDVGT(tchopdong.LAY_DS_DANGKY_DVGT_V2(hdtb_id));

        HIEN_THONGSO_KYTHUAT();
    }
}
//endregion

//region Hiện thông số kỹ thuật
HIEN_THONGSO_KYTHUAT()
{
    //nếu ko dùng cabman2.0 thì không hiện
    if (!IS_USING_CABMAN_V2) {
        if (!palThongTinCapCu.Visible) {
            palThongTinCapCu.Visible = true;
            palThongSoKyThuat.Visible = false;
        }
        return;
    }

    if (!palThongSoKyThuat.Visible) {
        palThongSoKyThuat.Size = palThongTinCapCu.Size;
        palThongTinCapCu.Visible = false;
        palThongSoKyThuat.Visible = true;
        palThongSoKyThuat.Location = palThongTinCapCu.Location;
        palThongSoKyThuat.BorderStyle = DevExpress.XtraEditors.Controls.BorderStyles.Simple;
    }

    try {
                ds = new TCHopDongFacade().lay_ds_thongtin_kythuat_hdtb(hdtb_id);

        // Xóa hiển thị cũ
        wbrTSKT.Document.Body.InnerHtml = "";

        // Hiển thị dữ liệu mới
        foreach(DataRow dr in ds.Tables[0].Select())
        {
            wbrTSKT.Document.Body.InnerHtml += .Format(dr["FORMAT_TT"].ToString(), dr["TEN_TRUONG"]);
            wbrTSKT.Document.Body.InnerHtml += .Format(dr["FORMAT_GT"].ToString(), dr["GIATRI"]);
        }
    }
    catch (Exception ex)
    {
        wbrTSKT.Document.Body.InnerHtml = "Lỗi: " + ex.Message;
    }
}
//endregion

//region HienThiDVGT
HienThiDVGT(ds)
{
    if (ds.Tables[0].Rows.Count > 0) {
        //dgvdangkyDV.AutoGenerateColumns = false;
        gridDichvuGT.DataSource = ds.Tables[0];
        gridViewDVGT.BestFitColumns();
    }
    else
        gridDichvuGT.DataSource = null;
}
//endregion

//region Button Click
tsbtnClose_Click()
{
    Close();
}

//region Lấy tốc độ Id theo mã thuê bao
        private LayTocDo_ID_TheoMaTB(ma_tb)
{
    tocdo_id = "";
            //Lấy thuê bao id
            ds = new DataSet();
    ds = tcdanhba.LAY_DB_THEO_MATB(ma_tb, 0, 0);
            long thuebao_id = 0;
    if (ds.Tables[0].Rows.Count > 0) {
        thuebao_id = Convert.ToInt64(ds.Tables[0].Rows[0]["thuebao_id"].ToString());
                dsKiemTra = new DataSet();
        dsKiemTra = tcdanhba.LAY_DS_DB_ADSL_THEO_TBID(thuebao_id);
        if (dsKiemTra.Tables[0].Rows.Count > 0) {
            tocdo_id = dsKiemTra.Tables[0].Rows[0]["tocdo_id"].ToString();
        }
    }
    return tocdo_id;
}
//endregion

tsbtnHoanCong_Click()
{
    Hoan_Cong();
}

        bool hoancong_luon = false;
Hoan_Cong()
{
    //region Kiểm tra luồng nếu phải giao OMC thì giao không thì thôi
    hoancong_luon = false;
    if (PHAILAM(luong_id, "CHECK_OMC")) // Nếu có thao tác kiểm tra OMC thì -> làm
    {
        try {
            if (gvDanhSach.FocusedRowHandle < 0)
                return;
            var kq = checkdata.GET_VALUE_FUNC("{?DB2}.KIEMTRA_GIAO_OMC", "vhdtb_id", hdtb_id, "vloaihinh_tb", loaitb_id);
            if (kq == null) {
                Message_Box.ShowWarning("Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo");
                return;
            }
            else if (kq.ToString() == "2")// bo qua
            {
                hoancong_luon = true;
                        goto Hoancong;
            }
            else if (kq.ToString() == "1") {
                //Giaophieu
                if (MessageBox.Show("Phiếu sẽ được giao về PSTN để thực hiện. Bạn có muốn tiếp tục không ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return;
                kq_giao = giaophieutc.GIAOPHIEU_TIEPTHEO(hdtb_id, phieu_id, tt_nd.nhanvien_id, huonggiao_id, tt_nd.may_cn, tt_nd.ma_nd, 2);
                if (kq_giao == "1") {
                    //Hien thi Danh sach6
                    Message_Box.ShowTB("Đã thực hiện giao phiếu cho bộ phận OMC theo thành công");
                    HienThiDanhSachHDTB();
                    inDex_load = 0;
                    return;
                }
                else {
                    Message_Box.ShowWarning(kq_giao);
                    return;
                }
                return;
            }
            else {
                Message_Box.ShowWarning(kq.ToString());
                return;
            }



        }
        catch (Exception ex)
        {
            Message_Box.ShowWarning("Có lỗi khi gọi hàm kiểm thông tin thuê bao " + ex);
            return;
        }
    }



    //endregion
    Hoancong:

    if (PHAILAM(luong_id, "KT_HOANCONG_HD_BANCHEO")) {
        try {
            kq_bc = new BusinessFacade.BanCheoFacade.HopDong().KIEMTRA_HOANTHIEN_THOAITRA_BAN(hdtb_id, 1);
            if (kq_bc != "OK") {
                Message_Box.ShowWarning(kq_bc);
                return;
            }
        }
        catch (Exception) { }
    }

    //region Kiếm tra updatefile
    if (PHAILAM(luong_id, "KIEMTRA_FILEHS")) {
        try {
            var kq_file = checkdata.GET_VALUE_FUNC("{?DB1}.kiemtra_upfile", "vhdtb_id", hdtb_id, "vphieu_id", phieu_id);
            if (kq_file == null || kq_file == DBNull.Value) {
                Message_Box.ShowWarning("Không lấy được kết quả kiểm tra up file");
                return;
            }
            if (kq_file.ToString() != "1") {
                Message_Box.ShowWarning(kq_file.ToString());
                return;
            }
        }

        catch (Exception ex)
        {
            Message_Box.ShowWarning("Có lỗi khi kiểm file upload " + ex);
            return;
        }

    }
    //endregion

    //region TSMD - PHAI_CAPNHAT_TOADO_LD:Kiểm tra cập nhật tọa độ địa chỉ lắp đặt
    //KieuNV|28/05/2018: Kiểm tra phải cập nhật tọa độ địa chỉ lắp đặt nếu có thiết lập tham số.
    //Áp dụng cho hđ lắp mới và dịch chuyển
    if (new [] { LoaiHopDong.DAT_MOI, LoaiHopDong.DI_CHUYEN }.Contains(loaihd_id))
    {
        var CheckToadoLapdat = new ThamSo_MDFacade().LAY_DS_THAMSO_MD_MATS("PHAI_CAPNHAT_TOADO_LD");

        if (
            CheckToadoLapdat != null &&
            CheckToadoLapdat.Tables != null &&
            CheckToadoLapdat.Tables.Count > 0 &&
            CheckToadoLapdat.Tables[0].Rows.Count > 0 &&
            CheckToadoLapdat.Tables[0].Rows[0]["ten_ts"].ToString() == "1"
        ) {
            var ToadoLD = new TruyenDanFacade().GET_VALUE_FUNC("{?DB2}.LAY_TOADO_LAPDAT".ReplaceSchema(), "vhdtb_id", hdtb_id, "vthuebao_id", -1);
            if (ToadoLD == null || ToadoLD == DBNull.Value || .IsNullOrEmpty(ToadoLD.ToString())) {
                Message_Box.ShowWarning("Địa chỉ lắp đặt của thuê bao chưa được cập nhật tọa độ.\r\n*Trợ giúp: Nhấn nút [Địa chỉ LĐ] để mở bản đồ, ghim vị trí nhà khách hàng trên bản đồ rồi nhấn [Chấp nhận].");
                return;
            }
        }
    }
    //endregion

    //region CAPNHAT_CAP_TRUOCKHI_HC: Kiểm tra phải nhập cáp
    if (PHAILAM(luong_id, "CAPNHAT_CAP_TRUOCKHI_HC")) {
        var kq = checkdata.GET_VALUE_FUNC("{?DB5}.KIEMTRA_CAPNHAT_CAP_TRUOCKHI_HC", "vphieu_id", phieu_id, "vhdtb_id", hdtb_id, "vbaohong_id", -1);
        if (!"OK".Equals(kq)) {
            Message_Box.ShowWarning(kq.ToString());
            return;
        }
    }
    //endregion

    //region "KIEMTRA_KHUVUC_DONVI"
    if (PHAILAM(luong_id, "KIEMTRA_KHUVUC_DONVI")) {
        if (!new TCHopDongFacade().KIEMTRA_KHUVUC_DONVI(hdtb_id)) {
            Message_Box.ShowTB("Khu vực được gán cho thuê bao " + txtMaTB.Text + " không thuộc trạm " + tt_nd.ten_dv + " quản lý. Hãy gán lại khu vực!.");
            return;
        }
    }
    //endregion
    if (!chkNgayBG.Checked) {
        Message_Box.ShowTB("Phải cập nhật ngày thực hiện !");
        chkNgayBG.Focus();
        return;
    }
    if (bangts.KIEMTRA_KENH_TN(hdkh_id)) //Neu la 700
    {
        if (!bangts.KIEMTRA_TRANGTHAI_PHIEUTT(hdkh_id)) {
            Message_Box.ShowTB("Hợp đồng 700 chưa được thanh toán/Ký HĐ với Khách hàng, bạn không thể hoàn công !");
            return;
        }
    }
    //Bổ sung tham số, nếu hợp đồng chưa thanh toán, không cho hoàn công
    if (khong_hoancong_chua_thanhtoan == 1) {
        if (!bangts.KIEMTRA_TRANGTHAI_PHIEUTT(hdkh_id)) {
            Message_Box.ShowTB("Hợp đồng chưa được thanh toán, bạn không thể hoàn công !");
            return;
        }
    }
    if (MessageBox.Show("Bạn thật sự muốn hoàn công không ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return;

    if (PHAILAM(luong_id, "TUDONG_HTHS")) {
        //Nếu hdtb_id_cha, với isdn thì fai hoàn công thuê bao cha trước
        if (hdtb_cha_id != 0) {
            str1 = "select * from " + DatabaseConstants.DB2 + ".hd_thuebao where tthd_id = 6 and hdtb_id = " + hdtb_cha_id;
                    DataTable dtTmp = bangts.GetDataSql(str1);
            if (dtTmp.Rows.Count <= 0) {
                Message_Box.ShowWarning("Bạn phải hoàn công số chính trước khi hoàn công thuê bao này !");
                return;
            }
        }
                int kt_taobang = bangts.KT_TaoDB_Thang(dtpNgayBG.Value.ToString("yyyyMM"));
        if (kt_taobang == 0) {
            Message_Box.ShowWarning("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !");
            return;
        }

        thangsau = dtpNgayBG.Value.AddMonths(1).ToString("yyyyMM");
        kt_taobang = bangts.KT_TaoDB_Thang(thangsau);
        if (kt_taobang != 0) {
            Message_Box.ShowTB("Danh bạ tháng " + dtpNgayBG.Value.ToString("MM/yyyy") + " đã được chốt. Liên hệ Admin để xử lý !");
            return;
        }
    }

    if (!KT_DuLieuNhap(luong_id)) return;
    //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
    ngay_sys;
    ngay_tt;
    ngay_ht;
            // hoangpkn : 06/06/2015 :Zem do HCM ko cần thu hồi công vẫn hoàn công
            //if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.DI_CHUYEN)
            //{
            //    //Kiểm tra xem có tồn tại hướng giao thu hồi cổng còn trạng thái mới không.
            //    dsdvthuhoi = new DataSet();
            //    dsdvthuhoi = bangts.Kiemtra_thuhoicong(hdtb_id);
            //    if (dsdvthuhoi.Tables[0].Rows.Count > 0)
            //    {
            //        Message_Box.ShowWarning("Thuê bao " + txtMaTB.Text.Trim() + " đang thực hiện thu hồi cổng, dây bởi '" + dsdvthuhoi.Tables[0].Rows[0]["ten_dv"].ToString() + "'. Bạn hãy kiểm tra lại");
            //        return;
            //    }
            //}

            DataRow dr = gvDanhSach.GetFocusedDataRow();
    ngay_sys = tt_nd.ngay_cn.ToString("dd/MM/yyyy");
    ngay_tt = dr["thangnam_tt"].ToString().Trim();
    ngay_ht = dtpNgayBG.Value.ToString("dd/MM/yyyy");
    if (Number(cboLoaiHD.SelectedValue) != LoaiHopDong.KHOIPHUC_SD) {
        if (!KiemTraDK_HoanThanh(ngay_tt, ngay_ht))//Nếu ngày thanh toán > ngày hoàn thành
        {
            Message_Box.ShowWarning(" Thuê bao " + dr["ma_tb"].ToString().Trim() + " có ngày thanh toán : " + ngay_tt + " \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán");
            return;
        }
    }
    if (!KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
    {
        Message_Box.ShowWarning("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
        return;
    }
            bool ht = true;
            dsphieu = new DataSet();
            int hg_phieu_id = 0;
            int donvi_nhan_id = 0;
            int tthd_dich_id = 0;
    if (hoancong_luon) // nếu co thao tác omc và hoàn công lập tức thì làm luôn
    {
        dsphieu = bangts.Lay_DonVi_GiaoPhieu_HTHS(huonggiao_id, hdtb_id);
    }
    else
        dsphieu = bangts.Lay_DonVi_GiaoPhieu(huonggiao_id, hdtb_id);
    if (dsphieu.Tables[0].Rows.Count > 0) {
        hg_phieu_id = Number(dsphieu.Tables[0].Rows[0]["huonggiao_id"].ToString());
        donvi_nhan_id = Number(dsphieu.Tables[0].Rows[0]["donvi_id"].ToString());
        tthd_dich_id = Number(dsphieu.Tables[0].Rows[0]["tthd_id"].ToString());
    }
    else {
        Message_Box.ShowWarning("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !");
        return;
    }

    if (!bangts.KiemTra_Phieu_DaGiao(hdtb_id, hg_phieu_id)) {
        Message_Box.ShowWarning("Thuê bao " + txtMaTB.Text + " đã được giao đi. Bạn hãy kiểm tra lại");
        HienThiDanhSachHDTB();
        return;
    }
    if (CapNhat()) {
        if (PHAILAM(luong_id, "GIAOVIEC_NVQL_CAP")) {
                    dsNV = tchopdong.LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieu_id, tt_nd.nhanvien_id, 2);
            if (dsNV != null && dsNV.Tables[0].Rows.Count <= 0) {
                        GiaoPhieu_NVFacade objgiaophieu = new GiaoPhieu_NVFacade();

                strGV = "select nhanvien_id from " + DatabaseConstants.DB5
                    + ".vi_nhanvien_theocap_hdtb where hdtb_id =" + hdtb_id;
                        dsGV = bangts.GetSQL(strGV, "");

                if (dsGV != null && dsGV.Tables[0].Rows.Count > 0) {
                    if (dsGV.Tables[0].Rows[0]["nhanvien_id"].ToString() == "") {
                        Message_Box.ShowTB("Kết cuối chưa được phân nhân viên!");
                        return;
                    }
                            int nvql_cap_id = Number(dsGV.Tables[0].Rows[0]["nhanvien_id"].ToString());

                    objgiaophieu.Delete(phieu_id);
                    TaoDL_GiaoPhieu_NVQL_CAP(phieu_id, "Thi công", nvql_cap_id);
                    objgiaophieu.Insert(dsnhanviengp);
                }
                else {
                    Message_Box.ShowTB("Thuê bao chưa được cấp cáp!");
                    return;
                }
            }
        }

                long vhdtb_id = 0;
                long vphieu_id = 0;
        if (loaihd_id != LoaiHopDong.DAT_MOI) {
            //region CAPNHAT_NGAYHT_HC
            if (PHAILAM(luong_id, "CAPNHAT_NGAYHT_HC")) {
                thangsau = tt_nd.ngay_cn.AddMonths(1).ToString("yyyyMM");
                dbt = bangts.KT_DBThang_DaChot(thangsau);
                if (dbt != "0") {
                    Message_Box.ShowTB(dbt);
                    return;
                }
                lapHD.CAPNHAT_NGAYHT(hdtb_id, dtpNgayBG.Value);

                if ((loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
                    || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
                    || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
                    || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) && sochinh == true) {
                    for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
                    {
                        if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                            vhdtb_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["hdtb_id"].ToString());
                            lapHD.CAPNHAT_NGAYHT(vhdtb_id, dtpNgayBG.Value);
                        }
                    }
                }
            }
            //endregion

            //region CAPNHAT_NGAYKH_HC
            if (PHAILAM(luong_id, "CAPNHAT_NGAYKH_HC")) {
                lapHD.CAPNHAT_NGAYKH(hdtb_id, tt_nd.ngay_cn);
                if ((loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
                    || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
                    || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
                    || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) && sochinh == true) {
                    for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
                    {
                        if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                            vhdtb_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["hdtb_id"].ToString());
                            lapHD.CAPNHAT_NGAYKH(vhdtb_id, tt_nd.ngay_cn);
                        }
                    }
                }
            }
            //endregion
        }

        try {
            vketqua = "";
                    short capnhat_kh = 0;
                    short capnhat_ht = 0;
            if (loaihd_id == LoaiHopDong.DAT_MOI) {
                if (PHAILAM(luong_id, "CAPNHAT_NGAYHT_HC"))
                    capnhat_ht = 1;
                if (PHAILAM(luong_id, "CAPNHAT_NGAYKH_HC"))
                    capnhat_kh = 1;

                //region Đặt mới
                //hoancong.HOANTHANH_HOANCONG(hdtb_id, phieu_id, tthd_dich_id, (int)tt_nd.nhanvien_id);
                if (PHAILAM(luong_id, "TUDONG_HTHS")) {
                    vketqua = hths_dm.HOANTHIEN_HS_DATMOI_V2(hdtb_id, phieu_id, tt_nd.nhanvien_id, capnhat_ht,
                        capnhat_kh, dtpNgayBG.Value, hg_phieu_id, tt_nd.donvi_id
                        , tt_nd.may_cn, tt_nd.ma_nd);
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }

                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }

                    if (vketqua != "ok") {
                        Message_Box.ShowTB("" + vketqua);
                        return;
                    }
                }
                //Kieunv|03/12/2015: cập nhật thông tin đấu nối tb vào kết cuối
                LapThueBaoVaoKetCuoi();


                if (loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
                    || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
                    || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
                    || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) {
                    if (sochinh == true) {
                        for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
                        {
                            if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                                vhdtb_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["hdtb_id"].ToString());
                                vphieu_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["phieu_id"].ToString());
                                if (PHAILAM(luong_id, "TUDONG_HTHS")) {
                                    vketqua = hths_dm.HOANTHIEN_HS_DATMOI_V2(vhdtb_id, vphieu_id, tt_nd.nhanvien_id, capnhat_ht,
                                        capnhat_kh, dtpNgayBG.Value, hg_phieu_id, tt_nd.donvi_id
                                        , tt_nd.may_cn, tt_nd.ma_nd);
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (vketqua != "ok") {
                                        Message_Box.ShowTB("" + vketqua);
                                        return;
                                    }
                                    ht = true;
                                }
                                else {
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_HC"))
                                        vketqua = hths_dm.HOANTHIEN_HS_DATMOI_V2(vhdtb_id, vphieu_id, tt_nd.nhanvien_id,
                                            capnhat_ht, capnhat_kh, dtpNgayBG.Value, hg_phieu_id, tt_nd.donvi_id
                                            , tt_nd.may_cn, tt_nd.ma_nd);
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    //hoancong.GIAOPHIEU_TD_KHI_HC(vhdtb_id, vphieu_id, hg_phieu_id, tt_nd.donvi_id, donvi_nhan_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                                    if (vketqua != "ok") {
                                        Message_Box.ShowTB("" + vketqua);
                                        HienThiDanhSachHDTB();
                                        return;
                                    }
                                    ht = false;
                                }
                            }
                        }
                    }
                    else {
                        //Với số không phải là số chính, có số chính là trong danh bạ

                        List < DataRow > lstChon = dsIn.Tables[0].AsEnumerable().Where(r => Convert.ToInt64(r["HDKH_ID"]) == hdkh_id && r["HDTB_CHA_ID"].ToString() == ""
                            && r["THUEBAO_CHA_ID"].ToString() != "").ToList();

                        for (int i = 0; i < lstChon.Count; i++)
                        {
                            if (Convert.ToInt64(lstChon[i]["thuebao_cha_id"].ToString()) == thuebao_cha_id) {
                                vphieu_id = Convert.ToInt64(lstChon[i]["phieu_id"].ToString());
                                vhdtb_id = Convert.ToInt64(lstChon[i]["hdtb_id"].ToString());
                                if (vhdtb_id != hdtb_id) {
                                    vketqua = hths_dm.HOANTHIEN_HS_DATMOI_V2(vhdtb_id, vphieu_id, tt_nd.nhanvien_id, capnhat_ht,
                                        capnhat_kh, dtpNgayBG.Value, hg_phieu_id, tt_nd.donvi_id
                                        , tt_nd.may_cn, tt_nd.ma_nd);
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                                        try {
                                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                        }
                                        catch (Exception) { }
                                    }
                                    if (vketqua != "ok") {
                                        Message_Box.ShowTB("" + vketqua);
                                        HienThiDanhSachHDTB();
                                        return;
                                    }
                                    ht = true;
                                }
                            }
                        }
                    }
                }
                //endregion

                if (PHAILAM(luong_id, "GUI_SMS_KHI_HOANCONG")) {
                    if (txtSoDT.Text.Trim() != "") {
                        try {
                            //    sms_noidung = "VNPT VinaPhone: Cảm ơn Quý khách đã lựa chọn và sử dụng dịch vụ của VNPT VinaPhone. ";
                            //   sms_noidung += " Chúng tôi đã hoàn thành lắp đặt thuê bao (mã dịch vụ: " + txtMaTB.Text + "). ";
                            //   sms_noidung += " Trong quá trình sử dụng dịch vụ khách hàng có thể gọi đến số 800126 để được hỗ trợ trực tiếp.";
                            //   SMSService.SendSMS(txtSoDT.Text.Trim(), sms_noidung);
                            sms_noidung = tcchung.LAY_NOIDUNG_TINNHAN(hdtb_id, NHOM_QT.DIEUHANH_THICONG);
                            if (sms_noidung != "-1")
                                SMSService.SendSMS(txtSoDT.Text.Trim(), sms_noidung);
                        }
                        catch
                        { }
                    }
                }

                //dungpa - bo sung nhan tin cho nhan vien kd dia ban, cong tac vien
                if (PHAILAM(luong_id, "GUI_SMS_NVKD_CTV")) {
                    try {
                        _strCmd = "select nv.so_dt ";
                        _strCmd += " from " + DatabaseConstants.DB2 + ".hdtb_kv a, " + DatabaseConstants.DB1 + ".nhanvien nv, " + DatabaseConstants.DB2 + ".khuvuc_nv kv ";
                        _strCmd += " where a.khuvuc_id = kv.khuvuc_id ";
                        _strCmd += " and kv.nhanvien_id = nv.nhanvien_id ";
                        _strCmd += " and kv.loainv_id = " + Loai_NV.NHANVIEN_KINHDOANH;
                        _strCmd += " and a.hdtb_id = " + hdtb_id;
                        _strCmd += " union all ";
                        _strCmd += " select nv.so_dt ";
                        _strCmd += " from " + DatabaseConstants.DB2 + ".hd_khachhang kh, " + DatabaseConstants.DB1 + ".nhanvien nv";
                        _strCmd += " where kh.ctv_id = nv.nhanvien_id ";
                        _strCmd += " and kh.hdkh_id = " + hdkh_id;
                                _dsNVKD_CTV = bangts.getDataFromSQL(_strCmd, "ds");
                        if (_dsNVKD_CTV != null && _dsNVKD_CTV.Tables.Count > 0 && _dsNVKD_CTV.Tables[0].Rows.Count > 0) {
                            sms_noidung = tcchung.LAY_NOIDUNG_TINNHAN(hdtb_id, NHOM_QT.DIEUHANH_THICONG);
                            for (int z = 0; z < _dsNVKD_CTV.Tables[0].Rows.Count; z++)
                            {
                                if (_dsNVKD_CTV.Tables[0].Rows[z]["so_dt"] != null && _dsNVKD_CTV.Tables[0].Rows[z]["so_dt"] != DBNull.Value
                                    && _dsNVKD_CTV.Tables[0].Rows[z]["so_dt"].ToString() != "") {
                                    [] _strSDT = _dsNVKD_CTV.Tables[0].Rows[z]["so_dt"].ToString().Split(';');
                                    for (int k = 0; k < _strSDT.length; k++)
                                    {
                                        SMSService.SendSMS(_strSDT[k].Trim(), sms_noidung);
                                    }
                                }
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                    }
                }

                if (PHAILAM(luong_id, "GUI_SMS_GDPBH")) {
                    try {
                        _strCmd = "select a.so_dt ";
                        _strCmd += " from " + DatabaseConstants.DB1 + ".donvi a, " + DatabaseConstants.DB1 + ".donvi_ldv b";
                        _strCmd += " where a.donvi_id = b.donvi_id ";
                        _strCmd += "    and b.loaidv_id = 2";
                        _strCmd += "    and b.donvi_id in (select tt.donvi_id ";
                        _strCmd += " from " + DatabaseConstants.DB2 + ".hd_thanhtoan tt, " + DatabaseConstants.DB2 + ".hd_thuebao tb where tt.hdtt_id = tb.hdtt_id and tb.hdtb_id = " + hdtb_id + " ) ";
                                _dsGDPBH = bangts.getDataFromSQL(_strCmd, "ds");
                        if (_dsGDPBH != null && _dsGDPBH.Tables.Count > 0 && _dsGDPBH.Tables[0].Rows.Count > 0) {
                            sms_noidung = tcchung.LAY_NOIDUNG_TINNHAN(hdtb_id, NHOM_QT.DIEUHANH_THICONG);
                            for (int z = 0; z < _dsGDPBH.Tables[0].Rows.Count; z++)
                            {
                                if (_dsGDPBH.Tables[0].Rows[z]["so_dt"] != null && _dsGDPBH.Tables[0].Rows[z]["so_dt"] != DBNull.Value
                                    && _dsGDPBH.Tables[0].Rows[z]["so_dt"].ToString() != "") {
                                    [] _strSDT = _dsGDPBH.Tables[0].Rows[z]["so_dt"].ToString().Split(';');
                                    for (int k = 0; k < _strSDT.length; k++)
                                    {
                                        SMSService.SendSMS(_strSDT[k].Trim(), sms_noidung);
                                    }
                                }
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                    }
                }
            }

            if (loaihd_id == LoaiHopDong.DI_CHUYEN) {
                //region Di chuyển

                if (PHAILAM(luong_id, "TUDONG_HTHS")) {
                    hoancong.GIAOPHIEU_TD_HOST_THUHOI_1(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, 0, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, tt_nd.donvi_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                    hths_dc.HOANTHIEN_HS_DICHCHUYEN(hdtb_id, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
                    bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);

                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }

                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    ht = true;
                }
                else {
                    hoancong.HOANTHANH_HOANCONG(hdtb_id, phieu_id, tthd_dich_id, (int)tt_nd.nhanvien_id);
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_HC"))
                        hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, tt_nd.donvi_id, donvi_nhan_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                    ht = false;
                }

                //Kieunv|03/12/2015: cập nhật thông tin đấu nối tb vào kết cuối
                LapThueBaoVaoKetCuoi();

                if ((loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
                    || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
                    || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
                    || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) && sochinh == true) {
                    for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
                    {
                        if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()
                            || dsIn.Tables[0].Rows[i]["thuebao_cha_id"].ToString() == thuebao_id.ToString()) {
                            vhdtb_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["hdtb_id"].ToString());
                            vphieu_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["phieu_id"].ToString());

                            if (PHAILAM(luong_id, "TUDONG_HTHS")) {
                                hoancong.GIAOPHIEU_TD_HOST_THUHOI_1(vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, 0, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                hoancong.GIAOPHIEU_TD_KHI_HC(vhdtb_id, vphieu_id, hg_phieu_id, tt_nd.donvi_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                                hths_dc.HOANTHIEN_HS_DICHCHUYEN(vhdtb_id, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
                                bangts.CapNhat_TT_Phieu_HTHS(vhdtb_id);
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }

                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                ht = true;
                            }
                            else {
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_HC"))
                                    hoancong.GIAOPHIEU_TD_KHI_HC(vhdtb_id, vphieu_id, hg_phieu_id, tt_nd.donvi_id, donvi_nhan_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                                hoancong.HOANTHANH_HOANCONG(vhdtb_id, vphieu_id, tthd_dich_id, (int)tt_nd.nhanvien_id);
                                ht = false;
                            }
                        }
                    }
                }
                //endregion
            }
            if (loaihd_id == LoaiHopDong.CHUYENDOI_LH) {
                //region Chuyển đổi LH
                        HoanThienHoSoFacade hths = new HoanThienHoSoFacade();

                if (PHAILAM(luong_id, "TUDONG_HTHS")) {
                    hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, tt_nd.donvi_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                    hths.HOANTHIEN_HS_CHUYENDOI_LHTB(hdtb_id, dtpNgayBG.Value);
                    bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }

                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    ht = true;
                }
                else {
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_HC"))
                        hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, tt_nd.donvi_id, donvi_nhan_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                    hoancong.HOANTHANH_HOANCONG(hdtb_id, phieu_id, tthd_dich_id, (int)tt_nd.nhanvien_id);
                    ht = false;
                }

                //Kieunv|03/12/2015: cập nhật thông tin đấu nối tb vào kết cuối
                LapThueBaoVaoKetCuoi();

                if ((loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
                    || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
                    || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
                    || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) && sochinh == true) {
                    for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
                    {
                        if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                            vhdtb_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["hdtb_id"].ToString());
                            vphieu_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["phieu_id"].ToString());
                            hoancong.HOANTHANH_HOANCONG(vhdtb_id, vphieu_id, tthd_dich_id, (int)tt_nd.nhanvien_id);
                            if (PHAILAM(luong_id, "TUDONG_HTHS")) {
                                hoancong.GIAOPHIEU_TD_KHI_HC(vhdtb_id, vphieu_id, hg_phieu_id, tt_nd.donvi_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                                hths.HOANTHIEN_HS_CHUYENDOI_LHTB(vhdtb_id, dtpNgayBG.Value);
                                bangts.CapNhat_TT_Phieu_HTHS(vhdtb_id);
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }

                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                ht = true;
                            }
                            else {
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_HC"))
                                    hoancong.GIAOPHIEU_TD_KHI_HC(vhdtb_id, vphieu_id, hg_phieu_id, tt_nd.donvi_id, donvi_nhan_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                                ht = false;
                            }
                        }
                    }
                }
                //endregion
            }
            if (loaihd_id == LoaiHopDong.KHOIPHUC_SD) {
                //region Khôi phục

                if (PHAILAM(luong_id, "TUDONG_HTHS")) {
                    hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, tt_nd.donvi_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                    hths_kptl.HOANTHIEN_HS_KPTL(hdtb_id, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
                    bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }

                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                        try {
                            hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                        }
                        catch (Exception) { }
                    }
                    ht = true;
                }
                else {
                    if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_HC"))
                        hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, tt_nd.donvi_id, donvi_nhan_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                    hoancong.HOANTHANH_HOANCONG(hdtb_id, phieu_id, tthd_dich_id, (int)tt_nd.nhanvien_id);
                    ht = false;
                }

                if ((loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
                    || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
                    || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
                    || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) && sochinh == true) {
                    for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
                    {
                        if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                            vhdtb_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["hdtb_id"].ToString());
                            vphieu_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["phieu_id"].ToString());

                            if (PHAILAM(luong_id, "TUDONG_HTHS")) {
                                hoancong.GIAOPHIEU_TD_KHI_HC(vhdtb_id, vphieu_id, hg_phieu_id, tt_nd.donvi_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                                hths_kptl.HOANTHIEN_HS_KPTL(vhdtb_id, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
                                bangts.CapNhat_TT_Phieu_HTHS(vhdtb_id);
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }

                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(1, vhdtb_id, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                                    try {
                                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(vhdtb_id, 0, vphieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                                    }
                                    catch (Exception) { }
                                }
                                ht = true;
                            }
                            else {
                                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_HC"))
                                    hoancong.GIAOPHIEU_TD_KHI_HC(vhdtb_id, vphieu_id, hg_phieu_id, tt_nd.donvi_id, donvi_nhan_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
                                hoancong.HOANTHANH_HOANCONG(vhdtb_id, vphieu_id, tthd_dich_id, (int)tt_nd.nhanvien_id);
                                ht = false;
                            }
                        }
                    }
                }
                //endregion
            }


            if (PHAILAM(luong_id, "KT_HOANCONG_HD_BANCHEO")) {
                try {
                    new BusinessFacade.BanCheoFacade.HopDong().hoangcong_hopdong_bancheo(hdtb_id);
                }
                catch (Exception ex)
                {
                    tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action",
                        "vuser_name", tt_nd.ma_nd,
                        "vcomputer_name", tt_nd.may_cn,
                        "vaction_name", "frmHoanCongCoDinh_HC",
                        "vkey_parameter", "hdtb_id=" + hdtb_id.ToString() + ",ngay_cn=" + tt_nd.ngay_cn + ", Err : " + ex.ToString(),
                        "vserver_name", tt_nd.ip,
                        "vnote", " Hoàn công cố định");
                }
            }

            if (ht)
                Message_Box.ShowTB("Hoàn công và hoàn thiện hồ sơ thành công! ");
            else
                Message_Box.ShowTB("Hoàn công thành công! ");

            HienThiDanhSachHDTB();
            inDex_load = 0;
        }
        catch (err) {
            new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
        }
    }
}

//Kieunv|03/12/2015: cập nhật thông tin đấu nối tb vào kết cuối
void LapThueBaoVaoKetCuoi()
{
    try {
                DataTable hdtb = bangts.GetDataSql("select thuebao_id from " + DatabaseConstants.DB2 + ".hd_thuebao where hdtb_id=" + hdtb_id);
                long vthuebao_id = 0, vketcuoi_id = 0, vvitri = 0, vvitri2 = 0, vdoicap = 0, vdoicap2 = 0, vcap_id = 0, vdaucuoi = 0;
        if (hdtb.Rows.Count > 0) {
            long.TryParse(hdtb.Rows[0]["thuebao_id"].ToString(), out vthuebao_id);
                    DataTable hdtbCD = tchopdong.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id).Tables[0];
            if (hdtbCD.Rows.Count > 0 && vthuebao_id != 0) {
                long.TryParse(hdtbCD.Rows[0]["ketcuoi_id"].ToString(), out vketcuoi_id);
                long.TryParse(hdtbCD.Rows[0]["vitri"].ToString(), out vvitri);
                long.TryParse(hdtbCD.Rows[0]["vitri_2"].ToString(), out vvitri2);
                long.TryParse(hdtbCD.Rows[0]["doicap"].ToString(), out vdoicap);
                long.TryParse(hdtbCD.Rows[0]["doicap_2"].ToString(), out vdoicap2);
                long.TryParse(hdtbCD.Rows[0]["cap_id"].ToString(), out vcap_id);

                new KhaiThacBaoDuongFacade().Lap_dbtb_vao_ketcuoi(
                    vthuebao_id,
                    DichVuVienThong.CO_DINH,
                    vketcuoi_id, vvitri, vvitri2, vdoicap,
                    vdoicap2, vcap_id, vdaucuoi,
                    tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
            }
        }
    }
    catch
    {
    }
}

        private bool KT_DuLieuNhap(int luong_id)
{
            DataRow dr = gvDanhSach.GetFocusedDataRow();
    if (!chkNgayGV.Checked) {
        Message_Box.ShowWarning("Hãy chọn ngày giao việc !");
        chkNgayGV.Focus();
        return false;
    }

    if (PHAILAM(luong_id, "GIAOVIEC")) {
        if (gridviewGiaoviec.DataRowCount <= 0) {
            Message_Box.ShowWarning("Hãy nhập nhân viên thực hiện  !");
            return false;
        }
    }

    if (!chkNgayBG.Checked) {
        Message_Box.ShowWarning("Hãy nhập ngày thực hiện!");
        chkNgayBG.Focus();
        return false;
    }
    //Kiếm tra với luong_id, thì fai làm những gì
    //1. Cập nhật thông tin NE
    if (PHAILAM(luong_id, "CAPNHAT_NE")) {
        if (sochinh) {
            if (txtNE.Text == "" || ne_id == 0)//minhnt sửa, Long yc
            {
                Message_Box.ShowWarning("Hãy nhập thông tin NE !");
                btnChonNe.Focus();
                return false;
            }
            if (loaihd_id == LoaiHopDong.DI_CHUYEN) {
                if (giaophieutc.PHAIDOI_NE(hdtb_id)) {
                    if (ne_id == ne_id_cu) {
                        if (giaophieutc.KIEMTRA_NE(hdtb_id)) {
                            Message_Box.ShowWarning("Hãy chọn Ne mới !");
                            return false;
                        }
                    }
                }
            }
            //else if (loaihd_id == LoaiHopDong.CHUYENDOI_LH)
            //{
            //    if (ne_id == ne_id_cu)
            //    {
            //        if (giaophieutc.KIEMTRA_NE(hdtb_id))
            //        {
            //            Message_Box.ShowWarning("Hãy chọn Ne mới !");
            //            return false;
            //        }
            //    }
            //}
            else if (loaihd_id == LoaiHopDong.KHOIPHUC_SD) {
                if (bangts.KT_THUEBAO_SD_NE(ne_id, txtMaTB.Text.Trim())) {
                    Message_Box.ShowWarning("Ne đã được sử dụng. Bạn hãy chọn Ne khác !");
                    return false;
                }
            }
        }
    }
    else //Áp dụng trong TH k chọn Ne mà nhập text
    {
        if (PHAILAM(luong_id, "NHAP_NE_TEXT")) {
            kt_TramVeTinh = new BangTuDienFacade().THUCTHI_FUNCTION("FN_KiemTra_NE_TramVeTinh", DatabaseConstants.DB2, new object[] { "vhdtb_id", hdtb_id.ToString(), "vtramvetinh_id", cboTramVeTinh.EditValue.ToString() });
            if (txtNE.Text == "" && kt_TramVeTinh == "0") {
                Message_Box.ShowWarning("Hãy nhập thông tin NE !");
                txtNE.Focus();
                return false;
            }
        }
    }

    //2. Cập nhật thông tin cáp
    if (PHAILAM(luong_id, "CAPNHAT_CAP") && status == TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString()) {
        if (PHAILAM(luong_id, "PHAINHAP_CAP")) {
            if (txtCapGoc.Text == "") {
                Message_Box.ShowWarning("Hãy nhập thông tin Cáp gốc !");
                txtCapGoc.Focus();
                return false;
            }

            if (txtTuCapGoc.Text == "") {
                Message_Box.ShowWarning("Hãy nhập thông tin Tủ cáp gốc !");
                txtTuCapGoc.Focus();
                return false;
            }

            if (txtDoiCapGoc.Text == "") {
                Message_Box.ShowWarning("Hãy nhập thông tin Đôi cáp gốc !");
                txtDoiCapGoc.Focus();
                return false;
            }

            if (txtCapNgon.Text == "") {
                Message_Box.ShowWarning("Hãy nhập thông tin Cáp ngọn !");
                txtCapNgon.Focus();
                return false;
            }

            if (txtDoiCapNgon.Text == "") {
                Message_Box.ShowWarning("Hãy nhập thông tin Đôi cáp ngọn !");
                txtDoiCapNgon.Focus();
                return false;
            }
        }
        if (txtSoMetDay.Text.Trim() == "" || txtSoMetDay.Text.Trim() == "0") {
            Message_Box.ShowWarning("Hãy nhập thông tin Cự ly (Số mét dây) !");
            txtSoMetDay.Focus();
            return false;
        }
        if (txtSoMetDay.Text.Trim() != "") {
            s = txtSoMetDay.Text;
            if (!CommonFunction.CheckIsNumber(CommonFunction.Unformat_tien(s))) {
                Message_Box.ShowWarning("Cự ly phải là kiểu số");
                txtSoMetDay.Text = "0";
                txtSoMetDay.Focus();
                return false;
            }
        }
    }

    System.TimeSpan diffResult = dtpNgayBG.Value.Subtract(dtpNgayGV.Value);
    if (diffResult.Seconds < 0 || diffResult.Minutes < 0 || diffResult.Hours < 0 || diffResult.Days < 0
        || diffResult.Milliseconds < 0) {
        Message_Box.ShowWarning("Ngày giao việc không được lớn hơn ngày hoàn thành!");
        return false;
    }
    //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
    ngay_sys;
    ngay_tt;
    ngay_ht;
    ngay_sys = dr["thangnam_sys"].ToString().Trim();
    ngay_tt = dr["thangnam_tt"].ToString().Trim();
    ngay_ht = dtpNgayBG.Value.ToString("dd/MM/yyyy");
    if (Number(cboLoaiHD.SelectedValue) != LoaiHopDong.KHOIPHUC_SD) {
        if (!KiemTraDK_HoanThanh(ngay_tt, ngay_ht))//Nếu ngày thanh toán > ngày hoàn thành
        {
            Message_Box.ShowWarning(" Thuê bao " + dr["ma_tb"].ToString().Trim() + " có ngày thanh toán : " + ngay_tt + " \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán");
            return false;
        }
    }
    if (!KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
    {
        Message_Box.ShowWarning("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
        return false;
    }
    ngay_gv = "";
    ngay_gv = dtpNgayGV.Value.ToString("dd/MM/yyyy");
    if (!KiemTraDK_HoanThanh(ngay_gv, ngay_sys))//Nếu ngày giao viêc > ngày hiện tại
    {
        Message_Box.ShowWarning("Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại");
        return false;
    }
    //end

    if (PHAILAM(luong_id, "CAPNHAT_TRAM_VETINH_CD")) {
        if (cboTramVeTinh.EditValue.ToString() == "" || cboTramVeTinh.Text == "") {
            Message_Box.ShowWarning("Hãy chọn trạm vệ tinh  !");
            cboTramVeTinh.Focus();
            return false;
        }
    }

    return true;
}

tsbtnChapNhan_Click()
{
    if (CapNhat()) {
                long in_hdtb_id = hdtb_id;
        HienThiDanhSachHDTB();
        gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        for (int i = 0; i < gvDanhSach.RowCount; i++)
        if (in_hdtb_id.Equals(gvDanhSach.GetDataRow(i)["hdtb_id"])) {
            gvDanhSach.FocusedRowHandle = i;
            gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(DevExpress.XtraGrid.GridControl.AutoFilterRowHandle, i));
            break;
        }
    }
}

        private bool CapNhat()
{
    try {
        if (!KT_DuLieuNhap(luong_id))
            return false;

        if (!PHAILAM(luong_id, "GIAOVIEC")) {
            TuDongGiaoViec();
        }

        //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
        ngay_sys;
        ngay_tt;
        ngay_ht;
                DataRow dr = gvDanhSach.GetFocusedDataRow();
        ngay_sys = tt_nd.ngay_cn.ToString("dd/MM/yyyy");
        ngay_tt = dr["thangnam_tt"].ToString().Trim();
        ngay_ht = dtpNgayBG.Value.ToString("dd/MM/yyyy");
        if (Number(cboLoaiHD.SelectedValue) != LoaiHopDong.KHOIPHUC_SD) {
            if (!KiemTraDK_HoanThanh(ngay_tt, ngay_ht))//Nếu ngày thanh toán > ngày hoàn thành
            {
                Message_Box.ShowWarning(" Thuê bao " + dr["ma_tb"].ToString().Trim() + " có ngày thanh toán : " + ngay_tt + " \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán");
                return false;
            }
        }
        if (!KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
        {
            Message_Box.ShowWarning("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
            return false;
        }
        //end

        //Tách biệt từng công đoạn, từng việc phải làm
        //if (loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ)
        //{
        //    //30B+D khong phai cap nhat NE
        //}
        //else
        //{
        //    if (PHAILAM(luong_id, "CAPNHAT_NE"))
        //    {
        //        lapHD.CAPNHAT_SONE(hdtb_id, txtMaTB.Text.Trim(), ne_id, txtNE.Text.Trim(), 0, tramtb_id);
        //    }
        //}
        // HCM : 30B+D cũng phải nhập NE
        //if (PHAILAM(luong_id, "CAPNHAT_NE")) --minhnt sửa--30.03.2015: khi chọn ne thì cập nhật trạng thái luôn
        //{
        //    lapHD.CAPNHAT_SONE(hdtb_id, txtMaTB.Text.Trim(), ne_id, txtNE.Text.Trim(), 0, tramtb_id);
        //}
        // end hoàng sửa

        //dÙNG RIÊNG CHO hni
        if (PHAILAM(luong_id, "NHAP_NE_TEXT")) {
            kt_TramVeTinh = new BangTuDienFacade().THUCTHI_FUNCTION("FN_KiemTra_NE_TramVeTinh", DatabaseConstants.DB2, new object[] { "vhdtb_id", hdtb_id.ToString(), "vtramvetinh_id", cboTramVeTinh.EditValue.ToString() });
            if (kt_TramVeTinh == "0") {
                lapHD.CAPNHAT_SONE(hdtb_id, txtMaTB.Text.Trim(), ne_id, txtNE.Text.Trim(), 0, tramtb_id);
            }
        }
        if (PHAILAM(luong_id, "CAPNHAT_TRAM_VETINH_CD")) {
            lapHD.CAPNHAT_TRAM_VETINH(hdtb_id, Number(cboTramVeTinh.EditValue));
        }
        if (sochinh) {
            if (PHAILAM(luong_id, "CAPNHAT_CAP") && status == TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString()) {
                        //hoancong.CAPNHAT_THONGTIN_THICONG(hdtb_id, (int)tt_nd.nhanvien_id, txtTuyenCap.Text, txtTuCapNgon.Text, txtDoiCapNgon.Text, txtHopCapNgon.Text, Number(txtSoMetDay.Text));
                        long vdoicap_ngon = -1;
                        long vdoicap_goc = -1;
                if (txtDoiCapNgon.Text.Trim() != "")
                    vdoicap_ngon = Convert.ToInt64(txtDoiCapNgon.Text.Trim());
                if (txtDoiCapGoc.Text.Trim() != "")
                    vdoicap_goc = Convert.ToInt64(txtDoiCapGoc.Text.Trim());

                hoancong.CAPNHAT_THONGTIN_THICONG(hdtb_id, (int)tt_nd.nhanvien_id, txtTuyenCap.Text.Trim(), txtLienTu.Text.Trim(),
                    vdoicap_ngon, txtHopCapNgon.Text.Trim(), txtTuCapNgon.Text.Trim(),
                    Number(cboLoaiCap.SelectedValue), txtCapGoc.Text.Trim(), txtCapNgon.Text.Trim(),
                    vdoicap_goc, txtTuCapGoc.Text.Trim(), Number(txtSoMetDay.Text));
            }
        }
        else {
            if (loaitb_id == LoaiHinhTB.PABX) {
                if (PHAILAM(luong_id, "CAPNHAT_CAP") && status == TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString()) {
                            //hoancong.CAPNHAT_THONGTIN_THICONG(hdtb_id, (int)tt_nd.nhanvien_id, txtTuyenCap.Text, txtTuCapNgon.Text, txtDoiCapNgon.Text, txtHopCapNgon.Text, Number(txtSoMetDay.Text));
                            long vdoicap_ngon = -1;
                            long vdoicap_goc = -1;
                    if (txtDoiCapNgon.Text.Trim() != "")
                        vdoicap_ngon = Convert.ToInt64(txtDoiCapNgon.Text.Trim());
                    if (txtDoiCapGoc.Text.Trim() != "")
                        vdoicap_goc = Convert.ToInt64(txtDoiCapGoc.Text.Trim());

                    hoancong.CAPNHAT_THONGTIN_THICONG(hdtb_id, (int)tt_nd.nhanvien_id, txtTuyenCap.Text.Trim(), txtLienTu.Text.Trim(),
                        vdoicap_ngon, txtHopCapNgon.Text.Trim(), txtTuCapNgon.Text.Trim(),
                        Number(cboLoaiCap.SelectedValue), txtCapGoc.Text.Trim(), txtCapNgon.Text.Trim(),
                        vdoicap_goc, txtTuCapGoc.Text.Trim(), Number(txtSoMetDay.Text));
                }
            }
        }
                long vphieu_id = 0;
                long vhdtb_id = 0;
        if (loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
            || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
            || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
            || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) {
            if (sochinh == true) {
                for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
                {
                    if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                        vphieu_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["phieu_id"].ToString());
                        vhdtb_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["hdtb_id"].ToString());

                        if (PHAILAM(luong_id, "CAPNHAT_TRAM_VETINH_CD")) {
                            lapHD.CAPNHAT_TRAM_VETINH(vhdtb_id, Number(cboTramVeTinh.EditValue));
                        }

                        if (PHAILAM(luong_id, "CAPNHAT_CAP")) {
                                    long vdoicap_ngon = -1;
                                    long vdoicap_goc = -1;
                            if (txtDoiCapNgon.Text.Trim() != "")
                                vdoicap_ngon = Convert.ToInt64(txtDoiCapNgon.Text.Trim());
                            if (txtDoiCapGoc.Text.Trim() != "")
                                vdoicap_goc = Convert.ToInt64(txtDoiCapGoc.Text.Trim());

                            hoancong.CAPNHAT_THONGTIN_THICONG(vhdtb_id, (int)tt_nd.nhanvien_id, txtTuyenCap.Text.Trim(), txtLienTu.Text.Trim(),
                                vdoicap_ngon, txtHopCapNgon.Text.Trim(), txtTuCapNgon.Text.Trim(),
                                Number(cboLoaiCap.SelectedValue), txtCapGoc.Text.Trim(), txtCapNgon.Text.Trim(),
                                vdoicap_goc, txtTuCapGoc.Text.Trim(), Number(txtSoMetDay.Text));
                        }
                        hoancong.CAPNHAT_THONGTIN_PHIEU(vphieu_id, Number(cboNguoiGV.SelectedValue), (int)tt_nd.nhanvien_id, dtpNgayBG.Value, dtpNgayGV.Value);
                    }
                }
            }
            else {
                //Với số không phải là số chính, có số chính là trong danh bạ

                List < DataRow > lstChon = dsIn.Tables[0].AsEnumerable().Where(r => Convert.ToInt64(r["HDKH_ID"]) == hdkh_id && r["HDTB_CHA_ID"].ToString() == ""
                    && r["THUEBAO_CHA_ID"].ToString() != "").ToList();

                for (int i = 0; i < lstChon.Count; i++)
                {
                    if (Convert.ToInt64(lstChon[i]["thuebao_cha_id"].ToString()) == thuebao_cha_id) {
                        vphieu_id = Convert.ToInt64(lstChon[i]["phieu_id"].ToString());
                        vhdtb_id = Convert.ToInt64(lstChon[i]["hdtb_id"].ToString());
                        if (vhdtb_id != hdtb_id) {
                            if (PHAILAM(luong_id, "CAPNHAT_TRAM_VETINH_CD")) {
                                lapHD.CAPNHAT_TRAM_VETINH(vhdtb_id, Number(cboTramVeTinh.EditValue));
                            }

                            if (PHAILAM(luong_id, "CAPNHAT_CAP")) {
                                        long vdoicap_ngon = 0;
                                        long vdoicap_goc = 0;
                                if (txtDoiCapNgon.Text.Trim() != "")
                                    vdoicap_ngon = Convert.ToInt64(txtDoiCapNgon.Text.Trim());
                                if (txtDoiCapGoc.Text.Trim() != "")
                                    vdoicap_goc = Convert.ToInt64(txtDoiCapGoc.Text.Trim());

                                hoancong.CAPNHAT_THONGTIN_THICONG(vhdtb_id, (int)tt_nd.nhanvien_id, txtTuyenCap.Text.Trim(), txtLienTu.Text.Trim(),
                                    vdoicap_ngon, txtHopCapNgon.Text.Trim(), txtTuCapNgon.Text.Trim(),
                                    Number(cboLoaiCap.SelectedValue), txtCapGoc.Text.Trim(), txtCapNgon.Text.Trim(),
                                    vdoicap_goc, txtTuCapGoc.Text.Trim(), Number(txtSoMetDay.Text));
                            }
                            hoancong.CAPNHAT_THONGTIN_PHIEU(vphieu_id, Number(cboNguoiGV.SelectedValue), (int)tt_nd.nhanvien_id, dtpNgayBG.Value, dtpNgayGV.Value);
                        }
                    }
                }
            }

        }
        hoancong.CAPNHAT_THONGTIN_PHIEU(phieu_id, Number(cboNguoiGV.SelectedValue), (int)tt_nd.nhanvien_id, dtpNgayBG.Value, dtpNgayGV.Value);
        //bangts.Update_tt_giaoviec((int)tt_nd.nhanvien_id, dtpNgayBG.Value, hdtb_id, phieu_id, Convert.ToInt64(cboNguoiGV.SelectedValue));
        Message_Box.ShowTB("Cập nhật dữ liệu lên viễn thông tỉnh thành công.");

        return true;
    }
    catch (err) {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
        return false;
    }
}

btnLayTTMoi_Click()
{
    HienThiDanhSachHDTB();
}

btnInPhieuTC_Click()
{
}
//endregion

TraPhieu()
{
    try {
                frmTraPhieuTC frm = new frmTraPhieuTC(txtMaTB.Text.Trim(), Number(cboLoaiHD.SelectedValue), DichVuVienThong.CO_DINH, tt_nd.nhanvien_id,
        txtMaTB.Text.Trim(), "0", "0", 3);
        frm.ShowDialog();
        if (frm.kt_thuchien) {
            vma_tb = txtMaTB.Text.Trim();
            HienThiDanhSachHDTB();

            // Tìm kiếm row vừa cập nhật
            gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
            for (int i = 0; i < gvDanhSach.RowCount; i++)
            if (vma_tb == gvDanhSach.GetDataRow(i)["ma_tb"].ToString()) {
                gvDanhSach.FocusedRowHandle = i;
                //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(DevExpress.XtraGrid.GridControl.AutoFilterRowHandle, i));
                break;
            }
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("" + ex.ToString());
    }
}
//region frmHoanCongCoDinh_KeyUp
frmHoanCongCoDinh_KeyUp(KeyEventArgs e)
{
    if (e.KeyCode == Keys.F5)
        HienThiDanhSachHDTB();
}
//endregion

checkAll_CheckedChanged()
{
    for (int k = 0; k < dtList.Rows.Count; k++)
    {
        if (checkAll.Checked)
            dtList.Rows[k]["stt"] = 1;
        else
            dtList.Rows[k]["stt"] = 0;
    }
}

cboLoaiHD_SelectedValueChanged()
{
    try {
        cboLoaiHD_SelectedValueChanged();
    }
    catch (err) {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    }
}

cboLoaiHD_SelectedValueChanged()
{
    try {
        if (cboLoaiHD.Items.Count > 0) {
            if (cboLoaiHD.Items.Count > 0) {
                if (cboLoaiHD.SelectedValue.ToString() == "System.Data.DataRowView") return;
                loaihd_id = Number(cboLoaiHD.SelectedValue);
                tsbtnHuyKH.Visible = false;
                if (loaihd_id == LoaiHopDong.DAT_MOI || loaihd_id == LoaiHopDong.DI_CHUYEN) {
                    tsbtnHuyKH.Visible = true;
                    tsbtnHuyKH.Enabled = false;
                }
                // table, result, where, groupby;
                //table = "" + DatabaseConstants.DB2 + ".huonggiao a," + DatabaseConstants.DB2 + ".quytrinh b";
                //result = " distinct b.quytrinh, b.quytrinh_id ";
                //where = "  a.quytrinh_id = b.quytrinh_id and b.loaihd_id in (" + loaihd_id + ") ";
                //where += " and b.dichvuvt_id in (" + dsdichvuvt_id + ") and  a.tthd_id = " + tthd_id + " order by quytrinh desc ";
                //groupby = "";
                //ds = bangts.Laythongtin_table(table, where, result, groupby);
                ds = bangts.Lay_DS_QuyTrinh(loaihd_id.ToString(), dsdichvuvt_id, tthd_id.ToString(), 0);
                if (ds.Tables[0].Rows.Count > 0) {
                    cboQuyTrinh.DataSource = ds.Tables[0];
                    cboQuyTrinh.ValueMember = "quytrinh_id";
                    cboQuyTrinh.DisplayMember = "quytrinh";
                    if (_vquytrinh_id != 0) {
                        cboQuyTrinh.SelectedValue = _vquytrinh_id;
                    }
                }
                else {
                    quytrinh_id = -1;
                    HienThiDanhSachHDTB();
                    cboQuyTrinh.DataSource = null;
                    cboQuyTrinh.Text = "--Không có quy trình--";
                }

                //chkNgayBG.Checked = false;
                //dtpNgayBG.Enabled = false;
                if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.DAT_MOI ||
                    Number(cboLoaiHD.SelectedValue) == LoaiHopDong.DI_CHUYEN ||
                    Number(cboLoaiHD.SelectedValue) == LoaiHopDong.KHOIPHUC_SD) {
                    chkLayTSo.Enabled = true;
                }
                else {
                    chkLayTSo.Enabled = false;
                    chkLayTSo.Checked = false;
                }
            }
        }
    }
    catch (err) {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    }
}

chkNgayBG_CheckedChanged()
{
    dtpNgayBG.Enabled = chkNgayBG.Checked;
}

txtMaGD_KeyPress(KeyPressEventArgs e)
{
    try {
        if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
            if (txtMaGD.Text != "") {
                        int vloai_id = 0;// 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
                if (rdoPhieuMoi.Checked)
                    vloai_id = 0;
                else
                    vloai_id = 1;
                dsIn = giaophieutc.LAY_DS_PHIEU_HOANCONG_119(DichVuVienThong.CO_DINH, tthd_id,
                    (int)tt_nd.nhanvien_id, txtMaGD.Text, 0, Number(cboLoaiHD.SelectedValue), 0, 0, huonggiao_id, vloai_id);
                gridDanhSach.DataSource = dtList = dsIn.Tables[0];
                if (dtList.Rows.Count <= 0)
                    Clean();
            }
        }
    }
    catch (err) {
        Message_Box.ShowError("Có lỗi: " + exp.ToString());
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    }

}

        public void txtMaTB_KeyPress(KeyPressEventArgs e)
{
    if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        if (txtMaTB.Text != "") {
                    int vloai_id = 0;// 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
            if (rdoPhieuMoi.Checked) {
                vloai_id = 0;
            }
            else {
                vloai_id = 1;
            }
            dsIn = giaophieutc.LAY_DS_PHIEU_HOANCONG_119(DichVuVienThong.CO_DINH, tthd_id,
                (int)tt_nd.nhanvien_id, txtMaTB.Text, 0, Number(cboLoaiHD.SelectedValue), 0, 0, huonggiao_id, vloai_id);
            gridDanhSach.DataSource = dtList = dsIn.Tables[0];
        }
    }
}

//region "Show Báo cáo"
ShowBaoCao(tenfile)
{
            SSRTool report;
            BaoCaoFacade objbaocao = new BaoCaoFacade();
    try {
        //Lấy ds thiết bị đăng ký mua
        thietbi = "";
        thietbi = lapHD.LAY_THONGTIN_TBI_HDTB_ID(hdtb_id);

                int k = 0;
                int count = dsIn.Tables[0].Rows.Count;
        gvDanhSach.CloseEditor();
        for (int i = 0; i < count; i++)
        {
            if (dsIn.Tables[0].Rows[k]["CHON"].Equals(true)) {
                dsIn.Tables[0].Rows.RemoveAt(k);
            }
            else k++;
        }
        report = new SSRTool();
        dsIn.Tables[0].TableName = "BAOCAO";
        report.DataSource = dsIn;
        report.Parameters.Add("donvigui", tt_nd.ten_dv.ToUpper());
        report.Parameters.Add("ngayin", clsBaoCao.ngayin);
        report.Parameters.Add("ngaygiao", tt_nd.ngay_cn.ToShortDateString());
        report.Parameters.Add("diadanh", clsBaoCao.diadanh);
        report.Parameters.Add("nhanvien", "");
        report.Parameters.Add("thietbi", thietbi);
        fullpath;
        fullpath = clsBaoCao.reportpath + "\\ReportFile\\BienBanNT\\" + tenfile;
        report.ReportFileName = fullpath;
        report.ProductName = clsBaoCao.reportFooter + " " + DateTime.Now;
        report.ShowReport(frmFormMain.ActiveForm, true);
    }
    catch (err) {
        MessageBox.Show(exp.Message, "Thong bao");
    }
}
//endregion

txtSoMetDay_Leave()
{
    if (txtSoMetDay.Text.Trim() != "") {
        s = txtSoMetDay.Text;
        if (!CommonFunction.CheckIsNumber(CommonFunction.Unformat_tien(s))) {
            Message_Box.ShowWarning("Số mét dây phải là kiểu số");
            txtSoMetDay.Text = "0";
            txtSoMetDay.Focus();
            return;
        }
    }
}

chkLayTSo_CheckedChanged()
{
    if (chkLayTSo.Checked == true) {
                long imadoicap = 0;
                int kieuld_id = 0;  //1<->Lap kem; 2<->Lap tren duong co san
                int kieu = 0;
        if (txtSoMayTN.Text.Trim() == "") {
            Message_Box.ShowWarning("Thuê bao đang lắp trên đường không số. \n Bạn không thể lấy các thông số đã có.");
            chkLayTSo.Checked = false;
            return;
        }
                DataRow dr = gvDanhSach.GetFocusedDataRow();

        if (dr["madoicap"].ToString() != "")
            imadoicap = Convert.ToInt64(dr["madoicap"].ToString());
        if (dr["kieuld_id"].ToString().Trim() == "" || imadoicap == 0) {
            Message_Box.ShowWarning("Không tìm thấy thông tin!");
            return;
        }
        kieuld_id = Number(dr["kieuld_id"].ToString());
        ////Nếu lắp trên đường có sẵn -> kieu=2
        //if (kieuld_id == KieuLapDat.DM_CD_CO_SAN_ADSL || kieuld_id == KieuLapDat.DM_CD_CO_SAN_MYTV || kieuld_id == KieuLapDat.DM_CD_CO_SAN_MGW
        //    || kieuld_id == KieuLapDat.DC_THOAI_DENDUONG_ADSL_COSAN || kieuld_id == KieuLapDat.DC_THOAI_DENDUONG_MYTV_COSAN || kieuld_id == KieuLapDat.DC_THOAI_DENDUONG_MEGAWAN_COSAN)
        //    kieu = 2;
        //else
        //    if (kieuld_id == KieuLapDat.DM_CD_KEM_ADSL || kieuld_id == KieuLapDat.DM_CD_KEM_MYTV || kieuld_id == KieuLapDat.DM_CD_KEM_MGW
        //        || kieuld_id == KieuLapDat.DC_THOAI_KEM_ADSL || kieuld_id == KieuLapDat.DC_THOAI_KEM_MYTV)
        //        kieu = 1;
        kieu = Number(checkdata.MAP_ID("kieu", DatabaseConstants.DB2 + ".kieu_ld", "where kieuld_id = " + kieuld_id));

                ds = new DataSet();
        ds = tchopdong.Lay_thongso_thicong(txtSoMayTN.Text.Trim(), imadoicap, kieu);
        if (ds.Tables[0].Rows.Count == 0) {
            Message_Box.ShowWarning("Không tìm thấy thông tin!");
            return;
        }

        txtTuyenCap.Text = ds.Tables[0].Rows[0]["tuyencap"].ToString();
        txtLienTu.Text = ds.Tables[0].Rows[0]["lientu"].ToString();
        if (ds.Tables[0].Rows[0]["loaicap_id"].ToString() != "")
            cboLoaiCap.SelectedValue = Number(ds.Tables[0].Rows[0]["loaicap_id"].ToString());
        txtTuCapNgon.Text = ds.Tables[0].Rows[0]["tucap_p"].ToString();
        txtDoiCapNgon.Text = ds.Tables[0].Rows[0]["doicap_p"].ToString();
        txtHopCapNgon.Text = ds.Tables[0].Rows[0]["hopcap_p"].ToString();
        txtCapNgon.Text = ds.Tables[0].Rows[0]["cap_p"].ToString();
        txtCapGoc.Text = ds.Tables[0].Rows[0]["cap_goc"].ToString();
        txtDoiCapGoc.Text = ds.Tables[0].Rows[0]["doicap_goc"].ToString();
        txtTuCapGoc.Text = ds.Tables[0].Rows[0]["tucap_goc"].ToString();
        txtSoMetDay.Text = ds.Tables[0].Rows[0]["culy"].ToString();
        if (txtSoMetDay.Text == "")
            txtSoMetDay.Text = "0";
    }
    //else
    //    HienThiDanhSachHDTB();
}
        private bool KiemTraDK_HoanThanh(thangnam_tt, thangnam_sys) //thangnam_sys= ngay_ht
{
            bool result = false;
    if (Number(thangnam_tt.Substring(6, thangnam_tt.length - 6)) < Number(thangnam_sys.Substring(6, thangnam_sys.length - 6)))//So sánh năm
        return true;//Nếu năm thanh toán < năm hoàn thành thì true;
    else if (Number(thangnam_tt.Substring(6, thangnam_tt.length - 6)) == Number(thangnam_sys.Substring(6, thangnam_sys.length - 6)))//So sánh năm
    {
        //Nêu năm = nhau thì kiểm tra tiếp
        //Nếu tháng tt < thang ht -> true
        if (Number(thangnam_tt.Substring(3, 2)) < Number(thangnam_sys.Substring(3, 2)))
            result = true;
        else if (Number(thangnam_tt.Substring(3, 2)) == Number(thangnam_sys.Substring(3, 2)))
            //Nếu tháng & năm = nhau thì kiểm tra ngày
            if (Number(thangnam_tt.Substring(0, 2)) <= Number(thangnam_sys.Substring(0, 2)))
                return true;
            else
                return false;
        else
            result = false;//tháng tt lớn hơn thang ht
    }
    else
        result = false;

    return result;
}

tsbtnGiaoPhieu_Click()
{
    GiaoPhieu();
}

GiaoPhieu()
{
    try {
        if (!.IsNullOrEmpty(txtMaGD.Text.Trim())) {
            str = "";
            str += " select b.huonggiao_id from " + DatabaseConstants.DB2 + ".huonggiao_ldv a, " + DatabaseConstants.DB2 + ".huonggiao_ldv b, " + DatabaseConstants.DB2 + ".quytrinh c";
            str += " where a.loaidv_dich_id = b.loaidv_nguon_id";
            str += " and a.quytrinh_id = c.quytrinh_id";
            str += " and c.quytrinh_id =  b.quytrinh_id";
            str += " and a.luong_id = " + luong_id;
            var dtTemp = bangts.GetDataSql(str);
            if (dtTemp.Rows.Count > 0) {
                        frmGiaoPhieu f = new frmGiaoPhieu(txtMaGD.Text, 1, Number(dtTemp.Rows[0]["huonggiao_id"]), hdtb_id, 0, "0");
                f.luong_id = luong_id;
                f.ShowDialog();
            }
            else
                Message_Box.ShowTB("Chưa có hướng giao được gán !");
        }
        vma_tb = txtMaTB.Text.Trim();
        HienThiDanhSachHDTB();

        // Tìm kiếm row vừa cập nhật
        gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        for (int i = 0; i < gvDanhSach.RowCount; i++)
        if (vma_tb == gvDanhSach.GetDataRow(i)["ma_tb"].ToString()) {
            gvDanhSach.FocusedRowHandle = i;
            //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(DevExpress.XtraGrid.GridControl.AutoFilterRowHandle, i));
            break;
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("" + ex.ToString());
    }
}
tsbtnHoanThanh_Click()
{
    //Hoan_Thanh(); 
}

Hoan_Thanh()
{
    //try
    //{
    //    hoancong.HOANTHANH_THICONG(phieu_id, hdtb_id);
    //    // Giao phiêu về đài khu vực thực hiện hoàn công
    //    hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, HuongGiao.HOANTHIEN_HS, tt_nd.donvi_id, daikv_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
    //    Message_Box.ShowTB("Đã cập nhật thành công ");
    //    HienThiDanhSachHDTB();
    //}
    //catch (err)
    //{
    //    new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    //}
}
tsbtnGiaoViec_Click()
{
    GiaoViec();
}
GiaoViec()
{
    try {
        if (gvDanhSach.FocusedRowHandle < 0) {
            Message_Box.ShowWarning("Chưa chọn thuê bao. Bạn hãy kiểm tra lại! ");
            return;
        }
        if (cboNguoiGV.Items.Count <= 0 || cboNguoiGV.Text == "") {
            Message_Box.ShowWarning("Hãy nhập nhân viên giao việc!");
            cboNguoiGV.Focus();
            return;
        }
        if (!chkNgayGV.Checked) {
            Message_Box.ShowWarning("Hãy nhập ngày giao việc!");
            chkNgayGV.Focus();
            return;
        }
                long vnhanvien_giao_id = 0;
                DateTime vngaygiao;
        vnhanvien_giao_id = Convert.ToInt64(cboNguoiGV.SelectedValue);
        vngaygiao = dtpNgayGV.Value;
                frnGiaoPhieuNV gp = new frnGiaoPhieuNV(phieu_id, tt_nd.donvi_id, vnhanvien_giao_id, vngaygiao);
        gp.vma_tb = txtMaTB.Text.Trim();
        gp.vhdtb_id = hdtb_id;
        gp.huonggiao_id = huonggiao_id;
        gp.ShowDialog();


                // hoangpkn : 23/03/2015
                // bổ sung tự động giao việc cho số phụ ISDN
                GiaoPhieu_NVFacade objgiaophieu = new GiaoPhieu_NVFacade();
                long vphieu_id = 0;
        if ((loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
            || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
            || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
            || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) && sochinh == true) {
            for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
            {
                if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                    vphieu_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["phieu_id"].ToString());
                    objgiaophieu.Delete(vphieu_id);
                    TaoDuLieu_GPNV_ISDN(vphieu_id);
                    objgiaophieu.Insert(dsnhanviengp);
                }
            }
        }
                // end hoàng thêm

                //Hiển thị danh sách nhân viên
                ds = new DataSet();
        ds = tchopdong.LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieu_id, tt_nd.nhanvien_id, 2);
        HienThiDSNV(ds);
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi: " + ex.Message);
    }
}

TaoDuLieu_GPNV_ISDN(long vphieu_id)
{
    dsnhanviengp = new GIAOPHIEU_NV_DATA();
            ds_gpnv_isdn = new DataSet();
    ds_gpnv_isdn = bangts.getDataFromSQL("Select * from " + DatabaseConstants.DB2 + ".giaophieu_nv where phieu_id = " + phieu_id, "giaophieu_nv");
    for (int i = 0; i < ds_gpnv_isdn.Tables[0].Rows.Count; i++)
    {
        GIAOPHIEU_NV_DATA.GIAOPHIEU_NVRow row = dsnhanviengp.GIAOPHIEU_NV.NewGIAOPHIEU_NVRow();
        row.PHIEU_ID = vphieu_id;
        row.NHANVIEN_TH_ID = Number(ds_gpnv_isdn.Tables[0].Rows[i]["nhanvien_th_id"].ToString());
        row.SO_DT = ds_gpnv_isdn.Tables[0].Rows[i]["so_dt"].ToString();
        row.NHIEMVU = ds_gpnv_isdn.Tables[0].Rows[i]["nhiemvu"].ToString();
        row.GHICHU = ds_gpnv_isdn.Tables[0].Rows[i]["ghichu"].ToString();
        row.NHANVIEN_GIAO_ID = Convert.ToDecimal(ds_gpnv_isdn.Tables[0].Rows[i]["nhanvien_giao_id"].ToString());
        row.NGAYGIAO = Convert.ToDateTime(ds_gpnv_isdn.Tables[0].Rows[i]["ngaygiao"].ToString());
        dsnhanviengp.GIAOPHIEU_NV.AddGIAOPHIEU_NVRow(row);
    }
}

//region HienThiDVGT
HienThiDSNV(ds)
{
    //lvwNhanVien.Items.Clear();
    System.Data.DataTable myDataTables = ds.Tables[0];
    if (myDataTables.Rows.Count > 0) {
        chkNgayGV.Checked = true;
        dtpNgayGV.Value = Convert.ToDateTime(ds.Tables[0].Rows[0]["ngaygiao"].ToString());
        cboNguoiGV.SelectedValue = ds.Tables[0].Rows[0]["nhanvien_giao_id"].ToString();
        //dgvNhanVien.AutoGenerateColumns = false;
        gridGiaoviec.DataSource = ds.Tables[0];
        gridviewGiaoviec.BestFitColumns();
        nhanvien_tc_id = Number(ds.Tables[0].Rows[0]["nhanvien_th_id"].ToString());
        congviec_th = ds.Tables[0].Rows[0]["nhiemvu"].ToString();
    }
    else {
        gridGiaoviec.DataSource = null;
        //if (!PHAILAM(luong_id, "GIAOVIEC"))
        //{
        //    chkNgayGV.Checked = true;
        //    dtpNgayGV.Value = tt_nd.ngay_cn;
        //    //dtpNgayBG.Value = tt_nd.ngay_cn.AddSeconds(30);
        //}
        //else
        //{
        //    chkNgayGV.Checked = false;
        //    dtpNgayGV.Value = tt_nd.ngay_cn;
        //    //dtpNgayBG.Value = tt_nd.ngay_cn.AddSeconds(30);
        //}
        chkNgayGV.Checked = true;
        dtpNgayGV.Value = tt_nd.ngay_cn;

        cboNguoiGV.Text = "";
        cboNguoiGV.SelectedValue = tt_nd.nhanvien_id;
        nhanvien_tc_id = -1;
        congviec_th = "";
    }
}
//endregion

chkNgayGV_CheckedChanged()
{
    dtpNgayGV.Enabled = chkNgayGV.Checked;
}
        private int inDex_load = 0;

tsbtnTraPhieu_Click()
{
    TraPhieu();
}

btnChonNe_Click()
{
    try {
                long current_hdtb_id = hdtb_id;
        //if (loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ)
        //{
        //    Message_Box.ShowTB("Loại hình thuê bao này không phải cấp Ne");
        //    return;
        //}
        //else
        //{
        if (tramvt_id == 0 && host_id == 0) {
            Message_Box.ShowWarning("Không có thông tin Tổ thi công và Host để chọn Ne !");
            return;
        }
                //anhnt_29/11/2019: khóa trạng thái của cbb chọn số ne
                frmChonNe f;
        if (DatabaseConstants.Server.ToUpper() == "STG")
            f = new frmChonNe(tramvt_id, host_id, false);
        else
            f = new frmChonNe(tramvt_id, host_id);

        f.vtthd_id = tthd_id;
        f.ShowDialog();
        if (f.chapnhan) {
            txtNE.Text = f.ne;
            ne_id = f.ne_id;
            tramtb_id = f.vetinh_id;
            s = checkdata.MAP_ID("ten_dv", DatabaseConstants.DB2 + ".sone a, " + DatabaseConstants.DB1 + ".donvi b", "where a.donvi_id = b.donvi_id and a.ne_id = " + ne_id + "");
            if (s != "-1")
                txtTramNE.Text = s;

            if (PHAILAM(luong_id, "CAPNHAT_NE")) {
                lapHD.CAPNHAT_SONE(hdtb_id, txtMaTB.Text.Trim(), ne_id, txtNE.Text.Trim(), 0, tramtb_id);
            }

            HienThiDanhSachHDTB();
            gvDanhSach.FocusedRowHandle = FindGridIndexByValue("HDTB_ID", current_hdtb_id.ToString());
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi: " + ex.ToString());
    }
}
        private int FindGridIndexByValue(col_key, _value)
{
    try {
        for (int i = 0; i < gvDanhSach.DataRowCount; i++)
        {
            var cv = gvDanhSach.GetRowCellValue(i, col_key).ToString();
            if (cv != null && _value.Equals(cv)) {
                return i;
            }
        }
        return 0;
    }
    catch (Exception) {
        return 0;
    }

}

tsbtnVatTu_Click()
{
    if (phieu_id != 0) {
                frmVatTuThueBao frm = new frmVatTuThueBao();
        frm.phieu_id = phieu_id;
        frm.loaihd_id = loaihd_id;
        frm.hdtb_id = hdtb_id;
        frm.thuebao_id = thuebao_id;
        frm.ShowDialog();
    }
    else {
        MessageBox.Show("Bạn chưa chọn phiếu!");
    }
}

btnPhieuTC_Click()
{
    try {
        txtMaGD.Focus();
        str = "";
        //int dem = 0;
        //for (int k = 0; k < dtList.Rows.Count; k++)
        //{
        //    if (dtList.Rows[k]["stt"].ToString() == "1")
        //    {
        //        str += dtList.Rows[k]["hdtb_id"].ToString() + ",";
        //        dem++;
        //    }
        //}
        //if (dem == 0)
        //{
        //    Message_Box.ShowTB("Hãy chọn phiếu để In");
        //    return;
        //}

        if (dsIn.Tables[0].Select("CHON = true").length == 0) {
            Message_Box.ShowTB("Hãy chọn phiếu");
            return;
        }

        if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.DAT_MOI) {
            ShowBaoCao("INPHIEU_TC.rst");
        }

        if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.DI_CHUYEN) {
            ShowBaoCao("INPHIEU_TC_DC.rst");
        }

        vma_tb = txtMaTB.Text.Trim();
        HienThiDanhSachHDTB();
        // Tìm kiếm row vừa cập nhật
        gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        for (int i = 0; i < gvDanhSach.RowCount; i++)
        if (vma_tb == gvDanhSach.GetDataRow(i)["ma_tb"]) {
            gvDanhSach.FocusedRowHandle = i;
            //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(-1, i));
            break;
        }
    }
    catch (err) {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    }
}

btnBienBanNT_Click()
{
    try {
        txtMaGD.Focus();
        kt_thaydoi = false;
        gvDanhSach.CloseEditor();
        InPhieu();
        if (kt_thaydoi) {
            vma_tb = txtMaTB.Text.Trim();
            HienThiDanhSachHDTB();

            // Tìm kiếm row vừa cập nhật
            gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
            for (int i = 0; i < gvDanhSach.RowCount; i++)
            if (vma_tb == gvDanhSach.GetDataRow(i)["ma_tb"].ToString()) {
                gvDanhSach.FocusedRowHandle = i;
                //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(-1, i));
                break;
            }
        }

    }
    catch (err) {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
    }
}

//region In Phiếu NT
        private int nhanvien_tc_id = -1;
congviec_th = "";

InPhieu()
{
    try {
                ds_phieu = new DataSet();
        vhdkh_id = checkdata.MAP_ID("hdkh_id", DatabaseConstants.DB2 + ".hd_thuebao", "where hdtb_id = " + hdtb_id);

        if (vhdkh_id == "-1") {
            Message_Box.ShowTB("Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!");
            return;
        }

        if (dsIn.Tables[0].Select("CHON = true").length == 0) {
            Message_Box.ShowTB("Hãy chọn phiếu");
            return;
        }

                frmInBienBan obj = new frmInBienBan();
        //region "Nhã zem 14072015"
        //obj.liBienBan = new List<clsBienBan>();
        //clsBienBan bb = new clsBienBan();

        //for (int k = 0; k < dtgDanhSach.Rows.Count; k++)
        //{
        //    if (dtgDanhSach.Rows[k].Cells["stt"].ToString() == "1")
        //    {
        //        bb = new clsBienBan();

        //        bb.MA_GD = dtgDanhSach.Rows[k].Cells["ma_gd"].ToString();
        //        bb.HDKH_ID = Convert.ToInt64(dtgDanhSach.Rows[k].Cells["hdkh_id"].ToString());

        //        long ph_id = Convert.ToInt64(dtgDanhSach.Rows[k].Cells["phieu_id"].ToString());

        //        dsNV = tchopdong.LAY_DS_NHANVIEN_THEO_PHIEU_ID(ph_id, tt_nd.nhanvien_id, 2);

        //        bb.NVTH_ID = Number(dsNV.Tables[0].Rows[0]["nhanvien_th_id"].ToString());// nhanvien_tc_id;
        //        bb.CV_THUCHIEN = dsNV.Tables[0].Rows[0]["nhiemvu"].ToString();//congviec_th;
        //        bb.PHIEU_ID = ph_id;

        //        bb.HDTB_ID = Convert.ToInt64(dtgDanhSach.Rows[k].Cells["hdtb_id"].ToString());
        //        bb.HUONGGIAO_ID = huonggiao_id; //nhapt thêm 17/06/2015
        //        bb.TENTRAM_TB = "";

        //        obj.liBienBan.Add(bb);
        //    }
        //}

        //obj.ma_gd = txtMaGD.Text;

        //obj.hdkh_id = Convert.ToInt64(vhdkh_id);
        //obj.nvth_id = nhanvien_tc_id;
        //obj.cv_thuchien = congviec_th;
        //obj.phieu_id = phieu_id;
        //obj.hdtb_id = hdtb_id;
        //obj.huonggiao_id = huonggiao_id; //nhapt thêm 17/06/2015
        //obj.tentram_tb = "";
        //obj.ShowDialog();
        //endregion
        //------------------------------------------
        //nhapt sửa 10072015
        n_hdkh_id = "";
        n_hdtb_id = "";
        n_phieu_id = "";
        for (int k = 0; k < dsIn.Tables[0].Rows.Count; k++)
        {
            //  
            if (dsIn.Tables[0].Rows[k]["CHON"].Equals(true)) {
                n_hdkh_id += dsIn.Tables[0].Rows[k]["hdkh_id"].ToString() + ",";
                n_hdtb_id += dsIn.Tables[0].Rows[k]["hdtb_id"].ToString() + ",";
                n_phieu_id += dsIn.Tables[0].Rows[k]["phieu_id"].ToString() + ",";

            }
        }
        if (!.IsNullOrEmpty(n_hdkh_id) && !.IsNullOrEmpty(n_phieu_id) && !.IsNullOrEmpty(n_hdtb_id)) //nhapt thêm
        {
            obj.n_hdkh_id = n_hdkh_id.Substring(0, n_hdkh_id.length - 1);//Convert.ToInt64(vhdkh_id);
            obj.nvth_id = nhanvien_tc_id;
            obj.cv_thuchien = congviec_th;
            obj.n_phieu_id = n_phieu_id.Substring(0, n_phieu_id.length - 1);//phieu_id;
            obj.n_hdtb_id = n_hdtb_id.Substring(0, n_hdtb_id.length - 1);//hdtb_id;
            obj.huonggiao_id = huonggiao_id;
            obj.tentram_tb = "";

            //if (obj.liBienBan.Count > 0)
            obj.ShowDialog();
        }
        kt_thaydoi = obj.kt_thaydoi;
        //Tạo daset HDKH có table: HDKH_KYLAI
        //region minhnt sửa: bật form InBBNghiemThu
        //ds_baocao = new DataSet();
        //KhoiTao_DS("HD_TB1", ds_baocao, 0);
        //KhoiTao_DS("HD_TB2", ds_baocao, 0);
        //ds_phieu = tchopdong.In_phieu_nghiemthu(Convert.ToInt64(vhdkh_id), 0);
        //SSRTool report;
        //if (ds_phieu.Tables[0].Rows.Count > 0)
        //{
        //    if (ds_phieu.Tables[0].Rows.Count > 3)
        //    {
        //        for (int i = 0; i < 3; i++)
        //        {
        //            ds_baocao.Tables["HD_TB1"].Rows.Add(
        //               ds_phieu.Tables[0].Rows[i]["ten_dvvt"].ToString(), ds_phieu.Tables[0].Rows[i]["diachi_ld"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["ten_kieuld"].ToString(), ds_phieu.Tables[0].Rows[i]["ma_tb"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["thongso_nt"].ToString(), ds_phieu.Tables[0].Rows[i]["taikhoan"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["culy"].ToString(), ds_phieu.Tables[0].Rows[i]["diachi_ld_cu"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["thongso_nt_cu"].ToString(), "",
        //               ds_phieu.Tables[0].Rows[i]["tenmuc"].ToString()
        //               );
        //        }
        //        for (int i = 3; i < ds_phieu.Tables[0].Rows.Count; i++)
        //        {
        //            ds_baocao.Tables["HD_TB2"].Rows.Add(
        //               ds_phieu.Tables[0].Rows[i]["ten_dvvt"].ToString(), ds_phieu.Tables[0].Rows[i]["diachi_ld"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["ten_kieuld"].ToString(), ds_phieu.Tables[0].Rows[i]["ma_tb"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["thongso_nt"].ToString(), ds_phieu.Tables[0].Rows[i]["taikhoan"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["culy"].ToString(), ds_phieu.Tables[0].Rows[i]["diachi_ld_cu"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["thongso_nt_cu"].ToString(), "",
        //               ds_phieu.Tables[0].Rows[i]["tenmuc"].ToString()
        //               );
        //        }
        //    }
        //    else
        //    {
        //        for (int i = 0; i < ds_phieu.Tables[0].Rows.Count; i++)
        //        {
        //            ds_baocao.Tables["HD_TB1"].Rows.Add(
        //               ds_phieu.Tables[0].Rows[i]["ten_dvvt"].ToString(), ds_phieu.Tables[0].Rows[i]["diachi_ld"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["ten_kieuld"].ToString(), ds_phieu.Tables[0].Rows[i]["ma_tb"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["thongso_nt"].ToString(), ds_phieu.Tables[0].Rows[i]["taikhoan"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["culy"].ToString(), ds_phieu.Tables[0].Rows[i]["diachi_ld_cu"].ToString(),
        //               ds_phieu.Tables[0].Rows[i]["thongso_nt_cu"].ToString(), "",
        //               ds_phieu.Tables[0].Rows[i]["tenmuc"].ToString()
        //               );
        //        }
        //    }


        //    report = new SSRTool();
        //    ds = new DataSet();
        //    ds = tchopdong.LAY_DS_HOPDONG_KH_THEO_HDKH_ID(Convert.ToInt64(vhdkh_id));
        //    if (ds.Tables[0].Rows.Count > 0)
        //    {
        //        if (ds.Tables[0].Rows[0]["ngaylap_hd"].ToString() != "")
        //        {
        //            report.Parameters.Add("ngaylhd", Convert.ToDateTime(ds.Tables[0].Rows[0]["ngaylap_hd"].ToString()).ToString("dd/MM/yyyy"));
        //        }
        //        else
        //        {
        //            report.Parameters.Add("ngaylhd", ds.Tables[0].Rows[0]["ngaylap_hd"].ToString());
        //        }
        //        if (ds.Tables[0].Rows[0]["ghichu"].ToString() != "")
        //            report.Parameters.Add("dienthoai_lh", ds.Tables[0].Rows[0]["ghichu"].ToString());
        //        else
        //            report.Parameters.Add("dienthoai_lh", "");

        //         ten_tram = "";
        //        ten_tram = checkdata.MAP_ID("ten_dv", DatabaseConstants.DB1 + ".donvi", "where donvi_id = " + tt_nd.donvi_id.ToString());
        //        report.Parameters.Add("ten_tram", ten_tram);
        //        report.Parameters.Add("ten_kh", ds.Tables[0].Rows[0]["ten_kh"].ToString());
        //        report.Parameters.Add("ma_kh", ds.Tables[0].Rows[0]["ma_kh"].ToString());
        //        report.Parameters.Add("ma_hd", ds.Tables[0].Rows[0]["ma_hd"].ToString());
        //        //report.Parameters.Add("diadanh", clsBaoCao.diadanh);
        //         khuyenmai = "";
        //        khuyenmai = Lay_KhuyenMaiKH(vhdkh_id);
        //        report.Parameters.Add("khuyenmai", khuyenmai);
        //         ngay = tt_nd.ngay_cn.Day.ToString();
        //        if (ngay.length <= 1)
        //        {
        //            ngay = "0" + ngay;
        //        }
        //        report.Parameters.Add("ngay_ht", ngay);
        //         thang = tt_nd.ngay_cn.Month.ToString();
        //        if (thang.length <= 1)
        //        {
        //            thang = "0" + thang;
        //        }
        //        report.Parameters.Add("thang_ht", thang);
        //        report.Parameters.Add("nam_ht", tt_nd.ngay_cn.Year.ToString());
        //    }
        //    else
        //    {
        //        Message_Box.ShowTB("Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!");
        //        return;
        //    }
        //    dsnhanien = new DataSet();
        //    dsnhanien = tchopdong.LAY_DS_NHANVIEN_THEO_PHIEU_ID(Convert.ToInt64(phieu_id), tt_nd.nhanvien_id, 2);
        //    if (dsnhanien.Tables[0].Rows.Count > 0)
        //    {
        //        report.Parameters.Add("ten_nv", dsnhanien.Tables[0].Rows[0]["ten_nv"].ToString());
        //    }
        //    else
        //    {
        //        Message_Box.ShowTB("Bạn chưa được xuất phiếu khi chưa giao việc nhân viên!");
        //        return;
        //    }

        //    report.DataSource = ds_baocao;
        //     ngayky = tt_nd.ngay_cn.Day.ToString();
        //    if (ngayky.length <= 1)
        //    {
        //        ngayky = "0" + ngayky;
        //    }
        //     thangky = tt_nd.ngay_cn.Month.ToString();
        //    if (thangky.length <= 1)
        //    {
        //        thangky = "0" + thangky;
        //    }
        //    report.Parameters.Add("ten_loaihd", "1");//ds_phieu.Tables[0].Rows[0]["ten_loaihd"]
        //     ngayin = "ngày " + ngayky + " tháng " + thangky + " năm " + tt_nd.ngay_cn.Year.ToString();
        //     diadanh = clsBaoCao.diadanh;
        //    report.Parameters.Add("diadanh", diadanh);
        //    report.Parameters.Add("ngayin", ngayin);

        //     path = Application.StartupPath;
        //    if (ds_phieu.Tables[0].Rows.Count <= 3)
        //        report.Parameters.Add("remove_page", "0"); //Nếu <= 3 row thì remove  page 2
        //    else
        //        report.Parameters.Add("remove_page", "2");//không remove 
        //    report.ReportFileName = System.IO.Path.Combine(clsBaoCao.reportpath + "\\ReportFile\\InHopDong", "InPhieu_NghiemThu.rst");

        //    report.ProductName = "";//clsBaoCao.reportFooter;
        //    report.ShowReport(frmFormMain.ActiveForm, true);
        //} 
        //endregion
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("" + ex.ToString());
    }
}
//endregion

//region Lay_KhuyenMaiKH
        private Lay_KhuyenMaiKH(vhdkh_id)
{
    kq = "";
    try {
                ds_ctkm = new DataSet();
        str = "";
        str += " select c.ten_km";
        str += " from " + DatabaseConstants.DB2 + ".hd_thuebao a," + DatabaseConstants.DB2 + ".khuyenmai_hdtb b,";
        str += DatabaseConstants.DB2 + ".khuyenmai c ";
        str += " where a.hdtb_id = b.hdtb_id and b.khuyenmai_id = c.khuyenmai_id ";
        str += "   and a.hdkh_id = " + vhdkh_id;
        ds_ctkm = bangts.GetSQL(str, "dvgt");
        for (int i = 0; i < ds_ctkm.Tables[0].Rows.Count; i++)
        {
            kq += ds_ctkm.Tables[0].Rows[i]["ten_km"] + "; ";
        }
        if (kq.length > 0) {
            kq = kq.Substring(0, kq.length - 2);
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi: " + ex.ToString());
    }
    return kq;
}
//endregion

//region Lay_DichvuGT
        private Lay_DichvuGT(long vhdtb_id)
{
    kq = "";
    try {
                ds_ctkm = new DataSet();
        str = "";
        str += " select a.ma_dvgt";
        str += " from " + DatabaseConstants.DB2 + ".dichvu_gt a," + DatabaseConstants.DB2 + ".dangky_dvgt b";
        str += " where b.hdtb_id = " + vhdtb_id;
        str += "    and a.dichvugt_id = b.dichvugt_id";
        ds_ctkm = bangts.GetSQL(str, "dvgt");
        for (int i = 0; i < ds_ctkm.Tables[0].Rows.Count; i++)
        {
            kq += ds_ctkm.Tables[0].Rows[i]["ma_dvgt"] + ", ";
        }
        if (kq.length > 0) {
            kq = kq.Substring(0, kq.length - 2);
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi: " + ex.ToString());
    }
    return kq;
}
//endregion

//region KhoiTao_DS
KhoiTao_DS(table_name, ds_, int kieu)
{
    try {
        if (kieu == 0) // Khởi tạo dataset thuê bao
        {
            ds_.Tables.Add(table_name);
            ds_.Tables[table_name].Columns.Add("ten_dvvt");
            ds_.Tables[table_name].Columns.Add("diachi_ld");
            ds_.Tables[table_name].Columns.Add("ten_kieuld");
            ds_.Tables[table_name].Columns.Add("ma_tb");
            ds_.Tables[table_name].Columns.Add("thongso_nt");
            ds_.Tables[table_name].Columns.Add("taikhoan");
            ds_.Tables[table_name].Columns.Add("culy");
            ds_.Tables[table_name].Columns.Add("diachi_ld_cu");
            ds_.Tables[table_name].Columns.Add("thongso_nt_cu");
            ds_.Tables[table_name].Columns.Add("ma_dvgt");
            ds_.Tables[table_name].Columns.Add("tenmuc");
        }
        else // dataset vật tư
        {
            ds_.Tables.Add(table_name);
            ds_.Tables[table_name].Columns.Add("stt");
            ds_.Tables[table_name].Columns.Add("ten_dvvt");
            ds_.Tables[table_name].Columns.Add("ma_tb");
            ds_.Tables[table_name].Columns.Add("loai_tbi");
            ds_.Tables[table_name].Columns.Add("kieu_tbi");
            ds_.Tables[table_name].Columns.Add("soluong");
        }

    }
    catch (err) {
        Message_Box.ShowError("Có lỗi: " + exp.ToString());
    }
}
//endregion

tsbtnChuyenDB_Click()
{
    try {
        if (gvDanhSach.FocusedRowHandle >= 0) {
            //bangts.CapNhat_NVTH_ID(hdtb_id, tt_nd.nhanvien_id);

            if (PHAILAM(luong_id, "KT_HOANCONG_HD_BANCHEO")) {
                try {
                    kq_bc = new BusinessFacade.BanCheoFacade.HopDong().KIEMTRA_HOANTHIEN_THOAITRA_BAN(hdtb_id, 1);
                    if (kq_bc != "OK") {
                        Message_Box.ShowWarning(kq_bc);
                        return;
                    }
                }
                catch (Exception) { }
            }

            if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.DAT_MOI) {
                //new HTHSDatMoiFacade().HOANTHIEN_HS_DATMOI(hdtb_id, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
                //bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);

                new LapHopDongFacade2().CAPNHAT_NGAYKH_NGAY_HT(hdtb_id, tt_nd.ngay_cn, dtpNgayBG.Value);
                new HTHSDatMoiFacade().HOAN_CONG_DATMOI(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, true, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
                bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
            }
            if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.KHOIPHUC_SD) {
                new LapHopDongFacade2().CAPNHAT_NGAYKH_NGAY_HT(hdtb_id, tt_nd.ngay_cn, dtpNgayBG.Value);
                bangts.CapNhat_NVTH_ID(hdtb_id, tt_nd.nhanvien_id);
                hths_kptl.HOANTHIEN_HS_KPTL(hdtb_id, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
                bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }

                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(0, hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
            }
            if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.DI_CHUYEN) {
                new LapHopDongFacade2().CAPNHAT_NGAYKH_NGAY_HT(hdtb_id, tt_nd.ngay_cn, dtpNgayBG.Value);
                hths_dc.HOANTHIEN_HS_DICHCHUYEN(hdtb_id, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
                bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }

                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(0, hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
            }
            if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.CHUYENDOI_LH) {
                new LapHopDongFacade2().CAPNHAT_NGAYKH_NGAY_HT(hdtb_id, tt_nd.ngay_cn, dtpNgayBG.Value);
                new HoanThienHoSoFacade().HOANTHIEN_HS_CHUYENDOI_LHTB(hdtb_id, dtpNgayBG.Value);
                bangts.CapNhat_TT_Phieu_HTHS(hdtb_id);
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_TAO_DS_CAP_VT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_CAPVT(hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }

                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_QUYETTOAN_VT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_QTVT(0, hdtb_id, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_CAPVT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.MOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
                if (PHAILAM(luong_id, "TUDONG_GIAOPHIEU_DUYET_THUHOIVT")) {
                    try {
                        hoancong.GIAOPHIEU_TD_TAOPHIEU_DUYET_VT(hdtb_id, 0, phieu_id, tt_nd.donvi_id, tt_nd.nhanvien_id, KieuTbi.THUHOI, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip);
                    }
                    catch (Exception) { }
                }
            }
            if (PHAILAM(luong_id, "KT_HOANCONG_HD_BANCHEO")) {
                try {
                    new BusinessFacade.BanCheoFacade.HopDong().hoangcong_hopdong_bancheo(hdtb_id);
                }
                catch (Exception ex)
                {
                    tdan.EXECUTE_PROC("{?DB1}.QUANTRI_HETHONG.Log_Action",
                        "vuser_name", tt_nd.ma_nd,
                        "vcomputer_name", tt_nd.may_cn,
                        "vaction_name", "frmHoanCongCoDinh_HT",
                        "vkey_parameter", "hdtb_id=" + hdtb_id.ToString() + ",ngay_cn=" + tt_nd.ngay_cn + ", Err : " + ex.ToString(),
                        "vserver_name", tt_nd.ip,
                        "vnote", " Hoàn công cố định");
                }
            }
            Message_Box.ShowTB("Cật nhật dữ liệu vào danh bạ thành công!");
            HienThiDanhSachHDTB();
        }
        else {
            Message_Box.ShowTB("Không có hợp đồng trong danh sách để hoàn thiện !");
            return;
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi : " + ex);
    }
}

tsbtnKichHoat_Click()
{
    KichHoat();
}

KichHoat()
{
    try {
                bool result = true;
        message = "";
                bool serviceLoss = true;//true: mất dịch vụ; false: không mất dịch vụ
        //Chạy ws lào cai để kích hoạt tổng đài               
        if (matinh == "hpg" && !DONGBO_TEST.TEST) {
                    SUBMANService subman = new SUBMANService();
            //region Đặt mới
            if (loaihd_id == LoaiHopDong.DAT_MOI) {
                str = "select * from " + DatabaseConstants.DB2 + ".khoso_cd a, " + DatabaseConstants.DB2 + ".prefix b ";
                str += " where a.somay = '" + txtMaTB.Text.Trim() + "'";
                str += " and a.prefix = b.prefix";
                var dt = bangts.GetDataSql(str);
                if (dt.Rows.Count > 0) {
                    result = subman.TaoMayMoi(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), txtNE.Text, ref message);
                }
                //Nếu thành công thì gọi tiếp hàm đăng ký dịch vụ gia tăng
                if (result) {
                    for (int i = 0; i < gridViewDVGT.DataRowCount; i++)
                    {

                        if (gridViewDVGT.GetRowCellValue(i, "KIEU_YC").ToString().Trim() == "1") {
                            result = subman.DangKyDichVu(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), txtNE.Text, Number(gridViewDVGT.GetRowCellValue(i, "DICHVUGT_ID").ToString().Trim()), null, ref message); //Tham số dùng trong 1 số dịch vụ (chuyển thoại...) làm sau
                        }
                        else {
                            result = subman.HuyDichVu(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), txtNE.Text, Number(gridViewDVGT.GetRowCellValue(i, "DICHVUGT_ID").ToString().Trim()), null, ref message); //Tham số dùng trong 1 số dịch vụ (chuyển thoại...) làm sau
                        }
                    }
                }
            }
            //endregion
            //region Di chuyển
                    else if (loaihd_id == LoaiHopDong.DI_CHUYEN) {
                        //Kiểm tra nếu có sự thay đổi Ne thì kích hoạt đổi Ne
                        dskt = new DataSet();
                dskt = bangts.LAY_NE_CU_NE_MOI(hdtb_id);
                if (dskt.Tables[0].Rows.Count > 0) {
                            long ne_id_moi = Convert.ToInt64(dskt.Tables[0].Rows[0]["ne_id_moi"].ToString());
                            long ne_id_cu = Convert.ToInt64(dskt.Tables[0].Rows[0]["ne_id_cu"].ToString());
                    ne_cu = dskt.Tables[0].Rows[0]["ne_cu"].ToString();
                    ne_moi = dskt.Tables[0].Rows[0]["ne_moi"].ToString();
                    if (ne_id_moi != ne_id_cu) {
                        //Lấy tổng đài mới
                        str = "select * from " + DatabaseConstants.DB2 + ".khoso_cd a, " + DatabaseConstants.DB2 + ".prefix b ";
                        str += " where a.somay = '" + txtMaTB.Text.Trim() + "'";
                        str += " and a.prefix = b.prefix";
                        var dt = bangts.GetDataSql(str);

                        //Lấy tổng đài cũ
                        str = "select * from " + DatabaseConstants.DB2 + ".khoso_cd a, " + DatabaseConstants.DB2 + ".prefix b ";
                        str += " where a.somay = '" + txtMaTB_Cu.Text.Trim() + "'";
                        str += " and a.prefix = b.prefix";
                        var dtCu = bangts.GetDataSql(str);
                        result = subman.ThayDoiCauHinh(Number(dtCu.Rows[0]["donvi_id"]), txtMaTB_Cu.Text, Number(dt.Rows[0]["donvi_id"]),
                            txtMaTB.Text, ne_moi, ref serviceLoss, ref message);

                        //Hàm cũ
                        //result = subman.DoiViTri(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), ne_cu, ne_moi, ref serviceLoss, ref message);
                        //Nếu thành công, gọi các hàm đăng ký dịch vụ đang sử dụng của khách hàng (do đổi Ne mất hết các dịch vụ gia tăng đang đăng ký
                        if (result && serviceLoss) {
                            //Lấy danh sách dịch vụ gia tăng đang sử dụng
                            var dtDichVu = tcdanhba.LAY_DS_SUDUNG_DVGT(thuebao_id).Tables[0];
                            foreach(DataRow row in dtDichVu.Rows)
                            {
                                //Tham số dùng trong 1 số dịch vụ (chuyển thoại...) làm sau
                                result = subman.DangKyDichVu(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), ne_moi, Number(row["dichvugt_id"]), null, ref message);
                            }
                        }
                    }
                }
            }
            //endregion
            //region Chuyển đổi loại hình
                    else if (loaihd_id == LoaiHopDong.CHUYENDOI_LH) {

            }
            //endregion
            //region Khôi phục sử dụng
                    else if (loaihd_id == LoaiHopDong.KHOIPHUC_SD) {
                //Gọi như khi lắp đặt mới
                str = "select * from " + DatabaseConstants.DB2 + ".khoso_cd a, " + DatabaseConstants.DB2 + ".prefix b ";
                str += " where a.somay = '" + txtMaTB.Text.Trim() + "'";
                str += " and a.prefix = b.prefix";
                var dt = bangts.GetDataSql(str);
                if (dt.Rows.Count > 0) {
                    result = subman.TaoMayMoi(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), txtNE.Text, ref message);
                }
                //Nếu thành công thì gọi tiếp hàm đăng ký dịch vụ gia tăng
                if (result) {
                    for (int i = 0; i < gridViewDVGT.DataRowCount; i++)
                    {
                        if (gridViewDVGT.GetRowCellValue(i, "KIEU_YC").ToString().Trim() == "1") {
                            result = subman.DangKyDichVu(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), txtNE.Text, Number(gridViewDVGT.GetRowCellValue(i, "DICHVUGT_ID").ToString().Trim()), null, ref message); //Tham số dùng trong 1 số dịch vụ (chuyển thoại...) làm sau
                        }
                        else {
                            result = subman.HuyDichVu(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), txtNE.Text, Number(gridViewDVGT.GetRowCellValue(i, "DICHVUGT_ID").ToString().Trim()), null, ref message); //Tham số dùng trong 1 số dịch vụ (chuyển thoại...) làm sau
                        }
                    }
                }
            }
            //endregion
            if (!result) {
                Message_Box.ShowTB("Kích hoạt không thành công. !\nLỗi tổng đài trả về: " + message);
                return;
            }
        }
                //Update trạng thái hd_thuebao
                // Lam viết cho cả Khôi phục thanh lý cho Cố định (cùng gọi hàm CreatePhone)
                long vhdtb_id = 0;
        if ((loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
            || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
            || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
            || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) && sochinh == true) {
            for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
            {
                if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                    vhdtb_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["hdtb_id"].ToString());
                    //lapHD.CAPNHAT_STATUS_HDTB(vhdtb_id, TRANGTHAI_DONGBO.DONGBO_SERVICE);
                    if (PHAILAM(luong_id, "CAPNHAT_NGAYHT_KH")) {
                        thangsau = tt_nd.ngay_cn.AddMonths(1).ToString("yyyyMM");
                        dbt = bangts.KT_DBThang_DaChot(thangsau);
                        if (dbt != "0") {
                            Message_Box.ShowTB(dbt);
                            return;
                        }
                        lapHD.CAPNHAT_NGAYHT(vhdtb_id, dtpNgayBG.Value);
                    }
                    //if (PHAILAM(luong_id, "CAPNHAT_NGAYKH_KH"))
                    //    lapHD.CAPNHAT_NGAYKH(vhdtb_id, tt_nd.ngay_cn);
                    new LapHopDongFacade2().CAPNHAT_NGAYKH_STATUS_V2(vhdtb_id, tt_nd.ngay_cn, TRANGTHAI_DONGBO.DONGBO_SERVICE, tt_nd.ma_nd, tt_nd.ip);
                }
            }
        }

        if (loaihd_id == LoaiHopDong.CHUYENDOI_LH && dtList.AsEnumerable().Where(r => r["kieuld_id"].ToString() == KieuLapDat.CHUYENDOIIMS_CD.ToString() && r["hdtb_id"].ToString() == hdtb_id.ToString()).Count() > 0) {
            if (DONGBO_TEST.TEST == false) {
                        //Lamnt: Chuyển sang gọi hủy trên hệ thống mới luôn
                        IMSService ims = new IMSService();
                var kq = ims.HuyThueBao(txtMaTB.Text.Trim(), ref message);
                if (!kq) {
                    Message_Box.ShowError("Có lỗi trong quá trình hủy thuê bao trên IMS mới !\n" + message);
                    return;
                }
                //}
                //else
                //{
                //     result1 = new VTNService(tt_nd.user_vtn, tt_nd.pass_vtn).ThanhLyThueBao(txtMaTB.Text);
                //    if (result1 != "ok")
                //    {
                //        Message_Box.ShowError("Lỗi khi thanh lý IMS: " + result1);
                //        return;
                //    }
                //}
            }
        }

        //lapHD.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_SERVICE);
        if (PHAILAM(luong_id, "CAPNHAT_NGAYHT_KH")) {
            thangsau = tt_nd.ngay_cn.AddMonths(1).ToString("yyyyMM");
            dbt = bangts.KT_DBThang_DaChot(thangsau);
            if (dbt != "0") {
                Message_Box.ShowTB(dbt);
                return;
            }
            lapHD.CAPNHAT_NGAYHT(hdtb_id, dtpNgayBG.Value);
        }
        //if (PHAILAM(luong_id, "CAPNHAT_NGAYKH_KH"))
        //{
        //    lapHD.CAPNHAT_NGAYKH(hdtb_id, tt_nd.ngay_cn);
        //}
        new LapHopDongFacade2().CAPNHAT_NGAYKH_STATUS_V2(hdtb_id, tt_nd.ngay_cn, TRANGTHAI_DONGBO.DONGBO_SERVICE, tt_nd.ma_nd, tt_nd.ip);
                long in_hdtb_id = hdtb_id;
        HienThiDanhSachHDTB();
        gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        for (int i = 0; i < gvDanhSach.RowCount; i++)
        if (in_hdtb_id.Equals(gvDanhSach.GetDataRow(i)["hdtb_id"])) {
            gvDanhSach.FocusedRowHandle = i;
            //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(-1, i));
            break;
        }

        Message_Box.ShowTB("Kích hoạt tổng đài thành công !");
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi : " + ex);
    }
}

        private bool PHAILAM(int luong_id, CODE)
{
    if (dtThaoTac == null) return false;
    //return dtThaoTac.Select(x => x.enable == "1" && x.code == CODE)Count() > 0;
    foreach(var tt in dtThaoTac)
    {
        if (tt.enable == "1" && tt.code == CODE) return true;
    }
    return false;
}

//region Giao việc
TuDongGiaoViec()
{
    nhiemvu = "";
    if (tthd_id == TrangThaiHD.DIEUHANH_THI_CONG)
        nhiemvu = "Điều hành thi công";
    else if (tthd_id == TrangThaiHD.KHAIBAO_TONGDAI)
        nhiemvu = "Khai báo thông số";
    else if (tthd_id == TrangThaiHD.DANG_THI_CONG)
        nhiemvu = "Thi công";
    else if (tthd_id == TrangThaiHD.DOISOAT_HOSO)
        nhiemvu = "Đối soát hồ sơ";
    else if (tthd_id == TrangThaiHD.DANGCHO_HOANCONG)
        nhiemvu = "Đang chờ hoàn công";
    else nhiemvu = "";
            //Cập nhật thông tin giao việc nhân viên tự động
            GiaoPhieu_NVFacade objgiaophieu = new GiaoPhieu_NVFacade();
            // hoangpkn : 23/03/2015
            // thêm phục vụ cho ISDN
            //objgiaophieu.Delete(phieu_id);
            //TaoDuLieu_GIAOPHIEU_NV(phieu_id, nhiemvu);
            //objgiaophieu.Insert(dsnhanviengp);
            long vphieu_id = 0;
    if ((loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ
        || loaitb_id == LoaiHinhTB.ISDN30B_CD || loaitb_id == LoaiHinhTB.ISDN30B_CQ
        || loaitb_id == LoaiHinhTB.TRUNGKE_2M || loaitb_id == LoaiHinhTB.TRUNGKE_THUONG
        || loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) && sochinh == true) {
        for (int i = 0; i < dsIn.Tables[0].Rows.Count; i++)
        {
            if (dsIn.Tables[0].Rows[i]["hdtb_cha_id"].ToString() == hdtb_id.ToString()) {
                vphieu_id = Convert.ToInt64(dsIn.Tables[0].Rows[i]["phieu_id"].ToString());

                objgiaophieu.Delete(vphieu_id);
                TaoDuLieu_GIAOPHIEU_NV(vphieu_id, nhiemvu);
                objgiaophieu.Insert(dsnhanviengp);
            }
        }
    }

    if (PHAILAM(luong_id, "GIAOVIEC_NVQL_CAP")) {
                dsNV = tchopdong.LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieu_id, tt_nd.nhanvien_id, 2);
        if (dsNV != null && dsNV.Tables[0].Rows.Count <= 0) {
            strGV = "select nhanvien_id from " + DatabaseConstants.DB5
                + ".vi_nhanvien_theocap_hdtb where hdtb_id =" + hdtb_id;
                    dsGV = bangts.GetSQL(strGV, "");

            if (dsGV != null && dsGV.Tables[0].Rows.Count > 0) {
                if (dsGV.Tables[0].Rows[0]["nhanvien_id"].ToString() != "") {
                            int nvql_cap_id = Number(dsGV.Tables[0].Rows[0]["nhanvien_id"].ToString());

                    objgiaophieu.Delete(phieu_id);
                    TaoDL_GiaoPhieu_NVQL_CAP(phieu_id, nhiemvu, nvql_cap_id);
                    objgiaophieu.Insert(dsnhanviengp);
                }
            }
            else {
                objgiaophieu.Delete(phieu_id);
                TaoDuLieu_GIAOPHIEU_NV(phieu_id, nhiemvu);
                objgiaophieu.Insert(dsnhanviengp);
            }
        }
    }
    else {
        objgiaophieu.Delete(phieu_id);
        TaoDuLieu_GIAOPHIEU_NV(phieu_id, nhiemvu);
        objgiaophieu.Insert(dsnhanviengp);
    }

    //return true;
    // end hoàng thêm
}

TaoDuLieu_GIAOPHIEU_NV(long vphieu_id, nhiemvu)
{
    dsnhanviengp = new GIAOPHIEU_NV_DATA();
    GIAOPHIEU_NV_DATA.GIAOPHIEU_NVRow row = dsnhanviengp.GIAOPHIEU_NV.NewGIAOPHIEU_NVRow();
    row.PHIEU_ID = vphieu_id;
    row.NHANVIEN_TH_ID = tt_nd.nhanvien_id;
    row.NHIEMVU = nhiemvu;
    row.GHICHU = "";
    row.NHANVIEN_GIAO_ID = Number(cboNguoiGV.SelectedValue);
    row.NGAYGIAO = dtpNgayGV.Value;
    dsnhanviengp.GIAOPHIEU_NV.AddGIAOPHIEU_NVRow(row);
}

/// <summary>
/// Tạo dữ liệu giao phiếu nv quản lý cáp
/// </summary>
TaoDL_GiaoPhieu_NVQL_CAP(long vphieu_id, nhiemvu, int nvql_cap_id)
{
    dsnhanviengp = new GIAOPHIEU_NV_DATA();
    GIAOPHIEU_NV_DATA.GIAOPHIEU_NVRow row = dsnhanviengp.GIAOPHIEU_NV.NewGIAOPHIEU_NVRow();
    row.PHIEU_ID = vphieu_id;
    row.NHANVIEN_TH_ID = (int)nvql_cap_id;
    row.SO_DT = "";
    row.NHIEMVU = nhiemvu;
    row.GHICHU = "";
    row.NHANVIEN_GIAO_ID = Number(cboNguoiGV.SelectedValue);
    row.NGAYGIAO = dtpNgayGV.Value;
    dsnhanviengp.GIAOPHIEU_NV.AddGIAOPHIEU_NVRow(row);
}
//endregion

cboQuyTrinh_SelectedValueChanged()
{
    if (cboQuyTrinh.Items.Count > 0) {
        if (cboQuyTrinh.SelectedValue == null) return;
        if (cboQuyTrinh.SelectedValue.ToString() == "System.Data.DataRowView") return;
        quytrinh_id = Number(cboQuyTrinh.SelectedValue);

        // table, result, where, groupby;
        //table = "" + DatabaseConstants.DB2 + ".huonggiao a," + DatabaseConstants.DB2 + ".huonggiao_ldv b";
        //result = " b.luong_id, a.huonggiao_id, a.huonggiao ";
        //where = "  a.quytrinh_id = b.quytrinh_id and a.huonggiao_id = b.huonggiao_id ";
        //where += "  and a.quytrinh_id = " + quytrinh_id + " and  a.tthd_id = " + tthd_id;
        //groupby = "";
        //ds = bangts.Laythongtin_table(table, where, result, groupby);
        var dt_luong = (from DataRow DSTONG
                                      in dsDanhMuc.Tables["QUYTRINH"].Rows
                                where DSTONG["quytrinh_id"].ToString() == quytrinh_id.ToString() && DSTONG["tthd_id"].ToString() == tthd_id.ToString()
                                orderby int.Parse(DSTONG["quytrinh_id"].ToString())
                                select DSTONG).CopyToDataTable();
        if (dt_luong.Rows.Count > 0) {
            luong_id = Number(dt_luong.Rows[0]["luong_id"].ToString());
            huonggiao_id = Number(dt_luong.Rows[0]["huonggiao_id"].ToString());
            tenhg = dt_luong.Rows[0]["huonggiao"].ToString();
            labelFunctionTitle.Text = tenhg.ToUpper();
            Text = tenhg;
        }
        else {
            Message_Box.ShowTB("Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại");
            HienThiDanhSachHDTB();
            return;
        }
        HienThiGiaoDien(luong_id);

        if (!PHAILAM(luong_id, "GIAOVIEC")) {
            chkNgayBG.Checked = true;
            chkNgayGV.Checked = true;
            dtpNgayBG.Value = tt_nd.ngay_cn.AddSeconds(30);
            dtpNgayGV.Value = tt_nd.ngay_cn;
        }

        if (kt_load)
            HienThiDanhSachHDTB();


    }
}

rdoPhieuMoi_CheckedChanged()
{
    if (rdoPhieuMoi.Checked)
        HienThiDanhSachHDTB();
}

rdoPhieuTra_CheckedChanged()
{
    if (rdoPhieuTra.Checked)
        HienThiDanhSachHDTB();
}

btnNDTH_Click()
{
    try {
        if (phieu_id == 0) return;
        bangts.Update_ND_THUCHIEN(phieu_id, txtNoiDungTH.Text.Trim());
        Message_Box.ShowTB("Cập nhật thành công!");

        vma_tb = txtMaTB.Text.Trim();
        HienThiDanhSachHDTB();

        // Tìm kiếm row vừa cập nhật
        gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        for (int i = 0; i < gvDanhSach.RowCount; i++)
        if (vma_tb == gvDanhSach.GetDataRow(i)["ma_tb"].ToString()) {
            gvDanhSach.FocusedRowHandle = i;
            //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(-1, i));
            break;
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi : " + ex);
    }
}

txtDoiCapNgon_Leave()
{
    if (txtDoiCapNgon.Text.Trim() != "") {
        s = txtDoiCapNgon.Text;
        if (!CommonFunction.CheckIsNumber(CommonFunction.Unformat_tien(s))) {
            Message_Box.ShowWarning("Đôi cáp ngọn phải là kiểu số");
            txtDoiCapNgon.Focus();
            return;
        }
    }
}

txtDoiCapGoc_Leave()
{
    if (txtDoiCapGoc.Text.Trim() != "") {
        s = txtDoiCapGoc.Text;
        if (!CommonFunction.CheckIsNumber(CommonFunction.Unformat_tien(s))) {
            Message_Box.ShowWarning("Đôi cáp gốc phải là kiểu số");
            txtDoiCapGoc.Focus();
            return;
        }
    }
}

txtDoiCapGoc_KeyPress(KeyPressEventArgs e)
{
    if ((e.KeyChar >= '0' && e.KeyChar <= '9') || e.KeyChar == (char)8)
    e.Handled = false;
            else
    e.Handled = true;
}

txtDoiCapNgon_KeyPress(KeyPressEventArgs e)
{
    if ((e.KeyChar >= '0' && e.KeyChar <= '9') || e.KeyChar == (char)8)
    e.Handled = false;
            else
    e.Handled = true;
}

txtSoMetDay_KeyPress(KeyPressEventArgs e)
{
    if ((e.KeyChar >= '0' && e.KeyChar <= '9') || e.KeyChar == (char)8)
    e.Handled = false;
            else
    e.Handled = true;
}

btnChonNe_TuDong_Click()
{
    str = "";
            DataTable dtTemp = new DataTable();
    // str = "select donvi_id from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + hdtb_id + " and loaidv_id = " + LOAI_DV.TRAM_VT + " and kieudv_id = 2";
    //DataTable dtTemp = bangts.GetDataSql(str);
    //if (dtTemp.Rows.Count > 0)
    //    tramvt_id = Number(dtTemp.Rows[0]["donvi_id"].ToString());

    if (tramvt_id == 0 && host_id == 0) {
        Message_Box.ShowWarning("Không có thông tin Tổ thi công và Host để chọn Ne !");
        return;
    }
    if (tramvt_id != 0) {
        str = "select * From(select b.ne_id,b.sone, a.donvi_id vetinh_id";
        str += " from " + DatabaseConstants.DB1 + ".donvi_ql a, " + DatabaseConstants.DB2 + ".sone b";
        str += " where a.donvi_id = b.donvi_id";
        str += "     and a.donvi_ql_id = " + tramvt_id;
        str += "     and a.loaidv_ql_id = " + LOAI_DV.TRAM_VT;
        str += "     and a.loaidv_id = " + LOAI_DV.TRAM_VETINH;
        str += "     and b.trangthaiso_id = " + TrangthaiSo.TTSO_DANGROI;
        str += " order by ne_id) where rownum=1";
    }
    else {
        str = "select * From(select b.ne_id,b.sone, a.donvi_id vetinh_id";
        str += " from " + DatabaseConstants.DB1 + ".donvi_ql a, " + DatabaseConstants.DB2 + ".sone b";
        str += " where a.donvi_id = b.donvi_id";
        str += "     and a.donvi_ql_id = " + host_id;
        str += "     and a.loaidv_ql_id = " + LOAI_DV.HOST;
        str += "     and a.loaidv_id = " + LOAI_DV.TRAM_VETINH;
        str += "     and b.trangthaiso_id = " + TrangthaiSo.TTSO_DANGROI;
        str += " order by ne_id) where rownum=1";
    }
    dtTemp = bangts.GetDataSql(str);
    if (dtTemp.Rows.Count > 0) {
        txtNE.Text = dtTemp.Rows[0]["sone"].ToString();
        ne_id = Convert.ToInt64(dtTemp.Rows[0]["ne_id"].ToString());
        tramtb_id = Number(dtTemp.Rows[0]["vetinh_id"].ToString());

        s = checkdata.MAP_ID("ten_dv", DatabaseConstants.DB2 + ".sone a, " + DatabaseConstants.DB1 + ".donvi b", "where a.donvi_id = b.donvi_id and a.ne_id = " + ne_id + "");
        if (s != "-1")
            txtTramNE.Text = s;

        if (PHAILAM(luong_id, "CAPNHAT_NE")) {
            lapHD.CAPNHAT_SONE(hdtb_id, txtMaTB.Text.Trim(), ne_id, txtNE.Text.Trim(), 0, tramtb_id);
        }

        gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        if (inDex_load >= 0) {
            if (dtList.Rows.Count == 1)
                inDex_load = 0;
            gvDanhSach.FocusedRowHandle = inDex_load;
            //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(-1, inDex_load));
        }
        Message_Box.ShowTB("Cập nhật Ne thành công!");
    }
    else {
        Message_Box.ShowWarning("Không tìm thấy thông tin Ne!");
        return;
    }
}

tsbtnHen_Click()
{
    // Ban CLG yêu cầu không cho hẹn từ bên khối kỹ thuật.
    // Ban CLG đã có VB bỏ yêu cầu này
    //return;
    if (hdkh_id != 0) {
                frmHenKH f = new frmHenKH();
        f.vhdkh_id = hdkh_id;
        f.ShowDialog();

                long in_hdtb_id = hdtb_id;
        HienThiDanhSachHDTB();
        gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        for (int i = 0; i < gvDanhSach.RowCount; i++)
        if (hdtb_id == Convert.ToInt64(gvDanhSach.GetDataRow(i)["hdtb_id"].ToString())) {
            gvDanhSach.FocusedRowHandle = i;
            //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(-1, i));
            break;
        }
    }
}

tsbtnThongTinKT_Click()
{
    try {
        if (thuebao_id != 0) {
            try {
                        frmThongTinKT_2 frm = new frmThongTinKT_2();
                frm.thuebao_id = thuebao_id;
                frm.dichvuvt_id = DichVuVienThong.CO_DINH;
                frm.vma_tb = txtMaTB.Text.Trim();
                frm.vkieu = 1;
                frm.ShowDialog();
            }
            catch (Exception ex)
            {
                Message_Box.ShowError("Có lỗi : " + ex);
            }
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Có lỗi : " + ex);
    }
}

tsbtnHuyKH_Click()
{
    HuyKichHoat();
}

HuyKichHoat()
{
    try {
        if (dsIn.Tables[0].Rows.Count <= 0) return;
        if (Message_Box.ShowQuestion("Bạn có chắc chắn thực hiện hủy kích hoạt Số máy " + txtMaTB.Text.Trim() + " không?") == DialogResult.Yes) {
                    bool result = true;
            message = "";
            //region Hải phòng
            //Chạy ws lào cai để kích hoạt tổng đài               
            if (matinh == "hpg" && !DONGBO_TEST.TEST) {
                        SUBMANService subman = new SUBMANService();
                if (loaihd_id == LoaiHopDong.DAT_MOI) {
                    str = "select * from " + DatabaseConstants.DB2 + ".khoso_cd a, " + DatabaseConstants.DB2 + ".prefix b ";
                    str += " where a.somay = '" + txtMaTB.Text.Trim() + "'";
                    str += " and a.prefix = b.prefix";

                    var dt = bangts.GetDataSql(str);
                    if (dt.Rows.Count > 0) {
                        result = subman.XoaMay(Number(dt.Rows[0]["donvi_id"]), txtMaTB.Text.Trim(), txtNE.Text, ref message);
                    }
                }
                else if (loaihd_id == LoaiHopDong.DI_CHUYEN) {

                }
                else if (loaihd_id == LoaiHopDong.CHUYENDOI_LH) {

                }
                else if (loaihd_id == LoaiHopDong.KHOIPHUC_SD) {

                }
                if (!result) {
                    Message_Box.ShowTB("Hủy kích hoạt không thành công. !\nLỗi tổng đài trả về: " + message);
                    return;
                }
            }
            //endregion

                    int nIndex = gvDanhSach.FocusedRowHandle;

            //region LoaiHopDong.CHUYENDOI_LH
            if (Number(cboLoaiHD.SelectedValue) == LoaiHopDong.CHUYENDOI_LH) {
                str = "select a.dichvuvt_id,a.loaitb_id, b.password, c.ma_lhtb from " + DatabaseConstants.DB2 + ".db_thuebao a, " + DatabaseConstants.DB2 + ".db_ims b, " + DatabaseConstants.DB2 + ".loaihinh_tb c"
                    + " where a.thuebao_id= " + thuebao_id + " and a.thuebao_id = b.thuebao_id and a.loaitb_id = c.loaitb_id";

                        dset = bangts.getDataFromSQL(str, "danhsach");
                if (dset.Tables[0].Rows.Count > 0) {
                    if (Number(dset.Tables[0].Rows[0]["dichvuvt_id"].ToString()) == DichVuVienThong.IMS) {
                        if (Number(dset.Tables[0].Rows[0]["loaitb_id"].ToString()) == LoaiHinhTB.IMS_POTS || Number(dset.Tables[0].Rows[0]["loaitb_id"].ToString()) == LoaiHinhTB.IMS_SIP) {
                                    IMSService ims = new IMSService();
                            var kq = ims.KhoiTaoThueBao(txtMaTB.Text.Trim(), txtMaTB.Text.Trim(), dset.Tables[0].Rows[0]["password"].ToString(), dset.Tables[0].Rows[0]["ma_lhtb"].ToString(), "FixOfflineCharging", ref message);
                            if (kq) {
                                try {
                                    area = "";
                                    area = new LapHopDongFacade2().lay_thong_tin_diachi(thuebao_id, hdtb_id, LoaiHopDong.CHUYENDOI_LH);
                                    ims.CHANGE_LOCATION(txtMaTB.Text, area, ref message);
                                }
                                catch (Exception ex)
                                {
                                    Message_Box.ShowTB("Đổi location cho thuê bao " + txtMaTB.Text.Trim() + " không thành công !");
                                }

                                kq = ims.ThayDoiDVGT_Danhba(thuebao_id, dset.Tables[0].Rows[0]["ma_lhtb"].ToString(), txtMaTB.Text, ref message);
                                new LapHopDongFacade2().CAPNHAT_HUYKH_STATUS(hdtb_id, txtMaTB.Text.Trim(), tt_nd.ngay_cn, TRANGTHAI_DONGBO.CHUADONGBO, tt_nd.ma_nd, tt_nd.ip);
                            }
                            else {
                                Message_Box.ShowError("Có lỗi trong quá trình kích hoạt đặt mới trên IMS mới !" + message);
                            }
                        }
                        else {
                            Message_Box.ShowTB("Hệ thống chưa hỗ trợ hủy phiếu trong trường hợp này");
                            // hieutc: để sau tính
                            return;
                        }
                    }
                }
                new LapHopDongFacade2().CAPNHAT_HUYKH_STATUS(hdtb_id, txtMaTB.Text.Trim(), tt_nd.ngay_cn, TRANGTHAI_DONGBO.CHUADONGBO, tt_nd.ma_nd, tt_nd.ip);
            }
            //endregion
                    else
            new LapHopDongFacade2().CAPNHAT_HUYKH_STATUS(hdtb_id, txtMaTB.Text.Trim(), tt_nd.ngay_cn, TRANGTHAI_DONGBO.CHUADONGBO, tt_nd.ma_nd, tt_nd.ip);

            Message_Box.ShowTB("Hủy kích hoạt thành công");
                    long _hdtb_id = hdtb_id;
            HienThiDanhSachHDTB();

            gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
            for (int i = 0; i < gvDanhSach.RowCount; i++)
            if (_hdtb_id.ToString() == gvDanhSach.GetDataRow(i)["hdtb_id"].ToString()) {
                gvDanhSach.FocusedRowHandle = i;
                //gvDanhSach_FocusedRowChanged(null, new DevExpress.XtraGrid.Views.Base.FocusedRowChangedEventArgs(-1, i));
                break;
            }
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Lỗi xảy ra khi cập nhật dữ liệu: " + ex.Message);
    }
}

tsbtnNVKT_Click()
{
            //frmTCNhanVienDiaBan obj = new frmTCNhanVienDiaBan();
            //obj.hdtb_id = hdtb_id;
            //obj.loainv_id = Loai_NV.NHANVIEN_KYTHUAT;
            //obj.donviql_id = 0;
            //obj.ShowDialog();

            frmTCKhuVucDiaBan obj = new frmTCKhuVucDiaBan();
    obj.hdtb_id = hdtb_id;
    //obj.loainv_id = Loai_NV.NHANVIEN_KYTHUAT;
    obj.donviql_id = 0;
    obj.ShowDialog();
}

btnBanDoLD_Click()
{
    try {
                double lattitude = 0;
                double longtitude = 0;
        s = new CheckDataFacade().MAP_ID("kinhdo_ld", DatabaseConstants.DB2 + ".diachi_hdtb", "where hdtb_id =" + hdtb_id);
        if (s != "-1" && s != "")
            longtitude = double.Parse(s);

        s = new CheckDataFacade().MAP_ID("vido_ld", DatabaseConstants.DB2 + ".diachi_hdtb", "where hdtb_id =" + hdtb_id);
        if (s != "-1" && s != "")
            lattitude = double.Parse(s);
                frmBanDoTB frm;
        if (lattitude * longtitude > 0) {
            frm = new frmBanDoTB(lattitude, longtitude, txtMaTB.Text, false);
            frm.title = txtDiaChiLD.Text;
            frm.MapZoom = 17;
            frm.vkieu = 2;
            if (frm.ShowDialog() == DialogResult.OK) {
                bangts.CapnhatToaDoHDTB(frm.Lat, frm.Lng, hdtb_id, 1);
            }
        }
        else if (txtDiaChiLD.Text.Trim() != "") {
            frm = new frmBanDoTB(txtDiaChiLD.Text, "Vị trí tương đối theo địa chỉ:\r\n" + txtMaTB.Text, false);
            frm.title = txtDiaChiLD.Text;
            frm.MapZoom = 17;
            frm.vkieu = 2;
            if (frm.ShowDialog() == DialogResult.OK) {
                bangts.CapnhatToaDoHDTB(frm.Lat, frm.Lng, hdtb_id, 1);
            }
        }
        else {
            Message_Box.ShowTB("Chưa có thông tin về bản đồ địa chỉ thuê bao");
            return;
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowTB("" + ex.ToString());
    }
}

btnBanDoTB_Click()
{
    try {
                double lattitude = 0;
                double longtitude = 0;
        s = new CheckDataFacade().MAP_ID("kinhdo", DatabaseConstants.DB2 + ".diachi_hdtb", "where hdtb_id =" + hdtb_id);
        if (s != "-1" && s != "")
            longtitude = double.Parse(s);

        s = new CheckDataFacade().MAP_ID("vido", DatabaseConstants.DB2 + ".diachi_hdtb", "where hdtb_id =" + hdtb_id);
        if (s != "-1" && s != "")
            lattitude = double.Parse(s);
                frmBanDoTB frm;
        if (lattitude * longtitude > 0) {
            frm = new frmBanDoTB(lattitude, longtitude, txtMaTB.Text, false);
            frm.title = txtDiaChiLD.Text;
            frm.MapZoom = 17;
            frm.vkieu = 2;
            if (frm.ShowDialog() == DialogResult.OK) {
                bangts.CapnhatToaDoHDTB(frm.Lat, frm.Lng, hdtb_id, 0);
            }
        }
        else if (txtDiaChiLD.Text.Trim() != "") {
            frm = new frmBanDoTB(txtDiaChiLD.Text, "Vị trí tương đối theo địa chỉ:\r\n" + txtMaTB.Text, false);
            frm.title = txtDiaChiLD.Text;
            frm.MapZoom = 17;
            frm.vkieu = 2;
            if (frm.ShowDialog() == DialogResult.OK) {
                bangts.CapnhatToaDoHDTB(frm.Lat, frm.Lng, hdtb_id, 0);
            }
        }
        else {
            Message_Box.ShowTB("Chưa có thông tin về bản đồ địa chỉ thuê bao");
            return;
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowTB("" + ex.ToString());
    }
}

tsbtnDauNoiCap_Click()
{
    try {
        if (gvDanhSach.FocusedRowHandle < 0)
            return;
                int vtongdai_id = 0;
                //if (ne_id == 0)
                //{
                //    Message_Box.ShowTB("Chưa có thông tin về Ne, bạn không được phép cấp cáp.");
                //    return;
                //}

                // s = new CheckDataFacade().MAP_ID("donvi_id", DatabaseConstants.DB2 + ".sone a", "where a.ne_id = " + ne_id + "");
                //if (s != "-1" && s != "")
                //    vtongdai_id = Number(s);
                //else
                //{
                //    Message_Box.ShowTB("Chưa có thông tin về trạm thiết bị. Hãy kiểm tra lại.");
                //    return;
                //}

                double lattitude = 0;
                double longtitude = 0;
        var s = new CheckDataFacade().MAP_ID("kinhdo_ld", DatabaseConstants.DB2 + ".diachi_hdtb", "where hdtb_id =" + hdtb_id);
        if (s != "-1" && s != "")
            lattitude = double.Parse(s);

        s = new CheckDataFacade().MAP_ID("vido_ld", DatabaseConstants.DB2 + ".diachi_hdtb", "where hdtb_id =" + hdtb_id);
        if (s != "-1" && s != "")
            longtitude = double.Parse(s);

        vto_ql = new CheckDataFacade().MAP_ID("ten_dv", DatabaseConstants.DB1 + ".donvi", "where donvi_id =" + tramvt_id);
        vtram_tbi = new CheckDataFacade().MAP_ID("ten_dv", DatabaseConstants.DB1 + ".donvi", "where donvi_id =" + vtongdai_id);

        if (IS_USING_CABMAN_V2) {
            if (DatabaseConstants.DB2 == "CSS_HCM")
                using(var dig = new WinUICabmanV2.frmDauNoiCapTB(-hdtb_id))
                    {
                        dig.ShowDialog();
            HIEN_THONGSO_KYTHUAT();
            var culy = new CheckDataFacade().MAP_ID("CULY", DatabaseConstants.DB2 + ".DAUNOI_CAP", "where thuebao_id = -" + hdtb_id);
            if (culy == "-1" || .IsNullOrEmpty(culy))
                culy = "0";
            txtSoMetDay.Text = culy;
        }
        else
            using(var dig = new WinUICabmanV2.frmDauNoiTB(hdtb_id, 1))
                {
                    dig.ShowDialog();
        HIEN_THONGSO_KYTHUAT();
        var culy = new CheckDataFacade().MAP_ID("CULY", DatabaseConstants.DB2 + ".VI_HDTB", "where hdtb_id =" + hdtb_id);
        if (culy == "-1" || .IsNullOrEmpty(culy))
            culy = "0";
        txtSoMetDay.Text = culy;
    }
                }
                else
{
    WinUIDieuHanhThiCong.frmDauNoiTuyenCap obj = new WinUIDieuHanhThiCong.frmDauNoiTuyenCap();
    obj.vkinhdo = longtitude;
    obj.vvido = lattitude;
    obj.vten_tb = txtTenTB.Text.Trim();
    obj.vdiachi_ld = txtDiaChiLD.Text.Trim();
    obj.vhdtb_id = hdtb_id;
    obj.vdichvuvt_id = DichVuVienThong.CO_DINH;
    obj.vto_ql = vto_ql;
    obj.vtram_tbi = vtram_tbi;
    obj.vtongdai_id = vtongdai_id;
    obj.vtramvt_id = tramvt_id;// MANHLX 17/05/2016
    obj.vma_tb = txtMaTB.Text.Trim();
    obj.ShowDialog();
}
            }
            catch (Exception ex)
{
    Message_Box.ShowError("" + ex.ToString());
}
        }

tsbtnXuatFile_Click()
{
    try {
        if (dsIn.Tables[0].Rows.Count > 0) {
                    frmLayDuLieuBC frm = new frmLayDuLieuBC();
            frm.dsbaoCao = dsIn;
            frm.ShowDialog();
        }
        else
            Message_Box.ShowTB("Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !");
    }
    catch (Exception ex)
    {
        Message_Box.ShowTB("Có lỗi : " + ex);
    }
}

tsbtnGiaoPhieu_Ton_Click()
{
    if (phieu_id == 0) {
        Message_Box.ShowTB("Hãy chọn phiếu trước để nhập lý do tồn!");
        return;
    }

            frmGiaoPhieu_Ton frm = new frmGiaoPhieu_Ton();
    frm.phieu_id = phieu_id;
    frm.tthd_id = tthd_id;

    foreach(DataRow dr in dsIn.Tables[0].Rows)
    if (dr["CHON"].ToString() == "True" || dr["CHON"].ToString() == "1")
        frm.dsPhieu.Add(long.Parse(dr["PHIEU_ID"].ToString()));
    if (!frm.dsPhieu.Contains(phieu_id))
        frm.dsPhieu.Add(phieu_id);
    frm.ShowDialog();
}

tsbtnChuyenTo_Click()
{
    try {
        if (PHAILAM(luong_id, "KIEMTRA_VT_CHUYENTO")) {
            if (CapVT.LAY_DS_PHIEUVT_HDBH(phieu_id, 0, hdtb_id, 0, 0).Tables[0].Rows.Count > 0) {
                Message_Box.ShowTB("Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ");
                return;
            }
        }
        WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
        f.hdtb_id = hdtb_id;
        f.baohong_id = 0;
        f.phieu_id = phieu_id;
        f.ShowDialog();
        if (f.Chapnhan)
            HienThiDanhSachHDTB();
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("" + ex.ToString());
    }
}

tsbtnDuAn_Click()
{
    try {
                frmDuAn_HDTB f_duan = new frmDuAn_HDTB();
        f_duan.vhdtb_id = hdtb_id;
        f_duan.ShowDialog();
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("" + ex.ToString());
    }
}

tsbtnKQ_DoKiem_Click()
{
    if (hdtb_id != 0) {
                frmKetQuaDoKiem frm = new frmKetQuaDoKiem();
        frm.vhdtb_id = hdtb_id;
        frm.ShowDialog();
    }
    else {
        MessageBox.Show("Bạn chưa chọn phiếu!");
    }
}

tsbtnThongTinLH_Click()
{
    if (hdkh_id != 0) {
                frmThongTinLienHe f = new frmThongTinLienHe();
        f.vkhachhang_id = 0;
        f.vhdkh_id = hdkh_id;
        f.ten_kh = txtTenTB.Text.Trim();
        f.diachi = txtDiaChiLD.Text.Trim();
        f.ShowDialog();
    }
}

tsbtnVatTuMoi_Click()
{
    if (phieu_id != 0) {
                frmCapVatTuTB frm = new frmCapVatTuTB();
        frm.phieu_id = phieu_id;
        frm.loaihd_id = loaihd_id;
        frm.hdtb_id = hdtb_id;
        frm.thuebao_id = thuebao_id;
        frm.loaitb_id = loaitb_id;
        frm.baohong_id = 0;
        frm.ShowDialog();
    }
    else {
        MessageBox.Show("Bạn chưa chọn phiếu!");
    }
}

tsbtnSua_KenhThu_Click()
{
            frmThayDoi_KenhThu f = new frmThayDoi_KenhThu(txtMaGD.Text.Trim());
    f.ShowDialog();
}

chkTimKiem_CheckedChanged()
{
    if (chkTimKiem.Checked) {
        cboLoaiDVTK.Enabled = true;
        cboDViTimKiem.Enabled = true;
        if (cboLoaiDVTK.Items.Count == 0)
            bangts.HT_LOAIDV_TIMKIEM(cboLoaiDVTK);

        try {
            if (cboDViTimKiem.Text == "" || cboDViTimKiem.EditValue.ToString() == "0") return;
            HienThiDanhSachHDTB_TK_LOAIDV(Number(cboLoaiDVTK.SelectedValue), Number(cboDViTimKiem.EditValue));
        }
        catch (Exception ex)
        {
            Message_Box.ShowError("" + ex.ToString());
        }
    }
    else {
        cboLoaiDVTK.Enabled = false;
        cboDViTimKiem.Enabled = false;
        HienThiDanhSachHDTB();
        inDex_load = 0;
    }
}

cboDViTimKiem_EditValueChanged()
{
    try {
        if (cboDViTimKiem.Text == "" || cboDViTimKiem.EditValue.ToString() == "0") return;
        HienThiDanhSachHDTB_TK_LOAIDV(Number(cboLoaiDVTK.SelectedValue), Number(cboDViTimKiem.EditValue));
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("" + ex.ToString());
    }
}

cboLoaiDVTK_SelectedValueChanged()
{
    if (cboLoaiDVTK.Items.Count > 0) {
        if (cboLoaiDVTK.SelectedValue == null) return;
        if (cboLoaiDVTK.SelectedValue.ToString() == "System.Data.DataRowView") return;
        if (Number(cboLoaiDVTK.SelectedValue) == LOAI_DV.HOST) {
            if (ds_donvi_timkiem.Tables.Count == 0)
                ds_donvi_timkiem = bangts.Lay_ds_donvi_theo_loaidv(LOAI_DV.HOST);
            cboDViTimKiem.Properties.DataSource = null;
            //var t = ds_donvi_timkiem.Tables[0].Select("DONVI_QL_ID=" + dt.Rows[0]["hostvl_id"].ToString());
            cboDViTimKiem.Properties.DataSource = ds_donvi_timkiem.Tables[0];// t.length > 0 ? t.CopyToDataTable() : null;
            cboDViTimKiem.Properties.ValueMember = "DONVI_ID";
            cboDViTimKiem.Properties.DisplayMember = "TEN_DV";
            cboDViTimKiem.Properties.PopupFormWidth = 400;
            cboDViTimKiem.Properties.View.GridControl.Size = new Size(20, 20);

            cboDViTimKiem.Properties.TextEditStyle = DevExpress.XtraEditors.Controls.TextEditStyles.Standard;
            cboDViTimKiem.Properties.View.OptionsView.ShowAutoFilterRow = true;
            cboDViTimKiem.Properties.ImmediatePopup = true;
            cboDViTimKiem.EditValue = 0;//cboDViTimKiem.Properties.GetKeyValue(0);
            cboDViTimKiem.Text = "";
        }
    }
}

gview_DanhSach_RowCellStyle(DevExpress.XtraGrid.Views.Grid.RowCellStyleEventArgs e)
{
    try {
        if (e.RowHandle < 0)
            return;
        if (e.Column == gcol_Printed) {
                    DataRow dr = gvDanhSach.GetDataRow(e.RowHandle);
            if (dr["luu_in"] != DBNull.Value)
                e.Appearance.ForeColor = Color.Crimson;
        }
    }
    catch (Exception ex)
    {
        Message_Box.ShowError(ex.ToString());
    }
}

grid_DanhSach_DataSourceChanged()
{
    try {
        gvDanhSach.FocusedRowHandle = DevExpress.XtraGrid.GridControl.AutoFilterRowHandle;
        if (gridDanhSach.DataSource is DataTable)
        {
                    DataTable dt = gridDanhSach.DataSource as DataTable;
            gvDanhSach.Columns["GOI_KT"].Visible = dt.Columns.Contains("GOI_KT");
            gvDanhSach.Columns["GIO_CT"].Visible = dt.Columns.Contains("GIO_CT");
            gvDanhSach.Columns["GIO_CONLAI"].Visible = dt.Columns.Contains("GIO_CONLAI");
            gvDanhSach.Columns["LYDOTON_TC"].Visible = dt.Columns.Contains("LYDOTON_TC");
            gvDanhSach.Columns["GHICHU_TON"].Visible = dt.Columns.Contains("GHICHU_TON");
        }
        if (dtList.Columns.Contains("CHON")) {
            if (dtList.Columns["CHON"].DataType == typeof (bool))
                return;
            dtList.Columns.Remove("CHON");
        }
        dtList.Columns.Add("CHON", typeof (bool));
        foreach(DataRow dr in dtList.Rows)
        dr["CHON"] = false;
        gvDanhSach.FocusedRowHandle = 0;
    }
    catch (Exception ex)
    {
        Message_Box.ShowError(ex.ToString());
    }
}

gview_DanhSach_KeyDown(KeyEventArgs e)
{
    if (gvDanhSach.FocusedRowHandle < 0)
        return;
    try {
        if (e.KeyCode == Keys.Space) {

            foreach(int index in gvDanhSach.GetSelectedRows())
            {
                        DataRow dr = gvDanhSach.GetDataRow(index);
                dr["CHON"] = !dr["CHON"].Equals(true);
            }
        }
        if (e.KeyCode == Keys.Delete)
            foreach(DataRow dr in dtList.Rows)
        dr["CHON"] = false;

        if (e.KeyCode == Keys.Insert)
            foreach(DataRow dr in dtList.Rows)
        dr["CHON"] = true;
    }
    catch (Exception ex)
    {
        Message_Box.ShowError(ex.ToString());
    }
}

gvDanhSach_DoubleClick()
{
    try {
        return;
        if (gvDanhSach.FocusedRowHandle < 0)
            return;
                DataRow dr = gvDanhSach.GetFocusedDataRow();
        if (dr == null)
            return;
        if (dr["CHON"].Equals(true) && PHAILAM(luong_id, "CAPNHAT_CAP"))
            Message_Box.ShowWarning("Bạn phải chờ thuê bao khác cùng line với thuê bao này giao về Trạm thi công !");
    }
    catch (Exception ex)
    {
        new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, ex.Message, ex), true);
    }
}

btnCollapse_Click()
{
    if (btnCollapse.Text == "-") {
        panelControl1.Visible = false;
        btnCollapse.Text = "+";
    }
    else {
        panelControl1.Visible = true;
        btnCollapse.Text = "-";
    }
}

gvDanhSach_CustomDrawCell(DevExpress.XtraGrid.Views.Base.RowCellCustomDrawEventArgs e)
{
    if (e.RowHandle < 0) return;
    if (e.Column.FieldName == "IN_STATUS") {
        var istatus = gvDanhSach.GetRowCellValue(e.RowHandle, "LUU_IN").ToString();
        if (istatus != "") {
            gvDanhSach.SetRowCellValue(e.RowHandle, "IN_STATUS", "Đã in");
            var img = lblDaIn.Image;
            e.Appearance.TextOptions.HAlignment = DevExpress.Utils.HorzAlignment.Center;
            e.Graphics.DrawImage(img, e.Bounds.X, e.Bounds.Y, e.Bounds.Height, e.Bounds.Height);
        }
        else
            gvDanhSach.SetRowCellValue(e.RowHandle, "IN_STATUS", "");
    }
    e.Handled = false;
}

tsbtnGiaoPhieu_PH_V2_Click()
{
    try {
        if (gvDanhSach.FocusedRowHandle < 0)
            return;
                frmGiaoPhieu_PH f = new frmGiaoPhieu_PH();
        f.vhdtb_id_cur = hdtb_id;
        f.vphieu_id_cur = phieu_id;
        f.vquytrinh_id = quytrinh_id;
        f.ShowDialog();
    }
    catch (Exception ex)
    {
        Message_Box.ShowError("Lỗi giao phiếu P/H:\n " + ex.ToString());
    }
}

    }