<template>
    <b-modal 
        ref="popupHuongDan"
        size="lg"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                <span class="icon one-notepad"></span> Hướng dẫn
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Hướng đẫn sử dụng</div>
                    <div class="article">
                        Tìm kiếm:<br>
                        + Nhập địa chỉ nhấn Enter để tìm theo địa chỉ<br>
                        + Nhập tọa độ nhấn nút Tìm kiếm focus đến vị trí cần tạo.<br>

                        Tạo vùng:<br>
                        + Sử dụng công cụ góc phải màn hình chọn biểu tượng vẻ đa giác chấm các điểm nối nhau kéo thả, hoàn tất vẻ click phải chuột hoặc nhấn vào cái điểm cuối thêm 1 lần nữa<br>
                        Sửa vùng:<br>
                        + Chọn vùng và bấm giữ kéo thả vị trí<br>
                        + Nhấn vào các điểm cạnh kéo thả nhấn vào điểm đầu sinh ra trung điểm giữa kéo thả<br>

                        Xóa: <br>
                        + Chọn vùng cần xóa nhấn biểu tượng xóa <br>

                        Cập nhật:<br>
                        + Sau khi hoàn tất vẻ nhấn nút cập nhật để lưu<br>

                    </div>
                </div>
            </div>
        </div>    
    </b-modal>
</template>
<script>
export default {
    name:'HuongDanModal',
    data(){
        return {

        }
    },
    methods:{
        showModal() {
            this.$refs['popupHuongDan'].show()
        },
        hideModal() {
            this.$refs['popupHuongDan'].hide()
        }
    }

};
</script>
