<template>
  <b-modal
    ref="popupSaveFile"
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
          <span class="icon one-save"></span> Download file
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="row">
            <div class="col-sm-10 col-12">
                <div class="info-row">
                    <div class="key w90">Tên file</div>
                    <div class="value">
                        <input type="text" :value="fileName" @change="e=>fileName=e.target.value" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-2 col-12">
                <div class="info-row">
                    <!-- <div class="key w90">Ghi chú</div> -->
                    <div class="value">
                        <div class="select-custom">
                            <select v-model="extension_selected" class="form-control">
                                <option v-for="item in ds_extension" :key="item.ID" :value="item.ID">
                                    {{item.NAME}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="group-buttons -bottom right">
          <button class="btn btn-second nocorner" style="width: 120px" @click="download">
            Download
          </button>
        </div>
      </div>

    </div>
  </b-modal>
</template>
<script>
export default {
    name:'SaveFileModal',
    props:{
        file_name:{
            type:String,
            default:''
        }
    },
    data(){
        return {
            fileName:'',
            extension_selected:1,
            ds_extension:[
                {
                    ID:1,
                    NAME:'.xlsx'
                },
                {
                    ID:2,
                    NAME:'.txt'
                },
                // {
                //     ID:3,
                //     NAME:'.dbf'
                // }
            ]
        }
    },
    methods:{
        showModal() {
            this.$refs["popupSaveFile"].show()
        },
        hideModal() {
            this.$refs["popupSaveFile"].hide()
        },
        handleShowModal(){
            this.extension_selected=1
            this.fileName=''
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        initDuLieu(){
            this.fileName=this.file_name
        },
        download(){
            if(this.fileName.trim()==''){
                this.$toast.error('Nhập tên tập tin cần download !')
                return
            }
            let extension=this.ds_extension.find(x=>x.ID==this.extension_selected)
            this.$emit('download',{
                file_name:this.fileName.trim(),
                extension:extension.NAME
            })
            this.hideModal()
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
</style>