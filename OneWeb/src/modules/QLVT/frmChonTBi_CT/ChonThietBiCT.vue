<template src="./ChonThietBiCT.html"> </template>

<script>
import API from "../api/ChonThietBiCT.js";
export default {
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            vkho_id: "",
            xacnhan: false,
            vcongtrinh_id: "",
            vchungtu_id: "",
            bqlda: false, // nếu là điều chỉnh bqlda thì = true (menu nhapxuat v2)
            dt_ct: [],
            cboCongTrinh: [],
            cboCongTrinhValue: "",
            dtVatTu: [],
            dtVatTuItemSelecteds: [],
            dtDSVTGiao: [],
            dtDSVTGiaoItemSelecteds: [],
            btnShowHuyAndHC: false,
            btnXacNhanEnabled: false
        };
    },
    watch: {},

    methods: {
        openDialog() {
            this.$refs.dlgChonTBCT.show();
        },
        dialogOpen() {
            // this.vchungtu_id = 59004;
            this.NAP_DANHSACH();
        },
        reset() {
            this.vkho_id = "";
            this.xacnhan = false;
            this.vcongtrinh_id = "";
            this.vchungtu_id = "";
            this.bqlda = false;
            this.dt_congtrinh = [];
            this.dt_ct = [];
            this.cboCongTrinh = [];
            this.cboCongTrinhValue = "";
            this.dtVatTu = [];
            this.dtDSVTGiao = [];
            this.dtDSVTGiaoItemSelecteds = [];
            this.dtVatTuItemSelecteds = [];
            //design
            this.btnShowHuyAndHC = false;
            this.btnXacNhanEnabled = false;
        },
        btnLayTT() {
            this.NAP_DANHSACH();
        },
        btnXacNhan() {
            if (this.dtDSVTGiao.length <= 0) {
                return this.$toast.error(
                    "Không có thông tin vật tư để xác nhận"
                );
            }
            if (this.dtVatTu.length <= 0) {
                return this.$toast.error(
                    "Không có thông tin vật tư chứng từ không thể xác nhận"
                );
            }
            let dsDTVTGiao = this.dtDSVTGiao.filter(e =>
                this.dtDSVTGiaoItemSelecteds.includes(e.STT)
            );
            if (dsDTVTGiao.length <= 0) {
                return this.$toast.error("Bạn chưa chọn thiết bị để xác nhận");
            }
            this.dtVatTu.forEach(e => {
                e["SL_CHON"] = 0;
            });
            dsDTVTGiao.forEach((dr, index) => {
                let soluong = dr.LUONGCAP;
                let ctct_id = 0;
                this.dtVatTu.forEach(dr_v => {
                    if (
                        dr["VATTU_ID"] == dr_v["VATTU_ID"] &&
                        dr["LOHANG"] == dr_v["LOHANG"]
                    ) {
                        let sl_cl = dr_v["SOLUONG"];
                        let sl_chuyen = dr_v["SL_CHON"];
                        if (soluong >= sl_cl - sl_chuyen) {
                            dr_v["SL_CHON"] = sl_chuyen + sl_cl - sl_chuyen;
                            soluong = soluong - (sl_cl - sl_chuyen);
                            ctct_id = dr_v["CTCT_ID"];
                        } else {
                            dr_v["SL_CHON"] = sl_chuyen + soluong;
                            soluong = 0;
                            ctct_id = dr_v["CTCT_ID"];
                        }
                    }
                });

                dr["LUONGCAP"] = dr["LUONGCAP"] - soluong;
                if (dr["LUONGCAP"] <= 0) {
                    this.$refs.gridDSVTGiao.selectRow(index, true);
                    dr["ISCHECK"] = "0";
                }
                dr["CTCT_CT_ID"] = ctct_id;
            });
            this.btnXacNhanEnabled = false;
            this.btnShowHuyAndHC = true;
        },
        btnHuyXN() {
            this.btnXacNhanEnabled = true;
            this.btnShowHuyAndHC = false;
        },
        async btnHoanCong() {
            if (this.dtVatTu.length <= 0) return;
            if (this.dtVatTu.filter(e => e.SL_CHON != e.SOLUONG).length > 0) {
                return this.$toast.error("Số lượng bạn chọn chưa khớp");
            }
            if (this.dtVatTu.filter(e => e.SL_CHON == 0).length > 0) {
                return this.$toast.error("Bạn chưa chọn hết vật tư");
            }
            try {
                this.loading(true);
                let data = {
                    khoId: this.vkho_id,
                    dsVt: this.dtDSVTGiao,
                    congTrinhId: this.vcongtrinh_id,
                    chungTuId: this.vchungtu_id
                };
                let response = await API.postDieuChuyenVTCT(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data == 1) {
                        this.xacnhan = true;
                        this.$emit("closed", true);
                        return this.$refs.dlgChonTBCT.hide();
                    }
                    this.$toast.error(response.data.data);
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false);
            }
        },

        async NAP_DANHSACH() {
            try {
                this.loading(true);
                this.btnXacNhanEnabled = true;
                this.btnShowHuyAndHC = false;
                let response = await API.getDSVTChungTu(this.axios, {
                    chungTuId: this.vchungtu_id
                });
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined &&
                    response.data.data.length > 0
                ) {
                    response.data.data.forEach((e, index) => {
                        e["STT"] = index;
                        e["SL_CHON"] = 0;
                    });
                    this.dtVatTu = response.data.data;
                    let ds = this.dtVatTu.map(e => {
                        return {
                            LOHANG: e.LOHANG,
                            VATTU_ID: e.VATTU_ID
                        };
                    });
                    let data = {
                        khoId: this.vkho_id,
                        ds: ds,
                        congTrinhId: this.vcongtrinh_id,
                        nhanVienId: this.$auth.getNhanVienID(),
                        bqlDa: this.bqlda

                        //  khoId: 10134, //this.vkho_id,
                        // ds: [{ LOHANG: "524684", VATTU_ID: 13067 }], // ds,
                        // congTrinhId: 0, // this.vcongtrinh_id,
                        // nhanVienId: 1, //this.$auth.getNhanVienID(),
                        // bqlDa: this.bqlda
                    };
                    let responsea = await API.postDSVTGiao(this.axios, data);
                    if (
                        responsea.data.error_code === "BSS-00000000" &&
                        responsea.data.data !== undefined
                    ) {
                        responsea.data.data.forEach((e, index) => {
                            e["STT"] = index;
                        });

                        this.dtDSVTGiao = responsea.data.data;
                        this.NAP_COMBO_CT(this.dtDSVTGiao);
                    }
                }
            } catch (error) {
                this.$toast.error(error.data.message_detail);
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        NAP_COMBO_CT(ds) {
            if (ds.length > 0) {
                let distinctCT = [];
                ds.forEach(e => {
                    if (e.CONGTRINH_ID != "" && e.CONGTRINH_ID != null) {
                        distinctCT.push({
                            CONGTRINH_ID: e.CONGTRINH_ID,
                            TEN_CT: e.TEN_CT
                        });
                    }
                });
                const arrayUniqueByKey = [
                    ...new Map(
                        distinctCT.map(item => [item["CONGTRINH_ID"], item])
                    ).values()
                ];
                this.cboCongTrinh = arrayUniqueByKey;
            } else {
                this.cboCongTrinh = [];
            }
        },
        cboCongTrinhSelectChanged(value) {
            if (value) {
                this.cboCongTrinhValue = value.CONGTRINH_ID;
            }
        },
        selectedItemsGridDSVTGiao(value) {
            if (value) {
                if (value.length == 0) {
                    if (this.dtDSVTGiao && this.dtDSVTGiao.length > 0) {
                        this.dtDSVTGiao.forEach((e, index) => {
                            e.LUONGCAP = 0;
                            e.ISCHECK = 0;
                            this.$refs.gridDSVTGiao.grid.updateCell(
                                index,
                                "LUONGCAP",
                                0
                            );
                        });
                    }
                } else {
                    this.dtDSVTGiao.forEach((e, index) => {
                        if (value.includes(e.STT)) {
                            e.LUONGCAP = e.SL_TON;
                            e.ISCHECK = 1;
                            this.$refs.gridDSVTGiao.grid.updateCell(
                                index,
                                "LUONGCAP",
                                e.SL_TON
                            );
                        }
                    });
                }
                if (value) {
                    this.dtDSVTGiaoItemSelecteds = value;
                }
            }
        },
        cellSavedGridDSVTGiao(args) {
            if (args.rowData) {
                if (args.columnName == "LUONGCAP") {
                    this.dtDSVTGiao.forEach((e, index) => {
                        if (e.STT == args.rowData.STT) {
                            e.LUONGCAP = args.value;
                        }
                    });
                }
                let slgiao = args.value;
                let slton = args.rowData.SL_TON;
                let serial = args.rowData.SERIAL;
                if (serial == null) serial = "";
                if (slgiao != 0 && slton != 0) {
                    if (serial != "" && slgiao >= 0 && slgiao < 1) {
                        this.$refs.gridDSVTGiao.selectRow(
                            args.rowData.STT,
                            true
                        );
                    } else {
                    }
                } else if (slgiao == 0) {
                    this.$refs.gridDSVTGiao.selectRow(args.rowData.STT, true);
                }
            }
        },
        rowClickedGridDSVTGiao(...args) {},
        gridVatTuCellSaved(args) {
            let KEY = args.rowData.STT;
            let value = args.value;
            if (args.columnName == "TIEN") {
                this.dtVatTu.forEach(element => {
                    if (element.STT == KEY) {
                        element.TIEN = value;
                        return;
                    }
                });
            } else if (args.columnName == "SOTHANG_PB") {
                this.dtVatTu.forEach(element => {
                    if (element.STT == KEY) {
                        element.SOTHANG_PB = value;
                        return;
                    }
                });
            }
        },
        gridVatTuCommandClicked(e) {
            if (e.target.title === "EDIT") {
            } else if (e.target.title === "DELETE") {
            }
        }
    }
};
</script>

<style></style>
