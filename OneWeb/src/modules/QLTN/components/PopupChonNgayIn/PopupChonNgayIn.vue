<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgChonNgayIn"
        header="Chọn ngày in"
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
                            <a href="javascript: void(0)" @click="onClickAccept">
                                <span class="icon one-print"></span> In phiếu
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)">
                                <span class="icon one-seri"></span> Xem KQ
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)">
                                <span class="icon one-seri"></span> Xuất file
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
                      <div class="info-row">
                          <div class="key text-main w60">Ngày in</div>
                          <div class="value">
                            <ejs-datepicker
                                v-model="ngay_in"
                                :value="ngay_in"
                                :format="'dd/MM/y'"
                                :showClearButton="false"
                            >
                            </ejs-datepicker>
                          </div>
                      </div>
                    </div>
                </div>
            </div>
    </ejs-dialog>
</template>
<script>
export default {
    props: {
    },
    computed: {
    },
    data () {
        return {
          ngay_in: new Date(),
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
          this.ngay_in = new Date()
          this.$refs.dlgChonNgayIn.show();
        },

        close() {
          this.$refs.dlgChonNgayIn.hide();
        },
        onClickAccept() {
          let data = {
            ngay_in: this.ngay_in
          }
          this.$emit('acceptPrint', data);
          this.close()
        },
        onUpdateSeri()
        {

        },
    }
}
</script>
