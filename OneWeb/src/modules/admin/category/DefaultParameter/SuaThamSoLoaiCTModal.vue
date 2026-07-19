<template>
  <b-modal
    ref="popupSuaThamSoLoaiCT"
    size="sm"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-esc
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>{{ts.tenloai_ct}}
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="row">
            <div class="info-row">
                <div class="key w70">Tên đăng nhập</div>
                <div class="value">
                    <input type="text" v-model="ts.ma_nd" class="form-control" />
                </div>
            </div>
        </div>
        <div class="row">
            <div class="info-row">
                <div class="key w70">Mật khẩu</div>
                <div class="value">
                    <input type="text" v-model="ts.matkhau" class="form-control" />
                </div>
            </div>
        </div>
      </div>
      <div class="row">
          <div class="col-12 ">
              <button @click="ok" class="btn btn-primary w60 mt-0 ml-1 mr-3 mb-2 float-right">ok</button>
          </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
export default {
    name:'SuaThamSoLoaiCTModal',
    props:{
        thamso:{
            type:Object,
            default:null
        }
    },
    data(){
        return{
            ts:this.thamso!=null?this.thamso:{}
        }
    },
    methods:{
        showModal() {
            this.$refs["popupSuaThamSoLoaiCT"].show()
        },
        hideModal() {
            this.$refs["popupSuaThamSoLoaiCT"].hide()
        },
        handleShowModal(){
            this.ts=this.thamso
        },
        ok(){
          this.$emit('callbackChangeValue', this.ts)
          this.hideModal()
        }
    },
    watch:{
        thamso(val){
            this.ts=val
        }
    }
}
</script>