<template>
    <div class="row">
        <div class="row">
            <div class="col-sm-8 col-12">
                <div class="info-row">
                    <div class="key w100">Bras</div>
                    <div class="value">
                        <input type="text" :value="input_bras" @change="e=>input_bras=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Thiết bị</div>
                    <div class="value">
                        <input type="text" :value="input_thietbi" @change="e=>input_thietbi=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Dslam</div>
                    <div class="value">
                        <input type="text" :value="input_dslam" @change="e=>input_dslam=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Slot</div>
                    <div class="value">
                        <input type="text" :value="input_slot" @change="e=>input_slot=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Port</div>
                    <div class="value">
                        <input type="text" :value="input_port" @change="e=>input_port=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Username</div>
                    <div class="value">
                        <input type="text" :value="input_username" @change="e=>input_username=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Password</div>
                    <div class="value">
                        <input type="text" :value="input_password" @change="e=>input_password=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-2 col-12">
                <div class="info-row">
                    <div class="key w100">Vpi</div>
                    <div class="value">
                        <input type="text" :value="input_vpi" @change="e=>input_vpi=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-2 col-12">
                <div class="info-row">
                    <div class="key w100">Vci</div>
                    <div class="value">
                        <input type="text" :value="input_vci" @change="e=>input_vci=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">System</div>
                    <div class="value">
                        <input type="text" :value="input_system" @change="e=>input_system=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Rack</div>
                    <div class="value">
                        <input type="text" :value="input_rack" @change="e=>input_rack=e.target.value"  class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-2 col-12">
                <div class="info-row">
                    <div class="key w100">Shelf</div>
                    <div class="value">
                        <input type="text" :value="input_shelf" @change="e=>input_shelf=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-2 col-12">
                <div class="info-row">
                    <div class="key w100">Port Mdf</div>
                    <div class="value">
                        <input type="text" :value="input_port_mdf" @change="e=>input_port_mdf=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Adsl port</div>
                    <div class="value">
                        <input type="text" :value="input_adsl_port" @change="e=>input_adsl_port=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">SvLan</div>
                    <div class="value">
                        <input type="text" :value="input_svlan" @change="e=>input_svlan=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">CvLan</div>
                    <div class="value">
                        <input type="text" :value="input_cvlan" @change="e=>input_cvlan=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Mã truy nhập</div>
                    <div class="value">
                        <input type="text" :value="input_matruynhap" @change="e=>input_matruynhap=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Loại cáp</div>
                    <div class="value">
                        <input type="text" :value="input_loaicap" @change="e=>input_loaicap=e.target.value" class="form-control red bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Hộp cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_hopcapngon" @change="e=>input_hopcapngon=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Tủ cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_tucapgoc" @change="e=>input_tucapgoc=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Tủ cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_tucapngon" @change="e=>input_tucapngon=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Tuyến cáp</div>
                    <div class="value">
                        <input type="text" :value="input_tuyencap" @change="e=>input_tuyencap=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Đôi cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_doicapgoc" @change="e=>input_doicapgoc=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Đôi cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_doicapngon" @change="e=>input_doicapngon=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Liên tủ</div>
                    <div class="value">
                        <input type="text" :value="input_lientu" @change="e=>input_lientu=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Cáp gốc</div>
                    <div class="value">
                        <input type="text" :value="input_capgoc" @change="e=>input_capgoc=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Cáp ngọn</div>
                    <div class="value">
                        <input type="text" :value="input_capngon" @change="e=>input_capngon=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Cự ly</div>
                    <div class="value">
                        <input type="text" :value="input_culy" @change="e=>input_culy=e.target.value" class="form-control bold">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Slot OLT</div>
                    <div class="value">
                        <input type="text" :value="input_slot_olt" @change="e=>input_slot_olt=e.target.value" class="form-control bold red">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">
                <div class="info-row">
                    <div class="key w100">Port OLT</div>
                    <div class="value">
                        <input type="text" :value="input_port_olt" @change="e=>input_port_olt=e.target.value" class="form-control bold red">
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-12">

            </div>
        </div>
        
    </div>
</template>
<script>
import API from './API'
export default {
    name:'IMS',
    data(){
        return {
            input_bras:'',
            input_thietbi:'',
            input_dslam:'',
            input_slot:'',
            input_port:'',
            input_username:'',
            input_password:'',
            input_vpi:'',
            input_vci:'',
            input_system:'',
            input_rack:'',
            input_shelf:'',
            input_port_mdf:'',
            input_adsl_port:'',
            input_svlan:'',
            input_cvlan:'',
            input_matruynhap:'',
            input_loaicap:'',
            input_hopcapngon:'',
            input_tucapgoc:'',
            input_tucapngon:'',
            input_tuyencap:'',
            input_doicapgoc:'',
            input_doicapngon:'',
            input_lientu:'',
            input_capgoc:'',
            input_capngon:'',
            input_culy:'',
            input_port_olt:'',
            input_slot_olt:''
        }
    },
    methods:{
        async initDuLieu(){

        },
        async clearDuLieu(){
            this.input_bras=''
            this.input_thietbi=''
            this.input_dslam=''
            this.input_slot=''
            this.input_port=''
            this.input_username=''
            this.input_password=''
            this.input_vpi=''
            this.input_vci=''
            this.input_system=''
            this.input_rack=''
            this.input_shelf=''
            this.input_port_mdf=''
            this.input_adsl_port=''
            this.input_svlan=''
            this.input_cvlan=''
            this.input_matruynhap=''
            this.input_loaicap=''
            this.input_hopcapngon=''
            this.input_tucapgoc=''
            this.input_tucapngon=''
            this.input_tuyencap=''
            this.input_doicapgoc=''
            this.input_doicapngon=''
            this.input_lientu=''
            this.input_capgoc=''
            this.input_capngon=''
            this.input_culy=''
            this.input_slot_olt=''
            this.input_port_olt=''
        },
        async hienthi_dulieu_hdtb(item, hdtb_id){
            console.log('hienthi_dulieu_hdtb ims', item)
            this.input_bras=item.tenbras?item.tenbras.toString():''
            this.input_thietbi=item.ten_tbi?item.ten_tbi.toString():''
            this.input_dslam=item.tendslam?item.tendslam.toString():''
            this.input_port=item.port?item.port.toString():''
            this.input_username=item.username?item.username.toString():''
            this.input_password=item.password?item.password.toString():''
            this.input_system=item.system?item.system.toString():''
            this.input_vpi=item.vpi?item.vpi.toString():''
            this.input_vci=item.vci?item.vci.toString():''
            this.input_rack=item.rack?item.rack.toString():''
            this.input_shelf=item.shelf?item.shelf.toString():''
            this.input_port_mdf=item.port_mdf?item.port_mdf.toString():''
            // txtSoAo.Text = daset.Tables[0].Rows[0]["ma_lt"].ToString();
            this.input_matruynhap=item.matb_tn?item.matb_tn.toString():''
            this.input_loaicap=item.loaicap?item.loaicap.toString():''
            this.input_hopcapngon=item.hopcap_p?item.hopcap_p.toString():''
            this.input_tucapgoc=item.tucap_goc?item.tucap_goc.toString():''
            this.input_tucapngon=item.tucap_p?item.tucap_p.toString():''
            this.input_tuyencap=item.tuyencap?item.tuyencap.toString():''
            this.input_lientu=item.lientu?item.lientu.toString():''
            this.input_capgoc=item.cap_goc?item.cap_goc.toString():''
            this.input_capngon=item.cap_p?item.cap_p.toString():''
            this.input_culy=item.culy?item.culy.toString():''
            //
            if(item.port_id!=null&&item.port_id.toString()!=''){
                let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                if(vl.length>0){
                    this.input_slot_olt=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                    this.input_port_olt=vl[0].vitri!=null?vl[0].vitri.toString():''
                }
            }
            if(item.doicap_p!=null&&item.doicap_p!=-1){
                this.input_doicapgoc=item.doicap_p.toString()
            }else{
                this.input_doicapgoc=''
            }
            if(item.doicap!=null&&item.doicap!=-1){
                this.input_doicapngon=item.doicap.toString()
            }else{
                this.input_doicapngon=''
            }
            this.input_adsl_port=item.adsl_port?item.adsl_port.toString():''
            this.input_svlan=item.svlan?item.svlan.toString():''
            this.input_cvlan=item.cvlan?item.cvlan.toString():''
        },
        async hienthi_dulieu_thuebao(item, thuebao_id){
            console.log('hienthi_dulieu_thuebao ims', item)
            this.input_bras=item.tenbras?item.tenbras.toString():''
            this.input_thietbi=item.ten_tbi?item.ten_tbi.toString():''
            this.input_dslam=item.tendslam?item.tendslam.toString():''
            this.input_port=item.port?item.port.toString():''
            this.input_username=item.username?item.username.toString():''
            this.input_password=item.password?item.password.toString():''
            this.input_system=item.system?item.system.toString():''
            this.input_vpi=item.vpi?item.vpi.toString():''
            this.input_vci=item.vci?item.vci.toString():''
            this.input_rack=item.rack?item.rack.toString():''
            this.input_shelf=item.shelf?item.shelf.toString():''
            this.input_port_mdf=item.port_mdf?item.port_mdf.toString():''
            // txtSoAo.Text = daset.Tables[0].Rows[0]["ma_lt"].ToString();
            this.input_matruynhap=item.matb_tn?item.matb_tn.toString():''
            this.input_loaicap=item.loaicap?item.loaicap.toString():''
            this.input_hopcapngon=item.hopcap_p?item.hopcap_p.toString():''
            this.input_tucapgoc=item.tucap_goc?item.tucap_goc.toString():''
            this.input_tucapngon=item.tucap_p?item.tucap_p.toString():''
            this.input_tuyencap=item.tuyencap?item.tuyencap.toString():''
            this.input_lientu=item.lientu?item.lientu.toString():''
            this.input_capgoc=item.cap_goc?item.cap_goc.toString():''
            this.input_capngon=item.cap_p?item.cap_p.toString():''
            this.input_culy=item.culy?item.culy.toString():''
            //
            if(item.port_id!=null&&item.port_id.toString()!=''){
                let vl=await this.lay_tt_vatly_theo_port_logic(item.port_id)
                if(vl.length>0){
                    this.input_slot_olt=vl[0].ten_card!=null?vl[0].ten_card.toString():''
                    this.input_port_olt=vl[0].vitri!=null?vl[0].vitri.toString():''
                }
            }
            if(item.doicap_p!=null&&item.doicap_p!=-1){
                this.input_doicapgoc=item.doicap_p.toString()
            }else{
                this.input_doicapgoc=''
            }
            if(item.doicap!=null&&item.doicap!=-1){
                this.input_doicapngon=item.doicap.toString()
            }else{
                this.input_doicapngon=''
            }
            this.input_adsl_port=item.adsl_port?item.adsl_port.toString():''
            this.input_svlan=item.svlan?item.svlan.toString():''
            this.input_cvlan=item.cvlan?item.cvlan.toString():''
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
    }
}
</script>