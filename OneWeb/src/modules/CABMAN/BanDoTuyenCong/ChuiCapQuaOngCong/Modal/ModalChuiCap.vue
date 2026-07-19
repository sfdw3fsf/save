<template src="./ModalChuiCap.html"></template>
<script>
export default {
    name: "ModalChuiCap",
    props: ["modalId", "dataCong"],
    created: async function () {
        //await this.getTTVT()
        //await this.getNhomCap()
    },

    computed: {},
    methods: {
        focusMyElement: async function () {
            this.ongCongId = this.dataCong.ongCongId
            this.doanCongId = this.dataCong.doanCongId
            console.log("ID: "+ this.ongCongId)
            await this.getTTVT()
            await this.getNhomCap()
            await this.chitietOngCong()
            await this.getdsCapDaChui()
        },
        getTTVT: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/ds-ttvt-theo-nhanvien"
                )
                this.dsTTVT = rs.data
                this.dataSelected.TTVT=this.dsTTVT[0].DONVI_ID
                this.getToQL()
            } catch (error) {
            } finally {
            }
        },
        getToQL: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/ds-to-kythuat-theo-nhanvien/" +
                    this.dataSelected.TTVT
                )
                this.dsToQL = rs.data
                this.dataSelected.ToQL=this.dsToQL[0].DONVI_ID
                this.getTongDai()
            } catch (error) {
            } finally {
            }
        },
        getTongDai: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/bando_tuyencot/ds_tram_tong_dai/" +
                    this.dataSelected.ToQL
                )
                this.dsTongDai = rs.data
                this.dataSelected.TongDai=this.dsTongDai[0].DONVI_ID
                this.getCapGoc()
            } catch (error) {
            } finally {
            }
        },
        getNhomCap: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/danh-sach-nhom-loai-cap"
                )
                this.dsNhomCap = rs.data
            } catch (error) {
            } finally {
            }
        },
        getCapGoc: async function () {
            try {
                let loai_cap = this.dataSelected.NhomCap == "Cáp Quang" ? 1 : 0
                let rs = await this.$root.context.get(
                    "/web-cabman/bando_tuyencot/ds_cap_goc",
                    { loai_nhom_cap: loai_cap, tong_dai_id: this.dataSelected.TongDai }
                )
                this.dsCapGoc = rs.data
            } catch (error) {
            } finally {
            }
        },
        getdsCapDaChui: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/danh-sach-cap-da-chui?ongCongId=" + this.ongCongId
                )
                this.dsCapDaChui = rs.data
            } catch (error) {
            } finally {
            }
        },
        getdsCapChuaChui: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/danh-sach-cap-chua-chui",
                    { ongCongId: this.ongCongId, capGocId: this.dataSelected.CapGoc }
                )
                this.dsCapChuaChui = rs.data
            } catch (error) {
            } finally {
            }
        },
        chitietOngCong: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/chi-tiet-ong-cong/",
                    { doanCongId: this.doanCongId, ongCongId: this.ongCongId }
                )
                rs.data
                this.kyHieu = rs.data.KYHIEU
                this.stt = rs.data.THUTU
                this.kyHieuOngCong = rs.data.KHDC
                this.soCapChuiQua = rs.data.TONGCAP
            } catch (error) {
            } finally {
            }
        },

        onChangeTTVT: async function () {
            await this.getToQL()
        },
        onChangeToQL: async function () {
            await this.getTongDai()
        },
        onChangeTongDai: async function () {
            await this.getCapGoc()
        },
        onChangeNhomCap: async function () {
            await this.getCapGoc()
        },
        onChangeCapGoc: async function () {
            await this.getdsCapChuaChui()
        },

        sleep: function(ms) {
            return new Promise((resolve) => {
                setTimeout(resolve, ms)
            })
        },

        onChangeTextSearch: async function () {
            await this.sleep(2000)
            if (this.searchText != null)
            {
                await this.traCuu()
            }
        },

        chonCaptheoKyhieu: async function () {
            if (this.txtQuery != ""){
                this.$root.showLoading(true);
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/BanDoTuyenCong/tim-kiem-cap-da-chui",
                        {
                            tuKhoa:this.searchText
                        }
                    )
                    this.dsCapSearch = rs.data
                } catch (error) {
                } finally {
                    this.$root.showLoading(false)
                }
            }else{
                this.$toast.warning("Chưa nhập ký hiệu tìm kiếm")
            }
        },

        traCuu: async function () {
            this.chonCaptheoKyhieu()
        },

        showTimKiem: function () {
            this.showDataSearch = !this.showDataSearch
            console.log(this.showDataSearch)
        },

        selectCap: function(cap,index){
            this.showDataSearch = false
            let dsTam = JSON.parse(JSON.stringify(this.dsCapDaChui))
            let dsCap = {}
            if (this.dsCapDaChui.filter(x => x.KYHIEU == cap.OBJ_NAME).length == 0) {
                this.dsCapSearch.forEach(item  => {
                    if (item.OBJ_NAME == cap.OBJ_NAME) {
                        dsCap.CAP_ID = item.SEARCH_QUERY.split('|')[1]
                        dsCap.KYHIEU = item.OBJ_NAME
                        dsCap.TEN_CAP = item.OBJ_NAME
                    }
                })
                dsTam.push(dsCap)
                this.dsCapDaChui = dsTam
            }
            else {
                this.$toast.error('Cáp đã tồn tại trong danh sách!')
            }
        },

        selectChuaChui_selectedItemsChanged: function (dataList) {

            this.dsCapChuaChuiId = dataList
            //console.log(dataList)
        },
        gridChuaChui_selectedRowChanged: function (dataList) {

        },
        selectDaChui_selectedItemsChanged: function (dataList) {
            this.dsCapDaChuiId = dataList
            //console.log(dataList)
        },
        gridDaChui_selectedRowChanged: function (dataList) {

        },
        goCap: async function () {
            try {
                this.$root.showLoading(true);
                let dsTam = JSON.parse(JSON.stringify(this.dsCapChuaChui))
                for (let i = 0; i < this.dsCapDaChuiId.length; i++) {
                    let cap = this.dsCapDaChui.filter(
                        (x) => x.CAP_ID == this.dsCapDaChuiId[i]
                    )[0]
                    this.dsCapDaChui = this.dsCapDaChui.filter(
                        (x) => x.CAP_ID != this.dsCapDaChuiId[i]
                    )
                    let data = {
                        CAP_ID: cap.CAP_ID,
                        TEN_CAP: cap.TEN_CAP,
                        KYHIEU: cap.KYHIEU,
                        ISCHECK: "0",
                    }
                    //this.dsCapChuaChui.push(data)
                    dsTam.push(data)
                }
                this.dsCapChuaChui = dsTam
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra")
            } finally {
                this.$root.showLoading(false)
            }
        },
        chuiCap: async function () {
            try {
                //console.log(this.dsCapChuaChuiId)
                this.$root.showLoading(true)
                let dsTam = JSON.parse(JSON.stringify(this.dsCapDaChui))
                for (let i = 0; i < this.dsCapChuaChuiId.length; i++) {
                    let cap = this.dsCapChuaChui.filter(
                        (x) => x.CAP_ID == this.dsCapChuaChuiId[i]
                    )[0]
                    this.dsCapChuaChui = this.dsCapChuaChui.filter(
                        (x) => x.CAP_ID != this.dsCapChuaChuiId[i]
                    )
                    let data = {
                        CAP_ID: cap.CAP_ID,
                        TEN_CAP: cap.TEN_CAP,
                        KYHIEU: cap.KYHIEU,
                        ISCHECK: "0",
                    }
                    //this.dsCapDaChui.push(data)
                    dsTam.push(data)
                }
                this.dsCapDaChui = dsTam
            } catch (error) {
                console.log(error)
                this.$toast.error("Có lỗi xảy ra")
            } finally {
                this.$root.showLoading(false)
            }
        },
        capNhat: async function () {
            try {

                this.$root.showLoading(true);
                if (this.dsCapDaChui != null && this.dsCapDaChui.length > 0) {
                    let dsCap = []
                    for (let i = 0; i < this.dsCapDaChui.length; i++) {
                        let item = {capId: this.dsCapDaChui[i].CAP_ID}
                        dsCap.push(item)
                    }
                    let data = {
                        dsCapId: dsCap,
                        doanCongId: this.doanCongId,
                        ongCongId: this.ongCongId
                    }
                    let rs = await this.$root.context.post(
                        "/web-cabman/BanDoTuyenCong/chui-cap-qua-cong/", data
                    )
                    if(rs.error_code == 'BSS-00000000')
                    {
                        this.$toast.success("Gán thành công")
                    }
                }
                else {
                    this.$toast.error("Bạn chưa chọn cáp để gán")
                }
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra")
            } finally {
                this.$root.showLoading(false)
            }
        },
    },
    data: function () {
        return {
            fields: { text: "TEN_DV", value: "DONVI_ID" },
            fieldsCapGoc: { text: "TEN_CAP", value: "CAP_ID" },
            fieldsCap: { text: "NHOM_LC", value:"NHOMLC_ID" },
            ongCongId: null,
            doanCongId: null,
            dsCapDaChui: [],
            dsCapChuaChui: [],
            dsCapDaChuiId: [],
            dsCapChuaChuiId: [],
            dsCapGoc: [],
            dsTTVT: [],
            dsToQL: [],
            dsTongDai: [],
            dsNhomCap: [],
            dsCapSearch:[],
            dataSelected: {
                TTVT: null,
                ToQL: null,
                TongDai: null,
                NhomCap: "Cáp Quang",
                CapGoc: null,
            },
            kyHieu: "",
            stt: "",
            kyHieuOngCong: "",
            soCapChuiQua:"",
            searchText: "",
            showDataSearch: false
        };
    },
};
</script>
<style>
  .grid-stack-box {
    background-color: transparent;
  }
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
}
</style>
