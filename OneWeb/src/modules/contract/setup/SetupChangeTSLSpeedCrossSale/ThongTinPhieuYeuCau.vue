<template>
    <vue-card>
        <div class="legend-title">
            <div class="pull-left">
                <span></span> Thông tin phiếu yêu cầu
            </div>
            <div class="clearfix"></div>
        </div>   
        <b-row>
            <div class="col-sm-3">
                <div class="info-row">
                    <div class="key w90">Mã giao dịch</div>
                    <div class="value">
                        <div class="input-more-button">
                        <button class="btn" @click="showPopupSearchContract">
                            <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input type="text" class="form-control bold" v-model="model.v2_ma_giao_dich" id="v2_ma_giao_dich" @keydown="txtMaGD_KeyPress">
                        <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract" 
                                :loai_hd_id="16" :dichvu_id="Number(model.v2_dich_vu)" 
                                :ngay_yc="model.v2_ngay_yeu_cau ? model.v2_ngay_yeu_cau : ''" 
                                :ngay_kt="model.v2_ngay_yeu_cau ? model.v2_ngay_yeu_cau : ''" 
                                :trangthai_hd="1" />
                        <!-- <ejs-textbox v-model="model.v2_ma_giao_dich" id="v2_ma_giao_dich"></ejs-textbox> -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <vue-date format="DD/MM/YYYY" type="datetime" v-model="model.v2_ngay_yeu_cau" label="Ngày yêu cầu"></vue-date>
            </div>
            <div class="col-sm-3">
                <vue-element label="Dịch vụ" class="col-md-12">
                    <ejs-dropdownlist id="v2_dich_vu"
                        locale="vi-VN" @change="cboDichVuVT_SelectedValueChanged($event)"
                        v-model="model.v2_dich_vu"
                        :dataSource="v2_LstDichVu"
                        :allowFiltering="true" :filtering="onFilterV2DichVu"
                        :fields="{ value: 'ID', text: 'NAME' }"
                    />
                </vue-element>
            </div>
            <div class="col-sm-3">
                <vue-element label="Loại hình" class="col-md-12">
                    <ejs-dropdownlist id="v2_loai_hinh"
                        locale="vi-VN" @change="cboLoaihinhTB_SelectedValueChanged($event)"
                        v-model="model.v2_loai_hinh" :enabled="false"
                        :dataSource="v2_LstLoaiHinh"
                        :allowFiltering="true" :filtering="onFilterV2LoaiHinh"
                        :fields="{ value: 'ID', text: 'NAME' }"
                    />
                </vue-element>
            </div>
        </b-row>
        <b-row>
            <div class="col-sm-3">
                <div class="info-row">
                    <div class="key w90">Mã hợp đồng</div>
                    <div class="value">
                        <ejs-textbox class="bold" v-model="model.v2_ma_hop_dong" id="v2_ma_hop_dong"></ejs-textbox>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <vue-date format="DD/MM/YYYY" type="datetime" v-model="model.v2_ngay_lap_hd" label="Ngày lập HĐ"></vue-date>
            </div>
            <div class="col-sm-3">
                <vue-element label="Kiểu yêu cầu" class="col-md-12">
                <ejs-dropdownlist id="v2_kieu_yeu_cau"
                    locale="vi-VN" @change="cboKieuLD_SelectedValueChanged($event)"
                    v-model="model.v2_kieu_yeu_cau"
                    :dataSource="v2_LstKieuYeuCau"
                    :allowFiltering="true" :filtering="onFilterV2KieuYeuCau"
                    :fields="{ value: 'kieuld_id', text: 'ten_kieuld' }"
                />
                </vue-element>
            </div>
            <div class="col-sm-3">
                <div class="col-md-12">
                    <div class="info-row">
                        <div class="key w100">Mã HĐ - TTKD</div>
                        <div class="value">
                            <input type="text" class="form-control bold" v-model="model.v2_ma_hd_ttkd" id="v2_ma_hd_ttkd">
                        </div>
                    </div>
                </div>
            </div>
        </b-row>
        <b-row>
            <div class="col-sm-3">
                <div class="info-row">
                <div class="key w20 nowrap">
                    <div class="check-action">
                    <input id="v2_chb_so_cv_lct"
                        type="checkbox" @change="chkCV_CheckedChanged"
                        class="check">
                    <span class="name">Số CV/LCT</span>
                    </div>
                </div>
                <div class="value">
                    <input type="text" class="form-control" v-model="model.v2_so_sv_lct" id="v2_so_sv_lct">
                    <!-- <ejs-textbox v-model="model.v2_so_sv_lct" id="v2_so_sv_lct"></ejs-textbox> -->
                </div>
                </div>
            </div>
            <div class="col-sm-3">
                <vue-date format="DD/MM/YYYY" type="datetime" v-model="model.v2_ngay_cv" label="Ngày CV" :disable="!v2_enable_ngay_cv"></vue-date>
            </div>
        </b-row>
    </vue-card>
</template>
<script>
import { Query } from '@syncfusion/ej2-data'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
export default {
    name:'ThongTinPhieuYeuCau',
    components:{
        SearchContractModal
    },
    data() {
        return {
            model: {
                v2_ma_giao_dich: "",
                v2_ngay_yeu_cau: "",
                v2_ma_hop_dong: "",
                v2_ngay_lap_hd: "",
                v2_ma_hd_ttkd: "",
                v2_so_cv_lct: "",
                v2_ngay_cv: "",
                v2_dich_vu: "0",
                v2_loai_hinh: "",
                v2_kieu_yeu_cau: "",
            },
            v2_LstDichVu: [],
            v2_LstLoaiHinh: [],  
            v2_LstKieuYeuCau: [],
            v2_enable_ngay_cv: false,
        }
    },
    computed: {

    },
    methods: {
        onFilterV2DichVu: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.v2_LstDichVu, query);
        },
        onFilterV2LoaiHinh: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.v2_LstLoaiHinh, query);
        },
        onFilterV2KieuYeuCau: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_kieuld', 'contains', e.text, true) : query;
            e.updateData(this.v2_LstKieuYeuCau, query);
        },
        cboDichVuVT_SelectedValueChanged(event) {
            this.$emit('CboDichVuVT_SelectedValueChanged', event.value)
        },
        cboLoaihinhTB_SelectedValueChanged(event) {
            this.$emit('CboLoaihinhTB_SelectedValueChanged', event.value)
        },
        cboKieuLD_SelectedValueChanged(event) {
            this.$emit('CboKieuLD_SelectedValueChanged', event.value)
        },
        txtMaGD_KeyPress(event) {
            this.$emit('txtMaGD_KeyPress', event);
        },
        showPopupSearchContract() {
            this.$refs.popupSearchContract.showModal();
        },
        acceptSearchContract(item){
            this.$emit('acceptSearchContract', item.ma_gd);
        },
        chkCV_CheckedChanged() {
            this.$emit('chkCV_CheckedChanged');
        },  
    },
    mounted() {
        
    },
}
</script>