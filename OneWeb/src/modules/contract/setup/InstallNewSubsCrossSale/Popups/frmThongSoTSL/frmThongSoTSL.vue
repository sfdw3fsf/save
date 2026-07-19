<template src="./frmThongSoTSL.html"></template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import moment from 'moment'
export default {
    components: {
        Select2Ext
    },
    data() {
        return {
            thongso_Ban : {},
            thongso_TC : {},
            Capnhat : {},
            ngay : "",
            BCbase: {},
            tcdanhba : {},
            pLoaihinhTB: 0,
            tinhtc : {},
            bangts :{},
            bangts_tc : {},
            kt_load: 0,
            ngay_tc : moment().format("DD/MM/YYYY"),
            kenhtrang :false,
            vloaihd_id: 0,
            ht_tskt_vnpti : false,
            tinh_chuquan_id : "-1"
        }
    },
    methods: {
        showDialog () {
            this.$refs.frmThongSoTSL.show()
        },
        async frmThongSoTSL(tinh, ploaitb_ld, ploaitb, kieuld_id, pthongso_Ban, pthongso_TC,vloaihd, vkenhtrang)
        {
            //region khai bao
            this.kt_load = true;
            this.pLoaihinhTB = ploaitb;
            this.tinhtc = tinh;
            this.thongso_Ban = pthongso_Ban;
            this.thongso_TC = pthongso_TC;
            this.kenhtrang = vkenhtrang;
            bangts.HT_DoiTuong_Combobox(cboDoituongBan);
            rs = await this.axios.post("web-hopdong/thaydoitocdoadsl/sp_lay_ds_doituong_theo_dieukien",
            {
                p_phanvung_id: this.$root.token.getPhanVungID(),
                p_nguoidung_id: this.$root.token.getNhanVienID()
            })
            this.$refs.cboDoituongBan.dataSource = rs.data.data
            this.$refs.cboDoituongBan.dataTextField = ""
            this.$refs.cboDoituongBan.dataValueField = ""

            if (this.thongso_Ban.doituong_id == 0)
                this.$refs.cboDoituongBan.SelectedIndex = 0;
            else
                this.$refs.cboDoituongBan.value = this.thongso_Ban.doituong_id;

            rs = await this.axios.post("web-bancheo/danhmuc/load_dm_doi_tuong",
            {
                "tinhthicong_id": this.tinh
            })

            this.$refs.cboDoituongBan.dataSource = rs.data.data
            this.$refs.cboDoituongBan.dataTextField = ""
            this.$refs.cboDoituongBan.dataValueField = ""

            if (this.thongso_TC.doituong_id == 0)
                this.$refs.cboDoituongTC.SelectedIndex = 0;
            else
                this.$refs.cboDoituongTC.value = this.thongso_TC.doituong_id;

            rs = await this.axios.post("web-bancheo/danhmuc/ht_kieu_ld_combobox", {
                "tinhthicong_id": this.tinh,
                "loaitb_id": ploaitb_ld,
                "loaihd_id": vloaihd
            })

            this.$refs.cboKieuLD.dataSource = rs.data.data
            this.$refs.cboKieuLD.dataTextField = ""
            this.$refs.cboKieuLD.dataValueField = ""

            this.vloaihd_id = vloaihd;

            if (this.$refs.cboKieuLD.dataSource.length > 0)
                if (this.thongso_TC.kieuld_id == 0)
                    this.$refs.cboKieuLD.SelectedIndex = 0;
                else
                    this.$refs.cboKieuLD.value = this.thongso_TC.kieuld_id;
            else
                this.$toast.error("Không tìm thấy kiểu lắp đặt ở bên thi công ứng với loại hợp đồng và loại thuê bao này.");

            //bangts_tc.HT_Loai_Kenh_Combobox(tinh, cboLoaikenhBan, 1);
            rs = this.axios.get("web-quantri/danhmuc-chung/CSS_LOAI_KENH")
            this.$refs.cboLoaikenhBan.dataSource = rs.data.data
            this.$refs.cboLoaikenhBan.dataTextField = ""
            this.$refs.cboLoaikenhBan.dataValueField = ""

            if (this.thongso_Ban.loaikenh_id == 0)
                this.$refs.cboLoaikenhBan.SelectedIndex = 0;
            else
                this.$refs.cboLoaikenhBan.value = this.thongso_Ban.loaikenh_id;

            this.$refs.cboLoaikenhTC.dataSource = rs.data.data
            this.$refs.cboLoaikenhTC.dataTextField = ""
            this.$refs.cboLoaikenhTC.dataValueField = ""

            if (this.thongso_TC.loaikenh_id == 0)
                this.$refs.cboLoaikenhTC.SelectedIndex = 0;
            else
                this.$refs.cboLoaikenhTC.value = this.thongso_TC.loaikenh_id;

            //region Chủ quản
            rs = await this.axios.post("web-hopdong/hopdong/lay_chuquan_macdinh_theo_donvi_bancheo")
            this.tinh_chuquan_id = rs.data.data

            rs = await this.axios.get("web-hopdong/hopdong/thaydoimegawan/chuquan")
            // DataSet ds_cq_ban = bangts.getDataFromSQL("select * from " + DatabaseConstants.DB2 + ".chuquan order by thutu", "cq_ban");
            let ds_cq_ban = rs.data.data
            rs = await this.axios.post("web-bancheo/tracuu/get_chuquan", {
                tinhthicong_id: this.tinhtc.matinh
            })
            let ds_cq_tc = rs.data.data
            // DataSet ds_cq_tc = BCbase.getDataFromSQL_BC(tinhtc, "select * from css_" + tinhtc.matinh + ".chuquan where tinh_id is not null order by thutu", "cq_tc");
            this.$refs.cboChuquanBan.HT_COMBOBOX(ds_cq_ban, "", "")
            this.$refs.cboChuQuanLD_Ban.HT_COMBOBOX(ds_cq_ban, "", "")

            this.$refs.cboChuquanTC.HT_COMBOBOX(ds_cq_tc, "", "")
            this.$refs.cboChuQuanLD_TC.HT_COMBOBOX(ds_cq_tc, "", "")            
            // bangts.HT_COMBOBOX(cboChuquanBan, ds_cq_ban.Tables[0], "tenchuquan", "chuquan_id");
            // bangts.HT_COMBOBOX(cboChuQuanLD_Ban, ds_cq_ban.Tables[0], "tenchuquan", "chuquan_id");

            // bangts.HT_COMBOBOX(cboChuquanTC, ds_cq_tc.Tables[0], "tenchuquan", "chuquan_id");
            // bangts.HT_COMBOBOX(cboChuQuanLD_TC, ds_cq_tc.Tables[0], "tenchuquan", "chuquan_id");
            let det = [];
            if (this.tinh_chuquan_id == "-1")
                det = ds_cq_ban.filter(x => x["tinh_id"] == this.$root.token.getPhanVungID());
            else
                det = ds_cq_ban.filter(x => x["tinh_id"] == this.tinh_chuquan_id);
            
            let row
            if (det.length > 0)
            {
                lrow = det[0];
                if (row["chuquan_id"] != "")
                    this.$refs.cboChuquanBan.value = row["chuquan_id"]
            }

            if (this.tinh_chuquan_id == "-1")
                det = ds_cq_tc.filter(x => x["tinh_id"] == this.$root.token.getPhanVungID());
            else
                det = ds_cq_tc.filter(x => x["tinh_id"] == this.tinh_chuquan_id);
            
            if (det.length > 0)
            {
                row = det[0];
                if (row["chuquan_id"] != "")
                    this.$refs.cboChuquanTC.value = row["chuquan_id"]
            }

            if (this.tinhthicong_id == "-1")
                det = ds_cq_tc.filter(x => x["tinh_id"] == this.$root.token.getPhanVungID());
            else
                det = ds_cq_tc.filter(x => x["tinh_id"] == this.tinh_chuquan_id);
            
            if (det.length > 0)
            {
                row = det[0];
                if (row["chuquan_id"] != "")
                    this.$refs.cboChuquanTC.value = row["chuquan_id"]
            }

            det = ds_cq_tc.filter(x => x["tinh_id"] == this.tinhtc.tinhthicong_id)

            if (det.Count > 0)
            {
                 row = det[0];
                if (row["chuquan_id"] != "")
                    this.$refs.cboChuQuanLD_TC.value = row["chuquan_id"]
            }
            // //endregion

            rs = await this.axios.post("web-thicong/hoinghi_truyenhinh/ht_tocdo_kenh_combobox")

            // //bangts_tc.HT_Tocdo_Kenh_Combobox(tinh, cboTocdoBan, 1);
            // //bangts.HT_Tocdo_Kenh_MEGAWAN_Combobox(cboTocdoBan);
            // bangts.HT_Tocdo_Kenh_Combobox(cboTocdoBan);
            this.$refs.cboTocdoBan.HT_COMBOBOX(rs.data.data, "", "")
            if (!this.thongso_Ban.tocdo_id)
                this.$refs.cboTocdoBan.SelectedIndex = 0;
            else
                this.$refs.cboTocdoBan.value = this.thongso_Ban.tocdo_id;

            this.$refs.cboTocdoTC.HT_COMBOBOX(rs.data.data, "", "")
            if (!this.thongso_TC.tocdo_id)
                this.$refs.cboTocdoTC.SelectedIndex = 0;
            else
                this.$refs.cboTocdoTC.value = this.thongso_Ban.tocdo_id;

            rs = await this.axios.post("web-bancheo/danhmuc/load_dm_trang_bi",
            {
                "tinhthicong_id": this.tinh
            })
            this.$refs.cboTrangbiBan.HT_COMBOBOX(rs.data.data, "", "")
            if (!this.thongso_Ban.trangbi_id)
                this.$refs.cboTrangbiBan.SelectedIndex = 0;
            else
                this.$refs.cboTrangbiBan.value = this.thongso_Ban.trangbi_id;

            // //bangts.HT_TrangBi_TSL_Combobox_DK(cboTrangbiBan, "");
            // //bangts.HT_TrangBi_TSL_Combobox_DK(cboTrangbiTC, "");

            // bangts_tc.HT_TrangBi_Combobox(tinh, cboTrangbiTC);
            this.$refs.cboTrangbiTC.HT_COMBOBOX(rs.data.data, "", "")
            if (this.thongso_TC.trangbi_id == 0)
                this.$refs.cboTrangbiTC.SelectedIndex = 0;
            else
                this.$refs.cboTrangbiTC.value = this.thongso_TC.trangbi_id;

            
            rs = await this.axios.post("web-bancheo/danhmuc/ht_loai_tb_lhtb_combobox", {
                kieucb: 1,
                loaitb_id: this.ploaitb,
                tinhthicong_id: this.tinh
            })
            this.$refs.cboThietbiDauBan.HT_COMBOBOX(rs.data.data, "", "")
            // bangts_tc.HT_LOAI_TB_LHTB_Combobox(1, tinh, cboThietbiDauBan, ploaitb);
            if (!this.thongso_Ban.thietbidc_id)
                this.$refs.cboThietbiDauBan.SelectedIndex = 0;
            else
                this.$refs.cboThietbiDauBan.value = this.thongso_Ban.thietbidc_id;


            // bangts_tc.HT_LOAI_TB_LHTB_Combobox(1, tinh, cboThietbiCuoiBan, ploaitb);
            this.$refs.cboThietbiCuoiBan.HT_COMBOBOX(rs.data.data, "", "")
            if (this.thongso_Ban.thietbidc_id_cuoi == 0)
                this.$refs.cboThietbiCuoiBan.SelectedIndex = 0;
            else
                this.$refs.cboThietbiCuoiBan.value = this.thongso_Ban.thietbidc_id_cuoi;


            // bangts_tc.HT_LOAI_TB_LHTB_Combobox(2, tinh, cboThietbiDauTC, ploaitb);
            this.$refs.cboThietbiDauTC.HT_COMBOBOX(rs.data.data, "", "")
            if (this.thongso_TC.thietbidc_id == 0)
                this.$refs.cboThietbiDauTC.SelectedIndex = 0;
            else
                this.$refs.cboThietbiDauTC.value = this.thongso_TC.thietbidc_id;

            this.$refs.cboThietbiCuoiTC.HT_COMBOBOX(rs.data.data, "", "")
            if (thongso_TC.thietbidc_id_cuoi == 0)
                this.$refs.cboThietbiCuoiTC.SelectedIndex = 0;
            else
                this.$refs.cboThietbiCuoiTC.value = this.thongso_TC.thietbidc_id_cuoi;

            // bangts_tc.Load_DanhMuc_BC(tinh, cboMHKN_TC, "mhkn_id", "mohinh_kn", " mhkn_id, mohinh_kn ", "{?DB2}.mohinh_kn", "", "");
            // if (thongso_TC.mhkn_id > 0)
            //     cboMHKN_TC.EditValue = thongso_TC.mhkn_id;

            // bangts_tc.Load_DanhMuc_BC(cboMHKN_BAN, "mhkn_id", "mohinh_kn", " mhkn_id, mohinh_kn ", "{?DB2}.mohinh_kn", "", "");
            // if (thongso_Ban.mhkn_id > 0)
            //     cboMHKN_BAN.EditValue = thongso_Ban.mhkn_id;

            // bangts_tc.Load_DanhMuc_BC(cboTocDoPOP_BAN, "tocdo_id", "tocdo", "tocdo_id, tocdo||' '||donvi tocdo", "{?DB2}.tocdo_kenh", "", "");
            // if (thongso_Ban.tocdo_pop > 0)
            //     cboTocDoPOP_BAN.EditValue = thongso_Ban.tocdo_pop;

            // bangts_tc.Load_DanhMuc_BC(tinh, cboTocDoPOP_TC, "tocdo_id", "tocdo", "tocdo_id, tocdo||' '||donvi tocdo", "{?DB2}.tocdo_kenh", "", "");
            // if (thongso_TC.tocdo_pop > 0)
            //     cboTocDoPOP_TC.EditValue = thongso_TC.tocdo_pop;

            // bangts_tc.Load_DanhMuc_BC(cboGiaoDienKH_BAN, "gdkn_id", "giaodien_kn", "gdkn_id, giaodien_kn", "{?DB2}.giaodien_kn", "", "");
            // if (thongso_Ban.gdkn_id > 0)
            //     cboGiaoDienKH_BAN.EditValue = thongso_Ban.gdkn_id;

            // bangts_tc.Load_DanhMuc_BC(tinh, cboGiaoDienKH_TC, "gdkn_id", "giaodien_kn", "gdkn_id, giaodien_kn", "{?DB2}.giaodien_kn", "", "");
            // if (thongso_TC.gdkn_id > 0)
            //     cboGiaoDienKH_TC.EditValue = thongso_TC.gdkn_id;

            // var str = "select 0 chon, a.kieunt_id, a.kieu_nghiemthu from  {?DB2}.kieu_nghiemthu a ";
            // var dt_kieu_nt = new BanCheoBase().GET_DATA_SQL(str);

            // bangts_tc.HienThi_CheckBox_Combobox(cboNT_DV_BAN, dt_kieu_nt, "kieunt_id", "kieu_nghiemthu", "chon", "1");
            // cboNT_DV_BAN.Properties.PopupFormMinSize = new Size(cboNT_DV_BAN.Width, 100);
            // //gán dữ liệu
            // if (thongso_Ban.kieu_nghiemthu != null && thongso_Ban.kieu_nghiemthu.Rows.Count > 0)
            // {
            //     thongso_Ban.kieu_nghiemthu.AcceptChanges();
            //     for (int i = 0; i < cboNT_DV_BAN.Properties.Items.Count; i++)
            //     {
            //         if (thongso_Ban.kieu_nghiemthu.Select("KIEUNT_ID=" + cboNT_DV_BAN.Properties.Items[i].Value + "").Length > 0)
            //         {
            //             cboNT_DV_BAN.Properties.Items[i].CheckState = CheckState.Checked;
            //         }
            //         else
            //             cboNT_DV_BAN.Properties.Items[i].CheckState = CheckState.Unchecked;
            //     }
            // }

            // bangts_tc.HienThi_CheckBox_Combobox(cboNT_DV_TC, dt_kieu_nt, "kieunt_id", "kieu_nghiemthu", "chon", "1");
            // cboNT_DV_TC.Properties.PopupFormMinSize = new Size(cboNT_DV_TC.Width, 100);
            // //gán dữ liệu
            // if (thongso_TC.kieu_nghiemthu != null && thongso_TC.kieu_nghiemthu.Rows.Count > 0)
            // {
            //     thongso_TC.kieu_nghiemthu.AcceptChanges();
            //     for (int i = 0; i < cboNT_DV_TC.Properties.Items.Count; i++)
            //     {
            //         if (thongso_TC.kieu_nghiemthu.Select("KIEUNT_ID=" + cboNT_DV_TC.Properties.Items[i].Value + "").Length > 0)
            //         {
            //             cboNT_DV_TC.Properties.Items[i].CheckState = CheckState.Checked;
            //         }
            //         else
            //             cboNT_DV_TC.Properties.Items[i].CheckState = CheckState.Unchecked;
            //     }
            // }

            // txtAuto_Nego_BAN.Text = thongso_Ban.auto_nego;
            // txtAuto_Nego_TC.Text = thongso_TC.auto_nego;

            // txtSNCP_BAN.Text = thongso_Ban.sncp;
            // txtSNCP_TC.Text = thongso_TC.sncp;

            // txtCapChinh_BAN.Text = thongso_Ban.cap_chinh;
            // txtCapChinh_TC.Text = thongso_TC.cap_chinh;

            // txtCapDP_BAN.Text = thongso_Ban.cap_duphong;
            // txtCapDP_TC.Text = thongso_TC.cap_duphong;


            // string kq = new BanCheoBase().GET_STRING_FUNCTION("{?DB2}.bancheo_kiemtra_dichvu_vnpti",
            //                                                  "vloaitb_id", pLoaihinhTB,
            //                                                  "vloaihd_id", vloaihd_id,
            //                                                  "vdichvuvt_id", 9,
            //                                                  "vnhanvien_id", tt_nd.nhanvien_id);
            // if (kq == "OK")
            //     ht_tskt_vnpti = true;
            // if (!ht_tskt_vnpti)
            // {
            //     //if (this.Size.Height < 304)
            //     //{
            //     //    panelControl1.Visible = true;
            //     //    panelControl2.Visible = true;
            //     //    this.Size = new Size(this.Size.Width, (this.Size.Height + panelControl2.Size.Height));
            //     //}

            //     //else if (this.Size.Height > 304)
            //     //{
            //     //    panelControl1.Visible = false;
            //     //    panelControl2.Visible = false;
            //     //    this.Size = new Size(this.Size.Width, (this.Size.Height - panelControl2.Size.Height));
            //     //}
            //     panelControl1.Visible = false;
            //     panelControl2.Visible = false;
            //     this.Size = new Size(this.Size.Width, (this.Size.Height - panelControl2.Size.Height));
            // }

            // kt_load = false;
            // //endregion

            // if ((vloaihd == LoaiHopDong.CHUYEN_QUYEN || vloaihd == LoaiHopDong.DI_CHUYEN) && !kenhtrang)
            // {
            //     cboDoituongBan.Enabled = false;
            //     cboLoaikenhBan.Enabled = false;
            //     cboMuccuoctbBan.Enabled = false;
            //     cboThietbiCuoiBan.Enabled = false;
            //     cboThietbiDauBan.Enabled = false;
            //     cboTocdoBan.Enabled = false;

            //     cboDoituongTC.Enabled = false;
            //     cboLoaikenhTC.Enabled = false;
            //     cboMuccuoctbTC.Enabled = false;
            //     cboThietbiDauTC.Enabled = false;
            //     cboThietbiCuoiTC.Enabled = false;
            //     cboTocdoTC.Enabled = false;
            // }

        }
        // private void splitContainer1_Panel2_Paint(object sender, PaintEventArgs e)
        // {

        // }

        // private void ThongSoCoDinh_Load(object sender, EventArgs e)
        // {
        //     try
        //     {
        //         if (!kt_load)
        //         {
        //             bangts_tc.Lay_DS_MucCuoc_TB(tinhtc, cboMuccuoctbTC, 0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongTC.value), ngay, Convert.ToInt32(cboTrangbiTC.value), Convert.ToInt32(cboTocdoTC.value), 0);
        //             DataSet ds = new DataSet();
        //             ds = tcdanhba.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongBan.value), 0, 0, ngay, Convert.ToInt32(cboTrangbiBan.value));
        //             if (ds.Tables[0].Rows.Count > 0)
        //             {
        //                 cboMuccuoctbBan.DataSource = ds.Tables[0];
        //                 cboMuccuoctbBan.ValueMember = "mucuoctb_id";
        //                 cboMuccuoctbBan.DisplayMember = "tenmuccuoc";
        //             }
        //             else
        //                 cboMuccuoctbBan.DataSource = null;
        //         }
        //     }
        //     catch (Exception ex) { Message_Box.ShowTB(ex.Message); }
        // }

        // private void tsbtnThoat_Click(object sender, EventArgs e)
        // {
        //     Capnhat = false;
        //     Close();
        // }

        // private void tsbtnCapNhat_Click(object sender, EventArgs e)
        // {
        //     if (cboMuccuoctbBan.value == null || cboMuccuoctbTC.value == null)
        //     {
        //         Message_Box.ShowTB("Bạn phải chọn mức cước  thuê bao cho 2 đơn vị");
        //         return;
        //     }
        //     if (Convert.ToInt32(cboThietbiCuoiBan.value.ToString()) == 0 || Convert.ToInt32(cboThietbiDauBan.value.ToString()) == 0
        //      || Convert.ToInt32(cboThietbiDauTC.value.ToString()) == 0 || Convert.ToInt32(cboThietbiCuoiTC.value.ToString()) == 0)
        //     {
        //         Message_Box.ShowTB("Bạn phải chọn thiết bị đầu cuối cho 2 đơn vị");
        //         return;
        //     }

        //     var dt_kieu_nt_ban = new DataTable();
        //     dt_kieu_nt_ban.Columns.Add("KIEUNT_ID", typeof(int));
        //     var dt_kieu_nt_tc = new DataTable();
        //     dt_kieu_nt_tc.Columns.Add("KIEUNT_ID", typeof(int));

        //     if (Convert.ToInt32(cboLoaikenhBan.value) == 9) // kênh quốc tế
        //     {
        //         try
        //         {
        //             var obj = new ThongSoThueBao
        //             {
        //                 tocdo_id = 0,
        //                 tocdothuc_id = 0,
        //                 tocdo_pir_id = 0,
        //                 tocdo_nix = 0,
        //                 tocdo_isp = 0,
        //                 chuquan_id = Convert.ToInt32(cboChuquanBan.value),
        //                 chuquanld_id = Convert.ToInt32(cboChuQuanLD_Ban.value),
        //                 congnghe_id = 0,
        //                 muccuoc_id = 0,
        //                 kieutra_id = 0,
        //                 doituong_id = 0,
        //                 muccuoctb_id = 0,
        //                 trangbi_id = 0,
        //                 kieuld_id = Convert.ToInt32(cboKieuLD.value),
        //                 thietbidc_id = 0,
        //                 loaimd_id = 0,
        //                 loaikenh_id = Convert.ToInt32(cboLoaikenhBan.value),
        //                 thietbidc_id_cuoi = 0,
        //                 thoihan_id = 0,
        //                 ma_tn = "",
        //                 ma_doicap = "",
        //                 chuquanld_id_cuoi = 0,
        //                 phanloai_id = 0,
        //                 phantach_id = 0,
        //                 mhkn_id = Convert.ToInt32(cboMHKN_BAN.EditValue),
        //                 gdkn_id = 0,
        //                 yc_baove = 0,
        //                 tocdo_pop = Convert.ToInt32(cboTocDoPOP_BAN.EditValue),
        //                 gdkn_pop_id = 0,
        //                 gdkn_kh_id = Convert.ToInt32(cboGiaoDienKH_BAN.EditValue),
        //                 cauhinh_dt_id = 0,
        //                 kieu_duplex_id = 0,
        //                 kieukn_id = 0,
        //                 mtu_size = txtMTU_Size_BAN.Text,
        //                 auto_nego = txtAuto_Nego_BAN.Text,
        //                 sncp = txtSNCP_BAN.Text,
        //                 cap_chinh = txtCapChinh_BAN.Text,
        //                 cap_duphong = txtCapDP_BAN.Text,
        //                 diem_pop = "",
        //                 wan_ip = "",
        //                 lan_ip = "",
        //                 ce_ip = "",
        //                 tt_ip = "",
        //                 sl_ip = 0,
        //                 ten_trunk_nni = "",
        //                 so_as = "",
        //                 hqos = "",
        //                 cos = "",
        //                 svlan = "",
        //                 cvlan = "",
        //                 vlan_transparent = "",
        //                 tt_port = ""
        //             };
        //             var tt_ban = Newtonsoft.Json.JsonConvert.SerializeObject(obj);
        //             string str = "{?DB2}.bancheo_kiemtra_thongsokt";
        //             string kq_kt = new BanCheoBase().GET_STRING_FUNCTION(str, "vjson_kt", tt_ban,
        //                                                                    "vloaihd_id", vloaihd_id,
        //                                                                    "vdichvuvt_id", 9,
        //                                                                    "vloaitb_id", pLoaihinhTB,
        //                                                                    "vnhanvien_id", tt_nd.nhanvien_id);
        //             if (kq_kt != "OK")
        //             {
        //                 Message_Box.ShowError("Có lỗi: " + kq_kt);
        //                 return;
        //             }

        //             if (cboNT_DV_BAN.Properties.Items.Count > 0)
        //             {

        //                 for (int i = 0; i < cboNT_DV_BAN.Properties.Items.Count; i++)
        //                 {
        //                     if (cboNT_DV_BAN.Properties.Items[i].CheckState == CheckState.Unchecked)
        //                         continue;
        //                     dt_kieu_nt_ban.Rows.Add(Convert.ToInt32(cboNT_DV_BAN.Properties.Items[i].Value.ToString()));
        //                 }
        //             }
        //             if (cboNT_DV_TC.Properties.Items.Count > 0)
        //             {

        //                 for (int i = 0; i < cboNT_DV_TC.Properties.Items.Count; i++)
        //                 {
        //                     if (cboNT_DV_TC.Properties.Items[i].CheckState == CheckState.Unchecked)
        //                         continue;
        //                     dt_kieu_nt_tc.Rows.Add(Convert.ToInt32(cboNT_DV_TC.Properties.Items[i].Value.ToString()));
        //                 }
        //             }
        //             if ((dt_kieu_nt_tc.Rows.Count == 0 || dt_kieu_nt_ban.Rows.Count == 0) && vloaihd_id == LoaiHopDong.DAT_MOI 
        //                 && (pLoaihinhTB == 257  || pLoaihinhTB == 258 || pLoaihinhTB == 273))
        //             {
        //                 Message_Box.ShowError("Chưa chọn kiểu nghiệm thu cho loại kênh quốc tế");
        //                 return;
        //             }
        //         }
        //         catch (Exception exp)
        //         {
        //             Message_Box.ShowError("Có lỗi khi chọn thông số kênh quốc tế: " + exp.Message);

        //         }
        //     }


        //     Capnhat = true;
        //     if (cboLoaikenhBan.Items.Count > 0)
        //         thongso_Ban.loaikenh_id = Convert.ToInt32(cboLoaikenhBan.value.ToString());
        //     if (cboMuccuoctbBan.Items.Count > 0)
        //         thongso_Ban.muccuoctb_id = Convert.ToInt32(cboMuccuoctbBan.value.ToString());
        //     thongso_Ban.trangbi_id = Convert.ToInt32(cboTrangbiBan.value.ToString());
        //     thongso_Ban.tocdo_id = Convert.ToInt32(cboTocdoBan.value.ToString());
        //     thongso_Ban.chuquan_id = Convert.ToInt32(cboChuquanBan.value.ToString());
        //     thongso_Ban.chuquanld_id = Convert.ToInt32(cboChuQuanLD_Ban.value.ToString());
        //     thongso_Ban.doituong_id = Convert.ToInt32(cboDoituongBan.value.ToString());
        //     thongso_Ban.thietbidc_id = Convert.ToInt32(cboThietbiDauBan.value.ToString());
        //     thongso_Ban.thietbidc_id_cuoi = Convert.ToInt32(cboThietbiCuoiBan.value.ToString());

        //     thongso_TC.kieuld_id = Convert.ToInt32(cboKieuLD.value.ToString());

        //     if (cboLoaikenhTC.Items.Count > 0)
        //         thongso_TC.loaikenh_id = Convert.ToInt32(cboLoaikenhTC.value.ToString());
        //     if (cboMuccuoctbTC.Items.Count > 0)
        //         thongso_TC.muccuoctb_id = Convert.ToInt32(cboMuccuoctbTC.value.ToString());
        //     thongso_TC.trangbi_id = Convert.ToInt32(cboTrangbiTC.value.ToString());
        //     thongso_TC.tocdo_id = Convert.ToInt32(cboTocdoTC.value.ToString());
        //     thongso_TC.chuquan_id = Convert.ToInt32(cboChuquanTC.value.ToString());
        //     thongso_TC.chuquanld_id = Convert.ToInt32(cboChuQuanLD_TC.value.ToString());
        //     thongso_TC.doituong_id = Convert.ToInt32(cboDoituongTC.value.ToString());
        //     if (cboThietbiDauTC.value != null)
        //         thongso_TC.thietbidc_id = Convert.ToInt32(cboThietbiDauTC.value.ToString());
        //     if (cboThietbiCuoiTC.value != null)
        //         thongso_TC.thietbidc_id_cuoi = Convert.ToInt32(cboThietbiCuoiTC.value.ToString());

        //     //region thông số kênh qt

        //     if (cboMHKN_TC.EditValue.ToString() != "" && cboMHKN_TC.EditValue.ToString() != "0")
        //         thongso_Ban.mhkn_id = Convert.ToInt32(cboMHKN_TC.EditValue);
        //     else
        //         thongso_Ban.mhkn_id = 0;
        //     if (cboMHKN_TC.EditValue.ToString() != "" && cboMHKN_TC.EditValue.ToString() != "0")
        //         thongso_TC.mhkn_id = Convert.ToInt32(cboMHKN_BAN.EditValue);
        //     else
        //         thongso_TC.mhkn_id = 0;

        //     if (cboTocDoPOP_BAN.EditValue.ToString() != "" && cboTocDoPOP_BAN.EditValue.ToString() != "0")
        //         thongso_Ban.tocdo_pop = Convert.ToInt32(cboTocDoPOP_BAN.EditValue);
        //     else
        //         thongso_Ban.tocdo_pop = 0;
        //     if (cboTocDoPOP_TC.EditValue.ToString() != "" && cboTocDoPOP_TC.EditValue.ToString() != "0")
        //         thongso_TC.tocdo_pop = Convert.ToInt32(cboTocDoPOP_TC.EditValue);
        //     else
        //         thongso_TC.tocdo_pop = 0;

        //     if (cboGiaoDienKH_BAN.EditValue.ToString() != "" && cboGiaoDienKH_BAN.EditValue.ToString() != "0")
        //         thongso_Ban.gdkn_id = Convert.ToInt32(cboGiaoDienKH_BAN.EditValue);
        //     else
        //         thongso_Ban.gdkn_id = 0;
        //     if (cboGiaoDienKH_TC.EditValue.ToString() != "" && cboGiaoDienKH_TC.EditValue.ToString() != "0")
        //         thongso_TC.gdkn_id = Convert.ToInt32(cboGiaoDienKH_TC.EditValue);
        //     else
        //         thongso_Ban.gdkn_id = 0;


        //     thongso_Ban.kieu_nghiemthu = dt_kieu_nt_ban;
        //     thongso_TC.kieu_nghiemthu = dt_kieu_nt_tc;

        //     thongso_Ban.cap_chinh = txtCapChinh_BAN.Text;
        //     thongso_TC.cap_chinh = txtCapChinh_TC.Text;

        //     thongso_Ban.cap_duphong = txtCapDP_BAN.Text;
        //     thongso_TC.cap_duphong = txtCapDP_TC.Text;

        //     thongso_Ban.auto_nego = txtAuto_Nego_BAN.Text;
        //     thongso_TC.auto_nego = txtAuto_Nego_TC.Text;

        //     thongso_Ban.mtu_size = txtMTU_Size_BAN.Text;
        //     thongso_TC.mtu_size = txtMTU_Size_TC.Text;

        //     thongso_Ban.sncp = txtSNCP_BAN.Text;
        //     thongso_TC.sncp = txtSNCP_TC.Text;
        //     //endregion
        //     Close();
        // }

        // private void cboKieuLD_SelectedIndexChanged(object sender, EventArgs e)
        // {
        //     if (!kt_load)
        //     {
        //         //bangts_tc.HT_CONGNGHE_KIEULD_TC(tinhtc, Convert.ToInt32(cboKieuLD.value.ToString()), CBO);
        //         bangts_tc.Lay_DS_MucCuoc_TB(tinhtc, cboMuccuoctbTC, 0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongTC.value), ngay, Convert.ToInt32(cboTrangbiTC.value), Convert.ToInt32(cboTocdoTC.value), 0);
        //     }
        // }

        // private void cboTrangbiTC_SelectedIndexChanged(object sender, EventArgs e)
        // {
        //     if (!kt_load)
        //     {
        //         bangts_tc.Lay_DS_MucCuoc_TB(tinhtc, cboMuccuoctbTC, 0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongTC.value), ngay, Convert.ToInt32(cboTrangbiTC.value), Convert.ToInt32(cboTocdoTC.value), 0);

        //     }
        // }

        // private void cboDoituongTC_SelectedIndexChanged(object sender, EventArgs e)
        // {
        //     if (!kt_load)
        //     {
        //         bangts_tc.Lay_DS_MucCuoc_TB(tinhtc, cboMuccuoctbTC, 0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongTC.value), ngay, Convert.ToInt32(cboTrangbiTC.value), Convert.ToInt32(cboTocdoTC.value), 0);
        //     }
        // }

        // private void cboTrangbiBan_SelectedIndexChanged(object sender, EventArgs e)
        // {
        //     if (!kt_load)
        //     {
        //         //bangts_tc.Lay_DS_MucCuoc_TB(tinhtc, cboMuccuoctbBan, 0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongBan.value.ToString()), ngay, Convert.ToInt32(cboTrangbiBan.value.ToString()), Convert.ToInt32(cboTocdoBan.value.ToString()), 0, 1);
        //         DataSet ds = new DataSet();
        //         ds = tcdanhba.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongBan.value), 0, 0, ngay, Convert.ToInt32(cboTrangbiBan.value));
        //         if (ds.Tables[0].Rows.Count > 0)
        //         {
        //             cboMuccuoctbBan.DataSource = ds.Tables[0];
        //             cboMuccuoctbBan.ValueMember = "mucuoctb_id";
        //             cboMuccuoctbBan.DisplayMember = "tenmuccuoc";
        //         }
        //         else
        //             cboMuccuoctbBan.DataSource = null;
        //     }
        // }

        // private void cboDoituongBan_SelectedIndexChanged(object sender, EventArgs e)
        // {
        //     if (!kt_load)
        //     {
        //         //bangts_tc.Lay_DS_MucCuoc_TB(tinhtc, cboMuccuoctbBan, 0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongBan.value.ToString()), ngay, Convert.ToInt32(cboTrangbiBan.value.ToString()), Convert.ToInt32(cboTocdoBan.value.ToString()), 0, 1);                
        //         DataSet ds = new DataSet();
        //         ds = tcdanhba.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongBan.value), 0, 0, ngay, Convert.ToInt32(cboTrangbiBan.value));
        //         if (ds.Tables[0].Rows.Count > 0)
        //         {
        //             cboMuccuoctbBan.DataSource = ds.Tables[0];
        //             cboMuccuoctbBan.ValueMember = "mucuoctb_id";
        //             cboMuccuoctbBan.DisplayMember = "tenmuccuoc";
        //         }
        //         else
        //             cboMuccuoctbBan.DataSource = null;
        //     }
        // }

        // private void cboTocdoBan_SelectedIndexChanged(object sender, EventArgs e)
        // {
        //     if (!kt_load)
        //     {
        //         bool kt_tocdo_tc = false;
        //         for (int i = 0; i < cboTocdoTC.Items.Count; i++)
        //             if (((System.Data.DataRowView)(cboTocdoTC.Items[i])).Row[1].ToString() == ((System.Data.DataRowView)(cboTocdoBan.SelectedItem)).Row[1].ToString())
        //             {
        //                 cboTocdoTC.SelectedIndex = i;
        //                 kt_tocdo_tc = true;
        //                 break;
        //             }
        //         if (!kt_tocdo_tc)
        //             Message_Box.ShowWarning("Tốc độ không có bên tỉnh thi công !");

        //         //bangts_tc.Lay_DS_MucCuoc_TB(tinhtc, cboMuccuoctbBan, 0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongBan.value.ToString()), ngay, Convert.ToInt32(cboTrangbiBan.value.ToString()), Convert.ToInt32(cboTocdoBan.value.ToString()), 0, 1);                
        //         DataSet ds = new DataSet();
        //         ds = tcdanhba.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongBan.value), 0, 0, ngay, Convert.ToInt32(cboTrangbiBan.value));
        //         if (ds.Tables[0].Rows.Count > 0)
        //         {
        //             cboMuccuoctbBan.DataSource = ds.Tables[0];
        //             cboMuccuoctbBan.ValueMember = "mucuoctb_id";
        //             cboMuccuoctbBan.DisplayMember = "tenmuccuoc";
        //         }
        //         else
        //             cboMuccuoctbBan.DataSource = null;
        //     }
        // }

        // private void cboTocdoTC_SelectedIndexChanged(object sender, EventArgs e)
        // {
        //     if (!kt_load)
        //     {
        //         //bangts_tc.Lay_DS_MucCuoc_TB(tinhtc, cboMuccuoctbTC, 0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongTC.value.ToString()), ngay, Convert.ToInt32(cboTrangbiTC.value.ToString()), Convert.ToInt32(cboTocdoTC.value.ToString()), 0, 2);                
        //         DataSet ds = new DataSet();
        //         ds = tcdanhba.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, pLoaihinhTB, Convert.ToInt32(cboDoituongBan.value), 0, 0, ngay, Convert.ToInt32(cboTrangbiBan.value));
        //         if (ds.Tables[0].Rows.Count > 0)
        //         {
        //             cboMuccuoctbBan.DataSource = ds.Tables[0];
        //             cboMuccuoctbBan.ValueMember = "mucuoctb_id";
        //             cboMuccuoctbBan.DisplayMember = "tenmuccuoc";
        //         }
        //         else
        //             cboMuccuoctbBan.DataSource = null;
        //     }

        // }
    }
}
</script>

<style>

</style>