<template>
	<div class="col-sm-6 col-12">
		<div class="box-form">
			<div class="legend-title">Thông tin văn bản</div>

            <div class="row">
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w100">Số công văn <k-required-marker/></div>
						<div class="value">
							<input type="text" class="form-control" v-model="congvan_id" :disabled="true" />
						</div>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<div class="info-row">
						<div class="key w120">Ngày quyết định <k-required-marker/> </div>
						<div class="value">
							<div class="input-icon-right">
								<KDatePicker format="DD/MM/YYYY HH:mm:ss" v-model="ngay_qd" :disabled="true"/>
							</div>
						</div>
					</div>
				</div>
			</div>

            <div class="info-row">
				<div class="key w100">Tên công văn <k-required-marker/></div>
					<div class="value">
						<input type="text" class="form-control" v-model="ten_congvan" :disabled="true" />
					</div>
			</div>

			<div class="info-row">
				<div class="key w100">ĐV phát hành</div>
					<div class="value">
						<input type="text" class="form-control" v-model="donvi_ph_id" :disabled="true" />
					</div>
			</div>

            <div class="info-row">
				<div class="key w100">File đính kèm <k-required-marker/></div>
					<div class="value">
                        <a class="link" :href="encodeURI(url)" style="cursor: pointer;" @click.prevent="btnFileMau_Click">{{ ten_file }}</a>
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
	name: 'ThongTinVanBan',
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
			ttvb:{},
            congvan_id:'',
            ngay_qd:'',
            ten_congvan:'',
            donvi_ph_id:'',
            ten_file:'',
			url_downfile: '#',
		}
	},
	methods:{
		clear(){
		},
		DownloadFile: async function () {
                var input = {
                    "list_file": this.url_downfile
                };
                this.Loading = true;
                let response = await api.download_file(this.axios, input);
                if(response.data.data && response.data.data.length > 0)
                    this.url_downfile = response.data.data[0].file_url;
                else
                this.url_downfile = "#"; 
                this.Loading = false;
				console.log('Thiện1',this.url_downfile)
            },
		async btnFileMau_Click() {
			await this.DownloadFile();
			if (this.url_downfile && this.url_downfile !== "#") {
				window.open(this.url_downfile, '_blank');
			}
		},
		downloadItem(url, label) {
      this.axios
        .get(url, { responseType: 'blob' })
        .then((response) => {
          const blob = new Blob([response.data], { type: 'application/pdf' })
          const link = document.createElement('a')
          link.href = URL.createObjectURL(blob)
          link.download = label
          link.click()
          URL.revokeObjectURL(link.href)
        })
        .catch(console.error)
    },
	async btnFileMau_Click() {
      window.open('/static/files/MauFile_Import_HopDong_DungThat.xlsx')
    //   await this.downloadItem(this.url,this.ten_file)
    },
		async hienThiDanhSachThongTin(p_hdkh_id) {
			try {
				const response = await api.getDsThongTin(this.axios, p_hdkh_id);
				const data = response.data.data;	
				if (data && data.length > 0) {
					const thongTin = data[0];
					this.congvan_id = thongTin.socongvan;
					this.ngay_qd = thongTin.ngay_qd;
					this.ten_congvan = thongTin.ten_cv;
					this.donvi_ph_id = thongTin.ten_dv;

					const fileResponse = await api.getDsFileDinhKem(this.axios, thongTin.congvan_id);
					console.log('Vào đây')
					const fileList = fileResponse.data.data;
					if (fileList && fileList.length > 0) {
						this.url ='https://storage-onebss.vnpt.vn/onebss-production/' + fileList[0].url;
						this.ten_file = fileList[0].ten_file;
						console.log ('Thiện', this.url)
						
				} }
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
