<template>
	<div class="box-form">
		<div class="legend-title active">
			<span class="icon fa fa-angle-up hand_click"></span>Thông tin phân chia doanh thu
		</div>
		<DataGrid v-bind:columns="ds_phanchia.headers" v-bind:dataSource="ds_phanchia.data"
			:showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true"
			:showFilter="true"
			>
		</DataGrid>
    </div>
</template>

<script>
import api from '../api/index.js'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
export default {
	name: 'ThongTinPhanChiaDoanhThu',
    components: {
		KDatePicker,
		KRequiredMarker
	},
	// props: {
	// 	parent: {},
	// },
	props: ['propFromParent'],
	data() {
		return {
			ds_phanchia: {
                headers: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: false },
                    { fieldName: 'loai_phattrien', headerText: 'Loại phát triển', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, allowSorting: false },
                    { fieldName: 'ten_cd', headerText: 'Công đoạn', allowFiltering: true, allowSorting: false },
                    { fieldName: 'tyle', headerText: 'Tỷ lệ', allowFiltering: true, allowSorting: false },
                ],
                data:[]
            },
		}
	},
	methods:{
		clear(){
			this.hdtb={};
		},
		async layPhanChiaDoanhThu(data){
            try{
                console.log("Tud ffadfv")
                const response = await api.getPhanChiaDoanhThu(this.axios, data)
                if (response.data && response.data.data && response.data.data.length>0 ) {
                    console.log('có data phân chia doanh thu:',response.data.data)
                    const data = response.data.data.map(item => ({
                        ma_tb: item.ma_tb,
                        ten_dvvt: item.ten_dvvt,
                        loaihinh_tb: item.loaihinh_tb,
                        loai_phattrien: item.ten_loaipt,
                        ten_dv: item.tentinh,
                        ten_cd: item.congdoan,
                        tyle: item.tyle,
                    }));
                    this.ds_phanchia.data = data;
                } else {
                    this.$toast.success("Hợp đồng chưa được gán cơ chế phân chia doanh thu bán chéo")
                }
            } catch (e){
                this.$toast.error('Không tải được Thông tin phân chia doanh thu');
            }
        },

	},
	 mounted () {
		this.loading(true)
		this.layPhanChiaDoanhThu(this.propFromParent)
		this.loading(false)
	},
}
</script>
