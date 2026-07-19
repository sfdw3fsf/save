<template src="./InHoaDonDienTu.html"></template>
<style src="./InHoaDonDienTu.scss" scoped></style>
<script>
import moment from "moment";
import { mapActions, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import PopupDocFile from "./popup-docfile.vue";
import xlsx from "xlsx";
import { previewPrint } from "../../../utils/util";
import VueHtml2pdf from 'vue-html2pdf'

export default {
    name: "InHoaDonDienTu",
    components: {
        appKyCuoc: KyCuoc,
        PopupDocFile,
        VueHtml2pdf
    },
    async created() {
        this.clearData();
        this.fetchData();
    },
    async mounted() { },
    data() {
        return {
            header: {
                title: "IN HÓA ĐƠN ĐIỆN TỬ CHUYỂN ĐỔI",
                list: [
                    {
                        name: "Hóa đơn",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "In hóa đơn điện tử",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/y",
                    max: new Date()
                }
            },
            kyHoaDon: moment(new Date()).format("yyyyMM") + "01",
            pKyCuoc: "",
            listHoaDon: [],
            listTieuChi: [
                { id: 1, name: "DS chuyển đổi" },
                { id: 2, name: "Mã thanh toán" },
                { id: 3, name: "Seri" },
                { id: 4, name: "Số seri" },
                { id: 5, name: "Mã số thuế" }
            ],
            selectedTieuChi: 1,
            giaTri: "",
            fieldsHTTT: {
                text: "HTTT",
                value: "HTTT_ID"
            },
            selectedHTTT: [],
            checkHTTT: false,
            selectedItemCongNo: null,
            srcHtml: "",
            fieldDsCongNo: [
                {
                    fieldName: "MA_TT",
                    headerText: "Mã thanh toán",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MA_DD",
                    headerText: "Mã đại diện",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "Tên thanh toán",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SOSERI",
                    headerText: "Số seri",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SOQUYEN",
                    headerText: "Số thùng",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SERI",
                    headerText: "Seri",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "STK",
                    headerText: "Số tài khoản",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "MST",
                    headerText: "Mã số thuế",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_DV",
                    headerText: "Đại lý",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "Địa chỉ thanh toán",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TIEN",
                    headerText: "Tiền",
                    allowFiltering: true,
                    allowSorting: true,
                    type: 'number',
                    format: 'N0'
                },
                {
                    fieldName: "VAT",
                    headerText: "Thuế",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TONG",
                    headerText: "Tổng tiền",
                    allowFiltering: true,
                    allowSorting: true,
                    type: 'number',
                    format: 'N0'
                }
            ]
        };
    },
    watch: {
        listHTTTGetter: function (newValue) {
            if (newValue.length > 0) {
                this.selectedHTTT = newValue.map(item => item.HTTT_ID);
            }
        }
    },
    computed: {
        ...mapGetters("InHoaDonDienTu", ["listHTTTGetter"])
    },
    methods: {
        ...mapActions("InHoaDonDienTu", [
            "getHTTT",
            "traCuuHoaDon",
            "clearData"
        ]),

        handleKyCuoc(e) {
            this.pKyCuoc = moment(e.p_kyhoadon).format("yyyyMM") + e.p_chuky_no;
        },

        async fetchData() {
            await this.getList();
        },

        async getList() {
            this.loading(true);
            let data = {
                donViId: this.$auth.getDonViID(),
                nhanVienId: this.$auth.getNhanVienID()
            };
            try {
                await Promise.all([this.getHTTT()]);
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        onCongNoRowChange(dataItem) { },

        async traCuu() {
            this.loading(true);
            const data = {
                kieu: this.selectedTieuChi,
                dsHTTTId: this.checkHTTT ? this.selectedHTTT.toString() : [],
                giaTri: this.giaTri,
                kyCuoc: this.pKyCuoc
            };
            try {
                const response = await this.traCuuHoaDon(data);
                console.log({ response });
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length === 0
                ) {
                    this.$root.toastError(
                        "Không tìm thấy danh sách hóa đơn thỏa mãn điều kiện!"
                    );
                    this.listHoaDon = [];
                } else {
                    //response.data.data.forEach(item => {
                    //    item.TONGNO = Number(item.TONGNO).toLocaleString(
                    //        "en-GB"
                    //    );
                    //    item.NOAM = Number(item.NOAM).toLocaleString("en-GB");
                    //});
                    this.listHoaDon = response.data.data;
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },
        async taiNhieuFile() {
            if (this.listHoaDon.length === 0) {
                this.$root.toastError("Chưa có danh sách hóa đơn!");
                return;
            }
            const dsSelected = this.$refs.dsHoaDon.getSelectedRecords() || [];
            if (dsSelected.length === 0) {
                this.$root.toastError("Vui lòng chọn hóa đơn muốn xuất!");
                return;
            }
            const baseUrl = process.env.API;
            const dsFkey = dsSelected.map(item => item.FKEY || "")
            try {
                this.loading(true);
                await this.axios({
                    url:
                        `${baseUrl}/web-thuno/api/thu-no/in-hoa-don/in-hoa-don-dien-tu`,
                    method: "POST",
                    responseType: "blob",
                    data: {
                        dsFKeys: dsFkey
                    }
                }).then(response => {
                    previewPrint(response.data);
                }).catch(async err => {
                    const errorJson = JSON.parse(await err.data.text());
                    this.$toast.error(errorJson.message_detail);
                })
            } catch (error) {
                console.log(error)
                // this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false)
            }
        },
        async taiFile() {
            if (this.listHoaDon.length === 0) {
                this.$root.toastError("Chưa có danh sách hóa đơn!");
                return;
            }
            const dsSelected = this.$refs.dsHoaDon.getSelectedRecords() || [];
            if (dsSelected.length === 0) {
                this.$root.toastError("Vui lòng chọn hóa đơn muốn xuất!");
                return;
            }
            this.loading(true);
            const baseUrl = process.env.API;
            await Promise.all(dsSelected.map(async item => {
                if (item.FKEY) {
                    await this.axios({
                        url: `${baseUrl}/app-com/hoadon_dientu/downloadInvPDFFkeyNoPay`,
                        method: "POST",
                        data: {
                            phanvung_id: parseInt(this.$auth.getPhanVungID()),
                            fkey: item.FKEY
                        }
                    })
                        .then(response => {
                            let linkSource = `data:application/pdf;base64,${response.data.data.response}`;
                            let downloadLink = document.createElement("a");
                            let fileName = item.MA_TT + ".pdf";
                            downloadLink.href = linkSource;
                            downloadLink.download = fileName;
                            downloadLink.click();
                        })
                        .catch(error => {
                            console.log(error);
                            this.$root.toastError(error.data.message || `Có lỗi xảy ra với mã ${item.MA_TT}`);
                        });
                } else {
                    this.$root.toastError(`Mã ${item.MA_TT} không tồn tại fkey!`);
                }
            }))

            this.loading(false);
        },
        async chuyenDoiTaiFile_v3() {
            if (this.listHoaDon.length === 0) {
                this.$root.toastError("Chưa có danh sách hóa đơn!");
                return;
            }
            const dsSelected = this.$refs.dsHoaDon.getSelectedRecords() || [];
            if (dsSelected.length === 0) {
                this.$root.toastError("Vui lòng chọn hóa đơn muốn xuất!");
                return;
            }
            this.loading(true);
            const baseUrl = process.env.API;
            let dataPrint = []
            await Promise.all(dsSelected.map(async item => {
                if (item.FKEY) {
                    await this.axios({
                        url: `${baseUrl}/app-com/hoadon_dientu/convertForStoreFkey`,
                        method: "POST",
                        data: {
                            phanvung_id: parseInt(this.$auth.getPhanVungID()),
                            fkey: item.FKEY
                        }
                    })
                        .then(response => {
                            dataPrint.push(response.data.data)
                        })
                        .catch(error => {
                            console.log(error);
                            this.$root.toastError(error.data.message || `Có lỗi xảy ra với mã ${item.MA_TT}`);
                        });
                } else {
                    this.$root.toastError(`Mã ${item.MA_TT} không tồn tại fkey!`);
                }
            }))
            if(dataPrint.length > 0){
                let mywindow = window.open('', 'PRINT', 'height=650,width=1100,top=100,left=150');
                mywindow.document.write(dataPrint);
                mywindow.document.close();
                mywindow.focus();
                // mywindow.print()
                // mywindow.close();
            }
            this.loading(false);
        },
        // async chuyenDoiTaiFile() {
        //     if (this.listHoaDon.length === 0) {
        //         this.$root.toastError("Chưa có danh sách hóa đơn!");
        //         return;
        //     }
        //     const dsSelected = this.$refs.dsHoaDon.getSelectedRecords() || [];
        //     if (dsSelected.length === 0) {
        //         this.$root.toastError("Vui lòng chọn hóa đơn muốn xuất!");
        //         return;
        //     }
        //     this.loading(true);
        //     const baseUrl = process.env.API;
        //     await dsSelected.forEach(item => {
        //         if (item.FKEY) {
        //             this.axios({
        //                 url: `${baseUrl}/app-com/hoadon_dientu/convertForStoreFkey`,
        //                 method: "POST",
        //                 data: {
        //                     phanvung_id: parseInt(this.$auth.getPhanVungID()),
        //                     fkey: item.FKEY
        //                 }
        //             })
        //                 .then(response => {
        //                     let mywindow = window.open('', 'PRINT', 'height=650,width=1100,top=100,left=150');
        //                     mywindow.document.write(response.data.data);
        //                     // mywindow.document.close(); // necessary for IE >= 10
        //                     mywindow.focus(); // necessary for IE >= 10*/
        //                     // mywindow.print()
        //                     // mywindow.close();
        //                     // let iframe = document.createElement("iframe");
        //                     // document.body.appendChild(iframe);

        //                     // iframe.style.display = "none";
        //                     // iframe.srcdoc = response.data.data;
        //                     // iframe.onload = function() {
        //                     //     setTimeout(function() {
        //                     //         iframe.focus();
        //                     //         iframe.contentWindow.print();
        //                     //     }, 1);
        //                     // };
        //                     // this.srcHtml = response.data.data
        //                     // this.$bvModal.show("popup-taifile");

        //                 })
        //                 .catch(error => {
        //                     console.log(error);
        //                     this.$root.toastError(error.data.message);
        //                 });
        //         } else {
        //             this.$root.toastError(`Mã ${item.MA_TT} không tồn tại fkey!`);
        //         }
        //     });

        //     this.loading(false);
        // },
        generateReport() {
            this.$refs.html2Pdf.generatePdf();
            this.$bvModal.hide("popup-taifile");
        },
        onModalShown() {
            setTimeout(() => { this.generateReport() }, 500)
        },
        onExcelExport() {
            if (this.listHoaDon.length === 0) {
                this.$root.toastError("Chưa có danh sách để xuất excel!");
                return;
            } else {
                var wb = xlsx.utils.book_new();
                const dsSelected = this.$refs.dsHoaDon.getSelectedRecords() || [];
                if (dsSelected.length > 0) {
                    this.appendSheet(dsSelected, 0, wb);
                } else {
                    this.$root.toastError("Vui lòng chọn hàng phiếu muốn in!");
                    return;
                }
            }
        },

        appendSheet(items, type, workbook) {
            this.loading(true);
            try {
                let sheetTitle = "Danh sách phiếu";
                var ds = xlsx.utils.json_to_sheet(items);

                xlsx.utils.book_append_sheet(workbook, ds, sheetTitle);
                xlsx.writeFile(
                    workbook,
                    `Export_${moment(new Date()).format("DD/MM/YYYY HH:mm:ss")}.xlsx`
                );
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },

        sendData(data) {
            console.log({ data });
            this.listHoaDon = data;
        },

        docFile() {
            this.$bvModal.show("modal-docfile");
        }
    }
};
</script>
