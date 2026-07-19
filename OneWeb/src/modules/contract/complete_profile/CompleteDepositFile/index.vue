<template>
    <div>
        <vue-bread-crumb :header="header" />
        <vue-nav>
            <ul class="list">
                <!-- <li v-if="button.visibleNhapmoi">
                    <a v-on:click="clickButton('nhapmoi')" :class="{ disabled: !button.nhapmoi }">
                        <span class="icon one-file-plus"></span> Nhập mới
                    </a>
                </li>
                <li v-if="button.visibleGhilai">
                    <a v-on:click="clickButton('ghilai')" :class="{ disabled: !button.ghilai }">
                        <span class="icon one-save"></span> Ghi lại (F9)
                    </a>
                </li>
                <li v-if="button.visibleXoa">
                    <a v-on:click="clickButton('xoa')" :class="{ disabled: !button.xoa }">
                        <span class="icon one-trash"></span>Xóa
                    </a>
                </li>
                <li v-if="button.visibleHuybo">
                    <a v-on:click="clickButton('huybo')" :class="{ disabled: !button.huybo }">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                    </a>
                </li>
                <li v-if="button.visibleThanhtoan">
                    <a
                        v-on:click="clickButton('thanhtoan')"
                        :class="{ disabled: !button.thanhtoan }"
                    >
                        <span class="icon nc-icon-glyph business_coins"></span>Thanh toán
                    </a>
                </li> -->
                <li v-if="button.visibleHoanthien">
                    <a @click="clickButton('hoanthien')">
                        <span class="icon one-file-check"></span>Hoàn thiện
                    </a>
                </li>
                <!-- <li v-if="button.visiblePhieuthu">
                    <a v-on:click="clickButton('phieuthu')" :class="{ disabled: !button.phieuthu }">
                        <span class="icon one-print"></span>Phiếu thu
                    </a>
                </li>
                <li v-if="button.visibleLienhe">
                    <a v-on:click="clickButton('lienhe')">
                        <span class="icon nc-icon-glyph business_business-contact-86"></span>Liên hệ
                    </a>
                </li>
                <li v-if="button.visibleEmail">
                    <a v-on:click="clickButton('email')" :class="{ disabled: !button.email }">
                        <span class="icon nc-icon-glyph ui-1_email-84"></span> Email
                    </a>
                </li> -->
                <li v-if="button.visibleThoat">
                    <a v-on:click="clickButton('thoat')" :class="{ disabled: !button.thoat }">
                        <span class="nc-icon-glyph ui-1_circle-remove"></span>Thoát
                    </a>
                </li>
            </ul>
        </vue-nav>
        <div class="page-content">
            <div class="box-form padt8 padb5">
                <div class="row">
                    <div class="col-sm-5 col-6"></div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w30 nowrap">
                                <div class="check-action">
                                    <input
                                        id="isCheck"
                                        type="checkbox"
                                        class="check"
                                        v-model="isChecked"
                                    />
                                    <label for="isCheck" class="name">Đơn vị QL</label>
                                </div>
                            </div>
                            <div class="value">
                                <SelectExt
                                    v-model="selectDonViQL"
                                    :allowFiltering="true"
                                    data-text-field="ten_dv"
                                    data-value-field="donvi_id"
                                    :dataSource="cbDonViQL"
                                    :disabled="!isChecked"
                                    @change="changeDVQL"
                                ></SelectExt>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row">
                            <div class="key w30 nowrap">Nhóm</div>
                            <div class="value">
                                <SelectExt
                                    v-model="selectNhom"
                                    :allowFiltering="true"
                                    data-text-field="ten"
                                    data-value-field="loainv_id"
                                    :dataSource="cbNhom"
                                    :disabled="!isChecked"
                                    @change="changeNhomCTV"
                                ></SelectExt>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-2 col-12">
                        <div class="info-row">
                            <div class="key w30 nowrap">Nhân viên</div>
                            <div class="value">
                                <SelectExt
                                    v-model="selectNV"
                                    :allowFiltering="true"
                                    data-text-field="ten_nv"
                                    data-value-field="nhanvien_id"
                                    :dataSource="cbNhanVien"
                                ></SelectExt>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-7 col-12">
                    <div class="box-form">
                        <!-- THONG TIN THUE BAO -->
                        <div class="legend-title">
                            <div class="pull-left">Thông tin thuê bao</div>
                            <div class="pull-right text-main">
                                <div class="one-alert f20 inline text-warning"></div>File excel gồm: MA_GD
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <b-row>
                            <vue-element label="Đường dẫn" class="col-md-12">
                                <div class="input-more-button">
                                    <label class="btn" for="upload-file">
                                        <span class="-ap icon-more_horiz"></span>
                                        <input
                                            type="file"
                                            ref="files"
                                            class="hidden"
                                            id="upload-file"
                                            @change="UploadFileHoSo"
                                            accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                        />
                                    </label>
                                    <vue-input v-model="form.duong_dan" type="file" id="file" />
                                </div>
                            </vue-element>
                            <vue-element label="Mã giao dịch" class="col-md-5">
                                <div class="input-more-button">
                                    <button @click="showPopupSearchContract" class="btn">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <SearchContractModal
                                        ref="popupSearchContract"
                                        @accept="acceptSearchContract"
                                        :loai_hd_id="loai_hd_id" :trangthai_hd="trangthai_hd" :ngay_yc="ngay_yc" 
                                    />
                                    <div class="value">
                                        <input
                                            id="txtMaGD"
                                            ref="txtMaGD"
                                            type="text"
                                            class="form-control"
                                            v-model.trim="form.txtMaGD"
                                            v-on:keyup.enter="enterPress"
                                        />
                                    </div>
                                </div>
                            </vue-element>
                            <b-col md="7">
                                <vue-date
                                    labelWidth="100"
                                    v-model="form.ngay_yc"
                                    type="date"
                                    format="DD/MM/YYYY"
                                    label="Ngày yêu cầu"
                                    :disable="true"
                                ></vue-date>
                            </b-col>
                            <!-- <vue-element label="Ngày yêu cầu" class="col-md-8">
                                <ejs-textbox v-model="form.ngay_yc" />
                            </vue-element>-->

                            <vue-element label="Số máy/Acc" class="col-md-5">
                                <div class="input-more-button">
                                    <button @click="SearchDanhBa" class="btn">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <SearchAccountModal
                                        ref="searchAccountModal"
                                        @form-close="formCloseSearchAccount"
                                    />

                                    <!-- <SearchAccountModdal
                                        ref="popupSearchAccount" @passMaTB="setTB"
                                    />-->
                                    <vue-input v-model="form.so_may_acc" />
                                </div>
                            </vue-element>
                            <vue-element label="Dịch vụ" class="col-md-4">
                                <SelectExt
                                    v-model="form.selectDichVu"
                                    :allowFiltering="true"
                                    data-text-field="TEN_DVVT"
                                    data-value-field="DICHVUVT_ID"
                                    :dataSource="cbDichVu"
                                    @change="changeDichVu"
                                ></SelectExt>
                            </vue-element>
                            <vue-element label="Loại hình" class="col-md-3">
                                <SelectExt
                                    v-model="form.selectLoaiHinh"
                                    :allowFiltering="true"
                                    data-text-field="LOAIHINH_TB"
                                    data-value-field="LOAITB_ID"
                                    :dataSource="cbLoaiHinh"
                                    :disabled="true"
                                    :style="styleObject"
                                ></SelectExt>
                            </vue-element>
                            <vue-element label="Tên thuê bao" class="col-md-12">
                                <ejs-textbox v-model="form.ten_tb" />
                            </vue-element>
                            <vue-element label="Địa chỉ TB" class="col-md-12">
                                <ejs-textbox v-model="form.diachi_tb" />
                            </vue-element>
                        </b-row>

                        <!-- THONG TIN UU DAI -->
                        <div class="legend-title mart20">
                            <div class="pull-left">Thông tin ưu đãi</div>
                            <div class="clearfix"></div>
                        </div>
                        <b-row>
                            <vue-element label="Nhóm đặt cọc" class="col-md-12 col-lg-12">
                                <SelectExt
                                    v-model="form.nhom_dat_coc"
                                    :allowFiltering="true"
                                    data-text-field="TEN_NHOM"
                                    data-value-field="NHOM_DATCOC_ID"
                                    :dataSource="cbNhomDatCoc"
                                    @change="changeNhomCoc"
                                />
                            </vue-element>
                            <vue-element label="Trả trước" class="col-md-12 col-lg-12">
                                <div class="input-more-button">
                                    <button @click="btnSearch_Promotion" class="btn">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <SearchPromotionsModal
                                        ref="popupSearchPromotions"
                                        @dKM="getKM"
                                        :data="dataKM"
                                    />
                                    <SelectExt
                                        v-model="form.tra_truoc"
                                        :allowFiltering="true"
                                        data-text-field="ten_dv"
                                        data-value-field="donvi_id"
                                        :dataSource="cbTraTruoc"
                                    />
                                </div>
                            </vue-element>
                            <vue-element label="Chi tiết" class="col-md-12 col-lg-12">
                                <SelectExt
                                    v-model="form.chi_tiet_km"
                                    :allowFiltering="true"
                                    data-text-field="ten_ctkm"
                                    data-value-field="chitietkm_id"
                                    :dataSource="cbChiTietKM"
                                />
                            </vue-element>
                        </b-row>
                    </div>
                </div>
                <div class="col-sm-5 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin giảm cước</div>
                        <div class="row">
                            <vue-date
                                labelWidth="90"
                                v-model="form.thang_MG"
                                type="month"
                                format="MM/YYYY"
                                label="Tháng MG"
                                class="col-sm-7 col-12"
                            ></vue-date>

                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Số tháng MG</div>
                                    <div class="value">
                                        <input
                                            type="text"
                                            class="form-control"
                                            v-model="form.so_thang_MG"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tỷ lệ TB</div>
                                    <div class="value">
                                        <input
                                            type="text"
                                            class="form-control fw6 red tright"
                                            v-model="form.ty_le_tb"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tỷ lệ SD</div>
                                    <div class="value">
                                        <input
                                            type="text"
                                            class="form-control fw6 red tright"
                                            v-model="form.ty_le_sd"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tiền TB</div>
                                    <div class="value">
                                        <input
                                            type="text"
                                            class="form-control fw6 red tright"
                                            v-model="form.tien_tb"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tiền SD</div>
                                    <div class="value">
                                        <input
                                            type="text"
                                            class="form-control fw6 red tright"
                                            v-model="form.tien_sd"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Ghi chú</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.ghi_chu" />
                            </div>
                        </div>
                        <div class="legend-title mart20">Thông tin ưu đãi</div>
                        <div class="row">
                            <vue-date
                                labelWidth="90"
                                v-model="form.thang_BD"
                                type="month"
                                format="MM/YYYY"
                                label="Tháng BĐ"
                                class="col-sm-7 col-12"
                                
                            ></vue-date>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Số tháng</div>
                                    <div class="value">
                                        <input
                                            type="text"
                                            class="form-control"
                                            v-model="form.so_thang"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Cước ĐC</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.cuoc_DC" />
                            </div>
                        </div>
                        <div class="info-row marb50">
                            <div class="key w90">Tiền/tháng</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="form.tien_thang" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                    <div class="pull-left">Danh sách hợp đồng đọc từ file</div>
                    <div class="pull-right red weight">Tổng số: {{ options.hopdong.length }}</div>
                    <div class="clearfix"></div>
                </div>
                <DataGrid
                    ref="danh_sach_hop_dong"
                    :showFilter="true"
                    :allowPaging="true"
                    :enablePagingServer="false"
                    :dataSource="options.hopdong"
                    :columns="config.hopdong"
                    @selectedRowChanged="focusItemHD"
                />
            </div>
            <div class="box-form">
                <vue-card :noborder="true">
                    <div class="legend-title">
                        <div class="pull-left">Danh sách thuê bao đặt cọc</div>
                        <div class="pull-right red weight">Tổng số: {{ options.thuebao.length }}</div>
                        <div class="clearfix"></div>
                    </div>
                    <DataGrid
                        ref="danh_sach_thue_bao"
                        :showFilter="true"
                        :allowPaging="true"
                        :enablePagingServer="false"
                        :dataSource="options.thuebao"
                        :columns="config.thuebao"
                        @selectedRowChanged="focusItemTB"
                    />
                </vue-card>
            </div>
        </div>

        <ThongTinEmail
            ref="popupEmail"
            v-bind:hdkh_id="model.hopdong.hdkh_id"
            v-bind:khachhang_id="model.hopdong.khachhang_id"
        />
        <ThongTinLienHe
            ref="popupThongTinLienHe"
            :hdkh_id="model.hopdong.hdkh_id"
            :khachhang_id="model.hopdong.khachhang_id"
        />
    </div>
</template>
<script>
import define from "./define/index.js";
import api from "./api/index.js";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
// import SearchContractModal from './components/SearchContractModal.vue';

import SearchAccountModal from './components/SearchAccountModal.vue';
// import SearchAccountModdal from './components/SearchAccount/index.vue';

import SearchPromotionsModal from './components/SearchPromotions.vue';
import XLSX from 'xlsx';
import ThongTinEmail from "./components/ThongTinEmail.vue";
import ThongTinLienHe from './components/ThongTinLienHe.vue';
// import Payment from '@/modules/contract/setup/Payment/Payment.vue';\
import * as moment from 'moment';

export default {
    components: {
        SearchContractModal,
        SearchAccountModal,
        XLSX,
        SearchPromotionsModal,
        ThongTinEmail,
        // frmThanhToanHD,
        ThongTinLienHe,
        // Payment,
        // Email
    },
    data() {
        return {
            ...define,
            styleObject: {
                // width: 'fit-content',
                minWidth: '120px'
            },
            dataKM: {
                dt: [],
                loai: 0
            },
            ngay_yc: moment(new Date()).format("DD/MM/YYYY")
        };
    },
    mounted() {
        console.log('mouted !!');
    },
    watch: {
        
        // vhdkh_id() {
        //     this.getDsthueBao();
        // },
        selectDonViQL() {
            this.getDsNhanVien();
        },
        selectNhom() {
            this.getDsNhanVien();
        },
        SELECT_DichVu_ID(val) {
            // alert('')
            if (this.cbLoaiHinh.length > 0) {
                // debugger
                let tem = this.cbLoaiHinh.filter(i =>
                    i.DICHVUVT_ID == this.form.selectDichVu
                );
                // console.log(tem);
                this.form.selectLoaiHinh = tem[0]['LOAITB_ID'];
                this.cbLoaiHinh = tem;
                // this.SELECT_LOAI_HINH_ID = this.form.selectLoaiHinh;
            }
        },
        SELECT_LOAI_HINH_ID(val) {
            try {
                api.getDsKhuyenMaiChiTiet(this.axios, {
                    vkhuyenmai_id: this.form.tra_truoc ? this.form.tra_truoc : 0,
                    vloaitb_id: this.form.selectLoaiHinh ? this.form.selectLoaiHinh : 0,
                    vtocdotn_id: this.tocdo_id ? this.tocdo_id : 0,
                    vmuccuoctn_id: this.muccuoc_id ? this.muccuoc_id : 0,
                    vnhom_datcoc_id: this.form.nhom_dat_coc ? this.form.nhom_dat_coc : 0
                }).then(res => {
                    if (res.data.error == 200) {
                        // this.form.chi_tiet_km = res.data.data[0]['chitietkm_id']
                        this.cbChiTietKM = res.data.data;
                    }
                })
            } catch (error) {
                console.log(error);
            }
            
        },
        SELECTED_NHOM_COC(val) {
            try {
                api.getDsThamSoMD(this.axios, {
                    // list_of_cols: "tocdo_id, muccuoc_id",
                    // order: "",
                    // schema: "css",
                    // table_name: "db_adsl",
                    // where: "thuebao_id = " + val
                    p_type: 1,
                    p_param: val
                }).then(res => {
                    if (res.data.error == 200) {
                        let t = res.data.data[0];
                        this.muccuoc_id = t.muccuoc_id;
                        this.tocdo_id = t.tocdo_id;
                    }
                    api.getDsKhuyenMai(this.axios, {
                        kieuld_id: this.kieuld_id,
                        loaitb_id: this.form.selectLoaiHinh,
                        muccuoc_id: this.muccuoc_id,
                        doituong_id: this.doituong_id,
                        tocdo_id: this.tocdo_id,
                        thuebao_id: this.thuebao_id,
                        nhom_datcoc_id: this.form.nhom_dat_coc
                    }).then(result => {
                        if (result.data.error == 200) {
                            this.cbTraTruoc = result.data.data;
                            this.dataKM.dt = [...this.cbTraTruoc];
                            this.dataKM.loai = this.form.
                            this.form.tra_truoc = result.data.data[0]['khuyenmai_id'];
                            this.SELECT_LOAI_HINH_ID = this.form.selectLoaiHinh;
                        }
                    })
                })
            } catch (error) {
                this.$toast.error(error);
                console.log(error);
            }
        }

    },
    methods: {
        enterPress(e){
            try {
                // console.log(e.target.value ? 1 : 0);
                if(e.target.value){
                    // alert(true)
                    let data = {ma_gd: e.target.value}
                    this.acceptSearchContract(data);
                }else{
                    this.$toast.warning("Nhập mã giao dịch");
                }
            } catch (error) {
                
            }
        },
        async focusItemHD(data) {
            this.model.hopdong = data;
            // if (data.ma_gd != this.form.ma_gd) {
            //     this.form.txtMaGD = data.ma_gd;
            //     this.options.thuebao = [];
            //     let ds = await this.getDsHDTheoMaGD();
            //     this.options.hopdong = [...ds]
            //     this.HienThiTTHopDongKH(ds);
            // }
            // this.HienThiTTHopDongKH(data);
            // this.fillTTHD(data);
            // this.getDsthueBao();
            // this.model.hopdong = {};
            //     console.log(data);
            // if(i){
            //     // this.model.hopdong = i;
            //     this.SetButton(3)
            // console.log(data);
                this.acceptSearchContract(data);
            // }
        },
        async focusItemTB(data) {
            // let i = data.data ? data.data : data;
            this.model.thuebao = {};
            console.log("Data thue bao: ", data);
            if (data) {
                this.model.thuebao = data;
                // this.SetButton(3)
                this.fillThongTin(data);
            }
        },
        setTB(item) {
            // alert('');
            if (item.ma_tb) {
                this.form.so_may_acc = item.ma_tb;
            }
        },
        async UploadFileHoSo(e) {
            try {
                this.loading = true;
                let fileupload = this.$refs.files.files[0];
                this.form.duong_dan = e.target.files[0].name;
                // alert(this.form.duong_dan);
                let nhanvien_id = this.nhanvien_id;
                let nguoi_cn = this.nguoi_cn;
                if(this.form.duong_dan){
                    this.docFile = false;
                    var reader = new FileReader();
                    reader.onload = async (e) => {
                        
                        //Read the Excel File data in binary
                        var workbook = XLSX.read(e.target.result, {
                            type: 'binary'
                        });
    
                        //get the name of First Sheet.
                        var Sheet = workbook.SheetNames[0];
    
                        //Read all rows from First Sheet into an JSON array.
                        var excelRows = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[Sheet]);
                        // console.log(excelRows);
                        if (excelRows && excelRows.length > 0) {
                            if (!Object.keys(excelRows[0]).includes('MA_GD')) {
                                this.$toast.warning("Tên cột trong file không đúng định dạng: MA_GD");
                                return;
                            }
                            let d = new Date().toLocaleString('vi-VN', { dateStyle: 'short' }).toString();
                            let rows = [...excelRows];
                            let ls = [];
                            rows.forEach(row => {
                                row["LOI"] = "0";
                                row["LYDO_LOI"] = "";
                                row.NHANVIEN_ID = this.nhanvien_id;
                                row.NGUOI_CN = this.nguoi_cn;
                                row.NGAY_CN = d;
                                row.IP_CN = this.ip_cn;
                                ls.push(row);
                                
    
                                // console.log(rows);
                            });
                            // cap nhat
                            await this.updateDataCocFromFileTemp(ls);
                            let kiemTraDatCocTmp = await api.kiemTraDatCocTmp(this.axios, {
                                vnhanvien_id: this.nhanvien_id,
                            });
                            // this.$refs.files = [];

                            // kiemtra 
                            let dsS = kiemTraDatCocTmp.data.data ? kiemTraDatCocTmp.data.data : [];
                            // if (dsS.length > 0) {
                            //     dsS.forEach(i => {
                            //         if (i.KQ_LOI) {
                            //             this.$toast.warning(i.MA_GD + ": " + i.KQ_LOI);
                            //         }
                            //     });
                            //     return;
                            // }

                            let dsHD = await api.getDsHopDongTuFile(this.axios, {
                                vnhanvien_id: this.nhanvien_id,
                                vphanvung_id: this.phanvung_id,
                            });
                            // debugger
                            this.options.hopdong = [...dsHD.data.data];
                            // console.log(dsHD.data.data, this.options.hopdong);
                            this.docFile = true;
                            await this.focusItemHD(dsHD.data.data[0]);
                            this.loading = false;
                        }
                    };
                    reader.readAsBinaryString(fileupload);
                }
            }
            catch (error) {
                this.loading = false 
                console.log(error);
            }
        },
        fillTTAfterUploadFile(hd){
            try {
                this.acceptSearchContract(hd);
            } catch (error) {
                console.log(error);
            }
        },

        updateDataCocFromFileTemp(dsdatcoctemp) {
            // console.log(dsdatcoctemp[0].MA_GD);
            // this.form.txtMaGD = dsdatcoctemp[0].MA_GD;
            api.updateDataCocFromFileTemp(this.axios, {
                vnhanvien_id: this.nhanvien_id,
                dsdatcoctemp: dsdatcoctemp
            }).then(res => {
                if (res.data.error == 200) {
                    this.$toast.success("cập nhật thành công !")
                    // this.getDsHopDongTuFile();
                    this.acceptSearchContract(dsdatcoctemp[0]);
                    this.docFile = true;
                }
            })
        },
        btnSearch_Promotion() {

            // console.log('asdassd ', this.dataKM);
            if(!this.cbTraTruoc.length) {
                return;
            }
            this.$refs.popupSearchPromotions.showModal();
        },
        getKM(e) {
            if (e.loaikm)
                this.form.tra_truoc = e.loaikm;
        },
        SearchDanhBa() {
            this.$refs.searchAccountModal.showModal()
        },
        formCloseSearchAccount(data) {
            this.form.so_may_acc = data.ma_tb
        },
        showPopupSearchContract() {
            this.$refs.popupSearchContract.showModal();
        },
        async txtThuebao_KeyPress() {

            let dsTraCuuTB = []

            try {
                this.loading = true
                dsTraCuuTB = await this.lay_ds_thuebao_theo_ma_tb(this.txtThuebao.Text, 0)
            }
            finally {
                this.loading = false
            }

            if (dsTraCuuTB.length == 0) {
                this.$root.toastError("Không tìm thấy thông tin thuê bao " + this.txtThuebao.Text + " trong danh bạ !")
                return
            }

            if (dsTraCuuTB.length == 1) {
                this.onSelectThueBao(dsTraCuuTB[0])
            }
            else {
                let modal = this.$refs['ref-chon-thue-bao']
                modal.setDsThueBao(dsTraCuuTB)
                modal.show()
            }
        },
        async getDsHDTheoMaGD(){
            try {
                let res = await api.getDsHDTheoMaGD(this.axios, {
                    in_ma_gd: this.form.txtMaGD,
                    in_loaihd_id: this.LoaiHopDong.DATCOC_MOI,
                    in_donvi_id: Number(this.$root.token.getDonViID()),
                    in_tthd_id: this.TrangThaiHD.K,
                    in_nhanvien_id: Number(this.$root.token.getNhanVienID()),
                    in_donvi_dl_id: 0
                    // in_ma_gd: "HPG-KL/00530427",
                    // in_loaihd_id: 24,
                    // in_donvi_id: 186,
                    // in_tthd_id: 0,
                    // in_nhanvien_id: 13431,
                    // in_donvi_dl_id: 0
                });
                return res.data.data;
            } catch (error) {
                console.log(error);
            }
        },
        async fillTTHD(TTHD){
            try {
                if(TTHD){

                    // fill TTHD
                    this.vhdkh_id = TTHD.hdkh_id ? TTHD.hdkh_id : 0;
                    this.ma_kh = TTHD.ma_kh ? TTHD.ma_kh : 0;
                    this.khlon_id = TTHD.khlon_id ? TTHD.khlon_id : 0;
                    this.khachhang_id = TTHD.khach_hang_id ? TTHD.khach_hang_id : 0;
                    this.selectDonViQL = TTHD.donviql_id ? TTHD.donviql_id : 0;
                    this.kieuld_id = TTHD.kieuld_id ? TTHD.kieuld_id : 0;
                    this.doituong_id = TTHD.doituong_id ? TTHD.doituong_id : 0;
                    this.ten_kh = TTHD.ten_kh ? TTHD.ten_kh : '';
                    this.diachi_kh = TTHD.diachi_kh ? TTHD.diachi_kh : '';
                    this.ngaylap_hd = TTHD.ngaylap_hd ? TTHD.ngaylap_hd : '';
                    if (TTHD.ctv_id) {
                        this.isChecked = true;
                        let n = await api.getCBNhom(this.axios, {
                            nhomlnv_id: this.NHOM_LNV.CONGTACVIEN
                        });
                        this.selectNV = TTHD.ctv_id ? TTHD.ctv_id : 0;
                        if (n.data.data.length > 0) {
                            this.selectNhom = n.data.data[0].loainv_id;
                        }
                    } else {
                        this.isChecked = false;
                    }
                    
                    // Thong tin thue bao
                    // this.form.txtMaGD = TTHD.ma_gd ? TTHD.ma_gd : 0;
                    this.form.ngay_yc = TTHD.ngay_yc ? TTHD.ngay_yc.slice(0, 10).split('-').reverse().join('/') : new Date().toLocaleString('vi', { dateStyle: 'short' });
                    this.form.so_may_acc = TTHD.ma_tb ? TTHD.ma_tb : 0;
                    this.form.selectDichVu = TTHD.dichvuvt_id ? TTHD.dichvuvt_id : 0;
                    this.form.selectLoaiHinh = TTHD.loaitb_id ? TTHD.loaitb_id : 0;
                    this.form.ten_tb = TTHD.ten_tb ? TTHD.ten_tb : '';
                    this.form.diachi_tb = TTHD.diachi_tb ? TTHD.diachi_tb : '';
                    // get thong tin uu dai va thong tin giam cuoc
                    this.thuebao_id = TTHD['thuebao_id'];
                    this.SELECTED_NHOM_COC = this.thuebao_id;

    
                    // dich vu
                    this.SELECT_DichVu_ID = 0;
                }
            } catch (error) {
                console.log(error);
            }
        }, 
        async acceptSearchContract(item) {
            try {
                console.log(item);
                this.loading = true 
                if (item.ma_gd) {
                    this.form.txtMaGD = item.ma_gd;
                    this.options.thuebao = [];
                    let ds = await this.getDsHDTheoMaGD();
                    await this.HienThiTTHopDongKH(ds);
                }
                this.loading = false 
            } catch (error) {
                this.loading = false 
                this.informError(error);
            }
        },
        async HienThiTTHopDongKH(ds){
            try
            {
                console.log(ds);
                if (ds.length > 0)
                {
                    this.vhdkh_id = Number(ds[0]["hdkh_id"]);
                    this.form.txtMaGD = ds[0]["ma_gd"];
                    this.ma_kh = ds[0]["ma_kh"];
                    if (ds[0]["khachhang_id"] != "")
                        this.khachhang_id = Number(ds[0]["khachhang_id"]);
                    this.khlon_id = Number(ds[0]["khlon_id"]);
                    this.form.ten_tb = (ds[0]["ten_kh"]);
                    this.form.diachi_tb = (ds[0]["diachi_kh"]);
                    // ten_kh = (ds[0]["ten_kh"]);
                    // diachi_kh = (ds[0]["diachi_kh"]);
                    if (ds[0]["ngay_yc"] != "")
                        this.form.ngay_yc = (ds[0]["ngay_yc"].slice(0,10).split('-').reverse().join('/'));
                    this.form.ghi_chu = (ds[0]["ghichu"]);

                    if (!ds[0]["ctv_id"])
                    {
                        this.isChecked = true;
                    }
                    else
                        this.isChecked = false;

                    this.SetButton(3);
                    await this.HienThiDanhSacHDTB();
                }
                else
                {
                    // this.options.hopdong = [];
                    // this.SetButton(0);
                }
            }
            catch (exp)
            {
                console.log(exp);
                this.$toast.error("Có lỗi: " + exp);
            }
        },

        clickButton(key) {
            if (!this.button[key]) {
                return false;
            }
            if (key == "nhapmoi") {
                this.tsbtnNhapMoi_Click();
            }
            if (key == "ghilai") {
                this.tsbtnGhiLai_Click();
            }
            if (key == "xoa") {
                this.tsbtnXoa_Click();
            }
            if (key == "huybo") {
                this.tsbtnHuyBo_Click();
            }
            if (key == "thanhtoan") {
                this.tsbtnThanhToan_Click();
            }
            if (key == "hoanthien") {
                this.tsbtnHoanThien_Click();
            }
            if (key == "phieuthu") {
                this.tsbtnPhieuThu_Click();
            }
            if (key == "lienhe") {
                this.tsbtnLienHe_Click();
            }
            if (key == "email") {
                this.tsbtnEmail_Click();
            }
            if (key == "thoat") {
                this.tsbtnThoat_Click();
            }
        },
        tsbtnNhapMoi_Click() {
            this.SetButton(1);
        },
        async tsbtnGhiLai_Click() {
            // this.capnhat();
            this.$toast.info("asasd")
        },
        async capnhat() {
            if (!this.button.nhapmoi) {
                if (!this.KiemTra_DuLieu(true))
                    return;
                // this.TaoDuLieu(true);
                let dsHDKH = this.TaoDuLieu_HDKH(true);
                let dsHDTB = this.TaoDuLieu_HDTB(true);
                let hdtb_dc = this.TaoDuLieu_HDTB_DATCOC(true);
                let dsCTTHD = this.TaoDuLieu_CT_TIENHD(true);
                api.capnhatDatcocTheoFile(this.axios, {
                    vds: [
                        {
                            ISNHAPMOI: 0,
                            MA_TB: this.form.so_may_acc,
                            DICHVUVT_ID: this.form.selectDichVu,
                            LOAITB_ID: this.form.selectLoaiHinh,
                            HUONGGIAOTN_ID: this.huonggiaotn_id,
                            THUEBAO_ID: this.model.thuebao.thuebao_id,
                            THUEBAO_DC_ID: this.thuebao_dc_id,
                            THANGBDMOI: this.THANGBDMOI,
                            THANGKTMOI: this.THANGKTMOI,
                            NHOMDCOC_ID: this.form.nhom_dat_coc,
                            IN_MA_TB: "",
                            IN_DICHVUVT_ID: this.model.thuebao.dichvuvt_id
                        }
                    ],
                    dshdkh: dsHDKH,
                    dshdtb: dsHDTB,
                    hdtb_dc: hdtb_dc,
                    dsctthd: dsCTTHD
                }).then(res=> {
                    this.$toast.warning(res.data.data);
                });
            } else {
                if (!this.KiemTra_DuLieu(false)) return;
                //Taodulieu(false);
                ////
                //tb_dc.Update(dsDatCoc);
                // this.TaoDuLieu(false);
                let dsHDKH = this.TaoDuLieu_HDKH(false);
                let dsHDTB = this.TaoDuLieu_HDTB(false);
                let hdtb_dc = this.TaoDuLieu_HDTB_DATCOC(false);
                let dsCTTHD = this.TaoDuLieu_CT_TIENHD(false);
                api.capnhatDatcocTheoFile(this.axios, {
                    vds: [
                        {
                            ISNHAPMOI: 1,
                            MA_TB: this.form.so_may_acc,
                            DICHVUVT_ID: this.form.selectDichVu,
                            LOAITB_ID: this.form.selectLoaiHinh,
                            HUONGGIAOTN_ID: this.huonggiaotn_id,
                            THUEBAO_ID: this.thuebao_id,
                            THUEBAO_DC_ID: this.thuebao_dc_id,
                            THANGBDMOI: this.THANGBDMOI,
                            THANGKTMOI: this.THANGKTMOI,
                            NHOMDCOC_ID: this.form.nhom_dat_coc,
                            IN_MA_TB: this.model.thuenao.ma_tb,
                            IN_DICHVUVT_ID: this.model.thuebao.dichvuvt_id
                        }
                    ],
                    dshdkh: dsHDKH,
                    dshdtb: dsHDTB,
                    hdtb_dc: hdtb_dc,
                    dsctthd: dsCTTHD
                }).then(res=> {
                    this.$toast.warning(res.data.data);
                });
                // Message_Box.ShowTB("Cập nhật dữ liệu thành công!");
            }
            this.getDsthueBao();
        },
        async HienThiDanhSacHDTB(){
            try {
                this.loading = true 
                // let dtList = datcoc.Lay_DS_HDTB_THEO_HDKH(hdkh_id).Tables[0];
                let dtList = await this.getDsthueBao();
                if (dtList && dtList.length > 0)
                {
                    this.options.thuebao = dtList;
                    // this.SELECTED_NHOM_COC = dtList[0]["thuebao_id"];
                    this.fillThongTin(dtList[0]);
                }
                else
                {
                    this.options.thuebao = [];
                    this.SetButton(1);
                }
                this.loading = false 
            } catch (error) {
                this.loading = false 
                console.log(error);
            }
        },
        TaoDuLieu(themmoi) {
            this.TaoDuLieu_HDKH(themmoi);
            this.TaoDuLieu_HDTB(themmoi);
            this.TaoDuLieu_HDTB_DATCOC(themmoi);
            this.TaoDuLieu_CT_TIENHD(themmoi);
        },
        async getKey(key) {
            return this.axios.post('/web-quantri/donvitinh/get_keys', {
                keyname: key, numblocksize: 1, numretry: 10
            });
        },
        async TaoDuLieu_HDKH(themmoi) {
            //b.hdkh_id, b.ma_gd, b.ma_kh, b.khachhang_id, b.ten_kh, b.diachi_kh, b.ngaylap_hd, b.ghichu
            let dsHDKH = [];
            let rowHDKH = {};
            // thiếu key
            if (themmoi == true) {
                this.vhdkh_id = await this.getKey("HD_KHACHHANG").data.data;
            }
            rowHDKH.HDKH_ID = this.vhdkh_id;
            if (themmoi == true) {
                // call api để lấy mã GD
                let res = await this.axios.get('/web-ccdv/ht_hoso_thaydoidichvu/lay_ma_gd/' + this.DATCOC_MOI);
                this.form.txtMaGD = res.data.data;
                rowHDKH.MA_GD = this.form.txtMaGD;
            }
            else {
                rowHDKH.MA_GD = this.form.txtMaGD;
            }
            rowHDKH.MA_KH = this.ma_kh;
            if (Number(this.khachhang_id) > 0)
                rowHDKH.KHACHHANG_ID = this.khachhang_id;
            rowHDKH.KHLON_ID = this.khlon_id;
            rowHDKH.TEN_KH = this.ten_kh;
            rowHDKH.DIACHI_KH = this.diachi_kh;
            rowHDKH.NGAYLAP_HD = this.ngaylap_hd;
            rowHDKH.NGAY_YC = this.form.ngay_yc;
            rowHDKH.GHICHU = this.form.ghi_chu;
            rowHDKH.DONVI_ID = this.donvi_id;
            rowHDKH.NHANVIEN_ID = this.nhanvien_id;
            rowHDKH.LOAIHD_ID = this.DATCOC_MOI;
            rowHDKH.NGUOI_CN = this.nguoi_cn;
            rowHDKH.MAY_CN = this.may_cn;
            rowHDKH.NGAY_CN = this.ngay_cn;

            if (this.isChecked && this.cbNhanVien.length > 0 && selectNV) {
                rowHDKH.CTV_ID = Number(this.selectNV);
            }
            dsHDKH.push(rowHDKH);
            return dsHDKH;
            // if (themmoi == false)
            // {
            //     dsHDKH.AcceptChanges();
            //     dsHDKH.HD_KHACHHANG.Rows[0].SetModified();
            // }
        },
        async TaoDuLieu_HDTB(themmoi) {
            try {
                let dsHDTB = [];
                let rowHDTB = {};
                // thiếu key
                if (themmoi == true)
                    hdtb_id = await this.getKey("HD_THUEBAO").data.data;
                rowHDTB.HDTB_ID = this.model.thuebao.hdtb_id;
                rowHDTB.HDKH_ID = this.vhdkh_id;
                if (Number(this.model.thuebao.thuebao_id) > 0)
                    rowHDTB.THUEBAO_ID = this.model.thuebao.thuebao_id;
                rowHDTB.MA_TB = this.model.thuebao.ma_tb;
                rowHDTB.TEN_TB = this.model.thuebao.ten_tb;
                rowHDTB.DIACHI_LD = this.model.thuebao.diachi_ld;
                rowHDTB.DIACHI_TB = this.model.thuebao.diachi_tb;

                rowHDTB.KIEULD_ID = this.KieuLapDat.DATCOC_MOI; // Kiểu đặt cọc mới
                rowHDTB.DOITUONG_ID = this.model.thuebao.doituong_id;
                rowHDTB.TTHD_ID = this.TrangThaiHD.MOI;
                rowHDTB.LOAITB_ID = this.model.thuebao.loaitb_id;
                rowHDTB.DICHVUVT_ID = this.model.thuebao.dichvuvt_id;
                rowHDTB.DONVI_ID = this.donvi_id;
                rowHDTB.NGUOI_CN = this.nguoi_cn;
                rowHDTB.NGAY_CN = this.ngay_cn;
                rowHDTB.MAY_CN = this.may_cn;

                // vinhpv lay huonggiao_id
                // call api 
                // DataSet dset = bangts.LayHuongGiao_TiepNhan(this.DATCOC_MOI, this.KieuLapDat.DATCOC_MOI); // Kieu
                this.axios.post('/web-hopdong/goi-da-dichvu/sp_layhuonggiao_tiepnhan', {
                    kieuld_id: this.DATCOC_MOI,
                    loaihd_id: this.KieuLapDat.DATCOC_MOI,
                    phanvung_id: this.phanvung_id
                }).then(result => {
                    if (dset.data.data.length > 0) {
                        this.huonggiaotn_id = Number(result.data.data[0]["huonggiao_id"]);
                        this.quytrinh_id = Number(result.data[0].data[0]["quytrinh_id"]);
                        rowHDTB.QUYTRINH_ID = this.quytrinh_id;
                    }
                    return dsHDTB.push(rowHDTB);
                });
            } catch (error) {
                this.$toast.error('Xảy ra lỗi')
                console.log(error);
            }
        },

        async tinhSoThang(thang_bd, thang_kt) {
            try {
                // let sql = `select MONTHS_BETWEEN (to_date('${thang_kt}','MM/yyyy'), to_date('${thang_bd}','MM/yyyy')) + 1 sothang from dual`;
                let res = await this.axios.post('/web-thicong/hoinghi_truyenhinh/tinh_so_thang', {
                    thang_bd: thang_bd,
                    thang_kt: thang_kt
                });
                if(res.data.error == 200)
                    return res.data.data[0]['SOTHANG'];
                return 0;
            } catch (error) {
                this.$toast.error('Xảy ra lỗi')
                console.log(error);
            }
            
        },

        async TaoDuLieu_HDTB_DATCOC(themmoi) {
            try {
                let hdtb_dc = [];
                let rowGOI = {};
                if (themmoi) {
                    this.thuebao_dc_id = await this.getKey("THUEBAO_DC_ID").data.data;
                    rkm_id = await this.getKey("RKM_ID").data.data;
                }
                rowGOI.RKM_ID = rkm_id;
                rowGOI.THUEBAO_DC_ID = this.thuebao_dc_id;
                rowGOI.HDTB_ID = this.model.thuebao.hdtb_id ? this.model.thuebao.hdtb_id : '';
                rowGOI.NHOM_DATCOC_ID = Number(this.form.nhom_dat_coc);

                this.THANGBDMOI = Number(this.form.thang_BD.split('/').reverse().join(''));
                rowGOI.THANG_BD = this.THANGBDMOI;
                // // Tính số tháng: 
                let thang_kt = 0;
                thang_kt = this.tinhSoThang(Number(this.form.thang_BD.split('/').reverse().join('')), Number(this.form.so_thang)).data.data;
                rowGOI.THANG_KT = thang_kt;
                this.THANGKTMOI = thang_kt;
                // //----------
                let thang_bd_mg = Number(this.form.thang_MG ? this.form.thang_MG.split('/').reverse().join('') : 0);
                let thang_kt_mg = this.tinhSoThang(thang_bd_mg, Number(this.form.so_thang_MG)).data.data;
                if (thang_bd_mg == 0)
                    thang_bd_mg = thang_kt_mg = 0;
                rowGOI.THANG_BD_MG = thang_bd_mg;
                rowGOI.THANG_KT_MG = thang_kt_mg;

                rowGOI.NGAY_DK = this.form.ngay_yc;
                rowGOI.CHITIETKM_ID = Number(this.form.chi_tiet_km);
                rowGOI.GHICHU = this.form.ghi_chu;
                rowGOI.CUOC_DC = Number(this.unformat(this.form.cuoc_DC));
                rowGOI.TIEN_TD = Number(this.unformat(this.form.tien_thang));

                hdtb_dc.push(rowGOI);
                return hdtb_dc;
                // if (themmoi == false)
                // {
                //     hdtb_dc.AcceptChanges();
                //     hdtb_dc.HDTB_DATCOC.Rows[0].SetModified();
                // }
            } catch (error) {
                this.$toast.error('Xảy ra lỗi')
                console.log(error);
            }
        },
        async TaoDuLieu_CT_TIENHD(themmoi) {
            try {
                let dsCTTHD = [];
                let row = {};
                row.HDTB_ID = this.model.thuebao.hdtb_id ? this.model.thuebao.hdtb_id : '';
                if (themmoi) {
                    this.ct_tienhd_id = this.getKey("CT_TIENHD").data.data;
                }
                row.CT_TIENHD_ID = this.ct_tienhd_id;
                row.KHOANMUCTT_ID = this.KHOANMUC_TT.KMTT_DATCOC;
                let tien;
                let vat;
                tien = (Math.round(Number(this.unformat(this.form.cuoc_DC)) / 1.1));
                vat = ((Number(this.unformat(this.form.cuoc_DC))) - tien);
                row.TIEN = tien;
                row.VAT = vat;
                row.ID = this.KHOANMUC_TT.KMTT_DATCOC;
                row.LOAI_ID = 1;
                dsCTTHD.push(row);
                // if (!themmoi)
                // {
                //     dsCTTHD.AcceptChanges();
                //     dsCTTHD.CT_TIENHD.Rows[0].SetModified();
                // }
            return dsCTTHD;
            } catch (error) {
                this.$toast.error('Xảy ra lỗi')
                console.log(error);
            }
            
        },
        unformat(str) {
            return str.replace(/[^\w/\s/g]/gi, '').replace(/\s/g, '');
        },
        async tsbtnXoa_Click() {
            try {
                if (this.tthd > 1) {
                this.$toast.error("Hợp đồng không thể xóa!");
                return;
                }
                api.postXoaHD(this.axios, {
                    vhdkh_id: this.vhdkh_id,
                    vloaihd_id: this.DATCOC_MOI
                }).then(res => {
                    this.$toast.success("Xóa hợp đồng khách hàng thành công!")
                })
                this.SetButton(0);
            } catch (error) {
                this.$toast.error('Xảy ra lỗi')
                console.log(error);
            }
            
        },
        tsbtnHuyBo_Click() {
            this.SetButton(0);
        },

        tsbtnThanhToan_Click() {
            // this.$refs.dialogPayment.show();
            try {
                if (this.model.hopdong.tthd_id && this.model.hopdong.tthd_id > 1) {
                this.axios.get('/web-hopdong/ThayDoiDatCoc/lay_trangthai_hd', { params: { tthd_id: this.model.hopdong.tthd_id }})
                    .then(res => {
                        let mes = res.data.data.TRANGTHAI_HD;
                        this.$toast.success("Hợp đồng " + mes + " !")
                        return;
                    })
                }
                if(this.form.txtMaGD){
                    this.$router.push({name: 'Payment', parmas: {ma_gd: this.form.txtMaGD}});
                }
            } catch (error) {
                this.$toast.error('Xảy ra lỗi')
                console.log(error);
            }
            
        },

        async tsbtnHoanThien_Click() {
            try {
                this.loading = true 
                // if (this.docFile) {
                    let response = await api.postHTHSDatCocFile(this.axios, {
                        vnhanvien_id: this.nhanvien_id
                    })
                    if (response.data.error == 200) {
                        this.$toast.success("Cập nhật dữ liệu thành công!");
                    }else{
                        this.$toast.warning("Cập nhật dữ liệu không thành công!");
                    }
                // }
                this.options.thuebao = [];
                this.SetButton(0);
                this.removeCurrentContract();
                this.loading = false 
            } catch (error) {
                this.loading = false;
                this.$toast.error('Xảy ra lỗi')
                console.log(error);
            }
            
        },
        removeCurrentContract(){
            if(this.options.hopdong.length == 1){
                this.options.hopdong = [];
                this.form.duong_dan = "";
            } else {
                let tem = this.options.hopdong.filter(e => e.ma_gd != this.model.hopdong['ma_gd']);
                // this.model.hopdong = tem[0];
                // console.log('xxxxx ',tem)
                this.options.hopdong = [...tem];
                this.focusItemHD(tem[0]);
            }
        },

        tsbtnPhieuThu_Click() {
            try {
                InHoaDon();
            }
            catch (ex) {
                this.informError(ex);
            }
        },
        InHoaDon() {
            try {

            }
            catch (ex) {
                this.informError(ex)
                
            }
        },
        tsbtnLienHe_Click() {
            this.$refs.popupThongTinLienHe.showModal();
        },
        tsbtnEmail_Click() {
            this.$refs.popupEmail.showModal();
        },
        tsbtnThoat_Click() {
            // alert("chức năng đang phát triển")
        },

        frmTBDatCoc_File_Load() {
            this.SetButton(-1);
            this.LoadGrid();
        },
        SetButton(kieu) {
            this.button.nhapmoi = false;
            this.button.ghilai = false;
            this.button.xoa = false;
            this.button.huybo = false;
            if (kieu == -1) {
                //Bat dau
                this.button.ghilai = true;
                this.button.huybo = true;
            }

            if (kieu == 0) {
                //Bat dau
                //txtMaNV.Focus();
                this.button.nhapmoi = true;
                this.Clear();
            }

            if (kieu == 1) {
                //Them moi
                this.button.ghilai = true;
                this.button.huybo = true;
                this.Clear();
            }

            if (kieu == 2) {
                //Huy
                this.button.nhapmoi = true;
                this.button.xoa = true;
                this.Clear();
            }

            if (kieu == 3) {
                //Edit
                //txtTenNV.Focus();
                this.button.nhapmoi = true;
                this.button.ghilai = true;
                this.button.xoa = true;
                this.button.huybo = true;
            }
        },
        Clear() {
            this.form.txtMaGD = "";
            this.form.so_may_acc = "";
            this.form.ten_tb = "";
            this.form.diachi_tb = "";
            this.form.ghi_chu = "";
            this.form.cuoc_DC = 0;
            this.thuebao_dc_id = 0;
            this.thuebao_id = 0;
            this.form.so_thang = "0";
            this.form.tien_thang = "0";

            this.button.thang_bd = false;

            this.form.ty_le_sd = "0";
            this.form.ty_le_tb = "0";
            this.form.tien_tb = "0";
            this.form.tien_sd = "0";
            this.form.so_thang_MG = "0";
            this.form.cuoc_DC = "0";
        },

        async LoadGrid() {
            try {
                this.loading = true;
                await this.getDsDonViQL();
                await this.getDsNhom();
                // this.getDsNhanVien();
                await this.getDsDichVu();
                await this.getDsLoaiHinh();
                await this.getDsNhomDatCoc();

                await this.init();
                this.loading = false 
            } catch (e) {
                this.loading = false 
                this.informError(e);
            }
        },

        KiemTra_DuLieu(themmoi) {
            if(themmoi){
                if (this.kieuld_id == 0) {
                    this.$toast.error("Chỉ áp dụng đặt cọc cho thuê bao đang tồn tại trong danh bạ hoặc đang lắp đặt mới.");
                    return false;
                }
                // if (this.kieuld_id == 1) {
                //     this.$toast.error("Chỉ áp dụng đặt cọc cho thuê bao đang tồn tại trong danh bạ hoặc đang lắp đặt mới.");
                //     return false;
                // }

            }
            if(Number(this.model.thuebao.thuebao_id) <= 0){
                this.$toast.error("Không có thông tin thuê bao!");
                return false;
            }
            if (!this.form.txtMaGD) {
                this.$toast.error("Bạn chưa nhập mã giao dịch !");
                return false;
            }
            if (!this.form.so_may_acc) {
                this.$toast.error("Bạn chưa nhập số máy/account !");
                return false;
            }
            if (!this.form.selectDichVu) {
                this.$toast.error("Bạn chưa chọn dịch vụ !");
                return false;
            }
            if (!this.form.selectLoaiHinh) {
                this.$toast.error("Bạn chưa chọn loại hình dịch vụ !");
                return false;
            }
            if (!this.form.ten_tb) {
                this.$toast.error("Bạn chưa nhập tên thuê bao !");
                return false;
            }
            if (!this.form.diachi_tb) {
                this.$toast.error("Bạn chưa nhập địa chỉ thuê bao !");
                return false;
            }
            if (!this.form.nhom_dat_coc) {
                this.$toast.error("Bạn chưa chọn nhóm đặt cọc !");
                return false;
            }
            if (!this.form.tra_truoc) {
                this.$toast.error('Bạn hãy chọn khuyến mại !');
                return false;
            }
            if (!this.form.chi_tiet_km) {
                this.$toast.error('Bạn hãy chọn chi tiết khuyến mại !');
                return false;
            }
            if (Number(this.form.so_thang_MG) <= 0) {
                this.$toast.error('Hãy nhập số tháng miễn giảm !');
                return false;
            }
            if (!this.form.thang_MG) {
                this.$toast.error('Hãy nhập số tháng miễn giảm !');
                return false;
            }
            if (!this.form.ty_le_tb) {
                this.$toast.error('Hãy nhập tỷ lệ thuê bao !');
                return false;
            }
            if (!this.form.ty_le_sd) {
                this.$toast.error('Hãy nhập tỷ lệ sử dung !');
                return false;
            }
            if (!this.form.tien_tb) {
                this.$toast.error('Hãy nhập tiền thuê bao !');
                return false;
            }
            if (!this.form.tien_sd) {
                this.$toast.error('Hãy nhập tiền sử dụng !');
                return false;
            }

            if (!this.form.thang_BD) {
                this.$toast.error('Bạn chưa nhập tháng bắt đầu !');
                return false;
            }
            if (!this.form.so_thang) {
                this.$toast.error("Bạn chưa nhập số tháng phải đặt cọc !");
                return false;
            }

            if (!this.form.cuoc_DC) {
                this.$toast.error("Tiền đặt cọc phải lớn hơn 0");
                return false;
            }
            if (!this.form.tien_thang) {
                this.$toast.error("Số tiền tháng phải lớn hơn 0");
                return false;
            }
            return true
        },

        async init() {
            try {
                this.selectNV = this.nhanvien_id
                this.selectDonViQL = this.cbDonViQL.length > 0 ? this.cbDonViQL[0].donvi_id : '';
                this.selectNhom = this.cbNhom.length > 0 ? this.cbNhom[0].loainv_id : '';
                this.form.selectDichVu = this.cbDichVu.length > 0 ? this.cbDichVu[0].DICHVUVT_ID : '';
                this.form.selectLoaiHinh = this.cbLoaiHinh.length > 0 ? this.cbLoaiHinh[0].LOAITB_ID : '';
                this.form.nhom_dat_coc = this.cbNhomDatCoc.length > 0 ? this.cbNhomDatCoc[0].NHOM_DATCOC_ID : '';

                // set id đơn Vị
                this.donvi_id = this.$root.token.getDonViID();
                console.log(this.donvi_id);
                // set ngay
                this.form.ngay_yc = new Date().toLocaleString('vi-VN', { dateStyle: 'short' }).toString();
                this.form.thang_MG = new Date().toLocaleString('vi-VN', { dateStyle: 'short' }).toString().slice(3);
                this.form.thang_BD = new Date().toLocaleString('vi-VN', { dateStyle: 'short' }).toString().slice(3);
            } catch (error) {
                this.informError(error);
            }
            
        },

        informError(e) {
            // this.$toast.error("Đã có lỗi xảy ra")
            console.log(e);
        },

        async getDsDonViQL() {
            try {
                let res = await api.getCBDonViQL(this.axios);
                this.cbDonViQL = res.data.data;
            } catch (error) {
                this.informError(error);
            }
        },
        changeDVQL(i) {
            this.selectDonViQL = i;
            this.getDsNhanVien();
        },
        async getDsNhom() {
            try {
                let res = await api.getCBNhom(this.axios, {
                    nhomlnv_id: this.NHOM_LNV.CONGTACVIEN
                });
                this.cbNhom = res.data.data;
            } catch (error) {
                this.informError(error);
            }
        },
        changeNhomCTV(i) {
            this.selectNhom = i;
            this.getDsNhanVien();
        },
        async getDsNhanVien() {
            try {
                let res = await api.getCBNhanVien(this.axios, {
                    donvi_id: this.selectDonViQL,
                    loainv_id: this.NHOM_LNV.CONGTACVIEN
                });
                this.cbNhanVien = res.data.data;
            } catch (error) {
                this.informError(error);
            }
        },
        async getDsDichVu() {
            try {
                let res = await api.getCBDichVu(this.axios);
                this.cbDichVu = res.data.data;
            } catch (error) {
                this.informError(error);
            }

        },
        changeDichVu(i) {
            // console.log(i);
            this.form.selectDichVu = i;
            this.SELECT_DichVu_ID = i;
        },
        async getDsLoaiHinh() {
            try {
                let res = await api.getCBLoaiHinh(this.axios);
                this.cbLoaiHinh = res.data.data;
            } catch (error) {
                this.informError(error);
            }

        },
        async getDsNhomDatCoc() {
            try {
                let res = await api.getCBNhomDatCoc(this.axios);
                this.cbNhomDatCoc = res.data.data;
                console.log('x3');
                //this.form.nhom_dat_coc = this.cbNhomDatCoc[0].NHOM_DATCOC_ID;
            } catch (error) {
                this.informError(error);
            }

        },
        changeNhomCoc(i) {
            // alert(i)
            this.form.nhom_dat_coc = i;
            if (this.thuebao_id) {
                this.SELECTED_NHOM_COC = this.thuebao_id;
            }
        },
        async getDsKhuyenMai() {
            // let res = await api.getDsKhuyenMai(this.axios, {

            // });
        },
        async getDsKhuyenMaiChiTiet() {
            try {
                let res = await api.getDsKhuyenMaiChiTiet(this.axios, {
                    vkhuyenmai_id: 10922,
                    vloaitb_id: 58,
                    vtocdotn_id: 0,
                    vmuccuoctn_id: 0,
                    vnhom_datcoc_id: this.form.nhom_dat_coc
                });
                this.cbChiTietKM = res.data.data;
            } catch (error) {
                this.informError(error);
            }
        },

        async getDsthueBao() {
            try {
                // console.log(this.model.hopdong);
                let res = await api.getDsThueBaoDatCoc(this.axios, {
                    vhdkh_id: this.vhdkh_id,
                });
                
                if (res.data.error == 200) {
                    return res.data.data;
                    // if (res.data.data.length > 0) {
                    //     this.options.thuebao = res.data.data;
                    //     this.model.thuebao = res.data.data[0];
                    //     this.fillThongTin(this.model.thuebao);
                    // } else {
                    //     this.options.thuebao = [];
                    //     this.SetButton(1);
                    // }
                }
            } catch (error) {
                this.informError(error);
            }
        },
        getDsHopDongTuFile() {
            try {
                api.getDsHopDongTuFile(this.axios, {
                    vnhanvien_id: this.nhanvien_id,
                    vphanvung_id: this.phanvung_id
                }).then(res => {
                    if (res.data.error == 200) {
                        this.options.hopdong = res.data.data;
                        this.model.hopdong = res.data.data[0];
                        this.txtMaGD = this.model.hopdong.ma_gd;
                        this.vhdkh_id = this.vhdkh_id;
                    }
                });
            } catch (error) {
                this.informError(error);
            }
            
        },
        async fillThongTin(data) {
            try {
                // Thong tin uu dai 
                // this.form.nhom_dat_coc = this.cbNhomDatCoc[0].NHOM_DATCOC_ID;
                if(data){
                    this.ct_tienhd_id = data['ct_tienhd_id'] ? data['ct_tienhd_id'] : 0;
                    this.hdtb_id = data['hdtb_id'] ? data['hdtb_id'] : 0;
                    this.thuebao_dc_id = data['thuebao_dc_id'] ? data['thuebao_dc_id'] : 0;

                    this.form.selectDichVu = data['dichvuvt_id'] ? data['dichvuvt_id'] : 0;
                    this.SELECT_DichVu_ID = 0;
                    this.form.selectLoaiHinh = data['loaitb_id'] ? data['loaitb_id'] : 0;
                    this.SELECT_LOAI_HINH_ID = 0;
                    this.form.so_may_acc = data.ma_tb;
                    this.form.ten_tb = data.ten_tb;
                    this.form.diachi_tb = data.diachi_ld;

                    this.loaitb_id = data.loaitb_id;
                    this.dichvuvt_id = data.dichvuvt_id;
                    this.doituong_id = data.doituong_id;
                    this.hdtb_id = data.hdtb_id;
                    this.thuebao_id = data.thuebao_id;
                    this.tthd = data.tthd_id;
                    // get ds chi tiet(khuyen mai chi tiet) theo loai_hinh, nhom_coc, tra_truoc (khuyen_mai)
                    this.form.nhom_dat_coc = data.nhom_datcoc_id ? data.nhom_datcoc_id : 0;
                    
                    this.SELECTED_NHOM_COC = this.thuebao_id;
                    
                    this.form.tra_truoc = data.khuyenmai_id ? data.khuyenmai_id : -1;
                    this.form.chi_tiet_km = data.chitietkm_id ? data.chitietkm_id : -1;
                    
                    this.form.cuoc_DC = data.cuoc_dc;
                    this.form.ghi_chu = data.ghichu;
                    // Thong tin giam cuoc 
                    // if (data.thang_bd_mg)
                    //     this.form.thang_MG = data.thang_bd_mg;
                    // so thang nhap vao
                    this.form.ty_le_tb = data.tyle_tb;
                    this.form.ty_le_sd = data.tyle_sd;
                    this.form.tien_tb = data.tien_tb;
                    this.form.tien_sd = data.tien_sd;

                    
    
                    // thong tin uu dai 
                    if (data.thang_bdshow && data.thang_bdshow != "0") {
                        this.form.thang_BD = data.thang_bdshow
                        this.form.so_thang = await this.tinhSoThang(data.thang_bdshow, data.thang_ktshow);
                        this.form.tien_thang = Number(data.tien_td);
                    } else {
                        this.form.thang_BD = "";
                        this.form.so_thang = 0;
                        this.form.tien_thang = 0;
                    }
                    if (data.thang_bdmg_show && data.thang_bdmg_show != "0") {
                        this.form.thang_BD = data.thang_bdmg_show
                        this.form.so_thang_MG = await this.tinhSoThang(data.thang_bdmg_show, data.thang_ktmg_show);
                        console.log(this.tinhSoThang(data.thang_bdmg_show, data.thang_ktmg_show));
                        this.form.tien_sd = Number(data.tien_td);
                    } else {
                        this.form.thang_MG = "";
                        this.form.so_thang_MG = 0;
                        this.form.tien_sd = 0;
                    }
                }
            } catch (error) {
                this.informError(error);
            }
        },
        async khoitao(){
            // this.tt_nd = await this.$root.token.getThongTinNguoiDung().then(res => {
            //     console.log(res);
            // })
            this.phanvung_id = this.$root.token.getPhanVungID() ? this.$root.token.getPhanVungID() : '';
            this.nhanvien_id = this.$root.token.getNhanVienID();
            this.nguoi_cn = this.$root.token.getUserName() ? this.$root.token.getUserName() : '';
            this.ip_cn = "1.1.1.1";
            this.ngay_cn = ''
            this.may_cn = new Date().toLocaleString('vi-VN', { dateStyle: 'short' }).toString();
            // console.log(this.tt_nd);
        }
    },
    created() {
        try {
            
            this.khoitao();
            console.log(this.nhanvien_id, this.nguoi_cn);
            // get data cbx
            this.frmTBDatCoc_File_Load()
            // this.ip_cn = this.$root.token.getIP() ? this.$root.token.getIP() : "1.1.1.1";
            // this.ip_cn = "1.1.1.1";
            // this.ngay_cn = new Date().toLocaleString('vi-VN', { dateStyle: 'short' }).toString();
            // this.may_cn = this.$root.token.getMachine() ? this.$root.token.getMachine() : '';
            // this.may_cn = '';
        } catch (error) {
            this.informError(error);
        }
    }
};
</script>
<style>
.list a{
    cursor: pointer;
}
.no-header .e-columnheader {
    display: none;
}
li.non-active a {
    color: #d3d3d3 !important;
}

.SubcriberHistory .e-grid .e-toolbar {
    border-top: 0px;
}
.SubcriberHistory .e-detailcell {
    padding: 10px;
}

.SubcriberHistory .nav-tabs .nav-item.show .nav-link,
.nav-tabs .nav-link {
    background-color: #f5f5f5;
    border-color: #dee2e6 #dee2e6 #fff;
    border-left: 0px;
}
.SubcriberHistory-popup .page-content {
    top: 130px !important;
}

.SubcriberHistory .tabs {
    max-width: 1230px;
}

.SubcriberHistory .box-form {
    overflow: scroll;
}
</style>
