<template src="./index.html"></template>
<script>
import ComboboxGrid from "../../../../components/Controls/ComboboxGrid"
import moment from "moment"
export default {
    props: ["duan_id"],
    components: {
        appCombobox: ComboboxGrid,
    },
    data() {
        return {
            target: '.main-wrapper',
            cbo_ToaNha: {
                items: [],
                selected: null
            },
            cbo_DuAn: {
                items: [],
                selected: this.$props.duan_id
            },
            dsThongTinTB: [],
            chuKy: moment(new Date()).subtract(1, "months").endOf("months").toDate(),
            maTB: "",
            isCheck: false,
        }
    },
    async mounted() {
        // await this.onLoadForm()
        await this.traCuuDuAn()
    },
    methods: {
        async traCuuToaNha(duan_id) {
            try {
                this.loading(true)
                let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/tracuu_toanha/${duan_id}`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.cbo_ToaNha.items = response.data
                } else {
                    this.cbo_ToaNha.items = []
                }
            } catch(err) {
                this.cbo_ToaNha.items = []
            } finally {
                this.loading(false)
            }
        },
        async traCuuDuAn() {
            try {
                this.loading(true)
                let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/tracuu_duan`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.cbo_DuAn.items = response.data
                } else {
                    this.cbo_DuAn.items = []
                }
            } catch(err) {
                this.cbo_DuAn.items = []
            } finally {
                this.loading(false)
            }
        },
        async kiemTraTonTaiBang(chuKy) {
            try {
                let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/kiemtra_tontai_bang?vchuky=${chuKy}`)
                if (response.error_code === 'BSS-00000000' && response.data != 0) {
                    return true
                } else {
                    return false
                }
            } catch(err) {
                return false
            }
        },
        async traCuuTBToaNha() {
            try {
                this.loading(true)
                let apiBody = {
                    vkieu: this.isCheck ? 2 : 1,
                    vchuky: this.isCheck ? moment(this.chuKy).format("yyyyMM").toString() + "01" : "",
                    vduan_id: this.cbo_DuAn.selected,
                    vtoanha_id: this.cbo_ToaNha.selected,
                    vma_tb: this.maTB
                }
                let url = `web-toanha/tracuu-tb-toanha/tracuu_tb_toanha`
                let response = await this.$root.context.post(url, apiBody)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsThongTinTB = response.data
                } else {
                    this.dsThongTinTB = []
                }
            } catch(err) {
                this.dsThongTinTB = []
            } finally {
                this.loading(false)
            }
        },
        selectCboRow() {
            let idx1 = this.cbo_DuAn.items.findIndex(e => e.duan_id == this.cbo_DuAn.selected)
            let idx2 = this.cbo_ToaNha.items.findIndex(e => e.toanha_id == this.cbo_ToaNha.selected)

            if (idx1 != -1) {
                this.$refs.cboDuAn.$refs.grid.setCurrentSelectedRow(idx1)
            }
            if (idx2 != -1) {
                this.$refs.cboToaNha.$refs.grid.setCurrentSelectedRow(idx2)
            }
        },
        async onLoadForm() {
            this.cbo_DuAn.selected = this.$props.duan_id
            this.selectCboRow()
            await this.traCuuToaNha(this.cbo_DuAn.selected)
            await this.traCuuTBToaNha()
            // await this.traCuuDuAn()
        },
        async onTimKiem() {
            // if (this.isCheck) {
            //     let vchuky = this.chuKy && moment(this.chuKy).format("yyyyMM") + "01"
            //     let response = await this.kiemTraTonTaiBang(vchuky)
            //     if (!response) {
            //         this.$root.toastError("Không tồn tại bảng chốt DBTB_" + vchuky + ". Bạn không thể tìm kiếm thông tin")
            //         return
            //     }
            // }
            if (this.cbo_DuAn.selected == 0 && this.cbo_ToaNha.selected == 0 && this.maTB == "") {
                this.$root.toastError("Bạn chưa nhập thông tin tìm kiếm")
                return
            }
            await this.traCuuTBToaNha()
            if (this.isCheck && this.dsThongTinTB.length == 0) {
                this.$root.toastError(`Hiện chưa có dữ liệu chốt tháng ${moment(this.chuKy).format("MM/yyyy")}`)
            }
        },
        async duAnSelected(args) {
            if (args != null) {
                this.cbo_DuAn.selected = args.duan_id
                await this.traCuuToaNha(this.cbo_DuAn.selected)
            }
        },
        toaNhaSelected(args) {
            if (args != null) {
                this.cbo_ToaNha.selected = args.toanha_id
            }
        },
        onXuatExcel() {
            if (this.dsThongTinTB.length <= 0) {
                this.$toast.error("Chưa có danh sách để xuất excel!");
            } else {
                let excelExportProperties = {
                    fileName:"Danh-sach-thue-bao-toa-nha.xlsx",
                    dataSource: this.dsThongTinTB
                };
                this.$refs.thongTinTBToaNhaGrid.excelExport(excelExportProperties);
            }
        },
        close() {
            // this.$bvModal.hide("tracuu-tb-toanha")
            this.$refs.dlgTraCuu.hide()
        },
        async open() {
            this.$refs.dlgTraCuu.show()
            await this.onLoadForm()
        }
    }
}
</script>