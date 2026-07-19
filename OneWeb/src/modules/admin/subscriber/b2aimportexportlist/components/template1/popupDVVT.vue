<template>
    <b-modal ref="popupDVVT" size="lg" hide-header hide-header-close hide-footer body-class="p-0">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">Dịch vụ viễn thông</div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>

            <div class="popup-body">
                    
                    <div id='container' style="overflow-y: scroll;max-height: 30em;">
                        <ejs-listbox ref='listbox' id='sample-list-flat'  :dataSource='dsdvvt' ></ejs-listbox>
                    </div>
                    <div class="group-buttons -bottom center">
                        <button class="btn btn-outline-primary" @click="btnXacNhan">
                            Xác nhận
                        </button>
                        <button class="btn btn-outline-danger" @click="btnThoat">
                            Hủy bỏ
                        </button>
                    </div>
                </div>
        </div>
    </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
// import ActionTop from "@/components/ActionTop.vue";
// import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
import { ListBoxComponent } from "@syncfusion/ej2-vue-dropdowns";

export default {
  name: 'popupDVVT',
    components: { 
        "ejs-listbox": ListBoxComponent
    },
  // props: ['dsdvvt'],
  data() {
    return {
      dsdvvt: [],
    }
  },
  created(){
    // console.log('credsated', this.dsdvvt);
    // this.JS_LHTB.list = this.dsdvvt
    this.getDsDVVT();
  },
  mounted() {
    console.log('mount');
  },
  methods: {
    async getDsDVVT(){
      try {
        let res = await this.axios.post('/web-quantri/chien-dich-b2a/lay_ds_dichvu_vt', {
          v_kieu: 23
        });
        this.dsdvvt = res.data.data.map(d => {
          return {
            id: d.dichvuvt_id,
            text: d.ten_dvvt
          }
        });
        // console.log(this.DS_DVVT);
      } catch (error) {
        console.log(error);
      }
    },
    showModal() {
      this.$refs['popupDVVT'].show()
    },
    hideModal() {
      this.$refs['popupDVVT'].hide()
    },
    async btnThoat() {
      this.hideModal()
    },
    btnXacNhan() {
        // var listboxobj= document.getElementById('sample-list-flat').ej2_instances[0];
        // console.log(listboxobj.selectItems());
        // let items = this.$refs.listbox.selectItems();
        console.log(this.$refs.listbox.ej2Instances.prevSelIdx);
        // console.log(this.$refs.listbox.selectItems());
        // console.log(this.$refs.listbox.getSelectedItems());
        // console.log(this.$refs.listbox.getDataByValues());
        
    //   if (this.txtAcount.trim() == '') {
    //     this.$toast.error('Vui lòng nhập Account')
    //     this.$refs.inputAccount.focus()
    //     return
    //   }
      let idx = this.$refs.listbox.ej2Instances.prevSelIdx;
      this.$emit('acceptChange', this.dsdvvt[idx]);
      this.hideModal();
    }
  }
}
</script>
<style scoped>

#sample-list-flat{
    overflow-y: scroll;
    max-height: auto;
}
/* width */
::-webkit-scrollbar {
  width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px grey; 
  border-radius: 10px;
}
 
/* Handle */
::-webkit-scrollbar-thumb {
  background: #6e6b6b; 
  border-radius: 10px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #9d9797; 
}
</style>
