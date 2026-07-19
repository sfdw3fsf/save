<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='$data.animationSettings'
    ref="dlgPrintPreview"
    :header='"Xem trước khi in"'
    showCloseIcon=true
    width='90%'
    :target='$data.target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>BC_CT_THANHTOANNO_KH - Preview report</div>
            <div class="close -ap icon-close" data-dismiss="modal">
            </div>
        </div>
        <div class="popup-body pad0">

        </div>
    </div>
  </ejs-dialog>
</template>

<script>
  import Vue from 'vue'
  import GachNoAPI from '../../api/GachNoAPI'
  import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
  import {previewPrint} from "@/utils/util"

  Vue.use(DialogPlugin)

  export default Vue.extend({
    components : {
      //appRemotePagination : RemotePagination,
    },
    props: {
      //input: Object,
      dsPhieu : {
        type: Array
      },
      kycuoc : {
        type : String
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
      }
    },
    methods : {
      async openDialog () {
        this.getPrintPreview()
        this.$refs.dlgPrintPreview.show()
      },
      closeDialog() {
        this.$refs.dlgPrintPreview.hide()
      },
      onDialogClose () {
      },
      onDialogOpen () {
      },
      async getPrintPreview(){
        let postData = {
          kyCuoc: this.kycuoc,
          dsPhieu : this.dsPhieu
        }
        this.loading(true)
        try{
          const baseUrl = process.env.API;
          //let postConfig = { headers: { 'X-Requested-With': 'XMLHttpRequest' }, responseType: 'blob', } as AxiosRequestConfig
          this.axios.post(baseUrl + "/web-thuno/api/thu-no/gach-no/bien-nhan", postData, {responseType: 'blob'})
          .then( (response) => {
            /*
            let blob = new Blob([response.data], { type: 'application/pdf' });
            //console.log(blob)
            const fileURL = URL.createObjectURL(blob);
            window.open(fileURL);
            */
            previewPrint(response.data)
          })
          /*
          this.axios({
                  url: baseUrl + "/web-thuno/api/thu-no/gach-no/bien-nhan",
                  method: "POST",
                  responseType: "blob",
                  body: postData
                }).then(response => {
                    console.log("object", response);

                });
          */
          /*
          const res = GachNoAPI.printPhieuThu(this.axios, postData)
          if(res.data.error_code === 'BSS-00000000')
          {
            console.log(res.data.data)
          }
          */
        }catch(err)
        {
        }finally{
          this.loading(false)
        }
      }
    },
    watch: {
    }
  })
</script>

