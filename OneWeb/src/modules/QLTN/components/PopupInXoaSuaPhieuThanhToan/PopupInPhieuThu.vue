<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgInPhieuThu"
        header="In phiếu thu"
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
                            <a href="javascript: void(0)"  @click="onUpdateSeri">
                                <span class="icon one-seri"></span> Sửa seri
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="onClickAccept">
                                <span class="icon one-print"></span> In phiếu
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
                                <img src="../../../../assets/img/printer1.svg" alt="">
                            </div>
                            <div class="tdntt-content">
                                <div class="info-row">
                                    <div class="key text-main w60">Seri</div>
                                    <div class="value">
                                        <ejs-numerictextbox :max="10000000000" :showSpinButton=false class="form-control highlight text-main tright"  v-model="params.seri" format="N0"></ejs-numerictextbox>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key red w60">Thùng</div>
                                    <div class="value">
                                        <ejs-numerictextbox :max="10000000000" :showSpinButton=false class="form-control highlight text-main tright"  v-model="params.thung" format="N0"></ejs-numerictextbox>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w60">Số seri</div>
                                    <div class="value">
                                        <ejs-numerictextbox :max="10000000000" :showSpinButton=false class="form-control highlight text-main tright"  v-model="params.so_seri" format="N0"></ejs-numerictextbox>
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
import GachNoAPI from '../../api/GachNoAPI.js'
export default {
    name: 'PopupInPhieuThu',
    props: {
    },
    computed: {
      ds_phieu () {
        return this.dsPhieu
      }
    },
    data () {
        return {
          params: {
            seri: 0,
            thung: 0,
            so_seri: 0,
            is_print: 0
          },
          target: '.main-wrapper',
          animationSettings: { effect: 'Zoom' }
        }
    },
    methods: {

        dialogOpen() {
        },

        dialogClose() {
        },

        openDialog () {
          this.params.seri = 0
          this.params.thung = 0
          this.params.so_seri = 0
          this.params.is_print = 0
          this.$refs.dlgInPhieuThu.show();
        },

        close() {
          this.$refs.dlgInPhieuThu.hide();
        },
        onClickAccept() {
          //if(!this.validateForm())
          //  return
          this.params.is_print = 1
          this.$emit('accept', this.params);
          this.close()
        },
        onUpdateSeri()
        {
          if(!this.validateForm())
            return
          //this.updateProcess()
          this.params.is_print = 0
          this.$emit('accept', this.params);
          this.close()
        },

        validateForm(){
          let errors = []

          if(this.params.seri === null || this.params.seri === 0)
            errors.push('Hãy nhập seri.')
          if(this.params.thung === null || this.params.thung === 0)
            errors.push('Hãy nhập số thùng.')
          if(this.params.so_seri === null || this.params.so_seri === 0)
            errors.push('Hãy nhập số seri .')
          if(errors.length > 0)
          {
            this.$toast.error(errors.join('\r\n'))
            return false
          }
          return true
        },
        async updateProcess(args) {
          let phieuCount = 0;
          if(this.dsPhieu.length <= 0)
          {
            this.$toast.error('Không tìm thấy danh sách phiếu cần cập nhật.')
            return
          }
          this.loading(true);
          try {
            let apiBody = {
              "pKyCuoc": this.kycuoc,
              "pSeri": this.seri,
              "pQuyen": this.thung,
              "pSoSeri": this.so_seri,
              "pPhieuId": 0
            };
            for (let item of this.dsPhieu) {

              if (this.kiemTraNgayGachPhieu(0, item)) {
                apiBody.pPhieuId = item.PHIEU_ID
                apiBody.pSoSeri = apiBody.pSoSeri + phieuCount
                let result = null
                if(this.isPhieuHuy)
                  result = await this.updateSeriPhieuHuy(apiBody)
                else
                  result = await this.updateSeriPhieuThu(apiBody)
                if (result) {
                  phieuCount ++;
                }
              }
            };

            if (phieuCount === this.dsPhieu.length) {
              this.$toast.success('Cập nhật phiếu thanh toán thành công!');
              this.result =  true
              this.$emit('acceptUpdateSeriPhieuThu', this.result);
              this.close();
            } else
            {
              this.result =  false
            }
            //await this.doSearch();
          }
          catch (error) {
            console.log(error);
          }
          finally {
            this.loading(false);
          }
        },
      kiemTraNgayGachPhieu(actionType, item, value = null) {
        return true
      },
      async updateSeriPhieuThu(apiBody) {
        try {
          let response = await GachNoAPI.updateSeriThanhToan(this.axios, apiBody);
          return response.data.error_code === 'BSS-00000000' ? true : false;
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          return false;
        }
        finally {}
      },
      async updateSeriPhieuHuy(apiBody) {
        try {
          let response = await GachNoAPI.updateSeriPhieuHuy(this.axios, apiBody);
          return response.data.error_code === 'BSS-00000000' ? true : false;
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          return false;
        }
        finally {}
      },
    }
}
</script>
