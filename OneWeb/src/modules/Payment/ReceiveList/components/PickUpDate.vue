<template>
  <ejs-dialog :enableResize='true' :allowDragging='true' :visible='false' ref="dialogPickUpDate" showCloseIcon=true
        width='45%' target='#app .main-wrapper' header="Chọn ngày tạo bảng kê">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="javascript:;" @click="btnChapNhanClick">
                        <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">Điều kiện tìm kiếm</div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w60">Từ ngày</div>
                            <div class="value">
                                <date-picker input-class="form-control" format="DD/MM/YYYY" :value-type="'format'" v-model="vtungay" />
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w80">Đến ngày</div>
                            <div class="value">
                                <date-picker input-class="form-control" format="DD/MM/YYYY" :value-type="'format'" v-model="vdenngay" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>

<script>
import moment from "moment";
import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css'
export default {
    components: { DatePicker },
    data() {
        return {
            vtungay: moment(new Date()).format("DD/MM/YYYY"),
            vdenngay: moment(new Date()).format("DD/MM/YYYY"),

        }
    },
    methods: {
        btnChapNhanClick() {
            this.$emit("chapNhan", { vtungay: this.vtungay, vdenngay: this.vdenngay });
            this.hide();
        },
        show() {
            this.$refs.dialogPickUpDate.show();
        },
        hide() {
            this.$refs.dialogPickUpDate.hide();
        }
    }
}
</script>

<style>

</style>