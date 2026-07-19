<template>
    <div id="KhaoSatTBTapTrungLM" style="padding: 0px !important;">
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="legend-title">
                        <span @click="isExpandedTTKH=!isExpandedTTKH" class="icon fa fa-angle-up" :class="{'fa-angle-up':isExpandedTTKH, 'fa-angle-down':!isExpandedTTKH,}"></span>
                        Thông tin khách hàng/thuê bao
                    </div>
                    <div v-show="isExpandedTTKH">
                        <div class="info-row">
                            <div class="key w120">Mã GD</div>
                            <div class="value">
                                <input type="text" :value="input_magd" @change="e=>input_magd=e.target.value" class="form-control bold highlight">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Tên khách hàng</div>
                                    <div class="value">
                                        <input type="text" :value="input_tenkh" @change="e=>input_tenkh=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Ngày YC</div>
                                    <div class="value">
                                        <input type="text" :value="input_ngayyc" @change="e=>input_ngayyc=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Loại HĐ</div>
                            <div class="value">
                                <input type="text" :value="input_loaihd" @change="e=>input_loaihd=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Địa chỉ KH</div>
                            <div class="value">
                                <input type="text" :value="input_diachi_kh" @change="e=>input_diachi_kh=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Số liên hệ</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <a @click.prevent="clickCallIPCC" class="btn">
                                                <span class="icon one-mobile-plus"></span>
                                            </a>
                                            <input type="text" :value="input_sodt_kh" @change="e=>input_sodt_kh=e.target.value" class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Ngày hẹn KS</div>
                                    <div class="value">
                                        <input type="text" :value="input_ngayhen_ks" @change="e=>input_ngayhen_ks=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Số liên hệ</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn">
                                                <span class="icon one-mobile-plus"></span>
                                            </button>
                                            <input type="text" :value="input_sodt_kh1" @change="e=>input_sodt_kh1=e.target.value" class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Số liên hệ</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn">
                                                <span class="icon one-mobile-plus"></span>
                                            </button>
                                            <input type="text" :value="input_sodt_kh2" @change="e=>input_sodt_kh2=e.target.value" class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div> -->
                        <div class="info-row" v-if="layItemHenKT.Visible">
                            <div class="key w120">Hẹn kỹ thuật</div>
                            <div class="value">
                                <input type="text" :value="input_hen_kythuat" @change="e=>input_hen_kythuat=e.target.value" placeholder="Không có lịch hẹn kỹ thuật" class="form-control">
                            </div>
                        </div>
                    </div>
                    
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        <span @click="isExpandedCN=!isExpandedCN" class="icon fa fa-angle-up" :class="{'fa-angle-up':isExpandedCN, 'fa-angle-down':!isExpandedCN,}"></span>
                        Thông tin cập nhật
                    </div>
                    <div v-show="isExpandedCN">
                        <div class="info-row">
                            <div class="key w120">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="chkKhongTL.Check" :disabled="!chkKhongTL.Enabled">
                                    <span class="name red">Không trả lời</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <div class="select-custom">
                                        <select v-model="cboLyDoKTL.value" class="form-control" :disabled="!chkKhongTL.Check">
                                            <option v-for="item in cboLyDoKTL.data" :key="item.id" :value="item.id">{{item.text}}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" v-model="chkHenKS_Tu.check" :disabled="!chkHenKS_Tu.Enabled">
                                            <span class="name">Hẹn từ</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="hen_tu" type="datetime" format="DD/MM/YYYY HH:mm:ss" :disabled="!chkHenKS_Tu.check"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" v-model="chkHenKS_Den.check" :disabled="!chkHenKS_Den.Enabled">
                                            <span class="name">đến</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="hen_den" type="datetime" format="DD/MM/YYYY HH:mm:ss" :disabled="!chkHenKS_Den.check"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Người trả lời</div>
                                    <div class="value">
                                        <input type="text" ref="inputNguoiTraLoi" :value="input_nguoitraloi" @change="e=>input_nguoitraloi=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Số liên hệ</div>
                                    <div class="value">
                                        <input type="text" ref="inputSoLHCN" :value="input_sdt_capnhat" @change="e=>input_sdt_capnhat=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-12">
                                <div class="info-row">
                                    <div class="key w120">Ghi chú</div>
                                    <div class="value">
                                        <textarea :value="input_ghichu" @change="e=>input_ghichu=e.target.value"  class="form-control" style="height: 80px;resize: none;"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="legend-title">
                        <span @click="isExpandedDsTb=!isExpandedDsTb" class="icon fa fa-angle-up" :class="{'fa-angle-up':isExpandedDsTb, 'fa-angle-down':!isExpandedDsTb,}"></span>
                        Danh sách thuê bao
                    </div>
                    <KDataGrid
                        ref="gridDsThueBao"
                        v-show="isExpandedDsTb"
                        :columns="columnsDsTB"
                        :dataSource="dataSourcesDsTB"
                        :enable-paging-server="false"
                        :allowPaging="false"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        :panelDataHeight="getHeightRight"
                        @onRowSelected="onSelectedRow"
                        :enabledSelectFirstRow="true"/>
                    
                </div>
                <div class="box-form" v-if="layGrpHenKT.Visible">
                    <div class="legend-title red">
                        <span @click="isExpandedDsHen=!isExpandedDsHen" class="icon fa fa-angle-up" :class="{'fa-angle-up':isExpandedDsHen, 'fa-angle-down':!isExpandedDsHen,}"></span>
                        Hẹn sửa chữa
                    </div>
                    <KDataGrid
                        ref="gridLSHen"
                        v-show="isExpandedDsHen"
                        :columns="columnsLichSuHen"
                        :dataSource="dataSourcesLichSuHen"
                        :enable-paging-server="false"
                        :allowPaging="false"
                        :showFilter="true"
                        :panelDataHeight="getHeightRight"
                        :showColumnCheckbox="false"
                        :enabledSelectFirstRow="true"/>
                    
                </div>
                <div class="box-form">
                    <div class="legend-title">
                       <span @click="isExpandedDsCT=!isExpandedDsCT" class="icon fa fa-angle-up" :class="{'fa-angle-up':isExpandedDsCT, 'fa-angle-down':!isExpandedDsCT,}"></span>
                        Chi tiết các lần hỏi
                    </div>
                    <KDataGrid
                        ref="gridDs"
                        v-show="isExpandedDsCT"
                        :columns="columnsDs"
                        :dataSource="dataSourcesDs"
                        :enable-paging-server="false"
                        :allowPaging="false"
                        :showFilter="true"
                        :panelDataHeight="getHeightRight"
                        :showColumnCheckbox="false"
                        :enabledSelectFirstRow="true"/>
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">
                <span class="icon fa fa-angle-up"></span>
                Danh sách câu hỏi
            </div>
            <div class="form-control text pad10"  style="height: 400px; overflow-y: scroll;">
                <div v-for="(item, index) in dtCommon" :key="index">

                    <div class="title-bg-main">
                        <span class="title">Câu hỏi {{index+1}}:</span>
                    </div>
                    <div class="desc marb10">{{item.Text}}</div>
                    <div class="list-checks-v">

                        <div class="item marb10" v-for="(item1, index1) in item.DsCauTraLoi" :key="index1">
                            <div class="check-action">
                                <input type="radio" :name="item.QuestionID" class="check" @change="changeRadioCheck($event,index, index1)" :checked="item1.Check">
                                <span class="name">{{item1.Text}}</span>
                            </div>
                            
                            <!-- Câu hỏi phụ -->

                            <div v-if="item1.Parent&&item1.Check&&item1.Parent.RCH_ID==item1.RCH_ID" class="box-quest box-form marb5 marl20" style="background:#FFF9EB">
                                <div v-for="(item3, index3) in item1.Parent.ChildQuestion" :key="index3">
                                    <div class="title-bg-main">
                                        <span class="title underline">Câu hỏi phụ {{index3+1}}</span>
                                    </div>
                                    <div class="desc marb10">
                                        <p>{{item3.Text}}</p>
                                    </div>
                                    <div class="list-aw">
                                        <div class="item padt5 padb5"  v-for="(item4, index4) in item3.DsCauTraLoi" :key="index4">
                                            <div class="check-action">
                                                <input type="radio" :name="item3.QuestionID" class="check" @change="changeRadioCheckCauHoiPhu(index, index1, index3, index4)" :checked="item4.Check">
                                                <span class="name">{{item4.Text}}</span>
                                            </div>
                                            <div v-if="item4.Check" class="input" style="max-width: 600px;">
                                                <input type="text" v-model="dtCommon[index].DsCauTraLoi[index1].Parent.ChildQuestion[index3].DsCauTraLoi[index4].AnswerTextDetail" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>

                            <div v-if="item1.Check&&!item1.Parent" class="input marb20 marl20" style="max-width: 600px;">
                                <input type="text" v-model="dtCommon[index].DsCauTraLoi[index1].AnswerTextDetail" class="form-control">
                            </div>

                        </div>
                        
                    </div>

                </div>
            </div>
            
        </div>
    </div>
</template>
<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import API from './API'

// https://api-onebss.vnpt.vn/tichhop/ipcc/callOb
// {
//   "username": "han_agent_1022",
//   "extension": "240204",
//   "dataObject": "{{\"SoThueBao\":\"0943900256\",\"ChuongTrinhId\":\"3\"}}"
// }
export default {
    name:'KhaoSatTBTapTrungLM',
    components:{
        KDatePicker,
        KDataGrid
    },
    data(){
        return {
            hen_tu:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            hen_den:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            columnsLichSuHen:[
                {
                    fieldName: "MATB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "GIOHEN_TU",
                    headerText: "Hẹn từ",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "GIOHEN_DEN",
                    headerText: "Hẹn đến",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NOIDUNG",
                    headerText: "Nội dung hẹn",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAY_CN",
                    headerText: "Ngày CN",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "Người CN",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TEN_DV",
                    headerText: "Đơn vị",
                    allowFiltering: true,
                    width:100,
                    visible:true
                }
            ],
            dataSourcesLichSuHen:[],
            columnsDs:[
                {
                    fieldName: "IPCC_ID",
                    headerText: "Ghi âm",
                    allowFiltering: true,
                    width:100,
                    visible:true,
                    textAlign: "center",
                    template: this.columnTemplatePlay(this)
                },
                {
                    fieldName: "NGUOI_CN",
                    headerText: "Người hỏi",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGUOI_TL",
                    headerText: "Người trả lời",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DIENTHOAI_LH",
                    headerText: "Điện thoại",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAY_HOI",
                    headerText: "Ngày hỏi",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TRANGTHAI",
                    headerText: "Trạng thái",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LYDO_KTL",
                    headerText: "Lý do KTL",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "GHICHU",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
            ],
            dataSourcesDs:[],
            columnsDsTB:[
                {
                    fieldName: "TINH",
                    headerText: "Tỉnh",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TRANGTHAI_KS",
                    headerText: "Trạng thái KS",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_TB",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    width:200,
                    visible:true,
                    template: this.columnTemplateMaTB(this),
                },
                {
                    fieldName: "HEN_LD",
                    headerText: "Hẹn LĐ",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TRANGTHAI_HD",
                    headerText: "Trạng thái HĐ",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAY_HT",
                    headerText: "Ngày HT",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LOAIHINH_TB",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TEN_KIEULD",
                    headerText: "Kiểu lắp đặt",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TEN_TB",
                    headerText: "Tên TB",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DIACHI_LD",
                    headerText: "Địa chỉ lắp đặt",
                    allowFiltering: true,
                    width:100,
                    visible:true
                }
            ],
            dataSourcesDsTB:[],
            chkKhongTL:{
                Check:false,
                Enabled:true
            },
            cboLyDoKTL:{
                value:30,
                data:[
                    {
                        id:30,
                        text:'Không nghe máy, máy bận'
                    },
                    {
                        id:31,
                        text:'Số máy không có thực'
                    },
                    {
                        id:32,
                        text:'Các trường hợp khác'
                    }
                ]
            },
            chkHenKS_Tu:{
                Check:false,
                Enabled:false
            },
            chkHenKS_Den:{
                Check:false,
                Enabled:false
            },
            input_ghichu:'',
            input_nguoitraloi:'',
            input_sdt_capnhat:'',
            input_magd:'',
            input_tenkh:'',
            input_ngayyc:'',
            input_loaihd:'',
            input_diachi_kh:'',
            input_sodt_kh:'',
            input_ngayhen_ks:'',
            input_sodt_kh1:'',
            input_sodt_kh2:'',
            input_hen_kythuat:'',
            dtThuebao:[],
            dsDanhMuc:[],
            hdkh_id:0,
            oblm_id:0,
            ma_tb:'',
            layGrpHenKT:{
                Visible:true
            },
            layItemHenKT:{
                Visible:true
            },
            tbtl_id:0,
            dtCauHoi:[],

            dataSourcesTB:[],
            _dtCauHoiGoc:null,
            dtCommon:[],
            dsTraLoi:[],
            //60p
            THOIGIAN_GIUPHIEU:3600000,
            timer:null,
            totalTime:0,
            thoigian_dy:0,
            dsTb:[],
            dsCT:[],
            dsDieuLai:[],
            thuebao_id:0,
            IPCC_ID:0,
            giaophieu_ks_id:0,
            isExpandedDsTb:true,
            isExpandedDsHen:true,
            isExpandedDsCT:true,
            isExpandedTTKH:true,
            isExpandedCN:true
        }
    },
    computed:{
        getHeightRight(){
            var count=3
            if(!this.isExpandedDsTb){
                count--
            }
            if(!this.isExpandedDsHen||!this.layGrpHenKT.Visible){
                count--
            }
            if(!this.isExpandedDsCT){
                count--
            }
            if(count==0){
                return '60'
            }
            return (180/count).toString()
        }
    },
    methods:{
        async HienThi_Phieu(_dtThueBao){
            if(!_dtThueBao){
                return
            }
            this.Clear()
            this.dtThuebao=_dtThueBao
            //return luôn luôn false
            await this.HienThiThongTinPhuLuc()
           // this.chkKhongTL.Enabled=false
            this.chkKhongTL.Enabled=true
            this.chkKhongTL.Check=false
            //layGroup_CauHoi.Enabled = false;
            await this.HienThiThongTinTB()

            this.dtThuebao=this.dtThuebao.map(x=>{
                x.GHICHU=x.GHICHU?x.GHICHU.toString():''
                x.SELECT=x.SELECT?x.SELECT:false
                return x
            })

            console.log('dtThuebao', this.dtThuebao)
            let dtTbKhaoSat=this.dtThuebao.filter(x=>x.SOLAN_KS_THANHCONG==0&&x.TTHD_ID==6)
            this.dataSourcesTB=dtTbKhaoSat
            this.dtCauHoi=await this.GetDsCauHoi(dtTbKhaoSat)
            this.input_ngayhen_ks=_dtThueBao[0].HENKS_TU?_dtThueBao[0].HENKS_TU.toString():''


            //Kiểu =1
            let dtCauHoi_1=this.dtCauHoi.filter(x=>x.kieu==1).sort((a,b)=>{return a.thutu-b.thutu})

            let idCauHois=dtCauHoi_1.map(x=>x.cauhoi_id)
            idCauHois=idCauHois.filter((item, index)=>idCauHois.indexOf(item) === index)
            console.log('idCauHois', idCauHois.length)
            for(let i=0;i<idCauHois.length;i++){
                let temp=dtCauHoi_1.find(x=>x.cauhoi_id==idCauHois[i])
                var cauhoi={
                    QuestionID:temp.cauhoi_id?temp.cauhoi_id:0,
                    Text:temp.noidung?temp.noidung.toString():'',
                    Ordinal:temp.thutu?temp.thutu:0,
                    Type:temp.kieu?temp.kieu:1,
                    IsRadio:temp.loaich_id==1,
                    Visible:true,
                    RCH_ID:temp.rch_id?temp.rch_id:0,
                    DsCauTraLoi:[],
                    Parent:null
                }
                let dtTraLoi_1=this.dtCauHoi.filter(x=>x.cauhoi_id==idCauHois[i]).sort((a,b)=>{return a.thutu_tl-b.thutu_tl}).map(x=>{
                    return{
                        ChildQuestion:[],
                        QuestionID:x.cauhoi_id?x.cauhoi_id:0,
                        AnswerID:x.traloi_id?x.traloi_id:0,
                        Text:x.noidung_tl?x.noidung_tl.toString():'',
                        Type:x.loai?x.loai:0,
                        Ordinal:x.thutu_tl?x.thutu_tl:0,
                        RCH_ID:x.rch_id1?x.rch_id1:0,
                        dieulai:x.dieulai?x.dieulai:0,
                        dieulai_xn:x.dieulai_xn?x.dieulai_xn:0,
                        Check:false,
                        AnswerTextDetail:null
                    }
                })
                cauhoi.DsCauTraLoi=dtTraLoi_1
                this.dtCommon.push(cauhoi)
                //
                this.dsTraLoi=this.dsTraLoi.concat(dtTraLoi_1)
            }
            //Kiểu =2
            var dtCauHoi_2=this.dtCauHoi.filter(x=>x.kieu==2).sort((a,b)=>{return a.thutu-b.thutu})
            for(let i=0;i<dtCauHoi_2.length;i++){
                let cauhoi_id = dtCauHoi_2[i].cauhoi_id;
                let rch_id = dtCauHoi_2[i].rch_id
                const indexCauHoi=this.dtCommon.findIndex(x=>x.QuestionID==cauhoi_id)
                if(indexCauHoi>-1){
                    continue
                }
                let tlGoc=this.dsTraLoi.find(x=>x.RCH_ID==rch_id)
                if(!tlGoc){
                    continue
                }
                
                const indexCauHoiGoc=this.dtCommon.findIndex(x=>x.QuestionID==tlGoc.QuestionID)
                if(indexCauHoiGoc<0){
                    continue
                }
                const indexCauTraLoiGoc=this.dtCommon[indexCauHoiGoc].DsCauTraLoi.findIndex(x=>x.AnswerID==tlGoc.AnswerID)
                if(indexCauTraLoiGoc<0){
                    continue
                }

                //remove trùng
                const checkTrung=this.dsTraLoi.findIndex(x=>x.QuestionID==dtCauHoi_2[i].cauhoi_id&&x.AnswerID==dtCauHoi_2[i].traloi_id)
                if(checkTrung>-1){
                    continue
                }

                let temp=dtCauHoi_2[i]
                var cauhoi={
                    QuestionID:temp.cauhoi_id?temp.cauhoi_id:0,
                    Text:temp.noidung?temp.noidung.toString():'',
                    Ordinal:temp.thutu?temp.thutu:0,
                    Type:temp.kieu?temp.kieu:1,
                    IsRadio:temp.loaich_id==1,
                    Visible:true,
                    RCH_ID:temp.rch_id?temp.rch_id:0,
                    DsCauTraLoi:[],
                    Parent:null
                }
                
                let dtTraLoi_2=dtCauHoi_2.filter(x=>x.cauhoi_id==dtCauHoi_2[i].cauhoi_id).sort((a,b)=>{return a.thutu_tl-b.thutu_tl}).map(x=>{
                    return{
                        ChildQuestion:[],
                        QuestionID:x.cauhoi_id?x.cauhoi_id:0,
                        AnswerID:x.traloi_id?x.traloi_id:0,
                        Text:x.noidung_tl?x.noidung_tl.toString():'',
                        Type:x.loai?x.loai:0,
                        Ordinal:x.thutu_tl?x.thutu_tl:0,
                        RCH_ID:x.rch_id1?x.rch_id1:0,
                        dieulai:x.dieulai?x.dieulai:0,
                        dieulai_xn:x.dieulai_xn?x.dieulai_xn:0,
                        Check:false,
                        AnswerTextDetail:null
                    }
                })
                
                cauhoi.DsCauTraLoi=dtTraLoi_2
                tlGoc.ChildQuestion.push(cauhoi)

                this.dtCommon[indexCauHoiGoc].DsCauTraLoi[indexCauTraLoiGoc].Parent=tlGoc
                
                //
                this.dsTraLoi=this.dsTraLoi.concat(dtTraLoi_2)
            }
            console.log('dtCommon', this.dtCommon)
            this.thoigian_dy=this.THOIGIAN_GIUPHIEU
            this.timeOut=setInterval(()=>{
                this.totalTime+=1000
                this.TimerEslapsed()
            }, 1000)

        },
        clickCallIPCC(){
            this.$emit('clickCallIPCC', this.input_sodt_kh.trim())
            this.input_sdt_capnhat=this.input_sodt_kh.trim()
        },
        Clear(){
            this.hen_tu=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.hen_den=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.dataSourcesLichSuHen=[]
            this.dataSourcesDs=[]
            this.dataSourcesDsTB=[]
            this.chkKhongTL={
                Check:false,
                Enabled:true
            }
            this.chkHenKS_Tu={
                Check:false,
                Enabled:false
            }
            this.chkHenKS_Den={
                Check:false,
                Enabled:false
            }

            this.input_ghichu=''
            this.input_nguoitraloi=''
            this.input_sdt_capnhat=''
            this.input_magd=''
            this.input_tenkh=''
            this.input_ngayyc=''
            this.input_loaihd=''
            this.input_diachi_kh=''
            this.input_sodt_kh='',
            this.input_ngayhen_ks=''
            this.input_sodt_kh1=''
            this.input_sodt_kh2=''
            this.input_hen_kythuat=''
            this.dtThuebao=[]
            this.dsDanhMuc=[]
            this.hdkh_id=0
            this.oblm_id=0
            this.ma_tb=''
            this.layGrpHenKT={
                Visible:true
            }
            this.layItemHenKT={
                Visible:true
            }
            this.tbtl_id=0
            this.dtCauHoi=[]

            this.dataSourcesTB=[]
            this.dtCommon=[]
            this.dsTraLoi=[]
            this.timer=null
            this.totalTime=0
            this.thoigian_dy=0
            this.dsTb=[]
            this.dsCT=[]
            this.dsDieuLai=[]
            this.thuebao_id=0
            this.IPCC_ID=0
            this.giaophieu_ks_id=0

            this.isExpandedDsTb=true
            this.isExpandedDsHen=true
            this.isExpandedDsCT=true
            this.isExpandedTTKH=true
            this.isExpandedCN=true


        },
        async GetDsCauHoi(dtTbKhaoSat){
            if(this._dtCauHoiGoc==null){
                this._dtCauHoiGoc=await this.lay_ds_cauhoi_theoloainv(6, 1)
            }
            if(dtTbKhaoSat.length==1){
                return this._dtCauHoiGoc
            }else{
                let dt=this._dtCauHoiGoc.map(x=>{
                    x.Loai=3
                    x.loaich_id=2
                    return x
                })
                return dt
            }
        },
        async TimerEslapsed(){
            if(this.totalTime==this.thoigian_dy){
                clearInterval(this.timer)
                let result = await this.confirm('Thời gian giữ phiếu vượt quá 30 phút, bạn có muốn xin thêm 5 phút để thực hiện hay không ?','Chú ý')
                if(result==1){
                    this.totalTime=0
                    this.thoigian_dy=300000
                    this.timer=setInterval(()=>{
                        this.totalTime+=1000
                        this.TimerEslapsed()
                    }, 1000)

                }else{
                    //sp_capnhat_trangthai_phieu_lapmoiob
                    let result=await this.sp_capnhat_trangthai_phieu_lapmoiob1({
                        vhdkh_id: this.hdkh_id, 
                        vma_nd:this.$root.token.getUserName(), 
                        vtrangthai: 7, 
                        vip_cn: "127.0.0.1" 
                    })
                    if(result=='ok'){
                        this.$toast.success('Quá thời gian giữ phiếu, phiếu đã được trả về')
                    }else{
                        this.$toast.error(result)
                    }
                }
            }
            
        },
        async HienThiThongTinPhuLuc(){
            var chuaxong = false
            var chua_ks = false
            if(this.dsDanhMuc.length==0){
                this.dsDanhMuc=await this.fn_lay_danh_muc('1, 2, 10, 17, 150, 151')
            }
            this.hdkh_id = this.dtThuebao[0].HDKH_ID
            this.oblm_id = this.dtThuebao[0].OBLM_ID
            //sp_laythongtin_oblm
            //string ma_tinh = dtThuebao.TableName;
            let ma_tinh=this.$root.token.getMaTinh()
            let dsHDTB=[]
            this.dtThuebao = await this.sp_laythongtin_oblm(this.oblm_id);
            for(let i=0;i<this.dtThuebao.length;i++){
                this.dtThuebao[i].TINH=''
                this.dtThuebao[i].LOAIHINH=''
                this.dtThuebao[i].TRANGTHAI_HD=''
                this.dtThuebao[i].KIEU_LD=''
                this.dtThuebao[i].TRANGTHAI_KS=''

                this.dtThuebao[i].TINH=ma_tinh
                if(this.dtThuebao[i].SOLAN_KS_THANHCONG!=0){
                    this.dtThuebao[i].TRANGTHAI_KS='Đã khảo sát'
                }else{
                    chua_ks=true
                }
                if(this.dtThuebao[i].TTHD_ID!=6 &&this.dtThuebao[i].TTHD_ID!=7){
                    chuaxong=true
                }
                //WinUI.WinUIQLDHXLSuCo.MultiDB.FillSubData(dr)
                dsHDTB.push(this.dtThuebao[i].HDTB_ID)
            }
            console.log('HienThiThongTinPhuLuc dtThuebao', this.dtThuebao)
            this.dataSourcesDsTB=this.dtThuebao
            if(!chua_ks){
                this.$toast.success('Bất đồng bộ với hệ thống OB cũ, toàn bộ các phụ lục của khách hàng đã được khảo sát')
                //sp_capnhat_trangthai_phieu_lapmoiob
                await this.sp_capnhat_trangthai_phieu_lapmoiob({
                    vhdkh_id: this.hdkh_id,
                    vma_nd:this.$root.token.getUserName(),
                    vtrangthai: 7,
                    vip_cn: "127.0.0.1" 
                })
                return
            }
            if(chuaxong){
                let result = await this.confirm('Hợp đồng có phụ lục chưa hoàn thành, tiến hành khảo sát ?','Chú ý')
                if(result==0) return
                await this.giuphieu_ob_lm_taptrung(dsHDTB.toString(), this.$root.token.getUserName())
            }

            let dtHen=await this.sp_lay_ds_hdtb_hen(this.hdkh_id)
            if(dtHen&&dtHen.length>0){
                this.layGrpHenKT.Visible=true
                this.layItemHenKT.Visible=false
                this.dataSourcesLichSuHen=dtHen
            }else{
                this.layGrpHenKT.Visible=false
                this.layItemHenKT.Visible=true
            }

        },
        async HienThiThongTinTB(){
            let dt1=await this.lay_tt_hdkh_theo_hdkh_id(this.hdkh_id)
            if(dt1.length>0){
                this.input_magd=dt1[0].ma_gd?dt1[0].ma_gd:''
                this.input_tenkh=dt1[0].ten_kh?dt1[0].ten_kh:''
                this.input_diachi_kh=dt1[0].diachi_kh?dt1[0].diachi_kh:''
                this.input_sodt_kh=dt1[0].so_dt?dt1[0].so_dt:''
                this.input_sdt_capnhat=dt1[0].so_dt?dt1[0].so_dt:''
                this.input_nguoitraloi=dt1[0].ten_kh?dt1[0].ten_kh:''
                this.input_ngayyc=dt1[0].ngay_yc?dt1[0].ngay_yc:''
                this.input_loaihd=dt1[0].ten_loaihd?dt1[0].ten_loaihd:''
            }
            if(dt1.length>0){
                let dtLanGoi=await this.langoi_chuatraloi(this.hdkh_id, this.baohong_id)
                
                this.dataSourcesDs=dtLanGoi

                let dtTBTL=await this.sp_tb_traloi_theo_hdkh_id(this.hdkh_id)
                this.tbtl_id=0
                var isNew=false 
                if(dtTBTL.length>0){
                    isNew = false
                    if(this.tbtl_id=0){
                        this.tbtl_id=dtTBTL[0].tbtl_id?dtTBTL[0].tbtl_id:0
                    }
                    this.input_nguoitraloi=dtTBTL[0].nguoi_tl?dtTBTL[0].nguoi_tl:''
                    this.input_sdt_capnhat=dtTBTL[0].dienthoai_lh?dtTBTL[0].dienthoai_lh:''
                    this.input_ghichu=dtTBTL[0].ghichu?dtTBTL[0].ghichu:''
                    this.chkKhongTL.Check=dtTBTL[0].trangthai!=null&&dtTBTL[0].trangthai==0?true:false
                    if(this.cboLyDoKTL.Check){
                        isNew=true
                        tbtl_id=0
                        if(dtTBTL[0].lydoktl_id){
                            this.cboLyDoKTL.value=dtTBTL[0].lydoktl_id
                        }
                    }
                }else{
                    isNew = true
                    this.tbtl_id=0
                }

            }

            //     (layItemHenKT.Parent as DevExpress.XtraLayout.LayoutControlGroup).BeginInit();
            //     try
            //     {
            //         if (ChucNang.Instance.dsFormTinh[this] == "HCM")
            //         {
            //             layItemDienThoaiLH.AppearanceItemCaption.Reset();
            //             ulong khachhang_id = Convert.ToUInt64(dt1.Rows[0]["khachhang_id"].ToString());
            //             DataTable dtLienHe = fims.LaySoDienThoaiLienHe(khachhang_id);
            //             foreach (DataRow rowLienHe in dtLienHe.Rows)
            //             {
            //                 string sdt = rowLienHe[1].ToString().Trim();
            //                 if (sdt == txtDienThoaiLH.Text || layItem_x_LienHe1.Control.Text == sdt || layItem_x_LienHe2.Control.Text == sdt)
            //                 {
            //                     layItemDienThoaiLH.AppearanceItemCaption.ForeColor = Color.Lime;
            //                     continue;
            //                 }

            //                 if (layItem_x_LienHe1.Control.Text.Length == 0)
            //                 {
            //                     layItem_x_LienHe1.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
            //                     layItem_x_LienHe1.Control.Text = sdt;
            //                     continue;
            //                 }
            //                 if (layItem_x_LienHe2.Control.Text.Length == 0)
            //                 {
            //                     layItem_x_LienHe2.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
            //                     layItem_x_LienHe2.Control.Text = sdt;
            //                     continue;
            //                 }

            //                 DevExpress.XtraEditors.ButtonEdit btn = new DevExpress.XtraEditors.ButtonEdit();
            //                 btn.Properties.Buttons.Clear();
            //                 btn.Properties.Buttons.Add(new DevExpress.XtraEditors.Controls.EditorButton(
            //                     DevExpress.XtraEditors.Controls.ButtonPredefines.Glyph
            //                     , ""
            //                     , -1
            //                     , true
            //                     , true
            //                     , true
            //                     , DevExpress.XtraEditors.ImageLocation.MiddleCenter
            //                     , global::WinUI.Properties.Resources.subscriber_add));
            //                 btn.Name = "btn_x_" + rowLienHe[1].ToString();
            //                 btn.Text = rowLienHe[1].ToString();
            //                 btn.Tag = 1;
            //                 btn.ButtonClick += new DevExpress.XtraEditors.Controls.ButtonPressedEventHandler(this.txtDienThoaiLH_ButtonClick);
            //                 btn.KeyDown += txtDienThoaiLH_KeyDown;
            //                 DevExpress.XtraLayout.LayoutControlItem item = (layItemHenKT.Parent as DevExpress.XtraLayout.LayoutControlGroup).AddItem(layItemHenKT, DevExpress.XtraLayout.Utils.InsertType.Top);
            //                 item.Text = "SĐT Liên hệ";
            //                 item.Name = "layItem_x_" + rowLienHe[1].ToString();
            //                 item.AppearanceItemCaption.ForeColor = Color.Lime;
            //                 item.Control = btn;
            //             }
            //         }
            //     }
            //     catch (Exception) { }
            //     (layItemHenKT.Parent as DevExpress.XtraLayout.LayoutControlGroup).EndInit();
            // }

        },
        async tsbtnGhiLai_Click(){
            if(!this.ValidateAnswer(true)){
                return
            }
            //dsTb
            //dsCT
            //CreateResult
            if(this.dsTb.length>0){
                for(let i=0;i<this.dsTb.length;i++){
                    await this.sp_delete_tb_traloi(this.dsTb[i].TBTL_ID)
                    await this.sp_delete_ct_tbtraloi(this.dsTb[i].TBTL_ID)
                }
                this.dsTb=[]
                this.dsCT=[]
            }
            let dsResult=await this.CreateResult()
            console.log('tsbtnGhiLai_Click dsResult', dsResult)
            await this.TaoDuLieu(dsResult, false)
            await this.sp_insert_tb_traloi(JSON.stringify(this.dsTb))
            
            if(this.chkHenKS_Tu.Check){
                var ksTu=this.hen_tu
                var ksDen=null
                if(this.chkHenKS_Den.Check){
                    ksDen=this.hen_den
                }
                if(ksTu!=null&&ksDen!=null){
                    if(moment(ksTu, 'DD/MM/YYYY HH:mm:ss').add(1, 'hours').isBefore(moment(ksDen, 'DD/MM/YYYY HH:mm:ss'))){
                        this.$toast.error('Thời gian hẹn kết thúc cần cách thời gian hẹn bắt đầu ít nhất 1h')
                        return
                    }
                }
                await this.sp_capnhat_trangthai_phieu_lapmoiob({
                    vhdkh_id:this.hdkh_id, 
                    vma_nd:this.$root.token.getUserName(), 
                    vtrangthai:4, 
                    vip_cn:"127.0.0.1"
                })
                await this.sp_hen_ks_lapmoi_taptrung({
                    vhdkh_id:this.hdkh_id, 
                    vkstu: ksTu, 
                    vksden:ksDen
                })
            }else if(this.chkKhongTL.Check){
                var ksTu=null
                //LydoKTL.SaiSDT=31
                if(this.dataSourcesDs.filter(x=>x.LYDOKTL_ID!=null&&x.NHANVIEN_ID!=36004).length>=2||this.cboLyDoKTL.value==31){
                    await this.sp_capnhat_trangthai_phieu_lapmoiob({
                        vhdkh_id:this.hdkh_id, 
                        vma_nd:this.$root.token.getUserName(), 
                        vtrangthai:7, 
                        vip_cn:"127.0.0.1"
                    })
                }else if(this.cboLyDoKTL.value==30||this.cboLyDoKTL.value==32){
                    var ksTu=moment(new Date()).add(1, 'hours').format('DD/MM/YYYY HH:mm:ss')
                    await this.sp_hen_ks_lapmoi_taptrung({
                        vhdkh_id:this.hdkh_id, 
                        vkstu: ksTu, 
                        vksden:null
                    })
                    await this.sp_capnhat_trangthai_phieu_lapmoiob({
                        vhdkh_id:this.hdkh_id, 
                        vma_nd:this.$root.token.getUserName(), 
                        vtrangthai:4, 
                        vip_cn:"127.0.0.1"
                    })
                }
            }else{
                await this.sp_insert_ct_tbtraloi(JSON.stringify(this.dsCT))
                await this.sp_capnhat_trangthai_phieu_lapmoiob({
                    vhdkh_id:this.hdkh_id, 
                    vma_nd:this.$root.token.getUserName(), 
                    vtrangthai:6, 
                    vip_cn:"127.0.0.1"
                })
            }
            //refresh
            this.dataSourcesDs=await this.langoi_chuatraloi(this.hdkh_id, this.baohong_id)
            this.$toast.success('Ghi lại kết quả khảo sát thành công')


            for(let i=0;i<this.dsDieuLai.length;i++){
                let rs=await this.dieulai_ob_taptrung(this.dsDieuLai[i])
                if(rs!='ok'){
                    this.$toast.error(`TBTL ID: ${this.dsDieuLai[i]} ${rs}`)
                }
            }
        },
        async tsbtnDieuLai_Click(){
            let dr=this.$refs.gridDsThueBao.getSelectedRecords()
            console.log('tsbtnDieuLai_Click', dr)
            if(!dr||dr.length==0){
                return
            }
            if(dr[0].TTHD_ID!=6){
                this.$toast.error(`Hợp đồng ${dr[0].MA_TB} chưa hoàn thiện, không thể điều lại`)
                return
            }
            let result = await this.confirm(`Thực hiện điều lại đối với mã thuê bao [${dr[0].MA_TB}] ?`,'Điều lại phiếu')
            if(result==0) return
            let hdtb_id=dr[0].HDTB_ID
            let drTBTL = this.dataSourcesDs.filter(x=>x.HDTB_ID==hdtb_id)
            if(!drTBTL||drTBTL.length==0){
                this.$toast.error('Bạn cần hoàn thành khảo sát trước khi điều lại')
                return
            }
            //dieulai_ob_taptrung  drTBTL[0].TBTL_ID
            let rs=await this.dieulai_ob_taptrung(drTBTL[0].TBTL_ID)
            if(rs=='ok'){
                this.$toast.success('Thành công')
            }else{
                this.$toast.error(rs)
            }
        },
        async tsbtnBoQua_Click(){
            let result = await this.confirm(`Bạn có muốn lùi thời gian khảo sát lại không ? `,'Bỏ qua phiếu')
            if(result==1){
                await this.sp_hen_ks_lapmoi_taptrung({
                    vhdkh_id: 1, 
                    vkstu: moment(new Date()).add(1, 'minutes').format('DD/MM/YYYY HH:mm:ss'),
                    vksden: null 
                })
                await this.sp_capnhat_trangthai_phieu_lapmoiob({
                    vhdkh_id: this.hdkh_id,
                    vma_nd:this.$root.token.getUserName(),
                    vtrangthai: 4,
                    vip_cn: "127.0.0.1" 
                })
            }else{
                await this.sp_capnhat_trangthai_phieu_lapmoiob({
                    vhdkh_id: this.hdkh_id,
                    vma_nd:this.$root.token.getUserName(),
                    vtrangthai: 4,
                    vip_cn: "127.0.0.1" 
                })
            }
        },
        async CreateResult(){
            let dsResult=[]
            let dsTraLoi=await this.getKetQua()
            console.log('CreateResult dsTraLoi', dsTraLoi)
            var dt={}
            for(let i=0;i<this.dtThuebao.length;i++){
                if(this.dtThuebao[i].TTHD_ID!=6){
                    continue
                }
                if(this.dtThuebao[i].SOLAN_KS_THANHCONG!=0){
                    continue
                }
                dt.TableName=this.dtThuebao[i].MA_TB
                dt.Rows=[]
                for(let j=0;j<dsTraLoi.length;j++){
                    if(!dsTraLoi[j].Check){
                        continue
                    }

                    var text=''
                    if(dsTraLoi[j].Type==3){

                    }else{

                    }
                    text=dsTraLoi[j].AnswerTextDetail?dsTraLoi[j].AnswerTextDetail.toString():null
                    let cauhoi=this.dtCommon.find(x=>x.QuestionID==dsTraLoi[j].QuestionID)
                    dt.Rows.push({
                        HDTB_ID:this.dtThuebao[i].HDTB_ID,
                        THUEBAO_ID:this.dtThuebao[i].THUEBAO_ID,
                        CAUHOI_ID:dsTraLoi[j].QuestionID,
                        TRALOI_ID:dsTraLoi[j].AnswerID,
                        NOIDUNG:text,
                        CAUHOI:cauhoi?cauhoi.Text.toString():'',
                        TRALOI:dsTraLoi[j].Text,
                        DIEULAI:dsTraLoi[j].dieulai,
                        RCH_ID:dsTraLoi[j].RCH_ID,
                        DIEULAI_XN:dsTraLoi[j].dieulai_xn
                    })

                }
                dsResult.push(dt)
            }
            return dsResult
        },
        async getKetQua(){
            var dsTraLoi=[]
            for(let i=0;i<this.dtCommon.length;i++){
                dsTraLoi=dsTraLoi.concat(this.dtCommon[i].DsCauTraLoi)
                for(let j=0;j<this.dtCommon[i].DsCauTraLoi.length;j++){
                    if(this.dtCommon[i].DsCauTraLoi[j].Parent){
                        for(let z=0;z<this.dtCommon[i].DsCauTraLoi[j].Parent.ChildQuestion.length;z++){
                            dsTraLoi=dsTraLoi.concat(this.dtCommon[i].DsCauTraLoi[j].Parent.ChildQuestion[z].DsCauTraLoi)
                        }
                    }
                }
            }
            return dsTraLoi.filter(x=>x.Check);
        },
        async TaoDuLieu(dsThueBao, dieulai){
           for(let i=0;i<this.dtThuebao.length;i++){
                if(this.dtThuebao[i].TTHD_ID!=6){
                    continue
                }
                if(this.dtThuebao[i].SOLAN_KS_THANHCONG!=0){
                    continue
                }
                let hdtb_id=this.dtThuebao[i].HDTB_ID
                var objectTB_TRALOI={}
                this.tbtl_id=await this.getKey('TBTL_ID')
                this.thuebao_id=this.dtThuebao[i].THUEBAO_ID
                objectTB_TRALOI.TBTL_ID = this.tbtl_id
                objectTB_TRALOI.HDKH_ID = this.hdkh_id
                objectTB_TRALOI.HDTB_ID = hdtb_id
                objectTB_TRALOI.HINHTHUC = 0
                objectTB_TRALOI.THUEBAO_ID = this.thuebao_id
                objectTB_TRALOI.NGUOI_TL = this.input_nguoitraloi.trim()
                objectTB_TRALOI.DIENTHOAI_LH = this.input_sdt_capnhat.trim()
                objectTB_TRALOI.GHICHU = this.input_ghichu.trim()
                objectTB_TRALOI.TRANGTHAI = this.chkKhongTL.Check? 0 : 1
                objectTB_TRALOI.NGAY_HOI = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
                objectTB_TRALOI.NGUOI_CN = this.$root.token.getUserName()
                objectTB_TRALOI.MAY_CN ='localhost'
                objectTB_TRALOI.NHANVIEN_ID = this.$root.token.getNhanVienID()
                objectTB_TRALOI.NGUOIDUNG_ID = this.$root.token.getNguoiDungID()
                objectTB_TRALOI.IP_CN = '127.0.0.1'

                if (this.IPCC_ID > 0){
                    objectTB_TRALOI.IPCC_ID = this.IPCC_ID
                }

                if(this.chkKhongTL.Check){
                    objectTB_TRALOI.LYDOKTL_ID=this.cboLyDoKTL.value
                    objectTB_TRALOI.TTKS_ID = 1
                    if(this.dataSourcesDs.filter(x=>x.LYDO_KTL==null).length>3){
                        objectTB_TRALOI.TTKS_ID = 7;//Kết thúc KH không gọi lại KH nữa.
                    }

                }else{
                    objectTB_TRALOI.TTKS_ID=6
                }
                //Common.CommonConstants.FIMS_LYDO_KTL.KH_TU_CHOI_TIEP_CHUYEN=1
                if(this.chkKhongTL.Check&&this.cboLyDoKTL.value==1){
                    objectTB_TRALOI.TTKS_ID = 6
                }

                if (this.giaophieu_ks_id != 0){
                    objectTB_TRALOI.GIAOPHIEU_KS_ID = this.giaophieu_ks_id
                }
                this.dsTb.push(objectTB_TRALOI)

                var isDieuLai = false
                let dt=dsThueBao.find(x=>x.TableName==this.dtThuebao[i].MA_TB)
                if(!dt){
                    continue
                }
                for(let j=0;j<dt.Rows.length;j++){
                    let item=dt.Rows[j]
                    var rowct={}
                    rowct.CAUHOI_ID = item.CAUHOI_ID
                    rowct.TRALOI_ID = item.TRALOI_ID
                    rowct.TBTL_ID = this.tbtl_id
                    rowct.NOIDUNG = item.NOIDUNG
                    this.dsCT.push(rowct)
                    if(rowct.TRALOI_ID==523585){
                        this.dsDieuLai.push(objectTB_TRALOI.TBTL_ID)
                    }
                }      
           }
        },
        ClearAnswer(){
            for(let i=0;i<this.dtCommon.length;i++){
                for(let j=0;j<this.dtCommon[i].DsCauTraLoi.length;j++){
                    this.dtCommon[i].DsCauTraLoi[j].Check=false
                    this.dtCommon[i].DsCauTraLoi[j].AnswerTextDetail=null
                }

                for(let i=0;i<this.dtCommon.length;i++){
                    for(let j=0;j<this.dtCommon[i].DsCauTraLoi.length;j++){
                        if(this.dtCommon[i].DsCauTraLoi[j].Parent){
                            for(let z=0;z<this.dtCommon[i].DsCauTraLoi[j].Parent.ChildQuestion.length;z++){
                                for(let k=0;k<this.dtCommon[i].DsCauTraLoi[j].Parent.ChildQuestion[z].DsCauTraLoi.length;k++){
                                    this.dtCommon[i].DsCauTraLoi[j].Parent.ChildQuestion[z].DsCauTraLoi[k].Check=false
                                    this.dtCommon[i].DsCauTraLoi[j].Parent.ChildQuestion[z].DsCauTraLoi[k].AnswerTextDetail=false
                                }
                            }
                        }
                    }
                }
            }
        },
        columnTemplatePlay(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                          <a href="#" @click.prevent="clickPlay" style="text-decoration: unset !important"> <span class="icon fa fa-play"></span> Nghe ghi âm </a>
                        `,
                        data() {
                            return {
                              data: {},
                              parent: parent
                            }
                        },
                        computed: {
                            
                        },
                        methods:{
                            clickPlay(){
                                this.parent.clickPlay(this.data)
                            }
                        }
                        
                    }
                }
            }
        },
        clickPlay(item){
            if(item.IPCC_ID){
                
            }
        },
        callPhoneKH(){
            this.chkKhongTL.Enabled=true
            //xử lý sau
        },
        ValidateAnswer(themoi){
            if(this.input_nguoitraloi.trim()==''&&!this.chkKhongTL.Check){
                this.$toast.error('Chưa nhập tên người trả lời !')
                this.$refs.inputNguoiTraLoi.focus()
                return false
            }
            if(this.input_sdt_capnhat.trim()==''){
                this.$toast.error('Chưa nhập điện thoại liên hệ !')
                this.$refs.inputSoLHCN.focus()
                return false
            }
            if(!this.validatePhoneNumber(this.input_sdt_capnhat.trim())){
                this.$toast.error('Số điện thoại liên hệ của khách hàng phải là kiểu số!')
                this.$refs.inputSoLHCN.focus()
                return false
            }
            if(this.chkKhongTL.Check&&this.cboLyDoKTL.value==20){
                if(!this.chkHenKS_Tu.Check&&!this.chkHenKS_Den.Check){
                    this.$toast.error('Hãy chọn Ngày hẹn')
                    return false
                }

                if(this.chkHenKS_Tu.Check&&!this.hen_tu){
                    this.$toast.error('Hãy chọn Ngày hẹn khảo sát từ')
                    return false
                }
                if(this.chkHenKS_Den.Check&&!this.hen_den){
                    this.$toast.error('Hãy chọn Ngày hẹn khảo sát đến')
                    return false
                }

                if(this.chkHenKS_Tu.Check&&moment(this.hen_tu, 'DD/MM/YYYY').isBefore(moment(new Date()))){
                    this.$toast.error('Ngày hẹn từ không thể nhỏ hơn ngày hiện tại')
                    return false
                }

                if(this.chkHenKS_Den.Check&&moment(this.hen_den, 'DD/MM/YYYY').isBefore(moment(new Date()))){
                    this.$toast.error('Ngày hẹn đến không thể nhỏ hơn ngày hiện tại')
                    return false
                }

                if(this.chkHenKS_Tu.Check&&this.dtThuebao[0].NGAY_HT&&moment(this.dtThuebao[0].NGAY_HT, 'DD/MM/YYYY HH:mm:ss').add(72, 'hours').isBefore(moment(this.hen_tu, 'DD/MM/YYYY'))){
                    this.$toast.error('Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: '+moment(this.dtThuebao[0].NGAY_HT, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'))
                    return false
                }

                if(this.chkHenKS_Den.Check&&this.dtThuebao[0].NGAY_HT&&moment(this.dtThuebao[0].NGAY_HT, 'DD/MM/YYYY HH:mm:ss').add(72, 'hours').isBefore(moment(this.hen_den, 'DD/MM/YYYY'))){
                    this.$toast.error('Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: '+moment(this.dtThuebao[0].NGAY_HT, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'))
                    return false
                }

            }
            return true

        },
        validatePhoneNumber(input_str) {
            var re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
            return re.test(input_str);
        },
        onSelectedRow(item){
            if(!item){
                return
            }
            this.ma_tb=item.MA_TB?item.MA_TB.toString():''
        },
        changeRadioCheck(event, index, index1){
            this.dtCommon[index].DsCauTraLoi=this.dtCommon[index].DsCauTraLoi.map(x=>{
                x.Check=false
                return x
            })
            this.dtCommon[index].DsCauTraLoi[index1].Check=true
        },
        changeRadioCheckCauHoiPhu(index,index1, index3, index4){
            this.dtCommon[index].DsCauTraLoi[index1].Parent.ChildQuestion[index3].DsCauTraLoi=this.dtCommon[index].DsCauTraLoi[index1].Parent.ChildQuestion[index3].DsCauTraLoi.map(x=>{
                x.Check=false
                return x
            })
            this.dtCommon[index].DsCauTraLoi[index1].Parent.ChildQuestion[index3].DsCauTraLoi[index4].Check=true
        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        clickCall(item){
            console.log('clickCall', item)
            this.$emit('clickCallIPCC', item.MA_TB)
        },
        columnTemplateMaTB(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                          <div class="info-row">
                            <div class="value">
                                <div class="input-icon-right">
                                    <span class="name">{{data.MA_TB}}</span>
                                    <button v-if="isShowCall" class="btn" style="border-color: #0176FF !important;padding: 1px 5px !important;float: right !important;" @click="clickCall">
                                        <span class="-ap one-mobile-plus" style="color: #0176FF !important;"></span>
                                    </button>
                                </div>
                            </div>
                          </div>
                        `,
                        data() {
                            return {
                              data: {},
                              parent:parent
                            }
                        },
                        computed:{
                            isShowCall(){
                                if(this.data.DICHVUVT_ID==1){
                                    return true
                                }
                                return false
                            }
                        },
                        methods:{
                            clickCall(){
                                this.parent.clickCall(this.data)
                            }
                        }
                    }
                }
            }
        },
        async fn_lay_danh_muc(ds_danhmuc_id){
            try{
                this.loading(true)
                let response=await API.fn_lay_danh_muc(this.axios, ds_danhmuc_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else {
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async sp_laythongtin_oblm(oblm_id){
            try{
                this.loading(true)
                let response = await API.sp_laythongtin_oblm(this.axios, oblm_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result = response.data.data
                    //Upper case key json
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    return result
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_capnhat_trangthai_phieu_lapmoiob(data){
            try{
                this.loading(true)
                let response= await API.sp_capnhat_trangthai_phieu_lapmoiob(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    // if(response.data.data!='ok'){
                    //     this.$toast.error(response.data.data)
                    // }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }
            }
        },
        async sp_hen_ks_lapmoi_taptrung(data){
            try{
                this.loading(true)
                let response=await API.sp_hen_ks_lapmoi_taptrung(this.axios, data)
                this.loading(false)
                 if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    // if(response.data.data!='ok'){
                    //     this.$toast.error(response.data.data)
                    // }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }
            }
        },
        async sp_hen_ks_lapmoi_taptrung(data){
            try{
                this.loading(true)
                await API.sp_hen_ks_lapmoi_taptrung(this.axios, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async sp_lay_ds_hdtb_hen(hdkh_id){
            try{
                this.loading(true)
                let response = await API.sp_lay_ds_hdtb_hen(this.axios, hdkh_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result = response.data.data
                    //Upper case key json
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    return result
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_tt_hdkh_theo_hdkh_id(hdkh_id){
            try{
                this.loading(true)
                let response = await API.lay_tt_hdkh_theo_hdkh_id(this.axios, hdkh_id)
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
        async langoi_chuatraloi(hdkh_id, baohong_id){
            try{
                this.loading(true)
                let response = await API.langoi_chuatraloi(this.axios, hdkh_id, baohong_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    let result = response.data.data
                    //Upper case key json
                    for(var i = 0; i < result.length; i++){ 
                        for (var key in result[i]) {
                            if(key.toUpperCase() !== key){
                                result[i][key.toUpperCase()] = result[i][key];
                                delete result[i][key];
                            }
                        }
                    }
                    return result
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async giuphieu_ob_lm_taptrung(dshdtb, ma_nd){
            try{
                this.loading(true)
                await API.giuphieu_ob_lm_taptrung(this.axios, dshdtb, ma_nd)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        
        async sp_tb_traloi_theo_hdkh_id(hdkh_id){
            try{
                this.loading(true)
                let response = await API.sp_tb_traloi_theo_hdkh_id(this.axios, hdkh_id)
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
        async lay_ds_cauhoi_theoloainv(loainv_id, hinhthucks_id){
            try{
                this.loading(true)
                let response = await API.lay_ds_cauhoi_theoloainv(this.axios, loainv_id, hinhthucks_id)
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
        async sp_capnhat_trangthai_phieu_lapmoiob1(data){
            try{
                this.loading(true)
                let response = await API.sp_capnhat_trangthai_phieu_lapmoiob(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return 'ok'
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi, cập nhật trạng thái không thành công'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi, cập nhật trạng thái không thành công'
                }
            }
        },
        async dieulai_ob_taptrung(tbtl_id){
            try{
                this.loading(true)
                let response = await API.dieulai_ob_taptrung(this.axios, tbtl_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return 'ok'
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi'
                }
            }
        },
        async sp_delete_ct_tbtraloi(tbtl_id){
            try{
                this.loading(true)
                let response = await API.sp_delete_ct_tbtraloi(this.axios, tbtl_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    //return 'ok'
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xóa chi tiết tb trả lời không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xóa chi tiết tb trả lời không thành công!')
                }
            }
        },
        async sp_delete_tb_traloi(tbtl_id){
            try{
                this.loading(true)
                let response = await API.sp_delete_tb_traloi(this.axios, tbtl_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    //return 'ok'
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xóa tb trả lời không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xóa tb trả lời không thành công!')
                }
            }
        },
        async getKey(keyname){
            try{
                let response = await API.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async sp_insert_ct_tbtraloi(js_ct_tbtraloi){
            try{
                this.loading(true)
                let response = await API.sp_insert_ct_tbtraloi(this.axios, js_ct_tbtraloi)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    //return 'ok'
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, insert chi tiết tb trả lời không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, insert chi tiết tb trả lời không thành công!')
                }
            }
        },
         async sp_insert_tb_traloi(js_tbtraloi){
            try{
                this.loading(true)
                let response = await API.sp_insert_tb_traloi(this.axios, js_tbtraloi)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    //return 'ok'
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, insert tb trả lời không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, insert tb trả lời không thành công!')
                }
            }
        },
    },
    destroyed(){
        if(this.timeOut){
            clearInterval(this.timer)
        }
    }

}
</script>
<style>
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
</style>