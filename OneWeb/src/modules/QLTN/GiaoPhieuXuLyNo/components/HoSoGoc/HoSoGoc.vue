<template src="./HoSoGoc.html"></template>

<script>
import { CollumnsHoSoXuLyGoc } from "../../common";
import API from "../../../api/GiaoPhieuXuLyNo";
export default {
    props: {
        vma_kh: {
            type: String,
            default: ""
        },
        xulyno_id: {
            type: String,
            default: ""
        },
        buoc_xln_id: {
            type: String,
            default: ""
        }
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            cacmatb: "",
            cacchukyno: "",
            matb_hienthi: "",
            vsochukylchon: 0,
            cacthangno: "",
            vkieutra: 0, //0: Trả theo mã thanh toán, 1: Trả theo mã thuê bao.
            vsoluong_tb: 0, //Số lượng mã thuê bao trong mã thanh toán.
            dt_thuebao: [],
            //components:
            txtMaKH: "",
            grid_Dsthuebao: [],
            grid_DsthuebaoItemsSelected: [],
            grid_DsthuebaoCollumn: CollumnsHoSoXuLyGoc
        };
    },
    methods: {
        dialogOpen() {
            this.$refs.dlgHoSoGoc.show();
            this.txtMaKH = this.vma_kh;
            this.HienThiDanhSach();
        },
        dialogClose() {
            this.$refs.dlgHoSoGoc.hide();
            this.reset();
        },
        btnLayDS() {
            this.HienThiDanhSach();
        },
        reset() {
            this.txtMaKH = "";
            this.grid_Dsthuebao = [];
            this.vma_kh = "";
            this.xulyno_id = "";
            this.buoc_xln_id = "";
        },
        async HienThiDanhSach() {
            try {
                this.loading(true);
                let data = {
                    pMaKH: this.vma_kh,
                    pXuLyNoId: this.xulyno_id
                };
                let response = await API.getDSTBNoTheoMaKH(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data.length > 0) {
                        for (const key in response.data.data) {
                            response.data.data.KEY = key;
                            if (
                                response.data.data[key].TRANGTHAI ==
                                "Đã có hồ sơ gốc"
                            ) {
                                this.$refs.dtgridDSTB.selectedValues.push(key);
                            }
                        }
                        this.grid_Dsthuebao = response.data.data;
                    }
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        btnGhiLai() {
            this.CapNhat();
        },
        async CapNhat() {
            let dsChon = [];
            this.grid_DsthuebaoItemsSelected.forEach(item => {
                this.grid_Dsthuebao(e => {
                    if (item == e.KEY) {
                        dsChon.push(e);
                        return;
                    }
                });
            });
            if (dsChon.length > 0) {
                let dataDS = [];
                dsChon.forEach(element => {
                    dataDS.push({
                        ma_tb: element.MA_TB,
                        dichvuvt_id: element.DICHVUVT_ID,
                        loaitb_id: element.LOAITB_ID
                    });
                });
                let body = {
                    pXuLyNoId: this.xulyno_id,
                    pBuocXLNId: this.buoc_xln_id,
                    pDanhSachThueBao: JSON.stringify(dataDS),
                    pMayCN: null
                };
                try {
                    this.loading(true);
                    let response = await API.postGhiLaiHoSoGocXLN(
                        this.axios,
                        body
                    );
                    if (
                        response.data.error_code === "BSS-00000000" &&
                        response.data.data !== undefined
                    ) {
                        this.$toast.success("Cập nhật dữ liệu thành công!");
                    }
                } catch (error) {
                    this.$toast.error(error.data.message_detail);
                } finally {
                    this.loading(false);
                }
            } else {
                this.$toast.error("Bạn chưa chọn thuê bao!");
            }
            this.HienThiDanhSach();
        },
        isNullEmpty(value) {
            return value === null || value === "";
        },
        txtMaKHEnter() {
            if (this.isNullEmpty(this.txtMaKH)) return;
            this.vma_kh = this.txtMaKH.trim();
            HienThiDanhSach();
        },
        selectedItemsChangedDSTB(value) {
            console.log(value);
            this.grid_DsthuebaoItemsSelected = value;
        },
        formatJson(filterVal, jsonData) {
            return jsonData.map(v =>
                filterVal.map(j => {
                    return v[j];
                })
            );
        },
        btnExportExcel() {
            if (this.grid_Dsthuebao.length == 0)
                return this.$toast.error("Không có dữ liệu để xuất Excel");
            exportExcelCustom(
                this.grid_DsthuebaoCollumn,
                this.grid_Dsthuebao,
                "HoSoGoc",
                "export"
            );
        }
    }
};
</script>

<style src="./HoSoGoc.scss"></style>
