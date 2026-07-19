<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import TraCuuTBi from ".././TraCuuTBi"
import VanHanh_KM from ".././VanHanhKM"
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import axios from 'axios'
import token from "@/plugins/token"
import Vue from 'vue'
export default {
    components: { TraCuuTBi, VanHanh_KM },
    name: 'TiepNhanLuuKhoTBPT',
    props: {
        id: String,
        Kieugoi: {
            type: Number,
            defaut: 0   //Kieu goi = 1 la tu form khac sang
        },
        thietbi_id: {
            type: Number,
            defaut: 0
        }
    },

    created() {
        EventBus.$on('onDownloadTNLK', this.onDownloadHandler);
        EventBus.$on('onViewTNLK', this.onViewHandler);
        EventBus.$on('onDeleteTNLK', this.onDeleteHandler);
        EventBus.$on('chapNhanTraCuu', this.onChapNhanTraCuuHandler);
    },

    destroyed () {      
        EventBus.$off('onDownloadTNLK', this.onDownloadHandler);
        EventBus.$off('onViewTNLK', this.onViewHandler);
        EventBus.$off('onDeleteTNLK', this.onDeleteHandler);
        EventBus.$off('chapNhanTraCuu', this.onChapNhanTraCuuHandler);
    },

    data() {
        return {
            txt_MaTs: '',
            txt_DonViQL: '',
            txt_NoiDung: '',
            txt_DeXuat: '',
            txt_GhiChu: '',
            txt_KetQuaTD: '',
            txt_KetQua: '',
            txt_TongTien: 0,
            date_NgayYC: new Date(),
            date_NgayHT: new Date(),
            today: new Date(),
            btn_Enabled: {
                layTT: true,
                nhapMoi: false,
                capNhat: false,
                xoa: false,
                huy: true,
                hoanThanh: false,
                tongTien: true
            },
            grid_TTThietBi: {
                items: []
            },
            grid_LichSu: {
                items: []
            },
            grid_TiepNhan: {
                items: [],
                selected: {}
            },
            grid_File: {
                items: []
            },
            vthietbi_id: 0,
            vvattu_id: 0,
            vdonvi_id: 0,
            vloaiVH_id: 4,
            vvanhanh_id: 0,
            vphieuvh_id: 0,
            vmota: '',
            ma_ts: '',
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
                            EventBus.$emit('onDownloadTNLK', this.data);
                        },
                        onDelete() {
                            EventBus.$emit('onDeleteTNLK', this.data);
                        },
                        onView() {
                            EventBus.$emit('onViewTNLK', this.data);
                        }
                    }})
                }
            }
        }
    },

    methods: {
        async onShownModal() {
            this.vthietbi_id = this.thietbi_id;
            this.ma_ts = this.thietbi_id.toString();

            await this.load_ds_tiepNhan(0);
            if (this.Kieugoi === 1) {
                this.clear();
                this.txt_MaTs = this.ma_ts;
                await this.onLayTT();
                this.setButton(1);
            }

            //Remove table header of file grid:
            let elemts = this.$refs.fileGrid.$el.querySelectorAll('.e-headercell');
            elemts.forEach(elment => {
                elment.style.display = 'none';
            });
            this.$refs.maTs.focus();
        },

        onHiddenModal() {
            this.clear();
            this.grid_TiepNhan.items = [];
            this.grid_File.items = [];
        },

        onLayMaTbi() {
            this.clear();
            this.$bvModal.show('formTraCuuTBi');
        },

        async onChapNhanTraCuuHandler(args) {
            if (args != null) {
                this.vthietbi_id = args.THIETBI_ID;
                this.txt_DonViQL = args.TEN_DV;
                this.txt_MaTs = args.THIETBI_ID;
                this.vdonvi_id = args.DONVI_ID;
                this.vvattu_id = args.VATTU_ID;
                await this.load_ThuocTinh(this.vvattu_id, this.vthietbi_id);
            }
        },

        onTongTien() {
            this.txt_TongTien = 0;
            this.$bvModal.show('formVanHanhKM');
        },

        async onChapNhanTongTien(args) {
            if (args != null) {
                this.txt_TongTien = args;
                await this.load_ds_tiepNhan(0);
            }
        },

        onChonFile(e) {
            if (e.target.files.length) {
                let ds = [];
                for (let file of e.target.files) {
                    let extension = file.name.slice(-3);
                    if (extension === 'exe' || extension === 'msi' || extension === 'bat' || extension === '.js')
                        return this.$toast.error('Vui lòng không upload file có định dạng .exe/.msi/.bat!');
                        
                    this.grid_File.items.push({
                        TENFILE: file.name,
                        TRANGTHAI: 0,
                        NOIDUNG: file
                    });
                }
                this.grid_File.items = [...this.grid_File.items];
            }
        },

        async onLayTT() {
            this.txt_MaTs = this.txt_MaTs.trim();
            if (this.txt_MaTs === '') {
                this.$toast.error('Mã tài sản không được để trống!');
                this.$refs.maTs.focus();
            } else {
                let rs = await this.getDsPhuTroTheoThietBi(this.txt_MaTs);
                if (rs.length > 0) {
                    this.txt_DonViQL = rs[0].TEN_DV;
                    this.vthietbi_id = rs[0].THIETBI_ID;
                    this.vdonvi_id = rs[0].DONVI_ID;
                    this.vvattu_id = rs[0].VATTU_ID;
                    await this.load_ThuocTinh(this.vvattu_id, this.vthietbi_id);
                }
                await this.grid_lichSu(this.vthietbi_id);
            }
        },

        onNhapMoi() {
            this.clear();
            this.setButton(1);
        },

        async onCapNhat() {
            if (this.btn_Enabled.nhapMoi) { //Cap nhat
                if (this.vvanhanh_id === 0)
                    return this.$toast.error('Không có thiết bị để cập nhập!');

                let mota = await this.moTa();
                let rs = await this.updateTrangThaiTiepNhan(2, 1, mota);
                let rs1 = await this.updateDonViVanHanh(0);
                if (rs & rs1)
                    this.$toast.success('Cập nhập thiết bị thành công!');
                await this.load_ds_tiepNhan(0);
                this.hienThi_duongDan(mota);
            } else {                        //Them moi
                if (this.vthietbi_id === 0)
                    return this.$toast.error('Không có thiết bị!');

                if (this.txt_MaTs === '')
                    return this.$toast.error('Chưa có mã tài sản!');
                
                if (this.grid_TiepNhan.items.length > 0) {
                    let ds = this.grid_TiepNhan.items.filter(e => e.LOAIVH_ID === this.vloaiVH_id && e.THIETBI_ID === this.vthietbi_id);
                    if (ds.length > 0)
                        return this.$toast.error('Thiết bị cập nhật bị trùng!');
                }

                let mota = await this.moTa();
                let rs = await this.updateTrangThaiTiepNhan(1, 1, mota);
                if (rs)
                    this.$toast.success('Tiếp nhận thiết bị thành công!');
                await this.load_ds_tiepNhan(0);
                this.hienThi_duongDan(mota);
                this.setButton(2);
            }
        },

        async onXoa() {
            if (this.vvanhanh_id === 0)
                this.$toast.error('Không có thiết bị để xóa!');
            else {
                try {                
                    await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa thiết bị?', {
                    "title": 'Thông báo',
                    "size": 'sm',
                    "buttonSize": 'md',
                    "okVariant": 'primary',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Không đồng ý',
                    "hideHeaderClose": false,
                    "centered": true,
                    "modal-class": ["f18","text-center"],
                    })
                    .then(async(value) => {
                        if (value){
                            let rs = await this.updateTrangThaiTiepNhan(3, 1, '');
                            if (rs)
                                this.$toast.success('Xóa dữ liệu thành công!');
                            this.clear();
                            await this.load_ds_tiepNhan(0);
                        }
                    })
                    .catch(err => {
                        console.log(err);
                    })
                } catch (error) {
                    console.log(error);
                    this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
                }
            }
        },

        onHuy() {
            this.setButton(2);
        },

        async onHoanThanh() {
            if (this.vvanhanh_id === 0)
                this.$toast.error("Không có thiết bị để hoàn thành!");
            else {
                let mota = await this.moTa();
                let rs = await this.updateTrangThaiTiepNhan(2, 6, mota);
                if (rs)
                    this.$toast.success('Cập nhập hoàn thành thiết bị thành công!');
                await this.load_ds_tiepNhan(0);
            }
        },

        async onTiepNhanGridSelected(args) {
            this.grid_TiepNhan.selected = args.data;
            if (args != null) {
                this.setButton(2);
                this.vvanhanh_id = args.data.VANHANH_ID;
                this.vphieuvh_id = args.data.PHIEUVH_ID;
                this.vthietbi_id = args.data.THIETBI_ID;
                this.vvattu_id = args.data.VATTU_ID;
                this.vmota = args.data.MOTA;
                this.txt_DeXuat = args.data.DEXUAT;
                this.txt_GhiChu = args.data.GHICHU;
                this.txt_NoiDung = args.data.NOIDUNG;
                this.txt_DonViQL = args.data.TEN_DV;
                this.txt_MaTs = args.data.THIETBI_ID;
                this.txt_TongTien = Number(args.data.THANHTIEN);
                this.txt_KetQuaTD = args.data.KETQUA_TD;
                this.txt_KetQua = args.data.KETQUA;
                this.date_NgayYC = args.data.NGAY_YC !== '' && args.data.NGAY_YC != null ? moment(args.data.NGAY_YC, 'DD/MM/YYYY').toDate() : null;
                this.date_NgayHT = args.data.NGAY_HT !== '' && args.data.NGAY_HT != null ? moment(args.data.NGAY_HT, 'DD/MM/YYYY').toDate() : null;
                this.hienThi_duongDan(this.vmota);
                await this.load_ThuocTinh(this.vvattu_id, this.vthietbi_id);
                await this.grid_lichSu(this.vthietbi_id);
            }
        },

        clear() {
            this.vthietbi_id = 0;
            this.date_NgayYC = new Date();
            this.date_NgayHT = new Date();
            this.txt_NoiDung = '';
            this.txt_DeXuat = '';
            this.txt_GhiChu = '';
            this.txt_KetQuaTD = '';
            this.txt_KetQua = '';
            this.txt_MaTs = '';
            this.txt_DonViQL = '';
            this.txt_TongTien = 0;
            this.grid_TTThietBi.items = [];
            this.grid_LichSu.item = [];
        },

        setButton(kieu) {
            if (kieu === 1) {
                this.btn_Enabled.tongTien = false;
                this.btn_Enabled.layTT = true;
                this.btn_Enabled.xoa = false;
                this.btn_Enabled.capNhat = true;
                this.btn_Enabled.hoanThanh = false;
                this.btn_Enabled.nhapMoi = false;
            } else if (kieu === 2) {
                this.btn_Enabled.layTT = true;
                this.btn_Enabled.xoa = true;
                this.btn_Enabled.capNhat = true;
                this.btn_Enabled.hoanThanh = true;
                this.btn_Enabled.nhapMoi = true;
                this.btn_Enabled.tongTien = true;
            }
        },

        async load_ThuocTinh(vatTuId, thietBiId) {
            let rs = await this.getTTThuocTinhTheoVatTuThietBi(vatTuId, thietBiId);
            if (rs.length > 0) {
                let ds = [];
                rs.forEach(item => {
                    let vttt = {
                        vattu_id: item.VATTU_ID,
                        ThuocTinh: item.THUOCTINH,
                        Ten_loai: item.TEN_LOAI,
                        CauLenh: item.CAULENH,
                        kieutt_id: item.KIEUTT_ID
                    };
                    
                    if (item.GIATRI != null) {
                        vttt.GiaTri = { Value: item.GIATRI, ten: null };
                        vttt.GiaTriCu = { Value: item.GIATRI, ten: null };
                    } else vttt.GiaTri = { Value: null, ten: null };

                    if (vttt.ThuocTinh === 'Địa chỉ')
                        vttt.Ten_loai = 'Button';
                    
                    if (item.TTVT_ID != null)
                        vttt.ttvt_id = item.TTVT_ID;
                    
                    if (item.GTTT_ID != null)
                        vttt.gttt_id = item.GTTT_ID;
                    
                    if (vttt.Ten_loai === 'Anh')
                        this.load_img(vttt.GiaTri.ten ? vttt.GiaTri.ten : vttt.GiaTri.Value);
                    
                    ds.push(vttt);
                });
                this.grid_TTThietBi.items = [...ds];
            }
        },

        load_img(fileAnh) {
            if (fileAnh && fileAnh !== '') {
                //Chưa code xong
            }
        },

        //Tham số vttvh_id không sử dụng trong procedure:
        async load_ds_tiepNhan(vthietbi_id) {
            await this.getDsTiepNhanThietBiV3(vthietbi_id, this.vloaiVH_id);
            if (this.grid_TiepNhan.items.length < 0) {
                this.clear();
                this.setButton(1);
            }
        },

        async grid_lichSu(vthietbi_id) {
            await this.getDsTiepNhanThietBi(vthietbi_id);
        },

        hienThi_duongDan(moTa) {
            if (moTa === '' || moTa == null)
                this.grid_File.items = [];
            else {
                moTa = moTa.slice(0, -1);
                let arr = moTa.split(';');
                let ds = [];
                arr.forEach(item => {
                    ds.push({
                        TENFILE: item,
                        TRANGTHAI: 0,
                        NOIDUNG: null
                    });
                });
                this.grid_File.items = [...ds];
            }
        },

        async moTa() {
            let moTa = '';
            if (this.grid_File.items.length > 0) {
                for (let item of this.grid_File.items) {
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

        async onDownloadHandler(args) {
            try {
                this.loading(true);
                if (args.NOIDUNG != null) { // Ảnh có trên grid nhưng chưa được up lên minio
                    var FileSaver = require("file-saver");
                    var file = new File([args.NOIDUNG], args.TENFILE, {
                        type: args.NOIDUNG.type
                    });
                    FileSaver.saveAs(file);
                } else {                    // Ảnh đã upload lên minio
                    let isExists = await this.checkExistsFileInMinio(args.TENFILE);
                    if (isExists) {
                        let rs = await this.getFileFromMinio(args.TENFILE);
                        let tenArr = args.TENFILE.split('/');
                        var newTab = window.open('', '_blank');
                        var fURL = newTab.document.createElement("a");
                        fURL.href = URL.createObjectURL(new Blob([rs], { type: 'application/octet-stream' }));
                        fURL.setAttribute("download", tenArr[tenArr.length - 1]);
                        newTab.document.body.appendChild(fURL);
                        fURL.click();
                        URL.revokeObjectURL(fURL.href)
                        newTab.document.body.removeChild(fURL);
                        newTab.close();
                    } else 
                        return this.$toast.error('Không có dữ liệu trên Server!');
                }
            } catch (error) {
                console.log(error);
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            } finally {
                this.loading(false);
            }
        },

        async onViewHandler(args) {
            if (args.NOIDUNG != null) { // Ảnh có trên grid nhưng chưa được up lên minio
                var file = new Blob([args.NOIDUNG], {type: args.NOIDUNG.type});
                var fileURL = URL.createObjectURL(file);
                window.open(fileURL);
            } else {                    // Ảnh đã upload lên minio
                let isExists = await this.checkExistsFileInMinio(args.TENFILE);
                if (isExists) {
                    let url = await this.getPresignedUrlFromMinio(args.TENFILE);
                    if (url != null && url !== '') {
                        var newTab = window.open('', '_blank');
                        var fURL = newTab.document.createElement("a");
                        fURL.href = url;
                        newTab.document.body.appendChild(fURL);
                        fURL.click();
                        newTab.document.body.removeChild(fURL);
                    } else return this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!');
                } else 
                    return this.$toast.error('Không có dữ liệu trên Server!');
            }
        },

        async onDeleteHandler(args) {
            if (args.NOIDUNG != null) { // Ảnh có trên grid nhưng chưa được up lên minio
                this.grid_File.items.splice(args.index, 1);
                this.grid_File.items = [...this.grid_File.items];
            } else {                    // Ảnh đã upload lên minio
                let rs = await this.deleteFileFromMinio(args.TENFILE);
                if (rs) {
                    let moTa_New = this.vmota.replaceAll(args.TENFILE + ';', '');
                    let rs1 = await this.updateMoTaGiaoPhieuVh(this.vphieuvh_id, moTa_New);
                    if (rs1) {
                        this.$toast.success('Xóa file thành công!');
                        let ds = this.grid_File.items.filter(e => e.TENFILE !== args.TENFILE);
                        this.grid_File.items = [...ds];
                    }
                }
                await this.load_ds_tiepNhan(0);
            }
        },
        
        async getDsPhuTroTheoThietBi(maTS) {
            try {
                this.loading(true);
                let url = `/web-ecms/tiepnhan_luukho/ds-phutro-theo-thietbi?maVT=${maTS}&kieu=${1}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data;
                } else return [];
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getTTThuocTinhTheoVatTuThietBi(vatTuId, thietBiId) {
            try {
                this.loading(true);
                let url = `/web-ecms/tiepnhan_luukho/tt-thuoctinh-theo-vattu-thietbi?vatTuId=${vatTuId}&thietBiId=${thietBiId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data;
                } else return [];
            }
            catch (error) {
                console.log(error);
                return [];
            }
            finally {
                this.loading(false);
            }
        },

        async getDsTiepNhanThietBiV3(thietBiId, loaiVhId) {
            try {
                this.loading(true);
                this.grid_TiepNhan.items = [];
                let url = `/web-ecms/tiepnhan_luukho/ds-tnhan-thietbi-v3?thietBiId=${thietBiId}&loaiVhId=${loaiVhId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    this.grid_TiepNhan.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsTiepNhanThietBi(thietBiId, loaiVhId) {
            try {
                this.loading(true);
                this.grid_LichSu.items = [];
                let url = `/web-ecms/tiepnhan_luukho/ds-tnhan-thietbi?thietBiId=${thietBiId}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    this.grid_LichSu.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async updateTrangThaiTiepNhan(kieu, ttvhId, moTa) {
            try {
                this.loading(true);
                let apiBody = {
                    kieu: kieu,
                    vanHanhId: this.vvanhanh_id,
                    loaiVhId: this.vloaiVH_id,
                    thietBiId: this.vthietbi_id,
                    noiDung: this.txt_NoiDung,
                    ngayYc: this.date_NgayYC != null ? moment(this.date_NgayYC).format('DD/MM/YYYY') : null,
                    ketQuaTd: this.txt_KetQuaTD,
                    ketQua: this.txt_KetQua,
                    ttvhId: ttvhId,
                    ngayHt: this.date_NgayHT != null ? moment(this.date_NgayHT).format('DD/MM/YYYY') : null,
                    ghiChu: this.txt_GhiChu,
                    deXuat: this.txt_DeXuat,
                    moTa: moTa,
                    phieuVhId: this.vphieuvh_id,
                    donViNhanId: 0
                };
                let url = `/web-ecms/tiepnhan_luukho/update-trangthai-tiepnhan`;
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

        async updateDonViVanHanh(donViNhanId) {
            try {
                this.loading(true);
                let apiBody = {
                    vanHanhId: this.vvanhanh_id,
                    donViNhanId: donViNhanId
                };
                let url = `/web-ecms/tiepnhan_luukho/update-donvi-vanhanh`;
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

        async checkExistsFileInMinio(src) {
            try {
                let url = `/web-ecms/storage/checkExistsFile`;
                let response = await this.$root.context.post(url, { fileSource: src });
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.result;
                } else return false;
            } 
            catch(error) {
                console.log(error);
                return false;
            }
            finally {}
        },

        async getFileFromMinio(src) {
            try {
                let url = `/web-ecms/storage/downloadFile?fileSource=${src}`;
                let response = await this.getOriginal(url);
                return response.data;
            } 
            catch(error) {
                console.log(error);
                return null;
            }
            finally {}
        },

        async getOriginal(url) {
            var instance = axios.create();
            instance.interceptors.request.use(request => {
                request.baseURL = process.env.API;
                request.headers = {
                    "Content-Type": "application/json",
                    Authorization: token.getAuthorization()
                };
                request.data = {};
                request.responseType = 'blob';
                return request;
            });
            var begin = Date.now();
            var r = await instance.get(url);
            if (process.env.NODE_ENV !== "production") {
                console.info(
                    "url: " + url + " (" + (Date.now() - begin) + "ms)"
                );
            }
            return r;
        }
    }
}
</script>
