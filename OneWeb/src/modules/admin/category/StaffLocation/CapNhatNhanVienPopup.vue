<template>
  <b-modal
    ref="popupCapNhatNhanVien"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Cập nhật nhân viên quản lý đơn hàng</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin</div>
          <div class="info-row">
            <div class="key">Phòng bán hàng</div>
            <div class="value">
              <!-- <div class="select-custom">
                <select v-model="phongbh_selected" @change="changePhongBH" class="form-control">
                  <option v-for="item in danhsach_phongbh" :key="item.donvi_id" :value="item.donvi_id">{{item.ten_dv}}</option>
                </select>
              </div> -->
              <multiselect
                  :options="danhsach_phongbh"
                  placeholder="Chọn phòng bán hàng"
                  @input="changePhongBH"
                  :showLabels="false"
                  :allowEmpty="false"
                  :value="phongbh_o_selected"
                  label="ten_dv"
                  track-by="donvi_id">
                  <template #noOptions>
                      Không tìm thấy thông tin
                  </template>
                </multiselect>
            </div>
          </div>
          <div class="info-row">
            <div class="key">Nhân viên</div>
            <div class="value">
              <!-- <div class="select-custom">
                <select v-model="nhanvien_selected" class="form-control">
                  <option v-for="item in danhsach_nhanvien" :key="item.nhanvien_id" :value="item.nhanvien_id">{{item.ten_nv}}</option>
                </select>
              </div> -->
              <multiselect
                  :options="danhsach_nhanvien"
                  placeholder="Chọn nhân viên"
                  @input="changeNhanVien"
                  :showLabels="false"
                  :allowEmpty="false"
                  :value="nhanvien_o_selected"
                  :custom-label="customLableNhanVien"
                  label="ten_nv"
                  track-by="nhanvien_id">
                  <template #noOptions>
                      Không tìm thấy thông tin
                  </template>
                </multiselect>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách nhân viên quản lý đơn hàng</div>
          <KTableV3 :columns="columns" :dataSources="dataSources" id="donvi_id" :rowSelectedId="rowSelectedId"  @onSelectedRow="onSelectedRow" :allowFilter="true" />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import StaffLocationAPI from './StaffLocationAPI'
export default {
    name:'CapNhatNhanVienPopup',
    components:{
        KTableV3, ActionTop
    },
    computed:{
      phongbh_o_selected(){
        const phongbh=this.danhsach_phongbh.find(x=>x.donvi_id==this.phongbh_selected)
        return phongbh!=undefined?phongbh:null
      },
      nhanvien_o_selected(){
        const nhanvien=this.danhsach_nhanvien.find(x=>x.nhanvien_id==this.nhanvien_selected)
        return nhanvien!=undefined?nhanvien:null
      }
    },
    data(){
        return{
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                  id:'xoa',
                  name:'Xoá',
                  active:true,
                  icon_class:'-ap icon-close'
                }
            ],
            columns:[
                {
                    field: "ten_dv",
                    label: "Phòng bán hàng",
                    allowFilter: true,
                    allowSorting:true,
                    cssRows:{
                      fontWeight: 'bold'
                    }
                },
                {
                    field: "ma_nv",
                    label: "Mã nhân viên",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "ten_nv",
                    label: "Tên nhân viên",
                    allowFilter: true,
                    allowSorting:true,
                    cssRows:{
                      fontWeight: 'bold'
                    }
                },
                {
                    field: "so_dt",
                    label: "Số điện thoại",
                    allowFilter: true,
                    allowSorting:true
                },
                {
                    field: "email",
                    label: "Email",
                    allowFilter: true,
                    allowSorting:true
                },
            ],
            dataSources:[],
            danhsach_phongbh:[],
            phongbh_selected:-1,
            danhsach_nhanvien:[],
            nhanvien_selected:-1,
            rowSelected:null,
            rowSelectedId:-1
        }
    },
    methods:{
        onActionClick(action){
          
          if(action.id=='ghilai'){
            if(this.nhanvien_selected==-1){
              this.$alert('Chưa chọn nhân viên!', 'Thông báo', {
                dangerouslyUseHTMLString: true,
                confirmButtonText: 'OK',
                type: 'warning'
              })
              return
            }
            let nguoicn=this.$root.token.getUserName()
            let data={
              vdonvi_id: this.phongbh_selected,
              vmay_cn: "localhost",
              vnguoi_cn: nguoicn,
              vip_cn: "127.0.0.1",
              vnhanvien_id: this.nhanvien_selected
            }
            this.capnhat_nhanvien_macdinh(data, false)

          }else if(action.id=='xoa'){
            if(this.rowSelected==null)
            if(this.dataSources.length==0){
              this.$alert('Không có dữ liệu xoá', 'Thông báo', {
                dangerouslyUseHTMLString: true,
                confirmButtonText: 'OK',
                type: 'warning'
              })
              return
            }
            if(this.rowSelected==null){
              this.$alert('Chọn dữ liệu để xoá!', 'Thông báo', {
                dangerouslyUseHTMLString: true,
                confirmButtonText: 'OK',
                type: 'warning'
              })
              return
            }

            this.$confirm('Bạn chắc chắn muốn xóa?','',{
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'info'
            }).then(() => {
              let nguoicn=this.$root.token.getUserName()
              let data={
                vdonvi_id: this.phongbh_selected,
                vmay_cn: "localhost",
                vnguoi_cn: nguoicn,
                vip_cn: "127.0.0.1",
                vnhanvien_id: 0
              }
              this.capnhat_nhanvien_macdinh(data, true)
            }).catch((e) => {

            })
              
          }

        },
        handleShowModal(){
          this.dataSources=[]
          this.danhsach_phongbh=[]
          this.phongbh_selected=-1
          this.rowSelected=null
          //lấy danh sách phòng bh
          
          setTimeout(()=>{
            this.initDuLieu()
          }, 500)

        },
        async initDuLieu(){
          await this.lay_danhsach_phongbh({
            donvi_id: 0, 
            kieu: 1
          })
          if(this.danhsach_phongbh.length>0){
            this.phongbh_selected=this.danhsach_phongbh[0].donvi_id
            await this.lay_danhsach_nhanvien_ql({
              donvi_id:this.phongbh_selected,
              kieu:3
            })
            await this.lay_danhsach_nhanvien({
              donvi_id:this.phongbh_selected,
              kieu:2
            })
            if(this.rowSelectedId!=this.phongbh_selected){
              if(this.dataSources.length>0){
                this.rowSelectedId=this.dataSources[0].donvi_id
                this.onSelectedRow(this.dataSources[0])
              }
            }
          }

        },
        customLableNhanVien(object){
          return `${object.ma_nv} — ${object.ten_nv}`
        },
        changePhongBH(data){
          this.phongbh_selected=data.donvi_id
          this.rowSelectedId=data.donvi_id
          this.lay_danhsach_nhanvien({
              donvi_id:this.phongbh_selected,
              kieu:2
          })
        },
        changeNhanVien(data){
          this.nhanvien_selected=data.nhanvien_id
        },
        onSelectedRow(item){
          this.rowSelected=item
          this.rowSelectedId=item.donvi_id
          if(this.phongbh_selected==item.donvi_id){
            this.nhanvien_selected=item.nhanvien_id
          }else{
            this.phongbh_selected=item.donvi_id
            this.nhanvien_selected=item.nhanvien_id
            this.lay_danhsach_nhanvien({
              donvi_id:this.phongbh_selected,
              kieu:2
            })
          }
        },
        showModal() {
            this.$refs["popupCapNhatNhanVien"].show()
        },
        hideModal() {
            this.$refs["popupCapNhatNhanVien"].hide()
        },
        //với data.kieu=1
        async lay_danhsach_phongbh(data){
            try{
              this.loading(true)
                this.danhsach_phongbh=[]
                let response = await StaffLocationAPI.sp_lay_tt_nv_macdinh(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_phongbh=response.data.data
                }else{
                    this.danhsach_phongbh=[]
                }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được danh sách')
            }
        },
        //với data.kieu=3
        async lay_danhsach_nhanvien_ql(data){
            try{
                this.loading(true)
                this.dataSources=[]
                let response = await StaffLocationAPI.sp_lay_tt_nv_macdinh(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSources=response.data.data
                }else{
                    this.dataSources=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách nhân viên quản lý')
            }finally{
                this.loading(false)
            }
        },
        //với data.kieu=2
        async lay_danhsach_nhanvien(data){
            try{
              this.loading(true)
              this.danhsach_nhanvien=[]
              let response = await StaffLocationAPI.sp_lay_tt_nv_macdinh(this.axios, data)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.danhsach_nhanvien=response.data.data
              }else{
                this.danhsach_nhanvien=[]
              }
            }catch(e){
                this.$toast.error('Không load được danh sách nhân viên')
            }finally{
              this.loading(false)
            }
        },
        async capnhat_nhanvien_macdinh(data, isDel){
            try{
                this.loading(true)
                let response=await StaffLocationAPI.capnhat_nhanvien_macdinh(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$alert(isDel?'Xoá dữ liệu thành công!':'Cập nhật dữ liệu thành công!', 'Thông báo', {
                        dangerouslyUseHTMLString: true,
                        confirmButtonText: 'OK',
                        type: 'info'
                    })
                    this.lay_danhsach_nhanvien_ql({
                        donvi_id:this.phongbh_selected,
                        kieu:3
                    })
                    if(isDel){
                      this.nhanvien_selected=-1
                    }
                    this.rowSelected=null
                }else{
                    this.$alert(isDel?'Đã có lỗi xảy ra trong quá trình xoá dữ liệu':'Đã xảy ra lỗi không thể cập nhật được dữ liệu', 'Lỗi', {
                        dangerouslyUseHTMLString: true,
                        confirmButtonText: 'OK',
                        type: 'error'
                    })
                }
            }catch(e){
                this.$alert(isDel?'Đã có lỗi xảy ra trong quá trình xoá dữ liệu':'Đã xảy ra lỗi không thể cập nhật được dữ liệu', 'Lỗi', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'OK',
                    type: 'error'
                })
            }finally{
                this.loading(false)
            }
        }
    }
}
</script>
<style>
.multiselect, .multiselect__input, .multiselect__single {
    font-size: 1rem !important;
}
.multiselect__tags{
    min-height: 32px !important;
    border: 1px solid #ced4da !important;
    padding: 6px 32px 0 6px;
    border-radius: 4px;
}
.multiselect__placeholder {
    margin-bottom: 5px!important;
    padding-top: 0px!important;
}
.multiselect__select{
    top: 0px!important;
    padding: 10px 9px!important;
}
.multiselect--disabled{
  background: none!important;
}
.multiselect--disabled .multiselect__current, .multiselect--disabled .multiselect__select{
  background: none!important;
}
</style>