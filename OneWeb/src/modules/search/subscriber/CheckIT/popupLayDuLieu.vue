<template>

<b-modal
    ref="popupLayDuLieu"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal">
  <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                <span class="icon one-notepad"></span> Lấy dữ liệu
                </div>
                 <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
                <ActionTop :actions="actions"  @onActionClick="onActionClick"/>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="info-row">
                            <div class="item">
                            <div class="check-action">
                                <input  type="radio"  class="check" value="1" v-model="bancheo_id" :checked="bancheo_id === 1" />
                                <span class="name">Rà soát bán chéo</span>
                            </div>
                        </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Trạng thái</div>
                            <div class="value">
                                <div class="select-custom">
                                      <SelectExt v-model="trangthai_selectted" :options="list_trangthai" @select="onchangeTrangThai"/> 
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Lọc theo loại hình</div>

                        <div class="table-content">
                    <k-table :columns="columns_DSLoaiHinhTB" :dataSources="dataSources_ds_loaihinh"
                        @dataCheckeds="checkedDataDaGan" :allowCheckBox="true" :allowFilter="true"
                        :rowSelectedIndex="1" />
                </div>
                       
                    </div>
                </div>
            </div>
  </b-modal>
</template>

<script>
import KTable from "@/components/KTable.vue";
import ActionTop from "@/components/ActionTop.vue";
import API from './API'

export default {
    name:'popupLayDuLieu',
     props:{
    
    },
    components:{
        ActionTop,
        KTable
    },
    data(){
        return{
            actions:[
                {
                    id:'accept',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'one-reload1'
                },
                 {
                    id:'huy',
                    name:'Hủy',
                    active: true,
                    icon_class:'icon nc-icon-glyph ui-1_circle-remove'
                }
            ], 
            columns_DSLoaiHinhTB: [
                    {
                    field: "LOAITB_ID",
                    label: "ID",
                    allowFilter: true,
                    },
                    {
                    field: "LOAIHINH",
                    label: "Loại hình",
                    allowFilter: true,
                    },
                ],
            dataSources_ds_loaihinh: [],
            dataCheckeds_ds_loaihinh:[],
            list_trangthai: [],
            trangthai_selectted: null,
            danhsach:[],
            trangthai_id:null,
            bancheo_id:1,
            file_id:0,
            p_loaihinh:0
        }
    },
    methods:{
       
        onActionClick(action){
            if(action.id=='accept'){
               this.$emit('accept',{ trangthai_selectted: this.trangthai_selectted, dataCheckeds_ds_loaihinh: this.dataCheckeds_ds_loaihinh,bancheo_id: this.bancheo_id, file_id:this.file_id});
               this.hideModal();
            }
            if(action.id=='huy')
            {
                this.load_DS_TrangThai();
                this.load_ds_loaihinh(); 
            }
        },
        showModal() {
            this.$refs["popupLayDuLieu"].show()
        },
        hideModal() {
            this.$refs["popupLayDuLieu"].hide()
        },
        handleShowModal(){
            this.list_trangthai=[]
            this.trangthai_selectted=null
            this.load_DS_TrangThai();
            this.load_ds_loaihinh();
           
        },
        async load_DS_TrangThai() {
         let res = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "DOISOATIT_TRANGTHAI",
                          "p_para1": null,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.ID, text: item.NAME });
                    });
                        this.list_trangthai = items;
                        this.trangthai_selectted = items[0].id;
                    }
              }
       
    }, 
    checkedDataDaGan(dataItem) {
      this.dataCheckeds_ds_loaihinh = dataItem;
    },
    async load_ds_loaihinh( ) {
        this.dataSources_ds_loaihinh = [];
        this.loading(true);
        try {
            const response = await this.axios.post("/web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                            "p_id": "DOISOATIT_LOAIHINH_TB",
                            "p_para1": null,
                            "p_para2": null,
                            "p_para3": null,
                            "p_para4": null,
                            "p_para5": null,});
            if ( response.data.error_code === "BSS-00000000" && response.data.data !== undefined ) {
                    this.dataSources_ds_loaihinh = response.data.data;
            }
        } catch (error) {
            console.log(error);
        } finally {
            this.loading(false);
        }
      
    },
    
        onchangeTrangThai(agrs){
            this.trangthai_selectted=agrs.id;
              if(agrs.id==2)
              {
                this.file_id=435
              }else
              {
                this.file_id=126
              }
               
            },
        
    }
};
</script>

