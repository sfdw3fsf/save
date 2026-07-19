<template src="./index.html"></template>
<script>
export default {
    props: ["parent"],
    data() {
        return {
            DichVuVienThong: {
                GPHONE: 10
            },
            LOAI_DV: {
                TRAM_VT: 5
            },
            cboTrangBiGP: {
                DataSource: [],
                Value: null
            },
            txtSoSimGphone: {
                Text: ""
            },
            checkVSVX: {
                Checked: false
            },
            dsComboDonVi: [],
        }
    },
    computed: {
        idDichVu() {
            return parseInt(this.parent.parent.modelCbo.cboDichVuVT)
        },
        isActive() {
            return this.idDichVu == this.DichVuVienThong.GPHONE
        },
        idLoaiHinhTb() {
            return parseInt(this.parent.parent.modelCbo.cboLoaihinhTB)
        },
        idKieuLd() {
            return parseInt(this.parent.parent.modelCbo.cboKieuLD)
        },
        idDaiVt() {
            return this.parent.parent.modelCbo.cboDaiVT
        }
    },
    watch: {
        idKieuLd: async function() {
            if (this.isActive) {
                await this.HT_COMBO_DONVI()
            }
        },
        idLoaiHinhTb: async function() {
            if (this.isActive) {
                await this.HT_COMBO_DONVI()
            }
        },
        "cboTrangBiGP.Value": function(value) {
            this.cboTrangBiGP_SelectedValueChanged(value)
        },
    },
    async mounted() {
        this.cboTrangBiGP.DataSource = await this.lay_ds_trangbi()
        this.cboTrangBiGP.Value = this.cboTrangBiGP.DataSource[0].trangbi_id
    },
    methods: {
        TaoDuLieu_HDTB_GP(themmoi) {

            let rowHDTB_GP = {
                HDTB_ID: this.parent.parent.hdtb_id,
                SIMCARD: this.txtSoSimGphone.Text,
                TRANGBI_ID: parseInt(this.cboTrangBiGP.Value)
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
                dsHDTB_GP: [rowHDTB_GP],
                dsHDTBDV: dsHDTBDV
            }
        },
        async LAY_DS_DB_GP_THEO_TBID(thuebao_id) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_db_gp_theo_tbid?thuebao_id=${thuebao_id}`)
                return !response.data ? [] : response.data
            } catch (err) {
                return []
            }
        },
        async lay_ds_hdtb_GP_theo_hdtb_id(hdtb_id) {
            try {
                let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_hdtb_gp_theo_hdtb_id?hdtb_id=${hdtb_id}`)
                return !response.data ? [] : response.data
            } catch (err) {
                return []
            }
        },
        async HienThiTTMoRongHD(hdtb_id) { 
                
            let ds = await this.lay_ds_hdtb_GP_theo_hdtb_id(hdtb_id)

            if (ds.length <= 0) {
                return
            }

            this.txtSoSimGphone.Text = ds[0]["simcard"];

            if (!!ds[0]["trangbi_id"]) {
                this.cboTrangBiGP.Value = ds[0]["trangbi_id"] + "";
            }

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
            
            let ds = await this.LAY_DS_DB_GP_THEO_TBID(thuebao_id)

            if (ds.length <= 0) {
                return
            }

            this.txtSoSimGphone.Text = ds[0]["simcard"];

            if (!!ds[0]["trangbi_id"]) {
                this.cboTrangBiGP.Value = ds[0]["trangbi_id"] + "";
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
        cboTrangBiGP_SelectedValueChanged(value) {
            if (!!value) {
                this.$emit("cboTrangBiGP_SelectedValueChanged", { trangbi_id: value })
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
    },
}
</script>
