<template>
  <b-modal
    ref="popupChuyenTo"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chuyển tổ
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="title fw6 upper">THAY ĐỔI ĐƠN VỊ THI CÔNG</div>
        </div>
        <div class="box-form">
          <div class="legend-title">Thông tin phiếu yêu cầu</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Số máy/Acc</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click="searchAccount">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input type="text" ref="inputMaTB" :value="input_matb" @change="e=>input_matb=e.target.value" @keypress.enter="onEnterMaTB" class="form-control highlight" />
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Ngày BH</div>
                <div class="value">
                  <ejs-datetimepicker class="form-control"
                        v-model="ngay_bh"
                        :showClearButton="false"
                        @change="changeNgayBH"
                        :format="dateFormat"/>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w90">Dịch vụ</div>
                    <div class="value">
                      <input type="text" :value="input_dichvu" @change="e=>input_dichvu=e.target.value" class="form-control" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w70">Loại hình</div>
                    <div class="value">
                      <input type="text" :value="input_loaihinh" @change="e=>input_loaihinh=e.target.value" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Tên thuê bao</div>
                <div class="value">
                  <input type="text" :value="input_ten_tb" @change="e=>input_ten_tb=e.target.value" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Địa chỉ LĐ</div>
                <div class="value">
                  <input type="text" :value="input_diachi_ld" @change="e=>input_diachi_ld=e.target.value" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Ghi chú</div>
                <div class="value">
                  <textarea
                    name=""
                    :value="input_ghichu" @change="e=>input_ghichu=e.target.value"
                    class="form-control"
                    style="height: 100px; resize: none"
                  ></textarea>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Người giao</div>
                <div class="value">
                  <input type="text" :value="input_nguoigiao" @change="e=>input_nguoigiao=e.target.value" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Nơi nhận</div>
                <div class="value">
                  <input type="text" :value="input_noinhan" @change="e=>input_noinhan=e.target.value" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Trạng thái BH</div>
                <div class="value">
                  <input type="text" :value="input_trangthai_bh" @change="e=>input_trangthai_bh=e.target.value" class="form-control" />
                </div>
              </div>
              <div class="legend-title mart20">Thông tin thay đổi đơn vị</div>
              <div class="info-row">
                <div class="key w90">Đơn vị QL</div>
                <div class="value">
                  <multiselect
                        :options="ds_donvi_ql"
                        placeholder="Chọn đơn vị"
                        @input="changeDonViQL"
                        :showLabels="false"
                        :allowEmpty="false"
                        :value="donvi_ql_o_selected"
                        label="ten_ht"
                        track-by="donvi_id">
                        <template #noOptions>
                            Không tìm thấy thông tin
                        </template>
                        <template #noResult>
                            Không tìm thấy thông tin
                        </template>
                    </multiselect>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Đơn vị</div>
                <div class="value">
                    <multiselect
                        :options="ds_tram_vt"
                        placeholder="Chọn trạm VT"
                        @input="changeTramVT"
                        :showLabels="false"
                        :allowEmpty="false"
                        :value="tram_vt_o_selected"
                        label="ten_dv"
                        track-by="donvi_id">
                        <template #noOptions>
                            Không tìm thấy thông tin
                        </template>
                        <template #noResult>
                            Không tìm thấy thông tin
                        </template>
                    </multiselect>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Nội dung</div>
                <div class="value">
                  <input type="text" ref="inputNoiDung" :value="input_noidung" @change="e=>input_noidung=e.target.value" class="form-control" />
                </div>
              </div>
            </div>
          </div>
          <div class="nav tabs tab-over">
            <a v-if="visableCT_DHXL" href="#tab1" data-toggle="tab" @click.prevent="changeTab(1)" :class="{active: currentTab==1}">Chi tiết điều hành thi công</a>
            <a v-if="visableDsLacHuong" href="#tab2" data-toggle="tab" @click.prevent="changeTab(2)" :class="{active: currentTab==2}">Danh sách phiếu báo hỏng lạc hướng</a>
          </div>
          <div class="tab-content">
            <div v-if="visableCT_DHXL" class="tab-pane"  :class="{active: currentTab==1}" id="tab1" >
                <DataGrid
                ref="ref_gridChiTiet"
                    :columns="DhSuCo.headers"
                    :dataSource="DhSuCo.data"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"
                     @selectedItemsChanged="gridChiTiet_RowsSelected"
                     @rowClicked="gridChiTiet_Selected"
                     @rowDataBound="gridChiTiet_RowDataBound"
                     @dataBound="gridChiTiet_DataBound"

                    />
            </div>
            <div v-if="visableDsLacHuong" class="tab-pane" :class="{active: currentTab==2}" id="tab2">
                <DataGrid
                    :columns="DsPhieuBaoHong.headers"
                    :dataSource="DsPhieuBaoHong.data"
                    :showColumnCheckbox="true"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"
                    @selectedItemsChanged="gridBaoHong_RowsSelected"
                    @rowClicked="NAP_DS_LUOI"

                />
            </div>

          </div>
        </div>
      </div>
      <SearchAccountModal ref="searchAccountModal" @form-close="formCloseSearchAccount"/>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import moment from 'moment'
import ChuyenToAPI from './ChuyenToAPI'
import SearchAccountModal from '../../../contract/setup/SurveyRequest/popups/SearchAccountModal.vue'
export default {
    name:'ChuyenToModal',
    props:{
        phieu_id:{
            type:Number,
            default:0
        },
        baohong_id:{
            type:Number,
            default:0
        },
        hdtb_id:{
            type:Number,
            default:0
        },
        vtthd_id:{
            type:Number,
            default:-1
        },
        vttbh_id:{
            type:Number,
            default:-1
        },
        tag_hdbh:{
            type:Number,
            default:-1
            //default:2
        }
    },
    components:{
        ActionTop,
        SearchAccountModal
    },
    data(){
        return {
            actions:[
                {
                    id:'laytt',
                    name:'Lấy TT',
                    active: true,
                    visible:false,
                    icon_class:'one-file-attach'
                },
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
                }
            ],
            currentTab:1,
            DhSuCo: {
                headers: [
                    {
                        fieldName: 'huonggiao',
                        headerText: 'Hướng giao',
                        allowFiltering: true,
                        allowSorting: false,
                        textAlign: 'left',
                        isGroupedColumn: true
                    },
                    {
                        fieldName: 'dvg',
                        headerText: 'Đơn vị giao',
                        allowFiltering: true,
                        allowSorting: false,
                        width: 90,
                        textAlign: 'left'
                    },
                    {
                        fieldName: 'nv_giao',
                        headerText: 'Nhân viên giao',
                        allowFiltering: true,
                        allowSorting: false,
                        width: 120
                    },
                    {
                        fieldName: 'ngay_giao',
                        headerText: 'Ngày giao',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nd_giao',
                        headerText: 'Nội dung giao',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'dvn',
                        headerText: 'Đơn vị nhận',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ngay_tra',
                        headerText: 'Ngày trả',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nd_tra',
                        headerText: 'Nội dung trả',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'trangthai_ph',
                        headerText: 'Trạng thái phiếu',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nv_th',
                        headerText: 'Nhân viên TH',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ngay_th',
                        headerText: 'Ngày TH',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ghichu_th',
                        headerText: 'Nội dung TH',
                        allowFiltering: true,
                        allowSorting: false
                    }
                ],
                gridGroupSettings: {
                    columns: ['huonggiao']
                },
                data: [],
                selectedItems: []
            },
            DsPhieuBaoHong: {
                headers: [
                    {
                        fieldName: 'ma_tb',
                        headerText: 'Số máy/Acc',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ma_tb',
                        headerText: 'Số ảo',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'loaihinh_tb',
                        headerText: 'Loại hình',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ten_tb',
                        headerText: 'Tên thuê bao',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'diachi_ld',
                        headerText: 'Địa chỉ LĐ',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'donvinhan',
                        headerText: 'Đơn vị nhận',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'diachi_dau',
                        headerText: 'Địa chỉ LĐ (Điểm đầu)',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'diachi_cuoi',
                        headerText: 'Địa chỉ LĐ (Điểm cuối)',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ngay_bh',
                        headerText: 'Ngày BH',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'goi_kt',
                        headerText: 'Gói KT',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'kenh_tn',
                        headerText: 'Kênh tiếp nhận',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'chitieu_tg',
                        headerText: 'Chỉ tiêu TG',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'tgxl',
                        headerText: 'Giờ XL',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'gio_ck',
                        headerText: 'Giờ CK',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'gio_conlai',
                        headerText: 'Giờ còn lại',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'mucdo',
                        headerText: 'Mức độ',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ds_nhanvien_th',
                        headerText: 'Nhân viên TH',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'tinhtrang',
                        headerText: 'Tình trạng',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ten_plkh',
                        headerText: 'Phân loại KH',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'dienthoai_lh',
                        headerText: 'Số liên hệ',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'luot_bh',
                        headerText: 'Lượt BH',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'lan_pa',
                        headerText: 'KH gọi',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nguyennhan',
                        headerText: 'Nguyên nhân',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'trangthai_bh',
                        headerText: 'Trạng thái',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ngaygiao',
                        headerText: 'Ngày giao',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nd_giao',
                        headerText: 'Nội dung giao',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'lan_ks',
                        headerText: 'Lần KS',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ngay_sd',
                        headerText: 'Ngày SD',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ghichu_hong',
                        headerText: 'Ghi chú hỏng',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ghichu_th',
                        headerText: 'Ghi chú TH',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'lydoton',
                        headerText: 'Lý do tồn',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ghichuton',
                        headerText: 'Ghi chú tồn',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ten_dv',
                        headerText: 'Đơn vị giao',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nhanvienql',
                        headerText: 'Nhân viên QL',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ten_cap',
                        headerText: 'Cáp gốc',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'doi_cap',
                        headerText: 'Đôi số',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ten_kc',
                        headerText: 'Kết cuối',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'vitri',
                        headerText: 'Vị trí',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'tientrinh',
                        headerText: 'Tiến trình',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'hensuatu',
                        headerText: 'Hẹn sửa từ',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'hensuaden',
                        headerText: 'Hẹn sửa đến',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ten_kv',
                        headerText: 'Khu vực',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nvkt_db',
                        headerText: 'Nhân viên kỹ thuật ',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nvkd_db',
                        headerText: 'Nhân viên kinh doanh',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'nd_tra_con',
                        headerText: 'Nội dung trả',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'ngaytra_con',
                        headerText: 'Ngày trả',
                        allowFiltering: true,
                        allowSorting: false
                    },
                    {
                        fieldName: 'chuyenmang',
                        headerText: 'NCC khác',
                        allowFiltering: true,
                        allowSorting: false
                    }
                ],
                data: [],
                selectedItems: []
            },
            ngay_bh:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            dateFormat: "dd/MM/yyyy HH:mm:ss",
            visableDsLacHuong:false,
            visableCT_DHXL:true,
            ds_donvi_ql:[],
            donvi_ql_selected:null,
            ds_tram_vt:[],
            tram_vt_selected:null,
            input_matb:'',
            input_dichvu:'',
            input_loaihinh:'',
            input_ten_tb:'',
            input_diachi_ld:'',
            input_ghichu:'',
            input_nguoigiao:'',
            input_noinhan:'',
            input_trangthai_bh:'',
            input_noidung:'',
            trangthaibh_id:0,
            IS_USING_LACHUONG_BH:false,
            thuebao_id:0,
            selIndex: []
        }
    },
    computed:{
        donvi_ql_o_selected(){
            const donvi_ql=this.ds_donvi_ql.find(x=>x.donvi_id==this.donvi_ql_selected)
            return donvi_ql!=undefined?donvi_ql:null
        },
        tram_vt_o_selected(){
            const tram_vt=this.ds_tram_vt.find(x=>x.donvi_id==this.tram_vt_selected)
            return tram_vt!=undefined?tram_vt:null
        }
    },
    methods:{
        showModal() {
            this.$refs["popupChuyenTo"].show()
        },
        hideModal() {
            this.$refs["popupChuyenTo"].hide()
        },
        handleShowModal(){
            this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=true
            this.currentTab=1
            this.ngay_bh=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.DhSuCo.data=[]
            this.DhSuCo.selectedItems=[]
            this.DsPhieuBaoHong.data=[]
            this.DsPhieuBaoHong.selectedItems=[]
            this.visableDsLacHuong=false
            this.visableCT_DHXL=true
            this.ds_donvi_ql=[]
            this.donvi_ql_selected=null
            this.ds_tram_vt=[]
            this.tram_vt_selected=null
            this.input_matb=''
            this.input_dichvu=''
            this.input_loaihinh=''
            this.input_ten_tb=''
            this.input_diachi_ld=''
            this.input_ghichu=''
            this.input_nguoigiao=''
            this.input_noinhan=''
            this.input_trangthai_bh=''
            this.input_noidung=''
            this.trangthaibh_id=0
            this.IS_USING_LACHUONG_BH=false
            this.thuebao_id=0
            this.selIndex= []
            setTimeout(()=>{
                this.initDuLieu()
            },500)

        },
        onActionClick(action){
            if(action.id=='laytt'){
                this.nap_ds_lachuong(0)
            }else if(action.id=='capnhat'){
                this.capnhat()
            }
        },
        changeTab(tab){
            this.currentTab=tab
        },
        changeNgayBH(data){
            this.ngay_bh=moment(new Date(data.value)).format('DD/MM/YYYY HH:mm:ss')
        },
        searchAccount(){
            this.$refs.searchAccountModal.showModal()
        },
        async changeDonViQL(data){
            this.donvi_ql_selected=data.donvi_id
            if(this.baohong_id>0&&this.IS_USING_LACHUONG_BH){
                if(this.donvi_ql_selected==0&&this.tag_hdbh==-1){
                    //LOAI_DV.BH_DVXLDH=636
                    this.ds_tram_vt=await this.lay_ds_tramvt(this.donvi_ql_selected, 636)
                }else{
                    //LOAI_DV.VETINH = 5
                    this.ds_tram_vt=await this.lay_ds_tramvt(this.donvi_ql_selected, 5)
                }
            }else{
                this.ds_tram_vt=await this.lay_ds_tramvt(this.donvi_ql_selected, 5)
            }

        },
        gridChiTiet_RowsSelected: function (items) {
            this.DhSuCo.selectedItems = items
        },
        gridBaoHong_RowsSelected: function (items) {
            this.DsPhieuBaoHong.selectedItems = items
        },
        NAP_DS_LUOI(i, item) {
            this.ngay_bh=item.ngay_bh!=null?item.ngay_bh:''
            this.input_matb=item.ma_tb!=null?item.ma_tb:''
            this.input_loaihinh=item.loaihinh_tb!=null?item.loaihinh_tb:''
            this.input_ten_tb=item.ten_tb!=null?item.ten_tb:''
            this.input_diachi_ld=item.diachi_ld!=null?item.diachi_ld:''
            this.input_ghichu=item.ghichu!=null?item.ghichu:''
            this.input_trangthai_bh=item.trangthai_bh!=null?item.trangthai_bh:''
        },

        async gridChiTiet_Selected(i, item) {
            console.log('gridChiTiet_Selected', item)
            this.input_nguoigiao=item.nv_giao
            this.input_noinhan=item.dvn

            this.tram_vt_selected=item.donvi_nhan_id
            // let data={
            //     id_neo: 'count(*)',
            //     in_table: 'giaophieu',
            //     in_dk: 'where ttph_id =1 and  donvi_nhan_id in (select donvi_id from  admin.donvi_ldv where loaidv_id in (5,504,71)) and ttph_id = 1 and phieu_id='+item.phieu_id
            // }
            var check = '0'
            if(this.hdtb_id==0&&this.baohong_id!=0){
                check = '1'
            }else if(this.hdtb_id!=0&&this.baohong_id==0){
                check=await this.fn_tt_giaophieu(item.phieu_id)
            }
            this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=!(check=='0')

        },
        async gridChiTiet_RowDataBound(args) {
            try {
                if (args.data['phieu_id'] == this.phieu_id) {
                    this.selIndex.push(parseInt(args.row.getAttribute('aria-rowindex')))
                    await this.gridChiTiet_Selected(this.selIndex, args.data)
                }
            } catch (err) {
                console.log('gridChiTiet_RowDataBound', err, args)
            }
        },
        async gridChiTiet_DataBound(args) {
            try {
                if (this.selIndex.length == 0 && this.DhSuCo.data != null && this.DhSuCo.data.length > 0) {
                    this.selIndex.push(0)
                    await this.gridChiTiet_Selected(this.selIndex, args.data)
                }
                if (this.selIndex.length > 0) {
                    this.$refs.ref_gridChiTiet.grid.selectRows(this.selIndex)
                    this.selIndex = []
                }
            } catch (err) {
                console.log( 'gridChiTiet_DataBound ~ err', err, args)
            }
        },
        async capnhat(){
            if(this.input_noidung.trim()==''){
                this.$toast.error('Bạn chưa nhập nội dung chuyển tổ!')
                this.$refs.inputNoiDung.focus()
                return
            }
            if(this.tram_vt_selected==null){
                this.$toast.error('Bạn chưa chọn tổ thi công!')
                return
            }

            let jsondata = this.DsPhieuBaoHong.selectedItems.map((x) => ({ ischeck: 1, PHIEU_ID: x.phieu_id, BAOHONG_ID: x.baohong_id }))
            let data={
                vtag_hdbh: this.tag_hdbh,
                vbaohong_id: this.baohong_id,
                vhdtb_id: this.hdtb_id,
                vphieu_id: this.phieu_id,
                vttbh_id: this.vttbh_id,
                vdonvinhan_id: this.tram_vt_selected,
                vdonviql_id: this.donvi_ql_selected,
                vnhanvien_id: this.$root.token.getNhanVienID(),
                vnoidung: this.input_noidung.trim(),
                vdanhsach_json:jsondata
            }
            let result = await this.capnhat_chuyento(data)
            if(result.code==1){
                this.$toast.success(result.message)
                this.$emit('success')
                this.hideModal()
            }else{
                this.$toast.error(result.message)
            }
        },
        changeTramVT(data){
            this.tram_vt_selected=data.donvi_id

        },
        async onEnterMaTB(){
            if(this.input_matb.trim()==''){
                return
            }
            //
            let dsBaoHong = await this.lay_ds_baohong_theo_ma_tb({
                ma_tb: this.input_matb.trim(),
                dichvuvt_id: 0
            })
            if(dsBaoHong.length>0){
                if(dsBaoHong.length==1){

                }else{
                    this.$toast.error('Mở form frmTraCuu_ThueBao!')
                }
            }else{
                this.$toast.error('Không tìm thấy thông tin')
                return
            }

            if(this.tag_hdbh!=-1){
                if(this.tag_hdbh == 2){
                    // Chua lam
                    // NAP_DS_LACHUONG(Convert.ToInt64(ds.Tables[0].Rows[index]["thuebao_id"]));
                    this.thuebao_id=dsBaoHong[0].thuebao_id
                    await this.nap_ds_lachuong(this.thuebao_id)

                }
            }else{
                await this.lay_tt_baohong_chuyento_chung()
            }

        },
        formCloseSearchAccount(data){
            this.input_matb=data.ma_tb
            this.$refs.inputMaTB.focus()
        },
        async initDuLieu(){
            //khởi tạo tham số
            let thamso = await this.lay_ds_thamso_md()
            const index=thamso.findIndex(x=>x.ma_ts=='BAOHONG_LH')
            this.IS_USING_LACHUONG_BH=index>-1
            console.log('IS_USING_LACHUONG_BH', this.IS_USING_LACHUONG_BH)
            //
            this.actions[this.actions.findIndex(x=>x.id=='laytt')].visible=false
            if(this.tag_hdbh!=-1){
                this.currentTab=2
                this.visableCT_DHXL=false
                this.visableDsLacHuong=true

                //bangts.HT_donvi_theo_loaidv(cboDonViQL, LOAI_DV.DONVIQL_LD.ToString());
                //LOAI_DV.DONVIQL_LD=3
                this.ds_donvi_ql=await this.lay_ds_donvi_ql("3")


                this.actions[this.actions.findIndex(x=>x.id=='laytt')].visible=true

                //NAP_DS_LACHUONG(0);
                await this.nap_ds_lachuong(0)


            }else{
                //LOAI_DV.DONVIQL_LD=3
                this.ds_donvi_ql=await this.lay_ds_donvi_ql("3")
                //LOAI_DV.BH_QLDH=635
                var temp=[]
                if(this.baohong_id > 0 && this.IS_USING_LACHUONG_BH){
                    temp=await this.lay_ds_donvi_ql("635")
                }

                this.ds_donvi_ql=this.ds_donvi_ql.concat(temp)

                await this.lay_tt_baohong_chuyento_chung()


            }
        },
        async lay_ds_donvi_ql(loaidv_id){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.lay_ds_donvi_ql(this.axios, loaidv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_tramvt(donvi_id, loaidv_id){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.lay_ds_tramvt(this.axios, donvi_id, loaidv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_dvcha_theo_dvcon(donvi_id, loaidv_id){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.lay_dvcha_theo_dvcon(this.axios, donvi_id, loaidv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async hienthi_ds_phieuth_chuyento(baohong_id, hdtb_id){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.hienthi_ds_phieuth_chuyento(this.axios, baohong_id, hdtb_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_baohong_theo_ma_tb(data){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.lay_ds_baohong_theo_ma_tb(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_thamso_md(){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.lay_ds_thamso_md(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_tt_baohong_chuyento_chung(){
            // Dữ liệu test
            // {
            //     "vbaohong_id": 3218086,
            //     "vhdtb_id": 0,
            //     "vtthd_id":0,
            //     "vphieu_id":3658682
            // }
            let data={
                vbaohong_id: this.baohong_id,
                vhdtb_id: this.hdtb_id,
                vphieu_id: this.phieu_id,
                vtthd_id: this.vtthd_id
            }
            let dataBH=await this.lay_tt_baohong_chuyento_chung_api(data)
            await this.HienThi_Thongtin_bh(dataBH)

        },
        async HienThi_Thongtin_bh(data){
            if(this.baohong_id>0&&data.length>0){
                this.ngay_bh=moment(data[0].NGAY_BH,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
                this.input_matb=data[0].MA_TB!=null?data[0].MA_TB:''
                this.input_dichvu=data[0].TEN_DVVT!=null?data[0].TEN_DVVT:''
                this.input_loaihinh=data[0].LOAIHINH_TB!=null?data[0].LOAIHINH_TB:''
                this.input_ten_tb=data[0].TEN_TB!=null?data[0].TEN_TB:''
                this.input_diachi_ld=data[0].DIACHI_LD!=null?data[0].DIACHI_LD:''
                this.input_ghichu=data[0].GHICHU_HONG!=null?data[0].GHICHU_HONG:''
                this.input_trangthai_bh=data[0].TRANGTHAI_BH!=null?data[0].TRANGTHAI_BH:''
                this.trangthaibh_id=data[0].TTBH_ID!=null?data[0].TTBH_ID:''
                //     if (DatabaseConstants.DB1.ToUpper() != "ADMIN_HNI")
                //     {
                //         //???????
                //         //KieuNV: Chưa rõ tại sao lại lấy lại phieu_id, REM tạm đôi với HNI.
                //         phieu_id = Convert.ToInt64(dtBH.Rows[0]["phieu_id"]);
                //     }

                await this.HienThi_DS_PhieuTH(this.baohong_id)
            }
            if(this.hdtb_id>0&&data.length>0){
                this.ngay_bh=moment(data[0].NGAY_YC,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
                this.input_matb=data[0].MA_TB!=null?data[0].MA_TB:''
                this.input_dichvu=data[0].TEN_DVVT!=null?data[0].TEN_DVVT:''
                this.input_loaihinh=data[0].LOAIHINH_TB!=null?data[0].LOAIHINH_TB:''
                this.input_ten_tb=data[0].TEN_TB!=null?data[0].TEN_TB:''
                this.input_diachi_ld=data[0].DIACHI_LD!=null?data[0].DIACHI_LD:''
                this.input_ghichu=data[0].GHICHU!=null?data[0].GHICHU:''
                this.input_trangthai_bh=data[0].TRANGTHAI_HD!=null?data[0].TRANGTHAI_HD:''
                this.trangthaibh_id=data[0].TTHD_ID!=null?data[0].TTHD_ID:''

                await this.HienThi_DS_PhieuTH(this.baohong_id)

                let vdvn_id = data[0].DONVI_NHAN_ID
                let vdonvi_ql_id = 0
                //LOAI_DV.DONVIQL_LD=3
                let donvicha = await this.lay_dvcha_theo_dvcon(vdvn_id, 3)
                if (donvicha != null && donvicha.length > 0) {
                    vdonvi_ql_id = donvicha[0].donvi_id
                }
                if (vdonvi_ql_id == 0) {
                    //this.$toast.error('Không tìm thấy đơn vị quản lý!')
                    return
                }
                this.donvi_ql_selected=vdonvi_ql_id
                const donvi=this.ds_donvi_ql.find(x=>x.donvi_id==this.donvi_ql_selected)
                if(donvi!=undefined){
                    await this.changeDonViQL(donvi)
                    this.tram_vt_selected=vdvn_id
                }else{
                    this.tram_vt_selected=null
                }
            }
        },
        async HienThi_DS_PhieuTH(baohong_id){
            this.DhSuCo.data=await this.hienthi_ds_phieuth_chuyento(baohong_id, this.hdtb_id)
        },
        async nap_ds_lachuong(thuebao_id){
            this.DsPhieuBaoHong.data=await this.lay_ds_phieu_bh_lachuong(0, thuebao_id)
        },
        async lay_ds_phieu_bh_lachuong(dichvuvt_id, thuebao_id){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.lay_ds_phieu_bh_lachuong(this.axios, dichvuvt_id, thuebao_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_tt_baohong_chuyento_chung_api(data){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.lay_tt_baohong_chuyento_chung(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async fn_tt_giaophieu(phieu_id){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.fn_tt_giaophieu(this.axios, phieu_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return '0'
                }
            }catch(e){
                this.loading(false)
                return '0'
            }
        },
        async capnhat_chuyento(data){
            try{
                this.loading(true)
                let response = await ChuyenToAPI.capnhat_chuyento(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return {
                        code:1,
                        message: response.data.data
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        return{
                            code:0,
                            message: response.data.message
                        }

                    }else{
                        return{
                            code:0,
                            message: 'Cập nhật chuyển tổ không thành công!'
                        }
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return{
                        code:0,
                        message: e.data.message
                    }
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return{
                        code:0,
                        message: e.response.data.message
                    }
                }else{
                    return{
                        code:0,
                        message: 'Cập nhật chuyển tổ không thành công!'
                    }

                }
            }
        },


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
