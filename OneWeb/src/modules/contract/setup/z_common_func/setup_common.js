import axios from 'axios'

export async function fn_kiemtra_thongtin_hopdong (hdtb_id) {
  try {
    let response = await axios.post('/web-thicong/hoinghi_truyenhinh/fn_kiemtra_thongtin_hopdong', {
      hdtb_id: hdtb_id
    })
    if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data == 'OK') {
      return 'OK'
    } else {
      return response.data.data
    }
  }
  catch (e){
    console.log(e)
    return e
  }
}

export async function lay_tt_hdtb_sub (hdtb_id) {
  try {
    let response = await axios.post('/web-thicong/capnhatthongtin/lay_tt_hdtb_sub', {
      vhdtb_id: hdtb_id
    })
    console.log('lay_tt_hdtb_sub')
    console.log(response.data)
    return response.data.data
  }
  catch (e){
    console.log(e)
    return e
  }
}
export async function lay_trangthai_hdtb (hdtb_id) {
  try {
    let response = await axios.post('/web-tracuu/tracuu/lay_tt_hdtb', {
      vhdtb_id: hdtb_id
    })
    console.log('lay_trangthai_hdtb')
    console.log(response.data)
    return response.data.data
  }
  catch (e){
    console.log(e)
    return e
  }
}

export async function kiemtra_dulieu_laphopdong_cntt (input) {
  try {
    let response = await axios.post('/web-thicong/dvcntt/kiemtra_dulieu_laphopdong_cntt', input)
    console.log('kiemtra_dulieu_laphopdong_cntt')
    console.log(response.data)
    return response.data.data
  }
  catch (e){
    console.log(e)
    return e
  }
}

export async function FN_LAY_DS_THUEBAO_THEO_HDKHID (input) {
  try {
    let response = await axios.post('/web-thicong/capnhatthongtin/api_fn_lay_ds_thuebao_theo_hdkhid', input)
    console.log('api_fn_lay_ds_thuebao_theo_hdkhid')
    console.log(response.data)
    return response.data.data
  }
  catch (e){
    console.log(e)
    return e
  }
}

export async function FN_LAY_DS_THUEBAO_THEO_KHACHHANGID (input) {
  try {
    let response = await axios.post('/web-thicong/capnhatthongtin/api_fn_lay_ds_thuebao_theo_khachhangid', input)
    console.log('api_fn_lay_ds_thuebao_theo_khachhangid')
    console.log(response.data)
    return response.data.data
  }
  catch (e){
    console.log(e)
    return e
  }
}


