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
                        + Gõ mã vật tư cần tìm vào ô text mã vật tư.<br>
                        + Nhấn enter và đợi kết quả.<br>

                        Đồng bộ:<br>
                        + Tích chọn dòng vật tư cần đồng bộ (Nếu vật tư đã đồng bộ thì sẽ bỏ qua vật tư đó).<br>
                        + Chọn loại thiết bị cho từng vật tư.<br>
                        + Đơn vị tính là tự động, bạn không được phép chọn.<br>
                        + Gõ mã nhóm vào cột mã nhóm (nếu có).<br>
                        + Có thể gán nhanh loại thiết bị bằng tính năng gán nhanh. Tích chọn vật tư, chọn loại thiết bị ở panel gán nhanh, sau đó bấm nút mũi tên xuống nhỏ ngay cạnh để gán nhanh.<br>
                        + Bấm nút Đồng bộ trên thanh công cụ để đồng bộ vật tư.<br>

                        *** Lưu ý:<br>
                        - Nếu vật tư là loại có serial thì chỉ được phép chọn loại thiết bị là serial.<br>
                        - Nếu vật tư không là serial thì không được phép chọn loại thiết bị có serial.<br>
                        - Mỗi mã vật tư đều hiển thị vật tư cha, nếu đồng bộ xong và vào menu vật tư mà không thấy vật tư đã đồng bộ thì kiểm tra vật tư cha đã có trên chương trình chưa. Nếu không được thì liên hệ với admin.<br>
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
