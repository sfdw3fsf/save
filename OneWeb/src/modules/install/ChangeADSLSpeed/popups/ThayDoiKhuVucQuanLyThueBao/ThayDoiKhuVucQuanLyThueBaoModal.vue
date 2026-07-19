<template>
  <b-modal
    ref="popupThayDoiKhuVucQuanLyThueBao"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Gán khu vực quản lý thuê bao
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" v-if="!tracuu" />

      <div class="popup-body">
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin địa danh</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w60">Quận</div>
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input type="checkbox" v-model="thongtin_diadanh.checkQuan" @change="changeCheckDiaDanh" class="check" />
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <multiselect
                          :options="thongtin_diadanh.ds_quan"
                          placeholder="Chọn quận"
                          @input="changeQuan"
                          :showLabels="false"
                          :allowEmpty="false"
                          :disabled="!thongtin_diadanh.checkQuan"
                          :value="quan_selected"
                          label="TEN_QUAN"
                          track-by="QUAN_ID">
                          <template #noOptions>
                              Không tìm thấy thông tin
                          </template>
                      </multiselect>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w60">Phố</div>
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input type="checkbox" v-model="thongtin_diadanh.checkPho" @change="changeCheckDiaDanh" class="check" />
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <multiselect
                          :options="thongtin_diadanh.ds_pho"
                          placeholder="Chọn phố"
                          @input="changePho"
                          :disabled="!thongtin_diadanh.checkPho"
                          :showLabels="false"
                          :allowEmpty="false"
                          :value="pho_selected"
                          label="TEN_PHO"
                          track-by="PHO_ID">
                          <template #noOptions>
                              Không tìm thấy thông tin
                          </template>
                      </multiselect>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w60">Khu</div>
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input type="checkbox" v-model="thongtin_diadanh.checkKhu" @change="changeCheckDiaDanh" class="check" />
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <multiselect
                          :options="thongtin_diadanh.ds_khu"
                          placeholder="Chọn khu"
                          @input="changeKhu"
                          :showLabels="false"
                          :allowEmpty="false"
                          :disabled="!thongtin_diadanh.checkKhu"
                          :value="khu_selected"
                          label="TEN_PHO"
                          track-by="PHO_ID">
                          <template #noOptions>
                              Không tìm thấy thông tin
                          </template>
                      </multiselect>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w70">Phường</div>
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input type="checkbox" v-model="thongtin_diadanh.checkPhuong" @change="changeCheckDiaDanh" class="check" />
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <multiselect
                          :options="thongtin_diadanh.ds_phuong"
                          placeholder="Chọn phường"
                          @input="changePhuong"
                          :showLabels="false"
                          :allowEmpty="false"
                          :disabled="!thongtin_diadanh.checkPhuong"
                          :value="phuong_selected"
                          label="TEN_PHUONG"
                          track-by="PHUONG_ID">
                          <template #noOptions>
                              Không tìm thấy thông tin
                          </template>
                      </multiselect>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Ấp</div>
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input type="checkbox" v-model="thongtin_diadanh.checkAp" @change="changeCheckDiaDanh" class="check" />
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <multiselect
                          :options="thongtin_diadanh.ds_ap"
                          placeholder="Chọn ấp"
                          @input="changeAp"
                          :showLabels="false"
                          :allowEmpty="false"
                          :disabled="!thongtin_diadanh.checkAp"
                          :value="ap_selected"
                          label="TEN_PHO"
                          track-by="PHO_ID">
                          <template #noOptions>
                              Không tìm thấy thông tin
                          </template>
                      </multiselect>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w70">Đặc điểm</div>
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input type="checkbox" v-model="thongtin_diadanh.checkDacDiem" @change="changeCheckDiaDanh" class="check" />
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <multiselect
                          :options="thongtin_diadanh.ds_dacdiem"
                          placeholder="Chọn đặc điểm"
                          @input="changeDacDiem"
                          :showLabels="false"
                          :allowEmpty="false"
                          :value="dacdiem_selected"
                          :disabled="!thongtin_diadanh.checkDacDiem"
                          label="dacdiem"
                          track-by="dacdiem_id">
                          <template #noOptions>
                              Không tìm thấy thông tin
                          </template>
                      </multiselect>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="box-form" style="min-height: 210px">
              <div class="legend-title">Thông tin khu vực</div>
              <div class="info-row">
                <div class="key w70">Trạm TC</div>
                <div class="value w20 padt7">
                  <div class="check-action">
                    <input type="checkbox" v-model="thongtin_khuvuc.checkTramTC" @change="changeCheckTramTC" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <multiselect
                      :options="thongtin_khuvuc.ds_tram_tc"
                      placeholder="Chọn trạm TC"
                      @input="changeTramTC"
                      :showLabels="false"
                      :allowEmpty="false"
                      :value="tram_tc_o_selected"
                      :disabled="!thongtin_khuvuc.checkTramTC"
                      label="ten_dv"
                      track-by="donvi_id">
                      <template #noOptions>
                          Không tìm thấy thông tin
                      </template>
                  </multiselect>
                </div>
              </div>
              <div class="info-row">
                <div class="key w70">Khu vực</div>
                <div class="value">
                  <multiselect
                      :options="thongtin_khuvuc.ds_khuvuc"
                      placeholder="Chọn khu vực"
                      @input="changeKhuVuc"
                      :showLabels="false"
                      :allowEmpty="false"
                      :value="khuvuc_o_selected"
                      label="ten_kv"
                      track-by="khuvuc_id">
                      <template #noOptions>
                          Không tìm thấy thông tin
                      </template>
                  </multiselect>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách nhân viên trong khu vực</div>
          <KTableV3 :columns="columnsNVKV"
                :dataSources="dataSourcesNVKV"
                :rowSelectedId="rowSelectedNVKVId"
                id="nhanvien_id"
                @onSelectedRow="onSelectedRowNVKV"
                :allowFilter="true"/>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Danh sách nhân viên địa bàn theo thuê bao
          </div>
          <KTableV3 :columns="columnsNVDBTB"
                :dataSources="dataSourcesNVDBTB"
                :rowSelectedId="rowSelectedNVDBTBId"
                id="id"
                @onSelectedRow="onSelectedRowNVDBTB"
                :allowFilter="true"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from './API'
import moment from 'moment'
//frmTCKhuVucDiaBan.cs
// obj.quan_id = quanld_id;
//                 obj.phuong_id = phuongld_id;
//                 obj.pho_id = phold_id;
//                 obj.ap_id = apld_id;
//                 obj.khu_id = khuld_id;
//                 obj.dacdiem_id = 0;
//                 obj.khuvuc_id = khuvuc_id;
//                 obj.kt_kylai = true;
//                 //obj.loainv_id = Loai_NV.NHANVIEN_KINHDOANH;
//                 obj.donviql_id = Convert.ToInt32(cboDaiVT.SelectedValue);
//                 obj._show = 0;//nhapt
//                 obj.hdtb_id = hdtb_id;
export default {
    name:'ThayDoiKhuVucQuanLyThueBaoModal',
    components:{
        ActionTop,
        KTableV3
    },
    //data is not null, khởi tạo mặc định trước khi truyền vào
    props:{
      data:{
        type:Object,
        default:()=>({
          quan_id:0,
          phuong_id:0,
          pho_id:0,
          ap_id:0,
          khu_id:0,
          dacdiem_id:0,
          khuvuc_id:0,
          donviql_id:0,
          hdtb_id:0
        })
      },
      daucuoi_id:{
        type:Number,
        default:0
      },
      //mặc định false, true chế độ tra cứu tắt action top
      tracuu:{
        type:Boolean,
        default:false
      },
      show:{
        type:Number,
        default:0
      }
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận (F5)',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                }
            ],
            columnsNVKV:[
                {
                    field:'ten_dv',
                    label:'Đơn vị',
                    allowFilter:true
                },
                {
                    field:'ma_nv',
                    label:'Mã nhân viên',
                    allowFilter:true
                },
                {
                    field:'ten_nv',
                    label:'Tên nhân viên',
                    allowFilter:true
                },
                {
                    field:'nhiemvu',
                    label:'Nhiệm vụ',
                    allowFilter:true
                },
                {
                    field:'ten_loainv',
                    label:'Loại nhân viên',
                    allowFilter:true
                },
                {
                    field:'so_dt',
                    label:'Số điện thoại',
                    allowFilter:true
                },
                {
                    field:'ma_kv',
                    label:'Mã khu vực',
                    allowFilter:true
                },
                {
                    field:'ten_kv',
                    label:'Tên khu vực',
                    allowFilter:true
                },

            ],
            dataSourcesNVKV:[],
            columnsNVDBTB:[
                {
                    field:'ten_dv',
                    label:'Đơn vị',
                    allowFilter:true
                },
                {
                    field:'ma_nv',
                    label:'Mã nhân viên',
                    allowFilter:true
                },
                {
                    field:'ten_nv',
                    label:'Tên nhân viên',
                    allowFilter:true
                },
                {
                    field:'loai_nv',
                    label:'Loại nhân viên',
                    allowFilter:true
                },
                {
                    field:'nhiemvu',
                    label:'Nhiệm vụ',
                    allowFilter:true
                },
                {
                    field:'so_dt',
                    label:'Số điện thoại',
                    allowFilter:true
                },
                {
                    field:'daucuoi',
                    label:'Điểm',
                    allowFilter:true
                }
            ],
            dataSourcesNVDBTB:[],
            result:Object.assign({}, this.data),
            thongtin_diadanh:{
              checkQuan:false,
              checkPhuong:false,
              checkPho:false,
              checkAp:false,
              checkKhu:false,
              checkDacDiem:false,

              ds_quan:[],
              ds_phuong:[],
              ds_pho:[],
              ds_ap:[],
              ds_khu:[],
              ds_dacdiem:[],

              quan_selected:null,
              phuong_selected:null,
              pho_selected:null,
              ap_selected:null,
              khu_selected:null,
              dacdiem_selected:null,

            },
            thongtin_khuvuc:{
              ds_tram_tc:[],
              ds_khuvuc:[],
              tram_tc_selected:null,
              khuvuc_selected:null,
              checkTramTC:false
            },

            khuvuc_selected:null,
            rowSelectedNVKVId:-1,
            rowSelectedNVDBTBId:-1

        }
    },
    computed:{
      quan_selected(){
        const quan=this.thongtin_diadanh.ds_quan.find(x=>x.QUAN_ID==this.result.quan_id)
        return quan!=undefined?quan:null
      },
      phuong_selected(){
        const phuong=this.thongtin_diadanh.ds_phuong.find(x=>x.PHUONG_ID==this.result.phuong_id)
        return phuong!=undefined?phuong:null
      },
      pho_selected(){
        const pho=this.thongtin_diadanh.ds_pho.find(x=>x.PHO_ID==this.result.pho_id)
        return pho!=undefined?pho:null
      },
      ap_selected(){
        const ap=this.thongtin_diadanh.ds_ap.find(x=>x.PHO_ID==this.result.ap_id)
        return ap!=undefined?ap:null
      },
      khu_selected(){
        const khu=this.thongtin_diadanh.ds_khu.find(x=>x.PHO_ID==this.result.khu_id)
        return khu!=undefined?khu:null
      },
      dacdiem_selected(){
        const dacdiem=this.thongtin_diadanh.ds_dacdiem.find(x=>x.dacdiem_id==this.result.dacdiem_id)
        return dacdiem!=undefined?dacdiem:null
      },
      tram_tc_o_selected(){
        const tram_tc=this.thongtin_khuvuc.ds_tram_tc.find(x=>x.donvi_id==this.thongtin_khuvuc.tram_tc_selected)
        return tram_tc!=undefined?tram_tc:null
      },
      khuvuc_o_selected(){
        const khuvuc=this.thongtin_khuvuc.ds_khuvuc.find(x=>x.khuvuc_id==this.thongtin_khuvuc.khuvuc_selected)
        return khuvuc!=undefined?khuvuc:null
      }
    },
    methods:{
        showModal() {
            this.$refs["popupThayDoiKhuVucQuanLyThueBao"].show()
        },
        hideModal() {
            this.$refs["popupThayDoiKhuVucQuanLyThueBao"].hide()
        },
        onActionClick(action){
            if(action.id=='chapnhan'){
              this.handleChapNhan()
            }else if(action.id=='capnhat'){
              this.CapNhat()
            }
        },
        handleShowModal(){
            this.dataSourcesNVKV=[]
            this.dataSourcesNVDBTB=[]
            this.result=Object.assign({}, this.data)
            this.khuvuc_selected=null
            this.rowSelectedNVKVId=-1
            this.rowSelectedNVDBTBId=-1
            this.actions=[
              {
                    id:'chapnhan',
                    name:'Chấp nhận (F5)',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
              }
            ]

            this.thongtin_diadanh={
              checkQuan:false,
              checkPhuong:false,
              checkPho:false,
              checkAp:false,
              checkKhu:false,
              checkDacDiem:false,

              ds_quan:[],
              ds_phuong:[],
              ds_pho:[],
              ds_ap:[],
              ds_khu:[],
              ds_dacdiem:[],

              quan_selected:null,
              phuong_selected:null,
              pho_selected:null,
              ap_selected:null,
              khu_selected:null,
              dacdiem_selected:null,
            }
            this.thongtin_khuvuc={
              ds_tram_tc:[],
              ds_khuvuc:[],
              tram_tc_selected:null,
              khuvuc_selected:null,
              checkTramTC:false
            }

            setTimeout(()=>{
                this.initDuLieu()
            },200)
        },
        async handleChapNhan(){
          if(this.show==0){
            let ma_kv=this.khuvuc_o_selected!=null?this.khuvuc_o_selected.ma_kv:''
            //LOAIKV_ID = LOAI_KV.KHUVUC_DIABAN giá trị 4
            this.$emit('accept',{
              hdtb_id:this.result.hdtb_id,
              khuvuc_id:this.thongtin_khuvuc.khuvuc_selected!=null?this.thongtin_khuvuc.khuvuc_selected:0,
              ma_kv:ma_kv,
              loaikv_id:4,
              ngay_cn:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
              nguoi_cn: this.$root.token.getUserName(),
              may_cn:'localhost',
              ip_cn:'127.0.0.1',
              kieukv_id:this.daucuoi_id
            })
            this.hideModal()
          }else{
            //Cập nhật
            await this.CapNhat()
            //this.hideModal()
          }

        },
        async CapNhat(){
          //Kiểm tra dữ liệu
          if(this.thongtin_khuvuc.khuvuc_selected==null){
            this.$toast.warning('Bạn chưa chọn khu vực quản lý thuê bao!')
            return
          }
          
          let data=[
            {
              HDTB_ID:this.result.hdtb_id,
              KHUVUC_ID:this.thongtin_khuvuc.khuvuc_selected,
              LOAIKV_ID:4,
              NGAY_CN:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
              MAY_CN:'localhost',
              NGUOI_CN:this.$root.token.getUserName(),
              IP_CN:'127.0.0.1',
              KIEUKV_ID:this.daucuoi_id
            }
          ]
          this.sp_ins_hdtb_kv_v2(JSON.stringify(data))
        },
        onSelectedRowNVKV(item){

        },
        onSelectedRowNVDBTB(item){

        },
        changeQuan(data){
          this.result.quan_id=data.QUAN_ID
          this.result.phuong_id=0
          this.result.pho_id=0
          this.result.ap_id=0
          this.result.khu_id=0
          this.result.dacdiem_id=0

          this.thongtin_diadanh.ds_phuong=[]
          this.thongtin_diadanh.ds_pho=[]
          this.thongtin_diadanh.ds_ap=[]
          this.thongtin_diadanh.ds_khu=[]
          this.thongtin_diadanh.ds_dacdiem=[]

          this.layDanhSachPhuongTheoQuan(this.result.quan_id)
        },
        async changePhuong(data){
          this.result.phuong_id=data.PHUONG_ID
          this.result.pho_id=0
          this.result.ap_id=0
          this.result.khu_id=0
          this.result.dacdiem_id=0

          this.thongtin_diadanh.ds_pho=[]
          this.thongtin_diadanh.ds_ap=[]
          this.thongtin_diadanh.ds_khu=[]
          this.thongtin_diadanh.ds_dacdiem=[]

          await this.lay_danhsach_pho(data.PHUONG_ID)
          await this.lay_danhsach_ap(data.PHUONG_ID)
          await this.lay_danhsach_khu(data.PHUONG_ID)
        },
        async changePho(data){
          this.result.pho_id=data.PHO_ID
          this.result.dacdiem_id=0
          this.thongtin_diadanh.ds_dacdiem=[]

          await this.lay_danhsach_dacdiem(data.PHO_ID, this.result.phuong_id)
          //Hiển thị khu vực địa danh
          await this.hienthi_ds_khuvu_theo_diadanh()

        },
        async changeAp(data){
          this.result.ap_id=data.PHO_ID
          this.result.dacdiem_id=0
          this.thongtin_diadanh.ds_dacdiem=[]

          await this.lay_danhsach_dacdiem(data.PHO_ID, this.result.phuong_id)
          //Hiển thị khu vực địa danh
          await this.hienthi_ds_khuvu_theo_diadanh()
        },
        async changeKhu(data){
          this.result.khu_id=data.PHO_ID
          this.result.dacdiem_id=0
          this.thongtin_diadanh.ds_dacdiem=[]

          await this.lay_danhsach_dacdiem(data.PHO_ID, this.result.phuong_id)
          //Hiển thị khu vực địa danh
          await this.hienthi_ds_khuvu_theo_diadanh()
        },
        async changeDacDiem(data){
          this.result.dacdiem_id=data.dacdiem_id
          //Hiển thị khu vực địa danh
          await this.hienthi_ds_khuvu_theo_diadanh()
        },
        async changeTramTC(data){
          this.thongtin_khuvuc.tram_tc_selected=data.donvi_id
          //Hiển thị khu vực địa danh
          await this.hienthi_ds_khuvu_theo_diadanh()
        },
        async changeCheckTramTC(){
          await this.hienthi_ds_khuvu_theo_diadanh()
        },
        async changeCheckDiaDanh(){
          await this.hienthi_ds_khuvu_theo_diadanh()
        },

        async changeKhuVuc(data){
          this.thongtin_khuvuc.khuvuc_selected=data.khuvuc_id
          //Hiển thị khu vực nhân viên
          await this.hienthi_danhsach_nhanvien_khuvuc(this.thongtin_khuvuc.khuvuc_selected)

        },

        async layDanhSachQuan(){
          try{
            this.loading(true)
            this.thongtin_diadanh.ds_quan=[]
            let response = await API.lay_danhsach_quan(this.axios)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.thongtin_diadanh.ds_quan=response.data.data
              if(this.result.quan_id&&this.result.quan_id!=0){
                this.thongtin_diadanh.checkQuan=true
                await this.layDanhSachPhuongTheoQuan(this.result.quan_id)
              }
            }else{
              this.thongtin_diadanh.checkQuan=false
              this.thongtin_diadanh.ds_quan=[]
            }
          }catch(e){
            this.loading(false)
            this.$toast.error('Không load được danh sách quận/huyện')
          } 
        },
        async layDanhSachPhuongTheoQuan(quan_id){
          try{
            this.loading(true)
            this.thongtin_diadanh.ds_phuong=[]
            let response = await API.lay_danhsach_phuong(this.axios, quan_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.thongtin_diadanh.ds_phuong=response.data.data
              if(this.result.phuong_id&&this.result.phuong_id!=0){
                this.thongtin_diadanh.checkPhuong=true
                await this.lay_danhsach_pho(this.result.phuong_id)
                await this.lay_danhsach_ap(this.result.phuong_id)
                await this.lay_danhsach_khu(this.result.phuong_id)       
              }
            }else{
              this.thongtin_diadanh.checkPhuong=false
              this.thongtin_diadanh.ds_phuong=[]
            }
          }catch(e){
            this.loading(false)
            this.$toast.error('Không load được danh sách phường')
          } 
        },
        async lay_danhsach_pho(phuong_id){
            try{
              this.loading(true)
              this.thongtin_diadanh.ds_pho=[]
              let response=await API.lay_danhsach_pho(this.axios, phuong_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.thongtin_diadanh.ds_pho=response.data.data
                if(this.result.pho_id&&this.result.pho_id!=0){
                  this.thongtin_diadanh.checkPho=true
                  this.lay_danhsach_dacdiem(this.result.pho_id, phuong_id)
                }else{
                  this.thongtin_diadanh.checkPho=false
                }
              }else{
                this.thongtin_diadanh.ds_pho=[]
              }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được danh sách phố')
            }
        },
        async lay_danhsach_ap(phuong_id){
            try{
              this.loading(true)
              this.thongtin_diadanh.ds_ap=[]
              let response=await API.lay_danhsach_ap(this.axios, phuong_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  this.thongtin_diadanh.ds_ap=response.data.data
                  if(this.result.ap_id&&this.result.ap_id!=0){
                    this.thongtin_diadanh.checkAp=true
                    this.lay_danhsach_dacdiem(this.result.ap_id, phuong_id)
                  }else{
                    this.thongtin_diadanh.checkAp=false
                  }
              }else{
                  this.thongtin_diadanh.ds_ap=[]
              }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được danh sách ấp')
            }
        },
        async lay_danhsach_khu(phuong_id){
            try{
              this.loading(true)
              this.thongtin_diadanh.ds_khu=[]
              let response=await API.lay_danhsach_khu(this.axios, phuong_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  this.thongtin_diadanh.ds_khu=response.data.data
                  if(this.result.khu_id&&this.result.khu_id!=0){
                    this.thongtin_diadanh.checkKhu=true
                    await this.lay_danhsach_dacdiem(this.result.khu_id, phuong_id)
                  }else{
                    this.thongtin_diadanh.checkKhu=false
                  }
              }else{
                  this.thongtin_diadanh.ds_khu=[]
              }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được danh sách khu')
            }
        },
        async lay_danhsach_dacdiem(pho_id, phuong_id){
            try{
              this.loading(true)
              this.thongtin_diadanh.ds_dacdiem=[]
              let response=await API.lay_danhsach_dacdiem(this.axios, pho_id, phuong_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  this.thongtin_diadanh.ds_dacdiem=response.data.data
                  if(this.result.dacdiem_id&&this.result.dacdiem_id!=0){
                    this.thongtin_diadanh.checkDacDiem=true
                  }else{
                    this.thongtin_diadanh.checkDacDiem=false
                  }
              }else{
                  this.thongtin_diadanh.ds_dacdiem=[]
              }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được danh sách đặc điểm')
            }
        },
        async sp_lay_ds_khuvuc_theo_hdtb(daucuoi_id, hdtb_id){
            try{
              this.loading(true)
              let response=await API.sp_lay_ds_khuvuc_theo_hdtb(this.axios, daucuoi_id, hdtb_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' &&response.data.data && response.data.data.length>0) {
                return response.data.data[0]
              }else{
                return null
              }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được khu vực')
              return null
            }
        },
        async sp_lay_diachi_theo_hd_db(id, hd_db){
            try{
              this.loading(true)
              let response=await API.sp_lay_diachi_theo_hd_db(this.axios, id, hd_db)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' &&response.data.data && response.data.data.length>0) {
                return response.data.data[0]
              }else{
                return null
              }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được địa chỉ')
              return null
            }
        },
        async sp_lay_ds_donvi_tramtc(donvi_id){
            try{
              this.loading(true)
              let response=await API.sp_lay_ds_donvi_tramtc(this.axios, donvi_id)
              this.loading(false)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
              }else{
                return []
              }
            }catch(e){
              this.loading(false)
              this.$toast.error('Không load được trạm thi công')
              return []
            }
        },
        async hienthi_ds_khuvu_theo_diadanh(){
          //loaikv_id mặc định 4// LOAI_KV.KHUVUC_DIABAN
          let data={
            ap_id: this.thongtin_diadanh.checkAp?this.result.ap_id:0,
            dacdiem_id: this.thongtin_diadanh.checkDacDiem?this.result.dacdiem_id:0,
            khu_id: this.thongtin_diadanh.checkKhu?this.result.khu_id:0,
            loaikv_id: 4,
            pho_id: this.thongtin_diadanh.checkPho?this.result.pho_id:0,
            phuong_id: this.thongtin_diadanh.checkPhuong?this.result.phuong_id:0,
            quan_id: this.thongtin_diadanh.checkQuan?this.result.quan_id:0,
            tramtc_id: this.thongtin_khuvuc.checkTramTC?(this.thongtin_khuvuc.tram_tc_selected!=null?this.thongtin_khuvuc.tram_tc_selected:0):0
          }
          try{
            this.loading(true)
            let response=await API.sp_lay_khuvuc_theo_diadanh(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.thongtin_khuvuc.ds_khuvuc=response.data.data
              if(this.thongtin_khuvuc.ds_khuvuc.length>0){
                if(this.khuvuc_selected!=null&&this.khuvuc_selected.khuvuc_id!=0){
                  const index=this.thongtin_khuvuc.ds_khuvuc.findIndex(x=>x.khuvuc_id==this.khuvuc_selected.khuvuc_id)
                  if(index>-1){
                    this.thongtin_khuvuc.khuvuc_selected=this.khuvuc_selected.khuvuc_id
                  }else{
                    this.thongtin_khuvuc.khuvuc_selected=this.thongtin_khuvuc.ds_khuvuc[0].khuvuc_id
                  }
                }else{
                  this.thongtin_khuvuc.khuvuc_selected=this.thongtin_khuvuc.ds_khuvuc[0].khuvuc_id
                }
                //handle nhân viên

              }else{
                this.thongtin_khuvuc.khuvuc_selected=null
              }
            }else{
              this.thongtin_khuvuc.khuvuc_selected=null
              this.thongtin_khuvuc.ds_khuvuc=[]
            }
            await this.hienthi_danhsach_nhanvien_khuvuc(this.thongtin_khuvuc.khuvuc_selected!=null?this.thongtin_khuvuc.khuvuc_selected:0)
          }catch(e){
            this.loading(false)
            this.$toast.error('Không load được danh sách khu vực')
          }
        },
        async lay_ds_nhanvien_khuvuc(khuvuc_id){
          try{
            this.loading(true)
            this.dataSourcesNVKV=[]
            let response=await API.lay_ds_nhanvien_khuvuc(this.axios, khuvuc_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.dataSourcesNVKV=response.data.data
                if(this.dataSourcesNVKV.length>0){
                  this.rowSelectedNVKVId=this.dataSourcesNVKV[0].nhanvien_id
                  this.onSelectedRowNVKV(this.dataSourcesNVKV[0])
                }
            }else{
                this.dataSourcesNVKV=[]
            }
          }catch(e){
            this.loading(false)
            this.$toast.error('Không load được danh sách nhân viên khu vực')
          }
        },
        async lay_ds_nhanvien_kv_theo_hdtb_id(hdtb_id){
          try{
            this.loading(true)
            this.dataSourcesNVDBTB=[]
            let response=await API.lay_ds_nhanvien_kv_theo_hdtb_id(this.axios, hdtb_id)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              this.dataSourcesNVDBTB=response.data.data.map((x, index)=>Object.assign(x,{id:index+1}))
              if(this.dataSourcesNVDBTB.length>0){
                this.rowSelectedNVDBTBId=this.dataSourcesNVDBTB[0].id
                this.onSelectedRowNVDBTB(this.dataSourcesNVDBTB[0])
              }
            }else{
              this.dataSourcesNVDBTB=[]
            }
          }catch(e){
              this.loading(false)
              this.$toast.error('Không load được danh sách nhân viên khu vực theo HDTB')
          }
        },
        async sp_ins_hdtb_kv_v2(data){
            try{
                this.loading(true)
                let response = await API.sp_ins_hdtb_kv_v2(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật dữ liệu thành công!')

                    this.hideModal()
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi')
                }
            }
        },
        async hienthi_danhsach_nhanvien_khuvuc(khuvuc_id){
          await this.lay_ds_nhanvien_khuvuc(khuvuc_id)
          await this.lay_ds_nhanvien_kv_theo_hdtb_id(this.result.hdtb_id)
        },
        async initDuLieu(){

          if(this.result.hdtb_id!=0){
            this.actions.unshift({
              id:'capnhat',
              name:'Cập nhật',
              active: true,
              icon_class:'one-reload1'
            })
          }
          
          this.khuvuc_selected=await this.sp_lay_ds_khuvuc_theo_hdtb(this.daucuoi_id, this.result.hdtb_id)

          // Bỏ qua form này không sử dụng, sau sử dụng bổ sung thêm
          // if (thuebao_id != 0)
          //       {
          //           dtDiaDanh = tcF2.Lay_DiaChiLD_Theo_HD_DB(thuebao_id, 2).Tables[0];
          //           kv_id_dachon = Convert.ToInt32(obj.MAP_ID("khuvuc_id", DatabaseConstants.DB2 + ".dbtb_kv", " where thuebao_id =" + thuebao_id + " and loaikv_id=" + LOAI_KV.KHUVUC_DIABAN));
          //       }

          let diachi=await this.sp_lay_diachi_theo_hd_db(this.result.hdtb_id, 1)

          if(diachi!=null){
            Object.assign(this.result, {
              quan_id:diachi.quan_id!=null?diachi.quan_id:0,
              phuong_id:diachi.phuong_id!=null?diachi.phuong_id:0,
              pho_id:diachi.pho_id!=null?diachi.pho_id:0,
              ap_id:diachi.ap_id!=null?diachi.ap_id:0,
              khu_id:diachi.khu_id!=null?diachi.khu_id:0,
              dacdiem_id:diachi.dacdiem_id!=null?diachi.dacdiem_id:0
            })
          }

          if(this.result.donviql_id!=0){
            this.thongtin_khuvuc.ds_tram_tc= await this.sp_lay_ds_donvi_tramtc(this.result.donviql_id)
            this.thongtin_khuvuc.tram_tc_selected=this.result.donviql_id
            this.thongtin_khuvuc.checkTramTC=true
          }else{
            this.thongtin_khuvuc.checkTramTC=false
          }

          await this.layDanhSachQuan()

          await this.hienthi_ds_khuvu_theo_diadanh()

        }
    },
    watch: {
      data: function(newVal, oldVal){
          this.result=Object.assign({}, newVal)
      }
      
  }
}
</script>