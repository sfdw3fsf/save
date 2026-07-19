<!-- 07/07/2023: Form khong su dung
<template>
<b-modal 
    :id="modalId" 
    size="lg" 
    :data="data"
    :isStrictMode="isStrictMode"
    no-close-on-backdrop
    hide-footer 
    hide-header 
    hide-header-close 
    @shown="focusMyElement"
    @hidden="onModalHidden"
    body-class="modal-body p-0">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
                <span class="icon one-notepad"></span> Chọn địa chỉ
            </div>
            <div
                class="close -ap icon-close"
                data-dismiss="modal"
                @click="$bvModal.hide(modalId)">
            </div>
        </div>

        <div class="popup-body">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w100"></div>
                            <div class="value" style="color:blue">{{diachi.daydiachi}}</div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">Tỉnh/TP</div>
                            <div class="value">
                                <div class="select-custom" ref="tinh">
                                    <SelectExt v-model="diachi.tinh_id"  
                                      :settings="{dropdownParent: $refs['tinh']}"
                                      @change="onChangeTinh()"                            
                                      :dataSource="dsTinh" 
                                      dataTextField="TENTINH"
                                      dataValueField="TINH_ID"/>  
                                    </div> 
                                </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Quận/Huyện</div>
                            <div class="value">
                                <div class="select-custom" ref="quan">
                                    <SelectExt v-model="diachi.quan_id"    
                                      :settings="{dropdownParent: $refs['quan']}"                             
                                      :dataSource="dsQuan" 
                                      @change="onChangeQuan()" 
                                      dataTextField="TEN_QUAN"
                                      dataValueField="QUAN_ID"/>  
                                 </div> 
                                </div>
                        </div>
                     
                        <div class="info-row">
                            <div class="key w100">Phường/Xã</div>
                            <div class="value">
                                <div class="select-custom" ref="phuong">
                                    <SelectExt v-model="diachi.phuong_id"    
                                      :settings="{dropdownParent: $refs['phuong']}"                             
                                      :dataSource="dsPhuong" 
                                      @change="onChangePhuong()" 
                                      dataTextField="TEN_PHUONG"
                                      dataValueField="PHUONG_ID"/>  
                                 </div> 
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key w100">Số nhà</div>
                            <div class="value">
                                <input type="text" v-model.trim="diachi.so_nha" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">Đường/Phố</div>
                            <div class="value padt7 w20">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="isPho">
                                    <span class="name"></span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="pho" >
                                    <SelectExt v-model="diachi.pho_id"   :disabled="!isPho"
                                      :settings="{dropdownParent: $refs['pho']}"                             
                                      :dataSource="dsPho" 
                                      @change="onChangePho()" 
                                      dataTextField="TEN_PHO"
                                      dataValueField="PHO_ID"/>  
                                 </div> 
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Ngõ/Ấp</div>
                            <div class="value padt7 w20">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="isAp">
                                    <span class="name"></span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ap">
                                    <SelectExt v-model="diachi.ap_id"    :disabled="!isAp"
                                      :settings="{dropdownParent: $refs['ap']}"                             
                                      :dataSource="dsAp" 
                                      @change="onChangeAp()" 
                                      dataTextField="TEN_PHO"
                                      dataValueField="PHO_ID"/>  
                                 </div> 
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Khu</div>
                            <div class="value padt7 w20">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="isKhu">
                                    <span class="name"></span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="khu" >
                                    <SelectExt v-model="diachi.khu_id"    :disabled="!isKhu"
                                      :settings="{dropdownParent: $refs['khu']}"                             
                                      :dataSource="dsKhu" 
                                      @change="onChangeKhu()" 
                                      dataTextField="TEN_PHO"
                                      dataValueField="PHO_ID"/>  
                                 </div> 
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Đặc điểm</div>
                            <div class="value padt7 w20">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="isDacDiem" :disabled="!isPho">
                                    <span class="name"></span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="dacdiem" >
                                    <SelectExt v-model="diachi.dacdiem_id"  :disabled="!isDacDiem"
                                      :settings="{dropdownParent: $refs['khu']}"                             
                                      :dataSource="dsDacDiem" 
                                     
                                      dataTextField="DACDIEM"
                                      dataValueField="DACDIEM_ID"/>  
                                 </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w100">Địa chỉ</div>
                            <div class="value">
                                <textarea name="" v-model="thaydoiDiaChi" class="form-control" style="height: 100px;resize: none;" readonly></textarea>
                            </div>
                        </div>
                    </div>               
                </div>

            </div>
        <div class="group-buttons -bottom center">
            <button class="btn btn-second nocorner" style="width: 120px;" v-b-modal.ModalChonToaDo>
                <span class="icon nc-icon-glyph location_map-pin"></span> Vị trí
            </button>
            <button class="btn btn-second nocorner" style="width: 120px;" @click="chapnhan()">
                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
            </button>
            <button class="btn btn-second nocorner" style="width: 120px;" @click="$bvModal.hide(modalId)">
                <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
            </button>
        </div>
    </div>
    <ModalChonToaDo  idModalChonToaDo="ModalChonToaDo" @XacNhanToaDo="XacNhanToaDo" :position="getPosition" :diachi="diachi.diachimoi"></ModalChonToaDo>
</div>
</b-modal>    
</template>
<script>
export default {
  name: "popupDiaChiCNTT",
  props: ["modalId", "data","isStrictMode"],
  created: async function () {
    this.dsTinh=[]          
    this.diachi.tinh_id = null
  },
  components: { },
  computed: {
    thaydoiDiaChi: function () {
      if (this.tentinh != "") {
        this.diachi.diachimoi = this.tentinh;
        this.diachi.diachimoi = this.tenquan + ", " + this.diachi.diachimoi;
        this.diachi.diachimoi = this.tenphuong + ", " + this.diachi.diachimoi;
        if (this.isPho && this.diachi.pho_id != 0)
          this.diachi.diachimoi = this.tenpho + ", " + this.diachi.diachimoi;
        if (this.isAp && this.diachi.ap_id != 0)
          this.diachi.diachimoi = this.tenap + ", " + this.diachi.diachimoi;
        if (this.isKhu && this.diachi.khu_id != 0)
          this.diachi.diachimoi = this.tenkhu + ", " + this.diachi.diachimoi;
        if (this.diachi.so_nha != null && this.diachi.so_nha != "")
          this.diachi.diachimoi = this.diachi.so_nha + ", " + this.diachi.diachimoi;
        return this.diachi.diachimoi;
      }
      return "";
    },
    
    getPosition () {
      return {
        lat: parseFloat(this.diachi.lat),
        lng: parseFloat(this.diachi.lng)
      }
    },
  },
  watch: {
  },
  methods: {
    async LayDSTinh() {
        var rs = await this.$root.context.get("/web-hopdong/lapdatmoi/LayDSTinh", { });
        this.cboTinh.DataSource = rs.data;
        if(rs.data && rs.data.length>0) {
            this.cboTinh.Value = rs.data[0][this.loaitb_id==303?"TINH_IQMS_ID":"TINH_SI_ID"];
            this.cboTinh.ValueText = rs.data[0]["TENTINH"];
        }
    },
    async LayDSQuan()
    {
        var rs = await this.$root.context.get(`/tichhop/hkd/LayDanhSachQuan?loaitb_id=${this.loaitb_id}&tinh_id=${this.cboTinh.Value}`, { });
        this.cboQuan.DataSource = rs.data;
        if(rs.data && rs.data.length>0) {
            if(this.diachi_phuong) {
                this.cboQuan.Value = this.diachi_phuong.split(',')[1];
                this.cboQuan.ValueText = rs.data.find(x=>x.Id==this.cboQuan.Value)['Ten'];
            }
            else {
                this.cboQuan.Value = rs.data[0]["Id"];
                this.cboQuan.ValueText = rs.data[0]["Ten"];
            }
        }
    },
    async LayDSPhuong()
    {
        var rs = await this.$root.context.get(`/tichhop/hkd/LayDanhSachPhuong?loaitb_id=${this.loaitb_id}&tinh_id=${this.cboTinh.Value}&quan_id=${this.cboQuan.Value}`, { });
        this.cboPhuong.DataSource = rs.data;
        if(rs.data && rs.data.length>0) {
            if(this.diachi_phuong) {
                this.cboPhuong.Value = this.diachi_phuong.split(',')[0];
                this.cboPhuong.ValueText = rs.data.find(x=>x.Id==this.cboPhuong.Value)['Ten'];
            }
            else {
                this.cboPhuong.Value = rs.data[0]["Id"];
                this.cboPhuong.ValueText = rs.data[0]["Ten"];
            }
        }
        // string loaihinh = "HKD";
        // if (loaitb_id == 303)
        // {
        //     loaihinh = "IQMS";
        // }
        // string end_point = host + "api/"+ loaihinh +"/LayDanhSachPhuong/" + tinh_id.ToString() + "/" + quan_id.ToString();
        // RestClient rs = new RestClient(end_point);
        // string dulieu = rs.MakeCNTTRequest("", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTU1ODcwNjUsImlzcyI6ImR1b25ncCIsImF1ZCI6ImR1b25ncCJ9.dU3b3O-VWxIFv05lDs34Zi5AGUAFdx_n2oV_MX-DpHw", "test");
        // List<QuanHKD> lst = new List<QuanHKD>();
        // lst = JsonConvert.DeserializeObject<List<QuanHKD>>(dulieu);
        // cboPhuong.Properties.DataSource = lst;
        // cboPhuong.Properties.DisplayMember = "Ten";
        // cboPhuong.Properties.ValueMember = "Id";
        // cboPhuong.Properties.View.OptionsView.ShowColumnHeaders = false;
        // cboPhuong.Properties.View.Columns[0].Visible = false;
        // cboPhuong.Properties.View.Columns[2].Visible = false;
        // cboPhuong.Properties.View.OptionsView.ShowAutoFilterRow = true;

        // if (!string.IsNullOrEmpty(diachi_phuong))
        // {
        //     string[] dc = diachi_phuong.Split(',');
        //     cboPhuong.EditValue = Convert.ToInt32(dc[0]);
        //     txtDiaChiDangKy.Text = diachi_text;
        //     txtDuongPho.Text = diachi_text.Replace(cboTinh.Text, "");
        //     txtDuongPho.Text = txtDuongPho.Text.Replace(cboQuan.Text, "");
        //     txtDuongPho.Text = txtDuongPho.Text.Replace(cboPhuong.Text, "");
        //     string remove_char = "";
        //     for (int i = txtDuongPho.Text.Length - 1; i > -1; i--)
        //     {
        //         if (txtDuongPho.Text[i] != ' ' && txtDuongPho.Text[i] != ',')
        //         {
        //             break;
        //         }
        //         remove_char = remove_char + txtDuongPho.Text[i].ToString();
        //     }
        //     string remove_char_reverse = Reverse(remove_char);
        //     txtDuongPho.Text = txtDuongPho.Text.Replace(remove_char_reverse, "");
        //     txtDuongPho.Text = txtDuongPho.Text.Trim();
        //     diachi_phuong = "";
        //     diachi_text = "";
        // }
    }
//       onModalHidden() {
//           // this.dsTinh=[]
//           // this.diachi.tinh_id = null   
//           // this.diachi.quan_id = null 
//           // this.diachi.phuong_id = null        
//           // this.diachi.pho_id = null 
//           // this.diachi.ap_id = null 
//           // this.diachi.khu_id = null 
//           // this.diachi.dacdiem_id = null 
//           // this.diachi.so_nha=""
//           // this.diachi.diachimoi=""
//           // this.diachi.daydiachi=""
//       },
//       LowerCasePropertyList(obj) {
//         return obj.map( function( item ){
//             for(var key in item){
//                 var upper = key.toLowerCase();
//                 // check if it already wasn't uppercase
//                 if( upper !== key ){ 
//                     item[ upper ] = item[key];
//                     delete item[key];
//                 }
//             }
//             return item;
//         });
//       },
//       focusMyElement: async function () {
//         await this.getTinh();
//         if(this.data){
//           var tsdiachi = Object.assign({}, this.data);
//           tsdiachi = this.LowerCasePropertyList([tsdiachi])[0];
//         this.diachi.daydiachi = tsdiachi.diachi
//         if (tsdiachi.tinh_id && tsdiachi.tinh_id != 0) {
//         this.diachi.tinh_id = tsdiachi.tinh_id;
//         this.tentinh = this.dsTinh.filter(
//           (x) => x.TINH_ID == this.diachi.tinh_id
//         )[0].TENTINH; 
  
//         this.diachi.quan_id = tsdiachi.quan_id; 
//                 await this.getQuan();
             
//         this.diachi.phuong_id=tsdiachi.phuong_id;
//           await this.getPhuong();
//         if(tsdiachi.phuong_id){
//         if(tsdiachi.pho_id && tsdiachi.pho_id!=0) {
//           this.diachi.pho_id=tsdiachi.pho_id;
//           this.isPho=true}
       
//          if(tsdiachi.ap_id && tsdiachi.ap_id!=0) {
//             this.diachi.ap_id=tsdiachi.ap_id;
//            this.isAp=true}
      
//          if(tsdiachi.khu_id && tsdiachi.khu_id!=0){
//              this.diachi.khu_id=tsdiachi.khu_id;
//            this.isKhu=true}
//             await this.getPhoApKhu(); 
//         }
//         this.diachi.so_nha = tsdiachi.so_nha;
//         this.diachi.daydiachi = tsdiachi.diachi;
//         this.diachi.diachimoi = tsdiachi.diachi;

//       }else{
//         if(this.dsTinh && this.dsTinh.length>0)
  
//       this.diachi.tinh_id = this.dsTinh[0].TINH_ID;
//               this.tentinh = this.dsTinh.filter(
//           (x) => x.TINH_ID == this.diachi.tinh_id
//         )[0].TENTINH;  
//      await  this.getQuan();
//        }
//        }else{
//         if(this.dsTinh && this.dsTinh.length>0) 
//          this.diachi.tinh_id = this.dsTinh[0].TINH_ID;
//               this.tentinh = this.dsTinh.filter(
//           (x) => x.TINH_ID == this.diachi.tinh_id
//         )[0].TENTINH;  
//      await  this.getQuan();
//        }
//     },

//     getTinh: async function () {
//       try {
//         this.$root.showLoading(true);
//         var rs = await this.$root.context.get(
//           "/web-cabman/bando_tuyencot/lay_thongtin_tinh"
//         );
//         this.dsTinh.push(rs.data);
   
//       } catch (error) {
//         console.log(error)
//       } finally {
//         this.$root.showLoading(false);
//       }
//     },
//     getQuan: async function () {
//       try {
//         this.$root.showLoading(true);
//         var rs = await this.$root.context.get(
//           "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + this.diachi.tinh_id
//         );
//         this.dsQuan = rs.data;
//         if(this.diachi.quan_id && this.dsQuan)
//          this.tenquan = this.dsQuan.filter(
//            (x) => x.QUAN_ID == this.diachi.quan_id
//          )[0].TEN_QUAN;
//         else if((!this.diachi.quan_id || this.diachi.quan_id<=0) && this.dsQuan) {
//           this.diachi.quan_id = this.dsQuan[0].QUAN_ID;
//           this.tenquan = this.dsQuan[0].TEN_QUAN;
//         }
//         this.onChangeQuan();
//       } catch (error) {
//       } finally {
//         this.$root.showLoading(false);
//       }
//     },
//     getPhuong: async function () {
//       try {
//         this.$root.showLoading(true);
//         var rs = await this.$root.context.get(
//           "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + this.diachi.quan_id
//         );
//         this.dsPhuong = rs.data;
//         if(this.diachi.phuong_id && this.dsPhuong){
//           var listphuong = this.dsPhuong.filter(
//           x => x.PHUONG_ID == this.diachi.phuong_id);
//           if(listphuong && listphuong.length>0)
//             this.tenphuong = listphuong[0].TEN_PHUONG;
//           else if(this.dsPhuong && this.dsPhuong.length>0) {
//             this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID;
//             this.tenphuong = this.dsPhuong[0].TEN_PHUONG;
//           }
//         }
//         else if((!this.diachi.phuong_id || this.diachi.phuong_id<=0) && this.dsPhuong) {
//           this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID;
//           this.tenphuong = this.dsPhuong[0].TEN_PHUONG;
//         }
//         this.onChangePhuong();
//       } catch (error) {
//         console.log(error)

//       } finally {
//         this.$root.showLoading(false);
//       }
//     },
//     getPhoApKhu: async function () {
//       try {
//         this.$root.showLoading(true);
//         var rs = await this.$root.context.get(
//           "/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/" +
//             this.diachi.phuong_id
//         );
//         if (rs.data != null) {
//           this.dsPho = rs.data.dsPho;
//           if(this.diachi.pho_id && this.dsPho)
//            this.tenpho = this.dsPho.filter(
//           (x) => x.PHO_ID == this.diachi.pho_id
//          )[0].TEN_PHO;
//           this.dsAp = rs.data.dsAp;
//          if(this.diachi.ap_id && this.dsAp)
//            this.tenap = this.dsAp.filter(
//           (x) => x.PHO_ID == this.diachi.ap_id
//         )[0].TEN_PHO;
//           this.dsKhu = rs.data.dsKhu;
//            if(this.diachi.khu_id && this.dsKhu)
//             this.tenkhu = this.dsKhu.filter(
//           (x) => x.PHO_ID == this.diachi.khu_id
//         )[0].TEN_PHO;
//         }
//       } catch (error) {
//       } finally {
//         this.$root.showLoading(false);
//       }
//     },
//     getDacDiem: async function () {
//       try {
//         this.$root.showLoading(true);
//         var rs = await this.$root.context.get(
//           "/web-cabman/bando_tuyencot/lay_ds_dacdiem",
//           {
//             phuong_id: this.diachi.phuong_id == null ? 0 : this.diachi.phuong_id,
//             pho_id: this.diachi.pho_id == null ? 0 : this.diachi.pho_id,
//             ap_id: this.diachi.ap_id == null ? 0 : this.diachi.ap_id,
//             khu_id: this.diachi.khu_id == null ? 0 : this.diachi.khu_id,
//           }
//         );
//         this.dsDacDiem = rs.data;
//         if(this.dsDacDiem && this.dsDacDiem.length>0) {
//           this.isDacDiem = true;
//           if(!this.diachi.dacdiem_id || this.diachi.dacdiem_id <=0) this.diachi.dacdiem_id = this.dsDacDiem[0].DACDIEM_ID;
//         }
//         else { 
//           this.isDacDiem = false;
//           this.diachi.dacdiem_id = 0;
//         }
//       } catch (error) {
//       } finally {
//         this.$root.showLoading(false);
//       }
//     },
//     saveDiachi: async function () {
//       try {
//         this.$root.showLoading(true);
//       } catch (error) {
//         this.$toast.error("Lưu ảnh thất bại");
//       } finally {
//         this.$root.showLoading(false);
//       }
//     },
//     onChangeTinh: async function () {
//       await this.getQuan();
//       this.tentinh = this.dsTinh.filter(
//         (x) => x.TINH_ID == this.diachi.tinh_id
//       )[0].TENTINH;
//     },
//     onChangeQuan: async function () {
//       await this.getPhuong();
//             this.tenquan = this.dsQuan.filter(
//           (x) => x.QUAN_ID == this.diachi.quan_id
//         )[0].TEN_QUAN;
//     },
//     onChangePhuong: async function () {
//       await this.getPhoApKhu();
//       var listphuong = this.dsPhuong.filter(x => x.PHUONG_ID == this.diachi.phuong_id);
//       if(listphuong && listphuong.length>0)
//           this.tenphuong = listphuong[0].TEN_PHUONG;
//     },
//     onChangePho: async function () {
//       await this.getDacDiem();
//       this.tenpho = this.dsPho.filter(
//           (x) => x.PHO_ID == this.diachi.pho_id
//          )[0].TEN_PHO;
//     },
//     onChangeKhu: async function () {
//       await this.getDacDiem();
//    this.tenkhu = this.dsKhu.filter(
//           (x) => x.PHO_ID == this.diachi.khu_id
//         )[0].TEN_PHO;
//     },
//     onChangeAp: async function () {
//       await this.getDacDiem();
//         this.tenap = this.dsAp.filter(
//           (x) => x.PHO_ID == this.diachi.ap_id
//         )[0].TEN_PHO;
//     },
//     chapnhan: function () {
//       if (
//         this.isStrictMode &&
//         ((!this.isPho && !this.isAp && !this.isKhu) ||
//           (this.isPho && this.tenpho == "") ||
//           (this.isAp && this.tenap == "") ||
//           (this.isKhu && this.tenkhu == ""))
//       ) {
//         this.$toast.warning("Hãy chọn Phố / Ấp / Khu");
//         return;
//       }
//       if(this.dsDacDiem && this.dsDacDiem.length>0 && (!this.isDacDiem || this.diachi.dacdiem_id==0)) {
//         this.$toast.warning("Hãy chọn đặc điểm!");
//         return;
//       }
//       this.$emit("xacnhan", this.diachi);
//       this.$bvModal.hide(this.modalId);
//     },
//     XacNhanToaDo (position) {
//       this.diachi.lat = position.lat;
//       this.diachi.lng = position.lng;
//     },
  },
  data: function () {
    return {
    //   diachi: {
    //     diachi_id: null,
    //     tinh_id:null ,
    //     quan_id: null,
    //     phuong_id: null,
    //     pho_id: null,
    //     ap_id: null,
    //     khu_id:null,
    //     dac_diem_id: null,
    //     so_nha: "",
    //     daydiachi: "",
    //     diachimoi: "",
    //     lat:0,
    //     lng:0,
    //   },
      dsTinh: [],
      dsQuan: [],
      dsPhuong: [],
    //   dsPho: [],
    //   dsAp: [],
    //   dsKhu: [],
    //   dsDacDiem: [],
    //   isPho: false,
    //   isAp: false,
    //   isKhu: false,
    //   isDacDiem: false,
      tentinh: "",
      tenquan: "",
      diachi_phuong: "",
      diachi_text: "",
      loaitb_id: 0,

    //   tenpho: "",
    //   tenap: "",
    //   tenkhu: "",
    };
  },
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style> -->