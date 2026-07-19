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
                        <div class="key">Mã đối cáp</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.madoicap" ref= "MaDoiCap" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tốc độ</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tocdo" ref= "TocDo" readonly> 
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Mức cước</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.muccuoc" ref= "MucCuoc" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Thiết bị</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tbi" ref= "ThietBi" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Mã TN</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.matb_tn" ref= "MaTN" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Trang bị</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.trangbi" ref= "trangBi" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Thông tin TC</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.thongtin_tc" ref= "ThongTinTC" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tên đối tác</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.doitac" ref= "TenDoiTac" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cước đối tác</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_doitac" ref= "CuocDoiTac" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Chủ quản</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tenchuquan" ref= "ChuQuan" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Chủ quản LĐ</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.chuquan_ld" ref= "ChuQuanLD" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Loại TB My TV</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.loaimytv" ref= "LoaiTBMyTV" readonly> 
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Phân loại DV</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.phanloai" ref= "PhanLoaiDV" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">IPv4 WAN</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ipv4_wan" ref= "IPV4WAN" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">IPv6 LAN</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ipv6_lan" ref= "IPV6LAN" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Password</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.password" ref= "Password" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">USERNAME</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.username" ref= "Username" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">MAC_ADDR</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.mac_addr" ref= "MacAdd" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Lịch sử thay đổi thông số kỹ thuật</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "LichSuThayDoi" ref= "LichSuThayDoi" readonly>
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
    name:'BangRongCoDinh',
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