<template src="./ModalChuiCap.html"></template>
<script>
export default {
    name: "ModalChuiCap",
    props: ["modalId", "data"],
    created: async function () {
        //await this.getTTVT()
        //await this.getNhomCap()
    },

    computed: {},
    methods: {
        focusMyElement: async function () {
            this.capId = 530582
            //this.capId = this.data.capId
            console.log("ID: "+ this.ongCongId)
            this.dataSelected.TTVT = null
            this.dataSelected.ToQL = null
            this.dataSelected.tuyenCongBeId = null
            this.dataSelected.doanCongId = null
            this.dsCongChuaChui = []
            await this.getTTVT()
            await this.getdsCongDaChui()
        },
        getTTVT: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/ds-ttvt-theo-nhanvien"
                )
                this.dsTTVT = rs.data
                //this.dataSelected.TTVT=this.dsTTVT[0].DONVI_ID
                //this.getToQL()
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
                //this.dataSelected.ToQL=this.dsToQL[0].DONVI_ID
                //this.getTuyenCongBe()
            } catch (error) {
            } finally {
            }
        },
        getTuyenCongBe: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/ds-tuyen-cong/" +
                    this.dataSelected.ToQL
                )
                this.dsTuyenCongBe = rs.data
                //this.dataSelected.TongDai=this.dsTongDai[0].DONVI_ID
                //this.getDoanCong()
            } catch (error) {
            } finally {
            }
        },
        getDoanCong: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/mang-truyen-dan/lay-danh-sach-doan-cong-theo-tuyen-cong-be?tuyenCbId=" + this.dataSelected.tuyenCongBeId
                )
                this.dsDoanCong = rs.data
            } catch (error) {
            } finally {
            }
        },

        getdsCongDaChui: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/mang-truyen-dan/lay-danh-sach-ong-cong-da-chui?capId=" + this.capId
                )
                this.dsCongDaChui = rs.data
                console.log(this.dsCongDaChui)
            } catch (error) {
            } finally {
            }
        },
        getdsCongChuaChui: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/mang-truyen-dan/lay-danh-sach-ong-cong-chua-chui",
                    { capId: this.capId, doanCongId: this.dataSelected.doanCongId }
                )
                this.dsCongChuaChui = rs.data
            } catch (error) {
            } finally {
            }
        },

        onChangeTTVT: async function () {
            await this.getToQL()
        },
        onChangeToQL: async function () {
            await this.getTuyenCongBe()
        },
        onChangeTuyen: async function () {
            await this.getDoanCong()
        },
        onChangeDoanCong: async function () {
            await this.getdsCongChuaChui()
        },

        selectChuaChui_selectedItemsChanged: function (dataList) {
            this.dsCongChuaChuiId = dataList
            //console.log(dataList)
        },
        gridChuaChui_selectedRowChanged: function (dataList) {

        },
        selectDaChui_selectedItemsChanged: function (dataList) {
            this.dsCongDaChuiId = dataList
            //console.log(dataList)
        },
        gridDaChui_selectedRowChanged: function (dataList) {

        },
        goCap: async function () {
            try {
                this.$root.showLoading(true)
                let dsTam = JSON.parse(JSON.stringify(this.dsCongChuaChui))
                for (let i = 0; i < this.dsCongDaChuiId.length; i++) {
                    let cong = this.dsCongDaChui.find(
                        (x) => x.ONGCONG_ID == this.dsCongDaChuiId[i]
                    )
                    this.dsCongDaChui = this.dsCongDaChui.filter(
                        (x) => x.ONGCONG_ID != this.dsCongDaChuiId[i]
                    )
                    let data = {
                        ONGCONG_ID: cong.ONGCONG_ID,
                        DOANCONG_ID: cong.DOANCONG_ID,
                        KHDC: cong.KHDC,
                        ISCHECK: "0",
                        THUTU: cong.THUTU,
                        KHOC: cong.KHOC
                    }
                    dsTam.push(data)
                }
                this.dsCongChuaChui = dsTam
                //console.log(this.dsCongChuaChui)
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra")
            } finally {
                this.$root.showLoading(false)
            }
        },
        chuiCap: async function () {
            try {
                //console.log(this.dsCongChuaChuiId)
                if (this.dsCongDaChui.length > 0
                    && this.dsCongDaChui.filter(x => x.DOANCONG_ID == this.dataSelected.doanCongId).length > 0) {
                    this.$toast.error("Đoạn cống đã có ống cống được gán.")
                    return false
                }
                this.$root.showLoading(true)
                let dsTam = JSON.parse(JSON.stringify(this.dsCongDaChui))
                for (let i = 0; i < this.dsCongChuaChuiId.length; i++) {
                    let cong = this.dsCongChuaChui.find(
                        (x) => x.ONGCONG_ID == this.dsCongChuaChuiId[i]
                    )
                    this.dsCongChuaChui = this.dsCongChuaChui.filter(
                        (x) => x.ONGCONG_ID != this.dsCongChuaChuiId[i]
                    )
                    let data = {
                        ONGCONG_ID: cong.ONGCONG_ID,
                        DOANCONG_ID: cong.DOANCONG_ID,
                        KHDC: cong.KHDC,
                        ISCHECK: "0",
                        THUTU: cong.THUTU,
                        KHOC: cong.KHOC
                    }
                    //this.dsCongDaChui.push(data)
                    dsTam.push(data)
                }
                this.dsCongDaChui = dsTam
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
                if (this.dsCongDaChui != null && this.dsCongDaChui.length > 0) {
                    let dsCong = []
                    for (let i = 0; i < this.dsCongDaChui.length; i++) {
                        let item = {ongCongId: this.dsCongDaChui[i].ONGCONG_ID, doanCongId: this.dsCongDaChui[i].DOANCONG_ID}
                        dsCong.push(item)
                    }
                    let data = {
                        dsCong: dsCong,
                        capId: this.capId
                    }
                    let rs = await this.$root.context.post(
                        "/web-cabman/mang-truyen-dan/chui-cap-qua-cong/", data
                    )
                    if(rs.error_code == 'BSS-00000000')
                    {
                        this.$toast.success("Gán thành công")
                    }
                }
                else {
                    this.$toast.error("Bạn chưa chọn cống để gán")
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
            // fields: { text: "TEN_DV", value: "DONVI_ID" },
            // fieldsCapGoc: { text: "TEN_CAP", value: "CAP_ID" },
            // fieldsCap: { text: "NHOM_LC", value:"NHOMLC_ID" },
            capId: null,
            dsCongDaChui: [],
            dsCongChuaChui: [],
            dsTTVT: [],
            dsToQL: [],
            dsTuyenCongBe: [],
            dsDoanCong:[],
            dataSelected: {
                TTVT: null,
                ToQL: null,
                tuyenCongBeId: null,
                doanCongId:null
            },
        };
    },
};
</script>
<style>
  .grid-stack-box {
    background-color: transparent;
  }
/*div#popupTCTTTB___BV_modal_outer_ {*/
/*  z-index: 10 !important;*/
/*  margin-top: 100px;*/
/*}*/
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
}
</style>
