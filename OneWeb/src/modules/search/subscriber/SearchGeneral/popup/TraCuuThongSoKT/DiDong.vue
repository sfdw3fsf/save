<template>
    <div class="row">
        <div class="box-form">
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Số máy</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" v-model= "itemdata.ma_tb"  ref= "Account" v-on:keyup.enter="onAccountEnter">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Ghi chú</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" v-model= "itemdata.ghichu"  ref= "Account" v-on:keyup.enter="onAccountEnter">
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
    name:'DiDong',
    components:{
        KRequiredMarker
    },
    data(){
        return {
            itemdata:{},
            LichSuThayDoi:''

        }
    },
    methods:{
        async initDuLieu(){

        },
        
        async hienthi_dulieu_thuebao(ma_tb){
            this.itemdata.ma_tb = ma_tb
        },        
        async clearDuLieu(){            
        },
        async onAccountEnter(){            
        },
    }
}
</script>