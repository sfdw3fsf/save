<template src="./index.templete.html"></template>
<style scoped src="./index.scss"></style>
<script>
import moment from "moment"
import Vue from "vue"
import { currency } from "@/filters/currency"
export default {
    data() {
        return {
            isCheck: {
                kyCuoc: true,
                ngayTT: false
            },
            btnEnable: {
                xuatExcel: false
            },
            ma_dt: null,
            ten_dt: null,
            user_huy: null,
            kyHD: moment(new Date()).subtract(1, "months").endOf("months").toDate(),
            chuKy: "01",
            ngayTT: new Date(),
            today: moment(new Date()).subtract(1, "months").endOf("months").toDate(),
            ds_phieuHuy: [],
            sumTemplate: function() {
                return { template: Vue.component('sumTemplate', {
                    template: `<input type="text" class="form-control text-sum" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data: function () {return {data: {data: {}}};}
                    })
                }
            }
        }
    },
    computed: {
        aggregatesColumns () {
            return [
                { field: 'sotienhuy', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
            ]
        },
    },
    methods: {
        async layDSPhieuHuy() {
            try {
                this.loading(true)
                let body = {
                    vma_dt: this.ma_dt,
                    vkyhoadon: this.isCheck.kyCuoc ? moment(this.kyHD).format("yyyyMM").toString() + this.chuKy : null,
                    vngayhuy: this.isCheck.ngayTT ? moment(this.ngayTT).format("DD/MM/yyyy").toString() : null,
                    vuser_huy: this.user_huy
                }
                let response = await this.$root.context.post(`web-toanha/phieuhuy-dt/lay_ds_phieuhuy`, body)
                this.ds_phieuHuy = []
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.ds_phieuHuy = response.data
                    this.ds_phieuHuy.map(e => {
                        e.ngayhuy = moment(e.ngayhuy).format("DD/MM/yyyy")
                        // e.ngay_tt = moment(e.ngay_tt).format("DD/MM/yyyy")
                        // e.sotienhuy = currency(e.sotienhuy)
                    })
                }
            } catch(error) {
                this.ds_phieuHuy = []
            } finally {
                this.loading(false)
            }
        },
        async showModal() {
            await this.layDSPhieuHuy()
            if (this.ds_phieuHuy.length > 0) {
                this.btnEnable.xuatExcel = true
            }
            this.$refs['dlgPhieuHuy'].show()
        },
        hiddenModal() {
            this.ma_dt = null
            this.ten_dt = null
            this.btnEnable.kyCuoc = true
            this.btnEnable.xuatExcel = false
            this.ds_phieuHuy = []
        },
        async timKiem() {
            await this.layDSPhieuHuy()
            if (this.ds_phieuHuy.length > 0) {
                this.btnEnable.xuatExcel = true
                
            } else {
                this.btnEnable.xuatExcel = false
                this.$toast.error("Không có dữ liệu. Vui lòng thử lại!")
            }
        },
        onSelectedRowChanged: function(val, index) {
            if (this.ds_phieuHuy.length > 0 && val != null) {
                this.ma_dt = val.ma_dt
                this.ten_dt = val.ten_dt
                // this.user_huy = val.nguoihuy
                // this.ngay_tt = val.ngay_tt
            }
        },
        gridViewControll_ExcelQueryCellInfo(args) {
            if (args.column.field === "sotienhuy") {
                args.cell.innerText = currency(args.data.sotienhuy);
            }
        },
        gridViewControll_QueryCellInfo(args) {
            if (args.column.field === "sotienhuy") {
                args.cell.innerText = currency(args.data.sotienhuy);
            }
        },
        xuatExcel() {
            if (this.ds_phieuHuy.length == 0) {
                this.$toast.error("Không có dữ liệu để xuất Excel!")
                return
            }
            let exportProperties = {
                fileName: "Danh-sach-phieu-huy.xlsx",
                dataSource: this.ds_phieuHuy
            }
            this.$refs.dsPhieuHuy.excelExport(exportProperties)
        },

    }
}
</script>
