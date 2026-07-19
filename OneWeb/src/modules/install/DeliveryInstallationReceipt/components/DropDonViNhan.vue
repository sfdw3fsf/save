<template>
  <div>
    <SelectExt v-model="cbodsdvn.value" :disabled="false" placeholder="Chọn kiểu tình trạng" :dataSource="cbodsdvn.data" dataTextField="ten_dv"
               dataValueField="donvi_nhan_id"></SelectExt>

  </div>

</template>

<script>
// import api from '../api'

export default {
  name: 'DropDonViNhan',
  data () {
    return {
      data: {

      },
      cbodsdvn: {
        data: [],
        value: 0
      },
      selected:null,
    }
  },
  computed: {
    parent: function () {
      return this.$parent.$parent.$parent
    }
  },
  watch: {
    'cbodsdvn.value': function (newValue) {
      this.parent.receiverData(newValue, this.data)
    }
  },
  mounted () {
    this.sp_frmgiaophieuhd_lay_donvi();

  },
  methods: {
    // async sp_frmgiaophieuhd_lay_donvi(){
    //   let res = await api.sp_frmgiaophieuhd_lay_donvi(this.axios,{
    //     "hdkh_id": this.data.hdkh_id,
    //   });
    //   if(res.data.error_code === "BSS-00000000" && res.data.data){
    //     this.cbodsdvn.data = res.data.data;
    //     if(this.data.donvi_id){
    //         this.cbodsdvn.value = this.data.donvi_id;
    //     }
    //     else{
    //       this.cbodsdvn.value = this.cbodsdvn.data[0].donvi_id;
    //     }
    //
    //   }
    // },

    sp_frmgiaophieuhd_lay_donvi() {
      this.cbodsdvn.data = this.parent.dv_nhan;
      this.cbodsdvn.value = this.cbodsdvn.data[0].donvi_nhan_id;
    },

  }
}
</script>

