<template>
    <div >
        <div class="modal-dialog" style="width: 100%; max-width: 600px;">
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title">Nhập danh sách số máy</div>
                    <div class="close -ap icon-close" data-dismiss="modal">
                    </div>
                </div>

                <div class="popup-body">
                    <div class="box-form">
                        <p class="title">Nhập danh sách số máy</p>
                        <input type="text" class="form-control" v-model="SoMay">
                        <div class="group-buttons -bottom center">
                            <button class="btn btn-main">Xác nhận</button>
                            <button class="btn btn-second">Huỷ bỏ</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>
</template>
<script>
export default {
  name: 'NhapDanhSachSoMay',
  data () {
    return {
      isPopup: false,
      SoMay: ''
    }
  },
  methods: {
    ChonSo: function () {
      this.$emit('themSoMay', this.SoMay)
    },
    HuyBo: function () {
      this.$emit('closeEvent', this.SoMay)
    }
  }

}

</script>
