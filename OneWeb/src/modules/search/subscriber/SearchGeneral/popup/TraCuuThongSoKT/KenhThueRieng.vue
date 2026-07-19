<template>
    <div class="row">
        <div class="box-form">
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Account</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" v-model= "itemdata.ma_tb"  ref= "Account" >
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Mã HĐ - TTKD</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ma_pl" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Modem</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.modem" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Loại kênh</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.loaikenh" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Chủ quản</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tenchuquan" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Trang bị</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.trangbi" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" >Tốc độ</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tocdo" readonly> 
                        </div>
                    </div>
                </div>
                
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Thời hạn</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.thoihan" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cước Thuê cổng (no VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_tc" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cước Thuê kênh (no VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_tk" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" >Cước Thiết bị (no VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_tbi" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cước HT</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_ht" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số ảo đầu</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ma_lt_dau" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Số ảo cuối</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ma_lt" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Địa chỉ đầu</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.diachi_dau"  readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Địa chỉ cuối</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.diachi" readonly> 
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Thiết bị đầu</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tbi_dau" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Thiết bị cuối</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tbi_cuoi" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cáp đầu</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cap_dau" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cáp cuối</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cap_cuoi" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Site ID đầu</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.site_id_dau" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Site ID cuối</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.site_id" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">VRF đầu</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.vrf_dau" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">VRF cuối</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.vrf" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số lượng sợi quang</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.sl_soiquang" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Điểm chia đầu</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.s2" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Điểm chia cuối</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.s2" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Ghi chú</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "ghichu" readonly>
                        </div>
                    </div>
                </div>                
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
            lblLienHe_Cuoi:'',

            itemdata:null,
            ghichu:'',

        }
    },
    methods:{
        async initDuLieu(ds_tocdokenh, ds_loaithietbi){
            this.ds_tocdokenh=ds_tocdokenh
            this.ds_loaithietbi=ds_loaithietbi
        },
        
        async hienthi_dulieu_thuebao(data, thuebao_id,ghichu,ma_tb){
            console.log('hienthidulieu')
            console.log(data)
            if (data){
                this.itemdata = data
                this.itemdata.ma_tb = ma_tb
                this.ghichu = ghichu
            }            
        },
        

        async clearDuLieu(){
            this.ghichu = ''
            this.itemdata = null            
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