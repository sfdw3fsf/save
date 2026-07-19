<template>
    <b-modal
        id="popup-uploadDsGan"
        size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        @show="onShow"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> Đọc file gán đơn vị chăm sóc KHL
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="$bvModal.hide('popup-uploadDsGan')"
                ></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript: void(0)" @click="importExcel">
                            <span
                                class="icon design_bullet-list-67 nc-icon-glyph"
                            ></span>
                            Danh sách
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="CapNhap">
                            <span
                                class="icon ui-1_check-circle-08 nc-icon-glyph"
                            ></span>
                            Chấp nhận
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)">
                            <downloadexcel :fields="exportExcelFields" :data="this.dsDocTuFile" worksheet="export"
                        name="export.xls">
                                <span class="icon one-excel"></span> Xuất Excel
                            </downloadexcel>
                        </a>
                    </li>
                </ul>
                <div class="more">
                    <a>
                        <span class="icon -ap icon-dots-three-vertical"></span>
                    </a>
                </div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="list-checks fw6 marb10">
                        <div class="item">
                            <span
                                class="
                                    one-alert
                                    f24
                                    text-warning
                                    inline
                                    vcenter
                                    marr7
                                "
                            ></span>
                            <span class="text-main"
                                >File excel gồm trường: MA_KH, DONVI_ID</span
                            >
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key">Đường dẫn</div>
                        <div class="value">
                            <div class="input-more-button">
                                <!-- <button class="btn">
                                <span class="-ap icon-more_horiz"></span>
                            </button> -->
                                <label for="upload" class="btn">
                                    <span class="-ap icon-more_horiz"></span>
                                    <input
                                        type="file"
                                        id="upload"
                                        style="display: none"
                                        ref="fileInput"
                                        @change="onChangeFile"
                                        @click="$refs.fileInput.value=null"
                                    />
                                </label>
                                <input
                                    type="text"
                                    class="form-control"
                                    id="showFileNameUpload"
                                    v-model="txtFileName"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="legend-title mart20">
                        <div class="title pull-left">Danh sách khách hàng</div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="nav tabs tab-over">
                        <a
                            @click="tabDanhSach = false"
                            :class="{ active: !tabDanhSach }"
                            >Danh sách</a
                        >
                        <a
                            @click="tabDanhSach = true"
                            :class="{ active: tabDanhSach }"
                            >Lỗi</a
                        >
                    </div>
                    <div class="table-content">
                        <DataGrid
                            v-show="!tabDanhSach"
                            v-bind:columns="[
                                {
                                    fieldName: 'MA_KH',
                                    headerText: 'Mã KH',
                                    allowFiltering: true,
                                },
                                {
                                    fieldName: 'DONVI_ID',
                                    headerText: 'Đơn vị ID',
                                    allowFiltering: true,
                                },
                            ]"
                            :dataSource="dsDocTuFile"
                            :showFilter="true"
                            dataKeyField="STT"
                            ref="tblDsDocFile"
                            :allowPaging="true"
                            :enablePagingServer="false"
                        ></DataGrid>
                        <DataGrid
                            v-show="tabDanhSach"
                            v-bind:columns="[
                                {
                                    fieldName: 'MA_KH',
                                    headerText: 'Mã KH',
                                    allowFiltering: true,
                                },
                                {
                                    fieldName: 'LYDO_LOI',
                                    headerText: 'Lỗi',
                                    allowFiltering: true,
                                },
                            ]"
                            :dataSource="dsLoi"
                            :showFilter="true"
                            dataKeyField="STT"
                            ref="tblDsLoi"
                            :allowPaging="true"
                            :enablePagingServer="false"
                        ></DataGrid>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import xlsx from "xlsx";
import GanDVAPI from "../../api/GanNVChamSocKHLApi";
import CustomDataGrid from "../../utils/CustomDataGrid.vue";
import downloadexcel from "vue-json-excel";
export default {
    name: "UploadDSGan",
    props: ["kyHoaDon"],
    components: { downloadexcel, datagrid: CustomDataGrid },
    data() {
        return {
            loaiCot: 2,
            dsRow: [],
            items: [],
            loaiDanhMucVTSelected: "",
            dsLoi: [],
            dsDocTuFile: [],
            files: [],
            txtFileName: "",
            xlsData: [],
            tabDanhSach: false,
            exportExcelFields: {
                "Mã KH": "MA_KH",
                "Đơn vị ID": "DONVI_ID",
            },
        };
    },
    created() {},
    computed: {
        dataExcel() {
            return Object.values(this.dsLoi);
        },
    },
    mounted() {},
    methods: {
        onShow() {
            this.dsLoi = [];
            this.dsDocTuFile = [];
            this.tabDanhSach = false;
            this.txtFileName = ''
        },
        checkAll(event) {
            this.dsLoi.forEach(
                (element) => (element.isCheck = event.target.checked)
            );
        },
        gridRowClicked(record, index) {
            record.isCheck = !record.isCheck;
        },
        countMaTB(maTB) {},
        async onChangeFile(e) {
            // console.log(e);
            this.files = e.target.files;
            this.txtFileName = e.target.files[0].name;
            await this.importExcel();
        },
        async CapNhap() {
            if (this.dsDocTuFile.length == 0) {
                this.$root.toastError("Chưa có dữ liệu để cập nhật!");
                return;
            }

            try {
                this.loading(true)
                let data = {
                    kyHoaDon: this.kyHoaDon,
                    nguoiDungId: this.$auth.getNguoiDungID(),
                    loai: 2
                };
                let response = await GanDVAPI.ganDVChamSocKHLTuFile(this.axios, data);
                if (response.data.error_code == "BSS-00000000")
                {
                    this.dsLoi = []
                    if (response.data.data.listDataError && response.data.data.listDataError.length > 0) {
                        response.data.data.listDataError.forEach(e => {
                            this.dsLoi.push({MA_KH: e.MA_KH, LYDO_LOI: e.MSG})
                        })
                        this.tabDanhSach = true;
                        this.$root.toastError(response.data.data.messageResult)
                    }
                    else {
                        this.tabDanhSach = false;
                        this.$root.toastSuccess("Đã tạo danh sách đơn vị thành công")
                        this.$bvModal.hide('popup-uploadDsGan')
                    }
                }
                else {
                    this.$root.toastError("Đã tạo danh sách đơn vị thất bại")
                }
            } catch (e) {
                this.$root.toastError("Đã tạo danh sách đơn vị thất bại: " + e.data.message)
            } finally {
                this.loading(false);
            }
        },
        async importExcel() {
            this.xlsData = [];
            const files = this.files;
            console.log(files);
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                this.$root.toastError( "File không đúng định dạng : xls, xlsx");
                return
            }
            const fileReader = new FileReader();
            fileReader.onload = async (ev) => {
                this.loading(true);
                try {
                    this.dsLoi = [];
                    const data = ev.target.result;
                    const XLSX = xlsx;
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    });
                    const wsname = workbook.SheetNames[0];
                    const ws = XLSX.utils.sheet_to_json(
                        workbook.Sheets[wsname]
                    );
                    const excellist = [];
                    let dsMaTB = [];
                    let dsDuplicateMaTB = [];
                    // Edit data
                    for (var i = 0; i < ws.length; i++) {
                        excellist.push(ws[i]);
                    }

                    if (
                        !excellist[0].hasOwnProperty("MA_KH") ||
                        !excellist[0].hasOwnProperty("DONVI_ID")
                    ) {
                        this.$root.toastError(
                            "Tên cột trong file không đúng định dạng : MA_KH, DONVI_ID"
                        );
                        return;
                    }

                    excellist.forEach((element, index) => {
                        if (element.MA_KH || element.DONVI_ID) {
                            if (!element.MA_KH || !element.DONVI_ID)
                                this.dsLoi.push({
                                    STT: index + 1,
                                    MA_KH: element.MA_KH,
                                    DONVI_ID: element.DONVI_ID,
                                    LYDO_LOI: !element.MA_KH
                                        ? "Mã khách hàng null"
                                        : !element.MA_KH ? "Đơn vị ID null"
                                        : "",
                                });
                        }

                    });

                    if (this.dsLoi.length > 0) {
                        this.tabDanhSach = true;
                        return;
                    }
                    let dsCheckTrung = [];
                    excellist.forEach((x) =>
                        dsMaTB.push({
                            MA_KH: x.MA_KH,
                            DONVI_ID: x.DONVI_ID,
                            GOP: x.MA_KH + "" + x.DONVI_ID,
                        })
                    );

                    dsCheckTrung = dsMaTB.map((x) => x.GOP);

                    dsMaTB.forEach((element) => {
                        if (
                            dsCheckTrung.indexOf(element.GOP) !=
                                dsCheckTrung.lastIndexOf(element.GOP) &&
                            !dsDuplicateMaTB.includes(element.GOP)
                        )
                            dsDuplicateMaTB.push(element);
                    });


                    dsDuplicateMaTB.forEach((element, index) => {
                        this.dsLoi.push({
                            STT: index + 1,
                            MA_KH: element.MA_KH,
                            DONVI_ID: element.DONVI_ID,
                            LYDO_LOI: "Cặp MA_KH và DONVI_ID bị lặp lại",
                        });
                    });

                    if (this.dsLoi.length > 0) {
                        this.tabDanhSach = true;
                        return;
                    }

                    this.dsDocTuFile = excellist;

                    if (this.dsDocTuFile.length > 0) {
                        let tmp = {
                            reset: 1,
                            dsMatt: this.dsDocTuFile.map((x) => x.MA_KH).join(),
                            nguoiDungId: this.$auth.getNguoiDungID(),
                            dsGiaTri: this.dsDocTuFile
                                .map((x) => x.DONVI_ID)
                                .join(),
                            kieuId: 11,
                        };

                        let response = await GanDVAPI.uploadFileTmp(
                            this.axios,
                            tmp
                        );
                        if (response.data.error_code === "BSS-00000000") {
                            let data = {
                                loai: 1,
                                kyHoaDon: this.kyHoaDon,
                                nguoiDungId: this.$auth.getNguoiDungID(),
                            };
                            let response2 = await GanDVAPI.ganDVChamSocKHLTuFile(this.axios, data);
                            this.dsLoi = []
                            if (response2.data.error_code == "BSS-00000000")
                            {
                                if (response2.data.data.listDataError && response2.data.data.listDataError.length > 0) {
                                    response2.data.data.listDataError.forEach(e => {
                                        this.dsLoi.push({MA_KH: e.MA_KH, LYDO_LOI: e.MSG})
                                    })
                                    this.tabDanhSach = true;
                                    this.$root.toastError(response2.data.data.messageResult)
                                }
                                else {
                                   this.tabDanhSach = false;
                                }

                            }
                            else {
                                this.$root.toastError("Import file thất bại! " + response.data.message)
                            }
                        }
                    }

                    //console.log("Read results", excellist); // At this point, you get an array containing objects that need to be processed
                } catch (e) {
                    this.$root.toastError( "Có lỗi :" + e );
                } finally {
                    this.loading(false);
                }
            };

            fileReader.readAsBinaryString(files[0]);
            var input = document.getElementById("upload");
        }
    },
};
</script>
