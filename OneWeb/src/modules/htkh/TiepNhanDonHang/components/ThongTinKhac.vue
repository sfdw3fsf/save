<template>
	<div class="col-sm-6 col-12">
		<div class="box-form">
			<div class="legend-title">Thông tin khác</div>

			<div class="info-row">
				<div class="key w100">Hình thức nhận<k-required-marker /></div>
				<div class="value">
					<select2
						class="select2"
						v-model="htdh_selected"
						:options="ds_hinhthucdh.map(e=> ({id: e.hinhthucdh_id, text: e.hinhthuc_dh}))"
						@change="changeHinhThucNhan">
					</select2>
				</div>
			</div>

			<div class="info-row">
				<div class="key w100">Ghi chú</div>
				<div class="value">
					<input type="text" v-model="ghichu" class="form-control" />
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
		KRequiredMarker
	},
	data() {
		return {
			hdtb:{},
			ghichu:'',
			ds_hinhthucdh: [
				{
					hinhthucdh_id:1,
					hinhthuc_dh:"Tại địa chỉ y/c - COD"
				},
				{
					hinhthucdh_id:2,
					hinhthuc_dh:"Điểm giao dịch"
				},
				{
					hinhthucdh_id:3,
					hinhthuc_dh:"Điểm ủy quyền"
				},
			],
			htdh_selected : null
		}
	},
	methods:{
		clear(){
			this.hdtb={};
			this.ghichu='';
			this.htdh_selected=null;
		},
		async initDuLieu() {
			this.changeHinhThucNhan();
		},
		async fillData(data) {
			this.hdtb=data;
			this.htdh_selected = this.hdtb.httthi_id;
			this.ghichu = this.hdtb.ghichu;
		},
		getData() {
			return {
				HTDH_ID:this.htdh_selected,
				GHICHU:this.ghichu
			};
		},
		changeHinhThucNhan(){
			this.$emit('changed', {
				action: 'CHON_HTDH',
				data: {htdh_id:this.htdh_selected}
			});
		}
	}
}
</script>