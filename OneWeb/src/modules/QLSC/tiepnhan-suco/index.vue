<template src="./index.template.html"></template>
<script>
import ModalMucDoSuCo from './modal-mucdo-suco'
import XemThongTin from '../modal-xem-thongtin-new/index.vue'
import DanhSachThueBao from '../danhsach-thuebao/index.vue'
import Tab from './tab'
import TabContentConstructorTbi from './tab-item-tbi/index.js'
import TabContentConstructorIpcc from './tab-item-ipcc/index.js'
import ComboboxGridNhanVien from '../ComboboxGridNhanVien'
import GiaoPhieu from './giao-phieu'
import GiaoPhieuTts from './giao-phieu-tts'
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group, Aggregate, ContextMenu, ExcelExport } from "@syncfusion/ej2-vue-grids"
import { Page as TreePage, 
         Filter as TreeFilter,
         Sort as TreeSort,
         Resize as TreeResize,
         Freeze as TreeFreeze } from "@syncfusion/ej2-vue-treegrid";
import { L10n } from '@syncfusion/ej2-base'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'
import ModalKhoaPhieu from '../khoa-phieu/index.vue'
import XLSX from "xlsx"
import fileStorage from "../file-storage"
import Pagination from "@/components/Controls/Pagination";

let convertSecondToHms = function (seconds) {
    let hours   = Math.floor(seconds / 3600)
    let minutes = Math.floor((seconds - (hours * 3600)) / 60)
    seconds = seconds - (hours * 3600) - (minutes * 60)
    
    if (hours < 10) {hours = "0" + hours}
    if (minutes < 10) {minutes = "0" + minutes}
    if (seconds < 10) {seconds = "0" + seconds}
    return hours + ':' + minutes + ':' + seconds
}

let convertHmsToSecond = function(hms) {
    hms = !hms ? ['0'] : `${hms}`.split(':')
    let second = 0

    switch(hms.length) {
        case 3:
            second = parseInt(hms[0] * 3600) + parseInt(hms[1] * 60) + parseInt(hms[2])
            break
        case 2:
            second = parseInt(hms[0] * 60) + parseInt(hms[1]) 
            break
        case 1:
            second = parseInt(hms[0]) 
            break
        default:
            second = 0
    }

    return second
}

export default {
    provide: {
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group, Aggregate, ContextMenu, ExcelExport ],
        treegrid: [TreePage, TreeFilter, TreeSort, TreeResize, TreeFreeze]
    },
    components: { 
        Tab,
        GiaoPhieu,
        GiaoPhieuTts,
        ComboboxGridNhanVien,
        BssErrorMarker,
        BssRequiredMarker,
        ModalKhoaPhieu,
        XemThongTin,
        DanhSachThueBao,
        ModalMucDoSuCo,
        Pagination
    },
    validations: {
        nhomSuCoId: {
            required
        },
        ngaySuCo: {
            required,
            koLonHonNgayBh: (ngaySuCo, vm) => ngaySuCo <= vm.ngayBao
        },
        nhanVienBhId: {
            required
        },
        ngayBao: {
            required
        },
        dienThoaiLh: {
            required
        },
        noiDungSc: {
            required
        },
        henTlTu: {
            required: (henTlTu, vm) => !vm.chkHenTraLoi || !!henTlTu,
            koNhoThuaNgaySc: (henTlTu, vm) => henTlTu >= vm.ngaySuCo
        },
        henTlDen: {
            required: (henTlDen, vm) => !vm.chkHenTraLoi || !!henTlDen,
            koNhoThuaNgayTlTu: (henTlDen, vm) => henTlDen >= vm.henTlTu
        },
        phanLoaiSuCoId: {
            required
        },
    },
    data() {
        return {
            dsPhanLoaiSuCo: [],
            phanLoaiSuCoId: null,
            dsThamSoMd: [],
            isSuCoTts: false,
            dsNguonSuCo: [],
            nguonSuCoId: null,
            dsNhomSuCo: [],
            nhomSuCoId: null,
            nhomSuCoIdOld: null,
            nhanVienBhId: null,
            dienThoaiLh: null,
            ngaySuCo: new Date(),
            ngayBao: new Date(),
            chkHenTraLoi: false,
            henTlTu: null,
            henTlDen: null,
            noiDungSc: null,
            dsSuCoTam: [],
            dsSuCoCpn: [],
            dsSuCoDpnTree: [],
            dsSuCoDpnFlat: [],
            dsCongAnhHuong: [],
            dsTbaoAnhHuong: [],
            dsTinhTrangCntt: [],
            dsTinhTrangMvt: [],
            dsHeThongCntt: [],
            dsTinhTrangScCntt: [],
            dsTinhTrangScMvt: [],
            loaiThietHai: 0,
            suCoTmpDuocXoa: null,
            maSuCo: null,
            tam: false,
            keySuCoTmpDuocChon: null,
            idSuCoDpnDuocChon: null,
            dsIdSuCoCpnDuocChon: [],
            chkHenTraLoiEnabled: false,
            checkAllSuCoCpn: false,
            allowFilterSuCoTmp: true,
            allowFilterMdah: true,
            allowFilterSuCoCpn: true,
            showImagePanel: false,
            thuNhoTttnPanel: false,
            thuNhoDsSuCoTmpPanel: false,
            moRongTtscPanel: false,
            moRongDsSuCoTmpPanel: false,
            moRongDsSuCoDpnPanel: false,
            moRongDsSuCoCpnPanel: false,
            images: [],
            zoomImg: null,
            deletedImages: [],
            totalRecordsCount: 0,
            btnTiepNhan: {
                visible: false
            },
            btnCapNhat: {
                visible: false
            },
            btnNapLai: {
                visible: false
            },
            btnNhapMoi: {
                enabled: false
            },
            btnNhanHong: {
                enabled: false
            },
            btnXoaPhieu: {
                enabled: false
            },
            btnHuyBo: {
                visible: false
            },
            btnGiaoPhieu: {
                enabled: false
            },
            btnKhoaPhieu: {
                enabled: false
            },
            cboNhomSC: {
                enabled: false
            },
            dtpNgaySC: {
                enabled: false
            },
            dtpNgayBao: {
                enabled: false
            },
            txtNhanVienBao: {
                enabled: false
            },
            txtDienThoaiLH: {
                enabled: false
            },
            txtNoiDungSC: {
                enabled: false
            },
            cboAnhChup: {
                enabled: false
            },
            tab: {
                visible: true
            },
            btnTuDongChonCS: {
                visible: true
            },
            btnDongBoTT: {
                visible: true
            },
            columnSoThueBao: {
                caption: 'Số thuê bao'
            },
            radioLoaiThietHai: {
                visible: true
            },
            btnDsThueBao: {
                visible: true
            },
            btnXemChiTietMdahDoanhThu: {
                visible: true
            },
            contextMenuCpn: {
                visible: false
            },
            cboLoaiSuCo: {
                enabled: false
            },
            columnTemplate: function () {
                return { 
                    template: {
                        template: '<span class="col-mark-selected-row fa fa-play text-main"></span>',
                        data() {
                            return { 
                                data: { } 
                            }
                        }
                    }
                }
            },
            filterBarTemplate: {
                create: function (args) {

                    let div = document.createElement('div')
                    div.classList.add('input-icon-right')
                    
                    let input = document.createElement('input')
                    input.classList.add('form-control')
                    input.id = args.column.foreignKeyField

                    let span = document.createElement('span')
                    span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

                    div.append(input)
                    div.append(span)

                    return div
                },
                write: function (args) {

                    args.element.querySelector('input').addEventListener('input', (args) => {

                        let value = args.currentTarget.value
                        value = !!value ? value.trim() : value

                        if (!value) {
                            this.parent.removeFilteredColsByField(args.currentTarget.id);
                        } else {
                            this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
                        }
                    })
                }
            },
        }
    },
    computed: {
        nhomScText() {
            let nhomSuCo = this.dsNhomSuCo.find(item => item.NHOMSC_ID == this.nhomSuCoId)
            return !nhomSuCo ? 'mạng viễn thông' : nhomSuCo.NHOM_SC.toLowerCase()
        },
        suCoTmpDuocChon() {
            return this.dsSuCoTam.find(suCo => suCo.KEY == this.keySuCoTmpDuocChon)
        },
        suCoDpnDuocChon() {
            return this.dsSuCoDpnFlat.find(suCo => suCo.SUCO_ID == this.idSuCoDpnDuocChon)
        },
        componentTabTinhTrang() {
            return this.$refs['ref-tab-tinh-trang']
        },
        componentComboNhomSc() {
            return this.$refs['ref-combo-nhom-sc']
        },
        nhanVienBh() {
            return !this.nhanVienBhId ? null : this.$refs['ref-combo-nhanvien'].getItemByValue(this.nhanVienBhId)
        },
        viTri() {
            return !this.nhanVienBh ? null : this.nhanVienBh.VITRI
        },
        tenDonViBh() {
            return !this.nhanVienBh ? null : this.nhanVienBh.TEN_DV
        },
        idDonViBh() {
            return !this.nhanVienBh ? null : this.nhanVienBh.DONVI_ID
        },
        chucDanh() {
            return !this.nhanVienBh ? null : this.nhanVienBh.CHUCDANH
        },
        labelMaSuCo() {
            return this.btnHuyBo.visible ? 'Phiếu mới' : (!this.maSuCo ? '' : `Mã sự cố: ${this.maSuCo}`)
        },
        maSuCoTmp() {
            return !this.suCoTmpDuocXoa ? '' : this.suCoTmpDuocXoa.MA_SC
        },
        inputImageNames() {

            if (!this.images || this.images.length == 0) {
                return null
            }

            return this.images.map(image => {
                let fileName = image.relativeUrl.substr(image.relativeUrl.lastIndexOf('/') + 1)
                return fileName
            }).join(', ')
        },
        inputImageUrls() {
            return !this.images ? null : this.images.map(image => image.relativeUrl).join('|')
        },
        treeGridSuCoDpn() {
            return this.$refs['grid-suco-dpn']
        },
        QLSC_BATBUOC_NHAP_NGAYDEN() {
            let thamSo = this.dsThamSoMd.find(x => x.MA_TS == 'QLSC_BATBUOC_NHAP_NGAYDEN')
            return !thamSo ? -1 : (parseInt(thamSo.TEN_TS) == 1 ? 1 : -1)
        }
    },
    watch: {
        nhanVienBh(nhanVien) {
            let dienThoaiLh = !nhanVien ? null : nhanVien.SO_DT
            this.dienThoaiLh = !dienThoaiLh ? this.dienThoaiLh : dienThoaiLh
        },
        nhomSuCoId(newVal, oldVal) {

            if (!!newVal && newVal == this.nhomSuCoIdOld) {
                this.nhomSuCoIdOld = null
                return
            }

            if (this.componentTabTinhTrang.getDsComponentTabItem().length > 0) {
                this.nhomSuCoIdOld = oldVal
                this.$refs['ref-modal-xacnhan-doi-nhom-sc'].show()
                return
            }

            if (newVal == 0) {
                this.componentTabTinhTrang.setTabContentConstructor(TabContentConstructorIpcc)
            } else {
                this.componentTabTinhTrang.setTabContentConstructor(TabContentConstructorTbi)
            }
        },
        nguonSuCoId(nguonSuCoId) {

            if (nguonSuCoId == 1) { // PTTB
                this.btnNhapMoi.enabled = true
                this.btnXoaPhieu.enabled = true
                this.tab.visible = true
                this.btnTuDongChonCS.visible = true
                this.btnDongBoTT.visible = true
                this.columnSoThueBao.caption = 'Số thuê bao'
                this.radioLoaiThietHai.visible = true
                this.btnDsThueBao.visible = true
                this.btnXemChiTietMdahDoanhThu.visible = true
                this.contextMenuCpn.visible = false
            }

            if (nguonSuCoId == 2) { // Telsoft NMS
                this.btnNhapMoi.enabled = false
                this.btnXoaPhieu.enabled = false
                this.tab.visible = false
                this.btnTuDongChonCS.visible = false
                this.btnDongBoTT.visible = false
                this.columnSoThueBao.caption = 'Số BTS/NodeB'
                this.radioLoaiThietHai.visible = false
                this.loaiThietHai = 0
                this.btnDsThueBao.visible = false
                this.btnXemChiTietMdahDoanhThu.visible = false
                this.contextMenuCpn.visible = true
            }

            if (nguonSuCoId == 3) { // PRTG
                this.btnNhapMoi.enabled = false
                this.btnXoaPhieu.enabled = false
                this.tab.visible = false
                this.contextMenuCpn.visible = true
                this.btnTuDongChonCS.visible = false
                this.btnDongBoTT.visible = false
                this.columnSoThueBao.caption = 'Số thuê bao'
                this.radioLoaiThietHai.visible = true
                this.btnDsThueBao.visible = true
                this.btnXemChiTietMdahDoanhThu.visible = true
            }

            this.onClickBtnLayThongTin()
        },
        checkAllSuCoCpn(newVal) {

            if (newVal) {
                this.dsIdSuCoCpnDuocChon = this.dsSuCoCpn.map(item => item.SUCO_ID)
                return
            } 
            
            if (this.dsIdSuCoCpnDuocChon.length > 0 
                && this.dsIdSuCoCpnDuocChon.length == this.dsSuCoCpn.length) {
                this.dsIdSuCoCpnDuocChon = []
            }
        },
        dsIdSuCoCpnDuocChon(newVal) {
            this.checkAllSuCoCpn = newVal.length > 0 && newVal.length == this.dsSuCoCpn.length
        },
    },
    async mounted() {

        window.mytree = this.$refs['grid-suco-dpn']

        L10n.load({
            'vi-VN': {
                grid: {
                    EmptyRecord: 'Không có bản ghi nào để hiển thị'
                },
            }
        })

        try {
            this.loading(true)
            let loading1 = this.layDmNguonSuCo()
            let loading2 = this.layDmNhomSuCo()
            let loading3 = this.layDmHeThongCntt()
            let loading4 = this.layDmTinhTrangScCntt()
            let loading5 = this.layDmTinhTrangScMvt()
            let loading6 = this.layDsThamSoMd()
            this.dsPhanLoaiSuCo = [
                { PHANLOAI_ID: 1, PHANLOAI_SC: 'Thuộc phần mạng ngoại vi' },
                { PHANLOAI_ID: 0, PHANLOAI_SC: 'Không thuộc phần mạng ngoại vi' }
            ]
            await loading1
            await loading2
            await loading3
            await loading4
            await loading5
            await loading6
        } catch (err) {

        } finally {
            this.loading(false)
        }

        if (this.dsNguonSuCo && this.dsNguonSuCo.length > 0) {
            this.nguonSuCoId = this.dsNguonSuCo[0].NGUONSC_ID
        }

        this.componentTabTinhTrang.setGlobalData({
            dsHeThongCntt: [...this.dsHeThongCntt],
            dsTinhTrangScCntt: [...this.dsTinhTrangScCntt],
            dsTinhTrangScMvt: [...this.dsTinhTrangScMvt]
        })

        if (this.dsNhomSuCo && this.dsNhomSuCo.length > 0) {

            this.nhomSuCoId = this.dsNhomSuCo[0].NHOMSC_ID

            if (this.nhomSuCoId == 0) {
                this.componentTabTinhTrang.setTabContentConstructor(TabContentConstructorIpcc)
            } else {
                this.componentTabTinhTrang.setTabContentConstructor(TabContentConstructorTbi)
            }
        }

        await this.onClickBtnLayThongTin()
        this.xoaForm()
        this.btnTiepNhan.visible = false
        this.btnHuyBo.visible = false
        this.btnCapNhat.visible = false
        this.btnNapLai.visible = false
        this.btnNhanHong.enabled = false
        this.btnXoaPhieu.enabled = false
        this.btnGiaoPhieu.enabled = false
        this.btnKhoaPhieu.enabled = false

        setInterval(() => { 

            let gridSuCoTmp = this.$refs['grid-suco-tamthoi']

            if (!gridSuCoTmp) {
                return
            }

            gridSuCoTmp = gridSuCoTmp.grid

            if (!gridSuCoTmp.dataSource || gridSuCoTmp.dataSource.length == 0) {
                return
            }
            
            gridSuCoTmp.dataSource.forEach(item => ++item.SECOND)

            gridSuCoTmp.getCurrentViewRecords().forEach(item => {
                let hms = convertSecondToHms(item.SECOND)
                gridSuCoTmp.setCellValue(item.KEY, 'THOIGIAN', hms)
            })
        }, 1000)

        setInterval(() => { 

            if (!this.treeGridSuCoDpn) {
                return
            }

            let gridSuCoDpn = this.treeGridSuCoDpn.ej2Instances.grid

            if (!gridSuCoDpn || !gridSuCoDpn.dataSource || gridSuCoDpn.dataSource.length == 0) {
                return
            }

            gridSuCoDpn.dataSource.forEach(item => ++item.SECOND)

            gridSuCoDpn.getCurrentViewRecords().forEach(item => {
                let hms = convertSecondToHms(item.SECOND)
                gridSuCoDpn.setCellValue(item.KEY, 'THOIGIAN', hms)
            })
        }, 1000)
    },
    methods: {
        onContextMenuClickSuCoCpn(args) {

            let menuItemId = args.item.id

            switch(menuItemId) {
                case 'ctx-menu-huy-suco-cpn':
                    this.onClickBtnHuyPhieuCpn()
                    break
                case 'ctx-menu-xoa-suco-cpn':
                    this.onClickBtnXoaPhieuCpn()
                    break
            }
        },
        getContextMenuItemsSuCoCpn() {
            return [
                {text: 'Hủy phiếu', target: '.e-content', id: 'ctx-menu-huy-suco-cpn'},
                {text: 'Xóa phiếu', target: '.e-content', id: 'ctx-menu-xoa-suco-cpn'},
            ]
        },
        onContextMenuClickSuCoTmp(args) {
            if(args.item.id === 'ctx-menu-xoa-suco-tmp') {
                let rowData = args.rowInfo.rowData
                
                if (!!rowData) {
                    this.onClickXoaTiepNhan({...rowData})
                }
            }
        },
        getContextMenuItemsSuCoTmp() {
            return [{text: 'Xóa', target: '.e-content', id: 'ctx-menu-xoa-suco-tmp'}]
        },
        getHeaderTemplateChonAllSuCoCpn(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllSuCoCpn" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplageChuThichSuCoDpn(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <span :class="icon" :title="title" :style="{color: color}"></span>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                icon: null,
                                title: null,
                                color: 'transparent',
                                data: { }
                            }
                        },
                        mounted() {
                            if (!this.data.HENSUADEN) {
                                this.color = '#0176FF'
                                this.icon = 'icon one-change-date'
                                this.title = 'Sự cố chưa được thiết lập ngày hẹn sửa đến, vui lòng cập nhật để nhân viên chăm sóc khách hàng thông tin với khách hàng.'
                                return
                            }

                            if ((new Date(this.data.HENSUADEN)) < new Date()) {
                                this.color = 'orange'
                                this.icon = 'icon one-alert'
                                this.title = 'Sự cố đã quá hạn sửa chữa, hãy kiểm tra và nghiệm thu.'
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateNoiDungSc(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div style="position: relative; width: 100%; padding-right: 30px;">
                                <div style="width: 100%; overflow: hidden; text-overflow: ellipsis">
                                    {{data.NOIDUNG_SC}}
                                </div>
                                <button class="btn btn-sm btn-second" @click="onClick" style="position: absolute; right: 0; top: 0">...</button>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        methods: {
                            onClick() {
                                this.parent.onClickBtnXemNoiDungSc(this.data.NOIDUNG_SC)
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateChonSuCoCpn(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div class="check-action">
                                <input 
                                    type="checkbox" 
                                    class="check"
                                    v-model="parent.dsIdSuCoCpnDuocChon"
                                    :value="data.SUCO_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateChonSuCoTmp(parent) {
            return function () {
                return { 
                    template: {
                        template: `
                            <div class="check-action" @click="onClick()">
                                <input 
                                    type="radio" 
                                    class="check"
                                    v-model="parent.keySuCoTmpDuocChon"
                                    :value="data.KEY"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                            isSelected() {
                                return this.data.KEY == this.parent.keySuCoTmpDuocChon
                            }
                        },
                        methods: {
                            onClick() {
                                this.parent.keySuCoTmpDuocChon = this.isSelected ? null : this.data.KEY

                                if (!this.parent.keySuCoTmpDuocChon) {
                                    this.parent.isSuCoTts = false
                                } else {
                                    this.parent.isSuCoTts = this.parent.suCoTmpDuocChon.NGUONSC_ID == 6/*TTS*/
                                }

                                this.parent.onCheckSuCoTmp(this.parent.keySuCoTmpDuocChon)
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateChonSuCoDpn(parent) {
            return function () {
                return { 
                    template: {
                        template: `
                            <div class="check-action" @click="onClick()">
                                <input 
                                    type="radio" 
                                    class="check"
                                    v-model="parent.idSuCoDpnDuocChon"
                                    :value="data.SUCO_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                            isSelected() {
                                return this.data.SUCO_ID == this.parent.idSuCoDpnDuocChon
                            }
                        },
                        methods: {
                            onClick() {
                                this.parent.idSuCoDpnDuocChon = this.isSelected ? null : this.data.SUCO_ID
                                
                                if (!this.parent.idSuCoDpnDuocChon) {
                                    this.parent.isSuCoTts = false
                                } else {
                                    this.parent.isSuCoTts = this.parent.suCoDpnDuocChon.NGUONSC_ID == 6/*TTS*/
                                }
                                
                                this.parent.onCheckSuCoDpn(this.parent.idSuCoDpnDuocChon)
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateChiTietAhDoanhThu(parent) {
            return function() {
                return { 
                    template: {
                        template: `<button class="btn btn-sm btn-second" @click="onClick">...</button>`,
                        data() {
                            return {
                                parent: parent,
                                data: { }
                            }
                        },
                        methods: {
                            onClick() {
                                this.parent.onClickBtnChiTietMdahDoanhThu(this.data.RSC_ID)
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateChiTietAhThueBao(parent) {
            return function() {
                return { 
                    template: {
                        template: `<button class="btn btn-sm btn-second" @click="onClick">...</button>`,
                        data() {
                            return {
                                parent: parent,
                                data: { }
                            }
                        },
                        methods: {
                            onClick() {
                                this.parent.onClickBtnChiTietMdahThueBao(this.data.LOAITB_ID)
                            }
                        }
                    }
                }
            }
        },
        getTemplateGroupChiTietMdahThueBao(parent) {
            return function() {
                return { 
                    template: {
                        template: `<button class="btn btn-sm btn-second" @click="onClick">...</button>`,
                        data () {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        methods: {
                            onClick() {
                                let grid = this.$parent
                                let currentViewRecords = grid.getCurrentViewRecords()
                                let tenDichVu = this.data.key
                                let rowData = currentViewRecords.find(item => item.DICHVU_VT == tenDichVu)
                                this.parent.onClickBtnGroupMdahThueBao(rowData.DVVT_ID)
                            }
                        }
                    }
                }
            }
        },
        getTemplateGroupChiTietMdahDoanhThu(parent) {
            return function() {
                return { 
                    template: {
                        template: `<button class="btn btn-sm btn-second" @click="onClick">...</button>`,
                        data () {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        methods: {
                            onClick() {
                                let grid = this.$parent
                                let currentViewRecords = grid.getCurrentViewRecords()
                                let tenThietBi = this.data.key
                                let rowData = currentViewRecords.find(item => item.TEN_TBI == tenThietBi)
                                this.parent.onClickBtnGroupMdahDoanhThu(rowData.LOAIPTM_ID, rowData.PTM_ID)
                            }
                        }
                    }
                }
            }
        },
        getTemplateBtnXemDsThueBao(parent) {
            return function() {
                return { 
                    template: {
                        template: `<button class="btn btn-sm btn-second" @click="onClick">DS Thuê bao</button>`,
                        data () {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        methods: {
                            onClick() {
                                this.parent.onClickBtnDsThueBao()
                            }
                        }
                    }
                }
            }
        },
        sumLabelTemplate() {
            return { 
                template: {
                    template: `<span>Tổng</span>`,
                    data () {return { data: {}}}
                }
            }
        },
        sumValueTemplate() {
            return { 
                template: {
                    template: `<span>{{data.Sum}}</span>`,
                    data () {return { data: {}}}
                }
            }
        },
        sumValueTemplateDoanhThu() {
            return { 
                template: {
                    template: `<span>{{data.Sum}} đ / giờ</span>`,
                    data () {return { data: {}}}
                }
            }
        },
        onPageChangedSuCoDpn(args) {
            let pager = this.treeGridSuCoDpn.getPager().ej2_instances[0]
            pager.setPageSize(args.pageSize)
            pager.goToPage(parseInt(args.pageIndex) + 1)
        },
        onDataBoundTreeGrid(ref) {
            return () => {

                let grid = this.$refs[ref]

                if (!!grid.ej2Instances.grid.focusModule) {
                    grid.ej2Instances.grid.focusModule.destroy()
                }

                if (grid.getDataRows().length > 0) {
                    grid.selectRow(0)
                } else if (grid.frozenColumns > 0) {
                    let visibleColumnCount = grid.getVisibleColumns().length
                    let emptyRows = grid.$el.querySelectorAll('.e-emptyrow')
                    let cell0 = emptyRows[0].cells[0]
                    let cell1 = emptyRows[1].cells[0]
                    cell0.style.visibility = 'hidden'
                    cell1.innerHTML = cell0.innerHTML
                    cell1.setAttribute('colspan', visibleColumnCount - grid.frozenColumns)
                    cell1.style.setProperty('text-align', 'center', 'important')
                }

                let pager = this.treeGridSuCoDpn.getPager().ej2_instances[0]
                this.totalRecordsCount = pager.totalRecordsCount
            }
        },
        onDataBound(ref) {
            return () => {

                let grid = this.$refs[ref]

                if (!!grid.ej2Instances.focusModule) {
                    grid.ej2Instances.focusModule.destroy()
                }

                if (grid.getDataRows().length > 0) {
                    grid.selectRow(0)
                } else if (grid.isFrozenGrid()) {
                    let visibleColumnCount = grid.getVisibleColumns().length
                    let emptyRows = grid.$el.querySelectorAll('.e-emptyrow')
                    let cell0 = emptyRows[0].cells[0]
                    let cell1 = emptyRows[1].cells[0]
                    cell1.innerHTML = cell0.innerHTML
                    cell1.setAttribute('colspan', visibleColumnCount - grid.getFrozenLeftCount())
                    cell1.style.setProperty('text-align', 'left', 'important')
                    cell0.innerHTML = 'x'
                    cell0.style.color = 'white'
                }

                this.$refs[ref].$el.querySelector('.e-gridcontent').querySelectorAll('.e-indentcell').forEach(item => {
                    
                    item.classList.add('tree-first', 'bg-white')

                    let div1 = document.createElement('div')
                    div1.classList.add('tree-line')

                    let div2 = document.createElement('div')
                    div2.classList.add('tree-indent')
                    
                    item.append(div1)
                    item.append(div2)
                })
            }
        },
        okXoaAnh() {
            this.images = this.images.filter(item => {
                return !this.deletedImages.includes(item.relativeUrl)
            })
            this.$refs['ref-xacnhan-xoa-anh'].hide()
        },
        deleteImages() {
            if (this.deletedImages.length == 0) {
                this.$root.toastError('Chưa chọn ảnh để xóa')
                return
            }
            this.$refs['ref-xacnhan-xoa-anh'].show()
        },
        onMousemoveImg(e) {
            this.$refs['ref-zoom-img'].style.left = `${e.clientX + 100}px`
        },
        onMouseOverImg(src) {
            this.zoomImg = src
        },
        onMouseLeaveImg() {
            this.zoomImg = null
        },
        async onChangeChoosedImg() {
            let imgChooser = this.$refs['ref-choose-img']
            let choosedImgs = imgChooser.$el.querySelector('input').files

            if (choosedImgs.length == 0) {
                imgChooser.reset()
                return
            }

            choosedImgs = [...choosedImgs]
            imgChooser.reset()

            let paths = []
            
            try {
                this.loading(true)
                for (let i = 0; i < choosedImgs.length; i++) {
                    let item = choosedImgs[i]
                    let formData = new FormData()
                    formData.append("file", item)
                    let path = await fileStorage.uploadFile(this, formData)
                    paths.push(path)
                }
            } catch(err) {
            } finally {
                this.loading(false)
            }

            if (paths.length == 0) {
                this.$root.toastError('Upload file thất bại')
                return
            }

            this.images = this.images.concat(paths)
            this.$root.toastSuccess(`Đã upload được ${paths.length} files`)
        },
        async onClickBtnTuDongChonCongSoi() {
            
            let dsComponentTabItem = this.componentTabTinhTrang.getDsComponentTabItem()

            if (dsComponentTabItem.length <= 0) {
                return
            }

            if (this.nhomSuCoId == 0) { // công nghệ thông tin
                return
            }

            if (this.nhomSuCoId == 1) { // mạng viễn thông
                try {
                    this.loading(true)

                    for (let i = 0; i < dsComponentTabItem.length; i++) {

                        let component = dsComponentTabItem[i]

                        try {
                            if (!component.data.loaiTbiId || !component.data.thietBiId) {
                                throw 'Hãy chọn thiết bị trước khi thực hiện thao tác này'
                            }

                            component.data.congSoi = await this.layDsCongSoi({
                                loaiThietBiID: component.data.loaiTbiId,
                                thietBiID: component.data.thietBiId
                            })
                        } catch(err) {
                            this.componentTabTinhTrang.setSelectedTabId(component.id)
                            this.$root.toastError(err)
                            break
                        }
                    }
                } finally {
                    this.loading(false)
                }
            }
        },
        async okXoaPhieuCpn() {
            this.$refs['ref-modal-xacnhan-xoa-phieu-cpn'].hide()
            try {
                this.loading(true)
                let data = {
                    traPhieu: 0,
                    suCoIDs: this.dsIdSuCoCpnDuocChon
                }
                await this.huyPhieu(data)
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Xóa phiếu thành công')
            await this.napDsSuCoCpn()
            await this.napDsTiepNhan()
        },
        onClickBtnXoaPhieuCpn() {
            if (this.dsIdSuCoCpnDuocChon.length == 0) {
                this.$root.toastError('Hãy chọn ít nhất một phiếu ở danh sách chưa phân nhóm để xóa phiếu')
                return
            }
            this.$refs['ref-modal-xacnhan-xoa-phieu-cpn'].show()
        },
        async okHuyPhieuCpn() {
            this.$refs['ref-modal-xacnhan-huy-phieu-cpn'].hide()
            try {
                this.loading(true)
                let data = {
                    traPhieu: 1,
                    suCoIDs: this.dsIdSuCoCpnDuocChon
                }
                await this.huyPhieu(data)
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Hủy phiếu thành công')
            await this.napDsSuCoCpn()
            await this.napDsTiepNhan()
        },
        onClickBtnHuyPhieuCpn() {
            if (this.dsIdSuCoCpnDuocChon.length == 0) {
                this.$root.toastError('Hãy chọn ít nhất một phiếu ở danh sách chưa phân nhóm để hủy phiếu')
                return
            }
            this.$refs['ref-modal-xacnhan-huy-phieu-cpn'].show()
        },
        onClickBtnDongBoTinhTrang() {

            let dsComponentTabItem = this.componentTabTinhTrang.getDsComponentTabItem()

            if (dsComponentTabItem.length <= 1) {
                this.$root.toastError('Không đủ thông tin tình trạng để đồng bộ')
                return
            }

            try {
                let selectedTabItemComponent = this.componentTabTinhTrang.getSelectedTabItemComponent()

                dsComponentTabItem.forEach(component => {

                    if (component.id == selectedTabItemComponent.id) {
                        return
                    }

                    component.data.dsTinhTrangId = selectedTabItemComponent.data.dsTinhTrangId
                    component.data.ghiChuTt = selectedTabItemComponent.data.ghiChuTt
                })

                this.$root.toastSuccess('Đồng bộ tình trạng thành công')
            } catch(err) {
                this.$root.toastError('Đồng bộ tình trạng thất bại')
            }
        },
        onClickBtnXemMucDoSuCo() {
            this.$refs['modal-mucdo-suco'].show()
        }, 
        onQueryCellInfoSuCoDpn(args) {
            if (!!args.column.field && args.column.field != 'SUCO_ID') {
                args.cell.style.setProperty('background-color', `#${args.data.MAUNEN}`, 'important')
                args.cell.style.color = `#${args.data.MAUCHU}`
            }
        },
        notOkDoiNhomSuCo() {
            this.nhomSuCoId = this.nhomSuCoIdOld
            this.$refs['ref-modal-xacnhan-doi-nhom-sc'].hide()
        },
        okDoiNhomSuCo() {
            if (this.nhomSuCoId == 0) {
                this.componentTabTinhTrang.setTabContentConstructor(TabContentConstructorIpcc)
            }
            
            if (this.nhomSuCoId == 1) {
                this.componentTabTinhTrang.setTabContentConstructor(TabContentConstructorTbi)
            }

            this.nhomSuCoIdOld = null
            this.componentTabTinhTrang.clear()
            this.$refs['ref-modal-xacnhan-doi-nhom-sc'].hide()
        },
        onClickBtnDsThueBao() {
            let modal = this.$refs['modal-danhsach-thuebao']
            modal.setKieu(0)
            modal.setSuCoId(this.idSuCoDpnDuocChon)
            modal.setThamSo(0)
            modal.show()
        },
        onClickBtnGroupMdahDoanhThu(loaiPtmId, ptmId) {
            let modal = this.$refs['modal-danhsach-thuebao']
            modal.setKieu(3)
            modal.setSuCoId(this.idSuCoDpnDuocChon)
            modal.setThamSo([loaiPtmId, ptmId])
            modal.show()
        },
        onClickBtnGroupMdahThueBao(dvvtId) {
            let modal = this.$refs['modal-danhsach-thuebao']
            modal.setKieu(2)
            modal.setSuCoId(this.idSuCoDpnDuocChon)
            modal.setThamSo(dvvtId)
            modal.show()
        },
        onClickBtnChiTietMdahDoanhThu(rscId) {
            let modal = this.$refs['modal-danhsach-thuebao']
            modal.setKieu(1)
            modal.setSuCoId(this.idSuCoDpnDuocChon)
            modal.setThamSo(rscId)
            modal.show()
        },
        onClickBtnChiTietMdahThueBao(loaiThueBaoId) {
            let modal = this.$refs['modal-danhsach-thuebao']
            modal.setKieu(0)
            modal.setSuCoId(this.idSuCoDpnDuocChon)
            modal.setThamSo(loaiThueBaoId)
            modal.show()
        },
        onCheckSuCoDpn(idSuCo) {
            this.keySuCoTmpDuocChon = null
            this.idSuCoDpnDuocChon = idSuCo
            this.napSuCo()
        },
        onCheckSuCoTmp(key) {
            if (!key) {
                this.xoaForm()
                this.btnCapNhat.visible = false
                this.btnNapLai.visible = false
                return
            }

            this.napSuCoTmp()
            this.btnNhanHong.enabled = true
            this.idSuCoDpnDuocChon = null
        },
        onClickBtnXemNoiDungSc(noiDung) {
            let modal = this.$refs['modal-xem-thongtin']
            modal.setTitle('Nội dung sự cố')
            modal.setContent(noiDung)
            modal.show()
        },
        async onClickBtnGoGan() {

            if (!this.idSuCoDpnDuocChon) {
                this.$root.toastError('Hãy chọn một phiếu ở danh sách đã phân nhóm để gỡ nhóm')
                return
            }

            try {
                this.loading(true)
                await this.goNhomSuCo(this.idSuCoDpnDuocChon)
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Gỡ nhóm sự cố thành công')
            this.idSuCoDpnDuocChon = null
            this.napSuCo()
            await this.napDsSuCoCpn()
            await this.napDsSuCoDpn()
        },
        async onClickBtnGan() {

            if (this.dsIdSuCoCpnDuocChon.length == 0) {
                this.$root.toastError('Hãy chọn ít nhất một phiếu ở danh sách chưa phân nhóm để gán nhóm')
                return
            }

            try {
                this.loading(true)
                let data = {
                    suCoChaID: this.idSuCoDpnDuocChon,
                    suCoIDs: this.dsIdSuCoCpnDuocChon
                }
                await this.ganDsSuCoChoSuCoCha(data)
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Gán sự cố thành công')
            this.dsIdSuCoCpnDuocChon = []
            await this.napDsSuCoCpn()
            await this.napDsSuCoDpn()
        },
        async okXoaSuCoTam() {
            this.$refs['ref-modal-xacnhan-xoa-suco-tmp'].hide()
            try {
                this.loading(true)
                await this.xoaSuCoTam(this.suCoTmpDuocXoa.MA_SC)
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Xóa phiếu thành công')
            let grid = this.$refs['grid-suco-tamthoi'].grid
            grid.batchUpdate({ deletedRecords: [this.suCoTmpDuocXoa]})
            grid.refresh()

            if (this.suCoTmpDuocXoa.KEY == this.keySuCoTmpDuocChon) {
                this.keySuCoTmpDuocChon = null
                this.onCheckSuCoTmp(this.keySuCoTmpDuocChon)
            }

            this.suCoTmpDuocXoa = null
        },
        async onClickXoaTiepNhan(suCo) {
            this.suCoTmpDuocXoa = suCo
            this.$refs['ref-modal-xacnhan-xoa-suco-tmp'].show()
        },
        async onSuccessKhoaPhieu() {
            await this.napDsSuCoDpn()
            this.xoaForm()
        },
        onClickBtnKhoaPhieu() {

            if (!this.btnKhoaPhieu.enabled) {
                return
            }

            let modal = this.$refs['ref-modal-khoaphieu']
            modal.setNhomScId(this.nhomSuCoId)
            modal.setNhomScText(this.dsNhomSuCo.find(item => item.NHOMSC_ID == this.nhomSuCoId).NHOM_SC)
            modal.setSuCoId(this.idSuCoDpnDuocChon)
            modal.setNguonScId(this.suCoDpnDuocChon.NGUONSC_ID)
            modal.setKhoaPhieu(true)
            this.$bvModal.show('modal-khoaphieu')
        },
        async okXoaPhieu() {
            this.$refs['ref-modal-xacnhan-xoa'].hide()
            try {
                this.loading(true)
                await this.xoaPhieu(this.idSuCoDpnDuocChon)
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Đã xoá phiếu thành công!')
            this.xoaForm()
            this.khoaMoForm(false)
            this.napDsSuCoDpn()
        },
        onClickBtnXoaPhieu() {
            if (this.btnXoaPhieu.enabled) {
                this.$refs['ref-modal-xacnhan-xoa'].show()
            } 
        },
        async onClickBtnGiaoPhieu() {
            if (this.btnGiaoPhieu.enabled) {
                if (this.suCoDpnDuocChon.NGUONSC_ID == 6) {
                    let frmGiaoPhieu = this.$refs['ref-modal-giao-phieu-tts']
                    frmGiaoPhieu.setMaSuCo(this.suCoDpnDuocChon.MA_SC)
                    frmGiaoPhieu.setCheckboxNgayGiao(false)
                    frmGiaoPhieu.show()
                }
                else {
                    let frmGiaoPhieu = this.$refs['ref-modal-giao-phieu']
                    frmGiaoPhieu.setMaSuCo(this.suCoDpnDuocChon.MA_SC)
                    frmGiaoPhieu.setCheckboxNgaySuCo(false)
                    frmGiaoPhieu.show()
                }
                await this.napDsSuCoDpn();
            }
        },
        async onClickBtnNhanHong() {

            if (!this.btnNhanHong.enabled) {
                return
            }

            try {
                await this.kiemTraTiepNhan(true)

                if (this.nhomSuCoId == 0) {
                    this.kiemTraDsTinhTrangCnnt()
                    let tinhTrang = this.layInputTinhTrangCntt()

                    if (!tinhTrang) {
                        throw 'Không thể nhận hỏng do chưa khai báo tình trạng hỏng'
                    }

                    if (this.tam) {
                        
                        if (!this.maSuCo) {
                            let data = {
                                anhChup: this.inputImageUrls,
                                dienThoaiLH: this.dienThoaiLh,
                                donViID: this.$root.token.getDonViID(),
                                donViYeuCauID: this.idDonViBh,
                                ghiChuHong: this.noiDungSc,
                                henSuaTu: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlTu),
                                henSuaDen: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlDen),
                                ngayBD: this.yyyymmddhhmmssminus(this.ngaySuCo),
                                ngayYeuCau: this.yyyymmddhhmmssminus(this.ngayBao),
                                nguonSuCoID: 1,
                                nhanVienID: this.$root.token.getNhanVienID(),
                                nhanVienYeuCauID: this.nhanVienBhId,
                                nhomSuCoID: this.nhomSuCoId,
                                tinhTrang: tinhTrang
                            }
                            this.loading(true)
                            await this.tiepNhanSuCo(data)
                        } else {
                            let data = {
                                maSuCo: this.maSuCo,
                                tinhTrang: tinhTrang,
                                anhChup: this.inputImageUrls
                            }
                            this.loading(true)
                            await this.tiepNhanSuCoTam(data)
                            await this.napDsTiepNhan()
                        }
                        this.$root.toastSuccess('Tiếp nhận sự cố thành công')
                        await this.napDsSuCoCpn()
                        this.xoaForm()
                        this.khoaMoForm(false)
                        this.btnTiepNhan.visible = false
                        this.btnHuyBo.visible = false
                        this.btnCapNhat.visible = false
                        this.btnNapLai.visible = false
                    }
                }

                if (this.nhomSuCoId == 1) { 
                    this.kiemTraDsTinhTrangTbi()
                    let tinhTrang = this.layInputTinhTrangTbi()

                    if (!tinhTrang) {
                        throw 'Không thể nhận hỏng do chưa khai báo tình trạng hỏng'
                    }

                    if (this.tam) {

                        if (!this.maSuCo) {
                            let data = {
                                anhChup: this.inputImageUrls,
                                dienThoaiLH: this.dienThoaiLh,
                                donViID: this.$root.token.getDonViID(),
                                donViYeuCauID: this.idDonViBh,
                                ghiChuHong: this.noiDungSc,
                                henSuaTu: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlTu),
                                henSuaDen: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlDen),
                                ngayBD: this.yyyymmddhhmmssminus(this.ngaySuCo),
                                ngayYeuCau: this.yyyymmddhhmmssminus(this.ngayBao),
                                nguonSuCoID: 1,
                                nhanVienID: this.$root.token.getNhanVienID(),
                                nhanVienYeuCauID: this.nhanVienBhId,
                                nhomSuCoID: this.nhomSuCoId,
                                tinhTrang: tinhTrang
                            }
                            this.loading(true)
                            await this.tiepNhanSuCo(data)
                        } else {
                            let data = {
                                maSuCo: this.maSuCo,
                                tinhTrang: tinhTrang,
                                anhChup: this.inputImageUrls
                            }
                            this.loading(true)
                            await this.tiepNhanSuCoTam(data)
                            await this.napDsTiepNhan()
                        }
                        this.$root.toastSuccess('Tiếp nhận sự cố thành công')
                        await this.napDsSuCoCpn()
                        this.xoaForm()
                        this.khoaMoForm(false)
                        this.btnTiepNhan.visible = false
                        this.btnHuyBo.visible = false
                        this.btnCapNhat.visible = false
                        this.btnNapLai.visible = false
                    }
                }
            } catch(err) {
                this.$root.toastError(err)
            } finally {
                this.loading(false)
            }
        },
        onClickBtnNapLai() {
            this.xoaForm()
            this.btnCapNhat.visible = false
            this.btnNapLai.visible = false

            if (this.tam) {
                this.napSuCoTmp()
            } else {
                this.napSuCo()
            }
        },
        layInputTinhTrangTbi() {
            let dsComponentTabItem = this.componentTabTinhTrang.getDsComponentTabItem()

            if (dsComponentTabItem.length == 0) {
                return null
            }

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")
            let dsSuCoPtmIdCapNhat = []

            dsComponentTabItem.forEach(item => {
                let data = item.getData()
                let node = doc.createElement("node")
                let suCoPtmId = !data.suCoPtmId ? -1 : data.suCoPtmId
                dsSuCoPtmIdCapNhat.push(suCoPtmId)
                node.setAttribute("suco_ptm_id", suCoPtmId)
                node.setAttribute("loaitbi_id", data.loaiTbiId)
                node.setAttribute("thietbi_id", data.thietBiId)
                node.setAttribute("congsoi", data.congSoi)
                node.setAttribute("ghichu_tt", !data.ghiChuTt ? '' : data.ghiChuTt.trim())
                node.setAttribute("ds_tinhtrang", data.dsTinhTrangId.join())
                node.setAttribute("xoa", 0)
                root.appendChild(node)
            })
            
            let dsTinhTrangXoa = this.dsTinhTrangMvt.filter(tinhTrang => {
                return dsSuCoPtmIdCapNhat.indexOf(tinhTrang.SUCO_PTM_ID) < 0
            })

            dsTinhTrangXoa.forEach(tinhTrang => {
                let node = doc.createElement("node")
                node.setAttribute("suco_ptm_id", tinhTrang.SUCO_PTM_ID)
                node.setAttribute("loaitbi_id", tinhTrang.LOAIPTM_ID)
                node.setAttribute("thietbi_id", tinhTrang.PTM_ID)
                node.setAttribute("congsoi", tinhTrang.CONGSOI)
                node.setAttribute("ghichu_tt", tinhTrang.GHICHU)
                node.setAttribute("ds_tinhtrang", tinhTrang.DS_TINHTRANG)
                node.setAttribute("xoa", 1)
                root.appendChild(node)
            })

            doc.appendChild(root)
            return (new XMLSerializer()).serializeToString(doc)
        },
        kiemTraDsTinhTrangTbi() {
            let dsComponentTabItem = this.componentTabTinhTrang.getDsComponentTabItem()
            let dsIdThietBi = []

            for (let i = 0; i < dsComponentTabItem.length; i++) {
                let component = dsComponentTabItem[i]
                try {
                    component.validateInput()
                    let data = component.getData()
                    let thietBiId = `${data.loaiTbiId}-${data.thietBiId}`

                    if (dsIdThietBi.indexOf(thietBiId) >= 0) {
                        throw 'Không thể cập nhật sự cố do có 2 tình trạng cùng thiết bị'
                    } else {
                        dsIdThietBi.push(thietBiId)
                    }
                } catch (error) {
                    this.componentTabTinhTrang.setSelectedTabId(component.id)
                    throw error
                }
            }
        },
        exportGroupCaption(args) {
            let captionText = !args.captionText ? '' : args.captionText.trim()
            if (!!captionText) { 
                // loại bỏ tên cột, dấu 2 chấm ngăn cách tên cột và giá trị và loại bỏ số lượng item
                args.captionText = captionText.substr(1, captionText.lastIndexOf('-') - 1)
            }
        },
        xuatExcelTinhTrangMdah() {
            try {
                if (this.loaiThietHai == 0) {
                    if (this.dsCongAnhHuong.length == 0) {
                        throw 'Không có dữ liệu để xuất'
                    }
                    this.$refs['grid-cong-anh-huong-excel'].excelExport()
                    return
                }
                
                if (this.dsTbaoAnhHuong.length == 0) {
                    throw 'Không có dữ liệu để xuất'
                }
                this.$refs['grid-tbao-anh-huong-excel'].excelExport()
            } catch(err) {
                this.$root.toastError('Không có dữ liệu để xuất')
            }
        },
        onClickBtnXuatExcelDsSuCoCpn() {

            if (this.dsSuCoCpn.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            this.dsSuCoDpnFlat.forEach(item => {
                exportData.push({
                    'Nhóm': item.NHOM_SC,
                    'Mã sự cố': item.MA_SC,
                    'Nội dung sự cố': item.NOIDUNG_SC,
                    'Mức độ': item.MUCDO_SC,
                    'Ngày sự cố': item.NGAY_SC,
                    'Kéo dài': item.THOIGIAN,
                    'Ngày báo': item.NGAY_TB,
                    'Người báo': item.NHANVIEN_TB,
                    'Ngày TN': item.NGAY_TN,
                    'Người TN': item.NHANVIEN_TN,
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        onClickBtnXuatExcelDsSuCoDpn() {

            if (this.dsSuCoDpnFlat.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            this.dsSuCoDpnFlat.forEach(item => {
                exportData.push({
                    'Mã sự cố': item.MA_SC,
                    'Nhóm': item.NHOM_SC,
                    'Nội dung sự cố': item.NOIDUNG_SC,
                    'Mức độ': item.MUCDO_SC,
                    'Ngày sự cố': item.NGAY_SC_HT,
                    'Kéo dài': item.THOIGIAN,
                    'Ngày báo': item.NGAY_TB_HT,
                    'Người báo': item.NHANVIEN_TB,
                    'Điện thoại liên hệ': item.DIENTHOAI_LH,
                    'Ngày TN': item.NGAY_TN_HT,
                    'Người TN': item.NHANVIEN_TN,
                    'Hẹn sửa từ': item.HENSUATU_HT,
                    'Hẹn sửa đến': item.HENSUADEN_HT,
                    'Khóa phiếu': item.KHOAPHIEU
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        onClickBtnXuatExcelDsSuCoTmp() {

            if (this.dsSuCoTam.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            this.dsSuCoTam.forEach(item => {
                exportData.push({
                    'Mã sự cố': item.MA_SC,
                    'Nhóm': item.NHOM_SC,
                    'Nội dung sự cố': item.NOIDUNG_SC,
                    'Kéo dài': item.THOIGIAN,
                    'Ngày báo': item.NGAY_BH_HT,
                    'Người báo': item.NGUOI_BH,
                    'Ngày tiếp nhận': item.NGAY_TN_HT,
                    'Người tiếp nhận': item.NGUOI_TN,
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        onClickBtnXuatExcelTinhTrang() {

            if (this.nhomSuCoId == 0) {
                this.xuatExcelTinhTrangCntt()
                return
            }

            if (this.nhomSuCoId == 1) {
                this.xuatExcelTinhTrangTbi()
                return
            }
        },
        xuatExcelTinhTrangCntt() {
            let dsComponentTabItem = this.componentTabTinhTrang.getDsComponentTabItem()

            if (dsComponentTabItem.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            dsComponentTabItem.forEach(item => {
                let data = item.getData()
                let dsTinhTrang = null
                let tenHeThong = null

                if (!!data.dsTinhTrangId && data.dsTinhTrangId.length > 0) {
                    dsTinhTrang = this.dsTinhTrangCntt.filter(item => {
                        return data.dsTinhTrangId.indexOf(item.TINHTRANG_ID) >= 0
                    }).map(item => item.TINHTRANG_SC).join(', ')
                }

                if (!!data.heThongItId) {
                    let heThong = this.dsHeThongCntt.find(item => item.HETHONGIT_ID == data.heThongItId)
                    tenHeThong = heThong.TEN_HT
                }

                exportData.push({
                    hethong_id: data.heThongItId,
                    ten_ht: tenHeThong,
                    tinhtrang: dsTinhTrang,
                    ghichu: data.ghiChuTt
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        xuatExcelTinhTrangTbi() {
            let dsComponentTabItem = this.componentTabTinhTrang.getDsComponentTabItem()

            if (dsComponentTabItem.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            dsComponentTabItem.forEach(item => {
                let data = item.getData()
                let dsTinhTrang = null

                if (!!data.dsTinhTrangId && data.dsTinhTrangId.length > 0) {
                    dsTinhTrang = this.dsTinhTrangMvt.filter(item => {
                        return data.dsTinhTrangId.indexOf(item.TINHTRANG_ID) >= 0
                    }).map(item => item.TINHTRANG_SC).join(', ')
                }

                exportData.push({
                    loaitbi_id: data.loaiTbiId,
                    thietbi_id: data.thietBiId,
                    ten_tbi: data.tenTbi,
                    congsoi: data.congSoi,
                    tinhtrang: dsTinhTrang,
                    ghichu: data.ghiChuTt
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        layInputTinhTrangCntt() {
            let dsComponentTabItem = this.componentTabTinhTrang.getDsComponentTabItem()

            if (dsComponentTabItem.length == 0) {
                return null
            }

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")
            let dsSuCoPtmIdCapNhat = []

            dsComponentTabItem.forEach(item => {
                let data = item.getData()
                let node = doc.createElement("node")
                let suCoPtmId = !data.suCoPtmId ? -1 : data.suCoPtmId
                dsSuCoPtmIdCapNhat.push(suCoPtmId)
                node.setAttribute("suco_ptm_id", suCoPtmId)
                node.setAttribute("hethongit_id", data.heThongItId)
                node.setAttribute("ghichu_tt", !data.ghiChuTt ? '' : data.ghiChuTt.trim())
                node.setAttribute("ds_tinhtrang", data.dsTinhTrangId.join())
                node.setAttribute("xoa", 0)
                root.appendChild(node)
            })
            
            let dsTinhTrangXoa = this.dsTinhTrangCntt.filter(tinhTrang => {
                return dsSuCoPtmIdCapNhat.indexOf(tinhTrang.SUCO_PTM_ID) < 0
            })

            dsTinhTrangXoa.forEach(tinhTrang => {
                let node = doc.createElement("node")
                node.setAttribute("suco_ptm_id", tinhTrang.SUCO_PTM_ID)
                node.setAttribute("hethongit_id", tinhTrang.HETHONGIT_ID)
                node.setAttribute("ghichu_tt", tinhTrang.GHICHU)
                node.setAttribute("ds_tinhtrang", tinhTrang.DS_TINHTRANG)
                node.setAttribute("xoa", 1)
                root.appendChild(node)
            })

            doc.appendChild(root)
            return (new XMLSerializer()).serializeToString(doc)
        },
        kiemTraDsTinhTrangCnnt() {
            let dsComponentTabItem = this.componentTabTinhTrang.getDsComponentTabItem()
            let dsIdHeThong = []

            for (let i = 0; i < dsComponentTabItem.length; i++) {
                let component = dsComponentTabItem[i]
                try {
                    component.validateInput()
                    let data = component.getData()

                    if (dsIdHeThong.indexOf(data.heThongItId) >= 0) {
                        throw 'Không thể cập nhật sự cố do có 2 tình trạng cùng hệ thống'
                    } else {
                        dsIdHeThong.push(data.heThongItId)
                    }
                } catch (error) {
                    this.componentTabTinhTrang.setSelectedTabId(component.id)
                    throw error
                }
            }
        },
        async onClickBtnCapNhat() {
            try {
                await this.kiemTraTiepNhan(false)

                if (this.tam) { // cập nhật phiếu tạm
                    this.loading(true)
                    let data = {
                        dienThoaiLH: this.dienThoaiLh,
                        donViBH: this.idDonViBh,
                        donViTN: this.$root.token.getDonViID(),
                        henSuaTu: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlTu),
                        henSuaDen: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlDen),
                        maSuCo: this.maSuCo,
                        ngayBH: this.yyyymmddhhmmssminus(this.ngayBao),
                        ngaySuCo: this.yyyymmddhhmmssminus(this.ngaySuCo),
                        nhanVienBH: this.nhanVienBhId,
                        nhanVienTN: this.$root.token.getNhanVienID(),
                        noiDungSuCo: this.isSuCoTts ? 'x' : this.noiDungSc,
                        nhomSuCoID: this.nhomSuCoId,
                        anhChup: this.isSuCoTts ? 'x' : this.inputImageUrls
                    }
                    await this.capNhatSuCoTam(data)
                    this.$root.toastSuccess('Cập nhật sự cố thành công')
                    await this.napDsTiepNhan()
                } else { // cập nhật thông tin sự cố
                    if (this.nhomSuCoId == 0) {
                        this.kiemTraDsTinhTrangCnnt()
                        let tinhTrangCntt = this.layInputTinhTrangCntt()
                        let data = {
                            dienThoaiLH: this.dienThoaiLh,
                            donViBH: this.idDonViBh,
                            donViTN: this.$root.token.getDonViID(),
                            henSuaTu: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlTu),
                            henSuaDen: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlDen),
                            maSuCo: this.maSuCo,
                            ngayBH: this.yyyymmddhhmmssminus(this.ngayBao),
                            ngaySuCo: this.yyyymmddhhmmssminus(this.ngaySuCo),
                            nhanVienBH: this.nhanVienBhId,
                            nhanVienTN: this.$root.token.getNhanVienID(),
                            noiDungSuCo: this.isSuCoTts ? 'x' : this.noiDungSc,
                            tinhTrang: tinhTrangCntt,
                            phieuId: this.suCoDpnDuocChon.PHIEU_ID,
                            anhChup: this.isSuCoTts ? 'x' : this.inputImageUrls
                        }
                        this.loading(true)
                        await this.capNhatSuCo(data)
                        this.$root.toastSuccess('Cập nhật sự cố thành công')
                        await this.napDsSuCoDpn()

                    } else if (this.nhomSuCoId == 1) {
                        let tinhTrangTbi = ''

                        if (this.nguonSuCoId == 1) {
                            this.kiemTraDsTinhTrangTbi()
                            tinhTrangTbi = this.layInputTinhTrangTbi()
                        }
                        
                        let data = {
                            dienThoaiLH: this.dienThoaiLh,
                            donViBH: this.idDonViBh,
                            donViTN: this.$root.token.getDonViID(),
                            henSuaTu: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlTu),
                            henSuaDen: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlDen),
                            maSuCo: this.maSuCo,
                            ngayBH: this.yyyymmddhhmmssminus(this.ngayBao),
                            ngaySuCo: this.yyyymmddhhmmssminus(this.ngaySuCo),
                            nhanVienBH: this.nhanVienBhId,
                            nhanVienTN: this.$root.token.getNhanVienID(),
                            noiDungSuCo: this.isSuCoTts ? 'x' : this.noiDungSc,
                            tinhTrang: tinhTrangTbi,
                            phieuId: this.suCoDpnDuocChon.PHIEU_ID,
                            anhChup: this.isSuCoTts ? 'x' : this.inputImageUrls
                        }
                        this.loading(true)
                        await this.capNhatSuCo(data)
                        this.$root.toastSuccess('Cập nhật sự cố thành công')
                        await this.napDsSuCoDpn()
                    }
                }
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }
        },
        onClickBtnHuyBo() {
            this.xoaForm()
            this.btnTiepNhan.visible = false
            this.btnHuyBo.visible = false
        },
        async onClickBtnTiepNhan() {
            try {
                await this.kiemTraTiepNhan(true)
                this.loading(true)
                let maSuCo = await this.layMaSuCo({
                    ngay: this.yyyymmddminus(this.ngayBao),
                    nguonSuCoID: this.nguonSuCoId
                })
                let data = {
                    dienThoaiLH: this.dienThoaiLh,
                    donViBH: this.idDonViBh,
                    donViTN: this.$root.token.getDonViID(),
                    henSuaTu: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlTu),
                    henSuaDen: !this.chkHenTraLoi ? null : this.yyyymmddhhmmssminus(this.henTlDen),
                    maSuCo: maSuCo,
                    ngayBH: this.yyyymmddhhmmssminus(this.ngayBao),
                    ngaySuCo: this.yyyymmddhhmmssminus(this.ngaySuCo),
                    ngayTN: this.yyyymmddhhmmssminus(new Date()),
                    nhanVienBH: this.nhanVienBhId,
                    nhanVienTN: this.$root.token.getNhanVienID(),
                    noiDungSuCo: this.noiDungSc,
                    nguonSuCoID: this.nguonSuCoId,
                    nhomSuCoID: 1,
                    anhChup: this.inputImageUrls,
                    phanLoaiSuCoId: this.phanLoaiSuCoId
                }
                await this.taoSuCoTam(data)
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Tiếp nhận phiếu thành công')
            this.napDsTiepNhan()
            this.btnTiepNhan.visible = false
            this.btnHuyBo.visible = false
            this.btnCapNhat.visible = true
            this.btnNapLai.visible = true
        },
        onClickBtnNhapMoi() {

            if (!this.btnNhapMoi.enabled) {
                return
            }

            document.querySelector('.page-content').scrollTop = 0
            this.tam = true
            this.dsTinhTrangMvt = []
            this.xoaForm()
            this.khoaMoForm(true)
            this.btnTiepNhan.visible = true
            this.btnHuyBo.visible = true
            this.btnCapNhat.visible = false
            this.btnNapLai.visible = false
            this.btnNhanHong.enabled = true
            this.cboNhomSC.enabled = true
            this.keySuCoTmpDuocChon = null
            this.idSuCoDpnDuocChon = null
            setTimeout(() => {
                this.componentComboNhomSc.$children[0].select2.select2('open')
            }, 120)
        },
        async napSuCo() {

            this.xoaForm()

            if (!this.suCoDpnDuocChon) {
                this.btnTiepNhan.visible = false
                this.btnHuyBo.visible = false
                this.btnCapNhat.visible = false
                this.btnNapLai.visible = false
                this.btnNhanHong.enabled = false
                this.btnXoaPhieu.enabled = false
                this.btnGiaoPhieu.enabled = false
                this.btnKhoaPhieu.enabled = false
                return
            }

            this.tam = false
            this.khoaMoForm(true)
            this.cboNhomSC.enabled = false
            this.maSuCo = this.suCoDpnDuocChon.MA_SC
            this.nhomSuCoId = this.suCoDpnDuocChon.NHOMSC_ID
            this.ngaySuCo = new Date(this.suCoDpnDuocChon.NGAY_SC)
            this.nhanVienBhId = this.suCoDpnDuocChon.NHANVIEN_TB_ID
            this.ngayBao = new Date(this.suCoDpnDuocChon.NGAY_TB)
            this.dienThoaiLh = this.suCoDpnDuocChon.DIENTHOAI_LH
            this.noiDungSc = this.suCoDpnDuocChon.NOIDUNG_SC

            if (!!this.suCoDpnDuocChon.HENSUATU) {
                this.chkHenTraLoi = true
                this.chkHenTraLoiEnabled = true
                this.henTlTu = new Date(this.suCoDpnDuocChon.HENSUATU)
                this.henTlDen = new Date(this.suCoDpnDuocChon.HENSUADEN)
            }

            this.btnTiepNhan.visible = false
            this.btnHuyBo.visible = false
            this.btnCapNhat.visible = true
            this.btnNapLai.visible = true
            this.btnNhanHong.enabled = false
            this.btnXoaPhieu.enabled = true
            this.btnGiaoPhieu.enabled = true
            this.btnKhoaPhieu.enabled = true

            await this.napDsTinhTrang(this.idSuCoDpnDuocChon)
            await this.napDsAnhHuong(this.idSuCoDpnDuocChon)

            if (!!this.suCoDpnDuocChon.MOTA) {
                this.loadImages(this.suCoDpnDuocChon.MOTA.split('|'))
            }
        },
        onClickBtnNapLaiSuCoCpn() {
            this.napDsSuCoCpn()
        },
        onClickBtnNapLaiSuCoDpn() {
            this.napDsSuCoDpn()
        },
        onClickBtnNapLaiMdah() {
            
            if (!this.idSuCoDpnDuocChon) {
                return
            }

            this.napDsAnhHuong(this.idSuCoDpnDuocChon)
        },
        async napDsAnhHuong(suCoId) {

            if (this.tam) {
                this.dsCongAnhHuong = []
                this.dsTbaoAnhHuong = []
                return
            }

            try {
                this.loading(true)
                let loading1 = this.layDsThueBaoBiAnhHuong(suCoId)
                let loading2 = this.layDsCongBiAnhHuong(suCoId)
                await loading1
                await loading2
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async napSuCoTmp() {
            
            this.xoaForm()

            if (!this.suCoTmpDuocChon) {
                this.btnTiepNhan.visible = false
                this.btnHuyBo.visible = false
                this.btnCapNhat.visible = false
                this.btnNapLai.visible = false
                this.btnNhanHong.enabled = false
                this.btnXoaPhieu.enabled = false
                this.btnGiaoPhieu.enabled = false
                this.btnKhoaPhieu.enabled = false
                return
            }

            this.tam = true
            this.khoaMoForm(true)
            this.cboNhomSC.enabled = false
            this.nhomSuCoId = this.suCoTmpDuocChon.NHOMSC_ID
            this.ngaySuCo = new Date(this.suCoTmpDuocChon.NGAY_SC)
            this.dienThoaiLh = this.suCoTmpDuocChon.DIENTHOAI_LH
            this.nhanVienBhId = this.suCoTmpDuocChon.NHANVIEN_BH_ID
            this.ngayBao = new Date(this.suCoTmpDuocChon.NGAY_BH)
            this.maSuCo = this.suCoTmpDuocChon.MA_SC
            this.noiDungSc = this.suCoTmpDuocChon.NOIDUNG_SC

            if (!!this.suCoTmpDuocChon.HENSUATU) {
                this.chkHenTraLoiEnabled = true
                this.chkHenTraLoi = true
                this.henTlTu = new Date(this.suCoTmpDuocChon.HENSUATU)
                this.henTlDen = new Date(this.suCoTmpDuocChon.HENSUADEN)
            }
            
            this.btnTiepNhan.visible = false
            this.btnHuyBo.visible = false
            this.btnCapNhat.visible = true
            this.btnNapLai.visible = true
            this.btnNhanHong.enabled = true
            this.btnXoaPhieu.enabled = false
            this.btnGiaoPhieu.enabled = false
            this.btnKhoaPhieu.enabled = false

            if (!!this.suCoTmpDuocChon.MOTA) {
                this.loadImages(this.suCoTmpDuocChon.MOTA.split('|'))
            }
        },
        async loadImages(relativeUrls) {
            let paths = []
            try {
                this.loading(true)
                for (let i = 0; i < relativeUrls.length; i++) {
                    let relativeUrl = relativeUrls[i]
                    let presignedUrl = await fileStorage.getFileUrl(this, relativeUrl)
                    paths.push({relativeUrl, presignedUrl})
                }
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.images = paths
        },
        async kiemTraTiepNhan(isTiepNhan) {
            let errorMessage = []

            this.$v.$touch()

            if (this.$v.$invalid) {

                if (!this.$v.nhomSuCoId.required) {
                    errorMessage.push("Vui lòng chọn Nhóm SC")
                }

                if (!this.$v.nhanVienBhId.required) {
                    errorMessage.push("Vui lòng chọn Người BH")
                }

                if (!this.$v.ngayBao.required) {
                    errorMessage.push("Vui lòng chọn Ngày BH")
                }

                if (!this.$v.ngaySuCo.required) {
                    errorMessage.push("Vui lòng chọn Ngày SC")
                }

                if (!this.$v.ngaySuCo.koLonHonNgayBh) {
                    errorMessage.push("Ngày SC phải nhỏ hơn Ngày TB")
                }

                if (!this.$v.dienThoaiLh.required) {
                    errorMessage.push("Vui lòng nhập Điện thoại LH")
                }

                if (!this.$v.noiDungSc.required) {
                    errorMessage.push("Vui lòng nhập Nội dung SC")
                }

                if (this.QLSC_BATBUOC_NHAP_NGAYDEN == 1 && !this.chkHenTraLoi)
                {
                    errorMessage.push("Hãy nhập ngày Hẹn TL");
                }

                if (!this.$v.henTlTu.required) {
                    errorMessage.push("Vui lòng nhập Hẹn TL từ")
                }

                if (!this.$v.henTlDen.required) {
                    errorMessage.push("Vui lòng nhập Hẹn TL đến")
                }

                if (!this.$v.henTlTu.koNhoThuaNgaySc) {
                    errorMessage.push("Ngày hẹn sửa từ phải không được nhỏ hơn Ngày SC")
                }

                if (!this.$v.henTlDen.koNhoThuaNgayTlTu) {
                    errorMessage.push("Ngày hẹn sửa đến phải không được nhỏ hơn Ngày hẹn sửa từ")
                }

                if (!this.$v.phanLoaiSuCoId.required) {
                    errorMessage.push("Vui lòng chọn Phân loại SC")
                }
            }

            if (!isTiepNhan && this.tam) {
                try {
                    this.loading(true)
                    
                    let result = await this.kiemTraDieuKienTiepNhan({
                        kieu: 1,
                        ma_sc: this.maSuCo
                    })

                    if (result.length > 0) {

                        errorMessage.push(`Sự cố với mã ${this.maSuCo} đã được khai báo bởi nhân viên ${result[0].TEN_NV} \nĐơn vị: ${result[0].TEN_DV} \nĐiện thoại liên hệ: ${result[0].DIENTHOAI_LH} \nVào lúc: ${this.ddmmyyyyhhmm(result[0].NGAY_INS)}`) 
                    }

                    result = await this.kiemTraDieuKienTiepNhan({
                        kieu: 2,
                        ma_sc: this.maSuCo
                    })

                    if (result.length > 0 && result[0].RESULT == 0) {
                        errorMessage.push(`Sự cố tạm với mã ${this.maSuCo} đã bị xóa`) 
                    }
                } catch(err) {
                    throw err
                } finally {
                    this.loading(false)
                }
            }

            if (errorMessage.length > 0) {
                throw errorMessage.join('\n')
            }
        },
        async onClickBtnLayThongTin() {
            await this.napDsTiepNhan()
            await this.napDsSuCoCpn()
            await this.napDsSuCoDpn()
            this.xoaForm()
            this.btnTiepNhan.visible = false
            this.btnHuyBo.visible = false
            this.btnCapNhat.visible = false
            this.btnNapLai.visible = false
            this.btnNhanHong.enabled = false
            this.btnXoaPhieu.enabled = false
            this.btnGiaoPhieu.enabled = false
            this.btnKhoaPhieu.enabled = false
        },
        onClickBtnNapLaiTinhTrang() {

            if (!this.idSuCoDpnDuocChon) {
                return
            }

            this.napDsTinhTrang(this.idSuCoDpnDuocChon)
        },
        async napDsTinhTrang(suCoId) {

            this.componentTabTinhTrang.clear()

            if (!suCoId) {
                return
            }

            if (this.nhomSuCoId == 0) {
                await this.napDsTinhTrangCntt(suCoId)
                
                for (let i = 0; i < this.dsTinhTrangCntt.length; i++) {
                    let item = this.dsTinhTrangCntt[i]
                    this.componentTabTinhTrang.setTabContentConstructor(TabContentConstructorIpcc)
                    this.componentTabTinhTrang.addTab({...item}, item.TEN_HT)
                }

                return
            }
            
            await this.napDsTinhTrangMvt(suCoId)
                
            for (let i = 0; i < this.dsTinhTrangMvt.length; i++) {
                let item = this.dsTinhTrangMvt[i]
                this.componentTabTinhTrang.setTabContentConstructor(TabContentConstructorTbi)
                this.componentTabTinhTrang.addTab({...item, NGUONSC_ID: this.nguonSuCoId}, `${item.LOAI_PTM} ${item.TEN_PTM}`)
            }
        },
        async napDsTinhTrangMvt(suCoId) {
            try {
                this.loading(true)
                await this.layDsTinhTrangMvt(suCoId)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async napDsTinhTrangCntt(suCoId) {
            try {
                this.loading(true)
                await this.layDsTinhTrangCntt(suCoId)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async napDsSuCoDpn() {
            this.onCheckSuCoDpn(null)
            try {
                this.loading(true)
                await this.layDsSuCoDpn({ nguonSuCoID: this.nguonSuCoId })
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async napDsSuCoCpn() {
            try {
                this.loading(true)
                await this.layDsSuCoCpn({ nguonSuCoID: this.nguonSuCoId })
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async napDsTiepNhan() {

            this.keySuCoTmpDuocChon = null
            this.onCheckSuCoTmp(this.keySuCoTmpDuocChon)

            if (this.nguonSuCoId == 4) {
                this.dsSuCoTam = []
                return
            }

            try {
                this.loading(true)
                await this.layDsSuCoTam({
                    maSuCo: null,
                    nguonSuCoID: this.nguonSuCoId 
                })
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        xoaForm() {
            this.maSuCo = null
            this.nhomSuCoId = this.dsNhomSuCo.length == 0 ? null : this.dsNhomSuCo[0].NHOMSC_ID
            this.ngaySuCo = new Date()
            this.ngayBao = new Date()
            this.henTlTu = new Date()
            this.henTlDen = new Date()
            this.nhanVienBhId = null
            this.dienThoaiLh = null
            this.noiDungSc = null
            this.chkHenTraLoi = false
            this.images = []
            this.deletedImages = []
            this.showImagePanel = false
            this.componentTabTinhTrang.clear()
            this.dsCongAnhHuong = []
            this.dsTbaoAnhHuong = []
            this.khoaMoForm(false)
            this.btnCapNhat.visible = false
            this.btnNapLai.visible = false
            this.btnNhanHong.enabled = false
            this.btnXoaPhieu.enabled = false
            this.btnGiaoPhieu.enabled = false
            this.btnKhoaPhieu.enabled = false
            this.$v.$reset()
        },
        khoaMoForm(mo) {
            this.cboNhomSC.enabled = mo
            this.dtpNgaySC.enabled = mo
            this.dtpNgayBao.enabled = mo
            this.txtNhanVienBao.enabled = mo
            this.txtDienThoaiLH.enabled = mo
            this.txtNoiDungSC.enabled = mo
            this.chkHenTraLoiEnabled = mo
            this.cboAnhChup.enabled = mo
            this.cboLoaiSuCo.enabled = mo
        },
        getDateElements(date) {

            date = new Date(date)

            let day = date.getDate()
            day = day > 9 ? day : '0' + day

            let month = date.getMonth() + 1
            month = month > 9 ? month : '0' + month

            let hour = date.getHours()
            hour = hour > 9 ? hour : '0' + hour

            let min = date.getMinutes()
            min = min > 9 ? min : '0' + min

            let second = date.getSeconds()
            second = second > 9 ? second : '0' + second

            return {
                y: date.getFullYear(),
                M: month,
                d: day,
                h: hour,
                m: min,
                s: second
            }
        },
        yyyymmddminus(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.y}-${date.M}-${date.d}`
        },
        yyyymmddhhmmssminus(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.y}-${date.M}-${date.d} ${date.h}:${date.m}:${date.s}`
        },
        ddmmyyyyhhmm(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}`
        },
        async layDsThamSoMd() {
            try {
                let response = await this.$root.context.get('/web-suco/quanlysuco/layDsThamSoMd')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsThamSoMd = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsThamSoMd = []
            }
        },
        async layDmTinhTrangScMvt() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/ds-tinhtrang-sc-mvt')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTinhTrangScMvt = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsTinhTrangScMvt = []
            }
        },
        async layDmTinhTrangScCntt() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/ds-tinhtrang-sc-cntt')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTinhTrangScCntt = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsTinhTrangScCntt = []
            }
        },
        async layDmHeThongCntt() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/ds-hethong-cntt')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsHeThongCntt = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsHeThongCntt = []
            }
        },
        async layDmNhomSuCo() {
            try {
                let response = await this.$root.context.post('/web-suco/quanlysuco/getDsNhomSuCoTheoNhanVien')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhomSuCo = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsNhomSuCo = []
            }
        },
        async layDmNguonSuCo() {
            try {
                let response = await this.$root.context.post('/web-suco/quanlysuco/getDsNguonSuCo')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNguonSuCo = response.data.filter(item => item.NGUONSC_ID != 0)
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsNguonSuCo = []
            }
        },
        async layDsTinhTrangMvt(suCoId) {
            try {
                let response = await this.$root.context.post(`web-suco/quanlysuco/layDsTinhTrangMVT`, {id: suCoId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTinhTrangMvt = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsTinhTrangMvt = []
            }
        },
        async layDsTinhTrangCntt(suCoId) {
            try {
                let response = await this.$root.context.post(`web-suco/quanlysuco/layDsTinhTrangCNTT`, {id: suCoId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTinhTrangCntt = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsTinhTrangCntt = []
            }
        },
        async layDsCongBiAnhHuong(suCoId) {
            try {
                let response = await this.$root.context.get(`web-suco/tracuu/suco/ds-cong-bi-anhhuong?suco_id=${suCoId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsCongAnhHuong = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsCongAnhHuong = []
            }
        },
        async layDsThueBaoBiAnhHuong(suCoId) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/layDsThueBaoBiAnhHuong`, {id: suCoId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTbaoAnhHuong = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsTbaoAnhHuong = []
            }
        },
        async layDsSuCoTam(data) {
            try {
                let response = await this.$root.context.post('/web-suco/quanlysuco/layDsSuCoTam', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => {
                        item.KEY = index + 1
                        item.NGAY_SC_HT = this.ddmmyyyyhhmm(item.NGAY_SC)
                        item.NGAY_BH_HT = this.ddmmyyyyhhmm(item.NGAY_BH)
                        item.NGAY_TN_HT = this.ddmmyyyyhhmm(item.NGAY_TN)
                        item.SECOND = convertHmsToSecond(item.THOIGIAN)
                    })
                    this.dsSuCoTam = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsSuCoTam = []
            }
        },
        async layDsSuCoCpn(data) {
            try {
                let response = await this.$root.context.post('/web-suco/quanlysuco/layDsSuCoCPN', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsSuCoCpn = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsSuCoCpn = []
            }
        },
        async layMaSuCo(data) {
            try {
                let response = await this.$root.context.post('/web-suco/quanlysuco/getMaSuCo', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.result
                }
                throw {response: { data: response}}
            } catch (err) {
                throw 'Có lỗi xảy ra khi sinh mã sự cố'
            }
        },
        async layDsCongSoi(data) {
            try {
                let response = await this.$root.context.post('/web-suco/quanlysuco/layDsCongSoi', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.result
                }
                throw {response: { data: response}}
            } catch (err) {
                throw 'Có lỗi xảy ra khi lấy danh sách cổng sợi'
            }
        },
        async layDsSuCoDpn(data) {
            try {
                let response = await this.$root.context.post('/web-suco/quanlysuco/layDsSuCoDPN', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    response.data.forEach((item, index) => {
                        item.KEY = index + 1
                        item.SECOND = convertHmsToSecond(item.THOIGIAN)

                        if (!!item.DS_THIETBI) {
                            item.NOIDUNG_SC = `${item.NOIDUNG_SC } (${item.DS_THIETBI})`.trim()
                        }
                    })

                    let root = []

                    for (let i = 0; i < response.data.length; i++) {
                        
                        let parent = response.data[i]

                        if (!response.data.some(item => item.SUCO_ID == parent.SUCO_CHA_ID)) {
                            parent.SUCO_CHA_ID = null
                        }

                        if (!parent.SUCO_CHA_ID) {
                            root.push(parent)
                        }

                        parent.childrent = []

                        for (let j = 0; j < response.data.length; j++) {

                            let child = response.data[j]

                            if (child.SUCO_CHA_ID == parent.SUCO_ID) {
                                parent.childrent.push(child)
                            }
                        }
                    }
                    
                    this.dsSuCoDpnTree = root
                    this.dsSuCoDpnFlat = response.data
                    return
                }
                throw {response: { data: response}}
            } catch (err) {
                this.dsSuCoDpnTree = []
                this.dsSuCoDpnFlat = []
            }
        },
        async kiemTraDieuKienTiepNhan(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/danhmuc/kiemtra-dieukien-tiepnhan`, data)
                
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }

                throw {response: { data: response}}
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n') 
            }
        },
        async taoSuCoTam(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/taoSuCoTam_v2`, data)
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async capNhatSuCoTam(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/capNhatSuCoTam`, data)
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async capNhatSuCo(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/capNhatSuCo`, data)
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async tiepNhanSuCo(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/tiepNhanSuCo`, data)
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async tiepNhanSuCoTam(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/tiepNhanSuCoTam_v2`, data)
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async xoaPhieu(idSuCo) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/xoaPhieu`, {id: idSuCo})
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async xoaSuCoTam(maSuCo) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/xoaSuCoTam`, {maSuCo: maSuCo})
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async ganDsSuCoChoSuCoCha(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/ganDsSuCoChoSuCoCha`, data)
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async goNhomSuCo(suCoId) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/goNhomSuCo`, {id: suCoId})
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async huyPhieu(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/huyPhieu`, data)
                
                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
                }
            } catch (err) {
                let responseData = err.response.data
                let message = [] 

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
    }
}
</script>
<style>
    .tiepnhan-suco .e-grid {
        font-family: "Roboto", Arial !important;
    }

    .tiepnhan-suco .e-grid .col-mark-selected-row {
        font-size: 12px;
        display: none;
    }

    .tiepnhan-suco .e-grid .e-active .col-mark-selected-row {
        display: block;
    }

    .tiepnhan-suco .e-grid .e-filterbarcell {
        padding: 5px 7px !important;
        border-width: 1px 0 0 1px !important;
    }

    .tiepnhan-suco .e-grid th.e-headercell,
    .tiepnhan-suco .e-grid .e-gridcontent td.e-rowcell {
        padding: 5px 7px !important;
        height: 30px !important;
    }

    .tiepnhan-suco .e-grid th.e-grouptopleftcell,
    .tiepnhan-suco .e-grid th.e-headercell, 
    .tiepnhan-suco .e-grid th.e-filterbarcell {
        background-color: #BAE7FF !important;
        border-color: white !important;
    }

    .tiepnhan-suco .e-grid th.e-filterbarcell > div input.form-control {
        padding-bottom: 0 !important;
        padding-top: 0 !important;
        height: 26px !important;
    }

    .tiepnhan-suco .e-grid td.e-active {
        background: #F9E1A9 !important;
    }

    .tiepnhan-suco .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tiepnhan-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tiepnhan-suco .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tiepnhan-suco .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tiepnhan-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tiepnhan-suco .e-grid.e-gridhover .e-frozenhover {
        background-color: #FFF9EB !important;
    }

    .tiepnhan-suco .e-grid td.e-rowcell { 
        line-height: 20px !important;
        padding: 5px 7px !important;
    }

    .tiepnhan-suco .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
        border: 0 !important;
    }

    .tiepnhan-suco .e-grid .e-filtertext.e-input.e-disable {
        display: none !important;
    }

    .tiepnhan-suco .e-grid tr.e-emptyrow td {
        text-align: center !important;
    }

    .tiepnhan-suco .e-grid .e-pagerexternalmsg,
    .tiepnhan-suco .e-grid .e-sortnumber {
        display: none !important;
    }

    .tiepnhan-suco .e-grid .e-gdiagonaldown.e-icon-gdownarrow,
    .tiepnhan-suco .e-grid .e-gnextforward.e-icon-grightarrow {
        width: 16px;
        height: 16px;
        color: #0176FF;
        font-family: "ap";
        font-size: 16px;
        cursor: pointer;
        text-align: center;
        line-height: 20px;
        display: inline-block;
        text-indent: 0px;
    }

    .tiepnhan-suco .e-grid .e-icon-gdownarrow::before {
        content: "\E6B8"
    }

    .tiepnhan-suco .e-grid .e-icon-grightarrow::before {
        content: "\E6BB"
    }

    .tiepnhan-suco .e-grid .e-gridfooter .e-summarycell.e-templatecell {
        padding-right: 1px;
        text-align: right;
        font-weight: normal;
    }

    .tiepnhan-suco .e-grid .e-gridcontent .e-summarycell.e-templatecell {
        padding-right: 7px;
        padding-left: 7px;
        font-weight: normal;
    }

    .tiepnhan-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .tiepnhan-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }

    .tiepnhan-suco .grid-suco-dpn .e-gridpager {
        display: none;
    }

    .tiepnhan-suco .disable-btn:hover {
        background-color: white !important;
    }

    .tiepnhan-suco .disable-btn a {
        color: #6c757d !important;
    }

    .tiepnhan-suco .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }

    .tiepnhan-suco .panel-anh-suco {
        width: 99%; 
        height: 180px; 
        background-color: white; 
        position: absolute; 
        z-index: 9999;
        padding: 34px 5px 5px 5px;
        border: 1px solid #ccc;
    }

    .tiepnhan-suco .panel-zoom-img {
        position: fixed;
        width: 500px;
        height: 500px;
        background-color: transparent;
        z-index: 99999;
        margin: auto auto;
        top: 0;
        bottom: 0;
    }

    .tiepnhan-suco .btn-panel {
        padding: 2px !important;
        height: 23px !important;
    }
</style>