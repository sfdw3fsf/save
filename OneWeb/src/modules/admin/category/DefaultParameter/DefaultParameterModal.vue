<template>
  <b-modal
    ref="popupDefaultParameter"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-esc
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Cấu hình hệ thống
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="nav tabs tab-over">
          <a href="#tab1" data-toggle="tab" @click.prevent="changeTab(1)" :class="{active: this.currentTab==1}">Tham số mặc định</a>
          <a href="#tab2" data-toggle="tab" @click.prevent="changeTab(2)" :class="{active: this.currentTab==2}">Tham số người dùng</a>
          <a href="#tab3" data-toggle="tab" @click.prevent="changeTab(3)" :class="{active: this.currentTab==3}">Tham số đơn vị</a>
          <a href="#tab4" data-toggle="tab" @click.prevent="changeTab(4)" :class="{active: this.currentTab==4}">Tham số chung</a>
          <a href="#tab5" data-toggle="tab" @click.prevent="changeTab(5)" :class="{active: this.currentTab==5}">Loại chương trình</a>
        </div>
        <div class="tab-content">
          <div class="tab-pane" :class="{active: this.currentTab==1}" id="tab1">
            <ThamSoMacDinh ref="thamsomacdinh"/>
          </div>
          <div class="tab-pane" :class="{active: this.currentTab==2}" id="tab2">
            <ThamSoNguoiDung ref="thamsonguoidung"/>
          </div>
          <div class="tab-pane" :class="{active: this.currentTab==3}" id="tab3">
            <ThamSoDonVi ref="thamsodonvi"/>
          </div>
          <div class="tab-pane" :class="{active: this.currentTab==4}" id="tab4">
            <ThamSoChung ref="thamsochung"/>
          </div>
          <div class="tab-pane" :class="{active: this.currentTab==5}" id="tab5">
            <LoaiChuongTrinh ref="loaichuongtrinh"/>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import ThamSoMacDinh from './ThamSoMacDinh.vue'
import ThamSoNguoiDung from './ThamSoNguoiDung.vue'
import ThamSoDonVi from './ThamSoDonVi.vue'
import ThamSoChung from './ThamSoChung.vue'
import LoaiChuongTrinh from './LoaiChuongTrinh.vue'

export default {
    name:'DefaultParameterModal',
    components:{
        ActionTop,
        ThamSoMacDinh,
        ThamSoNguoiDung,
        ThamSoDonVi,
        ThamSoChung,
        LoaiChuongTrinh
    },
    data(){
        return {
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                }
            ],
            currentTab:1
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='ghilai'){
                if(this.currentTab==1){
                  this.$refs.thamsomacdinh.capnhat_thamso()
                }else if(this.currentTab==2){
                  this.$refs.thamsonguoidung.capnhat_thamso()
                }else if(this.currentTab==3){
                  this.$refs.thamsodonvi.capnhat_thamso()
                }else if(this.currentTab==4){
                  this.$refs.thamsochung.capnhat_thamso()
                }else if(this.currentTab==5){
                  this.$refs.loaichuongtrinh.capnhat_thamso()
                }
            }
        },
        showModal() {
            this.$refs["popupDefaultParameter"].show()
        },
        hideModal() {
            this.$refs["popupDefaultParameter"].hide()
        },
        handleShowModal(){
          //init data tab 1
          this.currentTab=1
          setTimeout(()=>{
            this.khoiTaoDuLieuBanDau()
          },500)
        },
        async khoiTaoDuLieuBanDau(){
          await this.$refs.thamsomacdinh.clearDuLieu()
          await this.$refs.thamsomacdinh.initDuLieu()
        },  
        async changeTab(tab){
            if(this.currentTab==tab){
                return
            }
            this.currentTab=tab
            if(this.currentTab==1){
                await this.$refs.thamsomacdinh.clearDuLieu()
                await this.$refs.thamsomacdinh.initDuLieu()
            }else if(this.currentTab==2){
                await this.$refs.thamsonguoidung.clearDuLieu()
                await this.$refs.thamsonguoidung.initDuLieu()
            }else if(this.currentTab==3){
                await this.$refs.thamsodonvi.clearDuLieu()
                await this.$refs.thamsodonvi.initDuLieu()
            }else if(this.currentTab==4){
                await this.$refs.thamsochung.clearDuLieu()
                await this.$refs.thamsochung.initDuLieu()
            }else if(this.currentTab==5){
                await this.$refs.loaichuongtrinh.clearDuLieu()
                await this.$refs.loaichuongtrinh.initDuLieu()
            }
        },

    }
}
</script>