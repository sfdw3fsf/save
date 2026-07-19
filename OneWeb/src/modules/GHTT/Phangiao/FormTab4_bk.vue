<template>
    <div class=" nocorner bor0 pad0 bg-none" style="box-shadow: none;">
        <div id="tab2" class="tab-pane">
            <div class="tab-content">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="box-form">
                            <div class="legend-title">Thông tin nhân viên điều chỉnh</div>
                            <div class="row">
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Chương trình</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Phòng</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Nhân viên điều chỉnh</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="box-form">
                            <div class="row">
                                <div class="col-sm-12 col-12">
                                    <!-- <div class="info-row"> -->
                                    <div class="legend-title">Danh sách thuê bao của nhân viên cần điều chỉnh</div>
                                    <div style="height: 200px;">
                                        <grid-tab41></grid-tab41>
                                    </div>
                                    <!-- <div class="box-move-select-table">
                                        <div class="box-col">
                                        <div>
                                            <div class="table-content" style="height: 250px">
                                                <vue-table v-model="currentRecordContractTypeDeliver"
                                                    :options="listContractTypeDeliver" @input="onCheckBoxDeliver"
                                                    :config="contractTypeHeaderDeliver" :unique="'kieuld_id'"
                                                    :multiple="true"></vue-table>
                                            </div>

                                        </div>
                                    </div> -->
                                    <!-- </div> -->
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--left -->
                    <div class="col-sm-6 col-12">
                        <div class="box-form">
                            <div class="row">
                                <div class="legend-title">Thông tin nhân viên tiếp nhận</div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Xử lý từ ngày</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Xử lý đến ngày</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Phòng</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Nhân viên tiếp nhận</div>
                                        <div class="value">
                                            <!-- <select2 v-model="listbox.loai_gt.value" class="select-custom" :options="listbox.loai_gt.list.map(gt=>({id:gt.loaigt_id,text:gt.loai_gt}))"></select2> -->
                                            <select2></select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="box-form">
                            <div class="row">
                                <div class="col-sm-12 col-12">
                                    <div class="legend-title">Danh sách thuê bao gán thêm cho nhân viên tiếp nhận</div>
                                    <div style="height: 200px;">
                                        <grid-tab42></grid-tab42>
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
import { TabComponent, TabItemDirective, TabItemsDirective } from '@syncfusion/ej2-vue-navigations';

import GridTab41 from '@/modules/ghtt/phangiao/GridTab41.vue'
import GridTab42 from '@/modules/ghtt/phangiao/GridTab42.vue'
// import InstallationType from "@/modules/admin/category/Promotion/components/InstallationType/InstallationType.vue"
export default {
    components: {
        'ejs-tab': TabComponent,
        'e-tabitem': TabItemDirective,
        'e-tabitems': TabItemsDirective
        , 'grid-tab41': GridTab41
        , 'grid-tab42': GridTab42
        // , InstallationType
    },
    name: 'app',
    data: function () {
        return {
            contractTypeHeaderDeliver: [
                {
                    label: "Kiểu lắp đặt đã gán",
                    filterAble: true,
                    key: "ten_kieuld",
                },
            ],
        }
    },
    method: {
        onCheckBoxDeliver(e) {
            this.tempContractTypeDeliver = e;
        },
    }
}
</script>

