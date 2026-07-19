<template>
    <div class="row">
        <div class="box-form">
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Account</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" v-model= "itemdata.ma_tb"  ref= "Account" v-on:keyup.enter="onAccountEnter">
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
                        <div class="key">Mã TN</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.matb_tn" ref= "matb_tn" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Loại node</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.loainode" ref= "loainode" readonly> 
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Site ID</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.site_id" ref= "site_id" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Mã HĐ - TTKD</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ma_pl" ref= "ma_pl" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Hướng KN</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.huong_kn" ref= "huong_kn" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tỉnh KN</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tentinh" ref= "tentinh" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Modem</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ten_tbi" ref= "ten_tbi" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Địa chỉ KN</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.diachi_kn" ref= "diachi_kn" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Loại Thiết bị</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.loai_md" ref= "loai_md" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Loại kênh</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.loai_kenh" ref= "loai_kenh" readonly>
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
                        <div class="key">Tốc độ CIR</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tocdoc_cir" ref= "tocdoc_cir" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Luồng giữ</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.giukenh" ref= "giukenh" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Tốc độ PIR</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tocdo_pir" ref= "tocdo_pir" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Thời hạn</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tenthoihan" ref= "Password" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Số lượng IP</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.sl_ip" ref= "sl_ip" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tốc độ NIX</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tocdo_nix" ref= "tocdo_nix" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cước NIX</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_nix" ref= "cuoc_nix" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cước IP</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_ip" ref= "cuoc_ip" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Tốc độ ISP</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tocdo_isp" ref= "tocdo_isp" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cước ISP</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_isp" ref= "cuoc_isp" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cước thuê cổng (no VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_tc" ref= "cuoc_tc" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cước thuê kênh (no VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_tk" ref= "cuoc_tk" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cước thiết bị (no VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_tbi" ref= "cuoc_tbi" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cước HT(no VAT)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_ht" ref= "cuoc_ht" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Biến động gói đa DV</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.biendong_dadv" ref= "biendong_dadv" readonly>
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
    name:'Megawan',
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