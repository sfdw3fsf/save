import { parseTimeStampToDayjs } from '@/utils/parser/date-time.parser';
import { orNull, orString, orUndefined } from '@/utils/parser/io.parser';
import { UploadFile } from 'antd';
import dayjs from 'dayjs';
import {
  FacilityDetailDTO,
  FacilityDetailListDTO,
  FacilityHistoryFormDTO,
  FacilityHistoryListDTO,
  FacilityListDTO,
} from './dto/facility.dto';
import { FacilityHistoryCreateUpdateREQ, FacilityListFilter, FacilityListUpdateREQ } from './request/facility.request';
import {
  FacilityCompanyNamesRESP,
  FacilityDetailListRESP,
  FacilityDetailRESP,
  FacilityHistoryMaintanancesListRESP,
  FacilityListRESP,
  FacilityTypesRESP,
} from './response/facility.response';

export const facilityCreateToReq = () => {
  return {
    companyName: '',
    managerName: '',
    type: '',
    contact: '',
  };
};

export const facilityDetailIntialFormValue = {
  companyName: '',
  managerName: '',
  type: '',
  contact: '',
  note: '',
  email: '',
};

export const facilityInitialFromValue = {
  maintenanceDate: dayjs().valueOf(),
  maintenanceScheduleDate: undefined,
  facilityId: undefined,
  companyName: undefined,
  contact: '',
  email: '',
  managerId: undefined,
  title: '',
  detail: '',
  maintenanceImages: [],
};

export const facilityListParamsToFilter = (searchParams: URLSearchParams): FacilityListFilter => {
  return {
    search: orUndefined(searchParams.get('search')),
  };
};

export const facilityRespToDto = (r: FacilityListRESP): FacilityListDTO => {
  return {
    key: r.id,
    facilityMaintenances: r.repairQuantities,
    ...r,
  };
};

export const facilityCompanyNamesDtoToOptions = (d: FacilityCompanyNamesRESP[]) => {
  return d.map((item) => ({
    text: item.companyName,
    label: item.companyName,
    value: item.companyName,
  }));
};

export const facilityDetailListRespToDto = (r: FacilityDetailListRESP): FacilityDetailListDTO => {
  return {
    key: r.id,
    id: r.id,
    maintenanceDate: parseTimeStampToDayjs(r.maintenanceDate),
    title: orString(r.title),
    managerName: orString(r?.manager?.fullName),
    managerId: orString(r?.manager?.id),
  };
};

export const facilityDetailRespToDto = (r: FacilityDetailRESP): FacilityDetailDTO => {
  return {
    companyName: orString(r.companyName),
    managerName: orString(r.managerName),
    type: orString(r.type),
    contact: orString(r.contact),
    note: orString(r.note),
    email: orString(r.email),
  };
};

export const facilityDetailDtoToREQ = (r: FacilityDetailDTO): FacilityListUpdateREQ => {
  return {
    companyName: orString(r.companyName),
    managerName: orString(r.managerName),
    type: orString(r.type),
    contact: orString(r.contact),
    note: orString(r.note),
    email: orUndefined(r.email),
  };
};

export const facilityDtoToCreateReq = (r: FacilityHistoryFormDTO, photoIds?: number[]): FacilityHistoryCreateUpdateREQ => {
  return {
    managerId: r.managerId,
    facilityId: orNull(r.facilityId),
    maintenanceDate: r.maintenanceDate?.valueOf(),
    maintenanceScheduleDate: r.maintenanceScheduleDate?.valueOf(),
    title: r.title,
    detail: r.detail,
    maintenanceImageIds: photoIds,
  };
};

export const facilityCompanyNamesDTOToOptions = (d: FacilityCompanyNamesRESP[]) => {
  return d.map((item) => ({
    text: item.companyName,
    label: item.companyName,
    value: item.companyName,
  }));
};

export const facilityHistoryDetailRespToDto = (r: FacilityHistoryMaintanancesListRESP): FacilityHistoryFormDTO => {
  return {
    managerId: orUndefined(r?.manager?.id),
    facilityId: r.facility.id,
    maintenanceDate: orUndefined(Number(r.maintenanceDate)),
    maintenanceScheduleDate: orUndefined(Number(r.maintenanceScheduleDate)),
    title: orString(r.title),
    companyName: orString(r.facility.companyName),
    detail: orString(r.detail),
    contact: orString(r.facility.contact),
    email: orString(r.facility.email),
    note: orString(r.facility.note),
    companyManager: orString(r.facility.managerName),
    maintenanceImages:
      r.maintenanceImages?.map(
        (item) =>
          ({
            uid: item.id.toString(),
            name: item.fileName,
            status: 'done',
            url: process.env.NEXT_PUBLIC_S3_PREFIX + item.key,
          }) as UploadFile,
      ) || [],
  };
};

export const facilityHistoryRespToDto = (r: FacilityHistoryMaintanancesListRESP): FacilityHistoryListDTO => {
  return {
    key: r.id,
    id: r.id,
    maintenanceDate: parseTimeStampToDayjs(r.maintenanceDate),
    maintenanceScheduleDate: parseTimeStampToDayjs(r.maintenanceScheduleDate),
    title: orString(r.title),
    'facility.companyName': orString(r.facility.companyName),
    facilityId: r.facility.id,
    facilityType: r.facility.type,
    type: r.facility.type,
    managerId: orUndefined(r?.manager?.id),
    'manager.fullName': orString(r?.manager?.fullName),
  };
};

export function facilityHistoryListParamToFilter(searchParams: URLSearchParams): FacilityListFilter {
  const date = searchParams.get('date') ? dayjs(Number(searchParams.get('date'))) : null;

  let filter: string | undefined = undefined;

  if (date) {
    const startOfDay = date?.startOf('day').valueOf();
    const endOfDay = date?.endOf('day').valueOf();

    filter = JSON.stringify({
      'maintenanceDate.gte': startOfDay,
      'maintenanceDate.lte': endOfDay,
    });
  }

  return {
    filter: orUndefined(filter),
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as FacilityListFilter;
}
export const facilityTypeDTOToOptions = (d: FacilityTypesRESP[]) => {
  return d.map((item) => ({
    text: item.type,
    label: item.type,
    value: item.id,
  }));
};
