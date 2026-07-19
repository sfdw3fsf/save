<template>
    <b-modal
        id="modal-docfilePhieuThanhLy"
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
                    <span class="icon one-notepad"></span>Đọc file danh sách
                    thanh lý
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
                        <a
                            href="javascript:void(0)"
                            @click="ChapNhan()"
                            :class="{
                                disabled: checkLoi || dsLoi.length === 0
                            }"
                        >
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
                        excel gồm 1 trường: MA_TB
                    </div>
                    <div class="info-row">
                        <div class="key w90">Dịch vụ</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control" disabled>
                                    <option value="dichVu.id">{{
                                        dichVu.name
                                    }}</option>
                                </select>
                            </div>
                        </div>
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
                                ? "Danh sách thuê bao lỗi"
                                : "Danh sách thuê bao"
                        }}
                    </div>
                    <table-c
                        :columns="fields"
                        :dataSource="dsLoi"
                        :showFilter="true"
                        ref="danhSach"
                    />
                </div>
            </div>
        </div>
    </b-modal>
</template>
<style></style>
<script>
import xlsx from "xlsx";
import { mapActions, mapState, mapGetters } from "vuex";

export default {
    name: "PopupDocFilePhieuThanhLy",
    mounted() {},
    props: ["dichVu"],
    data() {
        return {
            fields: [
                {
                    fieldName: "MA_TB",
                    headerText: "Mã TB",
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
        ...mapGetters("TaoDanhSachThanhLyThueBaoTamDung", ["listDonViGetter"])
    },
    methods: {
        ...mapActions("TaoDanhSachThanhLyThueBaoTamDung", [
            "getListTDTuFile",
            "updateListTDTuFile",
            "clearData"
        ]),

        close() {
            this.$bvModal.hide("modal-docfilePhieuThanhLy");
        },

        async XuatExcel() {
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
                await xlsx.writeFile(wb, "mau.xlsx", err => {
                    console.log(err);
                });
                this.$root.toastSuccess("Xuất dữ liệu thành công!");
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
            this.$confirm(`Bạn muốn mở file?`, "Thông báo", {
                confirmButtonText: "Có",
                cancelButtonText: "Không"
            })
                .then(() => {
                    this.onChangeFile(this.sourceFile);
                })
                .catch(e => {});
        },

        onLoadFile(e) {
            this.sourceFile = e;
            this.duongDan = e.target.files[0].name;
            this.dsLoi = [];
        },

        async getDataFromFile(ws) {
            const listMaTB = ws.map(item => {
                return {
                    ma_tb: item.MA_TB,
                    dichvuvt_id: this.dichVu.id
                };
            });

            const data = {
                pNguoiDungId: this.$auth.getNhanVienID(),
                pData: listMaTB
            };
            try {
                this.loading(true);
                const response = await this.getListTDTuFile(data);
                return response.data.data;
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },

        async onChangeFile(e) {
            console.log({ e });
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

                    let dsMaTT = [];
                    let dsDuplicateMaTT = [];
                    const excellist = [];

                    // Edit data
                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                        dsMaTT.push(ws[i].MA_TB);
                    }

                    var propCount = 0;
                    if (excellist && excellist.length > 0) {
                        for (var prop in excellist[0]) {
                            if (excellist[0].hasOwnProperty(prop)) propCount++;
                        }
                    }

                    // if (propCount != 1) {
                    // 	// this.$toast.warning(
                    // 	// 	"Số lượng cột phải = 1 cột, hiện đang có " + propCount + " cột!"
                    // 	// );
                    // 	 this.$root.toastError(
                    // 		"Cột đầu tiên trong file phải là MA_TB!"
                    // 	);
                    // 	return;
                    // }
                    if (ws.length === 0) {
                        this.$root.toastError("File không có dữ liệu!");
                        return;
                    }

                    console.log({ ws });
                    if (!excellist[0].hasOwnProperty("MA_TB")) {
                        this.$root.toastError(
                            "Cột đầu tiên trong file phải là MA_TB!"
                        );
                        return;
                    }
                    dsMaTT.forEach(element => {
                        if (
                            dsMaTT.indexOf(element) !=
                                dsMaTT.lastIndexOf(element) &&
                            !dsDuplicateMaTT.includes(element)
                        )
                            dsDuplicateMaTT.push(element);
                    });

                    dsDuplicateMaTT.forEach(element => {
                        this.dsLoi.push({
                            MA_TB: element,
                            TRANGTHAI: "Thuê bao bị lặp lại"
                        });
                        this.checkLoi = true;
                    });

                    if (dsDuplicateMaTT.length === 0) {
                        dsMaTT = await this.getDataFromFile(ws);
                        dsMaTT.forEach(item => {
                            if (item.TRANGTHAI_ID != "0") {
                                console.log({ item });
                                this.checkLoi = true;
                                return;
                            }
                            this.checkLoi = false;
                        });
                        this.dsLoi = dsMaTT.map(item => ({
                            MA_TB: item.MA_TB,
                            TRANGTHAI: item.TRANGTHAI
                        }));
                    }

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
            if (this.dsLoi.length === 0 || this.checkLoi) {
                this.$root.toastError("Chưa có danh sách thuê bao hợp lệ!");
                return;
            }
            const data = {
                pNguoiDungId: this.$auth.getNhanVienID(),
                pMayCN: "chinhtn"
            };
            await this.updateListTDTuFile(data);
            this.$emit("sendData", {
                dsThueBao: this.dsLoi
            });
            this.$bvModal.hide("modal-docfilePhieuThanhLy");
        },

        resetData() {
            this.duongDan = "";
            this.dsLoi = [];
            this.checkLoi = false;
        }
    }
};
</script>
