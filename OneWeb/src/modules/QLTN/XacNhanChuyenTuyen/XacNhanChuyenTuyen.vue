<template src="./XacNhanChuyenTuyen.html"></template>
<style src="./XacNhanChuyenTuyen.scss" scoped></style>
<script>
import EventBus from "@/utils/eventBus";
import KyCuoc from "@/components/KyCuoc";
import API from "../api/XacNhanChuyenTuyen";
import { mapActions, mapGetters } from "vuex";
import token from "@/plugins/token";
import xlsx from "xlsx";
import moment from "moment";

export default {
    name: "XacNhanChuyenTuyen",
    components: {
        appKyCuoc: KyCuoc
    },
    data() {
        return {
            isShowTC: false,
            maTT: [],
            dataSelected: "",
            itemSelected: [],
            kyCuoc: "",
            header: {
                title: "Xác nhận chuyển tuyến",
                list: [
                    {
                        name: "Quản lý thu nợ",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Quản lý đại lý",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Xác nhận chuyển tuyến",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            fields: [
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "Tên TT",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "Địa chỉ TT",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "TRANGTHAI_CT",
                    headerText: "Trạng thái",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "TUYENCU",
                    headerText: "Tuyến cũ",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "MA_NV_CU",
                    headerText: "NVTC cũ",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "TUYENMOI",
                    headerText: "Tuyến mới",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "MA_NV_MOI",
                    headerText: "NVTC mới",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "KY_HD",
                    headerText: "Kỳ HĐ",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "NGAY_CN",
                    headerText: "Ngày CN",
                    allowFiltering: true,
                    width: 130
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "Người CN",
                    allowFiltering: true,
                    width: 130
                }
            ]
        };
    },
    computed: {
        ...mapGetters("xacnhanchuyentuyen", [
            "danhSachNhanVienThuCuocGetter",
            "danhSachChuyenTuyenGetter"
        ])
    },
    methods: {
        ...mapActions("xacnhanchuyentuyen", [
            "getDSNhanVienThuCuoc",
            "getDSChuyenTuyen",
            "clearData"
        ]),
        chukycuocChangeHandler(e) {
            this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
        },
        async onChangeFile(e) {
            const files = e.target.files;
            this.maTT = [];
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$toast.error("File excel không đúng định dạng!");
            }

            const fileReader = new FileReader();
            fileReader.onload = async ev => {
                try {
                    this.maTT_Items = [];
                    const data = ev.target.result;
                    const workbook = xlsx.read(data, { type: "binary" });
                    const wsname = workbook.SheetNames[0];
                    const ws = xlsx.utils.sheet_to_json(
                        workbook.Sheets[wsname],
                        {
                            header: 0,
                            defval: ""
                        }
                    );

                    //Validate input file:
                    if (Object.keys(ws[0]).length !== 1) {
                        this.$toast.error("File phải có 1 cột: MA_TT");
                        return;
                    }
                    if (!ws[0].hasOwnProperty("MA_TT")) {
                        this.$toast.error(
                            "Tên cột trong file không đúng định dạng: MA_TT"
                        );
                        return;
                    }

                    if (ws.length > 0) {
                        ws.forEach(item => {
                            this.maTT.push(item.MA_TT);
                        });
                    }
                } catch (e) {
                    console.log(e);
                    return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
                }
                if (this.maTT.length > 0) {
                    this.$toast.success(
                        `Có ${this.maTT.length} mã thanh toán từ file`
                    );
                }
            };
            fileReader.readAsBinaryString(files[0]);
        },
        async searchListChuyenTuyen() {
            let kieu = -1;
            let nvtcId = 0;
            if (this.isShowTC) {
                if (this.dataSelected != null && this.dataSelected != "") {
                    nvtcId = this.dataSelected;
                } else {
                    nvtcId = token.getNhanVienID();
                }
                kieu = 1;
            } else {
                nvtcId = token.getNguoiDungID();
                kieu = 0;
            }
            this.loading(true);
            let data = {
                pKieu: kieu,
                pNguoiId: nvtcId,
                pMaTT: this.maTT.toString()
            };
            let response = [];
            try {
                response = await this.getDSChuyenTuyen(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.loading(false);
                    if (
                        response.data.data !== undefined &&
                        response.data.data.length > 0
                    ) {
                    } else {
                        this.$toast.error(
                            "Không tìm thấy dữ liệu thỏa điều kiện!"
                        );
                    }
                } else {
                    this.loading(false);
                    this.$toast.error(response.data.message_detail);
                }
            } catch (error) {
                this.loading(false);
                this.$toast.error(error.response.data.message_detail);
            }

            return response;
        },
        async confirmChuyenTuyen() {
            if (this.itemSelected.length < 1) {
                this.$toast.error("Bạn chưa chọn phiếu xác nhận!");
                return;
            }

            await this.$bvModal
                .msgBoxConfirm("Bạn thật sự muốn xác nhận phiếu này không?", {
                    title: "Thông báo",
                    size: "lg",
                    buttonSize: "lg",
                    okVariant: "primary",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy bỏ",
                    hideHeaderClose: false,
                    centered: true,
                    "modal-class": ["f18", "text-center"]
                })
                .then(async value => {
                    if (value) {
                        this.loading(true);
                        let data = {
                            pDSGiaoPhieuid: this.itemSelected.toString(),
                            pMayCN: null
                        };
                        console.log(data);
                        const response = await API.putXacNhanPhieuChuyenTuyen(
                            this.axios,
                            data
                        ).catch(err => {
                            this.loading(false);
                            this.$toast.error(err.data.message_detail);
                        });
                        if (response.data.error_code === "BSS-00000000") {
                            this.loading(false);
                            this.$toast.success(`Cập nhật phiếu thành công!`);
                        } else {
                            this.loading(false);
                            //this.$toast.error(response.data.message_detail);
                        }
                        this.searchListChuyenTuyen();
                    }
                })
                .catch(err => {
                    this.loading(false);
                });
        },
        async cancelChuyenTuyen() {
            if (this.itemSelected.length < 1) {
                this.$toast.error("Bạn chưa chọn phiếu xác nhận!");
                return;
            }
            try {
                await this.$bvModal
                    .msgBoxConfirm(
                        "Bạn thật sự muốn hủy xác nhận phiếu này không?",
                        {
                            title: "Thông báo",
                            size: "lg",
                            buttonSize: "lg",
                            okVariant: "primary",
                            okTitle: "Đồng ý",
                            cancelTitle: "Hủy bỏ",
                            hideHeaderClose: false,
                            centered: true,
                            "modal-class": ["f18", "text-center"]
                        }
                    )
                    .then(async value => {
                        if (value) {
                            this.loading(true);
                            let data = {
                                pDSGiaoPhieuid: this.itemSelected.toString(),
                                pMayCN: null
                            };
                            const response = await API.putHuyXacNhanPhieuChuyenTuyen(
                                this.axios,
                                data
                            ).catch(err => {
                                this.loading(false);
                                this.$toast.error(err.data.message_detail);
                            });
                            if (response.data.error_code === "BSS-00000000") {
                                this.loading(false);
                                this.$toast.success(
                                    `Cập nhật phiếu thành công!`
                                );
                            } else {
                                this.loading(false);
                            }
                        }
                    })
                    .catch(err => {
                        this.loading(false);
                    });
                this.searchListChuyenTuyen();
            } catch (error) {}
        },
        itemSelectedChanged(value) {
            this.itemSelected = value;
        }
    },

    created() {
        EventBus.$on("chukycuocChange", this.chukycuocChangeHandler);
        this.clearData();
    },
    async mounted() {
        this.loading(true);
        let data = {
            pNguoiDungId: token.getNguoiDungID()
        };
        const response = await this.getDSNhanVienThuCuoc(data);
        if (response.data.error_code === "BSS-00000000") {
            this.loading(false);
            if (
                response.data.data !== undefined &&
                response.data.data.data !== undefined &&
                response.data.data.data.length > 0
            ) {
            } else {
                this.$toast.error(
                    "Không tìm thấy danh sách nhân viên thu cước thỏa điều kiện!"
                );
            }
        } else {
            this.loading(false);
            this.$toast.error(response.data.data.message_detail);
        }
    },
    destroyed() {
        EventBus.$off("chukycuocChange", this.chukycuocChangeHandler);
    }
};
</script>
