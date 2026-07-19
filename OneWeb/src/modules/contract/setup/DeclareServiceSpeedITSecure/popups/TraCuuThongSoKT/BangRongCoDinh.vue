<template>
    <div class="row">
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w80">Bras</div>
                <div class="value">
                    <input type="text" :value="input_bras" @change="e=>input_bras=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Rack</div>
                <div class="value">
                    <input type="text" :value="input_rack" @change="e=>input_rack=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Slot</div>
                <div class="value">
                    <input type="text" :value="input_slot" @change="e=>input_slot=e.target.value" class="form-control">
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w80">Vpi/Svlan</div>
                        <div class="value">
                            <input type="text" :value="input_vpi_svlan" @change="e=>input_vpi_svlan=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w60">Vci/Cvlan</div>
                        <div class="value">
                            <input type="text" :value="input_vci_cvlan" @change="e=>input_vci_cvlan=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w80">Self</div>
                        <div class="value">
                            <input type="text" :value="input_self" @change="e=>input_self=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w60">QLT Slot</div>
                        <div class="value">
                            <input type="text" :value="input_olt_slot" @change="e=>input_olt_slot=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Loại cáp</div>
                <div class="value">
                    <input type="text" :value="input_loaicap" @change="e=>input_loaicap=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Cáp gốc</div>
                <div class="value">
                    <input type="text" :value="input_capgoc" @change="e=>input_capgoc=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Tủ cáp gốc</div>
                <div class="value">
                    <input type="text" :value="input_tucapgoc" @change="e=>input_tucapgoc=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Đôi cáp gốc</div>
                <div class="value">
                    <input type="text" :value="input_doicapgoc" @change="e=>input_doicapgoc=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w80">Slot QLT</div>
                <div class="value">
                    <input type="text" :value="input_slot_olt" @change="e=>input_slot_olt=e.target.value" class="form-control">
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w90">Dslam</div>
                <div class="value">
                    <input type="text" :value="input_dslam" @change="e=>input_dslam=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Shelf</div>
                <div class="value">
                    <input type="text" :value="input_shelf" @change="e=>input_shelf=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Port</div>
                <div class="value">
                    <input type="text" :value="input_port" @change="e=>input_port=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Adsl Port</div>
                <div class="value">
                    <input type="text" :value="input_adsl_port" @change="e=>input_adsl_port=e.target.value" class="form-control">
                </div>
            </div>
            <div class="row">
                <div class="col-sm-7 col-12">
                    <div class="info-row">
                        <div class="key w90">OLT Port</div>
                        <div class="value">
                            <input type="text" :value="input_olt_port" @change="e=>input_olt_port=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-5 col-12">
                    <div class="info-row">
                        <div class="key w40">ONUID</div>
                        <div class="value">
                            <input type="text" :value="input_onuid" @change="e=>input_onuid=e.target.value" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Hộp cáp ngọn</div>
                <div class="value">
                    <input type="text" :value="input_hopcapngon" @change="e=>input_hopcapngon=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Cáp ngọn</div>
                <div class="value">
                    <input type="text" :value="input_capngon" @change="e=>input_capngon=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Tủ cáp ngọn</div>
                <div class="value">
                    <input type="text"  :value="input_tucapngon" @change="e=>input_tucapngon=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Đôi cáp ngọn</div>
                <div class="value">
                    <input type="text" :value="input_doicapngon" @change="e=>input_doicapngon=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w90">Port OLT</div>
                <div class="value">
                    <input type="text" :value="input_port_olt" @change="e=>input_port_olt=e.target.value" class="form-control">
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w70">IP Dslam</div>
                <div class="value">
                    <input type="text" :value="input_ip_dslam" @change="e=>input_ip_dslam=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">Trạm T.Bị</div>
                <div class="value">
                    <input type="text" :value="input_tram_tbi" @change="e=>input_tram_tbi=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">System</div>
                <div class="value">
                    <input type="text" :value="input_system" @change="e=>input_system=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">Port MDF</div>
                <div class="value">
                    <input type="text" :value="input_port_mdf" @change="e=>input_port_mdf=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">NasportID</div>
                <div class="value">
                    <input type="text" :value="input_nasport_id" @change="e=>input_nasport_id=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">Số ảo</div>
                <div class="value">
                    <input type="text" :value="input_soao" @change="e=>input_soao=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">Tuyến cáp</div>
                <div class="value">
                    <input type="text" :value="input_tuyencap" @change="e=>input_tuyencap=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">Liên tủ</div>
                <div class="value">
                    <input type="text" :value="input_lientu" @change="e=>input_lientu=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">Cự ly</div>
                <div class="value">
                    <input type="text" :value="input_culy" @change="e=>input_culy=e.target.value" class="form-control">
                </div>
            </div>
            
        </div>
    </div>
</template>
<script>
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import API from './API'
export default {
    name:'BangRongCoDinh',
    components:{
        KRequiredMarker
    },
    data(){
        return {
            input_bras:'',
            input_rack:'',
            input_slot:'',
            input_vpi_svlan:'',
            input_vci_cvlan:'',
            input_self:'',
            input_olt_slot:'',
            input_loaicap:'',
            input_capgoc:'',
            input_tucapgoc:'',
            input_doicapgoc:'',
            input_slot_olt:'',
            input_dslam:'',
            input_shelf:'',
            input_port:'',
            input_adsl_port:'',
            input_olt_port:'',
            input_onuid:'',
            input_hopcapngon:'',
            input_capngon:'',
            input_tucapngon:'',
            input_doicapngon:'',
            input_port_olt:'',
            input_ip_dslam:'',
            input_tram_tbi:'',
            input_system:'',
            input_port_mdf:'',
            input_nasport_id:'',
            input_soao:'',
            input_tuyencap:'',
            input_lientu:'',
            input_culy:''

        }
    },
    methods:{
        async initDuLieu(){

        },
        async hienthi_dulieu_hdtb(item, hdtb_id){
            this.input_soao=item.ma_lt!=null?item.ma_lt.toString():''
            this.input_loaicap=item.loaicap!=null?item.loaicap.toString():''
            this.input_vpi_svlan=item.vpi!=null?item.vpi.toString():''
            this.input_vci_cvlan=item.vci!=null?item.vci.toString():''

            this.input_self=item.selfid01!=null?item.selfid01.toString():''
            this.input_olt_slot=item.selfid02!=null?item.selfid02.toString():''
            this.input_olt_port=item.selfid03!=null?item.selfid03.toString():''
            this.input_onuid=item.selfid04!=null?item.selfid04.toString():''
            this.input_port_mdf=item.port_mdf!=null?item.port_mdf.toString():''
            this.input_rack=item.rack!=null?item.rack.toString():''
            this.input_shelf=item.shelf!=null?item.shelf.toString():''
            this.input_slot=item.slot!=null?item.slot.toString():''
            this.input_port=item.port!=null?item.port.toString():''

            if(item.port_id!=null&&item.port_id.toString()!=''){
                let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                if(vl.length>0){
                    this.input_slot_olt=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                    this.input_port_olt=vl[0].vitri!=null?vl[0].vitri.toString():''
                }
            }

            this.input_system=item.system!=null?item.system.toString():''
            this.input_dslam=item.tendslam!=null?item.tendslam.toString():''
            this.input_hopcapngon=item.hopcap_p!=null?item.hopcap_p.toString():''
            this.input_capgoc=item.cap_goc!=null?item.cap_goc.toString():''
            this.input_capngon=item.cap_p!=null?item.cap_p.toString():''
            this.input_tuyencap=item.tuyencap!=null?item.tuyencap.toString():''
            this.input_tucapgoc=item.tucap_goc!=null?item.tucap_goc.toString():''
            this.input_tucapngon=item.tucap_p!=null?item.tucap_p.toString():''
            this.input_lientu=item.lientu!=null?item.lientu.toString():''
            this.input_culy=item.culy!=null?item.culy.toString():''
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
            this.input_tram_tbi=item.ten_dv!=null?item.ten_dv.toString():''
            this.input_bras=item.tenbras!=null?item.tenbras.toString():''
            if(this.input_self!=''){
                this.input_nasport_id=this.input_system+'/'+this.input_onuid+'/'+ this.input_olt_port+'/'+this.input_olt_slot+'/'+this.input_self

            }else{
                this.input_nasport_id=this.input_system
            }
            this.input_adsl_port=item.adsl_port!=null?item.adsl_port.toString():''
            this.input_ip_dslam=item.ip_dslam!=null?item.ip_dslam.toString():''
        },
        async hienthi_dulieu_thuebao(item, thuebao_id){
            this.input_soao=item.ma_lt!=null?item.ma_lt.toString():''
            this.input_loaicap=item.loaicap!=null?item.loaicap.toString():''
            this.input_vpi_svlan=item.vpi!=null?item.vpi.toString():''
            this.input_vci_cvlan=item.vci!=null?item.vci.toString():''

            this.input_self=item.selfid01!=null?item.selfid01.toString():''
            this.input_olt_slot=item.selfid02!=null?item.selfid02.toString():''
            this.input_olt_port=item.selfid03!=null?item.selfid03.toString():''
            this.input_onuid=item.selfid04!=null?item.selfid04.toString():''
            this.input_port_mdf=item.port_mdf!=null?item.port_mdf.toString():''
            this.input_rack=item.rack!=null?item.rack.toString():''
            this.input_shelf=item.shelf!=null?item.shelf.toString():''
            this.input_slot=item.slot!=null?item.slot.toString():''
            this.input_port=item.port!=null?item.port.toString():''

            if(item.port_id!=null&&item.port_id.toString()!=''){
                let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                if(vl.length>0){
                    this.input_slot_olt=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                    this.input_port_olt=vl[0].vitri!=null?vl[0].vitri.toString():''
                }
            }

            this.input_system=item.system!=null?item.system.toString():''
            this.input_dslam=item.tendslam!=null?item.tendslam.toString():''
            this.input_hopcapngon=item.hopcap_p!=null?item.hopcap_p.toString():''
            this.input_capgoc=item.cap_goc!=null?item.cap_goc.toString():''
            this.input_capngon=item.cap_p!=null?item.cap_p.toString():''
            this.input_tuyencap=item.tuyencap!=null?item.tuyencap.toString():''
            this.input_tucapgoc=item.tucap_goc!=null?item.tucap_goc.toString():''
            this.input_tucapngon=item.tucap_p!=null?item.tucap_p.toString():''
            this.input_lientu=item.lientu!=null?item.lientu.toString():''
            this.input_culy=item.culy!=null?item.culy.toString():''
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
            this.input_tram_tbi=item.ten_dv!=null?item.ten_dv.toString():''
            this.input_bras=item.tenbras!=null?item.tenbras.toString():''
            if(this.input_self!=''){
                this.input_nasport_id=this.input_system+'/'+this.input_onuid+'/'+ this.input_olt_port+'/'+this.input_olt_slot+'/'+this.input_self

            }else{
                this.input_nasport_id=this.input_system
            }
            this.input_adsl_port=item.adsl_port!=null?item.adsl_port.toString():''
            this.input_ip_dslam=item.ip_dslam!=null?item.ip_dslam.toString():''
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
        async clearDuLieu(){
            this.input_bras=''
            this.input_rack=''
            this.input_slot=''
            this.input_vpi_svlan=''
            this.input_vci_cvlan=''
            this.input_self=''
            this.input_olt_slot=''
            this.input_loaicap=''
            this.input_capgoc=''
            this.input_tucapgoc=''
            this.input_doicapgoc=''
            this.input_slot_olt=''
            this.input_dslam=''
            this.input_shelf=''
            this.input_port=''
            this.input_adsl_port=''
            this.input_olt_port=''
            this.input_onuid=''
            this.input_hopcapngon=''
            this.input_capngon=''
            this.input_tucapngon=''
            this.input_doicapngon=''
            this.input_port_olt=''
            this.input_ip_dslam=''
            this.input_tram_tbi=''
            this.input_system=''
            this.input_port_mdf=''
            this.input_nasport_id=''
            this.input_soao=''
            this.input_tuyencap=''
            this.input_lientu=''
            this.input_culy=''
        }
    }
}
</script>