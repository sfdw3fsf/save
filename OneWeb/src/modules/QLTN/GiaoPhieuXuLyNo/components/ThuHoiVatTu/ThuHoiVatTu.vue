<template src="./ThuHoiVatTu.html"></template>

<script>
import API from "../../../api/GiaoPhieuXuLyNo";
import { GridDSThueBaoCollumns } from "./common";
import moment from "moment";
import exportExcelCustom from "../../utils/ExportExcel";
export default {
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            bienban_id: null,
            chapnhan: false,
            ds_thtbi: [],
            dt: [],
            lookupKetQua: [],
            //
            gridDSThueBao: [],
            gridDSThueBaoCollumn: GridDSThueBaoCollumns,
            gridDSThueBaoSelected: []
        };
    },
    methods: {
        async funcDSKQThuHoi() {
            try {
                this.loading(true);
                let response = await API.getDSKQThuHoi(this.axios, {});
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.lookupKetQua = response.data.data;
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        async hienThiDanhSach() {
            try {
                this.loading(true);
                let data = {
                    pBienBanId: this.bienban_id
                };
                let response = await API.getTTSDThietBi(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.dt = response.data.data;
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
            if (this.ds_thtbi.length > 0) {
                for (let i = 0; i < this.ds_thtbi.length; i++) {
                    for (let j = 0; j < this.dt.length; j++) {
                        if (
                            this.ds_thtbi[i].THUEBAO_ID == this.dt[j].THUEBAO_ID
                        ) {
                            this.dt[j].KETQUA_ID = this.ds_thtbi[i].KETQUA_ID;
                            this.dt[j].GHICHU = this.ds_thtbi[i].GHICHU;
                            this.dt[j].SL = this.ds_thtbi[i].SL;
                        }
                    }
                }
            }
            if (this.dt.length > 0) {
                for (let index = 0; index < this.dt.length; index++) {
                    this.dt[index].CHON = index;
                }
                this.gridDSThueBao = this.dt;
            } else {
                this.gridDSThueBao = [];
            }
        },
        async openDialog() {
            await this.funcDSKQThuHoi();

            await this.hienThiDanhSach();
            this.$refs.dlgThuHoiVatTuGP.show();
        },
        reset() {
            this.bienban_id = null;
            this.chapnhan = false;
            this.ds_thtbi = [];
            this.dt = [];
            this.lookupKetQua = [];
            this.gridDSThueBao = [];
        },
        selectedItemsChangedDSThueBao(value) {
            this.gridDSThueBaoSelected = value;
        },
        btnChapNhan() {
            ChapNhan();
            this.$emit(
                "confirmEvent",
                this.chapnhan,
                this.ds_thtbi,
                this.bienban_id
            );
            this.$refs.dlgThuHoiVatTuGP.hide();
            this.reset();
        },
        ChapNhan() {
            this.chapnhan = true;
            let lstDSTB = [];
            this.gridDSThueBao.forEach(element => {
                this.gridDSThueBaoSelected.forEach(item => {
                    if (item.CHON == element) {
                        lstDSTB.push(item);
                        return;
                    }
                });
            });
            if (lstDSTB.length <= 0) {
                this.$toast.error(
                    "Bạn chưa chọn danh sách trên lưới để cập nhật!."
                );
                return;
            }
            this.TaoDL_THUHOI_TBI_DATA();
        },
        TaoDL_THUHOI_TBI_DATA() {
            try {
                let lstDSTB = [];
                this.gridDSThueBao.forEach(element => {
                    this.gridDSThueBaoSelected.forEach(item => {
                        if (item.CHON == element) {
                            lstDSTB.push(item);
                            return;
                        }
                    });
                });
                for (const item of lstDSTB) {
                    let temp = {};
                    temp.BIENBAN_ID = this.bienban_id;
                    temp.THUEBAO_ID = item.THUEBAO_ID;
                    temp.MA_TB = item.MA_TB;
                    temp.TRANGTHAITB_ID = item.TRANGTHAITB_ID;
                    temp.LOAITBI_ID = item.LOAITBI_ID;
                    temp.SL = item.SL;
                    temp.KETQUA_ID = item.KETQUA_ID;
                    temp.GHICHU = item.GHICHU;
                    temp.NGAY_TH = moment(new Date()).format("DD/MM/YYYY");
                    temp.NHANVIEN_ID = this.$auth.getNhanVienID();
                    this.ds_thtbi.push(temp);
                }
            } catch (error) {}
        },
        btnLayDS() {
            this.hienThiDanhSach();
        },
        formatJson(filterVal, jsonData) {
            return jsonData.map(v =>
                filterVal.map(j => {
                    return v[j];
                })
            );
        },
        btnExportExcel() {
            if (this.gridDSThueBao.length == 0)
                return this.$toast.error("Không có dữ liệu để xuất Excel");
            exportExcelCustom(
                this.gridDSThueBaoCollumn,
                this.gridDSThueBao,
                "Danh sách",
                "file"
            );
        }
    }
};
</script>

<style src="./ThuHoiVatTu.scss"></style>
