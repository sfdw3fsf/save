const item_test = {
  Uuid: '21529f3a-9672-4877-bb5e-512b00689946',
  campaign_id: '675',
  bot_campaign_name: 'Test_Chiến dịch OB AutoCall tập thuê bao HVC – Bán gói',
  user_ipcc: 'thuynt7',
  auto_call: 1,
  SoThueBao: '0842075768',
  PHANVUNG_ID: '28',
  MA_TINH: '28',
  OB_ID: 'data_3',
  SDT_THANH_TOAN: '0834727665',
  THUEBAO_ID: 'data_5',
  MA_TB: 'mylienpc12',
  LOAITB_ID: '58',
  TOCDO_ID: 'data_8',
  KHACHHANG_ID: '10148424',
  THANHTOAN_ID: 'data_10',
  TEN_KH: 'TRAN THI MAN',
  DIACHI_KH: 'DIACHI_KH',
  NGAY_BDDC: '2/21/2022',
  NGAY_KTDC: '2/21/2024',
  CUOC_DC: 'data_15',
  SO_THANGDC: 'data_16',
  CHITIETKM_ID: 'data_17',
  TEN_CTKM: 'data_18',
  SL_DATCOC: 'data_19',
  MATB_PHU: 'data_20'
}

class Util {
  decodeQuery(data) {
    if(!data) return {};
    try {
      let decode = atob(data)
      decode = decodeURIComponent(decode)
      decode = decode.split('=')[1].split('&')[0]
      decode = Buffer.from(decode, 'base64').toString('utf8')
      console.log('decode:', decode)
      // return item_test
      return JSON.parse(decode)
    } catch (error) {
      console.log(error);
      return {}
    }
    
  }

}

export default new Util()
