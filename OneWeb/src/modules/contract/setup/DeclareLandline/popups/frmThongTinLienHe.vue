<template>
    <ejs-dialog :enableResize='true' :visible="false" ref="thongtinlienhe" :header='"Thông tin liên hệ"'
      showCloseIcon=true target=".main-wrapper" width='60%' height="800">
      <ThongTinLienHe ref="ThongTinLienHe" :hdkh_id="hdkh_id" :khachhang_id="0"/>
    </ejs-dialog>
</template>
<script>
import  ThongTinLienHe from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
export default {
    data() {
      return {
          hdkh_id: 0
      }  
    },
    components: {
        ThongTinLienHe
    },
    methods: {
        show() {
            this.$refs.thongtinlienhe.show()
        }
    }
}
</script>