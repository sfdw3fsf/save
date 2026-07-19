<template>
    <b-modal
        id="popup-chiTietCacLanTaoDuLieu"
        size="lg"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> Chi tiết các lần tạo dữ liệu
                </div>
                <div class="close -ap icon-close" data-dismiss="modal"
                    @click="$bvModal.hide('popup-chiTietCacLanTaoDuLieu')"
                ></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="#">
                            <downloadexcel :data="dsLanTao">
                                <span class="icon one-download"></span>Xuất file
                            </downloadexcel>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Danh sách khách hàng</div>
                    <DataGrid :columns="fields" :dataSource="dsLanTao" :allowPaging="true" :enablePagingServer="false"></DataGrid>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import downloadexcel from "vue-json-excel";
export default {
    props: ["dsLanTao"],
    components: { downloadexcel },
    data() {
        return {
            fields: [
                {
                    fieldName: "MA_KH",
                    headerText: "Mã KH",
                    allowFiltering: true
                },
                {
                    fieldName: "TEN_KH",
                    headerText: "Tên KH",
                    allowFiltering: true
                },
                {
                    fieldName: "DIACHI_KH",
                    headerText: "Địa chỉ KH",
                    allowFiltering: true
                },
                {
                    fieldName: "BINHQUAN_DT",
                    headerText: "Bình quân DT",
                    allowFiltering: true
                },
            ]
        }
    },

}
</script>
