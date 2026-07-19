<template src="./index.html">
</template>


<script>
import KhaiBaoCongInterface from "../../Modal/khaibao_cong_interface";

export default {
    components: { KhaiBaoCongInterface },
    props: {
        modelParent: {
            type: String,
            default: () => ''
        },
        chungLoaiThietBiId: {
            type: Number,
            default: 0
        },
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            card: {
                model: '',
                manufacturer: '',
                height: '',
                note: '',
                validity: 1,
                fullDepth: 1,
                parent: '',
                capacity: ''
            },
            listOfInterfacePortTypes: [],
            lichSuItemList: [],
            listOfManufacturers: [],
            phanLoai: null
        }
    },
    created: async function () {
        try {
            this.$root.showLoading(true);
            // this.thuocChungLoaiTbList = await this.getDmChungLoaiThietBi();
            // this.chungLoaiTbItemList = await this.getDsChungLoaiThietBi();
            this.listOfManufacturers = await this.getDmHangSx();
        } finally {
            this.$root.showLoading(false);
        }
    },
    methods: {
        getDmHangSx: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/hatang_mang", {
                    loaiDanhMuc: "HATANG_HANGSX",
                    thamSo1: null,
                    thamSo2: null
                });
                result = rs.data;
            } catch (e) {
                if (!(e.response == null || e.response == undefined)) {
                    if (!(e.response.data == null || e.response.data == undefined)) {
                        this.$root.toastError(e.response.data.message_detail);
                    }
                }
            }
            return result;
        },
        saveCard: async function (args) {
            let result = false;
            try {
                let rs = await this.$root.context.post("/web-ecms/chungloai-thietbi/capnhat-chungloai-thietbi", {
                    id: null,
                    model: args.model,
                    nhasx_id: args.manufacturer,
                    chungloai_id: this.chungLoaiThietBiId,
                    chieucao: args.height,
                    cs_dinhdanh: args.capacity,
                    ghi_chu: args.note,
                    hieu_luc: args.validity,
                    fulldepth: args.fullDepth,
                    phanLoai: this.phanLoai,
                    so_line_card: null,
                    so_power: null,
                    so_control_card: null
                });
                result = rs.data;
            } catch (e) {
                console.log(e.response.data.message_detail);
                throw e;
            }
            return result;
        },
        grid_onCommandClicked(name, dataItem) {
            // if (name === 'SUA') {
            //     this.setThongTinCongItem(dataItem);
            //     this.updateCong();
            // }
            // if (name === 'XOA') {
            //     this.onDeleteCongInterface(dataItem);
            // }
        },
        grdItems_RowSelected: async function (args) {
            // try {
            //     this.$root.showLoading(true);
            //     this.setCurrentItem(args.data);

            // } finally {
            //     this.$root.showLoading(false);
            // }

        },
        grdItems_RowDeselected: async function () {
            // let items = this.grdItems.getSelectedRecords();
            // if (!(items == null || items.length == 0)) {
            //     try {
            //         this.$root.showLoading(true);
            //         this.setCurrentItem(items[0]);

            //     } finally {
            //         this.$root.showLoading(false);
            //     }
            // } else {
            //     this.setCurrentItemDefault();
            // }
        },
        // Column template of Interface Port Types table.
        getLoaiCongText: function (value) {
            let result = null;
            if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.loaiCongListItems) {
                result = this.$refs.dialogKhaiBaoCongInterface.loaiCongListItems.find((item) => item.ID == value);
            }
            return result ? result.TEN : value;
        },
        getColumnLoaiCongTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<span>{{ getText() }}</span>`,
                        methods: {
                            getText: function () {
                                return parent.getLoaiCongText(this.data.loaiCongId);
                            },
                        },
                    },
                };
            };
        },
        getMucDichSuDungCongText: function (value) {
            let result = null;
            if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.mucDichSdCongListItems) {
                result = this.$refs.dialogKhaiBaoCongInterface.mucDichSdCongListItems.find((item) => item.ID == value);
            }
            return result ? result.TEN : value;
        },
        getColumnMucDichSuDungCongTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<span>{{ getText() }}</span>`,
                        methods: {
                            getText: function () {
                                return parent.getMucDichSuDungCongText(this.data.mucDichId);
                            },
                        },
                    },
                };
            };
        },
        getBangThongText: function (value) {
            let result = null;
            if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.bangThongListItems) {
                result = this.$refs.dialogKhaiBaoCongInterface.bangThongListItems.find((item) => item.ID == value);
            }
            return result ? result.TEN : value;
        },
        getColumnBangThongCongTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<span>{{ getText() }}</span>`,
                        methods: {
                            getText: function () {
                                return parent.getBangThongText(this.data.bangThongId);
                            },
                        },
                    },
                };
            };
        },
        getThongTinModuleText: function (value) {
            let result = null;
            if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.thongTinModuleListItems) {
                result = this.$refs.dialogKhaiBaoCongInterface.thongTinModuleListItems.find((item) => item.ID == value);
            }
            return result ? result.TEN : value;
        },
        getColumnThongTinModuleTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<span>{{ getText() }}</span>`,
                        methods: {
                            getText: function () {
                                return parent.getThongTinModuleText(this.data.thongTinModuleId);
                            },
                        },
                    },
                };
            };
        },
        getKetNoiText: function (value) {
            let result = null;
            if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.ketNoiListItems) {
                result = this.$refs.dialogKhaiBaoCongInterface.ketNoiListItems.find((item) => item.ID == value);
            }
            return result ? result.TEN : value;
        },
        getColumnKetNoiTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<span>{{ getText() }}</span>`,
                        methods: {
                            getText: function () {
                                return parent.getKetNoiText(this.data.ketNoiId);
                            },
                        },
                    },
                };
            };
        },
        getHieuLucText: function (value) {
            return value == 1 ? "Hiệu lực" : "Không hiệu lực";
        },
        getColumnHieuLucTemplate: function (parent) {
            return function () {
                return {
                    template: {
                        template: `<span>{{ getText() }}</span>`,
                        methods: {
                            getText: function () {
                                return parent.getHieuLucText(this.data.HIEU_LUC);
                            },
                        },
                    },
                };
            };
        },

        khaiBaoCong() {
            this.$refs.dialogKhaiBaoCongInterface.dialogOpen();
        },
        saveInterfacePortType: function (interfacePortType) {
            if (interfacePortType) {
                this.listOfInterfacePortTypes.push(interfacePortType);
            }
        },
        openModalThemCard: function (phanLoai) {
            this.card.parent = this.modelParent;
            this.phanLoai = phanLoai;
            this.$refs.dialogInterfaceThemCard.show();
            document.addEventListener('keydown', this.handleEscKey);
        },
        closeModalThemCard: function () {
            this.resetData();
            this.$refs.dialogInterfaceThemCard.hide();
            document.removeEventListener('keydown', this.handleEscKey);
        },
        resetData: function () {
            this.card = {
                model: '',
                manufacturer: '',
                height: '',
                note: '',
                validity: 1,
                fullDepth: 1,
                parent: '',
                capacity: ''
            };
            this.phanLoai = null;
        },
        onSave: async function () {
            if (this.validateControls()) {
                let flag = await this.$confirm(
                    "Bạn có muốn lưu thông tin này không ?",
                    "Xác nhận",
                    {
                        confirmButtonText: "Có",
                        cancelButtonText: "Không",
                        type: "info",
                        dangerouslyUseHTMLString: true,
                    }
                )
                    .then((s) => true)
                    .catch((e) => {
                        return false;
                    });
                if (flag) {
                    try {
                        this.$root.showLoading(true);
                        let rs = await this.saveCard(this.card);
                        if (rs) {
                            this.$root.toastSuccess("Thêm thiết bị thành công");
                            
                            if (this.phanLoai == 1) {
                                this.$emit('loadDsCards');
                            }
                            else if (this.phanLoai == 2) {
                                this.$emit('loadDsModule');
                            }
                            else if (this.phanLoai == 3) {
                                this.$emit('loadDsControlCards');
                            }
                        }
                        else {
                            this.$root.toastError("Thêm thiết bị thất bại");
                        }
                    } catch (err) {
                        this.$root.toastError(err.response.data.message_detail);
                    }
                    finally {
                        this.closeModalThemCard();
                        this.$root.showLoading(false);
                    }
                }
            }
        },
        validateControls: function () {
            let msg = "";
            if (this.card.model == null || this.card.model == "") {
                msg = "Model thiết bị không được để trống.";
            }
            if (this.card.manufacturer == null || this.card.manufacturer == 0) {
                if (msg != "") msg += "\n\r";
                msg += "Nhà sản xuất không được để trống.";
            }
            if (msg != "") {
                this.$root.toastError(msg);
                return false;
            }
            return true;
        },
        handleEscKey: function (event) {
            if (event.key === 'Escape') {
                this.closeModalThemCard();
            }
        },
    }
}
</script>
