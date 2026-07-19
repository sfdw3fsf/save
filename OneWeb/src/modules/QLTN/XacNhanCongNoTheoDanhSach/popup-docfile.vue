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
                    <span class="icon one-notepad"></span>Đọc file xác nhận công
                    nợ
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="close()"
                ></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <!-- <li @click="DanhSach()">
						<a href="javascript:;">
							<span class="icon design_bullet-list-67 nc-icon-glyph"></span>
							Danh sách
						</a>
					</li> -->
                    <li>
                        <a href="/static/files/Bieumau_xacnhanno.xls" class="link underline inline padt7 padb7">Tải biểu mẫu</a>
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
                    <div class="info-row">
                        <div class="key w90">Tên file</div>
                        <div class="value">
                            <div class="input-more-button">
                                <label for="upload" class="btn">
                                    <span class="-ap icon-more_horiz"></span>
                                    <input
                                        ref="fileInput"
                                        type="file"
                                        id="upload"
                                        style="display: none"
                                        @change="onChangeFile"
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
                        <div class="value w80 nowrap">
                            <a href="/static/files/Bieumau_xacnhanno.xls" class="link underline inline padt7 padb7">Tải biểu mẫu</a>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        <div class="pull-left">Danh sách công nợ</div>
                        <div class="pull-right red">00:00:00</div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="nav tabs tab-over">
                        <a
                            href="javascript:;"
                            data-toggle="tab"
                            :class="{ active: !isTab }"
                            @click="isTab = false"
                        >
                            <span
                                class="
									nc-icon-glyph
									design_bullet-list-67
									text-main
									f20
									inline
									vcenter
								"
                            ></span>
                            Danh sách
                        </a>
                        <a
                            href="javascript:;"
                            data-toggle="tab"
                            :class="{ active: isTab }"
                            @click="isTab = true"
                        >
                            <span
                                class="one-alert text-warning f20 inline vcenter"
                            ></span
                            >Lỗi
                        </a>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tabDS" v-show="!isTab">
                            <!-- <table-c
                                :columns="fieldsCongNo"
                                :dataSource="dsLanTao"
                                :showFilter="true"
                                ref="tabIn"
                            ></table-c> -->
                            <DataGrid v-bind:columns="fieldsCongNo"
                                v-bind:dataSource="dsLanTao"
                                :showFilter="true"
                                :allowPaging="true"
                                :enablePagingServer="false"
                                ref="tabIn"
                            >
                            </DataGrid>
                        </div>
                        <div class="tab-pane active" id="tabLoi" v-show="isTab">
                            <!-- <table-c
                                :columns="fieldsLoi"
                                :dataSource="dsLoi"
                                :showFilter="true"
                                ref="tabLoi"
                            ></table-c> -->
                            <DataGrid v-bind:columns="fieldsLoi"
                                v-bind:dataSource="dsLoi"
                                :showFilter="true"
                                :allowPaging="true"
                                :enablePagingServer="false"
                                ref="tabLoi"
                            >
                            </DataGrid>
                        </div>
                    </div>
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
    name: "PopupDocFile",
    mounted() {},
    props: ["kyCuoc", "lanTao"],
    data() {
        return {
            fieldsLoi: [
                {
                    fieldName: "MA_TT",
                    headerText: "MA_TT",
                    allowFiltering: true
                },
                {
                    fieldName: "NGAY_XACTHUC",
                    headerText: "NGAY_XACTHUC",
                    allowFiltering: true
                },
                {
                    fieldName: "NGAY_THUHOI",
                    headerText: "NGAY_THUHOI",
                    allowFiltering: true
                },
                {
                    fieldName: "KETQUA",
                    headerText: "KETQUA",
                    allowFiltering: true
                },
                {
                    fieldName: "LYDO_KXN",
                    headerText: "LYDO_KXN",
                    allowFiltering: true
                },
                {
                    fieldName: "SODU",
                    headerText: "SODU",
                    allowFiltering: true
                },
                {
                    fieldName: "NGUYENNHAN_CHENHLECH",
                    headerText: "NGUYENNHAN_CHENHLECH",
                    allowFiltering: true
                },
                {
                    fieldName: "GHICHU",
                    headerText: "GHICHU",
                    allowFiltering: true
                },
                {
                    fieldName: "TRANGTHAI",
                    headerText: "Lý do lỗi",
                    allowFiltering: true
                }
            ],
            fieldsCongNo: [
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: true
                },
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true
                },
                {
                    fieldName: "NGAY_XACTHUC",
                    headerText: "Ngày xác thực",
                    allowFiltering: true
                },
                {
                    fieldName: "NGAY_THUHOI",
                    headerText: "Ngày thu hồi",
                    allowFiltering: true
                },
                {
                    fieldName: "KETQUA",
                    headerText: "Kết quả",
                    allowFiltering: true
                },
                {
                    fieldName: "LYDO_KXN",
                    headerText: "Lý do KXN",
                    allowFiltering: true
                },
                {
                    fieldName: "NGUYENNHAN_CHENHLECH",
                    headerText: "Nguyên nhân CL",
                    allowFiltering: true
                },
                {
                    fieldName: "TRANGTHAI",
                    headerText: "Ghi chú",
                    allowFiltering: true
                }
            ],
            dsTemp: [],
            duongDan: "",
            dsLoi: [],
            dsLanTao: [],
            sourceFile: null,
            checkLoi: false,
            isTab: false
        };
    },
    computed: {},
    methods: {
        ...mapActions("XacNhanCongNoTheoDanhSach", [
            "kiemTraFile",
            "capNhatCongNoTheoFile",
            "taiBieuMau",
            "clearData"
        ]),
        close() {
            this.$bvModal.hide("modal-docfile");
        },
        async taiFile() {
            this.axios({
                url:
                    "/static/files/Bieumau_xacnhanno.xlsx",
                method: "GET",
                responseType: "blob"
            }).then(response => {
                console.log("object", response);
                var fileURL = window.URL.createObjectURL(
                    new Blob([response.data])
                );
                var fURL = document.createElement("a");

                fURL.href = fileURL;
                fURL.setAttribute("download", "Bieumau_xacnhanno.xls");
                document.body.appendChild(fURL);

                fURL.click();
            });
        },
        XuatExcel() {
            if (this.dsLanTao.length <= 0) {
                this.$root.toastError("Chưa có danh sách để xuất Excel!");
                return;
            }
            this.loading(true);
            try {
                var dsach = xlsx.utils.json_to_sheet(this.dsLanTao);
                var wb = xlsx.utils.book_new(); // make Workbook of Excel
                xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
                // export Excel file
                xlsx.writeFile(wb, "mau.xlsx");
            } catch (error) {
            } finally {
                this.loading(false);
            }
        },

        async DanhSach() {
            if (this.duongDan.trim() === "") {
                this.$root.toastError("Hãy chọn đường dẫn!");
                this.$refs.fileInput.focus();
                return;
            }

            if (this.dsLoi.length > 0) {
                this.isTab = true;
                return;
            }
            this.dsLanTao = this.dsTemp;
        },

        async getDataFromFile(ws) {
            const listMaTB = ws.map(item => {
                return {
                    maTT: item.ma_tt,
                    ngayXacThuc: item.ngay_xacthuc,
                    ngayThuHoi: item.ngay_thuhoi,
                    ketQua: item.ketqua,
                    lyDo: item.lydo_kxn !== undefined ? item.lydo_kxn : null,
                    soDu: item.sodu !== undefined ? item.sodu : null,
                    nguyenNhanChenhLech: item.nguyennhan_chenhlech !== undefined ? item.nguyennhan_chenhlech : null,
                    ghiChu: item.Ghichu !== undefined ? item.Ghichu : null
                };
            });
            console.log({ lantao: this.lanTao });
            const data = {
                // lanTaoId: 79,
                lanTaoId: this.lanTao,
                ds: listMaTB
            };
            try {
                this.loading(true);
                const response = await this.kiemTraFile(data);
                if (response.data.error_code === "BSS-00000000") {
                    return response.data.data;
                } else {
                    return this.$root.toastError("File excel không hợp lệ!");
                }
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },

        async capNhat() {
            console.log({ lantaoList: this.dsLanTao });

            const listMaTB = this.dsLanTao.map(item => {
                return {
                    maTT: item.MA_TT,
                    ngayXacThuc: item.NGAY_XACTHUC,
                    ngayThuHoi: item.NGAY_THUHOI,
                    ketQua: item.KETQUA,
                    lyDo: item.LYDO_KXN,
                    soDu: item.SODU,
                    nguyenNhanChenhLech: item.NGUYENNHAN_CHENHLECH
                };
            });
            const data = {
                // lanTaoId: 79,
                lanTaoId: this.lanTao,
                ds: listMaTB
            };
            try {
                this.loading(true);
                const response = await this.capNhatCongNoTheoFile(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.$root.toastSuccess("Cập nhật kết quả thành công!");
                } else {
                    return this.$root.toastError(response.data.message_detail);
                }
            } catch (e) {
                return this.$root.toastError(e.data.message_detail);
            } finally {
                this.loading(false);
            }
        },

        async onChangeFile(e) {
            const files = e.target.files;
            this.duongDan = e.target.files[0].name;
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
                    this.dsLoi = [];
                    this.dsLanTao = [];
                    const data = ev.target.result;
                    const XLSX = xlsx;
                    const excellist = [];
                    let dsDanhSach = [];
                    let dsMaTT = [];
                    let dsDuplicateMaTT = [];
                    const workbook = XLSX.read(data, {
                        type: "binary"
                    });
                    const wsname = workbook.SheetNames[0];
                    const ws = XLSX.utils.sheet_to_json(
                        workbook.Sheets[wsname]
                    );
                    console.log({ ws });

                    if (ws.length === 0) {
                        this.$root.toastError("File không có dữ liệu!");
                        return;
                    }

                    const response = await this.getDataFromFile(ws);
                    response.forEach(item => {
                        console.log(item);
                        if (item.STATUS == 1) {
                            this.dsLoi.push(item);
                            this.isTab = true;
                        }
                    });
                    console.log({ response });
                    console.log({ listloi: this.dsLoi });

                    // Edit data
                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                        dsMaTT.push(ws[i].ma_tt);
                        dsDanhSach.push({
                            ma_tt: ws[i].ma_tt,
                            ngay_xacthuc: ws[i].ngay_xacthuc,
                            ngay_thuhoi: ws[i].ngay_thuhoi,
                            ketqua: ws[i].ketqua,
                            lydo_kxn: ws[i].lydo_kxn,
                            sodu: ws[i].sodu,
                            nguyennhan_chenhlech: ws[i].nguyennhan_chenhlech,
                            Ghichu: ws[i].Ghichu
                        });
                    }
                    console.log({ dsDanhSach });

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
                            ma_tt: element,
                            LYDO_LOI: "Mã thanh toán bị lặp lại"
                        });
                    });
                    if (this.dsLoi.length > 0) {
                        this.dsLanTao = [];
                    } else {
                        this.dsLanTao = response;
                        this.tabIn = false;
                    }
                    this.dsTemp = ws;
                    this.dsDanhSachTemp = dsDanhSach;
                } catch (e) {
                    return this.$root.toastError(
                        "Kiểm tra dữ liệu file đầu vào!"
                    );
                }
            };
            fileReader.readAsBinaryString(files[0]);
        },

        async ChapNhan() {
            if (this.dsLanTao.length > 0) {
                await this.capNhat();
                this.$bvModal.hide("modal-docfile");
            } else {
                this.$root.toastError("Chưa có danh sách để cập nhật!");
            }
        },

        resetData() {
            this.duongDan = "";
            this.dsLoi = [];
            this.dsLanTao = [];
            this.tabIn = false;
        }
    }
};
</script>
