<template src="./index.html"></template>
<style>
    .select-bras .vue-treeselect__control {
        height: 32px !important;
    }
</style>
<script>
    import { actionName, statePropertyName } from '../../store/QuanLyPrefixIPCuaBras'
    import { mapActions, mapState } from 'vuex'
    import BssLocalPagination1 from '@/components/BssLocalPagination_1'

    export default {
        name: 'QuanLyPrefixIPCuaBras',
        components: {BssLocalPagination1},
        computed: {
            ...mapState('ecms/quanLyPrefixIPCuaBras', statePropertyName),
            treeSelectOptions () {
                return this.dsBras.map(item => { return { id: item.BRAS_ID, label: item.TENBRAS }})
            }
        },
        methods: {
            ...mapActions('ecms/quanLyPrefixIPCuaBras', actionName),
            onChangeComboDichvuvtId: async function (event) {
                try {
                    this.loading(true)
                    await this.layDsLoaiTbChuaGan({ prefix: this.tableDsPrefixIp.selected, dichvuvt_id: event.target.value })
                } catch (err) {
                    console.log(err)
                } finally {
                    this.loading(false)
                }
            },
            onClickDongCuaBangPrefix (prefix, rowIndex) {
                this.tableDsPrefixIp.selected = prefix
                this.tableDsPrefixIp.selectedRowIndex = rowIndex
                this.hienThiThongTinPrefix()
                this.setButton(1)
            },
            onClickDongCuaBangDsLoaiTbChuaGan (loaiTbId, rowIndex) {
                this.tableDsLoaiTbChuaGan.selected = loaiTbId
                this.tableDsLoaiTbChuaGan.selectedRowIndex = rowIndex
            },
            onClickDongCuaBangDsLoaiTbDaGan (loaiTbId, rowIndex) {
                this.tableDsLoaiTbDaGan.selected = loaiTbId
                this.tableDsLoaiTbDaGan.selectedRowIndex = rowIndex
            },
            onClickDongCuaBangDsIp (ip, rowIndex) {
                this.tableDsIp.selected = ip
                this.tableDsIp.selectedRowIndex = rowIndex
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
                
                this.clear();
                this.tableDsLoaiTbChuaGan.searchData.LOAIHINH_TB = ''
                this.tableDsLoaiTbChuaGan.searchData.CHECKED = false
                this.tableDsLoaiTbDaGan.searchData.LOAIHINH_TB = ''
                this.tableDsLoaiTbDaGan.searchData.CHECKED = false
                this.setButton(0);
            },
            onClickButtonHuyBo () {
                this.hienThiDsPrefix()
            },
            async onClickButtonCapNhat () {
                let data = {
                    bras_id: this.brasId.join(','),
                    prefix: this.prefix,
                    tuso: this.tuso + '',
                    denso: this.denso + '',
                    loai_id: this.loaiIpId + '',
                    ds_loaitb_id: this.dsLoaiTbDaGan.map(item => item.LOAITB_ID).join()
                }

                let errorMessage = []

                if (!data.tuso.trim() || this.tuso < 0) {
                    errorMessage.push('Bạn chưa nhập từ số')
                }

                if (!data.denso.trim() || this.denso < 0) {
                    errorMessage.push('Bạn chưa nhập đến số')
                }

                if (this.tuso > this.denso) {
                    errorMessage.push('Từ số không được lớn hơn Đến số')
                }

                if (!data.bras_id.trim()) {
                    errorMessage.push("Bạn chưa chọn bras")
                }

                if (!data.loai_id.trim()) {
                    errorMessage.push("Bạn chưa chọn loại IP");
                }

                if (!data.prefix.trim()) {
                    errorMessage.push("Bạn chưa nhập prefix");
                }

                if (!data.ds_loaitb_id.trim()) {
                    errorMessage.push("Bạn chưa nhập loại hình thuê bao");
                }

                if (errorMessage.length > 0) {
                    this.$toast.error(errorMessage.join('\n'))
                    return
                }

                try {
                    this.loading(true)
                    let result = null

                    if (!this.buttonState.btnNhapMoiEnabled) {
                        result = await this.themMoi(data)
                    } else {
                        result = await this.capNhat(data)
                    }

                    this.$toast.success(result)
                } catch (err) {
                    this.$toast.error(err)
                    return
                } finally {
                    this.loading(false)
                }
                
                this.hienThiDsPrefix()
            },
            async onClickButtonXoa () {
                if (!this.buttonState.btnXoaEnabled) {
                    return
                }

                try {
                    this.loading(true)
                    await this.kiemTraPrefix(this.tableDsPrefixIp.selected)
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
                    await this.xoaPrefix(this.tableDsPrefixIp.selected)
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
                    await this.sinhIp({prefix: this.tableDsPrefixIp.selected})
                    this.$toast.success('Cập nhật thành công')
                    await this.layDsIp(this.tableDsPrefixIp.selected)
                } catch (err) {
                    this.$toast.error(err)
                } finally {
                    this.loading(false)
                }
            },
            onPageChangeDsPrefixIp (data) {

                this.tableDsPrefixIp.data = data.pageData

                if (data.pageData && data.pageData.length > 0) {
                    this.tableDsPrefixIp.selected = data.selectedRowData.PREFIX
                    this.hienThiThongTinPrefix()
                    this.setButton(1)
                }
            },
            onPageChangeDsLoaiTbChuaGan (data) {
                this.tableDsLoaiTbChuaGan.data = data.pageData

                if (data.pageData && data.pageData.length > 0) {
                    this.tableDsLoaiTbChuaGan.selected = data.selectedRowData.LOAITB_ID
                }
            },
            onPageChangeDsLoaiTbDaGan (data) {
                this.tableDsLoaiTbDaGan.data = data.pageData

                if (data.pageData && data.pageData.length > 0) {
                    this.tableDsLoaiTbDaGan.selected = data.selectedRowData.LOAITB_ID
                }
            },
            onPageChangeDsIp (data) {
                this.tableDsIp.data = data.pageData

                if (data.pageData && data.pageData.length > 0) {
                    this.tableDsIp.selected = data.selectedRowData.IP
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
            },
            async hienThiThongTinPrefix () {
                let prefixInfo = this.tableDsPrefixIp.data.filter(item => item.PREFIX === this.tableDsPrefixIp.selected)[0]
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
            onFilterLoaiHinhChuaGan () {

                let loaiHinhTb = this.tableDsLoaiTbChuaGan.searchData.LOAIHINH_TB
                let checked = this.tableDsLoaiTbChuaGan.searchData.CHECKED

                if (!loaiHinhTb) {
                    loaiHinhTb = ''
                }

                loaiHinhTb = loaiHinhTb.trim()

                this.pagerDsLoaiTbChuaGan = this.dsLoaiTbChuaGan.filter(item => {
                        return item.LOAIHINH_TB.toLowerCase().includes(loaiHinhTb.toLowerCase())
                                && (!checked || this.tableDsLoaiTbChuaGan.checked.indexOf(item.LOAITB_ID) >= 0)
                    })
            },
            onFilterLoaiHinhDaGan () {

                let loaiHinhTb = this.tableDsLoaiTbDaGan.searchData.LOAIHINH_TB
                let checked = this.tableDsLoaiTbDaGan.searchData.CHECKED

                if (!loaiHinhTb) {
                    loaiHinhTb = ''
                }

                loaiHinhTb = loaiHinhTb.trim()

                this.pagerDsLoaiTbDaGan = this.dsLoaiTbDaGan.filter(item => {
                        return item.LOAIHINH_TB.toLowerCase().includes(loaiHinhTb.toLowerCase())
                                && (!checked || this.tableDsLoaiTbDaGan.checked.indexOf(item.LOAITB_ID) >= 0)
                    })
            },
            onFilterDsPrefix () {

                let tenBras = this.tableDsPrefixIp.searchData.TENBRAS
                let prefix = this.tableDsPrefixIp.searchData.PREFIX
                let tuSo = this.tableDsPrefixIp.searchData.TUSO
                let denSo = this.tableDsPrefixIp.searchData.DENSO
                let loai = this.tableDsPrefixIp.searchData.LOAI

                if (!tenBras) {
                    tenBras = ''
                }

                if (!prefix) {
                    prefix = ''
                }

                if (!tuSo) {
                    tuSo = ''
                }

                if (!denSo) {
                    denSo = ''
                }

                if (!loai) {
                    loai = ''
                }

                tenBras = tenBras.trim()
                prefix = prefix.trim()
                tuSo = tuSo.trim()
                denSo = denSo.trim()
                denSo = denSo.trim()

                this.pagerDsPrefixIp = this.dsPrefixIp.filter(item => {
                        return (!item.TENBRAS ? '' : item.TENBRAS).toLowerCase().includes(tenBras.toLowerCase())
                                && item.PREFIX.toLowerCase().includes(prefix.toLowerCase())
                                && (item.TUSO + '').includes(tuSo.toLowerCase())
                                && (item.DENSO + '').includes(denSo.toLowerCase())
                                && item.LOAI.toLowerCase().includes(loai.toLowerCase())
                    })
            },
            onFilterDsIp () {
                let ip = this.tableDsIp.searchData.IP
                let trangThai = this.tableDsIp.searchData.TEN_TTSO

                if (!ip) {
                    ip = ''
                }

                if (!trangThai) {
                    trangThai = ''
                }

                ip = ip.trim()
                trangThai = trangThai.trim()

                this.pagerDsIp = this.dsIp.filter(item => {
                        return item.IP.toLowerCase().includes(ip.toLowerCase())
                                && item.TEN_TTSO.toLowerCase().includes(trangThai.toLowerCase())
                    })
            }
        },
        async mounted () {
            this.$store.dispatch('setHeader', {
                title: 'Cập nhật prefix cho bras',
                list: [
                    { 
                        name: 'Lập hợp đồng',
                        link: { name: 'Ui.cards' } },
                    {
                        name: 'Lắp đặt mới',
                        link: { name: 'Ui.buttons' }
                    }
                ]
            })
            try {
                this.loading(true)
                let loading1 = this.layDsBras()
                let loading2 = this.layDsLoaiIp()
                let loading3 = this.layDsDichVuVt()
                await loading1
                await loading2
                await loading3

                if (this.dsBras && this.dsBras.length > 0) {
                    this.brasId = !this.dsBras[0].BRAS_ID ? [] : (this.dsBras[0].BRAS_ID + '').split(',').map(item => parseInt(item))
                }

                if (this.dsLoaiIp && this.dsLoaiIp.length > 0) {
                    this.loaiIpId = this.dsLoaiIp[0].LOAI_ID
                }

                if (this.dsDichVuVt && this.dsDichVuVt.length > 0) {
                    this.dichvuvtId = this.dsDichVuVt[0].DICHVUVT_ID
                }
            } catch (err) {
                console.log(err)
            } finally {
                this.loading(false)
            }

            this.hienThiDsPrefix()
        },
        watch: {
            dsPrefixIp (newVal) {
                this.tableDsPrefixIp.searchData.TENBRAS = ''
                this.tableDsPrefixIp.searchData.PREFIX = ''
                this.tableDsPrefixIp.searchData.TUSO = ''
                this.tableDsPrefixIp.searchData.DENSO = ''
                this.tableDsPrefixIp.searchData.LOAI = ''
                this.pagerDsPrefixIp = [...newVal]
            },
            dsLoaiTbChuaGan (newVal) {
                this.tableDsLoaiTbChuaGan.checked = []
                this.tableDsLoaiTbChuaGan.searchData.LOAIHINH_TB = ''
                this.tableDsLoaiTbChuaGan.searchData.CHECKED = false
                this.pagerDsLoaiTbChuaGan = [...newVal]
            },
            dsLoaiTbDaGan (newVal) {
                this.tableDsLoaiTbDaGan.checked = []
                this.tableDsLoaiTbDaGan.searchData.LOAIHINH_TB = ''
                this.tableDsLoaiTbDaGan.searchData.CHECKED = false
                this.pagerDsLoaiTbDaGan = [...newVal]
            },
            dsIp (newVal) {
                this.tableDsIp.searchData.IP = ''
                this.tableDsIp.searchData.TEN_TTSO = ''
                this.pagerDsIp = [...newVal]
            }
        },
        data () {
            return {
                tableDsPrefixIp: { 
                    data: [], 
                    selected: 0, 
                    selectedRowIndex: 0,
                    searchData: {
                        TENBRAS: '',
                        PREFIX: '',
                        TUSO: '',
                        DENSO: '',
                        LOAI: ''
                    }
                },
                tableDsLoaiTbChuaGan: { 
                    data: [], 
                    selected: 0, 
                    checked: [], 
                    selectedRowIndex: 0,
                    searchData: {
                        LOAIHINH_TB: '',
                        CHECKED: false
                    }
                },
                tableDsLoaiTbDaGan: { 
                    data: [], 
                    selected: 0, 
                    checked: [], 
                    selectedRowIndex: 0,
                    searchData: {
                        LOAIHINH_TB: '',
                        CHECKED: false
                    } 
                },
                tableDsIp: { 
                    data: [], 
                    selected: 0, 
                    selectedRowIndex: 0,
                    searchData: {
                        IP: '',
                        TEN_TTSO: ''
                    } 
                },
                pagerDsPrefixIp: [],
                pagerDsLoaiTbChuaGan: [],
                pagerDsLoaiTbDaGan: [],
                pagerDsIp: [],
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
                xacNhanXoa: false
            }
        },
        created () {
            this.clearState()
            this.createApiManager()
        },
        destroyed () {
            this.clearState()
        }
    }
</script>