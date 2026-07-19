<template>
    <div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Tốc độ CIR</div>
                    <div class="value">
                        <div class="select-custom">
                            <select class="form-control" v-model="tocdo_cir_selected" disabled>
                                <option v-for="item in ds_tocdokenh" :key="item.tocdo_id" :value="item.tocdo_id">
                                    {{item.toc_do}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">
                        <div class="check-action">
                            <input type="checkbox" class="check" v-model="checkTocDoPir" disabled >
                            <span class="name">TĐ PIR</span>
                        </div>
                    </div>
                    <div class="value">
                        <div class="select-custom">
                            <select class="form-control" v-model="tocdo_pir_selected" disabled>
                                <option v-for="item in ds_tocdokenh" :key="item.tocdo_id" :value="item.tocdo_id">
                                    {{item.toc_do}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Site ID</div>
                    <div class="value">
                        <input type="text" :value="input_site_id" @change="e=>input_site_id=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="legend-title gray_line">Thông tin PE(router bên VTN)</div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Router</div>
                    <div class="value">
                        <input type="text" :value="input_router" @change="e=>input_router=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Slot</div>
                    <div class="value">
                        <input type="text" :value="input_slot" @change="e=>input_slot=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Port PE</div>
                    <div class="value">
                        <input type="text" :value="input_port_pe" @change="e=>input_port_pe=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Trạm T.Bị</div>
                    <div class="value">
                        <input type="text" :value="input_tram_tbi" @change="e=>input_tram_tbi=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-8 col-12">
                <div class="info-row">
                    <div class="key w100">Hướng kết nối</div>
                    <div class="value">
                        <input type="text" :value="input_huongketnoi" @change="e=>input_huongketnoi=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Dslam</div>
                    <div class="value">
                        <input type="text" :value="input_dslam" @change="e=>input_dslam=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Adsl Port</div>
                    <div class="value">
                        <input type="text" :value="input_adsl_port" @change="e=>input_adsl_port=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Port MDF</div>
                    <div class="value">
                        <input type="text" :value="input_port_mdf" @change="e=>input_port_mdf=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">NasportID</div>
                    <div class="value">
                        <input type="text" :value="input_nasport_id" @change="e=>input_nasport_id=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Ip Dslam</div>
                    <div class="value">
                        <input type="text" :value="input_ip_dslam" @change="e=>input_ip_dslam=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">SvLan</div>
                    <div class="value">
                        <input type="text" :value="input_svlan" @change="e=>input_svlan=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8 col-12">
                <div class="info-row">
                    <div class="key w100">Wan IP</div>
                    <div class="value">
                        <input type="text" :value="input_wan_ip" @change="e=>input_wan_ip=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">CvLan</div>
                    <div class="value">
                        <input type="text" :value="input_cvlan" @change="e=>input_cvlan=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8 col-12">
                <div class="info-row">
                    <div class="key w100">Lan IP</div>
                    <div class="value">
                        <input type="text" :value="input_lan_ip" @change="e=>input_lan_ip=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Vrf/Vfi</div>
                    <div class="value">
                        <input type="text" :value="input_vrf_vfi" @change="e=>input_vrf_vfi=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8 col-12">
                <div class="info-row">
                    <div class="key w100">Link</div>
                    <div class="value">
                        <input type="text" :value="input_link" @change="e=>input_link=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Route</div>
                    <div class="value">
                        <input type="text" :value="input_route" @change="e=>input_route=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8 col-12">
                <div class="info-row">
                    <div class="key w100">Thiết bị ĐC</div>
                    <div class="value">
                        <input type="text" :value="input_thietbi_dc" @change="e=>input_thietbi_dc=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Serial</div>
                    <div class="value">
                        <input type="text" :value="input_serial" @change="e=>input_serial=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Thiết bị</div>
                    <div class="value">
                        <div class="select-custom">
                            <select class="form-control" v-model="thietbi_selected">
                                <option v-for="item in ds_loaithietbiWan" :key="item.thietbidc_id" :value="item.thietbidc_id">
                                    {{item.ten_tbi}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Loại cáp</div>
                    <div class="value">
                        <input type="text" :value="input_loaicap" @change="e=>input_loaicap=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Liên tủ</div>
                    <div class="value">
                        <input type="text" :value="input_lientu" @change="e=>input_lientu=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="info-row">
                        <div class="key w100">Tủ cáp gốc</div>
                        <div class="value">
                            <input type="text" :value="input_tucapgoc" @change="e=>input_tucapgoc=e.target.value" readonly class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Tủ cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_tucapngon" @change="e=>input_tucapngon=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Hộp cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_hopcapngon" @change="e=>input_hopcapngon=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="info-row">
                        <div class="key w100">Đôi cáp gốc</div>
                        <div class="value">
                            <input type="text" :value="input_doicapgoc" @change="e=>input_doicapgoc=e.target.value" readonly class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Đôi cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_doicapngon" @change="e=>input_doicapngon=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Tuyến cáp</div>
                    <div class="value">
                        <input type="text" :value="input_tuyencap" @change="e=>input_tuyencap=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="info-row">
                        <div class="key w100">Cáp gốc</div>
                        <div class="value">
                            <input type="text" :value="input_capgoc" @change="e=>input_capgoc=e.target.value" readonly class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_capngon" @change="e=>input_capngon=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Cự ly</div>
                    <div class="value">
                        <input type="text" :value="input_culy" @change="e=>input_culy=e.target.value" readonly class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="info-row">
                        <div class="key w100">Slot OLT</div>
                        <div class="value">
                            <input type="text" :value="input_slot_olt" @change="e=>input_slot_olt=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Port OLT</div>
                    <div class="value">
                        <input type="text" :value="input_port_olt" @change="e=>input_port_olt=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                
            </div>
        </div>
    </div>
</template>
<script>
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import API from './API' 
export default {
    name:'MegawanMetronet',
    components:{
        KRequiredMarker
    },
    data(){
        return {
            ds_tocdokenh:[],
            ds_loaithietbiWan:[],
            tocdo_cir_selected:null,
            tocdo_pir_selected:null,
            input_site_id:'',
            input_router:'',
            input_route:'',
            input_slot:'',
            input_port_pe:'',
            input_tram_tbi:'',
            input_huongketnoi:'',
            input_dslam:'',
            input_adsl_port:'',
            input_port_mdf:'',
            input_nasport_id:'',
            input_ip_dslam:'',
            input_svlan:'',
            input_wan_ip:'',
            input_cvlan:'',
            input_lan_ip:'',
            input_vrf_vfi:'',
            input_link:'',
            input_thietbi_dc:'',
            input_serial:'',
            input_loaicap:'',
            thietbi_selected:null,
            input_lientu:'',
            input_tucapgoc:'',
            input_tucapngon:'',
            input_hopcapngon:'',
            input_doicapgoc:'',
            input_doicapngon:'',
            input_tuyencap:'',
            input_capgoc:'',
            input_capngon:'',
            input_culy:'0',
            input_slot_olt:'',
            input_port_olt:'',
            checkTocDoPir:false

        }
    },
    methods:{
        async initDuLieu(ds_tocdokenh, ds_loaithietbi){
            this.ds_tocdokenh=ds_tocdokenh
            this.ds_loaithietbiWan=ds_loaithietbi
            if(this.ds_tocdokenh.length>0){
                this.tocdo_cir_selected=this.ds_tocdokenh[0].tocdo_id
                this.tocdo_pir_selected=this.ds_tocdokenh[0].tocdo_id
            }
            if(this.ds_loaithietbiWan.length>0){
                this.thietbi_selected=this.ds_loaithietbiWan[0].thietbidc_id
            }
        },
        async hienthi_dulieu_hdtb(item, hdtb_id){
            let result=await this.sp_loai_tbi_giaophieu_tbi_theo_hdtb_id(hdtb_id)
            if(result.length>0){
                this.input_thietbi_dc=result[0].loai_tbi
            }else{
                this.input_thietbi_dc=''
            }
            this.input_port_mdf=item.port_mdf!=null?item.port_mdf.toString():''
            this.input_adsl_port=item.adsl_port!=null?item.adsl_port.toString():''
            this.input_router=item.pe!=null?item.pe.toString():''
            this.input_slot=item.slot_pe!=null?item.slot_pe.toString():''
            this.input_port_pe=item.port_pe!=null?item.port_pe.toString():''
            this.input_dslam=item.tendslam!=null?item.tendslam:''
            this.thietbi_selected=item.thietbidc_id!=null?item.thietbidc_id.toString():''

            if(item.selfid01!=null&&item.selfid01!=''){
                this.input_nasport_id=item.system!=null?item.system.toString():''+'/'
                                        +item.selfid04!=null?item.selfid04.toString():''+'/'
                                        +item.selfid03!=null?item.selfid03.toString():''+'/'
                                        +item.selfid02!=null?item.selfid02.toString():''+'/'
                                        +item.selfid01!=null?item.selfid01.toString():''

                                    
            }else{
                this.input_nasport_id=item.system!=null?item.system.toString():''
            }
            this.input_ip_dslam=item.ip!=null?item.ip.toString():''
            this.input_tram_tbi=item.ten_dv!=null?item.ten_dv.toString():''
            this.input_svlan=item.svlan!=null?item.svlan.toString():''
            this.input_cvlan=item.cvlan!=null?item.cvlan.toString():''
            this.input_vrf_vfi=item.vrf!=null?item.vrf.toString():''
            this.input_route=item.route!=null?item.route.toString():''
            this.input_link=item.link!=null?item.link.toString():''
            this.input_serial=item.serial!=null?item.serial.toString():''
            this.input_wan_ip=item.wan_ip!=null?item.wan_ip.toString():''
            this.input_loaicap=item.loaicap!=null?item.loaicap.toString():''
            this.input_lan_ip=item.lan_ip!=null?item.lan_ip.toString():''
            this.input_tuyencap=item.tuyencap!=null?item.tuyencap.toString():''
            this.input_tucapgoc=item.tucap_goc!=null?item.tucap_goc.toString():''
            this.input_hopcapngon=item.hopcap_p!=null?item.hopcap_p.toString():''
            this.input_tucapngon=item.tucap_p!=null?item.tucap_p.toString():''
            this.input_tuyencap=item.tuyencap!=null?item.tuyencap.toString():''
            this.input_culy=item.culy!=null?item.culy.toString():''
            this.input_capgoc=item.cap_goc!=null?item.cap_goc.toString():''
            this.input_capngon=item.cap_p!=null?item.cap_p.toString():''
            this.input_lientu=item.lientu!=null?item.lientu.toString():''

            if(item.port_id!=null&&item.port_id.toString()!=''){
                let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                if(vl.length>0){
                    this.input_slot_olt=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                    this.input_port_olt=vl[0].vitri!=null?vl[0].vitri.toString():''
                }
            }
            this.tocdo_cir_selected=item.tocdo_id
            if(item.tocdo_pir_id!=null){
                this.checkTocDoPir=true
                this.tocdo_pir_selected=item.tocdo_pir_id
            }else{
                this.checkTocDoPir=false
                his.tocdo_pir_selected=item.tocdo_id
            }
            if(item.doicap_goc!=null&&item.doicap_goc!=-1){
                this.input_doicapgoc=item.doicap_goc.toString()
            }else{
                this.input_doicapgoc=''
            }

            if(item.doicap_p!=null&&item.doicap_p!=-1){
                this.input_doicapngon=item.doicap_p
            }else{
                this.input_doicapngon=''
            }

            this.input_site_id=item.site_id!=null?item.site_id:''

            let ds_kn=await this.lay_huongkn_moi_cu(hdtb_id)
            if(ds_kn.length>0){
                if(ds_kn[0].huong_kn_moi!=null&&ds_kn[0].huong_kn_moi.toString()!=''){
                    this.input_huongketnoi=ds_kn[0].huong_kn_moi!=null?ds_kn[0].huong_kn_moi.toString():''
                }else{
                    this.input_huongketnoi=ds_kn[0].huong_kn_cu!=null?ds_kn[0].huong_kn_cu.toString():''
                }
            }else{
                this.input_huongketnoi=''
            }
        },
        async hienthi_dulieu_thuebao(item, thuebao_id){
            let result=await this.sp_ds_loai_tbi_theo_thuebao_id(thuebao_id)
            if(result.length>0){
                this.input_thietbi_dc=result[0].loai_tbi
            }else{
                this.input_thietbi_dc=''
            }
            this.input_port_mdf=item.port_mdf!=null?item.port_mdf.toString():''
            this.input_adsl_port=item.adsl_port!=null?item.adsl_port.toString():''
            this.input_router=item.pe!=null?item.pe.toString():''
            this.input_slot=item.slot_pe!=null?item.slot_pe.toString():''
            this.input_port_pe=item.port_pe!=null?item.port_pe.toString():''
            this.input_dslam=item.tendslam!=null?item.tendslam:''
            this.thietbi_selected=item.thietbidc_id!=null?item.thietbidc_id.toString():''

            if(item.selfid01!=null&&item.selfid01!=''&&item.selfid02!=null&&item.selfid02!=''
                &&item.selfid03!=null&&item.selfid03!=''&&item.selfid04!=null&&item.selfid04!=''){
                this.input_nasport_id=item.system!=null?item.system.toString():''+'/'
                                        +item.selfid04!=null?item.selfid04.toString():''+'/'
                                        +item.selfid03!=null?item.selfid03.toString():''+'/'
                                        +item.selfid02!=null?item.selfid02.toString():''+'/'
                                        +item.selfid01!=null?item.selfid01.toString():''

                                    
            }else{
                this.input_nasport_id=item.system!=null?item.system.toString():''
            }
            
            this.input_ip_dslam=item.ip!=null?item.ip.toString():''
            this.input_tram_tbi=item.ten_dv!=null?item.ten_dv.toString():''
            this.input_svlan=item.svlan!=null?item.svlan.toString():''
            this.input_cvlan=item.cvlan!=null?item.cvlan.toString():''
            this.input_vrf_vfi=item.vrf!=null?item.vrf.toString():''
            this.input_route=item.route!=null?item.route.toString():''
            this.input_link=item.link!=null?item.link.toString():''
            this.input_serial=item.serial!=null?item.serial.toString():''
            this.input_wan_ip=item.wan_ip!=null?item.wan_ip.toString():''
            this.input_loaicap=item.loaicap!=null?item.loaicap.toString():''
            this.input_lan_ip=item.lan_ip!=null?item.lan_ip.toString():''
            this.input_tuyencap=item.tuyencap!=null?item.tuyencap.toString():''
            this.input_tucapgoc=item.tucap_goc!=null?item.tucap_goc.toString():''
            this.input_hopcapngon=item.hopcap_p!=null?item.hopcap_p.toString():''
            this.input_tucapngon=item.tucap_p!=null?item.tucap_p.toString():''
            this.input_tuyencap=item.tuyencap!=null?item.tuyencap.toString():''
            this.input_culy=item.culy!=null?item.culy.toString():''
            this.input_capgoc=item.cap_goc!=null?item.cap_goc.toString():''
            this.input_capngon=item.cap_p!=null?item.cap_p.toString():''
            this.input_lientu=item.lientu!=null?item.lientu.toString():''

            if(item.port_id!=null&&item.port_id.toString()!=''){
                let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                if(vl.length>0){
                    this.input_slot_olt=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                    this.input_port_olt=vl[0].vitri!=null?vl[0].vitri.toString():''
                }
            }
            this.tocdo_cir_selected=item.tocdo_id
            if(item.tocdo_pir_id!=null){
                this.checkTocDoPir=true
                this.tocdo_pir_selected=item.tocdo_pir_id
            }else{
                this.checkTocDoPir=false
                his.tocdo_pir_selected=item.tocdo_id
            }
            if(item.doicap_goc!=null&&item.doicap_goc!=-1){
                this.input_doicapgoc=item.doicap_goc.toString()
            }else{
                this.input_doicapgoc=''
            }

            if(item.doicap_p!=null&&item.doicap_p!=-1){
                this.input_doicapngon=item.doicap_p
            }else{
                this.input_doicapngon=''
            }

            this.input_site_id=item.site_id!=null?item.site_id:''

            if(item.huong_kn!=null){
                this.input_huongketnoi=await this.fn_lay_text_huongketnoi_v2(item.huong_kn)
            }else{
                this.input_huongketnoi=''
            }

        },
        async sp_loai_tbi_giaophieu_tbi_theo_hdtb_id(hdtb_id){
            try{
                this.loading(true)
                let response = await API.sp_loai_tbi_giaophieu_tbi_theo_hdtb_id(this.axios, hdtb_id)
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
        async lay_huongkn_moi_cu(hdtb_id){
            try{
                this.loading(true)
                let response = await API.lay_huongkn_moi_cu(this.axios, hdtb_id)
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
        async sp_ds_loai_tbi_theo_thuebao_id(thuebao_id){
            try{
                this.loading(true)
                let response = await API.sp_ds_loai_tbi_theo_thuebao_id(this.axios, thuebao_id)
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
        async fn_lay_text_huongketnoi_v2(huong_kn){
            try{
                this.loading(true)
                let response = await API.fn_lay_text_huongketnoi_v2(this.axios, huong_kn)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        
        async clearDuLieu(){
            this.tocdo_cir_selected=null
            this.tocdo_pir_selected=null
            this.input_site_id=''
            this.input_router=''
            this.input_route=''
            this.input_slot=''
            this.input_port_pe=''
            this.input_tram_tbi=''
            this.input_huongketnoi=''
            this.input_dslam=''
            this.input_adsl_port=''
            this.input_port_mdf=''
            this.input_nasport_id=''
            this.input_ip_dslam=''
            this.input_svlan=''
            this.input_wan_ip=''
            this.input_cvlan=''
            this.input_lan_ip=''
            this.input_vrf_vfi=''
            this.input_link=''
            this.input_thietbi_dc=''
            this.input_serial=''
            this.input_loaicap=''
            this.thietbi_selected=null
            this.input_lientu=''
            this.input_tucapgoc=''
            this.input_tucapngon=''
            this.input_hopcapngon=''
            this.input_doicapgoc=''
            this.input_doicapngon=''
            this.input_tuyencap=''
            this.input_capgoc=''
            this.input_capngon=''
            this.input_culy='0'
            this.input_slot_olt=''
            this.input_port_olt=''
            this.checkTocDoPir=false
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