
import axios from 'axios';

const BASE_PATH_DONVI = '/web-ecms/danhmuc-don-vi';

export const getAllDonVi = async () => axios.get(`${BASE_PATH_DONVI}/get-danhsach-don-vi`)

export const updateDonVi = async (data) => axios.put(`${BASE_PATH_DONVI}/update`, data)

export const addDonVi = async (data) => axios.post(`${BASE_PATH_DONVI}/add`, data)

export const deleteDonVi = async (id) => axios.delete(`${BASE_PATH_DONVI}/delete/${id}`)

export const addMultipleDonVi = async (data) => axios.post(`${BASE_PATH_DONVI}/multiple-create`, data)

export const checkValidateExcel = async (data) => axios.post(`${BASE_PATH_DONVI}/validate-batch`, data)


