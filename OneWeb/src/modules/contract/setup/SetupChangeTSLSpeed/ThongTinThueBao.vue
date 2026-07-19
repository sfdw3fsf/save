<template>
    <div class="box-col box-form">
        <div class="legend-title">Thông tin thuê bao</div>
        <div class="info-row">
            <div class="key">Số máy/Acc</div>
            <div class="value">
                <div class="input-more-button">
                    <button class="btn" @click="bntLayMaKh_Click">
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" class="form-control bold" v-model="model.v3_so_may_acc" id="v3_so_may_acc" @keydown="txtMaTB_KeyPress">
                    <!-- <ejs-textbox ></ejs-textbox> -->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-7 col-12">
                <div class="info-row">
                    <div class="key">Tốc độ mới</div>
                    <div class="value">
                        <div class="select-custom">
                            <ejs-dropdownlist id="v3_toc_do_moi"
                                locale="vi-VN"
                                v-model="model.v3_toc_do_moi"
                                :dataSource="v3_LstTocDo"
                                :allowFiltering="true" :filtering="onFilterV3TocDo"
                                :fields="{ value: 'ID', text: 'NAME' }"
                            />
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-5 col-12">
                <div class="info-row">
                    <div class="key">Tốc độ cũ</div>
                    <div class="value">
                        <div class="select-custom">
                            <ejs-dropdownlist id="v3_toc_do_cu"
                                locale="vi-VN" :enabled="false"
                                v-model="model.v3_toc_do_cu"
                                :dataSource="v3_LstTocDo"
                                :allowFiltering="true" :filtering="onFilterV3TocDo"
                                :fields="{ value: 'ID', text: 'NAME' }"
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-7 col-12">
                <div class="info-row">
                    <div class="key">Tên thuê bao</div>
                    <div class="value">
                        <input type="text" class="form-control bold" v-model="model.v3_ten_thue_bao" id="v3_ten_thue_bao" :disabled="true">
                    </div>
                </div>
            </div>
            <div class="col-sm-5 col-12">
                <div class="info-row">
                    <div class="key">Mã ERP</div>
                    <div class="value">
                        <input type="text" class="form-control" v-model="model.v3_ma_erp" id="v3_ma_erp">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-7 col-12">
                <div class="info-row">
                    <div class="key">Địa chỉ thuê bao</div>
                    <div class="value">
                        <input type="text" class="form-control bold" v-model="model.v3_dia_chi_thue_bao" id="v3_dia_chi_thue_bao" :disabled="true">
                    </div>
                </div>
            </div>
            <div class="col-sm-5 col-12">
                <div class="info-row">
                    <div class="key">Mã BSS</div>
                    <div class="value">
                        <input type="text" class="form-control" v-model="model.v3_ma_bss" id="v3_ma_bss">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-7 col-12">
                <div class="info-row">
                    <div class="key">Địa chỉ đầu</div>
                    <div class="value">
                        <input type="text" class="form-control bold" v-model="model.v3_dia_chi_dau" id="v3_dia_chi_dau" :disabled="true">
                    </div>
                </div>
            </div>
            <div class="col-sm-5 col-12">
                <div class="info-row">
                    <div class="key">Số ảo đầu</div>
                    <div class="value">
                        <input type="text" class="form-control italic" v-model="model.v3_so_ao_dau" id="v3_so_ao_dau" :disabled="true">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-7 col-12">
                <div class="info-row">
                    <div class="key">Địa chỉ cuối</div>
                    <div class="value">
                        <input type="text" class="form-control bold" v-model="model.v3_dia_chi_cuoi" id="v3_dia_chi_cuoi" :disabled="true">
                    </div>
                </div>
            </div>
            <div class="col-sm-5 col-12">
                <div class="info-row">
                    <div class="key">Số ảo cuối</div>
                    <div class="value">
                        <input type="text" class="form-control italic" v-model="model.v3_so_ao_cuoi" id="v3_so_ao_cuoi" :disabled="true">
                    </div>
                </div>
            </div>
        </div>
        <div class="info-row">
            <div class="key">
                <div class="check-action">
                    <input type="checkbox" class="check" id="v3_chb_khuyen_mai" @change="chkKhuyenMai_CheckedChanged">
                    <span class="name">K.Mại</span>
                </div>
            </div>
            <div class="value">
                <div class="input-more-button">
                    <button class="btn" id="v3_btn_khuyen_mai" @click="ShowPopupChonKhuyenMai" :disabled="!v3_enable_khuyen_mai">
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                    <ejs-dropdownlist id="v3_khuyen_mai"
                        locale="vi-VN" :enabled="v3_enable_khuyen_mai"
                        v-model="model.v3_khuyen_mai" @change="cboKhuyenMai_SelectionChangeCommitted($event)"
                        :dataSource="v3_LstKhuyenMai"
                        :allowFiltering="true" :filtering="onFilterV3KhuyenMai"
                        :fields="{ value: 'khuyenmai_id', text: 'ten_km' }"
                    />
                </div>
            </div>
            <div class="value w30 nowrap">
                <button class="btn btn-second" id="v3_btn_chi_tiet" @click="btnChiTietKM_Click" :disabled="!v3_enable_khuyen_mai">
                    <span class="one-eye f20 inline vcenter"></span> Chi tiết
                </button>
            </div>
        </div>
        <div class="info-row">
            <div class="key">Ghi chú</div>
            <div class="value">
                <textarea name="" class="form-control" style="height: 236px;resize: none;"
                    v-model="model.v3_ghi_chu" id="v3_ghi_chu"></textarea>
            </div>
        </div>
    </div>
</template>
<script>
import { Query } from '@syncfusion/ej2-data'
export default {
    name:'ThongTinThueBao',
    components:{

    },
    data() {
        return {
            model: {
                v3_so_may_acc: "",
                v3_ten_thue_bao: "",
                v3_ma_erp: "",
                v3_dia_chi_thue_bao: "",
                v3_ma_bss: "",
                v3_dia_chi_dau: "",
                v3_so_ao_dau: "",
                v3_dia_chi_cuoi: "",
                v3_so_ao_cuoi: "",
                v3_ghi_chu: "",
                v3_toc_do_moi: "",
                v3_toc_do_cu: "",
                v3_khuyen_mai: "",
            },
            v3_LstTocDo: [],
            v3_LstKhuyenMai: [],
            v3_enable_khuyen_mai: false,
        }
    },
    computed: {

    },
    methods: {
        onFilterV3TocDo: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.v3_LstTocDo, query);
        },
        onFilterV3KhuyenMai: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_km', 'contains', e.text, true) : query;
            e.updateData(this.v3_LstKhuyenMai, query);
        },
        btnChiTietKM_Click() {
            this.$emit('btnChiTietKM_Click')
        },
        ShowPopupChonKhuyenMai() {
            this.$emit('ShowPopupChonKhuyenMai')
        },
        txtMaTB_KeyPress(event) {
            this.$emit('txtMaTB_KeyPress', event);
        },
        bntLayMaKh_Click() {
            this.$emit('bntLayMaKh_Click');
        },
        chkKhuyenMai_CheckedChanged() {
            this.$emit('chkKhuyenMai_CheckedChanged');
        },
        cboKhuyenMai_SelectionChangeCommitted(event) {
            this.$emit('cboKhuyenMai_SelectionChangeCommitted',  event.value);
        }
    },
    mounted() {
        
    },
}
</script>