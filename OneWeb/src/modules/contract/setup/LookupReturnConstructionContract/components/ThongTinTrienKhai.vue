<template>
	<div class="col-sm-6 col-12">
		<div class="box-form">
			<div class="legend-title">Thông tin triển khai</div>

			<div class="info-row">
				<div class="key w100">Đơn vị BH<k-required-marker /></div>
				<div class="value">
					<input type="text" v-model="donvi" class="form-control" :disabled="true" />
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Phân loại HĐ <k-required-marker/></div>
						<div class="value">
							<input type="text" class="form-control" v-model="phanloai_hd" :disabled="true" />
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Số lượng TB <k-required-marker/></div>
						<div class="value">
							<input type="number" class="form-control" v-model="sl_tb" :disabled="true" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Mã KH <k-required-marker/></div>
						<div class="value">
							<input type="text" class="form-control" v-model="ma_kh" :disabled="true" />
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Mã số thuế <k-required-marker/></div>
						<div class="value">
							<input type="number" class="form-control" v-model="ma_st" :disabled="true" />
						</div>
					</div>
				</div>
			</div>
			<div class="info-row">
				<div class="key w100">Tên KH <k-required-marker/></div>
				<div class="value">
					<input type="text" v-model="ten_kh" class="form-control" :disabled="true" />
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Người nhập <k-required-marker/></div>
						<div class="value">
							<input type="text" class="form-control" v-model="nguoi_cn" :disabled="true" />
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Ngày nhập <k-required-marker/> </div>
						<div class="value">
							<div class="input-icon-right">
								<KDatePicker format="DD/MM/YYYY HH:mm:ss" v-model="ngay_nhap" :disabled="true"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import api from '../api/index.js'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
export default {
	name: 'ThongTinTrienKhai',
    components: {
		KDatePicker,
		KRequiredMarker
	},
	// props: {
	// 	parent: {}
	// },
	props: ['propFromParent'],
	data() {
		return {
			hdtb:{},
			donvi:'',
			phanloai_hd:'',
			sl_tb: 0,
			ten_kh:'',
			ma_kh:'',
			ma_st:'',
			nguoi_cn:'',
			ngay_nhap:'',
			
		}
	},
	methods:{
		clear(){
			this.ma_kh='';	
		},
		async hienThiDanhSachThongTin(p_hdkh_id) {
			try {
				const response = await api.getDsThongTin(this.axios, p_hdkh_id);
				const data = response.data.data;	
				if (data && data.length > 0) {
					const thongTin = data[0];
					this.donvi = thongTin.ten_dv;
					this.phanloai_hd = thongTin.phanloai_hd;
					this.sl_tb = thongTin.sl_tb;
					this.ma_kh = thongTin.ma_kh;
					this.ma_st = thongTin.mst;
					this.ten_kh = thongTin.ten_kh;
					this.nguoi_cn = thongTin.nguoi_cn;
					this.ngay_nhap = thongTin.ngay_cn;
				} 
			} catch (e) {
				this.$toast.error('Không tải được Thông tin văn bản');
			}
		},

	},
	 mounted () {
		this.loading(true)
		this.hienThiDanhSachThongTin(this.propFromParent)
		this.loading(false)
	},
}
</script>
