<template src="./index.template.html"></template>
<script>
import ComboboxGridNhanVien from '../ComboboxGridNhanVien'
import ModalCongSoiBiSuCo from '../khoa-phieu/chon-congsoi/index.vue'
import ModalXemThongTin from './modal-xem-thongtin/index.vue'
import ModalTraPhieu from './modal-traphieu/index.vue'
import ModalBaoTon from './modal-baoton/index.vue'
import ModalInPhieu from './modal-in-phieu/index.vue'
import ModalGiaoPhieuNv from './modal-giaophieu-nv/index.vue'
import XLSX from "xlsx"
//import sendSms from '../send-sms'

var toanTu = {
    '>=': '≥',
    '>': '>',
    '=': '=',
    '<=': '≤',
    '<': '<'
}

var layGiaTriToanTu = value => {
    switch (value) {
        case toanTu['<=']:
            return -2;
        case toanTu['<']:
            return -1;
        case toanTu['=']:
            return 0;
        case toanTu['>']:
            return 1;
        case toanTu['>=']:
            return 2;
        default:
            return ''
    }
}

export default {
    components: {
        ComboboxGridNhanVien,
        ModalCongSoiBiSuCo,
        ModalXemThongTin,
        ModalTraPhieu,
        ModalBaoTon,
        ModalInPhieu,
        ModalGiaoPhieuNv
    },
    data() {
        return {
            dsNhomSuCo: [],
            nhomSuCoId: null,
            chkMaSuCo: false,
            chkNguoiBao: false,
            chkMdKhanCap: false,
            chkNguonSuCo: false,
            chkTinhTrang: false,
            chkNoiDungSuCo: false,
            chkNoiDungGiao: false,
            chkNgaySuCo: false,
            chkNgayBao: false,
            chkNgayGiao: false,
            chkMucDoSuCo: false,
            chkKeoDaiTu: false,
            chkKeoDaiDen: false,
            nguoiBaoId: null,
            dsMucDoKhanCap: [],
            mucDoKhanCapId: null,
            dsNguonSuCo: [],
            nguonSuCoId: null,
            dsTinhTrang: [],
            tinhTrangId: null,
            dsMucDoSuCo: [],
            mucDoSuCoId: null,
            ngaySuCoTu: null,
            ngaySuCoDen: null,
            ngayBaoTu: new Date(),
            ngayBaoDen: new Date(),
            ngayGiaoTu: null,
            ngayGiaoDen: null,
            keoDaiTuH: 0,
            keoDaiTuM: 0,
            keoDaiTuS: 0,
            keoDaiDenH: 0,
            keoDaiDenM: 0,
            keoDaiDenS: 0,
            toanTuKeoDaiTu: toanTu['>='],
            toanTuKeoDaiDen: toanTu['<='],
            showKeoDaiDen: true,
            noiDungSuCo: null,
            noiDungGiao: null,
            dsPhieuXuLy: [],
            maSuCo: null,
            dsPhanTuMang: [],
            dsGiaoViec: [],
            enableBtnGhiChuXL: false,
            enableBtnGiaoViec: false,
            enableBtnXuatPhieu: false,
            enableBtnDoKiem: false,
            enableBtnHoanCong: false,
            enableBtnBaoTon: false,
            enableBtnTraPhieu: false,
            allowShowFilterGiaoViec: false,
            allowShowFilterPtm: false,
            allowShowFilterPhieuXuLy: true,
            idPhieuDuocChon: null,
            phanTuMangDuocChon: null,
            ghiChu: null,
            kieuXuatPhieu: 0,
            ghiChuHoanCong: null,
            showTttk: true,
            expandDsPhieu: false,
            expandDsPtm: false,
            expandDsGiaoViec: false,
            showBottomPanels: true,
            ghiChuPhanTuMang: null,
            phanTuMangCapNhat: null,
            ghiChuXuLyNv: null,
            giaoViecCapNhat: null,
            showImg: false,
            phieuImage: null,
            zoomImg: null,
            showZoomImg: false,
            deletedImgs: []
        }
    },
    watch: {
        phieuImage() {
            this.deletedFiles = []
        },
        dsGiaoViec(newVal) {
            newVal.forEach(item => item.NGAYGIAO = this.ddmmyyyyhhmi(item.NGAYGIAO))
        },
        dsPhanTuMang(newVal) {
            newVal.forEach(item => item.NGAY_CN = this.ddmmyyyy(item.NGAY_CN))
        },
        dsPhieuXuLy(newVal) {
            this.phieuImage = null
            this.idPhieuDuocChon = null
        },
        async idPhieuDuocChon(newVal) {

            this.enableBtnGhiChuXL = !!newVal
            this.enableBtnGiaoViec = !!newVal
            this.enableBtnXuatPhieu = !!newVal
            this.enableBtnDoKiem = !!newVal
            this.enableBtnHoanCong = !!newVal
            this.enableBtnBaoTon = !!newVal
            this.enableBtnTraPhieu = !!newVal

            if (!newVal) {
                this.dsPhanTuMang = []
                this.dsGiaoViec = []
                return
            }

            try {
                this.loading(true)
                await this.layDsPhanTuMang(newVal)
            } catch (err) {

            } finally {
                if (this.dsPhanTuMang.length == 0) {
                    this.loading(false)
                }
            }
        },
        async phanTuMangDuocChon(newVal) {

            if (!newVal) {
                this.dsGiaoViec = []
                return
            }

            try {
                this.loading(true)
                await this.layDsGiaoViecNhanVien(this.idPhieuDuocChon, newVal.SUCO_PTM_ID)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        }
    },
    computed: {
        keoDaiTu() {
            return this.keoDaiTuH * 60 * 60 + this.keoDaiTuM * 60 + this.keoDaiTuS
        },
        keoDaiDen() {
            return this.keoDaiDenH * 60 * 60 + this.keoDaiDenM * 60 + this.keoDaiDenS
        },
        phieuDuocChon() {
            return !this.idPhieuDuocChon ? null : this.dsPhieuXuLy.filter(item => item.PHIEU_ID == this.idPhieuDuocChon)[0]
        }
    },
    async mounted() {
        try {
            this.loading(true)
            let loading1 = this.layDmNhomSuCo()
            let loading2 = this.layDmMucDoKhanCap()
            let loading3 = this.layDmNguonSuCo()
            let loading4 = this.layDmTinhTrang()
            let loading5 = this.layDmMucDoSuCo()
            await loading1
            await loading2
            await loading3
            await loading4
            await loading5
        } catch (err) {

        } finally {
            this.loading(false)
        }

        if (this.dsNhomSuCo && this.dsNhomSuCo.length > 0) {
            this.nhomSuCoId = this.dsNhomSuCo[0].NHOMSC_ID
            this.thucHienTraCuu()
        }
    },
    methods: {
        getColumnTemplateAnhSuCo(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div style="position: relative;">
                                <button ref="ref-btn-show-img" class="btn btn-sm btn-second" @click="parent.showPanelImg(data)">
                                    <span>...</span>
                                </button>
                                <button class="btn btn-sm btn-second" @click="parent.onClickBtnCapNhatAnhChup(data)">
                                    <span>✓</span>
                                </button>
                                <div ref="ref-panel-anh-suco" class="panel-anh-suco" v-show="showPanelAnhSuCo">
                                    <div style="position: absolute; height: 35px; text-align: left; top: 0; padding: 5px 0px">
                                        <span style="color: black;">Hình ảnh </span>
                                        <button type="button" class="btn btn-sm btn-second" @click="parent.chooseImg(data)">...</button>
                                        <button type="button" class="btn btn-sm btn-second" @click="parent.deleteImgs">x</button>
                                    </div>
                                    <button type="button" class="close" @click="parent.phieuImage = null" style="position: absolute; right: 4px; top: 0;">×</button>
                                    <div style="border: 1px solid #ccc; height: 100%; overflow: auto;">
                                        <div v-for="image in data.IMAGES" style="width: 50%; height: 100%; float: left; padding: 5px 5px 5px 25px; position: relative;">
                                            <div class="check-action" style="position: absolute !important; left: 5px; top: 65px">
                                                <input 
                                                    type="checkbox" 
                                                    class="check" 
                                                    v-model="parent.deletedImgs"
                                                    :value="image.relativeUrl"/>
                                                <span class="name"></span>
                                            </div>
                                            <img v-on:mouseover="parent.onMouseOver(image.presignedUrl)" v-on:mouseleave="parent.onMouseLeave" :src="image.presignedUrl" alt="" style="height: 100%; width: 100%;" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                            showPanelAnhSuCo() {
                                return !!this.parent.phieuImage && this.parent.phieuImage.PHIEU_ID == this.data.PHIEU_ID
                            }
                        },
                        watch: {
                            showPanelAnhSuCo(newVal) {
                                if (newVal) {
                                    let top = this.$refs['ref-btn-show-img'].getBoundingClientRect().top
                                    let height = this.$refs['ref-btn-show-img'].clientHeight
                                    this.$refs['ref-panel-anh-suco'].style.top = `${top + height + 3}px`
                                }
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateChucNangGiaoViec(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div>
                                <span title="Cập nhật ghi chú xử lý">
                                    <button class="btn btn-sm btn-second" @click="parent.onClickBtnCapNhatGhiChuXuLyNv(data)" :disabled="!data.ALLOW_ACTION">
                                        <span>✓</span>
                                    </button>
                                </span>
                                <span title="Hủy giao">
                                    <button class="btn btn-sm btn-second" @click="parent.onClickBtnHuyGiaoViecNv(data)" :disabled="!data.ALLOW_ACTION">
                                        <span>x</span>
                                    </button>
                                </span>
                                <span title="Gửi sms">
                                    <button class="btn btn-sm btn-second" @click="parent.onClickBtnSmsGiaoViecNv(data)" :disabled="!data.ALLOW_ACTION">
                                        <span>sms</span>
                                    </button>
                                </span>
                                <span title="In phiếu">
                                    <button class="btn btn-sm btn-second" @click="parent.onClickBtnInGiaoViecNv(data)">
                                        <span>&#x1F5B6;</span>
                                    </button>
                                </span>
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
        getColumnTemplateChucNangPtm(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div>
                                <span title="Cập nhật ghi chú xử lý">
                                    <button class="btn btn-sm btn-second" @click="parent.onClickBtnGhiChuPhanTuMang(data)">
                                        <span>✓</span>
                                    </button>
                                </span>
                                <span title="Chi tiết sự cố">
                                    <button class="btn btn-sm btn-second" @click="parent.onClickBtnCongSoiBiSuCo(data)">
                                        <span class="one-search"></span>
                                    </button>
                                </span>
                                <span title="Thông số kỹ thuật">
                                    <button class="btn btn-sm btn-second" @click="parent.onClickBtnThongSoPtm(data)">
                                        <span>...</span>
                                    </button>
                                </span>
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
        getColumnTemplateChonPhieuDieuXuLy(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div class="check-action">
                                <input 
                                    type="radio" 
                                    class="check"
                                    v-model="parent.idPhieuDuocChon"
                                    :value="data.PHIEU_ID"/>
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
        okXoaAnh() {
            this.phieuImage.IMAGES = this.phieuImage.IMAGES.filter(item => {
                return !this.deletedImgs.includes(item.relativeUrl)
            })
            this.phieuImage.MOTA = this.phieuImage.IMAGES.map(item => item.relativeUrl).join('|')
            this.$refs['ref-xacnhan-xoa-anh'].hide()
        },
        deleteImgs() {
            if (this.deletedImgs.length == 0) {
                this.$root.toastError('Chưa chọn ảnh để xóa')
                return
            }
            this.$refs['ref-xacnhan-xoa-anh'].show()
        },
        onMouseOver(src) {
            this.zoomImg = src
            this.showZoomImg = true
        },
        onMouseLeave() {
            this.zoomImg = null
            this.showZoomImg = false
        },
        async onImgChange() {
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
                    let path = await this.uploadFile(formData)
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

            this.phieuImage.IMAGES = this.phieuImage.IMAGES.concat(paths)
            this.phieuImage.MOTA = this.phieuImage.IMAGES.map(item => item.relativeUrl).join('|')
            this.$root.toastSuccess(`Đã upload được ${paths.length} files`)
        },
        chooseImg() {
            this.$refs['ref-choose-img'].$el.querySelector('input').click()
        },
        async showPanelImg(phieu) {
            this.phieuImage = phieu
            
            if (!phieu.MOTA || phieu.IMAGES.length > 0) {
                return
            }

            let relativeUrls = phieu.MOTA.split('|')
            let paths = []
            try {
                this.loading(true)
                for (let i = 0; i < relativeUrls.length; i++) {
                    let relativeUrl = relativeUrls[i]
                    let presignedUrl = await this.getFileUrl(relativeUrl)
                    paths.push({relativeUrl, presignedUrl})
                }
            } catch(err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.phieuImage.IMAGES = paths
        },
        async onClickBtnCapNhatAnhChup(phieu) {
            try {
                this.loading(true)
                await this.capNhatAnhChup({
                    phieu_id: phieu.PHIEU_ID,
                    mota: phieu.MOTA
                })
                this.$root.toastSuccess('Cập nhật ảnh chụp thành công !')
                this.phieuImage = null
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }
        },
        onClickBtnGiaoViec() {

            if (!this.enableBtnGiaoViec) {
                return
            }

            if (!this.phanTuMangDuocChon) {
                this.$root.toastError('Hãy chọn phần tử mạng bị sự cố cần xử lý')
                return
            }

            let modal = this.$refs['ref-modal-giaophieu-nv']
            modal.setPhieuId(this.phieuDuocChon.PHIEU_ID)
            modal.setSuCoId(this.phieuDuocChon.SUCO_ID)
            modal.setSuCoPtmId(this.phanTuMangDuocChon.SUCO_PTM_ID)
            modal.show()
        },
        onClickBtnExcelGiaoViec() {

            let data = this.dsGiaoViec

            if (!data || data.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            data.forEach(item => {
                exportData.push({
                    'Tên nhân viên': item.TEN_NV,
                    'Điện thoại liên hệ': item.SO_DT,
                    'Ngày giao': item.NGAYGIAO,
                    'Nhiệm vụ': item.NHIEMVU,
                    'Ghi chú xử lý': item.GHICHU_XL,
                    'Trạng thái thực hiện': item.TRANGTHAI,
                    'Nhắn tin': item.STATUS
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "ds_giaoviec.xlsx")
        },
        onClickBtnExcelPhanTuMang() {

            let data = this.dsPhanTuMang

            if (!data || data.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            data.forEach(item => {
                exportData.push({
                    'Loại PTM': item.LOAI_PTM,
                    'Tên phần tử mạng': item.TEN_PTM,
                    'Tình trạng': item.TINHTRANG,
                    'Ghi chú tình trạng': item.GHICHU_TT,
                    'Ghi chú hoàn công': item.GHICHU_HC,
                    'Ngày cập nhật': item.NGAY_CN,
                    'Người cập nhật': item.NGUOI_CN
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "ds_ptm.xlsx")
        },
        onClickBtnExcelPhieuXuLy() {

            let data = this.dsPhieuXuLy

            if (!data || data.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            data.forEach(item => {
                exportData.push({
                    'Mã sự cố': item.MA_SC,
                    'Mức độ': item.MUCDO_SC,
                    'Nhóm': item.NHOM_SC,
                    'Nguồn': item.NGUON_SC,
                    'Nội dung sự cố': item.NOIDUNG_SC,
                    'Ngày sự cố': item.NGAY_SC,
                    'Kéo dài': item.THOIGIAN,
                    'Trạng thái': item.TRANGTHAI_PH,
                    'Nội dung giao': item.NOIDUNG_GIAO,
                    'Đơn vị giao': item.DONVI_GIAO,
                    'Ngày giao': item.NGAYGIAO,
                    'Đơn vị nhận': item.DONVI_NHAN,
                    'Khẩn': item.MUCDO_KC,
                    'Chỉ tiêu thời gian': item.CHITIEU_TG,
                    'Còn lại': item.CONLAI,
                    'Ghi chú xử lý': item.GHICHU_XL
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "ds_phieu.xlsx")
        },
        onClickBtnBaoTon() {

            if (!this.enableBtnBaoTon) {
                return
            }

            if (!this.idPhieuDuocChon) {
                this.$root.toastError('Hãy chọn phiếu để báo tồn')
                return
            }

            this.$bvModal.show('modal-baoton')
        },
        onClickBtnTraPhieu() {

            if (!this.enableBtnTraPhieu) {
                return
            }

            if (!this.idPhieuDuocChon) {
                this.$root.toastError('Hãy chọn phiếu để trả')
                return
            }

            this.$bvModal.show('modal-traphieu')
        },
        async onClickBtnSmsGiaoViecNv(giaoViec) {
            try {
                this.loading(true)
                let smsItem = await this.layNdGuiSms(giaoViec.GIAOVIEC_ID);
                if(smsItem.Result != true){
                    this.$toast.error('Nhắn tin thất bại\n' + smsItem.Msg);
                }
                // let noiDungGuiSms = await this.layNdGuiSms(giaoViec.GIAOVIEC_ID)

                // if (!noiDungGuiSms) {
                //     throw 'Phiếu không tồn tại hoặc đã hoàn công không thể nhắn tin'
                // }

                // if (!noiDungGuiSms.SO_DT) {
                //     throw 'Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)'
                // }

                // try {
                //     await sendSms(this, noiDungGuiSms.SO_DT, noiDungGuiSms.ND_SMS)
                // } catch(err) {
                //     throw 'Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)'
                // }

                try {
                    await this.capNhatTrangThaiGuiSms(giaoViec.GIAOVIEC_ID)
                } catch(err) {
                }
            } catch(err) {
                this.$toast.error('Nhắn tin thất bại\n' + err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Nhắn tin thành công')

            this.thucHienLayDsGiaoViec()
        },
        async okHuyGiaoViecNv() {
            try {
                this.loading(true)
                let noiDungGuiSms = await this.huyGiaoViecNv(this.giaoViecCapNhat.GIAOVIEC_ID)

                // if (!!noiDungGuiSms.SO_DT && !!noiDungGuiSms.ND_SMS) {
                //     try {
                //         await sendSms(this, noiDungGuiSms.SO_DT, noiDungGuiSms.ND_SMS)
                //     } catch(err) { }
                // }

                this.$toast.success('Hủy giao thành công')
            } catch (err) {
                this.$toast.error('Hủy giao thất bại\n' + err)
                return
            } finally {
                this.loading(false)
            }

            this.$bvModal.hide('modal-xacnhan-huy-giaoviec-nv')
            this.thucHienLayDsGiaoViec()
        },
        onClickBtnHuyGiaoViecNv(giaoViec) {
            this.giaoViecCapNhat = giaoViec
            this.$bvModal.show('modal-xacnhan-huy-giaoviec-nv')
        },
        async okCapNhatGhiChuXuLyNv() {

            let data = {
                giaoviec_id: this.giaoViecCapNhat.GIAOVIEC_ID,
                ghichu: this.ghiChuXuLyNv
            }

            try {
                this.loading(true)
                await this.capNhatGhiChuXuLyNv(data)
                this.$toast.success('Cập nhật thành công')
            } catch (err) {
                this.$toast.error('Cập nhật thất bại\n' + err)
                return
            } finally {
                this.loading(false)
            }

            this.$bvModal.hide('modal-ghichu-xuly-nv')
            this.thucHienLayDsGiaoViec()
        },
        onClickBtnCapNhatGhiChuXuLyNv(giaoViec) {
            this.giaoViecCapNhat = giaoViec
            this.ghiChuXuLyNv = null
            this.$bvModal.show('modal-ghichu-xuly-nv')
        },
        onClickBtnThongSoPtm(phanTuMang) {
            this.$refs['ref-modal-xem-thongtin'].setTitle('Thông số phần tử mạng')
            this.$refs['ref-modal-xem-thongtin'].setContent(!phanTuMang.MOTA ? '' : phanTuMang.MOTA.trim())
            this.$bvModal.show('modal-xem-thongtin')
        },
        onClickBtnCongSoiBiSuCo(phanTuMang) {

            this.phanTuMangCapNhat = phanTuMang
            
            switch(this.phieuDuocChon.NGUONSC_ID) {
                case 1:
                case 3: 
                    let modalChonCongSoi = this.$refs['modal-congsoi-bi-suco']
                    modalChonCongSoi.setLoaiTbiId(this.phanTuMangCapNhat.LOAIPTM_ID)
                    modalChonCongSoi.setThietBiId(this.phanTuMangCapNhat.PTM_ID)
                    modalChonCongSoi.setCongSoi(this.phanTuMangCapNhat.CONGSOI)
                    modalChonCongSoi.setKieu(1)
                    modalChonCongSoi.show()
                    break
                case 2:
                    this.$refs['ref-modal-xem-thongtin'].setTitle('Thông tin lỗi')
                    this.$refs['ref-modal-xem-thongtin'].setContent(phanTuMang.CONGSOI)
                    this.$bvModal.show('modal-xem-thongtin')
                    break
            }
        },
        async okCapNhatGhiChuPhanTuMang() {

            let data = {
                phieu_id: this.idPhieuDuocChon,
                suco_ptm_id: this.phanTuMangCapNhat.SUCO_PTM_ID,
                ghi_chu: this.ghiChuPhanTuMang
            }

            try {
                this.loading(true)
                await this.capNhatGhiChuPtm(data)
                this.$toast.success('Cập nhật thành công')
            } catch (err) {
                this.$toast.error('Cập nhật thất bại\n' + err)
                return
            } finally {
                this.loading(false)
            }

            this.$bvModal.hide('modal-ghichu-phantu-mang')
            this.thucHienLayDsPhanTuMang()
        },
        onClickBtnGhiChuPhanTuMang(phanTuMang) {
            this.phanTuMangCapNhat = phanTuMang
            this.ghiChuPhanTuMang = null
            this.$bvModal.show('modal-ghichu-phantu-mang')
        },
        async thucHienLayDsGiaoViec() {

            if (!this.idPhieuDuocChon || !this.phanTuMangDuocChon) {
                return
            }

            try {
                this.loading(true)
                await this.layDsGiaoViecNhanVien(this.idPhieuDuocChon, this.phanTuMangDuocChon.SUCO_PTM_ID)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async thucHienLayDsPhanTuMang() {

            if (!this.idPhieuDuocChon) {
                return
            }

            try {
                this.loading(true)
                await this.layDsPhanTuMang(this.idPhieuDuocChon)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        toggleShowBottomPanels() {
            this.showBottomPanels = !this.showBottomPanels
        },
        toggleExpandDsGiaoViec() {
            this.expandDsGiaoViec = !this.expandDsGiaoViec
        },
        toggleExpandDsPtm() {
            this.expandDsPtm = !this.expandDsPtm
        },
        toggleExpandDsPhieu() {
            this.showTttk = !this.showTttk
            this.expandDsPhieu = !this.expandDsPhieu
        },
        onToggleTttk() {
            this.showTttk = !this.showTttk
        },
        onClickBtnLamMoiTttk() {

            this.maSuCo = null
            this.nguoiBaoId = null
            this.mucDoKhanCapId = null
            this.nguonSuCoId = null
            this.tinhTrangId = null
            this.mucDoSuCoId = null
            this.ngaySuCoTu = null
            this.ngaySuCoDen = null
            this.ngayBaoTu = new Date()
            this.ngayBaoDen = new Date()
            this.ngayGiaoTu = null
            this.ngayGiaoDen = null
            this.keoDaiTuH = 0
            this.keoDaiTuM = 0
            this.keoDaiTuS = 0
            this.keoDaiDenH = 0
            this.keoDaiDenM = 0
            this.keoDaiDenS = 0
            this.toanTuKeoDaiTu = toanTu['>=']
            this.toanTuKeoDaiDen = toanTu['<=']
            this.showKeoDaiDen = true
            this.noiDungSuCo = null
            this.noiDungGiao = null

            this.chkMaSuCo = false
            this.chkNguoiBao = false
            this.chkMdKhanCap = false
            this.chkNguonSuCo = false
            this.chkTinhTrang = false
            this.chkNoiDungSuCo = false
            this.chkNoiDungGiao = false
            this.chkNgaySuCo = false
            this.chkNgayBao = false
            this.chkNgayGiao = false
            this.chkMucDoSuCo = false
            this.chkKeoDaiTu = false
            this.chkKeoDaiDen = false

            if (this.dsNhomSuCo && this.dsNhomSuCo.length > 0) {
                this.nhomSuCoId = this.dsNhomSuCo[0].NHOMSC_ID
                this.onClickBtnTimKiem()
            }
        },
        onClickBtnDoKiem() {
            if (this.enableBtnDoKiem) {
                this.$root.toastError('Chức năng đang được xây dựng')
            }
        },
        onClickBtnHoanCong() {
            if (this.enableBtnHoanCong) {
                this.ghiChuHoanCong = null
                this.$bvModal.show('modal-hoancong')
            }
        },
        async okHoanCong() {

            let data = {
                phieu_id: this.idPhieuDuocChon,
                ghi_chu: this.ghiChuHoanCong
            }

            try {
                this.loading(true)
                await this.hoanCong(data)
                this.$toast.success('Đã hoàn công phiếu xử lý')
            } catch (err) {
                this.$toast.error('Hoàn công thất bại\n' + err)
                return
            } finally {
                this.loading(false)
            }

            this.$bvModal.hide('modal-hoancong')
            this.thucHienTraCuu()
        },
        onClickBtnXuatPhieu() {
            if (this.enableBtnXuatPhieu) {
                this.kieuXuatPhieu = 0
                this.$bvModal.show('modal-xuatphieu')
            }
        },
        okXuatPhieu() {

            let data = {
                suco_id: this.kieuXuatPhieu == 0 ? 0 : this.phieuDuocChon.SUCO_ID,
                giaoviec_id: null
            }

            this.$refs['ref-modal-in-phieu'].setData(data)
            this.$bvModal.show('modal-in-phieu')
        },
        onClickBtnInGiaoViecNv(giaoViec) {

            let data = {
                suco_id: this.phieuDuocChon.SUCO_ID,
                giaoviec_id: giaoViec.GIAOVIEC_ID
            }

            this.$refs['ref-modal-in-phieu'].setData(data)
            this.$bvModal.show('modal-in-phieu')
        },
        onClickBtnGhiChu() {
            if (this.enableBtnGhiChuXL) {
                this.ghiChu = this.phieuDuocChon.GHICHU_XL
                this.$refs['ref-modal-ghichu'].show()
            }
        },
        async okCapNhatGhiChu() {

            let data = {
                phieu_id: this.idPhieuDuocChon,
                ghi_chu: this.ghiChu
            }

            try {
                this.loading(true)
                await this.capNhatGhiChu(data)
                this.$toast.success('Cập nhật thành công')
            } catch (err) {
                this.$toast.error('Cập nhật thất bại\n' + err)
                return
            } finally {
                this.loading(false)
            }

            this.$refs['ref-modal-ghichu'].hide()
            this.thucHienTraCuu()
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
        ddmmyyyy(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y}`
        },
        ddmmyyyyhhmi(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}`
        },
        onSelectRowPtm(rowData) {
            this.phanTuMangDuocChon = rowData
        },
        onClickBtnKeoDaiDen() {
            switch (this.toanTuKeoDaiDen) {
                case toanTu['<=']:
                    this.toanTuKeoDaiDen = toanTu['<']
                    break
                case toanTu['<']:
                    this.toanTuKeoDaiDen = toanTu['<=']
                    break
            }
        },
        onClickBtnKeoDaiTu() {
            switch (this.toanTuKeoDaiTu) {
                case toanTu['>=']:
                    this.toanTuKeoDaiTu = toanTu['>']
                    this.showKeoDaiDen = true
                    break
                case toanTu['>']:
                    this.toanTuKeoDaiTu = toanTu['=']
                    this.showKeoDaiDen = false
                    break
                case toanTu['=']:
                    this.toanTuKeoDaiTu = toanTu['>=']
                    this.showKeoDaiDen = true
                    break
            }
        },
        onClickBtnTimKiem() {
            this.idPhieuDuocChon = null
            this.thucHienTraCuu()
            
        },
        async thucHienTraCuu() {

            let data = {
                nhomsc_id: this.nhomSuCoId,
                ma_sc: !this.chkMaSuCo ? '' : (!this.maSuCo ? '' : this.maSuCo),
                nguoibao_id: !this.chkNguoiBao ? '' : (!this.nguoiBaoId ? '' : this.nguoiBaoId),
                mucdokc_id: !this.chkMdKhanCap ? '' : (!this.mucDoKhanCapId ? '' : this.mucDoKhanCapId),
                nguonsc_id: !this.chkNguonSuCo ? '' : (!this.nguonSuCoId ? '' : this.nguonSuCoId),
                tinhtrang: !this.chkTinhTrang ? '' : (!this.tinhTrangId ? '' : this.tinhTrangId),
                noidung_sc: !this.chkNoiDungSuCo ? '' : (!this.noiDungSuCo ? '' : this.noiDungSuCo),
                noidung_giao: !this.chkNoiDungGiao ? '' : (!this.noiDungGiao ? '' : this.noiDungGiao),
                ngaysc_tu: !this.chkNgaySuCo ? null : this.ddmmyyyy(this.ngaySuCoTu),
                ngaysc_den: !this.chkNgaySuCo ? null : this.ddmmyyyy(this.ngaySuCoDen),
                ngaybao_tu: !this.chkNgayBao ? null : this.ddmmyyyy(this.ngayBaoTu),
                ngaybao_den: !this.chkNgayBao ? null :  this.ddmmyyyy(this.ngayBaoDen),
                ngaygiao_tu: !this.chkNgayGiao ? null : this.ddmmyyyy(this.ngayGiaoTu),
                ngaygiao_den: !this.chkNgayGiao ? null : this.ddmmyyyy(this.ngayGiaoDen),
                mucdosc_id: !this.chkMucDoSuCo ? '' : (!this.mucDoSuCoId ? '' : this.mucDoSuCoId),
                keodai_tu_op: !this.chkKeoDaiTu ? '' : layGiaTriToanTu(this.toanTuKeoDaiTu),
                keodai_tu: !this.chkKeoDaiTu ? '' : this.keoDaiTu,
                keodai_den_op: !this.chkKeoDaiDen || !this.showKeoDaiDen ? '' : layGiaTriToanTu(this.toanTuKeoDaiDen),
                keodai_den: !this.chkKeoDaiDen || !this.showKeoDaiDen ? '' : this.keoDaiDen
            }

            try {
                this.loading(true)
                await this.traCuuPhieu(data)
            } catch (err) {

            } finally {
                this.loading(false)
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
        async hoanCong(data) {
            try {
                let response = await this.$root.context.post('/web-suco/ketqua-xuly-suco/hoan-cong', data)
                
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
        async capNhatGhiChu(data) {
            try {
                let response = await this.$root.context.post('/web-suco/ketqua-xuly-suco/capnhat-ghichu', data)
                
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
        async capNhatGhiChuPtm(data) {
            try {
                let response = await this.$root.context.post('/web-suco/ketqua-xuly-suco/capnhat-ghichu-ptm', data)
                
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
        async capNhatGhiChuXuLyNv(data) {
            try {
                let response = await this.$root.context.post('/web-suco/ketqua-xuly-suco/capnhat-ghichu-xuly-nv', data)
                
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
        async huyGiaoViecNv(giaoViecId) {
            try {
                let response = await this.$root.context.post(`/web-suco/ketqua-xuly-suco/huy-giaoviec-nv?giaoviec_id=${giaoViecId}`)
                
                if (response.error_code == 'BSS-00000000') {
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
        async layDsGiaoViecNhanVien(phieuId, suCoPtmId) {
            try {
                let response = await this.$root.context.get(`/web-suco/ketqua-xuly-suco/danhsach-giaoviec-nv?phieu_id=${phieuId}&suco_ptm_id=${suCoPtmId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => {
                        item.ALLOW_ACTION = item.TRANGTHAI == 'Chưa xử lý'
                    })
                    this.dsGiaoViec = response.data
                } else {
                    this.dsGiaoViec = []
                }
            } catch (err) {
                this.dsGiaoViec = []
            }
        },
        async layDsPhanTuMang(phieuId) {
            try {
                let response = await this.$root.context.get(`/web-suco/ketqua-xuly-suco/danhsach-phantu-mang?phieu_id=${phieuId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsPhanTuMang = response.data
                } else {
                    this.dsPhanTuMang = []
                }
            } catch (err) {
                this.dsPhanTuMang = []
            }
        },
        async traCuuPhieu(data) {
            try {
                let response = await this.$root.context.post('/web-suco/ketqua-xuly-suco/danhsach-phieu', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => { 
                        item.IMAGES = []
                    })
                    this.dsPhieuXuLy = response.data
                } else {
                    this.dsPhieuXuLy = []
                }
            } catch (err) {
                this.dsPhieuXuLy = []
            }
        },
        async layDmMucDoSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/mucdo-suco')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({MUCDO_ID: 0, MUCDO_SC: 'Tất cả', CANHBAO_DT: -1 })
                    this.dsMucDoSuCo = response.data
                } else {
                    this.dsMucDoSuCo = []
                }
            } catch (err) {
                this.dsMucDoSuCo = []
            }
        },
        async layDmTinhTrang() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/tinhtrang-giaoviec')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTinhTrang = response.data
                } else {
                    this.dsTinhTrang = []
                }
            } catch (err) {
                this.dsTinhTrang = []
            }
        },
        async layDmNguonSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/nguon-suco')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNguonSuCo = response.data.filter(item => item.NGUONSC_ID != 0)
                } else {
                    this.dsNguonSuCo = []
                }
            } catch (err) {
                this.dsNguonSuCo = []
            }
        },
        async layDmMucDoKhanCap() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/mucdo-khancap')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsMucDoKhanCap = response.data
                } else {
                    this.dsMucDoKhanCap = []
                }
            } catch (err) {
                this.dsMucDoKhanCap = []
            }
        },
        async layDmNhomSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/nhom-suco')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhomSuCo = response.data
                } else {
                    this.dsNhomSuCo = []
                }
            } catch (err) {
                this.dsNhomSuCo = []
            }
        },
        async uploadFile(data) {
            try{
                var rs = await this.$root.context.post("/web-suco/storage/uploadFile", data)

                if(rs.data != null && rs.data.status)  {
                    return rs.data.message
                }
                throw ''
            } catch(e){
                throw 'Có lỗi xảy ra trong quá trình upload file'
            }
        },
        async getFileUrl(src){
            try{
                var rs = await this.$root.context.post(`/web-suco/storage/getPresignedUrl`, {fileSource: src})
                if(rs.data != null)  {
                    return rs.data.result
                }
                throw ''
            } catch(err) {
                throw 'Có lỗi xảy ra trong quá trình lấy đường dẫn file'
            }
        },
        async deleteFile(src){
            try{
                var rs = await this.$root.context.post(`/web-suco/storage/deleteFile`, {fileSource: src})
                if(rs.data != null && rs.data.status)  {
                    return
                }
                throw ''
            } catch(err) {
                throw 'Có lỗi xảy ra trong quá trình xóa file'
            }
        },
        async capNhatAnhChup(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/ketqua-xuly-suco/capnhat-anhchup`, data)
                
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
    .frm-capnhat-xuly-suco .disable-btn:hover {
        background-color: white !important;
    }

    .frm-capnhat-xuly-suco .disable-btn a {
        color: #6c757d !important;
    }

    .frm-capnhat-xuly-suco .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }

    .frm-capnhat-xuly-suco .select2-container--focus .select2-selection {
        border-color: #80bdff;
        -webkit-box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
        box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
    }

    .frm-capnhat-xuly-suco .panel-anh-suco {
        width: 380px; 
        height: 180px; 
        background-color: white; 
        position: fixed; 
        right: 42px;
        z-index: 1;
        padding: 34px 5px 5px 5px;
        border: 1px solid #ccc;
    }

    .frm-capnhat-xuly-suco .panel-zoom-img {
        position: fixed;
        width: 500px;
        height: 500px;
        background-color: transparent;
        z-index: 99999;
        margin: auto auto;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
    }

    .frm-capnhat-xuly-suco .table-result .highlight1 {
        background-color: #F9E1A9 !important;
    }

    .frm-capnhat-xuly-suco .grid-giao-viec-nhan-vien .e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .frm-capnhat-xuly-suco .grid-giao-viec-nhan-vien .e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
    .frm-capnhat-xuly-suco .grid-phan-tu-mang .e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .frm-capnhat-xuly-suco .grid-phan-tu-mang .e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div,
    .frm-capnhat-xuly-suco .grid-phieu-dieu-xuly-suco .e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .frm-capnhat-xuly-suco .grid-phieu-dieu-xuly-suco .e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }
</style>