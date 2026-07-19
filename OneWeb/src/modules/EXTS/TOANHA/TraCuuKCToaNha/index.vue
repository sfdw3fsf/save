<template src="./index.html"></template>
<script>
import ComboboxGrid from "../../../../components/Controls/ComboboxGrid"
export default {
    props: ["duan_id"],
    components: {
        appCombobox: ComboboxGrid,
    },
    data() {
        return {
            cbo_ToaNha: {
                items: [],
                selected: null
            },
            cbo_DuAn: {
                items: [],
                selected: null
            },
            dsThongTinKC: [],
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
        async traCuuKCToaNha() {
            try {
                this.loading(true)
                let apiBody = {
                    vduan_id: this.cbo_DuAn.selected,
                    vtoanha_id: this.cbo_ToaNha.selected
                }
                console.log("body: ", apiBody)
                let url = `web-toanha/tracuu-kc-toanha/tracuu_kc_toanha`
                let response = await this.$root.context.post(url, apiBody)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsThongTinKC = response.data
                } else {
                    this.dsThongTinKC = []
                }
            } catch(err) {

            } finally {
                this.loading(false)
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
        selectCboRow() {
            let idx1 = this.cbo_DuAn.items.findIndex(e => e.duan_id == this.cbo_DuAn.selected)
            let idx2 = this.cbo_ToaNha.items.findIndex(e => e.toanha_id == this.cbo_ToaNha.selected)
            console.log(idx1, " ", this.cbo_DuAn.selected)

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
            // await this.traCuuDuAn()
            await this.traCuuToaNha(this.cbo_DuAn.selected)
            await this.traCuuKCToaNha()
        },
        async onTimKiem() {
            if (this.cbo_DuAn.selected == 0 && this.cbo_ToaNha.selected == 0) {
                this.$toast.error("Bạn chưa nhập thông tin tìm kiếm!")
                return
            }
            await this.traCuuKCToaNha()
        },
        onXuatExcel() {
            if (this.dsThongTinKC.length <= 0) {
                this.$toast.error("Chưa có danh sách để xuất excel!");
            } else {
                let excelExportProperties = {
                    fileName:"Danh-sach-ket-cuoi-toa-nha.xlsx",
                    dataSource: this.dsThongTinKC
                };
                this.$refs.thongTinKCToaNhaGrid.excelExport(excelExportProperties);
            }
        },
        async open() {
            this.$bvModal.show("tracuu-kc-toanha")
            // await this.onLoadForm()
        },
        close() {
            this.$bvModal.hide("tracuu-kc-toanha")
        },
        async focusMyElement() {
            await this.onLoadForm()
            console.log("cboDuAn", this.$refs.cboDuAn)
        }
    },
    destroyed() {
        
    },
}
</script>