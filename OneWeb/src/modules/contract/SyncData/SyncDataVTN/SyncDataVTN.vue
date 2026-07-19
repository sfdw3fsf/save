<template>
  <div class="main-wrapper">
      <breadcrumb :header="header" />
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="page-content">
          <div class="grid-stack-box">
              <div class="box-col box-form" style="width: calc(50% - 8px);">
                  <div class="legend-title">
                      <div class="pull-left">
                          Thông tin tìm kiếm
                      </div>
                      <div class="pull-right">
                          <div class="list-checks">
                              <div class="item">
                                  <div class="check-action">
                                      <input type="radio" class="check" value="0" :checked="trangthai==0" @change="changeTrangThai($event)">
                                      <span class="name">Chưa kích hoạt</span>
                                  </div>
                              </div>
                              <div class="item">
                                  <div class="check-action">
                                      <input type="radio" class="check" value="1" :checked="trangthai==1" @change="changeTrangThai($event)">
                                      <span class="name">Đã kích hoạt</span>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="clearfix"></div>
                  </div>
                  <div class="row">
                      <div class="col-sm-7 col-12">
                          <div class="info-row">
                              <div class="key">Dịch vụ</div>
                              <div class="value">
                                  <div class="select-custom" ref="ds_dichvu">
                                      <select2 :settings="{ dropdownParent: $refs['ds_dichvu'] }" ref="cboDichVu"
                                          v-model="dichvu_selected" class="select2"
                                          :options="dsDichVu.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                                          @change="changeDichVu"
                                      >
                                      </select2>
                                  </div> 
                              </div>
                          </div>
                      </div>
                      <div class="col-sm-5 col-12">
                          <div class="info-row">
                              <div class="key">Loại HĐ/PL</div>
                              <div class="value">
                                  <div class="select-custom" ref="ds_loaihd">
                                      <select2 :settings="{ dropdownParent: $refs['ds_loaihd'] }"
                                          v-model="loaihd_selected" class="select2"
                                          :options="ds_loaihd.map(e=> ({id: e.LOAIHD_ID, text: e.TEN_LOAIHD}))"
                                          @change="changeLoaiHD"
                                      >
                                      </select2>
                                  </div> 
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-sm-7 col-12">
                          <div class="info-row">
                              <div class="key">Quy trình</div>
                              <div class="value">
                                  <div class="select-custom" ref="ds_quytrinh">
                                      <select2 :settings="{ dropdownParent: $refs['ds_quytrinh'] }"
                                          v-model="quytrinh_selected" class="select2"
                                          :options="ds_quytrinh.map(e=> ({id: e.quytrinh_id, text: e.quytrinh}))"
                                          @change="changeQuyTrinh"
                                      >
                                      </select2>
                                  </div> 
                              </div>
                          </div>
                      </div>
                      <div class="col-sm-5 col-12">
                          <div class="info-row">
                              <div class="key">Ngày thực hiện</div>
                              <div class="value">
                                  <div class="input-icon-right">
                                      <k-date-picker v-model="ngay_th" :disabled="true"/>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-sm-7 col-12">
                          <div class="info-row">
                              <div class="key">Số máy</div>
                              <div class="value">
                                  <input type="text" :value="input_matb" @change="e=>input_matb=e.target.value" readonly class="form-control highlight">
                              </div>
                          </div>
                      </div>
                      <div class="col-sm-5 col-12">
                          <div class="info-row">
                              <div class="key">
                                  <div class="check-action">
                                      <input type="checkbox" class="check" v-model="checkLoaiHinhTB" @change="changeCheckLoaiTb">
                                      <span class="name">Loại hình</span>
                                  </div>
                              </div>
                              <div class="value">
                                  <div class="select-custom" ref="ds_loaihinh">
                                      <select2 :settings="{ dropdownParent: $refs['ds_loaihinh'] }"
                                          v-model="loaihinhtb_selected" class="select2"
                                          :options="ds_loaihinhtb_filter.map(e=> ({id: e.LOAITB_ID, text: e.LOAIHINH_TB}))"
                                          :disabled="!checkLoaiHinhTB"
                                      >
                                      </select2>
                                  </div> 
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key">
                          Kiểu yêu cầu
                      </div>
                      <div class="value">
                            <div class="select-custom" ref="ds_kieuld">
                                <select2 :settings="{ dropdownParent: $refs['ds_kieuld'] }"
                                    v-model="kieuld_selected" class="select2"
                                    :options="ds_kieuld.map(e=> ({id: e.kieuld_id, text: e.ten_kieuld}))"
                                    :disabled="true"
                                >
                                </select2>
                            </div> 
                      </div>
                  </div>
                  <div class="legend-title mart20">Thông tin khách hàng</div>
                  <div class="row">
                      <div class="col-sm-7 col-12">
                          <div class="info-row">
                              <div class="key">Mã giao dịch</div>
                              <div class="value">
                                  <input type="text" :value="input_magd" @change="e=>input_magd=e.target.value" readonly class="form-control highlight">
                              </div>
                          </div>
                      </div>
                      <div class="col-sm-5 col-12">
                          <div class="info-row">
                              <div class="key">Ngày yêu cầu</div>
                              <div class="value">
                                  <div class="input-icon-right">
                                      <k-date-picker v-model="ngay_yc" :disabled="true"/>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key">Mã khách hàng</div>
                      <div class="value">
                          <input type="text" :value="input_makh" @change="e=>input_makh=e.target.value" readonly class="form-control">
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key">Tên khách hàng</div>
                      <div class="value">
                          <input type="text" :value="input_tenkh" @change="e=>input_tenkh=e.target.value" readonly class="form-control">
                      </div>
                  </div>
                  <div class="info-row">
                      <div class="key">Địa chỉ KH</div>
                      <div class="value">
                          <input type="text" :value="input_diachikh" @change="e=>input_diachikh=e.target.value" readonly class="form-control">
                      </div>
                  </div>
                  
              </div>
              <div class="gutter gutter-horizontal" style="width: 16px;"></div>
              <div class="box-col box-form" style="width: calc(50% - 8px);">
                  <div class="legend-title">Thông tin dịch vụ đăng ký</div>
                  <KDataGrid
                    ref="gridTTDVGT"
                    :columns="columnsTTDVGT"
                    :dataSource="dataSourcesTTDVGT"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="false"
                    :enabledSelectFirstRow="true"/>
              </div>
          </div>
          <div class="box-form">
              <div class="legend-title">
                  Dánh sách thuê bao
              </div>
              <KDataGrid
                  ref="gridDsThueBao"
                  :columns="columns"
                  :dataSource="dataSources"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :showFilter="true"
                  :showColumnCheckbox="false"
                  :enabledSelectFirstRow="true"
                  :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                  @onRowSelected="onRowSelected"/>
          </div>

      </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import API from './API'
export default {
  name:'SyncDataVTN',
  components:{
    breadcrumb,
    ActionTop,
    KDataGrid,
    KDatePicker
  },
  data(){
    return {
      header: {
          title: "Đồng bộ dữ liệu thuê bao IMS",
          list: [
              { 
                  name: "Lập hợp đồng", 
                  link: { name: "Ui.cards" },
                  active: false
              },
              {
                  name: "Đồng bộ dữ liệu",
                  link: { name: "Ui.buttons" },
                  active: false
              },
              {
                  name: "Đồng bộ dữ liệu VTN",
                  link: { name: "Ui.buttons" },
                  active: true
              },
          ],
      },
      actions:[
          {
              id:'tlstpRefresh',
              name:'Lấy DS',
              active: true,
              icon_class:'one-file-attach1',
              visible:true,
              tooltip:'Lấy danh sách mới',
          },
          {
              id:'tsbtnKichHoat',
              name:'Đồng bộ',
              active: true,
              icon_class:'one-sync1',
              visible:true,
              tooltip:'Đồng bộ VTN',
          },
          {
              id:'tsbtnGiaoPhieu',
              name:'Giao phiếu',
              active: false,
              icon_class:'one-file-arrow-right1',
              visible:true,
              tooltip:'Giao phiếu',
          },
          {
              id:'tsbtnHoanCong',
              name:'Hoàn công',
              active: false,
              icon_class:'one-hoancong',
              visible:true,
              tooltip:'Cập nhật kết quả',
          }
      ],
      columns:[
          {
              fieldName:'ma_tb',
              headerText:'Mã thuê bao',
              allowFiltering:true
          },
          {
              fieldName:'ten_tb',
              headerText:'Tên thuê bao',
              allowFiltering:true
          },
          {
              fieldName:'diachi_ld',
              headerText:'Địa chỉ lắp đặt',
              allowFiltering:true
          },
          {
              fieldName:'loaihinh_tb',
              headerText:'Loại hình thuê bao',
              allowFiltering:true
          },
          {
              fieldName:'ten_status',
              headerText:'Trạng thái đồng bộ',
              allowFiltering:true
          }
      ],
      dataSources:[],
      columnsTTDVGT:[
          {
              fieldName:'ten_dvgt',
              headerText:'Tên dịch vụ',
              allowFiltering:true
          },
          {
              fieldName:'ma_dvgt',
              headerText:'Mã DVGT',
              allowFiltering:true
          },
          {
              fieldName:'ten_kieu_yc',
              headerText:'Kiểu YC',
              allowFiltering:true
          },
          {
              fieldName:'noidung',
              headerText:'Nội dung',
              allowFiltering:true
          }
      ],
      dataSourcesTTDVGT:[],
      trangthai:0,
      ngay_th:moment(new Date()).format('DD/MM/YYYY'),
      ngay_yc:moment(new Date()).format('DD/MM/YYYY'),
      tthd_id:0,
      status:0,
      dsloaihd_id:'',
      dsdichvuvt_id:'',
      dsDichVu:[],
      dichvu_selected:null,
      ds_loaihd:[],
      loaihd_selected:null,
      dichvuvt_id:0,
      loaihd_id:0,
      ds_loaihinhtb:[],
      loaihinhtb_selected:null,
      ds_loaihinhtb_filter:[],
      load:false,
      checkLoaiHinhTB:false,
      ds_kieuld:[],
      kieuld_selected:null,
      ds_quytrinh:[],
      quytrinh_selected:null,
      input_matb:'',
      input_magd:'',
      input_makh:'',
      input_tenkh:'',
      input_diachikh:'',
      quytrinh_id:0,
      dtThaoTac:[],
      all_control:[],
      ds_control:[],
      dvnhan_id:0,
      hdtb_id:0,
      phieu_id:0,
      hdtb_cha_id:0,
      thuebao_cha_id:0,
      dsDVGT:[],
      thuebao_id:0,
      loaitb_id:0




    }
  },
  methods:{
    onActionClick(action){
        if(action.id=='tlstpRefresh'){
            this.LayDS()
        }else if(action.id=='tsbtnKichHoat'){
            this.DongBo()
        }else if(action.id=='tsbtnGiaoPhieu'){
            this.GiaoPhieu()
        }else if(action.id=='tsbtnHoanCong'){
            this.HoanCong()
        }
    },
    async onRowSelected(item){
        this.input_magd=item.ma_gd?item.ma_gd.toString():''
        if(item.ngay_yc){
            this.ngay_yc=moment(item.ngay_yc, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
        }
        this.dvnhan_id=item.donvi_id?item.donvi_id:0
        this.hdtb_id=item.hdtb_id?item.hdtb_id:0
        this.thuebao_id=item.thuebao_id?item.thuebao_id:0
        this.loaitb_id=item.loaitb_id?item.loaitb_id:0
        this.input_makh=item.ma_kh?item.ma_kh.toString():''
        this.input_tenkh=item.ten_kh?item.ten_kh.toString():''
        this.input_diachikh=item.diachi_kh?item.diachi_kh.toString():''
        this.input_matb=item.ma_tb?item.ma_tb.toString():''
        this.kieuld_selected=item.kieuld_id

        this.dsDVGT=await this.lay_ds_dangky_dvgt(this.hdtb_id)
        await this.HienThiDVGT(this.dsDVGT)

        //LoaiHopDong.CHAMDUT_SD=4
        //TrangThaiHD.THANH_TOAN=2
        if(this.tthd_id==2||this.loaihd_selected==4){
            this.phieu_id=item.phieu_id?item.phieu_id:0
        }else if(item.hdtb_cha_id){
            this.hdtb_cha_id=item.hdtb_cha_id?item.hdtb_cha_id:0
        }else if(item.thuebao_cha_id){
            this.thuebao_cha_id=item.thuebao_cha_id?item.thuebao_cha_id:0
        }
    },
    async HienThiDVGT(ds){
        this.dataSourcesTTDVGT=[]
        this.dataSourcesTTDVGT=ds.map(x=>{
            if(x.kieu_yc&&x.kieu_yc==1){
                x.ten_kieu_yc='Đăng ký'
            }else{
                x.ten_kieu_yc='Huỷ'
            }
            return x
        })
    },
    async LayDS(){
        await this.Lay_TrangThai_HD()
        await this.HienThiDanhSachTB('0','0')
    },
    async DongBo(){
        if(this.dataSources.length==0){
            this.$toast.error('Không có thuê bao để thực hiện đồng bộ VTN !')
            return
        }
        let ip_cn=await this.$root.token.getIP()
        console.log('IP_CN', ip_cn)
        let result=await this.fn_frmdongbo_vtn_kichhoat({
            p_quytrinh_id: this.quytrinh_id,
            p_hdtb_id: this.hdtb_id,
            p_ma_tb: this.input_matb,
            p_thuebao_id: this.thuebao_id,
            p_loaihd_id: this.loaihd_id,
            p_loaitb_id: this.loaitb_id,
            p_hdtb_cha_id:this.hdtb_cha_id,
            p_thuebao_cha_id:this.thuebao_cha_id,
            p_ip_cn: ip_cn?ip_cn.toString():'127.0.0.1',
            p_nguoi_cn: this.$root.token.getUserName(),
            p_step: "KICHHOAT"
        })
        if(result&&result.ERROR_CODE==0){
            this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
            await this.HienThiDanhSachTB('0','0')
            //Bỏ qua bước dưới api đã gọi luôn rùi
            //Kích hoạt thành công step--> KICHHOAT_THANHCONG
            // let resultStep2=await this.fn_frmdongbo_vtn_kichhoat({
            //     p_quytrinh_id: this.quytrinh_id,
            //     p_hdtb_id: this.hdtb_id,
            //     p_ma_tb: this.input_matb,
            //     p_thuebao_id: this.thuebao_id,
            //     p_loaihd_id: this.loaihd_id,
            //     p_loaitb_id: this.loaitb_id,
            //     p_hdtb_cha_id:this.hdtb_cha_id,
            //     p_thuebao_cha_id:this.thuebao_cha_id,
            //     p_ip_cn: ip_cn?ip_cn.toString():'127.0.0.1',
            //     p_nguoi_cn: this.$root.token.getUserName(),
            //     p_step: result.STEP
            // })
            // if(resultStep2&&resultStep2.ERROR_CODE==0){
            //     this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
            //     await this.HienThiDanhSachTB('0','0')
            // }else if(resultStep2&&resultStep2.MESSAGE){
            //     this.$toast.error(resultStep2.MESSAGE)
            // }
        }else if(result&&result.MESSAGE){
            this.$toast.error(result.MESSAGE)
        }

    },
    async GiaoPhieu(){
        //LOAI_DV.GIAODICH=4
        let dtTemp=await this.sp_lay_huonggiao_theo_quytrinh(this.quytrinh_id, 4)
        if(dtTemp.length>0){
            let query={
                magd: this.input_magd,
                kieu_id:1,
                hdtb_id:this.hdtb_id,
                huonggiao: dtTemp[0].huonggiao_id,
                trangthaiphieu:1,
                ngaygiao:'0'
            }
            console.log('query', query)
            let routeData = this.$router.resolve({name: 'HandoverTicket', query: query});
            window.open(routeData.href, '_blank')
        }else{
            this.$toast.error('Chưa có hướng giao được gán !')
        }
    },
    async HoanCong(){
        if(this.dataSources.length==0){
            this.$toast.error('Không có thuê bao để thực hiện hoàn công !')
            return
        }
        let ip_cn=await this.$root.token.getIP()
        console.log('IP_CN', ip_cn)
        let result=await this.fn_frmdongbo_vtn_hoancong({
            p_phieu_id: this.phieu_id,
            p_hdtb_id: this.hdtb_id,
            p_thuebao_id:this.thuebao_id,
            p_loaihd_id:this.loaihd_id,
            p_quytrinh_id:this.quytrinh_id,
            p_nhanvien_id:this.$root.token.getNhanVienID(),
            p_donvi_id:this.$root.token.getDonViID(),
            p_nguoi_cn:this.$root.token.getUserName(),
            p_may_cn:ip_cn?ip_cn.toString():'127.0.0.1',
            p_ip_cn:ip_cn?ip_cn.toString():'127.0.0.1'
        })
        if(result=='OK'){
            this.$toast.success('Hoàn công thành công !')
            await this.HienThiDanhSachTB('0','0')
        }else{
            this.$toast.error(result)
        }

    },
    async changeTrangThai(event){
        this.trangthai=event.target.value
    
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=false
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=false
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=false
        await this.Lay_TrangThai_HD()
        await this.HienThiDanhSachTB('0','0')
    },
    async changeDichVu(){
      this.dichvuvt_id=this.dichvu_selected?this.dichvu_selected:0
      this.ds_loaihinhtb_filter=this.ds_loaihinhtb.filter(x=>x.DICHVUVT_ID==this.dichvu_selected)
      if(this.ds_loaihinhtb_filter.length>0){
        this.loaihinhtb_selected=this.ds_loaihinhtb_filter[0].LOAITB_ID
      }
      if(this.dichvuvt_id>0&&this.loaihd_id>0){
        await this.HT_Quytrinh(this.dichvuvt_id, this.loaihd_id)
      }
      //           bangts.HT_Kieu_LD_Combobox(cboKieuLD,0,Convert.ToInt32(loaihd_id));
    },
    async changeLoaiHD(){
        this.loaihd_id=this.loaihd_selected?this.loaihd_selected:0
        this.ds_kieuld=await this.lay_ds_kieu_ld(this.loaihd_selected, 0)
        if(this.ds_kieuld.length>0){
            this.kieuld_selected=this.ds_kieuld[0].kieuld_id
        }
        //           if (Convert.ToInt32(cboLoaiHD.SelectedValue) == LoaiHopDong.DOISO_TB)
        //               dgvThueBao.Columns["clmatb_cu"].Visible = true;
        //           else
        //               dgvThueBao.Columns["clmatb_cu"].Visible = false;  
        if(this.dichvuvt_id>0&&this.loaihd_id>0){
            await this.HT_Quytrinh(this.dichvuvt_id, this.loaihd_id)
        }        
    },
    async changeQuyTrinh(){
        this.quytrinh_id=this.quytrinh_selected?this.quytrinh_selected:0
        await this.HienThiGiaoDien(this.quytrinh_id)
        if(this.load){
            await this.Lay_TrangThai_HD()
            await this.HienThiDanhSachTB('0', '0')
        }
    },
    async HienThiGiaoDien(quytrinh_id){
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].visible=true
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].visible=true
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].visible=true
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=false
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=false
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=false

        this.ds_control=[]
        this.dtThaoTac=await this.sp_lay_thaotac_theo_quytrinh(quytrinh_id, 4)
        for(let i=0;i<this.dtThaoTac.length;i++){
            let controls=this.all_control.filter(x=>x.thaotac_id==this.dtThaoTac[i].thaotac_id)
            this.ds_control=this.ds_control.concat(controls.map(x=>Object.assign(this.dtThaoTac[i],x)))
        }
        console.log('ds_control', this.ds_control)
        for(let i=0;i<this.ds_control.length;i++){
            let control=this.ds_control[i]
            //check với action top
            const index=this.actions.findIndex(x=>x.id==control.control_name)
            if(index>-1){
                if(control.enable==1){
                    this.actions[index].visible=true
                }else{
                    this.actions[index].visible=false
                }
            }
            //handle các control khác trên form, chưa cần thiết
        }


    },
    async HT_Quytrinh(dichvuvt_id, loaihd_id){
        //sp_ht_quytrinh_gdv_kh
        this.ds_quytrinh=await this.sp_ht_quytrinh_gdv_kh(dichvuvt_id, loaihd_id)
        if(this.ds_quytrinh.length>0){
            this.quytrinh_selected=this.ds_quytrinh[0].quytrinh_id
            await this.changeQuyTrinh()
        }
    },
    async changeCheckLoaiTb(){
        await this.Lay_TrangThai_HD()
        await this.HienThiDanhSachTB("0", "0")
    },
    async initDuLieu(){
      //tag 2+4;7+11
      let tag=this.$route.query.tag
      console.log('tag', tag)

      if(tag&&tag!=''){
        let words=tag.split('+')
        if(words.length>0){
          this.tthd_id=Number(words[0])
        }
        if(words.length>1){
          this.dsloaihd_id=words[1].replaceAll(';',',')
        }
        if(words.length>2){
          this.dsdichvuvt_id=words[2].replaceAll(';',',')
        }
        //Dịch vụ vt
        this.dsDichVu=await this.lay_danhsach_dichvuvt(this.dsdichvuvt_id)
        
        if(this.dsDichVu.length>0){
          this.dichvu_selected=this.dsDichVu[0].DICHVUVT_ID
        }
        //Loại Hình TB
        this.ds_loaihinhtb=await this.lay_danhsach_loaihinhtb()
        //all thao tác
        this.all_control=await this.lay_all_thaotac_control()
        //Loại HD
        this.ds_loaihd=await this.lay_danhsach_loaihd(this.dsloaihd_id)
        if(this.ds_loaihd.length>0){
          this.loaihd_selected=this.ds_loaihd[0].LOAIHD_ID
        }
        this.ngay_th=moment(new Date()).format('DD/MM/YYYY') 

        //trigger change
        await this.changeDichVu() 
        await this.changeLoaiHD()

        this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=false
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=false
        this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=false

        if(this.ds_quytrinh.length>0){
            await this.Lay_TrangThai_HD()
            await this.HienThiDanhSachTB('0', '0')
        } 
        this.load=true    

      }else{
        this.tthd_id = 2
        this.dsloaihd_id = '1'
        this.dsdichvuvt_id = '1'
      }
      console.log('tthd_id', this.tthd_id)
      console.log('dsloaihd_id', this.dsloaihd_id)
      console.log('dsdichvuvt_id', this.dsdichvuvt_id)
    },
    async Lay_TrangThai_HD(){
        //LoaiHopDong.CHAMDUT_SD=4
        if(this.loaihd_selected==4){
            //TrangThaiHD.MOI=1
            this.tthd_id=1
        }else{
            //TrangThaiHD.THANH_TOAN=2
            this.tthd_id=2
        }
        if(this.trangthai==0){
            //TRANGTHAI_DONGBO.CHUADONGBO=0
            this.status=0
        }else if(this.trangthai==1){
            //TRANGTHAI_DONGBO.DONGBO_CM=5
            this.status=5
        }
    },
    async HienThiDanhSachTB(magd, matb){
        var vloaitb_id=0
        if(this.checkLoaiHinhTB){
            vloaitb_id=this.loaihinhtb_selected
        }
        let dataResult=await this.lay_dstb_ims_db_vtn({
            vma_gd: magd,
            vma_tb: matb,
            vstatus: this.status,
            vloaihd_id: this.loaihd_selected,
            vnhanvien_id: this.$root.token.getNhanVienID(),
            vdichvuvt_id: this.dichvuvt_id,
            vloaitb_id: vloaitb_id,
            vquytrinh_id: this.quytrinh_id
        })
        if(dataResult.length>0){
            this.dataSources=dataResult
            //Hàm này cấu hình template table sau
            // HienThiAnhTrangThai();
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=this.trangthai==1
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=this.trangthai==0
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=this.trangthai==1
        }else{
            this.input_magd=''
            this.input_matb=''
            this.ngay_yc=moment(new Date()).format('DD/MM/YYYY')
            this.input_makh=''
            this.input_tenkh=''
            this.input_diachikh=''
            this.dataSources=[]               
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnGiaoPhieu')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnKichHoat')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='tsbtnHoanCong')].active=false
            this.Clear()
        }
    },
    Clear(){
        this.ngay_th=moment(new Date()).format('DD/MM/YYYY')
        this.dataSourcesTTDVGT=[]
    },
    async lay_danhsach_dichvuvt(dsdichvuvt_id){
        try{
            this.loading(true)
            let response = await API.lay_danhsach_dichvuvt(this.axios)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              let arrDV=dsdichvuvt_id.split(',')
              return response.data.data.filter(x=>{
                if(arrDV.length==0){
                  return false
                }
                return arrDV.indexOf(x.DICHVUVT_ID.toString())>-1
              })
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_danhsach_loaihd(dsloaihd_id){
        try{
            this.loading(true)
            let response = await API.lay_danhsach_loaihd(this.axios)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              let arr=dsloaihd_id.split(',')
              return response.data.data.filter(x=>{
                if(arr.length==0){
                  return false
                }
                return arr.indexOf(x.LOAIHD_ID.toString())>-1
              })
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_danhsach_loaihinhtb(){
        try{
            this.loading(true)
            let response = await API.lay_danhsach_loaihinhtb(this.axios)
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
    async lay_ds_kieu_ld(loaihd_id, loaitb_id){
        try{
            this.loading(true)
            let response = await API.lay_ds_kieu_ld(this.axios, loaihd_id, loaitb_id)
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
    async sp_ht_quytrinh_gdv_kh(dichvuvt_id, loaihd_id){
        try{
            this.loading(true)
            let response = await API.sp_ht_quytrinh_gdv_kh(this.axios, dichvuvt_id, loaihd_id)
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
    async sp_lay_thaotac_theo_quytrinh(quytrinh_id, loaidv_id){
        try{
            this.loading(true)
            let response = await API.sp_lay_thaotac_theo_quytrinh(this.axios, quytrinh_id, loaidv_id)
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
    async lay_all_thaotac_control(){
      try{
        this.loading(true)
        let response = await API.lay_all_thaotac_control(this.axios)
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
    async lay_dstb_ims_db_vtn(data){
      try{
        this.loading(true)
        let response = await API.lay_dstb_ims_db_vtn(this.axios, data)
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
    async lay_ds_dangky_dvgt(hdtb_id){
      try{
        //this.loading(true)
        let response = await API.lay_ds_dangky_dvgt(this.axios, hdtb_id)
        //this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        }else{
          return []
        }
      }catch(e){
       // this.loading(false)
        return []
      }
    },
    async fn_frmdongbo_vtn_kichhoat(data){
        try{
            this.loading(true)
            let response = await API.fn_frmdongbo_vtn_kichhoat(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return JSON.parse(response.data.data)
            }else{
                if(response&&response.data&&response.data.message){
                    this.$toast.error(response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, đồng bộ không thành công!')
                }
                return null
            }
        }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
                this.$toast.error(e.data.message)
            }else if(e.response&&e.response.data&&e.response.data.message){
                this.$toast.error(e.response.data.message)
            }else{
                this.$toast.error('Đã xảy ra lỗi, đồng bộ không thành công!')
            }
            return null
        }
    },
    async sp_lay_huonggiao_theo_quytrinh(quytrinh_id, loaidv_id){
      try{
        this.loading(true)
        let response = await API.sp_lay_huonggiao_theo_quytrinh(this.axios, quytrinh_id, loaidv_id)
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
    async fn_frmdongbo_vtn_hoancong(data){
      try{
        this.loading(true)
        let response = await API.fn_frmdongbo_vtn_hoancong(this.axios, data)
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
            return response.data.data
        }else{
            if(response&&response.data&&response.data.message){
                this.$toast.error(response.data.message)
            }else{
                this.$toast.error('Đã xảy ra lỗi, hoàn công không thành công!')
            }
            return null
        }
      }catch(e){
        this.loading(false)
        if(e.data&&e.data.message){
            this.$toast.error(e.data.message)
        }else if(e.response&&e.response.data&&e.response.data.message){
            this.$toast.error(e.response.data.message)
        }else{
            this.$toast.error('Đã xảy ra lỗi, hoàn công không thành công!')
        }
        return null
      }
    },
  },
  mounted(){
    setTimeout(()=>{
      this.initDuLieu()
    }, 500)
  }
}
</script>
<style>
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
  width: 100% !important;
}

.select2-container--default .select2-selection--single {
  border-color: #E0E0E0;
  outline: none;
}

.select2-dropdown {
  border: 1px solid #E0E0E0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
  font-family: "FontAwesome";
  border: 0px;
  top: 0px;
  height: 1.428rem;
  bottom: 0px;
  line-height: 1.428rem;
  left: 5px;
  margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
  content: "";
}

.select2-container--default .select2-search--dropdown .select2-search__field {
  border: 0px;
  outline: none;
}
::-webkit-input-placeholder {
   font-style: italic;
}
:-moz-placeholder {
   font-style: italic;  
}
::-moz-placeholder {
   font-style: italic;  
}
:-ms-input-placeholder {  
   font-style: italic; 
}
</style>