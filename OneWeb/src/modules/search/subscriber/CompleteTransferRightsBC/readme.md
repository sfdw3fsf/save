# Mô tả
clone code từ src/modules/EXTS/HOPDONG/HoanThienHoSoChuyenQuyen và chỉnh sửa

1. ham frmHTHSChuyenQuyen_Load
    frmHTHSChuyenQuyen_Load: async function(){
        try {
            this.kieugoi_id = 26
            await this.HienThiGiaoDien(this.kieugoi_id)//this.kieugoi_id
            this.modelDateTime.dtpNgayLHD = DateTimeLib.toDateDisplay(new Date())
            this.SetButton(-1);
            const promises = []
            promises.push(this.HT_DichVuVT_Combobox());
            promises.push(this.HT_DonVi_loaidv_Combobox());
            promises.push(this.HT_LoaiGT_Combobox())
            promises.push(this.HT_DoiTuong_Combobox());
            promises.push(this.HT_NganHang_Combobox_LD());
            promises.push(this.HT_HinhThucTT_Combobox());
            promises.push(this.Lay_ds_loaikh())
            promises.push(this.HT_Tocdo_Adsl_Combobox());
            promises.push(this.HT_MucCuoc_Adsl_Combobox());
            promises.push(this.HT_DonVi_loaidv_Combobox_TT());
            // ///TuấnNA thêm code hiển thị một số thông tin mới thêm trên form -- Ngày 08/08/2010
            promises.push(this.HT_DanToc_Combobox());
            promises.push(this.HT_QuocTich_Combobox());
            promises.push(this.HT_NganhNgheLoc_Combobox());
            promises.push(this.HT_NganHang_Combobox_KH());
            promises.push(this.HT_DT_VinaPhone_Combobox());
            promises.push(this.HT_GoiCuoc_DD_Combobox(1));
            // xu ly bat dong bo de tang toc load trang
            await Promise.all(promises)


            this.modelDateTime.dtpNgayYC = DateTimeLib.toDateDisplay(new Date())

        } catch (error) {
            this.$root.$toast.error("" +error.response.data.message_detail)
        }
    },

2. ham HoanThienHS
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
                // if (kt_taobang == 0)
                // {
                //     this.$root.$toast.error("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !")
                //     return;
                // }
                let check = this.KiemTraDL_ChuyenVaoDB()
                if (!check) return;

                // form bán chéo bỏ đồng bộ backend và gọi hàn gôp ben duoi de thay the
                await this.fn_hths_chuyenquyen_bancheo(this.frm.ttThueBao.hdtbId);

                if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN2B_CQ
                || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id == Enum.LoaiHinhTB.ISDN30B_CQ
                || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_2M
                || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_THUONG
                || this.inputValue.loaitb_id == Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
                let vhdtb_id = 0;
                for (let i = 0; i < this.dtThueBao.length; i++) {
                    if (this.dtThueBao[i].thuebao_cha_id == this.inputValue.thuebao_id) {
                    vhdtb_id = this.dtThueBao[i].hdtb_id
                    await this.fn_hths_chuyenquyen_bancheo(vhdtb_id);
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
            console.log(exp)
            //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
        } finally {
            this.loading(false)
        }
    },

    fn_hths_chuyenquyen_bancheo: async function(hdtb_id){
        let ip = await this.getIp()
        let data = {
                p_hdtb_id: hdtb_id,
                p_luong_id: 0,
                p_nhanvien_id: this.$root.token.getNhanVienID(),
                p_may_cn: ip,
                p_nguoi_cn: this.$root.token.getUserName()
            }
        let rs = await this.$root.context.post('/web-hopdong/hoanthienhs-chuyenquyenbc/fn_hths_chuyenquyen_bancheo', data)
        return rs.data
    },

3. enable nut tsbtnChuyenDB: search this.tsbtnChuyenDB = true co 2 cho can sua
-- ham HienThiGiaoDien
    HienThiGiaoDien: function(kieugoi_id){
        this.inputValue.tthd_id = kieugoi_id

        this.tsbtnChuyenDB = false;
        this.tsbtnHuyBo = false;

        if (kieugoi_id == Enum.TrangThaiHD.HTHS_BANCHEO)
        {
            this.tsbtnHuyBo = true;
            this.tsbtnChuyenDB = true;
            console.log("🚀 ~ file: index.vue ~ line 2662 ~ HienThiGiaoDien:function ~ kieugoi_id", kieugoi_id)
        }
    },
-- ham HienThiTTHopDongTB_V2
    if (ds[0].TTHD_ID == Enum.TrangThaiHD.HTHS_BANCHEO)
        this.tsbtnChuyenDB = true;
    else
        this.tsbtnChuyenDB = false;


4. data va add component breadcrumb
    header: {
        title: 'Hoàn thiện hồ sơ chuyển quyền (Bán chéo)',
        list: [
            { name: 'Phát triển thuê bao', link: {name: 'Ui.cards'} },
            { name: 'Hợp đồng',link: {name: 'Ui.buttons'} },
            { name: 'Hoàn thiện hồ sơ',link: {name: 'Ui.buttons'} }
        ]
    },
