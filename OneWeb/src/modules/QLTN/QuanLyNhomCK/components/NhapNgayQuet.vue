<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgNhapNgayQuet"
        header="Nhập ngày quét"
        :animationSettings='animationSettings'
        showCloseIcon=true
        width='500px'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
            <div class="">
                <div class="list-actions-top">
                    <ul class="list">

                        <li>
                            <a href="javascript: void(0)" @click="onClickAccept">
                                <span class="icon -ap icon-ion-ios-checkmark"></span> Xác nhận
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="close">
                                <span class="icon one-export"></span> Hủy bỏ
                            </a>
                        </li>


                    </ul>
                </div>
                <div class="popup-body">
                    <div class="row">
                      <div class="info-row">
                          <div class="key text-main w100">Ngày quét</div>
                          <div class="value">
                            <ejs-numerictextbox locale="vi-VN" :min="1" :showSpinButton="true" class="red form-control tright" v-model="$data.p_songay_quet" format="N0"></ejs-numerictextbox>
                          </div>

                      </div>
                      <div class="info-row">
                        Khi đến ngày bạn chọn thì chương trình sẽ tự động gộp nhóm
                      </div>
                    </div>

                </div>
            </div>
    </ejs-dialog>
</template>
<script>
import CommonsAPI from '../../api/Commons'
import NhomChuyenKhoanAPI from '../../api/NhomChuyenKhoan'
export default {
    props: {
    },
    computed: {
    },
    data () {
        return {
          p_songay_quet: 0,
          target: '.main-wrapper',
          animationSettings: { effect: 'Zoom' }
        }
    },
    methods: {

        dialogOpen() {
          // load tham số từ cấu hình
          // this.p_songay_quet = 2
          CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'NGAYQUET_TS_NHOMCK'})
            .then(res => {
              if(res.data.error_code === 'BSS-00000000'
                && res.data.data !== undefined
                && res.data.data.length > 0)
                {
                  this.p_songay_quet = parseInt(res.data.data[0].TEN_TS)
                }
            })
            .catch(error => {
              this.$toast.error(error.data.message_detail)
            })
        },

        dialogClose() {
        },

        openDialog () {
          this.$refs.dlgNhapNgayQuet.show();
        },

        close() {
          this.$refs.dlgNhapNgayQuet.hide();
        },
        onClickAccept() {
          // update tham số vào DB
          let postData = {
            ngayQuet: this.p_songay_quet
          }
          this.loading(true)
          NhomChuyenKhoanAPI.capnhatTSNgayQuet(this.axios, postData)
            .then((res) => {
              if(res.data.error_code === 'BSS-00000000')
              {
                this.$toast.success('Cập nhật thành công')
                this.close()
              }
            })
            .catch(error => {
              this.$toast.error(error.data.message_detail)
            })
            .finally(() => {
              this.loading(false)
            })

        },
    }
}
</script>
