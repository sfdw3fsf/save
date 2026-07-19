<template>
    <b-modal ref="popupTraLHTB" size="lg" hide-header hide-header-close hide-footer body-class="p-0">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">Tick chọn các loại hình bên dưới</div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="popup-body">
                    
                    <div>
                        <ejs-listview 
                            ref='listview'
                            id='sample-list-flat' 
                            :dataSource='dstb' 
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
  name: 'popupTraLHTB',
    components: { 
        "ejs-listview": ListViewComponent
    },
  props: ['vdichvu_id', 'ds_tttb_id'],
  data() {
    return {
      dstb: [
      ],
    }
  },
  created(){
    this.getDsTB();
  },
  watch: {
    ds_tttb_id: async (newval) => {
      await this.getDsTB();
      let tem = this.dstb.filter(e => {
        return !newval.includes(e.id)
      });
      this.dstb = tem;
    }
  },
  mounted() {
    console.log('mount', this.vdichvu_id);
  },
  methods: {
    async getDsTB(){
      try {
        let res = await this.axios.post('/web-quantri/chien-dich-b2a/lay_ds_loaihinh_tb', {
          vdichvu_id: this.vdichvu_id
        });
        if(res.data.data && res.data.data.length){
          this.dstb = res.data.data.map(d => {
            return {
              id: d.dichvuvt_id,
              text: d.ten_dvvt
            }
          });
        }
      } catch (error) {
        console.log(error);
      }
    },
    showModal() {
      this.$refs['popupTraLHTB'].show()
    },
    hideModal() {
      this.$refs['popupTraLHTB'].hide()
    },
    async btnThoat() {
      this.hideModal()
    },
    handle(ls){
        try {
            let e = ls.map(e => {
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
      this.$emit('acceptChangetb', this.handle(items));
      this.hideModal();
    }
  },
  watch: {
    'vdichvu_id': function(newval, oldval){
      console.log('abc', newval, oldval, this.vdichvu_id);
      this.getDsTB();
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
