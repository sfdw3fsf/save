<template>
    <b-modal
        id="popup-maKhachHang"
        size="lg"
        no-close-on-backdrop
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        @hidden="$bvModal.hide('popup-maKhachHang')"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> Danh sách mã khách hàng
                </div>
                <div class="close -ap icon-close" data-dismiss="modal"
                    @click="$bvModal.hide('popup-maKhachHang')"
                ></div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Danh sách mã khách hàng lỗi</div>
                    <DataGrid
                        :columns="fields"
                        :dataSource="dsMaKHLoi"
                        :allowPaging="true"
                        :enablePagingServer="false">
                    </DataGrid>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
export default {
    props: ["dsMaKHLoi"],
    data() {
        return {
            fields: [
                {
                    fieldName: "MA_KH",
                    headerText: "Mã KH",
                    allowFiltering: true
                },
                {
                    fieldName: "message",
                    headerText: "Lỗi",
                    allowFiltering: true
                }
            ]
        }
    }
}
</script>
