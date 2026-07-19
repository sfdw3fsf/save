<template>
    <div>
        <!-- <div class="legend-title gray_line"> <span style="color:blue;">Điểm đầu</span></div> -->
        <div class="legend-title gray_line">
            <div class="pull-left"><span style="color:blue;">Điểm đầu</span></div>
            <div class="">
                <span class="name" style="color:red; font-weight: bold; margin-left: 30px;">Liên hệ: {{lblLienHe_Dau}}</span>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Số ảo</div>
                    <div class="value">
                        <input type="text" :value="input_soao_dau" @change="e=>input_soao_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Thiết bị</div>
                    <div class="value">
                        <div class="select-custom" >
                            <select class="form-control" v-model="thietbi_dau_selected">
                                <option v-for="item in ds_loaithietbi" :key="item.thietbidc_id" :value="item.thietbidc_id">
                                    {{item.ten_tbi}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Serial</div>
                    <div class="value">
                        <input type="text" :value="input_serial_dau" @change="e=>input_serial_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Loại cáp</div>
                    <div class="value">
                        <input type="text" :value="input_loaicap_dau" @change="e=>input_loaicap_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Svlan</div>
                    <div class="value">
                        <input type="text" :value="input_svlan_dau" @change="e=>input_svlan_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Cvlan</div>
                    <div class="value">
                        <input type="text" :value="input_cvlan_dau" @change="e=>input_cvlan_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_capgoc_dau" @change="e=>input_capgoc_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_capngon_dau" @change="e=>input_capngon_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Adsl cáp</div>
                    <div class="value">
                        <input type="text" :value="input_adsl_cap_dau" @change="e=>input_adsl_cap_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Adsl port</div>
                    <div class="value">
                        <input type="text" :value="input_adsl_port_dau" @change="e=>input_adsl_port_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Tủ cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_tucapgoc_dau" @change="e=>input_tucapgoc_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Tủ cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_tucapngon_dau" @change="e=>input_tucapngon_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Nasport ID</div>
                    <div class="value">
                        <input type="text" :value="input_nasport_id_dau" @change="e=>input_nasport_id_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Vrf/Vfi</div>
                    <div class="value">
                        <input type="text" :value="input_vrf_vfi_dau" @change="e=>input_vrf_vfi_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Đội cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_doicapgoc_dau" @change="e=>input_doicapgoc_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Đội cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_doicapngon_dau" @change="e=>input_doicapngon_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Ip Dslam</div>
                    <div class="value">
                        <input type="text" :value="input_ip_dslam_dau" @change="e=>input_ip_dslam_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Tốc độ</div>
                    <div class="value">
                        <div class="select-custom">
                            <select class="form-control" v-model="tocdo_dau_selected" disabled>
                                <option v-for="item in ds_tocdokenh" :key="item.tocdo_id" :value="item.tocdo_id">
                                    {{item.toc_do}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Liên tủ</div>
                    <div class="value">
                        <input type="text" :value="input_lientu_dau" @change="e=>input_lientu_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Hộp cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_hopcapngon_dau" @change="e=>input_hopcapngon_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">TB t.dẫn</div>
                    <div class="value">
                        <input type="text" :value="input_tb_t_dan_dau" @change="e=>input_tb_t_dan_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Ip wan</div>
                    <div class="value">
                        <input type="text" :value="input_ip_wan_dau" @change="e=>input_ip_wan_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Cự ly<k-required-marker/></div>
                    <div class="value">
                        <input type="text" :value="input_culy_dau" @change="e=>input_culy_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Tuyến cáp</div>
                    <div class="value">
                        <input type="text" :value="input_tuyencap_dau" @change="e=>input_tuyencap_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">TimeSlot</div>
                    <div class="value">
                        <input type="text" :value="input_time_slot_dau" @change="e=>input_time_slot_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Link</div>
                    <div class="value">
                        <input type="text" :value="input_link_dau" @change="e=>input_link_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w100">Địa chỉ LĐ</div>
                    <div class="value">
                        <input type="text" :value="input_diachi_ld_dau" @change="e=>input_diachi_ld_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Slot OLT</div>
                    <div class="value">
                        <input type="text" :value="input_slot_olt_dau" @change="e=>input_slot_olt_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Port OLT</div>
                    <div class="value">
                        <input type="text" :value="input_port_olt_dau" @change="e=>input_port_olt_dau=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                
            </div>
            <div class="col-sm-3 col-12">
                
            </div>
        </div>
        <!-- <div class="legend-title gray_line"> <span style="color:blue;">Điểm cuối</span></div> -->
        <div class="legend-title gray_line">
            <div class="pull-left"><span style="color:blue;">Điểm cuối</span></div>
            <div class="">
                <span class="name" style="color:red; font-weight: bold; margin-left: 30px;">Liên hệ: {{lblLienHe_Cuoi}}</span>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Số ảo</div>
                    <div class="value">
                        <input type="text" :value="input_soao_cuoi" @change="e=>input_soao_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Thiết bị</div>
                    <div class="value">
                        <div class="select-custom">
                            <select class="form-control" v-model="thietbi_cuoi_selected">
                                <option v-for="item in ds_loaithietbi" :key="item.thietbidc_id" :value="item.thietbidc_id">
                                    {{item.ten_tbi}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Serial</div>
                    <div class="value">
                        <input type="text" :value="input_serial_cuoi" @change="e=>input_serial_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Loại cáp</div>
                    <div class="value">
                        <input type="text" :value="input_loaicap_cuoi" @change="e=>input_loaicap_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Svlan</div>
                    <div class="value">
                        <input type="text" :value="input_svlan_cuoi" @change="e=>input_svlan_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Cvlan</div>
                    <div class="value">
                        <input type="text" :value="input_cvlan_cuoi" @change="e=>input_cvlan_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_capgoc_cuoi" @change="e=>input_capgoc_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_capngon_cuoi" @change="e=>input_capngon_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Adsl cáp</div>
                    <div class="value">
                        <input type="text" :value="input_adsl_cap_cuoi" @change="e=>input_adsl_cap_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Adsl port</div>
                    <div class="value">
                        <input type="text" :value="input_adsl_port_cuoi" @change="e=>input_adsl_port_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Tủ cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_tucapgoc_cuoi" @change="e=>input_tucapgoc_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Tủ cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_tucapngon_cuoi" @change="e=>input_tucapngon_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Nasport ID</div>
                    <div class="value">
                        <input type="text" :value="input_nasport_id_cuoi" @change="e=>input_nasport_id_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Vrf/Vfi</div>
                    <div class="value">
                        <input type="text" :value="input_vrf_vfi_cuoi" @change="e=>input_vrf_vfi_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Đội cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_doicapgoc_cuoi" @change="e=>input_doicapgoc_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Đội cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_doicapngon_cuoi" @change="e=>input_doicapngon_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Ip Dslam</div>
                    <div class="value">
                        <input type="text" :value="input_ip_dslam_cuoi" @change="e=>input_ip_dslam_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Tốc độ</div>
                    <div class="value">
                        <div class="select-custom">
                            <select class="form-control" v-model="tocdo_cuoi_selected" disabled>
                                <option v-for="item in ds_tocdokenh" :key="item.tocdo_id" :value="item.tocdo_id">
                                    {{item.toc_do}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Liên tủ</div>
                    <div class="value">
                        <input type="text" :value="input_lientu_cuoi" @change="e=>input_lientu_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Hộp cáp ngọn</div>
                    <div class="value">
                        <input type="text"  :value="input_hopcapngon_cuoi" @change="e=>input_hopcapngon_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">TB t.dẫn</div>
                    <div class="value">
                        <input type="text" :value="input_tb_t_dan_cuoi" @change="e=>input_tb_t_dan_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Ip wan</div>
                    <div class="value">
                        <input type="text" :value="input_ip_wan_cuoi" @change="e=>input_ip_wan_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Cự ly<k-required-marker/></div>
                    <div class="value">
                        <input type="text" :value="input_culy_cuoi" @change="e=>input_culy_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Tuyến cáp</div>
                    <div class="value">
                        <input type="text" :value="input_tuyencap_cuoi" @change="e=>input_tuyencap_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">TimeSlot</div>
                    <div class="value">
                        <input type="text" :value="input_time_slot_cuoi" @change="e=>input_time_slot_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Link</div>
                    <div class="value">
                        <input type="text" :value="input_link_cuoi" @change="e=>input_link_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key w100">Địa chỉ LĐ</div>
                    <div class="value">
                        <input type="text" :value="input_diachi_ld_cuoi" @change="e=>input_diachi_ld_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Slot OLT</div>
                    <div class="value">
                        <input type="text" :value="input_slot_olt_cuoi" @change="e=>input_slot_olt_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="info-row">
                    <div class="key w100">Port OLT</div>
                    <div class="value">
                        <input type="text" :value="input_port_olt_cuoi" @change="e=>input_port_olt_cuoi=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                
            </div>
            <div class="col-sm-3 col-12">
                
            </div>
        </div>
    </div>
</template>
<script>
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import API from './API'
export default {
    name:'KenhThueRieng',
    components:{
        KRequiredMarker
    },
    data(){
        return{
            ds_tocdokenh:[],
            ds_loaithietbi:[],
            //Điểm đầu
            input_soao_dau:'',
            thietbi_dau_selected:null,
            input_serial_dau:'',
            input_loaicap_dau:'',
            input_svlan_dau:'',
            input_cvlan_dau:'',
            input_capgoc_dau:'',
            input_capngon_dau:'',
            input_adsl_cap_dau:'',
            input_adsl_port_dau:'',
            input_doicapgoc_dau:'',
            input_doicapngon_dau:'',
            input_nasport_id_dau:'',
            input_vrf_vfi_dau:'',
            input_tucapgoc_dau:'',
            input_tucapngon_dau:'',
            input_ip_dslam_dau:'',
            input_lientu_dau:'',
            tocdo_dau_selected:null,
            input_hopcapngon_dau:'',
            input_tb_t_dan_dau:'',
            input_ip_wan_dau:'',
            input_culy_dau:'0',
            input_tuyencap_dau:'',
            input_time_slot_dau:'',
            input_link_dau:'',
            input_diachi_ld_dau:'',
            input_slot_olt_dau:'',
            input_port_olt_dau:'',
            lblLienHe_Dau:'',

            //Điểm cuối
            input_soao_cuoi:'',
            thietbi_cuoi_selected:null,
            input_serial_cuoi:'',
            input_loaicap_cuoi:'',
            input_svlan_cuoi:'',
            input_cvlan_cuoi:'',
            input_capgoc_cuoi:'',
            input_capngon_cuoi:'',
            input_adsl_cap_cuoi:'',
            input_adsl_port_cuoi:'',
            input_capgoc_cuoi:'',
            input_capngon_cuoi:'',

            input_doicapgoc_cuoi:'',
            input_doicapngon_cuoi:'',

            input_nasport_id_cuoi:'',
            input_vrf_vfi_cuoi:'',
            input_tucapgoc_cuoi:'',
            input_tucapngon_cuoi:'',
            input_ip_dslam_cuoi:'',
            input_lientu_cuoi:'',
            tocdo_cuoi_selected:null,
            input_hopcapngon_cuoi:'',
            input_tb_t_dan_cuoi:'',
            input_ip_wan_cuoi:'',
            input_culy_cuoi:'0',
            input_tuyencap_cuoi:'',
            input_time_slot_cuoi:'',
            input_link_cuoi:'',
            input_diachi_ld_cuoi:'',
            input_slot_olt_cuoi:'',
            input_port_olt_cuoi:'',
            lblLienHe_Cuoi:''

        }
    },
    methods:{
        async initDuLieu(ds_tocdokenh, ds_loaithietbi){
            this.ds_tocdokenh=ds_tocdokenh
            this.ds_loaithietbi=ds_loaithietbi
        },
        async hienthi_dulieu_hdtb(data, hdtb_id){
            for(let i=0;i<data.length;i++){
                let item=data[i]
                if(item.daucuoi_id==1){
                    //Điểm đầu
                    this.thietbi_dau_selected=item.thietbidc_id!=null?item.thietbidc_id.toString():null
                    this.input_time_slot_dau=item.timeslot!=null?item.timeslot.toString():''
                    this.input_link_dau=item.link!=null?item.link.toString():''
                    this.input_serial_dau=item.serial!=null?item.serial.toString():''
                    this.input_tb_t_dan_dau=item.ten_tbi!=null?item.ten_tbi.toString():''
                    if(item.loaicap_id!=null){
                        let loai_cap=await this.fn_tt_loaicap_theo_loaicap_id(item.loaicap_id,'')
                        this.input_loaicap_dau=loai_cap!='-1'?loai_cap:''
                    }else{
                        this.input_loaicap_dau=''
                    }
                    this.input_hopcapngon_dau=item.hopcap_p!=null?item.hopcap_p.toString():''
                    this.input_tuyencap_dau=item.tuyencap!=null?item.tuyencap.toString():''
                    this.input_capgoc_dau=item.cap_goc!=null?item.cap_goc.toString():''
                    this.input_capngon_dau=item.cap_p!=null?item.cap_p.toString():''
                    this.input_lientu_dau=item.lientu!=null?item.lientu.toString():''
                    this.input_tucapgoc_dau=item.tucap_goc!=null?item.tucap_goc.toString():''
                    this.input_tucapngon_dau=item.tucap_p!=null?item.tucap_p.toString():''
                    this.input_culy_dau=item.culy!=null?item.culy.toString():''
                    this.input_doicapgoc_dau=item.doicap_goc!=null?item.doicap_goc.toString():''
                    this.input_doicapngon_dau=item.doicap_p!=null?item.doicap_p.toString():''
                    this.input_soao_dau=item.ma_lt!=null?item.ma_lt.toString():''
                    this.input_svlan_dau=item.vpi!=null?item.vpi.toString():''
                    this.input_cvlan_dau=item.vci!=null?item.vci.toString():''
                    this.input_adsl_cap_dau=item.port_mdf!=null?item.port_mdf.toString():''
                    this.input_adsl_port_dau=item.adsl_port!=null?item.adsl_port.toString():''

                    if(item.selfid01!=null&&item.selfid01!=''&&item.selfid02!=null&&item.selfid02!=''
                        &&item.selfid03!=null&&item.selfid03!=''&&item.selfid04!=null&&item.selfid04!=''){
                        this.input_nasport_id_dau=item.system!=null?item.system.toString():''+'/'
                                                +item.selfid04!=null?item.selfid04.toString():''+'/'
                                                +item.selfid03!=null?item.selfid03.toString():''+'/'
                                                +item.selfid02!=null?item.selfid02.toString():''+'/'
                                                +item.selfid01!=null?item.selfid01.toString():''

                                            
                    }else{
                        this.input_nasport_id_dau=item.system!=null?item.system.toString():''
                    }

                    this.input_ip_dslam_dau=item.ip!=null?item.ip.toString():''
                    this.input_vrf_vfi_dau=item.vrf!=null?item.vrf.toString():''
                    this.input_ip_wan_dau=item.ip_wan!=null?item.ip_wan.toString():''

                    if(item.port_id!=null&&item.port_id.toString()!=''){
                        let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                        if(vl.length>0){
                            this.input_slot_olt_dau=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                            this.input_port_olt_dau=vl[0].vitri!=null?vl[0].vitri.toString():''
                        }
                    }


                }else{
                    //Điểm cuối
                    this.thietbi_cuoi_selected=item.thietbidc_id!=null?item.thietbidc_id.toString():null
                    this.input_time_slot_cuoi=item.timeslot!=null?item.timeslot.toString():''
                    this.input_link_cuoi=item.link!=null?item.link.toString():''
                    this.input_serial_cuoi=item.serial!=null?item.serial.toString():''
                    this.input_tb_t_dan_cuoi=item.ten_tbi!=null?item.ten_tbi.toString():''
                    if(item.loaicap_id!=null){
                        let loaicap=await this.fn_tt_loaicap_theo_loaicap_id(item.loaicap_id,'')
                        this.input_loaicap_cuoi=loaicap!='-1'?loaicap:''
                    }else{
                        this.input_loaicap_cuoi=''
                    }
                    this.input_hopcapngon_cuoi=item.hopcap_p!=null?item.hopcap_p.toString():''
                    this.input_tuyencap_cuoi=item.tuyencap!=null?item.tuyencap.toString():''
                    this.input_capgoc_cuoi=item.cap_goc!=null?item.cap_goc.toString():''
                    this.input_capngon_cuoi=item.cap_p!=null?item.cap_p.toString():''
                    this.input_lientu_cuoi=item.lientu!=null?item.lientu.toString():''
                    this.input_tucapgoc_cuoi=item.tucap_goc!=null?item.tucap_goc.toString():''
                    this.input_tucapngon_cuoi=item.tucap_p!=null?item.tucap_p.toString():''
                    this.input_culy_cuoi=item.culy!=null?item.culy.toString():''
                    this.input_doicapgoc_cuoi=item.doicap_goc!=null?item.doicap_goc.toString():''
                    this.input_doicapngon_cuoi=item.doicap_p!=null?item.doicap_p.toString():''
                    this.input_soao_cuoi=item.ma_lt!=null?item.ma_lt.toString():''
                    this.input_svlan_cuoi=item.vpi!=null?item.vpi.toString():''
                    this.input_cvlan_cuoi=item.vci!=null?item.vci.toString():''
                    this.input_adsl_cap_cuoi=item.port_mdf!=null?item.port_mdf.toString():''
                    this.input_adsl_port_cuoi=item.adsl_port!=null?item.adsl_port.toString():''

                    if(item.selfid01!=null&&item.selfid01!=''&&item.selfid02!=null&&item.selfid02!=''
                        &&item.selfid03!=null&&item.selfid03!=''&&item.selfid04!=null&&item.selfid04!=''){
                        this.input_nasport_id_cuoi=item.system!=null?item.system.toString():''+'/'
                                                +item.selfid04!=null?item.selfid04.toString():''+'/'
                                                +item.selfid03!=null?item.selfid03.toString():''+'/'
                                                +item.selfid02!=null?item.selfid02.toString():''+'/'
                                                +item.selfid01!=null?item.selfid01.toString():''

                                            
                    }else{
                        this.input_nasport_id_cuoi=item.system!=null?item.system.toString():''
                    }

                    this.input_ip_dslam_cuoi=item.ip!=null?item.ip.toString():''
                    this.input_vrf_vfi_cuoi=item.vrf!=null?item.vrf.toString():''
                    this.input_ip_wan_cuoi=item.ip_wan!=null?item.ip_wan.toString():''

                    if(item.port_id!=null&&item.port_id.toString()!=''){
                        let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                        if(vl.length>0){
                            this.input_slot_olt_cuoi=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                            this.input_port_olt_cuoi=vl[0].vitri!=null?vl[0].vitri.toString():''
                        }
                    }

                }
            }
        },
        async hienthi_dulieu_thuebao(data, thuebao_id){
            for(let i=0;i<data.length;i++){
                let item=data[i]
                if(item.daucuoi_id==1){
                    //Điểm đầu
                    this.thietbi_dau_selected=item.thietbidc_id!=null?item.thietbidc_id.toString():null
                    this.input_time_slot_dau=item.timeslot!=null?item.timeslot.toString():''
                    this.input_link_dau=item.link!=null?item.link.toString():''
                    this.input_serial_dau=item.serial!=null?item.serial.toString():''
                    this.input_tb_t_dan_dau=item.ten_tbi!=null?item.ten_tbi.toString():''
                    if(item.loaicap_id!=null){
                        let loai_cap=await this.fn_tt_loaicap_theo_loaicap_id(item.loaicap_id,'')
                        this.input_loaicap_dau=loai_cap!='-1'?loai_cap:''
                    }else{
                        this.input_loaicap_dau=''
                    }
                    this.input_hopcapngon_dau=item.hopcap_p!=null?item.hopcap_p.toString():''
                    this.input_tuyencap_dau=item.tuyencap!=null?item.tuyencap.toString():''
                    this.input_capgoc_dau=item.cap_goc!=null?item.cap_goc.toString():''
                    this.input_capngon_dau=item.cap_p!=null?item.cap_p.toString():''
                    this.input_lientu_dau=item.lientu!=null?item.lientu.toString():''
                    this.input_tucapgoc_dau=item.tucap_goc!=null?item.tucap_goc.toString():''
                    this.input_tucapngon_dau=item.tucap_p!=null?item.tucap_p.toString():''
                    this.input_culy_dau=item.culy!=null?item.culy.toString():''
                    this.input_doicapgoc_dau=item.doicap_goc!=null?item.doicap_goc.toString():''
                    this.input_doicapngon_dau=item.doicap_p!=null?item.doicap_p.toString():''
                    this.input_soao_dau=item.ma_lt!=null?item.ma_lt.toString():''
                    this.input_svlan_dau=item.vpi!=null?item.vpi.toString():''
                    this.input_cvlan_dau=item.vci!=null?item.vci.toString():''
                    this.input_adsl_cap_dau=item.port_mdf!=null?item.port_mdf.toString():''
                    this.input_adsl_port_dau=item.adsl_port!=null?item.adsl_port.toString():''

                    if(item.selfid01!=null&&item.selfid01!=''&&item.selfid02!=null&&item.selfid02!=''
                        &&item.selfid03!=null&&item.selfid03!=''&&item.selfid04!=null&&item.selfid04!=''){
                        this.input_nasport_id_dau=item.system!=null?item.system.toString():''+'/'
                                                +item.selfid04!=null?item.selfid04.toString():''+'/'
                                                +item.selfid03!=null?item.selfid03.toString():''+'/'
                                                +item.selfid02!=null?item.selfid02.toString():''+'/'
                                                +item.selfid01!=null?item.selfid01.toString():''

                                            
                    }else{
                        this.input_nasport_id_dau=item.system!=null?item.system.toString():''
                    }

                    this.input_ip_dslam_dau=item.ip!=null?item.ip.toString():''
                    this.input_vrf_vfi_dau=item.vrf!=null?item.vrf.toString():''
                    this.input_ip_wan_dau=item.ip_wan!=null?item.ip_wan.toString():''

                    if(item.port_id!=null&&item.port_id.toString()!=''){
                        let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                        if(vl.length>0){
                            this.input_slot_olt_dau=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                            this.input_port_olt_dau=vl[0].vitri!=null?vl[0].vitri.toString():''
                        }
                    }

                    //
                    this.tocdo_dau_selected=item.tocdo_id
                    this.tocdo_cuoi_selected=item.tocdo_id
                    this.input_diachi_ld_dau=item.diachi!=null?item.diachi.toString():''
                    //let lienhe_cuoi=await this.sp_lay_thongtin_lh_db_tsl(thuebao_id, 0)
                    let lienhe_cuoi=await this.sp_lay_thongtin_lh_db_tsl(thuebao_id, 1)
                    if(lienhe_cuoi.length>0){
                        this.lblLienHe_Dau=lienhe_cuoi[0].lienhe!=null?lienhe_cuoi[0].lienhe:''
                    }
                }else{
                    //Điểm cuối
                    this.thietbi_cuoi_selected=item.thietbidc_id!=null?item.thietbidc_id.toString():null
                    this.input_time_slot_cuoi=item.timeslot!=null?item.timeslot.toString():''
                    this.input_link_cuoi=item.link!=null?item.link.toString():''
                    this.input_serial_cuoi=item.serial!=null?item.serial.toString():''
                    this.input_tb_t_dan_cuoi=item.ten_tbi!=null?item.ten_tbi.toString():''
                    if(item.loaicap_id!=null){
                        let loaicap=await this.fn_tt_loaicap_theo_loaicap_id(item.loaicap_id,'')
                        this.input_loaicap_cuoi=loaicap!='-1'?loaicap:''
                    }else{
                        this.input_loaicap_cuoi=''
                    }
                    this.input_hopcapngon_cuoi=item.hopcap_p!=null?item.hopcap_p.toString():''
                    this.input_tuyencap_cuoi=item.tuyencap!=null?item.tuyencap.toString():''
                    this.input_capgoc_cuoi=item.cap_goc!=null?item.cap_goc.toString():''
                    this.input_capngon_cuoi=item.cap_p!=null?item.cap_p.toString():''
                    this.input_lientu_cuoi=item.lientu!=null?item.lientu.toString():''
                    this.input_tucapgoc_cuoi=item.tucap_goc!=null?item.tucap_goc.toString():''
                    this.input_tucapngon_cuoi=item.tucap_p!=null?item.tucap_p.toString():''
                    this.input_culy_cuoi=item.culy!=null?item.culy.toString():''
                    this.input_doicapgoc_cuoi=item.doicap_goc!=null?item.doicap_goc.toString():''
                    this.input_doicapngon_cuoi=item.doicap_p!=null?item.doicap_p.toString():''
                    this.input_soao_cuoi=item.ma_lt!=null?item.ma_lt.toString():''
                    this.input_svlan_cuoi=item.vpi!=null?item.vpi.toString():''
                    this.input_cvlan_cuoi=item.vci!=null?item.vci.toString():''
                    this.input_adsl_cap_cuoi=item.port_mdf!=null?item.port_mdf.toString():''
                    this.input_adsl_port_cuoi=item.adsl_port!=null?item.adsl_port.toString():''

                    if(item.selfid01!=null&&item.selfid01!=''&&item.selfid02!=null&&item.selfid02!=''
                        &&item.selfid03!=null&&item.selfid03!=''&&item.selfid04!=null&&item.selfid04!=''){
                        this.input_nasport_id_cuoi=item.system!=null?item.system.toString():''+'/'
                                                +item.selfid04!=null?item.selfid04.toString():''+'/'
                                                +item.selfid03!=null?item.selfid03.toString():''+'/'
                                                +item.selfid02!=null?item.selfid02.toString():''+'/'
                                                +item.selfid01!=null?item.selfid01.toString():''

                                            
                    }else{
                        this.input_nasport_id_cuoi=item.system!=null?item.system.toString():''
                    }

                    this.input_ip_dslam_cuoi=item.ip!=null?item.ip.toString():''
                    this.input_vrf_vfi_cuoi=item.vrf!=null?item.vrf.toString():''
                    this.input_ip_wan_cuoi=item.ip_wan!=null?item.ip_wan.toString():''

                    if(item.port_id!=null&&item.port_id.toString()!=''){
                        let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                        if(vl.length>0){
                            this.input_slot_olt_cuoi=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                            this.input_port_olt_cuoi=vl[0].vitri!=null?vl[0].vitri.toString():''
                        }
                    }
                    //
                    this.input_diachi_ld_cuoi=item.diachi!=null?item.diachi.toString():''
                    let lienhe_cuoi=await this.sp_lay_thongtin_lh_db_tsl(thuebao_id, 2)
                    if(lienhe_cuoi.length>0){
                        this.lblLienHe_Cuoi=lienhe_cuoi[0].lienhe!=null?lienhe_cuoi[0].lienhe:''
                    }

                }
            }
        },
        async lay_tt_vatly_theo_port_logic(port_id){
            try{
                this.loading(true)
                let response = await API.lay_tt_vatly_theo_port_logic(this.axios, port_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async fn_tt_loaicap_theo_loaicap_id(data, defaultValue){
            try{
                this.loading(true)
                let response = await API.fn_tt_loaicap_theo_loaicap_id(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return defaultValue
                }
            }catch(e){
                this.loading(false)
                return defaultValue
            }
        },
        async sp_lay_thongtin_lh_db_tsl(thuebao_id, daucuoi_id){
            try{
                this.loading(true)
                let response = await API.sp_lay_thongtin_lh_db_tsl(this.axios, thuebao_id, daucuoi_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },

        async clearDuLieu(){
            //Điểm đầu
            this.input_soao_dau=''
            this.thietbi_dau_selected=null
            this.input_serial_dau=''
            this.input_loaicap_dau=''
            this.input_svlan_dau=''
            this.input_cvlan_dau=''
            this.input_capgoc_dau=''
            this.input_capngon_dau=''
            this.input_adsl_cap_dau=''
            this.input_adsl_port_dau=''
            this.input_doicapgoc_dau=''
            this.input_doicapngon_dau=''
            this.input_nasport_id_dau=''
            this.input_vrf_vfi_dau=''
            this.input_tucapgoc_dau=''
            this.input_tucapngon_dau=''
            this.input_ip_dslam_dau=''
            this.input_lientu_dau=''
            this.tocdo_dau_selected=null
            this.input_hopcapngon_dau=''
            this.input_tb_t_dan_dau=''
            this.input_ip_wan_dau=''
            this.input_culy_dau='0'
            this.input_tuyencap_dau=''
            this.input_time_slot_dau=''
            this.input_link_dau=''
            this.input_diachi_ld_dau=''
            this.input_slot_olt_dau=''
            this.input_port_olt_dau=''
            this.lblLienHe_Dau=''

            //Điểm cuối
            this.input_soao_cuoi=''
            this.thietbi_cuoi_selected=null
            this.input_serial_cuoi=''
            this.input_loaicap_cuoi=''
            this.input_svlan_cuoi=''
            this.input_cvlan_cuoi=''
            this.input_capgoc_cuoi=''
            this.input_capngon_cuoi=''
            this.input_adsl_cap_cuoi=''
            this.input_adsl_port_cuoi=''
            this.input_doicapgoc_cuoi=''
            this.input_doicapngon_cuoi=''
            this.input_nasport_id_cuoi=''
            this.input_vrf_vfi_cuoi=''
            this.input_tucapgoc_cuoi=''
            this.input_tucapngon_cuoi=''
            this.input_ip_dslam_cuoi=''
            this.input_lientu_cuoi=''
            this.tocdo_cuoi_selected=null
            this.input_hopcapngon_cuoi=''
            this.input_tb_t_dan_cuoi=''
            this.input_ip_wan_cuoi=''
            this.input_culy_cuoi='0'
            this.input_tuyencap_cuoi=''
            this.input_time_slot_cuoi=''
            this.input_link_cuoi=''
            this.input_diachi_ld_cuoi=''
            this.input_slot_olt_cuoi=''
            this.input_port_olt_cuoi=''
            this.lblLienHe_Cuoi=''
        }
    }
}
</script>
<style>
.gray_line{
    border-bottom: 1px solid gray !important;
    font-weight: normal !important;
}
</style>