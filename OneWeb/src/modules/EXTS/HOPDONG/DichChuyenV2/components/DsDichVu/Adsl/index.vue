<template src="./index.html"></template>
<script>
    import frmDSTBCungDoiCap from '../CD/frmDSTBCungDoiCap'
    import frmDSTBLapKem from '../CD/frmDSTBLapKem'
    import frmDSTBCungDoiCap_KH_Khac from '../../../../ChuyenDichBC/Components/DsDichVu/Shared/frmDSTBCungDoiCap_KH_Khac/frmDSTBCungDoiCap_KH_Khac'
    import Enum from '../../../Enum.js'
    export default {
        components: {
            frmDSTBCungDoiCap,
            frmDSTBLapKem,
            frmDSTBCungDoiCap_KH_Khac
        },
        props: ["idLoaihinhTb", "idKieuLd", "parent"],
        data() {
            return {
                btnMaTN_ADSL: {
                    Enabled: true
                },
                txtMaTN_ADSL: {
                    Text: null,
                    Enabled: true
                },
                cboTrangBiADSL: {
                    DataSource: [],
                    Value: null
                },
                cboGoiCuocAdsl: {
                    DataSource: [],
                    Value: null
                },
                cboMucCuoc: {
                    DataSource: [],
                    Value: null
                },
                cboLoaiTBAdsl: {
                    DataSource: [],
                    Value: null
                },
                txtEmailAdsl: {
                    Text: null
                },
                chkDoiTac_ADSL: {
                    Checked: false
                },
                cboDoiTac_ADSL: {
                    DataSource: [],
                    Value: null,
                    Enabled: true
                },
                txtCuocDT_Adsl: {
                    Text: 0,
                    Enabled: true
                },
                txtMaDoiCap_ADSL: {
                    Text: null
                },
                dsComboDonVi: [],
                _tocdo_id: null,
                ckTuCaiADSL: {
                    Checked: false
                },
                kt_adsl: true
            }
        },
        computed: {
            thueBaoId() {
                return this.parent.inputValue.thuebao_id
            },
            khachHangId() {
                return this.parent.inputValue.khachhang_id
            },
            hdkhId() {
                return this.parent.inputValue.hdkh_id
            },
            idDaiVt() {
                return this.parent.modelCbo.cboDaiVT
            },
            phuongld_id() {
                return this.parent.inputValue.phuongld_id
            },
            phold_id() {
                return this.parent.inputValue.phold_id 
            },
            apld_id() {
                return this.parent.inputValue.apld_id 
            },
            khuld_id() {
                return this.parent.inputValue.khuld_id 
            },
            dacdiemld_id() {
                return this.parent.inputValue.dacdiemld_id 
            },
            ht_loc_cbo_tram() {
                return this.parent.inputValue.ht_loc_cbo_tram
            }
        },
        watch: {
            "cboTrangBiADSL.Value": function(value) {
                this.cboTrangBiADSL_SelectedValueChanged(value)
            },
            "cboGoiCuocAdsl.Value": function(value) {
                this.cboGoiCuocAdsl_SelectedValueChanged(value)
            },
            "cboMucCuoc.Value": function(value) {
                this.cboMucCuoc_SelectedValueChanged(value)
            },
            idLoaihinhTb: async function(value) {

                if (!value) {
                    this.cboGoiCuocAdsl.DataSource = []
                    return
                }

                this.cboGoiCuocAdsl.DataSource = await this.lay_ds_tocdo_adsl(value)

                if (this.cboGoiCuocAdsl.DataSource.length > 0) {

                    if (!!this.cboGoiCuocAdsl.Value) {

                        let exist = this.cboGoiCuocAdsl.DataSource.some(item => item.tocdo_id == parseInt(this.cboGoiCuocAdsl.Value))

                        if (!exist) {
                            this.cboGoiCuocAdsl.Value = this.cboGoiCuocAdsl.DataSource[0].tocdo_id
                        }
                    } else {
                        this.cboGoiCuocAdsl.Value = this.cboGoiCuocAdsl.DataSource[0].tocdo_id
                    }
                }
            },
            idKieuLd: async function(value) {

                if (!value) {
                    return
                }

                let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

                if (kieu == 1 || kieu == 2 || kieu == 4) {
                    this.btnMaTN_ADSL.Enabled = true
                    this.txtMaTN_ADSL.Enabled = false
                } else {
                    this.btnMaTN_ADSL.Enabled = false
                    this.txtMaTN_ADSL.Enabled = false
                }

                this.txtMaTN_ADSL.Text = ""

                if (!this.parent.inputValue.kt_load) {
                    if (this.parent.inputValue.load_ds_donvi) {
                        await this.HT_COMBO_DONVI()
                    }
                }
            },
            idDaiVt: async function (value) {
                if (!value) {
                    this.dsComboDonVi = []
                    return
                }

                await this.HT_COMBO_DONVI()
            },
        },
        async mounted() {
            this.cboTrangBiADSL.DataSource = await this.lay_ds_trangbi()
            this.cboTrangBiADSL.Value = this.cboTrangBiADSL.DataSource[0].trangbi_id

            this.cboMucCuoc.DataSource = await this.lay_ds_muccuoc()
            this.cboMucCuoc.Value = this.cboMucCuoc.DataSource[0].muccuoc_id

            this.cboLoaiTBAdsl.DataSource = await this.lay_ds_loai_tb()
            this.cboLoaiTBAdsl.Value = this.cboLoaiTBAdsl.DataSource[0].thietbidc_id

            this.cboDoiTac_ADSL.DataSource = await this.lay_ds_doitac()
        },
        methods: {
            async HienThiTTMoRongHD(hdtb_id) {

                let ds = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id)

                if (ds.length <= 0) {
                    return
                }

                this.txtMaTN_ADSL.Text = ds[0]["matb_tn"];
                this.parent.modelInput.txtSomayTN = ds[0]["so_dt"];
                this.parent.inputValue.SoMayTN = this.txtMaTN_ADSL.Text;
                this.txtEmailAdsl.Text = ds[0]["email"];

                if (parseInt(ds[0]["kh_cd"]) == 1)
                    this.ckTuCaiADSL.Checked = true;
                else
                    this.ckTuCaiADSL.Checked = false;
                
                this.cboGoiCuocAdsl.Value = parseInt(ds[0]["tocdo_id"]) + "";
                this._tocdo_id = parseInt(ds[0]["tocdo_id"]);
                this.cboMucCuoc.Value = parseInt(ds[0]["muccuoc_id"]) + "";
                this.cboLoaiTBAdsl.Value = parseInt(ds[0]["thietbidc_id"]) + "";
                this.txtMaDoiCap_ADSL.Text = ds[0]["madoicap"];
                
                if (!!ds[0]["trangbi_id"] || parseInt(ds[0]["trangbi_id"]) == 0)
                    this.cboTrangBiADSL.Value = ds[0]["trangbi_id"] + "";

                if (!!ds[0]["doitac_id"] || parseInt(ds[0]["doitac_id"]) == 0)
                {
                    this.chkDoiTac_ADSL.Checked = true;
                    this.cboDoiTac_ADSL.Value = parseInt(ds[0]["doitac_id"]) + "";

                    setTimeout(() => {
                        this.$refs['ref-combo-doitac'].$children[0].selectRowByRowData(parseInt(this.cboDoiTac_ADSL.Value))
                    }, 1000)

                    this.txtCuocDT_Adsl.Text = ds[0]["cuoc_doitac"];
                }
                else
                {
                    this.chkDoiTac_ADSL.Checked = false;
                    this.cboDoiTac_ADSL.Value = null;
                    this.txtCuocDT_Adsl.Text = "0";
                }

                await this.HT_COMBO_DONVI();

                //Lấy ra các đơn vị được chọn
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
            async LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_hdtb_adsl_theo_hdtb_id?hdtb_id=${hdtb_id}`)
                    return !response.data ? [] : response.data
                } catch (err) {
                    return []
                }
            },
            async HienThiTTMoRongTB(thuebao_id) {

                let ds = await this.LAY_DS_DB_ADSL_THEO_TBID(thuebao_id)

                if (ds.length <= 0) {
                    return
                }

                let data = ds[0]

                this.txtMaTN_ADSL.Text = null
                this.txtEmailAdsl.Text = data.email
                this.cboGoiCuocAdsl.Value = data.tocdo_id
                this._tocdo_id = data.tocdo_id
                this.cboMucCuoc.Value = data.muccuoc_id
                this.txtMaDoiCap_ADSL.Text = data.madoicap

                if (!!data.thietbidc_id) {
                    this.cboLoaiTBAdsl.Value = data.thietbidc_id
                } else {
                    this.cboLoaiTBAdsl.Text = "";
                }
                    
                if (!!data.trangbi_id) {
                    this.cboTrangBiADSL.Value = data["trangbi_id"]
                }
                    
                if (!!data.doitac_id) {
                    this.chkDoiTac_ADSL.Checked = true;
                    this.cboDoiTac_ADSL.Value = parseInt(data.doitac_id);

                    setTimeout(() => {
                        this.$refs['ref-combo-doitac'].$children[0].selectRowByRowData(parseInt(this.cboDoiTac_ADSL.Value))
                    }, 1000)
                } else {
                    this.chkDoiTac_ADSL.Checked = false;
                    this.cboDoiTac_ADSL.Value = null;
                    this.txtCuocDT_Adsl.Text = "0";
                }

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
            async LAY_DS_DB_ADSL_THEO_TBID(thuebao_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_db_adsl_theo_tbid?thuebao_id=${thuebao_id}`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async TaoDuLieu_HDTB_ADSL(themmoi) {
                
                this.kt_adsl = true

                let ds = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thueBaoId)

                if (ds.length <= 0) {
                    throw 'Không lấy được thông tin thuê bao'
                }

                let tbInfo = ds[0]
                let rowHDTB_ADSL = {}

                Object.getOwnPropertyNames(tbInfo).forEach(el => rowHDTB_ADSL[el.toUpperCase()] = tbInfo[el])
                rowHDTB_ADSL.EMAIL = this.txtEmailAdsl.Text
                rowHDTB_ADSL.SO_DT = this.txtMaTN_ADSL.Text
                rowHDTB_ADSL.KH_CD = this.ckTuCaiADSL.Checked ? 1 : 0
                rowHDTB_ADSL.TOCDO_ID = this._tocdo_id
                rowHDTB_ADSL.MUCCUOC_ID = parseInt(this.cboMucCuoc.Value)
                rowHDTB_ADSL.THIETBIDC_ID = parseInt(this.cboLoaiTBAdsl.Value)
                rowHDTB_ADSL.PROFILEDSL_ID = null
                rowHDTB_ADSL.MATB_TN = this.txtMaTN_ADSL.Text
                rowHDTB_ADSL.MA_LT = null
                rowHDTB_ADSL.MADOICAP = null
                rowHDTB_ADSL.TRANGBI_ID = parseInt(this.cboTrangBiADSL.Value)
                rowHDTB_ADSL.DOITAC_ID = null
                rowHDTB_ADSL.CUOC_DOITAC = null

                let tocDoInfo = await this.lay_tt_tocdo_adsl(this.cboGoiCuocAdsl.Value)
                rowHDTB_ADSL.PROFILEDSL_ID = !tocDoInfo ? null : tocDoInfo.profiledsl_id

                let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

                if (kieu == 1 || kieu == 2 || kieu == 4)
                {
                    if (!this.txtMaDoiCap_ADSL.Text)
                    {
                        this.$root.toastError("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập");
                        this.kt_adsl = false
                        return;
                    }

                    rowHDTB_ADSL.MADOICAP = parseInt(this.txtMaDoiCap_ADSL.Text);
                }
                else //TH còn lại sinh mã đôi cáp
                {
                    if (themmoi == true)
                    {
                        rowHDTB_ADSL.MADOICAP = await this.get_key_madoicap()
                        this.txtMaDoiCap_ADSL.Text = rowHDTB_ADSL.MADOICAP;
                    }
                    else
                        rowHDTB_ADSL.MADOICAP = parseInt(this.txtMaDoiCap_ADSL.Text);
                }

                if (this.chkDoiTac_ADSL.Checked)
                {
                    rowHDTB_ADSL.DOITAC_ID = parseInt(this.cboDoiTac_ADSL.Value);

                    if (!!this.txtCuocDT_Adsl.Text) 
                        rowHDTB_ADSL.CUOC_DOITAC = parseInt(this.txtCuocDT_Adsl.Text);
                    else
                        rowHDTB_ADSL.CUOC_DOITAC = 0;
                }
                else
                {
                    rowHDTB_ADSL.DOITAC_ID = null;
                    rowHDTB_ADSL.CUOC_DOITAC = 0;
                }

                let dsHDTBDV = []

                for (let i = 0; i < this.dsComboDonVi.length; i++)
                {
                    let item = this.dsComboDonVi[i]
                    let row = {
                        LOAIDV_ID: parseInt(item.loaiDonVi),
                        DONVI_ID: parseInt(this.$refs[item.key][0].value),
                        KIEUDV_ID: 2
                    }
                    dsHDTBDV.push(row);
                }

                return { rowHDTB_ADSL, dsHDTBDV }
            },
            async get_key_madoicap() {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/get_key_madoicap`)
                    return response.data.key
                } catch (err) {
                    return null
                }
            },
            async lay_tt_tocdo_adsl(tocdo_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_tt_tocdo_adsl`, {tocdo_id})
                    return response.data
                } catch (err) {
                    return null
                }
            },
            onSelectDoiTac(data) {
                this.cboDoiTac_ADSL.Value = !data ? null : data.doitac_id
            },
            chkDoiTac_ADSL_CheckedChanged(value) {

                if (this.chkDoiTac_ADSL.Checked) {
                    this.cboDoiTac_ADSL.Enabled = true;
                    this.txtCuocDT_Adsl.Enabled = true;
                } else {
                    this.cboDoiTac_ADSL.Enabled = false;
                    this.txtCuocDT_Adsl.Enabled = false;
                    this.txtCuocDT_Adsl.Text = "0";
                }
            },
            cboMucCuoc_SelectedValueChanged(value) {

                if (!value) {
                    this.dsComboDonVi = []
                    return
                }

                this.HT_COMBO_DONVI()
            },
            cboGoiCuocAdsl_SelectedValueChanged(value) {

                if (!value) {
                    this.dsComboDonVi = []
                    return
                }

                this.HT_COMBO_DONVI()
            },
            cboTrangBiADSL_SelectedValueChanged(value) {
                if (!!value) {
                    this.$emit("cboTrangBiADSL_SelectedValueChanged", { trangbi_id: value })
                }
            },
            async btnMaTN_ADSL_Click() {
                
                let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

                if (kieu == 2) { //Dịch chuyển trên đường có sẵn
                
                    if (parseInt(this.khachHangId) > 0) {

                        let frm = this.$refs['ref-ds-thue-bao-cung-cap']
                        frm.khachhang_id = parseInt(this.khachHangId)
                        frm.kieuld_id = parseInt(this.idKieuLd)
                        frm.loaitb_id = parseInt(this.idLoaihinhTb)
                        
                        let data = await frm.show()
                        
                        if (data.chapnhan) {
                            this.txtMaTN_ADSL.Text = data.ma_tn
                            this.txtMaDoiCap_ADSL.Text = data.ma_doicap
                        }
                    } else {
                        this.$root.toastError("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !")
                    }
                }
                else if (kieu == 1) { //Lắp kèm
                
                    if (parseInt(this.hdkhId) > 0) {

                        let frm = this.$refs['ref-ds-thue-bao-lap-kem']
                        frm.hdkh_id = parseInt(this.hdkhId)
                        frm.kieuld_id = parseInt(this.idKieuLd)
                        
                        let data = await frm.show()
                        
                        if (data.chapnhan) {
                            this.txtMaTN_ADSL.Text = data.ma_tn
                            this.txtMaDoiCap_ADSL.Text = data.ma_doicap
                        }
                    } else {
                        this.$root.toastError("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !");
                    }
                } 
                else if (kieu == 4) { // Lắp trên đường có sẵn của khách hàng khác
                    let frm = this.$refs.frmDSTBCungDoiCap_KH_Khac
                    frm.loaitb_id = parseInt(this.idLoaiHinhTb)
                    frm.kieuld_id = parseInt(this.idKieuLd)
                    let data = await frm.show()

                    if (data.chapnhan) {
                        this.txtMaTN_ADSL.Text = data.ma_tn
                        this.txtMaDoiCap_ADSL.Text = data.ma_doicap
                    }
                }
            },
            async HT_COMBO_DONVI() {

                let muccuoctn_id = 0

                if (parseInt(this.idLoaihinhTb) != Enum.LoaiHinhTB.INTERNET_MYTV
                    && parseInt(this.idLoaihinhTb) != Enum.LoaiHinhTB.INTERNET_SGTV
                    && parseInt(this.idLoaihinhTb) != Enum.LoaiHinhTB.HTVC_FIBERVNN_ANALOG
                    && parseInt(this.idLoaihinhTb) != Enum.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL
                    && parseInt(this.idLoaihinhTb) != Enum.LoaiHinhTB.BRCD_FBDT
                    && parseInt(this.idLoaihinhTb) != Enum.LoaiHinhTB.BRCD_THDT
                    && !!this.cboMucCuoc.Value)
                {
                    muccuoctn_id = parseInt(this.cboMucCuoc.Value);
                }

                let dsLoaiDonVi = await this.lay_ds_loai_donvi({
                    loaihinh_tb: this.idLoaihinhTb,
                    kieu_ld: this.idKieuLd,
                    tocdo_id: this.cboGoiCuocAdsl.Value,
                    muccuoc_id: muccuoctn_id
                })

                let dsComboDonVi = []

                for (let i = 0; i < dsLoaiDonVi.length; i++) {

                    let loaiDonVi = dsLoaiDonVi[i]

                    if (loaiDonVi["loaidv_dich_id"] == Enum.LOAI_DV.TRAM_VT || loaiDonVi["loaidv_dich_id"] == Enum.LOAI_DV.TRAM_CAP_PORT) {

                        let dsDonVi = []

                        if (loaiDonVi["loaidv_dich_id"] == Enum.LOAI_DV.TRAM_VT) {
                            dsDonVi = await this.lay_ds_donvi_tramtc({
                                loaidv_id: loaiDonVi["loaidv_dich_id"],
                                donvi_ql_id: this.idDaiVt
                            })
                        }

                        if (!!this.phuongld_id && this.phuongld_id != 0) {

                            let ds = await this.lay_donvi_theo_loaidv_diachi_ld_v2(Enum.LOAI_DV.TRAM_VT, this.phuongld_id, this.phold_id, this.apld_id, this.khuld_id, this.dacdiemld_id)

                            if (ds.length <= 0)
                            {
                                ds = await this.lay_donvi_theo_loaidv_diachi_ld_v2(Enum.LOAI_DV.TRAM_VT, this.phuongld_id, this.phold_id, this.apld_id, 0, this.dacdiemld_id)
                                
                                if (ds.length <= 0)
                                {
                                    ds = await this.lay_donvi_theo_loaidv_diachi_ld_v2(Enum.LOAI_DV.TRAM_VT, this.phuongld_id, this.phold_id, 0, 0, this.dacdiemld_id);
                                    
                                    if (ds.length <= 0)
                                    {
                                        this.$root.toastError("Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!");
                                        
                                        if (!!this.ht_loc_cbo_tram)
                                        {
                                            dsDonVi = []
                                        }
                                    }
                                }
                            }
                            
                            if (ds.length > 0)
                            {
                                if (!!this.ht_loc_cbo_tram)
                                {
                                    dsDonVi = await this.lay_ds_donvi_tramtc({
                                        loaidv_id: 0,
                                        donvi_ql_id: parseInt(ds[0]["donvi_id"])
                                    })
                                }
                                else
                                {
                                    loaiDonVi.donvi_id = parseInt(ds[0]["donvi_id"])
                                }
                            }

                            loaiDonVi.dsDonVi = dsDonVi
                        }
                    } else {

                        loaiDonVi.dsDonVi = await this.lay_ds_donvi(loaiDonVi["loaidv_dich_id"])

                        if (loaiDonVi["loaidv_dich_id"] != Enum.LOAI_DV.TTKD_NGHIEMTHU) {

                            if (this.phuongld_id != 0) {

                                let ds = await this.lay_donvi_theo_loaidv_diachi_ld_v2(loaiDonVi["loaidv_dich_id"], this.phuongld_id, this.phold_id, this.apld_id, this.khuld_id, this.dacdiemld_id)

                                if (ds.length > 0) {
                                    loaiDonVi.donvi_id = ds[0].donvi_id
                                }
                            }
                        } else {
                            loaiDonVi.donvi_id = this.$root.token.getDonViID()
                        }
                    }

                    dsComboDonVi.push(this.newComboDonVi(loaiDonVi))
                }

                this.dsComboDonVi = dsComboDonVi
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

                            if (!!loaiDonVi.donvi_id && this.dataSource.some(x => x.donvi_id == loaiDonVi.donvi_id)) {
                                this.value = loaiDonVi.donvi_id
                            } else {
                                this.value = this.dataSource[0].donvi_id
                            }
                        }
                    }
                }
            },
            async lay_donvi_theo_loaidv_diachi_ld_v2(loaidv_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id) {
                try {
                    var data = {loaidv_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id}
                    var response = await this.$root.context.post('/web-hopdong/chuyendich-codinh/lay_donvi_theo_loaidv_diachi_ld_v2', data)
                    return response.data
                } catch (error) {
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
            async lay_ds_donvi_tramtc(data) {
                try {
                    let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_tramtc`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_loai_donvi(data) {
                try {
                    let response = await this.$root.context.post(`/web-hopdong/chuyendich-adsl/lay_ds_loai_donvi`, data)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_doitac() {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_doitac`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_loai_tb() {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_loai_tb`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_tocdo_adsl(loaitb_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_tocdo_adsl?loaitb_id=${loaitb_id}`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async lay_ds_muccuoc() {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_muccuoc`)
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
            },
            async HT_DonVi_TRAMTC_Combobox(cbo, DONVI_ID) {
                
                cbo = this.$refs[cbo.key][0];

                try {
                    var rs = await this.$root.context.post('/web-hopdong/chuyendich/ht_donvi_tramtc_combobox', {DONVI_ID})
                    cbo.dataSource = !rs.data ? [] : rs.data
                } catch (error) {
                    cbo.dataSource = []
                }

                if (cbo.dataSource.length > 0) {
                    cbo.value = cbo.dataSource[0].donvi_id
                } else {
                    cbo.value = null
                }
            },
            async HT_DonVi_TRAMTC_Combobox2(cbo, LOAIDV_ID, DONVI_QL_ID) {

                cbo = this.$refs[cbo.key][0];

                try {
                    var rs = await this.$root.context.post('/web-hopdong/chuyendich/ht_donvi_tramtc_combobox_2', {LOAIDV_ID, DONVI_QL_ID})
                    cbo.dataSource = !rs.data ? [] : rs.data
                } catch (error) {
                    cbo.dataSource = []
                }

                if (cbo.dataSource.length > 0) {
                    cbo.value = cbo.dataSource[0].donvi_id
                } else {
                    cbo.value = null
                }
            }
        }
    }
</script>
