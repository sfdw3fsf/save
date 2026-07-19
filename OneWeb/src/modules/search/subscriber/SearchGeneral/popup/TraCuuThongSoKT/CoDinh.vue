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
                            <input type="text" class="form-control" v-model= "itemdata.matb_tn" ref= "SoAo" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Trang bị</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.trangbi" ref= "MaDoiCap" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Chủ quản</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.chuquan" ref= "TocDo" readonly> 
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Chủ quản LĐ</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.chuquan_ld" ref= "MucCuoc" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Trạm Viễn thông</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tramvt" ref= "ThietBi" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Trạm thiết bị</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tramtbi" ref= "MaTN" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Loại cáp</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.loaicap" ref= "trangBi" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">NE</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ne" ref= "ThongTinTC" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tủ cáp gốc</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tucap_goc" ref= "TenDoiTac" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Đôi cáp gốc</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.doicap_goc" ref= "CuocDoiTac" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cáp gốc</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cap_goc" ref= "ChuQuan" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Hộp cáp ngọn</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.hopcap_p" ref= "ChuQuanLD" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tủ cáp ngọn</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tucap_p" ref= "LoaiTBMyTV" readonly> 
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Đôi cáp ngọn</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.doicap_p" ref= "PhanLoaiDV" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Cáp ngọn</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cap_p" ref= "IPV4WAN" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Tuyến cáp</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tuyencap" ref= "IPV6LAN" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Liên tủ</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.lientu" ref= "Password" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cự ly</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.culy" ref= "Username" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tên đối tác</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ten_doitac" ref= "Password" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key">Cước đối tác</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.cuoc_doitac" ref= "Username" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Tiền cam kết</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.tien_ck" ref= "MacAdd" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Biến động gói đa DV</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.biendong_dadv" ref= "LichSuThayDoi" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="info-row">
                        <div class="key" style="width: 220px;">Ghi chú</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model= "itemdata.ghichu" ref= "LichSuThayDoi" readonly>
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
    name:'CoDinh',
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
        
        async hienthi_dulieu_thuebao(item, ma_tb,ghichu){
            if (item){
                for (let key in item) {
                this.$set(this.itemdata, key, item[key]);
                }
                this.itemdata.ma_tb = ma_tb
                this.itemdata.ghichu = ghichu
            }
        },        
        async clearDuLieu(){            
        },
        async onAccountEnter(){            
        },
    }
}
</script>