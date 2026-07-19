<template src="./index.template.html"></template>
<script>
import TraCuuTBi from ".././TraCuuTBi"
import VanHanh_KM from ".././VanHanhKM"
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import Vue from 'vue'
export default {
    components: { TraCuuTBi, VanHanh_KM },
    data() {
        return {
            cbo_donvi: [],
            cbo_tramTC: [],
            cbo_tramVT: [],
            cbo_thietBiCha: [],
            gridTiepNhan: [],
            gridLichSu: [],
            gridValue: [],
            gcDSFile: [],
            btnEnabled: {
                tongTien: false,
                tiepNhan: false,
                layTT: false,
                xoa: false,
                capNhap: false,
                hoanThanh: false,
                nhapMoi: false
            },
            kieuGoi: 0, //gọi từ frm khác kieuGoi = 1
            vdonvi_id: 0,
            vdonvicon_id: 0,
            vvanhanh_id: 0,
            vphieuvh_id: 0,
            ma_ts: "",
            vthietbi_id: 0,
            vvattu_id: 0,
            ngayYC: null,
            ngayHT: null,
            noiDung: "",
            deXuat: "",
            ghiChu: "",
            ketQuaTD: "",
            ketQua: "",
            donViQL: "",
            tongTien: "",
            vmota: "",
            donvi_nhan_id: 0,
            thietbi_cha_id: 0,
            chuyen_tbcon: false,
            today: new Date(),
            actionTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: ` <div>
                                    <button class="btn btn-primary lh14 pad3" @click="onDownload" title="Tải file">
                                        <span class="-ap icon-download"></span>
                                    </button>
                                    <button class="btn btn-danger lh14 pad3" @click="onDelete" title="Xóa file">
                                        <span class="-ap icon-close"></span>
                                    </button>
                                    <button class="btn btn-success lh14 pad3" @click="onView" title="Xem file">
                                        <span class="icon nc-icon-outline ui-1_eye-17"></span>
                                    </button>
                                </div>`,                        
                    data () { 
                        return { data: {} }; 
                    },
                    methods: {
                        onDownload() {                  
                            EventBus.$emit('onDownload', this.data);
                        },
                        onDelete() {
                            EventBus.$emit('onDelete', this.data);
                        },
                        onView() {
                            EventBus.$emit('onView', this.data);
                        }
                    }})
                }
            }
        }
    },
    async mounted() {
        try {
            EventBus.$on("chapNhanTraCuu", (data) => {
                this.onChapNhanMaTbi(data)
            });
            EventBus.$on("chapNhanTongTien", (data) => {
                this.onChapNhanTongTien(data)
            });
        } catch (error) {
        } finally {
        }
        console.log(this.$auth.getNhanVienID())

        EventBus.$on('onDownload', this.onDownloadHandler);
        EventBus.$on('onView', this.onViewHandler);
        EventBus.$on('onDelete', this.onDeleteHandler);
        
    },
    destroyed() {
        EventBus.$off("chapNhanTraCuu");
        EventBus.$off("chapNhanTongTien");
        EventBus.$off('onDownload', this.onDownloadHandler);
        EventBus.$off('onView', this.onViewHandler);
        EventBus.$off('onDelete', this.onDeleteHandler);
    },
    methods: {
        onHiddenModal() {
            this.clear()
            this.gridTiepNhan = []
        },
        async onShownModal() {
            this.loading(true)
            let elemts = this.$refs.fileGrid.$el.querySelectorAll('.e-headercell');
            elemts.forEach(elment => {
                elment.style.display = 'none';
            });
            await this.loadCboTramTC(this.vdonvi_id, 5)
            await this.loadCboTramVT(this.vdonvi_id, 25)
            await this.loadCboThietBiCha(this.vvattu_id, this.donvi_nhan_id)
            await this.loadForm()
            this.loading(false)
        },
        async phuTroLayDSTheoTBi(ma_vt) {
            try {
                let response = await this.$root.context.get(`/web-ecms/quanlythietbiphutro/phutro_lay_ds_theo_tbi?ma_vt=${ma_vt}&kieu=1`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }
            } catch(err) {
                console.log(err)
            }
        },
        async phuTroLayDSTiepNhanTBi(vttvh_id, vthietbi_id) {
            try {
                let apiBody = {
                    ttvh_id: vttvh_id,
                    thietbi_id: vthietbi_id,
                    nhanvien_id: this.$auth.getNhanVienID(),
                }
                let response = await this.$root.context.post(`/web-ecms/vanhanh-thuongxuyen/phutro-lay-ds-tiepnhan-tbi`, apiBody)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.gridLichSu = response.data
                    this.gridLichSu.map(e => {
                        e.NGAY_YC = moment(e.NGAY_YC).format("DD/MM/YYYY")
                        e.NGAY_HT = moment(e.NGAY_HT).format("DD/MM/YYYY")
                    })
                }
            } catch(err) {
                this.gridLichSu = []
                console.log(err)
            }
        },
        async phuTroLayDSTiepNhanTBiV3() {
            try {
                // let apiBody = {
                //     ttvh_id: 1,
                //     thietbi_id: 0,
                //     nhanvien_id: this.$auth.getNhanVienID(),
                //     loaivh_id: 3
                // }
                let response = await this.$root.context.get(`/web-ecms/tiepnhan_luukho/ds-tnhan-thietbi-v3?thietBiId=0&loaiVhId=3`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.gridTiepNhan = response.data
                    // this.gridTiepNhan.map(e => e.NGAY_YC = moment(e.NGAY_YC).format("DD/MM/YYYY"))
                }
            } catch(err) {
                this.gridTiepNhan = []
                console.log(err)
            }
        },
        async loadCboDonVi(donvi_id, loaidv_id) {
            try {
                let response = await this.$root.context.get(`/web-ecms/tiepnhan-dieuchuyen/lay-ds-donvi?donvi_id=${donvi_id}&loaidv_id=${loaidv_id}`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    // console.log(response.data)
                    this.cbo_donvi = response.data
                    this.vdonvi_id = this.cbo_donvi[0].DONVI_ID
                }
            } catch(err) {
                this.cbo_donvi = []
                this.vdonvi_id = 0
            }
        },
        async loadCboTramTC(donvi_id, loaidv_id) {
            try {
                let response = await this.$root.context.get(`/web-ecms/tiepnhan-dieuchuyen/lay-ds-donvicon?donvi_id=${donvi_id}&loaidv_id=${loaidv_id}`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    // console.log(response.data)
                    this.cbo_tramTC = response.data
                    this.vdonvicon_id = this.cbo_tramTC[0].DONVI_ID
                }
            } catch(err) {
                this.cbo_tramTC = []
                this.vdonvicon_id = 0
            }
        },
        async loadCboTramVT(donvi_id, loaidv_id) {
            try {
                let response = await this.$root.context.get(`/web-ecms/tiepnhan-dieuchuyen/lay-ds-donvi-v2?donvi_id=${donvi_id}&loaidv_id=${loaidv_id}`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    // console.log(response.data)
                    this.cbo_tramVT = response.data
                    this.donvi_nhan_id = this.cbo_tramVT[0].DONVI_ID
                }
            } catch(err) {
                this.cbo_tramVT = []
                this.donvi_nhan_id = 0
            }
        },
        async loadCboThietBiCha(vattu_id, donvi_id) {
            try {
                let response = await this.$root.context.get(`/web-ecms/tiepnhan-dieuchuyen/lay-ds-thietbi-cha?vvattu_id=${vattu_id}&vdonvi_id=${donvi_id}`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    // console.log(response.data)
                    this.cbo_thietBiCha = response.data
                }
            } catch(err) {
                this.cbo_thietBiCha = []
            }
        },
        async layTTThuocTinhTheoVatTuTBi(vattu_id, vthietbi_id) {
            try {
                let response = await this.$root.context.get(`/web-ecms/tiepnhan-dieuchuyen/lay-tt-thuoctinh-theo-vtu-tbi?vattu_id=${vattu_id}&thietbi_id=${vthietbi_id}`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                }
            } catch (err) {
                console.log(err)
            }
        },
        async phuTroTrangThaiTiepNhan(kieu, mota) {
            try {
                this.loading(true)
                let apiBody = {
                    kieu: kieu,
                    vanHanhId: this.vvanhanh_id,
                    loaiVhId: 3,
                    thietBiId: this.vthietbi_id,
                    noiDung: this.noiDung,
                    ngayYc: this.ngayYC != null ? moment(this.ngayYC).format('DD/MM/YYYY') : null,
                    ketQuaTd: this.ketQuaTD,
                    ketQua: this.ketQua,
                    ttvhId: 6,
                    ngayHt: this.ngayHT != null ? moment(this.ngayHT).format('DD/MM/YYYY') : null,
                    ghiChu: this.ghiChu,
                    deXuat: this.deXuat,
                    moTa: mota,
                    phieuVhId: this.vphieuvh_id,
                    donViNhanId: this.donvi_nhan_id
                }
                let response = await this.$root.context.post(`/web-ecms/tiepnhan_luukho/update-trangthai-tiepnhan`, apiBody)
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return true
                    // console.log(response.data)
                    // return response.data.startsWith('OK');
                } return false
            } catch(err) {
                console.log(err)
                return false
            } finally {
                this.loading(false)
            }
        },
        async phuTroTrangThaiTiepNhanV2(kieu, mota, chuyen_tbcon) {
            try {
                this.loading(true)
                let apiBody = {
                    vkieu: kieu,
                    vvanhanh_id: this.vvanhanh_id,
                    vloaivh_id: 3,
                    vthietbi_id: this.vthietbi_id,
                    vnoidung: this.noiDung,
                    vngay_yc: this.ngayYC != null ? moment(this.ngayYC).format("DD/MM/YYYY") : null,
                    vdonvi_id: this.vdonvi_id,
                    vketqua_td: this.ketQuaTD,
                    vketqua: this.ketQua,
                    vttvh_id: 1,
                    vngay_ht: this.ngayHT != null ? moment(this.ngayHT).format("DD/MM/YYYY") : null,
                    vghichu: this.ghiChu,
                    vngay_cn: moment(new Date()).format("DD/MM/YYYY"),
                    vdexuat: this.deXuat,
                    vmota: mota,
                    vphieuvh_id: this.vphieuvh_id,
                    vdonvi_nhan_id: this.donvi_nhan_id,
                    vthietbi_cha_id: this.thietbi_cha_id,
                    vchuyen_tbcon: chuyen_tbcon
                }
                let response = await this.$root.context.post(`/web-ecms/tiepnhan-dieuchuyen/phutro-capnhap-trangthai-tiepnhan`, apiBody)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    // console.log(response.data)
                    return true
                    // return response.data.startsWith('OK')
                } else {
                    return false
                }
            } catch(err) {
                console.log(err)
                return false
            } finally {
                this.loading(false)
            }
        },
        async updateMoTaGiaoPhieuVh(phieuVhId, moTa) {
            try {
                this.loading(true);
                let apiBody = {
                    phieuVhId: phieuVhId,
                    moTa: moTa
                };
                let url = `/web-ecms/tiepnhan_luukho/update-mota-giaophieu-vh`;
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.startsWith('OK');
                } return false;
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        },
        setButton(kieu) {
            if (kieu == 1) {
                this.btnEnabled.tongTien = false
                this.btnEnabled.layTT = true
                this.btnEnabled.xoa = false
                this.btnEnabled.capNhap = true
                this.btnEnabled.hoanThanh = false
                this.btnEnabled.nhapMoi = false
            } 
            if (kieu == 2) {
                this.btnEnabled.layTT = true
                this.btnEnabled.xoa = true
                this.btnEnabled.capNhap = true
                this.btnEnabled.nhapMoi = true
                this.btnEnabled.tongTien = true
                this.btnEnabled.hoanThanh = true
            }
        },
        clear() {
            this.ma_ts = ""
            this.vthietbi_id = 0
            this.ngayYC = this.today
            this.ngayHT = this.today
            this.noiDung = "",
            this.deXuat = "",
            this.ghiChu = "",
            this.ketQuaTD = "",
            this.ketQua = "",
            this.donViQL = "",
            this.tongTien = ""
            this.gridLichSu = []
            this.gridValue = []
            this.gcDSFile = []
        },
        async loadDSTiepNhan() {
            await this.phuTroLayDSTiepNhanTBiV3()
            if (this.gridTiepNhan.length > 0) {
                this.setButton(2)
            }
        },
        async loadGridLichSu(vttvh_id, vthietbi_id) {
            await this.phuTroLayDSTiepNhanTBi(vttvh_id, vthietbi_id)
        },
        async loadForm() {
            await this.loadCboDonVi(0,3) //DONVIQL_LD = 3
            await this.loadDSTiepNhan()
            if (this.kieuGoi == 1) {
                let ma_ts = this.ma_ts
                this.clear()
                this.ma_ts = ma_ts
                await this.layTT()
                this.setButton(1)
            }
        },
        async loadGrid(a) {
            let dt = await this.phuTroLayDSTheoTBi(a)
            if (dt != null && dt.length > 0) {
                this.donViQL = dt[0].TEN_DV
                this.vthietbi_id = dt[0].THIETBI_ID
                this.donvi_id = dt[0].DONVI_ID
                this.vattu_id = dt[0].VATTU_ID 
                await this.loadThuocTinh(this.vattu_id, this.vthietbi_id)
            }
        },
        async loadThuocTinh(vattu_id, thietbi_id) {
            // let rs = []
            let rs = await this.layTTThuocTinhTheoVatTuTBi(vattu_id, thietbi_id)
            if (rs.length > 0) {
                let ds = []
                rs.forEach(element => {
                    let vttt = {
                        vattu_id: element.VATTU_ID,
                        ThuocTinh: element.THUOCTINH,
                        Ten_loai: element.TENLOAI,
                        CauLenh: element.CAULENH,
                        kieutt_id: element.KIEUTT_ID
                    }

                    if (element.GIAITRI != null) {
                        vttt.GiaTri = { Value: element.GIATRI, ten: null }
                        vttt.GiaTriCu = { Value: element.GIATRI, ten: null }
                    } else {
                        vttt.GiaTri = { Value: element.GIATRI, ten: null }
                    }

                    if (vttt.ThuocTinh === "Địa chỉ") {
                        vttt.Ten_loai = "Button"
                    }

                    if (element.TTVT_ID != null) {
                        vttt.ttvt_id = element.TTVT_ID
                    }

                    if (element.GTTT_ID != null) {
                        vttt.gttt_id = element.GTTT_ID
                    }

                    if (vttt.Ten_loai == "Anh") {
                        this.loadImg(vttt.GiaTri.ten ? vttt.GiaTri.ten : vttt.GiaTri.Value)
                    }

                    ds.push(vttt)
                })
                this.gridValue = [...ds]
            }
        },
        loadImg(fileAnh) {

        },
        async moTa() {
            let moTa = '';
            if (this.gcDSFile.length > 0) {
                for (let item of this.gcDSFile) {
                    if (item.NOIDUNG != null) { //Ảnh mới
                        let url = await this.uploadFileToMinio(item.NOIDUNG);
                        if (url == null)
                            return this.$toast.error('Upload file lên Minio thất bại!');
                        else moTa += url.relativeUrl + ';';
                    } else { //Ảnh cũ
                        if (item.TENFILE !== '')
                            moTa += item.TENFILE + ';';
                        else moTa = '';
                    }
                }
            }
            return moTa;
        },
        hienThiDuongDan(mota) {
            if (mota === "" || mota == null) {
                this.gcDSFile = []
            } else {
                let mota1 = mota.slice(0, -1)
                let arr_mota = mota1.split(";")
                let ds = [];
                arr_mota.forEach(item => {
                    ds.push({
                        TENFILE: item,
                        TRANGTHAI: 0,
                        NOIDUNG: null
                    });
                });
                this.gcDSFile = [...ds];
            }
        },
        onChonFile(e) {
            if (e.target.files.length) {
                for (let file of e.target.files) {
                    this.gcDSFile.push({
                        TENFILE: file.name,
                        TRANGTHAI: 0,
                        NOIDUNG: file
                    });
                }
                this.gcDSFile = [...this.gcDSFile];
            }
        },
        async onDownloadHandler(args) {
            try {
                let url = await this.getPresignedUrlFromMinio(args.TENFILE);
                if (url == null || url === '')
                    return this.$toast.error('Không có dữ liệu trên Server!');

                this.loading(true);
                var newTab = window.open('', '_blank');
                var fURL = newTab.document.createElement("a");
                fURL.href = url;
                fURL.setAttribute("download", args.TENFILE);
                newTab.document.body.appendChild(fURL);
                fURL.click();
                newTab.document.body.removeChild(fURL);
            } catch (error) {
                console.log(error);
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            } finally {
                this.loading(false);
            }
        },

        async onViewHandler(args) {
            try {
                let url = await this.getPresignedUrlFromMinio(args.TENFILE);
                if (url == null || url === '')
                    return this.$toast.error('Không có dữ liệu trên Server!');

                this.loading(true);
                var newTab = window.open('', '_blank');
                var fURL = newTab.document.createElement("a");
                fURL.href = url;
                fURL.setAttribute("download", args.TENFILE);
                newTab.document.body.appendChild(fURL);
                fURL.click();
                newTab.document.body.removeChild(fURL);
            } catch (error) {
                console.log(error);
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            } finally {
                this.loading(false);
            }
        },

        async onDeleteHandler(args) {
            let rs = await this.deleteFileFromMinio(args.TENFILE);
            if (rs) {
                let moTa_New = this.vmota.replaceAll(args.TENFILE + ';', '');
                let rs1 = await this.updateMoTaGiaoPhieuVh(this.vphieuvh_id, moTa_New);
                if (rs1)
                    this.$toast.success('Xóa file thành công!');
            }
            await this.loadDSTiepNhan()
        },
        gridTiepNhanSelectedRowChanged: async function (val, index) {
            if (val) {
                this.loading(true)
                this.vvanhanh_id = val.VANHANH_ID
                this.vphieuvh_id = val.PHIEUVH_ID
                this.deXuat = val.DEXUAT
                this.ghiChu = val.GHICHU
                this.noiDung = val.NOIDUNG
                this.ngayYC = val.NGAY_YC != null ? moment(val.NGAY_YC, "DD/MM/YYYY").toDate() : null
                this.donViQL = val.TEN_DV
                this.vattu_id = val.VATTU_ID
                this.ma_ts = val.THIETBI_ID
                this.vthietbi_id = val.THIETBI_ID
                this.tongTien = val.THANHTIEN
                this.ketQua = val.KETQUA 
                this.ketQuaTD = val.KETQUA_TD
                this.ngayHT = val.NGAY_HT != null ? moment(val.NGAY_HT, "DD/MM/YYYY").toDate() : null
                this.vmota = val.MOTA
                this.hienThiDuongDan(this.vmota)
                await this.loadThuocTinh(this.vattu_id, this.vthietbi_id)
                await this.loadGridLichSu(0, this.vthietbi_id)
                this.setButton(2)
                this.loading(false)
            }
        },
        async layTT() {
            if (this.ma_ts == "") {
                this.$root.toastError("Mã tài sản không được trống!")
                return
            }
            await this.loadGrid(this.ma_ts)
            await this.loadGridLichSu(0, this.vthietbi_id)
        },
        nhapMoi() {
            this.clear()
            this.setButton(1)
        },
        async capNhap() {
            let vchuyen_tbcon = this.chuyen_tbcon ? 1 : 0 //1: dieu chuyen thiet bi con, 2: dieu chuyen thiet bi cha
            if (this.btnEnabled.nhapMoi) {
                if (this.vvanhanh_id == 0) {
                    this.$root.toastError("Không có thiết bị để cập nhập!")
                    return
                }
                if (this.donvi_nhan_id == 0) {
                    this.$root.toastError("Bạn chưa chọn đơn vị nhận!")
                    return
                }
                if (this.chuyen_tbcon) {
                    if (this.thietbi_cha_id == 0) {
                        this.$root.toastError("Bạn chưa chọn thiết bị cha!")
                        return
                    } 
                }
                let mota = await this.moTa()
                let rs = await this.phuTroTrangThaiTiepNhanV2(2, mota, vchuyen_tbcon)
                if (rs) {
                    this.$root.toastSuccess("Cập nhập thiết bị thành công!")
                    this.loadDSTiepNhan()
                    this.hienThiDuongDan(mota)
                }
            } else {
                if (this.vthietbi_id == 0) {
                    this.$root.toastError("Không có thiết bị!")
                    return
                }
                if (this.ma_ts == "") {
                    this.$root.toastError("Bạn chưa có mã tài sản!")
                    return
                }
                if (this.donvi_nhan_id == 0) {
                    this.$root.toastError("Bạn chưa chọn đơn vị nhận!")
                    return
                }
                if (this.chuyen_tbcon) {
                    if (this.thietbi_cha_id == 0) {
                        this.$root.toastError("Bạn chưa chọn thiết bị cha!")
                        return
                    } 
                }
                this.vvanhanh_id = 0
                if (this.gridTiepNhan != null) {
                    let aa = this.gridTiepNhan.filter(e => e.LOAIVH_ID == 3 && e.THIETBI_ID == this.vthietbi_id)
                    if (aa.length > 0) {
                        this.$root.toastError("Thiết bị cập nhật bị trùng!")
                        return
                    }
                }
                let mota = await this.moTa()
                let rs = await this.phuTroTrangThaiTiepNhanV2(1, mota, vchuyen_tbcon)
                if (rs) {
                    this.loadDSTiepNhan()
                    this.hienThiDuongDan(mota)
                    this.$root.toastSuccess("Tiếp nhận thiết bị thành công!")
                    this.setButton(2)
                }
            }
        },
        async xoa() {
            if (this.vvanhanh_id != 0) {
                await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa thiết bị?', {
                    "title": 'Thông báo',
                    "size": 'md',
                    "buttonSize": 'md',
                    "okVariant": 'primary',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Không đồng ý',
                    "hideHeaderClose": false,
                    "centered": true,
                    "modal-class": ["f18","text-center"],
                    }).then(async (value) => {
                        let vchuyen_tbcon = this.chuyen_tbcon ? 1 : 0
                        let mota = await this.moTa()
                        let rs = await this.phuTroTrangThaiTiepNhanV2(3, mota, vchuyen_tbcon)
                        if (rs) {
                            this.clear()
                            this.loadDSTiepNhan()
                        }
                    })
            } else {
                this.$root.toastError("Không có thiết bị để xóa!")
                return
            }
        },
        huy() {
            this.setButton(2)
        },
        async hoanThanh() {
            if (this.vvanhanh_id == 0) {
                this.$root.toastError("Không có thiết bị để hoàn thành")
                return
            }
            let mota = await this.moTa()
            let rs = await this.phuTroTrangThaiTiepNhan(2, mota)
            if (rs) {
                this.loadDSTiepNhan()
                this.$root.toastSuccess("Cập nhập hoàn thành thiết bị thành công!")
            }
            
        },
        onLayMaTbi() {
            this.clear();
            this.$bvModal.show('formTraCuuTBi');
        },
        async onChapNhanMaTbi(args) {
            if (args != null) {
                this.loading(true)
                this.vthietbi_id = args.THIETBI_ID
                this.donViQL = args.TEN_DV
                this.ma_ts = args.THIETBI_ID
                this.vdonvi_id = args.DONVI_ID
                this.vvattu_id = args.VATTU_ID
                await this.loadThuocTinh(this.vvattu_id, this.vthietbi_id)
                this.loading(false)
            }
        },
        onTongTien() {
            this.tongTien = 0;
            this.$bvModal.show('formVanHanhKM');
        },

        async onChapNhanTongTien(args) {
            if (args != null) {
                this.tongTien = args;
                await this.loadDSTiepNhan();
            }
        },
        async uploadFileToMinio(item) {
            try {
                this.loading(true);
                let formData = new FormData();
                formData.append("file", item);
                let url = `/web-ecms/storage/uploadFile`;
                let response = await this.$root.context.post(url, formData);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    if (response.data.status) {
                        return response.data.message;
                    } else return null;
                } else return null;
            } 
            catch(error) {
                console.log(error);
                this.$toast.error('Có lỗi xảy ra trong quá trình upload file!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },
        async getPresignedUrlFromMinio(src) {
            try {
                this.loading(true);
                let url = `/web-ecms/storage/getPresignedUrl`;
                let response = await this.$root.context.post(url, { fileSource: src });
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.result;
                } else return null;
            } 
            catch(error) {
                console.log(error);
                return null;
            }
            finally {
                this.loading(false);
            }
        },
        async deleteFileFromMinio(src) {
            try {
                this.loading(true);
                let url = `/web-ecms/storage/deleteFile`;
                let response = await this.$root.context.post(url, { fileSource: src });
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    if (!response.data.status)
                        this.$toast.error(response.data.message);
                    return response.data.status;
                } else return false;
            } 
            catch(error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        },
    },
    watch: {
        "vdonvi_id": async function() {
            if (this.vdonvi_id != null) {
                await this.loadCboTramTC(this.vdonvi_id, 5) //TRAM_VT = 5
            }
        },
        "vdonvicon_id": async function() {
            if (this.vdonvicon_id != null) {
                await this.loadCboTramVT(this.vdonvicon_id, 25) //TRAM_VETINH = 25
            }
        },
        "donvi_nhan_id": async function() {
            if (this.donvi_nhan_id != null) {
                await this.loadCboThietBiCha(this.vvattu_id, this.donvi_nhan_id)
            }
        }
    },
}
</script>
<style>
.modal-fullscreen .modal-dialog {
    max-width: 90% !important;
    margin: 0;
    top: 5% !important;
    bottom: 0;
    left: 5% !important;
    right: 0;
    height: 100vh;
    display: flex;
    position: fixed;
    z-index: 100000;
}
div#frmDieuChuyen___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
a.disabled {
    color: lightgrey !important;
    pointer-events: none;
    cursor: default;
    text-decoration: none;
}
</style>