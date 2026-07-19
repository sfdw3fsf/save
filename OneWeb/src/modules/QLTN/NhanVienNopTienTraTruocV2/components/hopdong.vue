<template>

    <div class="" id="mdHopDong">
        <!-- <div class="modal-dialog" style="width: 100%; max-width: 600px;"> -->
            <div class="modal-content popup-box">
                <!-- <div class="popup-header">
                    <div class="title">Chọn người giới thiệu</div>
                    <div class="close -ap icon-close" data-dismiss="modal" @Click="onClose">

                    </div>
                </div> -->
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href='javascript: void(0)'  :class="{'disabled': isDisableLayDS}"  @click.prevent="onLayDanhSach">
                                <span class="icon  one-reload1" ></span> Lấy danh sách
                            </a>
                        </li>
                        <li>
                            <a href='javascript: void(0)'  :class="{'disabled': isDisableChapNhan}" @click="onClickAccept" >
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                            </a>
                        </li>

                        <li>
                            <a href="javascript: void(0)" @click="onClose">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>


                    </ul>
                </div>

                <!-- noi dung -->
                <div class="popup-body">
                        <div class="box-form">
                             <div class="legend-title">Thông tin tìm kiếm
                            </div>

                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                            <div class="info-row">
                                                <div class="key w90">Từ ngày</div>
                                                <div class="value ">
                                                     <ejs-datepicker class="form-control bg-none bor0 fw6 text h24" :format="ngay.dateFormat"  v-model="tuNgay">
                                                </ejs-datepicker>
                                                </div>
                                            </div>
                                            <div class="info-row">
                                            <div class="key w90">Đến ngày</div>
                                            <div class="value">
                                                    <ejs-datepicker class="form-control bg-none bor0 fw6 text h24" :format="ngay.dateFormat"  v-model="denNgay">
                                                </ejs-datepicker>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-8 col-12">
                                        <div class="info-row">
                                            <div class="key w90">Tổng tiền</div>
                                            <div class="value ">
                                                <!-- <input type="text" class="form-control" v-model="form.tongTien"> -->
                                                <vue-numeric currency='' separator=',' v-model="form.tongTien" class="form-control" type="numeric">
                                                </vue-numeric>
                                            </div>
                                        </div>
                                        <div class="info-row">
                                            <div class="key w90">Lý do</div>
                                            <div class="value">
                                                <textarea name=""  class="form-control" style="height: 50px;resize: none;" v-model="form.lyDo"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            <div class="legend-title">Danh sách hợp đồng
                            </div>

                        <div class="table-content" >
                                <DataGrid ref="refGridHD"
                                            :dataSource="dsHopDong"
                                            :columns="[
                                                    {fieldName: 'MA_GD', headerText: 'Mã giao dịch', allowFiltering: true, allowSorting:true,width:70},
                                                    {fieldName: 'MA_TB', headerText: 'Mã thuê bao', allowFiltering: true, allowSorting:true,width:100 },
                                                    {fieldName: 'TEN_TB', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting:true},
                                                    {fieldName: 'NGAY_TT', headerText: 'Ngày thanh toán', allowFiltering: true, allowSorting:true,width:70 },
                                                    {fieldName: 'TONGTIEN', headerText: 'Tổng tiền', allowFiltering: true, allowSorting:true,width:70},
                                                    {fieldName: '', headerText: 'Xóa', allowFiltering: true, allowSorting:true,width:50, template:btnTemplate, cssClass: 'text-center',visible:kieu===1?false: true}]"
                                            :showFilter="true"
                                            :enablePagingServer="false"
                                            :allowPaging="true"
                                            :enabledSelectFirstRow="false"
                                            :showColumnCheckbox='kieu === 1 ? true : false'
                                            @rowClicked= "RowGridChange"
                                            @checkBoxChange="checkBoxHDChange">
                                </DataGrid>
                        </div>
                    </div>

                </div>


            </div>
    </div>
</template>
<style scoped>
a.disabled { color:lightgray !important; }
a.disabled:hover { color:lightgray !important; }
.disabled { pointer-events: none; color:lightgray; border-color:lightgray; }
</style>
<script>
import moment from 'moment'
import EventBus from '@/utils/eventBus'
import API from '../../api/QuanLyDaiLy'
import Vue from 'vue'
export default {

    props: ["nhanvien_id","kieu","dailynop_tienhd","nopNHid"],
   data: function () {
    return {
        isDisableChapNhan:false,
        isDisableLayDS:false,
        dsHopDong:[],
        first:true,
        outData:[],
        tuNgay:'',
        denNgay:'',
        rec_dele:[],
        ngay: {
                    dateVal: new Date(),
                    dateFormat: "dd/MM/yyyy",
            },
        form:{
        tongTien:0,
        lyDo:''
        },
           btnTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-primary lh14 pad3" @click="btnDelete" title="Chi tiết nộp">
                                    <span class="-ap icon-close"></span>
                                </button></div>`,
                    data () {
                        return { data: {} };
                    },
                    methods: {
                        btnDelete() {
                           EventBus.$emit('onDelete', this.data);
                           //console.log('sạd');
                        }
                    }})
                }
            },

    }},
    created:async function(){
        console.log(this.dailynop_tienhd);
        if (this.kieu===1)
        {
            this.isDisableChapNhan=false;
            this.isDisableLayDS=false;
            this.denNgay=new Date();
            this.tuNgay = new Date(new Date().getFullYear(), 0, 1);
            this.outData=this.dailynop_tienhd;
            await this.getDSHopDongChuaThu(this.nhanvien_id,moment(this.tuNgay).format('DD/MM/yyyy'),moment(this.denNgay).format('DD/MM/yyyy'));
            if (this.dailynop_tienhd.length>0)
            {
                this.checkGridLoad1();
            }
        }
        else
        {
            this.isDisableChapNhan=true;
            this.isDisableLayDS=true;
            this.tuNgay=new Date();
            this.denNgay=new Date();
            await this.getChiTietNopTienNH(this.nopNHid);
            this.checkGridLoad2();
                    // if (this.dailynop_tienhd.length>0)
                    // {
                    //     this.checkGridLoad2();
                    // }
        }
     EventBus.$on('onDelete', this.onDeleteHandler);
    },
//      computed: {
//     grid: function(){
//       return this.$refs[`refGridHD`].grid;
//     }
//   },

    destroyed () {
        EventBus.$off('onDelete', this.onDeleteHandler);
    },
    methods:{
        checkGridLoad1(){
            let tongtien=0;
            let matb='';
             let row=0;
                    let selectArray=[];
                    this.dsHopDong.forEach((el) => {
                        this.outData.forEach((item) => {
                        if (el.HDTB_ID == item.HDTB_ID) {
                            tongtien = tongtien + item.TONGTIEN;
                            matb = matb+item.MA_TB+',';
                            selectArray.push(row);
                        }
                        });
                        row=row+1;
                    });
                    // this.tabData[this.currentTab] = [... this.tabData[this.currentTab]];
                    setTimeout(() => this.$refs.refGridHD.grid.selectRows(selectArray), 1000);
                    this.form.tongTien = tongtien;
                    this.form.lyDo='Nộp tiền trả trước :'+matb+'('+tongtien+')';
        },
        checkGridLoad2(){
            let tongtien=0;
            let matb='';
            let row=0;
            let selectArray=[];
                    if (this.dsHopDong.length>0)
                    this.dsHopDong.forEach((el) => {
                        tongtien=tongtien+el.TONGTIEN;
                         matb=matb+el.MA_TB+',';
                        selectArray.push(row);
                        row=row+1;

                    });

                    setTimeout(() => this.$refs.refGridHD.grid.selectRows(selectArray), 1000);
                    this.form.tongTien=tongtien;
                    this.form.lyDo='Nộp tiền trả trước :'+matb+'('+tongtien+')';
        },
        onClose(){
         this.$bvModal.hide('mdHopDong');
        },
        getDSHopDongChuaThu: async function(nhanVienId,tuNgay,denNgay){

             try {
                 this.loading(true);
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/quan-ly-dai-ly/ds-phieu-chua-nop-tien-hop-dong?nhanVienId=${nhanVienId}&tuNgay=${tuNgay}&denNgay=${denNgay}`
                );
                //if (rs.data.length>0)
                    this.dsHopDong = rs.data;
                    this.dsHopDong = rs.data.map(item => ({...item,NGAY_TT: moment(item.NGAY_TT,'DD/MM/YYYY').format('MM/DD/YYYY')}))
                    this.loading(false);
            } catch (error) {}
        },
        getChiTietNopTienNH: async function(nopnh_id){

             try {
                 this.loading(true);
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/quan-ly-dai-ly/chi-tiet-nop-tien-hop-dong/${nopnh_id}`
                );
                //if (rs.data.length>0)
                    this.dsHopDong = rs.data.map(item => ({...item,NGAY_TT: moment(item.NGAY_TT,'DD/MM/YYYY').format('MM/DD/YYYY')}))
                    this.loading(false);
            } catch (error) {}
        },

        onDeleteHandler(){
            this.$bvModal.msgBoxConfirm(
                            "Bạn có chắc chắn xóa thu tiền hợp đòng thuê bao này không?",
                            {
                                title: "Xác nhận hành động",
                                centered: true,
                                size: "sm",
                                okTitle: 'Có',
                                cancelTitle: 'không',
                            }
                            )
                            .then((value) => {
                                if (value) {
                                    console.log(this.rec_dele)
                                            API.xoaChiTietNopNH(this.axios,'hdkhId='+this.rec_dele.HDKH_ID+'&hdtbId='+this.rec_dele.HDTB_ID)
                                                                            .then(response => {
                                                                                this.loading(false);
                                                                                if (response.data.error_code === 'BSS-00000000') {
                                                                                    this.$toast.success('Xóa phiếu thành công');
                                                                                    this.getChiTietNopTienNH(this.nopNHid);
                                                                                    this.checkGridLoad2();
                                                                                }
                                                                            })
                                                                            .catch(error => {
                                                                                this.loading(false);
                                                                                console.log(error);
                                                                            })
                                    } else {
                                    return;
                                    }

                            });
        },

        onLayDanhSach: async function(){
            await this.getDSHopDongChuaThu(this.nhanvien_id,moment(this.tuNgay).format('DD/MM/yyyy'),moment(this.denNgay).format('DD/MM/yyyy'));
        },
        RowGridChange(...data){
            this.rec_dele=data[1];
            // let  danhsach=this.outData;
            let danhsach = []
            let tongtien=0;
            let matb='';
            let dong_xoa=null;
            if (this.kieu===1)
            {
                // let row=0;
                //     if (danhsach.length>0)
                //     {
                //         danhsach.forEach((el) => {
                //                 if (el.HDTB_ID===data[1].HDTB_ID)
                //                     dong_xoa=row;
                //                 row=row+1;
                //             });
                //     }
                //     console.log('dong_xoa',dong_xoa)
                //     if (dong_xoa===null)
                //         danhsach.push(data[1]);
                //     else
                //         danhsach.splice(dong_xoa,1);
                let dsSelected = this.$refs.refGridHD.getSelectedRecords() || [];
                let isCheck = dsSelected.includes(data[1])
                danhsach = dsSelected
                if(!isCheck) {
                    danhsach.push(data[1])
                } else {
                    danhsach = danhsach.filter(item => item.HDTB_ID != data[1].HDTB_ID)
                }

                    danhsach.forEach((el) => {
                                    tongtien=tongtien+el.TONGTIEN;
                                    matb=matb+el.MA_TB+',';
                            });

                this.outData=[...danhsach];
                this.form.tongTien=tongtien;
                this.form.lyDo='Nộp tiền trả trước : ' +matb+ ' ('+tongtien+')';
            }

    },
     onClickAccept() {

                this.$emit('acceptChange',this.outData);
                this.onClose();
    },
    checkBoxHDChange (args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        if(args.checked){
            let tongtien=0;
            let matb='';
            if (this.dsHopDong.length > 0)
                this.dsHopDong.forEach((el) => {
                    tongtien=tongtien+el.TONGTIEN;
                    matb=matb+el.MA_TB+',';
                });

            this.form.tongTien=tongtien;
            this.form.lyDo='Nộp tiền trả trước :'+matb+'('+tongtien+')';
        } else {
            this.form.tongTien= 0;
            this.form.lyDo=''
        }
      }
    },
    }
}
</script>
