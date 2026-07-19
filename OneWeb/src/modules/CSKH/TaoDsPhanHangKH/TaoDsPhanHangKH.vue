<template src="./TaoDsPhanHangKH.html"></template>
<style src="./TaoDsPhanHangKH.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import KyCuoc from "../../QLTN/components/KyCuoc.vue";
import moment from "moment";
import YesNoModal from "../utils/YesNoModal.vue";
import frmChiTietTaoDL from "./popup/frmChiTietTaoDL.vue";
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze } from "@syncfusion/ej2-vue-grids"
import { L10n } from '@syncfusion/ej2-base'
import XuatFile from "../utils/XuatFile.vue";
import { currency } from '@/filters/currency'
L10n.load({
    'vi-VN': {
        grid: {
            FilterButton: 'Lọc',
            ClearButton: 'Đóng',
            SelectAll: 'Chọn tất cả'
        }
    }
})
export default {
    provide: {
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze ]
    },
    components: {
        appKyCuoc: KyCuoc,
        breadcrumb,
        appYesNoModal: YesNoModal,
        frmChiTietTaoDL,
        appModalXuatDuLieu: XuatFile,
    },
    computed: {
        kyPhanLoai() {
            let today = new Date();
            let year = today.getFullYear();
            return [
                {
                    ID: 1,
                    text:
                        "Quý 1: " + (year - 1) + "1001 - " + (year - 1) + "1201"
                },
                { ID: 2, text: "Quý 2: " + year + "0101 - " + year + "0301" },
                { ID: 3, text: "Quý 3: " + year + "0401 - " + year + "0601" },
                { ID: 4, text: "Quý 4: " + year + "0701 - " + year + "0901" }
            ];
        },
        dsThuocTinh() {
            return this.columnsDsKhachHang.map(x => x.fieldName)
        },
        pKyPH() {
            return this.popupKyPH
        },
        pNgayCN() {
            return this.popupNgayCN
        }
    },
    data() {
        return {
            header: {
                title: "PHÂN HẠNG KHÁCH HÀNG",
                list: [
                    {
                        name: "Chăm sóc khách hàng",
                        link: "/cskh"
                    },
                    {
                        name: "Quản lý khách hàng lớn",
                        link: "/cskh/homequanlykhl"
                    },
                    {
                        name: "Tạo DS phân hạng KH",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            pCboKyPhanLoai: 1,
            kyCuoc: moment(new Date()).add(-1, 'months').format("YYYYMM") + '01',
            p_KHLON_ID: -1,
            pDsLanTao: [],
            pDtDSKHL: [],

            tsbtnNhapMoiEnabled: true,
            tsbtnLayDSEnabled: true,
            tsbtnGhiLaiEnabled: true,
            tsbtnThemKHL_LeEnabled: true,
            tsbtnXoaEnabled: true,
            tsbtnNhapExcelEnabled: true,

            popupKyPH: 0,
            popupNgayCN: moment(new Date()).format("DD/MM/YYYY"),
            index: 0,
            commandChucNang: [
                {
                    type: "Xóa",
                    buttonOption: {
                        cssClass: "btn btn-danger btn-delete-lantao fa fa-times"
                    }
                }
            ],

            columnsDsKhachHang: [
                { fieldName: "MA_KH", headerText: "Mã KH", allowSorting: true, allowFiltering: true, }, 
                { fieldName: "TEN_KH", headerText: "Tên KH", allowSorting: true, allowFiltering: true }, 
                { fieldName: "DIACHI_KH", headerText: "Địa chỉ KH", allowSorting: true, allowFiltering: true }, 
                { fieldName: "BINHQUAN_DT", headerText: "Bình quân DT", allowSorting: true, allowFiltering: true, cellCssClass: 'text-right font-weight-bold text-danger' }, 
                { fieldName: "MA_PLKH", headerText: "Phân loại KH", allowSorting: true, allowFiltering: true }, 
                { fieldName: "THOIGIAN_SD", headerText: "Thời gian SD (năm)", allowSorting: true, allowFiltering: true }, 
                { fieldName: "DIEM_DT", headerText: "Điểm", allowSorting: true, allowFiltering: true }, 
                { fieldName: "PHANHANGKH_ID", headerText: "Phân hạng", allowSorting: true, allowFiltering: true },
                {
                    fieldName: "THAOTAC",
                    headerText: "",
                    allowFiltering: false,
                    width: 50,
                    commands: [
                        {
                            type: "Xóa",
                            buttonOption: {
                                cssClass: "btn btn-danger btn-delete-lantao",
                                iconCss: " fa fa-times"
                            }
                        }
                    ]
                }
            ],
            columnsDsKhachHangXuatFile: [
                {
                    fieldName: "NGUOIDUNG_ID",
                    headerText: "NGUOIDUNG_ID",
                    allowSorting: true,
                    allowFiltering: true,
                },
                {
                    fieldName: "KHACHHANG_ID",
                    headerText: "KHACHHANG_ID",
                    allowSorting: true,
                    allowFiltering: true,
                },
                {
                    fieldName: "MA_KH",
                    headerText: "MA_KH",
                    allowSorting: true,
                    allowFiltering: true,
                },
                {
                    fieldName: "TEN_KH",
                    headerText: "TEN_KH",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "DIACHI_KH",
                    headerText: "DIACHI_KH",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "NGUOI_CN",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "PHANLOAIKH_ID",
                    headerText: "PHANLOAIKH_ID",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "BINHQUAN_DT",
                    headerText: "BINHQUAN_DT",
                    allowSorting: true,
                    allowFiltering: true,
                    // customAttributes: "{class: 'text-right font-weight-bold text-danger'}",
                    cellCssClass: 'text-currency'
                },
                {
                    fieldName: "MA_PLKH",
                    headerText: "MA_PLKH",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "THOIGIAN_SD",
                    headerText: "THOIGIAN_SD",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "DIEM_DT",
                    headerText: "DIEM_DT",
                    allowSorting: true,
                    allowFiltering: true
                },
                
                {
                    fieldName: "DIEM_PLKH",
                    headerText: "DIEM_PLKH",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "DIEM_TGSD",
                    headerText: "DIEM_TGSD",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "TONGDIEM",
                    headerText: "TONGDIEM",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "PHANHANGKH_ID",
                    headerText: "PHANHANGKH_ID",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "CHUKYNO_TU",
                    headerText: "CHUKYNO_TU",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "CHUKYNO_DEN",
                    headerText: "CHUKYNO_DEN",
                    allowSorting: true,
                    allowFiltering: true
                },
                 {
                    fieldName: "MA_PLKH",
                    headerText: "MA_PLKH",
                    allowSorting: true,
                    allowFiltering: true
                },
                {
                    fieldName: "CHON",
                    headerText: "CHON",
                    allowSorting: true,
                    allowFiltering: true
                },
            ],
            columnDsLanTao: [
                { fieldName: "NGAY_CN", headerText: "Ngày tạo", allowSorting: true, allowFiltering: true }, 
                { fieldName: "KY_PH", headerText: "Kỳ PH", allowSorting: true, allowFiltering: true }, 
                { fieldName: "SOLUONG", headerText: "Số lượng", allowSorting: true, allowFiltering: true },
                {
                    fieldName: "THAOTAC",
                    headerText: "",
                    allowFiltering: false,
                    width: 50,
                    commands: [
                        {
                            type: "Xóa",
                            buttonOption: {
                                cssClass: "btn btn-danger btn-delete-lantao",
                                iconCss: " fa fa-times"
                            }
                        }
                    ]
                }
            ],
            columnHuyLanTaoTemplate: function () {
                return {
                    template: {
                        template: `
                                    <div class="e-unboundcelldiv" style="display: inline-block">
                                        <button type="button" class="e-lib e-btn e-control pad3 lh14 icon one-trash btn-danger" @click="onXoaLanTao"></button>
                                    </div>
                                `,
                        data() {
                            return {
                                data: { }
                            }
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent
                            }
                        },
                        methods: {
                            onXoaLanTao() {
                                console.log(this.parent )
                                this.parent.onXoaLanTao(this.data)
                            }
                        }
                    
                    },
                    
                }
            },
            columnHuyKhachHangTemplate: function () {
                return {
                    template: {
                        template: `
                                    <div class="e-unboundcelldiv" style="display: inline-block">
                                        <button type="button" class="e-lib e-btn e-control pad3 lh14 icon one-trash btn-danger" @click="onXoaKhachHang"></button>
                                    </div>
                                `,
                        data() {
                            return {
                                data: { }
                            }
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent
                            }
                        },
                        methods: {
                            onXoaKhachHang() {
                                console.log(this.parent )
                                this.parent.onXoaKhachHang(this.data)
                            }
                        }
                    
                    },
                    
                }
            },
            pLanTaoXoaRecord: null,
            pDanhSachXoaRecord: null,
            dsKHLCommand: [
                {
                    type: "Xóa",
                    buttonOption: {
                        cssClass: "btn btn-danger btn-delete-lantao",
                        iconCss: " fa fa-times"
                    }
                }
            ],
        };
    },
    async mounted() {
        this.$root.showLoading(true);
        try {
            this.setButton(-1);
            this.$nextTick(async () => await this.getDsLanTao())
            // await this.getDsLanTao();
        } catch (e) {
        } finally {
            this.$root.showLoading(false);
        }
    },
    methods: {
        handleKyHoaDon(e) {
            console.log(e)
            this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no; //"20210101"; //
        },
        async getDsLanTao() {
            try {
                this.pDsLanTao = [];
                let response = await this.$root.context.get(
                    "/web-cskh/api/danh-sach-phan-hang/lan-tao",
                    {
                        kyHoaDon: this.kyCuoc,
                        pageNo: 1,
                        pageSize: 1000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    this.pDsLanTao = response.data.data || [];
                    this.pDsLanTao.forEach((e, i) => {
                        e.STT = i + 1
                        e.SOLUONG = currency(Math.round(e.SOLUONG))
                    });
                }
            } catch (e) {}
        },
        gridDsLanTaoCellCreated(e) {
            e.cellAllowHtml = true;
            console.log(this.index++);
            $(e.cellElement).append(
                '<span class="btn btn-danger btn-delete-lantao fa fa-times"></span>'
            );

            $(".btn-delete-lantao").on("click", e => {
                //chua co api xoa
                console.log(1);
            });
        },
        gridDsLanTaoRowDoubleClicked(rowIndex, dataItem) {
            this.popupNgayCN = moment(new Date()).format("DD/MM/YYYY");
            // this.popupKyPH = 0;
            this.popupNgayCN = dataItem.NGAY_CN;
            this.popupKyPH = dataItem.KY_PH;
            this.$refs['frmChiTietTaoDL'].kyPH = dataItem.KY_PH
            this.$refs['frmChiTietTaoDL'].ngayCN = dataItem.NGAY_CN
            this.$refs['frmChiTietTaoDL'].kieu = 3
            this.$refs['frmChiTietTaoDL'].openDialog()
            console.log('b', this.popupKyPH)
            // this.$bvModal.show("popup-fromChiTietTaoDL");
        },
        gridDsLanTaoRowChanged(dataItem) {
            console.log(2);
        },
        kiemTraDuLieu() {
            if (this.pCboKyPhanLoai == -1) {
                this.$root.toastError("Hãy chọn Kỳ phân hạng!");
                return false;
            }
            return true;
        },
        async onTaoDsClick() {
            if (this.kiemTraDuLieu()) {
                this.$root.showLoading(true);
                try {
                    this.setButton(4);
                    await this.bgwTaoDSRun();
                } catch (e) {
                } finally {
                    this.$root.showLoading(false);
                }
            }
        },
        async onGhiLaiClick() {
            if (this.pDtDSKHL.length <= 0) {
                this.$root.toastError(
                    "Hãy tạo danh sách phân hạng KH trước khi ghi lại!"
                );
                return;
            }
            this.$bvModal.show("popup-confirmGhiLai");
        },
        async onGhiLaiConfirm() {
            if (!this.kiemTraDuLieu()) {
                return;
            }
            try {
                this.$root.showLoading(true);
                let response = await this.$root.context.post(
                    "/web-cskh/api/danh-sach-phan-hang/cn-tao-dsph",
                    {
                        kyPhanLoai: this.pCboKyPhanLoai,
                        kyHoaDon: this.kyCuoc,
                        nguoiDungId: this.$auth.getNguoiDungID(),
                        mayCn: this.$root.token.getUserName()
                    }
                );

                if (response.error_code === "BSS-00000000") {
                    this.pDtDSKHL = []
                    await this.getDsLanTao();
                }
                
            } catch (e) {
                console.log(e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        async bgwTaoDSRun() {
            await this.taoDsPhanHangKH();
            this.setButton(5);
        },
        async taoDsPhanHangKH() {
            this.pDtDSKHL = [];
            try {
                let response = await this.$root.context.post("/web-cskh/api/danh-sach-phan-hang/tao-dsph",
                {
                  kyPhanLoai: this.pCboKyPhanLoai,
                  nguoiDungId: this.$root.token.getNguoiDungID(),
                  nguoiCn: this.$root.token.getUserName()
                });
                if (response.error_code === "BSS-00000000") {
                    let responseGetDsKH = await this.$root.context.post('/web-cskh/api/danh-sach-phan-hang/ds-phan-hang?pageNo=1&pageSize=10000', {kyPhanLoai: this.pCboKyPhanLoai})
                    if (responseGetDsKH.error_code === "BSS-00000000") {
                        this.pDtDSKHL = responseGetDsKH.data.data || [];
                        this.pDtDSKHL.forEach((e, i) => {
                            e.STT = i + 1
                            e.BINHQUAN_DT = currency(Math.round(e.BINHQUAN_DT))
                        });
                        this.$refs.gridDsKhachHang.selectAllItem = true;
                    }
                    
                }
            } catch (e) {}
        },
        clear() {
            this.p_KHLON_ID = -1;
        },
        setButton(kieu) {
            this.tsbtnGhiLaiEnabled = false;
            this.tsbtnHuyBoEnabled = false;

            if (kieu == -1) {
                //Bat dau
                this.tsbtnGhiLaiEnabled = true;
                this.tsbtnHuyBoEnabled = true;
            }

            if (kieu == 0) {
                //Bat dau
                this.tsbtnNhapMoiEnabled = true;
                this.Clear();
            }

            if (kieu == 1) {
                //Them moi
                this.tsbtnGhiLaiEnabled = true;
                this.tsbtnHuyBoEnabled = true;
                this.Clear();
            }

            if (kieu == 2) {
                //Huy
                this.tsbtnNhapMoiEnabled = true;
                this.tsbtnXoaEnabled = true;
                this.Clear();
            }

            if (kieu == 3) {
                //Edit
                this.tsbtnNhapMoiEnabled = true;
                this.tsbtnXoaEnabled = true;
                this.tsbtnGhiLaiEnabled = true;
                this.tsbtnHuyBoEnabled = true;
            }

            if (kieu == 4) {
                //background đang chạy
                this.tsbtnLayDSEnabled = false;
                this.tsbtnNhapMoiEnabled = false;
                this.tsbtnThemKHL_LeEnabled = false;
                this.tsbtnXoaEnabled = false;
                this.tsbtnNhapExcelEnabled = false;
            }

            if (kieu == 5) {
                //background chạy xong
                this.tsbtnNhapMoiEnabled = true;
                this.tsbtnLayDSEnabled = true;
                this.tsbtnGhiLaiEnabled = true;
                this.tsbtnThemKHL_LeEnabled = true;
                this.tsbtnXoaEnabled = true;
                this.tsbtnNhapExcelEnabled = true;
            }
        },
        xoaLanTao() {
            this.$bvModal.show("popup-confirmXoaLanTao");
        },
        async xoaLanTaoConfirm() {
            if (this.pLanTaoXoaRecord) {
                let response = await this.$root.context.post(
                    "/web-cskh/api/danh-sach-phan-hang/xoa-phan-hang-khach-hang",
                    {
                        kyHoaDon: this.kyCuoc,
                        kyPh: this.pLanTaoXoaRecord.KY_PH
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    this.$root.toastSuccess("Xóa thành công")
                    await this.getDsLanTao()
                }
                else {
                    this.$root.toastError("")
                }
            }
        },
        async gridDsKhachHangCommand(args) {
            console.log(args);
            this.pDanhSachXoaRecord = args.rowData;
            try {
              this.$root.showLoading(true)
                if (args.rowData) {
                    let response = await this.$root.context.post(
                        "/web-cskh/api/danh-sach-phan-hang/xoa-lan-tao-khach-hang",
                        {
                            khachHangId: args.rowData.KHACHHANG_ID,
                            nguoiDungId: this.$auth.getNguoiDungID()
                        }
                    );
                    console.log(args.rowData.STT - 1)
                    
                    if (response.error_code === "BSS-00000000") {
                        this.pDtDSKHL = this.pDtDSKHL.filter(x => x.KHACHHANG_ID != args.rowData.KHACHHANG_ID)
                    } else {
                        this.$root.toastError("Không thành công");
                    }
                }
            } catch (e) {
                this.$root.toastError("Không thành công");
            } finally {
              this.$root.showLoading(false)
            }
        },
        gridDsLanTaoCommand(args) {
            this.pLanTaoXoaRecord = args.rowData;
            this.$bvModal.show("popup-confirmXoaLanTao");
        },
        onXoaLanTao(data) {
            this.pLanTaoXoaRecord = data;
            this.$bvModal.show("popup-confirmXoaLanTao");
        },
        async onXoaKhachHang(data) {
            console.log(data);
            this.pDanhSachXoaRecord = data;
            try {
              this.$root.showLoading(true)
                if (data) {
                    let response = await this.$root.context.post(
                        "/web-cskh/api/danh-sach-phan-hang/xoa-lan-tao-khach-hang",
                        {
                            khachHangId: data.KHACHHANG_ID,
                            nguoiDungId: this.$auth.getNguoiDungID()
                        }
                    );
                    
                    if (response.error_code === "BSS-00000000") {
                        this.pDtDSKHL = this.pDtDSKHL.filter(x => x.KHACHHANG_ID != data.KHACHHANG_ID)
                    } else {
                        this.$root.toastError("Không thành công");
                    }
                }
            } catch (e) {
                this.$root.toastError("Không thành công");
            } finally {
              this.$root.showLoading(false)
            }
        },
        onXuatFileClicked() {
            if (this.pDtDSKHL.length <= 0) {
                this.$root.toastError("Không có dữ liệu để xuất!")
                return
            }
            this.$bvModal.show('modal-xuat-dulieu')
        }
    }
};
</script>
