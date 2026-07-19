<template src="./InThongBaoCuoc.html"></template>
<style src="./InThongBaoCuoc.scss" scoped></style>
<script>
import { previewPrint } from "../../../utils/util";

import moment from "moment";
export default {
    created: async function () {
        this.loadTreeView();
    },
    data: function () {
        return {
            inv_version: [],
            inv_list: [],
            inv_condition: [],
            inv_orders: [],
            ver_id: 0,
            inv_id: 0,
            order_id: 0,
            Tree_view: [],
            tree_leaves: 0,
            date: {
                dateVal: new Date(new Date().setDate(2)),
                dateFormat: "dd/MM/yyyy",
                monthVal: moment(new Date()).subtract(1, "months").endOf("month").toDate()
            },
            con_1: "",
            con_2: "",
            con_3: "",
            con_4: "",
            con_5: "",
            con_6: "",
            con_7: "",
            con_8: "",
            con_9: "",
            con_10: "",
            con_11: "",
            con_12: "",
            con_13: "",
            con_14: "",
            con_15: "NO_TONG>0",
            con_16: "0=0",
            con_17: "",
            con_18: "0=0",
            con_19: "0=0",
            con_22: "0=0",
            noidung_in: null,
            isDisableView: true,
            isDisableExport: true,
            isDisablePrint: true,
            dsMaTT: '',
        }

    },
    methods: {
        loadTreeView: async function () {
            try {
                const rs = await this.$root.context.get(
                    `/web-thuno/api/thu-no/in-hoa-don/in-bao-cuoc-phien-ban`
                );
                this.inv_version = rs.data;

                const rs1 = await this.$root.context.get(
                    `/web-thuno/api/thu-no/in-hoa-don/in-bao-cuoc-chuc-nang`
                );
                this.inv_list = rs1.data;
            } catch (error) { }
        },
        item_child: function (value) {
            var list_item = this.inv_list.filter((item) => item.VER_ID === value);
            return list_item;
        },
        load_condition: async function (ver_id, inv_id) {
            try {
                const rs = await this.$root.context.get(
                    `/web-thuno/api/thu-no/in-hoa-don/in-bao-cuoc-condition?verId=${ver_id}&invId=${inv_id}`
                );
                this.inv_condition = rs.data;
                this.inv_condition.map(item=>{
                    if(item.REPLACE_STRING == "KIEUIN"){
                        item.DATA_FIELD.sort((a,b)=> a.TT - b.TT)
                    }
                });
                this.con_2 = '';
                this.con_3 = "";
                this.con_4 = "";
                this.con_5 = "";
                this.con_6 = "";
                this.con_7 = "";
                this.con_8 = "";
                this.con_9 = "";
                this.con_10 = "";
                this.con_11 = "";
                this.con_12 = "";
                this.con_13 = "";
                this.con_14 = "";
                this.con_15 = "";
                this.con_16 = "";
                this.con_17 = "";
                this.con_18 = "";
                this.con_19 = "";
                this.con_22 = "";
                this.change_Date(1,{value:this.date.monthVal})
                // this.change_Date(2,new Date())
                let now = new Date()
                let dateP = now.setDate(2)
                this.change_Date(3,{value: new Date(dateP)})
                for (const item in this.inv_condition) {
                    if (this.inv_condition[item].C_TYPE === 3) {
                        if (this.inv_condition[item].C_ID === 2)
                            this.con_2 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 3)
                            this.con_3 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 4)
                            this.con_4 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 5)
                            this.con_5 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 6)
                            this.con_6 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 7)
                            this.con_7 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 8)
                            this.con_8 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 9)
                            this.con_9 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 10)
                            this.con_10 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 11)
                            this.con_11 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 12)
                            this.con_12 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 13)
                            this.con_13 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 14)
                            this.con_14 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 15)
                            this.con_15 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 16)
                            this.con_16 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 17)
                            this.con_17 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 18)
                            this.con_18 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 19)
                            this.con_19 = this.inv_condition[item].DATA_FIELD[0].TT;

                        if (this.inv_condition[item].C_ID === 22)
                            this.con_22 = this.inv_condition[item].DATA_FIELD[0].TT;

                    }
                }

                const rs1 = await this.$root.context.get(
                    `/web-thuno/api/thu-no/in-hoa-don/in-bao-cuoc-data-order?verId=${ver_id}&invId=${inv_id}`
                );
                this.inv_orders = rs1.data;
                this.ver_id = ver_id;
                this.inv_id = inv_id;
                this.thongtin_in = null;
            } catch (error) { }
        },
        onTaoBanKe: async function () {
            let dieukien = {
                1: this.con_1,
                2: this.con_2,
                3: this.con_3,
                4: this.con_4.indexOf(',') === 0 ? this.con_4.substring(1) : this.con_4,
                5: this.con_5.indexOf(',') === 0 ? this.con_5.substring(1) : this.con_5,
                6: this.con_6.indexOf(',') === 0 ? this.con_6.substring(1) : this.con_6,
                7: this.con_7,
                8: this.con_8,
                9: this.con_9,
                10: this.con_10,
                11: this.con_11,
                12: this.con_12,
                13: this.con_13,
                14: this.con_14 ? "1" : "0",
                15: this.con_15,
                16: this.con_16,
                17: this.con_17.indexOf(',') === 0 ? this.con_17.substring(1) : this.con_17,
                18: this.con_18,
                19: this.con_19,
                22: this.con_22

            }
            let param = {
                verId: this.ver_id,
                invId: this.inv_id,
                orderId: this.order_id,
                conditions: dieukien
            };
            this.loading(true);
            try {

                const baseUrl = process.env.API;
                await this.axios({
                    url:
                        `${baseUrl}/web-thuno/api/thu-no/in-hoa-don/in-thong-bao-cuoc`,
                    method: "POST",
                    responseType: "blob",
                    data: {
                        verId: this.ver_id,
                        invId: this.inv_id,
                        orderId: this.order_id,
                        conditions: dieukien
                    }
                }).then((response) => {
                    this.noidung_in = response.data;
                });

            }
            catch (error) {
                this.$root.toastError(error.statusText);
            }
            finally {
                this.loading(false);
                if (this.noidung_in != null) {
                    this.$root.toastSuccess('Tạo dữ liệu thành công');
                    this.isDisableView = false;
                    this.isDisableExport = false;
                    this.isDisablePrint = false;
                }
                else {
                    this.$root.toastError('Không có dữ liệu theo yêu cầu');
                };
            }

        },
        formatDate(value) {
            return value === null ? "" : moment(value).format("DD/MM/yyyy");
        },
        onXemKetQua: function () {
            previewPrint(this.noidung_in);
        },
        onXuatPDF: function () {
            //console.log(this.noidung_in);
            let blob = new Blob([this.noidung_in], { type: "application/pdf" });
            let link = document.createElement('a')
            link.href = window.URL.createObjectURL(blob)
            link.download = 'Results.pdf'
            link.click()

        },
        onImportFile() {
            document.getElementById("upload").click();
            this.$refs.fileInput.value = "";
        },
        getDataFromFile(ws) {
            const listMaTT = ws.map(item => {
                return item.MA_TT
                    ;
            });

            return listMaTT.join(",");
        },
        async onChangeFile(e) {

            const files = e.target.files;

            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$toast.warning("File excel không đúng định dạng!");
            }
            const fileReader = new FileReader();
            fileReader.onload = async ev => {
                try {
                    const data = ev.target.result;
                    const XLSX = xlsx;
                    const workbook = XLSX.read(data, {
                        type: "binary"
                    });
                    const wsname = workbook.SheetNames[0];
                    const ws = XLSX.utils.sheet_to_json(
                        workbook.Sheets[wsname]
                    );
                    const excellist = [];
                    // Edit data
                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                    }

                    var propCount = 0;
                    if (excellist && excellist.length > 0) {
                        for (var prop in excellist[0]) {
                            if (excellist[0].hasOwnProperty(prop)) propCount++;
                        }
                    }

                    if (propCount != 1) {
                        this.$toast.warning(
                            "Số lượng cột phải = 1 cột, hiện đang có " +
                            propCount +
                            " cột!"
                        );
                        return;
                    }

                    if (!excellist[0].hasOwnProperty("MA_TT")) {
                        this.$toast.warning(
                            "Cột đầu tiên trong file phải là MA_TT!"
                        );
                        return;
                    }

                    this.dsMaTT = this.getDataFromFile(ws);
                    for (const item in this.inv_condition) {
                        if (this.inv_condition[item].REPLACE_STRING === 'MAKH_C') {
                            if (this.inv_condition[item].C_ID === 2)
                                this.con_2 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 3)
                                this.con_3 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 4)
                                this.con_4 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 5)
                                this.con_5 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 6)
                                this.con_6 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 7)
                                this.con_7 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 8)
                                this.con_8 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 9)
                                this.con_9 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 10)
                                this.con_10 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 11)
                                this.con_11 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 12)
                                this.con_12 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 13)
                                this.con_13 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 14)
                                this.con_14 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 15)
                                this.con_15 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 16)
                                this.con_16 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 17)
                                this.con_17 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 18)
                                this.con_18 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 19)
                                this.con_19 = this.dsMaTT;

                            if (this.inv_condition[item].C_ID === 22)
                                this.con_22 = this.dsMaTT;

                        }
                    }


                } catch (e) {
                    console.log(e);
                    return this.$toast.warning(
                        "Kiểm tra dữ liệu file đầu vào!"
                    );
                }
            };
            fileReader.readAsBinaryString(files[0]);
        },
        chang_Text: function (id, value) {
            if (id === 1)
                this.con_1 = value.target.value;
            if (id === 2)
                this.con_2 = value.target.value;
            if (id === 3)
                this.con_3 = value.target.value;
            if (id === 4)
                this.con_4 = value.target.value;
            if (id === 5)
                this.con_5 = value.target.value;
            if (id === 6)
                this.con_6 = value.target.value;
            if (id === 7)
                this.con_7 = value.target.value;
            if (id === 8)
                this.con_8 = value.target.value;
            if (id === 9)
                this.con_9 = value.target.value;
            if (id === 10)
                this.con_10 = value.target.value;
            if (id === 11)
                this.con_11 = value.target.value;
            if (id === 12)
                this.con_12 = value.target.value;
            if (id === 13)
                this.con_13 = value.target.value;

            if (id === 14)
                this.con_14 = value.target.value;

            if (id === 15)
                this.con_15 = value.target.value;

            if (id === 16)
                this.con_16 = value.target.value;

        },
        change_Date: function (id, value) {
            if (id === 1) {
                this.con_1 = value === null ? "" : moment(value.value).format("MM/yyyy");
            }
            if (id === 3) {
                this.con_3 = value === null ? "" : moment(value.value).format("DD/MM/yyyy");
            }
            if (id === 2) {
                this.con_2 = value === null ? "" : moment(value.value).format("DD/MM/yyyy");
            }
            if (id === 5) {
                this.con_5 = value === null ? "" : moment(value.value).format("DD/MM/yyyy");
            }

        },

        change_Combo: function (id, value) {
            if (id === 2)
                this.con_2 = value.target.value;
            if (id === 3)
                this.con_3 = value.target.value;
            if (id === 4)
                this.con_4 = value.target.value;
            if (id === 5)
                this.con_5 = value.target.value;
            if (id === 6)
                this.con_6 = value.target.value;
            if (id === 7)
                this.con_7 = value.target.value;
            if (id === 8)
                this.con_8 = value.target.value;
            if (id === 9)
                this.con_9 = value.target.value;
            if (id === 10)
                this.con_10 = value.target.value;
            if (id === 11)
                this.con_11 = value.target.value;
            if (id === 12)
                this.con_12 = value.target.value;
            if (id === 13)
                this.con_13 = value.target.value;
            if (id === 14)
                this.con_14 = value.target.value;

            if (id === 15)
                this.con_15 = value.target.value;
            if (id === 16)
                this.con_16 = value.target.value;
            if (id === 18)
                this.con_18 = value.target.value;
            if (id === 19)
                this.con_19 = value.target.value;
            if (id === 22)
                this.con_22 = value.target.value;
        },
        click_Checked: function (id, value) {
            let temp = '';

            if (id === 4) {
                temp = this.con_4.replace(',,', ',') + ',';
                if (temp.indexOf(',' + value + ',') >= 0) {
                    this.con_4 = temp.replace(',' + value + ',', ',');
                }
                else {
                    this.con_4 = this.con_4 + "," + value
                }
                this.con_4 = this.con_4.replace(',,', ',')
            }

            if (id === 5) {
                temp = this.con_5.replace(',,', ',') + ',';
                if (temp.indexOf(',' + value + ',') >= 0) {
                    this.con_5 = temp.replace(',' + value + ',', ',');
                }
                else {
                    this.con_5 = this.con_5 + "," + value
                }
                this.con_5 = this.con_5.replace(',,', ',')
            }
            if (id === 6) {
                temp = this.con_6.replace(',,', ',') + ',';
                if (temp.indexOf(',' + value + ',') >= 0) {
                    this.con_6 = temp.replace(',' + value + ',', ',');
                }
                else {
                    this.con_6 = this.con_6 + "," + value
                }
                this.con_6 = this.con_6.replace(',,', ',')
            }
            if (id === 17) {
                temp = this.con_17.replace(',,', ',') + ',';
                if (temp.indexOf(',' + value + ',') >= 0) {
                    this.con_17 = temp.replace(',' + value + ',', ',');
                }
                else {
                    this.con_17 = this.con_17 + "," + value
                }
                this.con_17 = this.con_17.replace(',,', ',')
            }
        },
        click_leaves: async function (ver_id, inv_id) {
            this.tree_leaves = inv_id;
            await this.load_condition(ver_id, inv_id);
        },
        value_object: function (obj, vitri) {
            var i = 0;
            for (item of obj) {
                for (const value in item) {
                    if (i === vitri)
                        return value;
                }
                i = i + 1;
            }

        },
    }
}
</script>
