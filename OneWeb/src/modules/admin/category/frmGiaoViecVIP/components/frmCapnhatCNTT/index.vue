<template src='./index.html'></template>

<script>
import api from './api'
import moment from 'moment'

export default {
    props:{
    	modalId: [Number, String],
        tinhId: [Number, String],
        hdtbId: [Number, String],
        huongGiaoId: [Number, String]
    },
    data() {
        return {
            title:'Cập nhật dịch vụ CNTT',
            gridEditors:[]
        }
    },
    methods:{
    	async callApiWrapper(action, body, options = { showError: true }) {
	      	let result = null
	      	try {
	        	result = await action(this.axios, body)

	        	return result.data.data != null ? result.data.data : null
	      	} catch (e) {
	        	if (options.showError) {
		          	if (e.data != null) {
		            	this.$toast.error(`${e.data.message}`)
		          	} else {
		            	this.$toast.error(`Có lỗi gọi API`)
		          	}
	        	}
	        	return 'error'
	      	}
	      	return null
	    },
    	onHiddenModal() {
            console.log(this.modalId,'onHiddenModal');
        },
        async onShownModal() {
        	this.gridEditors = await this.callApiWrapper(api.fn_lay_thuoctinh_dong_hdtb_cntt, {
        		vphanvung_id: 0,
          		vtinh_id: this.tinhId,
          		vhdtb_id: this.hdtbId,
          		voptions: "huonggiao_id:"+this.huongGiaoId
        	});

        	this.gridEditors.forEach((e)=>{
        		e.doAction=function(){
        			console.log(e.EDIT_VALUE);
        			e.CHANGED=1;
        		}
        	});

        	if (this.gridEditors.length==0){
        		this.$bvModal.hide(this.modalId);
        		this.$toast.warning('Không có thông tin nào được phép điều chỉnh.');
        	}
        },
    	async onClickBtnChapNhan(){
    		let updateData=[];
    		this.gridEditors.forEach((e)=>{
    			if(e.CHANGED==1){
    				var data ={
    					"EDIT_VALUE":e.EDIT_VALUE,
    					"DATA_NAME":e.FIELD_NAME,
    					"CAPTION":e.CAPTION,
    					"ATT":e.ATT,
    					"DATA_TYPE":e.DATA_TYPE,
    					"TBL_NAME":e.TABLE_NAME
    				}

    				if (e.ATT==2)
    					data.EDIT_VALUE=moment(e.EDIT_VALUE).format('DD/MM/YYYY');
    				else if (e.ATT==9)
    					data.EDIT_VALUE=moment(e.EDIT_VALUE).format('DD/MM/YYYY HH:MI:SS');
    				else
    					data.EDIT_VALUE=e.EDIT_VALUE;

    				updateData.push(data);
    			}
        	});
        	if(updateData.length==0){
        		this.$toast.warning('Không có thông tin nào thay đổi.');
        		return;
        	}

    		let rs = await this.callApiWrapper(
    			api.sp_capnhat_thuoctinh_dong_hdtb_cntt,
    			{
	    			vtinh_id:this.tinhId,
	    			vhdtb_id:this.hdtbId,
	    			vjs_data:JSON.stringify(updateData)
	    		}
    		);
    		if(rs ==null || rs=='OK'){
    			this.$toast.success('Cập nhật thành công');
    			this.$emit('xacnhan', true);
    		}
    	}
    }
}
</script>
