<template src="./index.template.html"></template>
<script>
export default {
  components: {},
  name: 'ChonDoiTuongDich',
  props: {
    modalShow: Boolean,
    data: Object,
    listObj: Object,
    id: String
  },

  computed: {
  },

  data () {
    return {
        // key: 1,
        isShown: false,
        ok:true,
        showDetail: false,
        enableButton: false,
        dsDoiTuong: [],
        dsDoiTuong2: [],
        dataChon: {}
    }
  },
  methods: {

    onHiddenModal() {
      this.isShown = false
      this.dsDoiTuong = []
    },
    async onShownModal() {
        try{
            this.loading(true)
            this.dsDoiTuong = []
            this.isShown = true
            await this.loadDanhsachDoituong()
        }catch(error){

        }finally{
            this.loading(false)
        }
    },
    showAll: function(){
        this.showDetail != this.showDetail
        if (this.showDetail){
            let dsNoShow =  JSON.parse(JSON.stringify(this.dsDoiTuong))
            dsNoShow.forEach(element => {
                 element.THONGTIN_DT = element.THONGTIN_DT.replace('\r\n', '\<br\\>'),
                element.LOAI_DT = (element.LOAI_DT ==="TRAM_TB" ? 'Trạm thiết bị' : (element.LOAI_DT == "KETCUOI" ? 'Kết cuối' : ''))
            })
            this.dsDoiTuong2 = JSON.parse(JSON.stringify(dsNoShow))
        }
        else {
            let dsNoShow =  JSON.parse(JSON.stringify(this.dsDoiTuong))
            dsNoShow.forEach(element => {
                element.THONGTIN_DT = element.THONGTIN_DT.split('\r\n')[0],
                element.LOAI_DT = (element.LOAI_DT ==="TRAM_TB" ? 'Trạm thiết bị' : (element.LOAI_DT == "KETCUOI" ? 'Kết cuối' : ''))
            })
            this.dsDoiTuong2 = JSON.parse(JSON.stringify(dsNoShow))
        }
    },
    chon: async function(){
        if  (this.dataChon != null){
            let data = this.dataChon
            this.$emit('chon', data)
            this.$bvModal.hide(this.id)
        }

    },
    loadDanhsachDoituong: async function(list){
        try {
            let data = {
                ds_kc : this.listObj.dsketcuoi,
                ds_tram : this.listObj.dstram
            }
            this.dsDoiTuong = []
            let response = await this.$root.context.post('/web-cabman/mang-truyen-dan/lay-ds-chon-doituong',data)
            if (response.data != null) {
                this.dsDoiTuong = response.data
                this.dsDoiTuong2 =  JSON.parse(JSON.stringify(this.dsDoiTuong))
                this.dsDoiTuong2.forEach(element => {
                    let thongtin = element.THONGTIN_DT != null ? element.THONGTIN_DT.split('\r\n') : []
                    element.THONGTIN_DT = thongtin.length > 0 ? thongtin[0] : element.THONGTIN_DT
                    element.LOAI_DT = (element.LOAI_DT ==="TRAM_TB" ? 'Trạm thiết bị' : (element.LOAI_DT == "KETCUOI" ? 'Kết cuối' : ''))
                })
                this.enableButton = response.data.length > 0
            } else {
                this.dsDoiTuong2 = []
            }
        } catch (error) {
            console.log(error)
            this.$root.toastError(' '+error.response.data.message_detail)
        }
        finally {
        }
    },
    onDoubleClickRow: async function(){
        await this.chon()
    },
    onClickRowTable: async function(data){
        if (data != null || data != undefined){
            this.dataChon.loai_dt = data.LOAI_DT === 'Trạm thiết bị' ? 'TRAM_TB' : 'KETCUOI'
            this.dataChon.doituong_id = data.DOITUONG_ID
        }

    }
  },
  async mounted () {
  },
  async created() {
  }
}
</script>
<style>
    .chon-tram-tbi .show-detail {
       display: grid;
        overflow: hidden;
        line-height: 32px;
        padding: 0 5px;
        border-left: 0;
        border-top: 0;
    }

    .chon-tram-tbi .no-show-detail {
        white-space: normal;
    }

    .disabled {
        pointer-events: none;
        color: #0176FF;
        border-left: 1px solid #BAE7FF;
        opacity: 0.65;
    }

    .table-result td, .table-result th {
        height: auto !important;
    }

    td.show-detail span {
        display: contents;
    }
</style>
