<template src="./index.template.html"></template>
<style>
    .lap-hoso-dauchuyen .vue-treeselect__control {
        height: 32px !important;
    }

    .lap-hoso-dauchuyen .vue-treeselect--disabled .vue-treeselect__control {
        background-color: #f1f1f1;
    }

    .lap-hoso-dauchuyen .vue-treeselect__placeholder,
    .lap-hoso-dauchuyen .vue-treeselect__single-value {
        line-height: 30px;
    }

    .lap-hoso-dauchuyen span.vue-treeselect__tip-text.vue-treeselect__no-options-tip-text {
        text-align: center;
        font-size: 14px;
        line-height: 30px;
    }

    .lap-hoso-dauchuyen div.vue-treeselect__icon-container {
        display: none;
    }

    .lap-hoso-dauchuyen .vue-treeselect__control-arrow {
        width: 7px;
        height: 6px;
        color: inherit;
        margin-left: 7px;
    }

    .lap-hoso-dauchuyen .disable-panel {
        position: absolute;
        width: 100%;
        height: 100%;
        background-color: #ccc;
        top: 0;
        left: 0;
        opacity: 0.3;
    }

    .lap-hoso-dauchuyen .e-input-group-icon.e-time-icon.e-icons {
        display: none !important;
    }

    .lap-hoso-dauchuyen .select2-container .select2-selection--single .select2-selection__rendered {
        white-space: initial;
        height: 28px;
    }

    .lap-hoso-dauchuyen .tc-pagination .dropdown .input-number::before,
    .lap-hoso-dauchuyen .tc-pagination .dropdown .input-number::after {
        background-color: transparent !important;
    }

    .lap-hoso-dauchuyen .e-datetimepicker.e-input {
        font-family: "Roboto", Arial !important;
        color: #444 !important;
    }

    .lap-hoso-dauchuyen .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .lap-hoso-dauchuyen .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }

    @media (min-width: 1200px) {
        #id-modal-lap-hoso .modal-xl {
            max-width: 1200px !important;
        }
    }
</style>
<script>
import ModalChonThueBaoTheoCap from '../modal-chon-thuebao-theocap/index.vue'
import ModalChonThueBao from '../modal-chon-thuebao/index.vue'
import ModalNhapDoDaiDayThueBao from '../modal-nhap-dodai-day-thuebao/index.vue'
import ModalThayDoiThongTinCap from '../modal-thaydoi-thongtin-cap/index.vue'
import ModalDangKyDauChuyenTheoFile from '../modal-dangky-dauchuyen-theo-file/index.vue'
import ModalChonTramTbi from '../modal-chon-tramtbi/index.vue'
import ModalDoiCapHangLoat from '../modal-doicap-hangloat/index.vue'
import XLSX from "xlsx"
import BssPagination2 from '@/components/BssPagination_2'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, maxLength } from 'vuelidate/lib/validators'
import Vue from 'vue';
import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars'
Vue.use(DateTimePickerPlugin)

export default {
    components: {
        ModalChonThueBaoTheoCap,
        ModalChonThueBao,
        ModalNhapDoDaiDayThueBao,
        ModalThayDoiThongTinCap,
        ModalDangKyDauChuyenTheoFile,
        ModalChonTramTbi,
        ModalDoiCapHangLoat,
        BssPagination2,
        BssErrorMarker,
        BssRequiredMarker
    },
    props:{
        modalId: String,
        hsccId: Number,
        loaihs: String
    },
    data() {
        return {
            title:'Lập hồ sơ đấu chuyển',
            dsLoaiHoSo: [],
            dsKieuCatChuyen: [],
            dsTtvt: [],
            dsTrangThaiCatChuyen: [],
            dsDichVuVt: [],
            dsVeTinh: [],
            dsThueBaoDangKy: [],
            loaiHoSoId: null,
            kieuCatChuyenId: null,
            ngayBatDau: new Date(),
            ngayKetThuc: new Date(),
            ttvtId: null,
            veTinhId: null,
            trangThaiId: null,
            chkChuyen119: false,
            modalKey: 1,
            thongTinHoSo: null,
            tenHoSo: null,
            maHoSo: null,
            tenNguoiDangKy: null,
            hsccMoiId: null,
            isModified: false,
            isReadonly: false,
            showButton: {
                thayDay: false,
                chuyenVeTinh: true,
                doiThongTinCap: false,
                nhapCapHangLoat: true
            },
            disableButton: {
                thayDay: true,
                chuyenVeTinh: true,
                xoaDangKyHangLoat: true,
                nhapCapHangLoat: true
            },
            giaTriTimThueBao: {
                maThueBao: null,
                dichVuId: null
            },
            giaTriThemPTM: {
                dsNhomTbi: [],
                dsLoaiTbi: [],
                dsHoSoPTM: [],
                nhomTbiId: null,
                loaiTbId: null,
                soLuong: 1
            },
            dkccIdDuocChon: null,
            dsDkccIdDuocChon: [],
            selectAllThueBao: false,
            cuLy: 0,
            dsThueBaoNhapCapHangLoat: [],
            fileExcel: null,
            dsMaThueBaoImport: [],
            hsptmIdDuocChon: null
        }
    },
    computed: {
        isAdding() {
            return !this.hsccId && !this.hsccMoiId
        },
        fileName() {
            return !this.fileExcel ? null : this.fileExcel.name
        },
        disablePanel() {
            return this.isAdding || this.isReadonly
        }
    },
    watch: {
        dsDkccIdDuocChon(newValue) {
            this.disableButton.thayDay = newValue.length == 0
            this.disableButton.chuyenVeTinh = newValue.length == 0
            this.disableButton.xoaDangKyHangLoat = newValue.length == 0
            this.disableButton.nhapCapHangLoat = newValue.length == 0
            this.selectAllThueBao = newValue.length > 0 && newValue.length == this.dsThueBaoDangKy.filter(item => item.ALLOW_SELECT).length
        },
        selectAllThueBao(newValue) {

            if (newValue) {
                this.dsDkccIdDuocChon =
                    this.dsThueBaoDangKy.filter(item => item.ALLOW_SELECT).map(item => item.DKCC_ID)
                return
            }

            if (this.dsDkccIdDuocChon.length > 0 && this.dsDkccIdDuocChon.length == this.dsThueBaoDangKy.filter(item => item.ALLOW_SELECT).length) {
                this.dsDkccIdDuocChon = []
            }
        },
        dsThueBaoDangKy(newVal) {
            this.selectAllThueBao = false
            this.dsDkccIdDuocChon = []
        },
        async ttvtId(newVal) {

            this.veTinhId = null

            if (!newVal) {
                this.dsVeTinh = []
                return
            }

            try {
                this.loading(true)
                await this.layDmVeTinh(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        dsVeTinh(dsVeTinh) {
            if (this.thongTinHoSo) {

                for (var i = 0; i < dsVeTinh.length; i++) {

                    let toQuanLy = dsVeTinh[i]

                    if (!toQuanLy.children) {
                        continue
                    }

                    for (var j = 0; j < toQuanLy.children.length; j++) {

                        let veTinh = toQuanLy.children[j]

                        if (parseInt(veTinh.id.split('_')[0]) == this.thongTinHoSo.TRAMTBI_ID) {
                            this.veTinhId = veTinh.id
                            break
                        }
                    }

                    if (this.veTinhId) {
                        break
                    }
                }
            }
        },
        loaiHoSoId(newVal){
            this.title=newVal==5?'Lập hồ sơ cải tạo, bảo dưỡng phần tử mạng':'Lập hồ sơ đấu chuyển'
        },
        chkChuyen119(newVal) {
            if (newVal && this.isAdding) {
                this.$toast.error('*Lưu ý: Khi tích chọn \"Chuyển 119\", nếu thuê bao trong danh sách đấu chuyển chưa được hoàn thiện mà khách hàng báo hỏng thì sẽ không được phép nhập phiếu báo hỏng.')
            }
        },
        ngayBatDau(newVal) {
            if (this.ngayKetThuc && this.ngayKetThuc < newVal) {
                this.ngayKetThuc = newVal
            }
        },
        ngayKetThuc(newVal) {
            if (this.ngayBatDau && this.ngayBatDau > newVal) {
                this.ngayBatDau = newVal
            }
        },
        'giaTriThemPTM.nhomTbiId'(newVal){
            this.loaiTbId = null

            if (!newVal) {
                this.dsLoaiTbi = []
                return
            }

            try {
                this.loading(true)
                this.layDsLoaiTbi(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        }
    },
    validations: {
        tenHoSo: {
            required,
            maxLength: maxLength(200)
        },
        loaiHoSoId: {
            required
        },
        kieuCatChuyenId: {
            required
        },
        ngayBatDau: {
            required,
            khongLonHonNgayKetThuc: (ngay, vm) => ngay <= vm.ngayKetThuc
        },
        ngayKetThuc: {
            required
        },
        ttvtId: {
            required
        },
        veTinhId: {
            required
        },
        trangThaiId: {
            required
        }
    },
    methods: {
        onModalHidden() {

            this.modalKey++
            this.maHoSo = null
            this.tenHoSo = null
            this.loaiHoSoId = null
            this.kieuCatChuyenId = null
            this.tenNguoiDangKy = null
            this.ngayBatDau = new Date()
            this.ngayKetThuc = new Date()
            this.ttvtId = null
            this.veTinhId = null
            this.chkChuyen119 = false
            this.thongTinHoSo = null
            this.hsccMoiId = null
            this.trangThaiId = null
            this.giaTriTimThueBao.maThueBao = null
            this.giaTriTimThueBao.dichVuId = null
            this.giaTriThemPTM.nhomTbiId = null;
            this.giaTriThemPTM.loaiTbi = null;
            this.giaTriThemPTM.soLuong = 1;
            this.dkccIdDuocChon = null
            this.dsThueBaoDangKy = []
            this.giaTriThemPTM.dsHoSoPTM = []
            this.dsDkccIdDuocChon = []
            this.dsThueBaoNhapCapHangLoat = []
            this.fileExcel = null
            this.dsMaThueBaoImport = []
            this.showButton.thayDay = false
            this.showButton.chuyenVeTinh = true
            this.showButton.doiThongTinCap = false
            this.showButton.nhapCapHangLoat = true
            this.disableButton.thayDay = true
            this.disableButton.chuyenVeTinh = true
            this.disableButton.xoaDangKyHangLoat = true
            this.disableButton.nhapCapHangLoat = true

            this.$v.$reset()

            if (this.isModified) {
                this.$emit('change')
            }
        },
        async onModalShown() {

            this.$refs['ref-ten-hoso'].focus()
            this.tenNguoiDangKy = this.$root.token.getUserName()
            this.isModified = false
            this.isReadonly = false

            try {
                this.loading(true)
                let loading1 = this.layDsLoaiHoSo()
                let loading2 = this.layDsKieuCatChuyen()
                let loading3 = this.layDsTtvt()
                let loading4 = this.layDsTrangThaiCatChuyen()
                let loading5 = this.layDsDichVuVt()
                let loading6 = this.layDsNhomTbi()
                await loading1
                await loading2
                await loading3
                await loading4
                await loading6
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsLoaiHoSo.length > 0) {
                this.loaiHoSoId = this.dsLoaiHoSo[0].LOAIHSCC_ID
            }

            if (this.dsKieuCatChuyen.length > 0) {
                this.kieuCatChuyenId = this.dsKieuCatChuyen[0].KIEUCC_ID
            }

            if (this.dsTtvt.length > 0) {
                this.ttvtId = this.dsTtvt[0].DONVI_ID
            }

            if (this.dsTrangThaiCatChuyen.length > 0) {
                this.trangThaiId = this.dsTrangThaiCatChuyen[0].TTCC_ID
            }

            if (this.dsDichVuVt.length > 0) {
                this.giaTriTimThueBao.dichVuId = this.dsDichVuVt[0].DICHVUVT_ID
            }

            if (this.giaTriThemPTM.dsNhomTbi.length > 0) {
                this.giaTriThemPTM.nhomTbiId = this.giaTriThemPTM.dsNhomTbi[0].NHOMTBI_ID
            }

            if (this.hsccId) {
                await this.napThongTinHoSo()
                await this.napDsThueBaoDangKy()
                await this.napDsHoSoPTM()
            }
        },
        getHeaderTemplateChon(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" class="check" v-model="parent.selectAllThueBao" :disabled="parent.isReadonly">
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
        getColumnTemplateChon(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" class="check" :value="data.DKCC_ID" :disabled="!data.ALLOW_SELECT" v-model="parent.dsDkccIdDuocChon">
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
        getColumnTemplateChucNang(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div>
                                <button class="btn btn-main padt1 padb1" @click="parent.onClickBtnCapNhatDoDaiDayThueBao(data.DKCC_ID)" v-show="parent.showButton.thayDay" :disabled="data.TTCC_ID == 6">Thay dây</button>
                                <button class="btn btn-main padt1 padb1" @click="parent.onClickBtnDoiThongTinCap(data)" v-show="parent.showButton.doiThongTinCap" :disabled="data.TTCC_ID == 6">Đổi thông tin cáp</button>
                                <button class="btn btn-main pad4 lh14" @click="parent.onClickBtnXoaDangKyCatChuyen(data.DKCC_ID)" :disabled="!data.ALLOW_SELECT">
                                    <span class="-ap icon-close"></span>
                                </button>
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
        getColumnTemplateChucNangPTM(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div>
                                <button class="btn btn-main pad4 lh14" @click="parent.onClickBtnXoaHSPTM(data.HSPTM_ID)"><span class="-ap icon-close"></span></button>
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
            if (!!args.column.field) {
                args.cell.style.color = args.data.TTCC_ID == 1 ? '#cc2626' : 'black'
            }
        },
        onClickBtnDoiThongTinCap(thueBao) {
            let tramTbiId = this.veTinhId.split('_')[0]
            let modal = this.$refs['ref-thaydoi-thongtin-cap']
            modal.setThueBao(thueBao)
            modal.setTramTbiId(parseInt(tramTbiId))
            modal.show()
        },
        async onChangeThongTinCap(data) {
            try{
                this.loading(true)
                await this.hoanThienDauChuyen({
                    dkcc_id: data.DKCC_ID,
                    ghichu: ''
                })
            } catch(err) {
                this.$root.toastError(`1. Thay đổi thông tin cáp: [Thành công].\n Hoàn thiện đấu chuyển: [Thất bại] \n ${err}`)
                return
            } finally {
                this.loading(false)
            }

            await this.napThongTinHoSo()
            await this.napDsThueBaoDangKy()
            await this.napDsHoSoPTM()
            this.isModified = true
        },
        async onClickBtnHoanThien() {

            if (this.isAdding) {
                return
            }

            try {
                this.loading(true)
                await this.hoanThienHoSo(this.getHsccId())
                this.$toast.success('Hoàn thiện thành công')
                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.onClickBtnNapLai()
        },
        async onClickBtnDongBo() {

            if (this.isAdding) {
                return
            }

            try {
                this.loading(true)
                await this.dongBoTrangThai(this.getHsccId())
                this.$toast.success('Đồng bộ thành công')
                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.onClickBtnNapLai()
        },
        onChangeDangKyTbaoTheoFile() {
            this.napDsThueBaoDangKy()
        },
        onClickBtnDangKyTheoFile() {

            if (!this.fileExcel) {
                this.$toast.error('Tệp không tồn tại!')
                return
            }

            this.$bvModal.show('id-modal-dangky-dauchuyen-theo-file')
        },
        onFileChange() {
            this.fileExcel = this.$refs['ref-file-upload'].$refs.input.files[0]
        },
        chooseFile() {
            document.getElementById('lap-hoso-dauchuyen-input-file-upload').click()
        },
        async onChangeDauNoiThueBao() {
            await this.napThongTinHoSo()
            await this.napDsThueBaoDangKy()
            this.isModified = true
        },
        onClickBtnDoiCapHangLoat() {

            if (!this.dsDkccIdDuocChon || this.dsDkccIdDuocChon.length == 0) {
                this.$toast.error('Chưa chọn thuê bao')
                return
            }

            this.dsThueBaoNhapCapHangLoat = this.dsThueBaoDangKy.filter(item => this.dsDkccIdDuocChon.indexOf(item.DKCC_ID) >= 0)
            this.$bvModal.show('id-modal-doicap-hangloat')
        },
        onChangeDangKyTbaoTheoCap() {
            this.napDsThueBaoDangKy()
        },
        async onSelectTramTbi(tramTbi) {

            let dsDkccChuyenTram = this.dsThueBaoDangKy.filter(item => item.ALLOW_SELECT && this.dsDkccIdDuocChon.indexOf(item.DKCC_ID) >= 0)

            let data = {
                hscc_id: this.getHsccId(),
                ds_dkcc_id: dsDkccChuyenTram.map(item => item.DKCC_ID).join(),
                tramtbi_id: tramTbi.DONVI_ID
            }

            try {
                this.loading(true)
                await this.chuyenVeTinhDsThueBao(data)
                this.$toast.success('Chuyển vệ tinh thành công!')
                this.dsDkccIdDuocChon = []
                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            await this.napThongTinHoSo()
            await this.napDsThueBaoDangKy()
        },
        onClickBtnChuyenVeTinh() {

            if (!this.dsDkccIdDuocChon || this.dsDkccIdDuocChon.length == 0) {
                this.$toast.error('Chưa chọn thuê bao')
                return
            }

            let dsDkccChuyenTram = this.dsThueBaoDangKy.filter(item => item.ALLOW_SELECT && this.dsDkccIdDuocChon.indexOf(item.DKCC_ID) >= 0)

            if (dsDkccChuyenTram.length == 0) {
                this.$toast.error('Thuê bao đang chọn đã hoàn thiện đấu chuyển hoặc đang xử lý, không thể chuyển cáp.')
                return
            }

            this.$bvModal.show('id-modal-chon-tramtbi')
        },
        async onClickBtnXacNhanThayDay() {

            if (!this.dsDkccIdDuocChon || this.dsDkccIdDuocChon.length == 0) {
                this.$toast.error('Chưa chọn thuê bao')
                return
            }

            let dsDkccThayDay = this.dsThueBaoDangKy.filter(item => item.ALLOW_SELECT && this.dsDkccIdDuocChon.indexOf(item.DKCC_ID) >= 0)

            if (dsDkccThayDay.length == 0) {
                this.$toast.error('Thuê bao đang chọn đã hoàn thiện đấu chuyển hoặc đang xử lý, không thể thay dây.')
                return
            }

            let data = {
                hscc_id: this.getHsccId(),
                ds_dkcc_id: dsDkccThayDay.map(item => item.DKCC_ID).join()
            }

            try {
                this.loading(true)
                await this.thayDayDsThueBao(data)
                this.$toast.success('Cập nhật thành công!')
                this.dsDkccIdDuocChon = []
                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            await this.napThongTinHoSo()
            await this.napDsThueBaoDangKy()
        },
        async okCapNhatDoDaiDayThueBao(doDaiDay) {
            let data = {
                dkcc_id: this.dkccIdDuocChon,
                culy: doDaiDay
            }
            try {
                this.loading(true)
                await this.capNhatDoDaiDayThueBao(data)
                this.$bvModal.hide('id-modal-nhap-dodai-day-thuebao')
                this.$toast.success('Cập nhật độ dài dây thuê bao thành công!')
                this.dkccIdDuocChon = null
                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            await this.napThongTinHoSo()
            await this.napDsThueBaoDangKy()
        },
        async onClickBtnCapNhatDoDaiDayThueBao(dkccId) {

            this.dkccIdDuocChon = dkccId

            this.loading(true)
            this.cuLy = await this.layDoDaiDayThueBao(dkccId)
            this.loading(false)

            this.$bvModal.show('id-modal-nhap-dodai-day-thuebao')
        },
        async okXoaDsThueBaoDangKy() {

            let dsDkccXoa = this.dsThueBaoDangKy.filter(item => item.TTCC_ID == 1 && this.dsDkccIdDuocChon.indexOf(item.DKCC_ID) >= 0)
            let dsDkccId = dsDkccXoa.map(item => item.DKCC_ID)
            dsDkccId = [...new Set(dsDkccId)]

            let dsThueBaoDuocLoaiBoThanhCong = []

            try {
                this.loading(true)

                for (var i = 0; i < dsDkccId.length; i++) {
                    let dkccId = dsDkccId[i]
                    await this.xoaThueBaoDangKyDauChuyen(dkccId)
                    dsThueBaoDuocLoaiBoThanhCong.push(dkccId)
                }

                this.$toast.success('Loại thuê bao khỏi danh sách cắt chuyển thành công!')
                this.dsDkccIdDuocChon = []
                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                this.dsDkccIdDuocChon = this.dsDkccIdDuocChon.filter(item => dsThueBaoDuocLoaiBoThanhCong.indexOf(item) < 0)
            } finally {
                this.loading(false)
            }

            this.dsThueBaoDangKy = this.dsThueBaoDangKy.filter(item => dsThueBaoDuocLoaiBoThanhCong.indexOf(item.DKCC_ID) < 0)
            await this.napThongTinHoSo()
        },
        onClickBtnXoaDsDangKyCatChuyen() {

            if (!this.dsDkccIdDuocChon || this.dsDkccIdDuocChon.length == 0) {
                this.$toast.error('Chưa chọn thuê bao')
                return
            }

            let dsDkccXoa = this.dsThueBaoDangKy.filter(item => item.TTCC_ID == 1 && this.dsDkccIdDuocChon.indexOf(item.DKCC_ID) >= 0)

            if (dsDkccXoa.length == 0) {
                this.$toast.error('Các thuê bao đang chọn không thể xóa.')
                return
            }

            this.$bvModal.show('modal-xacnhan-xoa-ds-dkcc')
        },
        onClickBtnXoaDangKyCatChuyen(dkccId) {
            this.dkccIdDuocChon = dkccId
            this.$bvModal.show('modal-xacnhan-xoa-dkcc')
        },
        async okXoaThueBaoDangKy() {
            try {
                this.loading(true)
                await this.xoaThueBaoDangKyDauChuyen(this.dkccIdDuocChon)
                this.$toast.success('Loại thuê bao khỏi danh sách cắt chuyển thành công!')
                this.dsThueBaoDangKy = this.dsThueBaoDangKy.filter(item => item.DKCC_ID != this.dkccIdDuocChon)
                this.dkccIdDuocChon = null
                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            await this.napThongTinHoSo()
        },
        async okXoaHoSoPTM() {
            try {
                this.loading(true)
                await this.xoaHoSoPTM(this.hsptmIdDuocChon)
                this.hsptmIdDuocChon = null
                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            await this.napDsHoSoPTM()
        },
        onClickBtnXoaHSPTM(hsptm_id) {
            this.hsptmIdDuocChon = hsptm_id
            this.$bvModal.show('modal-xacnhan-xoa-ptm')
        },
        getHsccId() {
            return this.hsccId ? this.hsccId : (this.hsccMoiId ? this.hsccMoiId : null)
        },
        async onSelectThueBao(thueBao) {

            if (!this.getHsccId()) {
                this.$toast.error('Không tồn tại hồ sơ đấu chuyển để đăng ký đấu chuyển thuê bao')
                return
            }

            let data = {
                thuebao_id: thueBao.THUEBAO_ID,
                ma_tb: thueBao.MA_TB,
                ten_tb: thueBao.TEN_TB,
                diachi_ld: thueBao.DIACHI_LD,
                ghichu: thueBao.DAUCUOI_ID,
                ne: '',
                kieucc_id: this.kieuCatChuyenId,
                ttcc_id: this.trangThaiId,
                donvi_id: this.veTinhId ? this.veTinhId.split('_')[0] : null,
                ngaybd_dk: this.yyyymmddhhmissminus(this.ngayBatDau),
                ngaykt_dk: this.yyyymmddhhmissminus(this.ngayKetThuc),
                hscc_id: this.getHsccId(),
                chan_baohong: this.chkChuyen119 ? 1 : 0
            }

            try {
                this.loading(true)
                await this.themThueBaoDangKyDauChuyen(data)
                this.isModified = true
                this.$toast.success('Đăng ký đấu chuyển thuê bao thành công!')
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.napDsThueBaoDangKy()
        },
        onClickBtnTimThueBao() {

            if (!this.giaTriTimThueBao.maThueBao) {
                this.$toast.error('Chưa nhập số máy/account')
                return
            }

            this.$bvModal.show('id-modal-chon-thuebao')
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
        yyyymmddhhmissminus(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.y}-${date.M}-${date.d} ${date.h}:${date.m}:${date.s}`
        },
        async onClickBtnGhiLai() {

            let errorMessage = []

            this.$v.$touch()

            let allowFocus = true

            if (this.$v.$invalid) {

                if (!this.$v.tenHoSo.required) {
                    errorMessage.push("Chưa nhập tên hồ sơ")
                    this.$refs['ref-ten-hoso'].focus()
                    allowFocus = false
                }

                if (!this.$v.tenHoSo.maxLength) {
                    errorMessage.push("Tên hồ sơ không đươc vượt quá 200 ký tự")
                    this.$refs['ref-ten-hoso'].focus()
                    allowFocus = false
                }

                if (!this.$v.loaiHoSoId.required) {
                    errorMessage.push("Chưa chọn loại hồ sơ")
                }

                if (!this.$v.kieuCatChuyenId.required) {
                    errorMessage.push("Chưa chọn hình thức đấu chuyển")
                }

                if (!this.$v.ngayBatDau.required) {
                    errorMessage.push("Chưa chọn ngày bắt đầu")

                    if (allowFocus) {
                        this.$refs['ref-ngay-batdau'].focusIn()
                        allowFocus = false
                    }
                }

                if (!this.$v.ngayKetThuc.required) {
                    errorMessage.push("Chưa chọn ngày kết thúc")

                    if (allowFocus) {
                        this.$refs['ref-ngay-ketthuc'].focusIn()
                        allowFocus = false
                    }
                }

                if (!this.$v.ngayBatDau.khongLonHonNgayKetThuc) {
                    errorMessage.push("Ngày bắt đầu không được sau ngày kết thúc")

                    if (allowFocus) {
                        this.$refs['ref-ngay-batdau'].focusIn()
                        allowFocus = false
                    }
                }

                if (!this.$v.ttvtId.required) {
                    errorMessage.push("Chưa chọn trung tâm viễn thông")
                }

                if (!this.$v.veTinhId.required) {
                    errorMessage.push("Chưa chọn vệ tinh")
                    if (allowFocus) {
                        this.$refs['ref-vetinh'].$el.querySelector('input').focus()
                        allowFocus = false
                    }
                }

                if (!this.$v.trangThaiId.required) {
                    errorMessage.push("Chưa chọn trạng thái hồ sơ")
                }
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return
            }

            let data = {
                ten_hs: this.tenHoSo,
                loaihscc_id: this.loaiHoSoId,
                kieucc_id: this.kieuCatChuyenId,
                ttcc_id: this.trangThaiId,
                ngaybd_dk: this.yyyymmddhhmissminus(this.ngayBatDau),
                ngaykt_dk: this.yyyymmddhhmissminus(this.ngayKetThuc),
                ttvt_id: this.ttvtId,
                tramtbi_id: this.veTinhId ? this.veTinhId.split('_')[0] : null,
                chan_baohong: this.chkChuyen119 ? 1 : 0
            }

            try {
                this.loading(true)

                if (this.isAdding) {
                    let result = await this.themHoSo(data)
                    this.hsccMoiId = result.HSCC_ID
                    this.$toast.success('Thêm mới hồ sơ thành công!')
                    await this.napThongTinHoSo()
                } else {
                    data.hscc_id = this.hsccId ? this.hsccId : this.hsccMoiId
                    await this.capNhatHoSo(data)
                    this.$toast.success('Cập nhật hồ sơ thành công!')
                }

                this.isModified = true
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }
        },
        async onClickBtnNapLai() {
            if (!this.isAdding) {
                await this.napThongTinHoSo()
                await this.napDsThueBaoDangKy()
                await this.napDsHoSoPTM()
            } else {
                this.tenHoSo = null
                this.loaiHoSoId = this.dsLoaiHoSo.length > 0 ? this.dsLoaiHoSo[0].LOAIHSCC_ID : null
                this.kieuCatChuyenId = this.dsKieuCatChuyen.length > 0 ? this.dsKieuCatChuyen[0].KIEUCC_ID : null
                this.ngayBatDau = new Date()
                this.ngayKetThuc = new Date()
                this.ttvtId = this.dsTtvt.length > 0 ? this.dsTtvt[0].DONVI_ID : null
                this.veTinhId = null
                this.chkChuyen119 = false
                setTimeout(() => { this.$v.$reset() }, 100)
            }
        },
        onClickBtnTaiFileMau() {
            window.location.href = "/static/files/MauFile_ThueBao_DangKy_DauChuyen.xls"
        },
        async onClickBtnThemPTM() {
            //this.giaTriThemPTM.dsHoSoPTM.push({
            //    'TEN_NHOM':this.giaTriThemPTM.dsNhomTbi.find(x=>x.NHOMTBI_ID==this.giaTriThemPTM.nhomTbiId).NHOM_TBI,
            //    'TEN_LOAI':this.giaTriThemPTM.dsLoaiTbi.find(x=>x.LOAITBI_ID==this.giaTriThemPTM.loaiTbId).LOAI_TBI,
            //    'SOLUONG':this.giaTriThemPTM.soLuong
            //});

            if (!this.getHsccId()) {
                this.$toast.error('Chưa có thông tin hồ sơ bảo dưỡng.')
                return
            }

            let data = {
                hscc_id: this.getHsccId(),
                loaitbi_id:this.giaTriThemPTM.loaiTbId,
                dvitinh_id:1,
                soluong:this.giaTriThemPTM.soLuong
            }

            try {
                this.loading(true)
                await this.themHoSoPTM(data)
                this.isModified = true
                this.$toast.success('Đăng ký số lượng phần tử mạng bảo dưỡng thành công!')
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.napDsHoSoPTM();
        },
        async napThongTinHoSo() {
            try {
                this.loading(true)
                await this.layThongTinHoSo(this.getHsccId())
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.thongTinHoSo == null) {
                this.$toast.error('Hồ sơ không tồn tại')
                return
            }

            this.maHoSo = this.thongTinHoSo.MA_HS_CC
            this.tenHoSo = this.thongTinHoSo.TEN_HS
            this.loaiHoSoId = this.thongTinHoSo.LOAIHSCC_ID
            this.kieuCatChuyenId = this.thongTinHoSo.KIEUCC_ID
            this.tenNguoiDangKy = this.thongTinHoSo.NGUOI_DK
            this.ngayBatDau = new Date(this.thongTinHoSo.NGAYBD_DK)
            this.ngayKetThuc = new Date(this.thongTinHoSo.NGAYKT_DK)
            this.ttvtId = this.thongTinHoSo.TTVT_ID
            this.chkChuyen119 = this.thongTinHoSo.CHAN_BAOHONG == 1
            this.trangThaiId = this.thongTinHoSo.TTCC_ID

            if (this.trangThaiId == 6/*hoàn thành*/) {
                this.isReadonly = true
            }

            if (this.kieuCatChuyenId == 5/*không chuyển cáp*/) {
                this.showButton.thayDay = true
                this.showButton.doiThongTinCap = false
                this.showButton.chuyenVeTinh = false
                this.showButton.nhapCapHangLoat = false
            } else {
                this.showButton.thayDay = false
                this.showButton.doiThongTinCap = true
                this.showButton.chuyenVeTinh = true
                this.showButton.nhapCapHangLoat = true
            }
        },
        async napDsThueBaoDangKy() {
            try {
                this.loading(true)
                await this.layDsThueBaoDangKy(this.getHsccId())
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async layDsThueBaoDangKy(hsccId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/hoso-dauchuyen/danhsach-thuebao-dangky?hscc_id=${hsccId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => item.ALLOW_SELECT = [5/*Đang xử lý*/, 6/*Hoàn thành*/].indexOf(item.TTCC_ID) < 0)
                    this.dsThueBaoDangKy = response.data
                } else {
                    this.dsThueBaoDangKy = []
                }
            } catch (err) {
                this.dsThueBaoDangKy = []
            }
        },
        async napDsHoSoPTM(){
            try {
                let response =await this.$root.context.get("web-cabman/them-hoso-ptm/lay-ds-hoso-ptm", {hscc_id: this.getHsccId()})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.giaTriThemPTM.dsHoSoPTM = response.data
                } else {
                    this.giaTriThemPTM.dsHoSoPTM = []
                }
            } catch (err) {
                this.giaTriThemPTM.dsHoSoPTM = []
                this.$toast.error(err.message)
            }
        },
        async layThongTinHoSo(hsccId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/hoso-dauchuyen/thongtin-hoso?hscc_id=${hsccId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.thongTinHoSo = response.data
                } else {
                    this.thongTinHoSo = null
                }
            } catch (err) {
                this.thongTinHoSo = null
            }
        },
        async layDsDichVuVt() {
            try {
                let response = await this.$root.context.get('/web-cabman/danhmuc/layDsDichVuVtDangDung')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({DICHVUVT_ID: -1, TEN_DVVT: '---Tất cả---'})
                    this.dsDichVuVt = response.data
                } else {
                    this.dsDichVuVt = []
                }
            } catch (err) {
                this.dsDichVuVt = []
            }
        },
        async layDsTrangThaiCatChuyen() {
            try {
                let response = await this.$root.context.get('/web-cabman/danhmuc/layDsTrangThaiCatChuyenFull')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTrangThaiCatChuyen = response.data
                } else {
                    this.dsTrangThaiCatChuyen = []
                }
            } catch (err) {
                this.dsTrangThaiCatChuyen = []
            }
        },
        async layDsTtvt() {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsTTVTTheoNVTrongToken')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTtvt = response.data
                } else {
                    this.dsTtvt = []
                }
            } catch (err) {
                this.dsTtvt = []
            }
        },
        async layDsNhomTbi() {
            try {
                let response = await this.$root.context.get(`/web-ecms/danhmuc/layDsNhomTbi`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.giaTriThemPTM.dsNhomTbi = response.data
                    return
                }

                throw {response: { data: response}}
            } catch (err) {
                this.giaTriThemPTM.dsNhomTbi = []
            }
        },
        async layDsLoaiTbi(nhomTbiId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsLoaiTbiTheoNhom`,{id: nhomTbiId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.giaTriThemPTM.dsLoaiTbi = response.data
                    if (this.giaTriThemPTM.dsLoaiTbi.length > 0) {
                        this.giaTriThemPTM.loaiTbId = this.giaTriThemPTM.dsLoaiTbi[0].LOAITBI_ID
                    }
                    return
                }

                throw {response: { data: response}}
            } catch (err) {
                this.giaTriThemPTM.dsLoaiTbi = []
            }
        },
        async layDsKieuCatChuyen() {
            try {
                let response = await this.$root.context.get('/web-cabman/danhmuc/layDsKieuCatChuyen')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsKieuCatChuyen = response.data
                } else {
                    this.dsKieuCatChuyen = []
                }
            } catch (err) {
                this.dsKieuCatChuyen = []
            }
        },
        async layDsLoaiHoSo() {
            try {
                let response = await this.$root.context.get('/web-cabman/danhmuc/layDsLoaiHoSo')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    if (this.loaihs=='BAO_DUONG'){
                        this.dsLoaiHoSo = response.data.filter((e) => { return e.LOAIHSCC_ID == 5 })
                    }else{
                        this.dsLoaiHoSo = response.data.filter((e) => { return e.LOAIHSCC_ID != 5 })
                    }
                    //this.dsLoaiHoSo = response.data
                } else {
                    this.dsLoaiHoSo = []
                }
            } catch (err) {
                this.dsLoaiHoSo = []
            }
        },
        async layDmVeTinh (ttvtId) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsVeTinh', {id: ttvtId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    let dsVeTinh = response.data
                    let dsVeTinhRoot = []

                    if (dsVeTinh.length > 0) {

                        dsVeTinh = dsVeTinh.map(item => {
                            return {
                                id: item.IS_VETINH == 0 ? item.DONVI_ID : `${item.DONVI_ID}_${item.DONVI_CHA_ID}`,
                                label: item.TEN_DV,
                                pid: item.IS_VETINH == 0 ? 0 : item.DONVI_CHA_ID,
                                children: []
                            }
                        })

                        dsVeTinhRoot = dsVeTinh.filter(item => item.pid == 0)
                        dsVeTinhRoot.forEach(item => {
                            item.isDisabled = true
                            item.isDefaultExpanded = true
                        })

                        dsVeTinh.forEach(item => {

                            dsVeTinh.forEach(item1 => {
                                if (item1.pid == item.id) {
                                    item.children.push(item1)
                                }
                            })

                            if (item.children.length == 0) {
                                delete item.children
                            }
                        })
                    }

                    this.dsVeTinh = dsVeTinhRoot
                } else {
                    this.dsVeTinh = []
                }
            } catch (err) {
                this.dsVeTinh = []
            }
        },
        async themHoSo(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/themmoi-hoso', data)

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
        async capNhatHoSo(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/capnhat-hoso', data)

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
        async themThueBaoDangKyDauChuyen(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/them-thuebao-dk-dauchuyen', data)

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
        async themHoSoPTM(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/them-hs-ptm-dk-baoduong', data)

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
        async xoaThueBaoDangKyDauChuyen(dkccId) {
            try {
                let response = await this.$root.context.post(`/web-cabman/hoso-dauchuyen/xoa-dk-dauchuyen?dkcc_id=${dkccId}`)

                if (response.error_code != 'BSS-00000000') {
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
        async xoaHoSoPTM(hsptmId) {
            try {
                let response = await this.$root.context.post(`/web-cabman/hoso-dauchuyen/xoa-hs-ptm-dk-baoduong?hsptm_id=${hsptmId}`)

                if (response.error_code != 'BSS-00000000') {
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
        async capNhatDoDaiDayThueBao(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/capnhat-dodai-day-tb', data)

                if (response.error_code != 'BSS-00000000') {
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
        async layDoDaiDayThueBao(dkccId) {

            let cuLy = 0

            try {
                let response = await this.$root.context.get(`/web-cabman/hoso-dauchuyen/lay-dodai-day-thuebao?dkcc_id=${dkccId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    cuLy = response.data.CULY
                }
            } catch (err) {

            }

            return cuLy
        },
        async thayDayDsThueBao(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/thayday-ds-thuebao', data)

                if (response.error_code != 'BSS-00000000') {
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
        async chuyenVeTinhDsThueBao(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/chuyen-vetinh-ds-thuebao', data)

                if (response.error_code != 'BSS-00000000') {
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
        async dongBoTrangThai(hsccId) {
            try {
                let response = await this.$root.context.post(`/web-cabman/hoso-dauchuyen/dongbo-trangthai-hoso?hscc_id=${hsccId}`)

                if (response.error_code != 'BSS-00000000') {
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
        async hoanThienHoSo(hsccId) {
            try {
                let response = await this.$root.context.post(`/web-cabman/hoso-dauchuyen/hoanthien-hoso?hscc_id=${hsccId}`)

                if (response.error_code != 'BSS-00000000') {
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
        async hoanThienDauChuyen(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/hoanthien-dauchuyen', data)

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
        nhomThueBaoDangKyDauChuyen(dsThueBaoDk) {

            let dsDkccId = dsThueBaoDk.map(item => item.DKCC_ID)
            dsDkccId = [...new Set(dsDkccId)]

            let dsDkKhongTrung = []

            dsDkccId.forEach(item => {

                let dkcc = dsThueBaoDk.filter(item1 => item1.DKCC_ID == item)
                let dkccDuocNhom = dkcc[0]

                if (dkcc.length < 2) {
                    dsDkKhongTrung.push(dkccDuocNhom)
                    return
                }

                Object.keys(dkccDuocNhom).forEach(item1 => {

                    let value = []

                    dkcc.forEach(item2 => {
                        value.push(item2[item1])
                    })

                    value = [...new Set(value)]

                    if (value.length > 1) {
                        dkccDuocNhom[item1] = value.join(', ')
                    }
                })

                dsDkKhongTrung.push(dkccDuocNhom)
            })

            return dsDkKhongTrung
        }
    }
}
</script>
