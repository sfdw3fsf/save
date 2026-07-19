<template>
    <b-modal ref="popupTraCuuDV" size="lg" hide-header hide-header-close hide-footer body-class="p-0">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">Chọn đơn vị</div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>

            <div class="popup-body">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w110">Tick chọn các đơn vị bên dưới</div>
                    </div>
                    <div>
                        <ejs-listview 
                            ref='listview'
                            id='sample-list-flat' 
                            :dataSource='JS_TINH.list' 
                            showCheckBox='true'>
                        </ejs-listview>
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
        </div>
    </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
// import ActionTop from "@/components/ActionTop.vue";
// import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
import { ListViewComponent } from "@syncfusion/ej2-vue-lists";
import store from "../../store";

export default {
  name: 'popupTraCuuDV',
  components: { 
      "ejs-listview": ListViewComponent
    },
  created(){
    console.log('created popupdv');
    this.getDsTinh();
  },
  props: ['exLs'],
  data() {
    return {
      JS_TINH: {
        list: [
            // { text: 'Hennessey Venom', id: 'list-01' }
            
        ]
      },
      selectedItem: 0
    }
  },
  watch: {
    exLs(newval) {
      console.log('props change', newval);
      let temArr = store.state.DS_DONVI;
      this.JS_TINH.list = temArr.filter(e => {
        return !newval.includes(e.id)
      });
    }
  },
  mounted() {
    console.log('mounted popupdv');
  },
  methods: {
    async getDsTinh(){
      try {
          let res = await this.axios.post('/web-quantri/chien-dich-b2a/lay_ds_tinh', {
              v_kieu: 22
          });
          let ds_donvi = res.data.data.map(d => {
              return {
                  id: d.tinh_id,
                  text: d.tentinh
              }
          });
          this.JS_TINH.list = ds_donvi;
          console.log('chuan bi luu vao store');
          await store.commit("SET_DS_DONVI",ds_donvi);
      } catch (error) {
          console.log(error);
      }
    },
    showModal() {
      this.$refs['popupTraCuuDV'].show()
    },
    hideModal() {
      this.$refs['popupTraCuuDV'].hide()
    },
    async btnThoat() {
      this.hideModal()
    },
    handleDV(lsDV){
        try {
            let e = lsDV.map(e => {
                return{
                    id: e.id,
                    name: e.text
                }
            });
            return e;
        } catch (error) {
            console.log(error);
        }
    },
    btnXacNhan() {
        let items = this.$refs.listview.getSelectedItems()['data'];
        // console.log(this.handleDV(items));
        
    //   if (this.txtAcount.trim() == '') {
    //     this.$toast.error('Vui lòng nhập Account')
    //     this.$refs.inputAccount.focus()
    //     return
    //   }
        this.$emit('acceptChange', this.handleDV(items))
        this.hideModal();
    }
  }
}
</script>
<style scoped>
#sample-list-flat .e-list-parent .e-list-item .e-text-content .e-list-text{
    padding: 10px
}
#sample-list-flat{
    overflow-y: scroll;
    max-height: 300px;
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
