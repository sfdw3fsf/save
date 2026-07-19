<template>
    <div class=" nocorner bor0 pad0 bg-none" style="box-shadow: none;">
        <div id="tab1" class="tab-pane active">
            <div class="box marb20">
                <div class="legend-title">Thông tin phân giao dự kiến "Gia hạn trả trước BRCĐ"</div>
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="row">
                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w50">Chương trình</div>
                                    <div id='container'>
                                        <ejs-combobox id='cmbChuongTrinh' :dataSource='data_ct.dulieu' :fields='fields'
                                            placeholder='Chọn chương trình'></ejs-combobox>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tháng KT</div>
                                    <div class="control-section">
                                        <div id="control_wrapper">
                                            <ejs-datepicker start="Year" format="MM/yyyy" depth="Year"></ejs-datepicker>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w30">Tổ</div>
                                    <div id='container'>
                                        <ejs-combobox id='cmbTo' :dataSource='cmbTo.dataSource'
                                            :fields="{ value: 'DICHVUVT_ID', text: 'TEN_DVVT' }"
                                            placeholder="Chọn tổ"></ejs-combobox>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-1 col-12">
                                <div class="info-row">
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <input type="checkbox" class="check">
                                            <span class="name"></span>
                                        </div>
                                    </div>
                                    <div class="key w80">Nhóm ưu tiên</div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tên nhân viên</div>
                                    <div class="value">
                                        <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                        <select2></select2>
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-2 col-12">
                                <div class="info-row">
                                    <div class="key w60">Số lượng</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ComboBoxComponent } from "@syncfusion/ej2-vue-dropdowns";
import { Query, DataManager, ODataV4Adaptor } from '@syncfusion/ej2-data';
import api from '@/modules/contract/setup/CompleteProfileBuySamsungTV/Api.js';

const waterMark = 'Select a date';
export default {
    components: {
        'ejs-combobox': ComboBoxComponent
    },
    data() {
        return {
            data_ct: {
                dulieu: [
                    { "Id": "1", "Name": "Gia hạn trả trước BRCĐ" },
                    { "Id": "2", "Name": "Quản trị TB DĐ" }
                ]
            },
            fields: { text: 'Name', value: 'Id' },

            cmbTo: {
                dataSource: [],
                value: 0,
            },
        }
    },
    created() {
        this.getdsTo()
    },
    methods: {
        async getdsTo() {
            try {
                let response = await api.getcboDichVuVT(this.axios);
                this.cmbTo.dataSource = response.data.data
                console.log(response.data)
            } catch (e) {
                console.log(e)
            }
        },
    }
}
</script>







