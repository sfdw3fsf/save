<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgThayDoiNgayTT"
        header="Thay đổi ngày thanh toán"
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
                            <a href='javascript: void(0)' @click="onClickAccept">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="close">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>


                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="popup-tdntt">
                            <div class="icon">
                                <img src="../../../../assets/img/calendar-full.svg" alt="">
                            </div>
                            <div class="tdntt-content">
                                <span class="note red fw6  block">
                                    <span class="-ap icon-warning3 text-warning f20 mar10"></span> Chỉ được chuyển trong cùng kỳ hóa đơn
                                </span>
                                <div class="row">
                                    <div class="col-md-8 col-12">
                                        <div class="info-row">
                                            <div class="key text-main">Ngày TT cũ</div>
                                            <div class="value">
                                                <ejs-datepicker
                                                    v-model="ngayTTCu"
                                                    :value="ngayTTCu"
                                                    :max="today"
                                                    :format="'dd/MM/y'"
                                                    :disabled="true"
                                                >
                                                </ejs-datepicker>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-12">
                                        <div class="label-text text-main">{{getChuKyCu}}</div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8 col-12">
                                        <div class="info-row">
                                            <div class="key">Ngày TT mới</div>
                                            <div class="value">
                                                <ejs-datepicker
                                                    v-model="ngayTTMoi"
                                                    :value="today"
                                                    :max="today"
                                                    :format="'dd/MM/y'"
                                                    :showClearButton="false"
                                                >
                                                </ejs-datepicker>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-12">
                                        <div class="label-text red">{{getChuKyMoi}}</div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8 col-12">
                                        <div class="info-row">
                                            <div class="key">Mật khẩu</div>
                                            <div class="value">
                                                <input :type="typeInput" autofocus  @keyup.enter="onClickAccept" v-model="password" placeholder="" class="form-control highlight">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-12">
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
import moment from 'moment'
import XLAPI from '../../api/XuLyPhieuThanhToanAPI.js'

export default {
    name: 'PopupThayDoiNgayThanhToan',

    props: {
      phieu : {
        type: Object
      },
      kycuoc : {
        type : String
      }
    },

    computed: {
        getChuKyCu (){
          let kyhd = moment(this.kycuoc, 'YYYYMMDD').format('MMYYYY')
          let chuky = moment(this.kycuoc, 'YYYYMMDD').format('DD')
          return 'Kỳ: ' + kyhd + '. Lần: ' + chuky
        },
        getChuKyMoi (){
          let kyhd = moment(this.ngayTTMoi).subtract(1,'months').endOf('month').format('MMYYYY')
          let chuky = '01'
          return 'Kỳ: ' + kyhd + '. Lần: ' + chuky
        },
        getPhieu() {
          return this.phieu
        }
    },

    data () {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            ngayTTCu: new Date(),
            ngayTTMoi: new Date(),
            today: new Date(),
            chuKyMoi: '01',
            password: '',
            result: false,
            thongtinPhieu: null,
            typeInput: 'text'
        }
    },

    methods: {
        dialogOpen() {
          this.typeInput = 'password'
          this.password = ''
        },

        dialogClose() {
          this.typeInput = 'text'
          this.$emit('acceptChange', this.result);
        },

        openDialog () {
          this.$refs.dlgThayDoiNgayTT.show();
        },

        close() {
          this.ngayTTMoi = new Date();
          this.today = new Date();
          this.$emit('acceptChange', this.result);
          this.$refs.dlgThayDoiNgayTT.hide();
        },

        onClickAccept() {
          this.changeDateProcess()
        },
        async changeDateProcess()
        {
          let errors = []
          if (this.ngayTTMoi === null)
          {
            errors.push('Chưa nhập ngày thanh toán mới!');
          }
          if(this.password === null || this.password === '' || this.password.trim() === '')
            errors.push('Chưa nhập xác nhận mật khẩu!');
          if(errors.length > 0)
          {
            this.$toast.error(errors.join('\r\n'))
            return
          }

          try{
            this.loading(true);
            let apiBody = {
              pKyCuoc: this.kycuoc,
              pPhieuId: this.thongtinPhieu.PHIEU_ID,
              pNgayTT: moment(this.ngayTTMoi).format('DD/MM/YYYY'),
              pPassword: this.password
            };
            console.log(apiBody)

            if (this.thongtinPhieu.PHIEU_ID_NEO !== '' && this.thongtinPhieu.PHIEU_ID_NEO > 0) {
              this.$toast.error('Bạn không được cập nhật ngày thanh toán của mã ' + this.thongtinPhieu.MA_TT + ' do đã gạch trên VNP!');
              return;
            }

            let result = await this.updateNgayThanhToan(apiBody);

            if (result) {
              this.$toast.success('Cập nhật ngày thanh toán thành công!');
              this.result =  true
              this.close();
            } else
            {
              this.result =  false
            }
          } catch(error)
          {
            this.$toast.error(error)
          }finally{
            this.loading(false)
          }
      },
      async updateNgayThanhToan(apiBody) {
        try {
          let response = await XLAPI.updateNgayThanhToan(this.axios, apiBody);
          return response.data.error_code === 'BSS-00000000' ? true : false;
        }
        catch (error) {

          this.$toast.error('Không thể cập nhật ngày thanh toán của phiếu ' + apiBody.pPhieuId + ' : ' + error.data.message_detail)
          return false;
        }
        finally {}
      },
    },
    watch: {
      getPhieu : {
        handler: function(new_value, old_value) {
          this.thongtinPhieu = new_value
          if(this.thongtinPhieu !== null)
            this.ngayTTCu = moment(this.thongtinPhieu.NGAY_TT, 'DD/MM/YYYY').toDate()
        }
      },
    }
  }
</script>
