<template src="./index.html">
</template>

<script>
import DateTimeLib from "../../../../utils/DateTimeLib";
import VanHanh_KM from ".././VanHanhKM";
import TraCuuTBi from ".././TraCuuTBi";
import EventBus from "../../../../utils/eventBus";
import FormGiaoPhieuBaoHongThietBi from "../GiaoPhieuBaoHongThietBi"
export default {
    components: {
        VanHanh_KM,
        TraCuuTBi,
        FormGiaoPhieuBaoHongThietBi
    },
    props: {
        modalId: String,
        kieugoi: 0, // Kieu goi = 1 la tu form khac sang
        ma_ts: Number,
    },
    data: function() {
        return {
            vthietbi_id: 0,
            vvattu_id: 0,
            vdonvi_id: 0,
            vvanhanh_id: 0,
            vphieuvh_id: 0,
            vloaiVH_id: 2,
            dtpNgayYC: null,
            dtpNgayHT: null,
            txtNoiDung: null,
            txtDeXuat: null,
            txtGhichu: null,
            txtKetQuaTD: null,
            txtKetQua: null,
            txtMaTs: null,
            txtdonviql: null,
            txtTongtien: null,

            currentItem:{},
            waterMark: "Chọn ngày",
            dateVal:  Date,
            dateFormat: "dd/MM/yyyy",

            isEnable_btnTongtien: true,
            isEnable_tbtnLaytt: true,
            isEnable_btnXoa: true,
            isEnable_btnGiaophieu: true,
            isEnable_btnCapnhap: true,
            isEnable_btnhoanthanh: true,
            isEnable_btnNhapmoi: true,

            dsTiepNhan: [],
            dsLichSu: [],
            dsThuocTinh: [],

            grid_File: {
                items: []
            },
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
    methods: {
        showForm: async function() {
            await this.load_ds_tiepnhan(1, 0);
            if(this.kieugoi == 1) {
                this.clear();
                this.txtMaTs = this.ma_ts;
                await this.tsbtnLaytt_Click();
                this.setbutton(1);
            }
        },
        load_ds_tiepnhan: async function(vttvh_id, vthietbi_id) {
            try {
                let rs = await this.$root.context.post('/web-ecms/quanlythietbiphutro/phutro_lay_ds_tnhan_tbi_v3', {
                    ttvh_id: vttvh_id,
                    thietbi_id: vthietbi_id,
                    loaivh_id: this.vloaiVH_id
                })
                if(rs.data.length > 0) {
                    this.dsTiepNhan = rs.data;
                }
                else {
                    this.clear();
                    this.setbutton(1);
                }
            } catch (error) {
                this.$root.toastError('Có lỗi lấy danh sách tiếp nhận');
            }
        },
        clear: function() {
            this.dtpNgayYC = new Date();
            this.dtpNgayHT = new Date();
            this.txtNoiDung = null;
            this.txtDeXuat = null;
            this.txtGhichu = null;
            this.txtKetQuaTD = null;
            this.txtKetQua = null;
            this.txtMaTs = null;
            this.txtdonviql = null;
            this.txtTongtien = null;
            this.txtKetQuaTD = null;
            this.txtKetQua = null;
            this.dsLichSu = [];
            this.dsThuocTinh = [];
        },
        setbutton: function(val) {
            if(val == 1) {
                this.isEnable_btnTongtien = false;
                this.isEnable_tbtnLaytt = true;
                this.isEnable_btnXoa = false;
                this.isEnable_btnGiaophieu = false;
                this.isEnable_btnCapnhap = true;
                this.isEnable_btnhoanthanh = false;
                this.isEnable_btnNhapmoi = false;
            }
            else if(val == 2) {
                this.isEnable_tbtnLaytt = true;
                this.isEnable_btnXoa = true;
                this.isEnable_btnGiaophieu = true;
                this.isEnable_btnCapnhap = true;
                this.isEnable_btnNhapmoi = true;
                this.isEnable_btnTongtien = true;
                this.isEnable_btnhoanthanh = true;
            }
        },
        tsbtnLaytt_Click: async function() {
            if(!this.txtMaTs) {
                this.$root.toastError('Mã tài sản không được trống!');
                return;
            }
            await this.load_grid(this.txtMaTs);
            await this.grid_lichsu(0, this.vthietbi_id);
        },
        load_grid: async function(mats) {
            try {
                this.dsThuocTinh = [];
                let rs = await this.$root.context.get(`/web-ecms/quanlythietbiphutro/phutro_lay_ds_theo_tbi?ma_vt=${mats}&kieu=1`);
                if(rs.data.length > 0) {
                    this.txtdonviql = rs.data[0]['TEN_DV'];
                    this.vthietbi_id = rs.data[0]['THIETBI_ID'];
                    this.vdonvi_id = rs.data[0]['DONVI_ID'];
                    this.vvattu_id = rs.data[0]['VATTU_ID'];
                    await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id);
                }
            } catch (error) {
                
            }
        },
        grid_lichsu: async function(vttvh_id, vthietbi_id) {
            try {
                let rs = await this.$root.context.get('/web-ecms/quanlythietbiphutro/phutro_lay_ds_tnhan_tbi', {
                    ttvh_id: vttvh_id,
                    thietbi_id: vthietbi_id
                });
                if(rs.data.length > 0) {
                    this.dsLichSu = rs.data;
                }
            } catch (error) {
                this.$root.toastError("Có lỗi lấy danh sách lịch sử ");
            }
        },
        load_thuoctinh: async function(vattu_id, vthietbi_id) {
            try {
                let rs = await this.$root.context.get(`/web-ecms/quanlythietbiphutro/Lay_ThongTin_ThuocTinh_Theo_VatTu_ThietBi?vattu_id=${vattu_id}&thietbi_id=${vthietbi_id}`);
                this.dsThuocTinh = rs.data;
            } catch (error) {
                
            }
        },
        gridDsTiepNhan_rowSelected: async function(args) {
            if(args!=null){
                Object.assign(this.currentItem, args.data)
                this.vvanhanh_id = args.data.VANHANH_ID;
                this.vphieuvh_id = args.data.PHIEUVH_ID;
                this.txtGhichu = args.data.GHICHU;
                this.txtNoiDung = args.data.NOIDUNG;
                this.dtpNgayYC = DateTimeLib.toDateDisplay(args.data.NGAY_YC);
                this.txtdonviql = args.data.TEN_DV;
                this.txtMaTs = args.data.THIETBI_ID;
                this.vthietbi_id = args.data.THIETBI_ID;
                this.vvattu_id = args.data.VATTU_ID;
                this.txtTongtien = args.data.THANHTIEN;
                this.txtKetQua = args.data.KETQUA;
                this.dtpNgayHT = DateTimeLib.toDateDisplay(args.data.NGAY_HT);
                this.vmota = args.data.MOTA;
                await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id);
                await this.grid_lichsu(0, this.vthietbi_id);
            }
        },
        btnNhapmoi_Click: function() {
            this.clear();
            this.setbutton(1);
        },
        btnCapnhap_Click: async function() {
            try {
                this.loading(true);
                if(this.isEnable_btnNhapmoi) {
                    if(this.vvanhanh_id == 0) {
                        this.$root.toastError("Không có thiết bị để cập nhập!");
                        return;
                    }
                    let vdonvi_nhan_id = 0;
                    let mota = await this.Mota();
                    await this.phutro_trangthai_tiepnhan(2, this.vvanhanh_id, '', this.vloaiVH_id, this.vthietbi_id, this.txtNoiDung, this.dtpNgayYC, 
                    this.txtKetQuaTD, this.txtKetQua, 1, this.dtpNgayHT, this.txtGhichu, this.txtDeXuat, mota, this.vphieuvh_id, vdonvi_nhan_id);
                    await this.update_vanhanh();
                    this.$root.toastSuccess("Cập nhập thiết bị thành công!");
                    await this.load_ds_tiepnhan(1, 0);
                    this.hienthi_duongdan(mota);
                }
                else {
                    if(this.vthietbi_id == 0) {
                        this.$root.toastError("Không có thiết bị!");
                        return;
                    }
                    if(!this.txtMaTs) {
                        this.$root.toastError("Không có thiết bị!");
                        return;
                    }
                    let vdonvi_nhan_id = 0;
                    this.vvanhanh_id = 0;
                    if(this.dsTiepNhan.length > 0) {
                        let aa = this.dsTiepNhan.filter(r => r.LOAIVH_ID == this.vloaiVH_id && r.THIETBI_ID == this.vthietbi_id).length;
                        if (aa > 0)
                        {
                            this.$root.toastError("Thiết bị cập nhật bị trùng!");
                            return;
                        }
                    }
                    //them moi
                    let mota = await this.Mota();
                    await this.phutro_trangthai_tiepnhan(1, this.vvanhanh_id, '', this.vloaiVH_id, this.vthietbi_id, this.txtNoiDung, this.dtpNgayYC,
                    this.txtKetQuaTD, this.txtKetQua, 1, this.dtpNgayHT, this.txtGhichu, this.txtDeXuat, mota, this.vphieuvh_id, vdonvi_nhan_id);
                    await this.load_ds_tiepnhan(1, 0);
                    this.hienthi_duongdan(mota);
                    this.$root.toastSuccess("Tiếp nhận thiết bị thành công!");
                    //focusrow
                    this.setbutton(2);
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        btnHuy_Click: function() {
            if (this.currentItem == null)
            {
                if (this.dsTiepNhan.length == 0)
                    return;
                this.$refs.gridTiepNhan.setCurrentSelectedRow(0);
            }
            else
            {
               
            }
            this.setbutton(2);
        },
        btnXoa_Click: function() {
            try
            {             
                if (this.vvanhanh_id != 0)
                {
                    this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa thiết bị!', {
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                    }).then(async v => {
                        if(v) {
                            let vdonvi_nhan_id = 0;
                            await this.phutro_trangthai_tiepnhan(3, this.vvanhanh_id, '', this.vloaiVH_id, this.vthietbi_id, this.txtNoiDung, this.dtpNgayYC,
                            '', '', 1, this.dtpNgayHT, this.txtGhichu, '', '', this.vphieuvh_id, vdonvi_nhan_id);
                            this.clear();
                            await this.load_ds_tiepnhan(1, 0);
                        }
                    }).catch(error => {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thất bại');
                    })
                }
                else
                {
                    this.$root.toastError("Không có thiết bị để xóa!");
                    return;
                }
            }
            catch (ex)
            {
                this.$root.toastError("Xóa thất bại")
            }
        },
        btnhoanthanh_Click: async function() {
            try
            {
                if (vvanhanh_id == 0)
                {
                    this.$root.toastError("Không có thiết bị để hoàn thành!");
                    return;
                }
                let vdonvi_nhan_id = 0;
                let mota = await this.Mota();
                await this.phutro_trangthai_tiepnhan(2, this.vvanhanh_id, '', this.vloaiVH_id, this.vthietbi_id, this.txtNoiDung, this.dtpNgayYC,
                this.txtKetQuaTD, this.txtKetQua, 6, this.dtpNgayHT, this.txtGhichu, this.txtDeXuat, mota, this.vphieuvh_id, vdonvi_nhan_id);
                await this.load_ds_tiepnhan(1, 0);
                this.$root.toastSuccess("Cập nhập hoàn thành thiết bị thành công!");
            }
            catch (ex)
            {
                this.$root.toastError("Hoàn thành thất bại");
            }
        },
        btnGiaophieu_Click: function() {
            try {
                if(this.dsTiepNhan.length > 0) {
                    if(this.vvanhanh_id == 0) {
                        this.$root.toastError("Chọn 1 phiếu để giao!");
                        return;
                    }
                    else {
                        this.$bvModal.show('popupGiaoPhieuBaoHongThietBi');
                    }
                }
                else {
                    this.$root.toastError("Không có thiết bị báo hỏng để giao phiếu!");
                    return;
                }
            } catch (error) {
                
            }
        },
        btnTongtien_Click: function() {
            try
            {
              this.txtTongtien = "";
              this.$bvModal.show('formVanHanhKM')           
            }
            catch (ex)
            {
                this.$root.toastError("Có lỗi xảy ra")
            }
        },
        chapNhanTongTien: async function(tongtien) {
            this.txtTongtien = tongtien
            await this.load_ds_tiepnhan(1, 0);
        },
        bntLayMaTbi_Click: function (){
            this.clear();
            this.$bvModal.show('formTraCuuTBi')               
        },
        chapNhanTraCuu: async function(data) {
            this.vthietbi_id = data.THIETBI_ID;
            this.txtdonviql = data.TEN_DV;
            this.txtMaTs = data.MA_TBI;
            this.vdonvi_id = data.DONVI_ID;
            this.vvattu_id = data.VATTU_ID;
            await this.load_thuoctinh(this.vvattu_id, this.vthietbi_id);
        },
        Mota: async function() {
            let moTa = '';
            if (this.grid_File.items.length > 0) {
                for (let item of this.grid_File.items) {
                    if (item.NOIDUNG != null) { //Ảnh mới
                        let url = await this.uploadFileToMinio(item.NOIDUNG);
                        if (url == null) {
                            return this.$toast.error('Upload file lên Minio thất bại!');
                        } 
                        else {
                            moTa += url.relativeUrl + ';';
                        }
                    }
                    else {
                        if(item.TENFILE !== '') {
                            moTa += item.TENFILE + ';';
                        }
                        else {
                            moTa = '';
                        }
                    }
                }
            }
            return moTa;
        },
        hienthi_duongdan: function(moTa) {
            if(moTa === '' || moTa == null) {
                this.grid_File.items = [];
            }
            else {
                moTa = moTa.slice(0, -1);
                let arr_mota = moTa.split(';');
                let ds = [];
                for (const item of arr_mota) {
                    ds.push({
                        TENFILE: item,
                        TRANGTHAI: 0,
                        NOIDUNG: null
                    })
                }
                this.grid_File.items = [...ds];
            }
        },
        getIp: async function(){
            try{
            const response = await fetch("https://jsonip.com/")
            const data = await response.json();
            return data.ip;
            }catch(e) {
                return ""
            }
        },
        phutro_trangthai_tiepnhan: async function(kieu,vanhanh_id,ma_vh,loaivh_id,thietbi_id,
                noidung,ngay_yc,ketqua_td,ketqua,ttvh_id,ngay_ht,ghichu,dexuat,mota,phieuvh_id,donvi_nhan_id){
            try{
                let ip= await this.getIp();
                let data  = {
                    kieu:kieu,
                    vanhanh_id:vanhanh_id,
                    ma_vh:ma_vh,
                    loaivh_id:loaivh_id,
                    thietbi_id:thietbi_id,
                    noidung:noidung,
                    ngay_yc:DateTimeLib.toDateDisplay(ngay_yc),
                    nhanvien_id:this.$root.token.getNhanVienID(),
                    donvi_id:this.$root.token.getDonViID(),        
                    ketqua_td:ketqua_td,
                    ketqua:ketqua,
                    ttvh_id:ttvh_id,
                    ngay_ht:DateTimeLib.toDateDisplay(ngay_ht),
                    ghichu:ghichu,
                    ngay_cn:DateTimeLib.toDateDisplay(new Date()),
                    nguoi_cn:this.$root.token.getUserName(),
                    may_cn:'',
                    ip_cn:ip,
                    dexuat:dexuat,
                    mota:mota,
                    phieuvh_id:phieuvh_id,
                    donvi_nhan_id:donvi_nhan_id
            }
            let response = await this.$root.context.post(`/web-ecms/vanhanh-thuongxuyen/phutro-trangthai-tiepnhan`, data)
            return response.data;
            }catch (err) {
                throw err;
            }
        } ,
        update_vanhanh: async function(donvi_nhan_id, vanhanh_id){
            try{
                let response = await this.$root.context.post(`/web-ecms/vanhanh-thuongxuyen/capnhat-donvinhanid-vanhanh`, {
                    donvi_nhan_id:donvi_nhan_id,
                    vanhanh_id:vanhanh_id
                })
            }catch (err) {
                return null;
            }
        },
        onChonFile(e) {
            if (e.target.files.length) {
                let ds = [];
                for (let file of e.target.files) {
                    this.grid_File.items.push({
                        TENFILE: file.name,
                        TRANGTHAI: 0,
                        NOIDUNG: file
                    });
                }
                this.grid_File.items = [...this.grid_File.items];
            }
        },
        uploadFileToMinio: async function(item) {
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
            this.load_ds_tiepnhan(0);
        },
        closeForm: function() {
            this.load_ds_tiepnhan(1, 0);
        }
    },
    mounted: function() {
        try {
             EventBus.$on('onDownload', this.onDownloadHandler);
            EventBus.$on('onView', this.onViewHandler);
            EventBus.$on('onDelete', this.onDeleteHandler);
            EventBus.$on("chapNhanTraCuu", (data) => {
                this.chapNhanTraCuu(data)
            });
            EventBus.$on("chapNhanTongTien", (data) => {
                this.chapNhanTongTien(data)
            });
        }
        catch (error) {

        }
        finally {

        }
    },
    destroyed() {
        EventBus.$off("chapNhanTraCuu");
        EventBus.$off("chapNhanTongTien");
        EventBus.$off('onDownload', this.onDownloadHandler);
        EventBus.$off('onView', this.onViewHandler);
        EventBus.$off('onDelete', this.onDeleteHandler);
    },
}
</script>

<style scoped>
a.disabled-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
