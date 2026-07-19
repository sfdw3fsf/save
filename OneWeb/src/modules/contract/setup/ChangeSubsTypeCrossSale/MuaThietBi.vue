<template>
    <div class="col-sm-6 col-12">
        <div class="box-form">
            <div class="legend-title">Mua thiết bị</div>
            <div class="info-row">
                <div class="value padl0">
                    <div class="input-icon-right">
                        <div class="input-more-button">
                            <button class="btn" @click="click" :disabled="!enabledMuaTbi" :class="{disabledButton: !enabledMuaTbi}">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <div class="form-control text pad10"  style="height: 105px; overflow-y: scroll;">
                                <div style="width:100%;" v-for="(item, index) in dsTenTB" :key="index">
                                    <span style="display: block;font-size: 15px;" class="name">{{item}}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <ChiTietMuaThietBiModal ref="chiTietMuaThietBiModal"
            :dulieu="dulieu"
            @form-close="formClose"
        />
    </div>
</template>
<script>
import ChiTietMuaThietBiModal from '../ChangeSubsType/popups/ChiTietMuaThietBiModal.vue'
export default {
    name:'MuaThietBi',
    components:{
        ChiTietMuaThietBiModal 
    },
    props:{
        dsCTM_TBI:{
            type:Array,
            default:()=>[]
        },
        dsTenTB:{
            type:Array,
            default:()=>[]
        },
        loaitb_id:{
            type:[Number, String],
            default:0
        }
    },
    data(){
        return {
            dulieu:{
                _loaitb_id:this.loaitb_id,
                ds:[]
            },
            enabledMuaTbi:true
        }
    },
    methods:{
        click(){
            this.$refs.chiTietMuaThietBiModal.showModal()
        },
        formClose(ds){
            this.$emit('acceptPopupCTMuaThietBi', ds)
        },
        async initDuLieu(){

        },
        async clearDulieu(){
           
        }
    },
    watch:{
        dsCTM_TBI(val){
            this.dulieu.ds=[]
            for(let i=0;i<val.length;i++){
                let item=val[i]
                this.dulieu.ds.push({
                    block_dau: item.BLOCK_DAU,
                    block_tiep: item.BLOCK_TIEP,
                    gia_block_tiep: item.GIA_BLOCK_TIEP,
                    loai_tbi: item.LOAI_TBI,
                    loaitbi_id: item.LOAITBI_ID,
                    ma_tbi: item.MA_TBI,
                    serial: item.SERIAL,
                    sl_cha: item.SL_CHA,
                    soluong: item.SOLUONG,
                    tien: item.TIEN,
                    tien_km: item.TIEN_KM,
                    tien_thue: item.TIEN_THUE,
                    tien_tragop: item.TIEN_TRAGOP,
                    tien_tratruoc: item.TIEN_TRATRUOC,
                    tong_thue: item.TONG_THUE,
                    tong_tien: item.TONG_TIEN,
                    tyle_vat: item.TYLE_VAT,
                    tyle_vat_id: item.TYLE_VAT_ID,
                    vat: item.VAT,
                    vat_block_tiep: item.VAT_BLOCK_TIEP,
                    vat_km: item.VAT_KM,
                    vat_tragop: item.VAT_TRAGOP,
                    vat_tratruoc: item.VAT_TRATRUOC
                })
            }
        },
        loaitb_id(val){
            this.dulieu._loaitb_id=val
        }
    }
}
</script>
<style>
.disabledButton{
  border-color: gray !important;
  color: gray !important;
}
</style>