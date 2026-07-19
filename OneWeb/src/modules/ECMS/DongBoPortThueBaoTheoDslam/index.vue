<template src="./index.template.html"></template>
<script>
    import { GridPlugin, Page, VirtualScroll, Sort, Filter, Selection, ExcelExport, PdfExport, Toolbar} from '@syncfusion/ej2-vue-grids'
    import ModalXuatDuLieu from '../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu'
    import { PdfTrueTypeFont } from '@syncfusion/ej2-pdf-export'
    import { adventProFont } from "./font.js"
    import { L10n } from '@syncfusion/ej2-base'
    L10n.load({
        'vi-VN': {
            grid: {
                EmptyRecord: 'Không có bản ghi nào để hiển thị',
            },
            pager: {
                pagerDropDown: 'Số bảng ghi trên một trang',
                //totalItemsInfo: '({0} items)',
                totalItemInfo: '({0} bảng ghi)',
                Item: 'bảng ghi',
                Items: 'Số bảng ghi',
                currentPageInfo: '{0} của {1} trang'
            }
        }
    })
    export default {
        components:{ModalXuatDuLieu},
        provide: {
            grid: [Page, Sort, Filter, Selection, ExcelExport, PdfExport, Toolbar]
        },
        data: function () {
            return {
                dsBras: [],
                selectedBras: '',
                maThueBao: '',
                dsDslam: [],
                selectedDslam: '',
                dsThueBao: [],
                isDisableNhapMoi: false,
                isDisableKichHoat: true,
                isDisableXoa: true,
                isDisableHoanThien: true,
                isDisableGhiLai: true,
                dsThueBaoChon: [],
                dsThuocTinh:[],
                // toolbar: ['ExcelExport', 'PdfExport'],
                selectionOptions: {type: 'Multiple', mode: 'Row'},
                pageSettings: { pageSizes: [20, 50, 100, 200], pageSize: 20, pageCount: 4 },
                filterOptions: {
                    type: 'FilterBar',
                    mode: 'Immediate',
                    showFilterBarStatus:false
                },
                modalXoa: false
            }
        },
        created: async function () {
            let dataBtn = {
                isDisableNhapMoi: false,
                isDisableKichHoat: true,
                isDisableXoa: true,
                isDisableHoanThien: true,
                isDisableGhiLai: true
            }
            this.setDisableBtn(dataBtn)
            await this.getDsBras()
            this.selectedBras = this.dsBras.length > 0 ? this.dsBras[0].BRAS_ID : ''
            await this.getdsDslam()
            this.selectedDslam = this.dsDslam.length > 0 ? this.dsDslam[0].DSLAM_ID : ''
        },
        methods: {
            getDsBras: async function () {
                let rs = await this.$root.context.get(
                    '/web-ecms/DongBoPortThueBao/danh-sach-bras'
                )
                this.dsBras = rs.data
            },
            getdsDslam: async function () {
                this.$root.showLoading(true)
                try {
                    let rs = await this.$root.context.get('/web-ecms/DongBoPortThueBao/danh-sach-dslam-theo-bras',
                        {brasId: this.selectedBras}
                    )
                    if (rs.data.length > 0) {
                        this.dsDslam = rs.data
                    } else {
                        this.dsDslam = rs.data
                        this.$root.$toast.error('Bras này không có dslam.')
                    }
                } catch (e) {

                } finally {
                    this.$root.showLoading(false)
                }
            },
            onChangeSelectedBras: async function () {
                await this.getdsDslam()
                this.selectedDslam = this.dsDslam.length > 0 ? this.dsDslam[0].DSLAM_ID : ''
            },
            onChangeSelectedDslam: async function () {
                await this.getDsThueBao()
                // this.selectedDslam = this.dsDslam.length > 0 ? this.dsDslam[0].DSLAM_ID : ''
            },
            getDsThueBao: async function () {
                if (this.selectedDslam == null || this.selectedDslam == '') {
                    return false
                }
                if (this.isDisableNhapMoi == false) {
                    await this.getDsThueBaoDangDongBo()
                } else {
                    await this.getDsThueBaoTrongDanhBa()
                }
            },
            getDsThueBaoTrongDanhBa: async function () {
                //this.$root.$toast.info('danh ba')
                this.$root.showLoading(true)
                try {
                    let rs = await this.$root.context.get('/web-ecms/DongBoPortThueBao/danh-sach-thue-bao-trong-danh-ba',
                        {
                            dslamId: this.selectedDslam,
                            pageNum: 1,
                            pageSize: 10000
                        }
                    )
                    if (rs.data.tongSoRows > 0) {
                        this.dsThueBao = rs.data.ds
                    } else {

                    }
                } catch (e) {

                } finally {
                    this.$root.showLoading(false)
                }
            },
            getDsThueBaoDangDongBo: async function () {
                //this.$root.$toast.info('dong bo')
                this.$root.showLoading(true)

                try {
                    let rs = await this.$root.context.get('/web-ecms/DongBoPortThueBao/danh-sach-thue-bao-dang-dong-bo',
                        {
                            dslamId: this.selectedDslam,
                            pageNum: 1,
                            pageSize: 10000
                        }
                    )
                    //console.log(rs)

                    if (rs.data.tongSoRows > 0) {
                        this.dsThueBao = rs.data.ds
                        let dataBtn = {
                            isDisableNhapMoi: false,
                            isDisableKichHoat: false,
                            isDisableXoa: false,
                            isDisableHoanThien: false,
                            isDisableGhiLai: true
                        }
                        this.setDisableBtn(dataBtn)
                    } else {
                        this.dsThueBao = null
                        let dataBtn = {
                            isDisableNhapMoi: true,
                            isDisableKichHoat: true,
                            isDisableXoa: true,
                            isDisableHoanThien: true,
                            isDisableGhiLai: false
                        }
                        this.setDisableBtn(dataBtn)
                    }
                } catch (e) {

                } finally {
                    this.$root.showLoading(false)
                }
            },
            selectedThueBao: async function (args) {
                this.maThueBao = args.data.MA_TB
                if (args.data.length > 1) {
                    args.data.forEach(item => this.dsThueBaoChon.push(item))
                } else this.dsThueBaoChon.push(args.data)
            },
            deselectedThueBao: async function (args) {
                if (args.data.length > 1) {
                    args.data.forEach(item => {
                        let index = this.dsThueBaoChon.findIndex((element) => element.MA_TB === item.MA_TB)
                        if (index !== -1) {
                            this.dsThueBaoChon.splice(index, 1)
                        }
                    })
                } else this.dsThueBaoChon.splice(this.dsThueBaoChon.findIndex((element) => element.MA_TB === args.data.MA_TB), 1)
            },
            onClickExportExcel: async function () {
                let exportProperties = {
                    dataSource: this.dsThueBao
                }
                this.$refs.grid.excelExport(exportProperties)
            },
            // onClickExportPdf: async function () {
            //     let exportProperties = {
            //         //dataSource: this.dsThueBao,
            //         fileName: 'new.pdf',
            //         theme: {
            //             header: {font:  new PdfTrueTypeFont(adventProFont, 12) },
            //             caption: { font: new PdfTrueTypeFont(adventProFont, 10) },
            //             record: { font: new PdfTrueTypeFont(adventProFont, 9) }
            //         }
            //     }
            //     this.$refs.grid.pdfExport(exportProperties)
            // },

            onClickXuatFile: async function () {
                this.$root.showLoading(true)
                try {
                    if (this.dsThueBao) {
                        this.dsThuocTinh = []
                        for (let key in this.dsThueBao[0]){
                            this.dsThuocTinh.push(key)
                        }
                        this.$bvModal.show('modal-xuat-dulieu')
                    }else {
                        this.$toast.error("Danh sách không có dữ liệu để xuất.")
                    }
                } catch (error) {
                    console.log(error)
                } finally {
                    this.$root.showLoading(false)
                }
            },

            onClickHuy: async function () {
                let dataBtn = {
                    isDisableNhapMoi: false,
                    isDisableKichHoat: true,
                    isDisableXoa: true,
                    isDisableHoanThien: true,
                    isDisableGhiLai: true
                }
                this.setDisableBtn(dataBtn)
                this.dsThueBao = null
            },
            onClickNhapMoi: async function () {
                let dataBtn = {
                    isDisableNhapMoi: true,
                    isDisableKichHoat: true,
                    isDisableXoa: true,
                    isDisableHoanThien: true,
                    isDisableGhiLai: false
                }
                this.setDisableBtn(dataBtn)
                this.dsThueBao = null
            },
            onClickGhiLai: async function () {
                if (this.dsThueBaoChon.length == 0){
                    this.$root.$toast.error('Bạn chưa chọn thuê bao.')
                    return
                }

                let data = {
                    dsDongBoPort: [],
                    dslamId: this.selectedDslam,
                    pageSize: 10000,
                    pageNum: 1
                }
                this.dsThueBaoChon.forEach(item => {
                    let element = {
                        brasIdVDC: item.BRAS_ID_VDC,
                        dichVuVTId: item.DICHVUVT_ID,
                        dslamIdVDC: item.DSLAM_ID_VDC,
                        ip: item.IP,
                        loaiTBId: item.LOAITB_ID,
                        maTB: item.MA_TB,
                        port: item.PORT,
                        portId: item.PORT_ID,
                        rack: item.RACK,
                        selfId01: item.SELFID01,
                        selfId02: item.SELFID02,
                        selfId03: item.SELFID03,
                        selfId04: item.SELFID04,
                        shelf: item.SHELF,
                        slot: item.SLOT,
                        system: item.SYSTEM,
                        thamSo: item.THAMSO,
                        vci: item.VCI,
                        vciVpiId: item.VCI_VPI_ID,
                        vpi: item.VPI
                    }
                    data.dsDongBoPort.push(element)
                })

                this.$root.showLoading(true)
                try {
                    let response = await this.$root.context.post('/web-ecms/DongBoPortThueBao/them-dong-bo-port-thue-bao', data)
                    if (response.error_code === 'BSS-00000000') {
                        this.$root.$toast.success('Thêm đồng bộ port thành công.')
                        let dataBtn = {
                            isDisableNhapMoi: false,
                            isDisableKichHoat: false,
                            isDisableXoa: false,
                            isDisableHoanThien: false,
                            isDisableGhiLai: true
                        }
                        this.setDisableBtn(dataBtn)
                        this.getDsThueBaoDangDongBo()
                    }
                } catch (e) {
                    console.log(e)
                    //this.$root.$toast.error(e.message)
                } finally {
                    this.$root.showLoading(false)
                }
            },
            onClickXoa() {
                if (!this.isDisableXoa) {
                    this.modalXoa = !this.modalXoa;
                }
            },
            xacNhanXoa: async function () {
                console.log(this.dsThueBaoChon)
                if (this.dsThueBaoChon.length == 0){
                    this.$root.$toast.error('Bạn chưa chọn thuê bao.')
                    return
                }

                let data = {
                    dsDongBoPortId: [],
                    dslamId: this.selectedDslam,
                    pageSize: 10000,
                    pageNum: 1
                }

                await this.dsThueBaoChon.forEach(item => {
                    if (item.TRANGTHAI_ID != 0){
                        this.$root.$toast.error("Thuê bao đã qua bước kích hoạt, không thể xóa.")
                        return
                    }
                    data.dsDongBoPortId.push(item.DONGBO_ID)
                })
                this.$root.showLoading(true)
                try {
                    let response = await this.$root.context.post('/web-ecms/DongBoPortThueBao/xoa-dong-bo-port-thue-bao', data)
                    if (response.error_code === 'BSS-00000000') {
                        this.$root.$toast.success('Xóa đồng bộ port thành công.')
                        let dataBtn = {
                            isDisableNhapMoi: false,
                            isDisableKichHoat: false,
                            isDisableXoa: false,
                            isDisableHoanThien: false,
                            isDisableGhiLai: true
                        }
                        this.setDisableBtn(dataBtn)
                        this.getDsThueBaoDangDongBo()
                    }
                } catch (e) {
                    console.log(e)
                    //this.$root.$toast.error(e.message)
                } finally {
                    this.$root.showLoading(false)
                }
            },
            onClickKichHoat: async function () {
                if (this.dsThueBaoChon.length == 0){
                    this.$root.$toast.error('Bạn chưa chọn thuê bao.')
                    return
                }

                let data = {
                    dsDongBoPortId: [],
                    dslamId: this.selectedDslam,
                    pageSize: 10000,
                    pageNum: 1
                }
                this.dsThueBaoChon.forEach(item => {
                    if (item.TRANGTHAI_ID != 0){
                        this.$root.$toast.error("Thuê bao đã qua bước kích hoạt.")
                        return
                    }
                    data.dsDongBoPortId.push(item.DONGBO_ID)
                })
                this.$root.showLoading(true)
                try {
                    let response = await this.$root.context.post('/web-ecms/DongBoPortThueBao/kich-hoat-dong-bo-port-thue-bao', data)
                    if (response.error_code === 'BSS-00000000') {
                        this.$root.$toast.success('Kích hoạt đồng bộ port thành công.')
                        let dataBtn = {
                            isDisableNhapMoi: false,
                            isDisableKichHoat: false,
                            isDisableXoa: false,
                            isDisableHoanThien: false,
                            isDisableGhiLai: true
                        }
                        this.setDisableBtn(dataBtn)
                        this.getDsThueBaoDangDongBo()
                    }
                } catch (e) {
                    console.log(e)
                    //this.$root.$toast.error(e.message)
                } finally {
                    this.$root.showLoading(false)
                }
            },
            onClickHoanThien: async function () {
                if (this.dsThueBaoChon.length == 0){
                    this.$root.$toast.error('Bạn chưa chọn thuê bao.')
                    return
                }

                let data = {
                    dsDongBoPortId: [],
                    dslamId: this.selectedDslam,
                    pageSize: 10000,
                    pageNum: 1
                }
                this.dsThueBaoChon.forEach(item => {
                    if (item.TRANGTHAI_ID != 5){
                        this.$root.$toast.error("Thuê bao chưa được kích hoạt.")
                        return
                    }
                    data.dsDongBoPortId.push(item.DONGBO_ID)
                })
                this.$root.showLoading(true)
                try {
                    let response = await this.$root.context.post('/web-ecms/DongBoPortThueBao/hoan-thien-dong-bo-port-thue-bao', data)
                    if (response.error_code === 'BSS-00000000') {
                        this.$root.$toast.success('Hoàn thiện thành công.')
                        let dataBtn = {
                            isDisableNhapMoi: false,
                            isDisableKichHoat: false,
                            isDisableXoa: false,
                            isDisableHoanThien: false,
                            isDisableGhiLai: true
                        }
                        this.setDisableBtn(dataBtn)
                        this.getDsThueBaoDangDongBo()
                    }
                } catch (e) {
                    console.log(e)
                    //this.$root.$toast.error(e.message)
                } finally {
                    this.$root.showLoading(false)
                }
            },
            setDisableBtn: function (data) {
                this.isDisableNhapMoi = data.isDisableNhapMoi
                this.isDisableKichHoat = data.isDisableKichHoat
                this.isDisableXoa = data.isDisableXoa
                this.isDisableHoanThien = data.isDisableHoanThien
                this.isDisableGhiLai = data.isDisableGhiLai
            }
        }
    }
</script>
<style>
  div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
  }
  .dashboard-header {
    z-index: 9 !important;
  }

  .disabled:hover {
    background-color: white !important;
  }

  .disabled a {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }

  .disabled a:hover {
    cursor: default;
    color: #6c757d;
  }
</style>
