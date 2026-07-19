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
                        <div class="key" style="width: 220px;">Tiền cam kết</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tien_ck" ref= "tien_ck" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Sim card</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.simcard" ref= "simcard" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Chủ quản</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tenchuquan" ref= "tenchuquan" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Trang bị</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tentrangbi" ref= "tentrangbi" readonly> 
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cell</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cell1" ref= "cell1" readonly>
                        </div>
                    </div>
                </div>
            </div>   
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tọa độ</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.toado" ref= "toado" readonly>
                        </div>
                    </div>
                </div>
            </div>         
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Danh sách nhân viên khu vực thuê bao</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ds_nhanvien" ref= "ds_nhanvien" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Ghi chú</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ghichu" ref= "ghichu" readonly>
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
    name:'Gphone',
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
        
        async hienthi_dulieu_thuebao(item, ma_tb){
            if (item){
                for (let key in item) {
                this.$set(this.itemdata, key, item[key]);
                }
                this.itemdata.ma_tb = ma_tb
                itemdata.ds_nhanvien = ''
            }
        },        
        async clearDuLieu(){            
        },
        async onAccountEnter(){            
        },
    }
}
</script>