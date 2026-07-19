<template>
    <div id="KhaoSatTBTapTrungBH" style="padding: 0px !important;">
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="legend-title">
                        <span @click="isExpandedTTTB=!isExpandedTTTB" class="icon fa fa-angle-up" :class="{'fa-angle-up':isExpandedTTTB, 'fa-angle-down':!isExpandedTTTB,}"></span>
                        Thông tin thuê bao
                    </div>
                    <div v-show="isExpandedTTTB">
                        <div class="info-row">
                            <div class="key w120">Mã thuê bao</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="clickCallMaTB" :disabled="disabledCallTB" :class="{disabledBtn:disabledCallTB}">
                                        <span class="-ap one-mobile-plus"></span>
                                    </button>
                                    <input type="text" :value="input_matb" @change="e=>input_matb=e.target.value" class="form-control bold highlight"/>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Loại hình/tốc độ</div>
                            <div class="value">
                                <input type="text" :value="input_loaihinh" @change="e=>input_loaihinh=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">TB cùng đôi cáp</div>
                            <div class="value">
                                <input type="text" :value="input_tb_cungcap" @change="e=>input_tb_cungcap=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Tên thuê bao</div>
                            <div class="value">
                                <input type="text" :value="input_tentb" @change="e=>input_tentb=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Địa chỉ LĐ</div>
                            <div class="value">
                                <input type="text" :value="input_diachi_ld" @change="e=>input_diachi_ld=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">SĐT khách hàng </div>
                            <div class="value">
                                <div class="input-more-button">
                                    <a @click.prevent="clickCallIPCC_KH" class="btn">
                                        <span class="icon one-mobile-plus"></span>
                                    </a>
                                    <input type="text" :value="input_sdt_kh" @change="e=>input_sdt_kh=e.target.value" class="form-control"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        <span @click="isExpandedTTCN=!isExpandedTTCN" class="icon fa fa-angle-up" :class="{'fa-angle-up':isExpandedTTCN, 'fa-angle-down':!isExpandedTTCN,}"></span>
                        Thông tin cập nhật
                    </div>
                    <div v-show="isExpandedTTCN">
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
                                        <input type="text" ref="inputNguoiTraLoi" :value="input_nguoitraloi" @change="e=>input_nguoitraloi=e.target.value"  class="form-control">
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
                                        <textarea :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control" style="height: 180px;resize: none;"></textarea>
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
                       <span @click="isExpandedTTBH=!isExpandedTTBH" class="icon fa fa-angle-up" :class="{'fa-angle-up':isExpandedTTBH, 'fa-angle-down':!isExpandedTTBH,}"></span>
                        Thông tin báo hỏng
                    </div>
                    <div v-show="isExpandedTTBH">
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width:auto !important;">Số lần hỏng trong 30 ngày:</div>
                                    <div class="value" style="padding-top: 4px !important;">
                                        <span class="text" style="color: red !important;">{{lbLanHong}}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Thời gian VP</div>
                                    <div class="value">
                                        <input type="text" :value="input_thoigian_vp" @change="e=>input_thoigian_vp=e.target.value" class="form-control red">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Ngày báo hỏng</div>
                                    <div class="value">
                                        <input type="text" :value="input_ngay_baohong" @change="e=>input_ngay_baohong=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Ngày HT</div>
                                    <div class="value">
                                        <input type="text" :value="input_ngay_ht" @change="e=>input_ngay_ht=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Người báo hỏng</div>
                                    <div class="value">
                                        <input type="text" :value="input_nguoi_baohong" @change="e=>input_nguoi_baohong=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row" v-if="layItemHenKT.Visible">
                                    <div class="key w120">Hẹn sửa chữa</div>
                                    <div class="value">
                                        <input type="text" :value="input_hen_suachua" @change="e=>input_hen_suachua=e.target.value" placeholder="Không có lịch hẹn kỹ thuật" class="form-control">
                                    </div>
                                </div>
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
                                            <input type="text" :value="input_sdt_baohong" @change="e=>input_sdt_baohong=e.target.value" class="form-control"/>
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
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Tình trạng</div>
                                    <div class="value">
                                        <input type="text" :value="input_tinhtrang" @change="e=>input_tinhtrang=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w120">Nguyên nhân</div>
                                    <div class="value">
                                        <input type="text" :value="input_nguyennhan" @change="e=>input_nguyennhan=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120">Ghi chú</div>
                            <div class="value">
                                <input type="text" :value="input_ghichu_nt" @change="e=>input_ghichu_nt=e.target.value"  class="form-control">
                            </div>
                        </div>
                    </div>
                    
                    
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
                        :panelDataHeight="getHeightRight"
                        :allowPaging="false"
                        :showFilter="true"
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
                        :panelDataHeight="getHeightRight"
                        :allowPaging="false"
                        :showFilter="true"
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
export default {
    name:'KhaoSatTBTapTrungBH',
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
                    fieldName: "GIOHEN_TU",
                    headerText: "Ngày hẹn từ",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "GIOHEN_DEN",
                    headerText: "Ngày hẹn đến",
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
                    fieldName: "NOIDUNG",
                    headerText: "Nội dung",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LYDOHEN",
                    headerText: "Lý do hẹn",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DV_HEN",
                    headerText: "ĐV hẹn",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "KIEU_HEN",
                    headerText: "Kiểu hẹn",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LYDO_XM",
                    headerText: "KQ xác minh",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGUOI_XM",
                    headerText: "NV xác minh",
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
            input_matb:'',
            input_loaihinh:'',
            input_tb_cungcap:'',
            input_tentb:'',
            input_diachi_ld:'',
            input_sdt_kh:'',
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
                Enabled:true
            },
            chkHenKS_Den:{
                Check:false,
                Enabled:true
            },
            input_nguoitraloi:'',
            input_sdt_capnhat:'',
            input_ghichu:'',
            input_thoigian_vp:'',
            input_ngay_baohong:'',
            input_ngay_ht:'',
            input_nguoi_baohong:'',
            input_hen_suachua:'',
            input_sdt_baohong:'',
            input_ngayhen_ks:'',
            input_tinhtrang:'',
            input_nguyennhan:'',
            input_ghichu_nt:'',
            ma_tb:'',
            thuebao_id:0,
            ma_tinh:'',
            dtThuebao:[],
            baohong_id:0,
            lbLanHong:'0',
            hdkh_id:0,
            sua_phieu:false,
            dtTBTL:[],
            tbtl_id:0,
            isNew:true,
            ngay_ks:'',
            CHOPHEP_SUA_OB_KHONG_TL:-1,
            ngay_bd_ks:null,
            vNGUOI_CN: '',
            vMAY_CN:'',
            vNHANVIEN_ID:0,
            vNGUOIDUNG_ID:0,
            vIP_CN:'',
            layGrpHenKT:{
                Visible:true
            },
            layItemHenKT:{
                Visible:true
            },
            dtCommon:[],
            dtCauHoi:[],
            dtCTTBTL:[],
            dsTraLoi:[],
            dsTb:[],
            IPCC_ID:0,
            vdacapnhat:false,
            giaophieu_ks_id:0,
            dsCT:[],
            isDieuLai: false,
            isDieuLai_XN: false,
            DieuLH_CD:false,
            isExpandedDsCT:true,
            isExpandedDsHen:true,
            isExpandedTTTB:true,
            isExpandedTTCN:true,
            isExpandedTTBH:true,

            disabledCallTB:true

        }
    },
    computed:{
        getHeightRight(){
            var count=2
            if(!this.isExpandedDsHen||!this.layGrpHenKT.Visible){
                count--
            }
            if(!this.isExpandedDsCT){
                count--
            }
            if(count==0){
                return '60'
            }
            return (160/count).toString()
        }
    },
    methods:{
        async HienThi_Phieu(dtIn){
            this.disabledCallTB=true
            this.ma_tinh=this.$root.token.getMaTinh()
            dtIn=dtIn.map(x=>{
                x.TINH=this.ma_tinh
                return x
            })
            console.log('HienThi_Phieu', dtIn)
            if(dtIn[0].DICHVUVT_ID==1){
                this.disabledCallTB=false
            }
            // setTimeout(()=>{
            //     this.disabledCallTB=false
            // }, 2000)
            this.ReLoadDivisionalData(dtIn[0]);
            await this.HienThiThongTinTB(dtIn[0].BAOHONG_ID);
            await this.HienThiCH()
        },
        ReLoadDivisionalData(dr){
            //Không thực hiện
        },
        async HienThiThongTinTB(baohong_id){
             console.log('HienThiThongTinTB', baohong_id)
            this.dtThuebao=await this.lay_tt_baohong_theo_baohong_id(baohong_id)
            if(this.dtThuebao.length==0){
                return
            }
            let dr=this.dtThuebao[0]
            this.baohong_id = baohong_id;
            this.thuebao_id = dr.thuebao_id?dr.thuebao_id:0
            this.input_matb=dr.ma_tb?dr.ma_tb.toString():''
            this.ma_tb=dr.ma_tb?dr.ma_tb.toString():''
            this.input_tentb=dr.ten_tb?dr.ten_tb.toString():''
            this.input_diachi_ld=dr.diachi_ld?dr.diachi_ld.toString():''
            this.input_sdt_kh=dr.so_dt_kh?dr.so_dt_kh.toString():''
            // Lấy tốc độ loại hình
            this.input_loaihinh=await this.fn_lay_tocdo_loaihinh(dr.dichvuvt_id, dr.ma_tb)
            // txtMaTB.Properties.Buttons[0].Visible = dr["dichvuvt_id"].ToString() == "1" || dr["dichvuvt_id"].ToString() == "11"
            // Thông tin báo hỏng
            this.input_ngay_baohong=dr.ngay_bh?dr.ngay_bh.toString():''
            this.input_nguoi_baohong=dr.nguoi_bh?dr.nguoi_bh.toString():''
            this.input_sdt_baohong=dr.dienthoai_lh?dr.dienthoai_lh.toString():''

            if(dr.giohen_tu){
                //không rõ làm gì
                //     StringBuilder sb = new StringBuilder();
                //     sb.AppendFormat("{0:dd/MM/yyyy HH:mm}", dr["giohen_tu"]);
                //     if (dr["NV_HEN"] != DBNull.Value)
                //         sb.Append(" - " + dr["NV_HEN"].ToString());
                //     if (dr["NOIDUNG_HEN"] != DBNull.Value)
                //         sb.Append(" - " + dr["NOIDUNG_HEN"].ToString());
            }
            if(dr.ngay_ht){
                this.input_ngay_ht=dr.ngay_ht.toString()
            }
            this.input_tinhtrang=dr.tinhtrang?dr.tinhtrang.toString():''
            this.input_nguyennhan=dr.nguyennhan?dr.nguyennhan.toString():''

            this.input_ghichu_nt=dr.ghichu_xl?dr.ghichu_xl.toString():''
            this.input_thoigian_vp=dr.vpxl?dr.vpxl.toString():''
            // Lấy thông tin hỏng nhiều lần trong 30 ngày
            this.lbLanHong=(await this.fn_lanhong_trong30ngay(this.thuebao_id)).toString()
            //langoi_chuatraloi
            this.dataSourcesDs=await this.langoi_chuatraloi(this.hhdkh_id, this.baohong_id)
            if(this.sua_phieu){
                this.dtTBTL=await this.sp_tb_traloi_theo_tbtl_id(this.tbtl_id)
            }else{
                this.dtTBTL=await this.sp_tb_traloi_theo_baohong_id(this.baohong_id)
            }

            if(this.dtTBTL.length>0){
                this.isNew=false
                if(this.tbtl_id==0){
                    this.tbtl_id=this.dtTBTL[0].tbtl_id
                }
                this.input_nguoitraloi=this.dtTBTL[0].nguoi_tl?this.dtTBTL[0].nguoi_tl.toString():''
                this.input_sdt_capnhat=this.dtTBTL[0].dienthoai_lh?this.dtTBTL[0].dienthoai_lh.toString():''
                this.input_ghichu=this.dtTBTL[0].ghichu?this.dtTBTL[0].ghichu.toString():''
                this.chkKhongTL.Check=this.dtTBTL[0].trangthai==0
                //"ngay_hoi": "2022-12-09 17:33:31",
                this.ngay_ks = this.dtTBTL[0].ngay_hoi?this.dtTBTL[0].ngay_hoi.toString():''
                if(this.chkKhongTL.Check){
                    this.isNew=true
                    if(this.CHOPHEP_SUA_OB_KHONG_TL==-1){
                        this.tbtl_id=0
                    }
                    this.cboLyDoKTL.value=this.dtTBTL[0].lydoktl_id
                    console.log('cboLyDoKTL.value', this.cboLyDoKTL.value)
                }
                this.ngay_bd_ks=this.dtTBTL[0].ngay_bd
                this.vNGUOI_CN=this.dtTBTL[0].nguoi_cn
                this.vMAY_CN=this.dtTBTL[0].may_cn
                this.vNHANVIEN_ID=this.dtTBTL[0].nhanvien_id
                this.vNGUOIDUNG_ID=this.dtTBTL[0].nguoidung_id
                this.vIP_CN=this.dtTBTL[0].nguoidung_id


            }else{
                this.isNew = true
                this.tbtl_id = 0
            }

            this.input_ngayhen_ks=dr.giohen_tu?moment(dr.giohen_tu, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
            if(!this.sua_phieu&&this.dtTBTL.filter(x=>x.trangthai==1).length>0){
                this.$toast.error('Bất đồng bộ với hệ thống OB cũ, toàn bộ các bó hỏng của khách hàng đã được khảo sát')
                await this.sp_capnhat_trangthai_phieu_baohongob({
                    vbaohong_id:this.baohong_id, 
                    vma_nd:this.$root.token.getUserName(), 
                    vtrangthai:3, 
                    vip_cn:"127.0.0.1"
                })
                return
            }
            let ds=await this.lay_lichsu_hen_baohong(this.baohong_id)
            if(ds&&ds.length>0){
                this.layGrpHenKT.Visible = true
                this.layItemHenKT.Visible = false
                this.dataSourcesLichSuHen=ds
            }else{
                this.layGrpHenKT.Visible = false
                this.layItemHenKT.Visible = true
            }
        },
        async HienThiCH(){
            //Khởi tạo câu hỏi
            this.dtCauHoi=await this.lay_ds_cauhoi_theoloainv(6, 2)
            //Kiểu =1
            let dtCauHoi_1=this.dtCauHoi.filter(x=>x.kieu==1).sort((a,b)=>{return a.thutu-b.thutu})
            let idCauHois=dtCauHoi_1.map(x=>x.cauhoi_id)
            idCauHois=idCauHois.filter((item, index)=>idCauHois.indexOf(item) === index)
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
            let dtCauHoi_2=this.dtCauHoi.filter(x=>x.kieu==2).sort((a,b)=>{return a.thutu-b.thutu})
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
                
                let dtTraLoi_2=this.dtCauHoi.filter(x=>x.cauhoi_id==dtCauHoi_2[i].cauhoi_id).sort((a,b)=>{return a.thutu_tl-b.thutu_tl}).map(x=>{
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
            //Khởi tạo đáp án
            if(!this.isNew){
                this.dtCTTBTL=await this.sp_ct_tbtraloi(this.tbtl_id)
                for(let i=0;i<this.dtCTTBTL.length;i++){
                    const indexCauHoi=this.dtCommon.findIndex(x=>x.QuestionID==this.dtCTTBTL[i].cauhoi_id)
                    if(indexCauHoi>-1){
                        const indexDapAn=this.dtCommon[indexCauHoi].DsCauTraLoi.findIndex(x=>x.AnswerID==this.dtCTTBTL[i].traloi_id)
                        if(indexDapAn>-1){
                            this.dtCommon[indexCauHoi].DsCauTraLoi[indexDapAn].Check=true
                            this.dtCommon[indexCauHoi].DsCauTraLoi[indexDapAn].AnswerTextDetail=this.dtCTTBTL[i].noidung?this.dtCTTBTL[i].noidung.toString():null

                            continue
                        }
                    }
                    //Khởi tạo câu hỏi phụ
                    for(let k=0;k<this.dtCommon.length;k++){
                        for(let j=0;j<this.dtCommon[k].DsCauTraLoi.length;j++){
                            if(this.dtCommon[k].DsCauTraLoi[j].Parent){
                                const indexCauHoiPhu=this.dtCommon[k].DsCauTraLoi[j].Parent.ChildQuestion.findIndex(x=>x.QuestionID==this.dtCTTBTL[i].cauhoi_id)
                                if(indexCauHoiPhu<0){
                                    continue
                                }

                                const indexDapAnPhu=this.dtCommon[k].DsCauTraLoi[j].Parent.ChildQuestion[indexCauHoiPhu].DsCauTraLoi.findIndex(x=>x.AnswerID==this.dtCTTBTL[i].traloi_id)
                                if(indexDapAnPhu>-1){
                                    this.dtCommon[k].DsCauTraLoi[j].Parent.ChildQuestion[indexCauHoiPhu].DsCauTraLoi[indexDapAnPhu].Check=true
                                    this.dtCommon[k].DsCauTraLoi[j].Parent.ChildQuestion[indexCauHoiPhu].DsCauTraLoi[indexDapAnPhu].AnswerTextDetail=this.dtCTTBTL[i].noidung?this.dtCTTBTL[i].noidung.toString():null
                                }
                                console.log('vào đây test')
                            }
                        }
                    }
                    
                }
            }

        },
        Clear(){
            this.hen_tu=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.hen_den=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.dataSourcesLichSuHen=[]
            this.dataSourcesDs=[]
            this.input_matb=''
            this.input_loaihinh=''
            this.input_tb_cungcap=''
            this.input_tentb=''
            this.input_diachi_ld=''
            this.input_sdt_kh=''
            this.chkKhongTL={
                Check:false,
                Enabled:true
            }
            this.chkHenKS_Tu={
                Check:false,
                Enabled:true
            }
            this.chkHenKS_Den={
                Check:false,
                Enabled:true
            }
            this.input_nguoitraloi=''
            this.input_sdt_capnhat=''
            this.input_ghichu=''
            this.input_thoigian_vp=''
            this.input_ngay_baohong=''
            this.input_ngay_ht=''
            this.input_nguoi_baohong=''
            this.input_hen_suachua=''
            this.input_sdt_baohong=''
            this.input_ngayhen_ks=''
            this.input_tinhtrang=''
            this.input_nguyennhan=''
            this.input_ghichu_nt=''
            this.ma_tb=''
            this.thuebao_id=0
            this.dtThuebao=[]
            this.baohong_id=0
            this.lbLanHong='0'
            this.hdkh_id=0
            this.sua_phieu=false
            this.dtTBTL=[]
            this.tbtl_id=0
            this.isNew=true
            this.ngay_ks=''
            this.CHOPHEP_SUA_OB_KHONG_TL=-1
            this.ngay_bd_ks=null
            this.vNGUOI_CN= ''
            this.vMAY_CN=''
            this.vNHANVIEN_ID=0
            this.vNGUOIDUNG_ID=0
            this.vIP_CN=''
            this.layGrpHenKT={
                Visible:true
            }
            this.layItemHenKT={
                Visible:true
            }
            this.dtCommon=[]
            this.dtCauHoi=[]
            this.dtCTTBTL=[]
            this.dsTraLoi=[]
            this.dsTb=[]
            this.IPCC_ID=0
            this.vdacapnhat=false
            this.giaophieu_ks_id=0
            this.dsCT=[]
            this.isDieuLai = false
            this.isDieuLai_XN = false
            this.DieuLH_CD=false
            this.isExpandedDsCT=true
            this.isExpandedDsHen=true
            this.isExpandedTTTB=true
            this.isExpandedTTCN=true
            this.isExpandedTTBH=true


        },
        clickCallIPCC_KH(){
            this.$emit('clickCallIPCC', this.input_sdt_kh.trim())
        },
        clickCallIPCC(){
            this.$emit('clickCallIPCC', this.input_sdt_baohong.trim())
        },
        clickCallMaTB(){
            this.$emit('clickCallIPCC', this.input_matb.trim())
        },
        async tsbtnGhiLai_Click(){
            this.vdacapnhat=false
            let kt=await this.KiemTra_DuLieu(true)
            if(!kt){
                return
            }
            if(this.isDieuLai){
                let result1 = await this.confirm(`Cập nhật nội dung này sẽ điều phiếu hỏng cho thuê bao ${this.input_matb}. Bạn có chắc chắn muốn điều?`,'')
                if(result1==0){
                    return
                }
            }
            await this.sp_delete_ct_tbtraloi(this.tbtl_id)
            await this.sp_delete_tb_traloi(this.tbtl_id)
            await this.TaoDuLieu(true, false)
            await this.sp_insert_tb_traloi(JSON.stringify(this.dsTb))
            await this.sp_insert_ct_tbtraloi(JSON.stringify(this.dsCT))
            this.vdacapnhat=true
            
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
                await this.sp_hen_ks_baohong_taptrung({
                    vbaohong_id:this.baohong_id, 
                    vkstu: ksTu, 
                    vksden:ksDen
                })
                await this.sp_capnhat_trangthai_phieu_baohongob({
                    vbaohong_id:this.baohong_id, 
                    vma_nd:this.$root.token.getUserName(), 
                    vtrangthai:4, 
                    vip_cn:"127.0.0.1"
                })
            }else if(this.chkKhongTL.Check){
                var ksTu=null
                //LydoKTL.SaiSDT=31
                if(this.dataSourcesDs.filter(x=>x.LYDOKTL_ID!=null&&x.NHANVIEN_ID!=36004).length>=2||this.cboLyDoKTL.value==31){
                    await this.sp_capnhat_trangthai_phieu_baohongob({
                        vbaohong_id:this.baohong_id, 
                        vma_nd:this.$root.token.getUserName(), 
                        vtrangthai:7, 
                        vip_cn:"127.0.0.1"
                    })
                }else if(this.cboLyDoKTL.value==30||this.cboLyDoKTL.value==32){
                    var ksTu=moment(new Date()).add(1, 'hours').format('DD/MM/YYYY HH:mm:ss')
                    await this.sp_hen_ks_baohong_taptrung({
                        vbaohong_id:this.baohong_id, 
                        vkstu: ksTu, 
                        vksden:null
                    })
                    await this.sp_capnhat_trangthai_phieu_baohongob({
                        vbaohong_id:this.baohong_id, 
                        vma_nd:this.$root.token.getUserName(), 
                        vtrangthai:4, 
                        vip_cn:"127.0.0.1"
                    })
                }
            }else{
                await this.sp_capnhat_trangthai_phieu_baohongob({
                    vbaohong_id:this.baohong_id, 
                    vma_nd:this.$root.token.getUserName(), 
                    vtrangthai:6, 
                    vip_cn:"127.0.0.1"
                })
            }

            // Gửi đề xuất phạt chất lượng lên iBSC khi kết quả khảo sát là KHL do NVKT
            let kt1=await this.lay_ds_thamso_md_mats('DEXUAT_BSC_KHL_NVKT')
            if(kt1&&kt1.length>0&&kt1[0].ten_ts=='1'){
                await this.dexuat_bsc_khl_nvkt(this.tbtl_id)
            }
            this.$toast.success('Cập nhật thành công!')
            if(this.isDieuLai){
                await this.DIEUHONGLAI()
            }
            if(this.isDieuLai_XN){
                await this.DIEULAI_XN()
            }
            await this.capnhat_phieu_dieulai_xn(this.tbtl_id, this.$root.token.getNhanVienID(), this.$root.token.getDonViID())
        },
        async tsbtnDieuLai_Click(){
            let ktDHL=await this.fn_kiemtradieuhonglai(this.baohong_id, 0)
            if(ktDHL==1){
                this.$toast.error('Thuê bao đang có phiếu điều hỏng chưa xử lý xong! Bạn không thể điều lại!')
                return
            }
            console.log('tsbtnDieuLai_Click isNew', this.isNew)
            if(this.isNew){
                let result = await this.confirm(`Bạn có chắc chắn muốn điều hỏng thuê bao ${this.input_matb} ?`,'')
                if(result==0){
                    return
                }
                let ktDV=await this.KiemTra_DuLieu(true)
                if(!ktDV){
                    return
                }

                await this.TaoDuLieu(true, true)
                await this.sp_insert_tb_traloi(JSON.stringify(this.dsTb))
                await this.sp_delete_ct_tbtraloi(this.tbtl_id)
                await this.sp_insert_ct_tbtraloi(JSON.stringify(this.dsCT))
                await this.DIEUHONGLAI()
                let kt=await this.lay_ds_thamso_md_mats('CAPNHAT_SO_DT_OUTBOUND')
                if(kt&&kt.length>0&&kt[0].ten_ts=='1'){
                    if(!this.chkKhongTL.Check){
                        let result1 = await this.confirm(`Bạn đã liên hệ được với khách hàng. Bạn có muốn cập nhật số điện thoại: ${this.input_sdt_capnhat} cho thuê bao ${this.input_matb} ?`,'')
                        if(result1==0){
                            return
                        }
                        let rs=await this.capnhat_so_dt_khi_outbound(this.thuebao_id, this.input_sdt_capnhat.trim())
                        if(rs=='ok'){
                            this.$toast.success('Cập nhật thông tin liên hệ thành công!')
                        }else{
                            this.$toast.error(rs)
                        }
                    }
                }
            }
        },
        async tsbtnBoQua_Click(){
            let result = await this.confirm(`Bạn có muốn lùi thời gian khảo sát lại không ?`,'Bỏ qua phiếu')
            if(result==1){
                await this.sp_hen_ks_baohong_taptrung({
                    vbaohong_id:this.baohong_id, 
                    vkstu: moment(new Date()).add(2, 'minutes').format('DD/MM/YYYY HH:mm:ss'), 
                    vksden:null
                })
                await this.sp_capnhat_trangthai_phieu_baohongob({
                    vbaohong_id:this.baohong_id, 
                    vma_nd:this.$root.token.getUserName(), 
                    vtrangthai:4, 
                    vip_cn:"127.0.0.1"
                })
            }else{
                await this.sp_capnhat_trangthai_phieu_baohongob({
                    vbaohong_id:this.baohong_id, 
                    vma_nd:this.$root.token.getUserName(), 
                    vtrangthai:4, 
                    vip_cn:"127.0.0.1"
                })
            }
            
        },
        async DIEUHONGLAI(){
            //dieulai_ob_taptrung
            let result=await this.dieulai_ob_taptrung(this.tbtl_id)
            if(result!='ok'){
                this.$toast.error(result)
            }
        },
        async DIEULAI_XN(){
            let result=await this.dieulai_ob_taptrung(this.tbtl_id)
            if(result!='ok'){
                this.$toast.error(result)
            }
        },
        async TaoDuLieu(themmoi, xuatlai){
            this.dsTb=[]
            var object={}
            if(themmoi){
                this.tbtl_id=await this.getKey('TBTL_ID')
            }
            object.TBTL_ID=this.tbtl_id
            object.BAOHONG_ID=this.baohong_id
            object.HDKH_ID=this.hdkh_id
            object.HINHTHUC = 1
            object.THUEBAO_ID = this.thuebao_id
            object.NGUOI_TL = this.input_nguoitraloi.trim()
            object.DIENTHOAI_LH = this.input_sdt_capnhat.trim()
            object.GHICHU = this.input_ghichu.trim()
            object.TRANGTHAI = this.chkKhongTL.Check?0:1
            if(this.chkKhongTL.Check){
                object.LYDOKTL_ID=this.cboLyDoKTL.value
            }
            if(xuatlai){
                object.TRANGTHAI = 2
            }
            if(themmoi){
				object.NGAY_HOI = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            }else{
                object.NGAY_HOI=this.ngay_ks
            }
            if(this.IPCC_ID>0){
                object.IPCC_ID=this.IPCC_ID
            }
            if(!this.sua_phieu){
                object.NGUOI_CN = this.$root.token.getUserName()
                object.MAY_CN = 'localhost'
                object.NHANVIEN_ID = this.$root.token.getNhanVienID()
                object.NGUOIDUNG_ID = this.$root.token.getNguoiDungID()
                object.IP_CN = '127.0.0.1'
            }
            if(this.chkKhongTL.Check){
                object.LYDOKTL_ID=this.cboLyDoKTL.value
                object.TTKS_ID=1

                if(this.dataSourcesDs.filter(x=>x.LYDO_KTL==null).length>3){
                    //Kết thúc KH không gọi lại KH nữa.
                    object.TTKS_ID = 7
                }

                this.vdacapnhat=true
            }else{
                object.TTKS_ID = 6
                this.vdacapnhat = true
            }

            // if (OB_KHONGTL_KTC1 == 1 && chkKTL.Checked && cboLyDoKTL.EditValue.Equals(LydoKTL.Khac))
            // {
            //     row.TTKS_ID = 6;
            //     vdacapnhat = true;
            // }
            if (this.giaophieu_ks_id != 0){
                object.GIAOPHIEU_KS_ID = this.giaophieu_ks_id
            }
            this.dsTb.push(object)

            this.dsCT=[]
            this.isDieuLai = false
            var rs = await this.getKetQua()

            for(let i=0;i<rs.length;i++){
                var object1={}
                object1.CAUHOI_ID = rs[i].CAUHOI_ID?rs[i].CAUHOI_ID:0
                object1.TRALOI_ID = rs[i].TRALOI_ID?rs[i].TRALOI_ID:0
                object1.TBTL_ID = this.tbtl_id
                object1.NOIDUNG = rs[i].NOIDUNG?rs[i].NOIDUNG.toString():''

                if(rs[i].dieulai==1){
                    this.isDieuLai=true
                }
                if(rs[i].dieulai_xn==1){
                    this.isDieuLai_XN=true
                }
                this.dsCT.push(object1)
            }
        },
        async KiemTra_DuLieu(themmoi){
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
            
			if(this.chkKhongTL.Check && ![30,31,32].includes(this.cboLyDoKTL.value)) {
                this.$toast.error('Hãy chọn Lý do không trả lời')
                return false
            }
			
            if(this.chkHenKS_Tu.Check){
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
            let rs=await this.getKetQua()
            if(rs.length>0&&this.chkKhongTL.Check){
                this.$toast.error('Bạn đang chọn \"Không trả lời\" và đồng thời tích chọn trả lời trong Danh sách câu hỏi!')
                return false
            }
            if(!this.chkKhongTL.Check){
                // nếu check vào KTL rồi thì ko cần kiểm tra
                if(rs.length==0){
                    this.$toast.error('Bạn phải chọn câu trả lời!')
                    return false
                }
                var kiemtra_quagio=false
                //Khảo sát chưa có hàm bỏ qua
                for(let i=0;i<rs.length;i++){
                    //         int traloi_id = Convert.ToInt32(item["RCH_ID"]);
                    //         //vinhpv kiem tra qua gio HPG
                    //         if (item["TRALOI_ID"].ToString() == "53")
                    //             kiemtra_quagio = true;
                    //         var a = fims.GetDataTable("select * from " + DatabaseConstants.DB9 + ".cauhoi_phu where rch_id =" + traloi_id);
                    //         if (a.Rows.Count > 0)
                    //         {
                    //             var b = from r1 in a.AsEnumerable()
                    //                     join r2 in rs.AsEnumerable() on
                    //                     r1["CAUHOI_ID"].ToString() equals r2["CAUHOI_ID"].ToString() into r3
                    //                     from r in r3
                    //                     select new { c = r };
                    //             //select new { g = r1 };
                    //             if (b.Count() <= 0)
                    //             {
                    //                 if (fims.Kiemtra_cauhoi_hailong(traloi_id) != "1")
                    //                 {
                    //                     MessageBox.Show("Bạn chọn câu trả lời '" + item["TRALOI"].ToString() + "', mà chưa chọn lý do ! ");
                    //                     txtTenNguoiTL.Focus();
                    //                     return false;
                    //                 }
                    //             }
                    //         }
                    //         // vinhpv kiểm tra các câu trả lời ý kiến khác mà không nhập text.
                    //         int tlid = Convert.ToInt32(item["TRALOI_ID"]);
                    //         a = fims.GetDataTable("select * from " + DatabaseConstants.DB9 + ".traloi where traloi_id =" + tlid);
                    //         if (a.Rows.Count > 0)
                    //         {
                    //             if ((a.Rows[0]["LOAI"].ToString() == "1" || a.Rows[0]["LOAI"].ToString() == "2") && item["NOIDUNG"].ToString().Trim() == "")
                    //             {
                    //                 MessageBox.Show("Bạn chưa nhập nội dung cho câu trả lời '" + item["TRALOI"].ToString() + " ! ");
                    //                 txtTenNguoiTL.Focus();
                    //                 return false;
                    //             }

                    //             //Nếu câu trả lời lq đến NET thì fai tồn tại hướng giao về Net - VB 1348/VNPT-CLG-TL
                    //             if (a.Rows[0]["MA_TL"].ToString() == "NET1" || a.Rows[0]["MA_TL"].ToString() == "NET2" || a.Rows[0]["MA_TL"].ToString() == "NET3")
                    //                 if (fims.kiemtra_giaophieu_net_v2(baohong_id, 2) != "1")
                    //                 {
                    //                     MessageBox.Show("Bạn chỉ được chọn hạng mục 'NET1', 'NET2', 'NET3' sau khi kiểm tra có phiếu VNPT Net xử lý");
                    //                     return false;
                    //                 }
                    //         }

                    //         if (kt_dambao_ckcl_kh)
                    //         {
                    //             if (baohong_ckcl)
                    //             {
                    //                 DataRow[] drRTL = rs.Select("CAUHOI_ID = 8841");
                    //                 if (drRTL == null || (drRTL != null && drRTL.Count() <= 0))
                    //                 {
                    //                     MessageBox.Show("Thuê bao được hưởng khuyến mại cam kết chất lượng. Bắt buộc chọn Câu hỏi 5!");
                    //                     return false;
                    //                 }
                    //                 int ch_id = Convert.ToInt32(item["CAUHOI_ID"]);
                    //                 if (ch_id == 8841 && tlid == 263)
                    //                 {
                    //                     MessageBox.Show("Thuê bao được hưởng khuyến mại cam kết chất lượng. Câu hỏi 5 không thể chọn trả lời 'Bỏ qua tư vấn'!");
                    //                     return false;
                    //                 }
                    //             }
                    //         }
                }
                //     if (DatabaseConstants.DB2 == "CSS_HPG")// fix HPG
                //     {
                //         if (txtVPXL.Text.Trim() != "")
                //             if (Convert.ToDecimal(txtVPXL.Text) < 0 && kiemtra_quagio)
                //             {
                //                 MessageBox.Show(@"Trường hợp này thi công/xử lý chưa quá giờ! Bạn hay chọn Hài long, đồng thời chuyển sang câu 'Ý kiến hài lòng của khách hàng' và chọn tiếp 'Thời gian sửa chữa, lắp đặt trên hệ thống đúng quy định của VNPT nhưng khách hàng vẫn phàn nàn chậm'");
                //                 txtTenNguoiTL.Focus();
                //                 return false;
                //             }

                //         // Kiểm tra nhóm câu hỏi
                //         var dtNhomCH = bangts.GetDataSql(@"select a.nhomch_id, a.sl_tu, a.sl_den, c.cauhoi_id, c.noidung, b.batbuoc from " + DatabaseConstants.DB9 + @".nhom_ch a, " + DatabaseConstants.DB9 + @".cauhoi_nch b, " + DatabaseConstants.DB9 + @".cauhoi c 
                //     where a.nhomch_id = b.nhomch_id and a.nhomch_id = 2 and b.batbuoc = 1 and b.cauhoi_id = c.cauhoi_id");

                //         if (dtNhomCH.Rows.Count > 0)
                //         {
                //             int sl_tu = Convert.ToInt32(dtNhomCH.Rows[0]["SL_TU"]);
                //             int sl_den = Convert.ToInt32(dtNhomCH.Rows[0]["SL_DEN"]);
                //             var ch_batbuoc = dtNhomCH.AsEnumerable().Select(p => Convert.ToInt32(p["CAUHOI_ID"])).ToList();
                //             var ch_chon = rs.AsEnumerable().Select(p => Convert.ToInt32(p["CAUHOI_ID"])).Distinct().ToList().Where(s => ch_batbuoc.Contains(s)).Count();
                //             if (ch_chon < sl_tu || ch_chon > sl_den)
                //             {
                //                 MessageBox.Show("Bạn phải chọn 1 trong các lựa chọn 'hài lòng', 'không hài lòng', 'không tính mẫu', 'không thành công' ");
                //                 txtTenNguoiTL.Focus();
                //                 return false;
                //             }
                //         }
                //     }

            }
            // Kiểm tra chỉ user người khảo sát mới được sửa nội dung ks và chỉ sửa trong tháng
            if(!themmoi){
                if(this.$root.token.getPhanVungID()==28){
                    if(this.$root.token.getUserName().startsWith('admin')){
                        let kt=await this.lay_ds_thamso_md_mats('TOTRUONG_SUA_KHAOSAT')
                        if(kt&&kt.length>0&&kt[0].ten_ts=='1'){
                            let ktra=await this.kiemtra_nhanvien_sua_khaosat(this.tbtl_id, this.giaophieu_ks_id, 2)
                            if(ktra != "ok"){
                                this.$toast.error(ktra)
                                return false
                            }
                        }else{
                            let b=await this.sp_lay_tb_traloi_nd(this.tbtl_id, this.$root.token.getUserName())
                            if(b.length<=0){
                                this.$toast.error('Bạn không có quyền sửa nội dung cuộc khảo sát này !')
                                this.$refs.inputNguoiTraLoi.focus()
                                return false
                            }
                        }
                    }
                }else{
                    //kiểm tra quyền nhân viên và thời gian sửa phiếu
                    let ktra=await this.fn_quyen_sua_khaosat(this.tbtl_id, this.giaophieu_ks_id, 2)
                    if(ktra != "ok"){
                        this.$toast.error(ktra)
                        return false
                    }
                }
            }
            
            if (!(await this.KiemTraSoDienThoaiCoDinh())){
                return false
            }

            return true

        },
        async KiemTraSoDienThoaiCoDinh(){
            //Kiểm tra tham số MĐ
            this.DieuLH_CD = false
            let dt=await this.lay_ds_thamso_md_mats('BATBUOC_CAPNHAT_SOLH_DD')
            if(dt&&dt.length>0&&dt[0].ten_ts=='1'){
                if(this.input_sdt_capnhat.trim().length<10){
                    this.$toast.error('Số điện thoại không đúng chuẩn !')
                    this.$refs.inputSoLHCN.focus()
                    return false
                }
                if(this.input_sdt_capnhat.trim().substring(0, 1)!='0'){
                    this.$toast.error('Số điện thoại không đúng chuẩn !')
                    this.$refs.inputSoLHCN.focus()
                    return false
                }
                if(this.input_sdt_capnhat.trim().length>10){
                    let result = await this.confirm(`Số liên hệ hiện tại là số cố định/gphone/ims, bạn có muốn thay đổi thành số di động không?`,'CSKH Tập trung')
                    if(result==1){
                        this.$refs.inputSoLHCN.focus()
                        return false
                    }

                    if(this.input_sdt_capnhat.trim().substring(0, 4)!='0225'){
                        this.$toast.error(`Số liên hệ ${this.input_matb}  không tồn tại trong danh bạ hoặc không hoạt động bình thường!`)
                        this.$refs.inputSoLHCN.focus()
                        return false
                    }

                    //         //Kiểm tra số cố định có trong danh bạ của mình hay không
                    //         dt = bangts.GetDataSql("select 1 from " + DatabaseConstants.DB2 + ".db_thuebao where trangthaitb_id = 1 and dichvuvt_id in (1,10,11) and ma_tb = '" + txtSoLH.Text.Remove(0, 4) + "'");
                    //         if (dt.Rows.Count == 0)
                    //         {
                    //             MessageBox.Show("Số liên hệ " + txtSoLH.Text + " không tồn tại trong danh bạ hoặc không hoạt động bình thường!");
                    //             txtSoLH.Focus();
                    //             return false;
                    //         }
                    //         //Kiểm tra xem KH có dùng dịch vụ khác CĐ, IMS Gphone không, nếu có dùng thì điều thu thập liên hệ
                    //         dt = bangts.GetDataSql("select * from " + DatabaseConstants.DB2 + ".db_thuebao where dichvuvt_id not in (1,10,11) and khachhang_id in (select khachhang_id from " + DatabaseConstants.DB2 + ".db_thuebao a  where a.thuebao_id = " + thuebao_id + ")");
                    //         DieuLH_CD = (dt.Rows.Count > 0);



                }
            }
            return true
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
            return dsTraLoi.filter(x=>x.Check).map(x=>{
                return {
                    TBTL_ID:this.targetTBTL_ID,
                    CAUHOI_ID:x.QuestionID,
                    TRALOI_ID:x.AnswerID,
                    NOIDUNG:x.AnswerTextDetail
                }
            })
        },
        validatePhoneNumber(input_str) {
            var re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
            return re.test(input_str);
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
        async lay_tt_baohong_theo_baohong_id(baohong_id){
            try{
                this.loading(true)
                let response = await API.lay_tt_baohong_theo_baohong_id(this.axios,baohong_id)
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
        async fn_lay_tocdo_loaihinh(dichvuvt_id, ma_tb){
            try{
                this.loading(true)
                let response = await API.fn_lay_tocdo_loaihinh(this.axios, dichvuvt_id, ma_tb)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async fn_lanhong_trong30ngay(thuebao_id){
            try{
                this.loading(true)
                let response = await API.fn_lanhong_trong30ngay(this.axios, thuebao_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
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
        async sp_tb_traloi_theo_tbtl_id(tbtl_id){
            try{
                this.loading(true)
                let response = await API.sp_tb_traloi_theo_tbtl_id(this.axios,tbtl_id)
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
        async sp_tb_traloi_theo_baohong_id(baohong_id){
            try{
                this.loading(true)
                let response = await API.sp_tb_traloi_theo_baohong_id(this.axios,baohong_id)
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
        async lay_lichsu_hen_baohong(baohong_id){
            try{
                this.loading(true)
                let response = await API.lay_lichsu_hen_baohong(this.axios, baohong_id)
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
        async sp_capnhat_trangthai_phieu_baohongob(data){
            try{
                this.loading(true)
                let response=await API.sp_capnhat_trangthai_phieu_baohongob(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
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
        async sp_hen_ks_baohong_taptrung(data){
            try{
                this.loading(true)
                let response= await API.sp_hen_ks_baohong_taptrung(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data!='ok'){
                        this.$toast.error(response.data.data)
                    }
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
        async sp_ct_tbtraloi(tbtl_id){
            try{
                this.loading(true)
                let response = await API.sp_ct_tbtraloi(this.axios, tbtl_id)
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
        async lay_ds_thamso_md_mats(ma_ts){
            try{
                this.loading(true)
                let response = await API.lay_ds_thamso_md_mats(this.axios, ma_ts)
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
        async capnhat_so_dt_khi_outbound(thuebao_id, somay){
            try{
                this.loading(true)
                let response = await API.capnhat_so_dt_khi_outbound(this.axios, thuebao_id, somay)
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
        async fn_kiemtradieuhonglai(baohong_id, hdtb_id){
            try{
                this.loading(true)
                let response = await API.fn_kiemtradieuhonglai(this.axios, baohong_id, hdtb_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
            }
        },
        async dexuat_bsc_khl_nvkt(tbtl_id){
            try{
                this.loading(true)
                let response = await API.dexuat_bsc_khl_nvkt(this.axios, tbtl_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
            }
        },
        async capnhat_phieu_dieulai_xn(tbtl_id, nhanvien_id, donvi_id){
            try{
                this.loading(true)
                let response = await API.capnhat_phieu_dieulai_xn(this.axios, tbtl_id, nhanvien_id, donvi_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                this.loading(false)
                return 0
            }
        },

        async kiemtra_nhanvien_sua_khaosat(tbtl_id, giaophieu_ks_id, kieu){
            try{
                this.loading(true)
                let response = await API.kiemtra_nhanvien_sua_khaosat(this.axios, tbtl_id, giaophieu_ks_id, kieu)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
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
        async fn_quyen_sua_khaosat(tbtl_id, giaophieu_ks_id, loaiphieu_id){
            try{
                this.loading(true)
                let response = await API.fn_quyen_sua_khaosat(this.axios, tbtl_id, giaophieu_ks_id, loaiphieu_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
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
        async sp_lay_tb_traloi_nd(tbtl_id, ma_nd){
            try{
                this.loading(true)
                let response = await API.sp_lay_tb_traloi_nd(this.axios, tbtl_id, ma_nd)
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
    }
}
</script>
<style>
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
.disabledBtn{
    color: gray !important;
    border-color: gray!important;
}
</style>