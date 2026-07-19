<template>
  <div class="col-sm-6 col-12">
    <div class="box-form">
      <div class="legend-title">Thông tin địa danh</div>
      <div class="row">
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w30 nowrap">Tỉnh/TP</div>
            <div class="value">
                <div class="select-custom" ref="ds_tinh">
                    <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_tinh'] }" ref="cboTinh"
                        v-model="tinh_selected" class="select2"
                        :options="danhSachTinh.map(e=> ({id: e.tinh_id, text: e.tentinh}))"
                        @change="changeTinh">
                    </select2>
                </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w30 nowrap">Quận</div>
            <div class="value">
                <div class="select-custom" ref="ds_quan">
                    <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_quan'] }" ref="cboQuan"
                        v-model="quan_selected" class="select2"
                        :options="danhSachQuan.map(e=> ({id: e.ID, text: e.NAME}))"
                        @change="changeQuan">
                    </select2>
                </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w30 nowrap">Phường</div>
            <div class="value">
                <div class="select-custom" ref="ds_phuong">
                    <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_phuong'] }" ref="cboPhuong"
                        v-model="phuong_selected" class="select2"
                        :options="danhSachPhuong.map(e=> ({id: e.PHUONG_ID, text: e.TEN_PHUONG}))"
                        @change="changePhuong">
                    </select2>
                </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-move-select-table">

        <div class="box-col">
          <div class="title-bg-main fw6 mart10">Danh sách phố đã gán</div>
          
            <KTableV3 :columns="columnsPho_Gan"
              :dataSources="dataSourcesPho_Gan"
              :allowFilter="allowFilters.pho"
              :allowCheckBox="true"
              :isShowPagination="false"
              id="pho_id"
              :tableHeight="dataSourcesPho_Gan.length>0?'140px':'auto'"
              @onSelectedRow="selectedRowPhoGan"
              @dataCheckeds="onDataCheckedsPhoGan"/>
        </div>
        <div class="actions">
          <button class="btn" @click="onClickButtonFilterPho">
            <span class="nc-icon-outline ui-2_filter"></span>
          </button>
          <button class="btn" @click="boGanDanhSachPho">
            <span class="fa fa-angle-right"></span>
          </button>

          <button class="btn" @click="ganDanhSachPho">
            <span class="fa fa-angle-left"></span>
          </button>
        </div>

        <div class="box-col">
          <div class="title-bg-main fw6 mart10">Danh sách phố chưa gán</div>
          <KTableV3 :columns="columnsPho_ChuaGan"
              :dataSources="dataSourcesPho_ChuaGan"
              :allowFilter="allowFilters.pho"
              :allowCheckBox="true"
              :isShowPagination="false"
              id="pho_id"
              :tableHeight="dataSourcesPho_ChuaGan.length>0?'140px':'auto'"
              @dataCheckeds="onDataCheckedsPhoChuaGan"/>
        </div>

      </div>


      <div class="box-move-select-table">

        <div class="box-col">
          <div class="title-bg-main fw6 mart10">Danh sách ấp đã gán</div>
          <KTableV3 :columns="columnsAp_Gan"
            :dataSources="dataSourcesAp_Gan"
            :allowFilter="allowFilters.ap"
            :allowCheckBox="true"
            :isShowPagination="false"
            id="pho_id"
            :tableHeight="dataSourcesAp_Gan.length>0?'140px':'auto'"
            @onSelectedRow="selectedRowApGan"
            @dataCheckeds="onDataCheckedsApGan"/>
        </div>

        <div class="actions">
          <button class="btn" @click="onClickButtonFilterAp">
            <span class="nc-icon-outline ui-2_filter"></span>
          </button>
          <button class="btn" @click="boGanDanhSachAp">
            <span class="fa fa-angle-right"></span>
          </button>

          <button class="btn" @click="ganDanhSachAp">
            <span class="fa fa-angle-left"></span>
          </button>
        </div>
        <div class="box-col">
          <div class="title-bg-main fw6 mart10">Danh sách ấp chưa gán</div>
          <KTableV3 :columns="columnsAp_ChuaGan"
              :dataSources="dataSourcesAp_ChuaGan"
              :allowFilter="allowFilters.ap"
              :isShowPagination="false"
              :allowCheckBox="true"
              id="pho_id"
              :tableHeight="dataSourcesAp_ChuaGan.length>0?'140px':'auto'"
              @dataCheckeds="onDataCheckedsApChuaGan"/>
        </div>
      </div>

      <div class="box-move-select-table">
        <div class="box-col">
          <div class="title-bg-main fw6 mart10">Danh sách khu đã gán</div>
          <KTableV3 :columns="columnsKhu_Gan"
            :dataSources="dataSourcesKhu_Gan"
            :allowFilter="true"
            :allowCheckBox="true"
            :isShowPagination="false"
            id="pho_id"
            :tableHeight="dataSourcesKhu_Gan.length>0?'140px':'auto'"
            @onSelectedRow="selectedRowKhuGan"
            @dataCheckeds="onDataCheckedsKhuGan"/>
        </div>
        <div class="actions">
          <button class="btn" @click="boGanDanhSachKhu">
            <span class="fa fa-angle-right" ></span>
          </button>

          <button class="btn" @click="ganDanhSachKhu">
            <span class="fa fa-angle-left"></span>
          </button>
        </div>
        <div class="box-col">
          <div class="title-bg-main fw6 mart10">Danh sách khu chưa gán</div>
          <KTableV3 :columns="columnsKhu_ChuaGan"
              :dataSources="dataSourcesKhu_ChuaGan"
              :allowFilter="true"
              :allowCheckBox="true"
              :isShowPagination="false"
              id="pho_id"
              :tableHeight="dataSourcesKhu_ChuaGan.length>0?'140px':'auto'"
              @dataCheckeds="onDataCheckedsKhuChuaGan"/>
        </div>
      </div>


      <div class="box-move-select-table">
        <div class="box-col">
          <div class="title-bg-main fw6 mart10">Danh sách đặc điểm đã gán</div>
          <KTableV3 :columns="columnsDacDiem_Gan"
              :dataSources="dataSourcesDacDiem_Gan"
              :allowFilter="true"
              :allowCheckBox="true"
              :isShowPagination="false"
              id="dacdiem_id"
              :tableHeight="dataSourcesDacDiem_Gan.length>0?'140px':'auto'"
              @dataCheckeds="onDataCheckedsDacDiemGan"/>
        </div>
        <div class="actions">
          <button class="btn" @click="boGanDanhSachDacDiem">
            <span class="fa fa-angle-right"></span>
          </button>

          <button class="btn" @click="ganDanhSachDacDiem">
            <span class="fa fa-angle-left"></span>
          </button>
        </div>
        <div class="box-col">
          <div class="title-bg-main fw6 mart10">
            Danh sách đặc điểm chưa gán
          </div>
          <KTableV3 :columns="columnsDacDiem_ChuaGan"
              :dataSources="dataSourcesDacDiem_ChuaGan"
              :allowFilter="true"
              :allowCheckBox="true"
              :isShowPagination="false"
              id="dacdiem_id"
              :tableHeight="dataSourcesDacDiem_ChuaGan.length>0?'140px':'auto'"
              @dataCheckeds="onDataCheckedsDacDiemChuaGan"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import DepartmentAreaAPI from './DepartmentAreaAPI'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
export default {
    name:'ThongTinDiaDanh',
    components:{
      KTableV3
    },
    data(){
      return {
        columnsPho_Gan:[
          {
              field: "ten_pho",
              label: "Tên phố",
              allowFilter: true
          },
          {
              field: "ten_phuong",
              label: "Tên phường",
              allowFilter: true
          }
        ],
        dataSourcesPho_Gan:[],
        columnsPho_ChuaGan: [
          {
              field: "ten_pho",
              label: "Tên phố",
              allowFilter: true
          },
        ],
        dataSourcesPho_ChuaGan:[],
        columnsAp_Gan:[
          {
            field:'ten_pho',
            label:'Tên Ấp',
            allowFilter:true
          },
          {
            field:'ten_phuong',
            label:'Tên Phường',
            allowFilter:true
          }
        ],
        dataSourcesAp_Gan:[],
        columnsAp_ChuaGan:[
          {
            field:'ten_pho',
            label:'Tên Ấp',
            allowFilter:true
          }
        ],
        dataSourcesAp_ChuaGan:[],
        columnsKhu_Gan:[
          {
            field:'ten_pho',
            label:'Tên Khu',
            allowFilter:true
          },
          {
            field:'ten_phuong',
            label:'Tên Phường',
            allowFilter:true
          }
        ],
        dataSourcesKhu_Gan:[],
        columnsKhu_ChuaGan:[
          {
            field:'ten_pho',
            label:'Tên Khu',
            allowFilter:true
          }
         
        ],
        dataSourcesKhu_ChuaGan:[],
        columnsDacDiem_Gan:[
          {
            field:'dacdiem',
            label:'Đặc điểm',
            allowFilter:true
          },
          {
            field:'ten_pho',
            label:'Phố/Ấp/Khu',
            allowFilter:true
          }
        ],
        dataSourcesDacDiem_Gan:[],
        columnsDacDiem_ChuaGan:[
          {
            field:'dacdiem',
            label:'Đặc điểm',
            allowFilter:true
          },
          {
            field:'ten_pho',
            label:'Phố/Ấp/Khu',
            allowFilter:true
          }
        ],
        dataSourcesDacDiem_ChuaGan:[],
        tinh_selected:null,
        danhSachTinh:[],
        quan_selected:null,
        danhSachQuan:[],
        phuong_selected:null,
        danhSachPhuong:[],

        dataCheckBoxPhoGan:[],
        dataCheckBoxPhoChuaGan:[],
        dataCheckBoxApGan:[],
        dataCheckBoxApChuaGan:[],
        dataCheckBoxKhuGan:[],
        dataCheckBoxKhuChuaGan:[],
        dataCheckBoxDacDiemGan:[],
        dataCheckBoxDacDiemChuaGan:[],
        allowFilters:{
          pho:true,
          ap:true
        },
      }
    },
    methods:{
      async layDanhSachTinh(){
        try{
          let response = await DepartmentAreaAPI.lay_danhsach_tinh(this.axios)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachTinh=response.data.data
            if(this.danhSachTinh.length>0){
              //tỉnh lấy từ token
              this.tinh_selected=this.$root.token.getPhanVungID()
              //this.tinh_selected=this.danhSachTinh[0].tinh_id
              this.layDanhSachQuanTheoTinh(this.tinh_selected)
            }
          }else{
            this.danhSachTinh=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách tỉnh')
        }      
      },
      async layDanhSachQuanTheoTinh(tinh_id){
        try{
          let response = await DepartmentAreaAPI.lay_danhsach_quan_theo_tinh(this.axios, tinh_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachQuan=response.data.data
            if(this.danhSachQuan.length>0){
              this.quan_selected=this.danhSachQuan[0].ID
              this.layDanhSachPhuongTheoQuan(this.quan_selected)
            }
          }else{
            this.danhSachQuan=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách quận/huyện')
        } 
      },
      async layDanhSachPhuongTheoQuan(quan_id){
        try{
          let response = await DepartmentAreaAPI.lay_danhsach_phuong_theo_quan(this.axios, quan_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.danhSachPhuong=response.data.data
            if(this.danhSachPhuong.length>0){
              this.phuong_selected=this.danhSachPhuong[0].PHUONG_ID
              this.lay_ds_pho_ap_khu(this.phuong_selected)
            }
          }else{
            this.danhSachPhuong=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách phường')
        } 
      },

      async lay_danhsach_duong_pho_chua_gan(phuong_id){
        try{
          let response = await DepartmentAreaAPI.lay_danhsach_duong_pho_chua_gan(this.axios, phuong_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            //this.dataSourcesPho_ChuaGan=response.data.data
            const quan=this.danhSachQuan.find(x=>x.ID==this.quan_selected)
            response.data.data.forEach((item)=>{
              this.dataSourcesPho_ChuaGan.push(Object.assign(item,{
                  quan_id:quan.ID,
                  ten_quan:quan.NAME
              }))
            })
            // model data response
            // {
            //   "pho_id": 77217,
            //   "ten_pho": "Chung Cư C3",
            //   "phuong_id": 4295,
            //   "ten_phuong": "Phường 06"
            // }
          }else{
            this.dataSourcesPho_ChuaGan=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách phố')
        } 
      },
      async lay_danhsach_ap_chua_gan(phuong_id){
        try{
          let response = await DepartmentAreaAPI.lay_danhsach_ap_chua_gan(this.axios, phuong_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            //this.dataSourcesAp_ChuaGan=response.data.data
            const quan=this.danhSachQuan.find(x=>x.ID==this.quan_selected)
            response.data.data.forEach((item)=>{
              this.dataSourcesAp_ChuaGan.push(Object.assign(item,{
                  quan_id:quan.ID,
                  ten_quan:quan.NAME
              }))
            })
            // model data response
            // {
            //   "pho_id": 77217,
            //   "ten_pho": "Chung Cư C3",
            //   "phuong_id": 4295,
            //   "ten_phuong": "Phường 06"
            // }
          }else{
            this.dataSourcesAp_ChuaGan=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách ấp')
        } 
      },
      async lay_danhsach_khu_chua_gan(phuong_id){
        try{
          let response = await DepartmentAreaAPI.lay_danhsach_khu_chua_gan(this.axios, phuong_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            //this.dataSourcesKhu_ChuaGan=response.data.data
            const quan=this.danhSachQuan.find(x=>x.ID==this.quan_selected)
            response.data.data.forEach((item)=>{
              this.dataSourcesKhu_ChuaGan.push(Object.assign(item,{
                  quan_id:quan.ID,
                  ten_quan:quan.NAME
              }))
            })
            // model data response
            // {
            //   "pho_id": 77217,
            //   "ten_pho": "Chung Cư C3",
            //   "phuong_id": 4295,
            //   "ten_phuong": "Phường 06"
            // }
           
          }else{
            this.dataSourcesKhu_ChuaGan=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách khu')
        } 
      },

      async lay_danhsach_dacdiem_chua_gan(pho_id, phuong_id){
        try{
          let response = await DepartmentAreaAPI.lay_danhsach_dacdiem_chua_gan(this.axios, pho_id, phuong_id)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            this.dataSourcesDacDiem_ChuaGan=response.data.data
            // model data response
            // {
            //   "dacdiem_id": 24,
            //   "dacdiem": "Hẻm 117",
            //   "pho_id": 1532,
            //   "ten_pho": "Khu Sân Đúc"
            // }
          }else{
            this.dataSourcesDacDiem_ChuaGan=[]
          }
        }catch(e){
          this.$toast.error('Không load được danh sách đặc điểm')
        } 
      },

      lay_ds_pho_ap_khu(phuong_id){
        this.dataSourcesPho_ChuaGan=[]
        this.dataSourcesAp_ChuaGan=[]
        this.dataSourcesKhu_ChuaGan=[]
        this.dataSourcesDacDiem_ChuaGan=[]
        this.dataSourcesPho_Gan=[]
        this.dataSourcesAp_Gan=[]
        this.dataSourcesKhu_Gan=[]
        this.dataSourcesDacDiem_Gan=[]
        this.lay_danhsach_duong_pho_chua_gan(phuong_id)
        this.lay_danhsach_ap_chua_gan(phuong_id)
        this.lay_danhsach_khu_chua_gan(phuong_id)
      },
      changeTinh(){
        this.layDanhSachQuanTheoTinh(this.tinh_selected)
      },
      changeQuan(){
        this.layDanhSachPhuongTheoQuan(this.quan_selected)
      },
      changePhuong(){
        this.lay_ds_pho_ap_khu(this.phuong_selected)
      },
      onDataCheckedsPhoGan(data){
        this.dataCheckBoxPhoGan=data;
      },
      onDataCheckedsPhoChuaGan(data){
        console.log("size: "+data.length)
        this.dataCheckBoxPhoChuaGan=data
      },
      onDataCheckedsApGan(data){
        this.dataCheckBoxApGan=data
      },
      onDataCheckedsApChuaGan(data){
        this.dataCheckBoxApChuaGan=data
      },
      onDataCheckedsKhuGan(data){
        this.dataCheckBoxKhuGan=data
      },
      onDataCheckedsKhuChuaGan(data){
        this.dataCheckBoxKhuChuaGan=data
      },
      onDataCheckedsDacDiemGan(data){
        this.dataCheckBoxDacDiemGan=data
      },
      onDataCheckedsDacDiemChuaGan(data){
        this.dataCheckBoxDacDiemChuaGan=data
      },
      boGanDanhSachPho(){
        if(this.dataCheckBoxPhoGan&&this.dataCheckBoxPhoGan.length>0){
          this.dataCheckBoxPhoGan.forEach((item)=>{
            //kiểm tra tồn tại
            const indexCheck=this.dataSourcesPho_ChuaGan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
            if(indexCheck<0){
              this.dataSourcesPho_ChuaGan.push(item)
            }

             const index = this.dataSourcesPho_Gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
             if (index > -1) {
               this.dataSourcesPho_Gan.splice(index, 1);
             }
          })
          this.dataCheckBoxPhoGan=[]
          this.dataCheckBoxPhoChuaGan=[]   
        }
      },
      ganDanhSachPho(){
        if(this.dataCheckBoxPhoChuaGan&&this.dataCheckBoxPhoChuaGan.length>0){
          this.dataCheckBoxPhoChuaGan.forEach((item)=>{
            //kiểm tra tồn tại
            const indexCheck=this.dataSourcesPho_Gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
            if(indexCheck<0){
              this.dataSourcesPho_Gan.push(item)
            }
            const index=this.dataSourcesPho_ChuaGan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
             if (index > -1) {
               this.dataSourcesPho_ChuaGan.splice(index, 1);
             }
          })
          this.dataCheckBoxPhoGan=[]
          this.dataCheckBoxPhoChuaGan=[]  
        }
      },
      boGanDanhSachAp(){
        if(this.dataCheckBoxApGan&&this.dataCheckBoxApGan.length>0){
          this.dataCheckBoxApGan.forEach((item)=>{
            //kiểm tra tồn tại
            const indexCheck=this.dataSourcesAp_ChuaGan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
            if(indexCheck<0){
              this.dataSourcesAp_ChuaGan.push(item)
            }

             const index = this.dataSourcesAp_Gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
             if (index > -1) {
               this.dataSourcesAp_Gan.splice(index, 1);
             }
          })
          this.dataCheckBoxApGan=[]
          this.dataCheckBoxApChuaGan=[]   
        }
      },
      ganDanhSachAp(){
        if(this.dataCheckBoxApChuaGan&&this.dataCheckBoxApChuaGan.length>0){
          this.dataCheckBoxApChuaGan.forEach((item)=>{
            //kiểm tra tồn tại
            const indexCheck=this.dataSourcesAp_Gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
            if(indexCheck<0){
              this.dataSourcesAp_Gan.push(item)
            }

            const index=this.dataSourcesAp_ChuaGan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
             if (index > -1) {
               this.dataSourcesAp_ChuaGan.splice(index, 1);
             }
          })
          this.dataCheckBoxApGan=[]
          this.dataCheckBoxApChuaGan=[]     
        }
      },
      boGanDanhSachKhu(){
        if(this.dataCheckBoxKhuGan&&this.dataCheckBoxKhuGan.length>0){
          this.dataCheckBoxKhuGan.forEach((item)=>{
            //kiểm tra tồn tại
            const indexCheck=this.dataSourcesKhu_ChuaGan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
            if(indexCheck<0){
              this.dataSourcesKhu_ChuaGan.push(item)
            }

             const index = this.dataSourcesKhu_Gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
             if (index > -1) {
               this.dataSourcesKhu_Gan.splice(index, 1);
             }
          })
          this.dataCheckBoxKhuGan=[]
          this.dataCheckBoxKhuChuaGan=[]   
        }
      },
      ganDanhSachKhu(){
        if(this.dataCheckBoxKhuChuaGan&&this.dataCheckBoxKhuChuaGan.length>0){
          this.dataCheckBoxKhuChuaGan.forEach((item)=>{
            //kiểm tra tồn tại
            const indexCheck=this.dataSourcesKhu_Gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
            if(indexCheck<0){
              this.dataSourcesKhu_Gan.push(item)
            }

            const index=this.dataSourcesKhu_ChuaGan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
             if (index > -1) {
               this.dataSourcesKhu_ChuaGan.splice(index, 1);
             }
          })
          this.dataCheckBoxKhuGan=[]
          this.dataCheckBoxKhuChuaGan=[]    
        }
      },
      boGanDanhSachDacDiem(){
        if(this.dataCheckBoxDacDiemGan&&this.dataCheckBoxDacDiemGan.length>0){
         
          this.dataCheckBoxDacDiemGan.forEach((item)=>{
            //kiểm tra tồn tại
            const indexCheck=this.dataSourcesDacDiem_ChuaGan.findIndex((i)=>i.pho_id==item.pho_id&&i.dacdiem_id==item.dacdiem_id)
            if(indexCheck<0){
              this.dataSourcesDacDiem_ChuaGan.push(item)
            }

             const index = this.dataSourcesDacDiem_Gan.findIndex((i)=>i.pho_id==item.pho_id&&i.dacdiem_id==item.dacdiem_id)
             if (index > -1) {
               this.dataSourcesDacDiem_Gan.splice(index, 1);
             }
          })
          this.dataCheckBoxDacDiemGan=[]
          this.dataCheckBoxDacDiemChuaGan=[]   
        }
      },
      ganDanhSachDacDiem(){
        if(this.dataCheckBoxDacDiemChuaGan&&this.dataCheckBoxDacDiemChuaGan.length>0){
          this.dataCheckBoxDacDiemChuaGan.forEach((item)=>{
            const indexCheck=this.dataSourcesDacDiem_Gan.findIndex((i)=>i.pho_id==item.pho_id&&i.dacdiem_id==item.dacdiem_id)
            if(indexCheck<0){
              this.dataSourcesDacDiem_Gan.push(item)
            }

            const index=this.dataSourcesDacDiem_ChuaGan.findIndex((i)=>i.pho_id==item.pho_id&&i.dacdiem_id==item.dacdiem_id)
             if (index > -1) {
               this.dataSourcesDacDiem_ChuaGan.splice(index, 1);
             }
          })
          this.dataCheckBoxDacDiemGan=[]
          this.dataCheckBoxDacDiemChuaGan=[]     
        }
      },
      selectedRowPhoGan(item){
        this.lay_danhsach_dacdiem_chua_gan(item.pho_id, item.phuong_id)
      },
      selectedRowApGan(item){
        this.lay_danhsach_dacdiem_chua_gan(item.pho_id, item.phuong_id)
      },
      selectedRowKhuGan(item){
        this.lay_danhsach_dacdiem_chua_gan(item.pho_id, item.phuong_id)
      },
      getDataNap(donvi_ql){
        // this.dataSources.push({
        //                 phuong_id: item.phuong_id,
        //                 pho_id: item.pho_id,
        //                 ap_id: item.ap_id,
        //                 khu_id: item.khu_id,
        //                 ten_phuong: item.ten_phuong!=null?item.ten_phuong:'',
        //                 ten_pho: item.ten_pho!=null?item.ten_pho:'',
        //                 ten_ap: item.ten_ap!=null?item.ten_ap:'',
        //                 ten_khu: item.ten_khu!=null?item.ten_khu:'',
        //                 ten_quan: item.ten_quan!=null?item.ten_quan:'',
        //                 donvi_ql_id: item.donviql_id,
        //                 donvi_ql:item.donvi_ql!=null?item.donvi_ql:'',
        //                 dacdiem_id: item.dacdiem_id,
        //                 dacdiem: item.dacdiem
        //           })

        // var result=[]
        // var data=[]
        // data=data.concat(this.dataSourcesPho_Gan, this.dataSourcesAp_Gan, this.dataSourcesKhu_Gan)
        // //add phuong vao dac diem
        // this.dataSourcesDacDiem_Gan.forEach((item)=>{
        //   const index =data.findIndex(x=>x.pho_id==item.pho_id)
        //   if(index>-1){
        //     Object.assign(item, data[index])
        //   }
        // })
        // this.dataSourcesDacDiem_Gan.forEach((item)=>{
        //   const index =data.findIndex(x=>x.pho_id==item.pho_id)
        //   if(index>-1){
        //     data.splice(index,1)
        //   }
        // })
        // this.dataSourcesDacDiem_Gan.forEach((item)=>{
        //   data.push(item)
        // })
        // // phân tách ấp, khu do api data để ap, khu là pho_id
        //  this.dataSourcesAp_Gan.forEach((item)=>{
        //    const index =data.findIndex(x=>x.pho_id==item.pho_id)
        //    data[index].ap_id=data[index].pho_id
        //    data[index].ten_ap=data[index].ten_pho
        //    data[index].pho_id=0
        //    data[index].ten_pho=''
        //  })
        //  this.dataSourcesKhu_Gan.forEach((item)=>{
        //    const index =data.findIndex(x=>x.pho_id==item.pho_id)
        //    data[index].khu_id=data[index].pho_id
        //    data[index].ten_khu=data[index].ten_pho
        //    data[index].pho_id=0
        //    data[index].ten_pho=''
        //  })

        // data.forEach((item)=>{
        //   result.push({
        //     phuong_id: item.phuong_id,
        //     pho_id:  item.pho_id,
        //     ap_id: item.hasOwnProperty('ap_id')?item.ap_id:0,
        //     khu_id: item.hasOwnProperty('khu_id')?item.khu_id:0,
        //     ten_phuong: item.ten_phuong,
        //     ten_pho: item.ten_pho,
        //     ten_ap: item.hasOwnProperty('ten_ap')?item.ten_ap:'',
        //     ten_khu: item.hasOwnProperty('ten_khu')?item.ten_khu:'',
        //     ten_quan:item.hasOwnProperty('ten_quan')?item.ten_quan:'',
        //     donvi_ql_id: 0,
        //     donvi_ql:'',
        //     dacdiem_id: item.hasOwnProperty('dacdiem_id')?item.dacdiem_id:0,
        //     dacdiem: item.hasOwnProperty('dacdiem')?item.dacdiem:'Không xác định'
        //   })
        // })
        if(this.dataSourcesPho_Gan.length==0&&this.dataSourcesAp_Gan.length==0&&this.dataSourcesKhu_Gan.length==0&&this.dataSourcesDacDiem_Gan.length==0){
          return []
        }
        var result=[]
        let phuong=this.danhSachPhuong.find(x=>x.PHUONG_ID==this.phuong_selected)
        let txtPhuong=phuong?phuong.TEN_PHUONG:''
        let quan=this.danhSachQuan.find(x=>x.ID==this.quan_selected)
        let txtQuan=quan?quan.NAME:''
        if(this.dataSourcesPho_Gan.length>0){
          for(let i=0;i<this.dataSourcesPho_Gan.length;i++){
            if(this.dataSourcesAp_Gan.length>0){
              for(let j=0;j<this.dataSourcesAp_Gan.length;j++){
                if(this.dataSourcesKhu_Gan.length>0){
                  for(let z=0;z<this.dataSourcesKhu_Gan.length;z++){
                    if(this.dataSourcesDacDiem_Gan.length>0){
                      for(let k=0;k<this.dataSourcesDacDiem_Gan.length;k++){
                        result.push({
                            phuong_id: this.phuong_selected,
                            pho_id: this.dataSourcesPho_Gan[i].pho_id,
                            ap_id: this.dataSourcesAp_Gan[j].pho_id,
                            khu_id: this.dataSourcesKhu_Gan[z].pho_id,
                            ten_phuong: txtPhuong,
                            ten_pho: this.dataSourcesPho_Gan[i].ten_pho,
                            ten_ap: this.dataSourcesAp_Gan[j].ten_pho,
                            ten_khu: this.dataSourcesKhu_Gan[z].ten_pho,
                            ten_quan:txtQuan,
                            donvi_ql_id: donvi_ql.DONVI_ID,
                            donvi_ql:donvi_ql.TEN_DV,
                            dacdiem_id: this.dataSourcesDacDiem_Gan[k].dacdiem_id,
                            dacdiem:this.dataSourcesDacDiem_Gan[k].dacdiem
                        })
                      }
                    }else{
                      result.push({
                          phuong_id: this.phuong_selected,
                          pho_id: this.dataSourcesPho_Gan[i].pho_id,
                          ap_id: this.dataSourcesAp_Gan[j].pho_id,
                          khu_id: this.dataSourcesKhu_Gan[z].pho_id,
                          ten_phuong: txtPhuong,
                          ten_pho: this.dataSourcesPho_Gan[i].ten_pho,
                          ten_ap: this.dataSourcesAp_Gan[j].ten_pho,
                          ten_khu: this.dataSourcesKhu_Gan[z].ten_pho,
                          ten_quan:txtQuan,
                          donvi_ql_id: donvi_ql.DONVI_ID,
                          donvi_ql:donvi_ql.TEN_DV,
                          dacdiem_id: null,
                          dacdiem:null
                      })
                    }
                  }
                }else{
                  if(this.dataSourcesDacDiem_Gan.length>0){
                      for(let k=0;k<this.dataSourcesDacDiem_Gan.length;k++){
                        result.push({
                            phuong_id: this.phuong_selected,
                            pho_id: this.dataSourcesPho_Gan[i].pho_id,
                            ap_id: this.dataSourcesAp_Gan[j].pho_id,
                            khu_id: 0,
                            ten_phuong: txtPhuong,
                            ten_pho: this.dataSourcesPho_Gan[i].ten_pho,
                            ten_ap: this.dataSourcesAp_Gan[j].ten_pho,
                            ten_khu: '',
                            ten_quan:txtQuan,
                            donvi_ql_id: donvi_ql.DONVI_ID,
                            donvi_ql:donvi_ql.TEN_DV,
                            dacdiem_id: this.dataSourcesDacDiem_Gan[k].dacdiem_id,
                            dacdiem:this.dataSourcesDacDiem_Gan[k].dacdiem
                        })
                      }
                    }else{
                      result.push({
                          phuong_id: this.phuong_selected,
                          pho_id: this.dataSourcesPho_Gan[i].pho_id,
                          ap_id: this.dataSourcesAp_Gan[j].pho_id,
                          khu_id: 0,
                          ten_phuong: txtPhuong,
                          ten_pho: this.dataSourcesPho_Gan[i].ten_pho,
                          ten_ap: this.dataSourcesAp_Gan[j].ten_pho,
                          ten_khu: '',
                          ten_quan:txtQuan,
                          donvi_ql_id: donvi_ql.DONVI_ID,
                          donvi_ql:donvi_ql.TEN_DV,
                          dacdiem_id: null,
                          dacdiem:null
                      })
                    }
                }
              }
            }else{
              if(this.dataSourcesKhu_Gan.length>0){
                  for(let z=0;z<this.dataSourcesKhu_Gan.length;z++){
                    if(this.dataSourcesDacDiem_Gan.length>0){
                      for(let k=0;k<this.dataSourcesDacDiem_Gan.length;k++){
                        result.push({
                            phuong_id: this.phuong_selected,
                            pho_id: this.dataSourcesPho_Gan[i].pho_id,
                            ap_id: 0,
                            khu_id: this.dataSourcesKhu_Gan[z].pho_id,
                            ten_phuong: txtPhuong,
                            ten_pho: this.dataSourcesPho_Gan[i].ten_pho,
                            ten_ap: '',
                            ten_khu: this.dataSourcesKhu_Gan[z].ten_pho,
                            ten_quan:txtQuan,
                            donvi_ql_id: donvi_ql.DONVI_ID,
                            donvi_ql:donvi_ql.TEN_DV,
                            dacdiem_id: this.dataSourcesDacDiem_Gan[k].dacdiem_id,
                            dacdiem:this.dataSourcesDacDiem_Gan[k].dacdiem
                        })
                      }
                    }else{
                      result.push({
                          phuong_id: this.phuong_selected,
                          pho_id: this.dataSourcesPho_Gan[i].pho_id,
                          ap_id: 0,
                          khu_id: this.dataSourcesKhu_Gan[z].pho_id,
                          ten_phuong: txtPhuong,
                          ten_pho: this.dataSourcesPho_Gan[i].ten_pho,
                          ten_ap: '',
                          ten_khu: this.dataSourcesKhu_Gan[z].ten_pho,
                          ten_quan:txtQuan,
                          donvi_ql_id: donvi_ql.DONVI_ID,
                          donvi_ql:donvi_ql.TEN_DV,
                          dacdiem_id: null,
                          dacdiem:null
                      })
                    }
                  }
              }else{
                if(this.dataSourcesDacDiem_Gan.length>0){
                    for(let k=0;k<this.dataSourcesDacDiem_Gan.length;k++){
                      result.push({
                          phuong_id: this.phuong_selected,
                          pho_id: this.dataSourcesPho_Gan[i].pho_id,
                          ap_id: 0,
                          khu_id: 0,
                          ten_phuong: txtPhuong,
                          ten_pho: this.dataSourcesPho_Gan[i].ten_pho,
                          ten_ap: '',
                          ten_khu: '',
                          ten_quan:txtQuan,
                          donvi_ql_id: donvi_ql.DONVI_ID,
                          donvi_ql:donvi_ql.TEN_DV,
                          dacdiem_id: this.dataSourcesDacDiem_Gan[k].dacdiem_id,
                          dacdiem:this.dataSourcesDacDiem_Gan[k].dacdiem
                      })
                    }
                  }else{
                    result.push({
                        phuong_id: this.phuong_selected,
                        pho_id: this.dataSourcesPho_Gan[i].pho_id,
                        ap_id: 0,
                        khu_id: 0,
                        ten_phuong: txtPhuong,
                        ten_pho: this.dataSourcesPho_Gan[i].ten_pho,
                        ten_ap: '',
                        ten_khu: '',
                        ten_quan:txtQuan,
                        donvi_ql_id: donvi_ql.DONVI_ID,
                        donvi_ql:donvi_ql.TEN_DV,
                        dacdiem_id: null,
                        dacdiem:null
                    })
                  }
              }
            }
          }
        }else{
          if(this.dataSourcesAp_Gan.length>0){
              for(let j=0;j<this.dataSourcesAp_Gan.length;j++){
                if(this.dataSourcesKhu_Gan.length>0){
                  for(let z=0;z<this.dataSourcesKhu_Gan.length;z++){
                    if(this.dataSourcesDacDiem_Gan.length>0){
                      for(let k=0;k<this.dataSourcesDacDiem_Gan.length;k++){
                        result.push({
                            phuong_id: this.phuong_selected,
                            pho_id: 0,
                            ap_id: this.dataSourcesAp_Gan[j].pho_id,
                            khu_id: this.dataSourcesKhu_Gan[z].pho_id,
                            ten_phuong: txtPhuong,
                            ten_pho: '',
                            ten_ap: this.dataSourcesAp_Gan[j].ten_pho,
                            ten_khu: this.dataSourcesKhu_Gan[z].ten_pho,
                            ten_quan:txtQuan,
                            donvi_ql_id: donvi_ql.DONVI_ID,
                            donvi_ql:donvi_ql.TEN_DV,
                            dacdiem_id: this.dataSourcesDacDiem_Gan[k].dacdiem_id,
                            dacdiem:this.dataSourcesDacDiem_Gan[k].dacdiem
                        })
                      }
                    }else{
                      result.push({
                          phuong_id: this.phuong_selected,
                          pho_id: 0,
                          ap_id: this.dataSourcesAp_Gan[j].pho_id,
                          khu_id: this.dataSourcesKhu_Gan[z].pho_id,
                          ten_phuong: txtPhuong,
                          ten_pho: '',
                          ten_ap: this.dataSourcesAp_Gan[j].ten_pho,
                          ten_khu: this.dataSourcesKhu_Gan[z].ten_pho,
                          ten_quan:txtQuan,
                          donvi_ql_id: donvi_ql.DONVI_ID,
                          donvi_ql:donvi_ql.TEN_DV,
                          dacdiem_id: null,
                          dacdiem:null
                      })
                    }
                  }
                }else{
                  if(this.dataSourcesDacDiem_Gan.length>0){
                      for(let k=0;k<this.dataSourcesDacDiem_Gan.length;k++){
                        result.push({
                            phuong_id: this.phuong_selected,
                            pho_id: 0,
                            ap_id: this.dataSourcesAp_Gan[j].pho_id,
                            khu_id: 0,
                            ten_phuong: txtPhuong,
                            ten_pho: '',
                            ten_ap: this.dataSourcesAp_Gan[j].ten_pho,
                            ten_khu: '',
                            ten_quan:txtQuan,
                            donvi_ql_id: donvi_ql.DONVI_ID,
                            donvi_ql:donvi_ql.TEN_DV,
                            dacdiem_id: this.dataSourcesDacDiem_Gan[k].dacdiem_id,
                            dacdiem:this.dataSourcesDacDiem_Gan[k].dacdiem
                        })
                      }
                    }else{
                      result.push({
                          phuong_id: this.phuong_selected,
                          pho_id: 0,
                          ap_id: this.dataSourcesAp_Gan[j].pho_id,
                          khu_id: 0,
                          ten_phuong: txtPhuong,
                          ten_pho: '',
                          ten_ap: this.dataSourcesAp_Gan[j].ten_pho,
                          ten_khu: '',
                          ten_quan:txtQuan,
                          donvi_ql_id: donvi_ql.DONVI_ID,
                          donvi_ql:donvi_ql.TEN_DV,
                          dacdiem_id: null,
                          dacdiem:null
                      })
                    }
                }
              }
            }else{
              if(this.dataSourcesKhu_Gan.length>0){
                  for(let z=0;z<this.dataSourcesKhu_Gan.length;z++){
                    if(this.dataSourcesDacDiem_Gan.length>0){
                      for(let k=0;k<this.dataSourcesDacDiem_Gan.length;k++){
                        result.push({
                            phuong_id: this.phuong_selected,
                            pho_id: 0,
                            ap_id: 0,
                            khu_id: this.dataSourcesKhu_Gan[z].pho_id,
                            ten_phuong: txtPhuong,
                            ten_pho: '',
                            ten_ap: '',
                            ten_khu: this.dataSourcesKhu_Gan[z].ten_pho,
                            ten_quan:txtQuan,
                            donvi_ql_id: donvi_ql.DONVI_ID,
                            donvi_ql:donvi_ql.TEN_DV,
                            dacdiem_id: this.dataSourcesDacDiem_Gan[k].dacdiem_id,
                            dacdiem:this.dataSourcesDacDiem_Gan[k].dacdiem
                        })
                      }
                    }else{
                      result.push({
                          phuong_id: this.phuong_selected,
                          pho_id: 0,
                          ap_id: 0,
                          khu_id: this.dataSourcesKhu_Gan[z].pho_id,
                          ten_phuong: txtPhuong,
                          ten_pho: '',
                          ten_ap: '',
                          ten_khu: this.dataSourcesKhu_Gan[z].ten_pho,
                          ten_quan:txtQuan,
                          donvi_ql_id: donvi_ql.DONVI_ID,
                          donvi_ql:donvi_ql.TEN_DV,
                          dacdiem_id: null,
                          dacdiem:null
                      })
                    }
                  }
              }else{
                if(this.dataSourcesDacDiem_Gan.length>0){
                    for(let k=0;k<this.dataSourcesDacDiem_Gan.length;k++){
                      result.push({
                          phuong_id: this.phuong_selected,
                          pho_id: 0,
                          ap_id: 0,
                          khu_id: 0,
                          ten_phuong: txtPhuong,
                          ten_pho: '',
                          ten_ap: '',
                          ten_khu: '',
                          ten_quan:txtQuan,
                          donvi_ql_id: donvi_ql.DONVI_ID,
                          donvi_ql:donvi_ql.TEN_DV,
                          dacdiem_id: this.dataSourcesDacDiem_Gan[k].dacdiem_id,
                          dacdiem:this.dataSourcesDacDiem_Gan[k].dacdiem
                      })
                    }
                  }else{
                    result.push({
                        phuong_id: this.phuong_selected,
                        pho_id: 0,
                        ap_id: 0,
                        khu_id: 0,
                        ten_phuong: txtPhuong,
                        ten_pho: '',
                        ten_ap: '',
                        ten_khu: '',
                        ten_quan:txtQuan,
                        donvi_ql_id: donvi_ql.DONVI_ID,
                        donvi_ql:donvi_ql.TEN_DV,
                        dacdiem_id: null,
                        dacdiem:null
                    })
                  }
              }
            }
        }



        return result
      },
      cleanDataGan(){
        this.dataSourcesPho_Gan=[]
        this.dataSourcesAp_Gan=[]
        this.dataSourcesKhu_Gan=[]
        this.dataSourcesDacDiem_Gan=[]
      },
      onClickButtonFilterPho(){
        this.allowFilters.pho=!this.allowFilters.pho
      },
      onClickButtonFilterAp(){
        this.allowFilters.ap=!this.allowFilters.ap
      },
      initDuLieu(){
        this.layDanhSachTinh()
      }
    },
    mounted(){
      this.initDuLieu()
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>
