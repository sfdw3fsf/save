<template>
	<div class="col-sm-6 col-12">
		<div class="box-form">
			<div class="legend-title">Thông tin thanh toán</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Tổng tiền TT</div>
						<div class="value">
							<vue-numeric 
								class="text-chartE form-control tright" 
								currency="" 
								separator="," 
								v-model="tien_tt"></vue-numeric>
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Hình thức TT</div>
						<div class="value">
							<div class="select-custom">
								<select2
									class="select2"
									v-model="httt_selected"
									:options="ds_hinhthuctt.map(e=> ({id: e.httt_id, text: e.hinhthuc_tt}))">
								</select2>
							</div>	
						</div>

					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Trạng thái TT</div>
						<div class="value">
								<select2
									class="select2"
									v-model="tttt_selected"
									:options="ds_trangthaitt.map(e=> ({id: e.trangthai_id, text: e.ten_tt}))">
								</select2>
						</div>
					</div>
				</div>

				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Ngày TT</div>
						<div class="value">
							<div class="input-icon-right">
								<ejs-datepicker :format="'dd/MM/yyyy'" v-model="ngay_tt"></ejs-datepicker>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</template>

<script>
import myApi from '../api'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
	name: 'ThongTinThanhToan',
	props: {
		parent: {}
	},
	components: {
		KDatePicker,
		KRequiredMarker
	},
	data() {
		return {
			hdtb:{},
			tien_tt:0,
			ngay_tt:'',
			ds_hinhthuctt: [],
			httt_selected : null,
			ds_trangthaitt: [],
			tttt_selected : null
		}
	},
	methods:{
		clear(){
			this.hdtb={};
			this.tien_tt=0;
			this.ngay_tt='';
			this.httt_selected=null;
			this.tttt_selected=null;
		},
		async initDuLieu() {
			//Danh sách hình thức thanh toán
			let response = await myApi.lay_danhmuc_lapmoi_tt_chung_v7(this.axios, 'HINHTHUC_TT');
			if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
				if (response.data.data.hinhthuc_tt) {
					this.ds_hinhthuctt = response.data.data.hinhthuc_tt
				}
			}

			//Danh sách trạng thái thanh toán
			response = await myApi.lay_danhsach_trangthai_tt(this.axios)
			if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
				this.ds_trangthaitt = response.data.data
			}
		},
		async fillData(data) {
			this.hdtb=data;

			this.tien_tt=data.tien_tt??0;
			this.httt_selected=data.httt_id;
			this.tttt_selected=data.tttt_id;
			this.ngay_tt=this.hdtb.ngay_tt != null ? moment(this.hdtb.ngay_tt, 'DD/MM/YYYY HH:mm:ss').toDate() : null;
		},
		getData() {
			return {
				TIEN_TT:this.tien_tt,
				NGAY_TT: Object.prototype.toString.call(this.ngay_tt) === '[object Date]'?moment(this.ngay_tt).format('DD/MM/YYYY HH:mm:ss'):this.ngay_tt,
				HTTT_ID:this.httt_selected,
				TTTT_ID:this.tttt_selected
			};
		}
	}
}
</script>