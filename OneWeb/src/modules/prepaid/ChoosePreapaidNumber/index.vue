<template>
  <div >
    <breadcrumb :header="header"/>
    <div style="overflow-y:auto;height: 600px;">
      <popupChonSoKH ref="popupChonSoKH" :dulieu="dulieu"/>
    </div>

  </div>

</template>

<script>
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import popupChonSoKH from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonSoKH.vue"
import breadcrumb from '@/components/breadcrumb'
export default {
  components:{
    popupChonSoKH,
    breadcrumb,
  },
  data: function () {
    return {
      dulieu:{
        vkieuform: 1,
      },
      Tag:1,
      header: {
        title: 'Chọn số trả trước',
        list: [
          { name: ' Phát triển thuê bao', link: { name: 'Ui.cards' } },
          {
            name: ' Chọn số trả trước',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
    }
  },
  methods:{

  },
  mounted(){

  },
  created() {
    if (this.$route.name.toLowerCase() == 'choosepreapaidnumber'.toLowerCase()) {
      this.Tag = '0' // Tra truoc
    } else if (this.$route.name.toLowerCase() == 'chosenumberinprovincewarehouse'.toLowerCase()) {
      this.Tag = '1' // Tra sau
    } else if (this.$route.name.toLowerCase() == '') {
      // alert(this.$route.name)
      //this.$router.push('choosepreapaidnumber')
      this.Tag = '0' //  Tra truoc
    } else {
      //alert(this.$route.name)
    }
    if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
      this.Tag = this.$route.query.tag
    }
    this.dulieu.vkieuform = this.Tag;
    console.log(this.Tag);
  }
}
</script>

<style scoped>

</style>
