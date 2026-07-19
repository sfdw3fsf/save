<template src="./index.html">
</template>

<script>
import ThongTinTram from './ThongTinTram'
import QuanLyODF from './QuanLyODF'
import QuanLyMDF from './QuanLyMDF'
import QuanLySplitter from './QuanLySplitter'
import QuanLyCEx from './QuanLyCEx'

export default {
    props: {
        tramTb_id: Number,
        id: String
    },
    data() {
        return {
            tab1: true,
            tab2: false,
            tab3: false,
            tab4: false,
            tab5: false,
            tramTb: {},
        }
    },
    components: {
        ThongTinTram,
        QuanLyODF,
        QuanLyMDF,
        QuanLySplitter,
        QuanLyCEx
    },
    methods: {
        show: function(){
            this.$refs.modalTramTBI.show();
        },
        showModal: function() {
            // this.$bvModal.show(this.id);
            this.setTabs(true, false, false, false, false);
        },
        closeModal: function() {
            this.$bvModal.hide(this.id)
        },
        setTabs: function(isTab1, isTab2, isTab3, isTab4, isTab5) {
            this.tab1 = isTab1;
            this.tab2 = isTab2;
            this.tab3 = isTab3;
            this.tab4 = isTab4;
            this.tab5 = isTab5;
        },
        clickTab: function(data) {
            if(data == 1) {
                this.setTabs(true, false, false, false, false);
            }
            else if(data == 2) {
                this.setTabs(false, true, false, false, false);
            }
            else if(data == 3) {
                this.setTabs(false, false, true, false, false);
            }
            else if(data == 4) {
                this.setTabs(false, false, false, true, false);
            }
            else if(data == 5) {
                this.setTabs(false,false, false, false, true);
            }
        },
        showModalTTT: async function() {
            try {
                this.loading(true);
                if(this.tramTb_id) {
                    let rs = await this.$root.context.get(`/web-cabman/mang-truyen-dan/lay-tt-tramtbi-by-id/${this.tramTb_id}`);
                    this.tramTb = rs.data.length > 0 ? rs.data[0] : {};
                    this.setTabs(true, false, false, false, false);
                }
            } catch (error) {

            } finally {
                this.loading(false);
            }
        },
      layToaDoMoi(data){
          this.$emit("isOk", data)
      }
    }
}
</script>
<style>
#popupTramThietBi___BV_modal_content_ #popupTramThietBi___BV_modal_body_ {
    padding: 0px;
}
</style>
