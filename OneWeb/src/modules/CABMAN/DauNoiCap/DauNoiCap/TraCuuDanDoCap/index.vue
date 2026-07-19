<template src="./index.html"></template>
<script>
export default {
  name: "TraCuuDanDoCap",
  props: {
    capTraCuuObject: {
      type: Object,
      required: false
    },
    id: String
  },
  data: function () {
    return {
      key: 1,
      isShown: false,
      cap: {
        kyhieu: null,
        soi: null
      },

      dsMidspan:[],
      dsSoi:[],
      dsDanDoCap:[],

      cboMidspan:null,
      cboSoi:null,
      // show: false,
    }
  },
  watch:{
    "cboSoi": async function(value){
      if (!this.isShown) return
      try {
        this.loading(true)
        await this.getDsDanDoCap(this.cap.kyhieu,this.cboMidspan,value)
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
  },
  methods:{
    onHiddenModal() {
      this.beforeDestroy()
      this.isShown = false
      // this.show = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      try {
        this.loading(true)
        this.cap = this.capTraCuuObject
        await this.getDsMidspanTheoKyHieu(this.cap.kyhieu)
        if (this.cboMidspan) {
          await this.getDsSoi(this.cap.kyhieu, this.cboMidspan)
          if (this.cboSoi)
            await this.getDsDanDoCap(this.cap.kyhieu,this.cboMidspan,this.cboSoi)
        }
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async btnTraCuu(){
      if (this.cboMidspan === null){
        this.$toast.warning("Chưa chọn midspan")
        return
      }
      if (this.cboSoi === null){
        this.$toast.warning("Chưa chọn sợi cáp")
        return
      }
      try {
        this.loading(true)
        await this.getDsDanDoCap(this.cap.kyhieu, this.cboMidspan, this.cboSoi)
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async getDsMidspanTheoKyHieu(kyhieu){
      await this.$root.context.get(
        "/web-cabman/daunoi-cap/lay-ds-midspan-theo-kyhieu",{kyhieu:kyhieu}
      ).then(res =>{
        this.dsMidspan = res.data
        if (res.data.length > 0) this.cboMidspan = res.data[0].MIDSPAN_ID
      }).catch(e=> {
        this.$toast.error("Có lỗi xảy ra khi tải danh sách mid-span")
      })
    },
    async getDsSoi(kyhieu, midspanId){
      await this.$root.context.get(
        "/web-cabman/daunoi-cap/lay-ds-soi-cap",{kyhieu:kyhieu, midspanId:midspanId}
      ).then(res =>{
        this.dsSoi = res.data
        if (res.data.length > 0) this.cboSoi = this.cap.soi
      }).catch(e=> {
        this.$toast.error("Có lỗi xảy ra khi tải danh sách sợi cáp")
      })
    },
    async getDsDanDoCap(kyhieu, midspanId, soi){
      await this.$root.context.get(
        "/web-cabman/daunoi-cap/lay-ds-dando-cap",{kyhieu:kyhieu.toString().trim(), midspanId:midspanId, soi:soi}
      ).then(res =>{
        this.dsDanDoCap = res.data
      }).catch(e=> {
        if (e.response && e.response.data)
          this.$toast.error(e.response.data.message_detail)
        else this.$toast.error("Có lỗi xảy ra khi tải danh sách dẫn độ cáp")
      })
    },
    beforeDestroy(){
      this.dsMidspan = []
      this.dsSoi = []
      this.dsDanDoCap = []
      this.cboMidspan = null
      this.cboSoi = null
    }
  },
}
</script>

<style scoped>

</style>
