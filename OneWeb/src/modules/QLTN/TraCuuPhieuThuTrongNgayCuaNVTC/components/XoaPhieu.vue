<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgXoaPhieuTT"
        header="Xác nhận xóa phiếu"
        :animationSettings='animationSettings'
        showCloseIcon=true width='50%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
        <div class="">
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="javascript: void(0)" @click="onClickAccept" title="Chấp nhận">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="close" title="Thoát">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="popup-tdntt">
                            <div class="icon">
                                <img src="../../../../assets/img/account.svg" alt="">
                            </div>
                            <div class="tdntt-content">
                                <span class="note red fw6 bold block">
                                    Bạn có đồng ý xóa {{ tongPhieu | currency }} phiếu với tổng tiền: {{ tongTien | currency }} ?
                                </span>
								<br>
                                <div class="row">
                                    <div class="col-md-10 col-12">
                                        <div class="info-row">
                                            <div class="key w80">Mật khẩu</div>
                                            <div class="value">
                                                <input type="password" @keyup.enter="onClickAccept" ref='xacnhanxoaphieu_password' autofocus placeholder="" class="form-control highlight" v-model="password">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </ejs-dialog>
</template>
<script>
import CommonsAPI from '../../api/Commons.js'
import XLAPI from '../../api/XuLyPhieuThanhToanAPI.js'
import { currency } from '@/filters/currency'

export default {
    name: 'XoaPhieu',

    props: {
      dsPhieu : {
        type: Array
      },
      kycuoc: {
        type: String
      }
    },

    computed: {
      ds_phieu () {
        return this.dsPhieu
      }
    },

    data () {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            password: '',
            result: false,
            tongPhieu: 0,
            tongTien: 0
        }
    },
    methods: {
      dialogOpen() {
      },

      dialogClose() {
      },

      openDialog () {
        this.password = '';
        this.$refs.dlgXoaPhieuTT.show();
      },

      onClickAccept() {
       this.deleteProcess()
      },

      close() {
        this.$refs.dlgXoaPhieuTT.hide();
      },

      kiemTraNgayGachPhieu(actionType, item, value = null) {
        return true
      },

      async deleteProcess(args) {
        let phieuCount = 0;
        if(this.dsPhieu.length <= 0)
            return this.$toast.error('Không tìm thấy danh sách phiếu cần xóa!');
        if(this.password == null || this.password.trim() === '')
            return this.$toast.error('Chưa xác nhận mật khẩu!');
        try {
            this.loading(true);
            let apiBody = {
                pKyCuoc: this.kycuoc,
                pMayHuy: '-',
                pNguoiHuy: this.$auth.getUserName(),
                pPhieuId: 0,
                pPassword: this.password
            };
            for (let item of this.dsPhieu) {
                if (this.kiemTraNgayGachPhieu(0, item)) {
                    apiBody.pPhieuId = item.PHIEU_ID;
                    let result = await this.deletePhieuThu(apiBody);
                    if (result) phieuCount++;
                    else break;
                }
            };

            if (phieuCount === this.dsPhieu.length) {
                this.$toast.success('Xóa phiếu thanh toán thành công!');
                this.result = true;
                this.close();
            } else this.result = false;
            this.$emit('acceptChange', this.result);
        }
        catch (error) {
          console.log(error);
        }
        finally {
          this.loading(false);
        }
      },

      async deletePhieuThu(apiBody) {
        try {
          let response = await XLAPI.deletePhieuThanhToan(this.axios, apiBody);
          return response.data.error_code === 'BSS-00000000' ? true : false;
        }
        catch (error) {
          this.$toast.error(error.data.message_detail);
          return false;
        }
        finally {}
      },

    },
    watch: {
      ds_phieu : {
        handler: function(new_value, old_value) {
          this.tongPhieu = 0;
          this.tongTien = 0;
          if(new_value.length > 0)
          {
            this.tongPhieu = new_value.length;
            new_value.forEach((item) => {
              this.tongTien += item.TIEN_TRA;
            })
          }
        }
      },
    }
}
</script>
