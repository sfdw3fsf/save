<template src="./index.html"></template>
<script>
    import frmDSTBCungDoiCap from '../Shared/frmDSTBCungDoiCap'
    import frmDSTBLapKem from '../Shared/frmDSTBLapKem'
    import frmDSTBCungDoiCap_KH_Khac from '../Shared/frmDSTBCungDoiCap_KH_Khac/frmDSTBCungDoiCap_KH_Khac.vue'

    export default {
        components: {
            frmDSTBCungDoiCap,
            frmDSTBLapKem,
            frmDSTBCungDoiCap_KH_Khac
        },
        props: ["parent"],
        data() {
            return {
                DichVuVienThong: {
                    IMS: 11
                },
                LOAI_DV: {
                    TRAM_VT: 5
                },
                btnMaTN_Ims: {
                    Enabled: true
                },
                txtMATN_IMS: {
                    Text: ""
                },
                txtMaDoiCap_IMS: {
                    Text: ""
                },
                cboTrangBiIMS: {
                    DataSource: [],
                    Value: null
                },
                dsComboDonVi: []
            }
        },
        computed: {
            idDichVu() {
                return parseInt(this.parent.parent.modelCbo.cboDichVuVT)
            },
            isActive() {
                return this.idDichVu == this.DichVuVienThong.IMS
            },
            idLoaiHinhTb() {
                return parseInt(this.parent.parent.modelCbo.cboLoaihinhTB)
            },
            idKieuLd() {
                return parseInt(this.parent.parent.modelCbo.cboKieuLD)
            },
            khachHangId() {
                return this.parent.parent.khachhang_id
            },
            hdkhId() {
                return this.parent.parent.hdkh_id
            },
            idDaiVt() {
                return this.parent.parent.modelCbo.cboDaiVT
            }
        },
        watch: {
            idKieuLd: async function() {
                if (this.isActive) {
                    await this.cboKieuLD_SelectedValueChanged()
                }
            },
            idLoaiHinhTb: async function() {
                if (this.isActive) {
                    await this.HT_COMBO_DONVI()
                }
            },
        },
        async mounted() {
            this.cboTrangBiIMS.DataSource = await this.lay_ds_trangbi()
            this.cboTrangBiIMS.Value = this.cboTrangBiIMS.DataSource[0].trangbi_id
        },
        methods: {
            async TaoDuLieu_HDBC_IMS(themmoi) {

                let rowHDBCIMS = {}
                let tinh_id = this.parent.parent.BanCheoData.Tinh_TC.tinhthicong_id

                if (!this.parent.parent.kenh_trang) {
                    let ds = await this.LAY_DS_DB_IMS_THEO_TBID(tinh_id, this.parent.parent.thuebao_tc_id)
                    let firstRow = {...ds[0]}
                    Object.keys(firstRow).forEach(key => rowHDBCIMS[key.toUpperCase()] = firstRow[key])
                }

                let thongso_tc = this.parent.parent.thongso_tc

                rowHDBCIMS.HDTB_ID = this.parent.parent.hdtb_id
                rowHDBCIMS.MATB_TN = thongso_tc.ma_tn

                let kieu = await this.lay_tt_kieu_ld(thongso_tc.kieuld_id)

                if (kieu == 1 || kieu == 2 || kieu == 4)
                {
                    if (!thongso_tc.ma_doicap)
                    {
                        this.$root.toastError("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.");
                        return;
                    }
                    rowHDBCIMS.MADOICAP = parseInt(thongso_tc.ma_doicap);
                }
                else //TH còn lại sinh mã đôi cáp
                {
                    if (themmoi == true)
                    {
                        rowHDBCIMS.MADOICAP = await this.get_key_madoicap(tinh_id);
                    }
                    else
                    {
                        rowHDBCIMS.MADOICAP = parseInt(thongso_tc.ma_doicap);
                    }
                }

                rowHDBCIMS.TRANGBI_ID = thongso_tc.trangbi_id;
                rowHDBCIMS.TUYENCAP = "";
                rowHDBCIMS.LIENTU = "";
                rowHDBCIMS.DOICAP_P = 0;
                rowHDBCIMS.HOPCAP_P = "";
                rowHDBCIMS.TUCAP_P = "";
                rowHDBCIMS.CAP_GOC = "";
                rowHDBCIMS.CAP_P = "";
                rowHDBCIMS.DOICAP_GOC = 0;
                rowHDBCIMS.TUCAP_GOC = "";
                rowHDBCIMS.CULY = "0";

                let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID)

                if (KetCuoi_ID > 0) {
                    rowHDBCIMS.KETCUOI_ID = KetCuoi_ID;
                }

                return { dsHDBCIMS: [rowHDBCIMS] }
            },
            async TaoDuLieu_HDTB_IMS(themmoi) {
                
                let rowHDTB_IMS = {}
                let ds = await this.LAY_DS_DB_IMS_THEO_TBID(this.$root.token.getPhanVungID(), this.parent.parent.thuebao_id)
                
                let firstRow = {...ds[0]}
                Object.keys(firstRow).forEach(key => rowHDTB_IMS[key.toUpperCase()] = firstRow[key])


                rowHDTB_IMS.HDTB_ID = this.parent.parent.hdtb_id
                rowHDTB_IMS.MATB_TN = this.txtMATN_IMS.Text;

                let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

                if (kieu == 1 || kieu == 2 || kieu == 4)
                {
                    if (!this.txtMaDoiCap_IMS.Text)
                    {
                        this.$root.toastError("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.");
                        return;
                    }
                    rowHDTB_IMS.MADOICAP = parseInt(this.txtMaDoiCap_IMS.Text);
                }
                else //TH còn lại sinh mã đôi cáp
                {
                    if (themmoi == true)
                    {
                        rowHDTB_IMS.MADOICAP = await this.get_key_madoicap();
                        this.txtMaDoiCap_IMS.Text = rowHDTB_IMS.MADOICAP;
                    }
                    else
                    {
                        rowHDTB_IMS.MADOICAP = parseInt(this.txtMaDoiCap_IMS.Text);
                    }
                }

                rowHDTB_IMS.TRANGBI_ID = parseInt(this.cboTrangBiIMS.Value);
                rowHDTB_IMS.TUYENCAP = "";
                rowHDTB_IMS.LIENTU = "";
                rowHDTB_IMS.DOICAP_P = 0;
                rowHDTB_IMS.HOPCAP_P = "";
                rowHDTB_IMS.TUCAP_P = "";
                rowHDTB_IMS.CAP_GOC = "";
                rowHDTB_IMS.CAP_P = "";
                rowHDTB_IMS.DOICAP_GOC = 0;
                rowHDTB_IMS.TUCAP_GOC = "";
                rowHDTB_IMS.CULY = "0";

                let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID)

                if (KetCuoi_ID > 0) {
                    rowHDTB_IMS.KETCUOI_ID = KetCuoi_ID;
                }

                let dsHDTBDV = []

                for (let i = 0; i < this.dsComboDonVi.length; i++)
                {
                    let item = this.dsComboDonVi[i]
                    let row = {
                        HDTB_ID: this.parent.parent.hdtb_id,
                        LOAIDV_ID: parseInt(item.loaiDonVi),
                        DONVI_ID: parseInt(this.$refs[item.key][0].value),
                        KIEUDV_ID: 2
                    }
                    dsHDTBDV.push(row);
                }

                return {
                    dsHDTB_IMS: [rowHDTB_IMS],
                    dsHDTBDV: dsHDTBDV
                }
            },
            async get_key_madoicap(tinh_id) {
                try {
                    tinh_id = !tinh_id ? this.$root.token.getPhanVungID() : tinh_id
                    let response = await this.$root.context.get(`/web-bancheo/chuyendich-bancheo-dichvu/get_key_madoicap?tinh_id=${tinh_id}`)
                    return !response.data ? null : parseInt(response.data)
                } catch (err) {
                    return null
                }
            },
            async LAY_DS_DB_IMS_THEO_TBID(tinh_id, thuebao_id) {
                try {
                    let response = await this.$root.context.post(`/web-bancheo/tracuu/lay_ds_db_ims_theo_tbid`, { tinhthicong_id: tinh_id, thuebao_id })
                    return !response.data ? [] : response.data
                } catch (err) {
                    return []
                }
            },
            async LAY_DS_HDTB_IMS_THEO_HDTB_ID(hdtb_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_hdtb_ims_theo_hdtb_id?hdtb_id=${hdtb_id}`)
                    return !response.data ? [] : response.data
                } catch (err) {
                    return []
                }
            },
            async HienThiTTMoRongHD(hdtb_id) { 

                let ds = await this.LAY_DS_HDTB_IMS_THEO_HDTB_ID(hdtb_id)

                if (ds.length <= 0) {
                    return
                }

                this.txtMATN_IMS.Text = ds[0]["matb_tn"];
                this.txtMaDoiCap_IMS.Text = ds[0]["madoicap"];

                if (!!ds[0]["trangbi_id"] || parseInt(ds[0]["trangbi_id"]) == 0)
                    this.cboTrangBiIMS.Value = ds[0]["trangbi_id"];

                await this.HT_COMBO_DONVI();

                let dsDonVi = await this.lay_ds_donvi_cua_hopdong(hdtb_id);

                setTimeout(() => {
                    if (dsDonVi.length > 0)
                    {
                        for (let i = 0; i < this.dsComboDonVi.length; i++)
                        {
                            let cbo = this.dsComboDonVi[i]
                            let donVi = dsDonVi.find(item => item.loaidv_id == parseInt(cbo.loaiDonVi));

                            if (donVi)
                            {
                                this.$refs[cbo.key][0].value = donVi["donvi_id"];
                            }
                        }
                    }
                }, 300)                
            },
            async lay_ds_donvi_cua_hopdong(hdtb_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_hopdong`, { hdtb_id })
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async HienThiTTMoRongTB(thuebao_id) {
                
                let ds = await this.LAY_DS_DB_IMS_THEO_TBID(this.$root.token.getPhanVungID(), thuebao_id)

                if (ds.length <= 0) {
                    return
                }

                if (!!ds[0]["trangbi_id"]) {
                    this.cboTrangBiIMS.Value = ds[0]["trangbi_id"] + "";
                }
                            
                this.txtMaDoiCap_IMS.Text = ds[0]["madoicap"];

                await this.HT_COMBO_DONVI();

                //Lấy ra các đơn vị được chọn
                let dsDonVi = await this.lay_ds_donvi_cua_tbao(thuebao_id);

                setTimeout(() => {
                    if (dsDonVi.length > 0)
                    {
                        for (let i = 0; i < this.dsComboDonVi.length; i++)
                        {
                            let cbo = this.dsComboDonVi[i]
                            let donVi = dsDonVi.find(item => item.loaidv_id == parseInt(cbo.loaiDonVi));

                            if (donVi)
                            {
                                this.$refs[cbo.key][0].value = donVi["donvi_id"];
                            }
                        }
                    }
                }, 300)
            },
            async lay_ds_donvi_cua_tbao(thuebao_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_tbao`, { thuebao_id })
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async cboKieuLD_SelectedValueChanged() {

                await this.HT_COMBO_DONVI()

                if (!this.idKieuLd) {
                    return
                }

                let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

                if (kieu == 1 || kieu == 2 || kieu == 4) {
                    this.btnMaTN_Ims.Enabled = true
                    this.txtMATN_IMS.Text = ""
                } else {
                    this.btnMaTN_Ims.Enabled = false
                    this.txtMATN_IMS.Text = ""
                }
            },
            newComboDonVi(loaiDonVi) {
                return {
                    key: `combo-id-${loaiDonVi.loaidv_dich_id}`,
                    loaiDonVi: loaiDonVi.loaidv_dich_id,
                    template: `
                        <div class="info-row">
                            <div class="key">${loaiDonVi.ten_loaidv}</div>
                            <div class="value">
                                <SelectExt
                                    v-model="value" 
                                    :dataSource="dataSource" 
                                    dataValueField="donvi_id"
                                    dataTextField="ten_dv" />
                            </div>
                        </div>
                    `,
                    data() {
                        return {
                            value: null,
                            dataSource: []
                        }
                    },
                    mounted() {
                        if (loaiDonVi.dsDonVi.length > 0) {
                            this.dataSource = [...loaiDonVi.dsDonVi]
                            this.value = this.dataSource[0].donvi_id
                        }
                    }
                }
            },
            async HT_COMBO_DONVI() {

                if (!this.idLoaiHinhTb && !this.idKieuLd) {
                    this.dsComboDonVi = []
                    return
                }

                let dsLoaiDonVi = await this.lay_ds_loai_donvi({
                    dichvu_id: this.idDichVu,
                    loaihinh_tb: this.idLoaiHinhTb,
                    kieu_ld: this.idKieuLd
                })

                let dsComboDonVi = []

                for (let i = 0; i < dsLoaiDonVi.length; i++) {

                    let loaiDonVi = dsLoaiDonVi[i]

                    if (loaiDonVi["loaidv_dich_id"] == this.LOAI_DV.TRAM_VT) {
                        loaiDonVi.dsDonVi = await this.lay_ds_donvi_tramtc({
                            loaidv_id: loaiDonVi["loaidv_dich_id"],
                            donvi_ql_id: this.idDaiVt
                        })
                    } else {
                        loaiDonVi.dsDonVi = await this.lay_ds_donvi(loaiDonVi["loaidv_dich_id"])
                    }

                    dsComboDonVi.push(this.newComboDonVi(loaiDonVi))
                }

                this.dsComboDonVi = dsComboDonVi
            },
            async btnMaTN_Ims_Click() {

                let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

                if (kieu == 2) { //Lắp mới trên đường có sẵn

                    if (parseInt(this.khachHangId) > 0) {

                        let frm = this.$refs.frmDSTBCungDoiCap
                        frm.khachhang_id = parseInt(this.khachHangId)
                        frm.kieuld_id = parseInt(this.idKieuLd)
                        frm.loaitb_id = parseInt(this.idLoaiHinhTb)
                        let data = await frm.show()
                        
                        if (data.chapnhan) {
                            this.txtMATN_IMS.Text = data.ma_tn
                            this.txtMaDoiCap_IMS.Text = data.ma_doicap
                        }
                    } else {
                        this.$root.toastError("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !")
                    }
                }
                else if (kieu == 1) { //Lắp kèm 
                
                    if (parseInt(this.hdkhId) > 0) {

                        let frm = this.$refs.frmDSTBLapKem
                        frm.hdkh_id = parseInt(this.hdkhId)
                        frm.kieuld_id = parseInt(this.idKieuLd)
                        let data = await frm.show()
                        
                        if (data.chapnhan) {
                            this.txtMATN_IMS.Text = data.ma_tn
                            this.txtMaDoiCap_IMS.Text = data.ma_doicap
                        }
                    } else {
                        this.$root.toastError("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !");
                    }
                }
                else if (kieu == 4) { // Lắp trên đường có sẵn của khách hàng khác
                    let frm = this.$refs.frmDSTBCungDoiCap_KH_Khac
                    frm.kieuld_id = parseInt(this.idKieuLd)
                    frm.loaitb_id = parseInt(this.idLoaiHinhTb)
                    let data = await frm.show()

                    if (data.chapnhan) {
                        this.txtMATN_IMS.Text = data.ma_tn;
                        this.txtMaDoiCap_IMS.Text = data.ma_doicap;
                    }
                }
            },
            async lay_ds_donvi_tramtc(data) {
                try {
                    let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_tramtc`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_donvi(loaidv_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi?loaidv_id=${loaidv_id}`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_loai_donvi(data) {
                try {
                    let response = await this.$root.context.post(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_loai_donvi`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_trangbi() {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_trangbi`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_tt_kieu_ld(kieuld_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_tt_kieu_ld?kieuld_id=${kieuld_id}`)
                    return response.data.kieu
                } catch (err) {
                    return null
                }
            }
        }
    }
</script>
