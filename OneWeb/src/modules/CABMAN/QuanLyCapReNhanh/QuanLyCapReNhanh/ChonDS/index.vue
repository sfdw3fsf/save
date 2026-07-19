<template src="./index.html"></template>
<script>
export default {
  name: "ChonDS",
  props: {
    midspanObject: {
      type: Object,
      required: false
    },
    id: String
  },
  data:function (){
    return {
      key: 1,
      isShown: false,
      dsSoi:[],
      soiChecked:[],
      checkAll:false,
      filterSoi:'',
      dsTimKiem:[],
    }
  },
  watch:{
    "checkAll": function (value){
      if (value){
        let dsCheck = this.dsSoi.filter(item => item.READONLY === false).map(item => item.IDX)
        this.soiChecked = [...this.soiChecked,...dsCheck]
      }else{
        this.soiChecked = this.dsSoi.filter(item => item.READONLY === true && item.CHON === "s1").map(item => item.IDX)
      }
    },
    "filterSoi": function (value){
      if (value !== ''){
        this.dsTimKiem = this.dsSoi.filter(item => item.IDX.toString().includes(value))
      }else {
        this.dsTimKiem = this.dsSoi
      }
    }
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      this.checkAll = false
      this.filterSoi = ''
      try {
        this.loading(true)
        let object = new Object()
        object.cap_id = this.midspanObject.CAP_ID
        object.midspan_id = this.midspanObject.MIDSPAN_ID
        object.ketcuoi_n_id = this.midspanObject.KETCUOI_N_ID
        object.ketcuoi_d_id = this.midspanObject.KETCUOI_D_ID
        object.donvi_id = this.midspanObject.DONVI_ID
        object.donvi_td_id = this.midspanObject.DONVI_TD_ID
        await this.getDsSoi(object)
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    btnXacNhan(){
      this.$emit('soiChecked', this.soiChecked.toString())
      this.onHiddenModal()
    },
    async getDsSoi(midSpanObject){
      if (midSpanObject && midSpanObject.cap_id) {
        await this.$root.context.post(
          "/web-cabman/mid-span/lay-ds-soi",midSpanObject
        ).then(res =>{
          this.dsSoi = res.data
          this.dsSoi = this.dsSoi.map(item => ({ CHON:item.CHON, IDX:item.IDX, READONLY:item.READONLY === "s1"}))
          this.dsTimKiem = this.dsSoi
          this.soiChecked = this.dsSoi.filter(item => item.CHON === 's1').map(item => item.IDX)
        }).catch(e=> {
          this.$toast.error("Có lỗi xảy ra khi tải danh sách Sợi")
        })
      }
    },
  },
}
</script>

<style scoped>

</style>
