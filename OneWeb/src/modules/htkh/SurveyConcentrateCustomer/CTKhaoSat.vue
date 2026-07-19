<template>
  <b-modal
    ref="popupCTKhaoSat"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    centered
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
        <div class="box-form" style="padding: 0px !important;">
            <div class="box-form" style="margin-bottom: 0px !important;">
                <!-- <div class="legend-title red">Kết quả khảo sát</div> -->
                <div class="legend-title">
                    <div class="pull-left" style="margin-top: 7px !important;">
                    Kết quả khảo sát
                    </div>
                    <div class="pull-right ">
                        <div class="info-row">
                            <div class="value">
                                <button @click="btnCapNhat_Click" id="btn_capnhat" class="btn btn-second nocorner" style="width: 120px;" :class="{disabled: !this.btnCapNhat.Enabled, disabled_color: !this.btnCapNhat.Enabled}">
                                    Cập nhật
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w120">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="chkKhongTL.Check" :disabled="!chkKhongTL.Enabled">                                    <span class="name red">Không trả lời</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="cboLyDoKTL.value" class="form-control" :disabled="!chkKhongTL.Check">
                                        <option v-for="item in cboLyDoKTL.data" :key="item.id" :value="item.id">{{item.text}}</option>
                                    </select>
                                </div>
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
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w120">Người trả lời</div>
                            <div class="value">
                                <input ref="inputNguoiTraLoi" :value="input_nguoitraloi" @change="e=>input_nguoitraloi=e.target.value"  type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="info-row">
                            <div class="key w120">Ghi chú</div>
                            <div class="value">
                                <textarea :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control" style="height: 66px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form" style="margin-bottom: 0px !important;">
                <div class="legend-title">Câu trả lời</div>
                <div class="form-control text pad10"  style="height: 280px; overflow-y: scroll;">
                    
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
    </div>
  </b-modal>
</template>
<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import API from './API'
export default {
    name:'CTKhaoSat',
    components:{
        KDatePicker,
    },
    props:{
        tbtl_id:{
            type:Number,
            default:0
        }
    },
    data(){
        return{
            hen_tu:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            hen_den:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            chkKhongTL:{
                Check:false,
                Enabled:false
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
            input_ghichu:'',
            input_nguoitraloi:'',
            chkHenKS_Tu:{
                Check:false,
                Enabled:true
            },
            chkHenKS_Den:{
                Check:false,
                Enabled:true
            },
            dtCauHoi:[],
            dtCommon:[],
            dsTraLoi:[],
            targetTBTL_ID:0,
            btnCapNhat:{
                Enabled:false
            },
            InRole:'HTKH_KSV',//HTKH_KSV, HTKH_ToTruong
            distantDate:3,
            dtCTTBTL:[]

        }
    },
    methods:{
        showModal() {
            this.$refs["popupCTKhaoSat"].show()
        },
        hideModal() {
            this.$refs["popupCTKhaoSat"].hide()
        },
        getDaysInMonth(year, month) {
            return new Date(year, month, 0).getDate();
        },
        async btnCapNhat_Click(){
            if(!this.ValidateAnswer()){
                return
            }
            //Tạo dữ liệu
            let tbTraLoi=[
                {
                    TBTL_ID:this.targetTBTL_ID,
                    NGUOI_TL:this.input_nguoitraloi.trim(),
                    GHICHU:this.input_ghichu.trim(),
                    TRANGTHAI:this.chkKhongTL.Check?0:1,
                    NGUOI_CN:this.$root.token.getUserName(),
                    MAY_CN:'localhost',
                    IP_CN:'127.0.0.1'
                }
            ]
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
            if(dsTraLoi.length==0){
                return
            }
            let tbCTTraLoi=dsTraLoi.filter(x=>x.Check).map(x=>{
                return {
                    TBTL_ID:this.targetTBTL_ID,
                    CAUHOI_ID:x.QuestionID,
                    TRALOI_ID:x.AnswerID,
                    NOIDUNG:x.AnswerTextDetail
                }
            })
            let result=await this.sp_updateoutbound(JSON.stringify(tbTraLoi), JSON.stringify(tbCTTraLoi))
            if(result=='ok'){
                this.$toast.success('Cập nhật thành công!')
            }else{
                this.$toast.error(result)
            }
            //     fcdCSKH.UpdateOutbound(ds);
        },
        ValidateAnswer(){
            if(this.input_nguoitraloi.trim()==''&&!this.chkKhongTL.Check){
                this.$toast.error('Chưa nhập tên người trả lời !')
                this.$refs.inputNguoiTraLoi.focus()
                return false
            }
            //FIMS_LYDO_KTL.KHACHHANG_HEN=20
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

            }

            return true

        },
        async handleShowModal(){
            //Clear data
            this.hen_tu=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.hen_den=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.chkKhongTL={
                Check:false,
                Enabled:false
            }
            this.cboLyDoKTL.value=30
            this.input_ghichu='',
            this.input_nguoitraloi='',
            this.chkHenKS_Tu={
                Check:false,
                Enabled:false
            }
            this.chkHenKS_Den={
                Check:false,
                Enabled:false
            }
            this.dtCauHoi=[]
            this.dtCommon=[]
            this.dsTraLoi=[]
            this.dtCTTBTL=[]
            this.targetTBTL_ID=0
            this.btnCapNhat={
                Enabled:false
            }
            this.distantDate=3
            if(this.InRole=='HTKH_ToTruong'){
                let dateNow=new Date()
                this.distantDate=this.getDaysInMonth(dateNow.getFullYear(),dateNow.getMonth()+1,0)+14
            }else if(this.InRole=='HTKH_KSV'){
                let dateNow=new Date()
                this.distantDate=this.getDaysInMonth(dateNow.getFullYear(),dateNow.getMonth()+1,0)+3
            }
            console.log('distantDate', this.distantDate)
            //
            setTimeout(async()=>{
                //Load form
                await this.FormLoad()
                await this.HienThi_Phieu(this.tbtl_id)
            }, 500)
        },
        async FormLoad(){
            this.dtCauHoi=await this.lay_ds_cauhoi_theoloainv(6,1)
            //Kiểu =1
            let dtCauHoi_1=this.dtCauHoi.filter(x=>x.kieu==1).sort((a,b)=>{return b.thutu-a.thutu})

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
        async HienThi_Phieu(tbtl_id){
            this.targetTBTL_ID=tbtl_id
            let ds=await this.sp_laychitietoutbound(tbtl_id)
            let dtTraLoi=ds.returnd_tb_traloi
            if(!dtTraLoi||dtTraLoi.length==0){
                return
            }
            //"ngay_hoi": "2020-05-03 08:40:47"
            let ngayHoi=moment(dtTraLoi[0].ngay_hoi, 'YYYY-MM-DD HH:mm:ss')
            this.btnCapNhat.Enabled=ngayHoi.add(this.distantDate,'days').isAfter(moment(new Date()))
            //this.btnCapNhat.Enabled=true
            if(dtTraLoi[0].lydoktl_id!=null){
                this.chkKhongTL.Check=true
                this.cboLyDoKTL.value=dtTraLoi[0].lydoktl_id
            }
            this.input_ghichu=dtTraLoi[0].ghichu?dtTraLoi[0].ghichu.toString():''
            this.input_nguoitraloi=dtTraLoi[0].nguoi_tl?dtTraLoi[0].nguoi_tl.toString():''

            var dtOB=[]
            if(ds.returnd_baohong_ob&&ds.returnd_baohong_ob.length>0){
                dtOB=ds.returnd_baohong_ob
            }else if(ds.returnd_lapmoi_ob&&ds.returnd_lapmoi_ob.length>0){
                dtOB=ds.returnd_lapmoi_ob
            }
            if(dtOB.length>0){
                if(dtOB[0].henks_tu){
                    this.hen_tu=moment(dtOB[0].henks_tu, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
                    this.chkHenKS_Tu.Enabled=moment(dtOB[0].henks_tu, 'YYYY-MM-DD HH:mm:ss').isAfter(moment(new Date()))
                }
                if(dtOB[0].henks_den){
                    this.hen_den=moment(dtOB[0].henks_den, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
                    this.chkHenKS_Den.Enabled=moment(dtOB[0].henks_den, 'YYYY-MM-DD HH:mm:ss').isAfter(moment(new Date()))
                }
            }
            //
            this.dtCTTBTL=ds.returnd_ct_tbtraloi
            console.log('dtCTTBTL', this.dtCTTBTL)
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
                        }
                    }
                }
                
            }

            console.log('dtCommon', this.dtCommon)
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
        async sp_laychitietoutbound(tbtl_id){
            try{
                this.loading(true)
                let response = await API.sp_laychitietoutbound(this.axios, tbtl_id)
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
        async sp_updateoutbound(js_tb_traloi, js_ct_tbtraloi){
            try{
                this.loading(true)
                let response = await API.sp_updateoutbound(this.axios, js_tb_traloi, js_ct_tbtraloi)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    return 'ok'
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi, cập nhật không thành công'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi, cập nhật không thành công'
                }
            }
        },

        
    }
}
</script>
<style>
#btn_capnhat{
    background: #fff!important;
    color: #0176FF!important;
    border-style: solid!important;
    border-width: 1px!important;
    padding: 5px 10px!important;
    border-radius: 4px!important;
    border-color: #0176FF!important;
}
#btn_capnhat:hover{
    background: #035FCC!important;
    color: #fff!important;
}
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
.disabled{
    pointer-events:none;
    opacity:0.7;
}
.disabled_color{
  color: gray !important;
}
</style>