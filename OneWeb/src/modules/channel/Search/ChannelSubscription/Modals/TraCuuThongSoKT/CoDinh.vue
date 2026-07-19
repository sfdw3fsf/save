<template>
    <div class="row">
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w100">Mã truy nhập</div>
                <div class="value">
                    <input type="text" :value="input_matruynhap" @change="e=>input_matruynhap=e.target.value" readonly class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Loại cáp</div>
                <div class="value">
                    <input type="text" :value="input_loaicap" @change="e=>input_loaicap=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Cáp gốc <k-required-marker/></div>
                <div class="value">
                    <input type="text" :value="input_capgoc" @change="e=>input_capgoc=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Tủ cáp gốc <k-required-marker/></div>
                <div class="value">
                    <input type="text" :value="input_tucapgoc" @change="e=>input_tucapgoc=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Đôi cáp gốc <k-required-marker/></div>
                <div class="value">
                    <input type="text" :value="input_doicapgoc" @change="e=>input_doicapgoc=e.target.value" class="form-control">
                </div>
            </div>
            
        </div>
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w100">Lắp kèm</div>
                <div class="value">
                    <input type="text" :value="input_lapkem" @change="e=>input_lapkem=e.target.value" readonly class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Liên tủ</div>
                <div class="value">
                    <input type="text" :value="input_lientu" @change="e=>input_lientu=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Cáp ngọn<k-required-marker/></div>
                <div class="value">
                    <input type="text" :value="input_capngon" @change="e=>input_capngon=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Tủ cáp ngọn</div>
                <div class="value">
                    <input type="text" :value="input_tucapngon" @change="e=>input_tucapngon=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Đôi cáp ngọn<k-required-marker/></div>
                <div class="value">
                    <input type="text" :value="input_doicapngon" @change="e=>input_doicapngon=e.target.value" class="form-control">
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-12">
            <div class="info-row">
                <div class="key w100">Ne</div>
                <div class="value">
                    <input type="text" :value="input_ne" @change="e=>input_ne=e.target.value" readonly class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Hộp cáp ngọn</div>
                <div class="value">
                    <input type="text" :value="input_hopcapngon" @change="e=>input_hopcapngon=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Tuyến cáp</div>
                <div class="value">
                    <input type="text" :value="input_tuyencap" @change="e=>input_tuyencap=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Vệ tinh</div>
                <div class="value">
                    <input type="text" :value="input_vetinh" @change="e=>input_vetinh=e.target.value" class="form-control">
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Cự ly <k-required-marker/></div>
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
    name:'CoDinh',
    components:{
        KRequiredMarker
    },
    data(){
        return{
            input_matruynhap:'',
            input_loaicap:'',
            input_capgoc:'',
            input_tucapgoc:'',
            input_doicapgoc:'',
            input_lapkem:'',
            input_lientu:'',
            input_capngon:'',
            input_tucapngon:'',
            input_doicapngon:'',
            input_ne:'',
            input_hopcapngon:'',
            input_tuyencap:'',
            input_vetinh:'',
            input_culy:''
        }
    },
    methods:{
        async initDuLieu(){

        },
        async hienthi_dulieu_hdtb(item, hdtb_id){
            var vdstb_lapkem=""
            if(item.madoicap!=null&&item.madoicap.toString()!=''){
                vdstb_lapkem=await this.lay_ds_hdtb_con_theo_hdtbid(hdtb_id, item.madoicap)
            }
            this.input_lapkem=vdstb_lapkem
            this.input_matruynhap=item.matb_tn!=null?item.matb_tn.toString():''
            this.input_ne=item.sone!=null?item.sone.toString():''
            this.input_loaicap=item.loaicap!=null?item.loaicap.toString():''
            //txtTramNE.Text = daset.Tables[0].Rows[0]["ten_dv"].ToString();
            this.input_vetinh=item.ten_dv!=null?item.ten_dv.toString():''
            
            this.input_lientu=item.lientu!=null?item.lientu.toString():''
            this.input_hopcapngon=item.hopcap_p!=null?item.hopcap_p.toString():''
            this.input_capgoc=item.cap_goc!=null?item.cap_goc.toString():''
            this.input_capngon=item.cap_p!=null?item.cap_p.toString():''
            this.input_tuyencap=item.tuyencap!=null?item.tuyencap.toString():''
            this.input_tucapgoc=item.tucap_goc!=null?item.tucap_goc.toString():''
            this.input_tucapngon=item.tucap_p!=null?item.tucap_p.toString():''
            if(item.doicap_goc!=null&&item.doicap_goc!=-1){
                this.input_doicapgoc=item.doicap_goc.toString()
            }else{ 
                this.input_doicapgoc=''
            }
            if(item.doicap_p!=null&&item.doicap_p!=-1){
                this.input_doicapngon=item.doicap_p.toString()
            }else{
                this.input_doicapngon=''
            }
            this.input_culy=item.culy!=null?item.culy.toString():''
        },
        async hienthi_dulieu_thuebao(item, thuebao_id){
            var vdstb_lapkem=""
            if(item.madoicap!=null&&item.madoicap.toString()!=''){
                vdstb_lapkem=await this.lay_thuebao_lapkem_db(thuebao_id, item.madoicap)
            }
            this.input_lapkem=vdstb_lapkem
            this.input_matruynhap=item.matb_tn!=null?item.matb_tn.toString():''
            this.input_ne=item.sone!=null?item.sone.toString():''
            this.input_loaicap=item.loaicap!=null?item.loaicap.toString():''
             //txtTramNE.Text = daset.Tables[0].Rows[0]["ten_dv"].ToString();
            this.input_vetinh=item.ten_dv!=null?item.ten_dv.toString():''
            
            this.input_lientu=item.lientu!=null?item.lientu.toString():''
            this.input_hopcapngon=item.hopcap_p!=null?item.hopcap_p.toString():''
            this.input_capgoc=item.cap_goc!=null?item.cap_goc.toString():''
            this.input_capngon=item.cap_p!=null?item.cap_p.toString():''
            this.input_tuyencap=item.tuyencap!=null?item.tuyencap.toString():''
            this.input_tucapgoc=item.tucap_goc!=null?item.tucap_goc.toString():''
            this.input_tucapngon=item.tucap_p!=null?item.tucap_p.toString():''
            this.input_culy=item.culy!=null?item.culy.toString():''
            if(item.doicap_goc!=null&&item.doicap_goc!=-1){
                this.input_doicapgoc=item.doicap_goc.toString()
            }else{ 
                this.input_doicapgoc=''
            }
            if(item.doicap_p!=null&&item.doicap_p!=-1){
                this.input_doicapngon=item.doicap_p.toString()
            }else{
                this.input_doicapngon=''
            }
        },
        async lay_ds_hdtb_con_theo_hdtbid(hdtb_id, dichvuvt_id){
            try{
                this.loading(true)
                let response = await API.lay_ds_hdtb_con_theo_hdtbid(this.axios, hdtb_id, dichvuvt_id)
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
        async lay_thuebao_lapkem_db(thuebao_id, dichvuvt_id){
            try{
                this.loading(true)
                let response = await API.lay_thuebao_lapkem_db(this.axios, thuebao_id, dichvuvt_id)
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
            this.input_matruynhap=''
            this.input_loaicap=''
            this.input_capgoc=''
            this.input_tucapgoc=''
            this.input_doicapgoc=''
            this.input_lapkem=''
            this.input_lientu=''
            this.input_capngon=''
            this.input_tucapngon=''
            this.input_doicapngon=''
            this.input_ne=''
            this.input_hopcapngon=''
            this.input_tuyencap=''
            this.input_vetinh=''
            this.input_culy=''
        }
    }
}
</script>