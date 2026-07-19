<template src="./LookUpCrossSellDivisionInfo.html"></template>
<script>
import api from './api';
import moment from 'moment';
import XLSX from 'xlsx'
import breadcrumb from '@/components/breadcrumb'
import popupAttachFiles from './popupAttachFiles.vue';
import "./LookUpCrossSellDivisionInfo.css";

export default {
    name: "LookUpCrossSellDivisionInfo",
    components: {
        breadcrumb,
        popupAttachFiles
    },
    data() {
        return {
            dsTieuChi: [
                { "id": "1", "name": "Mã số thuế" },
                { "id": "2", "name": "Tên khách hàng" }
            ],
            dsPhanLoaiHd: [
                { "id": "1", "name": "Hợp đồng ký tập trung/đa điểm" },
                { "id": "2", "name": "Hợp đồng ký gom kênh/ thuê bao" },
                { "id": "3", "name": "Hợp đồng ký qua Telcos/ Reseller" },
                { "id": "4", "name": "Hợp đồng bán chéo" }
            ],
            CongVanIdSelected: '',
            dsDonViBhCha: [],
            dsDonViBhCon: [],
            dsFileDinhKem: [],
            searchInfo: {
                chkSoCv: false,
                chkDonViPhatHanh: false,
                chkTenCv: false,
                chkNgayQd: false,
                chkTraCuuTheo: false,
                chkDonViBh: false,
                chkPhanLoaiHd: false,
                soCv: '',
                donViPhatHanh: '',
                tenCv: '',
                ngayTu: moment(moment(new Date()).subtract(1, 'months').startOf('month').toDate()).format("DD/MM/YYYY"),
                ngayDen: moment(new Date()).format("DD/MM/YYYY"),
                traCuuSelected: 1,
                traCuuGiaTri: '',
                donvi_cha_id: '999999',
                donvi_con_id: '999999',
                phanLoaiHd: '1'
            },
            dgCongVan: {
                dataSource: [],
                selectionOptions: { type: 'Single', persistSelection: false, checkboxMode: 'ResetOnRowClick' },
                columns: [
                    {
                        fieldName: "ma_coche",
                        headerText: "Mã cơ chế",
                        allowFiltering: true
                    },
                    {
                        fieldName: "socongvan",
                        headerText: "Số công văn",
                        allowFiltering: true,
                    },
                    {
                        fieldName: "ten_cv",
                        headerText: "Tên công văn",
                        allowFiltering: true
                    },
                    {
                        fieldName: "ngay_qd",
                        headerText: "Ngày quyết định",
                        allowFiltering: true
                    },
                    {
                        fieldName: "donvi_ph",
                        headerText: "Đơn vị phát hành",
                        allowFiltering: true
                    },
                    {
                        fieldName: "nguoi_cn",
                        headerText: "Người nhập",
                        allowFiltering: true
                    },
                    {
                        fieldName: "ngay_cn",
                        headerText: "Ngày nhập",
                        allowFiltering: true
                    },
                    {
                        fieldName: "donvi_bh",
                        headerText: "Đon vị bán hàng",
                        allowFiltering: true
                    },
                    {
                        fieldName: "ten_kh",
                        headerText: "Tên khách hàng",
                        allowFiltering: true
                    },
                    {
                        fieldName: "mst",
                        headerText: "Mã số thuế",
                        allowFiltering: true
                    },
                    {
                        fieldName: "phanloai_hd",
                        headerText: "Phân loại HĐ",
                        allowFiltering: true
                    },
                    {
                        fieldName: "ds_loaihd",
                        headerText: "Loại HĐ",
                        allowFiltering: true
                    },
                    {
                        fieldName: "sl_tb",
                        headerText: "Số lượng thuê bao",
                        allowFiltering: true
                    }
                ]
            },
            dgPhanChiaDt: {
                dataSource: [],
                selectionOptions: { type: 'Single', persistSelection: false },
                columns: [
                    { fieldName: "ten_dvvt", headerText: "Dịch vụ", allowFiltering: true },
                    { fieldName: "loaihinh_tb", headerText: "Loại hình", allowFiltering: true },
                    { fieldName: "loai_pt", headerText: "Loại phát triển", allowFiltering: true },
                    { fieldName: "ten_dv", headerText: "Đơn vị", allowFiltering: true },
                    { fieldName: "congdoan", headerText: "Công đoạn", allowFiltering: true },
                    { fieldName: "tyle", headerText: "Tỷ lệ PCDT", allowFiltering: true },
                    { fieldName: "tungay", headerText: "Từ ngày", allowFiltering: true },
                    { fieldName: "denngay", headerText: "Đến ngày", allowFiltering: true }
                ]
            },
            dgCoCheBanCheo: {
                dataSource: [],
                selectionOptions: { type: 'Single', persistSelection: false },
                columns: [
                    { fieldName: "ma_gd", headerText: "Mã giao dịch", allowFiltering: true },
                    { fieldName: "ten_loaihd", headerText: "Loại hợp đồng", allowFiltering: true },
                    { fieldName: "ngay_yc", headerText: "Ngày yêu cầu", allowFiltering: true },
                    { fieldName: "ma_tb", headerText: "Số máy/Acc", allowFiltering: true },
                    { fieldName: "ten_dvvt", headerText: "Dịch vụ", allowFiltering: true },
                    { fieldName: "loaihinh_tb", headerText: "Loại hình", allowFiltering: true },
                    { fieldName: "loai_pt", headerText: "Loại phát triển", allowFiltering: true },
                    { fieldName: "ten_dv", headerText: "Đơn vị", allowFiltering: true },
                    { fieldName: "congdoan", headerText: "Công đoạn", allowFiltering: true },
                    { fieldName: "tyle", headerText: "Tỷ lệ PCDT", allowFiltering: true },
                    { fieldName: "tungay", headerText: "Từ ngày", allowFiltering: true },
                    { fieldName: "denngay", headerText: "Đến ngày", allowFiltering: true }
                ]
            }
        }
    },
    methods: {
        async initForm() {
            await this.layDsTieuChiTraCuu();
            await this.layDsPhanLoaiHd();
            await this.getDonViTheoND();
            await this.timKiem();
        },
        async layDsTieuChiTraCuu() {
            this.dsTieuChi = [
                { "id": "1", "name": "Mã số thuế" },
                { "id": "2", "name": "Tên khách hàng" }
            ];
        },
        async layDsPhanLoaiHd() {
            this.dsPhanLoaiHd = [
                { "id": "1", "name": "Hợp đồng ký tập trung/đa điểm" },
                { "id": "2", "name": "Hợp đồng ký gom kênh/ thuê bao" },
                { "id": "3", "name": "Hợp đồng ký qua Telcos/ Reseller" },
                { "id": "4", "name": "Hợp đồng bán chéo" }
            ];
        },
        async getDonViTheoND() {
            // this.loading(true);
            let apiParams = {
                v_ma_nd: this.$root.token.getUserName(),
            };
            api.getDonViIDTheoND(this.axios, apiParams)
                .then((response) => {
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined &&
                        response.data.data.length > 0
                    ) {
                        let donviIDCHA = response.data.data;
                        this.loadDonViGoc(donviIDCHA);
                    }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {
                    // this.loading(false);
                });
        },
        async loadDonViGoc(donvi_nguoidung) {
            this.dsDonViBhCha = [{ id: "999999", label: "-Chọn tất cả-" }];
            api.getDonviBh(this.axios)
                .then((response) => {
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined &&
                        response.data.data.length > 0
                    ) {
                        let items = [];
                        if (response.data.data !== undefined) {
                            response.data.data.forEach(function (item) {
                                items.push({
                                    id: item.donvi_id,
                                    label: item.ten_dv,
                                    // pid: item.DONVI_CHA_ID,
                                    // children: [],
                                });
                            });
                            // this.dsDonViBhCha = items.filter((x) => x.id == donvi_nguoidung || x.id=="");
                            // this.dsDonViBhCha.splice(0, {id : "", label : "-Chọn tất cả-"});
                        }
                        this.dsDonViBhCha.push(...items);
                    }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {
                    this.loading(false);
                });
        },
        async getDonViCon(donvi_cha_id) {
            this.dsDonViBhCon = [{ id: "999999", label: "-Chọn tất cả-" }];
            api.getDonviCon(this.axios, donvi_cha_id)
                .then((response) => {
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined &&
                        response.data.data.length > 0
                    ) {
                        let items = [];
                        if (response.data.data !== undefined) {
                            response.data.data.forEach(function (item) {
                                items.push({
                                    id: item.donvi_id,
                                    label: item.ten_dv
                                });
                            });
                        }
                        this.dsDonViBhCon.push(...items);
                    }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {
                    this.loading(false);
                });
        },
        async onChangeDonViCha() {
            if (this.searchInfo.donvi_cha_id == "999999")
                this.searchInfo.donvi_con_id = "999999";
            await this.getDonViCon(this.searchInfo.donvi_cha_id);
        },
        async timKiem() {
            let donvi_bh_cha = 0;
            let donvi_bh_con = 0;
            if (this.searchInfo.chkDonViBh == true) {
                donvi_bh_cha = (this.searchInfo.donvi_cha_id == "999999") ? 0 : this.searchInfo.donvi_cha_id;
                donvi_bh_con = (this.searchInfo.donvi_con_id == "999999") ? 0 : this.searchInfo.donvi_con_id;
            }
            this.$root.loading(true);
            this.dgCongVan.dataSource = [];
            this.dgPhanChiaDt.dataSource = [];
            this.dgCoCheBanCheo.dataSource = [];
            let params = {
                p_socongvan: (this.searchInfo.chkSoCv == true) ? this.searchInfo.soCv : "0",
                p_donvi_ph: (this.searchInfo.chkDonViPhatHanh == true) ? this.searchInfo.donViPhatHanh : "0",
                p_ten_congvan: (this.searchInfo.chkTenCv == true) ? this.searchInfo.tenCv : "0",
                p_tungay: (this.searchInfo.chkNgayQd == true) ? this.searchInfo.ngayTu : "0",
                p_denngay: (this.searchInfo.chkNgayQd == true) ? this.searchInfo.ngayDen : "0",
                p_type: (this.searchInfo.chkTraCuuTheo == true) ? this.searchInfo.traCuuSelected : 0,
                p_value: (this.searchInfo.chkTraCuuTheo == true) ? this.searchInfo.traCuuGiaTri : "0",
                p_donvi_bh_cha: donvi_bh_cha,
                p_donvi_id: donvi_bh_con,
                p_phanloai_hd_id: (this.searchInfo.chkPhanLoaiHd == true) ? this.searchInfo.phanLoaiHd : 0
            };
            api.getDsCongVanBanCheo(this.axios, params)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0) {
                        this.dgCongVan.dataSource = response.data.data;
                        this.$root.loading(false);
                    }
                }).catch((error) => {
                    console.log(error);
                    this.$root.loading(false);
                })
                .finally(() => {
                    this.loading(false);
                });
        },
        async taiFileDinhKem() {
            this.getDsFileDinhKem(this.CongVanIdSelected);
        },
        async getDsPhanChiaDoanhThu(coche_id) {
            this.dgPhanChiaDt.dataSource = [];
            api.getDsPhanChiaDoanhThu(this.axios, coche_id)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0) {
                        this.dgPhanChiaDt.dataSource = response.data.data;
                    }
                }).catch((error) => {
                    console.log(error);
                })
                .finally(() => {
                    this.loading(false);
                });
        },
        async getDsGanCoCheBanCheoChoThueBao(coche_id) {
            this.$root.loading(true);
            this.dgCoCheBanCheo.dataSource = [];            
            api.getDsCoCheBanCheoChoThueBao(this.axios, coche_id)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0) {
                        this.dgCoCheBanCheo.dataSource = response.data.data;
                    }
                    this.$root.loading(false);
                }).catch((error) => {
                    console.log(error);
                    this.$root.loading(false);
                })
                .finally(() => {
                    this.loading(false);
                });
        },
        async onDgCongVan_SelectedItemsChanged(row) {
            this.CongVanIdSelected = 0;
            this.dgPhanChiaDt.dataSource = [];
            this.dsFileDinhKem = [];
            if (row !== undefined) {
                let coche_id = row['coche_id'];
                let p_hdkh_id = row['hdkh_id'];
                let p_so_cv = row['socongvan'];
                let p_macoche = row['ma_coche'];
                let p_congvan_id = row['congvan_id'];
                this.CongVanIdSelected = row['congvan_id'];

                await this.getDsPhanChiaDoanhThu(coche_id);
                await this.getDsGanCoCheBanCheoChoThueBao(coche_id);
            }
        },
        async getDsFileDinhKem(p_congvan_id) {
            if (this.CongVanIdSelected == 0) {
                this.$toast.warning('Vui lòng chọn công văn bán chéo');
                return;
            }
            api.getDsFileDinhKem(this.axios, p_congvan_id)
                .then((response) => {

                    if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0) {
                        this.dsFileDinhKem = response.data.data;
                    }
                    this.$bvModal.show('popupAttachFiles');
                    // this.$root.loading(false);
                }).catch((error) => {
                    console.log(error);
                    this.dsFileDinhKem = [];
                    // this.$root.loading(false);
                })
                .finally(() => {
                    this.loading(false);
                });
        },
        thayDoiCheck(num) {
            //1: số công văn; 2: đơn vị phát hành; 3: tên công văn; 4: ngày quyết định
            // 5: tra cứu theo; 6. đơn vị bán hàng; 7.phân loại hđ
            if (num == 1) this.chkSoCv = !this.chkSoCv;
            if (num == 2) this.chkDonViPhatHanh = !this.chkDonViPhatHanh;
            if (num == 3) this.chkTenCv = !this.chkTenCv;
            if (num == 4) this.chkNgayQd = !this.chkNgayQd;
            if (num == 5) this.chkTraCuuTheo = !this.chkTraCuuTheo;
            if (num == 6) this.chkDonViBh = !this.chkDonViBh;
            if (num == 7) this.chkPhanLoaiHd = !this.chkPhanLoaiHd;
        },
        async xuatFile() {
            this.exportExcelByCols(this.dgCongVan.dataSource, this.dgCongVan.columns);
        },
        async xuatFile_Ccbc() {
            this.exportExcelByCols(this.dgCoCheBanCheo.dataSource, this.dgCoCheBanCheo.columns);
        },
        exportExcelByCols(inputArray, arrayFields) {
            var exportData = []
            exportData = inputArray.map((e) => {
                var exportEntity = {}
                arrayFields.map((x) => {
                    exportEntity[x.fieldName] = e[x.fieldName]
                })
                return exportEntity
            })
            if (exportData.length > 0) {
                let worksheet = XLSX.utils.json_to_sheet(exportData)
                let workbook = XLSX.utils.book_new()
                XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
                XLSX.writeFile(workbook, 'export.xlsx')
            } else {
                this.$toast.warning('Không có dữ liệu để xuất file!')
            }
        }
    },
    async mounted() {
        await this.initForm();
    }
}
</script>