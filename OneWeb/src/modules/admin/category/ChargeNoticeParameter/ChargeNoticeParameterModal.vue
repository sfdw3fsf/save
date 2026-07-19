<template>
  <b-modal
    ref="popupChargeNoticeParameter"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Tham số in phiếu báo trước
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Chọn số Up</div>
          <div class="info-row">
            <div class="value w30 nowrap padl0">
              <div class="list-checks marb10">
                <div class="item">
                  <div class="check-action">
                    <input type="radio" class="check" name="ups_print" :checked="data.UpsPrint==1" @change="changeUpsPrint(1)"/>
                    <span class="name">1 up</span>
                  </div>
                </div>
                <div class="item vcenter">
                  <span
                    class="
                      f20
                      nc-icon-outline
                      files_single-content-02
                      text-desc
                    "
                  ></span>
                </div>
              </div>
              <div class="list-checks">
                <div class="item vcenter">
                  <div class="check-action">
                    <input type="radio" class="check" name="ups_print" :checked="data.UpsPrint==2" @change="changeUpsPrint(2)" />
                    <span class="name">2 up (in song song hình chữ Z)</span>
                  </div>
                </div>
                <div class="item vcenter">
                  <span
                    class="
                      f20
                      nc-icon-outline
                      files_single-content-02
                      text-desc
                    "
                  ></span>
                  <span
                    class="
                      f20
                      nc-icon-outline
                      files_single-content-02
                      text-desc
                    "
                  ></span>
                </div>
              </div>
            </div>
            <div class="value padl20">
              <div class="list-checks marb10">
                <div class="item vcenter">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="data.Datapost" />
                    <span class="name">Tạo file Datapost</span>
                  </div>
                </div>
                <div class="item vcenter">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="data.QRcode" />
                    <span class="name">Print QR Code</span>
                  </div>
                </div>
                <div class="item vcenter">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="data.Pdf" />
                    <span class="name">Xuất file dạng Pdf</span>
                  </div>
                </div>
              </div>
              <div class="list-checks">
                <div class="item vcenter">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="data.Barcode" />
                    <span class="name">Print Barcode</span>
                  </div>
                </div>
                <div class="item vcenter">
                  <img
                    src="https://meta.vn/Data/image/2020/08/10/code-39.jpg"
                    style="height: 110px; height: 34px; object-fit: contain"
                    alt=""/>
                </div>
                <div class="item vcenter">
                  <div class="info-row marb0">
                    <div class="key w90">Paper Offset</div>
                    <div class="value">
                      <input type="text" ref="inputPaperOffset" class="form-control" @keypress="isNumber($event)" v-model="data.PaperOffset" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form" :class="{disabled:true}">
          <div class="legend-title">Trạm nạp quảng cáo</div>
          <div class="list-checks">
            <div class="item">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name">Trạm 1</span>
              </div>
            </div>
            <div class="item">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name">Trạm 2</span>
              </div>
            </div>
            <div class="item">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name">Trạm 3</span>
              </div>
            </div>
            <div class="item">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name">Trạm 4</span>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Tùy chọn khác</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Tổng số trang trong 1 file</div>
                <div class="value">
                  <input type="text" ref="inputPBTotal" class="form-control tright" @keypress="isNumber($event)" v-model="data.PBTotal" />
                </div>
              </div>
              <div class="check-action">
                <input type="checkbox" class="check" v-model="data.RunSQL" />
                <span class="name">Cập nhật thông tin sau khi tạo file</span>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="check-action marb5">
                <input type="checkbox" class="check" v-model="data.AdvOpion" />
                <span class="name">Đánh số thứ tự phiếu báo cước</span>
              </div>
              <div class="info-row">
                <div class="key w30 nowrap">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="data.InvNumberOption" />
                    <span class="name">Tạo dữ liệu từ số thứ tự</span>
                  </div>
                </div>
                <div class="value">
                  <input type="text" ref="inputInvNumber" class="form-control" :disabled="!data.InvNumberOption" @keypress="isNumber($event)" v-model="data.InvNumber" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="check-action marl15">
          <input type="checkbox" class="check" v-model="data.ShowOption" />
          <span class="name">Hiển thị tùy chọn trước khi in</span>
        </div>
        <div class="group-buttons center">
          <button class="btn btn-main" style="width: 130px" @click="luuThamSo">Lưu tham số</button>
          <button class="btn btn-second" style="width: 130px" @click="hideModal">Đóng</button>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
export default {
  name: "ChargeNoticeParameterModal",
  data(){
      return {
        data:{
            UpsPrint:1,
            Datapost:false,
            Barcode:false,
            QRcode:false,
            Pdf:false,
            PaperOffset:'',
            PBTotal:'',
            RunSQL:false,
            ShowOption:false,
            AdvOpion:false,
            InvNumber:'0',
            InvNumberOption:false   
        }
      }
  },
  computed:{
    ...mapGetters('admin', [
       'UpsPrint',
       'Datapost',
       'Barcode',
       'QRcode',
       'Pdf',
       'PaperOffset',
       'PBTotal',
       'RunSQL',
       'ShowOption',
       'AdvOpion',
       'InvNumber',
       'InvNumberOption'
    ])
  },
  methods:{
    ...mapActions('admin', [
      'setUpsPrint',
      'setDatapost',
      'setBarcode',
      'setQRcode',
      'setPdf',
      'setPaperOffset',
      'setPBTotal',
      'setRunSQL',
      'setShowOption',
      'setAdvOpion',
      'setInvNumber',
      'setInvNumberOption'
    ]),
    showModal() {
        this.$refs["popupChargeNoticeParameter"].show()
    },
    hideModal() {
        this.$refs["popupChargeNoticeParameter"].hide()
    },
    handleShowModal(){
        this.load_tt()
    },
    load_tt(){
        this.data.UpsPrint=this.UpsPrint?this.UpsPrint.toString():''
        this.data.Datapost=this.Datapost&&this.Datapost==1?true:false
        this.data.Barcode=this.Barcode&&this.Barcode==1?true:false
        this.data.QRcode=this.QRcode&&this.QRcode==1?true:false
        this.data.Pdf=this.Pdf&&this.Pdf==1?true:false
        this.data.PaperOffset=this.PaperOffset?this.PaperOffset.toString():''
        this.data.PBTotal=this.PBTotal?this.PBTotal.toString():''
        this.data.RunSQL=this.RunSQL&&this.RunSQL==1?true:false
        this.data.ShowOption=this.ShowOption&&this.ShowOption==1?true:false
        this.data.AdvOpion=this.AdvOpion&&this.AdvOpion==1?true:false
        this.data.InvNumber=this.InvNumber?this.InvNumber.toString():'0'
        this.data.InvNumberOption=this.InvNumberOption&&this.InvNumberOption==1?true:false
    },
    changeUpsPrint(value){
        this.data.UpsPrint=value
    },
    isNumber(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
            evt.preventDefault()
        } else {
            return true
        }
    },
    luuThamSo(){
        if(this.data.InvNumber.trim()==''){
            this.$toast.error('Chưa nhập số thứ tự !')
            this.$refs.inputInvNumber.focus()
            return
        }

        if(this.data.PaperOffset.trim()==''){
            this.$toast.error('Chưa nhập PaperOffset !')
            this.$refs.inputPaperOffset.focus()
            return
        }

        if(this.data.PBTotal.trim()==''){
            this.$toast.error('Chưa nhập tổng số trang trong 1 file !')
            this.$refs.inputPBTotal.focus()
            return
        }

        this.setUpsPrint(this.data.UpsPrint)
        this.setDatapost(this.data.Datapost?1:0)
        this.setBarcode(this.data.Barcode?1:0)
        this.setQRcode(this.data.QRcode?1:0)
        this.setPdf(this.data.Pdf?1:0)
        this.setPaperOffset(this.data.PaperOffset)
        this.setPBTotal(this.data.PBTotal)
        this.setRunSQL(this.data.RunSQL?1:0)
        this.setShowOption(this.data.ShowOption?1:0)
        this.setAdvOpion(this.data.AdvOpion?1:0)
        this.setInvNumber(this.data.InvNumber)
        this.setInvNumberOption(this.data.InvNumberOption?1:0)

        this.$toast.success('Cập nhật tham số in thông báo cước thành công !')
    }
  }
}
</script>
<style scoped>
.btn-main{
    background: #0176FF;
    color: #fff;
}
.btn-main:hover{
    background: #0759b8;
    color: #fff;
}
.btn-second{
    background: #fff;
    color: #0176FF;
    border: 1px solid #0176FF
    
}
.btn-second:hover{
    background: #0176FF;
    color: #fff;
}
.disabled{
    pointer-events:none;
    opacity:0.7;
}
</style>