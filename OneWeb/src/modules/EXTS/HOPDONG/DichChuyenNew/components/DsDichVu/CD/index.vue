<template src="./index.html"></template>
<script>
    import frmDSTBCungDoiCap from './frmDSTBCungDoiCap'
    import frmDSTBLapKem from './frmDSTBLapKem'
    import Enum from '../../../Enum.js'

    export default {
        components: {
            frmDSTBCungDoiCap,
            frmDSTBLapKem
        },
        props: ["idKieuLd", "parent"],
        data() {
            return {
                LOAI_DV: {
                    TRAM_VT: 5
                },
                txtMaTN_CD: {
                    Text: null
                },
                txtMaDoiCap_CD: {
                    Text: null
                },
                txtSoMetDay: {
                    Text: "0"
                },
                cboTrangBiCD: {
                    DataSource: [],
                    Value: null
                },
                dsComboDonVi: [],
                isdn_sochinh: false,
                matb_isdn_chinh: "",
                ds_isdn: [],
                ne_id: 0,
                sone: ""
            }
        },
        computed: {
            khachHangId() {
                return this.parent.inputValue.khachhang_id
            },
            hdkhId() {
                return this.parent.inputValue.hdkh_id
            },
            idDaiVt() {
                return this.parent.modelCbo.cboDaiVT
            },
            idLoaihinhTb() {
                return parseInt(this.parent.modelCbo.cboLoaihinhTB)
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
            idDaiVt: async function (value) {
                if (!value) {
                    this.dsComboDonVi = []
                    return
                }

                await this.HT_COMBO_DONVI()
            },
            idKieuLd: async function(value) {

                if (!value) {
                    this.dsComboDonVi = []
                    return
                }

                await this.HT_COMBO_DONVI()
            },
            "cboTrangBiCD.Value": function(value) {
                this.cboTrangBiCD_SelectedValueChanged(value)
            },
        },
        async mounted() {
            this.cboTrangBiCD.DataSource = await this.lay_ds_trangbi()
            this.cboTrangBiCD.Value = this.cboTrangBiCD.DataSource[0].trangbi_id
        },
        methods: {
            async HienThiTTMoRongHD(hdtb_id) {
                
                let ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id)

                if (ds.length <= 0) {
                    return
                }

                this.txtSoMetDay.Text = ds[0]["culy"];
                this.txtMaDoiCap_CD.Text = ds[0]["madoicap"];
                this.txtMaTN_CD.Text = ds[0]["matb_tn"];

                if (!!ds[0]["trangbi_id"] || parseInt(ds[0]["trangbi_id"]) == 0)
                    this.cboTrangBiCD.Value = ds[0]["trangbi_id"] + "";

                if (!!ds[0]["sotruot"])
                {
                    this.parent.inputValue.kieu_thueso = Enum.THUESO.TRUOTSO;
                    this.parent.inputValue.sothang_thueso = parseInt(ds[0]["tg_thueso"]);
                    this.parent.inputValue.noidung_thueso = ds[0]["sotruot"];
                }

                if (!!ds[0]["noidung_tb"])
                {
                    this.parent.inputValue.kieu_thueso = Enum.THUESO.THONGBAO;
                    this.parent.inputValue.sothang_thueso = parseInt(ds[0]["tg_thueso"]);
                    this.parent.inputValue.noidung_thueso = ds[0]["noidung_tb"];
                }

                if (!ds[0]["sotruot"] && !ds[0]["noidung_tb"])
                {
                    this.parent.inputValue.kieu_thueso = 0;
                    this.parent.inputValue.sothang_thueso = 0;
                    this.parent.inputValue.noidung_thueso = "";
                }

                if (ds[0]["ne_id"] != "")
                {
                    this.ne_id = parseInt(ds[0]["ne_id"]);
                }

                this.sone = ds[0]["ne"];

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
            async LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_hdtb_cd_theo_hdtb_id?hdtb_id=${hdtb_id}`)
                    return !response.data ? [] : response.data
                } catch (err) {
                    return []
                }
            },
            async HienThiTTMoRongTB(thuebao_id) {

                let ds = await this.LAY_DS_DB_CD_THEO_TBID(thuebao_id)

                if (ds.length <= 0) {
                    return
                }

                let data = ds[0]

                this.txtMaTN_CD.Text = null
                this.txtSoMetDay.Text = data.culy
                this.txtMaDoiCap_CD.Text = data.madoicap

                if (!!data.trangbi_id) {
                    this.cboTrangBiCD.Value = data.trangbi_id
                }

                if (this.idLoaihinhTb == Enum.LoaiHinhTB.ISDN2B_CD
                    || this.idLoaihinhTb == Enum.LoaiHinhTB.ISDN2B_CQ
                    || this.idLoaihinhTb == Enum.LoaiHinhTB.ISDN30B_CD
                    || this.idLoaihinhTb == Enum.LoaiHinhTB.ISDN30B_CQ
                    || this.idLoaihinhTb == Enum.LoaiHinhTB.TRUNGKE_2M
                    || this.idLoaihinhTb == Enum.LoaiHinhTB.TRUNGKE_THUONG
                    || this.idLoaihinhTb == Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
                    await this.LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id)
                }

                if (!!data.ne_id) {
                    this.ne_id = parseInt(data.ne_id);
                }
                            
                this.sone = data.ne

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
            async LAY_DS_DB_CD_THEO_TBID(thuebao_id) {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_db_cd_theo_tbid?thuebao_id=${thuebao_id}`)
                    return response.data
                } catch (err) {
                    return []
                }
            },
            async LAY_DS_ISDN_THEO_THUEBAO_ID(vthuebao_id) {

                this.isdn_sochinh = false;
                this.matb_isdn_chinh = "";

                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_isdn_theo_thuebao_id?thuebao_id=${vthuebao_id}`)
                    this.ds_isdn = response.data
                } catch (err) {
                    this.ds_isdn = []
                }

                for (let i = 0; i < this.ds_isdn.length; i++)
                {
                    if (parseInt(this.ds_isdn[i]["thuebao_id"]) == parseInt(vthuebao_id))
                    {
                        if (!this.ds_isdn[i]["thuebao_cha_id"]) {
                            this.isdn_sochinh = true;
                        }
                    }

                    if (!this.ds_isdn[i]["thuebao_cha_id"])
                    {
                        this.matb_isdn_chinh = this.ds_isdn[i]["ma_tb"];
                    }
                }
            },
            async TaoDuLieu_HDTB_CD(themmoi) {

                let rowHDTB_CD = {
                    MATB_TN: this.txtMaTN_CD.Text,
                    TRANGBI_ID: parseInt(this.cboTrangBiCD.Value),
                    MADOICAP: null,
                    NE_ID: this.ne_id,
                    NE: this.sone
                }

                let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

                if (kieu == 1 || kieu == 2 || kieu == 4) {

                    if (!this.txtMaDoiCap_CD.Text) {
                        this.$root.toastError("Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.");
                        return;
                    }

                    rowHDTB_CD.MADOICAP = parseInt(this.txtMaDoiCap_CD.Text);
                }
                else //TH còn lại sinh mã đôi cáp
                {
                    if (themmoi == true)
                    {
                        rowHDTB_CD.MADOICAP = await this.get_key_madoicap()
                        this.txtMaDoiCap_CD.Text = rowHDTB_CD.MADOICAP;
                    }
                    else {
                        rowHDTB_CD.MADOICAP = parseInt(this.txtMaDoiCap_CD.Text);
                    }
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

                return { rowHDTB_CD, dsHDTBDV }
            },
            async get_key_madoicap() {
                try {
                    let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/get_key_madoicap`)
                    return response.data.key
                } catch (err) {
                    return null
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
                            
                            if (!!loaiDonVi.donvi_id && this.dataSource.some(x => x.donvi_id == loaiDonVi.donvi_id)) {
                                this.value = loaiDonVi.donvi_id
                            } else {
                                this.value = this.dataSource[0].donvi_id
                            }
                        }
                    }
                }
            },
            async HT_COMBO_DONVI() {

                let dsLoaiDonVi = await this.lay_ds_loai_donvi({
                    loaihinh_tb: this.idLoaihinhTb,
                    kieu_ld: this.idKieuLd
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
            async lay_donvi_theo_loaidv_diachi_ld_v2(loaidv_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id) {
                try {
                    var data = {loaidv_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id}
                    var response = await this.$root.context.post('/web-hopdong/chuyendich-codinh/lay_donvi_theo_loaidv_diachi_ld_v2', data)
                    return response.data
                } catch (error) {
                    return []
                }
            },
            cboTrangBiCD_SelectedValueChanged(value) {
                if (!!value) {
                    this.$emit("cboTrangBiCD_SelectedValueChanged", { trangbi_id: value })
                }
            },
            async btnMaTN_CD_Click() {

                let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

                if (kieu == 2) { //Lắp mới trên đường có sẵn

                    if (parseInt(this.khachHangId) > 0) {

                        let frm = this.$refs['ref-ds-thue-bao-cung-cap']
                        frm.khachhang_id = parseInt(this.khachHangId)
                        frm.kieuld_id = parseInt(this.idKieuLd)
                        frm.loaitb_id = parseInt(this.idLoaihinhTb)
                        
                        let data = await frm.show()
                        
                        if (data.chapnhan) {
                            this.txtMaTN_CD.Text = data.ma_tn
                            this.txtMaDoiCap_CD.Text = data.ma_doicap
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
                        frm.loaitb_id = parseInt(this.idLoaihinhTb)
                        
                        let data = await frm.show()
                        
                        if (data.chapnhan) {
                            this.txtMaTN_CD.Text = data.ma_tn
                            this.txtMaDoiCap_CD.Text = data.ma_doicap
                        }
                    } else {
                        this.$root.toastError("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !");
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
                    let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/lay_ds_loai_donvi`, data)
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
