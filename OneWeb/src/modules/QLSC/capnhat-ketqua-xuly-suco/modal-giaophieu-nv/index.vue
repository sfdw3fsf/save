<template src="./index.template.html"></template>
<script>
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group } from "@syncfusion/ej2-vue-grids"
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {
    provide: {
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group ]
    },
    data() {
        return {
            const: {
                ALL: -1
            },
            parameters: {
                phieuId: null,
                suCoId: null,
                suCoPtmId: null
            },
            chkTrungTam: false,
            ttvtId: null,
            dsTtvt: [],
            toKtId: null,
            dsToKt: [],
            dsDaGiao: [],
            dsCanGiao: [],
            tableCanGiao: {
                dataSource: [],
                paginationKey: 1,
                pagerData: [],
                pageSize: 10
            },
            nhanVienDaGiaoDuocChon: null
        }
    },
    computed: {
        gridNvDaGiao() {
            return this.$refs['grid-nhanvien-da-giao'].grid
        }
    },
    watch: {
        dsCanGiao(newVal) {
            this.tableCanGiao.dataSource = [...newVal]
            this.tableCanGiao.paginationKey++
            this.onPageChangedTableCanGiao({pageIndex: 0, pageSize: this.tableCanGiao.pageSize})
        },
        chkTrungTam() {
            this.onClickBtnLamMoiDsCanGiao()
        },
        toKtId(newVal) {
            if (!!newVal) {
                this.onClickBtnLamMoiDsCanGiao()
            }
        },
        async ttvtId(newVal) {

            if (!newVal) {
                return
            }

            try {
                this.loading(true)

                if (newVal == this.const.ALL) {
                    await this.layAllDsToKt()
                } else {
                    await this.layDsToKt(newVal)
                }
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (!!this.dsToKt && this.dsToKt.length > 0) {
                this.toKtId = this.dsToKt[0].DONVI_ID
            }
        },
    },
    methods: {
        show() {
            this.$refs['ref-modal-giaophieu-nv'].show()
        },
        hide() {
            this.$refs['ref-modal-giaophieu-nv'].hide()
        },
        onModalHidden() {
            this.parameters.phieuId = null
            this.parameters.suCoId = null
            this.parameters.suCoPtmId = null
            this.chkTrungTam = false
            this.ttvtId = null
            this.dsTtvt = []
            this.toKtId = null
            this.dsToKt = []
            this.dsDaGiao = []
            this.dsCanGiao = []
        },
        setPhieuId(phieuId) {
            this.parameters.phieuId = phieuId
        },
        setSuCoId(suCoId) {
            this.parameters.suCoId = suCoId
        },
        setSuCoPtmId(suCoPtmId) {
            this.parameters.suCoPtmId = suCoPtmId
        },
        async onModalShown() {

            let data = {
                phieu_id: this.parameters.phieuId,
                suco_ptm_id: this.parameters.suCoPtmId
            }

            try {
                this.loading(true)
                let loading1 = this.layDsTtvt()
                let loading2 = this.layDsCanGiaoTheoNv(data)
                let loading3 = this.layDsDaGiao(data)
                await loading1
                await loading2
                await loading3
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (!!this.dsTtvt && this.dsTtvt.length > 0) {
                this.ttvtId = this.dsTtvt[0].DONVI_ID
            }
        },
        getColumnTemplateBtnXoa(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <a href="javascript:void(0)" class="btn btn-main pad4 lh14" @click="onClick">
		                        <span class="-ap icon-close"></span>
	                        </a>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                            grid() {
                                return this.$parent
                            }
                        },
                        methods: {
                            onClick() {
                                this.data.NHIEMVU = null
                                this.data.GHICHU = null
                                
                                let change = {
                                    CHON: this.data.CHON,
                                    NHANVIEN_TH_ID: this.data.NHANVIEN_TH_ID,
                                    NHIEMVU: null,
                                    GHICHU: null,
                                    SO_DT: this.data.SO_DT,
                                    TEN_NV: this.data.TEN_NV,
                                    DONVI_ID: this.data.DONVI_ID,
                                    TEN_DV: this.data.TEN_DV,
                                    MA_NV: this.data.MA_NV,
                                    KEY: this.data.KEY
                                }
                                
                                this.grid.batchUpdate({ changedRecords: [change] })
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateCheckbox(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div class="check-action">
                                <input 
                                    type="checkbox" 
                                    class="check"
                                    :checked="checked"
                                    disabled="true"/>
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
                            checked() {
                                return !!this.data.NHIEMVU || !!this.data.GHICHU
                            }
                        }
                    }
                }
            }
        },
        onQueryCellInfoNhanVien(args) {

            let title = ''

            switch(args.column.field) {
                case 'NHIEMVU':
                    title = 'Nhấn đúp chuột lên ô để nhập nhiệm vụ'
                    break
                case 'GHICHU':
                    title = 'Nhấn đúp chuột lên ô để nhập ghi chú'
                    break
                default:
                    title = ''
            }
            
            if (!!title) {
                args.cell.title = title
            }
        },
        onCellSaved() {
            let grid = this.$refs['grid-nhanvien-can-giao'].grid
            let changes = grid.getBatchChanges()
            grid.batchUpdate(changes)
		},
        onDataBound(ref) {
            return () => {

                let grid = this.$refs[ref]
                let divEmpty = grid.$el.querySelector('.bss-customize-empty')

                if (!!divEmpty) {
                    divEmpty.remove()
                }

                if (grid.getDataRows().length > 0) {
                    grid.$el.querySelector('.e-content').style.visibility = 'visible'
                    grid.selectRow(0)
                } else {
                    grid.$el.querySelector('.e-content').style.visibility = 'hidden'
                    let divEmpty = document.createElement('div')
                    divEmpty.classList.add('bss-customize-empty')
                    divEmpty.style.textAlign = 'center'
                    divEmpty.style.borderBottom = '1px solid lightgray'
                    divEmpty.style.padding = '10px'
                    divEmpty.style.lineHeight = '21px'
                    divEmpty.style.position = 'absolute'
                    divEmpty.style.width = '100%'
                    divEmpty.innerHTML = 'Không có bản ghi nào để hiển thị'
                    grid.$el.querySelector('.e-gridcontent').prepend(divEmpty)
                }
            }
        },
        onPageChangedTableCanGiao(args) {

            this.tableCanGiao.pageSize = args.pageSize

            let pageIndex = args.pageIndex + 1

            if (pageIndex > 0) {
                let to = pageIndex * args.pageSize
                let from = to - args.pageSize
                this.tableCanGiao.pagerData = this.tableCanGiao.dataSource.slice(from, to)
            } else {
                this.tableCanGiao.pagerData = []
            }
        },
        async onClickBtnGhiLai() {

            let hasData = this.dsCanGiao.some(item => {

                let nhiemVu = !item.NHIEMVU ? '' : item.NHIEMVU.trim()
                let ghiChu = !item.GHICHU ? '' : item.GHICHU.trim()

                return !!nhiemVu || !!ghiChu
            })

            if (!hasData) {
                this.$root.toastError('Bạn chưa nhập nội dung giao')
                return
            }

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")

            this.dsCanGiao.forEach(item => {

                let nhanVienId = item.NHANVIEN_TH_ID
                let nhiemVu = !item.NHIEMVU ? '' : item.NHIEMVU.trim()
                let ghiChu = !item.GHICHU ? '' : item.GHICHU.trim()

                if (!nhiemVu && !ghiChu) {
                    return
                }

                if (!nhiemVu) {
                    this.$root.toastError('Hãy nhập nội dung nhiệm vụ cần giao cho nhân viên')
                    return
                }

                let node = doc.createElement("item")
                node.setAttribute("ni", nhanVienId)
                node.setAttribute("nv", nhiemVu)
                node.setAttribute("gc", ghiChu)
                root.appendChild(node)
            })

            doc.appendChild(root)

            let dsGiaoViec = (new XMLSerializer()).serializeToString(doc)

            let data = {
                phieu_id: this.parameters.phieuId,
                suco_ptm_id: this.parameters.suCoPtmId,
                ds_giaoviec: dsGiaoViec
            }

            try {
                this.loading(true)
                let dsNoiDungSms = await this.giaoViecNv(data)
                let dsSmsThanhCong = dsNoiDungSms.Result

                // for (let i = 0; i < dsNoiDungSms.length; i++) {

                //     let sms = dsNoiDungSms[i]

                //     try {
                //         if (!!sms.SO_DT && !!sms.ND_SMS) {
                //             await this.sendSms(sms.SO_DT, sms.ND_SMS)
                //             dsSmsThanhCong.push(sms.GIAOVIEC_ID)
                //         }
                //     } catch(err) {
                //     }
                // }

                if (dsSmsThanhCong.length > 0) {
                    await this.capNhatTrangThaiGuiSms(dsSmsThanhCong.join())
                }
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Giao việc thành công')
            this.$emit('success')
            this.hide()
        },
        async okGuiSms() {

            if (!this.nhanVienDaGiaoDuocChon) {
                this.$root.toastError('Bạn chưa chọn nhân viên để gửi tin nhắn')
                return
            }

            try {
                this.loading(true)
                let item = await this.layNdGuiSms(this.nhanVienDaGiaoDuocChon.GIAOVIEC_ID);
                if(item.Result != true){
                    this.$root.toastError(item.Msg)
                }
                // let noiDungGuiSms = await this.layNdGuiSms(this.nhanVienDaGiaoDuocChon.GIAOVIEC_ID)

                // if (!noiDungGuiSms) {
                //     throw 'Phiếu không tồn tại hoặc đã hoàn công không thể nhắn tin'
                // }

                // if (!noiDungGuiSms.SO_DT) {
                //     throw 'Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)'
                // }

                // try {
                //     await this.sendSms(noiDungGuiSms.SO_DT, noiDungGuiSms.ND_SMS)
                // } catch(err) {
                //     throw 'Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)'
                // }

                try {
                    await this.capNhatTrangThaiGuiSms(this.nhanVienDaGiaoDuocChon.GIAOVIEC_ID)
                } catch(err) {
                }
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Nhắn tin thành công')
        },
        onSelectRowNvDaGiao(data) {
            this.nhanVienDaGiaoDuocChon = data
        },
        onClickBtnSms() {
            if (this.gridNvDaGiao.getDataRows().length > 0 && !!this.nhanVienDaGiaoDuocChon) {
                this.$refs['modal-xacnhan-gui-sms'].show()
            }
        },
        async onClickBtnLamMoiDsCanGiao() {
            try {
                this.loading(true)

                if (this.chkTrungTam) {
                    await this.layDsCanGiaoTheoDv(this.toKtId)
                } else {
                    await this.layDsCanGiaoTheoNv({
                        phieu_id: this.parameters.phieuId,
                        suco_ptm_id: this.parameters.suCoPtmId
                    })
                }
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async onClickBtnLamMoiDsDaGiao() {

            let data = {
                phieu_id: this.parameters.phieuId,
                suco_ptm_id: this.parameters.suCoPtmId
            }

            try {
                this.loading(true)
                await this.layDsDaGiao(data)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async giaoViecNv(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/ketqua-xuly-suco/giaoviec-nv`, data)
                
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
        async layDsCanGiaoTheoDv(donViId) {
            try {
                let response = await this.$root.context.get(`/web-suco/tracuu/suco/ds-nhanvien-xl-theo-dv?donvi_id=${donViId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => item.KEY = (index + 1))
                    this.dsCanGiao= response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsCanGiao = []
            }
        },
        async layDsCanGiaoTheoNv(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/tracuu/suco/ds-nhanvien-xl-theo-nv`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => item.KEY = (index + 1))
                    this.dsCanGiao = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsCanGiao = []
            }
        },
        async layNdGuiSms(giaoViecId) {
            try {
                let response = await this.$root.context.get(`/web-suco/ketqua-xuly-suco/lay-nd-gui-sms?giaoviec_id=${giaoViecId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return null
            }
        },
        async layDsDaGiao(data) {
            try {
                let response = await this.$root.context.get(`/web-suco/tracuu/suco/ds-nhanvien-xuly`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => item.NGAYGIAO = this.ddmmyyyyhhmi(item.NGAYGIAO))
                    this.dsDaGiao= response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsDaGiao = []
            }
        },
        async layDsToKt(ttvtId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsToKT`, {id: ttvtId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsToKt = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsToKt = []
            }
        },
        async layAllDsToKt(ttvtId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsToKTTheoPhanVung`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsToKt = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsToKt = []
            }
        },
        async layDsTtvt() {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsTTVTTrongToken`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTtvt = [{DONVI_ID: this.const.ALL, TEN_DV: 'Tất cả'}].concat(response.data)
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsTtvt = []
            }
        },
        async capNhatTrangThaiGuiSms(dsGiaoPhieuId) {
            try {
                let response = await this.$root.context.post("/web-suco/ketqua-xuly-suco/capnhat-trangthai-gui-sms", { ds_giaoviec_id: dsGiaoPhieuId})
                
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
        // async sendSms(soDt, noiDung) {

        //     if (!soDt || !noiDung) {
        //         return
        //     }

        //     soDt = "" + soDt

        //     if (!soDt.startsWith("84")) {
        //         soDt = `84${soDt.startsWith("0") ? soDt.substr(1) : soDt}`
        //     }

        //     let data = {
        //         content: noiDung,
        //         msisdn: soDt,
        //         mt_type: "reply"
        //     }

        //     try {
        //         await this.$root.context.post("/web-cabman/traphieukhieunai/off-send-sms", data)
        //     } catch(err) {
        //     }
        //},
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
        ddmmyyyyhhmi(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}`
        },
     }
}
</script>
<style>
    .modal-giaophieu-nhanvien .e-grid .col-mark-selected-row {
        font-size: 12px;
        display: none;
    }

    .modal-giaophieu-nhanvien .e-grid .e-active .col-mark-selected-row {
        display: block;
    }

    .modal-giaophieu-nhanvien .e-grid {
        font-family: "Roboto", Arial !important;
    }

    .modal-giaophieu-nhanvien .e-grid .e-filterbarcell {
        padding: 5px 7px !important;
        border-width: 1px 0 0 1px !important;
    }

    .modal-giaophieu-nhanvien .e-grid th.e-headercell,
    .modal-giaophieu-nhanvien .e-grid .e-gridcontent td.e-rowcell {
        padding: 5px 7px !important;
        height: 30px !important;
    }

    .modal-giaophieu-nhanvien .e-grid th.e-grouptopleftcell,
    .modal-giaophieu-nhanvien .e-grid th.e-headercell, 
    .modal-giaophieu-nhanvien .e-grid th.e-filterbarcell {
        background-color: #BAE7FF !important;
        border-color: white !important;
    }

    .modal-giaophieu-nhanvien .e-grid th.e-filterbarcell > div input.form-control {
        padding-bottom: 0 !important;
        padding-top: 0 !important;
        height: 26px !important;
    }

    .modal-giaophieu-nhanvien .e-grid td.e-active {
        background: #F9E1A9 !important;
    }

    .modal-giaophieu-nhanvien .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .modal-giaophieu-nhanvien .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .modal-giaophieu-nhanvien .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .modal-giaophieu-nhanvien .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .modal-giaophieu-nhanvien .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
        background-color: #FFF9EB !important;
    }

    .modal-giaophieu-nhanvien .e-grid td.e-rowcell { 
        line-height: 20px !important;
        padding: 5px 7px !important;
    }

    .modal-giaophieu-nhanvien .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
        border: 0 !important;
    }

    .modal-giaophieu-nhanvien .e-grid .e-filtertext.e-input.e-disable {
        display: none !important;
    }

    .modal-giaophieu-nhanvien .e-grid tr.e-emptyrow td {
        text-align: center !important;
    }

    .modal-giaophieu-nhanvien .e-grid .e-pagerexternalmsg,
    .modal-giaophieu-nhanvien .e-grid .e-sortnumber {
        display: none !important;
    }

    .modal-giaophieu-nhanvien .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .modal-giaophieu-nhanvien .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }
</style>
