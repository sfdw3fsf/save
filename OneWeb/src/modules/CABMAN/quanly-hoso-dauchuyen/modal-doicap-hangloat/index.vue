<template src="./index.template.html"></template>
<script>
import ModalChonKetCuoi from '../../DauNoiThueBao/Modal/index.vue'
export default {
    components: { ModalChonKetCuoi },
    props: {
        modalId: String,
        dsThueBaoDauNoi: Array
    },
    data() {
        return {
            isModified: false,
            dsViTriRoi: [],
            dsThueBao: [],
            showModalChonKetCuoi: false,
            ketCuoiDuocChon: null,
            tableThueBao: {
                paginationKey: 1,
                dataSource: [],
                pagerData: [],
                selectedRowIndexOnPage: 0,
                selectedRowIndexOnData: 0,
                selectedData: null,
            },
            checkAllViTri: false,
            dsViTriIdDuocChon: [],
            checkAllThueBao: false,
            dsThueBaoIdDuocChon: [],
            columnThueBao: [
                {
                    field: 'STT',
                    headerText: 'STT',
                    headerTextAlign: 'Center',
                    textAlign: 'Right',
                    width: 70
                },
                {
                    field: 'MA_TB',
                    headerText: 'Số máy/Account',
                    headerTextAlign: 'Center',
                    width: 130
                },
                {
                    field: 'TEN_TB',
                    headerText: 'Thông tin thuê bao',
                    headerTextAlign: 'Center',
                    width: 130
                },
                {
                    field: 'DIACHI_LD',
                    headerText: 'Địa chỉ lắp đặt',
                    headerTextAlign: 'Center',
                    width: 130
                },
            ],
            columnCap: [
                {
                    field: 'KYHIEU_KC_VT',
                    headerText: 'Kết cuối#vị trí',
                    headerTextAlign: 'Center',
                    width: 130
                },
                {
                    field: 'TRANGTHAI',
                    headerText: 'Trạng thái',
                    headerTextAlign: 'Center',
                    width: 130
                },
            ]
        }
    },
    computed: {
        kyHieuKc() {
            return !this.ketCuoiDuocChon ? null : this.ketCuoiDuocChon.KYHIEU
        },
        ketCuoiId() {
            return !this.ketCuoiDuocChon ? null : this.ketCuoiDuocChon.KETCUOI_ID
        },
        enabledBtnThucThi() {
            return this.dsThueBao.length > 0 && this.dsThueBao.some(item => !!item.KETCUOI_ID)
        }
    },
    watch: {
        dsThueBao(newVal) {
            this.tableThueBao.dataSource = [...newVal]
            this.tableThueBao.paginationKey++
        },
        checkAllViTri(newVal) {

            if (newVal) {
                this.dsViTriIdDuocChon = this.dsViTriRoi.filter(item => item.ENABLED).map(item => item.ID)
                return
            } 

            if (this.dsViTriIdDuocChon.length > 0 
                && this.dsViTriIdDuocChon.length == this.dsViTriRoi.filter(item => item.ENABLED).length) {
                this.dsViTriIdDuocChon = []
            }
        },
        dsViTriIdDuocChon(newValue) {
            this.checkAllViTri = newValue.length > 0 && newValue.length == this.dsViTriRoi.filter(item => item.ENABLED).length
        },
        checkAllThueBao(newVal) {

            if (newVal) {
                this.dsThueBaoIdDuocChon = this.dsThueBao.map(item => item.THUEBAO_ID)
                return
            } 

            if (this.dsThueBaoIdDuocChon.length > 0 
                && this.dsThueBaoIdDuocChon.length == this.dsThueBao.length) {
                this.dsThueBaoIdDuocChon = []
            }
        },
        dsThueBaoIdDuocChon(newValue) {
            this.checkAllThueBao = newValue.length > 0 && newValue.length == this.dsThueBao.length
        },
        dsViTriRoi(newVal) {

            let ketCuoiViTriDaChon = this.dsThueBao.map(item => `${item.KETCUOI_ID}#${item.VITRI}`)

            newVal.forEach(item => {
                if (ketCuoiViTriDaChon.indexOf(`${item.KETCUOI_ID}#${item.ID}`) >= 0) {
                    item.ENABLED = false
                    item.MOTA = `${viTri.ID} - Đã gán`
                }
            })
        }
    },
    methods: {
        onModalHidden() {
            this.dsViTriRoi = []
            this.dsThueBao = []
            this.showModalChonKetCuoi = false
            this.ketCuoiDuocChon = null
            this.checkAllViTri = false
            this.dsViTriIdDuocChon = []
            this.checkAllThueBao = false,
            this.dsThueBaoIdDuocChon = []

            if (this.isModified) {
                this.$emit('change')
            }
        },
        async onModalShown() {
            
            this.isModified = false

            try {
                this.loading(true)
                await this.layDsThueBao(this.dsThueBaoDauNoi.map(item => item.THUEBAO_ID).join())
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        getColumnTemplateChon(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" class="check" :value="data.THUEBAO_ID" v-model="parent.dsThueBaoIdDuocChon">
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            }
                        }
                    }
                }
            }
        },
        getHeaderTemplateChon(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" class="check" v-model="parent.checkAllThueBao">
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            }
                        }
                    }
                }
            }
        },
        onQueryCellInfo(args) {
            if (!!args.column && args.column.field == 'TRANGTHAI') {
                args.cell.innerHTML = this.layMoTaTrangThai(args.data.TRANGTHAI)
                args.cell.style.color = this.layMauTrangThai(args.data.TRANGTHAI)
            }
        },
        async onClickBtnThucThi() {

            let dsThueBaoDauNoi = this.dsThueBao.filter(item => item.TRANGTHAI == 1)

            if (dsThueBaoDauNoi.length == 0) {
                this.$toast.error('Chưa có thuê bao nào được gán kết cuối')
                return
            }

            let soLuongDauNoiThanhCong = 0

            try {
                this.loading(true)

                for (var i = 0; i < dsThueBaoDauNoi.length; i++) {
                    
                    let thueBao = dsThueBaoDauNoi[i]
                    thueBao.TRANGTHAI = 2
                    this.tableThueBao.paginationKey++
                    
                    let data = {
                        db_id: thueBao.THUEBAO_ID,
                        ketcuoi_id: thueBao.KETCUOI_ID,
                        vitri: thueBao.VITRI
                    }

                    try {
                        await this.dauNoiThueBao(data)
                        thueBao.TRANGTHAI = 3
                        this.tableThueBao.paginationKey++
                        this.isModified = true
                        ++soLuongDauNoiThanhCong
                    } catch(err) {
                        thueBao.TRANGTHAI = 4
                        this.tableThueBao.paginationKey++
                    }
                }
            } catch (err) {
                this.$toast.error('Có lỗi xảy ra trong quá trình đăng ký.\n' + err)
                return
            } finally {
                this.loading(false)
            }

            let message = []
            message.push('Tiến trình đấu nối thuê bao vào kết cuối kết thúc!')
            message.push('Tổng cộng ' + dsThueBaoDauNoi.length + ' thuê bao trên danh sách')
            message.push('Đấu nối thành công cho: ' + soLuongDauNoiThanhCong + ' thuê bao')
            this.$toast.info(message.join('\n'))

            if (soLuongDauNoiThanhCong > 0 
                && soLuongDauNoiThanhCong == dsThueBaoDauNoi.length) {
                this.$bvModal.hide(this.modalId)
            }
        },
        onClickBtnHuyGan() {
            
            let dsThueBaoDuocChon = this.dsThueBao.filter(item => {
                return this.dsThueBaoIdDuocChon.indexOf(item.THUEBAO_ID) >= 0
            })

            for (var i = 0; i < dsThueBaoDuocChon.length; i++) {

                let thueBao = dsThueBaoDuocChon[i]

                if ([1, 4].indexOf(thueBao.TRANGTHAI) >= 0) {
                    
                    let viTri = this.dsViTriRoi.filter(item => {
                        return item.KETCUOI_ID == thueBao.KETCUOI_ID && item.ID == thueBao.VITRI
                    })
                    
                    if (viTri.length > 0) {
                        viTri[0].ENABLED = true
                        viTri[0].MOTA = `${viTri[0].ID}`
                    }
                    
                    thueBao.KYHIEU_KC_VT = null
                    thueBao.KETCUOI_ID = null
                    thueBao.VITRI = null
                    thueBao.TRANGTHAI = 0
                }
            }

            let dsViTriEnabled = this.dsViTriRoi.filter(item => item.ENABLED)
            this.checkAllViTri = this.dsViTriIdDuocChon.length > 0 && this.dsViTriIdDuocChon.length == dsViTriEnabled.length

            this.$refs['grid-thuebao'].grid.refresh()
        },
        onClickBtnGan() {
            
            let dsViTriDuocChon = this.dsViTriRoi.filter(item => {
                return this.dsViTriIdDuocChon.indexOf(item.ID) >= 0
            })

            let dsViTriIdGanThanhCong = []

            for (var i = 0; i < dsViTriDuocChon.length; i++) {
                
                let viTri = dsViTriDuocChon[i]

                for (var j = 0; j < this.dsThueBao.length; j++) {

                    let thueBao = this.dsThueBao[j]

                    if (thueBao.TRANGTHAI == 0) {
                        thueBao.KYHIEU_KC_VT = `${viTri.KYHIEU_KC}#${viTri.ID}`
                        thueBao.KETCUOI_ID = viTri.KETCUOI_ID
                        thueBao.VITRI = viTri.ID
                        thueBao.TRANGTHAI = 1
                        viTri.ENABLED = false
                        viTri.MOTA = `${viTri.ID} - Đã gán`
                        dsViTriIdGanThanhCong.push(viTri.ID)
                        break
                    }
                }
            }

            this.dsViTriIdDuocChon = this.dsViTriIdDuocChon.filter(item => {
                return dsViTriIdGanThanhCong.indexOf(item) < 0
            })

            this.$refs['grid-thuebao'].grid.refresh()
        },
        async onSelectKetCuoi(rowData) {

            this.ketCuoiDuocChon = rowData
            this.dsViTriIdDuocChon = []

            try {
                this.loading(true)
                await this.layViTriRoi(rowData.KETCUOI_ID)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        closeModalChonKetCuoi() {
            this.showModalChonKetCuoi = !this.showModalChonKetCuoi
        },
        onPageChangeTableThueBao(data) {
            
            this.tableThueBao.selectedRowIndexOnPage = data.rowIndexOnPage
            this.tableThueBao.selectedRowIndexOnData = data.rowIndexOnData

            if (data.pageIndex > 0) {
                let to = data.pageIndex * data.pageSize
                let from = to - data.pageSize
                this.tableThueBao.pagerData = this.tableThueBao.dataSource.slice(from, to)
                this.tableThueBao.selectedData = this.tableThueBao.pagerData[data.rowIndexOnPage - 1]
            } else {
                this.tableThueBao.pagerData = []
                this.tableThueBao.selectedData = null
            }
        },
        onRowChangeTableThueBao(data) {
            this.tableThueBao.selectedRowIndexOnPage = data.rowIndexOnPage
            this.tableThueBao.selectedRowIndexOnData = data.rowIndexOnData
            this.tableThueBao.selectedData = this.tableThueBao.pagerData[data.rowIndexOnPage - 1]
        },
        onClickRowTableThueBao(rowIndex) {
            this.tableThueBao.selectedRowIndexOnPage = rowIndex
        },
        layMoTaTrangThai(trangThaiId) {
            switch (trangThaiId) {
                case 0:
                    return "Chờ gán kết cuối"
                case 1:
                    return "Đã gán kết cuối"
                case 2:
                    return "Đang đấu nối..."
                case 3:
                    return "Đấu nối thành công"
                case 4:
                    return "Đấu nối thất bại"
            }
        },
        layMauTrangThai(trangThaiId) {
            switch (trangThaiId) {
                case 0:
                    return "black"
                case 1:
                    return "blue"
                case 2:
                    return "teal"
                case 3:
                    return "green"
                case 4:
                    return "red"
            }
        },
        async layDsThueBao(dsThueBaoId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/hoso-dauchuyen/lay-thongtin-ds-thuebao-theo-id?ds_id_thuebao=${dsThueBaoId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    
                    response.data.forEach(item => {
                        item.KYHIEU_KC_VT = null
                        item.KETCUOI_ID = null
                        item.VITRI = null
                        item.TRANGTHAI = 0
                    })

                    this.dsThueBao = response.data
                } else {
                    this.dsThueBao = []
                }
            } catch (err) {
                this.dsThueBao = []
            }
        },
        async layViTriRoi(ketCuoiId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/hoso-dauchuyen/ds-vitri-nguon?ketcuoi_id=${ketCuoiId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => { 
                        item.ENABLED = true 
                        item.KYHIEU_KC = this.kyHieuKc
                        item.KETCUOI_ID = this.ketCuoiId
                    })
                    this.dsViTriRoi = response.data
                } else {
                    this.dsViTriRoi = []
                }
            } catch (err) {
                this.dsViTriRoi = []
            }
        },
        async dauNoiThueBao(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/daunoi-thuebao', data)
                
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
    #id-modal-doicap-hangloat .e-grid .e-stackedheader .e-templatecell .e-headercelldiv {
        padding: 0 !important;
    }

    #id-modal-doicap-hangloat .e-grid.e-default.e-bothlines .e-headercell, .e-grid.e-default.e-bothlines .e-detailheadercell {
        border-width: 1px 1px 0px 0px !important;
    }

    @media (min-width: 1200px) {
        #id-modal-doicap-hangloat .modal-xl {
            max-width: 99% !important;
        }
    }
</style>