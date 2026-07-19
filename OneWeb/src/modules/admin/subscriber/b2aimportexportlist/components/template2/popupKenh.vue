<template>
    <b-modal ref="popupTraCuuKenhNV" size="lg" hide-header hide-header-close hide-footer body-class="p-0">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">Chọn đơn vị</div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>

            <div class="popup-body">
                <div class="box-form">
                  <div class="group-buttons -bottom center">
                      <button class="btn btn-outline-primary" @click="btnXacNhan">
                          Xác nhận
                      </button>
                      <button class="btn btn-outline-danger" @click="btnThoat">
                          Hủy bỏ
                      </button>
                  </div>
                  <div class="legend-title">Chọn loại nhân viên</div>
                  <!-- table -->
                  <DataGrid 
                      ref="danh_sach_nhan_vien" 
                      :showColumnCheckbox="true"
                      :showFilter="true" 
                      :allowPaging="false" 
                      :enablePagingServer="false"
                      :dataSource="JS_KENH.list" 
                      :columns="JS_KENH.columns" 
                      @selectedItemsChanged="selectedNV"
                  />
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
// import ActionTop from "@/components/ActionTop.vue";
// import breadcrumb from "@/components/breadcrumb";
import store from "../../store";

export default {
  name: 'popupTraCuuKenhNV',
  props: ['ls_kenh_id'],
  created(){
    this.initData();
  },
  data() {
    return {
      JS_KENH: {
        list: [
        ],
        columns: [
            {
                headerText: '',
                fieldName: 'name',
            }
        ]
      },
      selectedItem: []
    }
  },
  watch: {
    ls_kenh_id(newval) {
      console.log('props change', newval);
      let temArr = store.state.DS_TV_KENH;
      this.JS_TINH.list = temArr.filter(e => {
        return !newval.includes(e.id)
      });
    }
  },
  mounted() {
    console.log('mounted popupdv');
  },
  methods: {
    async initData(){
      try {
        let res = await this.axios.post('/web-quantri/chien-dich-b2a/lay_ds_loainv', {
          v_kieu: 25
        });
        this.JS_KENH.list = res.data.data.map(i => {
          return{
            id: i.loainv_id,
            name: i.ten
          }
        });
        console.log('chuan bi luu vao store');
          await store.commit("SET_DS_TV_KENH", this.JS_KENH.list);
      } catch (error) {
        console.log(error);
      }
    },
    selectedNV(e){
      // this.handleKENH(e);
      console.log(e);
      this.selectedItem = e;
    },
    showModal() {
      this.$refs['popupTraCuuKenhNV'].show()
    },
    hideModal() {
      this.$refs['popupTraCuuKenhNV'].hide()
    },
    async btnThoat() {
      this.hideModal()
    },
    // handle(ls){
    //     try {
    //         let e = ls.map(e => {
    //             return{
    //                 id: e.loainv_id,
    //                 name: e.ten
    //             }
    //         });
    //         return e;
    //     } catch (error) {
    //         console.log(error);
    //     }
    // },
    btnXacNhan() {
        this.hideModal();
        this.$emit('acceptChange', this.selectedItem)
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
