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
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Mã TN</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.matb_tn" ref= "matb_tn" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Mã đôi cáp</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.madoicap" ref= "madoicap" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Số ảo</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ma_lt" ref= "SoAo" readonly>
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
                        <div class="key">Thời hạn</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tenthoihan" ref= "tenthoihan" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Slot</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.slot" ref= "slot" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Port</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.port" ref= "port" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">System</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.system" ref= "system" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Rack</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.rack" ref= "rack" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Shelf</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.shelf" ref= "shelf" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Tham số</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.thamso" ref= "thamso" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">VCI/Cvlan</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.vci" ref= "vci" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">VPI/Svlan</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.vpi" ref= "vpi" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Mã thiết bị</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ma_tbi" ref= "ma_tbi" readonly> 
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Port MDF</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.port_mdf" ref= "port_mdf" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Port ADSL</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.adsl_port" ref= "adsl_port" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Tên DSLAM</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tendslam" ref= "tendslam" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tên đối tác</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ten_doitac" ref= "ten_doitac" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cước đối tác</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_doitac" ref= "cuoc_doitac" readonly>
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
    name:'IMS',
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
            }
        },        
        async clearDuLieu(){            
        },
        async onAccountEnter(){            
        },
    }
}
</script>