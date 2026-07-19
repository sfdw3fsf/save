<template> 
    <div class="grid-stack-box-ver ">
        <div class="tab-content box-col" id="boxTop">
            <div class="grid-stack-box fullh">
                <div class="box-col" id="boxLeft1" style="min-width:150px;">
                    <div class="legend-title">Thông tin lô hồ sơ</div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Mã lô hồ sơ</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="malohoso" id="malohoso" :disabled="!enableFormNhapMoi">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Ngày tạo</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <vue-date format="DD/MM/YYYY" type="datetime" v-model="ngaytao" id="ngaytao" :disable="!enableFormNhapMoi"></vue-date>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w80">Đơn vị giao</div>
                        <div class="value">
                            <div class="select-custom">
                                <ejs-dropdownlist id="donvigiao"
                                    locale="vi-VN" @change="cboDonViGiao_Changed"
                                    v-model="donvigiao" :enabled="enableFormNhapMoi"
                                    :dataSource="lstDonViGiao"
                                    :allowFiltering="true" :filtering="onFilterTTLHSDonViGiao"
                                    :fields="{ value: 'ID', text: 'NAME' }"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w80">Ghi chú</div>
                        <div class="value">
                            <textarea name="" style="height: 132px;resize: none;" id="ghichu" cols="30" rows="12" class="form-control" 
                                    v-model="ghichu" :disabled="!enableFormNhapMoi"></textarea>
                        </div>
                    </div>
                </div>
                <div class="box-col" id="boxRight1" style="margin-left: 5px">
                    <div class="legend-title">Thông tin</div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Ngày tạo từ</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <vue-date format="DD/MM/YYYY" type="datetime" v-model="ngaytaotu" id="ngaytaotu"></vue-date>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Đến ngày</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <vue-date format="DD/MM/YYYY" type="datetime" v-model="ngaytaoden" id="ngaytaoden"></vue-date>
                                    </div>
                                </div>
                                <div class="value w30 nowrap padt7">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="nguoilap">
                                        <span class="name">Người lập</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-content" style="max-height: calc(100% - 95px);">
                        <DataGrid
                            ref="ds_thongtin"
                            v-bind:columns="columnsTT"
                            v-bind:dataSource="ds_thongtin"
                            :showColumnCheckbox="true"
                            :showFilter="true"
                            :enabledSelectFirstRow="true"
                            :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                            panelDataHeight="95"
                            @rowSelected="grvDanhSachLo_FocusedRowChanged"
                            :allowPaging="true" :enablePagingServer="false"
                        ></DataGrid>   
                    </div>
                </div>
            </div>
        </div>
        <div class="box-move-select-table box-col" id="boxBottom">
            <div class="box-col box-form">
                <div class="legend-title">Danh sách hồ sơ đã gán lô</div>
                <div class="table-content">
                    <DataGrid
                        ref="ds_hoso_daganlo"
                        v-bind:columns="columnsHSDG"
                        v-bind:dataSource="ds_hoso_daganlo"
                        :showColumnCheckbox="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="false"
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                        panelDataHeight="150"
                        :allowPaging="true" :enablePagingServer="false"
                        @selectedItemsChanged="gridHSDG_SelectRow"
                    ></DataGrid>   
                </div>
            </div>
            <div class="actions">
                <button class="btn" @click="btnGo_Click">
                    <span class="-ap icon-chevron-thin-right"></span>
                </button>
                <button class="btn" @click="btnGan_Click">
                    <span class="-ap icon-chevron-thin-left"></span>
                </button>
            </div>
            <div class="box-col box-form">
                <div class="legend-title">Danh sách hồ sơ chưa có lô</div>
                <div class="table-content">
                    <DataGrid
                        ref="ds_hoso_chuaganlo"
                        v-bind:columns="columnsHSCG"
                        v-bind:dataSource="ds_hoso_chuaganlo"
                        :showColumnCheckbox="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="false"
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                        panelDataHeight="150"
                        :allowPaging="true" :enablePagingServer="false"
                        @selectedItemsChanged="gridHSCG_SelectRow"
                    ></DataGrid> 
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import collums from "./define/collums.js";
import moment from "moment";
import api from './api/index.js'
import { Query } from '@syncfusion/ej2-data'

export default {
    name:'QuanLyLoHoSo',
    components:{
        
    },
    props:{
        tths_id:{
            type:Number,
            default: -1
        },
        quytrinh_id:{
            type:Number,
            default: -1
        },        
    },
    data() {
        return {
            ...collums,
            ngaytao: null,
            malohoso: null,
            ghichu: null,
            enableFormNhapMoi: false,
            ds_thongtin: [],
            ds_hoso_daganlo: [],
            ds_hoso_daganlo_selected: [],
            ds_hoso_chuaganlo: [],
            ds_hoso_chuaganlo_selected: [],
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            ngaytaotu: null,
            ngaytaoden: null,
            nguoilap: null,
            donvigiao: null,
            lstDonViGiao: [],
            vlohs_id: null,
            vlohs_luoi_id: null,
            vghichu_luoi: null,
            vdonvi_luoi_id: null,
            ngaytao_luoi: null,
            vmalo_hs: null,
            vnguoi_cn_luoi: null,
        }
    },
    methods: {
        init() {
            var today = moment();
            this.ngaytaotu = moment(today).add(-10, 'days').format("DD/MM/yyyy");
            this.ngaytaoden = this.ngay_ht;
        },
        async NAP_COMB_GIAOPHIEU() {
            let vdt_dvi_giao = await this.donvi_giao_hoso();
            if (vdt_dvi_giao != null && vdt_dvi_giao.length > 0) {
                this.lstDonViGiao = vdt_dvi_giao;
                this.donvigiao = vdt_dvi_giao[0].ID;
            }
        },
        async donvi_giao_hoso() {
            let vdt_dvi_giao = []
            try {
                let response = await api.ur_29002_004_donvi_giao_hoso(this.axios);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    vdt_dvi_giao = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return vdt_dvi_giao
        },
        onFilterTTLHSDonViGiao: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.lstDonViGiao, query);
        },
        cboDonViGiao_Changed() {

        },
        async NAP_DS_LOHS(tb) {
            this.vlohs_id = 0;
            this.ds_thongtin = [];
            try {
                let ds = await this.lay_ds_lobhs();
                if (ds == null || ds.length == 0) {
                    if (tb == 1) {
                        this.$toast.error("Không tìm thấy danh sách lô hồ sơ");
                    }              
                } else {
                    let checkbox = document.getElementById("nguoilap");
                    if (checkbox.checked) {
                        for (let data of ds) {
                            if (data.NGUOI_CN == this.$root.token.getUserName()) {
                                this.ds_thongtin.push(data);
                            }
                        }
                    } else {
                        this.ds_thongtin = ds;
                    }
                }
        
                this.ds_hoso_daganlo = [];
                this.ds_hoso_chuaganlo = [];
                if (this.ds_thongtin != null && this.ds_thongtin.length == 0) {
                    this.$emit("onLoadDS", false)
                } else {
                    this.$emit("onLoadDS", true)
                }  
            } catch (e) {
                console.log(e);
            }
        },
        XOA_FORM() {
            this.vlohs_id = 0;
            this.ngaytao = null;
            this.malohoso = "";
            this.ghichu = "";
            this.donvigiao = "-1";
            this.KHOAMO_FORM(false);
        },
        KHOAMO_FORM(mo) {
            this.enableFormNhapMoi = mo;
        },
        async lay_ds_lobhs() {
            let ds = []
            try {
                let params = {
                    "vtungay": this.ngaytaotu,
                    "vdenngay": this.ngaytaoden
                }
                let response = await api.ur_29003_001_lay_ds_lobhs(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    ds = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return ds
        },
        async grvDanhSachLo_FocusedRowChanged(row) {
            try {
                this.loading(true)
                if (row && row.data) {
                    await this.NAP_DS_CHITIET_TT(row.data);
                }            
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async NAP_DS_CHITIET_TT(row) {
            try {
                this.vlohs_id = row.LOHS_ID;
                this.vlohs_luoi_id = row.LOHS_ID;       
                this.ghichu = row.GHICHU;
                this.vghichu_luoi = row.GHICHU;
                this.donvigiao = String(row.DONVI_ID);
                this.vdonvi_luoi_id = row.DONVI_ID;
                this.malohoso = row.MALO_HS;
                this.vmalo_hs = row.MALO_HS;
                this.ngaytao = row.NGAYTAO;
                this.ngaytao_luoi = row.NGAYTAO;
                this.vnguoi_cn_luoi = row.NGUOI_CN;
                await this.NAP_DS_BOHS(0);
               
                this.KHOAMO_FORM(true);
                this.$emit("onFocusRow")
            } catch (ex) {
                this.$toast.error("Có lỗi khi lấy danh sách thông tin bộ hồ sơ " + ex);
            }
        },
        async lay_ds_bo_hs_ganlo(vkieu) {
            let ds = []
            try {
                let params = {
                    "vlohs_id": this.vlohs_id,
                    "vkieu": vkieu,
                    "vdonvi_id": this.vdonvi_luoi_id,
                    "vtths_id": this.tths_id
                }
                let response = await api.ur_29003_002_lay_ds_bo_hs_ganlo(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    ds = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return ds
        },
        gridHSDG_SelectRow (val) {
            this.ds_hoso_daganlo_selected = [];
            for (let index in val) {
                for (let ix in this.ds_hoso_daganlo) {
                    if (val[index].bohs_id == this.ds_hoso_daganlo[ix].bohs_id) {
                        this.ds_hoso_daganlo_selected.push(val[index]);
                    }
                }
            }
        },
        gridHSCG_SelectRow (val) {
            this.ds_hoso_chuaganlo_selected = [];
            for (let index in val) {
                for (let ix in this.ds_hoso_chuaganlo) {
                    if (val[index].bohs_id == this.ds_hoso_chuaganlo[ix].bohs_id) {
                        this.ds_hoso_chuaganlo_selected.push(val[index]);
                    }
                }
            }
        },
        async NAP_DS_BOHS(kieu) {          
            try {
                if (kieu == 0) {
                    this.ds_hoso_daganlo = [];
                    this.ds_hoso_chuaganlo = [];

                    if (this.vlohs_id > 0) {
                        this.ds_hoso_daganlo = await this.lay_ds_bo_hs_ganlo(2);
                        this.ds_hoso_chuaganlo = await this.lay_ds_bo_hs_ganlo(1);
                    }
                } else {
                    this.ds_hoso_chuaganlo = await this.lay_ds_bo_hs_ganlo(kieu);
                }
            } catch (ex) {
                this.$toast.error("Có lỗi khi nạp danh sách chưa gán "+ ex);
            }
        },
        btnGo_Click() {
            this.$emit("btnGo_Click");
        },
        btnGan_Click() {
            this.$emit("btnGan_Click");
        },
        async sp_lay_ds_bhs_theo_id(vdanhsach) {
            let ds = []
            try {
                let params = {
                    "danhsach": vdanhsach,
                    "tths_id": this.tths_id
                }
                let response = await api.ur_29003_006_sp_lay_ds_bhs_theo_id(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    ds = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return ds
        },
        async sp_lay_ds_chitiet_lo_hs() {
            let ds = []
            try {
                let params = {
                    "lohs_id": this.vlohs_id,
                    "tths_id": this.tths_id
                }
                let response = await api.ur_29003_006_sp_lay_ds_chitiet_lo_hs(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    ds = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return ds
        },
        async sp_them_ds_chitiet_lo_hs(vdanhsach) {
            let ds = []
            try {
                let params = {
                    "danhsach": vdanhsach,
                    "lohs_id": this.vlohs_id
                }
                let response = await api.ur_29003_006_sp_them_ds_chitiet_lo_hs(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    ds = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return ds
        },
        async sp_xoa_ds_chitiet_lo_hs(vdanhsach) {
            let ds = []
            try {
                let params = {
                    "danhsach": vdanhsach,
                    "lohs_id": this.vlohs_id
                }
                let response = await api.ur_29003_007_sp_xoa_ds_chitiet_lo_hs(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    ds = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return ds
        },
        async GAN_GO_LO_HS(kieu) {
            try {
                if (kieu == 1) {  // Gan
                    if (this.vnguoi_cn_luoi != this.$root.token.getUserName()) {
                        this.$toast.error("Bạn không được quyền gán/gỡ vào lô hồ sơ của người khác");
                        return;
                    }

                    if (this.ds_hoso_chuaganlo == null || this.ds_hoso_chuaganlo.length == 0) {
                        this.$toast.error("Không có danh sách hồ sơ để gán");
                        return;
                    }

                    if (this.ds_hoso_chuaganlo_selected.length == 0) {
                        this.$toast.error("Bạn chưa chọn phiếu để thực hiện");
                        return;
                    }

                    let vdanhsach = "<ds>"
                    for (let i=0; i<this.ds_hoso_chuaganlo_selected.length; i++) {
                        vdanhsach += ("<vt bhs_id='" + this.ds_hoso_chuaganlo_selected[i].bohs_id + "' />")
                    }
                    vdanhsach += "</ds>"

                    let ds_chan_gan = await this.sp_lay_ds_bhs_theo_id(vdanhsach)
                    if (ds_chan_gan.length != 0) {
                        this.$toast.error("Bạn không được gán hồ sơ do phiếu đã giao đi. ( " + ds_chan_gan[0].ma_bhs + ")");
                        return;
                    }
                    
                    let ds_chan = await this.sp_lay_ds_chitiet_lo_hs();
                    if (ds_chan.length != 0) {
                        this.$toast.error("Bạn không được gỡ hồ sơ ra khỏi lô do phiếu đã giao đi. ( " + ds_chan[0].ma_bhs + ")");
                        return;
                    }

                    try {
                        let kq = await this.sp_them_ds_chitiet_lo_hs(vdanhsach);           
                        this.$toast.success("Đã gán lô hồ sơ thành công");
                        await this.NAP_DS_BOHS(0);
                    } catch (ex) {
                        this.$toast.error("Có lỗi xảy ra trong quá trình thực hiện " + ex);
                        return;
                    }
                } else if (kieu == 2) {  // Go
                    if (this.ds_hoso_daganlo == null || this.ds_hoso_daganlo.length == 0) {
                        this.$toast.error("Không có danh sách hồ sơ để bỏ");
                        return;
                    }

                    if (this.ds_hoso_daganlo_selected.length == 0) {
                        this.$toast.error("Bạn chưa chọn phiếu để thực hiện");
                        return;
                    }

                    let vdanhsach = "<ds>"
                    for (let i=0; i<this.ds_hoso_daganlo_selected.length; i++) {
                        vdanhsach += ("<vt bhs_id='" + this.ds_hoso_daganlo_selected[i].bohs_id + "' />")
                    }
                    vdanhsach += "</ds>"

                    let ds_chan = await this.sp_lay_ds_bhs_theo_id(vdanhsach)
                    if (ds_chan.length != 0) {
                        this.$toast.error("Bạn không được gỡ hồ sơ ra khỏi lô do phiếu đã giao đi. ( " + ds_chan[0].ma_bhs + ")");
                        return;
                    }

                    try {
                        let kq = await this.sp_xoa_ds_chitiet_lo_hs(vdanhsach);
                        this.$toast.success("Đã xóa bộ hồ sơ khỏi lô thành công");
                        await this.NAP_DS_BOHS(0);
                    } catch (ex) {
                        this.$toast.error("Có lỗi xảy ra trong quá trình thực hiện " + ex);
                        return;
                    }   
                }
            } catch (ex) {
                this.$toast.error("Có lỗi xảy ra trong quá trình thực hiện " + ex);   
            }
        },
        async CAPNHAT(nhapmoi, hg_id) {
            try {
                if (!this.KIEMTRA_DULIEU(nhapmoi)) return;
                let kieu = 0; // Cập nhật
                if (!nhapmoi) {
                    kieu = 1;  // Nhập mới
                }

                let kq = await this.capnhat_lobhs(kieu, hg_id)
                if (kq.startsWith("OK")) {
                    if (!nhapmoi) {
                        this.$toast.success("Tạo lô hồ sơ thành công")
                    } else {
                        this.$toast.success("Cập nhật thông tin lô hồ sơ thành công");
                    }

                    await this.NAP_DS_LOHS(0);
                    let vlohs_temp_id = String(kq.split('-')[1]);
                    let newRcd;
                    let iRm;
                    for (let i=0; i<this.ds_thongtin.length; i++) {
                        if (String(this.ds_thongtin[i].LOHS_ID) == vlohs_temp_id) {
                            iRm = i;
                            newRcd = this.ds_thongtin[i]
                            break;
                        }
                    }
                    this.ds_thongtin.splice(iRm, 1)
                    this.ds_thongtin.unshift(newRcd)
                } else {
                    this.$toast.error("Thông báo: " + kq);
                }
            } catch (e) {
                this.$toast.error("Có lỗi khi tiến hành cập nhật " + e)
            }
        },
        async capnhat_lobhs(vkieu, vhg_id) {
            let kq = "";
            try {
                let params = {
                    "vkieu": vkieu,
                    "vlohs_id": this.vlohs_id,
                    "vdonvi_giao_id": Number(this.donvigiao),
                    "vnhanvien_id": this.$root.token.getNhanVienID(),
                    "vghichu": this.ghichu,
                    "vhuonggiao_id": vhg_id
                }
                let response = await api.ur_29003_004_capnhat_lobhs(this.axios, params);
                
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    kq = response.data.data;              
                }
            } catch (e) {
                console.log(e)
            }
            return kq;
        },
        KIEMTRA_DULIEU(nhapmoi) {
            try {
                if (Number(this.donvigiao) == -1) {
                    this.$toast.error("Bạn chưa chọn đơn vị giao của lô hồ sơ");
                    return false;
                }
                if (this.vnguoi_cn_luoi != this.$root.token.getUserName() && nhapmoi) {
                    this.$toast.error("Bạn không được quyền cập nhật lô hồ sơ của người khác");
                    return false;
                }
                return true;
            } catch (ex) {
                this.$toast.error("Có lỗi khi kiểm tra dữ liệu " + ex);
                return false;
            }
        },
        async XOA_BOHOSO(hg_id) {
            this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xoá lô bộ hồ sơ " + this.vmalo_hs + " không?", {
                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
            }).then(async v => {
                if (v) {
                    try {
                        this.loading(true)
                        if (this.vnguoi_cn_luoi != this.$root.token.getUserName()) {
                            this.$toast.error("Bạn không được phép xóa bộ hồ sơ của người khác");
                            return;
                        }

                        let kq = await this.capnhat_lobhs(3, hg_id)
                        if (kq == null) {
                            this.$toast.error("Không nhận được thông tin phản hồi khi xóa lô hồ sơ");
                            return;
                        }

                        if (String(kq) == "1") {
                            this.$toast.success("Xóa bộ lô hồ sơ thành công");
                            await this.NAP_DS_LOHS(0);
                        } else {
                            this.$toast.error(String(kq));
                        }
                    } catch (ex) {
                        this.$toast.error("Có lỗi khi xóa phiếu: " + ex);
                    } finally {
                        this.loading(false)
                    }     
                }
            });
        }
    }
}
</script>
