<template>
    <div id="xemChitiet" >
      <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="lay_tt_SDDV"> <span class="icon one-search"></span>Lấy trạng thái SDDV </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="row">
        <div class="col-sm-12 col-12">
          <div class="box-form">
            <div class="legend-title">Thông tin tra cứu</div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="row">
                  <div class="col-sm-3 col-12">
                    <div class="info-row">
                      <div class="key w90">Số máy/Acc</div>
                      <div class="value">
                        <div class="input-more-button">
                          <button class="btn">
                            <span class="-ap icon-more_horiz"></span>
                          </button>
                          <input type="text" v-model="thongtinTracuu.ma_tb" class="form-control highlight" v-on:keyup.enter='lay_tt_SDDV' :disabled="true">
                        </div>
                      </div>
                      <!-- <div class="value">
                        <div class="input-more-button">
                          <button class="btn" @click.prevent='ShowSearchAccount'>
                            <span class="-ap icon-more_horiz"></span>
                          </button>
                          <input type='text' ref='reftxtMaTB' v-model.trim='thongtin_input.ma_tb'
                            v-on:keyup.enter='txtMaTB_KeyPress' class='form-control highlight'  placeholder="Nhập số máy/Acc">
                        </div>
                      </div> -->
                    </div>
                  </div>
                  <div class="col-sm-3 col-12">
                    <div class="info-row">
                      <div class="key w90">Loại TB</div>
                      <div class="value">
                        <input type="text" class="form-control" v-model="thongtinTracuu.loai_tb" :disabled="true"/>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-3 col-12">
                    <div class="info-row">
                      <div class="key w90">Từ ngày</div>
                      <div class="value">
                        <div class="input-icon-right">
                          <KDatePicker format="DD/MM/YYYY" v-model="thongtinTracuu.tungay" :disabled="false" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-3 col-12">
                    <div class="info-row">
                      <div class="key w90">Đến ngày</div>
                      <div class="value">
                        <div class="input-icon-right">
                          <KDatePicker format="DD/MM/YYYY" v-model="thongtinTracuu.denngay" :disabled="false" />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row"  v-if="loaitb_id === 58">
              <div class="col-sm-3 col-12">
                <div class="info-row">
                  <div class="key w90">ManagerIP</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="thongtinTracuu.managerIp" :disabled="true"/>
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-12">
                <div class="info-row">
                  <div class="key w90">FrameNo</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="thongtinTracuu.frameNo" :disabled="true" />
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-12">
                <div class="info-row">
                  <div class="key w90">SlotNo</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="thongtinTracuu.slotNo" :disabled="true"/>
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-12">
                <div class="info-row">
                  <div class="key w90">PortNo</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="thongtinTracuu.portNo" :disabled="true"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="row"  v-if="loaitb_id === 58">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">OnuNo</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="thongtinTracuu.onuNo" :disabled="true"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title" style="color: rgb(0, 0, 0);">Lịch sử sử dụng dịch vụ</div>
        <div class="row">
          <div class="clearfix"></div>
          <div v-if="loaitb_id !== 58">
            <DataGrid ref="gridDSlichsu1" 
              v-bind:columns="[
                {fieldName: 'ngay', headerText: 'Ngày', allowfiltering: true, allowSorting: true},
                {fieldName: 'trangthai', headerText: 'Trạng thái tương tác của thuê bao', allowfiltering: true},
              ]"
              v-bind:dataSource="gridDSlichsu1"
              :enable-paging-server="false" :allowPaging="true" :showFilter="true"  :showColumnCheckbox="false"
              :enabledSelectFirstRow="false">
            </DataGrid>
          </div>
          <div v-else>
            <DataGrid ref="gridDSlichsu2" 
              v-bind:columns="[
                {fieldName: 'createdDate', headerText: 'Ngày', allowfiltering: true, allowSorting: true},
                {fieldName: 'onuIdv2', headerText: 'onuIdv2', allowfiltering: true},
                {fieldName: 'bytesDs', headerText: 'Lưu lượng download (bytes)', allowfiltering: true},
                {fieldName: 'bytesUs', headerText: 'Lưu lượng upload (bytes)', allowfiltering: true}
              ]"
              v-bind:dataSource="gridDSlichsu2"
              :enable-paging-server="false" :allowPaging="true" :showFilter="true"   :showColumnCheckbox="false"
              :enabledSelectFirstRow="false">
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
    <!-- <b-modal id="popupComponents" size="huge" :title="popupComponentHeader" :hide-header="popupComponentHeader?false:true"
      hide-footer modal-class="modal-custom" body-class="modal-body p-0">
      <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
        isPopup="true" v-on="popupComponentEvts" @form-close="popupClosed"></component>
    </b-modal> -->
    </div>
    </template>
    <style>
    </style>
    <script>
      import moment from 'moment'
      import api from '../API.js'
      import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
      import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
      import ActionTop from '@/components/kylq_components/ActionTop.vue'
      export default {
      name:'xemChitiet',
      components:{
        ActionTop,
        KDatePicker,
        KRequiredMarker
      },
      props: ["dulieu"],
      data(){
        return{
          thongtinTracuu:{
            ma_tb:'',
            loai_tb:'',
            tungay: moment(new Date()).subtract(31, 'days').format('DD/MM/YYYY'),
            denngay: moment(new Date()).subtract(1, 'days').format('DD/MM/YYYY'),
            managerIp:'',
            slotNo:'',
            portNo:'',
            onuNo:'',
            frameNo:''
          },
          loaitb_id:'',
          gridDSlichsu1:[],
          gridDSlichsu2:[]
  
        }
      },
      mounted() {
        this.initDulieu()
      },
      methods:{
        async initDulieu(){
          console.log('Dữ liệu popup nhận được:',this.dulieu)
          this.thongtinTracuu.ma_tb = this.dulieu.ma_tb
          this.thongtinTracuu.loai_tb = this.dulieu.ten_loaitb
          this.loaitb_id = this.dulieu.loaitb_id
          this.$forceUpdate();
          if (this.loaitb_id === 58){
            await this.layport_theoMATB()
          }
        },
        async layport_theoMATB(){
          try {
            const Input = {
              ma_tb: this.thongtinTracuu.ma_tb,
              kieu: 1
            };
            const response = await api.lay_port_theoMATB(this.axios, Input);
            if (response && response.data && response.data.error_code === "BSS-00000000"){
              console.log('có data trả về',response.data.data[0])
              const output = response.data.data[0]
              this.thongtinTracuu.managerIp = output.ip
              this.thongtinTracuu.slotNo = output.slot
              this.thongtinTracuu.onuNo = output.vpi
              this.thongtinTracuu.frameNo = output.rack
              this.thongtinTracuu.portNo = output.sport
            }
          }catch(error){
            this.$toast.error('Xảy ra lỗi khi lấy thông tin port thuê bao')
          }finally{
            this.loading(false);
          }
        },
        async lay_tt_SDDV(){
  
          if (this.loaitb_id === 58){
            await this.lay_trangthai_SDDV_fiber()
          }else{
            await this.lay_trangthai_SDDV_myTV()
          }
        },
        async lay_trangthai_SDDV_fiber(){
          if (this.thongtinTracuu.tungay === null || this.thongtinTracuu.denngay === null){
            this.$toast.error('Bạn phải nhập thông tin Từ ngày và Đến ngày')
            return
          }
          if (moment(this.thongtinTracuu.denngay, 'DD/MM/YYYY').isAfter(moment())) {
            this.$toast.error('Đến ngày phải nhỏ hơn ngày hiện tại')
            return
          }
          if (moment(this.thongtinTracuu.tungay, 'DD/MM/YYYY').isAfter(moment(this.thongtinTracuu.denngay, 'DD/MM/YYYY'))) {
            this.$toast.error('Từ ngày không được lớn hơn Đến ngày');
            return
          }
          try{
            const Input = {
              deviceip: this.thongtinTracuu.managerIp,
              slot: this.thongtinTracuu.slotNo,
              port: this.thongtinTracuu.portNo,
              vpi: this.thongtinTracuu.onuNo,
              frame: this.thongtinTracuu.frameNo,
              fromTime: this.thongtinTracuu.tungay,
              toTime: this.thongtinTracuu.denngay
            };
            const response = await api.lay_trangthai_SDDV_fiber(this.axios, Input);
            if (response && response.data && response.data.error_code === "BSS-00000000"){
             this.gridDSlichsu2 = response.data.data
            }
          }catch{
            this.$toast.error('Xảy ra lỗi khi lấy trạng thái SDDV thuê bao Fiber')
          }finally{
            this.loading(false);
          }
        },
        async lay_trangthai_SDDV_myTV(){
          this.$toast.error('MyTV đang chờ api')
          return
        },
        huy(){
            this.$emit('form-close', false);
        },
        clearAll(){
      
        },
      }
      }
      </script>