<template>
    <b-modal
        id="modal-docfile"
        size="lg"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        @hide="resetData"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Đọc file in hóa đơn
                    điện tử
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="close()"
                ></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li @click="DocFile()">
                        <a href="javascript:;">
                            <span
                                class="icon design_bullet-list-67 nc-icon-glyph"
                            ></span>
                            Danh sách
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" @click="ChapNhan()">
                            <span
                                class="icon ui-1_check-circle-08 nc-icon-glyph"
                            ></span>
                            Chấp nhận
                        </a>
                    </li>
                    <li @click="XuatExcel()">
                        <a href="javascript:;">
                            <span class="icon one-excel"></span> Xuất Excel
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="note text-main">
                        <span class="one-alert f20 text-warning"></span> File
                        excel gồm Sheet1: MA_TT
                    </div>
                    <div class="info-row">
                        <div class="key w90">Đường dẫn</div>
                        <div class="value">
                            <div class="input-more-button">
                                <label for="upload" class="btn">
                                    <span class="-ap icon-more_horiz"></span>
                                    <input
                                        ref="fileInput"
                                        type="file"
                                        id="upload"
                                        style="display: none"
                                        @change="onLoadFile"
                                        accept=".xls, .xlsx"
                                        @click="$refs.fileInput.value = null"
                                    />
                                </label>
                                <input
                                    autofocus
                                    ref="duongDan"
                                    v-model="duongDan"
                                    type="text"
                                    class="form-control highlight"
                                />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        {{
                            checkLoi
                                ? "Danh sách các hóa đơn"
                                : "Danh sách thuê bao"
                        }}
                    </div>
                    <DataGrid
                        :columns="fields"
                        :dataSource="dsLoi"
                        :showFilter="true"
                        ref="danhSach"
                        :allowPaging="true"
                        :enablePagingServer="false"
                    />
                </div>
            </div>
        </div>
    </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";
import { mapActions, mapGetters } from "vuex";

export default {
    name: "PopupDocFile",
    props: ["dichVu", "kyCuoc"],
    data() {
        return {
            fields: [
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true
                },
                {
                    fieldName: "TRANGTHAI",
                    headerText: "Trạng thái",
                    allowFiltering: true
                }
            ],
            duongDan: "",
            dsLoi: [],
            sourceFile: null,
            checkLoi: false
        };
    },
    computed: {
        ...mapGetters("InHoaDonDienTu", ["listDonViGetter"])
    },
    methods: {
        ...mapActions("InHoaDonDienTu", [
            "docFileMaTT",
            "getHDDTTheoFile",
            "kiemTraFile",
            "clearData"
        ]),

        close() {
            this.$bvModal.hide("modal-docfile");
        },

        XuatExcel() {
            if (this.dsLoi.length <= 0) {
                this.$root.toastError("Chưa có danh sách để xuất Excel!");
                return;
            }
            this.loading(true);
            try {
                var dsach = xlsx.utils.json_to_sheet(this.dsLoi);
                var wb = xlsx.utils.book_new(); // make Workbook of Excel
                xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
                // export Excel file
                xlsx.writeFile(wb, "mau.xlsx");
            } catch (error) {
            } finally {
                this.loading(false);
            }
        },

        DocFile() {
            if (this.duongDan === "") {
                this.$root.toastError("Hãy chọn đường dẫn!");
                return;
            }
            this.onChangeFile(this.sourceFile);
        },

        onLoadFile(e) {
            this.sourceFile = e;
            this.duongDan = e.target.files[0].name;
            this.dsLoi = [];
        },

        async getDataFromFile(ws) {
            const listMaTT = ws.map(item => item.MA_TT);

            const data = {
                reset: 1,
                dsMaTT: listMaTT
            };
            try {
                this.loading(true);
                const response = await this.docFileMaTT(data);
                console.log(response);
                if (response.data.error_code === "BSS-00000000") {
                    this.checkLoi = false;
                } else {
                    this.$root.toastError(response.data.message_detail);
                    this.checkLoi = true;
                }
                return response.data.data;
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },

        async getHDDT() {
            const listMaTT = this.dsLoi.map(item => item.MA_TT);
            try {
                this.loading(true);
                const data = {
                    kyCuoc: this.kyCuoc,
                    dsMaTT: listMaTT
                };
                const response = await this.getHDDTTheoFile(data);
                console.log(response);
                if (response.data.error_code === "BSS-00000000") {
                    if (response.data.data.length === 0) {
                        this.$root.toastError(
                            "Không tìm thấy danh sách hóa đơn thỏa mãn điều kiện!"
                        );
                    }
                    return response.data.data;
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },

        async onChangeFile(e) {
            const files = e.target.files;

            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$root.toastError(
                    "File excel không đúng định dạng!"
                );
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
                    console.log({ ws });
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
                        this.$root.toastError(
                            "Số lượng cột phải = 1 cột, hiện đang có " +
                                propCount +
                                " cột!"
                        );
                        return;
                    }

                    if (!excellist[0].hasOwnProperty("MA_TT")) {
                        this.$root.toastError(
                            "Cột đầu tiên trong file phải là MA_TT!"
                        );
                        return;
                    }

                    let dsMaTT = await this.getDataFromFile(ws);
                    dsMaTT.forEach(item => {
                        if (item.TRANGTHAI_ID !== "0") {
                            this.checkLoi = true;
                            return;
                        }
                        this.checkLoi = false;
                    });
                    console.log({ check: this.checkLoi });
                    this.dsLoi = dsMaTT.map(item => ({
                        MA_TT: item.MA_TT,
                        TRANGTHAI: item.TRANGTHAI
                    }));

                    this.$refs.danhSach.setCurrentSelectedRow(0);
                } catch (e) {
                    console.log(e);
                    return this.$root.toastError(
                        "Kiểm tra dữ liệu file đầu vào!"
                    );
                }
            };
            fileReader.readAsBinaryString(files[0]);
        },

        async ChapNhan() {
            if (this.dsLoi.length === 0 || !this.checkLoi) {
                this.$root.toastError("Chưa có danh sách hóa đơn hợp lệ!");
                return;
            }
            const data = await this.getHDDT();
            console.log({ data });

            this.$emit("sendData", data);
            this.$bvModal.hide("modal-docfile");
        },

        resetData() {
            this.duongDan = "";
            this.dsLoi = [];
        }
    }
};
</script>
