<template src="./TSBCTemplate.html"></template>
<script>
import Calculator from '@/components/Calculator'
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"

export default{
    name:'TSBCTemplate',
    components:{Calculator, CheckBoxSelection},
    data (){
      return {
        valueTemplate:'',
        dateFormat:'dd/MM/yyyy',
        timeFormat:'hh:mm:ss',
        KEY_VALUE:'a',
        KEY_FIELD:'a',
        sourceImg:'null',
        isCalculatorShow: false,
        dsLenh:[],
      }
    },
    computed:{
      grid: function () {
        return this.$parent;
      },
      getDsLOAIDK4(){
        try {
          this.loading(true)
          // let ds = await this.
        }catch (e) {
          return []
        }finally {
          this.loading(false)
        }
      },
    },
    async mounted() {
      if (this.data.blob_val) this.sourceImg = 'data:image/png;base64,' + this.data.blob_val
      if (this.data.lenh) {
        this.getData()
      }
    },
    methods:{
      updateData: function () {
        this.grid.$parent.updateDataSource(this.data);
      },
      getData(){
        if (this.data && this.data.lenh && this.data.lenh.length > 0) {
          if (this.data.loai_dk == 4){
            this.dsLenh = this.data.lenh
            if (Object.keys(this.data.lenh[0]).length == 1){
              this.KEY_FIELD = Object.keys(this.data.lenh[0])[0]
              this.KEY_VALUE = Object.keys(this.data.lenh[0])[0]
            }else {
              this.KEY_FIELD = Object.keys(this.data.lenh[0])[1]
              this.KEY_VALUE = Object.keys(this.data.lenh[0])[0]
            }
          }
          if (this.data.loai_dk == 5){
            this.KEY_FIELD = Object.keys(this.data.lenh[0])[1]
            this.KEY_VALUE = Object.keys(this.data.lenh[0])[0]
            console.log("KEY_FIELD:",this.KEY_FIELD, "KEY_VALUE:",this.KEY_VALUE)
            // this.data.lenh.push({
            //   tentinh:'DN',
            //   tinh_id:43
            // })
            if (this.data.giatri){
              let giatri = this.data.giatri
              console.log("giatri:",giatri)
              setTimeout(()=>{
                let idxs = this.data.lenh.filter(item => giatri.includes(item[this.KEY_VALUE])).map((item,idx) => idx)
                console.log('idxs:',idxs)
                this.$nextTick(()=>{
                  this.$refs['cboTENTINH'].$children[0].grid.selectRows(idxs)
                })
              },30)
            }
          }
        }
        // await this.$root.context.post('/web-quantri/thamso-quanly-thuno/query-dynamic', {
        //   query:query
        // }).then(res =>{
        //   if (res.message_detail)
        //     throw e
        //   this.dsLenh = res.data
        //   if (this.data.loai_dk == 4){
        //     if (Object.keys(res.data[0]).length == 1){
        //       this.KEY_FIELD = Object.keys(res.data[0])[0]
        //       this.KEY_VALUE = Object.keys(res.data[0])[0]
        //     }else {
        //       this.KEY_FIELD = Object.keys(res.data[0])[0]
        //       this.KEY_VALUE = Object.keys(res.data[0])[1]
        //     }
        //   }
        //   if (this.data.loai_dk == 5){
        //     this.KEY_FIELD = Object.keys(res.data[0])[1]
        //     this.KEY_VALUE = Object.keys(res.data[0])[0]
        //     console.log("KEY_FIELD:",this.KEY_FIELD, "KEY_VALUE:",this.KEY_VALUE)
        //     this.dsLenh.push({
        //       tentinh:'DN',
        //       tinh_id:43
        //     })
        //     if (this.data.giatri){
        //       let giatri = this.data.giatri
        //       setTimeout(()=>{
        //         let idxs = this.dsLenh.filter(item => giatri.includes(item[this.KEY_VALUE])).map((item,idx) => idx)
        //         console.log('idxs:',idxs)
        //         this.$refs['cboTENTINH'].$children[0].grid.selectRows(idxs)
        //       },20)
        //     }
        //   }
        // }).catch(e => {throw e})
      },
      previewImage(event){
        let input = event.target
        // let index = parseInt(event.path[3].id.split("_")[1], 10)
        // if (isNaN(index)){ // do mất 1 thẻ span.one-trash
        //   index = parseInt(event.path[2].id.split("_")[1], 10)
        // }
        if (input.files) {
          let reader = new FileReader()
          reader.onload = (e) => {
            this.data.blob_val = this.parseBase64ToByteArray(e.target.result);
            this.sourceImg = e.target.result // file upload minio
            this.updateData()
          }
          reader.readAsDataURL(input.files[0])
        }
      },
      chonImage(){
        document.getElementById(this.data.ma_ts).click();
      },
      deleteImage(){
        this.sourceImg = 'null'
        this.data.blob_val = null
        this.updateData()
      },
      parseBase64ToByteArray(dataUri){
        const byteCharacters = atob(dataUri.split(',')[1])
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        return byteNumbers
      },
    },
    watch:{
      "data.giatri":function (val){
        if (val && this.data.loai_dk == 5) this.data.giatri = val.toString()
        console.log("data.giatri:",this.data.giatri)
        this.updateData()
      },
    }
}

</script>
<style>
.e-grid.e-responsive .e-rowcell, .e-grid.e-responsive .e-headercelldiv {
  white-space: inherit !important;
}
.calculator-custom {
  position: absolute;
  right: 0;
  left: auto;
}
</style>
