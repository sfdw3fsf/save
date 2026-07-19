<template src="./index.template.html"></template>
<script>
import newTabItem from './tab-item/index.js'
import ComboboxGridNhanVien from '../ComboboxGridNhanVien'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'

let getDateElements = (date) => {

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
}

let yyyymmddhhmiss = (date) => {

    if (!date) {
        return ''
    }
    
    date = getDateElements(date)
    return `${date.y}-${date.M}-${date.d} ${date.h}:${date.m}:${date.s}`
}

export default {
    components: {
        ComboboxGridNhanVien, BssErrorMarker, BssRequiredMarker
    },
    props: {
        modalId: String
    },
    validations: {
        nhanVienTh: {
            required
        },
        ngayTh: {
            required,
            khongLonHonNgayHienTai: (ngayTh, vm) => ngayTh <= new Date()
        },
    },
    data() {
        return {
            isDown: false,
            isOver: false,
            pos: { 
                left: 0,
                x: 0,
            },
            tabs: null,
            selectedTabId: null,
            items: [],
            ngayTh: null,
            nhanVienTh: null,
            ghiChuTh: null,
            nhomScId: null,
            nhomScText: null,
            suCoId: null,
            khoaPhieu: true,
            nguonScId: null,
            dsTinhTrang: [],
            dsNhomNn: [],
        }
    },
    computed: {
        title() {
            let title = this.khoaPhieu ? 'Khóa phiếu sự cố' : 'Nghiệm thu sự cố'
            let nhomScText = !this.nhomScText ? '' : this.nhomScText.trim()
            return `${title} ${nhomScText}`
        },
        btnKhoaPhieuText() {
            return this.khoaPhieu ? 'Khóa phiếu' : 'Nghiệm thu'
        }
    },
    methods: {
        onModalHidden() {
            this.isDown = false
            this.isOver = false
            this.pos = { left: 0, x: 0 }
            this.tabs = null
            this.items = []
            this.ngayTh = null
            this.nhanVienTh = null
            this.ghiChuTh = null
            this.nhomScId = null
            this.suCoId = null
            this.khoaPhieu = true
            this.nguonScId = null
            this.nhomScText = null
            this.dsTinhTrang = []
            this.dsNhomNn = []
            this.$v.$reset()
        },
        async onModalShown() {
            this.ngayTh = new Date()
            try {
                this.loading(true)
                let loading1 = this.layDsNhomNn()
                let loading2 = this.layDsTinhTrangMvt(this.suCoId)
                await loading1
                await loading2
            } catch (err) {

            } finally {
                this.loading(false)
            }

            this.tabs = this.$refs['ref-tabs']

            if (this.dsTinhTrang.length == 0) {
                this.addTab(null)
                return
            }

            this.dsTinhTrang.forEach(item => {
                this.addTab({
                    loaiTbiId: item.LOAIPTM_ID,
                    thietBiId: item.PTM_ID,
                    tenLoaiTbi: item.LOAI_PTM,
                    tenTbi: item.TEN_PTM,
                    congSoi: item.CONGSOI,
                    ghiChu: item.GHICHU,
                    nguonScId: this.nguonScId
                })
            })
        },
        onClickBtnDongBo() {
            
            if (this.items.length <= 1) {
                return
            }

            let selectedTabId = this.selectedTabId
            let selectedTab = this.$refs[selectedTabId][0]
            let data = selectedTab.getData()
            let dsNguyenNhan = selectedTab.dsNguyenNhan

            for (let i = 0; i < this.items.length; i++) {

                let item = this.items[i]

                if (item.args.id == selectedTabId) {
                    continue
                }

                let component = this.$refs[item.args.id][0]

                try {
                    component.data.ghiChu = data.ghiChu
                    component.allowReactNguyenNhan = false
                    component.data.nhomNnId = data.nhomNnId
                    component.dsNguyenNhan = [...dsNguyenNhan]
                    component.data.dsNguyenNhanId = [...data.dsNguyenNhanId]
                } catch (err) {
                    this.$root.toastError('Đồng bộ nguyên nhân thất bại')
                    return
                }
            }

            this.$root.toastSuccess('Đồng bộ nguyên nhân thành công')
        },
        async okKhoaPhieu() {

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")

            if (this.nhomScId == 1) {

                let tabComponents = this.items.map(item => this.$refs[item.args.id][0])

                tabComponents.forEach(item => {
                    let data = item.getData()
                    let node = doc.createElement("node")
                    node.setAttribute("loaiptm_id", data.thietBi.loaiTbiId)
                    node.setAttribute("ptm_id", data.thietBi.thietBiId)
                    node.setAttribute("ds_nguyennhan", data.dsNguyenNhanId.join())
                    node.setAttribute("congsoi", data.congSoi)
                    node.setAttribute("ghichu", data.ghiChu)
                    root.appendChild(node)
                })
            }

            doc.appendChild(root)

            let dsNguyenNhan = (new XMLSerializer()).serializeToString(doc)

            let data = {
                suco_id: this.suCoId,
                ngay_th: yyyymmddhhmiss(this.ngayTh), // yyyymmddhhmiss(new Date()),
                nhanvien_th_id: this.nhanVienTh,
                ds_nguyennhan: dsNguyenNhan,
                ghichu_th: this.ghiChuTh,
                khoaphieu: this.khoaPhieu ? 1 : 0,
            }

            try {
                this.loading(true)
                await this.khoaPhieuSuCo(data)
                this.$root.toastSuccess(`${this.btnKhoaPhieuText} thành công`)
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$emit('success')
            this.$bvModal.hide(this.modalId)
        },
        onClickBtnKhoaPhieu() {

            // if (this.nhomScId != 1) {
            //     this.$root.toastError(`Nhóm sự cố ${this.nhomScText} chưa được hỗ trợ`)
            //     return
            // }

            let errorMessage = []

            this.$v.$touch()

            if (this.$v.$invalid) {

                if (!this.$v.nhanVienTh.required) {
                    errorMessage.push("Hãy chọn nhân viên thực hiện")
                }

                if (!this.$v.ngayTh.required) {
                    errorMessage.push("Hãy chọn ngày thực hiện")
                }

                if (!this.$v.ngayTh.khongLonHonNgayHienTai) {
                    errorMessage.push("Ngày thực hiện không được lớn hơn ngày hiện tại")
                }
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            if (this.nhomScId == 1) {

                let tabComponents = this.items.map(item => this.$refs[item.args.id][0])
                let dsThietBiChon = []

                for (let i = 0; i < tabComponents.length; i++) {

                    let component = tabComponents[i]

                    try {
                        component.validateInput()

                        let data = component.getData()
                        let thietBi = `${data.thietBi.loaiTbiId}_${data.thietBi.thietBiId}`

                        if (dsThietBiChon.indexOf(thietBi) >= 0) {
                            throw `Thiết bị ${data.thietBi.tenLoaiTbi} ${data.thietBi.tenTbi} đang được khai báo nhiều lần, chỉ được nhập mỗi thiết bị trên 1 tab duy nhất`
                        }

                        dsThietBiChon.push(thietBi)
                    } catch (error) {
                        this.$root.toastError(error)
                        this.selectedTabId = component.id

                        if (this.isOver) {
                            this.tabs.scrollLeft = this.$refs[`tab-${component.id}`][0].offsetLeft
                        }
                        
                        return
                    }
                }
            }

            this.$refs['ref-modal-xacnhan-khoa-phieu'].show()
        },
        setNhomScId(nhomScId) {
            this.nhomScId = nhomScId
        },
        setNhomScText(nhomScText) {
            this.nhomScText = nhomScText
        },
        setSuCoId(suCoId) {
            this.suCoId = suCoId
        },
        setKhoaPhieu(khoaPhieu) {
            this.khoaPhieu = khoaPhieu
        },
        setNguonScId(nguonScId) {
            this.nguonScId = nguonScId
        },
        onSelectThietBi(dsThietBi) {

            let dsThietBiDaChon = [] 
            
            if (!!this.items && this.items.length > 0) {
                let tabComponents = this.items.map(item => this.$refs[item.args.id][0])
                dsThietBiDaChon = tabComponents.map(item => item.getData().thietBi)
            }

            let dsThietBiChuaChon = dsThietBi.filter(item => {
                return !dsThietBiDaChon.some(element => {
                    return element.loaiTbiId == item.LOAITBI_ID && element.thietBiId == item.THIETBI_ID
                })
            })
            
            dsThietBiChuaChon.forEach(item => {

                let emptyTab = this.getEmptyTab()

                if (!emptyTab) {
                    this.addTab({
                        loaiTbiId: item.LOAITBI_ID,
                        thietBiId: item.THIETBI_ID,
                        tenLoaiTbi: item.LOAI_TBI,
                        tenTbi: item.TEN_TBI,
                    })
                    return
                }

                let component = this.$refs[emptyTab.args.id][0]
                component.data.thietBi.loaiTbiId = item.LOAITBI_ID
                component.data.thietBi.thietBiId = item.THIETBI_ID
                component.data.thietBi.tenLoaiTbi = item.LOAI_TBI
                component.data.thietBi.tenTbi = item.TEN_TBI
                emptyTab.args.name = `${item.LOAI_TBI} ${item.TEN_TBI}`
            })
        },
        getEmptyTab() {
            for (let i = 0; i < this.items.length; i++) {

                let item = this.items[i]
                let component = this.$refs[item.args.id]

                if (!component || component.length == 0) {
                    continue
                }

                component = component[0]

                if (!(component.getData().thietBi.thietBiId)) {
                    return item
                }
            }

            return null
        },
        onMousedown(e) {
            this.isDown = true
            this.pos = {
                left: this.tabs.scrollLeft, // the current scroll 
                x: e.clientX,               // the current mouse position
            }
        },
        onMouseleave(e) {
            this.isDown = false
        },
        onMouseup(e) {
            this.isDown = false
        },
        onMousemove(e) {
            if(!this.isDown) { 
                return 
            }
            e.preventDefault()
            const dx = e.clientX - this.pos.x           // how far the mouse has been moved
            this.tabs.scrollLeft = this.pos.left - dx   // scroll the element
        },
        onClickAddTab() {
            this.addTab(null)
        },
        addTab(data) {

            let index = 1

            if (this.items.length > 0) {
                let lastItem = this.items[this.items.length - 1]
                index = lastItem.args.index + 1
            }

            this.selectedTabId = `item${index}`

            let args = { 
                id: this.selectedTabId, 
                index: index, 
                data: data 
            }

            if (!!data && !!data.tenTbi) {
                args.name = `${data.tenLoaiTbi} ${data.tenTbi}`
            }

            this.items.push(newTabItem(args, this))

            setTimeout(() => {
                this.tabs.scrollLeft = this.tabs.scrollWidth
                this.isOver = this.tabs.scrollWidth > this.tabs.clientWidth
            }, 50)
        },
        onClickTab(tabItem, e) {

            e.preventDefault()

            if (e.target.tagName.toLowerCase() == 'span') {

                if (this.items.length == 1) {
                    return
                }

                let items = []
                let selectedTabId = this.selectedTabId

                for (let i = 0; i < this.items.length; i++) {

                    let item = this.items[i]

                    if (item.args.id != tabItem.args.id) {
                        items.push(item)
                        continue
                    }

                    if (item.args.id == this.selectedTabId) {
                        let selectedTab = i == 0 ? this.items[1] : this.items[i - 1]
                        selectedTabId = selectedTab.args.id
                    }
                }
                
                this.selectedTabId = selectedTabId
                this.items = items

                setTimeout(() => {
                    this.isOver = this.tabs.scrollWidth > this.tabs.clientWidth
                }, 50)

                return
            }

            this.selectedTabId = tabItem.args.id
        },
        async layDsTinhTrangMvt(suCoId) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/layDsTinhTrangMVT`, {id: suCoId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTinhTrang = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsTinhTrang = []
            }
        },
        async layDsNhomNn() {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-nhom-nn`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhomNn = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsNhomNn = []
            }
        },
        async khoaPhieuSuCo(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/qlsc/nghiem-thu/khoa-phieu`, data)
                
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
    },
}
</script>
<style>
    .khoa-phieu .tabs a span.remove-item {
        display: none;
    }

    .khoa-phieu .tabs a:hover span.remove-item {
        display: inline;
    }
</style>