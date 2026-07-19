<template src="./index.template.html"></template>
<style>
    .select-bras .vue-treeselect__control {
        height: 32px !important;
    }

    .quanly-prefix-cua-bras .disable-btn:hover {
        background-color: white !important;
    }

    .quanly-prefix-cua-bras .disable-btn a {
        color: #6c757d !important;
    }

    .quanly-prefix-cua-bras .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }

    .quanly-prefix-cua-bras .grid-loai-hinh-da-gan .e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .quanly-prefix-cua-bras .grid-loai-hinh-da-gan .e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
    .quanly-prefix-cua-bras .grid-loai-hinh-chua-gan .e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .quanly-prefix-cua-bras .grid-loai-hinh-chua-gan .e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }

    .quanly-prefix-cua-bras .div-ds-prefix .e-gridcontent .e-content .e-table tbody .e-row .e-rowcell {
        cursor: pointer;
    }
</style>
<script>
    import BssLocalPagination1 from '@/components/BssLocalPagination_1'
    import BssPagination2 from '@/components/BssPagination_2'
    import BssErrorMarker from '@/components/BssErrorMarker'
    import BssRequiredMarker from '@/components/BssRequiredMarker'
    import { required } from 'vuelidate/lib/validators'

    export default {
        name: 'QuanLyPrefixIPCuaBras',
        components: {BssLocalPagination1, BssErrorMarker, BssRequiredMarker, BssPagination2},
        computed: {
            treeSelectOptions () {
                return this.dsBras.map(item => { return { id: item.BRAS_ID, label: item.TENBRAS }})
            }
        },
        methods: {
            getHeaderTemplateChonAllLoaiTbDaGan(parent) {
                return function() {
                    return {
                        template: {
                            template: `
                                <div class="check-action">
                                    <input type="checkbox" v-model="parent.checkAllLoaiHinhDaGan" class="check"/>
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
            getColumnTemplateChonLoaiTbDaGan(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <div class="check-action">
                                    <input 
                                        type="checkbox" 
                                        class="check"
                                        v-model="parent.tableDsLoaiTbDaGan.checked"
                                        :value="data.LOAITB_ID"/>
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
            getHeaderTemplateChonAllLoaiTbChuaGan(parent) {
                return function() {
                    return {
                        template: {
                            template: `
                                <div class="check-action">
                                    <input type="checkbox" v-model="parent.checkAllLoaiHinhChuaGan" class="check"/>
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
            getColumnTemplateChonLoaiTbChuaGan(parent) {
                return function() {
                    return { 
                        template: {
                            template: `
                                <div class="check-action">
                                    <input 
                                        type="checkbox" 
                                        class="check"
                                        v-model="parent.tableDsLoaiTbChuaGan.checked"
                                        :value="data.LOAITB_ID"/>
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
            async layDsPrefixIp () {
                try {
                    let response = await this.$root.context.get('/web-ecms/prefix-ip-bras/lay-ds-prefix-ip')
                    
                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        response.data.forEach((item, index) => item.KEY = index + 1)
                        this.dsPrefixIp = response.data
                        return
                    } 
                
                    throw {response: { data: response}}
                } catch (err) {
                    this.dsPrefixIp = []
                }
            },
            async layDsLoaiTbDaGan (prefix) {
                try {
                    let response = await this.$root.context.get('/web-ecms/prefix-ip-bras/lay-ds-loaitb-dagan', { prefix: prefix })
                    this.dsLoaiTbDaGan = response.data != null ? response.data : []
                } catch (err) {
                    throw err
                }
            },
            setDsLoaiTbDaGan (data) {
                this.dsLoaiTbDaGan = data
            },
            async layDsLoaiTbChuaGan (data) {
                try {
                    let response = await this.$root.context.post('/web-ecms/prefix-ip-bras/lay-ds-loaitb-chuagan', data)
                    this.dsLoaiTbChuaGan = response.data != null ? response.data : []
                } catch (err) {
                    throw err
                }
            },
            setDsLoaiTbChuaGan (data) {
                this.dsLoaiTbChuaGan = data
            },
            async layDsIp (prefix) {
                try {
                    let response = await this.$root.context.get('/web-ecms/prefix-ip-bras/lay-ds-ip', { prefix: prefix })
                    
                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        response.data.forEach((item, index) => item.KEY = index + 1)
                        this.dsIp = response.data
                        return
                    } 
                
                    throw {response: { data: response}}
                } catch (err) {
                    this.dsIp = []
                }
            },
            setDsIp (data) {
                this.dsIp =  data
            },
            async layDsBras () {
                try {
                    let response = await this.$root.context.get('/web-ecms/danhmuc/quanlybras/danhsach')
                    this.dsBras = response.data != null ? response.data : []
                } catch (err) {
                    throw err
                }
            },
            async themMoi (data) {
                try {
                    let response = await this.$root.context.post('/web-ecms/prefix-ip-bras/themmoi', data)
                    
                    if (response.error_code === 'BSS-00000000') {
                        return {
                            message: 'Cập nhật thành công',
                            data: response.data
                        }
                    }

                    throw {response: { data: response}}
                } catch (err) {
                    let responseData = err.response.data
                    let message = responseData.message

                    if (responseData.message_detail) {
                        message += '\n' + responseData.message_detail
                    }

                    if (responseData.data) {
                        let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                        message = message + '\n' +  [...new Set(detailMessage)].join('\n')
                    }

                    throw message
                }
            },
            async capNhat (data) {
                try {
                    let response = await this.$root.context.post('/web-ecms/prefix-ip-bras/capnhat', data)
                    
                    if (response.error_code === 'BSS-00000000') {
                        return 'Cập nhật thành công'
                    }

                    throw {response: { data: response}}
                } catch (err) {
                    let responseData = err.response.data
                    let message = responseData.message

                    if (responseData.message_detail) {
                        message += '\n' + responseData.message_detail
                    }

                    if (responseData.data) {
                        let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                        message = message + '\n' +  [...new Set(detailMessage)].join('\n')
                    }

                    throw message
                }
            },
            async kiemTraPrefix (data) { 
                try {
                    let response = await this.$root.context.post(`/web-ecms/prefix-ip-bras/kiemtra-prefix-ip?prefix=${data}`)
                    
                    if (response.error_code !== 'BSS-00000000') {
                        throw {response: { data: response}}
                    }
                } catch (err) {
                    let message = response.data.message

                    if (response.data.message_detail) {
                        message += '\n' + response.data.message_detail
                    }

                    throw message
                }
            },
            async xoaPrefix(data) {
                try {
                    let response = await this.$root.context.post(`/web-ecms/prefix-ip-bras/xoa?prefix=${data}`)
                    
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
            async sinhIp (data) { 
                try {
                    let response = await this.$root.context.post('/web-ecms/prefix-ip-bras/sinh-ip', data)
                    
                    if (response.error_code !== 'BSS-00000000') {
                        throw {response: { data: response}}
                    }
                } catch (err) {
                    let response = err.response.data 
                    let message = [] 

                    if (response.message) {
                        message.push(response.message)
                    }

                    if (response.message_detail) {
                        message.push(response.message_detail)
                    }

                    if (response.data) {
                        let detailMessage = Object.getOwnPropertyNames(response.data).map(item => response.data[item])
                        message.push([...new Set(detailMessage)].join('\n'))
                    }

                    throw message.join('\n')
                }
            },
            async layDsDichVuVt () {
                try {
                    let response = await this.$root.context.get('/web-ecms/prefix-ip-bras/dm-dichvu-vt')
                    this.dsDichVuVt = response.data != null ? response.data : []
                } catch (err) {
                    throw err
                }
            },
            async layDsLoaiIp () {
                try {
                    let response = await this.$root.context.get('/web-ecms/prefix-ip-bras/dm-loai-ip')
                    this.dsLoaiIp = response.data != null ? response.data : []
                } catch (err) {
                    throw err
                }
            },
            onChangeComboDichvuvtId: async function (event) {
                try {
                    this.loading(true)
                    let data = {
                        prefix: !this.tablePrefix.selectedData ? '' : this.tablePrefix.selectedData.PREFIX,
                        dichvuvt_id: event.target.value
                    }
                    await this.layDsLoaiTbChuaGan(data)
                } catch (err) {
                    
                } finally {
                    this.loading(false)
                }
            },
            onClickButtonGoGan () {
                let dsLoaiTbGo = this.dsLoaiTbDaGan.filter(item => this.tableDsLoaiTbDaGan.checked.includes(item.LOAITB_ID))
                this.setDsLoaiTbDaGan(this.dsLoaiTbDaGan.filter(item => !dsLoaiTbGo.includes(item)))
                this.setDsLoaiTbChuaGan(dsLoaiTbGo.concat(this.dsLoaiTbChuaGan))
            },
            onClickButtonGan () {
                let dsLoaiTbGan = this.dsLoaiTbChuaGan.filter(item => this.tableDsLoaiTbChuaGan.checked.includes(item.LOAITB_ID))
                this.setDsLoaiTbChuaGan(this.dsLoaiTbChuaGan.filter(item => !dsLoaiTbGan.includes(item)))
                this.setDsLoaiTbDaGan(dsLoaiTbGan.concat(this.dsLoaiTbDaGan))
            },
            onClickButtonNhapMoi () {
                if (!this.buttonState.btnNhapMoiEnabled) {
                    return
                }
                
                this.clear()
                this.setButton(0)

                let grid = this.$refs['grid-prefix'].grid
                this.oldSelectedRowIndexPrefix = grid.getSelectedRowIndexes()
                
                if (!!this.oldSelectedRowIndexPrefix && this.oldSelectedRowIndexPrefix.length > 0) {
                    this.oldSelectedRowIndexPrefix = this.oldSelectedRowIndexPrefix[0]
                    grid.clearSelection()
                }
            },
            onClickButtonHuyBo () {

                if (!this.buttonState.btnNhapMoiEnabled) {
                    this.setButton(1)
                    
                    if (this.dsPrefixIp.length > 0) {
                        this.$refs['grid-prefix'].grid.selectRow(this.oldSelectedRowIndexPrefix)
                    } else {
                        this.clear()
                    }
                    return
                }

                this.hienThiDsPrefix()
            },
            async onClickButtonCapNhat () {

                let errorMessage = []

                this.$v.$touch()

                if (this.$v.$invalid) {

                    if (!this.$v.brasId.required) {
                        errorMessage.push("Bạn chưa chọn bras")
                    }

                    if (!this.$v.prefix.required) {
                        errorMessage.push("Bạn chưa nhập prefix")
                    }

                    if (!this.$v.tuso.khongLonHonDenSo) {
                        errorMessage.push('Từ số không được lớn hơn Đến số')
                    }

                    if (!this.$v.loaiIpId.required) {
                        errorMessage.push("Bạn chưa chọn loại IP")
                    }
                }

                if (!this.dsLoaiTbDaGan || this.dsLoaiTbDaGan.length == 0) {
                    errorMessage.push("Bạn chưa nhập loại hình thuê bao")
                }

                if (errorMessage.length > 0) {
                    this.$toast.error(errorMessage.join('\n'))
                    return
                }

                let data = {
                    bras_id: this.brasId.join(','),
                    prefix: this.prefix,
                    tuso: this.tuso + '',
                    denso: this.denso + '',
                    loai_id: this.loaiIpId + '',
                    ds_loaitb_id: this.dsLoaiTbDaGan.map(item => item.LOAITB_ID).join()
                }

                try {
                    let result = null

                    this.loading(true)

                    if (!this.buttonState.btnNhapMoiEnabled) {
                        result = await this.themMoi(data)
                        this.dsPrefixIp.unshift(result.data)
                        this.loading(false)
                        this.$toast.success(result.message)
                    } else {
                        result = await this.capNhat(data)
                        this.tablePrefix.selectedData.BRAS_ID = data.bras_id
                        this.tablePrefix.selectedData.PREFIX = data.prefix
                        this.tablePrefix.selectedData.TUSO = data.tuso
                        this.tablePrefix.selectedData.DENSO = data.denso
                        this.tablePrefix.selectedData.LOAI_ID = data.loai_id
                        this.tablePrefix.selectedData.LOAI = this.dsLoaiIp.filter(item => item.LOAI_ID == data.loai_id)[0].LOAI
                        this.tablePrefix.selectedData.TENBRAS = this.dsBras.filter(item => this.brasId.indexOf(item.BRAS_ID) >= 0).map(item => item.TENBRAS).join(', ')
                        this.$refs['grid-prefix'].grid.batchUpdate({ changedRecords: [{...this.tablePrefix.selectedData}] })
                        this.loading(false)
                        this.$toast.success(result)
                    }
                } catch (err) {
                    this.loading(false)
                    this.$toast.error(err)
                }
            },
            async onClickButtonXoa () {
                if (!this.buttonState.btnXoaEnabled) {
                    return
                }

                try {
                    this.loading(true)
                    await this.kiemTraPrefix(this.tablePrefix.selectedData.PREFIX)
                } catch (err) {
                    this.$toast.error(err)
                    return
                } finally {
                    this.loading(false)
                }

                this.xacNhanXoa = true
            },
            async okXoaPrefix () {
                try {
                    this.loading(true)
                    await this.xoaPrefix(this.tablePrefix.selectedData.PREFIX)
                    this.$toast.success('Cập nhật thành công')
                } catch (err) {
                    this.$toast.error(err)
                    return
                } finally {
                    this.loading(false)
                }

                this.hienThiDsPrefix()
            },
            async onClickButtonSinhIp () {
                if (!this.buttonState.btnSinhIPEnabled) {
                    return
                }

                try {
                    this.loading(true)
                    await this.sinhIp({prefix: this.tablePrefix.selectedData.PREFIX})
                    this.$toast.success('Cập nhật thành công')
                    await this.layDsIp(this.tablePrefix.selectedData.PREFIX)
                } catch (err) {
                    this.$toast.error(err)
                } finally {
                    this.loading(false)
                }
            },
            async hienThiDsPrefix () {
                try {
                    this.loading(true)
                    await this.layDsPrefixIp()
                } catch (err) {

                } finally {
                    this.loading(false)
                } 
                
                if (!this.dsPrefixIp || this.dsPrefixIp.length == 0) {
                    this.setButton(0)
                    this.clear()
                } else {
                    this.setButton(1)
                }
            },
            setButton (kieu) {
                if (kieu == 0) { //nhap moi, chua co du lieu
                    this.buttonState.btnNhapMoiEnabled = false
                    this.buttonState.btnCapNhatEnabled = true
                    this.buttonState.btnXoaEnabled = false
                    this.buttonState.btnSinhIPEnabled = false
                } else if (kieu == 1) { //sua
                    this.buttonState.btnNhapMoiEnabled = true
                    this.buttonState.btnCapNhatEnabled = true
                    this.buttonState.btnXoaEnabled = true
                    this.buttonState.btnSinhIPEnabled = true
                }
            },
            async clear () {
                this.brasId = []
                this.prefix = ''
                this.tuso = 0
                this.denso = 0
                
                this.setDsLoaiTbDaGan([])
                this.setDsIp([])

                try {
                    this.loading(true)
                    await this.layDsLoaiTbChuaGan({ prefix: '', dichvuvt_id: this.dichvuvtId })
                } catch (err) {

                } finally {
                    this.loading(false)
                }

                this.$v.$reset()
            },
            async hienThiThongTinPrefix () {
                let prefixInfo = this.tablePrefix.selectedData
                this.prefix = prefixInfo.PREFIX
                this.brasId = !prefixInfo.BRAS_ID ? [] : (prefixInfo.BRAS_ID + '').split(',').map(item => parseInt(item))
                this.loaiIpId = prefixInfo.LOAI_ID
                this.tuso = prefixInfo.TUSO
                this.denso = prefixInfo.DENSO
                
                try {
                    this.loading(true)
                    let loading1 = this.layDsLoaiTbDaGan(this.prefix)
                    let loading2 = this.layDsLoaiTbChuaGan({ prefix: this.prefix, dichvuvt_id: this.dichvuvtId })
                    let loading3 = this.layDsIp(this.prefix)
                    await loading1
                    await loading2
                    await loading3
                } catch (err) {
                    
                } finally {
                    this.loading(false)
                }
            },
            onSelectRowPrefixIp (rowData) {

                this.tablePrefix.selectedData = rowData

                if (!this.tablePrefix.selectedData) {
                    return
                }

                this.hienThiThongTinPrefix()
                this.setButton(1)
            }
        },
        async mounted () {
            this.$refs['prefix-input'].focus()

            try {
                this.loading(true)
                let loading1 = this.layDsBras()
                let loading2 = this.layDsLoaiIp()
                let loading3 = this.layDsDichVuVt()
                await loading1
                await loading2
                await loading3

                if (this.dsLoaiIp && this.dsLoaiIp.length > 0) {
                    this.loaiIpId = this.dsLoaiIp[0].LOAI_ID
                }

                if (this.dsDichVuVt && this.dsDichVuVt.length > 0) {
                    this.dichvuvtId = this.dsDichVuVt[0].DICHVUVT_ID
                    await this.layDsLoaiTbChuaGan({ prefix: '', dichvuvt_id: this.dichvuvtId })
                }
            } catch (err) {
                
            } finally {
                this.loading(false)
            }

            this.hienThiDsPrefix()
        },
        watch: {
            dsLoaiTbChuaGan (newVal) {
                this.tableDsLoaiTbChuaGan.checked = []
            },
            dsLoaiTbDaGan (newVal) {
                this.tableDsLoaiTbDaGan.checked = []
            },
            checkAllLoaiHinhDaGan(newVal) {

                if (newVal) {
                    this.tableDsLoaiTbDaGan.checked = this.dsLoaiTbDaGan.map(item => item.LOAITB_ID)
                    return
                } 
                
                if (this.tableDsLoaiTbDaGan.checked.length > 0
                    && this.tableDsLoaiTbDaGan.checked.length == this.dsLoaiTbDaGan.length) {
                    this.tableDsLoaiTbDaGan.checked = []
                }
            },
            'tableDsLoaiTbDaGan.checked': function(newVal) {
                this.checkAllLoaiHinhDaGan = newVal.length > 0 && newVal.length == this.dsLoaiTbDaGan.length
            },
            checkAllLoaiHinhChuaGan(newVal) {

                if (newVal) {
                    this.tableDsLoaiTbChuaGan.checked = this.dsLoaiTbChuaGan.map(item => item.LOAITB_ID)
                    return
                } 
                
                if (this.tableDsLoaiTbChuaGan.checked.length > 0
                    && this.tableDsLoaiTbChuaGan.checked.length == this.dsLoaiTbChuaGan.length) {
                    this.tableDsLoaiTbChuaGan.checked = []
                }
            },
            'tableDsLoaiTbChuaGan.checked': function(newVal) {
                this.checkAllLoaiHinhChuaGan = newVal.length > 0 && newVal.length == this.dsLoaiTbChuaGan.length
            }
        },
        validations: {
            brasId: {
                required
            },
            prefix: {
                required
            },
            loaiIpId: {
                required
            },
            tuso: {
                khongLonHonDenSo: (tuso, vm) => tuso <= vm.denso
            }
        },
        data () {
            return {
                tableDsLoaiTbChuaGan: { 
                    checked: []
                },
                tableDsLoaiTbDaGan: { 
                    checked: []
                },
                brasId: [],
                loaiIpId: null,
                prefix: '',
                tuso: 0,
                denso: 0,
                dichvuvtId: null,
                buttonState: {
                    btnNhapMoiEnabled: true,
                    btnCapNhatEnabled: true,
                    btnXoaEnabled: true,
                    btnHuyBoEnabled: true,
                    btnSinhIPEnabled: true
                },
                xacNhanXoa: false,
                dsPrefixIp: [],
                dsLoaiTbDaGan: [],
                dsLoaiTbChuaGan: [],
                dsIp: [],
                dsBras: [],
                dsLoaiIp: [],
                dsDichVuVt: [],
                tablePrefix: {
                    selectedData: null
                },
                checkAllLoaiHinhDaGan: false,
                checkAllLoaiHinhChuaGan: false,
                oldSelectedRowIndexPrefix: 0
            }
        },
    }
</script>