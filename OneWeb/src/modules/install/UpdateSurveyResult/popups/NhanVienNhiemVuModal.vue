<template>
  <b-modal
    ref="popupNhanVienNhiemVu"
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
          <span class="icon one-notepad"></span> Nhân viên nhiệm vụ
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="row">
            <div class="col-12">
                <div class="info-row">
                    <div class="key w90">Nhiệm vụ</div>
                    <div class="value">
                        <multiselect v-model="nhiemvu_selected"
                            :options="ds_nhiemvu"
                            placeholder="Chọn nhiệm vụ"
                            @input="chonNhiemVu"
                            :multiple="true"
                            :close-on-select="false"
                            :clear-on-select="false"
                            :preserve-search="true"
                            label="NHIEMVU"
                            track-by="NHIEMVU_ID">
                            <template #noOptions>
                                Không tìm thấy thông tin
                            </template>
                        </multiselect>
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div class="info-row">
                    <div class="key w90">Ghi chú</div>
                    <div class="value">
                        <textarea
                            name=""
                            :value="input_ghichu" @change="e=>input_ghichu=e.target.value"
                            class="form-control"
                            style="height: 100px; resize: none"
                        ></textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="group-buttons -bottom right">
          <button class="btn btn-second nocorner" style="width: 120px" @click="ok">
            OK
          </button>
        </div>
      </div>

    </div>
  </b-modal>
</template>
<script>
export default {
    name:'NhanVienNhiemVuModal',
    props:{
        dsNhiemVu:{
            type:Array,
            default:()=>[]
        },
        item:{
            type:Object,
            default:()=>{}
        }
    },
    data(){
        return {
            nhiemvu_selected:[],
            ds_nhiemvu:[],
            input_ghichu:''
        }
    },
    methods:{
        showModal() {
            this.$refs["popupNhanVienNhiemVu"].show()
        },
        hideModal() {
            this.$refs["popupNhanVienNhiemVu"].hide()
        },
        handleShowModal(){
            this.nhiemvu_selected=[]
            this.ds_nhiemvu=[]
            this.input_ghichu=''
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        chonNhiemVu(data){

        },
        ok(){
            //handle
            this.item.ghichu=this.input_ghichu.trim()
            this.item.nhiemvu_selected=this.nhiemvu_selected
            this.item.nhiemvu=''
            // this.item.nhiemvu_selected.forEach((x)=>{
            //     this.item.nhiemvu+=x.NHIEMVU+'; '
            // })
            this.item.nhiemvu=this.item.nhiemvu_selected.map(x=>x.NHIEMVU).join(';')
            this.$emit('accept', this.item)
            //
            this.hideModal()
        },
        async initDuLieu(){
            this.ds_nhiemvu=this.dsNhiemVu
            if(this.item.nhiemvu_selected!==undefined){
                this.nhiemvu_selected=this.item.nhiemvu_selected
            }else{
                this.item.nhiemvu_selected=[]
            }
            this.input_ghichu=this.item.ghichu!=null?this.item.ghichu:''
        }
    }
}
</script>
<style>
.btn-second {
    background-color: #fff;
    border-color: #0176FF;
    color: #0176FF;
    font-weight: 600;
}
.btn-second:hover {
    background-color: #0176FF;
    border-color: #0176FF;
    color: #fff;
    font-weight: 600;
}
.btn-outline-secondary {
    background-color: #fff;
    color: #6c757d;
    border-color: #6c757d;
}
.btn-outline-secondary:hover {
    background-color: #6c757d;
    color: #fff;
    border-color: #6c757d;
}
.multiselect, .multiselect__input, .multiselect__single {
    font-size: 1rem !important;
}
.multiselect__tags{
    min-height: 32px !important;
    border: 1px solid #ced4da !important;
    padding: 6px 32px 0 6px;
    border-radius: 4px;
}
.multiselect__placeholder {
    margin-bottom: 5px!important;
    padding-top: 0px!important;
}
.multiselect__select{
    top: 0px!important;
    padding: 10px 9px!important;
}
.multiselect--disabled{
  background: none!important;
}
.multiselect--disabled .multiselect__current, .multiselect--disabled .multiselect__select{
  background: none!important;
}
</style>